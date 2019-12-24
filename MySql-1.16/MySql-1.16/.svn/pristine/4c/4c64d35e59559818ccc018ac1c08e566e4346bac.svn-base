/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public enum MonthEndDate {

    JANUARY("JANUARY", "31"),
    FEBRUARY("FEBRUARY", "28:29"),
    MARCH("MARCH", "31"),
    APRIL("APRIL", "30"),
    MAY("MAY", "31"),
    JUNE("JUNE", "30"),
    JULY("JULY", "31"),
    AUGUST("AUGUST", "31"),
    SEPTEMBER("SEPTEMBER", "30"),
    OCTOBER("OCTOBER", "31"),
    NOVEMBER("NOVEMBER", "30"),
    DECEMBER("DECEMBER", "31");
    private String value;
    private String name;

    private MonthEndDate(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }

    public static String getMonthName(String value) {
        for (MonthEndDate month : values()) {
            if (month.value.equals(value)) {
                return month.name;
            }
        }
        return null;
    }

    public static String getMonthValue(String name) {
        for (MonthEndDate month : values()) {
            if (month.name.equals(name)) {
                return month.value;
            }
        }
        return null;
    }
}
