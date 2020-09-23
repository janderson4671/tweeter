package edu.byu.cs.tweeter.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;

public class LoginPagerAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[] {R.string.loginTabTitle, R.string.registerTabTitle};
    private final int LOGIN_FRAGMENT = 0;
    private final int REGISTER_FRAGMENT = 1;
    private final Context mContext;

    public LoginPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == LOGIN_FRAGMENT) {
            return LoginFragment.newInstance();
        }
        else {
            return PlaceholderFragment.newInstance(position +10);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        //Show 2 pages
        return 2;
    }
}
