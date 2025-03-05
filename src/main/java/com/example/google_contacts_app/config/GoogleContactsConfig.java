package com.example.google_contacts_app.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class GoogleContactsConfig {

    @Bean
    public HttpTransport httpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    public Credential googleCredential(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        return GoogleCredential.getApplicationDefault()
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/contacts"));
    }

    @Bean
    public PeopleService peopleService(HttpTransport httpTransport, JsonFactory jsonFactory, Credential credential) {
        return new PeopleService.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google Contacts App")
                .build();
    }
}
