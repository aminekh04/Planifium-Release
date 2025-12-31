package com.diro.ift2255.controller;

import com.diro.ift2255.model.Opinion;
import com.diro.ift2255.service.OpinionService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

/**
 * Controleur responsable de la gestion des avis sur les cours.
 * Il permet lajout et la consultation des avis associes aux cours.
 */
public class OpinionController {

    /**
     * Service utilise pour la gestion des avis.
     */
    private static final OpinionService service = new OpinionService();

    /**
     * Ajoute un avis pour un cours a partir des donnees fournies dans la requete.
     *
     * @param ctx contexte de la requete http
     * @throws IllegalArgumentException si les donnees fournies sont invalides
     */
    public static void addOpinion(Context ctx) {

        Opinion opinion = ctx.bodyAsClass(Opinion.class);

        try {
            service.addOpinion(opinion);
            ctx.status(201).json(Map.of("status", "ok"));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Recupere la liste des avis associes a un cours donne.
     *
     * @param ctx contexte de la requete http
     * @return la liste des avis associes au cours
     */
    public static void getOpinions(Context ctx) {

        String course = ctx.queryParam("course");

        List<Opinion> opinions =
                service.getOpinions(course);

        ctx.json(opinions);
    }

}
