/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.hr;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "hr_data_prev_comp")
@NamedQueries({@NamedQuery(name = "HrDataPrevComp.findAll", query = "SELECT h FROM HrDataPrevComp h"),
@NamedQuery(name = "HrDataPrevComp.findByCompCode", query = "SELECT h FROM HrDataPrevComp h WHERE h.hrDataPrevCompPK.compCode = :compCode"),
@NamedQuery(name = "HrDataPrevComp.findByAdvtCode", query = "SELECT h FROM HrDataPrevComp h WHERE h.hrDataPrevCompPK.advtCode = :advtCode"),
@NamedQuery(name = "HrDataPrevComp.findByJobCode", query = "SELECT h FROM HrDataPrevComp h WHERE h.hrDataPrevCompPK.jobCode = :jobCode"),
@NamedQuery(name = "HrDataPrevComp.findByCandSrno", query = "SELECT h FROM HrDataPrevComp h WHERE h.hrDataPrevCompPK.candSrno = :candSrno"),
@NamedQuery(name = "HrDataPrevComp.findByCompName", query = "SELECT h FROM HrDataPrevComp h WHERE h.hrDataPrevCompPK.compName = :compName"),
@NamedQuery(name = "HrDataPrevComp.findByAnnualTurn", query = "SELECT h FROM HrDataPrevComp h WHERE h.annualTurn = :annualTurn"),
@NamedQuery(name = "HrDataPrevComp.findByLeaveDate", query = "SELECT h FROM HrDataPrevComp h WHERE h.leaveDate = :leaveDate"),
@NamedQuery(name = "HrDataPrevComp.findByCompAdd", query = "SELECT h FROM HrDataPrevComp h WHERE h.compAdd = :compAdd"),
@NamedQuery(name = "HrDataPrevComp.findByDurFrom", query = "SELECT h FROM HrDataPrevComp h WHERE h.durFrom = :durFrom"),
@NamedQuery(name = "HrDataPrevComp.findByDurTo", query = "SELECT h FROM HrDataPrevComp h WHERE h.durTo = :durTo"),
@NamedQuery(name = "HrDataPrevComp.findByDesgJoin", query = "SELECT h FROM HrDataPrevComp h WHERE h.desgJoin = :desgJoin"),
@NamedQuery(name = "HrDataPrevComp.findByDesgLeave", query = "SELECT h FROM HrDataPrevComp h WHERE h.desgLeave = :desgLeave"),
@NamedQuery(name = "HrDataPrevComp.findByTotEmp", query = "SELECT h FROM HrDataPrevComp h WHERE h.totEmp = :totEmp"),
@NamedQuery(name = "HrDataPrevComp.findByEmpUnder", query = "SELECT h FROM HrDataPrevComp h WHERE h.empUnder = :empUnder"),
@NamedQuery(name = "HrDataPrevComp.findBySalJoin", query = "SELECT h FROM HrDataPrevComp h WHERE h.salJoin = :salJoin"),
@NamedQuery(name = "HrDataPrevComp.findBySalLeave", query = "SELECT h FROM HrDataPrevComp h WHERE h.salLeave = :salLeave"),
@NamedQuery(name = "HrDataPrevComp.findByReason", query = "SELECT h FROM HrDataPrevComp h WHERE h.reason = :reason"),
@NamedQuery(name = "HrDataPrevComp.findByDefaultCompCode", query = "SELECT h FROM HrDataPrevComp h WHERE h.defaultCompCode = :defaultCompCode"),
@NamedQuery(name = "HrDataPrevComp.findByStatFlag", query = "SELECT h FROM HrDataPrevComp h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrDataPrevComp.findByStatUpFlag", query = "SELECT h FROM HrDataPrevComp h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrDataPrevComp.findByModDate", query = "SELECT h FROM HrDataPrevComp h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrDataPrevComp.findByAuthBy", query = "SELECT h FROM HrDataPrevComp h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrDataPrevComp.findByEnteredBy", query = "SELECT h FROM HrDataPrevComp h WHERE h.enteredBy = :enteredBy")})
public class HrDataPrevComp extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrDataPrevCompPK hrDataPrevCompPK;
    @Column(name = "ANNUAL_TURN")
    private Double annualTurn;
    @Column(name = "LEAVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date leaveDate;
    @Column(name = "COMP_ADD")
    private String compAdd;
    @Column(name = "DUR_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date durFrom;
    @Column(name = "DUR_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date durTo;
    @Column(name = "DESG_JOIN")
    private String desgJoin;
    @Column(name = "DESG_LEAVE")
    private String desgLeave;
    @Column(name = "TOT_EMP")
    private Integer totEmp;
    @Column(name = "EMP_UNDER")
    private Integer empUnder;
    @Column(name = "SAL_JOIN")
    private Double salJoin;
    @Column(name = "SAL_LEAVE")
    private Double salLeave;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "DEFAULT_COMP_CODE")
    private Integer defaultCompCode;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "CAND_SRNO", referencedColumnName = "CAND_ID", insertable = false, updatable = false), @JoinColumn(name = "ADVT_CODE", referencedColumnName = "ADVT_CODE", insertable = false, updatable = false), @JoinColumn(name = "JOB_CODE", referencedColumnName = "JOB_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrDatabank hrDatabank;

    public HrDataPrevComp() {
    }

    public HrDataPrevComp(HrDataPrevCompPK hrDataPrevCompPK) {
        this.hrDataPrevCompPK = hrDataPrevCompPK;
    }

    public HrDataPrevComp(int compCode, String advtCode, String jobCode, String candSrno, String compName) {
        this.hrDataPrevCompPK = new HrDataPrevCompPK(compCode, advtCode, jobCode, candSrno, compName);
    }

    public HrDataPrevCompPK getHrDataPrevCompPK() {
        return hrDataPrevCompPK;
    }

    public void setHrDataPrevCompPK(HrDataPrevCompPK hrDataPrevCompPK) {
        this.hrDataPrevCompPK = hrDataPrevCompPK;
    }

    public Double getAnnualTurn() {
        return annualTurn;
    }

    public void setAnnualTurn(Double annualTurn) {
        this.annualTurn = annualTurn;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getCompAdd() {
        return compAdd;
    }

    public void setCompAdd(String compAdd) {
        this.compAdd = compAdd;
    }

    public Date getDurFrom() {
        return durFrom;
    }

    public void setDurFrom(Date durFrom) {
        this.durFrom = durFrom;
    }

    public Date getDurTo() {
        return durTo;
    }

    public void setDurTo(Date durTo) {
        this.durTo = durTo;
    }

    public String getDesgJoin() {
        return desgJoin;
    }

    public void setDesgJoin(String desgJoin) {
        this.desgJoin = desgJoin;
    }

    public String getDesgLeave() {
        return desgLeave;
    }

    public void setDesgLeave(String desgLeave) {
        this.desgLeave = desgLeave;
    }

    public Integer getTotEmp() {
        return totEmp;
    }

    public void setTotEmp(Integer totEmp) {
        this.totEmp = totEmp;
    }

    public Integer getEmpUnder() {
        return empUnder;
    }

    public void setEmpUnder(Integer empUnder) {
        this.empUnder = empUnder;
    }

    public Double getSalJoin() {
        return salJoin;
    }

    public void setSalJoin(Double salJoin) {
        this.salJoin = salJoin;
    }

    public Double getSalLeave() {
        return salLeave;
    }

    public void setSalLeave(Double salLeave) {
        this.salLeave = salLeave;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDefaultCompCode() {
        return defaultCompCode;
    }

    public void setDefaultCompCode(Integer defaultCompCode) {
        this.defaultCompCode = defaultCompCode;
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

    public HrDatabank getHrDatabank() {
        return hrDatabank;
    }

    public void setHrDatabank(HrDatabank hrDatabank) {
        this.hrDatabank = hrDatabank;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrDataPrevCompPK != null ? hrDataPrevCompPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDataPrevComp)) {
            return false;
        }
        HrDataPrevComp other = (HrDataPrevComp) object;
        if ((this.hrDataPrevCompPK == null && other.hrDataPrevCompPK != null) || (this.hrDataPrevCompPK != null && !this.hrDataPrevCompPK.equals(other.hrDataPrevCompPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrDataPrevComp[hrDataPrevCompPK=" + hrDataPrevCompPK + "]";
    }

}
