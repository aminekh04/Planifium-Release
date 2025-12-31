package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service responsable de la recuperation des cours associes a un programme.
 * Il interroge une api externe et filtre les cours appartenant a un programme donne.
 */
public class ProgramService {

    /**
     * Adresse de base de l api externe utilisee.
     */
    private static final String BASE_URL =
            "https://planifium-api.onrender.com/api/v1";

    /**
     * Client http utilise pour effectuer les appels reseau.
     */
    private final HttpClientApi http = new HttpClientApi();

    /**
     * Outil de traitement des donnees json.
     */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Recupere la liste des cours associes a un programme.
     *
     * @param programId identifiant du programme
     * @return la liste des cours du programme
     * @throws IllegalArgumentException si l identifiant est invalide
     * @throws RuntimeException si une erreur survient lors de la recuperation
     */
    public List<Course> getCoursesForProgram(String programId) {

        if (programId == null || programId.isBlank()) {
            throw new IllegalArgumentException("programId requis");
        }

        try {
            String url =
                    BASE_URL
                            + "/programs?programs_list="
                            + programId
                            + "&include_courses_detail=true";

            String json = http.get(url);
            JsonNode root = mapper.readTree(json);

            JsonNode programs = root.get("programs");
            if (programs == null || programs.isEmpty()) {
                return List.of();
            }

            Set<String> programCourseIds = new HashSet<>();
            for (JsonNode c : programs.get(0).get("courses")) {
                programCourseIds.add(c.asText());
            }

            JsonNode allCourses = root.get("courses");
            if (allCourses == null) {
                return List.of();
            }

            List<Course> result = new ArrayList<>();

            for (JsonNode c : allCourses) {
                String id = c.get("_id").asText();

                if (programCourseIds.contains(id)) {
                    String name = c.get("name").asText();
                    int credits = c.get("credits").asInt();
                    result.add(new Course(id, name, credits));
                }
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erreur recuperation cours du programme " + programId,
                    e
            );
        }
    }
}
