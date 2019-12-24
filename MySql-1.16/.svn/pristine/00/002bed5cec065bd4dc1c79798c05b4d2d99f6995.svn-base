/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_mst_shift")
@NamedQueries({@NamedQuery(name = "HrMstShift.findAll", query = "SELECT h FROM HrMstShift h"),
    @NamedQuery(name = "HrMstShift.findByCompCode", query = "SELECT h FROM HrMstShift h WHERE h.hrMstShiftPK.compCode = :compCode"),
    @NamedQuery(name = "HrMstShift.findByShiftCode", query = "SELECT h FROM HrMstShift h WHERE h.hrMstShiftPK.shiftCode = :shiftCode"),
    @NamedQuery(name = "HrMstShift.findByShiftIn", query = "SELECT h FROM HrMstShift h WHERE h.shiftIn = :shiftIn"),
    @NamedQuery(name = "HrMstShift.findByShiftOut", query = "SELECT h FROM HrMstShift h WHERE h.shiftOut = :shiftOut"),
    @NamedQuery(name = "HrMstShift.findByShiftBfrom", query = "SELECT h FROM HrMstShift h WHERE h.shiftBfrom = :shiftBfrom"),
    @NamedQuery(name = "HrMstShift.findByShiftBto", query = "SELECT h FROM HrMstShift h WHERE h.shiftBto = :shiftBto"),
    @NamedQuery(name = "HrMstShift.findByShiftPunchTime", query = "SELECT h FROM HrMstShift h WHERE h.shiftPunchTime = :shiftPunchTime"),
    @NamedQuery(name = "HrMstShift.findByShiftOutPunch", query = "SELECT h FROM HrMstShift h WHERE h.shiftOutPunch = :shiftOutPunch"),
    @NamedQuery(name = "HrMstShift.findByShiftDesc", query = "SELECT h FROM HrMstShift h WHERE h.shiftDesc = :shiftDesc"),
    @NamedQuery(name = "HrMstShift.findByDefaultComp", query = "SELECT h FROM HrMstShift h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrMstShift.findByStatFlag", query = "SELECT h FROM HrMstShift h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrMstShift.findByStatUpFlag", query = "SELECT h FROM HrMstShift h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrMstShift.findByModDate", query = "SELECT h FROM HrMstShift h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrMstShift.findByAuthBy", query = "SELECT h FROM HrMstShift h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrMstShift.findByGraceShiftIn", query = "SELECT h FROM HrMstShift h WHERE h.graceShiftIn = :graceShiftIn"),
    @NamedQuery(name = "HrMstShift.findByGraceShiftOut", query = "SELECT h FROM HrMstShift h WHERE h.graceShiftOut = :graceShiftOut"),
    @NamedQuery(name = "HrMstShift.findByGraceShiftBreak", query = "SELECT h FROM HrMstShift h WHERE h.graceShiftBreak = :graceShiftBreak"),
    @NamedQuery(name = "HrMstShift.findByOdTimeFirst", query = "SELECT h FROM HrMstShift h WHERE h.odTimeFirst = :odTimeFirst"),
    @NamedQuery(name = "HrMstShift.findByOdTimeSecond", query = "SELECT h FROM HrMstShift h WHERE h.odTimeSecond = :odTimeSecond"),
    @NamedQuery(name = "HrMstShift.findByEnteredBy", query = "SELECT h FROM HrMstShift h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrMstShift.findByShiftCodeAndCompCode", query = "SELECT h FROM HrMstShift h WHERE h.hrMstShiftPK.compCode = :compCode and h.hrMstShiftPK.shiftCode = :shiftCode"),
    @NamedQuery(name = "HrMstShift.findMstShiftEntityByCompCode", query = "SELECT h FROM HrMstShift h WHERE h.hrMstShiftPK.compCode = :compCode")
})
public class HrMstShift extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrMstShiftPK hrMstShiftPK;
    @Column(name = "SHIFT_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftIn;
    @Column(name = "SHIFT_OUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftOut;
    @Column(name = "SHIFT_BFROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftBfrom;
    @Column(name = "SHIFT_BTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftBto;
    @Column(name = "SHIFT_PUNCH_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftPunchTime;
    @Column(name = "SHIFT_OUT_PUNCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftOutPunch;
    @Column(name = "SHIFT_DESC")
    private String shiftDesc;
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
    @Column(name = "GRACE_SHIFT_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date graceShiftIn;
    @Column(name = "GRACE_SHIFT_OUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date graceShiftOut;
    @Column(name = "GRACE_SHIFT_BREAK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date graceShiftBreak;
    @Column(name = "OD_TIME_FIRST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odTimeFirst;
    @Column(name = "OD_TIME_SECOND")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odTimeSecond;
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrMstShift")
    private Collection<HrShiftMap> hrShiftMapCollection;

    public HrMstShift() {
    }

    public HrMstShift(HrMstShiftPK hrMstShiftPK) {
        this.hrMstShiftPK = hrMstShiftPK;
    }

    public HrMstShift(int compCode, String shiftCode) {
        this.hrMstShiftPK = new HrMstShiftPK(compCode, shiftCode);
    }

    public HrMstShiftPK getHrMstShiftPK() {
        return hrMstShiftPK;
    }

    public void setHrMstShiftPK(HrMstShiftPK hrMstShiftPK) {
        this.hrMstShiftPK = hrMstShiftPK;
    }

    public Date getShiftIn() {
        return shiftIn;
    }

    public void setShiftIn(Date shiftIn) {
        this.shiftIn = shiftIn;
    }

    public Date getShiftOut() {
        return shiftOut;
    }

    public void setShiftOut(Date shiftOut) {
        this.shiftOut = shiftOut;
    }

    public Date getShiftBfrom() {
        return shiftBfrom;
    }

    public void setShiftBfrom(Date shiftBfrom) {
        this.shiftBfrom = shiftBfrom;
    }

    public Date getShiftBto() {
        return shiftBto;
    }

    public void setShiftBto(Date shiftBto) {
        this.shiftBto = shiftBto;
    }

    public Date getShiftPunchTime() {
        return shiftPunchTime;
    }

    public void setShiftPunchTime(Date shiftPunchTime) {
        this.shiftPunchTime = shiftPunchTime;
    }

    public Date getShiftOutPunch() {
        return shiftOutPunch;
    }

    public void setShiftOutPunch(Date shiftOutPunch) {
        this.shiftOutPunch = shiftOutPunch;
    }

    public String getShiftDesc() {
        return shiftDesc;
    }

    public void setShiftDesc(String shiftDesc) {
        this.shiftDesc = shiftDesc;
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

    public Date getGraceShiftIn() {
        return graceShiftIn;
    }

    public void setGraceShiftIn(Date graceShiftIn) {
        this.graceShiftIn = graceShiftIn;
    }

    public Date getGraceShiftOut() {
        return graceShiftOut;
    }

    public void setGraceShiftOut(Date graceShiftOut) {
        this.graceShiftOut = graceShiftOut;
    }

    public Date getGraceShiftBreak() {
        return graceShiftBreak;
    }

    public void setGraceShiftBreak(Date graceShiftBreak) {
        this.graceShiftBreak = graceShiftBreak;
    }

    public Date getOdTimeFirst() {
        return odTimeFirst;
    }

    public void setOdTimeFirst(Date odTimeFirst) {
        this.odTimeFirst = odTimeFirst;
    }

    public Date getOdTimeSecond() {
        return odTimeSecond;
    }

    public void setOdTimeSecond(Date odTimeSecond) {
        this.odTimeSecond = odTimeSecond;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Collection<HrShiftMap> getHrShiftMapCollection() {
        return hrShiftMapCollection;
    }

    public void setHrShiftMapCollection(Collection<HrShiftMap> hrShiftMapCollection) {
        this.hrShiftMapCollection = hrShiftMapCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrMstShiftPK != null ? hrMstShiftPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrMstShift)) {
            return false;
        }
        HrMstShift other = (HrMstShift) object;
        if ((this.hrMstShiftPK == null && other.hrMstShiftPK != null) || (this.hrMstShiftPK != null && !this.hrMstShiftPK.equals(other.hrMstShiftPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrMstShift[hrMstShiftPK=" + hrMstShiftPK + "]";
    }
}
