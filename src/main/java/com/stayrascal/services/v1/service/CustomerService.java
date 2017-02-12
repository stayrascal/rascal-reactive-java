package com.stayrascal.services.v1.service;

import com.stayrascal.services.v1.model.Customer;
import com.stayrascal.services.v1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Observable<Customer> retrieveCustomers(String customerName){
        return Observable.from(customerRepository.findByCustomerName(customerName))
                .filter( customer -> customer.isStatus());
    }
}
