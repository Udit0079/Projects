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
public class AdminUserRoleTO implements Serializable {
     private static final long serialVersionUID = 1L;

    protected AdminUserRolePKTO adminUserRolePKTO;

    private Date tranTime;

    private Date effectDate;

    private String enteredBy;

    private Integer defaultComp;

    private String authBy;

    private String statFlag;

    private String statUpFlag;

    public AdminUserRolePKTO getAdminUserRolePKTO() {
        return adminUserRolePKTO;
    }

    public void setAdminUserRolePKTO(AdminUserRolePKTO adminUserRolePKTO) {
        this.adminUserRolePKTO = adminUserRolePKTO;
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
    
}
