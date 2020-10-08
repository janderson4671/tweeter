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
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.DataRetrievalRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.DataRetrievalResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.main.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.UserHolder;
import edu.byu.cs.tweeter.view.main.ViewData;

/**
 * The fragment that displays on the 'Following' tab.
 */
public class FollowingFragment extends Fragment implements FollowingPresenter.View {

    private static final String LOG_TAG = "FollowingFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final int FRAGMENT_CODE = 2;

    private static final int LOADING_DATA_VIEW = 0;

    private static final int PAGE_SIZE = 12;

    private User user;
    private AuthToken authToken;
    private FollowingPresenter presenter;

    private ViewData data;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user      the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
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

        user = data.getLoggedInUser();
        authToken = data.getAuthToken();

        presenter = new FollowingPresenter(this);

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

        class FollowingViewAdapter extends PagedRecyclerViewAdapter implements FollowTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                FollowTask followTask = new FollowTask(presenter, this);
                FollowRequest request = new FollowRequest(user, PAGE_SIZE, lastItem, FRAGMENT_CODE);
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
            public void dataRetrieved(FollowResponse response) {
                List<User> followees = response.getFollowees();

                lastItem = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
                hasMorePages = response.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                addItems(followees);
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