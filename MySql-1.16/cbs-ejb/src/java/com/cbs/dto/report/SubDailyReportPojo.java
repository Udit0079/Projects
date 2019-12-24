package com.cbs.dto.report;

import java.math.BigDecimal;

public class SubDailyReportPojo implements java.io.Serializable, Comparable<SubDailyReportPojo> {

    private String actype;
    private String acno;
    private String custname;
    private BigDecimal crcash;
    private BigDecimal crtrans;
    private BigDecimal crclg;
    private BigDecimal drcash;
    private BigDecimal drtrans;
    private BigDecimal drclg;
    private String acdesc;
    private String enterBy;
    private String authBy;
    private String chequeNo;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public BigDecimal getCrcash() {
        return crcash;
    }

    public void setCrcash(BigDecimal crcash) {
        this.crcash = crcash;
    }

    public BigDecimal getCrclg() {
        return crclg;
    }

    public void setCrclg(BigDecimal crclg) {
        this.crclg = crclg;
    }

    public BigDecimal getCrtrans() {
        return crtrans;
    }

    public void setCrtrans(BigDecimal crtrans) {
        this.crtrans = crtrans;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public BigDecimal getDrcash() {
        return drcash;
    }

    public void setDrcash(BigDecimal drcash) {
        this.drcash = drcash;
    }

    public BigDecimal getDrclg() {
        return drclg;
    }

    public void setDrclg(BigDecimal drclg) {
        this.drclg = drclg;
    }

    public BigDecimal getDrtrans() {
        return drtrans;
    }

    public void setDrtrans(BigDecimal drtrans) {
        this.drtrans = drtrans;
    }
    public int compareTo(SubDailyReportPojo sub){
        return this.acno.compareTo(sub.acno);
    }

    public String getAcdesc() {
        return acdesc;
    }

    public void setAcdesc(String acdesc) {
        this.acdesc = acdesc;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }
    
    
}
