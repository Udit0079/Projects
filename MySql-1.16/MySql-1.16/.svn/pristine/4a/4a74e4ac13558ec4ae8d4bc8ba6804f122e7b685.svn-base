/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "hr_slab_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSlabMaster.findAll", query = "SELECT h FROM HrSlabMaster h"),
    @NamedQuery(name = "HrSlabMaster.findBySlabCode", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.slabCode = :slabCode"),
    @NamedQuery(name = "HrSlabMaster.findByDesgId", query = "SELECT h FROM HrSlabMaster h WHERE h.desgId = :desgId"),
    @NamedQuery(name = "HrSlabMaster.findByCompCode", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.compCode = :compCode"),
    @NamedQuery(name = "HrSlabMaster.findByBaseComponent", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.baseComponent = :baseComponent"),
    @NamedQuery(name = "HrSlabMaster.findByDependComponent", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.dependComponent = :dependComponent"),
    @NamedQuery(name = "HrSlabMaster.findBySalarySlabId", query = "SELECT h FROM HrSlabMaster h WHERE h.salarySlabId = :salarySlabId"),
    @NamedQuery(name = "HrSlabMaster.findBySlabCriteria", query = "SELECT h FROM HrSlabMaster h WHERE h.slabCriteria = :slabCriteria"),
    @NamedQuery(name = "HrSlabMaster.findBySlabCriteriaAmt", query = "SELECT h FROM HrSlabMaster h WHERE h.slabCriteriaAmt = :slabCriteriaAmt"),
    @NamedQuery(name = "HrSlabMaster.findByMaxCriteriaAmt", query = "SELECT h FROM HrSlabMaster h WHERE h.maxCriteriaAmt = :maxCriteriaAmt"),
    @NamedQuery(name = "HrSlabMaster.findByPurposeCode", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.purposeCode = :purposeCode"),
    @NamedQuery(name = "HrSlabMaster.findByNature", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.nature = :nature"),
    @NamedQuery(name = "HrSlabMaster.findByRangeFrom", query = "SELECT h FROM HrSlabMaster h WHERE h.rangeFrom = :rangeFrom"),
    @NamedQuery(name = "HrSlabMaster.findByRangeTo", query = "SELECT h FROM HrSlabMaster h WHERE h.rangeTo = :rangeTo"),
    @NamedQuery(name = "HrSlabMaster.findByComponentOrder", query = "SELECT h FROM HrSlabMaster h WHERE h.componentOrder = :componentOrder"),
    @NamedQuery(name = "HrSlabMaster.findByAppFlg", query = "SELECT h FROM HrSlabMaster h WHERE h.appFlg = :appFlg"),
    @NamedQuery(name = "HrSlabMaster.findByAuthBy", query = "SELECT h FROM HrSlabMaster h WHERE h.authBy = :authBy"),
    @NamedQuery(name = "HrSlabMaster.findByDefaultComp", query = "SELECT h FROM HrSlabMaster h WHERE h.defaultComp = :defaultComp"),
    @NamedQuery(name = "HrSlabMaster.findByEnteredBy", query = "SELECT h FROM HrSlabMaster h WHERE h.enteredBy = :enteredBy"),
    @NamedQuery(name = "HrSlabMaster.findByModDate", query = "SELECT h FROM HrSlabMaster h WHERE h.modDate = :modDate"),
    @NamedQuery(name = "HrSlabMaster.findByStatFlag", query = "SELECT h FROM HrSlabMaster h WHERE h.statFlag = :statFlag"),
    @NamedQuery(name = "HrSlabMaster.findByStatUpFlag", query = "SELECT h FROM HrSlabMaster h WHERE h.statUpFlag = :statUpFlag"),
    @NamedQuery(name = "HrSlabMaster.findByVariousParameters", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.compCode = :compCode"
            + " and h.hrSlabMasterPK.purposeCode = :purposeCode and h.hrSlabMasterPK.nature = :nature  " //            + "h.alDesc = :alDesc and "
            //            + "h.appFlg = :appFlg and h.hrSlabMasterPK.rangeFrom <= :basicSalary and h.hrSlabMasterPK.rangeTo >= :basicSalary and h.modDate = (select max(p.modDate) from HrSlabMaster p "
            //            + "where p.modDate <=:dateFrom and p.hrSlabMasterPK.compCode = :compCode and p.hrSlabMasterPK.purposeCode = :purposeCode and p.hrSlabMasterPK.nature = :nature and "
            //            + "p.alDesc = :alDesc and p.appFlg = :appFlg)"
            ),
    @NamedQuery(name = "HrSlabMaster.findByVariousParametsrs1", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.compCode = :compCode "
            + "and h.hrSlabMasterPK.purposeCode = :purposeCode and h.hrSlabMasterPK.nature = :nature and( h.hrSlabMasterPK.baseComponent = :component or  h.hrSlabMasterPK.dependComponent = :component)"),
    @NamedQuery(name = "HrSlabMaster.findEntityByKey", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.compCode = :compCode "
            + "and h.hrSlabMasterPK.purposeCode = :purposeCode and h.hrSlabMasterPK.nature = :nature "),
    @NamedQuery(name = "HrSlabMaster.findEntityWithAppFlag", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.compCode = :compCode "
            + "and h.hrSlabMasterPK.purposeCode = :purposeCode and h.hrSlabMasterPK.nature = :nature and h.hrSlabMasterPK.slabCode = :slabCode"
            + " and h.appFlg = :appFlg"),
    @NamedQuery(name = "HrSlabMaster.findEntityWithoutAppFlag", query = "SELECT h FROM HrSlabMaster h WHERE h.hrSlabMasterPK.compCode = :compCode "
            + "and h.hrSlabMasterPK.purposeCode = :purposeCode and h.hrSlabMasterPK.nature = :nature and  h.hrSlabMasterPK.slabCode = :slabCode")
})
public class HrSlabMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrSlabMasterPK hrSlabMasterPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "DESG_ID")
    private String desgId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SALARY_SLAB_ID")
    private String salarySlabId;
    @Size(max = 255)
    @Column(name = "SLAB_CRITERIA")
    private String slabCriteria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SLAB_CRITERIA_AMT")
    private Float slabCriteriaAmt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_CRITERIA_AMT")
    private float maxCriteriaAmt;
   //aditya added
    @Column(name = "MIN_CRITERIA_AMT")
    private float minCriteriaAmt;
    @Column(name = "RANGE_FROM")
    private Float rangeFrom;
    @Column(name = "RANGE_TO")
    private Float rangeTo;
    @Column(name = "COMPONENT_ORDER")
    private Integer componentOrder;
    @Column(name = "APP_FLG")
    private Character appFlg;
    @Size(max = 255)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Column(name = "DEFAULT_COMP")
    private Integer defaultComp;
    @Size(max = 255)
    @Column(name = "ENTERED_BY")
    private String enteredBy;
    @Column(name = "MOD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
    @Size(max = 255)
    @Column(name = "STAT_FLAG")
    private String statFlag;
    @Size(max = 255)
    @Column(name = "STAT_UP_FLAG")
    private String statUpFlag;

    public HrSlabMaster() {
    }

    public HrSlabMaster(HrSlabMasterPK hrSlabMasterPK) {
        this.hrSlabMasterPK = hrSlabMasterPK;
    }

    public HrSlabMaster(HrSlabMasterPK hrSlabMasterPK, String desgId, String salarySlabId, float maxCriteriaAmt, float minCriteriaAmt) {
        this.hrSlabMasterPK = hrSlabMasterPK;
        this.desgId = desgId;
        this.salarySlabId = salarySlabId;
        this.maxCriteriaAmt = maxCriteriaAmt;
        this .minCriteriaAmt= minCriteriaAmt;
    }

    public HrSlabMaster(String slabCode, int compCode, String baseComponent, String dependComponent, String purposeCode, String nature) {
        this.hrSlabMasterPK = new HrSlabMasterPK(slabCode, compCode, baseComponent, dependComponent, purposeCode, nature);
    }

    public HrSlabMasterPK getHrSlabMasterPK() {
        return hrSlabMasterPK;
    }

    public void setHrSlabMasterPK(HrSlabMasterPK hrSlabMasterPK) {
        this.hrSlabMasterPK = hrSlabMasterPK;
    }

    public String getDesgId() {
        return desgId;
    }

    public void setDesgId(String desgId) {
        this.desgId = desgId;
    }

    public String getSalarySlabId() {
        return salarySlabId;
    }

    public void setSalarySlabId(String salarySlabId) {
        this.salarySlabId = salarySlabId;
    }

    public String getSlabCriteria() {
        return slabCriteria;
    }

    public void setSlabCriteria(String slabCriteria) {
        this.slabCriteria = slabCriteria;
    }

    public Float getSlabCriteriaAmt() {
        return slabCriteriaAmt;
    }

    public void setSlabCriteriaAmt(Float slabCriteriaAmt) {
        this.slabCriteriaAmt = slabCriteriaAmt;
    }

    public float getMaxCriteriaAmt() {
        return maxCriteriaAmt;
    }

    public void setMaxCriteriaAmt(float maxCriteriaAmt) {
        this.maxCriteriaAmt = maxCriteriaAmt;
    }

    public float getMinCriteriaAmt() {
        return minCriteriaAmt;
    }

    public void setMinCriteriaAmt(float minCriteriaAmt) {
        this.minCriteriaAmt = minCriteriaAmt;
    }

    
    
    public Float getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Float rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Float getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Float rangeTo) {
        this.rangeTo = rangeTo;
    }

    public Integer getComponentOrder() {
        return componentOrder;
    }

    public void setComponentOrder(Integer componentOrder) {
        this.componentOrder = componentOrder;
    }

    public Character getAppFlg() {
        return appFlg;
    }

    public void setAppFlg(Character appFlg) {
        this.appFlg = appFlg;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrSlabMasterPK != null ? hrSlabMasterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSlabMaster)) {
            return false;
        }
        HrSlabMaster other = (HrSlabMaster) object;
        if ((this.hrSlabMasterPK == null && other.hrSlabMasterPK != null) || (this.hrSlabMasterPK != null && !this.hrSlabMasterPK.equals(other.hrSlabMasterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrSlabMaster[hrSlabMasterPK=" + hrSlabMasterPK + "]";
    }
}
