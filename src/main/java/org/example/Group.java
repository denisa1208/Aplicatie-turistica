package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Group {
    List<Person> members;
    Professor guide;
    Integer museumCode;
    String timetable;

    public Group(List<Person> members, Professor guide, Integer museumCode) {
        members = new ArrayList<>();
        this.members = members;
        this.guide = guide;
        this.museumCode = museumCode;
        timetable = "";
    }

    public List<Person> getMembers() {
        return members;
    }
    public void setMembers(List<Person> members) {
        this.members = members;
    }
    public Professor getGuide() {
        return guide;
    }
    public void setGuide(Professor guide) {
        this.guide = guide;
    }
    public Integer getMuseumCode() {
        return museumCode;
    }
    public void setMuseumCode(Integer museumCode) {
        this.museumCode = museumCode;
    }
    public String getTimetable() {
        return timetable;
    }
    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

}

class GroupCommand {
    public void addMember(String line, Database database, String namefile) {
        String[] parts = line.split("\\|");
        String surname = parts[1];
        String name = parts[2];
        String profession = parts[3];
        String age = parts[4];
        int age_parse = Integer.parseInt(age);
        String email = null;
        if (!parts[5].isEmpty()) {
            email = parts[5];
        }

        String school = parts[6];
        String year_study_or_experience = parts[7];
        int year_study_or_exp_parse = Integer.valueOf(year_study_or_experience);
        String role = parts[8];
        String museum_code = parts[9];
        Integer museum_code_int = Integer.valueOf(museum_code);
        String timetable = parts[10];
        Set<Group> groups = database.getGroups();
        Group group_found = null;
        int ok = 0;
        if (!groups.isEmpty()) {
            for (Group group : groups) {
                if (group.museumCode.equals(museum_code_int)) {
                    group_found = group;
                    ok = 1;
                    break;
                }
            }
        }

        if (ok == 0) {
            // grupul trebuie creat
            group_found = new Group(null, null, museum_code_int);
            database.addGroup(group_found);
        }

        Person person =  PersonFactory.createPerson(surname, name, profession, school, year_study_or_exp_parse, timetable);
        person.setEmail(email);
        person.setAge(age_parse);
        person.setTimetable(timetable);
        person.setExperience(year_study_or_exp_parse);
        //System.out.println(person.getName());

        System.out.println("da prof");
        if (group_found.getMembers() == null) {
            group_found.setMembers(new ArrayList<>());
        }
        group_found.getMembers().add(person);
        String name_ocupation = null;
        if (profession.equals("profesor")) {
            name_ocupation = "experience";
        } else {
            name_ocupation = "studyYear";
        }
        System.out.println("name" + name + " si " + email);
        String output = museum_code + " ## " + timetable + " ## " + "new member: " + "surname=" + person.getSurname() + ", " + "name=" + person.getName() + ", role=" + role + ", age=" + person.getAge() + ", email=" + person.getEmail() + ", school=" + person.getSchool() + ", " + name_ocupation + "=" + person.getExperience();
        writeToFile(namefile, output);

    }

    public void addGuide(String line, Database database, String namefile) {
        String[] parts = line.split("\\|");
        String surname = parts[1];
        String name = parts[2];
        String profession = parts[3];
        String age = parts[4];
        int age_parse = Integer.parseInt(age);
        String email = parts[5];
        String school = parts[6];
        String year_study_or_experience = parts[7];
        int year_study_or_exp_parse = Integer.valueOf(year_study_or_experience);
        String role = parts[8];
        String museum_code = parts[9];
        Integer museum_code_int = Integer.valueOf(museum_code);
        String timetable = parts[10];

        Set<Group> groups = database.getGroups();
        Group group_found = null;
        int ok = 0;
        if (!groups.isEmpty()) {
            for (Group group : groups) {
                if (group.museumCode.equals(museum_code_int)) {
                    group_found = group;
                    ok = 1;
                    break;
                }
            }
        }

        if (ok == 0) {
            // grupul trebuie creat
            group_found = new Group(null, null, museum_code_int);
            database.addGroup(group_found);
        }

        // verificam daca exista ghidul deja
        int ok_guide = 0;
//        for (Person person : group_found.getMembers()) {
//            if (person.getName().equals(name) && person.getRole().equals()) {
//                System.out.println("da" + name);
//                ok_guide = 1;
//                break;
//            }
//        }
        if (ok_guide == 0) {

            Professor guide = new Professor(surname, name, profession);
            guide.setEmail(email);
            guide.setAge(age_parse);
            guide.setTimetable(timetable);
            guide.setExperience(year_study_or_exp_parse);
            guide.setSchool(school);
            group_found.setGuide(guide);
            String name_ocupation = null;
            if (profession.equals("profesor")) {
                name_ocupation = "experience";
            } else {
                name_ocupation = "studyYear";
            }
            String output = museum_code + " ## " + timetable + " ## " + "new guide: " + "surname=" + guide.getSurname() + ", " + "name=" + guide.getName() + ", role=" + role + ", age=" + guide.getAge() + ", email=" + guide.getEmail() + ", school=" + guide.getSchool() + ", " + name_ocupation +"=" + guide.getExperience();
            writeToFile(namefile, output);
        }


    }

    public void removeMember(String line, Database database, String namefile) {
        String[] parts = line.split("\\|");
        String surname = parts[1];
        String name = parts[2];
        String profession = parts[3];
        String age = parts[4];
        int age_parse = Integer.parseInt(age);
        String email = parts[5];
        String school = parts[6];
        String year_study_or_experience = parts[7];
        int year_study_or_exp_parse = Integer.valueOf(year_study_or_experience);
        String role = parts[8];
        String museum_code = parts[9];
        Integer museum_code_int = Integer.valueOf(museum_code);
        String timetable = parts[10];

        Set<Group> groups = database.getGroups();
        Group group_found = null;
        int ok = 0;
        if (!groups.isEmpty()) {
            for (Group group : groups) {
                if (group.museumCode.equals(museum_code_int)) {
                    group_found = group;
                    ok = 1;
                    break;
                }
            }
        }

        if (ok == 0) {
            // grupul trebuie creat
            group_found = new Group(null, null, museum_code_int);
            database.addGroup(group_found);
            return;
        }

        if (group_found != null && group_found.getMembers() != null) {
            System.out.println("DADADA");
            Person remove_person = null;
            for (Person person : group_found.getMembers()) {
                if (person.getName().equals(name) && person.getSurname().equals(surname)) {
                    remove_person = person;
                    break;
                }
            }
            String name_ocupation = null;
            if (profession.equals("profesor")) {
                name_ocupation = "experience";
            } else {
                name_ocupation = "studyYear";
            }
            if (remove_person != null) {
                group_found.getMembers().remove(remove_person);
                String output = museum_code + " ## " + timetable + " ## " + "removed member: " + "surname=" + remove_person.getSurname() + ", " + "name=" + remove_person.getName() + ", role=" + role + ", age=" + remove_person.getAge() + ", email=" + remove_person.getEmail() + ", school=" + remove_person.getSchool() + ", " + name_ocupation +"=" + remove_person.getExperience();
                writeToFile(namefile, output);
            }
        }
    }

    private void writeToFile(String namefile, String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namefile, true))) {
            writer.write(output);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
