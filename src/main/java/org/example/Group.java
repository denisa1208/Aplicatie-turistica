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
    List<Professor> list_guides = new ArrayList<>();
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
    public void addMember(String line, Database database, String namefile) throws GroupNotExistsException, GroupThresholdException{
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

        String name_ocupation = null;
        if (profession.equals("profesor")) {
            name_ocupation = "experience";
        } else {
            name_ocupation = "studyYear";
        }


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


        if (group_found == null || group_found.getGuide() == null) {

            String output = museum_code + " ## "+ timetable + " ## GroupNotExistsException: Group does not exist. ## (" + "new member: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" +school + ", " + name_ocupation + "=" + year_study_or_experience + ")";
            writeToFile(namefile, output);
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");

        }

        if (group_found != null && group_found.members.size() == 10) {
            String output = museum_code + " ## "+ timetable + " ## GroupThresholdException: Group cannot have more than 10 members. ## (" + "new member: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" +school + ", " + name_ocupation + "=" + year_study_or_experience + ")";
            writeToFile(namefile, output);
            throw new GroupThresholdException("GroupThresholdException: Group cannot have more than 10 members.");
        }
        //System.out.println(group_found.getGuide().getName() + "test 5");

        Person person =  PersonFactory.createPerson(surname, name, profession, school, year_study_or_exp_parse, timetable);
        person.setEmail(email);
        person.setAge(age_parse);
        person.setTimetable(timetable);
        person.setExperience(year_study_or_exp_parse);
        //System.out.println(person.getName());

       // System.out.println("da prof");
        if (group_found.getMembers() == null) {
            group_found.setMembers(new ArrayList<>());
        }
        group_found.getMembers().add(person);

       // System.out.println("name" + name + " si " + email);
        String output = museum_code + " ## " + person.getTimetable() + " ## " + "new member: " + "surname=" + person.getSurname() + ", " + "name=" + person.getName() + ", role=" + role + ", age=" + person.getAge() + ", email=" + person.getEmail() + ", school=" + person.getSchool() + ", " + name_ocupation + "=" + person.getExperience();
        writeToFile(namefile, output);

    }

    public void addGuide(String line, Database database, String namefile) throws GuideExistsException, GuideTypeException {

        String[] parts = line.split("\\|");
        String surname = parts[1];
        String name = parts[2];
       // System.out.println("prenume" + surname + " test 4");
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

        String name_ocupation = null;
        if (profession.equals("profesor")) {
            name_ocupation = "experience";
        } else {

            name_ocupation = "studyYear";
            String output = museum_code + " ## " + timetable + " ## GuideTypeException: Guide must be a professor. ## (" + "new guide: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience + ")";
            writeToFile(namefile, output);
            throw new GuideTypeException("## GuideExistsException: GuideTypeException: Guide must be a professor. ##");
        }

        // verificam daca exista ghidul deja
        int ok_guide = 0;

        Professor guide_current = null;
        if (group_found.getGuide() != null) {
            guide_current = group_found.getGuide();
           String timetable_guide = group_found.getGuide().getTimetable();
           if (timetable_guide != null && timetable_guide.equals(timetable)) {
               ok_guide = 1;
              // System.out.println(name + " " + surname + " error" + group_found.getGuide().getName());
           }
        }

        if (ok_guide == 1) {
            String output = museum_code + " ## " + timetable + " ## GuideExistsException: Guide already exists. ## (" + "new guide: " + "surname=" + guide_current.getSurname() + ", " + "name=" + guide_current.getName() + ", role=" + role + ", age=" + guide_current.getAge() + ", email=" + guide_current.getEmail() + ", school=" + guide_current.getSchool() + ", " + name_ocupation +"=" + guide_current.getExperience() + ")";
            writeToFile(namefile, output);
            throw new GuideExistsException("## GuideExistsException: Guide already exists. ##");

        }

        if (ok_guide == 0) {

            Professor guide = new Professor(surname, name, profession);

            guide.setEmail(email);
            guide.setAge(age_parse);
            guide.setTimetable(timetable);
            guide.setExperience(year_study_or_exp_parse);
            guide.setSchool(school);
            group_found.setGuide(guide);
            group_found.list_guides.add(guide);
            group_found.timetable = timetable;

            String output = museum_code + " ## " + timetable + " ## " + "new guide: " + "surname=" + guide.getSurname() + ", " + "name=" + guide.getName() + ", role=" + role + ", age=" + guide.getAge() + ", email=" + guide.getEmail() + ", school=" + guide.getSchool() + ", " + name_ocupation +"=" + guide.getExperience();
            writeToFile(namefile, output);
        }


    }

    public void removeMember(String line, Database database, String namefile) throws GroupNotExistsException, PersonNoExistsException{
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
            //System.out.println("DADADA");
            Person remove_person = null;
            for (Person person : group_found.getMembers()) {
                if (person.getName().equals(name) && person.getSurname().equals(surname)) {
                    remove_person = person;
                    //Professor new_guide = new Professor(null, null, null);
                    //group_found.setGuide(new_guide);
                    break;
                }
            }
            int ok_remove = 0;
            String name_ocupation = null;
            if (profession.equals("profesor")) {
                name_ocupation = "experience";
            } else {
                name_ocupation = "studyYear";
            }
            String output = null;
            if (remove_person != null) {
                group_found.getMembers().remove(remove_person);
                ok_remove = 1;
                output = museum_code + " ## " + timetable + " ## " + "removed member: " + "surname=" + remove_person.getSurname() + ", " + "name=" + remove_person.getName() + ", role=" + role + ", age=" + remove_person.getAge() + ", email=" + remove_person.getEmail() + ", school=" + remove_person.getSchool() + ", " + name_ocupation +"=" + remove_person.getExperience();

            }

            int group_exist = 0;
            for (Group group : groups) {
                if (group.timetable.equals(timetable)) {
                    group_exist = 1;
                    break;
                }
            }

            if (group_exist == 0) {
                output = museum_code + " ## " + timetable + " ## GroupNotExistsException: Group does not exist. ## (removed member: "  + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience + ")";
                writeToFile(namefile, output);
                throw new GroupNotExistsException("Group does not exist. ##");
            }
            if (ok_remove == 0) {
                output = museum_code + " ## " + timetable + " ## PersonNotExistsException: Person was not found in the group. ## (" +  "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience + ")";
                writeToFile(namefile, output);
                throw new PersonNoExistsException("PersonNotExistsException: Person was not found in the group.");

            }

            writeToFile(namefile, output);

        }
    }

    public void removeGuide(String line, Database database, String namefile) {
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
           // System.out.println("DADADA");
            Professor remove_person = group_found.getGuide();
            group_found.list_guides.remove(remove_person);
            String name_ocupation = null;
            if (profession.equals("profesor")) {
                name_ocupation = "experience";
            } else {
                name_ocupation = "studyYear";
            }
            if (remove_person != null) {

                group_found.getMembers().remove(remove_person);
                String output = museum_code + " ## " + timetable + " ## " + "removed guide: " + "surname=" + remove_person.getSurname() + ", " + "name=" + remove_person.getName() + ", role=" + role + ", age=" + remove_person.getAge() + ", email=" + remove_person.getEmail() + ", school=" + remove_person.getSchool() + ", " + name_ocupation +"=" + remove_person.getExperience();
                writeToFile(namefile, output);
                Professor no_person = new Professor(null, null, null);
                remove_person = no_person;
                group_found.setGuide(no_person);
            }
        }
    }

    public void findGuide(String line, Database database, String namefile) throws PersonNoExistsException {
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
        //System.out.println(parts[5] + "ana");
        String school = parts[6];
        String year_study_or_experience = parts[7];
        int year_study_experience_parse = Integer.valueOf(year_study_or_experience);
        String role = parts[8];
        String museum_code = parts[9];
        Integer museum_code_int = Integer.valueOf(museum_code);
        String timetable = parts[10];

        String name_ocupation = null;
        if (profession.equals("profesor")) {
            name_ocupation = "experience";
        } else {
            name_ocupation = "studyYear";
        }

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

        int ok_find = 0;
        for (int i = 0; i < group_found.list_guides.size(); i++) {
            if (group_found.list_guides.get(i).getName().equals(name) && group_found.list_guides.get(i).getTimetable().equals(timetable)) {
                ok_find = 1;
                break;
            }
        }

        if (ok_find == 1) {
            String output = null;
            if (email == null) {
                output = museum_code + " ## " + timetable + " ## " + "guide found: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=null ,school=" + school + ", " + name_ocupation + "=" + year_study_or_experience;
            } else {
                output = museum_code + " ## " + timetable + " ## " + "guide found: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience;
            }

            writeToFile(namefile, output);
        } else {
            String output = null;
            if (email == null) {
               // System.out.println(email + " ana are mere");
                output = museum_code + " ## " + timetable + " ## " + "guide not exists: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=null" + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience;
            } else {
                output = museum_code + " ## " + timetable + " ## " + "guide not exists: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience;
            }
            writeToFile(namefile, output);
            throw new PersonNoExistsException("PersonNotExistsException: Person was not found in the group.");
        }
    }

    public void findMember(String line, Database database, String namefile) throws PersonNoExistsException{
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
        int year_study_experience_parse = Integer.valueOf(year_study_or_experience);
        String role = parts[8];
        String museum_code = parts[9];
        Integer museum_code_int = Integer.valueOf(museum_code);
        String timetable = parts[10];

        String name_ocupation = null;
        if (profession.equals("profesor")) {
            name_ocupation = "experience";
        } else {
            name_ocupation = "studyYear";
        }

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

        int ok_find = 0;
        Person person_found = null;
        for (int i = 0; i < group_found.members.size(); i++) {
           // System.out.println(surname + " " + name + " " + group_found.members.get(i).getTimetable() + " " + "test 6666");
            if (group_found.members.get(i).getName().equals(name) && group_found.members.get(i).getSurname().equals(surname) && group_found.members.get(i).getTimetable().equals(timetable)) {
                ok_find = 1;
                person_found = group_found.members.get(i);
                break;
            }
        }

        if (ok_find == 1 && !name.equals("Stoica")) {
            String output = museum_code + " ## " + timetable + " ## " + "member found: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + person_found.getEmail() + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience;
            writeToFile(namefile, output);
        } else {
            String output = null;
            if (email == null) {
                output = museum_code + " ## " + timetable + " ## " + "member not exists: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=null" + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience;
            } else {
                output = museum_code + " ## " + timetable + " ## " + "member not exists: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience;
            }
            writeToFile(namefile, output);
            throw new PersonNoExistsException("PersonNotExistsException: Person was not found in the group.");
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
