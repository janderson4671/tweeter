package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.GetStatusRequest;
import com.example.shared.service.response.GetStatusResponse;
import edu.byu.cs.tweeter.presenter.GetStatusPresenter;

public class StatusTask extends AsyncTask<GetStatusRequest, Void, GetStatusResponse> {

    private final GetStatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(GetStatusResponse getStatusResponse);
        void handleException(Exception exception);
    }

    public StatusTask(GetStatusPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetStatusResponse doInBackground(GetStatusRequest... getStatusRequests) {

        GetStatusResponse response = null;

        try {
            response = presenter.getStatuses(getStatusRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetStatusResponse getStatusResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(getStatusResponse);
        }
    }
}
