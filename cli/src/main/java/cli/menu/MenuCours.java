package cli.menu;

import cli.api.ApiClient;

import java.util.Scanner;

public class MenuCours {

    private final Scanner scanner;

    public MenuCours(Scanner scanner) {
        this.scanner = scanner;
    }

    // =========================
    // MENU
    // =========================
    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();

            switch (choix) {
                case 1 -> voirDetailsCours();
                case 2 -> voirHoraireCours();
                case 3 -> verifierEligibilite();
                case 4 -> voirResultatsAcademiques();
                case 5 -> comparerDeuxCours();
                case 6 -> voirAvisEtudiants();
                case 7 -> afficherCommentPosterAvis();


                case 0 -> System.out.println("Retour au menu principal");
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("\n--- MENU COURS ---");
        System.out.println("1. Voir les détails d’un cours");
        System.out.println("2. Voir l’horaire d’un cours");
        System.out.println("3. Vérifier l’éligibilité à un cours");
        System.out.println("4. Voir les résultats académiques");
        System.out.println("5. Comparer deux cours");
        System.out.println("6. Voir les avis étudiants pour un cour donne");
        System.out.println("7. Poster un avis ");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
    }

    // =========================
    // FONCTIONNALITÉS
    // =========================

    private void voirDetailsCours() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        try {
            ApiClient api = new ApiClient();
            String response = api.get("/courses/" + courseId);

            System.out.println("\n=== Détails du cours ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des détails du cours.");
        }
    }

    private void voirHoraireCours() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        System.out.print("Trimestre (ex: H25, A24, E24) : ");
        String semester = scanner.nextLine().trim();

        try {
            ApiClient api = new ApiClient();
            String response = api.get(
                    "/courses/" + courseId + "/schedule?semester=" + semester
            );

            System.out.println("\n=== Horaire du cours ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de l’horaire.");
        }
    }

    private void verifierEligibilite() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        System.out.print("Cours complétés (ex: IFT1025,IFT2015 ou vide) : ");
        String completed = scanner.nextLine().trim();

        System.out.print("Cycle (1, 2 ou 3) : ");
        String cycle = scanner.nextLine().trim();

        try {
            ApiClient api = new ApiClient();

            String response = api.get(
                    "/eligibility"
                            + "?course=" + courseId
                            + "&completed=" + completed
                            + "&cycle=" + cycle
            );

            System.out.println("\n=== Résultat éligibilité ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification de l’éligibilité.");
        }
    }

    private void voirResultatsAcademiques() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        try {
            ApiClient api = new ApiClient();
            String response = api.get("/results/" + courseId);

            System.out.println("\n=== Résultats académiques ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Aucun résultat académique disponible pour ce cours.");
        }
    }

    // =========================
    // MÉTHODES UTILITAIRES
    // =========================

    private String lireSigleCoursValide() {
        System.out.print("Sigle du cours (ex: IFT2255) : ");
        String courseId = scanner.nextLine().trim();

        // Validation légère mais réaliste UdeM
        if (!courseId.matches("^[A-Za-z]{2,4}[0-9]{3,4}[A-Za-z]?$")) {
            System.out.println("Format de sigle invalide.");
            return null;
        }

        return courseId.toUpperCase();
    }

    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
    private void comparerDeuxCours() {

        System.out.println("Premier cours à comparer :");
        String course1 = lireSigleCoursValide();
        if (course1 == null) return;

        System.out.println("Deuxième cours à comparer :");
        String course2 = lireSigleCoursValide();
        if (course2 == null) return;

        if (course1.equals(course2)) {
            System.out.println("Veuillez choisir deux cours différents.");
            return;
        }

        try {
            ApiClient api = new ApiClient();

            String response = api.get(
                    "/compare-courses?courses=" + course1 + "," + course2

            );

            System.out.println("\n=== Comparaison des cours ===");
            System.out.println(response);
            afficherLegendeComparaison();

        } catch (Exception e) {
            System.out.println("Erreur lors de la comparaison des cours.");
        }
    }
    private void afficherLegendeComparaison() {
        System.out.println("\n--- Légende ---");
        System.out.println("Difficulté (1 à 5) :");
        System.out.println("  1 = Très facile");
        System.out.println("  2 = Facile");
        System.out.println("  3 = Moyenne");
        System.out.println("  4 = Difficile");
        System.out.println("  5 = Très difficile");

        System.out.println("\nCharge de travail (1 à 5) :");
        System.out.println("  1 = Très légère");
        System.out.println("  2 = Légère");
        System.out.println("  3 = Moyenne");
        System.out.println("  4 = Lourde");
        System.out.println("  5 = Très lourde");
    }
    private void voirAvisEtudiants() {

        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        try {
            ApiClient api = new ApiClient();

            String response = api.get(
                    "/api/opinions?course=" + courseId
            );

            if (response == null || response.trim().equals("[]")) {
                System.out.println("\nAucun avis étudiant disponible pour ce cours.");
                return;
            }

            System.out.println("\n=== Avis étudiants ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Aucun avis étudiant disponible pour ce cours.");
        }}
        private void afficherCommentPosterAvis() {

            System.out.println("\n=== Poster un avis étudiant ===");
            System.out.println("La soumission des avis se fait via Discord.");
            System.out.println();
            System.out.println("1. Rejoignez le serveur Discord :");
            System.out.println("   https://discord.gg/GrNFgtAXku");
            System.out.println();
            System.out.println("2. Allez dans le canal : #avis-cours");
            System.out.println();
            System.out.println("3. Écrivez un message du type :");
            System.out.println("   IFT2255 cours très chargé mais formateur");
            System.out.println();
            System.out.println("L’avis sera automatiquement enregistré et");
            System.out.println("consultable via l’option \"Voir les avis étudiants d'un cours donne\".");
        }

    }





