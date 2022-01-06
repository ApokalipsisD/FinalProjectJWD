package com.epam.jwd.service.passwordHashing.impl;

import com.epam.jwd.service.passwordHashing.api.PasswordManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PasswordManagerImplTest {
    private static PasswordManager passwordManager;
    private static String password;

    @BeforeAll
    static void beforeAll() {
        passwordManager = new PasswordManagerImpl();
        password = "qwerty1234567";
    }

    @Test
    void shouldGenerateNotSameStringAsInput() {
        System.out.println(passwordManager.encode("hi"));
        assertNotEquals(passwordManager.encode(password), password);
    }

    @Test
    void encodedTwoTimesPasswordsShouldBeTheSame() {
        assertEquals(passwordManager.encode(password), passwordManager.encode(password));
    }

    @Test
    void decodedAndEncodedPasswordsShouldBeTheSame() {
        String encodedPassword = passwordManager.encode(password);
        assertEquals(password, passwordManager.decode(encodedPassword));
    }
}