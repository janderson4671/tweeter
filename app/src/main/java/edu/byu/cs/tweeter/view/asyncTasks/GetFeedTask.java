package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;
import edu.byu.cs.tweeter.presenter.GetFeedPresenter;

public class GetFeedTask extends AsyncTask<GetFeedRequest, Void, GetFeedResponse> {

    private final GetFeedPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dataRetrieved(GetFeedResponse getFeedResponse);
        void handleException(Exception exception);
    }

    public GetFeedTask(GetFeedPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetFeedResponse doInBackground(GetFeedRequest... getFeedRequests) {

        GetFeedResponse response = null;

        try {
            response = presenter.getStatuses(getFeedRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetFeedResponse getFeedResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.dataRetrieved(getFeedResponse);
        }
    }
}
