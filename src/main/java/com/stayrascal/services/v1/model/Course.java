package com.stayrascal.services.v1.model;

import javax.persistence.Entity;

@Entity
public class Course {

    private Long courseId;

    private String courseName;

    private String courseNumber;

    private String courseType;
}
