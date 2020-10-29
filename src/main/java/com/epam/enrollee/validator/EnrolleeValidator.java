package com.epam.enrollee.validator;

import com.epam.enrollee.parser.NumberParser;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.enrollee.controller.command.RequestParameters.*;

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
            "([0-9])";
    private final String PASSPORT_PATTERN =
            "([A-Z]{2}[0-9]{7})";
    private static final String PERSONAL_NUMBER_PATTERN =
            "([0-9]{7}[A-Z][1-9]{3}[A-Z]{2}[1-9])";
    private static final String ADDRESS_NUMBER_PATTERN =
            "([0-9/]{1,7})";
    private static final String PHONE_NUMBER_PATTERN =
            "([0-9()]{7,9})";
    private static final String MARK_PATTERN =
            "([0-9]{1,3})";
    private static final int MARK_MAX_VALUE = 100;
    private static final int MARK_MIN_VALUE = 0;

    public boolean isEmailValid(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Map<String, String> validateRegistrationData(Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals(LAST_NAME) || key.equals(FIRST_NAME) || key.equals(MIDDLE_NAME)
                    || key.equals(COUNTRY) || key.equals(CITY) || key.equals(STREET)) {
                if (!isStringParameterValid(value)) {
                    parameters.put(key, "");
                }
            }

            if (key.equals(SUBJECT_ID_1) || key.equals(SUBJECT_ID_2) || key.equals(SUBJECT_ID_3) ||
                    key.equals(SPECIALTY_ID) || key.equals(FACULTY_ID)) {
                if (!isIntParameterValid(key)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(EMAIL)) {
                if (!isEmailValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(PASSWORD) || key.equals(REPEATED_PASSWORD)) {
                if (!isPasswordValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(PASSPORT_SERIES_AND_NUMBER)) {
                if (!isPassportValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(MARK_1) || key.equals(MARK_2)
                    || key.equals(MARK_3) || key.equals(MARK_4)) {
                if (!isMarkValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(PERSONAL_NUMBER)) {
                if (!isPersonalNumberValid(value)) {
                    parameters.put(key, "");
                }
            }

            if (key.equals(PHONE_NUMBER)) {
                if (!isPhoneNumberValid(value)) {
                    parameters.put(key, "");
                }
            }
            if (key.equals(HOUSE) || key.equals(FLAT)) {
                if (!isAddressNumberValid(value)) {
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

    private boolean isAddressNumberValid(String addressNumber) {
        if (!addressNumber.isEmpty()) {
            return addressNumber.matches(ADDRESS_NUMBER_PATTERN);
        } else {
            return false;
        }
    }

    private boolean isMarkValid(String mark) {
        NumberParser parser = new NumberParser();
        if (mark.matches(MARK_PATTERN)) {
            int intMark = parser.parseToInt(mark);
            return intMark > MARK_MIN_VALUE && intMark < MARK_MAX_VALUE;
        } else {
            return false;
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }

}
