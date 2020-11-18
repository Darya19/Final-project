package com.epam.enrollee.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Password encryptor.
 * Find hash code for string password.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class PasswordEncryptor {

    private static final int BYTES_IN_HASH_VALUE = 16;
    private static final int DIGITS_AMOUNT_IN_HASH = 32;

    /**
     * Encrypt password string.
     * Static method for encrypting password.
     *
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        byte[] digest;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        digest = messageDigest.digest();
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(BYTES_IN_HASH_VALUE);
        while (md5Hex.length() < DIGITS_AMOUNT_IN_HASH) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
