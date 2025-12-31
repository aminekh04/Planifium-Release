package com.diro.ift2255.controller;

import com.diro.ift2255.service.CourseComparisonService;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.OpinionService;
import com.diro.ift2255.service.ResultService;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controleur responsable de la comparaison de plusieurs cours
 * Il gere les requetes permettant d analyser et comparer des cours
 */
public class CourseComparisonController {

    /**
     * Service utilise pour effectuer la comparaison des cours
     */
    private static final CourseComparisonService service =
            new CourseComparisonService(
                    new CourseService(),
                    new OpinionService(),
                    new ResultService()   // PARAMÈTRE MANQUANT
            );

    /**
     * Methode qui traite la requete de comparaison de cours
     * Les cours a comparer sont fournis via les parametres de la requete
     *
     * @param ctx contexte de la requete http
     */
    public static void compareCourses(Context ctx) {

        String param = ctx.queryParam("courses");

        if (param == null || param.isBlank()) {
            ctx.status(400).json(
                    Map.of("error", "Paramètre 'courses' requis")
            );
            return;
        }

        List<String> courses =
                Arrays.stream(param.split(","))
                        .map(String::trim)
                        .toList();

        try {
            ctx.json(service.compareCourses(courses));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(
                    Map.of("error", e.getMessage())
            );
        }
    }
}
