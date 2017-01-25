package com.stayrascal.services.model;

import java.util.List;

public class User {

    private RawUser user;

    private List<RawUser> followers;

    private List<GithubRepository> repositories;

    public User(RawUser user, List<RawUser> followers, List<GithubRepository> repositories) {
        this.user = user;
        this.followers = followers;
        this.repositories = repositories;
    }

    public RawUser getUser() {
        return user;
    }

    public List<RawUser> getFollowers() {
        return followers;
    }

    public List<GithubRepository> getRepositories() {
        return repositories;
    }
}
