package com.diro.ift2255.model;

/**
 * Classe representant un conflit dhoraire entre deux cours.
 * Elle contient les informations necessaires pour identifier
 * la periode de chevauchement entre les cours.
 */
public class CourseConflict {

    /**
     * Identifiant du premier cours concerne par le conflit.
     */
    private String courseA;

    /**
     * Identifiant du second cours concerne par le conflit.
     */
    private String courseB;

    /**
     * Jour ou le conflit dhoraire se produit.
     */
    private String day;

    /**
     * Heure de debut du conflit.
     */
    private String startTime;

    /**
     * Heure de fin du conflit.
     */
    private String endTime;

    /**
     * Construit un conflit dhoraire entre deux cours.
     *
     * @param courseA identifiant du premier cours
     * @param courseB identifiant du second cours
     * @param day jour du conflit
     * @param startTime heure de debut
     * @param endTime heure de fin
     */
    public CourseConflict(
            String courseA,
            String courseB,
            String day,
            String startTime,
            String endTime
    ) {
        this.courseA = courseA;
        this.courseB = courseB;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Retourne l identifiant du premier cours.
     *
     * @return l identifiant du premier cours
     */
    public String getCourseA() { return courseA; }

    /**
     * Retourne l identifiant du second cours.
     *
     * @return l identifiant du second cours
     */
    public String getCourseB() { return courseB; }

    /**
     * Retourne le jour du conflit dhoraire.
     *
     * @return le jour du conflit
     */
    public String getDay() { return day; }

    /**
     * Retourne l heure de debut du conflit.
     *
     * @return l heure de debut
     */
    public String getStartTime() { return startTime; }

    /**
     * Retourne l heure de fin du conflit.
     *
     * @return l heure de fin
     */
    public String getEndTime() { return endTime; }
}
