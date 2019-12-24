/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Athar Reza
 */
public class AgentCommissionPojo implements Serializable {

    private String agCode;
    private String agName;
    private double agBal;
    private double comAmt;
    private double secAmt;
    private double iTax;
    private double surCharge;
    private double taxAmt;
    private double netAmt;
    private double perc;
    private String branch;
    private String acno;
    private String branchName;

    public double getAgBal() {
        return agBal;
    }

    public void setAgBal(double agBal) {
        this.agBal = agBal;
    }

    public String getAgCode() {
        return agCode;
    }

    public void setAgCode(String agCode) {
        this.agCode = agCode;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public double getComAmt() {
        return comAmt;
    }

    public void setComAmt(double comAmt) {
        this.comAmt = comAmt;
    }

    public double getiTax() {
        return iTax;
    }

    public void setiTax(double iTax) {
        this.iTax = iTax;
    }

    public double getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(double netAmt) {
        this.netAmt = netAmt;
    }

    public double getSecAmt() {
        return secAmt;
    }

    public void setSecAmt(double secAmt) {
        this.secAmt = secAmt;
    }

    public double getSurCharge() {
        return surCharge;
    }

    public void setSurCharge(double surCharge) {
        this.surCharge = surCharge;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }  

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    
    
}
