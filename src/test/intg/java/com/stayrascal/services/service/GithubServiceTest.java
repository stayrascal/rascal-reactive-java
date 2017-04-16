package com.stayrascal.services.service;

public class GithubServiceTest {
/*
    @Mock
    private GithubRepository restClient;

    @InjectMocks
    private GithubService githubService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(restClient.getUser(anyString())).thenAnswer(m -> getUserWithSleepTime());
        when(restClient.getFollowers(anyString())).thenAnswer(m -> getFollowersWithSleepTime());
        when(restClient.getRepositories(anyString())).then(m -> getReposWithSleepTime());
    }

    private RawUser getUserWithSleepTime() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        return new RawUser("foo", "foo bar", "???");
    }

    @Test
    public void testGetUser() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        GithubUser user = githubService.getUser("foo");
        stopWatch.stop();

        long duration = stopWatch.getTotalTimeMillis();

        assertThat(user).isNotNull();

        assertThat(user.getRawUser()).isNotNull();
        assertThat(user.getRawUser().getLogin()).isEqualTo("foo");
        assertThat(user.getRawUser().getName()).isEqualTo("foo bar");

        assertThat(user.getFollowers())
                .hasSize(3)
                .extracting("name")
                .contains("bar", "qix", "baz");

        assertThat(user.getRepositories())
                .hasSize(2)
                .extracting("name")
                .contains("foo", "bar");

        assertThat(duration).isGreaterThan(300);
        assertThat(duration).isLessThan(400);


    }

    public RawUser[] getFollowersWithSleepTime() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }

        return new RawUser[]{new RawUser("foo", "bar", null),
                new RawUser("foo", "qix", null),
                new RawUser("foo", "baz", null)};
    }

    public GithubUserRepository[] getReposWithSleepTime() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }

        return new GithubUserRepository[]{
                new GithubUserRepository("foo", "???"),
                new GithubUserRepository("bar", "???")};
    }*/
}