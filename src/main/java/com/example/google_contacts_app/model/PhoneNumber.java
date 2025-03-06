package com.example.google_contacts_app.model;

public class PhoneNumber {
    private String number;

    // Constructor
    public PhoneNumber(String number) {
        this.number = number;
    }

    // Getter and Setter
    public String getNumber() {
        return (number != null && !number.isEmpty()) ? number : "No phone number available.";
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}
