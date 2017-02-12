package com.stayrascal.services.v1.service;

import com.stayrascal.services.v1.domain.Customer;
import com.stayrascal.services.v1.domain.Product;
import com.stayrascal.services.v1.domain.address.Address;
import com.stayrascal.services.v1.domain.model.CustomerDto;
import com.stayrascal.services.v1.repository.AddressRepository;
import com.stayrascal.services.v1.repository.CustomerRepository;
import com.stayrascal.services.v1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

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
        return customerDto;
    }
}
