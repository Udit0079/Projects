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
public class HrApprisalDetailsTO implements Serializable{
 private static final long serialVersionUID = 1L;

    protected HrApprisalDetailsPKTO hrApprisalDetailsPKTO;
   
    private String ratingCode;
  
    private Character apprResult;
    
    private String trngCode;
    
    private String recHead;
    
    private String recHod;
    
    private String recHrd;
    
    private String recPersonnel;
    
    private Double incrAmt;
  
    private String promWef;
   
    private Integer defaultComp;
   
    private String statFlag;
    
    private String statUpFlag;

    private Date modDate;
   
    private String authBy;
   
    private String enteredBy;
   
    private HrPersonnelDetailsTO hrPersonnelDetailsTO;

    public Character getApprResult() {
        return apprResult;
    }

    public void setApprResult(Character apprResult) {
        this.apprResult = apprResult;
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

    public HrApprisalDetailsPKTO getHrApprisalDetailsPKTO() {
        return hrApprisalDetailsPKTO;
    }

    public void setHrApprisalDetailsPKTO(HrApprisalDetailsPKTO hrApprisalDetailsPKTO) {
        this.hrApprisalDetailsPKTO = hrApprisalDetailsPKTO;
    }

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO() {
        return hrPersonnelDetailsTO;
    }

    public void setHrPersonnelDetailsTO(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        this.hrPersonnelDetailsTO = hrPersonnelDetailsTO;
    }

    public Double getIncrAmt() {
        return incrAmt;
    }

    public void setIncrAmt(Double incrAmt) {
        this.incrAmt = incrAmt;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getPromWef() {
        return promWef;
    }

    public void setPromWef(String promWef) {
        this.promWef = promWef;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public String getRecHead() {
        return recHead;
    }

    public void setRecHead(String recHead) {
        this.recHead = recHead;
    }

    public String getRecHod() {
        return recHod;
    }

    public void setRecHod(String recHod) {
        this.recHod = recHod;
    }

    public String getRecHrd() {
        return recHrd;
    }

    public void setRecHrd(String recHrd) {
        this.recHrd = recHrd;
    }

    public String getRecPersonnel() {
        return recPersonnel;
    }

    public void setRecPersonnel(String recPersonnel) {
        this.recPersonnel = recPersonnel;
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

    public String getTrngCode() {
        return trngCode;
    }

    public void setTrngCode(String trngCode) {
        this.trngCode = trngCode;
    }

    

}
