package com.diro.ift2255.model;

/**
 * Classe representant un cours.
 * Elle contient les informations de base associees a un cours universitaire.
 */
public class Course {

    /**
     * Identifiant du cours.
     */
    private String id;

    /**
     * Nom du cours.
     */
    private String name;

    /**
     * Nombre de credits associes au cours.
     */
    private int credits;

    /**
     * Construit un cours a partir des informations fournies.
     *
     * @param id identifiant du cours
     * @param name nom du cours
     * @param credits nombre de credits
     */
    public Course(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    /**
     * Retourne lidentifiant du cours.
     *
     * @return lidentifiant
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le nom du cours.
     *
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le nombre de credits du cours.
     *
     * @return le nombre de credits
     */
    public int getCredits() {
        return credits;
    }
}
