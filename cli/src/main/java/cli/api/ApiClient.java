package cli.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Client HTTP pour interagir avec l'API REST de PlanifiumHelper.
 * <p>
 * Cette classe fournit des méthodes simplifiées pour effectuer des requêtes
 * HTTP GET et POST vers le serveur backend. Elle encapsule la logique de
 * création des requêtes, gestion des erreurs HTTP et parsing des réponses.
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * ApiClient client = new ApiClient();
 * String courses = client.get("/courses"); // GET /courses
 * String response = client.post("/opinions", "{\"text\": \"Bon cours\"}"); // POST /opinions
 * }
 * </pre>
 *
 * @see HttpClient
 * @see HttpRequest
 * @see HttpResponse
 */
public class ApiClient {

    /**
     * URL de base du serveur backend.
     * Par défaut : http://localhost:7000
     */
    private static final String BASE_URL = "http://localhost:7000";

    /**
     * Client HTTP réutilisable pour toutes les requêtes.
     */
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Constructeur par défaut.
     * Initialise une nouvelle instance d'ApiClient.
     */
    public ApiClient() {
        // Constructeur documenté
    }

    /**
     * Effectue une requête HTTP GET vers l'endpoint spécifié.
     * <p>
     * Cette méthode envoie une requête GET au serveur et retourne le corps
     * de la réponse si le statut HTTP est dans la plage 2xx (succès).
     * </p>
     *
     * @param endpoint Chemin de l'API à appeler (ex: "/courses", "/ping").
     *                 Ne doit pas inclure l'URL de base.
     * @return Le corps de la réponse HTTP sous forme de chaîne JSON.
     * @throws Exception Si une erreur réseau survient ou si le code de statut
     *                   HTTP n'est pas dans la plage 2xx (200-299).
     *
     * <p>Exemple d'utilisation :</p>
     * <pre>
     * {@code
     * ApiClient client = new ApiClient();
     * try {
     *     String result = client.get("/ping");
     *     System.out.println(result); // Affiche "pong"
     * } catch (Exception e) {
     *     System.err.println("Erreur: " + e.getMessage());
     * }
     * }
     * </pre>
     */
    public String get(String endpoint) throws Exception {
        // Construction de la requête GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        // Envoi de la requête et réception de la réponse
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // Vérification du code de statut HTTP
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new Exception("Erreur HTTP " + response.statusCode() +
                    " pour GET " + endpoint);
        }
        return response.body();
    }

    /**
     * Effectue une requête HTTP POST avec un corps JSON.
     * <p>
     * Cette méthode envoie une requête POST avec le contenu JSON spécifié
     * et retourne le corps de la réponse si le statut HTTP est 200 ou 201.
     * </p>
     *
     * @param endpoint Chemin de l'API à appeler (ex: "/api/opinions").
     *                 Ne doit pas inclure l'URL de base.
     * @param jsonBody Corps de la requête au format JSON.
     * @return Le corps de la réponse HTTP sous forme de chaîne JSON.
     * @throws Exception Si une erreur réseau survient ou si le code de statut
     *                   HTTP n'est ni 200 ni 201.
     *
     * <p>Exemple d'utilisation :</p>
     * <pre>
     * {@code
     * ApiClient client = new ApiClient();
     * String json = "{\"course_code\": \"IFT2255\", \"text\": \"Excellent cours\"}";
     * try {
     *     String result = client.post("/api/opinions", json);
     *     System.out.println(result); // Affiche la réponse JSON
     * } catch (Exception e) {
     *     System.err.println("Erreur: " + e.getMessage());
     * }
     * }
     * </pre>
     */
    public String post(String endpoint, String jsonBody) throws Exception {
        // Construction de la requête POST avec en-tête Content-Type
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Envoi de la requête et réception de la réponse
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // Vérification des codes de statut HTTP acceptés
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception(
                    "Erreur API (" + response.statusCode() + ") pour POST " +
                            endpoint + " : " + response.body()
            );
        }

        return response.body();
    }

    /**
     * Retourne l'URL de base utilisée par le client.
     *
     * @return L'URL de base du serveur (ex: "http://localhost:7000").
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Vérifie si le serveur est accessible en envoyant une requête ping.
     * <p>
     * Cette méthode envoie une requête GET à l'endpoint "/ping" et vérifie
     * si la réponse est "pong".
     * </p>
     *
     * @return {@code true} si le serveur répond correctement, {@code false} sinon.
     *
     * <p>Exemple d'utilisation :</p>
     * <pre>
     * {@code
     * ApiClient client = new ApiClient();
     * if (client.isServerAvailable()) {
     *     System.out.println("Serveur accessible");
     * } else {
     *     System.out.println("Serveur inaccessible");
     * }
     * }
     * </pre>
     */
    public boolean isServerAvailable() {
        try {
            String response = get("/ping");
            return "pong".equals(response.trim());
        } catch (Exception e) {
            return false;
        }
    }
}