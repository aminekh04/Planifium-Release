package com.diro.ift2255.tests;

import com.diro.ift2255.model.EligibilityResult;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.EligibilityService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EligibilityServiceTest {

    private EligibilityService eligibilityService;

    @Before
    public void setUp() {
        CourseService courseService = new CourseService();
        eligibilityService = new EligibilityService(courseService);
    }

    /**
     * TEST 1 ❌
     * Prérequis manquant → NON éligible
     * IFT2255 requiert IFT1025
     */
    @Test
    public void missingPrerequisite_returnsFalse() {

        EligibilityResult result =
                eligibilityService.checkEligibility(
                        "IFT2255",
                        List.of("IFT1065"), // mauvais cours
                        3
                );

        assertFalse(result.isEligible());
        assertEquals(1, result.getMissingPrerequisites().size());
        assertEquals("IFT1025", result.getMissingPrerequisites().get(0));
        assertEquals("Prérequis manquants", result.getReason());
    }

    /**
     * TEST 2 ❌
     * Cycle insuffisant → NON éligible
     */
    @Test
    public void insufficientCycle_returnsFalse() {

        EligibilityResult result =
                eligibilityService.checkEligibility(
                        "IFT3911",          // cours cycle 3
                        List.of("IFT2255"),
                        2                   // cycle insuffisant
                );

        assertFalse(result.isEligible());
        assertTrue(result.getMissingPrerequisites().isEmpty());
        assertEquals("Cycle insuffisant", result.getReason());
    }

    /**
     * TEST 3 ✅
     * Tout est correct → éligible
     */
    @Test
    public void allConditionsMet_returnsTrue() {

        EligibilityResult result =
                eligibilityService.checkEligibility(
                        "IFT2255",
                        List.of("IFT1025"),
                        3
                );

        assertTrue(result.isEligible());
        assertTrue(result.getMissingPrerequisites().isEmpty());
        assertNull(result.getReason());
    }

    /**
     * TEST 4 ❌
     * Aucun cours complété → NON éligible
     */
    @Test
    public void noCompletedCourses_returnsFalse() {

        EligibilityResult result =
                eligibilityService.checkEligibility(
                        "IFT2255",
                        List.of(),
                        3
                );

        assertFalse(result.isEligible());
        assertEquals(List.of("IFT1025"), result.getMissingPrerequisites());
    }

    /**
     * TEST 5 ❌
     * Mauvaise casse / espaces → normalisation vérifiée
     */
    @Test
    public void normalizationOfCompletedCourses_worksCorrectly() {

        EligibilityResult result =
                eligibilityService.checkEligibility(
                        "IFT2255",
                        List.of(" ift1025 "), // casse + espaces
                        3
                );

        assertTrue(result.isEligible());
        assertTrue(result.getMissingPrerequisites().isEmpty());
    }
}
