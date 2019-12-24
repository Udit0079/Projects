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
@Table(name = "hr_promotion_details")
@NamedQueries({@NamedQuery(name = "HrPromotionDetails.findAll", query = "SELECT h FROM HrPromotionDetails h"),
@NamedQuery(name="HrPromotionDetails.findAllPrmiarykey" ,query="SELECT h FROM HrPromotionDetails h where h.hrPromotionDetailsPK.compCode = :compCode and h.hrPromotionDetailsPK.arNo= :arNo"),
@NamedQuery(name = "HrPromotionDetails.findByCompCode", query = "SELECT h FROM HrPromotionDetails h WHERE h.hrPromotionDetailsPK.compCode = :compCode"),
@NamedQuery(name = "HrPromotionDetails.findByEmpCode", query = "SELECT h FROM HrPromotionDetails h WHERE h.empCode = :empCode"),
@NamedQuery(name = "HrPromotionDetails.findMaxArNoByCompCode", query = "SELECT max(h.hrPromotionDetailsPK.arNo) FROM HrPromotionDetails h WHERE h.hrPromotionDetailsPK.compCode = :compCode"),
@NamedQuery(name = "HrPromotionDetails.findByArNo", query = "SELECT h FROM HrPromotionDetails h WHERE h.hrPromotionDetailsPK.arNo = :arNo"), @NamedQuery(name = "HrPromotionDetails.findByArdate", query = "SELECT h FROM HrPromotionDetails h WHERE h.ardate = :ardate"), @NamedQuery(name = "HrPromotionDetails.findByDeptFrom", query = "SELECT h FROM HrPromotionDetails h WHERE h.deptFrom = :deptFrom"), @NamedQuery(name = "HrPromotionDetails.findByDeptTo", query = "SELECT h FROM HrPromotionDetails h WHERE h.deptTo = :deptTo"), @NamedQuery(name = "HrPromotionDetails.findByBlockFrom", query = "SELECT h FROM HrPromotionDetails h WHERE h.blockFrom = :blockFrom"), @NamedQuery(name = "HrPromotionDetails.findByBlockTo", query = "SELECT h FROM HrPromotionDetails h WHERE h.blockTo = :blockTo"), @NamedQuery(name = "HrPromotionDetails.findByZoneFrom", query = "SELECT h FROM HrPromotionDetails h WHERE h.zoneFrom = :zoneFrom"), @NamedQuery(name = "HrPromotionDetails.findByZoneTo", query = "SELECT h FROM HrPromotionDetails h WHERE h.zoneTo = :zoneTo"), @NamedQuery(name = "HrPromotionDetails.findByPresLocat", query = "SELECT h FROM HrPromotionDetails h WHERE h.presLocat = :presLocat"), @NamedQuery(name = "HrPromotionDetails.findByProposLocat", query = "SELECT h FROM HrPromotionDetails h WHERE h.proposLocat = :proposLocat"), @NamedQuery(name = "HrPromotionDetails.findBySalaryPres", query = "SELECT h FROM HrPromotionDetails h WHERE h.salaryPres = :salaryPres"), @NamedQuery(name = "HrPromotionDetails.findBySalaryPropos", query = "SELECT h FROM HrPromotionDetails h WHERE h.salaryPropos = :salaryPropos"), @NamedQuery(name = "HrPromotionDetails.findByPresDesig", query = "SELECT h FROM HrPromotionDetails h WHERE h.presDesig = :presDesig"), @NamedQuery(name = "HrPromotionDetails.findByProposDesig", query = "SELECT h FROM HrPromotionDetails h WHERE h.proposDesig = :proposDesig"), @NamedQuery(name = "HrPromotionDetails.findByPresRepId", query = "SELECT h FROM HrPromotionDetails h WHERE h.presRepId = :presRepId"), @NamedQuery(name = "HrPromotionDetails.findByProposRepId", query = "SELECT h FROM HrPromotionDetails h WHERE h.proposRepId = :proposRepId"), @NamedQuery(name = "HrPromotionDetails.findByBudgtStatus", query = "SELECT h FROM HrPromotionDetails h WHERE h.budgtStatus = :budgtStatus"), @NamedQuery(name = "HrPromotionDetails.findByOverRating", query = "SELECT h FROM HrPromotionDetails h WHERE h.overRating = :overRating"), @NamedQuery(name = "HrPromotionDetails.findByRemarks1", query = "SELECT h FROM HrPromotionDetails h WHERE h.remarks1 = :remarks1"), @NamedQuery(name = "HrPromotionDetails.findByPromWef", query = "SELECT h FROM HrPromotionDetails h WHERE h.promWef = :promWef"), @NamedQuery(name = "HrPromotionDetails.findByHeadApprov", query = "SELECT h FROM HrPromotionDetails h WHERE h.headApprov = :headApprov"), @NamedQuery(name = "HrPromotionDetails.findByHrdApprov", query = "SELECT h FROM HrPromotionDetails h WHERE h.hrdApprov = :hrdApprov"), @NamedQuery(name = "HrPromotionDetails.findByMdApprov", query = "SELECT h FROM HrPromotionDetails h WHERE h.mdApprov = :mdApprov"), @NamedQuery(name = "HrPromotionDetails.findByStatus", query = "SELECT h FROM HrPromotionDetails h WHERE h.status = :status"), @NamedQuery(name = "HrPromotionDetails.findByEmpIdOld", query = "SELECT h FROM HrPromotionDetails h WHERE h.empIdOld = :empIdOld"), @NamedQuery(name = "HrPromotionDetails.findByDefaultComp", query = "SELECT h FROM HrPromotionDetails h WHERE h.defaultComp = :defaultComp"), @NamedQuery(name = "HrPromotionDetails.findByStatFlag", query = "SELECT h FROM HrPromotionDetails h WHERE h.statFlag = :statFlag"), @NamedQuery(name = "HrPromotionDetails.findByStatUpFlag", query = "SELECT h FROM HrPromotionDetails h WHERE h.statUpFlag = :statUpFlag"), @NamedQuery(name = "HrPromotionDetails.findByModDate", query = "SELECT h FROM HrPromotionDetails h WHERE h.modDate = :modDate"), @NamedQuery(name = "HrPromotionDetails.findByAuthBy", query = "SELECT h FROM HrPromotionDetails h WHERE h.authBy = :authBy"), @NamedQuery(name = "HrPromotionDetails.findByEnteredBy", query = "SELECT h FROM HrPromotionDetails h WHERE h.enteredBy = :enteredBy")})
public class HrPromotionDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPromotionDetailsPK hrPromotionDetailsPK;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Column(name = "ARDATE")
    private String ardate;
    @Column(name = "DEPT_FROM")
    private String deptFrom;
    @Column(name = "DEPT_TO")
    private String deptTo;
    @Column(name = "BLOCK_FROM")
    private String blockFrom;
    @Column(name = "BLOCK_TO")
    private String blockTo;
    @Column(name = "ZONE_FROM")
    private String zoneFrom;
    @Column(name = "ZONE_TO")
    private String zoneTo;
    @Column(name = "PRES_LOCAT")
    private String presLocat;
    @Column(name = "PROPOS_LOCAT")
    private String proposLocat;
    @Column(name = "SALARY_PRES")
    private Double salaryPres;
    @Column(name = "SALARY_PROPOS")
    private Double salaryPropos;
    @Column(name = "PRES_DESIG")
    private String presDesig;
    @Column(name = "PROPOS_DESIG")
    private String proposDesig;
    @Column(name = "PRES_REP_ID")
    private String presRepId;
    @Column(name = "PROPOS_REP_ID")
    private String proposRepId;
    @Column(name = "BUDGT_STATUS")
    private Character budgtStatus;
    @Column(name = "OVER_RATING")
    private String overRating;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "PROM_WEF")
    private String promWef;
    @Column(name = "HEAD_APPROV")
    private Character headApprov;
    @Column(name = "HRD_APPROV")
    private Character hrdApprov;
    @Column(name = "MD_APPROV")
    private Character mdApprov;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "EMP_ID_OLD")
    private String empIdOld;
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

    public HrPromotionDetails() {
    }

    public HrPromotionDetails(HrPromotionDetailsPK hrPromotionDetailsPK) {
        this.hrPromotionDetailsPK = hrPromotionDetailsPK;
    }

    public HrPromotionDetails(HrPromotionDetailsPK hrPromotionDetailsPK, long empCode) {
        this.hrPromotionDetailsPK = hrPromotionDetailsPK;
        this.empCode = empCode;
    }

    public HrPromotionDetails(int compCode, String arNo) {
        this.hrPromotionDetailsPK = new HrPromotionDetailsPK(compCode, arNo);
    }

    public HrPromotionDetailsPK getHrPromotionDetailsPK() {
        return hrPromotionDetailsPK;
    }

    public void setHrPromotionDetailsPK(HrPromotionDetailsPK hrPromotionDetailsPK) {
        this.hrPromotionDetailsPK = hrPromotionDetailsPK;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getArdate() {
        return ardate;
    }

    public void setArdate(String ardate) {
        this.ardate = ardate;
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

    public String getPresLocat() {
        return presLocat;
    }

    public void setPresLocat(String presLocat) {
        this.presLocat = presLocat;
    }

    public String getProposLocat() {
        return proposLocat;
    }

    public void setProposLocat(String proposLocat) {
        this.proposLocat = proposLocat;
    }

    public Double getSalaryPres() {
        return salaryPres;
    }

    public void setSalaryPres(Double salaryPres) {
        this.salaryPres = salaryPres;
    }

    public Double getSalaryPropos() {
        return salaryPropos;
    }

    public void setSalaryPropos(Double salaryPropos) {
        this.salaryPropos = salaryPropos;
    }

    public String getPresDesig() {
        return presDesig;
    }

    public void setPresDesig(String presDesig) {
        this.presDesig = presDesig;
    }

    public String getProposDesig() {
        return proposDesig;
    }

    public void setProposDesig(String proposDesig) {
        this.proposDesig = proposDesig;
    }

    public String getPresRepId() {
        return presRepId;
    }

    public void setPresRepId(String presRepId) {
        this.presRepId = presRepId;
    }

    public String getProposRepId() {
        return proposRepId;
    }

    public void setProposRepId(String proposRepId) {
        this.proposRepId = proposRepId;
    }

    public Character getBudgtStatus() {
        return budgtStatus;
    }

    public void setBudgtStatus(Character budgtStatus) {
        this.budgtStatus = budgtStatus;
    }

    public String getOverRating() {
        return overRating;
    }

    public void setOverRating(String overRating) {
        this.overRating = overRating;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getPromWef() {
        return promWef;
    }

    public void setPromWef(String promWef) {
        this.promWef = promWef;
    }

    public Character getHeadApprov() {
        return headApprov;
    }

    public void setHeadApprov(Character headApprov) {
        this.headApprov = headApprov;
    }

    public Character getHrdApprov() {
        return hrdApprov;
    }

    public void setHrdApprov(Character hrdApprov) {
        this.hrdApprov = hrdApprov;
    }

    public Character getMdApprov() {
        return mdApprov;
    }

    public void setMdApprov(Character mdApprov) {
        this.mdApprov = mdApprov;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getEmpIdOld() {
        return empIdOld;
    }

    public void setEmpIdOld(String empIdOld) {
        this.empIdOld = empIdOld;
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
        hash += (hrPromotionDetailsPK != null ? hrPromotionDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPromotionDetails)) {
            return false;
        }
        HrPromotionDetails other = (HrPromotionDetails) object;
        if ((this.hrPromotionDetailsPK == null && other.hrPromotionDetailsPK != null) || (this.hrPromotionDetailsPK != null && !this.hrPromotionDetailsPK.equals(other.hrPromotionDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPromotionDetails[hrPromotionDetailsPK=" + hrPromotionDetailsPK + "]";
    }

}
