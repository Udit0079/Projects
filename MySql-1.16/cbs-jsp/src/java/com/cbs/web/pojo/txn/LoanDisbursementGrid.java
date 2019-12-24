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
public class LoanDisbursementGrid implements Serializable{
        String srNo;
        String disburseAmt;
        String disbDate;
        String remarks;

    public String getDisbDate() {
        return disbDate;
    }

    public void setDisbDate(String disbDate) {
        this.disbDate = disbDate;
    }

    public String getDisburseAmt() {
        return disburseAmt;
    }

    public void setDisburseAmt(String disburseAmt) {
        this.disburseAmt = disburseAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }
        
}
