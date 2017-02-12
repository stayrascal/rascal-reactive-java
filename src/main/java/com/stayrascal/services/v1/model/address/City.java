package com.stayrascal.services.v1.model.address;

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
@Table(name = "RASCAL_CITY", indexes = @Index(columnList = "CITY_ID"))
public class City {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_RASCAL_CITY", allocationSize = 1)
    @Column(name = "CITY_ID")
    private Long cityId;

    @Column(name = "CITY_NAME", nullable = false, length = 10)
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_ID", nullable = false)
    private Province province;


    @Type(type = "yes_no")
    @Column(name = "STATUS", nullable = false, length = 1)
    private boolean status;
}
