package com.diro.ift2255.model;

public class ComparedCourse {

    private String courseId;
    private String name;
    private int credits;

    private int reviewCount;

    // 👇 NOUVEAUX champs
    private int workload;     // avis Discord
    private int difficulty;   // results.csv

    public ComparedCourse(
            String courseId,
            String name,
            int credits,
            int reviewCount,
            int workload,
            int difficulty
    ) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.reviewCount = reviewCount;
        this.workload = workload;
        this.difficulty = difficulty;
    }

    public String getCourseId() { return courseId; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public int getReviewCount() { return reviewCount; }

    public int getWorkload() { return workload; }
    public int getDifficulty() { return difficulty; }
}
