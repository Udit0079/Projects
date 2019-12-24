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
public class PayOrderOutstandingGrid implements Serializable{

    String finYear;
    String seqNo;
    String instNo;
    String instAmt;
    String issueDate;
    String inFavourOf;
    String issuedBy;

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getInFavourOf() {
        return inFavourOf;
    }

    public void setInFavourOf(String inFavourOf) {
        this.inFavourOf = inFavourOf;
    }

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
        this.instAmt = instAmt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
}
