package com.stayrascal.services.v1.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.stayrascal.services.model.GithubRepository;
import com.stayrascal.services.model.GithubUser;
import com.stayrascal.services.model.RawUser;
import com.stayrascal.services.v1.domain.Customer;
import com.stayrascal.services.v1.domain.Product;
import com.stayrascal.services.v1.domain.address.Address;
import com.stayrascal.services.v1.domain.model.CustomerDto;
import com.stayrascal.services.v1.repository.AddressRepository;
import com.stayrascal.services.v1.repository.CustomerRepository;
import com.stayrascal.services.v1.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

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
    public GithubRepository[] getRepositories(String username) {
        logger.info("Get repositories {}", username);
        return restTemplate.getForObject(format(githubReposUrl, username), GithubRepository[].class);
    }

    public Observable<Customer> retrieveCustomers(String customerName) {
        return Observable.from(customerRepository.findByCustomerName(customerName))
                .filter(customer -> customer.isStatus());
    }

    public Observable<CustomerDto> retrieveCustomerDto(Long customerId) {
        return Observable.create(subscriber -> {
            Customer customer = customerRepository.findOne(customerId);
            List<Address> addresses = addressRepository.findByCustomerCustomerId(customerId);
            List<Product> products = productRepository.findByCustomerCustomerId(customerId);
            subscriber.onNext(buildCustomerDto(customer, addresses, products));
            subscriber.onCompleted();
        });
    }

    private CustomerDto buildCustomerDto(Customer customer, List<Address> addresses, List<Product> products) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer(customer);
        customerDto.addAddresses(addresses);
        customerDto.addProducts(products);
        customerDto.setGithubUser(ofNullable(getGitHubUser(customer.getCustomerName())));
        return customerDto;
    }

    public GithubUser getGitHubUser(String username) {
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
        return Observable.create(
                (Subscriber<? super GithubRepository[]> subscriber) -> subscriber.onNext(getRepositories(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve {} repositories", username, throwable);
                    return new GithubRepository[]{};
                })
                .subscribeOn(Schedulers.computation());
    }

    private Observable<RawUser[]> getFollowersObservable(String username) {
        return Observable.create(
                (Subscriber<? super RawUser[]> subscriber) -> subscriber.onNext(getFollowers(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve {} followers", username, throwable);
                    return new RawUser[]{};
                })
                .subscribeOn(Schedulers.computation());
    }

    private Observable<RawUser> getRawUserObservable(String username) {
        return Observable.create(
                (Subscriber<? super RawUser> subscriber) -> subscriber.onNext(getUser(username)))
                .onErrorReturn(throwable -> {
                    logger.error("Failed to retrieve user {}", username, throwable);
                    return new RawUser("???", null, null);
                })
                .subscribeOn(Schedulers.computation());
    }
}
