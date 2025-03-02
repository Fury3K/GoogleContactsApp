package com.example.google_contacts_app.controller;

import com.example.google_contacts_app.service.PeopleServiceHelper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class GoogleContactsController {

    private final PeopleServiceHelper peopleServiceHelper;

    public GoogleContactsController(PeopleServiceHelper peopleServiceHelper) {
        this.peopleServiceHelper = peopleServiceHelper;
    }

    @GetMapping("/contacts")
    public String getContacts(OAuth2AuthenticationToken token, Model model) {
        OidcUser user = (OidcUser) token.getPrincipal(); // Retrieve OpenID user
        String accessToken = user.getIdToken().getTokenValue(); // Extract access token

        try {
            // Create credentials using GoogleCredential
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
            
            // Initialize PeopleService
            PeopleService peopleService = peopleServiceHelper.getPeopleService(credential);
            
            // Fetch contacts
            List<Person> contacts = peopleServiceHelper.getContacts(peopleService);
            
            // Add to model
            model.addAttribute("contacts", contacts);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching contacts: " + e.getMessage());
        }

        return "contacts";
    }
}
