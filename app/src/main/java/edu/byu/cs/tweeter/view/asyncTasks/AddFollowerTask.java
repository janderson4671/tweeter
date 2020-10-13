package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.AddFollowerPresenter;

public class AddFollowerTask extends AsyncTask<AddFollowerRequest, Void, Response> {

    private final AddFollowerPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void addFollowerComplete(Response response);
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
    protected Response doInBackground(AddFollowerRequest... requests) {
        Response response = null;

        try {
            response = presenter.addFollower(requests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.addFollowerComplete(response);
        }
    }
}
