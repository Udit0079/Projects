/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public enum SmsServicesEnum {
    // TODO Remove Id From Enums

    CASH_DEPOSIT("Cash Deposit", "00"), CASH_WITHDRAWAL("Cash Withdrawal", "01"), XFER_DEPOSIT("Transfer Deposit", "20"),
    XFER_WITHDRAWAL("Transfer Withdrawal", "21"), CLG_DEPOSIT("Clearing Deposit", "10"), CLG_WITHDRAWAL("Clearing Withdrawal", "11"),
    INTEREST_CR("Interest Deposit", "80"), INTEREST_DR("Interest Withdrawal", "81"),CHARGE_CR("Charge Deposit", "90"), 
    CHARGE_DR("Charge Withdrawal", "91");
    private String serviceCode;
    private String value;

    private SmsServicesEnum(String value, String serviceCode) {
        this.value = value;
        this.serviceCode = serviceCode;
    }

    public String getValue() {
        return this.value;
    }

    public static String getService(String serviceCode) {
        for (SmsServicesEnum service : values()) {
            if (service.serviceCode.equals(serviceCode)) {
                return service.value;
            }
        }
        return null;
    }

    public static String getServiceCode(String value) {
        for (SmsServicesEnum service : values()) {
            if (service.value.equals(value)) {
                return service.serviceCode;
            }
        }
        return null;
    }
}
