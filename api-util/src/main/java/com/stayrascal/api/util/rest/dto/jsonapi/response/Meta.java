package com.stayrascal.api.util.rest.dto.jsonapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Meta implements Serializable {

    @JsonProperty("total-pages")
    private long totalPages;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}