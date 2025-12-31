package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.CourseDetails;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsable de la gestion des cours.
 * Il permet la recherche la consultation des details
 * et le filtrage des cours selon differents criteres.
 */
public class CourseService {

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
     * Outil de traitement des donnees json.
     */
    private final ObjectMapper mapper;

    /**
     * Service utilise pour acceder aux programmes.
     */
    private final ProgramService programService;

    /**
     * Construit le service de gestion des cours.
     */
    public CourseService() {
        this.http = new HttpClientApi();
        this.mapper = new ObjectMapper();
        this.programService = new ProgramService();
    }

    /**
     * Effectue une recherche de cours a partir de criteres simples.
     *
     * @param sigles identifiants de cours
     * @param name nom du cours
     * @return la liste des cours trouves
     * @throws IllegalArgumentException si aucun critere nest fourni
     */
    public List<Course> searchCourses(String sigles, String name) {

        if ((sigles == null || sigles.isBlank()) &&
                (name == null || name.isBlank())) {
            throw new IllegalArgumentException(
                    "Vous devez fournir sigles ou name"
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
     * Recupere les details complets dun cours.
     *
     * @param courseId identifiant du cours
     * @return les details du cours
     * @throws IllegalArgumentException si l identifiant est invalide
     */
    public CourseDetails getCourseDetails(String courseId) {

        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("courseId ne peut pas etre vide");
        }

        try {
            String url = BASE_URL + "/courses/" + courseId;

            String json = http.get(url);
            JsonNode node = mapper.readTree(json);

            String id = node.hasNonNull("id") ? node.get("id").asText() : "";
            String name = node.hasNonNull("name") ? node.get("name").asText() : "";
            int credits = node.hasNonNull("credits") ? node.get("credits").asInt() : 0;
            String description = node.hasNonNull("description") ? node.get("description").asText() : "";

            List<String> prereqCourses = new ArrayList<>();
            if (node.hasNonNull("prerequisite_courses")) {
                for (JsonNode p : node.get("prerequisite_courses")) {
                    prereqCourses.add(p.asText());
                }
            }

            String requirementText = node.hasNonNull("requirement_text")
                    ? node.get("requirement_text").asText()
                    : "";

            boolean autumn = false;
            boolean winter = false;
            boolean summer = false;
            if (node.hasNonNull("available_terms")) {
                JsonNode terms = node.get("available_terms");
                autumn = terms.hasNonNull("autumn") && terms.get("autumn").asBoolean();
                winter = terms.hasNonNull("winter") && terms.get("winter").asBoolean();
                summer = terms.hasNonNull("summer") && terms.get("summer").asBoolean();
            }

            boolean day = false;
            boolean night = false;
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
            throw new RuntimeException(
                    "Erreur lors de la recuperation des details du cours " + courseId,
                    e
            );
        }
    }

    /**
     * Effectue une recherche avancee de cours.
     *
     * @param siglePartial debut du sigle du cours
     * @param name nom du cours
     * @param description description du cours
     * @return la liste des cours trouves
     * @throws IllegalArgumentException si aucun critere nest fourni
     */
    public List<Course> searchCoursesAdvanced(
            String siglePartial,
            String name,
            String description
    ) {

        if ((siglePartial == null || siglePartial.isBlank())
                && (name == null || name.isBlank())
                && (description == null || description.isBlank())) {
            throw new IllegalArgumentException(
                    "Au moins un critere est requis"
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
            throw new RuntimeException("Erreur recherche avancee", e);
        }
    }

    /**
     * Recupere les cours offerts pour un trimestre et un programme.
     *
     * @param semester trimestre concerne
     * @param programId identifiant du programme
     * @return la liste des cours offerts
     */
    public List<Course> getCoursesForSemesterAndProgram(
            String semester,
            String programId
    ) {

        validateProgramId(programId);
        validateSemesterFormat(semester);

        List<Course> programCourses =
                programService.getCoursesForProgram(programId.trim());

        if (programCourses.isEmpty()) {
            throw new IllegalArgumentException(
                    "Programme inexistant " + programId
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

    /**
     * Valide le format du trimestre.
     *
     * @param semester trimestre a valider
     */
    private void validateSemesterFormat(String semester) {

        if (semester == null || semester.isBlank()) {
            throw new IllegalArgumentException("semester requis");
        }

        if (!semester.trim().toUpperCase().matches("^[AHE][0-9]{2}$")) {
            throw new IllegalArgumentException(
                    "Format de trimestre invalide"
            );
        }
    }

    /**
     * Valide l identifiant du programme.
     *
     * @param programId identifiant du programme
     */
    private void validateProgramId(String programId) {

        if (programId == null || programId.isBlank()) {
            throw new IllegalArgumentException("programId requis");
        }

        if (!programId.matches("^[0-9]{6}$")) {
            throw new IllegalArgumentException(
                    "Format de programme invalide"
            );
        }
    }

    /**
     * Indique si un cours est offert pour un trimestre donne.
     *
     * @param d details du cours
     * @param semester trimestre concerne
     * @return vrai si le cours est offert
     */
    private boolean isOfferedThisSemester(CourseDetails d, String semester) {

        if (semester == null || semester.length() < 1) {
            throw new IllegalArgumentException("Trimestre invalide");
        }

        char season = Character.toUpperCase(semester.charAt(0));

        return switch (season) {
            case 'H' -> d.isAvailableWinter();
            case 'A' -> d.isAvailableAutumn();
            case 'E' -> d.isAvailableSummer();
            default -> false;
        };
    }

}
