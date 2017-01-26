package com.stayrascal.services.util.rest.dto.jsonapi;

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
