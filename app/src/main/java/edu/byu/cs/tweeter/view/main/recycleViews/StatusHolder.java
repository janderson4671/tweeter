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
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.main.mainFragments.ViewUserActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class StatusHolder extends RecyclerView.ViewHolder {

    private User user;
    private Status status;

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;
    private final TextView userMessage;
    private final TextView userTimeStamp;

    /**
     * Creates an instance and sets an OnClickListener for the user's row.
     *
     * @param itemView the view on which the user will be displayed.
     */
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

        this.user = status.getUser();
        this.status = status;

        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        userAlias.setText(user.getAlias());
        userName.setText(user.getName());
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
        List<User> mentions = status.getMentions();

        for (User currUser : mentions) {
            if (currUser.getAlias().equals(alias)) {
                return true;
            }
        }

        return false;
    }

    public void startViewUserActivity(View v, String alias) {
        List<User> mentions = status.getMentions();
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
