package org.example;

import java.io.*;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
            Database database = Database.getInstance();
            Set<Museum> museumsToAdd = new LinkedHashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    //System.out.println(line + "ex2");
                    if (parts[0].equals("ADD MUSEUM")) {

                        MuseumCommands command = new MuseumCommands();
                        Museum museum_print;
                        Museum museum_exception = null;
                        long code = Long.parseLong(parts[1]);
                        try {
                            command.addMuseum(line, database, museumsToAdd);
                            museum_print = new Museum.MuseumBuilder(parts[2], code, 0, null).build();
                            museumsToAdd.add(museum_print);
                            //System.out.println(museum_print.getCode() + ": " + museum_print.getName());

                        } catch (Exception e) {
                            museum_exception = new Museum.MuseumBuilder(line, 0, 0, null).build();
                            database.addMuseum(museum_exception);
                            museum_print = new Museum.MuseumBuilder(parts[2], 0, 0, null)
                                    .setEmail(line).build();
                            museumsToAdd.add(museum_print);
                           // System.out.println(e.getMessage());
                        }

                        System.out.println(museum_print.getCode() + ": " + museum_print.getName());

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1] + ".out"))) {

                            database.addMuseums(museumsToAdd);
                            for (Museum museum : museumsToAdd) {
                                if (museum.getCode() != 0) {
                                    writer.write(museum.getCode() + ": " + museum.getName());
                                } else {
                                    writer.write("Exception: Data is broken. ## (" + museum.getEmail() + ")");
                                }
                                writer.newLine();
                            }


                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                       // database.resetDatabase();
                    } else if (parts[0].equals("ADD MEMBER")) {
                        String surname = parts[1];
                        String name = parts[2];
                        String profession = parts[3];
                        String age = parts[4];
                        String email = parts[5];
                        String school = parts[6];
                        String year_study_or_experience = parts[7];
                        int year_study_or_exp_parse = Integer.valueOf(year_study_or_experience);
                        String role = parts[8];
                        String museum_code = parts[9];
                        String timetable = parts[10];
                        GroupCommand command = new GroupCommand();
                        String namefile = args[1] + ".out";
                        command.addMember(line, database, namefile  );

                    Set<Group> groups = database.getGroups();
                    //System.out.println(groups.size() + " groups");


                    } else if (parts[0].equals("ADD GUIDE")) {
                        String namefile = args[1] + ".out";
                        GroupCommand command = new GroupCommand();

                        command.addGuide(line, database, namefile);
                    } else if(parts[0].equals("REMOVE MEMBER")) {
                        GroupCommand command = new GroupCommand();
                        String namefile = args[1] + ".out";
                        command.removeMember(line, database, namefile);
                    }

                }

                Set<Group> groups = database.getGroups();
                for (Group group : groups) {
                    for (Person person : group.getMembers()) {
                        System.out.println(person.getName() + " " + person.getEmail());
                    }
                }


            database = null;
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }



        } else if (args.length == 4) {
           //processMultipleFiles(pathType, args[1], args[2], args[3]);
            System.out.println("Path: "+ pathType + " " + args[1] +" "+ " " + args[2] + " " + args[3]);
        }
    }

}
