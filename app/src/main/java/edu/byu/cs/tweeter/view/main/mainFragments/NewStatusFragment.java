package edu.byu.cs.tweeter.view.main.mainFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Date;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.view.main.ViewData;

public class NewStatusFragment extends DialogFragment implements PostStatusTask.Observer, PostStatusPresenter.View {

    //widgets
    Button cancelButton;
    Button postButton;

    EditText statusMessageText;

    String message = null;

    ViewData data;
    PostStatusPresenter presenter;

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
        data = ViewData.getData();
        presenter = new PostStatusPresenter(this);
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
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStatus();
            }
        });

        statusMessageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
    }

    private void postStatus() {
        PostStatusTask task = new PostStatusTask(presenter, this);
        PostStatusRequest request = new PostStatusRequest(new Status(data.getLoggedInUser(), message, new Date(System.currentTimeMillis()), new ArrayList<>()),
                data.getLoggedInUser());
        task.execute(request);
    }

    private void wireWidgets(View v) {
        cancelButton = v.findViewById(R.id.cancelButton);
        postButton = v.findViewById(R.id.postButton);

        statusMessageText = v.findViewById(R.id.statusMessageTextBox);
    }

    @Override
    public void postComplete(PostStatusResponse response) {
        dismiss();
    }

    @Override
    public void handleException(Exception exception) {
        //TODO:: handle exception;
    }
}
