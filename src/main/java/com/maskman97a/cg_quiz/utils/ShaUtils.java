package com.maskman97a.cg_quiz.utils;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtils {
    private ShaUtils() {

    }

    @SneakyThrows
    public static String sha1Hash(String input) throws NoSuchAlgorithmException {
        // Get an instance of the SHA-1 MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // Digest the input string and get the hash bytes
        byte[] messageDigest = md.digest(input.getBytes());

        // Convert the byte array into a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
