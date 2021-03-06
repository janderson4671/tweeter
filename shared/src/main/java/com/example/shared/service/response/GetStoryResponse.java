package com.example.shared.service.response;

import com.example.shared.domain.Status;
import com.example.shared.domain.User;

import java.util.List;
import java.util.Objects;

public class GetStoryResponse extends PagedResponse {

    private List<Status> statuses;

    //Constructors
    public GetStoryResponse(String message) {
        super(false, message, false);
    }

    public GetStoryResponse(List<Status> statuses, boolean hasMorePages) {
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

        GetStoryResponse that = (GetStoryResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }

}
