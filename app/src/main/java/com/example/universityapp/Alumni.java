package com.example.universityapp;

public class Alumni {
    private String name;
    private String career;
    private String story;

    public Alumni() {
        // Default constructor required for Firestore
    }

    public Alumni(String name, String career, String story) {
        this.name = name;
        this.career = career;
        this.story = story;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}