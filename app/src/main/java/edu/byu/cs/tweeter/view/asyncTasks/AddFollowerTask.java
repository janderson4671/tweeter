package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class AddFollowerTask extends AsyncTask<FollowRequest, Void, FollowResponse> {

    private final FollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void addFollowerComplete(FollowResponse response);
        void FollowHandleException(Exception exception);
    }

    public AddFollowerTask(FollowPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowResponse doInBackground(FollowRequest... requests) {
        FollowResponse response = null;

        try {
            response = presenter.follow(requests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(FollowResponse response) {
        if (exception != null) {
            observer.FollowHandleException(exception);
        } else {
            observer.addFollowerComplete(response);
        }
    }
}
