/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.misc;

/**
 *
 * @author root
 */
public class BatchDeleteGridLoad {

    String acctNo;
    String custName;
    float balance;
    float crAmount;
    float drAmount;
    String payBy;
    String details;
    int instNo;
    int tranDesc;
    String enterBy;
    float recNo;
    String intFlag;
    int iY;
    int tY;
    String tableNo;
    int trsNo;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(float crAmount) {
        this.crAmount = crAmount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(float drAmount) {
        this.drAmount = drAmount;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public int getiY() {
        return iY;
    }

    public void setiY(int iY) {
        this.iY = iY;
    }

    public int getInstNo() {
        return instNo;
    }

    public void setInstNo(int instNo) {
        this.instNo = instNo;
    }

    public String getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(String intFlag) {
        this.intFlag = intFlag;
    }

    public String getPayBy() {
        return payBy;
    }

    public void setPayBy(String payBy) {
        this.payBy = payBy;
    }

    public float getRecNo() {
        return recNo;
    }

    public void setRecNo(float recNo) {
        this.recNo = recNo;
    }

    public int gettY() {
        return tY;
    }

    public void settY(int tY) {
        this.tY = tY;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public int getTranDesc() {
        return tranDesc;
    }

    public void setTranDesc(int tranDesc) {
        this.tranDesc = tranDesc;
    }

    public int getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(int trsNo) {
        this.trsNo = trsNo;
    }

    public BatchDeleteGridLoad(String acctNo, String custName, float balance, float crAmount, float drAmount, String payBy, String details, int instNo, int tranDesc, String enterBy, float recNo, String intFlag, int iY, int tY, String tableNo, int trsNo) {
        this.acctNo = acctNo;
        this.custName = custName;
        this.balance = balance;
        this.crAmount = crAmount;
        this.drAmount = drAmount;
        this.payBy = payBy;
        this.details = details;
        this.instNo = instNo;
        this.tranDesc = tranDesc;
        this.enterBy = enterBy;
        this.recNo = recNo;
        this.intFlag = intFlag;
        this.iY = iY;
        this.tY = tY;
        this.tableNo = tableNo;
        this.trsNo = trsNo;
    }

    


}
