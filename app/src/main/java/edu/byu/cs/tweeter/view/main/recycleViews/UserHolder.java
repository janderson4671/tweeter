package edu.byu.cs.tweeter.view.main.recycleViews;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.byu.cs.tweeter.R;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import edu.byu.cs.tweeter.presenter.GetNumFollowPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetNumFollowTask;
import edu.byu.cs.tweeter.view.main.mainFragments.ViewUserActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class UserHolder extends RecyclerView.ViewHolder implements GetNumFollowPresenter.View, GetNumFollowTask.Observer {

    private User user;

    private View userView;
    private GetNumFollowPresenter presenter;

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;

    public UserHolder(@NonNull View itemView) {
        super(itemView);

        userImage = itemView.findViewById(R.id.userImage);
        userAlias = itemView.findViewById(R.id.userAlias);
        userName = itemView.findViewById(R.id.userName);
        presenter = new GetNumFollowPresenter(this);

        user = null;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userView = view;
                fetchCurrentData();
            }
        });
    }

    private void fetchCurrentData() {
        GetNumFollowTask task = new GetNumFollowTask(presenter, this);
        GetNumFollowRequest request = new GetNumFollowRequest(user.getAlias(), new AuthToken());
        task.execute(request);
    }

    public void startViewUserActivity(View view) {
        Intent intent = new Intent(view.getContext(), ViewUserActivity.class);
        intent.putExtra(ViewUserActivity.VIEWED_USER_KEY, user);

        view.getContext().startActivity(intent);
    }

    public void bindUser(User user) {
        this.user = user;
        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
        userAlias.setText(user.getAlias());
        userName.setText(user.getName());
    }

    @Override
    public void numFollowRetrieved(GetNumFollowResponse response) {
        user.setNumFollowers(response.getNumFollowers());
        user.setNumFollowing(response.getNumFollowing());
        startViewUserActivity(userView);
    }

    @Override
    public void NumFollowHandleException(Exception exception) {
        Toast.makeText(userView.getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
