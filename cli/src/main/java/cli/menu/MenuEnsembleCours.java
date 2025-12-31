package cli.menu;

import cli.api.ApiClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu interactif pour la gestion des ensembles de cours et la génération d'horaires.
 * <p>
 * Cette classe permet aux utilisateurs de sélectionner plusieurs cours pour un trimestre
 * donné et de générer un horaire global combinant tous les cours sélectionnés.
 * Elle gère la validation des cours et des trimestres, et communique avec l'API
 * pour générer l'horaire optimal.
 * <p>
 * Fonctionnalités principales :
 * <ul>
 *   <li>Sélection d'un trimestre (Hiver, Automne, Été)</li>
 *   <li>Ajout progressif de cours à un ensemble</li>
 *   <li>Validation de l'existence des cours</li>
 *   <li>Limitation à 6 cours maximum</li>
 *   <li>Génération d'un horaire global sans conflits</li>
 * </ul>
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Scanner scanner = new Scanner(System.in);
 * MenuEnsembleCours menu = new MenuEnsembleCours(scanner);
 * menu.lancer(); // Lance le menu interactif
 * }
 * </pre>
 *
 * @see ApiClient
 * @see Scanner
 */
public class MenuEnsembleCours {

    /**
     * Scanner pour la saisie utilisateur.
     */
    private final Scanner scanner;

    /**
     * Liste des cours sélectionnés par l'utilisateur.
     */
    private final List<String> courses = new ArrayList<>();

    /**
     * Constructeur initialisant le menu avec un scanner.
     *
     * @param scanner Instance de Scanner pour la saisie utilisateur.
     */
    public MenuEnsembleCours(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Lance le menu interactif pour la gestion des ensembles de cours.
     * <p>
     * Le flux d'exécution est le suivant :
     * <ol>
     *   <li>Demande du trimestre</li>
     *   <li>Validation du format du trimestre</li>
     *   <li>Affichage du menu principal en boucle</li>
     *   <li>Gestion des options : ajout de cours, génération d'horaire, retour</li>
     * </ol>
     * <p>
     * Exemple d'interaction :
     * </p>
     * <pre>
     * // Exemple d'interaction
     * Trimestre (H25, A24, E24) : A24
     *
     * Cours sélectionnés : []
     * 1. Ajouter un cours
     * 2. Générer l'horaire
     * 0. Retour
     * Choix : 1
     * Sigle du cours : IFT2255
     *
     * Cours sélectionnés : [IFT2255]
     * 1. Ajouter un cours
     * 2. Générer l'horaire
     * 0. Retour
     * Choix : 2
     * === Horaire global ===
     * [{"cours": "IFT2255", "horaire": [...]}]
     * </pre>
     */
    public void lancer() {
        // Étape 1 : Saisie et validation du trimestre
        System.out.print("Trimestre (H25, A24, E24) : ");
        String semester = scanner.nextLine().trim().toUpperCase();

        if (!semester.matches("^[AHE][0-9]{2}$")) {
            System.out.println("Format de trimestre invalide. Utilisez H25, A24, E24, etc.");
            return;
        }

        // Étape 2 : Menu principal en boucle
        while (true) {
            System.out.println("\nCours sélectionnés : " + courses);
            System.out.println("1. Ajouter un cours");
            System.out.println("2. Générer l'horaire");
            System.out.println("0. Retour");
            System.out.print("Choix : ");

            int choix = lireEntier();

            if (choix == 1) {
                ajouterCours();
            } else if (choix == 2) {
                genererHoraire(semester);
                return; // Quitte après génération
            } else if (choix == 0) {
                return; // Retour au menu précédent
            } else {
                System.out.println("Choix invalide.");
            }
        }
    }

    /**
     * Gère l'ajout d'un cours à l'ensemble.
     * <p>
     * Vérifie les contraintes suivantes :
     * <ul>
     *   <li>Limite de 6 cours maximum</li>
     *   <li>Existence du cours via l'API</li>
     *   <li>Format valide du sigle</li>
     * </ul>
     * </p>
     */
    private void ajouterCours() {
        // Vérification de la limite de cours
        if (courses.size() >= 6) {
            System.out.println("Maximum de 6 cours atteint.");
            return;
        }

        // Saisie du sigle du cours
        System.out.print("Sigle du cours : ");
        String courseId = scanner.nextLine().trim().toUpperCase();

        // Validation de l'existence du cours
        if (!courseExiste(courseId)) {
            System.out.println("Cours inexistant : " + courseId);
            return;
        }

        // Ajout à la liste
        courses.add(courseId);
        System.out.println("Cours " + courseId + " ajouté.");
    }

    /**
     * Génère un horaire global pour l'ensemble des cours sélectionnés.
     * <p>
     * Envoie une requête POST à l'API avec la liste des cours et le trimestre,
     * puis affiche l'horaire généré qui optimise les plages horaires sans conflits.
     * <p>
     * Appelle l'endpoint : {@code POST /course-sets/schedule}
     * </p>
     * <p>
     * Exemple de requête JSON envoyée :
     * </p>
     * <pre>
     * {
     *   "semester": "A24",
     *   "courses": ["IFT2255", "IFT2015", "MAT1978"]
     * }
     * </pre>
     * <p>
     * Exemple de réponse JSON attendue :
     * </p>
     * <pre>
     * {
     *   "schedule": [
     *     {
     *       "course": "IFT2255",
     *       "sections": [
     *         {"day": "Lundi", "start": "10:00", "end": "11:30"}
     *       ]
     *     }
     *   ],
     *   "conflicts": [],
     *   "totalHours": 15
     * }
     * </pre>
     *
     * @param semester Trimestre pour lequel générer l'horaire (ex: "A24")
     */
    private void genererHoraire(String semester) {
        // Vérification qu'au moins un cours est sélectionné
        if (courses.isEmpty()) {
            System.out.println("Aucun cours sélectionné.");
            return;
        }

        try {
            ApiClient api = new ApiClient();

            // Construction du tableau JSON des cours
            String coursesJson = courses.stream()
                    .map(c -> "\"" + c + "\"")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            // Construction du corps JSON de la requête
            String jsonBody = """
                {
                  "semester": "%s",
                  "courses": [%s]
                }
                """.formatted(semester, coursesJson);

            // Envoi de la requête POST à l'API
            String response = api.post(
                    "/course-sets/schedule",
                    jsonBody
            );

            // Affichage du résultat
            System.out.println("\n=== Horaire global ===");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération de l'horaire : " + e.getMessage());
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
     * Vérifie si un cours existe en interrogeant l'API.
     * <p>
     * Envoie une requête GET à l'endpoint /courses/{courseId} et vérifie
     * si une réponse valide est retournée (pas d'erreur 404).
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses/{courseId}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * courseExiste("IFT2255") → true (si le cours existe dans l'API)
     * courseExiste("XXX0000") → false (si le cours n'existe pas)
     * </pre>
     *
     * @param courseId Sigle du cours à vérifier (ex: "IFT2255")
     * @return {@code true} si le cours existe, {@code false} sinon
     */
    private boolean courseExiste(String courseId) {
        try {
            ApiClient api = new ApiClient();
            api.get("/courses/" + courseId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retourne le nombre de cours actuellement sélectionnés.
     *
     * @return Nombre de cours dans l'ensemble
     */
    public int getNombreCours() {
        return courses.size();
    }

    /**
     * Retourne la liste des cours sélectionnés.
     *
     * @return Copie de la liste des cours (pour éviter la modification externe)
     */
    public List<String> getCourses() {
        return new ArrayList<>(courses);
    }

    /**
     * Réinitialise l'ensemble de cours (vide la liste).
     * <p>
     * Utile pour recommencer la sélection sans quitter le menu.
     * </p>
     */
    public void reinitialiser() {
        courses.clear();
        System.out.println("Ensemble de cours réinitialisé.");
    }
}