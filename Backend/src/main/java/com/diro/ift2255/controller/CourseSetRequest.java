package com.diro.ift2255.controller;

import java.util.List;

public class CourseSetRequest {

    private String semester;
    private List<String> courses;

    public String getSemester() {
        return semester;
    }

    public List<String> getCourses() {
        return courses;
    }
}
