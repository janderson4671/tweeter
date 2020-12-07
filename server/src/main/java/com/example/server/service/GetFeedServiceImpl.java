package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FeedDAO;
import com.example.server.dao.GetFeedDAO;
import com.example.server.dao.StoryDAO;
import com.example.server.dao.UserDAO;
import com.example.server.model.DBStatus;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFeedService;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;
import com.example.shared.service.response.GetStoryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetFeedServiceImpl implements GetFeedService {
    @Override
    public GetFeedResponse getStatuses(GetFeedRequest request) {

        //Authenticate user
        if (!AuthTokenDAO.validateUser(request.getAuthToken())) {
            return new GetFeedResponse("User Session Timed Out");
        }

        //Check if there is a status to start at
        String lastStatusStamp;

        if (request.getLastStatus() != null) {
            lastStatusStamp = request.getLastStatus().getTimeStamp();
        } else {
            lastStatusStamp = "";
        }

        //Grab DBStatuses from table
        List<DBStatus> statuses = FeedDAO.getFeed(request.getLoggedInUser(), lastStatusStamp, request.getLimit());

        //Need to construct the actual statuses
        List<Status> returnStatuses = new ArrayList<>();

        for (DBStatus currStat : statuses) {

            //Get the corresponding user
            User currUser = UserDAO.getUser(currStat.getAuthor());

            //Parse the message for the mentions
            List<String> mentions = parseMessage(currStat.getMessage());

            //Find valid mentioned users in the system
            List<User> mentionedUsers = getMentionedUsers(mentions);

            Status status = new Status(currUser, currStat.getMessage(), currStat.getTimeStamp(), mentionedUsers);

            returnStatuses.add(status);

        }


        return new GetFeedResponse(returnStatuses, true);
    }

    private List<User> getMentionedUsers(List<String> mentions) {

        List<User> users = new ArrayList<>();

        for (String currMention : mentions) {

            User currUser = UserDAO.getUser(currMention);

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

    public GetFeedDAO getFeedDAO() {
        return new GetFeedDAO();
    }
}
