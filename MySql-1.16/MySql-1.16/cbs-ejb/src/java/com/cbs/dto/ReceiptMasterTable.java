/**
 *
 * @author Navneet Goyal
 */
package com.cbs.dto;

public class ReceiptMasterTable {

    private String agCode;
    private String agName;
    private String issueDate;
    private float receiptFrom;
    private float receiptTo;
    private String issuedBy;
    private int leafs;

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getAgCode() {
        return agCode;
    }

    public void setAgCode(String agCode) {
        this.agCode = agCode;
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

    public int getLeafs() {
        return leafs;
    }

    public void setLeafs(int leafs) {
        this.leafs = leafs;
    }

    public float getReceiptFrom() {
        return receiptFrom;
    }

    public void setReceiptFrom(float receiptFrom) {
        this.receiptFrom = receiptFrom;
    }

    public float getReceiptTo() {
        return receiptTo;
    }

    public void setReceiptTo(float receiptTo) {
        this.receiptTo = receiptTo;
    }
}

