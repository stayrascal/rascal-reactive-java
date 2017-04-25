package com.stayrascal.services.rest;

import com.stayrascal.services.domain.model.github.GithubUser;
import com.stayrascal.services.service.GithubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/github/users")
public class GithubResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GithubService githubService;

    @RequestMapping("/{username}")
    public GithubUser getUser(@PathVariable("username") String username) {
        logger.info("Get git.com/{} info", username);
        logger.debug("Get git.com/{} debug", username);
        return githubService.getUser(username);
    }
}
