package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.model.EligibilityResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EligibilityService {

    private final CourseService courseService;

    public EligibilityService(CourseService courseService) {
        this.courseService = courseService;
    }

    public EligibilityResult checkEligibility(
            String courseId,
            List<String> completedCourses,
            int cycle
    ) {

        CourseDetails course =
                courseService.getCourseDetails(courseId);

        //  LOGS DEBUG (ICI C’EST LE BON ENDROIT)
        System.out.println("===== ELIGIBILITY DEBUG =====");
        System.out.println("COURSE ID = " + courseId);
        System.out.println("PREREQS FROM PLANIFIUM = " + course.getPrerequisiteCourses());
        System.out.println("COMPLETED (RAW) = " + completedCourses);
        System.out.println("CYCLE = " + cycle);

        //  Normalisation des cours complétés
        List<String> completedNormalized =
                completedCourses.stream()
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

        System.out.println("COMPLETED (NORMALIZED) = " + completedNormalized);

        // 1️ Vérifier les prérequis
        List<String> missing = new ArrayList<>();

        for (String prereq : course.getPrerequisiteCourses()) {
            String prereqNormalized = prereq.trim().toUpperCase();

            if (!completedNormalized.contains(prereqNormalized)) {
                missing.add(prereq);
            }
        }

        System.out.println("MISSING PREREQS = " + missing);

        if (!missing.isEmpty()) {
            System.out.println(" RESULT = NOT ELIGIBLE (prerequisites)");
            return new EligibilityResult(
                    false,
                    missing,
                    "Prérequis manquants"
            );
        }

        // 2️⃣ Vérifier le cycle requis
        int requiredCycle = estimateRequiredCycle(courseId);

        System.out.println("REQUIRED CYCLE = " + requiredCycle);

        if (cycle < requiredCycle) {
            System.out.println(" RESULT = NOT ELIGIBLE (cycle)");
            return new EligibilityResult(
                    false,
                    List.of(),
                    "Cycle insuffisant"
            );
        }

        // 3️⃣ OK
        System.out.println(" RESULT = ELIGIBLE");
        return new EligibilityResult(
                true,
                List.of(),
                null
        );
    }

    // Heuristique simple et défendable
    private int estimateRequiredCycle(String courseId) {

        if (courseId.matches(".*[3-4][0-9]{3}$")) {
            return 3;
        }

        if (courseId.matches(".*2[0-9]{3}$")) {
            return 2;
        }

        return 1;
    }
}
