package com.diro.ift2255.repository;

import com.diro.ift2255.model.AcademicResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResultRepository {

    // Regex CSV : split sur les virgules NON entre guillemets
    private static final String CSV_SPLIT_REGEX =
            ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

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

            String line = reader.readLine(); // ignorer l'en-tête

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(CSV_SPLIT_REGEX);

                // enlever les guillemets éventuels
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replaceAll("^\"|\"$", "").trim();
                }

                AcademicResult result = new AcademicResult(
                        parts[0],                     // sigle
                        parts[1],                     // nom
                        parts[2],                     // moyenne (B, A-, etc.)
                        Double.parseDouble(parts[3]), // score
                        Integer.parseInt(parts[4]),   // participants
                        Integer.parseInt(parts[5])    // trimestres
                );

                results.add(result);
            }

            return results;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement du fichier CSV", e);
        }
    }
}
