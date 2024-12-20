package com.example.universityapp.model;

public class AdmissionModel {
    private String id;
    private String name;
    private String deadline;
    private String process;
    private String requirement;

    public AdmissionModel() {
        // Default constructor required for Firebase
    }

    public AdmissionModel(String name, String deadline, String process, String requirement) {
        this.name = name;
        this.deadline = deadline;
        this.process = process;
        this.requirement = requirement;
    }

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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}