/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum CpsmsEnum {

    //Defined by Dinesh Pratap Singh
    PRINT_ADVICE_FILE_ONE("PRINT_ADVICE_FILE_ONE", "com.cpsms.paymentrequest.Payments"),
    PRINT_ADVICE_FILE_TWO("PRINT_ADVICE_FILE_TWO", "com.cpsms.paymentreqack.Acknowledgement"),
    PRINT_ADVICE_CBS_STATUS_01("01", "Success acknowledgement of received print advice file 1 has been sent successfully"), //MessageId and batch level status
    PRINT_ADVICE_CBS_STATUS_03("03", "The particular batch of print advice file 1 has been processed at branch level for intra transaction."), //Batch level status
    PRINT_ADVICE_CBS_STATUS_04("04", "A particular messageId has been failed or print advice mismatched at branch level"), //MessageId level status
    PRINT_ADVICE_CBS_STATUS_05("05", "Print advice mismatch at branch level for a particular batch"), //Batch level status
    PRINT_ADVICE_CBS_STATUS_06("06", "Payment initiated file of success debit transaction of received print advice payment file has been sent"), //batch level status
    PRINT_ADVICE_CBS_STATUS_07("07", "A particular messageId has been processed at branch level for intra transaction"), //MessageId level status
    RETURN_MESSAGE_TYPE_01("PAYREQACK", "Payment Request Acknowledgement File"),
    RETURN_MESSAGE_TYPE_02("INIPAY", "Initiated Payment File"),
    PRINT_ADVICE_PAY_REQ_RECEIVED("R", "Received"); //It will not change and defined by Dinesh Pratap Singh;
    private String code;
    private String value;

    private CpsmsEnum(String code, String value) {
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
        for (CpsmsEnum instance : values()) {
            if (instance.value.equals(value)) {
                return instance.code;
            }
        }
        return null;
    }

    public static String getValue(String code) {
        for (CpsmsEnum instance : values()) {
            if (instance.code.equals(code)) {
                return instance.value;
            }
        }
        return null;
    }
}
