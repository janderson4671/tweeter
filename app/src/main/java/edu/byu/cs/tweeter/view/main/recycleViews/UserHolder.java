package edu.byu.cs.tweeter.view.main.recycleViews;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.view.main.mainFragments.ViewUserActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class UserHolder extends RecyclerView.ViewHolder {

    private User user;

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;

    public UserHolder(@NonNull View itemView) {
        super(itemView);

        userImage = itemView.findViewById(R.id.userImage);
        userAlias = itemView.findViewById(R.id.userAlias);
        userName = itemView.findViewById(R.id.userName);

        user = null;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewUserActivity.class);
                intent.putExtra(ViewUserActivity.VIEWED_USER_KEY, user);

                view.getContext().startActivity(intent);
            }
        });
    }

    public void bindUser(User user) {
        this.user = user;
        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        userAlias.setText(user.getAlias());
        userName.setText(user.getName());
    }

}
