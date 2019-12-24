/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public enum Months {
     // TODO Remove Id From Enums
    JANUARY("JANUARY", "01"), 
    FEBRUARY("FEBRUARY", "02"), 
    MARCH("MARCH", "03"),
    APRIL("APRIL", "04"),
    MAY("MAY", "05"),
    JUNE("JUNE", "06"),
    JULY("JULY", "07"),
    AUGUST("AUGUST", "08"),
    SEPTEMBER("SEPTEMBER", "09"), 
    OCTOBER("OCTOBER", "10"), 
    NOVEMBER("NOVEMBER", "11"),
    DECEMBER("DECEMBER", "12");
    
    private String value;
    private String name;

    private Months(String name, String value) {
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
        for (Months month : values()) {
            if (month.value.equals(value)){
                return month.name;
            }
        }
        return null;
    }
    
    public static String getMonthValue(String name) {
        for (Months month : values()) {
           if (month.name.equals(name)){
                return month.value;
            }
        }
        return null;
    }
}
