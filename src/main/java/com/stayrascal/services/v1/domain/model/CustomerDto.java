package com.stayrascal.services.v1.domain.model;

import com.google.common.collect.Lists;
import com.stayrascal.services.model.GithubRepository;
import com.stayrascal.services.model.GithubUser;
import com.stayrascal.services.model.RawUser;
import com.stayrascal.services.v1.domain.Customer;
import com.stayrascal.services.v1.domain.Product;
import com.stayrascal.services.v1.domain.address.Address;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CustomerDto {
    private Customer customer;
    private List<Product> products = new ArrayList<>();
    private List<Address> addresses = new LinkedList<>();
    private Optional<GithubUser> githubUser;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProducts(List<Product> products) {
        products.addAll(products);
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addAddresses(List<Address> addresses) {
        addresses.addAll(addresses);
    }

    public void addMainAddress(Address address) {
        addresses.add(0, address);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Optional<GithubUser> getGithubUser() {
        return githubUser;
    }

    public void setGithubUser(@NotNull Optional<GithubUser> githubUser) {
        this.githubUser = githubUser;
    }

    public List<RawUser> getGithubFollowers() {
        return githubUser
                .map(user -> user.getFollowers())
                .orElse(Lists.newArrayList());
    }

    public List<GithubRepository> getGithRepository() {
        return githubUser
                .map(user -> user.getRepositories())
                .orElse(Lists.newArrayList());
    }
}
