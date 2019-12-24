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
@Table(name = "eps_corrbranchdetail")
@NamedQueries({
    @NamedQuery(name = "EPSCorrBranchDetail.findAll", query = "SELECT e FROM EPSCorrBranchDetail e"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByBankCode", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.bankCode = :bankCode"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByBranchCode", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.branchCode = :branchCode"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByBranchName", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.branchName = :branchName"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByShortName", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.shortName = :shortName"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByAddress", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.address = :address"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByTradeFinanceFlag", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.tradeFinanceFlag = :tradeFinanceFlag"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByRegionCode", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.regionCode = :regionCode"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByZoneCode", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.zoneCode = :zoneCode"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByIFSCode", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.iFSCode = :iFSCode"),
    @NamedQuery(name = "EPSCorrBranchDetail.findByCorrAccNo", query = "SELECT e FROM EPSCorrBranchDetail e WHERE e.corrAccNo = :corrAccNo")})
public class EPSCorrBranchDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "BankCode")
    private String bankCode;
    @Id
    @Basic(optional = false)
    @Column(name = "BranchCode")
    private String branchCode;
    @Basic(optional = false)
    @Column(name = "BranchName")
    private String branchName;
    @Column(name = "ShortName")
    private String shortName;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Column(name = "TradeFinanceFlag")
    private String tradeFinanceFlag;
    @Column(name = "RegionCode")
    private String regionCode;
    @Column(name = "ZoneCode")
    private String zoneCode;
    @Basic(optional = false)
    @Column(name = "IFSCode")
    private String iFSCode;
    @Basic(optional = false)
    @Column(name = "CorrAccNo")
    private String corrAccNo;

    public EPSCorrBranchDetail() {
    }

    public EPSCorrBranchDetail(String branchCode) {
        this.branchCode = branchCode;
    }

    public EPSCorrBranchDetail(String branchCode, String bankCode, String branchName, String address, String iFSCode, String corrAccNo) {
        this.branchCode = branchCode;
        this.bankCode = bankCode;
        this.branchName = branchName;
        this.address = address;
        this.iFSCode = iFSCode;
        this.corrAccNo = corrAccNo;
    }

    public EPSCorrBranchDetail(String bankCode, String branchCode, String branchName, String shortName, String address, String tradeFinanceFlag, String regionCode, String zoneCode, String iFSCode, String corrAccNo) {
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.shortName = shortName;
        this.address = address;
        this.tradeFinanceFlag = tradeFinanceFlag;
        this.regionCode = regionCode;
        this.zoneCode = zoneCode;
        this.iFSCode = iFSCode;
        this.corrAccNo = corrAccNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTradeFinanceFlag() {
        return tradeFinanceFlag;
    }

    public void setTradeFinanceFlag(String tradeFinanceFlag) {
        this.tradeFinanceFlag = tradeFinanceFlag;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getIFSCode() {
        return iFSCode;
    }

    public void setIFSCode(String iFSCode) {
        this.iFSCode = iFSCode;
    }

    public String getCorrAccNo() {
        return corrAccNo;
    }

    public void setCorrAccNo(String corrAccNo) {
        this.corrAccNo = corrAccNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchCode != null ? branchCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSCorrBranchDetail)) {
            return false;
        }
        EPSCorrBranchDetail other = (EPSCorrBranchDetail) object;
        if ((this.branchCode == null && other.branchCode != null) || (this.branchCode != null && !this.branchCode.equals(other.branchCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSCorrBranchDetail[branchCode=" + branchCode + "]";
    }
}
