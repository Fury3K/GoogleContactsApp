package com.example.google_contacts_app.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class PeopleServiceHelper {

    private static final String APPLICATION_NAME = "Google Contacts App";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public PeopleService getPeopleService(Credential credential) throws GeneralSecurityException, IOException {
        return new PeopleService.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Person> getContacts(PeopleService peopleService) throws IOException {
        ListConnectionsResponse response = peopleService.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names,emailAddresses")
                .execute();
        return response.getConnections();
    }

    public Person createContact(PeopleService peopleService, Person newContact) throws IOException {
        return peopleService.people().createContact(newContact).execute();
    }

    public Person updateContact(PeopleService peopleService, String resourceName, Person updatedContact) throws IOException {
        return peopleService.people().updateContact(resourceName, updatedContact)
                .setUpdatePersonFields("names,emailAddresses")
                .execute();
    }

    public void deleteContact(PeopleService peopleService, String resourceName) throws IOException {
        peopleService.people().deleteContact(resourceName).execute();
    }
}
