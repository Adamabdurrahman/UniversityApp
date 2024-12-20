package com.example.universityapp.Lecturer;

import com.google.firebase.firestore.PropertyName;

public class Attendance {
    private String name;
    private String className; // Java-side property for "class"
    private String qrCode;
    private long timestamp;

    public Attendance() {
        // Default constructor required for Firestore
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("class")
    public String getClassName() {
        return className;
    }

    @PropertyName("class")
    public void setClassName(String className) {
        this.className = className;
    }

    @PropertyName("qrCode")
    public String getQrCode() {
        return qrCode;
    }

    @PropertyName("qrCode")
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @PropertyName("timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    @PropertyName("timestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}