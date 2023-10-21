package com.test.devsu.srvclientmanager.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
@Table(name = "person")
public abstract class Person {

    private String name;

    private Character gender;

    private Integer age;

    @Column(unique = true, nullable = false)
    private String dni;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;


}
