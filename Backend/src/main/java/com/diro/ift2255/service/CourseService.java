package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.model.CourseSchedule;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseService {

    private static final String BASE_URL = "https://planifium-api.onrender.com/api/v1";

    private final HttpClientApi http;
    private final ObjectMapper mapper;
    private final ProgramService programService;

    public CourseService() {
        this.http = new HttpClientApi();
        this.mapper = new ObjectMapper();
        this.programService = new ProgramService();
    }

    /**
     * Recherche de cours.
     * - si "sigles" non null → utilise courses_sigle=...
     * - sinon si "name" non null → utilise name=...
     */
    public List<Course> searchCourses(String sigles, String name) {

        if ((sigles == null || sigles.isBlank()) &&
                (name == null || name.isBlank())) {
            throw new IllegalArgumentException(
                    "Vous devez fournir 'sigles' ou 'name'."
            );
        }

        try {
            StringBuilder url =
                    new StringBuilder(BASE_URL + "/courses?response_level=min");

            if (sigles != null && !sigles.isBlank()) {
                url.append("&courses_sigle=")
                        .append(URLEncoder.encode(sigles, StandardCharsets.UTF_8));
            }

            if (name != null && !name.isBlank()) {
                url.append("&name=")
                        .append(URLEncoder.encode(name, StandardCharsets.UTF_8));
            }

            String json = http.get(url.toString());
            JsonNode root = mapper.readTree(json);

            List<Course> courses = new ArrayList<>();

            for (JsonNode node : root) {
                courses.add(new Course(
                        node.get("id").asText(),
                        node.get("name").asText(),
                        node.get("credits").asInt()
                ));
            }

            return courses;

        } catch (Exception e) {
            throw new RuntimeException("Erreur recherche cours", e);
        }
    }


    /**
     * Détails d'un cours : GET /courses/{course_id}
     */
    public CourseDetails getCourseDetails(String courseId) {
        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("courseId ne peut pas être vide.");
        }

        try {
            String url = BASE_URL + "/courses/" + courseId;

            String json = http.get(url);
            JsonNode node = mapper.readTree(json);

            // Champs principaux
            String id = node.hasNonNull("id") ? node.get("id").asText() : "";
            String name = node.hasNonNull("name") ? node.get("name").asText() : "";
            int credits = node.hasNonNull("credits") ? node.get("credits").asInt() : 0;
            String description = node.hasNonNull("description") ? node.get("description").asText() : "";

            // Prérequis (liste de sigles)
            List<String> prereqCourses = new ArrayList<>();
            if (node.hasNonNull("prerequisite_courses")) {
                for (JsonNode p : node.get("prerequisite_courses")) {
                    prereqCourses.add(p.asText());
                }
            }

            String requirementText = node.hasNonNull("requirement_text")
                    ? node.get("requirement_text").asText()
                    : "";

            // Dispo par trimestre (autumn / winter / summer)
            boolean autumn = false, winter = false, summer = false;
            if (node.hasNonNull("available_terms")) {
                JsonNode terms = node.get("available_terms");
                autumn = terms.hasNonNull("autumn") && terms.get("autumn").asBoolean();
                winter = terms.hasNonNull("winter") && terms.get("winter").asBoolean();
                summer = terms.hasNonNull("summer") && terms.get("summer").asBoolean();
            }

            // Dispo jour / soir
            boolean day = false, night = false;
            if (node.hasNonNull("available_periods")) {
                JsonNode periods = node.get("available_periods");
                day = periods.hasNonNull("day") && periods.get("day").asBoolean();
                night = periods.hasNonNull("night") && periods.get("night").asBoolean();
            }

            String udemWebsite = node.hasNonNull("udem_website")
                    ? node.get("udem_website").asText()
                    : "";

            return new CourseDetails(
                    id,
                    name,
                    credits,
                    description,
                    prereqCourses,
                    requirementText,
                    autumn,
                    winter,
                    summer,
                    day,
                    night,
                    udemWebsite
            );

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des détails du cours " + courseId, e);
        }
    }

    public List<Course> searchCoursesAdvanced(
            String siglePartial,
            String name,
            String description
    ) {

        if ((siglePartial == null || siglePartial.isBlank())
                && (name == null || name.isBlank())
                && (description == null || description.isBlank())) {
            throw new IllegalArgumentException(
                    "Au moins un critère est requis"
            );
        }

        try {
            StringBuilder url =
                    new StringBuilder(BASE_URL + "/courses?response_level=min");

            if (name != null && !name.isBlank()) {
                url.append("&name=")
                        .append(URLEncoder.encode(name, StandardCharsets.UTF_8));
            }

            if (description != null && !description.isBlank()) {
                url.append("&description=")
                        .append(URLEncoder.encode(description, StandardCharsets.UTF_8));
            }

            String json = http.get(url.toString());
            JsonNode root = mapper.readTree(json);

            List<Course> result = new ArrayList<>();

            for (JsonNode node : root) {
                String id = node.get("id").asText();

                if (siglePartial != null &&
                        !id.startsWith(siglePartial.toUpperCase())) {
                    continue;
                }

                result.add(new Course(
                        id,
                        node.get("name").asText(),
                        node.get("credits").asInt()
                ));
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Erreur recherche avancée", e);
        }
    }
    private void validateSemesterFormat(String semester) {

        if (semester == null || semester.isBlank()) {
            throw new IllegalArgumentException(
                    "semester requis (ex: H25, A24, E24)"
            );
        }

        if (!semester.trim().toUpperCase().matches("^[AHE][0-9]{2}$")) {
            throw new IllegalArgumentException(
                    "Format de trimestre invalide (ex: H25, A24, E24)"
            );
        }
    }

    private void validateProgramId(String programId) {

        if (programId == null || programId.isBlank()) {
            throw new IllegalArgumentException("programId requis");
        }

        // À l’UdeM : ID programme = 6 chiffres
        if (!programId.matches("^[0-9]{6}$")) {
            throw new IllegalArgumentException(
                    "Format de programme invalide (ex: 117510)"
            );
        }
    }






    public List<Course> getCoursesForSemesterAndProgram(
            String semester,
            String programId
    ) {

        validateProgramId(programId);        // ✅ ICI
        validateSemesterFormat(semester);    // ✅ ICI

        List<Course> programCourses =
                programService.getCoursesForProgram(programId.trim());

        // ⚠️ programme inexistant → ERREUR
        if (programCourses.isEmpty()) {
            throw new IllegalArgumentException(
                    "Programme inexistant : " + programId
            );
        }

        String normalizedSemester = semester.trim().toUpperCase();

        return programCourses.stream()
                .filter(c -> {
                    CourseDetails d = getCourseDetails(c.getId());
                    return isOfferedThisSemester(d, normalizedSemester);
                })
                .toList();
    }









    private boolean isOfferedThisSemester(CourseDetails d, String semester) {

        if (semester == null || semester.length() < 1) {
            throw new IllegalArgumentException("Trimestre invalide");
        }

        char season = Character.toUpperCase(semester.charAt(0));

        return switch (season) {
            case 'H' -> d.isAvailableWinter();
            case 'A' -> d.isAvailableAutumn();
            case 'E' -> d.isAvailableSummer();
            default  -> false;
        };
    }









}
