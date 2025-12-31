package com.diro.ift2255.controller;

import com.diro.ift2255.model.EligibilityResult;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.EligibilityService;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Controleur responsable de la verification de l admissibilite a un cours.
 * Il permet de determiner si un etudiant satisfait les prerequis necessaires.
 */
public class EligibilityController {

    /**
     * Service utilise pour acceder aux informations sur les cours.
     */
    private static final CourseService courseService =
            new CourseService();

    /**
     * Service utilise pour verifier l admissibilite a un cours.
     */
    private static final EligibilityService service =
            new EligibilityService(courseService);

    /**
     * Verifie l admissibilite a un cours en fonction des cours completes
     * et du cycle detudes fourni dans la requete.
     *
     * @param ctx contexte de la requete http
     * @return le resultat de la verification d admissibilite
     */
    public static void checkEligibility(Context ctx) {

        String course = ctx.queryParam("course");
        String completed = ctx.queryParam("completed");
        String cycleParam = ctx.queryParam("cycle");

        if (course == null || completed == null || cycleParam == null) {
            ctx.status(400).json(
                    java.util.Map.of(
                            "error",
                            "Paramètres requis : course, completed, cycle"
                    )
            );
            return;
        }

        int cycle = Integer.parseInt(cycleParam);

        List<String> completedCourses =
                Arrays.stream(completed.split(","))
                        .map(String::trim)
                        .toList();

        EligibilityResult result =
                service.checkEligibility(
                        course,
                        completedCourses,
                        cycle
                );

        ctx.json(result);
    }
}
