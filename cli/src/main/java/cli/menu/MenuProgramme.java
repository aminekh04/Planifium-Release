package cli.menu;

import cli.api.ApiClient;
import java.util.Scanner;

public class MenuProgramme {

    private final Scanner scanner;

    public MenuProgramme(Scanner scanner) {
        this.scanner = scanner;
    }

    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();

            switch (choix) {
                case 1 -> afficherCoursProgramme();
                case 2 -> afficherCoursProgrammeParTrimestre();
                case 0 -> System.out.println("Retour menu principal");
                default -> System.out.println("Choix invalide");
            }

        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("\n--- Cours d’un programme ---");
        System.out.println("1. Tous les cours du programme");
        System.out.println("2. Cours du programme pour un trimestre");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
    }

    // OPTION 1
    private void afficherCoursProgramme() {
        System.out.print("ID du programme (ex: 117510) : ");
        String programId = scanner.nextLine();

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/programs/" + programId + "/courses");

            System.out.println("\nCours du programme " + programId + " :");
            System.out.println(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des cours du programme.");
        }
    }

    // ✅ OPTION 2 — PROGRAMME + TRIMESTRE
    private void afficherCoursProgrammeParTrimestre() {
        System.out.print("ID du programme (ex: 117510) : ");
        String programId = scanner.nextLine();

        System.out.print("Trimestre (ex: H25, A24, E24) : ");
        String semester = scanner.nextLine();

        try {
            ApiClient api = new ApiClient();
            String json = api.get(
                    "/programs/" + programId + "/courses?semester=" + semester
            );

            System.out.println("\nCours du programme " + programId
                    + " pour le trimestre " + semester + " :");
            System.out.println(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des cours pour ce trimestre.");
        }
    }

    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}
