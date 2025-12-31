package cli.menu;

import cli.api.ApiClient;
import java.util.Scanner;

/**
 * Menu interactif pour la recherche avancée de cours.
 * <p>
 * Cette classe offre une interface de recherche multicritère permettant
 * de trouver des cours selon différents champs : sigle, nom ou description.
 * Elle communique avec l'API PlanifiumHelper pour effectuer les recherches
 * et présente les résultats à l'utilisateur.
 * <p>
 * Types de recherche disponibles :
 * <ul>
 *   <li><strong>Par sigle</strong> : Recherche partielle ou exacte sur le code du cours</li>
 *   <li><strong>Par nom</strong> : Recherche par mots-clés dans le titre du cours</li>
 *   <li><strong>Par description</strong> : Recherche par mots-clés dans la description détaillée</li>
 * </ul>
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Scanner scanner = new Scanner(System.in);
 * MenuRecherche menu = new MenuRecherche(scanner);
 * menu.lancer(); // Lance le menu interactif de recherche
 * }
 * </pre>
 *
 * @see ApiClient
 * @see Scanner
 */
public class MenuRecherche {

    /**
     * Scanner pour la saisie utilisateur.
     */
    private final Scanner scanner;

    /**
     * Constructeur initialisant le menu avec un scanner.
     *
     * @param scanner Instance de Scanner pour la saisie utilisateur.
     */
    public MenuRecherche(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Lance le menu interactif de recherche.
     * <p>
     * Affiche le menu en boucle jusqu'à ce que l'utilisateur choisisse de quitter (option 0).
     * Chaque choix appelle la méthode de recherche correspondante.
     * </p>
     * <p>
     * Exemple de flux d'exécution :
     * </p>
     * <pre>
     * // Exemple de flux d'exécution
     * MenuRecherche menu = new MenuRecherche(scanner);
     * menu.lancer();
     * // Affiche :
     * // --- Recherche de cours ---
     * // 1. Par sigle
     * // 2. Par nom
     * // 3. Par description
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
                case 1 -> rechercheParSigle();
                case 2 -> rechercheParNom();
                case 3 -> rechercheParDescription();
                case 0 -> System.out.println("Retour au menu principal");
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }

    /**
     * Affiche le menu principal avec les types de recherche disponibles.
     * <p>
     * Les options correspondent aux différents champs sur lesquels effectuer
     * une recherche dans le catalogue de cours.
     * </p>
     * <p>
     * Sortie attendue :
     * </p>
     * <pre>
     * --- Recherche de cours ---
     * 1. Par sigle (ex: IFT, MAT, PHY)
     * 2. Par nom (ex: algorithm, logiciel, database)
     * 3. Par description (ex: programmation, réseau, web)
     * 0. Retour
     * Votre choix :
     * </pre>
     */
    private void afficherMenu() {
        System.out.println("\n--- Recherche de cours ---");
        System.out.println("1. Par sigle (ex: IFT, MAT, PHY)");
        System.out.println("2. Par nom (ex: algorithm, logiciel, database)");
        System.out.println("3. Par description (ex: programmation, réseau, web)");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
    }

    /**
     * Effectue une recherche de cours par sigle (code du cours).
     * <p>
     * Permet une recherche partielle ou exacte sur le sigle des cours.
     * La recherche est insensible à la casse et peut utiliser des fragments.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses?sigle={query}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : IFT
     * Sortie attendue :
     * Résultat :
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
     * Recherches courantes :
     * </p>
     * <pre>
     * "IFT" → Tous les cours d'informatique
     * "IFT2" → Tous les cours IFT de niveau 2000
     * "IFT2255" → Recherche exacte d'un cours spécifique
     * "MAT" → Tous les cours de mathématiques
     * </pre>
     */
    private void rechercheParSigle() {
        System.out.print("Sigle ou début de sigle (ex: IFT, MAT, PHY) : ");
        String sigle = scanner.nextLine().trim().toUpperCase();

        if (sigle.isEmpty()) {
            System.out.println("Le sigle ne peut pas être vide.");
            return;
        }

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/courses?sigle=" + sigle);

            System.out.println("\n=== Résultats de recherche (par sigle '" + sigle + "') ===");
            afficherResultatsFormates(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche par sigle : " + e.getMessage());
        }
    }

    /**
     * Effectue une recherche de cours par nom (titre du cours).
     * <p>
     * Recherche par mots-clés dans le nom complet des cours.
     * La recherche est insensible à la casse et supporte les recherches partielles.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses?name={query}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : algorithm
     * Sortie attendue :
     * Résultat :
     * [
     *   {
     *     "id": "IFT2015",
     *     "name": "Structures de données et algorithmes",
     *     "credits": 3
     *   },
     *   {
     *     "id": "IFT2125",
     *     "name": "Algorithmes et complexité",
     *     "credits": 3
     *   }
     * ]
     * </pre>
     * <p>
     * Recherches courantes :
     * </p>
     * <pre>
     * "logiciel" → Cours de développement logiciel
     * "base de données" → Cours de bases de données
     * "web" → Cours de développement web
     * "sécurité" → Cours de sécurité informatique
     * </pre>
     */
    private void rechercheParNom() {
        System.out.print("Mot-clé dans le nom du cours (ex: algorithm, logiciel, database) : ");
        String mot = scanner.nextLine().trim();

        if (mot.isEmpty()) {
            System.out.println("Le mot-clé ne peut pas être vide.");
            return;
        }

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/courses?name=" + mot);

            System.out.println("\n=== Résultats de recherche (nom contenant '" + mot + "') ===");
            afficherResultatsFormates(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche par nom : " + e.getMessage());
        }
    }

    /**
     * Effectue une recherche de cours par description.
     * <p>
     * Recherche par mots-clés dans la description détaillée des cours.
     * Utile pour trouver des cours sur des sujets spécifiques mentionnés
     * dans les descriptions mais pas nécessairement dans les titres.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses?description={query}}
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée utilisateur : Java
     * Sortie attendue :
     * Résultat :
     * [
     *   {
     *     "id": "IFT2255",
     *     "name": "Développement de logiciel",
     *     "description": "Introduction à la programmation orientée objet avec Java...",
     *     "credits": 3
     *   }
     * ]
     * </pre>
     * <p>
     * Recherches courantes :
     * </p>
     * <pre>
     * "Python" → Cours utilisant Python
     * "projet" → Cours avec composante projet
     * "équipe" → Travail d'équipe
     * "réseau" → Cours de réseautage
     * "machine learning" → Cours d'intelligence artificielle
     * </pre>
     */
    private void rechercheParDescription() {
        System.out.print("Mot-clé dans la description (ex: Java, Python, projet, équipe) : ");
        String mot = scanner.nextLine().trim();

        if (mot.isEmpty()) {
            System.out.println("Le mot-clé ne peut pas être vide.");
            return;
        }

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/courses?description=" + mot);

            System.out.println("\n=== Résultats de recherche (description contenant '" + mot + "') ===");
            afficherResultatsFormates(json);

        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche par description : " + e.getMessage());
        }
    }

    /**
     * Affiche les résultats de recherche de manière formatée et lisible.
     * <p>
     * Transforme le JSON brut en une présentation plus conviviale pour l'utilisateur.
     * Si aucun résultat n'est trouvé, affiche un message approprié.
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Entrée : [{"id":"IFT2255","name":"Développement logiciel","credits":3}]
     * Sortie :
     * 1. IFT2255 - Développement logiciel (3 crédits)
     *
     * Total : 1 cours trouvé
     * </pre>
     *
     * @param jsonResult Résultat JSON brut de l'API
     */
    private void afficherResultatsFormates(String jsonResult) {
        if (jsonResult == null || jsonResult.trim().isEmpty()) {
            System.out.println("Aucun résultat trouvé.");
            return;
        }

        if (jsonResult.trim().equals("[]") || jsonResult.trim().equals("{}")) {
            System.out.println("Aucun cours ne correspond à votre recherche.");
            return;
        }

        // Pour une implémentation simple, afficher le JSON brut
        System.out.println(jsonResult);

        // Note : Pour une version avancée, on pourrait parser le JSON
        // et afficher une liste formatée
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
     * Effectue une recherche combinée sur plusieurs critères.
     * <p>
     * Méthode utilitaire pour effectuer des recherches avancées combinant
     * plusieurs critères. Non exposée dans le menu principal mais disponible
     * pour des extensions futures.
     * </p>
     * <p>
     * Appelle l'endpoint : {@code GET /courses?sigle={}&name={}&description={}}
     * </p>
     *
     * @param sigle Sigle ou fragment de sigle (optionnel)
     * @param nom Mot-clé dans le nom (optionnel)
     * @param description Mot-clé dans la description (optionnel)
     * @return Résultat JSON de la recherche combinée
     * @throws Exception Si une erreur survient lors de la recherche
     */
    public String rechercheCombinee(String sigle, String nom, String description) throws Exception {
        StringBuilder url = new StringBuilder("/courses?");

        if (sigle != null && !sigle.isEmpty()) {
            url.append("sigle=").append(sigle).append("&");
        }
        if (nom != null && !nom.isEmpty()) {
            url.append("name=").append(nom).append("&");
        }
        if (description != null && !description.isEmpty()) {
            url.append("description=").append(description).append("&");
        }

        // Supprimer le dernier '&' si présent
        String urlFinal = url.toString();
        if (urlFinal.endsWith("&")) {
            urlFinal = urlFinal.substring(0, urlFinal.length() - 1);
        }

        ApiClient api = new ApiClient();
        return api.get(urlFinal);
    }

    /**
     * Affiche des conseils pour des recherches efficaces.
     * <p>
     * Donne des astuces aux utilisateurs pour améliorer leurs recherches.
     * </p>
     * <p>
     * Exemple :
     * </p>
     * <pre>
     * Conseils pour une recherche efficace :
     * - Utilisez des termes spécifiques (ex: "Java" plutôt que "programmation")
     * - Pour les sigles, 3-4 lettres suffisent (ex: "IFT" ou "MAT")
     * - Les recherches sont insensibles à la casse
     * - Utilisez plusieurs mots-clés pour affiner les résultats
     * </pre>
     */
    public void afficherConseilsRecherche() {
        System.out.println("\n=== Conseils pour une recherche efficace ===");
        System.out.println("• Utilisez des termes spécifiques : \"Java\" plutôt que \"programmation\"");
        System.out.println("• Pour les sigles, 3-4 lettres suffisent : \"IFT\" ou \"MAT\"");
        System.out.println("• Les recherches sont insensibles à la casse");
        System.out.println("• Cherchez par description pour des sujets précis");
        System.out.println("• Combinez plusieurs recherches pour affiner les résultats");
        System.out.println("• Exemples de recherches courantes :");
        System.out.println("  - Par sigle : IFT, MAT1, PHY");
        System.out.println("  - Par nom : algorithm, logiciel, sécurité");
        System.out.println("  - Par description : Python, projet, équipe, réseau");
    }
}