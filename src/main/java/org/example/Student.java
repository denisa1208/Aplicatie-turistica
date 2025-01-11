package org.example;

public class Student extends Person {

    String school;
    int studyYear;
    public Student(String surname, String name, String role) {
        super(surname, name, role);
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public int getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }



}
