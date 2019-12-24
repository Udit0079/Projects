/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author stellar
 */
public class HrSlabMasterPKTO implements Serializable {

    private int compCode;
    private String purposeCode;
    private String nature;
    private String slabCode;
    private Date dateFrom;
    private Date dateTo;
    private String salarySlabId;

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getSlabCode() {
        return slabCode;
    }

    public void setSlabCode(String slabCode) {
        this.slabCode = slabCode;
    }

    public String getSalarySlabId() {
        return salarySlabId;
    }

    public void setSalarySlabId(String salarySlabId) {
        this.salarySlabId = salarySlabId;
    }
}
