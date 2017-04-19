package com.stayrascal.services.rest;

import com.stayrascal.services.Stubby4JIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GithubResourceStubby4JIntegrationTest extends Stubby4JIntegrationTest {
    @Autowired
    private GithubResource resource;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    public void shouldReturnGithubUserWhenGivenUsername() throws Exception {
        mockMvc.perform(get(format("/github/users/%s", "stayrascal")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rawUser.login").value("stayrascal"));
    }
}