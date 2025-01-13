    package org.example;

    import java.util.ArrayList;
    import java.util.List;

    public class Professor extends Person implements Subject{

        // design pattern - Observer
        int experience;
        String school;
        String timetable;
        private List<Observer> observers;


        public Professor(String surname, String name, String role) {
            super(surname, name, role);
            this.observers = new ArrayList<>();
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
            notifyObservers();
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
            notifyObservers();
        }


        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);

        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);

        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update("ana are mere");
            }
        }
    }
