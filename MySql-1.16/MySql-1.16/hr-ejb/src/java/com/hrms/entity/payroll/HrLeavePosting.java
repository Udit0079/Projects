/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;


import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "hr_leave_posting")
@NamedQueries({@NamedQuery(name = "HrLeavePosting.findAll", query = "SELECT h FROM HrLeavePosting h"),
@NamedQuery(name = "HrLeavePosting.findByCompCode", query = "SELECT h FROM HrLeavePosting h WHERE h.hrLeavePostingPK.compCode = :compCode"),
@NamedQuery(name = "HrLeavePosting.findByEmpCode", query = "SELECT h FROM HrLeavePosting h WHERE h.hrLeavePostingPK.empCode = :empCode"),
@NamedQuery(name = "HrLeavePosting.findByLeaveCode", query = "SELECT h FROM HrLeavePosting h WHERE h.hrLeavePostingPK.leaveCode = :leaveCode"),
@NamedQuery(name = "HrLeavePosting.findByPostingDate", query = "SELECT h FROM HrLeavePosting h WHERE h.postingDate = :postingDate"),
@NamedQuery(name = "HrLeavePosting.findByDateFrom", query = "SELECT h FROM HrLeavePosting h WHERE h.hrLeavePostingPK.dateFrom = :dateFrom"),
@NamedQuery(name = "HrLeavePosting.findByDateTo", query = "SELECT h FROM HrLeavePosting h WHERE h.hrLeavePostingPK.dateTo = :dateTo"),
@NamedQuery(name = "HrLeavePosting.findByDefaultComp", query = "SELECT h FROM HrLeavePosting h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrLeavePosting.findByStatFlag", query = "SELECT h FROM HrLeavePosting h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrLeavePosting.findByStatUpFlag", query = "SELECT h FROM HrLeavePosting h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrLeavePosting.findByModDate", query = "SELECT h FROM HrLeavePosting h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrLeavePosting.findByAuthBy", query = "SELECT h FROM HrLeavePosting h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrLeavePosting.findByEnteredBy", query = "SELECT h FROM HrLeavePosting h WHERE h.enteredBy = :enteredBy"),
@NamedQuery(name = "HrLeavePosting.findhrLeavePostingByEmpCode", query = "SELECT h FROM HrLeavePosting h where h.hrLeavePostingPK.compCode = :compCode and h.hrLeavePostingPK.empCode = :empCode and h.hrLeavePostingPK.dateFrom = :dateFrom and h.hrLeavePostingPK.dateTo = :dateTo"),
@NamedQuery(name="HrLeavePosting.findhrLeavePostingByCheckAll",query="SELECT h FROM HrLeavePosting h where h.hrLeavePostingPK.compCode = :compCode and h.hrLeavePostingPK.empCode = :empCode and h.hrLeavePostingPK.dateFrom = :dateFrom and h.hrLeavePostingPK.dateTo = :dateTo and h.hrLeavePostingPK.leaveCode = :leaveCode ")
})
public class HrLeavePosting extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrLeavePostingPK hrLeavePostingPK;
    @Basic(optional = false)
    @Column(name = "POSTING_DATE")
    private String postingDate;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Basic(optional = false)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Basic(optional = false)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;
    @Basic(optional = false)
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Basic(optional = false)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrLeavePosting() {
    }

    public HrLeavePosting(HrLeavePostingPK hrLeavePostingPK) {
        this.hrLeavePostingPK = hrLeavePostingPK;
    }

    public HrLeavePosting(HrLeavePostingPK hrLeavePostingPK, String postingDate, String statFlag, String statUpFlag, Date modDate, String authBy, String enteredBy) {
        this.hrLeavePostingPK = hrLeavePostingPK;
        this.postingDate = postingDate;
        this.statFlag = statFlag;
        this.statUpFlag = statUpFlag;
        this.modDate = modDate;
        this.authBy = authBy;
        this.enteredBy = enteredBy;
    }

    public HrLeavePosting(int compCode, long empCode, String leaveCode, String dateFrom, String dateTo) {
        this.hrLeavePostingPK = new HrLeavePostingPK(compCode, empCode, leaveCode, dateFrom, dateTo);
    }

    public HrLeavePostingPK getHrLeavePostingPK() {
        return hrLeavePostingPK;
    }

    public void setHrLeavePostingPK(HrLeavePostingPK hrLeavePostingPK) {
        this.hrLeavePostingPK = hrLeavePostingPK;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
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

    public HrPersonnelDetails getHrPersonnelDetails() {
        return hrPersonnelDetails;
    }

    public void setHrPersonnelDetails(HrPersonnelDetails hrPersonnelDetails) {
        this.hrPersonnelDetails = hrPersonnelDetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrLeavePostingPK != null ? hrLeavePostingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeavePosting)) {
            return false;
        }
        HrLeavePosting other = (HrLeavePosting) object;
        if ((this.hrLeavePostingPK == null && other.hrLeavePostingPK != null) || (this.hrLeavePostingPK != null && !this.hrLeavePostingPK.equals(other.hrLeavePostingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrLeavePosting[hrLeavePostingPK=" + hrLeavePostingPK + "]";
    }

}
