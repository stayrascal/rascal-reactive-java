package com.stayrascal.services.v1.domain.model;

import com.stayrascal.services.v1.domain.Customer;
import com.stayrascal.services.v1.domain.Product;
import com.stayrascal.services.v1.domain.address.Address;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomerDto {
    private Customer customer;
    private List<Product> products = new ArrayList<>();
    private List<Address> addresses = new LinkedList<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProducts(List<Product> products){
        products.addAll(products);
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addAddresses(List<Address> addresses){
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
}
