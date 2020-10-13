package edu.byu.cs.tweeter.view.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.cs.tweeter.view.main.ViewData;

public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.Observer{

    private static final String LOG_TAG = "LoginActivity";

    //data members
    String mUsername;
    String mPassword;

    private LoginPresenter presenter;
    private Toast loginInToast;

    //Widgets
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button loginButton;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        presenter = new LoginPresenter(this);
        wireWidgets(v);
        setListeners();

        return v;
    }

    private void wireWidgets(View v) {
        editTextUsername = (EditText) v.findViewById(R.id.loginUsername);
        editTextPassword = (EditText) v.findViewById(R.id.loginPassword);
        loginButton = (Button) v.findViewById(R.id.loginButton);
    }

    private void setListeners() {
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing :)
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsername = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO: validate the username
                //TODO: Make sure that you do enabling of buttons
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO: validate the username
                //TODO: Make sure that you do enabling of buttons
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInToast = Toast.makeText(getActivity(), "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                LoginRequest loginRequest = new LoginRequest(mUsername, mPassword);
                LoginTask loginTask = new LoginTask(presenter, LoginFragment.this);
                loginTask.execute(loginRequest);
            }
        });
    }

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(getActivity(), MainActivity.class);

        ViewData.getData();
        ViewData.setLoggedInUser(loginResponse.getUser(), loginResponse.getAuthToken(), null);

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(getActivity(), "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getActivity(), "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
