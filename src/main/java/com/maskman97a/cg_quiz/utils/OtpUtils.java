package com.maskman97a.cg_quiz.utils;

import java.util.Random;

public class OtpUtils {
    public static String generateOtp() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return String.valueOf(randomNumber);
    }
}
