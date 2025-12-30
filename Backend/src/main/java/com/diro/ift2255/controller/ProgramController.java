package com.diro.ift2255.controller;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.ProgramService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;



public class ProgramController {

    private static final ProgramService programService =
            new ProgramService();

    private static final CourseService courseService =
            new CourseService();   // ✅ MANQUAIT


    public static void getProgramCourses(Context ctx) {

        String programId = ctx.pathParam("id");

        // 🔒 VALIDATION PROGRAMME — ON SORT IMMÉDIATEMENT
        if (programId == null || !programId.trim().matches("^[0-9]{6}$")) {
            ctx.status(400).json(Map.of(
                    "error", "ID de programme invalide (6 chiffres requis)"
            ));
            return; // ⛔ RIEN APRÈS
        }

        // ⚠️ ON NE LIT LE SEMESTRE QU'APRÈS
        String semester = ctx.queryParam("semester");

        // 🔒 VALIDATION SEMESTRE (OPTIONNEL)
        if (semester != null) {
            semester = semester.trim().toUpperCase();
            if (!semester.matches("^[AHE][0-9]{2}$")) {
                ctx.status(400).json(Map.of(
                        "error", "Format de trimestre invalide (ex: H25, A24, E24)"
                ));
                return; // ⛔ STOP
            }
        }

        try {
            List<Course> courses;

            if (semester == null) {
                courses = programService.getCoursesForProgram(programId);
            } else {
                courses = courseService.getCoursesForSemesterAndProgram(
                        semester,
                        programId
                );
            }

            ctx.json(Map.of(
                    "programId", programId,
                    "semester", semester,
                    "courses", courses
            ));

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            ctx.status(500).json(Map.of("error", "Erreur interne"));
        }
    }



}
