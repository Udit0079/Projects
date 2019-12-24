/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
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

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_transfer_details")
@NamedQueries({@NamedQuery(name = "HrTransferDetails.findAll", query = "SELECT h FROM HrTransferDetails h"), @NamedQuery(name = "HrTransferDetails.findByCompCode", query = "SELECT h FROM HrTransferDetails h WHERE h.hrTransferDetailsPK.compCode = :compCode"), @NamedQuery(name = "HrTransferDetails.findByArNo", query = "SELECT h FROM HrTransferDetails h WHERE h.hrTransferDetailsPK.arNo = :arNo"), @NamedQuery(name = "HrTransferDetails.findByArdate", query = "SELECT h FROM HrTransferDetails h WHERE h.ardate = :ardate"), @NamedQuery(name = "HrTransferDetails.findByEmpCode", query = "SELECT h FROM HrTransferDetails h WHERE h.empCode = :empCode"), @NamedQuery(name = "HrTransferDetails.findByPresentDesig", query = "SELECT h FROM HrTransferDetails h WHERE h.presentDesig = :presentDesig"), @NamedQuery(name = "HrTransferDetails.findByProposedDesig", query = "SELECT h FROM HrTransferDetails h WHERE h.proposedDesig = :proposedDesig"), @NamedQuery(name = "HrTransferDetails.findByZoneFrom", query = "SELECT h FROM HrTransferDetails h WHERE h.zoneFrom = :zoneFrom"), @NamedQuery(name = "HrTransferDetails.findByZoneTo", query = "SELECT h FROM HrTransferDetails h WHERE h.zoneTo = :zoneTo"), @NamedQuery(name = "HrTransferDetails.findByBlockFrom", query = "SELECT h FROM HrTransferDetails h WHERE h.blockFrom = :blockFrom"), @NamedQuery(name = "HrTransferDetails.findByBlockTo", query = "SELECT h FROM HrTransferDetails h WHERE h.blockTo = :blockTo"), @NamedQuery(name = "HrTransferDetails.findByDeptFrom", query = "SELECT h FROM HrTransferDetails h WHERE h.deptFrom = :deptFrom"), @NamedQuery(name = "HrTransferDetails.findByDeptTo", query = "SELECT h FROM HrTransferDetails h WHERE h.deptTo = :deptTo"), @NamedQuery(name = "HrTransferDetails.findByLocationFrom", query = "SELECT h FROM HrTransferDetails h WHERE h.locationFrom = :locationFrom"), @NamedQuery(name = "HrTransferDetails.findByLocationTo", query = "SELECT h FROM HrTransferDetails h WHERE h.locationTo = :locationTo"), @NamedQuery(name = "HrTransferDetails.findByEffective", query = "SELECT h FROM HrTransferDetails h WHERE h.effective = :effective"), @NamedQuery(name = "HrTransferDetails.findByTransfer", query = "SELECT h FROM HrTransferDetails h WHERE h.transfer = :transfer"), @NamedQuery(name = "HrTransferDetails.findByPf", query = "SELECT h FROM HrTransferDetails h WHERE h.pf = :pf"), @NamedQuery(name = "HrTransferDetails.findByEsi", query = "SELECT h FROM HrTransferDetails h WHERE h.esi = :esi"), @NamedQuery(name = "HrTransferDetails.findBySalProcess", query = "SELECT h FROM HrTransferDetails h WHERE h.salProcess = :salProcess"), @NamedQuery(name = "HrTransferDetails.findByReason", query = "SELECT h FROM HrTransferDetails h WHERE h.reason = :reason"), @NamedQuery(name = "HrTransferDetails.findByDeput", query = "SELECT h FROM HrTransferDetails h WHERE h.deput = :deput"), @NamedQuery(name = "HrTransferDetails.findByDefaultComp", query = "SELECT h FROM HrTransferDetails h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrTransferDetails.findByStatFlag", query = "SELECT h FROM HrTransferDetails h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrTransferDetails.findByStatUpFlag", query = "SELECT h FROM HrTransferDetails h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrTransferDetails.findByModDate", query = "SELECT h FROM HrTransferDetails h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrTransferDetails.findByAuthBy", query = "SELECT h FROM HrTransferDetails h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrTransferDetails.findByEnteredBy", query = "SELECT h FROM HrTransferDetails h WHERE h.enteredBy = :enteredBy")})
public class HrTransferDetails  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrTransferDetailsPK hrTransferDetailsPK;
    @Column(name = "ARDATE")
    private String ardate;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "PRESENT_DESIG")
    private String presentDesig;
    @Column(name = "PROPOSED_DESIG")
    private String proposedDesig;
    @Basic(optional = false)
    @Column(name = "ZONE_FROM")
    private String zoneFrom;
    @Column(name = "ZONE_TO")
    private String zoneTo;
    @Basic(optional = false)
    @Column(name = "BLOCK_FROM")
    private String blockFrom;
    @Column(name = "BLOCK_TO")
    private String blockTo;
    @Basic(optional = false)
    @Column(name = "DEPT_FROM")
    private String deptFrom;
    @Column(name = "DEPT_TO")
    private String deptTo;
    @Basic(optional = false)
    @Column(name = "LOCATION_FROM")
    private String locationFrom;
    @Column(name = "LOCATION_TO")
    private String locationTo;
    @Column(name = "EFFECTIVE")
    private String effective;
    @Basic(optional = false)
    @Column(name = "TRANSFER")
    private String transfer;
    @Basic(optional = false)
    @Column(name = "PF")
    private String pf;
    @Basic(optional = false)
    @Column(name = "ESI")
    private String esi;
    @Basic(optional = false)
    @Column(name = "SAL_PROCESS")
    private String salProcess;
    @Basic(optional = false)
    @Column(name = "REASON")
    private String reason;
    @Column(name = "DEPUT")
    private String deput;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "ENTERED_BY")
    private String enteredBy;

    public HrTransferDetails() {
    }

    public HrTransferDetails(HrTransferDetailsPK hrTransferDetailsPK) {
        this.hrTransferDetailsPK = hrTransferDetailsPK;
    }

    public HrTransferDetails(HrTransferDetailsPK hrTransferDetailsPK, long empCode, String presentDesig, String zoneFrom, String blockFrom, String deptFrom, String locationFrom, String transfer, String pf, String esi, String salProcess, String reason) {
        this.hrTransferDetailsPK = hrTransferDetailsPK;
        this.empCode = empCode;
        this.presentDesig = presentDesig;
        this.zoneFrom = zoneFrom;
        this.blockFrom = blockFrom;
        this.deptFrom = deptFrom;
        this.locationFrom = locationFrom;
        this.transfer = transfer;
        this.pf = pf;
        this.esi = esi;
        this.salProcess = salProcess;
        this.reason = reason;
    }

    public HrTransferDetails(int compCode, String arNo) {
        this.hrTransferDetailsPK = new HrTransferDetailsPK(compCode, arNo);
    }

    public HrTransferDetailsPK getHrTransferDetailsPK() {
        return hrTransferDetailsPK;
    }

    public void setHrTransferDetailsPK(HrTransferDetailsPK hrTransferDetailsPK) {
        this.hrTransferDetailsPK = hrTransferDetailsPK;
    }

    public String getArdate() {
        return ardate;
    }

    public void setArdate(String ardate) {
        this.ardate = ardate;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getPresentDesig() {
        return presentDesig;
    }

    public void setPresentDesig(String presentDesig) {
        this.presentDesig = presentDesig;
    }

    public String getProposedDesig() {
        return proposedDesig;
    }

    public void setProposedDesig(String proposedDesig) {
        this.proposedDesig = proposedDesig;
    }

    public String getZoneFrom() {
        return zoneFrom;
    }

    public void setZoneFrom(String zoneFrom) {
        this.zoneFrom = zoneFrom;
    }

    public String getZoneTo() {
        return zoneTo;
    }

    public void setZoneTo(String zoneTo) {
        this.zoneTo = zoneTo;
    }

    public String getBlockFrom() {
        return blockFrom;
    }

    public void setBlockFrom(String blockFrom) {
        this.blockFrom = blockFrom;
    }

    public String getBlockTo() {
        return blockTo;
    }

    public void setBlockTo(String blockTo) {
        this.blockTo = blockTo;
    }

    public String getDeptFrom() {
        return deptFrom;
    }

    public void setDeptFrom(String deptFrom) {
        this.deptFrom = deptFrom;
    }

    public String getDeptTo() {
        return deptTo;
    }

    public void setDeptTo(String deptTo) {
        this.deptTo = deptTo;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getEsi() {
        return esi;
    }

    public void setEsi(String esi) {
        this.esi = esi;
    }

    public String getSalProcess() {
        return salProcess;
    }

    public void setSalProcess(String salProcess) {
        this.salProcess = salProcess;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDeput() {
        return deput;
    }

    public void setDeput(String deput) {
        this.deput = deput;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrTransferDetailsPK != null ? hrTransferDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTransferDetails)) {
            return false;
        }
        HrTransferDetails other = (HrTransferDetails) object;
        if ((this.hrTransferDetailsPK == null && other.hrTransferDetailsPK != null) || (this.hrTransferDetailsPK != null && !this.hrTransferDetailsPK.equals(other.hrTransferDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrTransferDetails[hrTransferDetailsPK=" + hrTransferDetailsPK + "]";
    }

}
