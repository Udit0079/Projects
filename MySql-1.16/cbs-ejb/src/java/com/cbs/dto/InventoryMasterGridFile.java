/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author ROHIT KRISHNA
 */
public class InventoryMasterGridFile {

    private String invtClass;
    private String invtClassDesc;
    private String invtType;
    private String acNature;
    private String enterBy;
    private String enterDate;

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getInvtClass() {
        return invtClass;
    }

    public void setInvtClass(String invtClass) {
        this.invtClass = invtClass;
    }

    public String getInvtClassDesc() {
        return invtClassDesc;
    }

    public void setInvtClassDesc(String invtClassDesc) {
        this.invtClassDesc = invtClassDesc;
    }

    public String getInvtType() {
        return invtType;
    }

    public void setInvtType(String invtType) {
        this.invtType = invtType;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }
}
