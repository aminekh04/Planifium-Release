package cli.menu;

import cli.api.ApiClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEnsembleCours {

    private final Scanner scanner;
    private final List<String> courses = new ArrayList<>();

    public MenuEnsembleCours(Scanner scanner) {
        this.scanner = scanner;
    }

    public void lancer() {

        System.out.print("Trimestre (H25, A24, E24) : ");
        String semester = scanner.nextLine().trim().toUpperCase();

        if (!semester.matches("^[AHE][0-9]{2}$")) {
            System.out.println("Format de trimestre invalide.");
            return;
        }

        while (true) {
            System.out.println("\nCours sélectionnés : " + courses);
            System.out.println("1. Ajouter un cours");
            System.out.println("2. Générer l'horaire");
            System.out.println("0. Retour");
            System.out.print("Choix : ");

            int choix = lireEntier();

            if (choix == 1) {

                if (courses.size() >= 6) {
                    System.out.println("Maximum de 6 cours atteint.");
                    continue;
                }

                System.out.print("Sigle du cours : ");
                String courseId = scanner.nextLine().trim().toUpperCase();

                if (!courseExiste(courseId)) {
                    System.out.println("Cours inexistant : " + courseId);
                    continue;
                }

                courses.add(courseId);


            } else if (choix == 2) {
                genererHoraire(semester);
                return;

            } else if (choix == 0) {
                return;

            } else {
                System.out.println("Choix invalide.");
            }
        }
    }

    private void genererHoraire(String semester) {

        if (courses.isEmpty()) {
            System.out.println("Aucun cours sélectionné.");
            return;
        }

        try {
            ApiClient api = new ApiClient();

            String coursesJson = courses.stream()
                    .map(c -> "\"" + c + "\"")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            String jsonBody = """
                {
                  "semester": "%s",
                  "courses": [%s]
                }
                """.formatted(semester, coursesJson);

            String response = api.post(
                    "/course-sets/schedule",
                    jsonBody
            );

            System.out.println("\n=== Horaire global ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération de l'horaire.");
            e.printStackTrace(); // TEMPORAIRE POUR DEBUG
        }
    }


    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
    private boolean courseExiste(String courseId) {
        try {
            ApiClient api = new ApiClient();
            api.get("/courses/" + courseId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
