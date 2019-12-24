/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import java.math.BigDecimal;
/**
 *
 * @author Nishant Srivastava
 */
public class WorkingStmtLoansPojo implements java.io.Serializable {
        
    private String acno;
    private BigDecimal begDue;
    private BigDecimal  begOut;   
    private BigDecimal demand;  
    private BigDecimal recovered;
    private BigDecimal adv;
    private BigDecimal closeDue;
    private BigDecimal closeArrear;
    private BigDecimal  closeOut;
    private BigDecimal tmp1;
    private BigDecimal tmp2;
    private BigDecimal odLimit;
    private BigDecimal aboveod;
    private String acctNature;
    private  String tableName;
       String acctcode ;

    public String getAcctcode() {
        return acctcode;
    }

    public void setAcctcode(String acctcode) {
        this.acctcode = acctcode;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getAcctNature() {
        return acctNature;
    }
    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }
    public BigDecimal getAboveod() {
        return aboveod;
    }
    public void setAboveod(BigDecimal aboveod) {
        this.aboveod = aboveod;
    }
    public String getAcno() {
        return acno;
    }
    public void setAcno(String acno) {
        this.acno = acno;
    }
    public BigDecimal getAdv() {
        return adv;
    }
    public void setAdv(BigDecimal adv) {
        this.adv = adv;
    }
    public BigDecimal getBegDue() {
        return begDue;
    }
    public void setBegDue(BigDecimal begDue) {
        this.begDue = begDue;
    }
   
    public BigDecimal getCloseArrear() {
        return closeArrear;
    }
    public void setCloseArrear(BigDecimal closeArrear) {
        this.closeArrear = closeArrear;
    }
    public BigDecimal getCloseDue() {
        return closeDue;
    }
    public void setCloseDue(BigDecimal closeDue) {
        this.closeDue = closeDue;
    }
    
    public BigDecimal getBegOut() {
        return begOut;
    }
    public void setBegOut(BigDecimal begOut) {
        this.begOut = begOut;
    }
    public BigDecimal getCloseOut() {
        return closeOut;
    }
    public void setCloseOut(BigDecimal closeOut) {
        this.closeOut = closeOut;
    }
    public BigDecimal getDemand() {
        return demand;
    }
    public void setDemand(BigDecimal demand) {
        this.demand = demand;
    }
   
    public BigDecimal getOdLimit() {
        return odLimit;
    }
    public void setOdLimit(BigDecimal odLimit) {
        this.odLimit = odLimit;
    }
    public BigDecimal getRecovered() {
        return recovered;
    }
    public void setRecovered(BigDecimal recovered) {
        this.recovered = recovered;
    }
    public BigDecimal getTmp1() {
        return tmp1;
    }
    public void setTmp1(BigDecimal tmp1) {
        this.tmp1 = tmp1;
    }
    public BigDecimal getTmp2() {
        return tmp2;
    }
    public void setTmp2(BigDecimal tmp2) {
        this.tmp2 = tmp2;
    }    
}