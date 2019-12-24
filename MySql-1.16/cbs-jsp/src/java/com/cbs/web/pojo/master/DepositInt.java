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
public class DepositInt implements Serializable {

    private String tblInterestMethod;
    private String tblMaxDepositPeriodMonths;
    private String tblMaxDepositPeriodDays;
    private String tblBaseAmtInd;
    private String tblCompoundingPeriod;
    private String tblCompoundingBase;
    private String tblMinCompoundingPeriod;
    private String tblMinCompoundingBase;
    private String tblBrokenPeriodMethod;
    private String tblBrokenPeriodBase;
    private String tblDeleteFlag;
    private String counterSaveUpdateDeposit;

    public String getCounterSaveUpdateDeposit() {
        return counterSaveUpdateDeposit;
    }

    public void setCounterSaveUpdateDeposit(String counterSaveUpdateDeposit) {
        this.counterSaveUpdateDeposit = counterSaveUpdateDeposit;
    }

    public String getTblBaseAmtInd() {
        return tblBaseAmtInd;
    }

    public void setTblBaseAmtInd(String tblBaseAmtInd) {
        this.tblBaseAmtInd = tblBaseAmtInd;
    }

    public String getTblBrokenPeriodBase() {
        return tblBrokenPeriodBase;
    }

    public void setTblBrokenPeriodBase(String tblBrokenPeriodBase) {
        this.tblBrokenPeriodBase = tblBrokenPeriodBase;
    }

    public String getTblBrokenPeriodMethod() {
        return tblBrokenPeriodMethod;
    }

    public void setTblBrokenPeriodMethod(String tblBrokenPeriodMethod) {
        this.tblBrokenPeriodMethod = tblBrokenPeriodMethod;
    }

    public String getTblCompoundingBase() {
        return tblCompoundingBase;
    }

    public void setTblCompoundingBase(String tblCompoundingBase) {
        this.tblCompoundingBase = tblCompoundingBase;
    }

    public String getTblCompoundingPeriod() {
        return tblCompoundingPeriod;
    }

    public void setTblCompoundingPeriod(String tblCompoundingPeriod) {
        this.tblCompoundingPeriod = tblCompoundingPeriod;
    }

    public String getTblDeleteFlag() {
        return tblDeleteFlag;
    }

    public void setTblDeleteFlag(String tblDeleteFlag) {
        this.tblDeleteFlag = tblDeleteFlag;
    }

    public String getTblInterestMethod() {
        return tblInterestMethod;
    }

    public void setTblInterestMethod(String tblInterestMethod) {
        this.tblInterestMethod = tblInterestMethod;
    }

    public String getTblMaxDepositPeriodDays() {
        return tblMaxDepositPeriodDays;
    }

    public void setTblMaxDepositPeriodDays(String tblMaxDepositPeriodDays) {
        this.tblMaxDepositPeriodDays = tblMaxDepositPeriodDays;
    }

    public String getTblMaxDepositPeriodMonths() {
        return tblMaxDepositPeriodMonths;
    }

    public void setTblMaxDepositPeriodMonths(String tblMaxDepositPeriodMonths) {
        this.tblMaxDepositPeriodMonths = tblMaxDepositPeriodMonths;
    }

    public String getTblMinCompoundingBase() {
        return tblMinCompoundingBase;
    }

    public void setTblMinCompoundingBase(String tblMinCompoundingBase) {
        this.tblMinCompoundingBase = tblMinCompoundingBase;
    }

    public String getTblMinCompoundingPeriod() {
        return tblMinCompoundingPeriod;
    }

    public void setTblMinCompoundingPeriod(String tblMinCompoundingPeriod) {
        this.tblMinCompoundingPeriod = tblMinCompoundingPeriod;
    }
}
