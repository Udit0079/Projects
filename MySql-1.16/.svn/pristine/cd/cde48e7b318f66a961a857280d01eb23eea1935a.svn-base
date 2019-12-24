/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum SmsErrorCode {

    EnterpriseIdMissing("Enterprise Id Missing", "-1"),
    UserIdMissing("User Id Missing", "-2"),
    PasswordMissing("Password Missing", "-3"),
    ContenttypeMissing("Content type Missing", "-4"),
    SenderMissing("Sender Missing", "-5"),
    MSISDNMissing("MSISDN Missing", "-6"),
    MessageTextMissing("Message Text Missing", "-7"),
    MessageIdMissing("Message Id Missing", "-8"),
    WAPPushURLMissing("WAP Push URL Missing", "-9"),
    AuthenticationFailed("Authentication Failed", "-10"),
    ServiceBlockedforUser("Service Blocked for User", "-11"),
    RepeatedMessageIdReceived("Repeated Message Id Received", "-12"),
    InvalidContentTypeReceived("Invalid Content Type Received", "-13"),
    InternationalMessagesNotAllowed("International Messages Not Allowed", "-14"),
    IncompleteorInvalidXMLPacketReceived("Incomplete or Invalid XML Packet Received", "-15"),
    InvalidDNDFlagvalue("Invalid DND Flag value", "-16"),
    DirectPushingNotAllowed("Direct Pushing Not Allowed", "-17"),
    CLInotregistered("CLI not registered", "-18"),
    OperatorSpecificMSISDNBlocked("Operator Specific MSISDN Blocked", "-19"),
    NumberinDND("Number in DND", "-21"),
    ReasonNotAvailable("Reason Not Available", "ACL_000"),
    MobileAbstntextoutser("Mobile Abst.|nt ext|out ser", "ACL_100"),
    CallorSMSBared("Call or SMS Bared", "ACL_101"),
    MTSMSNotSupported("MT SMS Not Supported", "ACL_102"),
    NetworkWeakness("Network Weakness", "ACL_103"),
    MultipleSMSinQueue("Multiple SMS in Queue", "ACL_104"),
    MobinInitializingState("Mob. in Initializing State", "ACL_105"),
    MobileInboxisFull("Mobile Inbox is Full", "ACL_106"),
    SMSCisCongested("SMSC is Congested", "ACL_107"),
    InvalidMobileNumber("Invalid Mobile Number", "ACL_108"),
    OtherTransisRunning("Other Trans is Running", "ACL_109"),
    MobileSwitchOff("Mobile Switch Off", "ACL_110"),
    JunkinSMS("Junk in SMS", "ACL_111"),
    MobisnotAcknowledging("Mob. is not Acknowledging", "ACL_112"),
    MaxRetryExceeded("Max Retry Exceeded", "ACL_113"),
    TimeoutFrmDestHLR("Timeout Frm Dest. HLR", "ACL_114"),
    HLRMSCnotrespIntroam("HLR/MSC not resp.| Int roam", "ACL_115"),
    Destoprntrestimebeing("Dest opr nt res. time being", "ACL_116"),
    UnknowError("Unknow Error", "ACL_117"),
    BlacklistSubscriber("Blacklist Subscriber", "ACL_118"),
    AccountClosedOrTransferred("Account closed or transferred", "01"),
    NoSuchAccount("No such Account", "02"),
    NoSuchAadhaarNo("No such Aadhaar No.", "03"),
    AadhaarNoInvalid("Aadhaar No invalid", "04"),
    AccountNoOrAadhaarNoDoesNotPertainToTheBank("Account no or Aadhaar No does not pertain to the bank", "05"),
    Miscellaneous("Miscellaneous", "06");
    private String errorDesc;
    private String errorCode;

    private SmsErrorCode(String errorDesc, String errorCode) {
        this.errorDesc = errorDesc;
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static String getErrorDesc(String errorCode) {
        for (SmsErrorCode var : values()) {
            if (var.errorCode.equals(errorCode)) {
                return var.errorDesc;
            }
        }
        return null;
    }

    public static String getErrorCode(String errorDesc) {
        for (SmsErrorCode var : values()) {
            if (var.errorDesc.equals(errorDesc)) {
                return var.errorCode;
            }
        }
        return null;
    }
}
