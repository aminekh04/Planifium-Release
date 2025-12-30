package com.diro.ift2255.model;

public class CourseConflict {

    private String courseA;
    private String courseB;
    private String day;
    private String startTime;
    private String endTime;

    public CourseConflict(
            String courseA,
            String courseB,
            String day,
            String startTime,
            String endTime
    ) {
        this.courseA = courseA;
        this.courseB = courseB;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseA() { return courseA; }
    public String getCourseB() { return courseB; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
}
