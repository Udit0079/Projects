/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.intcal;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TdInterestCalGrid {

    Integer sno;
    String custId;
    String acno;
    String custName;
    String rtNo;
    String intToAcno;
    String intToAcnoCustname;
    double prinAmt;
    double roi;
    double interest;
    double tds;
    double totalInterest;
    double TotalTds;
    double intToPosted;
    String controlNo;
    String tdsFlag;
    private String minorFlag; 
    private String majorCustId;    
    private String minorCustId;    
    private String panExist;    
    private double unRecoverTds;
    private double minorInterest;    
    private double majorInterest;
    private double interestWithMinMaj;
    private double totalIntPaidVouchWise;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }   

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIntToAcno() {
        return intToAcno;
    }

    public void setIntToAcno(String intToAcno) {
        this.intToAcno = intToAcno;
    }

    public String getIntToAcnoCustname() {
        return intToAcnoCustname;
    }

    public void setIntToAcnoCustname(String intToAcnoCustname) {
        this.intToAcnoCustname = intToAcnoCustname;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getTds() {
        return tds;
    }

    public void setTds(double tds) {
        this.tds = tds;
    }

    public double getIntToPosted() {
        return intToPosted;
    }

    public void setIntToPosted(double intToPosted) {
        this.intToPosted = intToPosted;
    }

    public String getTdsFlag() {
        return tdsFlag;
    }

    public void setTdsFlag(String tdsFlag) {
        this.tdsFlag = tdsFlag;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getTotalTds() {
        return TotalTds;
    }

    public void setTotalTds(double TotalTds) {
        this.TotalTds = TotalTds;
    }

    public String getMajorCustId() {
        return majorCustId;
    }

    public void setMajorCustId(String majorCustId) {
        this.majorCustId = majorCustId;
    }

    public String getMinorCustId() {
        return minorCustId;
    }

    public void setMinorCustId(String minorCustId) {
        this.minorCustId = minorCustId;
    }

    public String getPanExist() {
        return panExist;
    }

    public void setPanExist(String panExist) {
        this.panExist = panExist;
    }

    public double getUnRecoverTds() {
        return unRecoverTds;
    }

    public void setUnRecoverTds(double unRecoverTds) {
        this.unRecoverTds = unRecoverTds;
    }

    public String getMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(String minorFlag) {
        this.minorFlag = minorFlag;
    }

    public double getMinorInterest() {
        return minorInterest;
    }

    public void setMinorInterest(double minorInterest) {
        this.minorInterest = minorInterest;
    }

    public double getMajorInterest() {
        return majorInterest;
    }

    public void setMajorInterest(double majorInterest) {
        this.majorInterest = majorInterest;
    }

    public double getInterestWithMinMaj() {
        return interestWithMinMaj;
    }

    public void setInterestWithMinMaj(double interestWithMinMaj) {
        this.interestWithMinMaj = interestWithMinMaj;
    }

    public double getTotalIntPaidVouchWise() {
        return totalIntPaidVouchWise;
    }

    public void setTotalIntPaidVouchWise(double totalIntPaidVouchWise) {
        this.totalIntPaidVouchWise = totalIntPaidVouchWise;
    }

}
