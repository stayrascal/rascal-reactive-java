package com.stayrascal.services.repository;

import com.stayrascal.services.ReactiveApplicationTest;
import com.stayrascal.services.domain.model.github.GithubUserRepository;
import com.stayrascal.services.domain.model.github.RawUser;
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

//@WebIntegrationTest("server.port:0")
public class GithubRepositoryTest extends ReactiveApplicationTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubRepository restClient;

    private String baseUrl = format("https://api.github.com/users/%s", "foo");

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(restTemplate.getForObject(baseUrl, RawUser.class))
                .thenReturn(new RawUser("foo", "foo bar", "http://foo.bar"));
        when(restTemplate.getForObject(baseUrl + "/followers", RawUser[].class))
                .thenReturn(new RawUser[]{new RawUser("bar", "???", "???")});
        when(restTemplate.getForObject(baseUrl + "/repos", GithubUserRepository[].class))
                .thenReturn(new GithubUserRepository[]{new GithubUserRepository("foo repo", "http://foo.bar/repo")});
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
        GithubUserRepository[] repos = restClient.getRepositories("foo");

        assertThat(repos).isNotEmpty();

        verify(restTemplate).getForObject(baseUrl + "/repos", GithubUserRepository[].class);

    }
}