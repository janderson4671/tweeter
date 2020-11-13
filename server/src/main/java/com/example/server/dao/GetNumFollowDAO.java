package com.example.server.dao;

import com.example.shared.domain.User;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import java.util.Arrays;
import java.util.List;

public class GetNumFollowDAO {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";


    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL, 15, 60);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL, 32, 74);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL, 84, 89);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL, 23, 74);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL, 28, 64);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL, 78, 23);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL, 2098, 2348);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL, 8732, 89);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL, 84, 23);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL, 83, 86);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL, 29, 53);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL, 95, 35);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL, 98, 21);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL, 98, 24);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL, 24, 67);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL, 83, 65);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL, 98, 23);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL, 86, 25);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL, 3, 2);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL, 98, 25);

    List<User> getDummyFollowers() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) {
        User user = findUser(request.getUser());

        if (user == null) {
            return new GetNumFollowResponse(false, "User Not Found");
        }

        return new GetNumFollowResponse(user.getNumFollowers(), user.getNumFollowing());
    }

    private User findUser(String user) {
        for (User currUser : getDummyFollowers()) {
            if (user.equals(currUser.getAlias())) {
                return currUser;
            }
        }

        return null;
    }

}
