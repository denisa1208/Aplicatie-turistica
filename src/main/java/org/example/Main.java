package org.example;

import java.io.*;
import java.net.http.WebSocket;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        PathTypes pathType;
        try {
            pathType = PathTypes.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid pathType. Allowed values: GROUPS, LISTENER, MUSEUMS");
            return;
        }

        if (args.length == 2) {
            processFile(pathType, args[1]);
        } else if (args.length == 4) {
            System.out.println(args[1] + " + add event listener");
            processAllFiles(args[1], args[2], args[3]);
        }
    }

    private static void processFile(PathTypes pathType, String filePath) throws IOException {
        String inputFile = filePath + ".in";
        String outputFile = filePath + ".out";

        Database database = Database.getInstance();
        Set<Museum> museumsToAdd = new LinkedHashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                switch (pathType) {
                    case MUSEUMS -> MuseumCmd(line, parts, database, museumsToAdd);
                    case GROUPS -> GroupCmd(line, parts, database, writer, outputFile);
                    case LISTENER -> ListenerCmd(line, parts, database, writer);
                }
            }

            // Write all museums to the output file
            for (Museum museum : museumsToAdd) {
                if (museum.getCode() != 0) {
                    writer.write(museum.getCode() + ": " + museum.getName());
                } else {
                    writer.write("Exception: Data is broken. ## (" + museum.getEmail() + ")");
                }
                writer.newLine();
            }
        }
        database.resetDatabase();
    }

    private static void processAllFiles(String museumsPath, String groupsPath, String eventsPath) throws IOException {
        processFile(PathTypes.MUSEUMS, museumsPath);
        processFile(PathTypes.GROUPS, groupsPath);
        processFile(PathTypes.LISTENER, eventsPath);
    }

    private static void MuseumCmd(String line, String[] parts, Database database, Set<Museum> museumsToAdd) {
        if (parts[0].equals("ADD MUSEUM")) {

            MuseumCommands command = new MuseumCommands();
            Museum museum_print;
            Museum museum_exception = null;
            long code = Long.parseLong(parts[1]);
            try {
                command.addMuseum(line, database);
                museum_print = new Museum.MuseumBuilder(parts[2], code, 0, null).build();
                museumsToAdd.add(museum_print);

            } catch (Exception e) {
                museum_exception = new Museum.MuseumBuilder(line, 0, 0, null).build();
                database.addMuseum(museum_exception);
                museum_print = new Museum.MuseumBuilder(parts[2], 0, 0, null)
                        .setEmail(line).build();
                museumsToAdd.add(museum_print);
            }

        }
    }

    private static void GroupCmd(String line, String[] parts, Database database, BufferedWriter writer, String namefile) throws IOException {
        if (parts[0].equals("ADD MEMBER")) {
            GroupCommand command = new GroupCommand();
            try {
                System.out.println(namefile + " AAAA");
                command.addMember(line, database, namefile);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (parts[0].equals("ADD GUIDE")) {
            GroupCommand command = new GroupCommand();
            try {
                command.addGuide(line, database, namefile);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (parts[0].equals("REMOVE MEMBER")) {
            GroupCommand command = new GroupCommand();
            try {
                command.removeMember(line, database, namefile);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (parts[0].equals("FIND GUIDE")) {
            GroupCommand command = new GroupCommand();
            try {
                command.findGuide(line, database, namefile);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (parts[0].equals("REMOVE GUIDE")) {
            GroupCommand command = new GroupCommand();
            try {
                command.removeGuide(line, database, namefile);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (parts[0].equals("FIND MEMBER")) {
            GroupCommand command = new GroupCommand();
            try {
                command.findMember(line, database, namefile);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void ListenerCmd(String line, String[] parts, Database database, BufferedWriter writer) throws IOException {
        if (parts[0].equals("ADD EVENT")) {
            try {
                Long codeParse = Long.parseLong(parts[1]);
                String message = parts[2];
                Museum museum = null;
                for (Museum m : database.getMuseums()) {
                    if (m.getCode() == codeParse) {
                        museum = m;
                        break;
                    }
                }
                if (museum != null) {
                    museum.notifyObservers(message, writer);

                } else {
                    writer.write("Museum not found for code: " + codeParse);
                    writer.newLine();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
