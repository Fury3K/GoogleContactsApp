package com.example.google_contacts_app.service;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleContactsService {

    private final PeopleService peopleService;

    public GoogleContactsService() throws IOException {
        this.peopleService = GooglePeopleServiceUtil.getPeopleService(); // Initialize PeopleService
    }

    // Get all contacts
    public List<Person> getAllContacts() throws IOException {
        try {
            List<Person> contacts = peopleService.people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers")
                .execute()
                .getConnections();
    
            if (contacts == null || contacts.isEmpty()) {
                // Log a warning if no contacts are found
                System.out.println("No contacts found.");
                return Collections.emptyList();
            }
            
            // Ensure every contact has at least a default name
            for (Person contact : contacts) {
                if (contact.getNames() == null || contact.getNames().isEmpty()) {
                    contact.setNames(Collections.singletonList(
                        new com.google.api.services.people.v1.model.Name().setDisplayName("No Name")
                    ));
                }
            }
    
            return contacts;
    
        } catch (IOException e) {
            // Log and rethrow the exception
            System.err.println("Error fetching contacts: " + e.getMessage());
            throw new IOException("Error fetching contacts from Google API.", e);
        }
    }

    // Add a contact
    public Person addContact(Person person) throws IOException {
        return peopleService.people().createContact(person).execute();
    }

    // Update a contact
    public Person updateContact(String resourceName, Person person) throws IOException {
        return peopleService.people().updateContact(resourceName, person)
            .setUpdatePersonFields("names,phoneNumbers,emailAddresses")
            .execute();
    }

    // Delete a contact
    public void deleteContact(String resourceName) throws IOException {
        peopleService.people().deleteContact(resourceName).execute();
    }
}
