package com.example.server.service;

import com.example.server.dao.PostStatusDAO;
import com.example.shared.service.PostStatusService;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public class PostStatusServiceImpl implements PostStatusService {
    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return getPostStatusDAO().postStatus(request);
    }

    public PostStatusDAO getPostStatusDAO() {
        return new PostStatusDAO();
    }
}
