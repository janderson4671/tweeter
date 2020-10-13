package edu.byu.cs.tweeter.view.main.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;
import edu.byu.cs.tweeter.view.main.adapters.SectionsPagerAdapter;
import edu.byu.cs.tweeter.view.main.login.LoginActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LogoutPresenter.View, LogoutTask.Observer {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private NewStatusFragment newStatusFragment;

    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followeeCount;
    TextView followerCount;
    Button logoutButton;
    FloatingActionButton fab;

    LogoutPresenter presenter;

    User loggedInUser;
    AuthToken authToken;

    ViewData data;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = ViewData.getData();

        loggedInUser = data.getLoggedInUser();
        authToken = data.getAuthToken();

        FragmentManager fm = getSupportFragmentManager();

        mContext = this;
        presenter = new LogoutPresenter(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), loggedInUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager_main);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_main);
        tabs.setupWithViewPager(viewPager);


        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewStatusFrag(fm);
            }
        });

        userName = findViewById(R.id.userName);
        userName.setText(loggedInUser.getName());

        userAlias = findViewById(R.id.userAlias);
        userAlias.setText(loggedInUser.getAlias());

        userImageView = findViewById(R.id.userImage);

        if (data.getProfile() != null) {
            userImageView.setImageBitmap(data.getProfile());
        } else {
            userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(loggedInUser.getImageBytes()));
        }

        followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText("Followers: " + String.valueOf(loggedInUser.getFolloweeCount()));

        followerCount = findViewById(R.id.followerCount);
        followerCount.setText("Following: " + String.valueOf(loggedInUser.getFollowerCount()));

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void updateView() {
        followeeCount.setText("Followers: " + String.valueOf(loggedInUser.getFolloweeCount()));
        followerCount.setText("Following: " + String.valueOf(loggedInUser.getFollowerCount()));
    }

    private void logoutUser() {
        LogoutTask task = new LogoutTask(presenter, this);
        LogoutRequest request = new LogoutRequest(data.getLoggedInUser(), data.getAuthToken());
        task.execute(request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateView();
    }

    private void startNewStatusFrag(FragmentManager fm) {
        FragmentTransaction transaction = fm.beginTransaction();
        NewStatusFragment frag = NewStatusFragment.newInstance();

        frag.show(transaction, "Dialogue");
    }

    @Override
    public void logoutComplete(LogoutResponse response) {

        Toast.makeText(this, "Logged Out!", Toast.LENGTH_LONG).show();
        Intent intent = LoginActivity.newIntent(mContext);

        startActivity(intent);
    }

    @Override
    public void handleException(Exception exception) {
        Toast.makeText(this, "Error: Can't Log out!", Toast.LENGTH_LONG).show();
    }
}