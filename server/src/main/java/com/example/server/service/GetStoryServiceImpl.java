package com.example.server.service;

import com.example.server.dao.StoryDAO;
import com.example.server.dao.UserDAO;
import com.example.server.model.DBStatus;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.GetStoryService;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetStoryServiceImpl implements GetStoryService {

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

    @Override
    public GetStoryResponse getStatuses(GetStoryRequest request) {

        User currUser = getUserDAO().getUser(request.getUser());

        //Check if there is a status to start at
        String lastStatusStamp;

        if (request.getLastStatus() != null) {
            lastStatusStamp = request.getLastStatus().getTimeStamp();
        } else {
            lastStatusStamp = "";
        }

        //Grab DBStatuses from table
        List<DBStatus> statuses = getStoryDAO().getStory(request.getUser(), lastStatusStamp, request.getLimit());

        //Need to construct the actual statuses
        List<Status> returnStatuses = new ArrayList<>();

        for (DBStatus currStat : statuses) {

            //Parse the message for the mentions
            List<String> mentions = parseMessage(currStat.getMessage());

            //Find valid mentioned users in the system
            List<User> mentionedUsers = getMentionedUsers(mentions);

            Status status = new Status(currUser, currStat.getMessage(), currStat.getTimeStamp(), mentionedUsers);

            returnStatuses.add(status);

        }

        return new GetStoryResponse(returnStatuses, true);  //TODO: GET HAS MORE PAGES
    }

    private List<User> getMentionedUsers(List<String> mentions) {

        List<User> users = new ArrayList<>();

        for (String currMention : mentions) {

            User currUser = getUserDAO().getUser(currMention);

            if (currUser != null) {
                users.add(currUser);
            }
        }

        return users;

    }

    private List<String> parseMessage(String message) {
        Pattern pattern = Pattern.compile("@\\w*");
        Matcher matcher = pattern.matcher(message);

        List<String> mentions = new ArrayList<>();

        while (matcher.find()) {

            int start = matcher.start();
            int end = matcher.end();

            mentions.add(message.substring(start, end));

        }

        return mentions;
    }

}
