package com.hrms.web.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public boolean validatePhone(String value) {
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            if (Character.isDigit(value.charAt(i))) {
                continue;
            }
            return false;
        }
        return true;
    }

    public boolean validateStringAll(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean validateStringOnlyChar(String value) {
        value = value.trim();
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetter(value.charAt(i))) {
                if ((int) value.charAt(i) == 32) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public boolean validateInteger(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        if (!value.matches("[0-9]*")) {
            return false;
        }
        return true;
    }

    public boolean validateDoubleAll(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        String alldouble = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
        if (!value.matches(alldouble)) {
            return false;
        }
        String period = ".";
        int len = value.length();
        int result = 0;
        if (len > 0) {
            int start = value.indexOf(period);
            while (start != -1) {
                result++;
                start = value.indexOf(period, start + 1);
            }
            if (result > 1) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean validateDoublePositive(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        String nonegativedouble = "((\\+)?[0-9]+(\\.[0-9]+)?)+";
        if (!value.matches(nonegativedouble)) {
            return false;
        }
        String period = ".";
        int len = value.length();
        int result = 0;
        if (len > 0) {
            int start = value.indexOf(period);
            while (start != -1) {
                result++;
                start = value.indexOf(period, start + 1);
            }
            if (result > 1) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean validateEmail(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        if (!value.matches("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            return false;
        }
        Pattern p = Pattern.compile("^\\.|^\\@");
        Matcher m = p.matcher(value);
        if (m.find()) {
            //System.err.println("Email addresses don't start with dots or @ signs.");
            return false;
        }
        p = Pattern.compile("^www\\.");
        m = p.matcher(value);
        if (m.find()) {
            ////System.out.println("Email addresses don't start with \"www.\", only web pages do.");
            return false;
        }
        p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
        m = p.matcher(value);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        boolean deletedIllegalChars = false;

        while (result) {
            deletedIllegalChars = true;
            m.appendReplacement(sb, "");
            result = m.find();
        }
        m.appendTail(sb);
        value = sb.toString();
        if (deletedIllegalChars) {
            //System.out.println("It contained incorrect characters , such as spaces or commas.");
            return false;
        }
        return true;
    }

    public boolean validateDate_dd_mm_yyyy(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        String nonegativedouble = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        if (!value.matches(nonegativedouble)) {
            return false;
        }
        return true;
    }

    public boolean validate12HourTime(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        if (!value.matches("(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)")) {
            return false;
        }
        return true;
    }

    public boolean validate24HourTime(String value) {
        if (value == null) {
            return false;
        }
        if (value.matches("[0-9:]*") && value.length() == 5 && value.charAt(2) == ':') {
            int hour = Integer.parseInt(value.substring(0, 2));
            int minute = Integer.parseInt(value.substring(3));
            if (hour < 0 || minute < 0) {
                return false;
            }
            if (minute >= 60) {
                return false;
            }
            if (hour >= 24) {

                return false;
            }
        } else {

            return false;
        }
        if (!value.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            return false;
        }
        return true;
    }
}
