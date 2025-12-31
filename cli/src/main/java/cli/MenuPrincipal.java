package cli;

import cli.menu.MenuCours;
import cli.menu.MenuEnsembleCours;
import cli.menu.MenuProgramme;
import cli.menu.MenuRecherche;

import java.util.Scanner;

/**
 * Menu principal de l'application CLI PlanifiumHelper.
 * <p>
 * Cette classe sert de point d'entrée central pour l'interface utilisateur
 * en ligne de commande. Elle coordonne tous les sous-menus et fonctionnalités
 * de l'application, offrant une navigation unifiée entre les différentes
 * sections.
 * <p>
 * Structure de navigation :
 * <pre>
 * Menu Principal
 * ├── 1. Rechercher des cours → {@link MenuRecherche}
 * ├── 2. Programmes → {@link MenuProgramme}
 * ├── 3. Cours → {@link MenuCours}
 * ├── 4. Ensemble de cours → {@link MenuEnsembleCours}
 * └── 0. Quitter
 * </pre>
 * <p>
 * Chaque option du menu principal lance un sous-menu spécialisé qui gère
 * un domaine fonctionnel spécifique de l'application.
 * <p>
 * Prérequis : Le serveur backend doit être démarré sur {@code http://localhost:7000}
 * avant d'utiliser cette interface.
 *
 * @see MenuRecherche
 * @see MenuProgramme
 * @see MenuCours
 * @see MenuEnsembleCours
 * @see Scanner
 */
public class MenuPrincipal {

    /**
     * Scanner pour la saisie utilisateur, réutilisé dans tous les sous-menus.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur par défaut.
     * <p>
     * Initialise une nouvelle instance du menu principal avec un scanner
     * partagé pour tous les sous-menus.
     * </p>
     */
    public MenuPrincipal() {
        // Constructeur documenté
    }

    /**
     * Lance le menu principal interactif.
     * <p>
     * Cette méthode constitue le cœur de l'interface utilisateur. Elle :
     * <ol>
     *   <li>Affiche le menu principal en boucle</li>
     *   <li>Lit le choix de l'utilisateur</li>
     *   <li>Délègue l'exécution au sous-menu correspondant</li>
     *   <li>Gère la sortie de l'application</li>
     * </ol>
     * <p>
     * Le menu s'exécute en boucle jusqu'à ce que l'utilisateur sélectionne
     * l'option 0 (Quitter).
     * </p>
     * <p>
     * Exemple de flux d'exécution :
     * </p>
     * <pre>
     * // Exemple de flux d'exécution
     * MenuPrincipal menu = new MenuPrincipal();
     * menu.lancer();
     * // Affiche :
     * // === MENU PRINCIPAL ===
     * // 1. Rechercher des cours
     * // 2. Programmes
     * // 3. Cours
     * // 4. Ensemble de cours
     * // 0. Quitter
     * // Votre choix :
     * </pre>
     * <p>
     * Note : Les sous-menus sont instanciés à la volée pour permettre
     *        une navigation fluide et une gestion mémoire optimale.
     * </p>
     */
    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();

            switch (choix) {
                case 1 -> lancerMenuRecherche();
                case 2 -> lancerMenuProgramme();
                case 3 -> lancerMenuCours();
                case 4 -> lancerMenuEnsembleCours();
                case 0 -> quitterApplication();
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }

        } while (choix != 0);
    }

    /**
     * Affiche le menu principal avec toutes les options disponibles.
     * <p>
     * Chaque option correspond à une fonctionnalité majeure de l'application.
     * La présentation est claire et structurée pour une utilisation facile.
     * </p>
     * <p>
     * Sortie attendue :
     * </p>
     * <pre>
     * === MENU PRINCIPAL ===
     * 1. Rechercher des cours
     * 2. Programmes
     * 3. Cours
     * 4. Ensemble de cours
     * 0. Quitter
     * Votre choix :
     * </pre>
     */
    private void afficherMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===\n");
        System.out.println("1. Rechercher des cours");
        System.out.println("   - Recherche par sigle, nom ou description");
        System.out.println();
        System.out.println("2. Programmes");
        System.out.println("   - Consulter les cours d'un programme d'études");
        System.out.println();
        System.out.println("3. Cours");
        System.out.println("   - Détails, horaires, éligibilité, avis étudiants");
        System.out.println();
        System.out.println("4. Ensemble de cours");
        System.out.println("   - Générer un horaire combiné pour plusieurs cours");
        System.out.println();
        System.out.println("0. Quitter l'application");
        System.out.print("\nVotre choix : ");
    }

    /**
     * Lance le sous-menu de recherche de cours.
     * <p>
     * Instancie et exécute le menu {@link MenuRecherche} qui permet
     * des recherches multicritères dans le catalogue de cours.
     * </p>
     *
     * @see MenuRecherche#lancer()
     */
    private void lancerMenuRecherche() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("       RECHERCHE DE COURS");
        System.out.println("═══════════════════════════════════\n");
        new MenuRecherche(scanner).lancer();
    }

    /**
     * Lance le sous-menu des programmes d'études.
     * <p>
     * Instancie et exécute le menu {@link MenuProgramme} qui permet
     * de consulter les cours associés à un programme spécifique.
     * </p>
     *
     * @see MenuProgramme#lancer()
     */
    private void lancerMenuProgramme() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("          PROGRAMMES");
        System.out.println("═══════════════════════════════════\n");
        new MenuProgramme(scanner).lancer();
    }

    /**
     * Lance le sous-menu de gestion des cours individuels.
     * <p>
     * Instancie et exécute le menu {@link MenuCours} qui offre toutes
     * les fonctionnalités liées à un cours spécifique.
     * </p>
     *
     * @see MenuCours#lancer()
     */
    private void lancerMenuCours() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("            COURS");
        System.out.println("═══════════════════════════════════\n");
        new MenuCours(scanner).lancer();
    }

    /**
     * Lance le sous-menu de gestion des ensembles de cours.
     * <p>
     * Instancie et exécute le menu {@link MenuEnsembleCours} qui permet
     * de sélectionner plusieurs cours et de générer un horaire global.
     * </p>
     *
     * @see MenuEnsembleCours#lancer()
     */
    private void lancerMenuEnsembleCours() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("      ENSEMBLE DE COURS");
        System.out.println("═══════════════════════════════════\n");
        new MenuEnsembleCours(scanner).lancer();
    }

    /**
     * Gère la sortie propre de l'application.
     * <p>
     * Affiche un message de fin, ferme les ressources et termine l'exécution.
     * </p>
     * <p>
     * Note : Cette méthode pourrait être étendue pour sauvegarder l'état
     *        de l'application ou effectuer d'autres opérations de nettoyage.
     * </p>
     */
    private void quitterApplication() {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("  Merci d'avoir utilisé PlanifiumHelper !");
        System.out.println("  À bientôt pour votre prochaine session.");
        System.out.println("══════════════════════════════════════════════\n");

        // Fermeture propre du scanner
        scanner.close();
    }

    /**
     * Lit un entier saisi par l'utilisateur.
     * <p>
     * Gère les erreurs de saisie en retournant -1 si l'entrée n'est pas
     * un entier valide. Cela permet au menu de continuer à fonctionner
     * même en cas d'erreur de saisie.
     * </p>
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
     * Vérifie si le serveur backend est accessible.
     * <p>
     * Méthode utilitaire pour vérifier la connectivité au backend
     * avant de lancer les fonctionnalités dépendantes de l'API.
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * MenuPrincipal menu = new MenuPrincipal();
     * if (menu.estServeurAccessible()) {
     *     menu.lancer();
     * } else {
     *     System.out.println("Serveur inaccessible. Démarrez-le d'abord.");
     * }
     * </pre>
     *
     * @return {@code true} si le serveur répond, {@code false} sinon
     */
    public boolean estServeurAccessible() {
        try {
            cli.api.ApiClient client = new cli.api.ApiClient();
            return client.isServerAvailable();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Affiche la bannière de démarrage de l'application.
     * <p>
     * Méthode optionnelle pour améliorer l'expérience utilisateur
     * avec une présentation visuelle au démarrage.
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * ╔══════════════════════════════════════════════╗
     * ║            PLANIFIUM HELPER                 ║
     * ║        Assistant Académique CLI             ║
     * ║         Université de Montréal              ║
     * ╚══════════════════════════════════════════════╝
     * </pre>
     */
    public void afficherBanniere() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            PLANIFIUM HELPER                 ║");
        System.out.println("║        Assistant Académique CLI             ║");
        System.out.println("║         Université de Montréal              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("\nVersion 1.0 | Backend: http://localhost:7000");
        System.out.println("==============================================\n");
    }
}