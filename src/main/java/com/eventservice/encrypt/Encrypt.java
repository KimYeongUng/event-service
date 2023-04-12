package com.eventservice.encrypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    public static String sha512(String passwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(passwd.getBytes(StandardCharsets.UTF_8));

        return String.format("%064x", new BigInteger(1, md.digest()));
    }
}
