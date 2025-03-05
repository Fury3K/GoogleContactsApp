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
        List<Person> contacts = peopleService.people().connections()
            .list("people/me")
            .setPersonFields("names,emailAddresses,phoneNumbers")
            .execute()
            .getConnections();

        // Ensure every contact has at least a default name
        if (contacts != null) {
            for (Person contact : contacts) {
                if (contact.getNames() == null || contact.getNames().isEmpty()) {
                    contact.setNames(Collections.singletonList(
                        new com.google.api.services.people.v1.model.Name().setDisplayName("No Name")
                    ));
                }
            }
        }

        return contacts;
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
