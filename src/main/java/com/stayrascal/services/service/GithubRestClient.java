package com.stayrascal.services.service;

import com.stayrascal.services.model.GithubRepository;
import com.stayrascal.services.model.RawUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

public class GithubRestClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String API_GITHUB_USERS = "https://api.github/users/%s";
    public static final String API_GITHUB_FOLLOWERS = API_GITHUB_USERS + "/followers";
    public static final String API_GITHUB_REPOS = API_GITHUB_USERS + "/repos";

    private final RestTemplate restTemplate;

    public GithubRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GithubRestClient() {
        this(new RestTemplate());
    }

    public RawUser getUser(String username) {
        logger.info("Get user {}", username);
        return restTemplate.getForObject(format(API_GITHUB_USERS, username), RawUser.class);
    }

    public RawUser[] getFollowers(String username) {
        logger.info("Get followers {}", username);
        return restTemplate.getForObject(format(API_GITHUB_FOLLOWERS, username), RawUser[].class);
    }

    public GithubRepository[] getRepositories(String username) {
        logger.info("Get repositories {}", username);
        return restTemplate.getForObject(format(API_GITHUB_REPOS, username), GithubRepository[].class);
    }
}
