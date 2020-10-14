package com.epam.enrollee.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    private final static int BYTES_IN_HASH_VALUE = 16;
    private final static int DIGITS_AMOUNT_IN_HASH = 32;

    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(BYTES_IN_HASH_VALUE);
        while( md5Hex.length() < DIGITS_AMOUNT_IN_HASH){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
