package com.example.universityapp.Lecturer;

public class Course {
    private String documentId;
    private String courseId, courseName, courseDescription, coursecode;
    private String lecturerName, location, materials, schedule;

    public Course() {
        // Constructor kosong untuk Firestore
    }

    // Getters dan Setters
    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getCourseDescription() { return courseDescription; }
    public String getCoursecode() { return coursecode; }
    public String getLecturerName() { return lecturerName; }
    public String getLocation() { return location; }
    public String getMaterials() { return materials; }
    public String getSchedule() { return schedule; }
}
