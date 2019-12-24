/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class GlHeadPojo implements Serializable {

    String glHead;
    String glName;
    String glClassification;
    BigDecimal opBal;
    BigDecimal totalDr;
    BigDecimal totalCr;
    BigDecimal balance;
    String remarks;
    BigDecimal firstAmt;
    BigDecimal secondAmt;
    BigDecimal thirsAmt;
    BigDecimal fourthAmt;
    BigDecimal fifthAmt;
    BigDecimal sixthAmt;
    BigDecimal seventhAmt;
    String date;

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public String getGlName() {
        return glName;
    }

    public void setGlName(String glName) {
        this.glName = glName;
    }

    public String getGlClassification() {
        return glClassification;
    }

    public void setGlClassification(String glClassification) {
        this.glClassification = glClassification;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getOpBal() {
        return opBal;
    }

    public void setOpBal(BigDecimal opBal) {
        this.opBal = opBal;
    }

    public BigDecimal getTotalDr() {
        return totalDr;
    }

    public void setTotalDr(BigDecimal totalDr) {
        this.totalDr = totalDr;
    }

    public BigDecimal getTotalCr() {
        return totalCr;
    }

    public void setTotalCr(BigDecimal totalCr) {
        this.totalCr = totalCr;
    }

    public BigDecimal getFirstAmt() {
        return firstAmt;
    }

    public void setFirstAmt(BigDecimal firstAmt) {
        this.firstAmt = firstAmt;
    }

    public BigDecimal getSecondAmt() {
        return secondAmt;
    }

    public void setSecondAmt(BigDecimal secondAmt) {
        this.secondAmt = secondAmt;
    }

    public BigDecimal getThirsAmt() {
        return thirsAmt;
    }

    public void setThirsAmt(BigDecimal thirsAmt) {
        this.thirsAmt = thirsAmt;
    }

    public BigDecimal getFourthAmt() {
        return fourthAmt;
    }

    public void setFourthAmt(BigDecimal fourthAmt) {
        this.fourthAmt = fourthAmt;
    }

    public BigDecimal getFifthAmt() {
        return fifthAmt;
    }

    public void setFifthAmt(BigDecimal fifthAmt) {
        this.fifthAmt = fifthAmt;
    }

    public BigDecimal getSixthAmt() {
        return sixthAmt;
    }

    public void setSixthAmt(BigDecimal sixthAmt) {
        this.sixthAmt = sixthAmt;
    }

    public BigDecimal getSeventhAmt() {
        return seventhAmt;
    }

    public void setSeventhAmt(BigDecimal seventhAmt) {
        this.seventhAmt = seventhAmt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
