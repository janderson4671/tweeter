package edu.byu.cs.tweeter.view.main.mainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;
import com.example.shared.service.response.GetNumFollowResponse;
import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

import edu.byu.cs.tweeter.presenter.DoesFollowPresenter;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.GetNumFollowPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.AddFollowerTask;
import edu.byu.cs.tweeter.view.asyncTasks.DoesFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetNumFollowTask;
import edu.byu.cs.tweeter.view.main.login.LoginActivity;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;
import edu.byu.cs.tweeter.view.main.adapters.ViewUserPagerAdapter;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class ViewUserActivity extends AppCompatActivity implements FollowPresenter.View, AddFollowerTask.Observer
        , DoesFollowPresenter.View, DoesFollowTask.Observer
        , GetNumFollowPresenter.View, GetNumFollowTask.Observer{

    public static final String VIEWED_USER_KEY = "ViewedUser";

    //Widgets
    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followingCount;
    TextView followerCount;
    private Button followButton;

    //Specific data for this instance
    User viewedUser;
    User loggedInUser;
    AuthToken authToken;
    private FollowPresenter followPresenter;
    private DoesFollowPresenter doesFollowPresenter;
    private GetNumFollowPresenter getNumPresenter;

    private ViewData data;
    boolean following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        followPresenter = new FollowPresenter(this);
        doesFollowPresenter = new DoesFollowPresenter(this);
        getNumPresenter = new GetNumFollowPresenter(this);

        data = ViewData.getData();

        loggedInUser = data.getLoggedInUser();
        authToken = data.getAuthToken();

        viewedUser = (User) getIntent().getSerializableExtra(VIEWED_USER_KEY);
        following = isFollowing(loggedInUser, viewedUser, authToken);

        ViewUserPagerAdapter viewUserPagerAdapter = new ViewUserPagerAdapter(this, getSupportFragmentManager(),
                loggedInUser, viewedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager_view_user);
        viewPager.setAdapter(viewUserPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_view_user);
        tabs.setupWithViewPager(viewPager);

        wireWidgets();
        setListeners();
    }

    private void wireWidgets() {
        userName = findViewById(R.id.userName_view_user);
        userName.setText(viewedUser.getName());

        userAlias = findViewById(R.id.userAlias_view_user);
        userAlias.setText(viewedUser.getAlias());

        userImageView = findViewById(R.id.userImage_view_user);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(viewedUser.getImageBytes()));

        followingCount = findViewById(R.id.followeeCount_view_user);
        followingCount.setText("Followers: " + String.valueOf(viewedUser.getNumFollowers()));

        followerCount = findViewById(R.id.followerCount_view_user);
        followerCount.setText("Following: " + String.valueOf(viewedUser.getNumFollowing()));

        followButton = findViewById(R.id.follow_button_view_user);
        followButton.setText("Wait");
        followButton.setEnabled(false);
    }

    public void updateView() {

        if (following) {
            followButton.setText("Unfollow");
        } else {
            followButton.setText("Follow");
        }

        followButton.setEnabled(true);

        GetNumFollowRequest request = new GetNumFollowRequest(viewedUser.getAlias(), authToken);
        GetNumFollowTask task = new GetNumFollowTask(getNumPresenter, this);
        task.execute(request);

    }

    private void setListeners() {
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                followButton.setText("Wait");
                followButton.setEnabled(false);

                if (following) {
                    addFollower(false);
                }
                else {
                    addFollower(true);
                }
            }
        });
    }

    private void updateCounts() {
        followingCount.setText("Followers: " + viewedUser.getNumFollowers());
        followerCount.setText("Following: " + viewedUser.getNumFollowing());
    }

    private void addFollower(boolean follow) {

        FollowRequest request = new FollowRequest(data.getLoggedInUser().getAlias(), authToken, viewedUser.getAlias(), follow);
        AddFollowerTask task = new AddFollowerTask(followPresenter, this);
        task.execute(request);
    }

    //TODO: Make this better
    @Override
    public void addFollowerComplete(FollowResponse response) {

        following = response.isFollowing();

//        if (following) {
//            viewedUser.setNumFollowers(viewedUser.getNumFollowers() + 1);
//            loggedInUser.setNumFollowing(loggedInUser.getNumFollowing() + 1);
//            followingCount.setText("Followers: " + viewedUser.getNumFollowers());
//        }
//        else {
//            viewedUser.setNumFollowers(viewedUser.getNumFollowers() - 1);
//            loggedInUser.setNumFollowing(loggedInUser.getNumFollowing() - 1);
//            followingCount.setText("Followers: " + viewedUser.getNumFollowers());
//        }

        updateView();
    }

    @Override
    public void doesFollowTaskComplete(DoesFollowResponse response) {
        following = response.isDoesFollow();

        updateView();
    }

    @Override
    public void DoesFollowHandleException(Exception exception) {

        if (exception.getMessage().equals("User Session Timed Out")) {
            Intent intent = LoginActivity.newIntent(this);

            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

            startActivity(intent);
        }

        Toast.makeText(this, "I Don't know if you follow this person or not!", Toast.LENGTH_LONG).show();

        updateView();
    }

    @Override
    public void FollowHandleException(Exception exception) {

        if (exception.getMessage().equals("User Session Timed Out")) {
            Intent intent = LoginActivity.newIntent(this);

            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

            startActivity(intent);
        }

        Toast.makeText(this, "Follow Failed!", Toast.LENGTH_LONG).show();

        updateView();
    }

    private boolean isFollowing(User loggedInUser, User viewedUser, AuthToken authToken) {
        DoesFollowRequest request = new DoesFollowRequest(authToken, loggedInUser.getAlias(), viewedUser.getAlias());
        DoesFollowTask task = new DoesFollowTask(doesFollowPresenter, this);
        task.execute(request);

        return false;
    }

    @Override
    public void numFollowRetrieved(GetNumFollowResponse response) {
        viewedUser.setNumFollowing(response.getNumFollowing());
        viewedUser.setNumFollowers(response.getNumFollowers());

        updateCounts();
    }

    @Override
    public void NumFollowHandleException(Exception exception) {

        if (exception.getMessage().equals("User Session Timed Out")) {
            Intent intent = LoginActivity.newIntent(this);

            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

            startActivity(intent);
        }

        Toast.makeText(this, "Could not get current follow stats", Toast.LENGTH_LONG).show();

        updateView();
    }
}
