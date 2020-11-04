package edu.byu.cs.tweeter.view.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.view.main.recycleViews.PlaceholderFragment;
import edu.byu.cs.tweeter.view.main.mainFragments.FollowerFragment;
import edu.byu.cs.tweeter.view.main.mainFragments.FollowingFragment;
import edu.byu.cs.tweeter.view.main.mainFragments.StoryFragment;

public class ViewUserPagerAdapter extends FragmentPagerAdapter {

    private static final int STORY_FRAGMENT_POSOTION = 0;
    private static final int FOLLOWING_FRAGMENT_POSITION = 1;
    private static final int FOLLOWER_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private final User userBeingViewed;
    private final AuthToken authToken;

    public ViewUserPagerAdapter(Context context, FragmentManager fm, User user, User userBeingViewed, AuthToken authToken) {
        super(fm);
        mContext = context;
        this.user = user;
        this.authToken = authToken;
        this.userBeingViewed = userBeingViewed;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == STORY_FRAGMENT_POSOTION) {
            return StoryFragment.newInstance(user, authToken);
        } else if (position == FOLLOWING_FRAGMENT_POSITION) {
            return FollowingFragment.newInstance(user, authToken);
        } else if (position == FOLLOWER_FRAGMENT_POSITION) {
            return FollowerFragment.newInstance(user, authToken);
        } else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString((TAB_TITLES[position]));
    }

    @Override
    public int getCount() {
        //show 3 pages
        return 3;
    }
}
