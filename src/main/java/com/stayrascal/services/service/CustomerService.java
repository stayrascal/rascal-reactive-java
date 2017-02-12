package com.stayrascal.services.service;

import com.stayrascal.services.domain.entity.Customer;
import com.stayrascal.services.domain.entity.Product;
import com.stayrascal.services.domain.entity.address.Address;
import com.stayrascal.services.domain.model.CustomerDto;
import com.stayrascal.services.repository.AddressRepository;
import com.stayrascal.services.repository.CustomerRepository;
import com.stayrascal.services.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;

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
    private GithubService githubService;

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
        customerDto.setGithubUser(ofNullable(githubService.getUser(customer.getCustomerName())));
        return customerDto;
    }
}
