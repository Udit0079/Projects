/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class CBSTradeFinanceInformationTO implements Serializable {
     private static final long serialVersionUID = 1L;
    
    private String customerId;
    
    private String name;
    
    private String addressLine1;
    
    private String addressLine2;
   
    private String cityCode;
   
    private String postalCode;
    
    private String countryCode;
    
    private String phoneNumber;
    
    private String faxNumber;
   
    private String telexNumber;
   
    private String native1;
   
    private String inlandTradeAllowed;
   
    private Date reviewDate;
    
    private String industryType;
    
    private String exportUnitFlag;
  
    private String status;
    
    private String tradeExpCode;
    
    private String partyConstitution;
   
    private String specialParty;
    
    private String partyType;
    
    private String productionCycle;
   
    private String codeGivenByCentralBANK;
   
    private String codeGivenByTradeAuthority;
   
    private String type;
    
    private Double forwardContractLimit;
    
    private Double fCMargin;
   
    private String fCSanctioningAuthority;
   
    private Date fCSanctionExpiryDate;
   
    private String fCNextNumberCode;
    
    private Double documentCreditLimit;
   
    private Double dCMargin;
    
    private String dCSanctioningAuthority;
   
    private Date dCSanctionExpiryDate;
   
    private String dCNextNumberCode;
    
    private Double packingContractLimit;
   
    private Double pCMargin;
   
    private String pCSanctioningAuthority;
    
    private Date pCSanctionExpiryDate;
  
    private String pCNextNumberCode;
    
    private Double bankGuaranteeLimit;
   
    private Double bGMargin;
    
    private String bGSanctioningAuthority;
    
    private Date bGSanctionExpiryDate;
    
    private String bGNextNumberCode;
    
    private Double presentOutstandingLiability;
  
    private String lastChangeUserID;
    
    private Date lastChangeTime;
   
    private String recordCreaterID;
    
    private Date creationTime;
  
    private String tsCnt;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Double getbGMargin() {
        return bGMargin;
    }

    public void setbGMargin(Double bGMargin) {
        this.bGMargin = bGMargin;
    }

    public String getbGNextNumberCode() {
        return bGNextNumberCode;
    }

    public void setbGNextNumberCode(String bGNextNumberCode) {
        this.bGNextNumberCode = bGNextNumberCode;
    }

    public Date getbGSanctionExpiryDate() {
        return bGSanctionExpiryDate;
    }

    public void setbGSanctionExpiryDate(Date bGSanctionExpiryDate) {
        this.bGSanctionExpiryDate = bGSanctionExpiryDate;
    }

    public String getbGSanctioningAuthority() {
        return bGSanctioningAuthority;
    }

    public void setbGSanctioningAuthority(String bGSanctioningAuthority) {
        this.bGSanctioningAuthority = bGSanctioningAuthority;
    }

    public Double getBankGuaranteeLimit() {
        return bankGuaranteeLimit;
    }

    public void setBankGuaranteeLimit(Double bankGuaranteeLimit) {
        this.bankGuaranteeLimit = bankGuaranteeLimit;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCodeGivenByCentralBANK() {
        return codeGivenByCentralBANK;
    }

    public void setCodeGivenByCentralBANK(String codeGivenByCentralBANK) {
        this.codeGivenByCentralBANK = codeGivenByCentralBANK;
    }

    public String getCodeGivenByTradeAuthority() {
        return codeGivenByTradeAuthority;
    }

    public void setCodeGivenByTradeAuthority(String codeGivenByTradeAuthority) {
        this.codeGivenByTradeAuthority = codeGivenByTradeAuthority;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getdCMargin() {
        return dCMargin;
    }

    public void setdCMargin(Double dCMargin) {
        this.dCMargin = dCMargin;
    }

    public String getdCNextNumberCode() {
        return dCNextNumberCode;
    }

    public void setdCNextNumberCode(String dCNextNumberCode) {
        this.dCNextNumberCode = dCNextNumberCode;
    }

    public Date getdCSanctionExpiryDate() {
        return dCSanctionExpiryDate;
    }

    public void setdCSanctionExpiryDate(Date dCSanctionExpiryDate) {
        this.dCSanctionExpiryDate = dCSanctionExpiryDate;
    }

    public String getdCSanctioningAuthority() {
        return dCSanctioningAuthority;
    }

    public void setdCSanctioningAuthority(String dCSanctioningAuthority) {
        this.dCSanctioningAuthority = dCSanctioningAuthority;
    }

    public Double getDocumentCreditLimit() {
        return documentCreditLimit;
    }

    public void setDocumentCreditLimit(Double documentCreditLimit) {
        this.documentCreditLimit = documentCreditLimit;
    }

    public String getExportUnitFlag() {
        return exportUnitFlag;
    }

    public void setExportUnitFlag(String exportUnitFlag) {
        this.exportUnitFlag = exportUnitFlag;
    }

    public Double getfCMargin() {
        return fCMargin;
    }

    public void setfCMargin(Double fCMargin) {
        this.fCMargin = fCMargin;
    }

    public String getfCNextNumberCode() {
        return fCNextNumberCode;
    }

    public void setfCNextNumberCode(String fCNextNumberCode) {
        this.fCNextNumberCode = fCNextNumberCode;
    }

    public Date getfCSanctionExpiryDate() {
        return fCSanctionExpiryDate;
    }

    public void setfCSanctionExpiryDate(Date fCSanctionExpiryDate) {
        this.fCSanctionExpiryDate = fCSanctionExpiryDate;
    }

    public String getfCSanctioningAuthority() {
        return fCSanctioningAuthority;
    }

    public void setfCSanctioningAuthority(String fCSanctioningAuthority) {
        this.fCSanctioningAuthority = fCSanctioningAuthority;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Double getForwardContractLimit() {
        return forwardContractLimit;
    }

    public void setForwardContractLimit(Double forwardContractLimit) {
        this.forwardContractLimit = forwardContractLimit;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getInlandTradeAllowed() {
        return inlandTradeAllowed;
    }

    public void setInlandTradeAllowed(String inlandTradeAllowed) {
        this.inlandTradeAllowed = inlandTradeAllowed;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNative1() {
        return native1;
    }

    public void setNative1(String native1) {
        this.native1 = native1;
    }

    public Double getpCMargin() {
        return pCMargin;
    }

    public void setpCMargin(Double pCMargin) {
        this.pCMargin = pCMargin;
    }

    public String getpCNextNumberCode() {
        return pCNextNumberCode;
    }

    public void setpCNextNumberCode(String pCNextNumberCode) {
        this.pCNextNumberCode = pCNextNumberCode;
    }

    public Date getpCSanctionExpiryDate() {
        return pCSanctionExpiryDate;
    }

    public void setpCSanctionExpiryDate(Date pCSanctionExpiryDate) {
        this.pCSanctionExpiryDate = pCSanctionExpiryDate;
    }

    public String getpCSanctioningAuthority() {
        return pCSanctioningAuthority;
    }

    public void setpCSanctioningAuthority(String pCSanctioningAuthority) {
        this.pCSanctioningAuthority = pCSanctioningAuthority;
    }

    public Double getPackingContractLimit() {
        return packingContractLimit;
    }

    public void setPackingContractLimit(Double packingContractLimit) {
        this.packingContractLimit = packingContractLimit;
    }

    public String getPartyConstitution() {
        return partyConstitution;
    }

    public void setPartyConstitution(String partyConstitution) {
        this.partyConstitution = partyConstitution;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getPresentOutstandingLiability() {
        return presentOutstandingLiability;
    }

    public void setPresentOutstandingLiability(Double presentOutstandingLiability) {
        this.presentOutstandingLiability = presentOutstandingLiability;
    }

    public String getProductionCycle() {
        return productionCycle;
    }

    public void setProductionCycle(String productionCycle) {
        this.productionCycle = productionCycle;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getSpecialParty() {
        return specialParty;
    }

    public void setSpecialParty(String specialParty) {
        this.specialParty = specialParty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelexNumber() {
        return telexNumber;
    }

    public void setTelexNumber(String telexNumber) {
        this.telexNumber = telexNumber;
    }

    public String getTradeExpCode() {
        return tradeExpCode;
    }

    public void setTradeExpCode(String tradeExpCode) {
        this.tradeExpCode = tradeExpCode;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
