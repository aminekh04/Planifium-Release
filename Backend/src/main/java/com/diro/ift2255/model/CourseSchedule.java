package com.diro.ift2255.model;

public class CourseSchedule {

    private String section;
    private String activityType;
    private String day;
    private String startTime;
    private String endTime;

    public CourseSchedule(
            String section,
            String activityType,
            String day,
            String startTime,
            String endTime
    ) {
        this.section = section;
        this.activityType = activityType;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSection() { return section; }
    public String getActivityType() { return activityType; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

}
