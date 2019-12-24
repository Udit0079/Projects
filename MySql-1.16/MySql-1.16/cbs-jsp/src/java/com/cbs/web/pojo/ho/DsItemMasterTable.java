/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.ho;

/**
 *
 * @author Ankit Verma
 */
public class DsItemMasterTable {
    private long sno;
    private String groupCode;
    private String groupName;
    private long itemCode;
    private String itemName;
    private String wefPeriod;
    private float depPercent;
    private String depMethod;
    private String depRound;
    private String roundBase;
    private String roundRange;
    private String enterBy;
    private String tempWefPeriod;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTempWefPeriod() {
        return tempWefPeriod;
    }

    public void setTempWefPeriod(String tempWefPeriod) {
        this.tempWefPeriod = tempWefPeriod;
    }

    
    public String getRoundBase() {
        return roundBase;
    }

    public void setRoundBase(String roundBase) {
        this.roundBase = roundBase;
    }

    
    public String getDepMethod() {
        return depMethod;
    }

    public void setDepMethod(String depMethod) {
        this.depMethod = depMethod;
    }

    public float getDepPercent() {
        return depPercent;
    }

    public void setDepPercent(float depPercent) {
        this.depPercent = depPercent;
    }

    public String getDepRound() {
        return depRound;
    }

    public void setDepRound(String depRound) {
        this.depRound = depRound;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public long getItemCode() {
        return itemCode;
    }

    public void setItemCode(long itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRoundRange() {
        return roundRange;
    }

    public void setRoundRange(String roundRange) {
        this.roundRange = roundRange;
    }

    public long getSno() {
        return sno;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public String getWefPeriod() {
        return wefPeriod;
    }

    public void setWefPeriod(String wefPeriod) {
        this.wefPeriod = wefPeriod;
    }

    

}
