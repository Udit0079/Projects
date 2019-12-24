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
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Embeddable
public class NeftFileDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "FILE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fileDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEQ_NO")
    private long seqNo;

    public NeftFileDetailsPK() {
    }

    public NeftFileDetailsPK(Date fileDate, long seqNo) {
        this.fileDate = fileDate;
        this.seqNo = seqNo;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(long seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileDate != null ? fileDate.hashCode() : 0);
        hash += (int) seqNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftFileDetailsPK)) {
            return false;
        }
        NeftFileDetailsPK other = (NeftFileDetailsPK) object;
        if ((this.fileDate == null && other.fileDate != null) || (this.fileDate != null && !this.fileDate.equals(other.fileDate))) {
            return false;
        }
        if (this.seqNo != other.seqNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftFileDetailsPK[ fileDate=" + fileDate + ", seqNo=" + seqNo + " ]";
    }
    
}
