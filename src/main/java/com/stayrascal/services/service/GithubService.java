package com.stayrascal.services.service;

import com.stayrascal.services.domain.model.github.GithubUser;
import com.stayrascal.services.domain.model.github.GithubUserRepository;
import com.stayrascal.services.domain.model.github.RawUser;
import com.stayrascal.services.repository.GithubRepository;
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
    private GithubRepository githubRepository;

    public GithubUser getUser(String username) {
        Observable<RawUser> rawUserObservable = getRawUserObservable(username);
        Observable<RawUser[]> followersObservable = getFollowersObservable(username);
        Observable<GithubUserRepository[]> repositoryObservable = getRepositoryObservable(username);

        Observable<GithubUser> users = Observable.zip(Arrays.asList(rawUserObservable, followersObservable, repositoryObservable),
                objects -> {
                    RawUser rawUser = (RawUser) objects[0];
                    RawUser[] followers = (RawUser[]) objects[1];
                    GithubUserRepository[] repositories = (GithubUserRepository[]) objects[2];

                    return new GithubUser(rawUser, Arrays.asList(followers), Arrays.asList(repositories));
                });
        return users.toBlocking().first();
    }

    private Observable<GithubUserRepository[]> getRepositoryObservable(String username) {
        return Observable.create(
                (Subscriber<? super GithubUserRepository[]> subscriber) -> subscriber.onNext(githubRepository.getRepositories(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve {} repositories", username, throwable);
                    return new GithubUserRepository[]{};
                })
                .subscribeOn(Schedulers.computation());
    }

    private Observable<RawUser[]> getFollowersObservable(String username) {
        return Observable.create((Subscriber<? super RawUser[]> subscriber) -> subscriber.onNext(githubRepository.getFollowers(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve {} followers", username, throwable);
                    return new RawUser[]{};
                })
                .subscribeOn(Schedulers.computation());
    }

    private Observable<RawUser> getRawUserObservable(String username) {
        return Observable.create((Subscriber<? super RawUser> subscriber) -> subscriber.onNext(githubRepository.getUser(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve user {}", username, throwable);
                    return new RawUser("???", null, null);
                })
                .subscribeOn(Schedulers.computation());
    }

}
