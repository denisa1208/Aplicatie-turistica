package org.example;

import java.io.BufferedWriter;
import java.io.IOException;

public class ProfessorObserver implements Observer {

    private String name;

    public ProfessorObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message, BufferedWriter writer) {
        System.out.println("Professor " + name + " received: " + message);
    }
}
