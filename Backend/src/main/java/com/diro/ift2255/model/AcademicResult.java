package com.diro.ift2255.model;

/**
 * Classe representant les resultats academiques associes a un cours.
 * Elle contient des informations statistiques sur la performance
 * et la participation des etudiants.
 */
public class AcademicResult {

    /**
     * Sigle du cours.
     */
    private String sigle;

    /**
     * Nom du cours.
     */
    private String nom;

    /**
     * Moyenne generale obtenue pour le cours.
     */
    private String moyenne;

    /**
     * Score global associe au cours.
     */
    private double score;

    /**
     * Nombre total de participants.
     */
    private int participants;

    /**
     * Nombre de trimestres consideres.
     */
    private int trimestres;

    /**
     * Construit un resultat academique a partir des donnees fournies.
     *
     * @param sigle sigle du cours
     * @param nom nom du cours
     * @param moyenne moyenne generale du cours
     * @param score score global du cours
     * @param participants nombre de participants
     * @param trimestres nombre de trimestres consideres
     */
    public AcademicResult(String sigle, String nom, String moyenne,
                          double score, int participants, int trimestres) {
        this.sigle = sigle;
        this.nom = nom;
        this.moyenne = moyenne;
        this.score = score;
        this.participants = participants;
        this.trimestres = trimestres;
    }

    /**
     * Retourne le sigle du cours.
     *
     * @return le sigle
     */
    public String getSigle() { return sigle; }

    /**
     * Retourne le nom du cours.
     *
     * @return le nom
     */
    public String getNom() { return nom; }

    /**
     * Retourne la moyenne generale du cours.
     *
     * @return la moyenne
     */
    public String getMoyenne() { return moyenne; }

    /**
     * Retourne le score global du cours.
     *
     * @return le score
     */
    public double getScore() { return score; }

    /**
     * Retourne le nombre de participants.
     *
     * @return le nombre de participants
     */
    public int getParticipants() { return participants; }

    /**
     * Retourne le nombre de trimestres consideres.
     *
     * @return le nombre de trimestres
     */
    public int getTrimestres() { return trimestres; }

}
