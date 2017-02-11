package com.stayrascal.services.v1.model.address;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Province {

    private Long provinceId;

    private String provinceName;

    private Country country;

    private List<Province> oldNames = new ArrayList<>();

    private boolean status;
}
