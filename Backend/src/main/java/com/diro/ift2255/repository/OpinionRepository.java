package com.diro.ift2255.repository;

import com.diro.ift2255.model.Opinion;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Classe responsable de la gestion des avis en memoire.
 * Elle permet lajout et la recuperation des avis associes aux cours.
 */
public class OpinionRepository {

    /**
     * Liste des avis stockes en memoire.
     */
    private final List<Opinion> opinions = new CopyOnWriteArrayList<>();

    /**
     * Ajoute un avis a la liste des avis.
     *
     * @param opinion avis a ajouter
     */
    public void add(Opinion opinion) {
        opinions.add(opinion);
    }

    /**
     * Retourne la liste complete des avis.
     *
     * @return la liste des avis
     */
    public List<Opinion> findAll() {
        return opinions;
    }

    /**
     * Retourne la liste des avis associes a un code de cours.
     *
     * @param courseCode code du cours
     * @return la liste des avis associes au cours
     */
    public List<Opinion> findByCourseCode(String courseCode) {
        return opinions.stream()
                .filter(o -> o.getCourse_code() != null
                        && o.getCourse_code().equalsIgnoreCase(courseCode))
                .collect(Collectors.toList());
    }
}
