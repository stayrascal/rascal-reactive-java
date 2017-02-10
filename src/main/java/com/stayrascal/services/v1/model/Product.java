package com.stayrascal.services.v1.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@javax.persistence.Entity
public class Product {

    @Id
    @GeneratedValue(generator = "generator",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_PRODUCT")
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", length = 5)
    private String productName;

    @ManyToOne
    @Column(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "company")
    private String company;

    @Type(type = "yes_no")
    @Column(name = "status", nullable = false, length = 1)
    private boolean status;


}
