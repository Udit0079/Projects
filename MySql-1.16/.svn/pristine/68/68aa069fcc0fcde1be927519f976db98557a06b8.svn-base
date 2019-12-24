/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum AccountStatusEnum {

    ONE("1", "1"),
    TWO("2", "2"),
    THREE("3", "3"),
    FOUR("4", "4"),
    FIVE("5", "5"),
    SIX("6", "6"),
    SEVEN("7", "7"),
    EIGHT("8", "8"),
    NINE("9", "9"),
    TEN("10", "10"),
    ELEVEN("11", "11"),
    TWELVE("12", "12"),
    THIRTEEN("13", "13"),
    FOURTEEN("14", "14");
    private String name;
    private String value;

    private AccountStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String getAcStatusName(String value) {
        for (AccountStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status.name;
            }
        }
        return null;
    }

    public static String getAcStatusValue(String name) {
        for (AccountStatusEnum status : values()) {
            if (status.name.equals(name)) {
                return status.value;
            }
        }
        return null;
    }
}
