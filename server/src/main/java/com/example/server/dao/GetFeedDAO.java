package com.example.server.dao;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GetFeedDAO {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    //Dummy Statuses
    private final Status stat1 = new Status(user1, "Hello! Follow this link :) https://www.google.com", "Fake Date", new ArrayList<>());
    private final Status stat2 = new Status(user2, "test", "Fake Date", new ArrayList<>());
    private final Status stat3 = new Status(user3, "test", "Fake Date", new ArrayList<>());
    private final Status stat4 = new Status(user4, "test @AmyAmes @FrankFrandson", "Fake Date", new ArrayList<>());
    private final Status stat5 = new Status(user5, "test", "Fake Date", new ArrayList<>());
    private final Status stat6 = new Status(user6, "test", "Fake Date", new ArrayList<>());
    private final Status stat7 = new Status(user7, "test", "Fake Date", new ArrayList<>());
    private final Status stat8 = new Status(user8, "test", "Fake Date", new ArrayList<>());
    private final Status stat9 = new Status(user9, "test @NathanCraddock @ElliottEnderson", "Fake Date", new ArrayList<>());
    private final Status stat10 = new Status(user10, "test @Joseph Chou", "Fake Date", new ArrayList<>());
    private final Status stat11 = new Status(user11, "test", "Fake Date", new ArrayList<>());
    private final Status stat12 = new Status(user12, "test hi", "Fake Date", new ArrayList<>());
    private final Status stat13 = new Status(user13, "test", "Fake Date", new ArrayList<>());
    private final Status stat14 = new Status(user14, "test", "Fake Date", new ArrayList<>());
    private final Status stat15 = new Status(user15, "test", "Fake Date", new ArrayList<>());
    private final Status stat16 = new Status(user16, "test", "Fake Date", new ArrayList<>());
    private final Status stat17 = new Status(user17, "test", "Fake Date", new ArrayList<>());
    private final Status stat18 = new Status(user18, "test", "Fake Date", new ArrayList<>());
    private final Status stat19 = new Status(user19, "test", "Fake Date", new ArrayList<>());
    private final Status stat20 = new Status(user20, "test", "Fake Date", new ArrayList<>());

    public GetFeedResponse getStatuses(GetFeedRequest request) {
        //For now we use dummy data

        List<Status> allStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followingIndex = getStatusesStartingIndex(request.getLastStatus(), allStatuses);

            for (int limitCounter = 0; followingIndex < allStatuses.size() && limitCounter < request.getLimit(); followingIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(followingIndex));
            }
            hasMorePages = followingIndex < allStatuses.size();
        }

        return new GetFeedResponse(responseStatuses, hasMorePages);
    }

    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    private int getStatusesStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }

        return statusIndex;
    }

    List<Status> getDummyStatuses() {
        List<Status> list = Arrays.asList(stat1, stat2, stat3, stat4, stat5, stat6, stat7, stat8, stat9,
                stat10, stat11, stat12, stat13, stat14, stat15, stat16, stat17, stat18, stat19, stat20);

        return findAssociatedUsers(list);
    }

    public List<Status> findAssociatedUsers(List<Status> statuses) {
        List<User> users = new ArrayList<>();

        for (Status currStat : statuses) {
            String message = currStat.getMessage();
            String [] array = message.split(" ");

            for (String currWord : array) {
                if (currWord.startsWith("@")) {
                    addValidUser(currWord, currStat);
                }
            }
        }

        return statuses;
    }

    private void addValidUser(String alias, Status currStat) {
        List<User> allUsers = getDummyFollowees();

        for (User currUser : allUsers) {
            if (alias.equals(currUser.getAlias())) {
                currStat.addMention(currUser);
            }
        }

    }

}