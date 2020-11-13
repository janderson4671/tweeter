package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import edu.byu.cs.tweeter.presenter.GetNumFollowPresenter;

public class GetNumFollowTask extends AsyncTask<GetNumFollowRequest, Void, GetNumFollowResponse> {

    private final GetNumFollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void numFollowRetrieved(GetNumFollowResponse response);
        void handleException(Exception exception);
    }

    public GetNumFollowTask(GetNumFollowPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetNumFollowResponse doInBackground(GetNumFollowRequest... getNumFollowRequests) {
        GetNumFollowResponse response = null;

        try {
            response = presenter.getNumFollow(getNumFollowRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetNumFollowResponse response) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.numFollowRetrieved(response);
        }
    }
}
