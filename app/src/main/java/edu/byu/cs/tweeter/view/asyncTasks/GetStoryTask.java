package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import edu.byu.cs.tweeter.presenter.GetStoryPresenter;

public class GetStoryTask extends AsyncTask<GetStoryRequest, Void, GetStoryResponse> {

    private final GetStoryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(GetStoryResponse getFeedResponse);
        void handleException(Exception exception);
    }

    public GetStoryTask(GetStoryPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetStoryResponse doInBackground(GetStoryRequest... getStoryRequests) {

        GetStoryResponse response = null;

        try {
            response = presenter.getStatuses(getStoryRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetStoryResponse getStoryResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(getStoryResponse);
        }
    }
}
