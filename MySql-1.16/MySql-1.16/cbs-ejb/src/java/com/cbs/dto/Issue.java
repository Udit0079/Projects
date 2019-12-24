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
public class Issue {
    String insttype;
    int code;
    String numFrom;
    String numTo;
    double amtFrom;
    BigDecimal amtTo;
    String leaves;
    String printlot;
    String issuedBy;

    public double getAmtFrom() {
        return amtFrom;
    }

    public void setAmtFrom(double amtFrom) {
        this.amtFrom = amtFrom;
    }

    public BigDecimal getAmtTo() {
        return amtTo;
    }

    public void setAmtTo(BigDecimal amtTo) {
        this.amtTo = amtTo;
    }

    

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInsttype() {
        return insttype;
    }

    public void setInsttype(String insttype) {
        this.insttype = insttype;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
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


}
