package com.galaxydata.starscan.config;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class PaginationRequest {

    @Min(value = 1, message = "Page number must be at least 1")
    private int page = 1;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 100, message = "Limit must not exceed 100")
    private int limit = 10;

    public PaginationRequest(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}