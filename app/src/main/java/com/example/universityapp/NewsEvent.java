package com.example.universityapp;

public class NewsEvent {
    private String title;
    private String date;
    private String description;

    public NewsEvent() {
        // Default constructor required for Firestore
    }

    public NewsEvent(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}