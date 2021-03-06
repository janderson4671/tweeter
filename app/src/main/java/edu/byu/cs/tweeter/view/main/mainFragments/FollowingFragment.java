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

import java.util.List;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;
import edu.byu.cs.tweeter.presenter.GetFollowingPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.tweeter.view.main.login.LoginActivity;
import edu.byu.cs.tweeter.view.main.recycleViews.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.recycleViews.UserHolder;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;

/**
 * The fragment that displays on the 'Following' tab.
 */
public class FollowingFragment extends Fragment implements GetFollowingPresenter.View {

    private static final String LOG_TAG = "FollowingFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final int LOADING_DATA_VIEW = 0;
    private static final int PAGE_SIZE = 12;

    //Specific data for this instance
    private User user;
    private AuthToken authToken;
    private GetFollowingPresenter presenter;

    private ViewData data;

    public static FollowingFragment newInstance(User user, AuthToken authToken) {
        FollowingFragment fragment = new FollowingFragment();

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

        data = ViewData.getData();

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new GetFollowingPresenter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        FollowingView followingView = new FollowingView(getContext(), recyclerView);

        return view;
    }

    private class FollowingView extends PagedRecyclerView<UserHolder, User> {

        public FollowingView(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);

            //Set the Adapter
            pagedRecyclerViewAdapter = new FollowingViewAdapter();
            recyclerView.setAdapter(pagedRecyclerViewAdapter);

        }

        class FollowingViewAdapter extends PagedRecyclerViewAdapter implements GetFollowingTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                GetFollowingTask getFollowingTask = new GetFollowingTask(presenter, this);

                GetFollowingRequest request;

                if (lastItem == null) {
                    request = new GetFollowingRequest(user.getAlias(), authToken, PAGE_SIZE, null);
                }
                else {
                    request = new GetFollowingRequest(user.getAlias(), authToken, PAGE_SIZE, lastItem.getAlias());
                }
                getFollowingTask.execute(request);
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
            public void dataRetrieved(GetFollowingResponse response) {


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