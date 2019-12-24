/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class ShadowBalClearanceTable implements Serializable{

    private String txnStatus;
    private String acno;
    private String  txnInstDate;
    private String txnInstNo;
    public double txnInstAmt;
    private String  txnBankName;
    private String remarks;
    private int vTot;
    private String  txnBankAddress;
     // Added by Manish kumar
    private String returnCode;
    private String returnDesc;
    public String getTxnBankAddress() {
        return txnBankAddress;
    }

    public double getTxnInstAmt() {
        return txnInstAmt;
    }

    public void setTxnInstAmt(double txnInstAmt) {
        this.txnInstAmt = txnInstAmt;
    }

//    public String getTxnInstAmt() {
//        return txnInstAmt;
//    }
//
//    public void setTxnInstAmt(String txnInstAmt) {
//        this.txnInstAmt = txnInstAmt;
//    }

    public void setTxnBankAddress(String txnBankAddress) {
        this.txnBankAddress = txnBankAddress;
    }
    
    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTxnBankName() {
        return txnBankName;
    }

    public void setTxnBankName(String txnBankName) {
        this.txnBankName = txnBankName;
    }

    public String getTxnInstDate() {
        return txnInstDate;
    }

    public void setTxnInstDate(String txnInstDate) {
        this.txnInstDate = txnInstDate;
    }

    public String getTxnInstNo() {
        return txnInstNo;
    }

    public void setTxnInstNo(String txnInstNo) {
        this.txnInstNo = txnInstNo;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public int getvTot() {
        return vTot;
    }

    public void setvTot(int vTot) {
        this.vTot = vTot;
    }
    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }
}
