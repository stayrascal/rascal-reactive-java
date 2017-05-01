package com.stayrascal.services.domain.entity.address;

import com.stayrascal.services.domain.entity.audit.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RASCAL_COUNTRY", indexes = @Index(columnList = "COUNTRY_ID"))
public class Country extends Auditable {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_RASCAL_COUNTRY")
    @Column(name = "COUNTRY_ID", nullable = false)
    private Long countryId;

    @Column(name = "COUNTRY_NAME", nullable = false, length = 10)
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getCountryId() {
        return countryId;
    }
}
