/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.master;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "gltable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gltable.findAll", query = "SELECT g FROM Gltable g"),
    @NamedQuery(name = "Gltable.findByAcNo", query = "SELECT g FROM Gltable g WHERE g.acNo = :acNo"),
    @NamedQuery(name = "Gltable.findByAcName", query = "SELECT g FROM Gltable g WHERE g.acName = :acName"),
    @NamedQuery(name = "Gltable.findByPostflag", query = "SELECT g FROM Gltable g WHERE g.postflag = :postflag"),
    @NamedQuery(name = "Gltable.findByMsgflag", query = "SELECT g FROM Gltable g WHERE g.msgflag = :msgflag")})
public class Gltable extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "AcNo")
    private String acNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "AcName")
    private String acName;
    @Column(name = "postflag")
    private Integer postflag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSGFLAG")
    private int msgflag;

    public Gltable() {
    }

    public Gltable(String acNo) {
        this.acNo = acNo;
    }

    public Gltable(String acNo, String acName, int msgflag) {
        this.acNo = acNo;
        this.acName = acName;
        this.msgflag = msgflag;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public Integer getPostflag() {
        return postflag;
    }

    public void setPostflag(Integer postflag) {
        this.postflag = postflag;
    }

    public int getMsgflag() {
        return msgflag;
    }

    public void setMsgflag(int msgflag) {
        this.msgflag = msgflag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acNo != null ? acNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gltable)) {
            return false;
        }
        Gltable other = (Gltable) object;
        if ((this.acNo == null && other.acNo != null) || (this.acNo != null && !this.acNo.equals(other.acNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.Gltable[ acNo=" + acNo + " ]";
    }
}
