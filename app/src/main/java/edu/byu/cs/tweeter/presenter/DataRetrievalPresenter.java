package edu.byu.cs.tweeter.presenter;


import java.io.IOException;

import edu.byu.cs.tweeter.model.service.DataRetrievalService;
import edu.byu.cs.tweeter.model.service.request.DataRetrievalRequest;
import edu.byu.cs.tweeter.model.service.response.DataRetrievalResponse;

public class DataRetrievalPresenter {

    private final View view;

    public interface View {

    }

    public DataRetrievalPresenter(View view) {this.view = view;}

    public DataRetrievalResponse getData(DataRetrievalRequest request) throws IOException {
        DataRetrievalService service = new DataRetrievalService();
        return service.getData(request);
    }

}
