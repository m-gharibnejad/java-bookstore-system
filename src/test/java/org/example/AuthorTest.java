package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {
    Author author = new Author();

    @Test
    public void testAuthenticateWithCorrectPassword() {
        assertTrue(author.authenticate("author123"));
    }

    @Test
    public void testAuthenticateWithWrongPassword() {
        assertFalse(author.authenticate("1234567"));
    }

    @Test
    public void testAuthenticateWithNull() {
        assertFalse(author.authenticate(null));
    }
}