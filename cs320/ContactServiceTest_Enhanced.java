package com.milestonethree;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class ContactServiceTest {

    private ContactService contactService;

    @BeforeEach
    public void setUp() {
        contactService = new ContactService();
    }

    // existing tests

    @Test
    public void testAddContactSuccessfully() {
        String result = contactService.addContact("555", "James", "Joe", "3146256225", "43 Newlands Avenue");
        assertEquals("Contact 555 added successfully.", result);
    }

    @Test
    public void testAddDuplicateContactReturnsError() {
        contactService.addContact("101", "James", "Joe", "9876543210", "43 Newlands Avenue");
        String result = contactService.addContact("101", "Elise", "Joe", "6543210987", "43 Newlands Avenue");
        assertEquals("Error: Contact ID 101 already exists.", result);
    }

    @Test
    public void testDeleteContactSuccessfully() {
        contactService.addContact("202", "James", "Joe", "5678901234", "43 Newlands Avenue");
        String result = contactService.deleteContact("202");
        assertEquals("Contact 202 deleted successfully.", result);
    }

    @Test
    public void testDeleteNonExistentContactReturnsError() {
        String result = contactService.deleteContact("999");
        assertEquals("Error: Contact ID 999 does not exist.", result);
    }

    @Test
    public void testUpdateContactSuccessfully() {
        contactService.addContact("303", "James", "Joe", "3456789012", "43 Newlands Avenue");
        String result = contactService.updateContact("303", "Elise", "Smith", "2109876543", "43 Newlands Avenue");
        assertEquals("Contact 303 updated successfully.", result);
        Contact updated = contactService.getContact("303");
        assertEquals("Elise", updated.getFirstName());
        assertEquals("Smith", updated.getLastName());
        assertEquals("2109876543", updated.getNumber());
        assertEquals("43 Newlands Avenue", updated.getAddress());
    }

    @Test
    public void testUpdateNonExistentContactReturnsError() {
        String result = contactService.updateContact("999", "James", "Joe", "5432109876", "43 Newlands Avenue");
        assertEquals("Error: Contact ID 999 does not exist.", result);
    }

    @Test
    public void testGetExistingContactSuccessfully() {
        contactService.addContact("404", "Elise", "Joe", "6789012345", "43 Newlands Avenue");
        Contact contact = contactService.getContact("404");
        assertNotNull(contact);
        assertEquals("Elise", contact.getFirstName());
    }

    @Test
    public void testGetNonExistentContactReturnsNull() {
        Contact contact = contactService.getContact("999");
        assertNull(contact);
    }

    @Test
    public void testUpdateContactFirstNameSuccessfully() {
        contactService.addContact("505", "James", "Joe", "7890123456", "43 Newlands Avenue");
        String result = contactService.updateContactFirstName("505", "Elise");
        assertEquals("Contact 505 first name updated successfully.", result);
        assertEquals("Elise", contactService.getContact("505").getFirstName());
    }

    @Test
    public void testUpdateContactLastNameSuccessfully() {
        contactService.addContact("606", "James", "Joe", "8901234567", "43 Newlands Avenue");
        String result = contactService.updateContactLastName("606", "Smith");
        assertEquals("Contact 606 last name updated successfully.", result);
        assertEquals("Smith", contactService.getContact("606").getLastName());
    }

    @Test
    public void testUpdateContactPhoneSuccessfully() {
        contactService.addContact("707", "James", "Joe", "9012345678", "43 Newlands Avenue");
        String result = contactService.updateContactPhone("707", "1234567890");
        assertEquals("Contact 707 phone number updated successfully.", result);
        assertEquals("1234567890", contactService.getContact("707").getNumber());
    }

    @Test
    public void testUpdateContactAddressSuccessfully() {
        contactService.addContact("808", "James", "Joe", "0123456789", "43 Newlands Avenue");
        String result = contactService.updateContactAddress("808", "22 Hillside Road");
        assertEquals("Contact 808 address updated successfully.", result);
        assertEquals("22 Hillside Road", contactService.getContact("808").getAddress());
    }

    // sorted retrieval tests

    @Test
    public void testGetContactsSortedByLastName_returnsAlphabeticalOrder() {
        contactService.addContact("001", "James", "Smith", "1234567890", "43 Newlands Avenue");
        contactService.addContact("002", "Elise", "Joe", "0987654321", "43 Newlands Avenue");
        contactService.addContact("003", "Tom", "Brown", "1122334455", "43 Newlands Avenue");

        List<Contact> sorted = contactService.getContactsSortedByLastName();

        assertEquals(3, sorted.size());
        assertEquals("Brown", sorted.get(0).getLastName());
        assertEquals("Joe", sorted.get(1).getLastName());
        assertEquals("Smith", sorted.get(2).getLastName());
    }

    @Test
    public void testGetContactsSortedByLastName_emptyWhenNoContacts() {
        List<Contact> sorted = contactService.getContactsSortedByLastName();
        assertNotNull(sorted);
        assertEquals(0, sorted.size());
    }

    @Test
    public void testGetContactsSortedByLastName_updatesAfterDelete() {
        contactService.addContact("001", "James", "Joe", "1234567890", "43 Newlands Avenue");
        contactService.addContact("002", "Elise", "Brown", "0987654321", "43 Newlands Avenue");
        contactService.deleteContact("001");

        List<Contact> sorted = contactService.getContactsSortedByLastName();
        assertEquals(1, sorted.size());
        assertEquals("Brown", sorted.get(0).getLastName());
    }

    @Test
    public void testGetContactsSortedByLastName_updatesAfterLastNameChange() {
        contactService.addContact("001", "James", "Smith", "1234567890", "43 Newlands Avenue");
        contactService.addContact("002", "Elise", "Brown", "0987654321", "43 Newlands Avenue");
        contactService.updateContactLastName("001", "Adams");

        List<Contact> sorted = contactService.getContactsSortedByLastName();
        assertEquals("Adams", sorted.get(0).getLastName());
        assertEquals("Brown", sorted.get(1).getLastName());
    }

    // parameterized duplicate ID tests

    @ParameterizedTest
    @ValueSource(strings = {"101", "202", "303"})
    public void testAddDuplicateContactID_returnsError(String contactID) {
        contactService.addContact(contactID, "James", "Joe", "1234567890", "43 Newlands Avenue");
        String result = contactService.addContact(contactID, "Elise", "Joe", "0987654321", "43 Newlands Avenue");
        assertEquals("Error: Contact ID " + contactID + " already exists.", result);
    }

    // exception message validation tests

    @Test
    public void testAddContact_duplicateID_correctMessage() {
        contactService.addContact("ID1", "James", "Joe", "1234567890", "43 Newlands Avenue");
        String result = contactService.addContact("ID1", "Elise", "Joe", "0987654321", "43 Newlands Avenue");
        assertEquals("Error: Contact ID ID1 already exists.", result);
    }

    @Test
    public void testDeleteContact_nonExistent_correctMessage() {
        String result = contactService.deleteContact("NONE");
        assertEquals("Error: Contact ID NONE does not exist.", result);
    }

    @Test
    public void testUpdateContactFirstName_nonExistent_correctMessage() {
        String result = contactService.updateContactFirstName("NONE", "James");
        assertEquals("Error: Contact ID NONE does not exist.", result);
    }

    @Test
    public void testUpdateContactPhone_invalidFormat_correctMessage() {
        contactService.addContact("ID2", "James", "Joe", "1234567890", "43 Newlands Avenue");
        String result = contactService.updateContactPhone("ID2", "123");
        assertEquals("Error: Phone number must not be null and must be exactly 10 digits.", result);
    }

    @Test
    public void testUpdateContactAddress_tooLong_correctMessage() {
        contactService.addContact("ID3", "James", "Joe", "1234567890", "43 Newlands Avenue");
        String result = contactService.updateContactAddress("ID3", "This address is way too long to pass");
        assertEquals("Error: Address must not be null, empty, or more than 30 characters.", result);
    }
}
