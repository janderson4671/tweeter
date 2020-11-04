package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, PostStatusResponse> {

    private final PostStatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void postComplete(PostStatusResponse response);
        void handleException(Exception exception);
    }

    public PostStatusTask(PostStatusPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected PostStatusResponse doInBackground(PostStatusRequest... requests) {
        PostStatusResponse response = null;

        try {
            response = presenter.postStatus(requests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(PostStatusResponse response) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.postComplete(response);
        }
    }

}
