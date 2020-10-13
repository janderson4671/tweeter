package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowerResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.AddFollowerPresenter;

public class AddFollowerTask extends AsyncTask<AddFollowerRequest, Void, AddFollowerResponse> {

    private final AddFollowerPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void addFollowerComplete(AddFollowerResponse response);
        void handleException(Exception exception);
    }

    public AddFollowerTask(AddFollowerPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected AddFollowerResponse doInBackground(AddFollowerRequest... requests) {
        AddFollowerResponse response = null;

        try {
            response = presenter.addFollower(requests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(AddFollowerResponse response) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.addFollowerComplete(response);
        }
    }
}
