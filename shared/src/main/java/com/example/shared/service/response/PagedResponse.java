package com.example.shared.service.response;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse extends Response {

    private boolean hasMorePages;

    //Constructors
    PagedResponse(boolean success, boolean hasMorePages) {
        super(success);
        this.hasMorePages = hasMorePages;
    }
    PagedResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message);
        this.hasMorePages = hasMorePages;
    }

    //Getter
    public boolean getHasMorePages() {
        return hasMorePages;
    }

    //Setter
    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
}
