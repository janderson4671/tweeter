package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;

public class StatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {

    private final StatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(StatusResponse statusResponse);
        void handleException(Exception exception);
    }

    public StatusTask(StatusPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StatusResponse doInBackground(StatusRequest... statusRequests) {

        StatusResponse response = null;

        try {
            response = presenter.getStatuses(statusRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(StatusResponse statusResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(statusResponse);
        }
    }
}
