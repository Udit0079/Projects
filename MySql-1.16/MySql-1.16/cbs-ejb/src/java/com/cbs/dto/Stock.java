/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class Stock {
    int sNo;
    String instCode;
    int code;
    String numFrom;
    String numTo;
    double amtFrom;
    BigDecimal amtto;
    String leaves;
    String printlot;
    String status;
    String enterBy;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
      public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
      public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumFrom() {
        return numFrom;
    }

    public void setNumFrom(String numFrom) {
        this.numFrom = numFrom;
    }

    public String getNumTo() {
        return numTo;
    }

    public void setNumTo(String numTo) {
        this.numTo = numTo;
    }
     

  
      public double getAmtFrom() {
        return amtFrom;
    }

    public void setAmtFrom(double amtFrom) {
        this.amtFrom = amtFrom;
    }

    public BigDecimal getAmtto() {
        return amtto;
    }

    public void setAmtto(BigDecimal amtto) {
        this.amtto = amtto;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

   
   

 public String getPrintlot() {
        return printlot;
    }

    public void setPrintlot(String printlot) {
        this.printlot = printlot;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

     public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

}
