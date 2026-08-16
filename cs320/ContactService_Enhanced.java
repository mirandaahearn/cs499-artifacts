package com.milestonethree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactService {

    // Primary structure for fast ID-based lookups
    private final Map<String, Contact> contacts = new HashMap<>();

    // Secondary structure for sorted retrieval by last name
    // Added to address the gap in the original design where
    // there was no way to return contacts in alphabetical order
    private final TreeMap<String, Contact> sortedContacts = new TreeMap<>();

    // Builds the key used in the TreeMap
    // Last name is lowercased so sorting is case-insensitive
    // ContactID is appended to keep keys unique when last names match
    private String buildSortedKey(String lastName, String contactID) {
        return lastName.toLowerCase() + "_" + contactID;
    }

    // Adds a new contact
    // Both structures are updated together to stay in sync
    public String addContact(String contactID, String firstName, String lastName, String number, String address) {
        if (contacts.containsKey(contactID)) {
            return "Error: Contact ID " + contactID + " already exists.";
        }
        Contact contact = new Contact(contactID, firstName, lastName, number, address);
        contacts.put(contactID, contact);
        sortedContacts.put(buildSortedKey(lastName, contactID), contact);
        return "Contact " + contactID + " added successfully.";
    }

    // Deletes a contact by ID
    // Removed from both the HashMap and TreeMap
    public String deleteContact(String contactID) {
        Contact contact = contacts.remove(contactID);
        if (contact == null) {
            return "Error: Contact ID " + contactID + " does not exist.";
        }
        sortedContacts.remove(buildSortedKey(contact.getLastName(), contactID));
        return "Contact " + contactID + " deleted successfully.";
    }

    // Updates first name only
    public String updateContactFirstName(String contactID, String firstName) {
        Contact contact = contacts.get(contactID);
        if (contact == null) {
            return "Error: Contact ID " + contactID + " does not exist.";
        }
        if (firstName != null && !firstName.trim().isEmpty()) {
            contact.setFirstName(firstName);
        } else {
            return "Error: First name must not be null or empty.";
        }
        return "Contact " + contactID + " first name updated successfully.";
    }

    // Updates last name only
    // The TreeMap key has to be removed and re-inserted when the last name changes
    // otherwise the sorted order would reflect the old name
    public String updateContactLastName(String contactID, String lastName) {
        Contact contact = contacts.get(contactID);
        if (contact == null) {
            return "Error: Contact ID " + contactID + " does not exist.";
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            sortedContacts.remove(buildSortedKey(contact.getLastName(), contactID));
            contact.setLastName(lastName);
            sortedContacts.put(buildSortedKey(lastName, contactID), contact);
        } else {
            return "Error: Last name must not be null or empty.";
        }
        return "Contact " + contactID + " last name updated successfully.";
    }

    // Updates phone number only
    public String updateContactPhone(String contactID, String number) {
        Contact contact = contacts.get(contactID);
        if (contact == null) {
            return "Error: Contact ID " + contactID + " does not exist.";
        }
        if (number != null && number.matches("\\d{10}")) {
            contact.setNumber(number);
        } else {
            return "Error: Phone number must not be null and must be exactly 10 digits.";
        }
        return "Contact " + contactID + " phone number updated successfully.";
    }

    // Updates address only
    public String updateContactAddress(String contactID, String address) {
        Contact contact = contacts.get(contactID);
        if (contact == null) {
            return "Error: Contact ID " + contactID + " does not exist.";
        }
        if (address != null && !address.trim().isEmpty() && address.length() <= 30) {
            contact.setAddress(address);
        } else {
            return "Error: Address must not be null, empty, or more than 30 characters.";
        }
        return "Contact " + contactID + " address updated successfully.";
    }

    // Updates multiple fields at once
    // Same TreeMap key management applies if last name is being changed
    public String updateContact(String contactID, String firstName, String lastName, String number, String address) {
        Contact contact = contacts.get(contactID);
        if (contact == null) {
            return "Error: Contact ID " + contactID + " does not exist.";
        }
        if (firstName != null && !firstName.trim().isEmpty()) {
            contact.setFirstName(firstName);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            sortedContacts.remove(buildSortedKey(contact.getLastName(), contactID));
            contact.setLastName(lastName);
            sortedContacts.put(buildSortedKey(lastName, contactID), contact);
        }
        if (number != null && number.matches("\\d{10}")) {
            contact.setNumber(number);
        }
        if (address != null && !address.trim().isEmpty() && address.length() <= 30) {
            contact.setAddress(address);
        }
        return "Contact " + contactID + " updated successfully.";
    }

    // Direct ID lookup via HashMap — O(1) average
    public Contact getContact(String contactID) {
        return contacts.get(contactID);
    }

    // Returns all contacts sorted alphabetically by last name
    // Uses the TreeMap values which are already maintained in sorted order
    public List<Contact> getContactsSortedByLastName() {
        return new ArrayList<>(sortedContacts.values());
    }
}
