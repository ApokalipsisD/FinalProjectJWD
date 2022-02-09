package com.epam.jwd.service.passwordHashing.api;

/**
 * Interface that provides methods to encode and decode strings
 */
public interface PasswordManager {

    /**
     * Method which encode string using Base64 algorithms
     *
     * @param password string to encode
     * @return encoded string
     */
    String encode(String password);

    /**
     * Method for decoding inputed string
     *
     * @param password string to decode
     * @return decoded string
     */
    String decode(String password);
}
