package com.diro.ift2255.model;

import java.util.List;

public class EligibilityResult {

    private boolean eligible;
    private List<String> missingPrerequisites;
    private String reason;

    public EligibilityResult(
            boolean eligible,
            List<String> missingPrerequisites,
            String reason
    ) {
        this.eligible = eligible;
        this.missingPrerequisites = missingPrerequisites;
        this.reason = reason;
    }

    public boolean isEligible() {
        return eligible;
    }

    public List<String> getMissingPrerequisites() {
        return missingPrerequisites;
    }

    public String getReason() {
        return reason;
    }
}
