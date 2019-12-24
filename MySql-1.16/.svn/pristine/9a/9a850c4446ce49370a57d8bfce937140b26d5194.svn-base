/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class TdCustomermasterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "custno")
    private String custno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "actype")
    private String actype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "brncode")
    private String brncode;

    public TdCustomermasterPK() {
    }

    public TdCustomermasterPK(String custno, String actype, String brncode) {
        this.custno = custno;
        this.actype = actype;
        this.brncode = brncode;
    }

    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custno != null ? custno.hashCode() : 0);
        hash += (actype != null ? actype.hashCode() : 0);
        hash += (brncode != null ? brncode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TdCustomermasterPK)) {
            return false;
        }
        TdCustomermasterPK other = (TdCustomermasterPK) object;
        if ((this.custno == null && other.custno != null) || (this.custno != null && !this.custno.equals(other.custno))) {
            return false;
        }
        if ((this.actype == null && other.actype != null) || (this.actype != null && !this.actype.equals(other.actype))) {
            return false;
        }
        if ((this.brncode == null && other.brncode != null) || (this.brncode != null && !this.brncode.equals(other.brncode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.TdCustomermasterPK[ custno=" + custno + ", actype=" + actype + ", brncode=" + brncode + " ]";
    }
    
}
