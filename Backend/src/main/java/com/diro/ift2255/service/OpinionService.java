package com.diro.ift2255.service;

import com.diro.ift2255.model.Opinion;
import com.diro.ift2255.repository.OpinionRepository;

import java.util.List;

public class OpinionService {

    private final OpinionRepository repository = new OpinionRepository();

    public void addOpinion(Opinion opinion) {

        if (opinion == null) {
            throw new IllegalArgumentException("Opinion invalide");
        }

        if (opinion.getCourse_code() == null || opinion.getCourse_code().isBlank()) {
            throw new IllegalArgumentException("course_code requis");
        }

        if (opinion.getText() == null || opinion.getText().isBlank()) {
            throw new IllegalArgumentException("texte de l'avis requis");
        }

        repository.add(opinion);
    }

    public List<Opinion> getOpinions(String courseCode) {

        // 🔴 SI PAS DE SIGLE → PAS D’AVIS
        if (courseCode == null || courseCode.isBlank()) {
            return List.of();   // ✅ liste vide
        }

        return repository.findByCourseCode(
                courseCode.trim().toUpperCase()
        );
    }

}
