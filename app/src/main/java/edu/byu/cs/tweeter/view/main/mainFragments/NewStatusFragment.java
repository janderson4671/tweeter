package edu.byu.cs.tweeter.view.main.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.byu.cs.tweeter.R;

public class NewStatusFragment extends DialogFragment {

    //widgets
    Button cancelButton;
    Button postButton;

    EditText statusMessageText;

    //Empty constructor for DialogFragment
    public NewStatusFragment() {}

    public static NewStatusFragment newInstance() {

        Bundle args = new Bundle();

        NewStatusFragment fragment = new NewStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_status_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wireWidgets(view);
        setListeners();

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void setListeners() {
        //TODO:: Set Listeners here!
    }

    private void wireWidgets(View v) {
        cancelButton = v.findViewById(R.id.cancelButton);
        postButton = v.findViewById(R.id.postButton);

        statusMessageText = v.findViewById(R.id.statusMessageTextBox);
    }

}
