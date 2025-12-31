package com.diro.ift2255.service;

import com.diro.ift2255.model.ComparedCourse;
import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.model.Opinion;

import java.util.List;

/**
 * Service responsable de la comparaison de cours.
 * Il combine les informations descriptives les avis
 * et les resultats academiques pour produire une comparaison.
 */
public class CourseComparisonService {

    /**
     * Service utilise pour acceder aux details des cours.
     */
    private final CourseService courseService;

    /**
     * Service utilise pour acceder aux avis des cours.
     */
    private final OpinionService opinionService;

    /**
     * Service utilise pour acceder aux resultats academiques.
     */
    private final ResultService resultService;

    /**
     * Construit le service de comparaison de cours.
     *
     * @param courseService service des cours
     * @param opinionService service des avis
     * @param resultService service des resultats
     */
    public CourseComparisonService(
            CourseService courseService,
            OpinionService opinionService,
            ResultService resultService
    ) {
        this.courseService = courseService;
        this.opinionService = opinionService;
        this.resultService = resultService;
    }

    /**
     * Compare deux cours fournis par leur identifiant.
     *
     * @param courses liste contenant exactement deux cours
     * @return la liste des cours compares
     * @throws IllegalArgumentException si le nombre de cours est invalide
     */
    public List<ComparedCourse> compareCourses(List<String> courses) {

        if (courses == null || courses.size() != 2) {
            throw new IllegalArgumentException("Exactement 2 cours requis");
        }

        return courses.stream()
                .map(this::buildComparedCourse)
                .toList();
    }

    /**
     * Construit un objet de comparaison pour un cours donne.
     *
     * @param courseId identifiant du cours
     * @return le cours comparable construit
     */
    private ComparedCourse buildComparedCourse(String courseId) {

        CourseDetails details =
                courseService.getCourseDetails(courseId);

        List<Opinion> opinions =
                opinionService.getOpinions(courseId);

        int reviewCount = opinions.size();

        int workload = estimateWorkload(opinions);

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

    /**
     * Estime la charge de travail a partir des avis.
     *
     * @param opinions liste des avis
     * @return la charge de travail estimee
     */
    private int estimateWorkload(List<Opinion> opinions) {
        if (opinions.isEmpty()) return 3;

        return (int) opinions.stream()
                .mapToInt(o -> extractWorkload(o.getText()))
                .average()
                .orElse(3);
    }

    /**
     * Extrait une estimation de charge de travail a partir du texte.
     *
     * @param text texte de lavis
     * @return la charge de travail estimee
     */
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
