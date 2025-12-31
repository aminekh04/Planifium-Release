package com.diro.ift2255.model;

import java.util.List;

/**
 * Classe representant un ensemble de cours.
 * Elle regroupe une liste didentifiants de cours.
 */
public class CourseSet {

    /**
     * Liste des identifiants de cours.
     */
    private List<String> courseIds;

    /**
     * Construit un ensemble de cours a partir dune liste didentifiants.
     *
     * @param courseIds liste des identifiants de cours
     */
    public CourseSet(List<String> courseIds) {
        this.courseIds = courseIds;
    }

    /**
     * Retourne la liste des identifiants de cours.
     *
     * @return la liste des identifiants de cours
     */
    public List<String> getCourseIds() {
        return courseIds;
    }
}
