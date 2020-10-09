package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followeeCount;
    TextView followerCount;

    User loggedInUser;
    AuthToken authToken;

    ViewData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = ViewData.getData();

        loggedInUser = data.getLoggedInUser();
        authToken = data.getAuthToken();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), loggedInUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager_main);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_main);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        userName = findViewById(R.id.userName);
        userName.setText(loggedInUser.getName());

        userAlias = findViewById(R.id.userAlias);
        userAlias.setText(loggedInUser.getAlias());

        userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(loggedInUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText("Followers: " + String.valueOf(loggedInUser.getFolloweeCount()));

        followerCount = findViewById(R.id.followerCount);
        followerCount.setText("Following: " + String.valueOf(loggedInUser.getFollowerCount()));
    }

    private void updateView() {
        followeeCount.setText("Followers: " + String.valueOf(loggedInUser.getFolloweeCount()));
        followerCount.setText("Following: " + String.valueOf(loggedInUser.getFollowerCount()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateView();
    }


}