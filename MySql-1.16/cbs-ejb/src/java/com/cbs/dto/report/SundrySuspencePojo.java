/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class SundrySuspencePojo implements Serializable{
    private Double seqno;
    private String acno;
    private String status;
    private Double amount;
    private Date dt;
    private String acnodt;
    private Double amountdt;
    private Date dtdt;
    private String fyear;
    private String type;
    private String typedt;

    public String getTypedt() {
        return typedt;
    }

    public void setTypedt(String typedt) {
        this.typedt = typedt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcnodt() {
        return acnodt;
    }

    public void setAcnodt(String acnodt) {
        this.acnodt = acnodt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountdt() {
        return amountdt;
    }

    public void setAmountdt(Double amountdt) {
        this.amountdt = amountdt;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getDtdt() {
        return dtdt;
    }

    public void setDtdt(Date dtdt) {
        this.dtdt = dtdt;
    }

    public String getFyear() {
        return fyear;
    }

    public void setFyear(String fyear) {
        this.fyear = fyear;
    }

    public Double getSeqno() {
        return seqno;
    }

    public void setSeqno(Double seqno) {
        this.seqno = seqno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
