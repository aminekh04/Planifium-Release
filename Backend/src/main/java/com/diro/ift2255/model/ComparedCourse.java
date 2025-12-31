package com.diro.ift2255.model;

/**
 * Classe representant un cours utilise dans une comparaison.
 * Elle contient des informations generales ainsi que des indicateurs
 * bases sur les avis et les resultats academiques.
 */
public class ComparedCourse {

    /**
     * Identifiant du cours.
     */
    private String courseId;

    /**
     * Nom du cours.
     */
    private String name;

    /**
     * Nombre de credits associes au cours.
     */
    private int credits;

    /**
     * Nombre davis disponibles pour le cours.
     */
    private int reviewCount;

    /**
     * Charge de travail associee au cours.
     */
    private int workload;

    /**
     * Niveau de difficulte associe au cours.
     */
    private int difficulty;

    /**
     * Construit un cours comparable a partir des donnees fournies.
     *
     * @param courseId identifiant du cours
     * @param name nom du cours
     * @param credits nombre de credits
     * @param reviewCount nombre davis
     * @param workload charge de travail
     * @param difficulty niveau de difficulte
     */
    public ComparedCourse(
            String courseId,
            String name,
            int credits,
            int reviewCount,
            int workload,
            int difficulty
    ) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.reviewCount = reviewCount;
        this.workload = workload;
        this.difficulty = difficulty;
    }

    /**
     * Retourne lidentifiant du cours.
     *
     * @return lidentifiant du cours
     */
    public String getCourseId() { return courseId; }

    /**
     * Retourne le nom du cours.
     *
     * @return le nom du cours
     */
    public String getName() { return name; }

    /**
     * Retourne le nombre de credits du cours.
     *
     * @return le nombre de credits
     */
    public int getCredits() { return credits; }

    /**
     * Retourne le nombre davis associes au cours.
     *
     * @return le nombre davis
     */
    public int getReviewCount() { return reviewCount; }

    /**
     * Retourne la charge de travail du cours.
     *
     * @return la charge de travail
     */
    public int getWorkload() { return workload; }

    /**
     * Retourne le niveau de difficulte du cours.
     *
     * @return le niveau de difficulte
     */
    public int getDifficulty() { return difficulty; }
}
