/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author ALOK YADAV
 */
public class AlmPojo {
    private Integer gCode;
    private Integer sgCode;
    private Integer ssgCode;
    private Integer sssgCode;
    private Integer ssssgCode;
    private String  description;
    private String  headType;
    private String  classType;
    private String  acNature;
    private String  acType;
    private String  codeFr;
    private String  codeTo;
    private String  headOfAcc;
    private String  formula;
    private String  countApplicable;
    private String  npaClassification;
    private Double  buk1,
            buk2,
            buk3,
            buk4,
            buk5,
            buk6,
            buk7,
            buk8,
            total;

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Double getBuk1() {
        return buk1;
    }

    public void setBuk1(Double buk1) {
        this.buk1 = buk1;
    }

    public Double getBuk2() {
        return buk2;
    }

    public void setBuk2(Double buk2) {
        this.buk2 = buk2;
    }

    public Double getBuk3() {
        return buk3;
    }

    public void setBuk3(Double buk3) {
        this.buk3 = buk3;
    }

    public Double getBuk4() {
        return buk4;
    }

    public void setBuk4(Double buk4) {
        this.buk4 = buk4;
    }

    public Double getBuk5() {
        return buk5;
    }

    public void setBuk5(Double buk5) {
        this.buk5 = buk5;
    }

    public Double getBuk6() {
        return buk6;
    }

    public void setBuk6(Double buk6) {
        this.buk6 = buk6;
    }

    public Double getBuk7() {
        return buk7;
    }

    public void setBuk7(Double buk7) {
        this.buk7 = buk7;
    }

    public Double getBuk8() {
        return buk8;
    }

    public void setBuk8(Double buk8) {
        this.buk8 = buk8;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCodeFr() {
        return codeFr;
    }

    public void setCodeFr(String codeFr) {
        this.codeFr = codeFr;
    }

    public String getCodeTo() {
        return codeTo;
    }

    public void setCodeTo(String codeTo) {
        this.codeTo = codeTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getgCode() {
        return gCode;
    }

    public void setgCode(Integer gCode) {
        this.gCode = gCode;
    }

    public String getHeadOfAcc() {
        return headOfAcc;
    }

    public void setHeadOfAcc(String headOfAcc) {
        this.headOfAcc = headOfAcc;
    }

    public String getHeadType() {
        return headType;
    }

    public void setHeadType(String headType) {
        this.headType = headType;
    }

    public Integer getSgCode() {
        return sgCode;
    }

    public void setSgCode(Integer sgCode) {
        this.sgCode = sgCode;
    }

    public Integer getSsgCode() {
        return ssgCode;
    }

    public void setSsgCode(Integer ssgCode) {
        this.ssgCode = ssgCode;
    }

    public Integer getSssgCode() {
        return sssgCode;
    }

    public void setSssgCode(Integer sssgCode) {
        this.sssgCode = sssgCode;
    }

    public Integer getSsssgCode() {
        return ssssgCode;
    }

    public void setSsssgCode(Integer ssssgCode) {
        this.ssssgCode = ssssgCode;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCountApplicable() {
        return countApplicable;
    }

    public void setCountApplicable(String countApplicable) {
        this.countApplicable = countApplicable;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(String npaClassification) {
        this.npaClassification = npaClassification;
    }   
}