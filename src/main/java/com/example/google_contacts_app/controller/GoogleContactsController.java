package com.example.google_contacts_app.controller;

import com.example.google_contacts_app.service.PeopleServiceHelper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class GoogleContactsController {

    private final PeopleServiceHelper peopleServiceHelper;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleContactsController(PeopleServiceHelper peopleServiceHelper,
                                    OAuth2AuthorizedClientService authorizedClientService) {
        this.peopleServiceHelper = peopleServiceHelper;
        this.authorizedClientService = authorizedClientService;
    }

    private String getAccessToken(OAuth2AuthenticationToken token) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),
                token.getName()
        );

        if (client == null || client.getAccessToken() == null) {
            System.err.println("Access token is missing or invalid.");
            return null;
        }

        String accessToken = client.getAccessToken().getTokenValue();
        System.out.println("Access token retrieved successfully.");
        return accessToken;
    }

    @GetMapping
    public String getContacts(OAuth2AuthenticationToken token, Model model) {
        String accessToken = getAccessToken(token);
        if (accessToken == null) {
            model.addAttribute("error", "Access token not available.");
            return "contacts";
        }

        try {
            Credential credential = new GoogleCredential().setAccessToken(accessToken);
            PeopleService peopleService = peopleServiceHelper.getPeopleService(credential);
            List<Person> contacts = peopleServiceHelper.getContacts(peopleService);
            model.addAttribute("contacts", contacts);
        } catch (GeneralSecurityException | IOException e) {
            System.err.println("Error fetching contacts: " + e.getMessage());
            model.addAttribute("error", "Error fetching contacts: " + e.getMessage());
        }
        return "contacts";
    }

    @PostMapping
    public String addContact(OAuth2AuthenticationToken token, @RequestBody Person newContact, Model model) {
        String accessToken = getAccessToken(token);
        if (accessToken == null) {
            model.addAttribute("error", "Access token not available.");
            return "redirect:/contacts";
        }

        try {
            Credential credential = new GoogleCredential().setAccessToken(accessToken);
            PeopleService peopleService = peopleServiceHelper.getPeopleService(credential);
            peopleServiceHelper.createContact(peopleService, newContact);
        } catch (GeneralSecurityException | IOException e) {
            System.err.println("Error adding contact: " + e.getMessage());
            model.addAttribute("error", "Error adding contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(OAuth2AuthenticationToken token, @PathVariable String contactId, @RequestBody Person updatedContact, Model model) {
        String accessToken = getAccessToken(token);
        if (accessToken == null) {
            model.addAttribute("error", "Access token not available.");
            return "redirect:/contacts";
        }

        try {
            Credential credential = new GoogleCredential().setAccessToken(accessToken);
            PeopleService peopleService = peopleServiceHelper.getPeopleService(credential);
            peopleServiceHelper.updateContact(peopleService, contactId, updatedContact);
        } catch (GeneralSecurityException | IOException e) {
            System.err.println("Error updating contact: " + e.getMessage());
            model.addAttribute("error", "Error updating contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }

    @PostMapping("/delete/{contactId}")
    public String deleteContact(OAuth2AuthenticationToken token, @PathVariable String contactId, Model model) {
        String accessToken = getAccessToken(token);
        if (accessToken == null) {
            model.addAttribute("error", "Access token not available.");
            return "redirect:/contacts";
        }

        try {
            Credential credential = new GoogleCredential().setAccessToken(accessToken);
            PeopleService peopleService = peopleServiceHelper.getPeopleService(credential);
            peopleServiceHelper.deleteContact(peopleService, contactId);
        } catch (GeneralSecurityException | IOException e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            model.addAttribute("error", "Error deleting contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }
}
