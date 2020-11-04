package com.example.shared.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.domain.Status;
import com.example.shared.domain.User;

public class GetStatusResponse extends PagedResponse {

    private List<Status> statuses;

    List<User> mentionedUsers;

    public GetStatusResponse(String message) {
        super(false, message, false);
    }

    public GetStatusResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public List<User> getMentionedUsers() { return mentionedUsers; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        GetStatusResponse that = (GetStatusResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }

}
