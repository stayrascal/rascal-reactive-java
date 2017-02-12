package com.stayrascal.services.v1.domain.address;

import com.stayrascal.services.v1.domain.Company;
import com.stayrascal.services.v1.domain.Customer;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RASCAL_ADDRESS", indexes = @Index(columnList = "ADDRESS_ID"))
public class Address {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_RASCAL_ADDRESS", allocationSize = 1)
    @Column(name = "ADDRESS_ID", nullable = false)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_IS", nullable = false)
    private Province province;

    @ManyToOne
    @Column(name = "CITY_ID", nullable = false)
    private City city;

    @ManyToOne
    @Column(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @Column(name = "COMPANY_ID")
    private Company company;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "POSTCODE", length = 10)
    private String postcode;

    @Type(type = "yes_no")
    @Column(name = "STATUS", nullable = false, length = 1)
    private boolean status;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
