/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class CurrencyExchangePojo {

    private String bnkName;
    private String bnkBranch;
    private String bnkIfsc;
    private String bnkExchangeAtBranch;
    private String denomination;
    private Integer noOfPieces;
    private BigDecimal totalValue;    
    private String nameOfTenderer;
    private String typeOfId;
    private String idNo;
    private Integer rs500;
    private Integer rs1000;
    private String enterBy;
    private String authBy;
    
    

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getBnkBranch() {
        return bnkBranch;
    }

    public void setBnkBranch(String bnkBranch) {
        this.bnkBranch = bnkBranch;
    }

    public String getBnkIfsc() {
        return bnkIfsc;
    }

    public void setBnkIfsc(String bnkIfsc) {
        this.bnkIfsc = bnkIfsc;
    }

    public String getBnkExchangeAtBranch() {
        return bnkExchangeAtBranch;
    }

    public void setBnkExchangeAtBranch(String bnkExchangeAtBranch) {
        this.bnkExchangeAtBranch = bnkExchangeAtBranch;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Integer getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
    }   

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public String getNameOfTenderer() {
        return nameOfTenderer;
    }

    public void setNameOfTenderer(String nameOfTenderer) {
        this.nameOfTenderer = nameOfTenderer;
    }

    public String getTypeOfId() {
        return typeOfId;
    }

    public void setTypeOfId(String typeOfId) {
        this.typeOfId = typeOfId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Integer getRs500() {
        return rs500;
    }

    public void setRs500(Integer rs500) {
        this.rs500 = rs500;
    }

    public Integer getRs1000() {
        return rs1000;
    }

    public void setRs1000(Integer rs1000) {
        this.rs1000 = rs1000;
    }
    
    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }    
}
