package com.jambit.iam.util;

import java.util.Random;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 6:53 PM
 */
public final class RandomEmailGenerator {

    private RandomEmailGenerator() {}
    
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com"};
    private static final Random RANDOM = new Random();

    public static String generateEmail() {
        String name = "user" + RANDOM.nextInt(1000);
        String domain = EMAIL_DOMAINS[RANDOM.nextInt(EMAIL_DOMAINS.length)];
        return name + "@" + domain;
    }
}
