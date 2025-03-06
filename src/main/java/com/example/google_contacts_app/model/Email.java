package com.example.google_contacts_app.model;

public class Email {
    private String emailAddress;

    // Constructor
    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Getter and Setter
    public String getEmailAddress() {
        return (emailAddress != null && !emailAddress.isEmpty()) ? emailAddress : "No email address available.";
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
