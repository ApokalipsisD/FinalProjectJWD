package com.epam.jwd.service.passwordHashing.impl;

import com.epam.jwd.service.passwordHashing.api.PasswordManager;
import org.apache.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;

public class PasswordManagerImpl implements PasswordManager {
    @Override
    public String encode(String password) {
        return Base64.encodeBase64String(password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decode(String password) {
        StringBuilder result = new StringBuilder();
        for (int i : Base64.decodeBase64(password)) {
            result.append(Character.toString(i));
        }
        return result.toString();
    }

    @Override
    public boolean checkForIdentity(String inputPassword, String encodedPassword) {
        return inputPassword.equals(encodedPassword);
    }
}
