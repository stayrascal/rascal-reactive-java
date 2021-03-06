package com.stayrascal.services.repository;

import com.stayrascal.services.domain.entity.address.Address;
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

    List<Address> findByCompanyCompanyId(@Param("companyId") Long companyId);
}
