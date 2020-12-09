package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import com.example.shared.domain.User;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import com.example.shared.util.ByteArrayUtils;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {

    private final RegisterPresenter presenter;
    private final RegisterTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void registerSuccessful(RegisterResponse registerResponse);
        void registerUnsuccessful(RegisterResponse registerResponse);
        void handleException(Exception ex);
    }

    public RegisterTask(RegisterPresenter presenter, RegisterTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected RegisterResponse doInBackground(RegisterRequest... registerRequests) {
        RegisterResponse registerResponse = null;

        try {
            registerResponse = presenter.register(registerRequests[0]);

            if (registerResponse.isSuccess()) {
                loadImage(registerResponse.getUser());
            }
        } catch (Exception ex) {
            exception = ex;
        }

        return registerResponse;
    }

    /**
     * Loads the profile image for the user
     *
     * @param user the user whose profile image is to be loaded
     */
    private void loadImage(User user) {
        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            //user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(RegisterRequest...)} method) when the task completes.
     *
     * @param registerResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(RegisterResponse registerResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(registerResponse.isSuccess()) {
            observer.registerSuccessful(registerResponse);
        } else {
            observer.registerSuccessful(registerResponse);
        }
    }
}
