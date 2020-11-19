package com.epam.enrollee.utiltest;

import com.epam.enrollee.util.NumberParser;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NumberParserTest {

    NumberParser parser = new NumberParser();

    @Test
    public void parseToIntPositiveTest() {
        String number = "125";
        int actual = parser.parseToInt(number);
        int expected = 125;
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void parseToIntNegativeTest() throws NumberFormatException {
        String number = "jmh";
        parser.parseToInt(number);
    }
}