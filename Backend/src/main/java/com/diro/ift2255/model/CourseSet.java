package com.diro.ift2255.model;

import java.util.List;

public class CourseSet {

    private List<String> courseIds;

    public CourseSet(List<String> courseIds) {
        this.courseIds = courseIds;
    }

    public List<String> getCourseIds() {
        return courseIds;
    }
}
