package com.example.server.service;

import com.example.server.dao.GetStoryDAO;
import com.example.shared.service.GetStoryService;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

public class GetStoryServiceImpl implements GetStoryService {
    @Override
    public GetStoryResponse getStatuses(GetStoryRequest request) {
        return getStoryDAO().getStatuses(request);
    }

    public GetStoryDAO getStoryDAO() {
        return new GetStoryDAO();
    }
}
