/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum CpsmsErrorEnum {

    //Error defined by dinesh it can be changed after given by CPSMS document.
    ERROR_DUPLICATE_BATCH("E01", "Duplicate Batch"),
    ERROR_BATCH_AMOUNT_MISMATCH("E02", "Batch Amount Mismatch"),
    ERROR_BALANCE_EXCEED("E03", "Balance Exceed"),
    //From CPSMS given document
    ERROR_R00("R00", "Success Transaction"),
    ERROR_R01("R01", "Account Closed"),
    ERROR_R02("R02", "Account Transferred"),
    ERROR_R03("R03", "Account Does Not Exist"),
    ERROR_R04("R04", "No Such Account Type"),
    ERROR_R05("R05", "Beneficiary Name Differes"),
    ERROR_R06("R06", "Account Holder Expired"),
    ERROR_R07("R07", "Account Under Attachement"),
    ERROR_R08("R08", "Garnishi Order Received"),
    ERROR_R09("R09", "Operations Suspended"),
    ERROR_R10("R10", "Party Instructions"),
    ERROR_R11("R11", "Any Other Resons"),
    ERROR_R12("R12", "Credit To Nri Account"),
    ERROR_R99("R99", "Payment Initiated"),
    ERROR_777("777", "Rejected By Agency At Bank"),
    ERROR_888("888", "Recalled By CPSMS"),
    ERROR_999("999", "Print Advice Mismatch"),
    //Dhiru Sir - From CPSMS given document 
    ERR_301("301", "Incorrect Username and/or Password"),
    ERR_302("302", "IP address Error"),
    ERR_103("103", "Message Header fields are missing. (RecordCount, Source, Destination, Request ID)."),
    ERR_104("104", "First four characters of Request ID should be same as BankCode."),
    ERR_105("105", "RequestId date format should be in DDMMYYYY format."),
    ERR_107("107", "BankCode should be alpha-numeric."),
    ERR_108("108", "Branch Code should be of max 7 characters."),
    ERR_110("110", "Gender should be 'M', 'F', 'T', 'O'."),
    ERR_111("111", "Mobile Number should be of 10 digits."),
    ERR_112("112", "Numbers of records in message header and actual records is different."),
    ERR_113("113", "PinCode should be numeric."),
    ERR_114("114", "PinCode should be of 6 digits."),
    ERR_115("115", "Request Id serial number should be numeric."),
    ERR_116("116", "RecordsCount should be numeric."),
    ERR_122("122", "Destination should be BankCode."),
    ERR_123("123", "Destination should be PFMS"),
    ERR_124("124", "BankCode does not exist in PFMS"),
    ERR_134("134", "Duplicate Request Id found."),
    ERR_139("139", "ReqMsgId/OriginalMessageId does not exist in CPSMS"),
    ERR_140("140", "Account Number does not exist in PFMS"),
    ERR_201("201", "Mandatory fields are missing."),
    ERR_202("202", "MessageID format should have starting from fifth character."),
    ERR_204("204", "Account Number does not exist in PFMS"),
    ERR_205("205", "Account Validity should be 'V' or 'I' or 'N'."),
    ERR_206("206", "Account Type should be 'SB', 'CA', 'CC', 'OD', 'TD', 'LN', 'SG', 'CG'."),
    ERR_209("209", "Gender should be 'M', 'F', 'T', 'O'."),
    ERR_210("210", "Mobile Number should be 10 digit."),
    ERR_212("212", "Account Category should be of 3 characters."),
    ERR_213("213", "Account Category does not exist in PFMS mapping."),
    ERR_222("222", "Duplicate AccountNumber, EntityCode and ReqMsgId found in the file."),
    ERR_223("223", "AccountStatus is mandatory in case of AccountValidity='V' and should be A, I, C, DF, CF, TF"),
    ERR_227("227", "IFSCCode should be 11 characters."),
    ERR_228("228", "More than one IFSCCode found for one AccountNumber in the file"),
    ERR_229("229", "More than one BSRCode found for one AccountNumber in the file"),
    ERR_230("230", "Fifth character of IFSCCode should be numeric."),
    ERR_231("231", "Earlier Valid account has come as Invalid."),
    ERR_232("232", "Duplicate file."),
    ERR_233("233", "EntityCode is missing."),
    ERR_234("234", "IFSCCode does not belong to Bank."),
    ERR_235("235", "Bank has send account validity E"),
    ERR_236("236", "Invalid PAN value"),
    ERR_237("237", "Invalid UID value"),
    ERR_238("238", "Invalid Branch Code"),
    ERR_239("239", "Invalid Account Number format");
    private String code;
    private String value;

    private CpsmsErrorEnum(String code, String value) {
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
        for (CpsmsErrorEnum instance : values()) {
            if (instance.value.equals(value)) {
                return instance.code;
            }
        }
        return null;
    }

    public static String getValue(String code) {
        for (CpsmsErrorEnum instance : values()) {
            if (instance.code.equals(code)) {
                return instance.value;
            }
        }
        return null;
    }
}
