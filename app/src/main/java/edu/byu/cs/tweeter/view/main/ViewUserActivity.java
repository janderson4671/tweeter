package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class ViewUserActivity extends AppCompatActivity {

    public static final String VIEWED_USER_KEY = "ViewedUser";

    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followeeCount;
    TextView followerCount;

    private Button followButton;

    private ViewData data;

    User viewedUser;
    User loggedInUser;
    AuthToken authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

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
                    followButton.setText("Unfollow");
                    viewedUser.addFollowee(viewedUser);
                    loggedInUser.addFollower(viewedUser);
                    updateView();
                }
                else {
                    followButton.setText("Follow");
                    viewedUser.removeFollowee(viewedUser);
                    loggedInUser.removeFollower(viewedUser);
                    updateView();
                }
            }
        });
    }

}
