/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "mb_sms_sender_bank_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbSmsSenderBankDetail.findAll", query = "SELECT m FROM MbSmsSenderBankDetail m"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByBankCode", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.bankCode = :bankCode"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findBySmsShortCode", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.smsShortCode = :smsShortCode"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findBySmsSender", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.smsSender = :smsSender"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByBankShortName", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.bankShortName = :bankShortName"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findBySenderId", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.senderId = :senderId"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByTemplateBankName", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.templateBankName = :templateBankName"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findBySmsVendor", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.smsVendor = :smsVendor"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByIin", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.iin = :iin"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByAadharLocation", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.aadharLocation = :aadharLocation"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByNpciBankCode", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.npciBankCode = :npciBankCode"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByNeftClientCode", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.neftClientCode = :neftClientCode"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByApyBackOffRefNo", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.apyBackOffRefNo = :apyBackOffRefNo"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByApyNlaoRegNo", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.apyNlaoRegNo = :apyNlaoRegNo"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByApyNlccRegNo", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.apyNlccRegNo = :apyNlccRegNo"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByBankEmail", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.bankEmail = :bankEmail"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByNeftOwBankName", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.neftOwBankName = :neftOwBankName"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByIdbiBankCode", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.idbiBankCode = :idbiBankCode"),
    @NamedQuery(name = "MbSmsSenderBankDetail.findByHdfcDomainId", query = "SELECT m FROM MbSmsSenderBankDetail m WHERE m.hdfcDomainId = :hdfcDomainId")})
public class MbSmsSenderBankDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "BANK_CODE")
    private String bankCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SMS_SHORT_CODE")
    private String smsShortCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SMS_SENDER")
    private String smsSender;
    @Size(max = 20)
    @Column(name = "bank_short_name")
    private String bankShortName;
    @Size(max = 6)
    @Column(name = "sender_id")
    private String senderId;
    @Size(max = 150)
    @Column(name = "template_bank_name")
    private String templateBankName;
    @Size(max = 50)
    @Column(name = "SMS_VENDOR")
    private String smsVendor;
    @Size(max = 9)
    @Column(name = "IIN")
    private String iin;
    @Size(max = 100)
    @Column(name = "AADHAR_LOCATION")
    private String aadharLocation;
    @Size(max = 4)
    @Column(name = "NPCI_BANK_CODE")
    private String npciBankCode;
    @Size(max = 5)
    @Column(name = "NEFT_CLIENT_CODE")
    private String neftClientCode;
    @Size(max = 17)
    @Column(name = "APY_BACK_OFF_REF_NO")
    private String apyBackOffRefNo;
    @Size(max = 7)
    @Column(name = "APY_NLAO_REG_NO")
    private String apyNlaoRegNo;
    @Size(max = 10)
    @Column(name = "APY_NLCC_REG_NO")
    private String apyNlccRegNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BANK_EMAIL")
    private String bankEmail;
    @Size(max = 20)
    @Column(name = "NEFT_OW_BANK_NAME")
    private String neftOwBankName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDBI_BANK_CODE")
    private String idbiBankCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "HDFC_DOMAIN_ID")
    private String hdfcDomainId;

    public MbSmsSenderBankDetail() {
    }

    public MbSmsSenderBankDetail(String bankCode) {
        this.bankCode = bankCode;
    }

    public MbSmsSenderBankDetail(String bankCode, String smsShortCode, String smsSender, String bankEmail, String idbiBankCode, String hdfcDomainId) {
        this.bankCode = bankCode;
        this.smsShortCode = smsShortCode;
        this.smsSender = smsSender;
        this.bankEmail = bankEmail;
        this.idbiBankCode = idbiBankCode;
        this.hdfcDomainId = hdfcDomainId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSmsShortCode() {
        return smsShortCode;
    }

    public void setSmsShortCode(String smsShortCode) {
        this.smsShortCode = smsShortCode;
    }

    public String getSmsSender() {
        return smsSender;
    }

    public void setSmsSender(String smsSender) {
        this.smsSender = smsSender;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTemplateBankName() {
        return templateBankName;
    }

    public void setTemplateBankName(String templateBankName) {
        this.templateBankName = templateBankName;
    }

    public String getSmsVendor() {
        return smsVendor;
    }

    public void setSmsVendor(String smsVendor) {
        this.smsVendor = smsVendor;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getAadharLocation() {
        return aadharLocation;
    }

    public void setAadharLocation(String aadharLocation) {
        this.aadharLocation = aadharLocation;
    }

    public String getNpciBankCode() {
        return npciBankCode;
    }

    public void setNpciBankCode(String npciBankCode) {
        this.npciBankCode = npciBankCode;
    }

    public String getNeftClientCode() {
        return neftClientCode;
    }

    public void setNeftClientCode(String neftClientCode) {
        this.neftClientCode = neftClientCode;
    }

    public String getApyBackOffRefNo() {
        return apyBackOffRefNo;
    }

    public void setApyBackOffRefNo(String apyBackOffRefNo) {
        this.apyBackOffRefNo = apyBackOffRefNo;
    }

    public String getApyNlaoRegNo() {
        return apyNlaoRegNo;
    }

    public void setApyNlaoRegNo(String apyNlaoRegNo) {
        this.apyNlaoRegNo = apyNlaoRegNo;
    }

    public String getApyNlccRegNo() {
        return apyNlccRegNo;
    }

    public void setApyNlccRegNo(String apyNlccRegNo) {
        this.apyNlccRegNo = apyNlccRegNo;
    }

    public String getBankEmail() {
        return bankEmail;
    }

    public void setBankEmail(String bankEmail) {
        this.bankEmail = bankEmail;
    }

    public String getNeftOwBankName() {
        return neftOwBankName;
    }

    public void setNeftOwBankName(String neftOwBankName) {
        this.neftOwBankName = neftOwBankName;
    }

    public String getIdbiBankCode() {
        return idbiBankCode;
    }

    public void setIdbiBankCode(String idbiBankCode) {
        this.idbiBankCode = idbiBankCode;
    }

    public String getHdfcDomainId() {
        return hdfcDomainId;
    }

    public void setHdfcDomainId(String hdfcDomainId) {
        this.hdfcDomainId = hdfcDomainId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankCode != null ? bankCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbSmsSenderBankDetail)) {
            return false;
        }
        MbSmsSenderBankDetail other = (MbSmsSenderBankDetail) object;
        if ((this.bankCode == null && other.bankCode != null) || (this.bankCode != null && !this.bankCode.equals(other.bankCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSmsSenderBankDetail[ bankCode=" + bankCode + " ]";
    }
}
