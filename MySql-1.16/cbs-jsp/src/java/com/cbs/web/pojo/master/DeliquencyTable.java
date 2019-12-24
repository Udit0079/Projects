/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.master;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class DeliquencyTable implements Serializable {

    private String schemeCode;
    private String deliqCycle;
    private String daysPastDue;
    private String placeInHolder;
    private String provisionInPercent;
    private String delFlagDeliq;
    private String counterSaveUpdate;

    public String getCounterSaveUpdate() {
        return counterSaveUpdate;
    }

    public void setCounterSaveUpdate(String counterSaveUpdate) {
        this.counterSaveUpdate = counterSaveUpdate;
    }

    public String getDaysPastDue() {
        return daysPastDue;
    }

    public void setDaysPastDue(String daysPastDue) {
        this.daysPastDue = daysPastDue;
    }

    public String getDelFlagDeliq() {
        return delFlagDeliq;
    }

    public void setDelFlagDeliq(String delFlagDeliq) {
        this.delFlagDeliq = delFlagDeliq;
    }

    public String getDeliqCycle() {
        return deliqCycle;
    }

    public void setDeliqCycle(String deliqCycle) {
        this.deliqCycle = deliqCycle;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getPlaceInHolder() {
        return placeInHolder;
    }

    public void setPlaceInHolder(String placeInHolder) {
        this.placeInHolder = placeInHolder;
    }

    public String getProvisionInPercent() {
        return provisionInPercent;
    }

    public void setProvisionInPercent(String provisionInPercent) {
        this.provisionInPercent = provisionInPercent;
    }
}
