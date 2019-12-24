/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class CashCr implements Serializable{
    String  sNo;
    String  acNo;
    String  CustName;
    double   instAmt;
    String  enterBy;
    String  details;
    String  orgn_br_code;
    String  dest_br_code;

    public String getDest_br_code() {
        return dest_br_code;
    }

    public void setDest_br_code(String dest_br_code) {
        this.dest_br_code = dest_br_code;
    }

    public String getOrgn_br_code() {
        return orgn_br_code;
    }

    public void setOrgn_br_code(String orgn_br_code) {
        this.orgn_br_code = orgn_br_code;
    }
    
    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String CustName) {
        this.CustName = CustName;
    }

    public double getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(double instAmt) {
        this.instAmt = instAmt;
    }


    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

     public CashCr(){
         
     }

}
