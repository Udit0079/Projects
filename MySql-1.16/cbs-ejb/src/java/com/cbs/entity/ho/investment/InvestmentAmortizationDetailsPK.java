/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sipl
 */
@Embeddable
public class InvestmentAmortizationDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private long ctrlNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SLNO")
    private long slno;

    public InvestmentAmortizationDetailsPK() {
    }

    public InvestmentAmortizationDetailsPK(long ctrlNo, long slno) {
        this.ctrlNo = ctrlNo;
        this.slno = slno;
    }

    public long getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(long ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public long getSlno() {
        return slno;
    }

    public void setSlno(long slno) {
        this.slno = slno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ctrlNo;
        hash += (int) slno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentAmortizationDetailsPK)) {
            return false;
        }
        InvestmentAmortizationDetailsPK other = (InvestmentAmortizationDetailsPK) object;
        if (this.ctrlNo != other.ctrlNo) {
            return false;
        }
        if (this.slno != other.slno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentAmortizationDetailsPK[ ctrlNo=" + ctrlNo + ", slno=" + slno + " ]";
    }
    
}
