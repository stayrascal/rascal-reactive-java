package com.stayrascal.services.v1.model.address;

import com.stayrascal.services.v1.model.audit.Auditable;
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
@Table(name = "RASCAL_PROVINCE", indexes = @Index(columnList = "PROBINCE_ID"))
public class Province extends Auditable {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_RASCAL_PROVINCE")
    @Column(name = "PROVINCE_ID", nullable = false)
    private Long provinceId;

    @Column(name = "PROVINCE_NAME", nullable = false, length = 10)
    private String provinceName;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @Type(type = "yes_no")
    @Column(name = "STATUS", nullable = false, length = 1)
    private boolean status;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
