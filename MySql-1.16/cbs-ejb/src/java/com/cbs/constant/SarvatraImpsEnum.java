/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum SarvatraImpsEnum {

    //Processing Value
    GENERATE_MMID("111001", "GENERATE MMID"),
    CANCEL_MMID("111002", "CANCEL MMID"),
    P2A_TRF("111009", "P2A FUND TRANSFER"),
    P2P_TRF("111004", "P2P FUND TRANSFER"),
    //Channel Value
    IMPS_BRANCH_CHANNEL("Mobile", "BRANCH CHANNEL");
    private String code;
    private String value;

    private SarvatraImpsEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getCode(String value) {
        for (SarvatraImpsEnum instance : values()) {
            if (instance.value.equals(value)) {
                return instance.code;
            }
        }
        return null;
    }

    public static String getValue(String code) {
        for (SarvatraImpsEnum instance : values()) {
            if (instance.code.equals(code)) {
                return instance.value;
            }
        }
        return null;
    }
}
