/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "reconvmast_trans")
@NamedQueries({
    @NamedQuery(name = "ReconvmastTrans.findAll", query = "SELECT r FROM ReconvmastTrans r"),
    @NamedQuery(name = "ReconvmastTrans.findByAcno", query = "SELECT r FROM ReconvmastTrans r WHERE r.acno = :acno"),
    @NamedQuery(name = "ReconvmastTrans.findByTrsno", query = "SELECT r FROM ReconvmastTrans r WHERE r.trsno = :trsno"),
    @NamedQuery(name = "ReconvmastTrans.findByBrncode", query = "SELECT r FROM ReconvmastTrans r WHERE r.brncode = :brncode")})
public class ReconvmastTrans extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "acno")
    private String acno;
    @Id
    @Basic(optional = false)
    @Column(name = "trsno")
    private Double trsno;
    @Column(name = "brncode")
    private String brncode;

    public ReconvmastTrans() {
    }

    public ReconvmastTrans(Double trsno) {
        this.trsno = trsno;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public Double getTrsno() {
        return trsno;
    }

    public void setTrsno(Double trsno) {
        this.trsno = trsno;
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
        hash += (trsno != null ? trsno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReconvmastTrans)) {
            return false;
        }
        ReconvmastTrans other = (ReconvmastTrans) object;
        if ((this.trsno == null && other.trsno != null) || (this.trsno != null && !this.trsno.equals(other.trsno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.ReconvmastTrans[trsno=" + trsno + "]";
    }
}
