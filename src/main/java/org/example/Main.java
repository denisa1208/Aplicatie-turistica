package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2 && args.length != 4) {
            System.out.println("Usage: java Main <pathType> <file1> [<file2> <file3>]");
            return;
        }

        String pathType = args[0];


        if (args.length == 2) {
            System.out.println("Path: " + pathType + " " + args[1]);

            String file1 = args[1];
            file1 = file1 + ".in";
            System.out.println("ana");

            try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
                String line;
                Database database = Database.getInstance();
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    System.out.println(line);
                    if (parts[0].equals("ADD MUSEUM")) {

                        MuseumCommands command = new MuseumCommands();
                        try {
                            command.addMuseum(line, database);

                        } catch (Exception e) {
                            Museum museum = new Museum.MuseumBuilder(line, 0, 0, null).build();
                            database.addMuseum(museum);
                            System.out.println(e.getMessage());
                        }


                    }

                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1] + ".out"))) {

                    // Iterăm prin muzeele din baza de date și le adăugăm în fișier
                    Set<Museum> museums = database.getMuseums();
                    for (Museum museum : museums) {
                        if (museum.getCode() != 0) {
                            writer.write( museum.getCode() + ": " +  museum.getName());
                        } else {
                            writer.write("Exception: Data is broken. ## (" + museum.getName() + ")");
                        }

                        writer.newLine();  // adăugăm o linie nouă după fiecare muzeu
                    }
                    System.out.println("Muzeele au fost adăugate în fișierul path_file.out.");
                } catch (IOException e) {
                    System.out.println("A apărut o eroare la scrierea în fișier: " + e.getMessage());
                }
                database.resetDatabase();

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }



        } else if (args.length == 4) {
           //processMultipleFiles(pathType, args[1], args[2], args[3]);
            System.out.println("Path: "+ pathType + " " + args[1] +" "+ " " + args[2] + " " + args[3]);
        }
    }

}
