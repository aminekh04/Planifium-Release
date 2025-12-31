package com.diro.ift2255.controller;

import com.diro.ift2255.service.CourseScheduleService;
import io.javalin.http.Context;

import java.util.Map;

/**
 * Controleur responsable de la gestion des horaires de cours.
 * Il permet de recuperer l horaire dun cours pour un trimestre donne.
 */
public class CourseScheduleController {

    /**
     * Service utilise pour la recuperation des horaires de cours.
     */
    private static final CourseScheduleService service =
            new CourseScheduleService();

    /**
     * Recupere l horaire dun cours pour un trimestre specifique.
     *
     * @param ctx contexte de la requete http
     * @throws IllegalArgumentException si les parametres fournis sont invalides
     */
    public static void getCourseSchedule(Context ctx) {

        String courseId = ctx.pathParam("id");
        String semester = ctx.queryParam("semester");

        try {
            ctx.json(
                    service.getCourseSchedule(courseId, semester)
            );
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(
                    Map.of("error", e.getMessage())
            );
        } catch (RuntimeException e) {
            ctx.status(500).json(
                    Map.of("error", "Erreur récupération horaire")
            );
        }
    }
}
