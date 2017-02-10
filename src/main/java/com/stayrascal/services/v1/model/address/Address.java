package com.stayrascal.services.v1.model.address;

import javax.persistence.Entity;

@Entity
public class Address {

    private Long addressId;

    private Long countryId;

    private Long provinceId;

    private Long cityId;

    private String details;

    private Integer postcode;

    private boolean status;
}
