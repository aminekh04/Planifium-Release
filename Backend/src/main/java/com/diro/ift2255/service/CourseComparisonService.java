package com.diro.ift2255.service;

import com.diro.ift2255.model.ComparedCourse;
import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.model.Opinion;

import java.util.List;

public class CourseComparisonService {

    private final CourseService courseService;
    private final OpinionService opinionService;
    private final ResultService resultService;

    public CourseComparisonService(
            CourseService courseService,
            OpinionService opinionService,
            ResultService resultService
    ) {
        this.courseService = courseService;
        this.opinionService = opinionService;
        this.resultService = resultService;
    }

    public List<ComparedCourse> compareCourses(List<String> courses) {

        if (courses == null || courses.size() != 2) {
            throw new IllegalArgumentException("Exactement 2 cours requis");
        }

        return courses.stream()
                .map(this::buildComparedCourse)
                .toList();
    }

    private ComparedCourse buildComparedCourse(String courseId) {

        CourseDetails details =
                courseService.getCourseDetails(courseId);

        List<Opinion> opinions =
                opinionService.getOpinions(courseId);

        int reviewCount = opinions.size();

        // charge de travail = AVIS
        int workload = estimateWorkload(opinions);

        //  difficulté = results.csv
        int difficulty =
                resultService.estimateDifficulty(courseId);

        return new ComparedCourse(
                details.getId(),
                details.getName(),
                details.getCredits(),
                reviewCount,
                workload,
                difficulty
        );
    }

    // ============================
    // 🔧 MÉTHODES MANQUANTES (ICI)
    // ============================

    private int estimateWorkload(List<Opinion> opinions) {
        if (opinions.isEmpty()) return 3;

        return (int) opinions.stream()
                .mapToInt(o -> extractWorkload(o.getText()))
                .average()
                .orElse(3);
    }

    private int extractWorkload(String text) {
        if (text == null) return 3;

        String t = text.toLowerCase();

        if (t.contains("énorme") || t.contains("beaucoup")) return 5;
        if (t.contains("lourd") || t.contains("chargé")) return 4;
        if (t.contains("moyen")) return 3;
        if (t.contains("léger") || t.contains("facile")) return 2;

        return 3;
    }
}
