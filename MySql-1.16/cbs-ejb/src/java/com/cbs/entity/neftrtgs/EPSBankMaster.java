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
 * @author Administrator
 */
@Entity
@Table(name = "eps_bankmaster")
@NamedQueries({
    @NamedQuery(name = "EPSBankMaster.findAll", query = "SELECT e FROM EPSBankMaster e"),
    @NamedQuery(name = "EPSBankMaster.findByBankCode", query = "SELECT e FROM EPSBankMaster e WHERE e.bankCode = :bankCode"),
    @NamedQuery(name = "EPSBankMaster.findByBankName", query = "SELECT e FROM EPSBankMaster e WHERE e.bankName = :bankName"),
    @NamedQuery(name = "EPSBankMaster.findByShortName", query = "SELECT e FROM EPSBankMaster e WHERE e.shortName = :shortName"),
    @NamedQuery(name = "EPSBankMaster.findByLocalClearingFlag", query = "SELECT e FROM EPSBankMaster e WHERE e.localClearingFlag = :localClearingFlag"),
    @NamedQuery(name = "EPSBankMaster.findByPrimeBankFlag", query = "SELECT e FROM EPSBankMaster e WHERE e.primeBankFlag = :primeBankFlag"),
    @NamedQuery(name = "EPSBankMaster.findByCorrBankFlag", query = "SELECT e FROM EPSBankMaster e WHERE e.corrBankFlag = :corrBankFlag"),
    @NamedQuery(name = "EPSBankMaster.findByBICode", query = "SELECT e FROM EPSBankMaster e WHERE e.bICode = :bICode"),
    @NamedQuery(name = "EPSBankMaster.findByWeeklyOff", query = "SELECT e FROM EPSBankMaster e WHERE e.weeklyOff = :weeklyOff")})
public class EPSBankMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "BankCode")
    private String bankCode;
    @Basic(optional = false)
    @Column(name = "BankName")
    private String bankName;
    @Column(name = "ShortName")
    private String shortName;
    @Column(name = "LocalClearingFlag")
    private String localClearingFlag;
    @Column(name = "PrimeBankFlag")
    private String primeBankFlag;
    @Basic(optional = false)
    @Column(name = "CorrBankFlag")
    private String corrBankFlag;
    @Column(name = "BICode")
    private String bICode;
    @Column(name = "Weekly_Off")
    private String weeklyOff;

    public EPSBankMaster() {
    }

    public EPSBankMaster(String bankCode) {
        this.bankCode = bankCode;
    }

    public EPSBankMaster(String bankCode, String bankName, String corrBankFlag) {
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.corrBankFlag = corrBankFlag;
    }

    /******************This Constructor is added by Dinesh***********************/
    public EPSBankMaster(String bankCode, String bankName, String shortName, String localClearingFlag, String primeBankFlag, String corrBankFlag, String bICode, String weeklyOff) {
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.shortName = shortName;
        this.localClearingFlag = localClearingFlag;
        this.primeBankFlag = primeBankFlag;
        this.corrBankFlag = corrBankFlag;
        this.bICode = bICode;
        this.weeklyOff = weeklyOff;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLocalClearingFlag() {
        return localClearingFlag;
    }

    public void setLocalClearingFlag(String localClearingFlag) {
        this.localClearingFlag = localClearingFlag;
    }

    public String getPrimeBankFlag() {
        return primeBankFlag;
    }

    public void setPrimeBankFlag(String primeBankFlag) {
        this.primeBankFlag = primeBankFlag;
    }

    public String getCorrBankFlag() {
        return corrBankFlag;
    }

    public void setCorrBankFlag(String corrBankFlag) {
        this.corrBankFlag = corrBankFlag;
    }

    public String getBICode() {
        return bICode;
    }

    public void setBICode(String bICode) {
        this.bICode = bICode;
    }

    public String getWeeklyOff() {
        return weeklyOff;
    }

    public void setWeeklyOff(String weeklyOff) {
        this.weeklyOff = weeklyOff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankCode != null ? bankCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSBankMaster)) {
            return false;
        }
        EPSBankMaster other = (EPSBankMaster) object;
        if ((this.bankCode == null && other.bankCode != null) || (this.bankCode != null && !this.bankCode.equals(other.bankCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSBankMaster[bankCode=" + bankCode + "]";
    }
}
