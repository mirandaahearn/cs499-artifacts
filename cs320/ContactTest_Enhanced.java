package com.milestonethree;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ContactTest {

    private Contact contact;

    @BeforeEach
    public void setUp() {
        contact = new Contact("2468975310", "James", "Joe", "3146256225", "43 Newlands Avenue");
    }

    // existing tests

    @Test
    public void testContactConstructorInitialization() {
        assertEquals("2468975310", contact.getContactID());
        assertEquals("James", contact.getFirstName());
        assertEquals("Joe", contact.getLastName());
        assertEquals("3146256225", contact.getNumber());
        assertEquals("43 Newlands Avenue", contact.getAddress());
    }

    @Test
    public void testSetFirstName_updatesSuccessfully() {
        contact.setFirstName("Elise");
        assertEquals("Elise", contact.getFirstName());
    }

    @Test
    public void testSetLastName_updatesSuccessfully() {
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
    }

    @Test
    public void testSetNumber_updatesSuccessfully() {
        contact.setNumber("1234567890");
        assertEquals("1234567890", contact.getNumber());
    }

    @Test
    public void testSetAddress_updatesSuccessfully() {
        contact.setAddress("22 Hillside Road");
        assertEquals("22 Hillside Road", contact.getAddress());
    }

    // parameterized boundary condition tests

    @ParameterizedTest
    @ValueSource(strings = {"", "TooLongFirstName123"})
    public void testSetFirstName_invalidValues_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(input));
    }

    @ParameterizedTest
    @NullSource
    public void testSetFirstName_null_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "TooLongLastName123"})
    public void testSetLastName_invalidValues_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(input));
    }

    @ParameterizedTest
    @NullSource
    public void testSetLastName_null_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "12345678901", "abcdefghij"})
    public void testSetNumber_invalidValues_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setNumber(input));
    }

    @ParameterizedTest
    @NullSource
    public void testSetNumber_null_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setNumber(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "This address is way too long to pass validation rules"})
    public void testSetAddress_invalidValues_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(input));
    }

    @ParameterizedTest
    @NullSource
    public void testSetAddress_null_throwsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(input));
    }

    // exception message validation tests

    @Test
    public void testSetFirstName_null_correctMessage() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Contact("ID1", null, "Joe", "1234567890", "43 Newlands Avenue"));
        assertEquals("First name must not be null and must be at most 10 characters", ex.getMessage());
    }

    @Test
    public void testSetLastName_tooLong_correctMessage() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            contact.setLastName("TooLongLastNameValue"));
        assertEquals("Last name must not be null and must be at most 10 characters", ex.getMessage());
    }

    @Test
    public void testSetNumber_invalidFormat_correctMessage() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            contact.setNumber("123"));
        assertEquals("Phone number must not be null and must be exactly 10 digits", ex.getMessage());
    }

    @Test
    public void testSetAddress_tooLong_correctMessage() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            contact.setAddress("This address is way too long to pass validation rules"));
        assertEquals("Address must not be null and must be at most 30 characters", ex.getMessage());
    }

    @Test
    public void testConstructor_nullContactID_correctMessage() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Contact(null, "James", "Joe", "1234567890", "43 Newlands Avenue"));
        assertEquals("Contact ID must not be null and must be at most 10 characters", ex.getMessage());
    }
}
