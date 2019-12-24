package com.cbs.dto.report;

import java.math.BigDecimal;

public class InwardClearingPojo implements java.io.Serializable, Comparable {

    private String bnkName;
    private String bnkAdd;
    private Integer brncode;
    private String acType;
    private String acno;
    private String acName;
    private BigDecimal drbal;
    private String chqno;

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getBnkAdd() {
        return bnkAdd;
    }

    public void setBnkAdd(String bnkAdd) {
        this.bnkAdd = bnkAdd;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public Integer getBrncode() {
        return brncode;
    }

    public void setBrncode(Integer brncode) {
        this.brncode = brncode;
    }

    public String getChqno() {
        return chqno;
    }

    public void setChqno(String chqno) {
        this.chqno = chqno;
    }

    public BigDecimal getDrbal() {
        return drbal;
    }

    public void setDrbal(BigDecimal drbal) {
        this.drbal = drbal;
    }


    public int compareTo(Object o) {
         String anotherAcno = ((InwardClearingPojo)o).getAcno();
         return (int)(Long.parseLong(this.acno) - Long.parseLong(anotherAcno));  

    }
 
}
