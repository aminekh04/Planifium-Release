package com.diro.ift2255.model;

import java.util.List;

/**
 * Classe representant l horaire associe a un cours dans un ensemble de cours.
 * Elle contient l identifiant du cours et la liste de ses plages horaires.
 */
public class CourseSetSchedule {

    /**
     * Identifiant du cours.
     */
    private String courseId;

    /**
     * Liste des horaires associes au cours.
     */
    private List<CourseSchedule> schedules;

    /**
     * Construit un horaire pour un cours a partir des informations fournies.
     *
     * @param courseId identifiant du cours
     * @param schedules liste des horaires du cours
     */
    public CourseSetSchedule(String courseId, List<CourseSchedule> schedules) {
        this.courseId = courseId;
        this.schedules = schedules;
    }

    /**
     * Retourne l identifiant du cours.
     *
     * @return l identifiant du cours
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Retourne la liste des horaires associes au cours.
     *
     * @return la liste des horaires
     */
    public List<CourseSchedule> getSchedules() {
        return schedules;
    }
}
