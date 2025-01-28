package org.example;


import java.io.BufferedWriter;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);

    void notifyObservers(String message, BufferedWriter writer);
}
