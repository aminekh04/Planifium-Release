package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseSchedule;

import java.util.Objects;

public class CourseScheduleWithCourse {

    private String courseId;
    private String section;
    private String activityType;
    private String day;
    private String startTime;
    private String endTime;

    public CourseScheduleWithCourse(
            String courseId,
            CourseSchedule schedule
    ) {
        this.courseId = courseId;
        this.section = schedule.getSection();
        this.activityType = schedule.getActivityType();
        this.day = schedule.getDay();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
    }

    public String getCourseId() { return courseId; }
    public String getSection() { return section; }
    public String getActivityType() { return activityType; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseScheduleWithCourse)) return false;

        CourseScheduleWithCourse that = (CourseScheduleWithCourse) o;

        return Objects.equals(courseId, that.courseId)
                && Objects.equals(section, that.section)
                && Objects.equals(activityType, that.activityType)
                && Objects.equals(day, that.day)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                courseId,
                section,
                activityType,
                day,
                startTime,
                endTime
        );
}}
