package cli.menu;

import cli.api.ApiClient;

import java.util.Scanner;

/**
 * Menu interactif en ligne de commande pour la gestion des cours.
 * <p>
 * Cette classe offre une interface utilisateur textuelle permettant d'accéder
 * aux différentes fonctionnalités liées aux cours via l'API PlanifiumHelper.
 * <p>
 * Fonctionnalités disponibles :
 * <ul>
 *   <li>Consultation des détails d'un cours</li>
 *   <li>Visualisation des horaires</li>
 *   <li>Vérification d'éligibilité</li>
 *   <li>Consultation des résultats académiques</li>
 *   <li>Comparaison de deux cours</li>
 *   <li>Visualisation des avis étudiants</li>
 *   <li>Information pour poster un avis</li>
 * </ul>
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Scanner scanner = new Scanner(System.in);
 * MenuCours menu = new MenuCours(scanner);
 * menu.lancer(); // Lance le menu interactif
 * }
 * </pre>
 *
 * @see ApiClient
 * @see Scanner
 */
public class MenuCours {

    /**
     * Scanner pour la saisie utilisateur.
     */
    private final Scanner scanner;

    /**
     * Constructeur initialisant le menu avec un scanner.
     *
     * @param scanner Instance de Scanner pour la saisie utilisateur.
     */
    public MenuCours(Scanner scanner) {
        this.scanner = scanner;
    }

    // =========================
    // MENU PRINCIPAL
    // =========================

    /**
     * Lance le menu interactif principal.
     * <p>
     * Affiche le menu en boucle jusqu'à ce que l'utilisateur choisisse de quitter (option 0).
     * Chaque choix appelle la fonctionnalité correspondante.
     * </p>
     * <p>
     * Exemple de flux d'exécution :
     * </p>
     * <pre>
     * {@code
     * MenuCours menu = new MenuCours(scanner);
     * menu.lancer();
     * // Affiche :
     * // --- MENU COURS ---
     * // 1. Voir les détails d'un cours
     * // 2. Voir l'horaire d'un cours
     * // ... etc
     * }
     * </pre>
     */
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

    /**
     * Affiche le menu principal avec les options disponibles.
     * <p>
     * Les options affichées correspondent aux différentes fonctionnalités
     * offertes par l'application.
     * </p>
     * <p>
     * Sortie attendue :
     * </p>
     * <pre>
     * --- MENU COURS ---
     * 1. Voir les détails d'un cours
     * 2. Voir l'horaire d'un cours
     * 3. Vérifier l'éligibilité à un cours
     * 4. Voir les résultats académiques
     * 5. Comparer deux cours
     * 6. Voir les avis étudiants pour un cours donné
     * 7. Comment poster un avis
     * 0. Retour
     * Votre choix :
     * </pre>
     */
    private void afficherMenu() {
        System.out.println("\n--- MENU COURS ---");
        System.out.println("1. Voir les détails d'un cours");
        System.out.println("2. Voir l'horaire d'un cours");
        System.out.println("3. Vérifier l'éligibilité à un cours");
        System.out.println("4. Voir les résultats académiques");
        System.out.println("5. Comparer deux cours");
        System.out.println("6. Voir les avis étudiants pour un cours donné");
        System.out.println("7. Comment poster un avis");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
    }

    // =========================
    // FONCTIONNALITÉS
    // =========================

    /**
     * Affiche les détails complets d'un cours spécifique.
     * <p>
     * Demande à l'utilisateur le sigle d'un cours, puis récupère et affiche
     * ses informations détaillées via l'API.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses/{courseId}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : IFT2255
     * Sortie attendue :
     * === Détails du cours ===
     * {
     *   "id": "IFT2255",
     *   "name": "Développement de logiciel",
     *   "credits": 3,
     *   "description": "Cours d'introduction..."
     * }
     * </pre>
     */
    private void voirDetailsCours() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        try {
            ApiClient api = new ApiClient();
            String response = api.get("/courses/" + courseId);

            System.out.println("\n=== Détails du cours ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des détails du cours : " + e.getMessage());
        }
    }

    /**
     * Affiche l'horaire d'un cours pour un trimestre spécifique.
     * <p>
     * Demande le sigle du cours et le trimestre, puis récupère l'horaire
     * correspondant via l'API.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses/{courseId}/schedule?semester={semester}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur :
     * Sigle : IFT2015
     * Trimestre : A24
     * Sortie attendue :
     * === Horaire du cours ===
     * [
     *   {
     *     "section": "01",
     *     "activityType": "Cours",
     *     "day": "Lundi",
     *     "startTime": "10:00",
     *     "endTime": "11:30"
     *   }
     * ]
     * </pre>
     */
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
            System.out.println("Erreur lors de la récupération de l'horaire : " + e.getMessage());
        }
    }

    /**
     * Vérifie l'éligibilité d'un étudiant à s'inscrire à un cours.
     * <p>
     * Demande le sigle du cours, la liste des cours déjà complétés et le cycle
     * d'études, puis évalue l'éligibilité via l'API.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /eligibility?course={courseId}&completed={list}&cycle={cycle}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur :
     * Sigle : IFT2255
     * Cours complétés : IFT1025,IFT1065
     * Cycle : 3
     * Sortie attendue :
     * === Résultat éligibilité ===
     * {
     *   "eligible": true,
     *   "missingPrerequisites": [],
     *   "reason": null
     * }
     * </pre>
     */
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
            System.out.println("Erreur lors de la vérification de l'éligibilité : " + e.getMessage());
        }
    }

    /**
     * Affiche les résultats académiques agrégés d'un cours.
     * <p>
     * Demande le sigle d'un cours et récupère les statistiques académiques
     * historiques (moyennes, taux de réussite, etc.).
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /results/{courseId}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : IFT2255
     * Sortie attendue :
     * === Résultats académiques ===
     * {
     *   "courseId": "IFT2255",
     *   "average": 78.5,
     *   "successRate": 85.2,
     *   "totalStudents": 150
     * }
     * </pre>
     */
    private void voirResultatsAcademiques() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        try {
            ApiClient api = new ApiClient();
            String response = api.get("/results/" + courseId);

            System.out.println("\n=== Résultats académiques ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Aucun résultat académique disponible pour ce cours : " + e.getMessage());
        }
    }

    // =========================
    // MÉTHODES UTILITAIRES
    // =========================

    /**
     * Lit et valide un sigle de cours saisi par l'utilisateur.
     * <p>
     * Le sigle doit suivre le format standard UdeM :
     * 2-4 lettres suivies de 3-4 chiffres, optionnellement suivi d'une lettre.
     * Exemples valides : IFT2255, MAT1978, PHY1001A
     * </p>
     *
     * @return Le sigle validé en majuscules, ou {@code null} si le format est invalide.
     *
     * <p>Exemple :</p>
     * <pre>
     * Entrée utilisateur : ift2255
     * Retourne : "IFT2255"
     *
     * Entrée utilisateur : abc123
     * Retourne : null (affiche "Format de sigle invalide.")
     * </pre>
     */
    private String lireSigleCoursValide() {
        System.out.print("Sigle du cours (ex: IFT2255) : ");
        String courseId = scanner.nextLine().trim();

        // Validation du format UdeM standard
        if (!courseId.matches("^[A-Za-z]{2,4}[0-9]{3,4}[A-Za-z]?$")) {
            System.out.println("Format de sigle invalide. Format attendu : AAA#### ou AAA####A");
            return null;
        }

        return courseId.toUpperCase();
    }

    /**
     * Lit un entier saisi par l'utilisateur.
     *
     * @return L'entier saisi, ou -1 si la saisie n'est pas un entier valide.
     */
    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Compare deux cours selon différents critères académiques.
     * <p>
     * Demande deux sigles de cours différents et affiche une comparaison
     * détaillée incluant difficulté, charge de travail, etc.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /compare-courses?courses={course1},{course2}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur :
     * Premier cours : IFT2255
     * Deuxième cours : IFT2015
     * Sortie attendue :
     * === Comparaison des cours ===
     * {
     *   "course1": {...},
     *   "course2": {...},
     *   "comparison": {...}
     * }
     * </pre>
     */
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
            System.out.println("Erreur lors de la comparaison des cours : " + e.getMessage());
        }
    }

    /**
     * Affiche la légende pour interpréter les scores de comparaison.
     * <p>
     * Explique l'échelle de notation utilisée pour la difficulté et la charge
     * de travail dans les résultats de comparaison.
     * </p>
     */
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

    /**
     * Affiche les avis étudiants pour un cours donné.
     * <p>
     * Récupère et affiche les avis soumis par les étudiants pour le cours spécifié.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /api/opinions?course={courseId}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : IFT2255
     * Sortie attendue :
     * === Avis étudiants ===
     * [
     *   {"text": "Excellent cours, très formateur"},
     *   {"text": "Charge de travail importante"}
     * ]
     * </pre>
     */
    private void voirAvisEtudiants() {
        String courseId = lireSigleCoursValide();
        if (courseId == null) return;

        try {
            ApiClient api = new ApiClient();

            String response = api.get(
                    "/api/opinions?course=" + courseId
            );

            if (response == null || response.trim().equals("[]") || response.trim().equals("{}")) {
                System.out.println("\nAucun avis étudiant disponible pour ce cours.");
                return;
            }

            System.out.println("\n=== Avis étudiants ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Aucun avis étudiant disponible pour ce cours : " + e.getMessage());
        }
    }

    /**
     * Affiche les instructions pour poster un avis étudiant.
     * <p>
     * Explique comment soumettre un avis via le serveur Discord dédié.
     * Cette fonctionnalité utilise un bot Discord pour collecter les avis.
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Sortie attendue :
     * === Poster un avis étudiant ===
     * La soumission des avis se fait via Discord.
     *
     * 1. Rejoignez le serveur Discord :
     *    https://discord.gg/GrNFgtAXku
     *
     * 2. Allez dans le canal : #avis-cours
     *
     * 3. Écrivez un message du type :
     *    IFT2255 cours très chargé mais formateur
     * </pre>
     */
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
        System.out.println("L'avis sera automatiquement enregistré et");
        System.out.println("consultable via l'option \"Voir les avis étudiants\".");
    }
}