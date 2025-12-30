package com.diro.ift2255.controller;

import com.diro.ift2255.model.Opinion;
import com.diro.ift2255.service.OpinionService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class OpinionController {

    private static final OpinionService service = new OpinionService();

    // POST /api/opinions
    public static void addOpinion(Context ctx) {

        Opinion opinion = ctx.bodyAsClass(Opinion.class);

        try {
            service.addOpinion(opinion);
            ctx.status(201).json(Map.of("status", "ok"));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/opinions?course_code=IFT2255
    public static void getOpinions(Context ctx) {

        String course = ctx.queryParam("course");

        List<Opinion> opinions =
                service.getOpinions(course);

        ctx.json(opinions);   // peut être [] → c’est voulu
    }

}
