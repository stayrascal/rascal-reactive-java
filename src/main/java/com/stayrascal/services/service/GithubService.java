package com.stayrascal.services.service;

import com.stayrascal.services.model.GithubRepository;
import com.stayrascal.services.model.GithubUser;
import com.stayrascal.services.model.RawUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.Arrays;

@Service
public class GithubService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GithubRestClient restClient;

    public GithubUser getUser(String username) {
        Observable<RawUser> rawUserObservable = getRawUserObservable(username);
        Observable<RawUser[]> followersObservable = getFollowersObservable(username);
        Observable<GithubRepository[]> repositoryObservable = getRepositoryObservable(username);

        Observable<GithubUser> users = Observable.zip(Arrays.asList(rawUserObservable, followersObservable, repositoryObservable),
                objects -> {
                    RawUser rawUser = (RawUser) objects[0];
                    RawUser[] followers = (RawUser[]) objects[1];
                    GithubRepository[] repositories = (GithubRepository[]) objects[2];

                    return new GithubUser(rawUser, Arrays.asList(followers), Arrays.asList(repositories));
                });
        return users.toBlocking().first();
    }

    private Observable<GithubRepository[]> getRepositoryObservable(String username) {
        return Observable.create((Subscriber<? super GithubRepository[]> subscriber) -> subscriber.onNext(restClient.getRepositories(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve {} repositories", username, throwable);
                    return new GithubRepository[]{};
                })
                .subscribeOn(Schedulers.computation());
    }

    private Observable<RawUser[]> getFollowersObservable(String username) {
        return Observable.create((Subscriber<? super RawUser[]> subscriber) -> subscriber.onNext(restClient.getFollowers(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve {} followers", username, throwable);
                    return new RawUser[]{};
                })
                .subscribeOn(Schedulers.computation());
    }

    private Observable<RawUser> getRawUserObservable(String username) {
        return Observable.create((Subscriber<? super RawUser> subscriber) -> subscriber.onNext(restClient.getUser(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve user {}", username, throwable);
                    return new RawUser("???", null, null);
                })
                .subscribeOn(Schedulers.computation());
    }

}
