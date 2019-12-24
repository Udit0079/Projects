/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.master;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class DepositFlow implements Serializable{

    private String tblFlowCode;
    
    private String tblFlowFreqMonths;
    
    private String tblFlowFreqDays;
    
    private String tblFlowPeriodBegin;
    
    private String tblFlowPeriodEnd;
    
    private String tblDelFlagFlow;
    
    private String counterSaveUpdateFlow;

    public String getCounterSaveUpdateFlow() {
        return counterSaveUpdateFlow;
    }

    public void setCounterSaveUpdateFlow(String counterSaveUpdateFlow) {
        this.counterSaveUpdateFlow = counterSaveUpdateFlow;
    }

    public String getTblDelFlagFlow() {
        return tblDelFlagFlow;
    }

    public void setTblDelFlagFlow(String tblDelFlagFlow) {
        this.tblDelFlagFlow = tblDelFlagFlow;
    }

    public String getTblFlowCode() {
        return tblFlowCode;
    }

    public void setTblFlowCode(String tblFlowCode) {
        this.tblFlowCode = tblFlowCode;
    }

    public String getTblFlowFreqDays() {
        return tblFlowFreqDays;
    }

    public void setTblFlowFreqDays(String tblFlowFreqDays) {
        this.tblFlowFreqDays = tblFlowFreqDays;
    }

    public String getTblFlowFreqMonths() {
        return tblFlowFreqMonths;
    }

    public void setTblFlowFreqMonths(String tblFlowFreqMonths) {
        this.tblFlowFreqMonths = tblFlowFreqMonths;
    }

    public String getTblFlowPeriodBegin() {
        return tblFlowPeriodBegin;
    }

    public void setTblFlowPeriodBegin(String tblFlowPeriodBegin) {
        this.tblFlowPeriodBegin = tblFlowPeriodBegin;
    }

    public String getTblFlowPeriodEnd() {
        return tblFlowPeriodEnd;
    }

    public void setTblFlowPeriodEnd(String tblFlowPeriodEnd) {
        this.tblFlowPeriodEnd = tblFlowPeriodEnd;
    }
}
