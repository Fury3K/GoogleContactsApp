package com.example.google_contacts_app.service;

import com.example.google_contacts_app.model.Contact;
import com.google.api.services.people.v1.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private GoogleContactsService googleContactsService; // Use GoogleContactsService instead of PeopleService

    public List<Contact> getAllContacts() throws IOException {
        List<Person> persons = googleContactsService.getAllContacts();
        
        return persons.stream().map(person -> {
            Contact contact = new Contact();
            contact.setResourceName(person.getResourceName());
            contact.setName(person.getNames() != null && !person.getNames().isEmpty() 
                ? person.getNames().get(0).getDisplayName() 
                : "No Name");
            contact.setPhoneNumber(person.getPhoneNumbers() != null && !person.getPhoneNumbers().isEmpty() 
                ? person.getPhoneNumbers().get(0).getValue() 
                : "No Number");
            contact.setEmail(person.getEmailAddresses() != null && !person.getEmailAddresses().isEmpty()
                ? person.getEmailAddresses().get(0).getValue()
                : "No Email"); // ✅ Extract email
            return contact;
        }).collect(Collectors.toList());
    }

    public void addContact(Contact contact) throws IOException {
        Person person = new Person();
        person.setNames(List.of(new com.google.api.services.people.v1.model.Name().setGivenName(contact.getName())));
        person.setPhoneNumbers(List.of(new com.google.api.services.people.v1.model.PhoneNumber().setValue(contact.getPhoneNumber())));
        person.setEmailAddresses(List.of(new com.google.api.services.people.v1.model.EmailAddress().setValue(contact.getEmail()))); // ✅ Include email

        googleContactsService.addContact(person);
    }

    public void updateContact(String resourceName, Contact updatedContact) throws IOException {
        Person person = new Person();
        person.setNames(List.of(new com.google.api.services.people.v1.model.Name().setGivenName(updatedContact.getName())));
        person.setPhoneNumbers(List.of(new com.google.api.services.people.v1.model.PhoneNumber().setValue(updatedContact.getPhoneNumber())));
        person.setEmailAddresses(List.of(new com.google.api.services.people.v1.model.EmailAddress().setValue(updatedContact.getEmail()))); // ✅ Include email

        googleContactsService.updateContact(resourceName, person);
    }

    public void deleteContact(String resourceName) throws IOException {
        googleContactsService.deleteContact(resourceName);
    }
}
