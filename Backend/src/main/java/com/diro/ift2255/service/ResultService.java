package com.diro.ift2255.service;

import com.diro.ift2255.model.AcademicResult;
import com.diro.ift2255.repository.ResultRepository;

/**
 * Service responsable de la gestion des resultats academiques.
 * Il permet de recuperer les resultats associes a un cours
 * et destimer le niveau de difficulte.
 */
public class ResultService {

    /**
     * Depot utilise pour acceder aux resultats academiques.
     */
    private final ResultRepository repository = new ResultRepository();

    /**
     * Recupere le resultat academique associe a un cours donne.
     *
     * @param courseId identifiant du cours
     * @return le resultat academique du cours
     * @throws IllegalArgumentException si l identifiant est invalide
     * ou si aucun resultat nest trouve
     */
    public AcademicResult getResultForCourse(String courseId) {

        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("Identifiant de cours invalide.");
        }

        return repository.loadAll().stream()
                .filter(r -> r.getSigle().equalsIgnoreCase(courseId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Aucun resultat academique trouve pour le cours " + courseId
                        )
                );
    }

    /**
     * Estime le niveau de difficulte dun cours a partir de son score.
     *
     * @param courseId identifiant du cours
     * @return le niveau de difficulte estime
     */
    public int estimateDifficulty(String courseId) {

        AcademicResult result;

        try {
            result = getResultForCourse(courseId);
        } catch (IllegalArgumentException e) {
            return 3;
        }

        double score = result.getScore();

        if (score >= 4.2) return 1;
        if (score >= 3.6) return 2;
        if (score >= 3.0) return 3;
        if (score >= 2.3) return 4;
        return 5;
    }

}
