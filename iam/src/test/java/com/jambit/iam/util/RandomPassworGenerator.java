package com.jambit.iam.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 6:52 PM
 */
public final class RandomPassworGenerator {

    private RandomPassworGenerator() {
    }

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final Random RANDOM = new SecureRandom();
    private static final int PASSWORD_LENGTH = 10;

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();

        password.append(getRandomChar(UPPERCASE));
        password.append(getRandomChar(LOWERCASE));
        password.append(getRandomChar(DIGITS));
        password.append(getRandomChar(SPECIAL_CHARACTERS));

        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(getRandomChar(ALL_CHARACTERS));
        }

        return shuffleString(password.toString());
    }

    private static char getRandomChar(String characters) {
        return characters.charAt(RANDOM.nextInt(characters.length()));
    }

    private static String shuffleString(String input) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
        return new String(array);
    }
}

