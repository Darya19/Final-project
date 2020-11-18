package com.epam.enrollee.validatortest;

import com.epam.enrollee.dataprovider.StaticDataProvider;
import com.epam.enrollee.validator.ProjectValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class ProjectValidatorTest {

    ProjectValidator validator = new ProjectValidator();

    @BeforeClass
    public void setUp() {
        validator = new ProjectValidator();
    }

    @Test(dataProvider = "correct parameters", dataProviderClass = StaticDataProvider.class)
    public void validateRegistrationDataTrueTest(Map<String, String> parameters) {
        Map<String, String> actual = validator.validateRegistrationData(parameters);
        assertFalse(actual.containsValue(""));
    }

    @Test(dataProvider = "incorrect parameters", dataProviderClass = StaticDataProvider.class)
    public void validateRegistrationDataFalseTest(Map<String, String> parameters) {
        Map<String, String> actual = validator.validateRegistrationData(parameters);
        assertTrue(actual.containsValue(""));
    }

    @Test
    public void isEmailValidTrueTest() {
        String email = "pop.gt@mail.ru";
        assertTrue(validator.isEmailValid(email));
    }

    @Test
    public void isEmailValidFalseTest() {
        String email = "pop.mail.ru";
        assertFalse(validator.isEmailValid(email));
    }

    @Test
    public void isStringParameterValidTrueTest() {
        String parameter = "Ekaterina";
        assertTrue(validator.isStringParameterValid(parameter));
    }

    @Test
    public void isStringParameterValidFalseTest() {
        String parameter = "Eka.1ina";
        assertFalse(validator.isStringParameterValid(parameter));
    }

    @Test
    public void isIntParameterValidTrueTest() {
        String parameter = "546";
        assertTrue(validator.isIntParameterValid(parameter));
    }

    @Test
    public void isIntParameterValidFalseTest() {
        String parameter = "54.h6";
        assertFalse(validator.isIntParameterValid(parameter));
    }

    @Test
    public void isMarkValidTrueTest() {
        String parameter = "85";
        assertTrue(validator.isMarkValid(parameter));
    }

    @Test
    public void isMarkValidFalseTest() {
        String parameter = "jh";
        assertFalse(validator.isMarkValid(parameter));
    }

    @Test
    public void isMarkValidMinValueFalseTest() {
        String parameter = "-1";
        assertFalse(validator.isMarkValid(parameter));
    }

    @Test
    public void isMarkValidMaxValueFalseTest() {
        String parameter = "101";
        assertFalse(validator.isMarkValid(parameter));
    }

    @Test
    public void validateDescriptionTest() {
        String expected = "<,>";
        String actual = validator.validateDescription("<,>");
        assertEquals(actual, expected);
    }
}








