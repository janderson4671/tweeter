package edu.byu.cs.tweeter.view.main.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;
import edu.byu.cs.tweeter.presenter.GetFollowersPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowersTask;
import edu.byu.cs.tweeter.view.main.login.LoginActivity;
import edu.byu.cs.tweeter.view.main.recycleViews.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.recycleViews.UserHolder;

/**
 * The fragment that displays on the 'Following' tab.
 */
public class FollowerFragment extends Fragment implements GetFollowersPresenter.View {

    private static final String LOG_TAG = "FollowingFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final int LOADING_DATA_VIEW = 0;
    private static final int PAGE_SIZE = 12;

    //Specific data for this instance
    private User user;
    private AuthToken authToken;
    private GetFollowersPresenter presenter;

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

        presenter = new GetFollowersPresenter(this);

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

        class FollowerViewAdapter extends PagedRecyclerViewAdapter implements GetFollowersTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                GetFollowersTask GetFollowersTask = new GetFollowersTask(presenter, this);

                GetFollowersRequest request;

                if (lastItem == null) {
                    request = new GetFollowersRequest(user.getAlias(), authToken, PAGE_SIZE, null);
                } else {
                    request = new GetFollowersRequest(user.getAlias(), authToken, PAGE_SIZE, lastItem.getAlias());
                }

                GetFollowersTask.execute(request);
            }

            @Override
            protected void addLoadingFooter() {
                addItem(new User("Dummy", "User", "", 0, 0));
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
            public void dataRetrieved(GetFollowersResponse response) {

                if (response.getUsers().size() > 0) {
                    lastItem = (itemList.size() > 0) ? response.getUsers().get(response.getUsers().size() - 1) : null;
                    hasMorePages = response.getHasMorePages();
                }

                isLoading = false;
                removeLoadingFooter();
                addItems(response.getUsers());
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

