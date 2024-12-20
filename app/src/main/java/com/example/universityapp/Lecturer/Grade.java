package com.example.universityapp.Lecturer;

public class Grade {
    private String id;  // ID dokumen Firestore
    private String name;
    private String grade;
    private String className;

    public Grade(String id, String name, String grade, String className) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.className = className;
    }

    // Getter dan Setter untuk ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}