package com.diro.ift2255.controller;

import com.diro.ift2255.model.AcademicResult;
import com.diro.ift2255.service.ResultService;
import io.javalin.http.Context;

/**
 * Controleur responsable de la consultation des resultats academiques.
 * Il permet de recuperer les resultats associes a un cours donne.
 */
public class ResultController {

    /**
     * Service utilise pour acceder aux resultats academiques.
     */
    private static final ResultService service = new ResultService();

    /**
     * Recupere le resultat academique associe a un cours.
     *
     * @param ctx contexte de la requete http
     * @return le resultat academique du cours
     * @throws IllegalArgumentException si le sigle du cours est invalide
     */
    public static void getResult(Context ctx) {

        String courseId = ctx.pathParam("courseId");

        try {
            AcademicResult result = service.getResultForCourse(courseId);
            ctx.json(result);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erreur interne lors de la récupération des résultats.");
        }
    }
}
