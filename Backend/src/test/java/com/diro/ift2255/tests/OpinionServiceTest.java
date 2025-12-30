package com.diro.ift2255.tests;

import com.diro.ift2255.model.Opinion;
import com.diro.ift2255.service.OpinionService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OpinionServiceTest {

    private OpinionService service;

    @Before
    public void setUp() {
        service = new OpinionService();
    }

    // =========================================
    // TEST : voir les avis d’un cours donné
    // =========================================
    @Test
    public void getOpinions_returnsOnlyOpinionsForRequestedCourse() {

        // Avis pour IFT2255
        service.addOpinion(opinion("IFT2255", "Avis 1 IFT2255"));
        service.addOpinion(opinion("IFT2255", "Avis 2 IFT2255"));

        // Avis pour d'autres cours
        service.addOpinion(opinion("IFT2015", "Avis IFT2015"));
        service.addOpinion(opinion("MAT1978", "Avis MAT1978"));

        // Action : récupérer les avis pour IFT2255
        List<Opinion> result = service.getOpinions("IFT2255");

        // Vérifications
        assertEquals(2, result.size());

        for (Opinion o : result) {
            assertEquals("IFT2255", o.getCourse_code());
        }
    }

    // =========================================
    // TEST : aucun avis pour ce cours
    // =========================================
    @Test
    public void getOpinions_noOpinionsForCourse_returnsEmptyList() {

        service.addOpinion(opinion("IFT2015", "Avis existant"));

        List<Opinion> result = service.getOpinions("IFT2255");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // =========================================
    // MÉTHODE HELPER
    // =========================================
    private Opinion opinion(String courseCode, String text) {
        Opinion o = new Opinion();
        o.course_code = courseCode;
        o.text = text;
        return o;
    }
}
