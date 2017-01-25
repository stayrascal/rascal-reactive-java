package com.stayrascal.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubRepository {

    private String name;

    @JsonProperty("html_url")
    private String url;

    public GithubRepository() {
    }

    public GithubRepository(String name, String url) {
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
