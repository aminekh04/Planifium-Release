package cli;

import cli.menu.MenuCours;
import cli.menu.MenuEnsembleCours;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner = new Scanner(System.in);

    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();

            switch (choix) {
                case 1 -> new cli.menu.MenuRecherche(scanner).lancer();
                case 2 -> new cli.menu.MenuProgramme(scanner).lancer();
                case 3 -> new MenuCours(scanner).lancer();
                case 4 -> new MenuEnsembleCours(scanner).lancer();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }

        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Rechercher des cours");
        System.out.println("2. Programmes");
        System.out.println("3. Cours ");
        System.out.println("4. Ensemble de cours");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }



    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}
