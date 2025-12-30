package com.diro.ift2255.service;

import com.diro.ift2255.model.CourseConflict;

import java.util.List;

public class CourseSetResult {

    private List<CourseScheduleWithCourse> schedules;
    private List<CourseConflict> conflicts;

    public CourseSetResult(
            List<CourseScheduleWithCourse> schedules,
            List<CourseConflict> conflicts
    ) {
        this.schedules = schedules;
        this.conflicts = conflicts;
    }

    public List<CourseScheduleWithCourse> getSchedules() {
        return schedules;
    }

    public List<CourseConflict> getConflicts() {
        return conflicts;
    }
}
