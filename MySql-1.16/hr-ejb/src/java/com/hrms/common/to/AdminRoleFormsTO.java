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
public class AdminRoleFormsTO implements Serializable {
   private static final long serialVersionUID = 1L;
   protected AdminRoleFormsPKTO adminRoleFormsPKTO;
    private Character addOp;
   
    private Character editOp;
   
    private Character viewOp;
    
    private Character deleteOp;
    
    private Character authorizeOp;
  
    private Date tranTime;
    
    private Date effectDate;
   
    private String enteredBy;
    
    private Integer defaultComp;
   
    private String authBy;
  
    private String statFlag;
   
    private String statUpFlag;

    public Character getAddOp() {
        return addOp;
    }

    public void setAddOp(Character addOp) {
        this.addOp = addOp;
    }

    public AdminRoleFormsPKTO getAdminRoleFormsPKTO() {
        return adminRoleFormsPKTO;
    }

    public void setAdminRoleFormsPKTO(AdminRoleFormsPKTO adminRoleFormsPKTO) {
        this.adminRoleFormsPKTO = adminRoleFormsPKTO;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Character getAuthorizeOp() {
        return authorizeOp;
    }

    public void setAuthorizeOp(Character authorizeOp) {
        this.authorizeOp = authorizeOp;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public Character getDeleteOp() {
        return deleteOp;
    }

    public void setDeleteOp(Character deleteOp) {
        this.deleteOp = deleteOp;
    }

    public Character getEditOp() {
        return editOp;
    }

    public void setEditOp(Character editOp) {
        this.editOp = editOp;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
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

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Character getViewOp() {
        return viewOp;
    }

    public void setViewOp(Character viewOp) {
        this.viewOp = viewOp;
    }
    
}
