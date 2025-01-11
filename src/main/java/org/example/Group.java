package org.example;

import java.util.ArrayList;
import java.util.List;

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
