package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.model.EligibilityResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsable de la verification de ladmissibilite a un cours.
 * Il analyse les prerequis et le cycle detudes afin de determiner
 * si un etudiant peut suivre un cours donne.
 */
public class EligibilityService {

    /**
     * Service utilise pour acceder aux details des cours.
     */
    private final CourseService courseService;

    /**
     * Construit le service de verification de ladmissibilite.
     *
     * @param courseService service des cours
     */
    public EligibilityService(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Verifie ladmissibilite a un cours en fonction des cours completes
     * et du cycle detudes fourni.
     *
     * @param courseId identifiant du cours
     * @param completedCourses liste des cours completes
     * @param cycle cycle detudes
     * @return le resultat de la verification dadmissibilite
     */
    public EligibilityResult checkEligibility(
            String courseId,
            List<String> completedCourses,
            int cycle
    ) {

        CourseDetails course =
                courseService.getCourseDetails(courseId);

        List<String> completedNormalized =
                completedCourses.stream()
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

        List<String> missing = new ArrayList<>();

        for (String prereq : course.getPrerequisiteCourses()) {
            String prereqNormalized = prereq.trim().toUpperCase();

            if (!completedNormalized.contains(prereqNormalized)) {
                missing.add(prereq);
            }
        }

        if (!missing.isEmpty()) {
            return new EligibilityResult(
                    false,
                    missing,
                    "Prerequis manquants"
            );
        }

        int requiredCycle = estimateRequiredCycle(courseId);

        if (cycle < requiredCycle) {
            return new EligibilityResult(
                    false,
                    List.of(),
                    "Cycle insuffisant"
            );
        }

        return new EligibilityResult(
                true,
                List.of(),
                null
        );
    }

    /**
     * Estime le cycle minimal requis pour suivre un cours.
     *
     * @param courseId identifiant du cours
     * @return le cycle requis
     */
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
