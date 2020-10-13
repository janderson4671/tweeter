package edu.byu.cs.tweeter.view.main.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.main.adapters.LoginPagerAdapter;

/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;
    private Toast loginInToast;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get tabs to show up
        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_login);
        viewPager.setAdapter(loginPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_login);
        tabs.setupWithViewPager(viewPager);
    }

}
