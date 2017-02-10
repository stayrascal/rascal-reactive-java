package com.stayrascal.services.v1.model;


import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_STUDENT")
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_NAME", length = 10)
    private String customerName;

    private Gener gener;

    private Long birthDate;

    @Column(name = "PHONE_NUMBER")
    private Long phoneNumber;

    @Type(type = "yes_no")
    @Column(name = "status", nullable = false, length = 1)
    private boolean status;

}
