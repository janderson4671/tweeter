package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingResponse;
import edu.byu.cs.tweeter.presenter.GetFollowingPresenter;

/**
 * An {@link AsyncTask} for retrieving followees for a user.
 */
public class FollowTask extends AsyncTask<GetFollowingRequest, Void, GetFollowingResponse> {

    private final GetFollowingPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(GetFollowingResponse getFollowingResponse);
        void handleException(Exception exception);
    }

    public FollowTask(GetFollowingPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetFollowingResponse doInBackground(GetFollowingRequest... getFollowingRequests) {

        GetFollowingResponse response = null;

        try {
            response = presenter.getFollowing(getFollowingRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetFollowingResponse getFollowingResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(getFollowingResponse);
        }
    }
}
