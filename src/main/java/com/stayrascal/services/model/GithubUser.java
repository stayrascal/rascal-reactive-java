package com.stayrascal.services.model;

import java.util.List;

public class GithubUser {

    private RawUser rawUser;

    private List<RawUser> followers;

    private List<GithubRepository> repositories;

    public GithubUser(RawUser rawUser, List<RawUser> followers, List<GithubRepository> repositories) {
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

    public List<GithubRepository> getRepositories() {
        return repositories;
    }
}
