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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Zeeshan Waris
 */
@Entity
@Table(name = "hr_org_chart")
@NamedQueries({@NamedQuery(name = "HrOrgChart.findAll", query = "SELECT h FROM HrOrgChart h"),
@NamedQuery(name = "HrOrgChart.findByCompCode", query = "SELECT h FROM HrOrgChart h WHERE h.hrOrgChartPK.compCode = :compCode"),
@NamedQuery(name = "HrOrgChart.findByDesgCode", query = "SELECT h FROM HrOrgChart h WHERE h.hrOrgChartPK.desgCode = :desgCode"),
@NamedQuery(name = "HrOrgChart.findByDesgCodeRp", query = "SELECT h FROM HrOrgChart h WHERE h.desgCodeRp = :desgCodeRp"),
@NamedQuery(name = "HrOrgChart.findByPosts", query = "SELECT h FROM HrOrgChart h WHERE h.posts = :posts"),
@NamedQuery(name = "HrOrgChart.findByJobSpecification", query = "SELECT h FROM HrOrgChart h WHERE h.jobSpecification = :jobSpecification"),
@NamedQuery(name = "HrOrgChart.findByDescription", query = "SELECT h FROM HrOrgChart h WHERE h.description = :description"),
@NamedQuery(name = "HrOrgChart.findByFlag1", query = "SELECT h FROM HrOrgChart h WHERE h.flag1 = :flag1"),
@NamedQuery(name = "HrOrgChart.findByDefaultComp", query = "SELECT h FROM HrOrgChart h WHERE h.defaultComp = :defaultComp"),
@NamedQuery(name = "HrOrgChart.findByStatFlag", query = "SELECT h FROM HrOrgChart h WHERE h.statFlag = :statFlag"),
@NamedQuery(name = "HrOrgChart.findByStatUpFlag", query = "SELECT h FROM HrOrgChart h WHERE h.statUpFlag = :statUpFlag"),
@NamedQuery(name = "HrOrgChart.findByModDate", query = "SELECT h FROM HrOrgChart h WHERE h.modDate = :modDate"),
@NamedQuery(name = "HrOrgChart.findByAuthBy", query = "SELECT h FROM HrOrgChart h WHERE h.authBy = :authBy"),
@NamedQuery(name = "HrOrgChart.findByEnteredBy", query = "SELECT h FROM HrOrgChart h WHERE h.enteredBy = :enteredBy")})
public class HrOrgChart extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrOrgChartPK hrOrgChartPK;
    @Column(name = "DESG_CODE_RP")
    private String desgCodeRp;
    @Column(name = "POSTS")
    private Integer posts;
    @Column(name = "JOB_SPECIFICATION")
    private String jobSpecification;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FLAG1")
    private Character flag1;
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
    @JoinColumns({@JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", insertable = false, updatable = false), @JoinColumn(name = "DESG_CODE", referencedColumnName = "STRUCT_CODE", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private HrMstStruct hrMstStruct;

    public HrOrgChart() {
    }

    public HrOrgChart(HrOrgChartPK hrOrgChartPK) {
        this.hrOrgChartPK = hrOrgChartPK;
    }

    public HrOrgChart(int compCode, String desgCode) {
        this.hrOrgChartPK = new HrOrgChartPK(compCode, desgCode);
    }

    public HrOrgChartPK getHrOrgChartPK() {
        return hrOrgChartPK;
    }

    public void setHrOrgChartPK(HrOrgChartPK hrOrgChartPK) {
        this.hrOrgChartPK = hrOrgChartPK;
    }

    public String getDesgCodeRp() {
        return desgCodeRp;
    }

    public void setDesgCodeRp(String desgCodeRp) {
        this.desgCodeRp = desgCodeRp;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    public String getJobSpecification() {
        return jobSpecification;
    }

    public void setJobSpecification(String jobSpecification) {
        this.jobSpecification = jobSpecification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getFlag1() {
        return flag1;
    }

    public void setFlag1(Character flag1) {
        this.flag1 = flag1;
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

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrOrgChartPK != null ? hrOrgChartPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrOrgChart)) {
            return false;
        }
        HrOrgChart other = (HrOrgChart) object;
        if ((this.hrOrgChartPK == null && other.hrOrgChartPK != null) || (this.hrOrgChartPK != null && !this.hrOrgChartPK.equals(other.hrOrgChartPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.hr.HrOrgChart[hrOrgChartPK=" + hrOrgChartPK + "]";
    }

}
