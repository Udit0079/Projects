package com.cbs.dto.report;

import java.math.BigDecimal;

public class BalanceCertificatePojo implements java.io.Serializable {

    private String CUSTNO;
    private String CUSTNAME;
    private String CRADDRESS;
    private String FATHERNAME;
    private String ACTYPE;
    private BigDecimal AMOUNT;
    private String BNKNAME;
    private String BNKADDRESS;

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

    public String getBNKADDRESS() {
        return BNKADDRESS;
    }

    public void setBNKADDRESS(String BNKADDRESS) {
        this.BNKADDRESS = BNKADDRESS;
    }

    public String getBNKNAME() {
        return BNKNAME;
    }

    public void setBNKNAME(String BNKNAME) {
        this.BNKNAME = BNKNAME;
    }

    public String getCRADDRESS() {
        return CRADDRESS;
    }

    public void setCRADDRESS(String CRADDRESS) {
        this.CRADDRESS = CRADDRESS;
    }

    public String getCUSTNAME() {
        return CUSTNAME;
    }

    public void setCUSTNAME(String CUSTNAME) {
        this.CUSTNAME = CUSTNAME;
    }

    public String getCUSTNO() {
        return CUSTNO;
    }

    public void setCUSTNO(String CUSTNO) {
        this.CUSTNO = CUSTNO;
    }

    public String getFATHERNAME() {
        return FATHERNAME;
    }

    public void setFATHERNAME(String FATHERNAME) {
        this.FATHERNAME = FATHERNAME;
    }
}
