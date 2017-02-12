package com.stayrascal.services.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RASCAL_NAME", indexes = @Index(columnList = "PRODUCT_ID"))
public class Product {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_PRODUCT")
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 20)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @ManyToMany
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Type(type = "yes_no")
    @Column(name = "STATUS", nullable = false, length = 1)
    private boolean status;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
