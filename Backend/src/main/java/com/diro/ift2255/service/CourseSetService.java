package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseConflict;
import com.diro.ift2255.model.CourseSchedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service responsable de la generation dhoraires a partir dun ensemble de cours.
 * Il permet dagreger les horaires et de detecter les conflits temporels.
 */
public class CourseSetService {

    /**
     * Nombre maximal de cours autorises dans un ensemble.
     */
    private static final int MAX_COURSES = 6;

    /**
     * Service utilise pour recuperer les horaires des cours.
     */
    private final CourseScheduleService scheduleService;

    /**
     * Construit le service de gestion des ensembles de cours.
     */
    public CourseSetService() {
        this.scheduleService = new CourseScheduleService();
    }

    /**
     * Cree un horaire a partir dun ensemble de cours pour un trimestre donne.
     *
     * @param courseIds liste des identifiants de cours
     * @param semester trimestre concerne
     * @return le resultat contenant les horaires et les conflits
     * @throws IllegalArgumentException si les parametres sont invalides
     */
    public CourseSetResult createCourseSetSchedule(
            List<String> courseIds,
            String semester
    ) {

        if (courseIds == null || courseIds.isEmpty()) {
            throw new IllegalArgumentException("Liste de cours requise");
        }

        if (courseIds.size() > MAX_COURSES) {
            throw new IllegalArgumentException("Maximum 6 cours");
        }

        List<CourseScheduleWithCourse> allSchedules = new ArrayList<>();

        for (String courseId : courseIds) {

            List<CourseSchedule> schedules =
                    scheduleService.getCourseSchedule(courseId, semester);

            for (CourseSchedule s : schedules) {
                allSchedules.add(
                        new CourseScheduleWithCourse(courseId, s)
                );
            }
        }

        List<CourseScheduleWithCourse> uniqueSchedules =
                allSchedules.stream()
                        .distinct()
                        .toList();

        List<CourseConflict> conflicts =
                detectConflicts(uniqueSchedules);

        return new CourseSetResult(uniqueSchedules, conflicts);
    }

    /**
     * Detecte les conflits dhoraires entre les cours.
     *
     * @param schedules liste des horaires a analyser
     * @return la liste des conflits detectes
     */
    public List<CourseConflict> detectConflicts(
            List<CourseScheduleWithCourse> schedules
    ) {

        List<CourseConflict> conflicts = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (int i = 0; i < schedules.size(); i++) {
            for (int j = i + 1; j < schedules.size(); j++) {

                CourseScheduleWithCourse a = schedules.get(i);
                CourseScheduleWithCourse b = schedules.get(j);

                if (a.getCourseId().equals(b.getCourseId())) continue;
                if (!a.getDay().equals(b.getDay())) continue;

                LocalTime startA = LocalTime.parse(a.getStartTime());
                LocalTime endA   = LocalTime.parse(a.getEndTime());
                LocalTime startB = LocalTime.parse(b.getStartTime());
                LocalTime endB   = LocalTime.parse(b.getEndTime());

                boolean overlap =
                        startA.isBefore(endB) &&
                                startB.isBefore(endA);

                if (!overlap) continue;

                String key =
                        a.getCourseId() +
                                b.getCourseId() +
                                a.getDay();

                if (seen.contains(key)) continue;
                seen.add(key);

                conflicts.add(
                        new CourseConflict(
                                a.getCourseId(),
                                b.getCourseId(),
                                a.getDay(),
                                max(startA, startB).toString(),
                                min(endA, endB).toString()
                        )
                );
            }
        }
        return conflicts;
    }

    /**
     * Retourne la valeur maximale entre deux heures.
     *
     * @param a premiere heure
     * @param b seconde heure
     * @return l heure la plus tardive
     */
    private LocalTime max(LocalTime a, LocalTime b) {
        return a.isAfter(b) ? a : b;
    }

    /**
     * Retourne la valeur minimale entre deux heures.
     *
     * @param a premiere heure
     * @param b seconde heure
     * @return l heure la plus tot
     */
    private LocalTime min(LocalTime a, LocalTime b) {
        return a.isBefore(b) ? a : b;
    }
}
