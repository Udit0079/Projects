/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.npci.cts;

import java.io.Serializable;
import java.util.List;

public class ItemDTO implements Serializable {

    private String presentingBankRoutNo;
    private String payorBankRoutNo;
    private Double amount;
    private String serialNo;
    private String itemSeqNo;
    private String transCode;
    private String accountNo;
    private String userField;
    private String presentmentDate;
    private String clearingType;
    private String cycleNo;
    private String docType;
    private List<ItemImageDTO> itemImages;
    private String addendABofdRoutNo;
    private String addendABofdBusDate;
    private String addendADepositorAcct;
    private String addendAIfsc;

    public String getPresentingBankRoutNo() {
        return presentingBankRoutNo;
    }

    public void setPresentingBankRoutNo(String presentingBankRoutNo) {
        this.presentingBankRoutNo = presentingBankRoutNo;
    }

    public String getPayorBankRoutNo() {
        return payorBankRoutNo;
    }

    public void setPayorBankRoutNo(String payorBankRoutNo) {
        this.payorBankRoutNo = payorBankRoutNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getItemSeqNo() {
        return itemSeqNo;
    }

    public void setItemSeqNo(String itemSeqNo) {
        this.itemSeqNo = itemSeqNo;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public List<ItemImageDTO> getItemImages() {
        return itemImages;
    }

    public void setItemImages(List<ItemImageDTO> itemImages) {
        this.itemImages = itemImages;
    }

    public String getPresentmentDate() {
        return presentmentDate;
    }

    public void setPresentmentDate(String presentmentDate) {
        this.presentmentDate = presentmentDate;
    }

    public String getClearingType() {
        return clearingType;
    }

    public void setClearingType(String clearingType) {
        this.clearingType = clearingType;
    }

    public String getCycleNo() {
        return cycleNo;
    }

    public void setCycleNo(String cycleNo) {
        this.cycleNo = cycleNo;
    }

    public String getAddendABofdRoutNo() {
        return addendABofdRoutNo;
    }

    public void setAddendABofdRoutNo(String addendABofdRoutNo) {
        this.addendABofdRoutNo = addendABofdRoutNo;
    }

    public String getAddendABofdBusDate() {
        return addendABofdBusDate;
    }

    public void setAddendABofdBusDate(String addendABofdBusDate) {
        this.addendABofdBusDate = addendABofdBusDate;
    }

    public String getAddendADepositorAcct() {
        return addendADepositorAcct;
    }

    public void setAddendADepositorAcct(String addendADepositorAcct) {
        this.addendADepositorAcct = addendADepositorAcct;
    }

    public String getAddendAIfsc() {
        return addendAIfsc;
    }

    public void setAddendAIfsc(String addendAIfsc) {
        this.addendAIfsc = addendAIfsc;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
