package com.epam.enrollee.util;

/**
 * The type Number parser.
 * Parse string parameters to int.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class NumberParser {

    /**
     * The constant instance.
     */
    public static NumberParser instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NumberParser getInstance() {
        if (instance == null) {
            instance = new NumberParser();
        }
        return instance;
    }

    /**
     * Parse to int int.
     *
     * @param value the value
     * @return the int
     */
    public int parseToInt(String value) {
        int intValue = Integer.parseInt(value);
        return intValue;
    }
}
