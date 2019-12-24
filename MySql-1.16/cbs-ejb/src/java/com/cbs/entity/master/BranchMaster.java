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
@Table(name = "branchmaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BranchMaster.findAll", query = "SELECT b FROM BranchMaster b"),
    @NamedQuery(name = "BranchMaster.findByBranchName", query = "SELECT b FROM BranchMaster b WHERE b.branchName = :branchName"),
    @NamedQuery(name = "BranchMaster.findByAddress", query = "SELECT b FROM BranchMaster b WHERE b.address = :address"),
    @NamedQuery(name = "BranchMaster.findByBrnCode", query = "SELECT b FROM BranchMaster b WHERE b.brnCode = :brnCode"),
    @NamedQuery(name = "BranchMaster.findByAlphaCode", query = "SELECT b FROM BranchMaster b WHERE b.alphaCode = :alphaCode"),
    @NamedQuery(name = "BranchMaster.findByCity", query = "SELECT b FROM BranchMaster b WHERE b.city = :city"),
    @NamedQuery(name = "BranchMaster.findByState", query = "SELECT b FROM BranchMaster b WHERE b.state = :state"),
    @NamedQuery(name = "BranchMaster.findByROffice", query = "SELECT b FROM BranchMaster b WHERE b.rOffice = :rOffice"),
    @NamedQuery(name = "BranchMaster.findByPincode", query = "SELECT b FROM BranchMaster b WHERE b.pincode = :pincode"),
    @NamedQuery(name = "BranchMaster.findByEnterBy", query = "SELECT b FROM BranchMaster b WHERE b.enterBy = :enterBy"),
    @NamedQuery(name = "BranchMaster.findByExCounter", query = "SELECT b FROM BranchMaster b WHERE b.exCounter = :exCounter"),
    @NamedQuery(name = "BranchMaster.findByParentBrnCode", query = "SELECT b FROM BranchMaster b WHERE b.parentBrnCode = :parentBrnCode"),
    @NamedQuery(name = "BranchMaster.findByIfscCode", query = "SELECT b FROM BranchMaster b WHERE b.ifscCode = :ifscCode")})
public class BranchMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "BranchName")
    private String branchName;
    @Size(max = 150)
    @Column(name = "Address")
    private String address;
    @Column(name = "BrnCode")
    private Integer brnCode;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "AlphaCode")
    private String alphaCode;
    @Size(max = 50)
    @Column(name = "City")
    private String city;
    @Size(max = 50)
    @Column(name = "State")
    private String state;
    @Size(max = 50)
    @Column(name = "ROffice")
    private String rOffice;
    @Column(name = "Pincode")
    private Integer pincode;
    @Size(max = 20)
    @Column(name = "EnterBy")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ex_Counter")
    private char exCounter;
    @Column(name = "Parent_BrnCode")
    private Integer parentBrnCode;
    @Size(max = 15)
    @Column(name = "IFSC_CODE")
    private String ifscCode;

    public BranchMaster() {
    }

    public BranchMaster(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public BranchMaster(String alphaCode, char exCounter) {
        this.alphaCode = alphaCode;
        this.exCounter = exCounter;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(Integer brnCode) {
        this.brnCode = brnCode;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getROffice() {
        return rOffice;
    }

    public void setROffice(String rOffice) {
        this.rOffice = rOffice;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public char getExCounter() {
        return exCounter;
    }

    public void setExCounter(char exCounter) {
        this.exCounter = exCounter;
    }

    public Integer getParentBrnCode() {
        return parentBrnCode;
    }

    public void setParentBrnCode(Integer parentBrnCode) {
        this.parentBrnCode = parentBrnCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alphaCode != null ? alphaCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BranchMaster)) {
            return false;
        }
        BranchMaster other = (BranchMaster) object;
        if ((this.alphaCode == null && other.alphaCode != null) || (this.alphaCode != null && !this.alphaCode.equals(other.alphaCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.master.BranchMaster[ alphaCode=" + alphaCode + " ]";
    }
}
