/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class DdsTransactionGrid implements Comparable {

    private String agentCode;
    private String date;
    private String receiptNo;
    private String accountNo;
    private String amount;
    private String agentName;
    private String name;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public int compareTo(Object o) {
        Double p1 = new Double(this.receiptNo);
        Double p2 = new Double(((DdsTransactionGrid) o).receiptNo);
        return p1.compareTo(p2);
    }
}
