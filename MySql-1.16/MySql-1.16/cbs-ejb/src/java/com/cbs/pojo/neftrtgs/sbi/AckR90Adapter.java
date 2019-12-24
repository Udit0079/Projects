/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs.sbi;

public class AckR90Adapter {

    private String tranRefNo;
    private String ackIndicator;
    private String reasonCode;

    public String getTranRefNo() {
        return tranRefNo;
    }

    public void setTranRefNo(String tranRefNo) {
        this.tranRefNo = tranRefNo;
    }

    public String getAckIndicator() {
        return ackIndicator;
    }

    public void setAckIndicator(String ackIndicator) {
        this.ackIndicator = ackIndicator;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
