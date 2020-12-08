package com.example.server.service;

import com.example.server.dao.FeedDAO;
import com.example.server.model.DBStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FeedPosterService {

    public String postToFeeds(String statuses) {
        List<DBStatus> statusList = getList(statuses);

        FeedDAO.batchPost(statusList);

        return "Success";
    }

    private List<DBStatus> getList(String statusStr) {
        Type listType = new TypeToken<ArrayList<DBStatus>>(){}.getType();

        ArrayList<DBStatus> statuses = new Gson().fromJson(statusStr, listType);

        return statuses;
    }

}
