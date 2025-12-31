package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseSchedule;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsable de la recuperation des horaires de cours.
 * Il interroge une api externe et transforme les donnees recues
 * en objets representant les plages horaires des cours.
 */
public class CourseScheduleService {

    /**
     * Adresse de base de l api externe utilisee.
     */
    private static final String BASE_URL =
            "https://planifium-api.onrender.com/api/v1";

    /**
     * Client http utilise pour effectuer les appels reseau.
     */
    private final HttpClientApi http;

    /**
     * Outil de deserialisation json.
     */
    private final ObjectMapper mapper;

    /**
     * Construit le service de recuperation des horaires de cours.
     */
    public CourseScheduleService() {
        this.http = new HttpClientApi();
        this.mapper = new ObjectMapper();
    }

    /**
     * Recupere l horaire dun cours pour un trimestre donne.
     *
     * @param courseId identifiant du cours
     * @param semester trimestre concerne
     * @return la liste des plages horaires du cours
     * @throws IllegalArgumentException si les parametres sont invalides
     * @throws RuntimeException si une erreur survient lors de la recuperation
     */
    public List<CourseSchedule> getCourseSchedule(
            String courseId,
            String semester
    ) {

        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("courseId requis");
        }

        if (semester == null || semester.isBlank()) {
            throw new IllegalArgumentException("semester requis");
        }

        String normalizedSemester = semester.trim().toUpperCase();

        if (!normalizedSemester.matches("^[AHE][0-9]{2}$")) {
            throw new IllegalArgumentException("Format de trimestre invalide");
        }

        try {
            String url = BASE_URL
                    + "/courses/"
                    + courseId
                    + "?include_schedule=true"
                    + "&schedule_semester="
                    + normalizedSemester;

            String json = http.get(url);
            JsonNode root = mapper.readTree(json);

            List<CourseSchedule> schedules = new ArrayList<>();

            JsonNode schedulesNode = root.path("schedules");
            if (!schedulesNode.isArray() || schedulesNode.isEmpty()) {
                return schedules;
            }

            JsonNode firstSchedule = schedulesNode.get(0);
            JsonNode sections = firstSchedule.path("sections");

            if (!sections.isArray()) {
                return schedules;
            }

            for (JsonNode section : sections) {

                String sectionName =
                        section.path("name").asText("");

                JsonNode volets = section.path("volets");
                if (!volets.isArray()) continue;

                for (JsonNode volet : volets) {

                    String activityType =
                            volet.path("name").asText("");

                    JsonNode activities = volet.path("activities");
                    if (!activities.isArray()) continue;

                    for (JsonNode activity : activities) {

                        JsonNode daysNode = activity.path("days");
                        if (!daysNode.isArray()) continue;

                        String start =
                                activity.path("start_time").asText("");

                        String end =
                                activity.path("end_time").asText("");

                        if (start.isBlank() || end.isBlank()) continue;

                        for (JsonNode dayNode : daysNode) {

                            String day = dayNode.asText("");

                            schedules.add(
                                    new CourseSchedule(
                                            sectionName,
                                            activityType,
                                            day,
                                            start,
                                            end
                                    )
                            );
                        }
                    }
                }
            }

            return schedules;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erreur récupération horaire", e);
        }

    }

}
