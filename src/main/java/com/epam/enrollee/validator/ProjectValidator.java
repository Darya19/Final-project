package com.epam.enrollee.validator;

import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.util.NumberParser;

import java.util.Map;

/**
 * The type Project validator.
 * Validate input from user parameters. Parameters come in request from user.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ProjectValidator {

    /**
     * The constant instance.
     */
    public static ProjectValidator instance;
    private static final String EMPTY_VALUE = "";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$";
    private static final String PASSWORD_PATTERN = "[a-zA-Z0-9@#$%!]{8,40}";
    private static final String STRING_PARAMETER_PATTERN = "([a-zA-ZА-Яа-я\\s]{2,40})";
    private static final String INT_PARAMETER_PATTERN = "[0-9]{1,7}";
    private static final String PASSPORT_PATTERN = "(^[A-Z]{2}[0-9]{7}$)";
    private static final String PERSONAL_NUMBER_PATTERN = "(^[0-9A-Z]{14}$)";
    private static final String MARK_PATTERN = "([0-9]{1,3})";
    private static final int MARK_MAX_VALUE = 100;
    private static final int MARK_MIN_VALUE = 0;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ProjectValidator getInstance() {
        if (instance == null) {
            instance = new ProjectValidator();
        }
        return instance;
    }

    /**
     * Validate registration data map.
     *
     * @param parameters the parameters
     * @return the map
     */
    public Map<String, String> validateRegistrationData(Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals(MapKeys.LAST_NAME) || key.equals(MapKeys.FIRST_NAME)
                    || key.equals(MapKeys.MIDDLE_NAME)) {
                if (!isStringParameterValid(value)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }

            if (key.equals(MapKeys.SUBJECT_ID_1) || key.equals(MapKeys.SUBJECT_ID_2) || key.equals(MapKeys.SUBJECT_ID_3)
                    || key.equals(MapKeys.SUBJECT_ID_4) || key.equals(MapKeys.SPECIALTY_ID) || key.equals(MapKeys.FACULTY_ID)) {
                if (!isIntParameterValid(value)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }
            if (key.equals(MapKeys.EMAIL)) {
                if (!isEmailValid(value)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }
            if (key.equals(MapKeys.PASSWORD) || key.equals(MapKeys.REPEATED_PASSWORD)) {
                if (!value.matches(PASSWORD_PATTERN)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }
            if (key.equals(MapKeys.PASSPORT_SERIES_AND_NUMBER)) {
                if (!value.matches(PASSPORT_PATTERN)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }
            if (key.equals(MapKeys.MARK_1) || key.equals(MapKeys.MARK_2)
                    || key.equals(MapKeys.MARK_3) || key.equals(MapKeys.MARK_4)) {
                if (!isMarkValid(value)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }
            if (key.equals(MapKeys.PERSONAL_NUMBER)) {
                if (!value.matches(PERSONAL_NUMBER_PATTERN)) {
                    parameters.put(key, EMPTY_VALUE);
                }
            }
        }
        return parameters;
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isEmailValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    /**
     * Is string parameter valid boolean.
     *
     * @param stringParameter the string parameter
     * @return the boolean
     */
    public boolean isStringParameterValid(String stringParameter) {
        return stringParameter.matches(STRING_PARAMETER_PATTERN);
    }

    /**
     * Is int parameter valid boolean.
     *
     * @param intParameter the int parameter
     * @return the boolean
     */
    public boolean isIntParameterValid(String intParameter) {
        return intParameter.matches(INT_PARAMETER_PATTERN);
    }

    /**
     * Is mark valid boolean.
     *
     * @param mark the mark
     * @return the boolean
     */
    public boolean isMarkValid(String mark) {
        NumberParser parser = new NumberParser();
        if (mark.matches(MARK_PATTERN)) {
            int intMark = parser.parseToInt(mark);
            return intMark >= MARK_MIN_VALUE && intMark <= MARK_MAX_VALUE;
        } else {
            return false;
        }
    }

    /**
     * Validate description string.
     *
     * @param description the description
     * @return the string
     */
    public String validateDescription(String description) {
        description.replaceAll("<", "&lt").replaceAll(">", "&gt");
        return description;
    }
}
