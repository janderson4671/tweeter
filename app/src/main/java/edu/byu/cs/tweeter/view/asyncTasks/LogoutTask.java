package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {

    private final LogoutPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void logoutComplete(LogoutResponse response);
        void handleException(Exception exception);
    }

    public LogoutTask(LogoutPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LogoutResponse doInBackground(LogoutRequest... requests) {
        LogoutResponse response = null;

        try {
            response = presenter.logout(requests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(LogoutResponse response) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.logoutComplete(response);
        }
    }

}
