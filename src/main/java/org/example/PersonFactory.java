package org.example;

import java.util.Observer;

public class PersonFactory {
    public static Person createPerson(String surname, String name, String role, String school, int experienceStudyYear) {
        if (role.equalsIgnoreCase("Student")) {

            Student student = new Student(surname, name, role);
            student.setSchool(school);
            student.setStudyYear(experienceStudyYear);
            return student;
        } else if (role.equalsIgnoreCase("Professor")) {
            Professor professor = new Professor(surname, name, role) {
                @Override
                public void addObserver(Observer observer) {

                }

                @Override
                public void removeObserver(Observer observer) {

                }

                @Override
                public void addObserver(org.example.Observer observer) {

                }

                @Override
                public void removeObserver(org.example.Observer observer) {

                }

                @Override
                public void notifyObservers() {

                }
            };
            professor.setSchool(school);
            professor.setExperience(experienceStudyYear);
            return professor;
        }
        return null;
    }
}
