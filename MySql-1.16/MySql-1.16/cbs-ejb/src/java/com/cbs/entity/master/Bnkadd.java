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
@Table(name = "bnkadd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bnkadd.findAll", query = "SELECT b FROM Bnkadd b"),
    @NamedQuery(name = "Bnkadd.findByBankname", query = "SELECT b FROM Bnkadd b WHERE b.bankname = :bankname"),
    @NamedQuery(name = "Bnkadd.findByBankaddress", query = "SELECT b FROM Bnkadd b WHERE b.bankaddress = :bankaddress"),
    @NamedQuery(name = "Bnkadd.findByVersion", query = "SELECT b FROM Bnkadd b WHERE b.version = :version"),
    @NamedQuery(name = "Bnkadd.findByAlphacode", query = "SELECT b FROM Bnkadd b WHERE b.alphacode = :alphacode"),
    @NamedQuery(name = "Bnkadd.findByMicr", query = "SELECT b FROM Bnkadd b WHERE b.micr = :micr"),
    @NamedQuery(name = "Bnkadd.findByMicrcode", query = "SELECT b FROM Bnkadd b WHERE b.micrcode = :micrcode"),
    @NamedQuery(name = "Bnkadd.findByBranchcode", query = "SELECT b FROM Bnkadd b WHERE b.branchcode = :branchcode")})
public class Bnkadd extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bankname")
    private String bankname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bankaddress")
    private String bankaddress;
    @Size(max = 5)
    @Column(name = "version")
    private String version;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "alphacode")
    private String alphacode;
    @Size(max = 5)
    @Column(name = "micr")
    private String micr;
    @Size(max = 5)
    @Column(name = "micrcode")
    private String micrcode;
    @Column(name = "branchcode")
    private Integer branchcode;

    public Bnkadd() {
    }

    public Bnkadd(String alphacode) {
        this.alphacode = alphacode;
    }

    public Bnkadd(String alphacode, String bankname, String bankaddress) {
        this.alphacode = alphacode;
        this.bankname = bankname;
        this.bankaddress = bankaddress;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAlphacode() {
        return alphacode;
    }

    public void setAlphacode(String alphacode) {
        this.alphacode = alphacode;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getMicrcode() {
        return micrcode;
    }

    public void setMicrcode(String micrcode) {
        this.micrcode = micrcode;
    }

    public Integer getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(Integer branchcode) {
        this.branchcode = branchcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alphacode != null ? alphacode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bnkadd)) {
            return false;
        }
        Bnkadd other = (Bnkadd) object;
        if ((this.alphacode == null && other.alphacode != null) || (this.alphacode != null && !this.alphacode.equals(other.alphacode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.Bnkadd[ alphacode=" + alphacode + " ]";
    }
}
