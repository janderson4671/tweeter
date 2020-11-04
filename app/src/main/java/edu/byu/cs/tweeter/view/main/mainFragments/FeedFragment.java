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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.R;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;
import edu.byu.cs.tweeter.presenter.GetFeedPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.view.main.recycleViews.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.recycleViews.StatusHolder;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;

public class FeedFragment extends Fragment implements GetFeedPresenter.View{

    private static final String LOG_TAG = "FeedFragment";
    private static final int FRAGMENT_CODE = 0;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int PAGE_SIZE = 12;

    private User user;
    private AuthToken authToken;
    private GetFeedPresenter presenter;

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

        presenter = new GetFeedPresenter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        FeedView feedView = new FeedView(getContext(), recyclerView);

        return view;
    }

    private class FeedView extends PagedRecyclerView<StatusHolder, Status> {

        public FeedView(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);

            //Set the Adapter
            pagedRecyclerViewAdapter = new FeedViewAdapter();
            recyclerView.setAdapter(pagedRecyclerViewAdapter);

        }

        class FeedViewAdapter extends PagedRecyclerViewAdapter implements GetFeedTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                GetFeedTask dataRetrievalTask = new GetFeedTask(presenter, this);
                GetFeedRequest request = new GetFeedRequest(user, authToken, PAGE_SIZE, lastItem);
                dataRetrievalTask.execute(request);
            }

            @Override
            protected void addLoadingFooter() {
                addItem(new Status(new User("Dummy", "User", ""), "Hello", new Date(System.currentTimeMillis()), new ArrayList<>()));
            }

            @NonNull
            @Override
            public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view;

                if (viewType == LOADING_DATA_VIEW) {
                    view = layoutInflater.inflate(R.layout.loading_row, parent, false);

                } else {
                    view = layoutInflater.inflate(R.layout.status_row, parent, false);
                }

                return new StatusHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull StatusHolder holder, int position) {
                if (!isLoading) {
                    holder.bindUser(itemList.get(position));
                }
            }

            @Override
            public void dataRetrieved(GetFeedResponse response) {

                //Add to list of mentioned users
                data.addMentionedUsers(response.getMentionedUsers());

                user.addFeedStatuses(response.getStatuses());

                List<Status> feed = user.getFeed();

                lastItem = (feed.size() > 0) ? feed.get(feed.size() - 1) : null;
                hasMorePages = response.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                addItems(feed);
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
