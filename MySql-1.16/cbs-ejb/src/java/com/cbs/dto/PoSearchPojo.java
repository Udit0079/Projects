/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;


/**
 *
 * @author Nishant Srivastava
 */
public class PoSearchPojo {

    String fYear;
    String seqNo;
    String instNo;
    String custName;
    String dt;
    String originDt;
    String status;
    String inFavauroff;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public String getOriginDt() {
        return originDt;
    }

    public void setOriginDt(String originDt) {
        this.originDt = originDt;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getInFavauroff() {
        return inFavauroff;
    }

    public void setInFavauroff(String inFavauroff) {
        this.inFavauroff = inFavauroff;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
