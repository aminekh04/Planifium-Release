package com.diro.ift2255.repository;

import com.diro.ift2255.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    private final List<Course> courses = new ArrayList<>();

    public CourseRepository() {
        // Données locales MINIMALES (suffisant pour tests)
        courses.add(new Course("IFT1015", "Programmation 1", 3));
        courses.add(new Course("IFT1025", "Programmation 2", 3));
        courses.add(new Course("IFT2015", "Structures de données", 3));
        courses.add(new Course("IFT2255", "Génie logiciel", 3));
        courses.add(new Course("MAT1400", "Calcul 1", 3));
    }

    public List<Course> findAll() {
        return courses;
    }
}
