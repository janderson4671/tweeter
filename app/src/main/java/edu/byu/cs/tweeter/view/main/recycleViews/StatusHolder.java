package edu.byu.cs.tweeter.view.main.recycleViews;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.view.main.mainFragments.ViewUserActivity;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;

public class StatusHolder extends RecyclerView.ViewHolder {

    private Status status;

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;
    private final TextView userMessage;
    private final TextView userTimeStamp;

    private ViewData data = ViewData.getData();

    public StatusHolder(@NonNull View itemView) {
        super(itemView);

        userImage = itemView.findViewById(R.id.userImage);
        userAlias = itemView.findViewById(R.id.userAlias);
        userName = itemView.findViewById(R.id.userName);
        userMessage = itemView.findViewById(R.id.statusMessage);
        userTimeStamp = itemView.findViewById(R.id.timeStamp);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:: Start StatusActivity?
            }
        });
    }

    public void bindUser(Status status) {

        this.status = status;

        //userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        userAlias.setText(status.getUser().getAlias());
        userName.setText(status.getUser().getFirstName());
        userMessage.setMovementMethod(LinkMovementMethod.getInstance());
        userMessage.setText(formatMessage(status.getMessage()));
        userTimeStamp.setText(status.getTimeStamp().toString());
    }

    private SpannableString formatMessage(String message) {

        Pattern pattern = Pattern.compile("@\\w*");
        Matcher matcher = pattern.matcher(message);

        SpannableString string = new SpannableString(message);

        while (matcher.find()) {

            int start = matcher.start();
            int end = matcher.end();

            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    startViewUserActivity(widget, message.substring(start, end));
                }
            };

            if (checkValidUser(message.substring(start, end))) {
                string.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }

        return string;

    }

    private boolean checkValidUser(String alias) {
        List<User> mentions = data.getAllMentionedUsers();

        if (mentions == null) {
            return false;
        }

        for (User currUser : mentions) {
            if (currUser.getAlias().equals(alias)) {
                return true;
            }
        }

        return false;
    }

    public void startViewUserActivity(View v, String alias) {
        List<User> mentions = data.getAllMentionedUsers();
        User selectedUser = null;

        for (User currUser : mentions) {
            if(currUser.getAlias().equals(alias)) {
                selectedUser = currUser;
            }
        }

        if (selectedUser != null) {
            Intent intent = new Intent(v.getContext(), ViewUserActivity.class);
            intent.putExtra(ViewUserActivity.VIEWED_USER_KEY, selectedUser);

            v.getContext().startActivity(intent);
        }

    }

}
