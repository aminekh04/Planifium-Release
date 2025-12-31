package com.diro.ift2255.model;

import java.util.List;

/**
 * Classe representant le resultat dune verification d admissibilite.
 * Elle indique si un cours est accessible et precise les raisons
 * en cas de non admissibilite.
 */
public class EligibilityResult {

    /**
     * Indique si le cours est admissible.
     */
    private boolean eligible;

    /**
     * Liste des prerequis manquants.
     */
    private List<String> missingPrerequisites;

    /**
     * Raison expliquant le resultat de ladmissibilite.
     */
    private String reason;

    /**
     * Construit un resultat dadmissibilite a partir des informations fournies.
     *
     * @param eligible indique si le cours est admissible
     * @param missingPrerequisites liste des prerequis manquants
     * @param reason raison du resultat
     */
    public EligibilityResult(
            boolean eligible,
            List<String> missingPrerequisites,
            String reason
    ) {
        this.eligible = eligible;
        this.missingPrerequisites = missingPrerequisites;
        this.reason = reason;
    }

    /**
     * Indique si le cours est admissible.
     *
     * @return vrai si admissible
     */
    public boolean isEligible() {
        return eligible;
    }

    /**
     * Retourne la liste des prerequis manquants.
     *
     * @return la liste des prerequis manquants
     */
    public List<String> getMissingPrerequisites() {
        return missingPrerequisites;
    }

    /**
     * Retourne la raison du resultat dadmissibilite.
     *
     * @return la raison du resultat
     */
    public String getReason() {
        return reason;
    }
}
