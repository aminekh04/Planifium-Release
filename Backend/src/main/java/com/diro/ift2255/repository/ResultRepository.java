package com.diro.ift2255.repository;

import com.diro.ift2255.model.AcademicResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable du chargement des resultats academiques.
 * Elle lit les donnees a partir dun fichier csv contenu dans les ressources.
 */
public class ResultRepository {

    /**
     * Expression reguliere utilisee pour separer les colonnes du fichier csv.
     */
    private static final String CSV_SPLIT_REGEX =
            ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    /**
     * Charge lensemble des resultats academiques depuis le fichier csv.
     *
     * @return la liste des resultats academiques
     * @throws RuntimeException si une erreur survient lors du chargement
     */
    public List<AcademicResult> loadAll() {

        List<AcademicResult> results = new ArrayList<>();

        try {
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/results.csv");

            if (is == null) {
                throw new RuntimeException("results.csv introuvable");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(CSV_SPLIT_REGEX);

                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replaceAll("^\"|\"$", "").trim();
                }

                AcademicResult result = new AcademicResult(
                        parts[0],
                        parts[1],
                        parts[2],
                        Double.parseDouble(parts[3]),
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5])
                );

                results.add(result);
            }

            return results;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement du fichier CSV", e);
        }
    }
}
