/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum ATMProcessingCodeEnum {

    ISSUER_CASH_WITHDRAWAL("01", "Issuer Cash Withdrawal"),
    ISSUER_BALANCE_INQUIRY("32", "Issuer Balance Inquiry"),
    ISSUER_MINI_STATEMENT("37", "Issuer Mini Statement"),
    ACQUIRER_WITHDRAWAL("19", "Acquirer Withdrawal"),
    CASH_AT_POS("44", "Cash At POS"),
    MERCHANT_SALE("45", "Merchant Sale"),
    POS_BALANCE_INQUIRY("31", "POS Balance Inquiry"),
    POS_MINI_STATEMENT("35", "POS Mini Statement"),
    ECOM_WITHDRAWAL("49", "ECOM Withdrawal");
    private String code;
    private String description;

    private ATMProcessingCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String getCode(String description) {
        for (ATMProcessingCodeEnum instance : values()) {
            if (instance.description.equals(description)) {
                return instance.code;
            }
        }
        return null;
    }

    public static String getDescription(String code) {
        for (ATMProcessingCodeEnum instance : values()) {
            if (instance.code.equals(code)) {
                return instance.description;
            }
        }
        return null;
    }
}
