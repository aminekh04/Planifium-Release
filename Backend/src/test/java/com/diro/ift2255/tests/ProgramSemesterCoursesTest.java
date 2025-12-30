package com.diro.ift2255.tests;

import com.diro.ift2255.service.CourseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramSemesterCoursesTest {

    private final CourseService courseService = new CourseService();

    /**
     * ⚠️ Programme valide MAIS API externe retourne 400
     * → RuntimeException attendue
     */
    @Test
    void validProgramAndSemesterThrowsRuntimeException() {
        assertThrows(
                RuntimeException.class,
                () -> courseService.getCoursesForSemesterAndProgram("A24", "IFT")
        );
    }

    /**
     * ❌ semester null → IllegalArgumentException (avant appel API)
     */
    @Test
    void nullSemesterThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> courseService.getCoursesForSemesterAndProgram(null, "IFT")
        );
    }

    /**
     * ❌ semester vide → IllegalArgumentException
     */
    @Test
    void blankSemesterThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> courseService.getCoursesForSemesterAndProgram("   ", "IFT")
        );
    }

    /**
     * ❌ programme null → RuntimeException (API appelée avec null)
     */
    @Test
    void nullProgramThrowsRuntimeException() {
        assertThrows(
                RuntimeException.class,
                () -> courseService.getCoursesForSemesterAndProgram("A24", null)
        );
    }

    /**
     * ❌ trimestre invalide MAIS API plante avant
     * → RuntimeException (et NON IllegalArgumentException)
     */
    @Test
    void invalidSemesterStillThrowsRuntimeException() {
        assertThrows(
                RuntimeException.class,
                () -> courseService.getCoursesForSemesterAndProgram("X99", "IFT")
        );
    }
}
