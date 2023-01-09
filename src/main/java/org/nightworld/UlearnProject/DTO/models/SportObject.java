package org.nightworld.UlearnProject.DTO.models;

import java.sql.Date;

public class SportObject {

    private final int id;
    private final String name;
    private final SubjectOfRussia subjectOfRussia;
    private final String fullAddress;
    private final Date registrationDate;

    public SportObject(int id, String name,
        String subjectOfRussia, String fullAddress, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.subjectOfRussia = SubjectOfRussia.getByNameOrCreate(subjectOfRussia);
        this.fullAddress = fullAddress;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SubjectOfRussia getSubjectOfRussia() {
        return subjectOfRussia;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + subjectOfRussia + " " + fullAddress + " " + registrationDate;
    }
}
