/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class ChBookAuthorization implements Serializable{

   public String acno;
   public String custName;
   public Integer chqSeries;
   public Integer chqNoFrom;
   public Integer chqNoTo;
   public Integer noOfLeaf;
   public String issuedBy;
   public String auth;
   public String chargeFlag; 
   public String custState; 
   public String branchState; 
   
    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

  

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Integer getChqNoFrom() {
        return chqNoFrom;
    }

    public void setChqNoFrom(Integer chqNoFrom) {
        this.chqNoFrom = chqNoFrom;
    }

    public Integer getChqNoTo() {
        return chqNoTo;
    }

    public void setChqNoTo(Integer chqNoTo) {
        this.chqNoTo = chqNoTo;
    }

    public Integer getChqSeries() {
        return chqSeries;
    }

    public void setChqSeries(Integer chqSeries) {
        this.chqSeries = chqSeries;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Integer getNoOfLeaf() {
        return noOfLeaf;
    }

    public void setNoOfLeaf(Integer noOfLeaf) {
        this.noOfLeaf = noOfLeaf;
    }

    public String getChargeFlag() {
        return chargeFlag;
    }

    public void setChargeFlag(String chargeFlag) {
        this.chargeFlag = chargeFlag;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getBranchState() {
        return branchState;
    }

    public void setBranchState(String branchState) {
        this.branchState = branchState;
    }   


}
