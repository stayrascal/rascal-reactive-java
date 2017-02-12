package com.stayrascal.services.repository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.stayrascal.services.domain.model.github.GithubUserRepository;
import com.stayrascal.services.domain.model.github.RawUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Repository
public class GithubRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.github.users}")
    private String githubUsersUrl;

    @Value("${api.github.followers}")
    private String githubFollowersUrl;

    @Value("${api.github.repos}")
    private String githubReposUrl;

    @HystrixCommand(groupKey = "GitHub API", commandKey = "Get Github user")
    public RawUser getUser(String username) {
        logger.info("Get user {}", username);
        return restTemplate.getForObject(format(githubUsersUrl, username), RawUser.class);
    }

    @HystrixCommand(groupKey = "GitHub API", commandKey = "Get Github followers")
    public RawUser[] getFollowers(String username) {
        logger.info("Get followers {}", username);
        return restTemplate.getForObject(format(githubFollowersUrl, username), RawUser[].class);
    }

    @HystrixCommand(groupKey = "GitHub API", commandKey = "Get Github repositories")
    public GithubUserRepository[] getRepositories(String username) {
        logger.info("Get repositories {}", username);
        return restTemplate.getForObject(format(githubReposUrl, username), GithubUserRepository[].class);
    }
}
