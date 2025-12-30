package com.diro.ift2255.controller;

import com.diro.ift2255.service.CourseComparisonService;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.OpinionService;
import com.diro.ift2255.service.ResultService;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CourseComparisonController {

    private static final CourseComparisonService service =
            new CourseComparisonService(
                    new CourseService(),
                    new OpinionService(),
                    new ResultService()   // ✅ PARAMÈTRE MANQUANT
            );

    // GET /compare-courses?courses=IFT2255,IFT2015
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
