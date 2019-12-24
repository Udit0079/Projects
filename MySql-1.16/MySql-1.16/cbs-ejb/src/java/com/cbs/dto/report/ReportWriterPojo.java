/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class ReportWriterPojo implements Serializable, Comparable<ReportWriterPojo> {

    private String custId;
    private String acNo;
    private String name;
    private String fatherName;
    private String crrAdd;
    private String occupation;
    private String acctype;
    private String phoneNo;
    private String operationMode;
    private String accopenDt;
    private String perAdd;
    private String jtName;
    private String dateofBirth;
    private Double openBal;
    private Double closeBal;
    private String gender;
    private String nomineName;
    private BigDecimal annualIncome;
    private String area;
    private BigDecimal averageBalance;
    private String riskCategorization;
    private Integer age;
    private double basic;
    private double total;
    private double da;
    private String aadharCard;
    private int i;
    private String panNo;
    private String actCategory;

    public String getActCategory() {
        return actCategory;
    }

    public void setActCategory(String actCategory) {
        this.actCategory = actCategory;
    }
    
    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    
    public String getNomineName() {
        return nomineName;
    }

    public void setNomineName(String nomineName) {
        this.nomineName = nomineName;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAccopenDt() {
        return accopenDt;
    }

    public void setAccopenDt(String accopenDt) {
        this.accopenDt = accopenDt;
    }

    public String getAcctype() {
        return acctype;
    }

    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public String getCrrAdd() {
        return crrAdd;
    }

    public void setCrrAdd(String crrAdd) {
        this.crrAdd = crrAdd;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Double getCloseBal() {
        return closeBal;
    }

    public void setCloseBal(Double closeBal) {
        this.closeBal = closeBal;
    }

    public Double getOpenBal() {
        return openBal;
    }

    public void setOpenBal(Double openBal) {
        this.openBal = openBal;
    }

    public String getPerAdd() {
        return perAdd;
    }

    public void setPerAdd(String perAdd) {
        this.perAdd = perAdd;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getAverageBalance() {
        return averageBalance;
    }

    public void setAverageBalance(BigDecimal averageBalance) {
        this.averageBalance = averageBalance;
    }

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

    public double getDa() {
        return da;
    }

    public void setDa(double da) {
        this.da = da;
    }
    

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }
    

    public int compareTo(ReportWriterPojo o) {
        switch (o.i) {
            case 2:
                return this.acNo.compareTo(o.getAcNo());
            case 1:
                return this.name.compareTo(o.getName());
            case 0:
                return Integer.valueOf(this.custId).compareTo(Integer.valueOf(o.getCustId()));
        }
        return 0;
    }
}
