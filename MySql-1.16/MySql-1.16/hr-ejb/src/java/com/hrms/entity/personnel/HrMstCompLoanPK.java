/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Embeddable
public class HrMstCompLoanPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Basic(optional = false)
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;

    public HrMstCompLoanPK() {
    }

    public HrMstCompLoanPK(int compCode, Date fromDate, Date toDate) {
        this.compCode = compCode;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (fromDate != null ? fromDate.hashCode() : 0);
        hash += (toDate != null ? toDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstCompLoanPK)) {
            return false;
        }
        HrMstCompLoanPK other = (HrMstCompLoanPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.fromDate == null && other.fromDate != null) || (this.fromDate != null && !this.fromDate.equals(other.fromDate))) {
            return false;
        }
        if ((this.toDate == null && other.toDate != null) || (this.toDate != null && !this.toDate.equals(other.toDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.personnel.HrMstCompLoanPK[compCode=" + compCode + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
    }
}
