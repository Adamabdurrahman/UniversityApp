package com.example.universityapp.model;

public class AlumniCareerModel {
    private String id;       // Untuk menyimpan ID dokumen Firestore
    private String name;     // Field "name" di Firestore
    private String career;   // Field "career" di Firestore
    private String story;    // Field "story" di Firestore

    // Constructor kosong diperlukan oleh Firestore
    public AlumniCareerModel() {}

    // Constructor untuk inisialisasi manual
    public AlumniCareerModel(String name, String career, String story) {
        this.name = name;
        this.career = career;
        this.story = story;
    }

    // Getter dan Setter untuk ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter dan Setter untuk Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter dan Setter untuk Career
    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    // Getter dan Setter untuk Story
    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}