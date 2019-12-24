/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public enum AccStatusEnum {

    OPERATIVE("", 1),
    INOPERATIVE("Account is marked Inoperative", 2),
    SUIT_FILED("Account is marked Suit Filed", 3),
    FROZEN("Account is marked Frozen", 4),
    RECALLED("Account is marked Recalled", 5),
    DECREED("Account is marked Decreed", 6),
    WITHDRAWAL_STOPPED("Withdrawal is not allowed in this Account", 7),
    OPERATION_STOPPED("Account is marked Operation Stopped", 8),
    CLOSED("Account has been Closed", 9),
    SUB_STANDARD("This Account is SUB STANDARD", 11),
    DOUBT_FUL("This Account is DOUBT FUL", 12),
    LOSS("This Account is LOSS Account", 13),
    PROTESTED_BILL("This Account is PROTESTED BILL", 14),
    DEAF("Account is marked DEAF", 15);

    private int status;
    private String value;

    AccStatusEnum(String value, int status) {
        this.value = value;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public static String getStatusMsg(int status) {
        for (AccStatusEnum acStatus : values()) {
            if (acStatus.status == status) {
                return acStatus.value;
            }
        }
        return null;
    }
}
