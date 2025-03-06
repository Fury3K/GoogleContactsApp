package com.example.google_contacts_app.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Contact {
    private String resourceName;
    private String etag;
    private List<Name> names;  // Use the Name class
    private PhoneNumber phoneNumber;  // Changed to use the PhoneNumber model
    private Email email;  // Use the Email model

    // Constructors
    public Contact() {
        this.names = new ArrayList<>();  // Initialize the names list to avoid null
        this.email = new Email("No email address available.");  // Default email
        this.phoneNumber = new PhoneNumber("No Number");  // Default phone number
    }

    public Contact(String resourceName, String etag, List<Name> names, PhoneNumber phoneNumber, Email email) {
        this.resourceName = resourceName;
        this.etag = etag;
        this.names = (names != null) ? names : new ArrayList<>(); // Ensure names is not null
        this.phoneNumber = phoneNumber != null ? phoneNumber : new PhoneNumber("No Number");  // Ensure phone number is not null
        this.email = (email != null) ? email : new Email("No email address available.");  // Ensure email is not null
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

    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = (names != null) ? names : new ArrayList<>();  // Ensure names is not null
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;  // Return PhoneNumber model object
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = (phoneNumber != null) ? phoneNumber : new PhoneNumber("No Number");  // Ensure phone number is not null
    }

    public Email getEmail() {
        return email;  // Return Email model object
    }

    public void setEmail(Email email) {
        this.email = (email != null) ? email : new Email("No email address available.");  // Ensure email is not null
    }

    // New method to get the display name from the names list
    public String getName() {
        return (names != null && !names.isEmpty()) ? names.get(0).getDisplayName() : "No Name";
    }

    // Override toString method for better readability
    @Override
    public String toString() {
        return "Contact{" +
                "resourceName='" + resourceName + '\'' +
                ", etag='" + etag + '\'' +
                ", names=" + names +
                ", phoneNumber='" + phoneNumber.getNumber() + '\'' +  // Use the PhoneNumber model
                ", email=" + email.getEmailAddress() +  // Use the Email model
                '}';
    }

    // Override equals and hashCode for object comparison and use in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(resourceName, contact.resourceName) &&
                Objects.equals(etag, contact.etag) &&
                Objects.equals(names, contact.names) &&
                Objects.equals(phoneNumber, contact.phoneNumber) &&
                Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceName, etag, names, phoneNumber, email);
    }
}
