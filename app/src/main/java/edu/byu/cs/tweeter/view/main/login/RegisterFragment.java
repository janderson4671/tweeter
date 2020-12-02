package edu.byu.cs.tweeter.view.main.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.mainFragments.MainActivity;
import edu.byu.cs.tweeter.view.main.viewData.ViewData;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment implements RegisterPresenter.View, RegisterTask.Observer {

    //Final vars
    final int REQUEST_IMAGE_CAPTURE = 1;

    //data members
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Bitmap profile;
    private byte[] profileBytes;

    //Widgets
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button registerButton;
    private Button pictureButton;

    //other
    private RegisterPresenter presenter;
    private Toast imageToast;
    private Toast registerToast;


    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        presenter = new RegisterPresenter(this);

        wireWidgets(v);
        setListenters();

        return v;
    }

    private void wireWidgets(View v) {
        editTextFirstName = (EditText) v.findViewById(R.id.registerFirstName);
        editTextLastName = (EditText) v.findViewById(R.id.registerLastName);
        editTextUsername = (EditText) v.findViewById(R.id.registerUsername);
        editTextPassword = (EditText) v.findViewById(R.id.registerPassword);
        registerButton = (Button) v.findViewById(R.id.registerButton);
        pictureButton = (Button) v.findViewById(R.id.pictureButton);

        registerButton.setEnabled(false);
    }

    private void setListenters() {
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = s.toString();
                verifyFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //verify and enable
            }
        });
        editTextLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName = s.toString();
                verifyFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //verify and enable
            }
        });
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = s.toString();
                verifyFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //verify and enable
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
                verifyFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //verify and enable
            }
        });
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Start a camera activity
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    imageToast = Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
                    imageToast.show();
                }

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerToast = Toast.makeText(getActivity(), "Logging In", Toast.LENGTH_LONG);
                registerToast.show();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                profile.compress(Bitmap.CompressFormat.PNG, 100, stream);
                profileBytes = stream.toByteArray();

                RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, username, password, profileBytes);
                RegisterTask registerTask = new RegisterTask(presenter, RegisterFragment.this);
                registerTask.execute(registerRequest);
            }
        });
    }

    public void verifyFields() {
        if (firstName != null && lastName != null && username != null && password != null && profile != null) {
            registerButton.setEnabled(true);
        } else {
            registerButton.setEnabled(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            profile = (Bitmap) extras.get("data");
        }
        verifyFields();
    }

    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {

        Intent intent = new Intent(getActivity(), MainActivity.class);

        ViewData.getData();
        ViewData.setLoggedInUser(registerResponse.getUser(), registerResponse.getAuthToken());

        registerToast.cancel();
        startActivity(intent);
    }

    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        registerToast = Toast.makeText(getActivity(), "Register Unsuccessful", Toast.LENGTH_LONG);
        registerToast.show();
    }

    @Override
    public void handleException(Exception ex) {
        registerToast = Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG);
        registerToast.show();
    }
}
