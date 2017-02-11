package com.stayrascal.services.v1.model.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Entity
public class Country {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_COUNTRY")
    @Column(name = "COUNTRY_ID")
    private Long countryId;

    @Column(name = "COUNTRY_NAME", length = 10)
    private String countryName;

    @OneToMany
    private Set<Province> provinces;
}
