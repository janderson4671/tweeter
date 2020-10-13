package edu.byu.cs.tweeter.model.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";


    //Dummy Users
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
    private final Status stat1 = new Status(user1, "Hello! Follow this link :) https://www.google.com", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat2 = new Status(user2, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat3 = new Status(user3, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat4 = new Status(user4, "test @AmyAmes @FrankFrandson", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat5 = new Status(user5, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat6 = new Status(user6, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat7 = new Status(user7, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat8 = new Status(user8, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat9 = new Status(user9, "test @NathanCraddock @ElliottEnderson", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat10 = new Status(user10, "test @Joseph Chou", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat11 = new Status(user11, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat12 = new Status(user12, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat13 = new Status(user13, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat14 = new Status(user14, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat15 = new Status(user15, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat16 = new Status(user16, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat17 = new Status(user17, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat18 = new Status(user18, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat19 = new Status(user19, "test", new Date(System.currentTimeMillis()), new ArrayList<>());
    private final Status stat20 = new Status(user20, "test", new Date(System.currentTimeMillis()), new ArrayList<>());


    public LoginResponse login(LoginRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());
    }

    public RegisterResponse register(RegisterRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(),
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new RegisterResponse(user, new AuthToken());
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        User user = request.getUser();
        user.addPost(request.getStatus());

        return new PostStatusResponse(true, "Successfully added post");
    }

    public LogoutResponse logout(LogoutRequest request) {

        return new LogoutResponse(true, "Successfully logged out!");

    }


    public FollowResponse getFollowees(FollowRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }
            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowResponse(responseFollowees, hasMorePages);
    }

    public FollowResponse getFollowers(FollowRequest request) {

        //used in place of assert statements becasue Android does not support them
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUser() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowees();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowers);

            for(int limitCounter = 0; followeesIndex < allFollowers.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followeesIndex));
            }
            hasMorePages = followeesIndex < allFollowers.size();
        }

        return new FollowResponse(responseFollowers, hasMorePages);
    }

    public StatusResponse getFeed(StatusRequest request) {
        //used in place of assert statements becasue Android does not support them
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUser() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStatuses();

        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);

            for (int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }
            hasMorePages = statusIndex < allStatuses.size();
        }

        return new StatusResponse(responseStatuses, hasMorePages);
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

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {

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

    public Response addFollower(AddFollowerRequest request) {
        List<User> users = getDummyFollowees();
        User currUser = request.getCurrUser();
        User userToFollow = request.getUserToFollow();

        int userIndex = users.indexOf(userToFollow);

        if (request.isFollow()) {
            users.get(userIndex).addFollower(currUser);
            currUser.addFollower(users.get(userIndex));
            return new Response(true, "Added Follower");
        } else {
            //users.get(userIndex).removeFollower(currUser);
            currUser.removeFollower(users.get(userIndex));
            return new Response(true, "Removed Follower");
        }
    }

    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    List<Status> getDummyStatuses() {
        List<Status> list = Arrays.asList(stat1, stat2, stat3, stat4, stat5, stat6, stat7, stat8, stat9,
                stat10, stat11, stat12, stat13, stat14, stat15, stat16, stat17, stat18, stat19, stat20);

        return findAssociatedUsers(list);
    }
}
