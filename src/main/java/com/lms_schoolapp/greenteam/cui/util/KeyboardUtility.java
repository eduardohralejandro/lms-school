package com.lms_schoolapp.greenteam.cui.util;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Utility class for handling keyboard input.
 * <p>
 * This class provides methods to prompt the user for different types of input via the console.
 * It uses a single static Scanner instance to read from System.in.
 * <p>
 * Example usage:
 * <pre>
 *     String name = KeyboardUtility.askForString("Enter your name: ");
 *     int age = KeyboardUtility.askForInt("Enter your age: ");
 *     double salary = KeyboardUtility.askForDouble("Enter your salary: ");
 *     KeyboardUtility.shutdown();
 * </pre>
 * <p>
 * Note: Remember to call {@link #shutdown()} to close the Scanner when all input operations are complete.
 * </p>
 *
 * @param <T> The type parameter used in generic methods.
 * @author Eduardo Hernández
 * @version 1.0
 * @see KeyboardUtility
 */
public class KeyboardUtility<T> {
    private static final Scanner KEYBOARD = new Scanner(System.in);

    /**
     * Reads a line of text from the console.
     *
     * @return The line of text entered by the user.
     */
    public static String readString() {
        while (true) {
            try {
                if (KEYBOARD.hasNextLine()) {
                    return KEYBOARD.nextLine();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while reading input: " + e.getMessage());
            }
        }
    }

    /**
     * Prompts the user with a message and reads a string input from the console.
     *
     * @param message The prompt message to display to the user.
     * @return The string entered by the user.
     */
    public static String askForString(String message) {
        System.out.print(message);
        return readString();
    }

    /**
     * Prompts the user with a message and reads an integer input from the console.
     *
     * @param message The prompt message to display to the user.
     * @return The integer entered by the user.
     * @throws RuntimeException If the input is not a valid integer.
     */
    public static Integer askForInt(String message) throws RuntimeException {
        while (true) {
            String input = askForString(message);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user with a message and reads a double input from the console.
     *
     * @param message The prompt message to display to the user.
     * @return The double entered by the user.
     * @throws RuntimeException If the input is not a valid integer.
     */
    public static Double askForDouble(String message) throws RuntimeException {
        while (true) {
            String input = askForString(message);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user to select an index from a generic array.
     * Lists each element in the array with a numbered list and asks the user to enter a choice.
     *
     * @param <T>   The type of the elements in the array.
     * @param array The array from which to choose an element.
     * @return The index chosen by the user.
     */
    public static <T> int askForIndexInArray(T[] array) throws RuntimeException, RuntimeException {
        for (int i = 0; i < array.length; i++) {
            System.out.print((i + 1) + ". " + array[i] + "\n");
        }
        final int INDEX_OFFSET = 1;
        while (true) {
            try {
                int index = askForInt("Enter a choice (1-" + array.length + "): \n") - INDEX_OFFSET;
                if (index >= 0 && index < array.length) {
                    return index;
                } else {
                    throw new RuntimeException("Invalid choice. Please enter a number between 1 and " + array.length + ".");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prompts the user to select an element from the given array by displaying a list of options.
     * Continuously prompts the user until a valid choice is made.
     *
     * @param <T>   The type of the elements in the array.
     * @param array The array from which the user will select an element.
     * @return The element from the array that the user selects.
     * @throws RuntimeException If the user selects an invalid index.
     */
    public static <T> T askForElementInArray(T[] array) throws RuntimeException {
        while (true) {
            try {
                int index = askForIndexInArray(array);
                return array[index];
            } catch (RuntimeException e) {
                System.out.print(e.getMessage());
            }
        }
    }


    /**
     * Closes the Scanner used for reading from the console.
     * <p>
     * This method should be called when all input operations are complete to release the associated resources.
     * After calling this method, further attempts to read input using this class will result in an IllegalStateException.
     * </p>
     */
    public static void shutdown() {
        KEYBOARD.close();
    }
}

