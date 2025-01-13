package org.example;

import java.util.ArrayList;
import java.util.List;

public class Person {

    // design pattern - Factory design Pattern
    private final String surname;
    private final String name;
    private final String role;
    private int age;
    private String email;
    private String timetable;
    private String school;
    private int experience;

    public Person(String surname, String name, String role) {
        this.surname = surname;
        this.name = name;
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public int getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimetable() {
        return timetable;
    }
    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }

}

