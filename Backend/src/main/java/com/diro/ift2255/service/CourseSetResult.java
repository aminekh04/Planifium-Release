package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseConflict;

import java.util.List;

/**
 * Classe representant le resultat dun ensemble de cours.
 * Elle contient les horaires generes ainsi que les conflits detectes.
 */
public class CourseSetResult {

    /**
     * Liste des horaires associes aux cours.
     */
    private List<CourseScheduleWithCourse> schedules;

    /**
     * Liste des conflits dhoraires entre les cours.
     */
    private List<CourseConflict> conflicts;

    /**
     * Construit un resultat pour un ensemble de cours.
     *
     * @param schedules liste des horaires generes
     * @param conflicts liste des conflits detectes
     */
    public CourseSetResult(
            List<CourseScheduleWithCourse> schedules,
            List<CourseConflict> conflicts
    ) {
        this.schedules = schedules;
        this.conflicts = conflicts;
    }

    /**
     * Retourne la liste des horaires.
     *
     * @return la liste des horaires
     */
    public List<CourseScheduleWithCourse> getSchedules() {
        return schedules;
    }

    /**
     * Retourne la liste des conflits.
     *
     * @return la liste des conflits
     */
    public List<CourseConflict> getConflicts() {
        return conflicts;
    }
}
