/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author sipl
 */
@Entity
@Table(name = "gl_desc_range")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GlDescRange.findAll", query = "SELECT g FROM GlDescRange g"),
    @NamedQuery(name = "GlDescRange.findByCode", query = "SELECT g FROM GlDescRange g WHERE g.code = :code"),
    @NamedQuery(name = "GlDescRange.findByGlname", query = "SELECT g FROM GlDescRange g WHERE g.glname = :glname"),
    @NamedQuery(name = "GlDescRange.findByFromno", query = "SELECT g FROM GlDescRange g WHERE g.fromno = :fromno"),
    @NamedQuery(name = "GlDescRange.findByTono", query = "SELECT g FROM GlDescRange g WHERE g.tono = :tono"),
    @NamedQuery(name = "GlDescRange.findByRecordCreaterID", query = "SELECT g FROM GlDescRange g WHERE g.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "GlDescRange.findByCreationTime", query = "SELECT g FROM GlDescRange g WHERE g.creationTime = :creationTime"),
    @NamedQuery(name = "GlDescRange.findByTsCnt", query = "SELECT g FROM GlDescRange g WHERE g.tsCnt = :tsCnt"),
    @NamedQuery(name = "GlDescRange.findBySecType", query = "SELECT g FROM GlDescRange g WHERE g.secType = :secType")})
public class GlDescRange implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Code")
    private Integer code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Glname")
    private String glname;
    @Size(max = 6)
    @Column(name = "FROMNO")
    private String fromno;
    @Size(max = 6)
    @Column(name = "TONO")
    private String tono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RecordCreaterID")
    private String recordCreaterID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TS_CNT")
    private int tsCnt;
    @Size(max = 1)
    @Column(name = "secType")
    private String secType;

    public GlDescRange() {
    }

    public GlDescRange(Integer code) {
        this.code = code;
    }

    public GlDescRange(Integer code, String glname, String recordCreaterID, Date creationTime, int tsCnt) {
        this.code = code;
        this.glname = glname;
        this.recordCreaterID = recordCreaterID;
        this.creationTime = creationTime;
        this.tsCnt = tsCnt;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getGlname() {
        return glname;
    }

    public void setGlname(String glname) {
        this.glname = glname;
    }

    public String getFromno() {
        return fromno;
    }

    public void setFromno(String fromno) {
        this.fromno = fromno;
    }

    public String getTono() {
        return tono;
    }

    public void setTono(String tono) {
        this.tono = tono;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public int getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(int tsCnt) {
        this.tsCnt = tsCnt;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlDescRange)) {
            return false;
        }
        GlDescRange other = (GlDescRange) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.GlDescRange[ code=" + code + " ]";
    }
    
}
