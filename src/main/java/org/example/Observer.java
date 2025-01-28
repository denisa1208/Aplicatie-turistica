package org.example;

import java.io.BufferedWriter;

public interface Observer {
    void update(String message, BufferedWriter writer);
}
