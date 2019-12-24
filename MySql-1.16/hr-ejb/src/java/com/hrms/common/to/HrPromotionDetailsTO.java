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
public class HrPromotionDetailsTO implements Serializable {
 private static final long serialVersionUID = 1L;

    protected HrPromotionDetailsPKTO hrPromotionDetailsPKTO;

    private long empCode;

    private String ardate;

    private String deptFrom;

    private String deptTo;

    private String blockFrom;

    private String blockTo;

    private String zoneFrom;

    private String zoneTo;

    private String presLocat;

    private String proposLocat;

    private Double salaryPres;

    private Double salaryPropos;

    private String presDesig;

    private String proposDesig;

    private String presRepId;

    private String proposRepId;

    private Character budgtStatus;

    private String overRating;

    private String remarks1;

    private String promWef;

    private Character headApprov;

    private Character hrdApprov;

    private Character mdApprov;

    private Character status;

    private String empIdOld;

    private Integer defaultComp;

    private String statFlag;

    private String statUpFlag;

    private Date modDate;

    private String authBy;

    private String enteredBy;

    public String getArdate() {
        return ardate;
    }

    public void setArdate(String ardate) {
        this.ardate = ardate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
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

    public Character getBudgtStatus() {
        return budgtStatus;
    }

    public void setBudgtStatus(Character budgtStatus) {
        this.budgtStatus = budgtStatus;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
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

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getEmpIdOld() {
        return empIdOld;
    }

    public void setEmpIdOld(String empIdOld) {
        this.empIdOld = empIdOld;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Character getHeadApprov() {
        return headApprov;
    }

    public void setHeadApprov(Character headApprov) {
        this.headApprov = headApprov;
    }

    public HrPromotionDetailsPKTO getHrPromotionDetailsPKTO() {
        return hrPromotionDetailsPKTO;
    }

    public void setHrPromotionDetailsPKTO(HrPromotionDetailsPKTO hrPromotionDetailsPKTO) {
        this.hrPromotionDetailsPKTO = hrPromotionDetailsPKTO;
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

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getOverRating() {
        return overRating;
    }

    public void setOverRating(String overRating) {
        this.overRating = overRating;
    }

    public String getPresDesig() {
        return presDesig;
    }

    public void setPresDesig(String presDesig) {
        this.presDesig = presDesig;
    }

    public String getPresLocat() {
        return presLocat;
    }

    public void setPresLocat(String presLocat) {
        this.presLocat = presLocat;
    }

    public String getPresRepId() {
        return presRepId;
    }

    public void setPresRepId(String presRepId) {
        this.presRepId = presRepId;
    }

    public String getPromWef() {
        return promWef;
    }

    public void setPromWef(String promWef) {
        this.promWef = promWef;
    }

    public String getProposDesig() {
        return proposDesig;
    }

    public void setProposDesig(String proposDesig) {
        this.proposDesig = proposDesig;
    }

    public String getProposLocat() {
        return proposLocat;
    }

    public void setProposLocat(String proposLocat) {
        this.proposLocat = proposLocat;
    }

    public String getProposRepId() {
        return proposRepId;
    }

    public void setProposRepId(String proposRepId) {
        this.proposRepId = proposRepId;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
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

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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
    

}
