package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author admin
 */
public class CashTranRepPojo implements Serializable {

    private Date DATE;
    private String ACCNAME;
    private String ACCNO;
   // private float AMOUNT;
    private BigDecimal AMOUNT;
    private BigDecimal TOTDEP;
    private String PANNO;
    private String FLAG;
    private String prAddr;
    private String fatherName;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPrAddr() {
        return prAddr;
    }

    public void setPrAddr(String prAddr) {
        this.prAddr = prAddr;
    }

    public String getACCNAME() {
        return ACCNAME;
    }

    public void setACCNAME(String ACCNAME) {
        this.ACCNAME = ACCNAME;
    }

    public String getACCNO() {
        return ACCNO;
    }

    public void setACCNO(String ACCNO) {
        this.ACCNO = ACCNO;
    }

    public BigDecimal getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(BigDecimal AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

   
    public Date getDATE() {
        return DATE;
    }

    public void setDATE(Date DATE) {
        this.DATE = DATE;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getPANNO() {
        return PANNO;
    }

    public void setPANNO(String PANNO) {
        this.PANNO = PANNO;
    }

    public BigDecimal getTOTDEP() {
        return TOTDEP;
    }

    public void setTOTDEP(BigDecimal TOTDEP) {
        this.TOTDEP = TOTDEP;
    }

   
}
