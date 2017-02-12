package com.stayrascal.services.repository;

import com.stayrascal.services.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCustomerCustomerId(@Param("customerId") Long customerId);
}
