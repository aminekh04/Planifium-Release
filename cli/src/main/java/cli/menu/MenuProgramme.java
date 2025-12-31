package cli.menu;

import cli.api.ApiClient;
import java.util.Scanner;

/**
 * Menu interactif pour la consultation des cours d'un programme d'études.
 * <p>
 * Cette classe permet aux utilisateurs de consulter les cours associés à un
 * programme d'études spécifique, soit pour l'ensemble du programme, soit pour
 * un trimestre particulier.
 * <p>
 * Fonctionnalités disponibles :
 * <ul>
 *   <li>Consultation de tous les cours d'un programme</li>
 *   <li>Consultation des cours d'un programme pour un trimestre spécifique</li>
 *   <li>Validation des identifiants de programme</li>
 * </ul>
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Scanner scanner = new Scanner(System.in);
 * MenuProgramme menu = new MenuProgramme(scanner);
 * menu.lancer(); // Lance le menu interactif
 * }
 * </pre>
 *
 * @see ApiClient
 * @see Scanner
 */
public class MenuProgramme {

    /**
     * Scanner pour la saisie utilisateur.
     */
    private final Scanner scanner;

    /**
     * Constructeur initialisant le menu avec un scanner.
     *
     * @param scanner Instance de Scanner pour la saisie utilisateur.
     */
    public MenuProgramme(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Lance le menu interactif principal.
     * <p>
     * Affiche le menu en boucle jusqu'à ce que l'utilisateur choisisse de quitter (option 0).
     * Chaque choix appelle la fonctionnalité correspondante pour consulter les cours
     * d'un programme.
     * </p>
     * <p>
     * Exemple de flux d'exécution :
     * </p>
     * <pre>
     * // Exemple de flux d'exécution
     * MenuProgramme menu = new MenuProgramme(scanner);
     * menu.lancer();
     * // Affiche :
     * // --- Cours d'un programme ---
     * // 1. Tous les cours du programme
     * // 2. Cours du programme pour un trimestre
     * // 0. Retour
     * // Votre choix :
     * </pre>
     */
    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();

            switch (choix) {
                case 1 -> afficherCoursProgramme();
                case 2 -> afficherCoursProgrammeParTrimestre();
                case 0 -> System.out.println("Retour au menu principal");
                default -> System.out.println("Choix invalide");
            }

        } while (choix != 0);
    }

    /**
     * Affiche le menu principal avec les options disponibles.
     * <p>
     * Les options permettent de consulter les cours d'un programme selon
     * différentes perspectives.
     * </p>
     * <p>
     * Sortie attendue :
     * </p>
     * <pre>
     * --- Cours d'un programme ---
     * 1. Tous les cours du programme
     * 2. Cours du programme pour un trimestre
     * 0. Retour
     * Votre choix :
     * </pre>
     */
    private void afficherMenu() {
        System.out.println("\n--- Cours d'un programme ---");
        System.out.println("1. Tous les cours du programme");
        System.out.println("2. Cours du programme pour un trimestre");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
    }

    /**
     * Affiche tous les cours associés à un programme d'études.
     * <p>
     * Demande à l'utilisateur l'identifiant du programme, puis récupère et affiche
     * la liste complète des cours qui y sont associés via l'API.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /programs/{programId}/courses}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : 117510 (Baccalauréat en informatique)
     * Sortie attendue :
     * Cours du programme 117510 :
     * [
     *   {
     *     "id": "IFT2255",
     *     "name": "Développement de logiciel",
     *     "credits": 3
     *   },
     *   {
     *     "id": "IFT2015",
     *     "name": "Structures de données et algorithmes",
     *     "credits": 3
     *   }
     * ]
     * </pre>
     * <p>
     * Note : L'identifiant de programme suit généralement le format numérique
     *        de l'Université de Montréal (ex: 117510 pour Bac en informatique).
     * </p>
     */
    private void afficherCoursProgramme() {
        System.out.print("ID du programme (ex: 117510) : ");
        String programId = scanner.nextLine().trim();

        if (programId.isEmpty()) {
            System.out.println("L'ID du programme ne peut pas être vide.");
            return;
        }

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/programs/" + programId + "/courses");

            System.out.println("\nCours du programme " + programId + " :");
            System.out.println(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des cours du programme : " + e.getMessage());
            System.out.println("Vérifiez que l'ID du programme est correct.");
        }
    }

    /**
     * Affiche les cours d'un programme pour un trimestre spécifique.
     * <p>
     * Cette fonctionnalité permet de voir quels cours d'un programme sont offerts
     * pendant un trimestre donné. Utile pour la planification académique.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /programs/{programId}/courses?semester={semester}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur :
     * ID du programme : 117510
     * Trimestre : A24 (Automne 2024)
     *
     * Sortie attendue :
     * Cours du programme 117510 pour le trimestre A24 :
     * [
     *   {
     *     "id": "IFT2255",
     *     "name": "Développement de logiciel",
     *     "credits": 3,
     *     "semester": "A24"
     *   }
     * ]
     * </pre>
     * <p>
     * Note : Les trimestres suivent le format standard UdeM :
     *        H = Hiver, A = Automne, E = Été, suivi de l'année (ex: H25 = Hiver 2025).
     * </p>
     */
    private void afficherCoursProgrammeParTrimestre() {
        System.out.print("ID du programme (ex: 117510) : ");
        String programId = scanner.nextLine().trim();

        if (programId.isEmpty()) {
            System.out.println("L'ID du programme ne peut pas être vide.");
            return;
        }

        System.out.print("Trimestre (ex: H25, A24, E24) : ");
        String semester = scanner.nextLine().trim().toUpperCase();

        // Validation du format du trimestre
        if (!semester.matches("^[AHE][0-9]{2}$")) {
            System.out.println("Format de trimestre invalide. Utilisez H25, A24, E24, etc.");
            return;
        }

        try {
            ApiClient api = new ApiClient();
            String json = api.get(
                    "/programs/" + programId + "/courses?semester=" + semester
            );

            System.out.println("\nCours du programme " + programId
                    + " pour le trimestre " + semester + " :");
            System.out.println(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des cours pour ce trimestre : " + e.getMessage());
            System.out.println("Vérifiez l'ID du programme et le trimestre.");
        }
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
     * Valide un identifiant de programme.
     * <p>
     * Vérifie que l'identifiant correspond au format attendu pour les programmes
     * de l'Université de Montréal (typiquement 6 chiffres).
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * validerProgramId("117510") → true
     * validerProgramId("IFT") → false
     * validerProgramId("12345") → false (trop court)
     * validerProgramId("1234567") → false (trop long)
     * </pre>
     *
     * @param programId L'identifiant du programme à valider
     * @return {@code true} si le format est valide, {@code false} sinon
     */
    private boolean validerProgramId(String programId) {
        return programId.matches("^[0-9]{6}$");
    }

    /**
     * Affiche un exemple d'identifiants de programme courants.
     * <p>
     * Utile pour guider l'utilisateur dans sa saisie.
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Exemples d'IDs de programme :
     * - 117510 : Baccalauréat en informatique
     * - 117511 : Baccalauréat en mathématiques et informatique
     * - 117512 : Baccalauréat en génie logiciel
     * - 117514 : Majeure en informatique
     * </pre>
     */
    public void afficherExemplesProgrammes() {
        System.out.println("\nExemples d'IDs de programme :");
        System.out.println("- 117510 : Baccalauréat en informatique");
        System.out.println("- 117511 : Baccalauréat en mathématiques et informatique");
        System.out.println("- 117512 : Baccalauréat en génie logiciel");
        System.out.println("- 117514 : Majeure en informatique");
        System.out.println("- 117516 : Mineure en informatique");
        System.out.println("\nNote : Ces IDs sont spécifiques à l'Université de Montréal.");
    }

    /**
     * Vérifie si un programme existe en interrogeant l'API.
     * <p>
     * Appelle l'endpoint : {@code GET /programs/{programId}}
     * </p>
     *
     * @param programId L'identifiant du programme à vérifier
     * @return {@code true} si le programme existe, {@code false} sinon
     */
    private boolean programmeExiste(String programId) {
        try {
            ApiClient api = new ApiClient();
            // Note : Cet endpoint pourrait nécessiter d'être implémenté dans l'API
            api.get("/programs/" + programId + "/info");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}