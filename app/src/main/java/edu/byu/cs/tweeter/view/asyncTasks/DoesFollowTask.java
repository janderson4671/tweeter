package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;

import edu.byu.cs.tweeter.presenter.DoesFollowPresenter;

public class DoesFollowTask extends AsyncTask<DoesFollowRequest, Void, DoesFollowResponse> {

    private final DoesFollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void doesFollowTaskComplete(DoesFollowResponse response);
        void DoesFollowHandleException(Exception exception);
    }

    public DoesFollowTask(DoesFollowPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected DoesFollowResponse doInBackground(DoesFollowRequest... doesFollowRequests) {
        DoesFollowResponse response = null;

        try {
            response = presenter.doesFollow(doesFollowRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(DoesFollowResponse response) {
        if (exception != null) {
            observer.DoesFollowHandleException(exception);
        } else {
            observer.doesFollowTaskComplete(response);
        }
    }
}
