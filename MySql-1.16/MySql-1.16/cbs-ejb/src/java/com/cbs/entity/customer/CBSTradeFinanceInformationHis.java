/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.customer;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ROHIT KRISHNA
 */
@Entity
@Table(name = "cbs_trade_finance_information_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findAll", query = "SELECT c FROM CBSTradeFinanceInformationHis c"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByCustomerId", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByName", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.name = :name"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByAddressLine1", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.addressLine1 = :addressLine1"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByAddressLine2", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.addressLine2 = :addressLine2"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByCityCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.cityCode = :cityCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPostalCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.postalCode = :postalCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByCountryCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.countryCode = :countryCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPhoneNumber", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByFaxNumber", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.faxNumber = :faxNumber"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByTelexNumber", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.telexNumber = :telexNumber"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByNative1", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.native1 = :native1"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByInlandTradeAllowed", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.inlandTradeAllowed = :inlandTradeAllowed"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByReviewDate", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.reviewDate = :reviewDate"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByIndustryType", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.industryType = :industryType"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByExportUnitFlag", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.exportUnitFlag = :exportUnitFlag"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByStatus", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.status = :status"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByTradeExpCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.tradeExpCode = :tradeExpCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPartyConstitution", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.partyConstitution = :partyConstitution"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findBySpecialParty", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.specialParty = :specialParty"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPartyType", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.partyType = :partyType"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByProductionCycle", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.productionCycle = :productionCycle"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByCodeGivenByCentralBANK", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.codeGivenByCentralBANK = :codeGivenByCentralBANK"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByCodeGivenByTradeAuthority", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.codeGivenByTradeAuthority = :codeGivenByTradeAuthority"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByType", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.type = :type"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByForwardContractLimit", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.forwardContractLimit = :forwardContractLimit"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByFCMargin", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.fCMargin = :fCMargin"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByFCSanctioningAuthority", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.fCSanctioningAuthority = :fCSanctioningAuthority"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByFCSanctionExpiryDate", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.fCSanctionExpiryDate = :fCSanctionExpiryDate"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByFCNextNumberCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.fCNextNumberCode = :fCNextNumberCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByDocumentCreditLimit", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.documentCreditLimit = :documentCreditLimit"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByDCMargin", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.dCMargin = :dCMargin"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByDCSanctioningAuthority", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.dCSanctioningAuthority = :dCSanctioningAuthority"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByDCSanctionExpiryDate", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.dCSanctionExpiryDate = :dCSanctionExpiryDate"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByDCNextNumberCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.dCNextNumberCode = :dCNextNumberCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPackingContractLimit", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.packingContractLimit = :packingContractLimit"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPCMargin", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.pCMargin = :pCMargin"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPCSanctioningAuthority", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.pCSanctioningAuthority = :pCSanctioningAuthority"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPCSanctionExpiryDate", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.pCSanctionExpiryDate = :pCSanctionExpiryDate"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPCNextNumberCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.pCNextNumberCode = :pCNextNumberCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByBankGuaranteeLimit", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.bankGuaranteeLimit = :bankGuaranteeLimit"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByBGMargin", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.bGMargin = :bGMargin"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByBGSanctioningAuthority", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.bGSanctioningAuthority = :bGSanctioningAuthority"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByBGSanctionExpiryDate", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.bGSanctionExpiryDate = :bGSanctionExpiryDate"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByBGNextNumberCode", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.bGNextNumberCode = :bGNextNumberCode"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByPresentOutstandingLiability", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.presentOutstandingLiability = :presentOutstandingLiability"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByLastChangeUserID", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.lastChangeUserID = :lastChangeUserID"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByLastChangeTime", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.lastChangeTime = :lastChangeTime"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByRecordCreaterID", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.recordCreaterID = :recordCreaterID"),
    @NamedQuery(name = "CBSTradeFinanceInformationHis.findByTxnid", query = "SELECT c FROM CBSTradeFinanceInformationHis c WHERE c.txnid = :txnid")})
public class CBSTradeFinanceInformationHis extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CustomerId")
    private String customerId;
    @Size(max = 40)
    @Column(name = "Name")
    private String name;
    @Size(max = 50)
    @Column(name = "AddressLine1")
    private String addressLine1;
    @Size(max = 50)
    @Column(name = "AddressLine2")
    private String addressLine2;
    @Size(max = 5)
    @Column(name = "CityCode")
    private String cityCode;
    @Size(max = 6)
    @Column(name = "PostalCode")
    private String postalCode;
    @Size(max = 3)
    @Column(name = "CountryCode")
    private String countryCode;
    @Size(max = 15)
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Size(max = 15)
    @Column(name = "FaxNumber")
    private String faxNumber;
    @Size(max = 15)
    @Column(name = "TelexNumber")
    private String telexNumber;
    @Size(max = 1)
    @Column(name = "Native")
    private String native1;
    @Size(max = 1)
    @Column(name = "InlandTradeAllowed")
    private String inlandTradeAllowed;
    @Column(name = "ReviewDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;
    @Size(max = 5)
    @Column(name = "IndustryType")
    private String industryType;
    @Size(max = 1)
    @Column(name = "ExportUnitFlag")
    private String exportUnitFlag;
    @Size(max = 1)
    @Column(name = "Status")
    private String status;
    @Size(max = 3)
    @Column(name = "TradeExpCode")
    private String tradeExpCode;
    @Size(max = 5)
    @Column(name = "PartyConstitution")
    private String partyConstitution;
    @Size(max = 1)
    @Column(name = "SpecialParty")
    private String specialParty;
    @Size(max = 25)
    @Column(name = "PartyType")
    private String partyType;
    @Size(max = 3)
    @Column(name = "ProductionCycle")
    private String productionCycle;
    @Size(max = 12)
    @Column(name = "CodeGivenByCentralBANK")
    private String codeGivenByCentralBANK;
    @Size(max = 12)
    @Column(name = "CodeGivenByTradeAuthority")
    private String codeGivenByTradeAuthority;
    @Size(max = 1)
    @Column(name = "Type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ForwardContractLimit")
    private Double forwardContractLimit;
    @Column(name = "FCMargin")
    private Double fCMargin;
    @Size(max = 40)
    @Column(name = "FCSanctioningAuthority")
    private String fCSanctioningAuthority;
    @Column(name = "FCSanctionExpiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fCSanctionExpiryDate;
    @Size(max = 20)
    @Column(name = "FCNextNumberCode")
    private String fCNextNumberCode;
    @Column(name = "DocumentCreditLimit")
    private Double documentCreditLimit;
    @Column(name = "DCMargin")
    private Double dCMargin;
    @Size(max = 40)
    @Column(name = "DCSanctioningAuthority")
    private String dCSanctioningAuthority;
    @Column(name = "DCSanctionExpiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dCSanctionExpiryDate;
    @Size(max = 20)
    @Column(name = "DCNextNumberCode")
    private String dCNextNumberCode;
    @Column(name = "PackingContractLimit")
    private Double packingContractLimit;
    @Column(name = "PCMargin")
    private Double pCMargin;
    @Size(max = 40)
    @Column(name = "PCSanctioningAuthority")
    private String pCSanctioningAuthority;
    @Column(name = "PCSanctionExpiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pCSanctionExpiryDate;
    @Size(max = 20)
    @Column(name = "PCNextNumberCode")
    private String pCNextNumberCode;
    @Column(name = "BankGuaranteeLimit")
    private Double bankGuaranteeLimit;
    @Column(name = "BGMargin")
    private Double bGMargin;
    @Size(max = 40)
    @Column(name = "BGSanctioningAuthority")
    private String bGSanctioningAuthority;
    @Column(name = "BGSanctionExpiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bGSanctionExpiryDate;
    @Size(max = 20)
    @Column(name = "BGNextNumberCode")
    private String bGNextNumberCode;
    @Column(name = "PresentOutstandingLiability")
    private Double presentOutstandingLiability;
    @Size(max = 15)
    @Column(name = "LastChangeUserID")
    private String lastChangeUserID;
    @Column(name = "LastChangeTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeTime;
    @Size(max = 15)
    @Column(name = "RecordCreaterID")
    private String recordCreaterID;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXNID")
    private Long txnid;

    public CBSTradeFinanceInformationHis() {
    }

    public CBSTradeFinanceInformationHis(Long txnid) {
        this.txnid = txnid;
    }

    public CBSTradeFinanceInformationHis(Long txnid, String customerId) {
        this.txnid = txnid;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getTelexNumber() {
        return telexNumber;
    }

    public void setTelexNumber(String telexNumber) {
        this.telexNumber = telexNumber;
    }

    public String getNative1() {
        return native1;
    }

    public void setNative1(String native1) {
        this.native1 = native1;
    }

    public String getInlandTradeAllowed() {
        return inlandTradeAllowed;
    }

    public void setInlandTradeAllowed(String inlandTradeAllowed) {
        this.inlandTradeAllowed = inlandTradeAllowed;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getExportUnitFlag() {
        return exportUnitFlag;
    }

    public void setExportUnitFlag(String exportUnitFlag) {
        this.exportUnitFlag = exportUnitFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTradeExpCode() {
        return tradeExpCode;
    }

    public void setTradeExpCode(String tradeExpCode) {
        this.tradeExpCode = tradeExpCode;
    }

    public String getPartyConstitution() {
        return partyConstitution;
    }

    public void setPartyConstitution(String partyConstitution) {
        this.partyConstitution = partyConstitution;
    }

    public String getSpecialParty() {
        return specialParty;
    }

    public void setSpecialParty(String specialParty) {
        this.specialParty = specialParty;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getProductionCycle() {
        return productionCycle;
    }

    public void setProductionCycle(String productionCycle) {
        this.productionCycle = productionCycle;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getForwardContractLimit() {
        return forwardContractLimit;
    }

    public void setForwardContractLimit(Double forwardContractLimit) {
        this.forwardContractLimit = forwardContractLimit;
    }

    public Double getFCMargin() {
        return fCMargin;
    }

    public void setFCMargin(Double fCMargin) {
        this.fCMargin = fCMargin;
    }

    public String getFCSanctioningAuthority() {
        return fCSanctioningAuthority;
    }

    public void setFCSanctioningAuthority(String fCSanctioningAuthority) {
        this.fCSanctioningAuthority = fCSanctioningAuthority;
    }

    public Date getFCSanctionExpiryDate() {
        return fCSanctionExpiryDate;
    }

    public void setFCSanctionExpiryDate(Date fCSanctionExpiryDate) {
        this.fCSanctionExpiryDate = fCSanctionExpiryDate;
    }

    public String getFCNextNumberCode() {
        return fCNextNumberCode;
    }

    public void setFCNextNumberCode(String fCNextNumberCode) {
        this.fCNextNumberCode = fCNextNumberCode;
    }

    public Double getDocumentCreditLimit() {
        return documentCreditLimit;
    }

    public void setDocumentCreditLimit(Double documentCreditLimit) {
        this.documentCreditLimit = documentCreditLimit;
    }

    public Double getDCMargin() {
        return dCMargin;
    }

    public void setDCMargin(Double dCMargin) {
        this.dCMargin = dCMargin;
    }

    public String getDCSanctioningAuthority() {
        return dCSanctioningAuthority;
    }

    public void setDCSanctioningAuthority(String dCSanctioningAuthority) {
        this.dCSanctioningAuthority = dCSanctioningAuthority;
    }

    public Date getDCSanctionExpiryDate() {
        return dCSanctionExpiryDate;
    }

    public void setDCSanctionExpiryDate(Date dCSanctionExpiryDate) {
        this.dCSanctionExpiryDate = dCSanctionExpiryDate;
    }

    public String getDCNextNumberCode() {
        return dCNextNumberCode;
    }

    public void setDCNextNumberCode(String dCNextNumberCode) {
        this.dCNextNumberCode = dCNextNumberCode;
    }

    public Double getPackingContractLimit() {
        return packingContractLimit;
    }

    public void setPackingContractLimit(Double packingContractLimit) {
        this.packingContractLimit = packingContractLimit;
    }

    public Double getPCMargin() {
        return pCMargin;
    }

    public void setPCMargin(Double pCMargin) {
        this.pCMargin = pCMargin;
    }

    public String getPCSanctioningAuthority() {
        return pCSanctioningAuthority;
    }

    public void setPCSanctioningAuthority(String pCSanctioningAuthority) {
        this.pCSanctioningAuthority = pCSanctioningAuthority;
    }

    public Date getPCSanctionExpiryDate() {
        return pCSanctionExpiryDate;
    }

    public void setPCSanctionExpiryDate(Date pCSanctionExpiryDate) {
        this.pCSanctionExpiryDate = pCSanctionExpiryDate;
    }

    public String getPCNextNumberCode() {
        return pCNextNumberCode;
    }

    public void setPCNextNumberCode(String pCNextNumberCode) {
        this.pCNextNumberCode = pCNextNumberCode;
    }

    public Double getBankGuaranteeLimit() {
        return bankGuaranteeLimit;
    }

    public void setBankGuaranteeLimit(Double bankGuaranteeLimit) {
        this.bankGuaranteeLimit = bankGuaranteeLimit;
    }

    public Double getBGMargin() {
        return bGMargin;
    }

    public void setBGMargin(Double bGMargin) {
        this.bGMargin = bGMargin;
    }

    public String getBGSanctioningAuthority() {
        return bGSanctioningAuthority;
    }

    public void setBGSanctioningAuthority(String bGSanctioningAuthority) {
        this.bGSanctioningAuthority = bGSanctioningAuthority;
    }

    public Date getBGSanctionExpiryDate() {
        return bGSanctionExpiryDate;
    }

    public void setBGSanctionExpiryDate(Date bGSanctionExpiryDate) {
        this.bGSanctionExpiryDate = bGSanctionExpiryDate;
    }

    public String getBGNextNumberCode() {
        return bGNextNumberCode;
    }

    public void setBGNextNumberCode(String bGNextNumberCode) {
        this.bGNextNumberCode = bGNextNumberCode;
    }

    public Double getPresentOutstandingLiability() {
        return presentOutstandingLiability;
    }

    public void setPresentOutstandingLiability(Double presentOutstandingLiability) {
        this.presentOutstandingLiability = presentOutstandingLiability;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnid != null ? txnid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBSTradeFinanceInformationHis)) {
            return false;
        }
        CBSTradeFinanceInformationHis other = (CBSTradeFinanceInformationHis) object;
        if ((this.txnid == null && other.txnid != null) || (this.txnid != null && !this.txnid.equals(other.txnid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.customer.CBSTradeFinanceInformationHis[ txnid=" + txnid + " ]";
    }
    
}
