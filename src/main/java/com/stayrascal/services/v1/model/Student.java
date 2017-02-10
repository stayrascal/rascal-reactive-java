package com.stayrascal.services.v1.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Student {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "SEQ_STUDENT")
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "NAME", length = 10)
    private String name;

    private Gener gener;

    private Long birthDate;

    @Type(type = "yes_no")
    @Column(name = "status", nullable = false, length = 1)
    private boolean status;
}
