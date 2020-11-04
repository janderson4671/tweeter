package edu.byu.cs.tweeter.presenter;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.GetFollowersServiceProxy;

public class GetFollowersPresenter {

    private final GetFollowersPresenter.View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public GetFollowersPresenter(GetFollowersPresenter.View view) {
        this.view = view;
    }

    public GetFollowersResponse GetFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException {
        GetFollowersService GetFollowersService = GetFollowersService();
        return GetFollowersService.getFollowers(request);
    }

    GetFollowersService GetFollowersService() {
        return new GetFollowersServiceProxy();
    }
}
