/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class HrSalaryStructureTO implements Serializable {
 private static final long serialVersionUID = 1L;

    protected HrSalaryStructurePKTO hrSalaryStructurePKTO;

    private Character taxable;

    private String applicableDate;

    private Integer defaultComp;

    private char statFlag;

    private char statUpFlag;

    private Date modDate;

    private String authBy;

    private String enteredBy;

    private HrMstStructTO hrMstStructTO;

    private HrMstStructTO hrMstStruct1TO;
    
    private String glCode;
    
    private int descShCode;

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
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

    public HrMstStructTO getHrMstStruct1TO() {
        return hrMstStruct1TO;
    }

    public void setHrMstStruct1TO(HrMstStructTO hrMstStruct1TO) {
        this.hrMstStruct1TO = hrMstStruct1TO;
    }

    public HrMstStructTO getHrMstStructTO() {
        return hrMstStructTO;
    }

    public void setHrMstStructTO(HrMstStructTO hrMstStructTO) {
        this.hrMstStructTO = hrMstStructTO;
    }

    public HrSalaryStructurePKTO getHrSalaryStructurePKTO() {
        return hrSalaryStructurePKTO;
    }

    public void setHrSalaryStructurePKTO(HrSalaryStructurePKTO hrSalaryStructurePKTO) {
        this.hrSalaryStructurePKTO = hrSalaryStructurePKTO;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public char getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(char statFlag) {
        this.statFlag = statFlag;
    }

    public char getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(char statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public Character getTaxable() {
        return taxable;
    }

    public void setTaxable(Character taxable) {
        this.taxable = taxable;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public int getDescShCode() {
        return descShCode;
    }

    public void setDescShCode(int descShCode) {
        this.descShCode = descShCode;
    }


}
