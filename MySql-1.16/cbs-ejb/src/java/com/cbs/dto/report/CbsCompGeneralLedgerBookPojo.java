/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author sipl
 */
public class CbsCompGeneralLedgerBookPojo implements Serializable {
    String ACNAME;
    BigDecimal AMOUNT;
    BigDecimal AMOUNT1;
    int GNO;
    String ACTYPE;
    String bankname;
    String bankaddress;
    int SGNO;

    public String getACNAME() {
        return ACNAME;
    }

    public void setACNAME(String ACNAME) {
        this.ACNAME = ACNAME;
    }

    public String getACTYPE() {
        return ACTYPE;
    }

    public void setACTYPE(String ACTYPE) {
        this.ACTYPE = ACTYPE;
    }

    public BigDecimal getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(BigDecimal AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public BigDecimal getAMOUNT1() {
        return AMOUNT1;
    }

    public void setAMOUNT1(BigDecimal AMOUNT1) {
        this.AMOUNT1 = AMOUNT1;
    }

    public int getGNO() {
        return GNO;
    }

    public void setGNO(int GNO) {
        this.GNO = GNO;
    }

    public int getSGNO() {
        return SGNO;
    }

    public void setSGNO(int SGNO) {
        this.SGNO = SGNO;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    
    
}
