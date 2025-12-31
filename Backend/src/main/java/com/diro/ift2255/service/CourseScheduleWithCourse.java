package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseSchedule;

import java.util.Objects;

/**
 * Classe representant un horaire de cours associe a un identifiant de cours.
 * Elle combine les informations de l horaire avec le cours correspondant.
 */
public class CourseScheduleWithCourse {

    /**
     * Identifiant du cours.
     */
    private String courseId;

    /**
     * Section du cours.
     */
    private String section;

    /**
     * Type d activite associee au cours.
     */
    private String activityType;

    /**
     * Jour de l activite.
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
     * Construit un horaire associe a un cours a partir dun objet horaire.
     *
     * @param courseId identifiant du cours
     * @param schedule horaire du cours
     */
    public CourseScheduleWithCourse(
            String courseId,
            CourseSchedule schedule
    ) {
        this.courseId = courseId;
        this.section = schedule.getSection();
        this.activityType = schedule.getActivityType();
        this.day = schedule.getDay();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
    }

    /**
     * Retourne l identifiant du cours.
     *
     * @return l identifiant du cours
     */
    public String getCourseId() { return courseId; }

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

    /**
     * Compare cet objet a un autre pour determiner legalite.
     *
     * @param o objet a comparer
     * @return vrai si les objets sont egaux
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseScheduleWithCourse)) return false;

        CourseScheduleWithCourse that = (CourseScheduleWithCourse) o;

        return Objects.equals(courseId, that.courseId)
                && Objects.equals(section, that.section)
                && Objects.equals(activityType, that.activityType)
                && Objects.equals(day, that.day)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime);
    }

    /**
     * Calcule le code de hachage de l objet.
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                courseId,
                section,
                activityType,
                day,
                startTime,
                endTime
        );
    }
}
