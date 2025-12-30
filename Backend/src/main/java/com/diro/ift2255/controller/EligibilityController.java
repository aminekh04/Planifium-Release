package com.diro.ift2255.controller;

import com.diro.ift2255.model.EligibilityResult;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.EligibilityService;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;

public class EligibilityController {

    private static final CourseService courseService =
            new CourseService();

    private static final EligibilityService service =
            new EligibilityService(courseService);

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
