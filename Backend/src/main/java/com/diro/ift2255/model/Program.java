package com.diro.ift2255.model;

/**
 * Classe representant un programme detudes.
 * Elle contient les informations generales associees a un programme.
 */
public class Program {

    /**
     * Identifiant du programme.
     */
    private String id;

    /**
     * Nom du programme.
     */
    private String name;

    /**
     * Nombre total de cours dans le programme.
     */
    private int nbCourses;

    /**
     * Construit un programme a partir des informations fournies.
     *
     * @param id identifiant du programme
     * @param name nom du programme
     * @param nbCourses nombre de cours du programme
     */
    public Program(String id, String name, int nbCourses) {
        this.id = id;
        this.name = name;
        this.nbCourses = nbCourses;
    }

    /**
     * Retourne l identifiant du programme.
     *
     * @return l identifiant
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le nom du programme.
     *
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le nombre de cours du programme.
     *
     * @return le nombre de cours
     */
    public int getNbCourses() {
        return nbCourses;
    }
}
