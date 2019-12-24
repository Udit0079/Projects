package com.cbs.utils;

import com.cbs.exception.ExceptionCode;
import com.cbs.exception.ValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public boolean validateString(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '#' || value.charAt(i) == '~') {
                return false;
            }
        }
        return true;
    }

    public boolean validateAddress(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '-' || ch == ',' || ch == '/' || Character.isSpaceChar(ch)) {
                continue;
            } else if (i > 0) {
                if (!Character.isLetterOrDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateDocument(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '-' || ch == ',' || ch == '/' || Character.isSpaceChar(ch)) {
                continue;
            } else if (ch == '#') {
                return false;
            } else if (i > 0) {
                if (!Character.isLetterOrDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateMobile(String value) {
        if (value == null) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '+' && i == 0) {
                continue;
            } else if (i > 0) {
                if (!Character.isDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateLandline(String value) {
        int cnt = 0;
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(0) == '-') {
                return false;
            }
            if (Character.isDigit(value.charAt(i))) {
                continue;
            }
            if (value.charAt(i) == '-') {
                cnt++;
                if (cnt > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateUserId(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                if ((int) value.charAt(i) == 95) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public boolean validateStringAll(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                if ((int) value.charAt(i) == 32) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public boolean validateStringAllNoSpace(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean validateStringOnlyChar(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
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
        value = value.trim();
        if (!value.matches("[0-9]*")) {
            return false;
        }
        return true;
    }

    public boolean validateDoubleAll(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
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
        value = value.trim();
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
        value = value.trim();
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
            return false;
        }
        return true;
    }

    public boolean validateDate_dd_mm_yyyy(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        String pattern = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20|21)\\d\\d)";
        if (!value.matches(pattern)) {
            return false;
        }
        return true;
    }

    public boolean validate12HourTime(String value) {
        if (value == null || value.equalsIgnoreCase("")) {
            return false;
        }
        value = value.trim();
        if (!value.matches("(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)")) {
            return false;
        }
        return true;
    }

    public boolean validate24HourTime(String value) {
        if (value == null) {
            return false;
        }
        value = value.trim();
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

    public static boolean validateDate(String date) {
        String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        if (date == null || date.equalsIgnoreCase("")) {
            return false;
        }
        date = date.trim();
        Pattern pattern = Pattern.compile(DATE_PATTERN);

        Matcher matcher = pattern.matcher(date);

        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31")
                        && (month.equals("4") || month.equals("6") || month.equals("9")
                        || month.equals("11") || month.equals("04") || month.equals("06")
                        || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0 || year % 400 == 0 ) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateDateYYYYMMDD(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    
    /**
	 * @param object
	 * @throws ValidationException
	 */
	public static void isNull(Object object) throws ValidationException {
		if (object == null) {
			throw new ValidationException(new ExceptionCode(ExceptionCode.OBJECT_IS_NULL, " Object is null"));
		}
	}

	/**
	 * @param object
	 * @param exceptionCode
	 * @throws ValidationException
	 */
	public static void isNull(Object object, ExceptionCode exceptionCode) throws ValidationException {
		if (object == null) {
			throw new ValidationException(exceptionCode);
		}
	}

	/**
	 * @param object
	 * @throws ValidationException
	 */
	public static void isNotNull(Object object) throws ValidationException {
		if (object != null) {
			throw new ValidationException(new ExceptionCode(ExceptionCode.OBJECT_IS_NOT_NULL,
				" Object is not null Object = " + object));
		}
	}

	/**
	 * @param object
	 * @param exceptionCode
	 * @throws ValidationException
	 */
	public static void isNotNull(Object object, ExceptionCode exceptionCode) throws ValidationException {
		if (object != null) {
			throw new ValidationException(exceptionCode);
		}
	}
}
