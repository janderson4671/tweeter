package com.example.shared.service.response;

import com.example.shared.domain.User;

import java.util.List;
import java.util.Objects;

public class GetFollowersResponse extends PagedResponse {

    public List<User> users;

    public GetFollowersResponse(String message) {
        super(false, message, false);
    }

    public GetFollowersResponse(List<User> users, boolean hasMorePages) {
        super(true, hasMorePages);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        GetFollowersResponse that = (GetFollowersResponse) param;

        return (Objects.equals(users, that.users) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }

}
