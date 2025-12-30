package com.diro.ift2255.tests;

import com.diro.ift2255.service.CourseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchCoursesTest {

    CourseService service = new CourseService();

    @Test
    void searchByValidSigleReturnsResults() {
        var courses = service.searchCourses("IFT2255", null);
        assertFalse(courses.isEmpty());
    }

    @Test
    void searchByAnotherValidSigleWorks() {
        var courses = service.searchCourses("IFT2015", null);
        assertFalse(courses.isEmpty());
    }

    @Test
    void searchByNameReturnsResults() {
        var courses = service.searchCourses(null, "algorithm");
        assertFalse(courses.isEmpty());
    }

    @Test
    void searchWithNoCriteriaThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.searchCourses(null, null)
        );
    }

    @Test
    void searchWithUnknownSigleReturnsEmptyList() {
        var courses = service.searchCourses("ZZZ9999", null);
        assertTrue(courses.isEmpty());
    }
}
