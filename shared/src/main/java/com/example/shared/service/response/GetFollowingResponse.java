package com.example.shared.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowingRequest;

/**
 * A paged response for a {@link GetFollowingRequest}.
 */
public class GetFollowingResponse extends PagedResponse {

    private List<User> users;

    public GetFollowingResponse(String message) {
        super(false, message, false);
    }

    public GetFollowingResponse(List<User> users, boolean hasMorePages) {
        super(true, hasMorePages);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        GetFollowingResponse that = (GetFollowingResponse) param;

        return (Objects.equals(users, that.users) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }
}
