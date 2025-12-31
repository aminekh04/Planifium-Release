package com.diro.ift2255.controller;

import com.diro.ift2255.service.CourseSetService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

/**
 * Controleur responsable de la gestion des ensembles de cours.
 * Il permet de generer un horaire a partir dun groupe de cours.
 */
public class CourseSetController {

    /**
     * Service utilise pour la creation des horaires a partir dun ensemble de cours.
     */
    private static final CourseSetService service =
            new CourseSetService();

    /**
     * Cree un horaire a partir dun ensemble de cours et dun trimestre.
     *
     * @param ctx contexte de la requete http
     * @throws IllegalArgumentException si les donnees fournies sont invalides
     */
    public static void createCourseSetSchedule(Context ctx) {

        CourseSetRequest request =
                ctx.bodyAsClass(CourseSetRequest.class);

        try {
            ctx.json(
                    service.createCourseSetSchedule(
                            request.getCourses(),
                            request.getSemester()
                    )
            );
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(
                    Map.of("error", e.getMessage())
            );
        } catch (RuntimeException e) {
            ctx.status(500).json(
                    Map.of("error", "Erreur création horaire")
            );
        }
    }
}
