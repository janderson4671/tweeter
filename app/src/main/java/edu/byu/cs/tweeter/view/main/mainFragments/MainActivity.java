package edu.byu.cs.tweeter.view.main.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

import edu.byu.cs.tweeter.presenter.GetNumFollowPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetNumFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;
import edu.byu.cs.tweeter.view.main.adapters.SectionsPagerAdapter;
import edu.byu.cs.tweeter.view.main.login.LoginActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LogoutPresenter.View, LogoutTask.Observer
                ,GetNumFollowPresenter.View, GetNumFollowTask.Observer {

    private static final String LOG_TAG = "MainActivity";

    LogoutPresenter logoutPresenter;
    GetNumFollowPresenter getNumPresenter;
    User loggedInUser;
    AuthToken authToken;
    ViewData data;
    Context mContext;

    //Widgets
    TextView userName;
    TextView userAlias;
    ImageView userImageView;
    TextView followeeCount;
    TextView followerCount;
    Button logoutButton;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = ViewData.getData();

        loggedInUser = data.getLoggedInUser();
        authToken = data.getAuthToken();

        FragmentManager fm = getSupportFragmentManager();

        mContext = this;
        logoutPresenter = new LogoutPresenter(this);
        getNumPresenter = new GetNumFollowPresenter(this);

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
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(loggedInUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText("Followers: " + String.valueOf(loggedInUser.getNumFollowers()));

        followerCount = findViewById(R.id.followerCount);
        followerCount.setText("Following: " + String.valueOf(loggedInUser.getNumFollowing()));

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void updateView() {
        followeeCount.setText("Followers: " + String.valueOf(loggedInUser.getNumFollowers()));
        followerCount.setText("Following: " + String.valueOf(loggedInUser.getNumFollowing()));
    }

    private void logoutUser() {
        LogoutTask task = new LogoutTask(logoutPresenter, this);
        LogoutRequest request = new LogoutRequest(data.getLoggedInUser().getAlias(), data.getAuthToken());
        task.execute(request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GetNumFollowRequest request = new GetNumFollowRequest(loggedInUser.getAlias(), authToken);
        GetNumFollowTask task = new GetNumFollowTask(getNumPresenter, this);
        task.execute(request);
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

    @Override
    public void numFollowRetrieved(GetNumFollowResponse response) {
        loggedInUser.setNumFollowers(response.getNumFollowers());
        loggedInUser.setNumFollowing(response.getNumFollowing());

        updateView();
    }

    @Override
    public void NumFollowHandleException(Exception exception) {

        if (exception.getMessage().equals("User Session Timed Out")) {
            Intent intent = LoginActivity.newIntent(mContext);

            Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();

            startActivity(intent);
        }

        Toast.makeText(this, "Cannot update counts", Toast.LENGTH_LONG).show();
    }
}