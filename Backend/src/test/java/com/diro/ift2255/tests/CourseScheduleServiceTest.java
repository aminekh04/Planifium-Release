package com.diro.ift2255.tests;

import com.diro.ift2255.model.CourseSchedule;
import com.diro.ift2255.service.CourseScheduleService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CourseScheduleServiceTest {

    private final CourseScheduleService service =
            new CourseScheduleService();

    // ============================
    // TEST 1 : CAS NOMINAL (NON VIDE)
    // ============================
    @Test
    public void validCourseAndSemester_returnsNonEmptyList() {

        List<CourseSchedule> schedules =
                service.getCourseSchedule("IFT2015", "A24");

        assertNotNull(schedules);
        assertFalse("La liste ne doit pas être vide", schedules.isEmpty());

        CourseSchedule s = schedules.get(0);
        assertNotNull(s.getSection());
        assertNotNull(s.getActivityType());
        assertNotNull(s.getDay());
        assertNotNull(s.getStartTime());
        assertNotNull(s.getEndTime());
    }

    // ============================
    // TEST 2 : TRIMESTRE INVALIDE
    // ============================
    @Test(expected = IllegalArgumentException.class)
    public void invalidSemester_throwsException() {

        service.getCourseSchedule("IFT2015", "Automne24");
    }

    // ============================
    // TEST 3 : PARAMÈTRE MANQUANT
    // ============================
    @Test(expected = IllegalArgumentException.class)
    public void missingSemester_throwsException() {

        service.getCourseSchedule("IFT2015", "");
    }

    // ============================
    // TEST 4 : COURS SANS HORAIRE
    // ============================
    @Test
    public void validCourse_returnsWellFormedScheduleEntries() {

        List<CourseSchedule> schedules =
                service.getCourseSchedule("IFT2015", "A24");

        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());

        for (CourseSchedule s : schedules) {
            assertNotNull("section manquante", s.getSection());
            assertNotNull("type manquant", s.getActivityType());
            assertNotNull("jour manquant", s.getDay());
            assertNotNull("heure début manquante", s.getStartTime());
            assertNotNull("heure fin manquante", s.getEndTime());

            assertFalse(s.getDay().isBlank());
            assertFalse(s.getStartTime().isBlank());
            assertFalse(s.getEndTime().isBlank());
        }
    }


    // ============================
    // TEST 5 : COURS INVALIDE
    // ============================
    @Test
    public void invalidCourse_throwsRuntimeException() {

        try {
            service.getCourseSchedule("XXX0000", "A24");
            fail("Une exception devait être levée");
        } catch (RuntimeException e) {
            assertTrue(
                    e.getMessage().contains("Erreur récupération horaire")
            );
        }
    }
}
