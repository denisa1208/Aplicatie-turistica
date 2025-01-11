package org.example;

public class ProfessorObserver implements Observer {

    private String name;

    public ProfessorObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Professor " + name + " received: " + message);
    }
}
