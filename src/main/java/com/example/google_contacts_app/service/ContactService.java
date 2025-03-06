package com.example.google_contacts_app.service;

import com.example.google_contacts_app.model.Contact;
import com.example.google_contacts_app.model.Email;
import com.example.google_contacts_app.model.Name;
import com.example.google_contacts_app.model.PhoneNumber;
import com.google.api.services.people.v1.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private GoogleContactsService googleContactsService;

    // Fetch all contacts from Google API and map them to the Contact model
    public List<Contact> getAllContacts() throws IOException {
        List<Person> persons = googleContactsService.getAllContacts();
        
        return persons.stream().map(person -> {
            Contact contact = new Contact();
            contact.setResourceName(person.getResourceName());

            // Set the names list in the contact (since the Name class holds display names)
            String displayName = (person.getNames() != null && !person.getNames().isEmpty()) 
                ? person.getNames().get(0).getDisplayName() 
                : "No Name";
            Name name = new Name(displayName);  // Create a Name object with the display name
            contact.setNames(List.of(name));  // Set the names list in the Contact
            
            // Set the phone number as a PhoneNumber object, not just a String
            String phoneNumberValue = (person.getPhoneNumbers() != null && !person.getPhoneNumbers().isEmpty()) 
                ? person.getPhoneNumbers().get(0).getValue() 
                : "No Number";
            PhoneNumber phoneNumber = new PhoneNumber(phoneNumberValue);  // Wrap the phone number in a PhoneNumber object
            contact.setPhoneNumber(phoneNumber);  // Set the PhoneNumber object in the Contact

            // Set email as an Email object, not just a String
            String emailValue = (person.getEmailAddresses() != null && !person.getEmailAddresses().isEmpty())
                ? person.getEmailAddresses().get(0).getValue()
                : "No Email";
            Email email = new Email(emailValue);  // Wrap the email address in an Email object
            contact.setEmail(email);  // Set the Email object in the Contact
            
            return contact;
        }).collect(Collectors.toList());
    }

    // Add a new contact to Google Contacts
    public void addContact(Contact contact) throws IOException {
        Person person = new Person();
        
        // Set the name field properly (wrap it in a Name object)
        person.setNames(List.of(new com.google.api.services.people.v1.model.Name().setGivenName(contact.getName())));
        
        // Set the phone number correctly by wrapping it in a PhoneNumber object
        String phoneNumberValue = contact.getPhoneNumber() != null ? contact.getPhoneNumber().getNumber() : "No Number";
        person.setPhoneNumbers(List.of(new com.google.api.services.people.v1.model.PhoneNumber().setValue(phoneNumberValue)));  // Set phone number

        // Ensure email is set correctly by wrapping it in an EmailAddress object
        String emailValue = contact.getEmail() != null ? contact.getEmail().getEmailAddress() : "No Email";
        person.setEmailAddresses(List.of(new com.google.api.services.people.v1.model.EmailAddress().setValue(emailValue)));  // Set email address
        
        googleContactsService.addContact(person);
    }

    // Update an existing contact in Google Contacts
    public void updateContact(String resourceName, Contact updatedContact) throws IOException {
        Person person = new Person();
        
        // Set the name field properly (wrap it in a Name object)
        person.setNames(List.of(new com.google.api.services.people.v1.model.Name().setGivenName(updatedContact.getName())));
        
        // Set the phone number correctly by wrapping it in a PhoneNumber object
        String phoneNumberValue = updatedContact.getPhoneNumber() != null ? updatedContact.getPhoneNumber().getNumber() : "No Number";
        person.setPhoneNumbers(List.of(new com.google.api.services.people.v1.model.PhoneNumber().setValue(phoneNumberValue)));  // Set phone number

        // Ensure email is set correctly by wrapping it in an EmailAddress object
        String emailValue = updatedContact.getEmail() != null ? updatedContact.getEmail().getEmailAddress() : "No Email";
        person.setEmailAddresses(List.of(new com.google.api.services.people.v1.model.EmailAddress().setValue(emailValue)));  // Set email address
        
        googleContactsService.updateContact(resourceName, person);
    }

    // Delete a contact from Google Contacts
    public void deleteContact(String resourceName) throws IOException {
        googleContactsService.deleteContact(resourceName);
    }
}
