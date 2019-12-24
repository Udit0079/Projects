/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.entity.neftrtgs;

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
public class EPSAckMessagePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "SequenceNo")
    private String sequenceNo;
    @Basic(optional = false)
    @Column(name = "InsertionTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionTime;

    public EPSAckMessagePK() {
    }

    public EPSAckMessagePK(String sequenceNo, Date insertionTime) {
        this.sequenceNo = sequenceNo;
        this.insertionTime = insertionTime;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Date getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(Date insertionTime) {
        this.insertionTime = insertionTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sequenceNo != null ? sequenceNo.hashCode() : 0);
        hash += (insertionTime != null ? insertionTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSAckMessagePK)) {
            return false;
        }
        EPSAckMessagePK other = (EPSAckMessagePK) object;
        if ((this.sequenceNo == null && other.sequenceNo != null) || (this.sequenceNo != null && !this.sequenceNo.equals(other.sequenceNo))) {
            return false;
        }
        if ((this.insertionTime == null && other.insertionTime != null) || (this.insertionTime != null && !this.insertionTime.equals(other.insertionTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSAckMessagePK[sequenceNo=" + sequenceNo + ", insertionTime=" + insertionTime + "]";
    }

}
