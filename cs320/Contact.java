package com.milestonethree;

public class Contact {
    private final String contactID; // Unique identifier for each contact. Immutable
    private String firstName;
    private String lastName;
    private String number;
    private String address;

    // Constructor for initializing Contact objects
    public Contact(String contactID, String firstName, String lastName, String number, String address) {
        // Validation checks for input parameters
        if (contactID == null || contactID.length() > 10) {
            throw new IllegalArgumentException("Contact ID must not be null and must be at most 10 characters");
        }
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name must not be null and must be at most 10 characters");
        }
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name must not be null and must be at most 10 characters");
        }
        if (number == null || !number.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must not be null and must be exactly 10 digits");
        }
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address must not be null and must be at most 30 characters");
        }

        // Initialize instance variables with provided values
        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.address = address;
    }

    // Getters
    public String getContactID() {
        return contactID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    // Setters to update the values of mutable instance variables
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name must not be null and must be at most 10 characters");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name must not be null and must be at most 10 characters");
        }
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        if (number == null || !number.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must not be null and must be exactly 10 digits");
        }
        this.number = number;
    }

    public void setAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address must not be null and must be at most 30 characters");
        }
        this.address = address;
    }
}
