package com.stayrascal.services.domain.entity;

import com.stayrascal.services.domain.entity.audit.Auditable;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RASCAL_COMPANY", indexes = @Index(columnList = "COMPANY_ID"))
public class Company extends Auditable {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_RASCAL_COMPANY")
    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "COMPANY_NAME", nullable = false, length = 30)
    private String companyName;

    @Column(name = "COMPANY_CODE", nullable = false, length = 10)
    private String companyCode;

    @Type(type = "yes_no")
    @Column(name = "STATUS", nullable = false, length = 1)
    private boolean status;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
