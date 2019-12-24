/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class HrManpowerTO implements Serializable{
 private static final long serialVersionUID = 1L;

    protected HrManpowerPKTO hrManpowerPKTO;

    private Double minmExp;

    private Double prefExp;

    private Double requExp;

    private Double autoExp;

    private Integer posFill;

    private Integer posRequ;

    private Integer posSanc;

    private String skillCode;

    private int defaultComp;

    private String statFlag;

    private String statUpFlag;

    private Date modDate;

    private String authBy;

    private String enteredBy;


    private String jone;

    private String deptCode;

    private String qualcode1;

    private String qualCode2;

    private String qualCode3;

    private String specialCode;

    private String desgCode;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Double getAutoExp() {
        return autoExp;
    }

    public void setAutoExp(Double autoExp) {
        this.autoExp = autoExp;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrManpowerPKTO getHrManpowerPKTO() {
        return hrManpowerPKTO;
    }

    public void setHrManpowerPKTO(HrManpowerPKTO hrManpowerPKTO) {
        this.hrManpowerPKTO = hrManpowerPKTO;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getJone() {
        return jone;
    }

    public void setJone(String jone) {
        this.jone = jone;
    }

    public String getQualCode2() {
        return qualCode2;
    }

    public void setQualCode2(String qualCode2) {
        this.qualCode2 = qualCode2;
    }

    public String getQualCode3() {
        return qualCode3;
    }

    public void setQualCode3(String qualCode3) {
        this.qualCode3 = qualCode3;
    }

    public String getQualcode1() {
        return qualcode1;
    }

    public void setQualcode1(String qualcode1) {
        this.qualcode1 = qualcode1;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public Double getMinmExp() {
        return minmExp;
    }

    public void setMinmExp(Double minmExp) {
        this.minmExp = minmExp;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Integer getPosFill() {
        return posFill;
    }

    public void setPosFill(Integer posFill) {
        this.posFill = posFill;
    }

    public Integer getPosRequ() {
        return posRequ;
    }

    public void setPosRequ(Integer posRequ) {
        this.posRequ = posRequ;
    }

    public Integer getPosSanc() {
        return posSanc;
    }

    public void setPosSanc(Integer posSanc) {
        this.posSanc = posSanc;
    }

    public Double getPrefExp() {
        return prefExp;
    }

    public void setPrefExp(Double prefExp) {
        this.prefExp = prefExp;
    }

    public Double getRequExp() {
        return requExp;
    }

    public void setRequExp(Double requExp) {
        this.requExp = requExp;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
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


}
