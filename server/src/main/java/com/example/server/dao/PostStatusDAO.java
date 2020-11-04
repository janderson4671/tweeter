package com.example.server.dao;

import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public class PostStatusDAO {

    public PostStatusResponse postStatus(PostStatusRequest request) {

        //Using dummy data for now

        //TODO:Actually add stuff here for later

        return new PostStatusResponse(true, "Added Post!");
    }

}
