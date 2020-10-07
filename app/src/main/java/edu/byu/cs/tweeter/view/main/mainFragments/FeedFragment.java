package edu.byu.cs.tweeter.view.main.mainFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.DataRetrievalRequest;
import edu.byu.cs.tweeter.model.service.response.DataRetrievalResponse;
import edu.byu.cs.tweeter.presenter.DataRetrievalPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.DataRetrievalTask;
import edu.byu.cs.tweeter.view.main.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.StatusHolder;
import edu.byu.cs.tweeter.view.main.UserHolder;
import edu.byu.cs.tweeter.view.main.ViewData;

public class FeedFragment extends Fragment implements DataRetrievalPresenter.View{

    private static final String LOG_TAG = "FeedFragment";
    private static final int FRAGMENT_CODE = 0;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int PAGE_SIZE = 12;

    private User user;
    private AuthToken authToken;
    private DataRetrievalPresenter presenter;

    private ViewData data;

    public static FeedFragment newInstance(User user, AuthToken authToken) {
        FeedFragment fragment = new FeedFragment();

        Bundle args = new Bundle(2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        data = ViewData.getData();

        user = data.getLoggedInUser();
        authToken = data.getAuthToken();

        presenter = new DataRetrievalPresenter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    private class FeedView extends PagedRecyclerView<UserHolder, User> {

        public FeedView(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);

            //Set the Adapter
            pagedRecyclerViewAdapter = new FeedViewAdapter();
            recyclerView.setAdapter(pagedRecyclerViewAdapter);

        }

        class FeedViewAdapter extends PagedRecyclerViewAdapter implements DataRetrievalTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                DataRetrievalTask dataRetrievalTask = new DataRetrievalTask(presenter, this);
                DataRetrievalRequest request = new DataRetrievalRequest(user, PAGE_SIZE, FRAGMENT_CODE, lastItem);
                dataRetrievalTask.execute(request);
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
            public void dataRetrieved(DataRetrievalResponse response) {
                List<User> followees = response.getData();

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
