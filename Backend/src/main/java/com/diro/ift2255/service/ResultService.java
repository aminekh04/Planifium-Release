package com.diro.ift2255.service;

import com.diro.ift2255.model.AcademicResult;
import com.diro.ift2255.repository.ResultRepository;

public class ResultService {

    private final ResultRepository repository = new ResultRepository();

    public AcademicResult getResultForCourse(String courseId) {

        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("Identifiant de cours invalide.");
        }

        return repository.loadAll().stream()
                .filter(r -> r.getSigle().equalsIgnoreCase(courseId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Aucun résultat académique trouvé pour le cours " + courseId
                        )
                );
    }
    public int estimateDifficulty(String courseId) {

        AcademicResult result;

        try {
            result = getResultForCourse(courseId);
        } catch (IllegalArgumentException e) {
            // cours absent du CSV → difficulté moyenne
            return 3;
        }

        double score = result.getScore();

        // Barème clair, conforme à l’énoncé
        if (score >= 4.2) return 1; // très facile
        if (score >= 3.6) return 2;
        if (score >= 3.0) return 3;
        if (score >= 2.3) return 4;
        return 5;                  // très difficile
    }


}
