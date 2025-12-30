package com.diro.ift2255.controller;

import com.diro.ift2255.model.AcademicResult;
import com.diro.ift2255.service.ResultService;
import io.javalin.http.Context;

public class ResultController {

    private static final ResultService service = new ResultService();

    public static void getResult(Context ctx) {

        String courseId = ctx.pathParam("courseId");

        try {
            AcademicResult result = service.getResultForCourse(courseId);
            ctx.json(result);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erreur interne lors de la récupération des résultats.");
        }
    }
}
