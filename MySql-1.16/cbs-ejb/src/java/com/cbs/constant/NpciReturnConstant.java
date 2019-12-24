/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum NpciReturnConstant {

    NACH_CR_RET_1("01", "Account closed or transferred"),
    NACH_CR_RET_2("02", "No such account"),
    NACH_CR_RET_3("03", "Account description does not tally"),
    NACH_CR_RET_4("04", "Miscellaneous-Others"),
    NACH_CR_RET_51("51", "KYC documents pending"),
    NACH_CR_RET_52("52", "Documents pending for account holder turning major"),
    NACH_CR_RET_53("53", "A/c Inactive"),
    NACH_CR_RET_54("54", "Dormant A/c"),
    NACH_CR_RET_55("55", "A/c in zero balance"),
    NACH_CR_RET_56("56", "Simple A/c, first transaction to be from base branch"),
    NACH_CR_RET_57("57", "Amount exceeds limit set on a/c by bank for credit per transaction"),
    NACH_CR_RET_58("58", "Account reached maximum credit limit set by bank"),
    NACH_CR_RET_59("59", "Network failure"),
    NACH_CR_RET_60("60", "A/c holder expired"),
    NACH_CR_RET_61("61", "Mandate cancelled"),
    NACH_CR_RET_62("62", "Account under litigation"),
    NACH_CR_RET_63("63", "Invalid aadhaar number"),
    NACH_CR_RET_64("64", "Aadhar number not mapped to a/c number"),
    NACH_CR_RET_65("65", "A/c holder name invalid"),
    NACH_CR_RET_66("66", "UMRN does not exist"),
    NACH_CR_RET_68("68", "A/c blocked or frozen"),
    NACH_CLEARING_RETURN_88("88", "other reason"), //It can be used only in case of inward and outward clearing
    NACH_CR_RET_99("99", "mark pending");
    private String code;
    private String value;

    private NpciReturnConstant(String code, String value) {
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
        for (NpciReturnConstant instance : values()) {
            if (instance.value.equals(value)) {
                return instance.code;
            }
        }
        return null;
    }

    public static String getValue(String code) {
        for (NpciReturnConstant instance : values()) {
            if (instance.code.equals(code)) {
                return instance.value;
            }
        }
        return null;
    }
}
