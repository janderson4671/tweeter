package edu.byu.cs.tweeter.view.main.mainFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.AddFollowerTask;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;
import edu.byu.cs.tweeter.view.main.adapters.ViewUserPagerAdapter;

public class ViewUserActivity extends AppCompatActivity implements FollowPresenter.View, AddFollowerTask.Observer {

    public static final String VIEWED_USER_KEY = "ViewedUser";

    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followingCount;
    TextView followerCount;

    private Button followButton;

    private ViewData data;

    private FollowPresenter presenter;

    User viewedUser;
    User loggedInUser;
    AuthToken authToken;

    boolean following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        presenter = new FollowPresenter(this);

        data = ViewData.getData();

        loggedInUser = data.getLoggedInUser();
        authToken = data.getAuthToken();

        viewedUser = (User) getIntent().getSerializableExtra(VIEWED_USER_KEY);
        following = false;

        ViewUserPagerAdapter viewUserPagerAdapter = new ViewUserPagerAdapter(this, getSupportFragmentManager(),
                viewedUser, null, authToken);
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
        //userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(viewedUser.getImageBytes()));

        followingCount = findViewById(R.id.followeeCount_view_user);
        followingCount.setText("Followers: " + String.valueOf(viewedUser.getFolloweeCount()));

        followerCount = findViewById(R.id.followerCount_view_user);
        followerCount.setText("Following: " + String.valueOf(viewedUser.getFollowerCount()));

        followButton = findViewById(R.id.follow_button_view_user);
    }

    public void updateView() {
        followingCount.setText("Followers: " + String.valueOf(viewedUser.getFolloweeCount()));
        followerCount.setText("Following: " + String.valueOf(viewedUser.getFollowerCount()));
    }

    private void setListeners() {
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (following) {
                    addFollower(false);
                }
                else {
                    addFollower(true);
                }
            }
        });
    }

    private void addFollower(boolean follow) {

        if (follow) {
            followButton.setText("Unfollow");
        } else {
            followButton.setText("Follow");
        }

        FollowRequest request = new FollowRequest(data.getLoggedInUser().getAlias(), authToken, viewedUser.getAlias(), following);
        AddFollowerTask task = new AddFollowerTask(presenter, this);
        task.execute(request);
    }

    @Override
    public void addFollowerComplete(FollowResponse response) {

        following = response.isFollowing();

        if (following) {
            viewedUser.addFollower(null);
            followingCount.setText("Followers: " + viewedUser.getFollowerCount());
        }
        else {
            viewedUser.removeFollower(null);
            followingCount.setText("Followers: " + viewedUser.getFollowerCount());
        }
    }

    @Override
    public void handleException(Exception exception) {
        Toast.makeText(this, "Follow Failed!", Toast.LENGTH_LONG).show();
    }
}
