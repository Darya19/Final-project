package com.epam.enrollee.parser;

public class NumberParser {

    public int parseToInt(String value) throws NumberFormatException{
            int intValue = Integer.parseInt(value);
            return intValue;
    }
}
