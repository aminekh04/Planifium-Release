package com.diro.ift2255.repository;

import com.diro.ift2255.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable de la gestion locale des cours.
 * Elle fournit un acces simple a une liste de cours en memoire.
 */
public class CourseRepository {

    /**
     * Liste des cours disponibles.
     */
    private final List<Course> courses = new ArrayList<>();

    /**
     * Construit le depot de cours et initialise les donnees locales.
     */
    public CourseRepository() {
        courses.add(new Course("IFT1015", "Programmation 1", 3));
        courses.add(new Course("IFT1025", "Programmation 2", 3));
        courses.add(new Course("IFT2015", "Structures de données", 3));
        courses.add(new Course("IFT2255", "Génie logiciel", 3));
        courses.add(new Course("MAT1400", "Calcul 1", 3));
    }

    /**
     * Retourne la liste complete des cours disponibles.
     *
     * @return la liste des cours
     */
    public List<Course> findAll() {
        return courses;
    }
}
