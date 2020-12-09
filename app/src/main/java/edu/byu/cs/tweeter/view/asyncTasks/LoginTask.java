package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import com.example.shared.domain.User;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import com.example.shared.util.ByteArrayUtils;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private final LoginPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void loginSuccessful(LoginResponse loginResponse);
        void loginUnsuccessful(LoginResponse loginResponse);
        void handleException(Exception ex);
    }

    public LoginTask(LoginPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        LoginResponse loginResponse = null;

        try {
            loginResponse = presenter.login(loginRequests[0]);

            if(loginResponse.isSuccess()) {
                loadImage(loginResponse.getUser());
            }
        } catch (Exception ex) {
            exception = ex;
        }

        return loginResponse;
    }

    private void loadImage(User user) {
        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            //user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(loginResponse.isSuccess()) {
            observer.loginSuccessful(loginResponse);
        } else {
            observer.loginUnsuccessful(loginResponse);
        }
    }
}
