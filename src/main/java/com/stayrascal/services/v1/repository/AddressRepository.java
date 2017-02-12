package com.stayrascal.services.v1.repository;

import com.stayrascal.services.v1.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCountryCountryId(@Param("countryId") Long countryId);

    List<Address> findByProvinceProvinceId(@Param("provinceId") Long provinceId);

    List<Address> findByCityCityId(@Param("cityId") Long cityId);

    List<Address> findByCustomerCustomerId(@Param("customerId") Long customerId);

    List<Address> findByProductProductId(@Param("productId") Long productId);
}
