package edu.byu.cs.tweeter.view.main.mainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;
import edu.byu.cs.tweeter.presenter.GetFollowingPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.main.recycleViews.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.recycleViews.UserHolder;

/**
 * The fragment that displays on the 'Following' tab.
 */
public class FollowerFragment extends Fragment implements GetFollowingPresenter.View {

    private static final String LOG_TAG = "FollowingFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final int FRAGMENT_CODE = 3;

    private static final int LOADING_DATA_VIEW = 0;

    private static final int PAGE_SIZE = 12;

    private User user;
    private AuthToken authToken;
    private GetFollowingPresenter presenter;

    public static FollowerFragment newInstance(User user, AuthToken authToken) {
        FollowerFragment fragment = new FollowerFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new GetFollowingPresenter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        FollowerView followingView = new FollowerView(getContext(), recyclerView);

        return view;
    }

    private class FollowerView extends PagedRecyclerView<UserHolder, User> {

        public FollowerView(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);

            //Set the Adapter
            pagedRecyclerViewAdapter = new FollowerViewAdapter();
            recyclerView.setAdapter(pagedRecyclerViewAdapter);

        }

        class FollowerViewAdapter extends PagedRecyclerViewAdapter implements FollowTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                FollowTask followTask = new FollowTask(presenter, this);
                GetFollowingRequest request = new GetFollowingRequest(user, authToken, PAGE_SIZE, lastItem, FRAGMENT_CODE);
                followTask.execute(request);
            }

            @Override
            protected void addLoadingFooter() {
                addItem(new User("Dummy", "User", ""));
            }

            @NonNull
            @Override
            public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view;

                if (viewType == LOADING_DATA_VIEW) {
                    view = layoutInflater.inflate(R.layout.loading_row, parent, false);

                } else {
                    view = layoutInflater.inflate(R.layout.user_row, parent, false);
                }

                return new UserHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull UserHolder holder, int position) {
                if (!isLoading) {
                    holder.bindUser(itemList.get(position));
                }
            }

            @Override
            public void dataRetrieved(GetFollowingResponse response) {

                user.addFollowers(response.getUsers());

                List<User> followers = user.getFollowers();

                lastItem = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
                hasMorePages = response.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                addItems(followers);
            }

            @Override
            public void handleException(Exception exception) {
                Log.e(LOG_TAG, exception.getMessage(), exception);
                removeLoadingFooter();
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}

