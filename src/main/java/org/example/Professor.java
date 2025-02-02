    package org.example;

    import java.io.BufferedWriter;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    public class Professor extends Person implements Observer {

        // design pattern - Observer
        int experience;
        String school;
        String timetable;



        public Professor(String surname, String name, String role) {
            super(surname, name, role);
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;

        }

        public String getSchool() {
            return school;
        }

        public String getTimetable() {
            return timetable;
        }

        public void setTimetable(String timetable) {
            this.timetable = timetable;
        }

        public void setSchool(String school) {
            this.school = school;

        }





        @Override
        public void update(String message, BufferedWriter writer) {
            try {
                writer.write("To: " + getEmail() + " ## " + message);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }
