package com.stayrascal.services.v1.model.address;

import java.util.ArrayList;
import java.util.List;

public class City {

    private Long cityId;

    private String cityName;

    private Province province;

    private List<City> oldNames = new ArrayList<>();

    private boolean status;
}
