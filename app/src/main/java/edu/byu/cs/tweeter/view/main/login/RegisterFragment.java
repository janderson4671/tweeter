package edu.byu.cs.tweeter.view.main.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;

//TODO: Have this class implement the presenter and observer classes
public class RegisterFragment extends Fragment {

    //data members
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    //Widgets
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button registerButton;


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

        //TODO: Presenter goes here

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
            }

            @Override
            public void afterTextChanged(Editable s) {
                //verify and enable
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:: Do the async Register stuff
            }
        });
    }

}
