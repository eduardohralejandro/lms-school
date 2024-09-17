package com.lms_schoolapp.greenteam.util;

import org.mindrot.jbcrypt.BCrypt;

public class AuthPasswordUtility {

    /**
     * Define the strength of the hashing (the higher the value, the more secure, but slower the process)
     */
    private static final int WORKLOAD = 12;

    /**
     * Hash a password using BCrypt.
     *
     * @param plainTextPassword The plain text password to hash
     * @return The hashed password
     */
    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    /**
     * Verify that a given plain text password matches a hashed password.
     *
     * @param plainTextPassword The plain text password to check
     * @param hashedPassword    The hashed password to compare against
     * @return True if the passwords match, false otherwise
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
