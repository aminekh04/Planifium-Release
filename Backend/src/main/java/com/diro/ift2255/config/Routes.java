package com.diro.ift2255.config;

import com.diro.ift2255.controller.*;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.ProgramService;
import io.javalin.Javalin;

/**
 * Classe responsable de la configuration des routes HTTP de l application.
 * Elle associe les points d acces REST aux controleurs correspondants
 * et configure la gestion globale des exceptions.
 */
public class Routes {

    /**
     * Configure l ensemble des routes de l application Javalin.
     *
     * @param app instance de l application Javalin utilisee pour enregistrer les routes
     */
    public static void configure(Javalin app) {
        app.get("/ping", ctx -> ctx.result("pong"));

        app.get("/courses-fake", CourseController::searchCoursesFake);
        app.get("/courses", CourseController::searchCourses);
        app.get("/courses-api-test", CourseController::coursesFromApiRaw);

        // Syntaxe correcte en Javalin 6
        app.get("/courses/{id}", CourseController::getCourseDetails);
        app.get("/results/{courseId}", ResultController::getResult);
        CourseService courseService = new CourseService();
        ProgramService programService = new ProgramService();
        app.get("/programs/{id}/courses", ProgramController::getProgramCourses);

        app.get(
                "/courses-by-semester-and-program",
                CourseController::getCoursesBySemesterAndProgram
        );
        app.get(
                "/courses/{id}/schedule",
                CourseScheduleController::getCourseSchedule
        );

        app.post("/api/opinions", OpinionController::addOpinion);
        app.get("/api/opinions", OpinionController::getOpinions);
        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500).json(java.util.Map.of(
                    "error", "Erreur interne",
                    "type", e.getClass().getSimpleName(),
                    "message", String.valueOf(e.getMessage())
            ));
        });
        app.post(
                "/course-sets/schedule",
                CourseSetController::createCourseSetSchedule
        );
        app.get("/compare-courses", CourseComparisonController::compareCourses);
        app.get("/eligibility", EligibilityController::checkEligibility);
    }

}
