package com.stayrascal.services.v1.repository;

import com.stayrascal.services.v1.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCountryCountryId(@Param("countryId") String countryId);

    List<Address> findByProvinceProvinceId(@Param("provinceId") String provinceId);

    List<Address> findByCityCityId(@Param("cityId") String cityId);

    List<Address> findByCustomerCustomerId(@Param("customerId") String customerId);

    List<Address> findByProductProductId(@Param("productId") String productId);
}
