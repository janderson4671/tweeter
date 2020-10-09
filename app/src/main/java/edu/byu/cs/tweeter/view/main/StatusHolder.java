package edu.byu.cs.tweeter.view.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class StatusHolder extends RecyclerView.ViewHolder {

    private User user;

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

        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        userAlias.setText(user.getAlias());
        userName.setText(user.getName());
        userMessage.setText(status.getMessage());
        userTimeStamp.setText(status.getTimeStamp().toString());
    }

}
