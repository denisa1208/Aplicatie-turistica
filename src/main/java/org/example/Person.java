package org.example;

public class Person {

    // design pattern - Factory design Pattern
    private final String surname;
    private final String name;
    private final String role;
    private int age;
    private String email;

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

    // factory class
    public static class PersonFactory {

    }

}
