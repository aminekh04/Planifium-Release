package com.diro.ift2255.model;

import java.util.List;

public class CourseDetails {

    private String id;
    private String name;
    private int credits;
    private String description;

    private List<String> prerequisiteCourses; // ["IFT1025", ...]
    private String requirementText;           // "Préalable : IFT1025"

    private boolean availableAutumn;
    private boolean availableWinter;
    private boolean availableSummer;

    private boolean availableDay;
    private boolean availableNight;

    private String udemWebsite;

    public CourseDetails(String id,
                         String name,
                         int credits,
                         String description,
                         List<String> prerequisiteCourses,
                         String requirementText,
                         boolean availableAutumn,
                         boolean availableWinter,
                         boolean availableSummer,
                         boolean availableDay,
                         boolean availableNight,
                         String udemWebsite) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.prerequisiteCourses = prerequisiteCourses;
        this.requirementText = requirementText;
        this.availableAutumn = availableAutumn;
        this.availableWinter = availableWinter;
        this.availableSummer = availableSummer;
        this.availableDay = availableDay;
        this.availableNight = availableNight;
        this.udemWebsite = udemWebsite;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public String getDescription() { return description; }
    public List<String> getPrerequisiteCourses() { return prerequisiteCourses; }
    public String getRequirementText() { return requirementText; }
    public boolean isAvailableAutumn() { return availableAutumn; }
    public boolean isAvailableWinter() { return availableWinter; }
    public boolean isAvailableSummer() { return availableSummer; }
    public boolean isAvailableDay() { return availableDay; }
    public boolean isAvailableNight() { return availableNight; }
    public String getUdemWebsite() { return udemWebsite; }
    // 🔹 Alias pour compatibilité (éligibilité)
    public List<String> getPrereqCourses() {
        return prerequisiteCourses;
    }

}
