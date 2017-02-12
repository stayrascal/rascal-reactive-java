package com.stayrascal.services.repository;

import com.stayrascal.services.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByCustomerName(@Param("customerName") String customerName);

    Customer findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
