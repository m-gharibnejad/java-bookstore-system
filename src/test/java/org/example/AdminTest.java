package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    Admin admin = new Admin();

    @Test
    public void testAuthenticateWithCorrectPassword() {
        assertTrue(admin.authenticate("admin123"));
    }

    @Test
    public void testAuthenticateWithWrongPassword() {
        assertFalse(admin.authenticate("12345678"));
    }

    @Test
    public void testAuthenticateNullPassword() {
        assertFalse(admin.authenticate(null));
    }



}