package edu.byu.cs.tweeter.view.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class UserHolder extends RecyclerView.ViewHolder {

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;

    /**
     * Creates an instance and sets an OnClickListener for the user's row.
     *
     * @param itemView the view on which the user will be displayed.
     */
    UserHolder(@NonNull View itemView) {
        super(itemView);

        userImage = itemView.findViewById(R.id.userImage);
        userAlias = itemView.findViewById(R.id.userAlias);
        userName = itemView.findViewById(R.id.userName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:: Call the UserActivity Here
            }
        });
    }

    /**
     * Binds the user's data to the view.
     *
     * @param user the user.
     */
    void bindUser(User user) {
        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        userAlias.setText(user.getAlias());
        userName.setText(user.getName());
    }

}
