package com.stayrascal.services.service;

import com.stayrascal.services.model.GithubRepository;
import com.stayrascal.services.model.RawUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GithubRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubRestClient restClient;

    private String baseUrl = format(GithubRestClient.API_GITHUB_USERS, "foo");

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(restTemplate.getForObject(baseUrl, RawUser.class))
                .thenReturn(new RawUser("foo", "foo bar", "http://foo.bar"));
        when(restTemplate.getForObject(baseUrl + "/followers", RawUser[].class))
                .thenReturn(new RawUser[]{new RawUser("bar", "???", "???")});
        when(restTemplate.getForObject(baseUrl + "/repos", GithubRepository[].class))
                .thenReturn(new GithubRepository[]{new GithubRepository("foo repo", "http://foo.bar/repo")});
    }

    @Test
    public void testGetUser() throws Exception {
        RawUser user = restClient.getUser("foo");

        assertThat(user).isNotNull();

        verify(restTemplate).getForObject(baseUrl, RawUser.class);
    }

    @Test
    public void testGetFollowers() throws Exception {
        RawUser[] followers = restClient.getFollowers("foo");

        assertThat(followers).isNotEmpty();

        verify(restTemplate).getForObject(baseUrl + "/followers", RawUser[].class);
    }

    @Test
    public void testGetRepositories() throws Exception {
        GithubRepository[] repos = restClient.getRepositories("foo");

        assertThat(repos).isNotEmpty();

        verify(restTemplate).getForObject(baseUrl + "/repos", GithubRepository[].class);

    }
}