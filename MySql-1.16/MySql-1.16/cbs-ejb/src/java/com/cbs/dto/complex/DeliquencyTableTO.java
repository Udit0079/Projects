/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.complex;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class DeliquencyTableTO implements Serializable {

    private String schemeCode;
    private String deliqCycle;
    private String daysPastDue;
    private String delFlagDeliq;
    private String placeHolder;
    private String provisionPercent;
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

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String getProvisionPercent() {
        return provisionPercent;
    }

    public void setProvisionPercent(String provisionPercent) {
        this.provisionPercent = provisionPercent;
    }
}
