package com.diro.ift2255.controller;

import com.diro.ift2255.service.CourseScheduleService;
import io.javalin.http.Context;

import java.util.Map;

public class CourseScheduleController {

    private static final CourseScheduleService service =
            new CourseScheduleService();

    // GET /courses/{id}/schedule?semester=A24
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
