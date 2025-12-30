package com.diro.ift2255.tests;

import com.diro.ift2255.service.ProgramService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramCoursesTest {

    ProgramService service = new ProgramService();

    @Test
    void nullProgramIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.getCoursesForProgram(null)
        );
    }

    @Test
    void emptyProgramIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.getCoursesForProgram("")
        );
    }

    @Test
    void blankProgramIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.getCoursesForProgram("   ")
        );
    }

    @Test
    void invalidProgramIdThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () ->
                service.getCoursesForProgram("IFT")
        );
    }

    @Test
    void unknownProgramIdThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () ->
                service.getCoursesForProgram("ZZZ999")
        );
    }
}
