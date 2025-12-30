package cli.menu;

import cli.api.ApiClient;
import java.util.Scanner;

public class MenuRecherche {

    private final Scanner scanner;

    public MenuRecherche(Scanner scanner) {
        this.scanner = scanner;
    }

    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();

            switch (choix) {
                case 1 -> rechercheParSigle();
                case 2 -> rechercheParNom();
                case 3 -> rechercheParDescription();
                case 0 -> System.out.println("Retour menu principal");
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("\n--- Recherche de cours ---");
        System.out.println("1. Par sigle");
        System.out.println("2. Par nom");
        System.out.println("3. Par description");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
    }

    private void rechercheParSigle() {
        System.out.print("Sigle partiel (ex: IFT) : ");
        String sigle = scanner.nextLine();

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/courses?sigle=" + sigle);
            System.out.println("\nRésultat :");
            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche.");
        }
    }

    private void rechercheParNom() {
        System.out.print("Mot-clé (nom) : ");
        String mot = scanner.nextLine();

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/courses?name=" + mot);
            System.out.println("\nRésultat :");
            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche.");
        }
    }

    private void rechercheParDescription() {
        System.out.print("Mot-clé (description) : ");
        String mot = scanner.nextLine();

        try {
            ApiClient api = new ApiClient();
            String json = api.get("/courses?description=" + mot);
            System.out.println("\nRésultat :");
            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche.");
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
