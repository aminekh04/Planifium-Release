package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseConflict;
import com.diro.ift2255.model.CourseSchedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseSetService {

    private static final int MAX_COURSES = 6;

    private final CourseScheduleService scheduleService;

    public CourseSetService() {
        this.scheduleService = new CourseScheduleService();
    }

    /**
     * Crée l'horaire résultant d'un ensemble de cours (max 6)
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

        // 1️⃣ Récupération des horaires pour chaque cours
        for (String courseId : courseIds) {

            List<CourseSchedule> schedules =
                    scheduleService.getCourseSchedule(courseId, semester);

            for (CourseSchedule s : schedules) {
                allSchedules.add(
                        new CourseScheduleWithCourse(courseId, s)
                );
            }
        }

        // ✅ DÉDUPLICATION ICI (LA CLÉ)
        List<CourseScheduleWithCourse> uniqueSchedules =
                allSchedules.stream()
                        .distinct()
                        .toList();

        // 2️⃣ Détection des conflits
        List<CourseConflict> conflicts =
                detectConflicts(uniqueSchedules);

        return new CourseSetResult(uniqueSchedules, conflicts);
    }



    // ============================
    // DÉTECTION DES CONFLITS (PROPRE)
    // ============================
    public List<CourseConflict> detectConflicts(
            List<CourseScheduleWithCourse> schedules
    ) {

        List<CourseConflict> conflicts = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (int i = 0; i < schedules.size(); i++) {
            for (int j = i + 1; j < schedules.size(); j++) {

                CourseScheduleWithCourse a = schedules.get(i);
                CourseScheduleWithCourse b = schedules.get(j);

                //  Ignore même cours
                if (a.getCourseId().equals(b.getCourseId())) continue;

                //  Jour différent
                if (!a.getDay().equals(b.getDay())) continue;

                LocalTime startA = LocalTime.parse(a.getStartTime());
                LocalTime endA   = LocalTime.parse(a.getEndTime());
                LocalTime startB = LocalTime.parse(b.getStartTime());
                LocalTime endB   = LocalTime.parse(b.getEndTime());

                boolean overlap =
                        startA.isBefore(endB) &&
                                startB.isBefore(endA);

                if (!overlap) continue;

                //  Clé d’unicité : 1 conflit / paire / jour
                String key =
                        a.getCourseId() + "|" +
                                b.getCourseId() + "|" +
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

    // ============================
    // UTILITAIRES
    // ============================
    private LocalTime max(LocalTime a, LocalTime b) {
        return a.isAfter(b) ? a : b;
    }

    private LocalTime min(LocalTime a, LocalTime b) {
        return a.isBefore(b) ? a : b;
    }
}
