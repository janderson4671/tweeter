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
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;
import edu.byu.cs.tweeter.presenter.GetStoryPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.main.recycleViews.PagedRecyclerView;
import edu.byu.cs.tweeter.view.main.recycleViews.StatusHolder;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;

public class StoryFragment extends Fragment implements GetStoryPresenter.View {

    private static final String LOG_TAG = "StoryFragment";
    private static final int FRAGMENT_CODE = 1;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int PAGE_SIZE = 12;

    private User user;
    private AuthToken authToken;
    private GetStoryPresenter presenter;

    private ViewData data;

    public static StoryFragment newInstance(User user, AuthToken authToken) {
        StoryFragment fragment = new StoryFragment();

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

        presenter = new GetStoryPresenter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        StoryView storyView = new StoryView(getContext(), recyclerView);

        return view;
    }

    private class StoryView extends PagedRecyclerView<StatusHolder, Status> {

        public StoryView(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);

            //set the adapter
            pagedRecyclerViewAdapter = new StoryViewAdapter();
            recyclerView.setAdapter(pagedRecyclerViewAdapter);
        }

        class StoryViewAdapter extends PagedRecyclerViewAdapter implements GetStoryTask.Observer {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                addLoadingFooter();

                GetStoryTask getStoryTask = new GetStoryTask(presenter, this);
                GetStoryRequest request = new GetStoryRequest(user, authToken, PAGE_SIZE, lastItem);
                getStoryTask.execute(request);
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
            public void dataRetrieved(GetStoryResponse response) {

                //Add all mentioned users to viewData for later use
                data.addMentionedUsers(response.getMentionedUsers());

                user.addStoryStatuses(response.getStatuses());
                
                List<Status> story = user.getStory();

                lastItem = (story.size() > 0) ? story.get(story.size() - 1) : null;
                hasMorePages = response.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                addItems(story);
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
