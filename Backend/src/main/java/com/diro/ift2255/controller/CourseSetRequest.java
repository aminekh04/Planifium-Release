package com.diro.ift2255.controller;

import java.util.List;

/**
 * Classe representant une requete de creation dun ensemble de cours.
 * Elle contient les informations necessaires pour generer un horaire.
 */
public class CourseSetRequest {

    /**
     * Trimestre pour lequel l horaire doit etre genere.
     */
    private String semester;

    /**
     * Liste des sigles de cours a inclure dans l horaire.
     */
    private List<String> courses;

    /**
     * Retourne le trimestre associe a la requete.
     *
     * @return le trimestre
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Retourne la liste des cours associes a la requete.
     *
     * @return la liste des sigles de cours
     */
    public List<String> getCourses() {
        return courses;
    }
}
