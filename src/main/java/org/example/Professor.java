    package org.example;

    import java.util.ArrayList;
    import java.util.List;

    public abstract class Professor extends Person implements Subject{

        // design pattern - Observer
        int experience;
        String school;
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
