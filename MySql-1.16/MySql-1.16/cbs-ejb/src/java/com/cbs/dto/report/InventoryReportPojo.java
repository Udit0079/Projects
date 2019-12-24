/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;


/**
 *
 * @author Ankit Verma
 */
public class InventoryReportPojo implements Serializable{
  private String invntClass,
           invntType,
           destBranch,
           invntSrNo,
           enterBy,
           tranDt;
   private long invntQty ;
   private int chNoFrom,
               chNoTo;
    public String getDestBranch() {
        return destBranch;
    }

    public void setDestBranch(String destBranch) {
        this.destBranch = destBranch;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getInvntClass() {
        return invntClass;
    }

    public void setInvntClass(String invntClass) {
        this.invntClass = invntClass;
    }

    public long getInvntQty() {
        return invntQty;
    }

    public void setInvntQty(long invntQty) {
        this.invntQty = invntQty;
    }

    public String getInvntSrNo() {
        return invntSrNo;
    }

    public void setInvntSrNo(String invntSrNo) {
        this.invntSrNo = invntSrNo;
    }

    

    public String getInvntType() {
        return invntType;
    }

    public void setInvntType(String invntType) {
        this.invntType = invntType;
    }

    public String getTranDt() {
        return tranDt;
    }

    public void setTranDt(String tranDt) {
        this.tranDt = tranDt;
    }

    public int getChNoFrom() {
        return chNoFrom;
    }

    public void setChNoFrom(int chNoFrom) {
        this.chNoFrom = chNoFrom;
    }

    public int getChNoTo() {
        return chNoTo;
    }

    public void setChNoTo(int chNoTo) {
        this.chNoTo = chNoTo;
    }
    
    
}
