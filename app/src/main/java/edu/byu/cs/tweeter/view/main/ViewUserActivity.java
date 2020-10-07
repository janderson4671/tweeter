package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;
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

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String VIEWED_USER_KEY = "ViewedUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        //Validate User
        User superUser = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        User viewedUser = (User) getIntent().getSerializableExtra(VIEWED_USER_KEY);

        ViewUserPagerAdapter viewUserPagerAdapter = new ViewUserPagerAdapter(this, getSupportFragmentManager(),
                viewedUser, null, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager_view_user);
        viewPager.setAdapter(viewUserPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_view_user);
        tabs.setupWithViewPager(viewPager);


        TextView userName = findViewById(R.id.userName_view_user);
        userName.setText(viewedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias_view_user);
        userAlias.setText(viewedUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage_view_user);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(viewedUser.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount_view_user);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount_view_user);
        followerCount.setText(getString(R.string.followerCount, 27));
    }


}
