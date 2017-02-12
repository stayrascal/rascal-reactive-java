package com.stayrascal.services.domain.model.github;

import java.util.List;

public class GithubUser {

    private RawUser rawUser;

    private List<RawUser> followers;

    private List<GithubUserRepository> repositories;

    public GithubUser(RawUser rawUser, List<RawUser> followers, List<GithubUserRepository> repositories) {
        this.rawUser = rawUser;
        this.followers = followers;
        this.repositories = repositories;
    }

    public RawUser getRawUser() {
        return rawUser;
    }

    public List<RawUser> getFollowers() {
        return followers;
    }

    public List<GithubUserRepository> getRepositories() {
        return repositories;
    }
}
