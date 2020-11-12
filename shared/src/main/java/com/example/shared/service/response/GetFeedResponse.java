package com.example.shared.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.domain.Status;
import com.example.shared.domain.User;

public class GetFeedResponse extends PagedResponse {

    private List<Status> statuses;

    //Constructors
    public GetFeedResponse(String message) {
        super(false, message, false);
    }

    public GetFeedResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    //Getter
    public List<Status> getStatuses() {
        return statuses;
    }

    //Setter
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    //Overridden Functions
    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        GetFeedResponse that = (GetFeedResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }

}
