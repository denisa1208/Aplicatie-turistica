package org.example;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashSet;

public class Database {

    // singleton style
    private static Database database;
    private Set<Museum> museums;
    private Set<Group> groups;

    // private constructor
    private Database() {
        museums = new LinkedHashSet<>();
        groups = new LinkedHashSet<>();
    }

    // public method to access the singleton instance
    public static synchronized Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }


    public void resetDatabase() {
        museums.clear();
        groups.clear();
    }

    public void addMuseum(Museum museum) {
        int ok = 0;
        for (Museum m : museums) {
            if (museum.getCode() == m.getCode()) {
                ok = 1;
                break;
            }
        }

        if (ok == 0) {
            museums.add(museum);
        }
    }

    public void addMuseums(Set<Museum> museums) {
        museums.addAll(museums);
    }

    public void  addGroup(Group group) {
        groups.add(group);

    }

    public void addGroups(Set<Group> groups) {
        groups.addAll(groups);
    }

    // getters
    public Set<Museum> getMuseums() {
        return museums;
    }

    public Set<Group> getGroups() {
        return groups;
    }
}
