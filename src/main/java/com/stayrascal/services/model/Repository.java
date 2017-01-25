package com.stayrascal.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {

    private String name;

    @JsonProperty("html_url")
    private String url;

    public Repository() {
    }

    public Repository(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
