/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public enum IdbiReasonCodeEnum {

    R01("R01", "ACCOUNT CLOSED"),
    R02("R02", "ACCOUNT TRANSFERED"),
    R03("R03", "ACCOUNT DOES NOT EXIST"),
    R04("R04", "NO SUCH ACCOUNT TYPE"),
    R05("R05", "BENEFICIARY NAME DIFFERS"),
    R06("R06", "ACCOUNT HOLDER EXPIRED"),
    R07("R07", "ACCOUNT UNDER ATTACHEMENT"),
    R08("R08", "GARNISHI ORDER RECEIVED"),
    R09("R09", "OPERATIONS SUSPENDED"),
    R10("R10", "PARTY’S  INSTRUCTIONS"),
    R11("R11", "ANY OTHER REASONS"),
    R12("R12", "CREDIT TO NRI ACCOUNT");
    private String code;
    private String reason;

    private IdbiReasonCodeEnum(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public static String getCode(String reason) {
        for (IdbiReasonCodeEnum instance : values()) {
            if (instance.reason.equals(reason)) {
                return instance.code;
            }
        }
        return null;
    }

    public static String getReason(String code) {
        for (IdbiReasonCodeEnum instance : values()) {
            if (instance.code.equals(code)) {
                return instance.reason;
            }
        }
        return null;
    }
}
