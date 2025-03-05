package com.example.google_contacts_app.model;

public class Contact {
    private String resourceName;
    private String etag;
    private String name;
    private String phoneNumber;
    private String email; // ✅ Added email field

    // Constructors
    public Contact() {}

    public Contact(String resourceName, String etag, String name, String phoneNumber, String email) {
        this.resourceName = resourceName;
        this.etag = etag;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() { // ✅ Added getter
        return email;
    }

    public void setEmail(String email) { // ✅ Added setter
        this.email = email;
    }
}
