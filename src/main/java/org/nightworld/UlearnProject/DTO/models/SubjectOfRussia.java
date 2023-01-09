package org.nightworld.UlearnProject.DTO.models;


import java.util.HashMap;
import java.util.Map;

public class SubjectOfRussia {

    private static int count;
    private static Map<String, SubjectOfRussia> subjectOfRussia = new HashMap<>();

    private final int id;
    private final String name;

    public SubjectOfRussia(String name) {
        this.id = count;
        this.name = name;
        subjectOfRussia.put(name, this);
        count++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static int getCount() {
        return count;
    }

    public static SubjectOfRussia getByNameOrCreate(String name) {
        name = joinMoscow(name);
        if (subjectOfRussia.containsKey(name)) {
            return subjectOfRussia.get(name);
        }
        return new SubjectOfRussia(name);
    }

    private static String joinMoscow(String name) {
        if (name.contains("Моск")) {
            return "Московская область";
        }
        return name;
    }
}
