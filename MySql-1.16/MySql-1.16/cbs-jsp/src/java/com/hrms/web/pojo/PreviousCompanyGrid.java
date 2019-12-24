/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.pojo;

/**
 *
 * @author Administrator
 */
public class PreviousCompanyGrid {

    private long empCode, prevCompCode;
    private int sno;
    private String annualTurnOver,
            prevCompAddress,
            reasonOfLeaving,
            durationFrom,
            durationTo,
            designationOnJoining,
            designationOnLeaving,
            strengthUnder,
            strengthTotal,
            preCompName;
    private double salaryOnJoining,
            salaryOnLeaving;

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public long getPrevCompCode() {
        return prevCompCode;
    }

    public void setPrevCompCode(long prevCompCode) {
        this.prevCompCode = prevCompCode;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public double getSalaryOnJoining() {
        return salaryOnJoining;
    }

    public void setSalaryOnJoining(double salaryOnJoining) {
        this.salaryOnJoining = salaryOnJoining;
    }

    public double getSalaryOnLeaving() {
        return salaryOnLeaving;
    }

    public void setSalaryOnLeaving(double salaryOnLeaving) {
        this.salaryOnLeaving = salaryOnLeaving;
    }

    public String getAnnualTurnOver() {
        return annualTurnOver;
    }

    public void setAnnualTurnOver(String annualTurnOver) {
        this.annualTurnOver = annualTurnOver;
    }

    public String getDesignationOnJoining() {
        return designationOnJoining;
    }

    public void setDesignationOnJoining(String designationOnJoining) {
        this.designationOnJoining = designationOnJoining;
    }

    public String getDesignationOnLeaving() {
        return designationOnLeaving;
    }

    public void setDesignationOnLeaving(String designationOnLeaving) {
        this.designationOnLeaving = designationOnLeaving;
    }

    public String getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(String durationFrom) {
        this.durationFrom = durationFrom;
    }

    public String getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(String durationTo) {
        this.durationTo = durationTo;
    }

    public String getPreCompName() {
        return preCompName;
    }

    public void setPreCompName(String preCompName) {
        this.preCompName = preCompName;
    }

    public String getPrevCompAddress() {
        return prevCompAddress;
    }

    public void setPrevCompAddress(String prevCompAddress) {
        this.prevCompAddress = prevCompAddress;
    }

    public String getReasonOfLeaving() {
        return reasonOfLeaving;
    }

    public void setReasonOfLeaving(String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
    }

    public String getStrengthTotal() {
        return strengthTotal;
    }

    public void setStrengthTotal(String strengthTotal) {
        this.strengthTotal = strengthTotal;
    }

    public String getStrengthUnder() {
        return strengthUnder;
    }

    public void setStrengthUnder(String strengthUnder) {
        this.strengthUnder = strengthUnder;
    }
}
