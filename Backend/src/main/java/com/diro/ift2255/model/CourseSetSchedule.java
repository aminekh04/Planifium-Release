package com.diro.ift2255.model;

import java.util.List;

public class CourseSetSchedule {

    private String courseId;
    private List<CourseSchedule> schedules;

    public CourseSetSchedule(String courseId, List<CourseSchedule> schedules) {
        this.courseId = courseId;
        this.schedules = schedules;
    }

    public String getCourseId() {
        return courseId;
    }

    public List<CourseSchedule> getSchedules() {
        return schedules;
    }
}
