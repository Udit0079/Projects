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
import javax.validation.constraints.Size;

/**
 *
 * @author mayank
 */
@Embeddable
public class InvestmentMasterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTROLNO")
    private int controlno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SECTYPE")
    private String sectype;

    public InvestmentMasterPK() {
    }

    public InvestmentMasterPK(int controlno, String sectype) {
        this.controlno = controlno;
        this.sectype = sectype;
    }

    public int getControlno() {
        return controlno;
    }

    public void setControlno(int controlno) {
        this.controlno = controlno;
    }

    public String getSectype() {
        return sectype;
    }

    public void setSectype(String sectype) {
        this.sectype = sectype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) controlno;
        hash += (sectype != null ? sectype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentMasterPK)) {
            return false;
        }
        InvestmentMasterPK other = (InvestmentMasterPK) object;
        if (this.controlno != other.controlno) {
            return false;
        }
        if ((this.sectype == null && other.sectype != null) || (this.sectype != null && !this.sectype.equals(other.sectype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentMasterPK[ controlno=" + controlno + ", sectype=" + sectype + " ]";
    }
    
}
