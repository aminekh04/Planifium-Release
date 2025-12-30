package com.diro.ift2255.model;

public class Program {

    private String id;
    private String name;
    private int nbCourses;

    public Program(String id, String name, int nbCourses) {
        this.id = id;
        this.name = name;
        this.nbCourses = nbCourses;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNbCourses() {
        return nbCourses;
    }
}
