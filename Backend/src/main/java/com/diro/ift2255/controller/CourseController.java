package com.diro.ift2255.controller;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.ProgramService;
import com.diro.ift2255.util.HttpClientApi;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

/**
 * Controleur responsable de la gestion des cours.
 * Il expose des points d acces permettant la recherche
 * et la consultation des informations de cours.
 */
public class CourseController {

    /**
     * Service utilise pour les operations liees aux cours.
     */
    private static final CourseService courseService = new CourseService();

    /**
     * Service utilise pour les operations liees aux programmes.
     */
    private static final ProgramService programService =
            new ProgramService();

    /**
     * Fournit une liste de cours fictifs a des fins de test.
     *
     * @param ctx contexte de la requete http
     */
    public static void searchCoursesFake(Context ctx) {
        List<Course> courses = List.of(
                new Course("IFT2255", "Génie logiciel", 3),
                new Course("IFT2015", "Structures de données", 3)
        );
        ctx.json(courses);
    }

    /**
     * Effectue une recherche de cours selon les criteres fournis
     * dans les parametres de la requete.
     *
     * @param ctx contexte de la requete http
     */
    public static void searchCourses(Context ctx) {

        String siglePartial = ctx.queryParam("sigle");
        String name = ctx.queryParam("name");
        String description = ctx.queryParam("description");

        try {
            List<Course> courses =
                    courseService.searchCoursesAdvanced(
                            siglePartial,
                            name,
                            description
                    );
            ctx.json(courses);

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(
                    Map.of("error", e.getMessage())
            );
        } catch (RuntimeException e) {
            ctx.status(500).json(
                    Map.of("error", "Erreur interne lors de la recherche de cours")
            );
        }
    }

    /**
     * Recupere les donnees brutes des cours depuis une api externe.
     *
     * @param ctx contexte de la requete http
     */
    public static void coursesFromApiRaw(Context ctx) {

        HttpClientApi http = new HttpClientApi();

        String url = "https://planifium-api.onrender.com/api/v1/courses"
                + "?courses_sigle=IFT2255,IFT2015"
                + "&response_level=min";

        String body = http.get(url);

        ctx.result(body).contentType("application/json");
    }

    /**
     * Recupere les details complets dun cours a partir de son identifiant.
     *
     * @param ctx contexte de la requete http
     * @throws IllegalArgumentException si le sigle du cours est invalide
     */
    public static void getCourseDetails(Context ctx) {

        String id = ctx.pathParam("id");

        if (id == null || !id.matches("[A-Za-z]{3,}[0-9]{0,4}")) {
            ctx.status(400).json(
                    Map.of("error", "Sigle de cours invalide (ex: IFT2255)")
            );
            return;
        }

        try {
            CourseDetails details =
                    courseService.getCourseDetails(id.toUpperCase());
            ctx.json(details);

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(
                    Map.of("error", e.getMessage())
            );
        } catch (RuntimeException e) {
            ctx.status(500).json(
                    Map.of("error", "Erreur interne lors de la récupération du cours")
            );
        }
    }

    /**
     * Recupere les cours offerts pour un trimestre et un programme donnes.
     *
     * @param ctx contexte de la requete http
     */
    public static void getCoursesBySemesterAndProgram(Context ctx) {

        String semester = ctx.queryParam("semester");
        String program = ctx.queryParam("program");

        try {
            List<Course> courses =
                    courseService.getCoursesForSemesterAndProgram(
                            semester,
                            program
                    );
            ctx.json(courses);

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(
                    Map.of("error", e.getMessage())
            );
        }
    }

}
