package com.epam.enrollee.validator;

import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.MapKeys;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnrolleeValidator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "[a-zA-Z0-9@#$%!]{8,40}";
    private static final String STRING_PARAMETER_PATTERN =
            "([a-zA-Z]{2,30})";
    private static final String INT_PARAMETER_PATTERN =
            "[0-9]";
    private final String PASSPORT_PATTERN =
            "(^[A-Z]{2}[0-9]{7}$)";
    private static final String PERSONAL_NUMBER_PATTERN =
            "(^[0-9A-Z]{14}$)";
    private static final String MARK_PATTERN =
            "([0-9]{1,3})";
    private static final int MARK_MAX_VALUE = 100;
    private static final int MARK_MIN_VALUE = 0;

    public boolean isEmailValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public Map<String, String> validateRegistrationData(Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals(MapKeys.LAST_NAME) || key.equals(MapKeys.FIRST_NAME) || key.equals(MapKeys.MIDDLE_NAME)) {
                if (!isStringParameterValid(value)) {
                    parameters.put(key, "");
                }
            }

            if (key.equals(MapKeys.SUBJECT_ID_1) || key.equals(MapKeys.SUBJECT_ID_2)
                    || key.equals(MapKeys.SUBJECT_ID_3) || key.equals(MapKeys.SPECIALTY_ID)
                    || key.equals(MapKeys.FACULTY_ID)) {
                if (!isIntParameterValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(MapKeys.EMAIL)) {
                if (!isEmailValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(MapKeys.PASSWORD) || key.equals(MapKeys.REPEATED_PASSWORD)) {
                if (!isPasswordValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(MapKeys.PASSPORT_SERIES_AND_NUMBER)) {
                if (!isPassportValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(MapKeys.MARK_1) || key.equals(MapKeys.MARK_2)
                    || key.equals(MapKeys.MARK_3) || key.equals(MapKeys.MARK_4)) {
                if (!isMarkValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(MapKeys.PERSONAL_NUMBER)) {
                if (!isPersonalNumberValid(value)) {
                    parameters.put(key, "");
                }
            }
        }
        return parameters;
    }

    private boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    private boolean isStringParameterValid(String stringParameter) {
        return stringParameter.matches(STRING_PARAMETER_PATTERN);
    }

    private boolean isIntParameterValid(String intParameter) {
        return intParameter.matches(INT_PARAMETER_PATTERN);
    }

    private boolean isPassportValid(String passport) {
        return passport.matches(PASSPORT_PATTERN);
    }

    private boolean isPersonalNumberValid(String personalNumber) {
        return personalNumber.matches(PERSONAL_NUMBER_PATTERN);
    }

    public boolean isMarkValid(String mark) {
        NumberParser parser = new NumberParser();
        if (mark.matches(MARK_PATTERN)) {
            int intMark = parser.parseToInt(mark);
            return intMark > MARK_MIN_VALUE && intMark < MARK_MAX_VALUE;
        } else {
            return false;
        }
    }
}
