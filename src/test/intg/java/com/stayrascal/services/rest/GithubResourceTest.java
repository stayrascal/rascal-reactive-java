package com.stayrascal.services.rest;

import com.stayrascal.services.ReactiveApplicationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GithubResourceTest extends ReactiveApplicationTest {
    private final static String contextPath = "localhost:8081";

    @Autowired
    private GithubResource resource;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    public void shouldReturnGithubUserWhenGivenUsername() throws Exception {
        mockMvc.perform(get(format(contextPath + "/github/users", "stayrascal")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].rawUser.name").value("stayrascal"))
                .andExpect(jsonPath("$[0].rawUser.login").value("stayrascal"));
    }
}