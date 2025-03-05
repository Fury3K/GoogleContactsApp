package com.example.google_contacts_app.controller;

import com.example.google_contacts_app.service.ContactService;
import com.example.google_contacts_app.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/local-contacts")
    public String listContacts(Model model) throws IOException {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "contacts"; // Refers to `contacts.html` in Thymeleaf templates
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute Contact contact) throws IOException {
        System.out.println("🔹 Attempting to add contact: " + contact.getName() + " | " + contact.getEmail());
        contactService.addContact(contact);
        System.out.println("✅ Contact added successfully!");
        return "redirect:/contacts/local-contacts";
    }

    @PostMapping("/update/{resourceName}")
    public String updateContact(@PathVariable String resourceName, @ModelAttribute Contact contact) throws IOException {
        System.out.println("🔹 Attempting to update contact: " + resourceName);
        contactService.updateContact(resourceName, contact);
        System.out.println("✅ Contact updated successfully!");
        return "redirect:/contacts/local-contacts";
    }

    @PostMapping("/delete/{resourceName}")
    public String deleteContact(@PathVariable String resourceName) throws IOException {
        System.out.println("🔹 Attempting to delete contact: " + resourceName);
        contactService.deleteContact(resourceName);
        System.out.println("✅ Contact deleted successfully!");
        return "redirect:/contacts/local-contacts";
    }
}
