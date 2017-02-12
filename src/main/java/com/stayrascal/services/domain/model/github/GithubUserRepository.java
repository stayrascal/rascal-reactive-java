package com.stayrascal.services.domain.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubUserRepository {

    private String name;

    @JsonProperty("html_url")
    private String url;

    public GithubUserRepository() {
    }

    public GithubUserRepository(String name, String url) {
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
