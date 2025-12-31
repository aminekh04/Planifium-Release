package com.diro.ift2255.model;

/**
 * Classe representant un horaire de cours.
 * Elle contient les informations relatives a une activite
 * comme la section le type et la periode horaire.
 */
public class CourseSchedule {

    /**
     * Section du cours.
     */
    private String section;

    /**
     * Type d activite associee au cours.
     */
    private String activityType;

    /**
     * Jour ou l activite a lieu.
     */
    private String day;

    /**
     * Heure de debut de l activite.
     */
    private String startTime;

    /**
     * Heure de fin de l activite.
     */
    private String endTime;

    /**
     * Construit un horaire de cours a partir des informations fournies.
     *
     * @param section section du cours
     * @param activityType type d activite
     * @param day jour de l activite
     * @param startTime heure de debut
     * @param endTime heure de fin
     */
    public CourseSchedule(
            String section,
            String activityType,
            String day,
            String startTime,
            String endTime
    ) {
        this.section = section;
        this.activityType = activityType;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Retourne la section du cours.
     *
     * @return la section
     */
    public String getSection() { return section; }

    /**
     * Retourne le type d activite du cours.
     *
     * @return le type d activite
     */
    public String getActivityType() { return activityType; }

    /**
     * Retourne le jour de l activite.
     *
     * @return le jour
     */
    public String getDay() { return day; }

    /**
     * Retourne l heure de debut de l activite.
     *
     * @return l heure de debut
     */
    public String getStartTime() { return startTime; }

    /**
     * Retourne l heure de fin de l activite.
     *
     * @return l heure de fin
     */
    public String getEndTime() { return endTime; }

}
