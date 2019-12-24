/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.clg;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class UnverifiedInstrumentsGrid implements Serializable{
    String acNo;
    String emflag;
    String txnInstrAmt;
    String txnInstrNo;
    String vtot;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getEmflag() {
        return emflag;
    }

    public void setEmflag(String emflag) {
        this.emflag = emflag;
    }

    public String getTxnInstrAmt() {
        return txnInstrAmt;
    }

    public void setTxnInstrAmt(String txnInstrAmt) {
        this.txnInstrAmt = txnInstrAmt;
    }

    public String getTxnInstrNo() {
        return txnInstrNo;
    }

    public void setTxnInstrNo(String txnInstrNo) {
        this.txnInstrNo = txnInstrNo;
    }

    public String getVtot() {
        return vtot;
    }

    public void setVtot(String vtot) {
        this.vtot = vtot;
    }
    
}
