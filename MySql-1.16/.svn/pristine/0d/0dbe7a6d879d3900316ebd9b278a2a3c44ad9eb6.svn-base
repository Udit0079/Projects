/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "neft_file_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NeftFileDetails.findAll", query = "SELECT n FROM NeftFileDetails n"),
    @NamedQuery(name = "NeftFileDetails.findByFileDate", query = "SELECT n FROM NeftFileDetails n WHERE n.neftFileDetailsPK.fileDate = :fileDate"),
    @NamedQuery(name = "NeftFileDetails.findBySeqNo", query = "SELECT n FROM NeftFileDetails n WHERE n.neftFileDetailsPK.seqNo = :seqNo"),
    @NamedQuery(name = "NeftFileDetails.findByFileName", query = "SELECT n FROM NeftFileDetails n WHERE n.fileName = :fileName"),
    @NamedQuery(name = "NeftFileDetails.findByGenTime", query = "SELECT n FROM NeftFileDetails n WHERE n.genTime = :genTime")})
public class NeftFileDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NeftFileDetailsPK neftFileDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GEN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date genTime;

    public NeftFileDetails() {
    }

    public NeftFileDetails(NeftFileDetailsPK neftFileDetailsPK) {
        this.neftFileDetailsPK = neftFileDetailsPK;
    }

    public NeftFileDetails(NeftFileDetailsPK neftFileDetailsPK, String fileName, Date genTime) {
        this.neftFileDetailsPK = neftFileDetailsPK;
        this.fileName = fileName;
        this.genTime = genTime;
    }

    public NeftFileDetails(Date fileDate, long seqNo) {
        this.neftFileDetailsPK = new NeftFileDetailsPK(fileDate, seqNo);
    }

    public NeftFileDetailsPK getNeftFileDetailsPK() {
        return neftFileDetailsPK;
    }

    public void setNeftFileDetailsPK(NeftFileDetailsPK neftFileDetailsPK) {
        this.neftFileDetailsPK = neftFileDetailsPK;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (neftFileDetailsPK != null ? neftFileDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftFileDetails)) {
            return false;
        }
        NeftFileDetails other = (NeftFileDetails) object;
        if ((this.neftFileDetailsPK == null && other.neftFileDetailsPK != null) || (this.neftFileDetailsPK != null && !this.neftFileDetailsPK.equals(other.neftFileDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftFileDetails[ neftFileDetailsPK=" + neftFileDetailsPK + " ]";
    }
}
