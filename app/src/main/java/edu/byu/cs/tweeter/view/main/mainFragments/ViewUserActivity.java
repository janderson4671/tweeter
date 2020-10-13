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
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.AddFollowerPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.AddFollowerTask;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;
import edu.byu.cs.tweeter.view.main.adapters.ViewUserPagerAdapter;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class ViewUserActivity extends AppCompatActivity implements AddFollowerPresenter.View, AddFollowerTask.Observer {

    public static final String VIEWED_USER_KEY = "ViewedUser";

    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followeeCount;
    TextView followerCount;

    private Button followButton;

    private ViewData data;

    private AddFollowerPresenter presenter;

    User viewedUser;
    User loggedInUser;
    AuthToken authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        presenter = new AddFollowerPresenter(this);

        data = ViewData.getData();

        loggedInUser = data.getLoggedInUser();
        authToken = data.getAuthToken();

        viewedUser = (User) getIntent().getSerializableExtra(VIEWED_USER_KEY);

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
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(viewedUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount_view_user);
        followeeCount.setText("Followers: " + String.valueOf(viewedUser.getFolloweeCount()));

        followerCount = findViewById(R.id.followerCount_view_user);
        followerCount.setText("Following: " + String.valueOf(viewedUser.getFollowerCount()));

        followButton = findViewById(R.id.follow_button_view_user);
    }

    public void updateView() {
        followeeCount.setText("Followers: " + String.valueOf(viewedUser.getFolloweeCount()));
        followerCount.setText("Following: " + String.valueOf(viewedUser.getFollowerCount()));
    }

    private void setListeners() {
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followButton.getText().equals("Follow")) {
                    addFollower(true);
                }
                else {
                    addFollower(false);
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

        AddFollowerRequest request = new AddFollowerRequest(data.getLoggedInUser(), authToken, viewedUser, follow);
        AddFollowerTask task = new AddFollowerTask(presenter, this);
        task.execute(request);
    }

    @Override
    public void addFollowerComplete(Response response) {
        if (followButton.getText().equals("Unfollow")) {
            viewedUser.addFollower(null);
            followerCount.setText("Following: " + viewedUser.getFollowerCount());
        }
        else {
            viewedUser.removeFollower(null);
            followerCount.setText("Following: " + viewedUser.getFollowerCount());
        }
    }

    @Override
    public void handleException(Exception exception) {
        Toast.makeText(this, "Follow Failed!", Toast.LENGTH_LONG).show();
    }
}
