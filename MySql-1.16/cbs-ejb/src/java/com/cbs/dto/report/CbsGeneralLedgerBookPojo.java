/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CbsGeneralLedgerBookPojo implements Serializable {

    String ACNAME;
    BigDecimal AMOUNT;
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

    public int getGNO() {
        return GNO;
    }

    public void setGNO(int GNO) {
        this.GNO = GNO;
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

    public int getSGNO() {
        return SGNO;
    }

    public void setSGNO(int SGNO) {
        this.SGNO = SGNO;
    }
}
