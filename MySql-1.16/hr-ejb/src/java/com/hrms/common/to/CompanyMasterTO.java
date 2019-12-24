/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class CompanyMasterTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String companyName;
    private Integer companyCode;
    private String mailingName;
    private String address;
    private String state;
    private String city;
    private Integer pin;
    private int countryCode;
    private String parentCompany;
    private Integer parentCompCode;
    private String defaultCompany;
    private Integer defCompCode;
    private char activeFlag;
    private String incomeTaxNo;
    private String lstNo;
    private String cstno;
    private String istNo;
    private String serviceTax;
    private String companyReg;
    private String applyVat;
    private String vatTinNo;
    private String finYearFrom;
    private String booksBeginingFrom;
    private String baseCurrrency;
    private String baseCurrencyNotation;
    private int floatingPoints;
    private char authFlag;
    private Character authStatus;
    private String authBy;
    private Date tranTime;
    private Date effectDt;
    private String enteredBy;

    public char getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(char activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyVat() {
        return applyVat;
    }

    public void setApplyVat(String applyVat) {
        this.applyVat = applyVat;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public char getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(char authFlag) {
        this.authFlag = authFlag;
    }

    public Character getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Character authStatus) {
        this.authStatus = authStatus;
    }

    public String getBaseCurrencyNotation() {
        return baseCurrencyNotation;
    }

    public void setBaseCurrencyNotation(String baseCurrencyNotation) {
        this.baseCurrencyNotation = baseCurrencyNotation;
    }

    public String getBaseCurrrency() {
        return baseCurrrency;
    }

    public void setBaseCurrrency(String baseCurrrency) {
        this.baseCurrrency = baseCurrrency;
    }

    public String getBooksBeginingFrom() {
        return booksBeginingFrom;
    }

    public void setBooksBeginingFrom(String booksBeginingFrom) {
        this.booksBeginingFrom = booksBeginingFrom;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyReg() {
        return companyReg;
    }

    public void setCompanyReg(String companyReg) {
        this.companyReg = companyReg;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getCstno() {
        return cstno;
    }

    public void setCstno(String cstno) {
        this.cstno = cstno;
    }

    public Integer getDefCompCode() {
        return defCompCode;
    }

    public void setDefCompCode(Integer defCompCode) {
        this.defCompCode = defCompCode;
    }

    public String getDefaultCompany() {
        return defaultCompany;
    }

    public void setDefaultCompany(String defaultCompany) {
        this.defaultCompany = defaultCompany;
    }

    public Date getEffectDt() {
        return effectDt;
    }

    public void setEffectDt(Date effectDt) {
        this.effectDt = effectDt;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFinYearFrom() {
        return finYearFrom;
    }

    public void setFinYearFrom(String finYearFrom) {
        this.finYearFrom = finYearFrom;
    }

    public int getFloatingPoints() {
        return floatingPoints;
    }

    public void setFloatingPoints(int floatingPoints) {
        this.floatingPoints = floatingPoints;
    }

    public String getIncomeTaxNo() {
        return incomeTaxNo;
    }

    public void setIncomeTaxNo(String incomeTaxNo) {
        this.incomeTaxNo = incomeTaxNo;
    }

    public String getIstNo() {
        return istNo;
    }

    public void setIstNo(String istNo) {
        this.istNo = istNo;
    }

    public String getLstNo() {
        return lstNo;
    }

    public void setLstNo(String lstNo) {
        this.lstNo = lstNo;
    }

    public String getMailingName() {
        return mailingName;
    }

    public void setMailingName(String mailingName) {
        this.mailingName = mailingName;
    }

    public Integer getParentCompCode() {
        return parentCompCode;
    }

    public void setParentCompCode(Integer parentCompCode) {
        this.parentCompCode = parentCompCode;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public String getVatTinNo() {
        return vatTinNo;
    }

    public void setVatTinNo(String vatTinNo) {
        this.vatTinNo = vatTinNo;
    }
}
