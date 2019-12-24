/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.loan;

/**
 *
 * @author root
 */
public class DemandMarkingTable {

    String accountNo;
    String partyName;
    String demandDate;
    String prinDemand;
    String intDemand;
    String chgDemand;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getChgDemand() {
        return chgDemand;
    }

    public void setChgDemand(String chgDemand) {
        this.chgDemand = chgDemand;
    }

    public String getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(String demandDate) {
        this.demandDate = demandDate;
    }

    public String getIntDemand() {
        return intDemand;
    }

    public void setIntDemand(String intDemand) {
        this.intDemand = intDemand;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPrinDemand() {
        return prinDemand;
    }

    public void setPrinDemand(String prinDemand) {
        this.prinDemand = prinDemand;
    }
    
}
