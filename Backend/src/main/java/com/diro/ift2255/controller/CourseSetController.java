package com.diro.ift2255.controller;

import com.diro.ift2255.service.CourseSetService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class CourseSetController {

    private static final CourseSetService service =
            new CourseSetService();

    // POST /course-sets/schedule
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
