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
public class GetReturnCheques implements Serializable{
      private String acno;
      private String TxnInstNo;
      private String  TxnInstDate;
      private Float TxnInstAmt;
      private String  ReasonForCancel;
      private int TxnType;
      private String TxnBankName;
      private String TxnBankAddress;
      private int vtot;
      private String ObcFlag;

    public String getObcFlag() {
        return ObcFlag;
    }

    public void setObcFlag(String ObcFlag) {
        this.ObcFlag = ObcFlag;
    }

    public String getReasonForCancel() {
        return ReasonForCancel;
    }

    public void setReasonForCancel(String ReasonForCancel) {
        this.ReasonForCancel = ReasonForCancel;
    }

    public String getTxnBankAddress() {
        return TxnBankAddress;
    }

    public void setTxnBankAddress(String TxnBankAddress) {
        this.TxnBankAddress = TxnBankAddress;
    }

    public String getTxnBankName() {
        return TxnBankName;
    }

    public void setTxnBankName(String TxnBankName) {
        this.TxnBankName = TxnBankName;
    }

    public Float getTxnInstAmt() {
        return TxnInstAmt;
    }

    public void setTxnInstAmt(Float TxnInstAmt) {
        this.TxnInstAmt = TxnInstAmt;
    }

    public String getTxnInstDate() {
        return TxnInstDate;
    }

    public void setTxnInstDate(String TxnInstDate) {
        this.TxnInstDate = TxnInstDate;
    }

    public String getTxnInstNo() {
        return TxnInstNo;
    }

    public void setTxnInstNo(String TxnInstNo) {
        this.TxnInstNo = TxnInstNo;
    }

    public int getTxnType() {
        return TxnType;
    }

    public void setTxnType(int TxnType) {
        this.TxnType = TxnType;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public int getVtot() {
        return vtot;
    }

    public void setVtot(int vtot) {
        this.vtot = vtot;
    }

    public GetReturnCheques(String acno, String TxnInstNo, String TxnInstDate, Float TxnInstAmt, String ReasonForCancel, int TxnType, String TxnBankName, String TxnBankAddress, int vtot, String ObcFlag) {
        this.acno = acno;
        this.TxnInstNo = TxnInstNo;
        this.TxnInstDate = TxnInstDate;
        this.TxnInstAmt = TxnInstAmt;
        this.ReasonForCancel = ReasonForCancel;
        this.TxnType = TxnType;
        this.TxnBankName = TxnBankName;
        this.TxnBankAddress = TxnBankAddress;
        this.vtot = vtot;
        this.ObcFlag = ObcFlag;
    }
   
}
