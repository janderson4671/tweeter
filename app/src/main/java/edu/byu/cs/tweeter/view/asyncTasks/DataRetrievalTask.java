package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.DataRetrievalRequest;
import edu.byu.cs.tweeter.model.service.response.DataRetrievalResponse;
import edu.byu.cs.tweeter.presenter.DataRetrievalPresenter;

public class DataRetrievalTask extends AsyncTask<DataRetrievalRequest, Void, DataRetrievalResponse> {
    private final DataRetrievalPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(DataRetrievalResponse response);
        void handleException(Exception exception);
    }

    public DataRetrievalTask(DataRetrievalPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected DataRetrievalResponse doInBackground(DataRetrievalRequest... requests) {
        DataRetrievalResponse response = null;

        try {
            response = presenter.getData(requests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(DataRetrievalResponse response) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(response);
        }
    }

}
