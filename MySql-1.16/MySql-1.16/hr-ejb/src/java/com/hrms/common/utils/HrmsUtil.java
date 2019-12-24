package com.hrms.common.utils;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class HrmsUtil {

    private static final Logger logger = Logger.getLogger(HrmsUtil.class);
    private static SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");

    public static long dayDiff(Date fromDate, Date toDate) {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fromDate);
        cal2.setTime(toDate);

        long fromMiliSecond = cal1.getTimeInMillis();
        long toMiliSecond = cal2.getTimeInMillis();
        long diff = toMiliSecond - fromMiliSecond;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

    public static int monthDiff(Date fromDt, Date toDt) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fromDt);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(toDt);

        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        if (cal2.get(Calendar.YEAR) > cal1.get(Calendar.YEAR)) {
            int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
            month2 = month2 + yearDiff * 12;
        }
        int diff = month2 - month1;
        return diff;
    }

    public static double round(double d, int places) {
        return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double) places);
    }

    public static String getAbbreviation(String str) {
        String abb = "" + str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == 32) {
                abb += (str.charAt(i + 1));
            }
        }
        return abb.toUpperCase();
    }

    public static String dateAdd(String dt, int noOfDays) throws DAOException {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.DATE, noOfDays);
            dt = ymmd.format(calendar.getTime());
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return dt;
    }

    public static String monthAdd(String dt, int noOfMonth) throws DAOException {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.MONTH, noOfMonth);
            dt = ymmd.format(calendar.getTime());
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return dt;
    }

    public static String yearAdd(String dt, int noOfYear) throws DAOException {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.YEAR, noOfYear);
            dt = ymmd.format(calendar.getTime());
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return dt;
    }

    public static int datePart(String option, String date) throws DAOException {
        int dtPart = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(date);
            calendar.setTime(frDate);
            if (option.equalsIgnoreCase("D")) {
                dtPart = calendar.get(Calendar.DATE);
            } else if (option.equalsIgnoreCase("M")) {
                dtPart = calendar.get(Calendar.MONTH) + 1;
            } else {
                dtPart = calendar.get(Calendar.YEAR);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return dtPart;
    }

    public static String correct24HourTime(String input) throws DAOException {
        String output = "";
        try {
            String hour = input.substring(0, input.indexOf(":"));
            String minute = input.substring(input.indexOf(":") + 1, input.indexOf(":") + 3);
            if (hour.length() != 2) {
                hour = "0" + hour;
            }
            if (minute.length() != 2) {
                minute = "0" + minute;
            }
            output = hour + ":" + minute;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return output;
    }

    public static String correct12HourTime(String input) throws DAOException {
        String output = "";
        try {
            String hour = input.substring(0, input.indexOf(":"));
            if (hour.equalsIgnoreCase("12")) {
                hour = "00";
            }
            String minute = input.substring(input.indexOf(":") + 1, input.indexOf(":") + 3);
            String AmPm = input.substring(input.lastIndexOf(" ") + 1);
            if (hour.length() != 2) {
                hour = "0" + hour;
            }
            if (minute.length() != 2) {
                minute = "0" + minute;
            }
            output = hour + ":" + minute + " " + AmPm;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return output;
    }

    /**
     * return no of days in month for the year
     * @param month
     * @param year
     * @return
     */
    public static int numOfDays(int month, int year) {
        if (month == 2) {
            if ((year % 4 == 0) && (year % 400 == 0) && !(year % 100 == 0)) {
                return 29;
            } else {
                return 28;
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else {
            return 30;
        }
    }
    
    public static String getMonthName(int month) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        return monthName[month];
    }
    
    public static String lPadding(int size, int number) {
        String format = "%0" + size + "d";
        return String.format(format, number);
    }
}
