package com.cbs.web.pojo.dds;

public class DDSEntryGrid implements Comparable {

    private int sno;
    private String accountNo, receiptNo, enteredBy, amount, recNo, tokenpaid, tokenNo;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getRecNo() {
        return recNo;
    }

    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getTokenpaid() {
        return tokenpaid;
    }

    public void setTokenpaid(String tokenpaid) {
        this.tokenpaid = tokenpaid;
    }

    public int compareTo(Object o) {
        Double p1 = new Double(this.receiptNo);
        Double p2 = new Double(((DDSEntryGrid) o).receiptNo);
        return p1.compareTo(p2);
    }
}
