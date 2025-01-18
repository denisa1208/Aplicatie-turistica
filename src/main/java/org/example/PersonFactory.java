package org.example;

public class PersonFactory {
    public static Person createPerson(String surname, String name, String role, String school, int experienceStudyYear, String timetable) {
        if (role.equalsIgnoreCase("student")) {

            Student student = new Student(surname, name, role);
            student.setSchool(school);
            student.setStudyYear(experienceStudyYear);
            student.setTimetable(timetable);
            return student;
        } else if (role.equalsIgnoreCase("profesor")) {
            Professor professor = new Professor(surname, name, role);
            professor.setSchool(school);
            professor.setExperience(experienceStudyYear);
            professor.setTimetable(timetable);
            return professor;
        }
        return null;
    }
}
