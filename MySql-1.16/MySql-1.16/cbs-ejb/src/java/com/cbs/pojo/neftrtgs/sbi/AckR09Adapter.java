/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs.sbi;

public class AckR09Adapter {

    private String tranRefNo;
    private String settledIndicator;
    private String reasonCode;
    private String settlementTime;

    public String getTranRefNo() {
        return tranRefNo;
    }

    public void setTranRefNo(String tranRefNo) {
        this.tranRefNo = tranRefNo;
    }

    public String getSettledIndicator() {
        return settledIndicator;
    }

    public void setSettledIndicator(String settledIndicator) {
        this.settledIndicator = settledIndicator;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }
}
