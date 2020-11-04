package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import edu.byu.cs.tweeter.presenter.GetFollowersPresenter;

public class GetFollowersTask extends AsyncTask<GetFollowersRequest, Void, GetFollowersResponse> {

    private final GetFollowersPresenter presenter;
    private final GetFollowersTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(GetFollowersResponse GetFollowersResponse);
        void handleException(Exception exception);
    }

    public GetFollowersTask(GetFollowersPresenter presenter, GetFollowersTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetFollowersResponse doInBackground(GetFollowersRequest... getFollowersRequests) {

        GetFollowersResponse response = null;

        try {
            response = presenter.GetFollowers(getFollowersRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetFollowersResponse GetFollowersResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(GetFollowersResponse);
        }
    }

}
