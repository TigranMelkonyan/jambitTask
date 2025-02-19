package com.jambit.iam.util;

import java.util.Random;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 6:54 PM
 */


public final class RandomNameGenerator {

    private RandomNameGenerator() {
    }
    
    private static final String[] FIRST_NAMES = {"John", "Jane", "Alex", "Emma", "Chris", "Olivia", "Liam", "Sophia", "James", "Ava"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Martinez", "Taylor", "Anderson", "Thomas"};

    private static final Random RANDOM = new Random();

    public static String generateName() {
        String firstName = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    public static void main(String[] args) {
        System.out.println("Random Name: " + generateName());
    }
}

