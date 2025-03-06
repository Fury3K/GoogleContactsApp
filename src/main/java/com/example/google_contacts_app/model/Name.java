package com.example.google_contacts_app.model;

public class Name {
    private String displayName;

    // Constructor
    public Name() {}

    public Name(String displayName) {
        this.displayName = displayName;
    }

    // Getter and Setter
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "Name{" +
                "displayName='" + displayName + '\'' +
                '}';
    }
}
