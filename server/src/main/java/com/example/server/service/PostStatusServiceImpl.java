package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.PostStatusDAO;
import com.example.server.dao.StoryDAO;
import com.example.shared.service.PostStatusService;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.GetFeedResponse;
import com.example.shared.service.response.PostStatusResponse;

public class PostStatusServiceImpl implements PostStatusService {
    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {

        //Authenticate the user
        if (!AuthTokenDAO.validateUser(request.getAuthToken())) {
            return new PostStatusResponse(true, "User Session Timed Out");
        }

        try {
            StoryDAO.addPost(request.getUser(), request.getStatus());
        } catch (Exception ex) {
            return new PostStatusResponse(false, ex.getMessage());
        }

        return new PostStatusResponse(true, "Added Post");
    }

    public PostStatusDAO getPostStatusDAO() {
        return new PostStatusDAO();
    }
}
