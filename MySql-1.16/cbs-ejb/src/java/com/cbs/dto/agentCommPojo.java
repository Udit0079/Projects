/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author user
 */
public class agentCommPojo {
    private String bankName;
    private String bnkAdd;
    private String agentName;
    private String agentSbAccount;
    private double totCollAmt;
    private double collInOneYr;
    private double collInTwoYr;
    private double collInGrTwoYr;
    private double commInOneYr;
    private double commInTwoYr;
    private double commInGrTwoYr;
    private double totCommAmt;
    private double tax;
    private double netAmount;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBnkAdd() {
        return bnkAdd;
    }

    public void setBnkAdd(String bnkAdd) {
        this.bnkAdd = bnkAdd;
    }
    
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentSbAccount() {
        return agentSbAccount;
    }

    public void setAgentSbAccount(String agentSbAccount) {
        this.agentSbAccount = agentSbAccount;
    }

    public double getTotCollAmt() {
        return totCollAmt;
    }

    public void setTotCollAmt(double totCollAmt) {
        this.totCollAmt = totCollAmt;
    }

    public double getCollInOneYr() {
        return collInOneYr;
    }

    public void setCollInOneYr(double collInOneYr) {
        this.collInOneYr = collInOneYr;
    }

    public double getCollInTwoYr() {
        return collInTwoYr;
    }

    public void setCollInTwoYr(double collInTwoYr) {
        this.collInTwoYr = collInTwoYr;
    }

    public double getCollInGrTwoYr() {
        return collInGrTwoYr;
    }

    public void setCollInGrTwoYr(double collInGrTwoYr) {
        this.collInGrTwoYr = collInGrTwoYr;
    }

    public double getCommInOneYr() {
        return commInOneYr;
    }

    public void setCommInOneYr(double commInOneYr) {
        this.commInOneYr = commInOneYr;
    }

    public double getCommInTwoYr() {
        return commInTwoYr;
    }

    public void setCommInTwoYr(double commInTwoYr) {
        this.commInTwoYr = commInTwoYr;
    }

    public double getCommInGrTwoYr() {
        return commInGrTwoYr;
    }

    public void setCommInGrTwoYr(double commInGrTwoYr) {
        this.commInGrTwoYr = commInGrTwoYr;
    }

    public double getTotCommAmt() {
        return totCommAmt;
    }

    public void setTotCommAmt(double totCommAmt) {
        this.totCommAmt = totCommAmt;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }
}
