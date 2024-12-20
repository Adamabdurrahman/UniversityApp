package com.example.universityapp;

public class Admission {
    private String name;
    private String requirement;
    private String process;
    private String deadline;

    public Admission() {
        // Default constructor required for calls to DataSnapshot.getValue(Admission.class)
    }

    public Admission(String name,String requirement, String process, String deadline) {
        this.name = name;
        this.requirement = requirement;
        this.process = process;
        this.deadline = deadline;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}