package com.diro.ift2255.service;

import com.diro.ift2255.model.Opinion;
import com.diro.ift2255.repository.OpinionRepository;

import java.util.List;

/**
 * Service responsable de la gestion des avis.
 * Il permet la validation lenregistrement et la recuperation
 * des avis associes aux cours.
 */
public class OpinionService {

    /**
     * Depot utilise pour stocker les avis.
     */
    private final OpinionRepository repository = new OpinionRepository();

    /**
     * Ajoute un avis apres validation des donnees fournies.
     *
     * @param opinion avis a ajouter
     * @throws IllegalArgumentException si lavis est invalide
     */
    public void addOpinion(Opinion opinion) {

        if (opinion == null) {
            throw new IllegalArgumentException("Opinion invalide");
        }

        if (opinion.getCourse_code() == null || opinion.getCourse_code().isBlank()) {
            throw new IllegalArgumentException("course_code requis");
        }

        if (opinion.getText() == null || opinion.getText().isBlank()) {
            throw new IllegalArgumentException("texte de l avis requis");
        }

        repository.add(opinion);
    }

    /**
     * Retourne la liste des avis associes a un cours donne.
     *
     * @param courseCode code du cours
     * @return la liste des avis
     */
    public List<Opinion> getOpinions(String courseCode) {

        if (courseCode == null || courseCode.isBlank()) {
            return List.of();
        }

        return repository.findByCourseCode(
                courseCode.trim().toUpperCase()
        );
    }

}
