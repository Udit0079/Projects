/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author root
 */
@Entity
@Table(name = "neft_ow_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NeftOwDetails.findAll", query = "SELECT n FROM NeftOwDetails n"),
    @NamedQuery(name = "NeftOwDetails.findByPaymentType", query = "SELECT n FROM NeftOwDetails n WHERE n.paymentType = :paymentType"),
    @NamedQuery(name = "NeftOwDetails.findByUniqueCustomerRefNo", query = "SELECT n FROM NeftOwDetails n WHERE n.uniqueCustomerRefNo = :uniqueCustomerRefNo"),
    @NamedQuery(name = "NeftOwDetails.findByBeneficiaryName", query = "SELECT n FROM NeftOwDetails n WHERE n.beneficiaryName = :beneficiaryName"),
    @NamedQuery(name = "NeftOwDetails.findByBeneficiaryCode", query = "SELECT n FROM NeftOwDetails n WHERE n.beneficiaryCode = :beneficiaryCode"),
    @NamedQuery(name = "NeftOwDetails.findByTxnAmt", query = "SELECT n FROM NeftOwDetails n WHERE n.txnAmt = :txnAmt"),
    @NamedQuery(name = "NeftOwDetails.findByPaymentDate", query = "SELECT n FROM NeftOwDetails n WHERE n.paymentDate = :paymentDate"),
    @NamedQuery(name = "NeftOwDetails.findByCreditAccountNo", query = "SELECT n FROM NeftOwDetails n WHERE n.creditAccountNo = :creditAccountNo"),
    @NamedQuery(name = "NeftOwDetails.findByOutsideBankIfscCode", query = "SELECT n FROM NeftOwDetails n WHERE n.outsideBankIfscCode = :outsideBankIfscCode"),
    @NamedQuery(name = "NeftOwDetails.findByDebitAccountNo", query = "SELECT n FROM NeftOwDetails n WHERE n.debitAccountNo = :debitAccountNo"),
    @NamedQuery(name = "NeftOwDetails.findByBeneficiaryEmailId", query = "SELECT n FROM NeftOwDetails n WHERE n.beneficiaryEmailId = :beneficiaryEmailId"),
    @NamedQuery(name = "NeftOwDetails.findByBeneficiaryMobileNo", query = "SELECT n FROM NeftOwDetails n WHERE n.beneficiaryMobileNo = :beneficiaryMobileNo"),
    @NamedQuery(name = "NeftOwDetails.findByCmsBankRefNo", query = "SELECT n FROM NeftOwDetails n WHERE n.cmsBankRefNo = :cmsBankRefNo"),
    @NamedQuery(name = "NeftOwDetails.findByUtrNo", query = "SELECT n FROM NeftOwDetails n WHERE n.utrNo = :utrNo"),
    @NamedQuery(name = "NeftOwDetails.findByInstNo", query = "SELECT n FROM NeftOwDetails n WHERE n.instNo = :instNo"),
    @NamedQuery(name = "NeftOwDetails.findByInstDate", query = "SELECT n FROM NeftOwDetails n WHERE n.instDate = :instDate"),
    @NamedQuery(name = "NeftOwDetails.findByDt", query = "SELECT n FROM NeftOwDetails n WHERE n.dt = :dt"),
    @NamedQuery(name = "NeftOwDetails.findByTrantime", query = "SELECT n FROM NeftOwDetails n WHERE n.trantime = :trantime"),
    @NamedQuery(name = "NeftOwDetails.findByStatus", query = "SELECT n FROM NeftOwDetails n WHERE n.status = :status"),
    @NamedQuery(name = "NeftOwDetails.findByReason", query = "SELECT n FROM NeftOwDetails n WHERE n.reason = :reason"),
    @NamedQuery(name = "NeftOwDetails.findByDetails", query = "SELECT n FROM NeftOwDetails n WHERE n.details = :details"),
    @NamedQuery(name = "NeftOwDetails.findByOrgBrnid", query = "SELECT n FROM NeftOwDetails n WHERE n.orgBrnid = :orgBrnid"),
    @NamedQuery(name = "NeftOwDetails.findByDestBrnid", query = "SELECT n FROM NeftOwDetails n WHERE n.destBrnid = :destBrnid"),
    @NamedQuery(name = "NeftOwDetails.findByAuth", query = "SELECT n FROM NeftOwDetails n WHERE n.auth = :auth"),
    @NamedQuery(name = "NeftOwDetails.findByEnterBy", query = "SELECT n FROM NeftOwDetails n WHERE n.enterBy = :enterBy"),
    @NamedQuery(name = "NeftOwDetails.findByAuthby", query = "SELECT n FROM NeftOwDetails n WHERE n.authby = :authby"),
    @NamedQuery(name = "NeftOwDetails.findByChargeType", query = "SELECT n FROM NeftOwDetails n WHERE n.chargeType = :chargeType"),
    @NamedQuery(name = "NeftOwDetails.findByChargeAmount", query = "SELECT n FROM NeftOwDetails n WHERE n.chargeAmount = :chargeAmount"),
    @NamedQuery(name = "NeftOwDetails.findByFileName", query = "SELECT n FROM NeftOwDetails n WHERE n.fileName = :fileName"),
    @NamedQuery(name = "NeftOwDetails.findBySenderCommModeType", query = "SELECT n FROM NeftOwDetails n WHERE n.senderCommModeType = :senderCommModeType"),
    @NamedQuery(name = "NeftOwDetails.findBySenderCommMode", query = "SELECT n FROM NeftOwDetails n WHERE n.senderCommMode = :senderCommMode"),
    @NamedQuery(name = "NeftOwDetails.findByRemmitInfo", query = "SELECT n FROM NeftOwDetails n WHERE n.remmitInfo = :remmitInfo"),
    @NamedQuery(name = "NeftOwDetails.findByOutwardFileName", query = "SELECT n FROM NeftOwDetails n WHERE n.outwardFileName = :outwardFileName"),
    @NamedQuery(name = "NeftOwDetails.findByCPSMSMessageId", query = "SELECT n FROM NeftOwDetails n WHERE n.cPSMSMessageId = :cPSMSMessageId"),
    @NamedQuery(name = "NeftOwDetails.findByCPSMSBatchNo", query = "SELECT n FROM NeftOwDetails n WHERE n.cPSMSBatchNo = :cPSMSBatchNo"),
    @NamedQuery(name = "NeftOwDetails.findByCPSMSTranIdCrTranId", query = "SELECT n FROM NeftOwDetails n WHERE n.cPSMSTranIdCrTranId = :cPSMSTranIdCrTranId"),
    @NamedQuery(name = "NeftOwDetails.findByDebitSuccessTrsno", query = "SELECT n FROM NeftOwDetails n WHERE n.debitSuccessTrsno = :debitSuccessTrsno"),
    @NamedQuery(name = "NeftOwDetails.findByResponseUpdateTime", query = "SELECT n FROM NeftOwDetails n WHERE n.responseUpdateTime = :responseUpdateTime"),
    @NamedQuery(name = "NeftOwDetails.findBySuccessToFailureFlag", query = "SELECT n FROM NeftOwDetails n WHERE n.successToFailureFlag = :successToFailureFlag"),
    @NamedQuery(name = "NeftOwDetails.findByDebitAmount", query = "SELECT n FROM NeftOwDetails n WHERE n.debitAmount = :debitAmount"),
    @NamedQuery(name = "NeftOwDetails.findByTrsNo", query = "SELECT n FROM NeftOwDetails n WHERE n.trsNo = :trsNo"),
    @NamedQuery(name = "NeftOwDetails.findByUpdateBy", query = "SELECT n FROM NeftOwDetails n WHERE n.updateBy = :updateBy"),
    @NamedQuery(name = "NeftOwDetails.findByUpdateDt", query = "SELECT n FROM NeftOwDetails n WHERE n.updateDt = :updateDt"),
    @NamedQuery(name = "NeftOwDetails.findByInitiatedBankName", query = "SELECT n FROM NeftOwDetails n WHERE n.initiatedBankName = :initiatedBankName")})
public class NeftOwDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PaymentType")
    private String paymentType;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "UniqueCustomerRefNo")
    private String uniqueCustomerRefNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "BeneficiaryName")
    private String beneficiaryName;
    @Size(max = 30)
    @Column(name = "BeneficiaryCode")
    private String beneficiaryCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "TxnAmt")
    private BigDecimal txnAmt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PaymentDate")
    private String paymentDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CreditAccountNo")
    private String creditAccountNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "OutsideBankIfscCode")
    private String outsideBankIfscCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "DebitAccountNo")
    private String debitAccountNo;
    @Size(max = 100)
    @Column(name = "BeneficiaryEmailId")
    private String beneficiaryEmailId;
    @Size(max = 10)
    @Column(name = "BeneficiaryMobileNo")
    private String beneficiaryMobileNo;
    @Size(max = 18)
    @Column(name = "CmsBankRefNo")
    private String cmsBankRefNo;
    @Size(max = 30)
    @Column(name = "UTRNO")
    private String utrNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "InstNo")
    private String instNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "InstDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date instDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trantime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trantime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Status")
    private String status;
    @Size(max = 255)
    @Column(name = "Reason")
    private String reason;
    @Size(max = 255)
    @Column(name = "Details")
    private String details;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "OrgBrnid")
    private String orgBrnid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "DestBrnid")
    private String destBrnid;
    @Size(max = 1)
    @Column(name = "Auth")
    private String auth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EnterBy")
    private String enterBy;
    @Size(max = 30)
    @Column(name = "Authby")
    private String authby;
    @Size(max = 60)
    @Column(name = "ChargeType")
    private String chargeType;
    @Column(name = "ChargeAmount")
    private BigDecimal chargeAmount;
    @Size(max = 50)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 50)
    @Column(name = "SENDER_COMM_MODE_TYPE")
    private String senderCommModeType;
    @Size(max = 50)
    @Column(name = "SENDER_COMM_MODE")
    private String senderCommMode;
    @Size(max = 50)
    @Column(name = "REMmIT_INFO")
    private String remmitInfo;
    @Size(max = 100)
    @Column(name = "Outward_File_Name")
    private String outwardFileName;
    @Size(max = 30)
    @Column(name = "CPSMS_MessageId")
    private String cPSMSMessageId;
    @Size(max = 16)
    @Column(name = "CPSMS_Batch_No")
    private String cPSMSBatchNo;
    @Size(max = 16)
    @Column(name = "CPSMSTranId_Cr_Tran_Id")
    private String cPSMSTranIdCrTranId;
    @Column(name = "Debit_Success_Trsno")
    private Float debitSuccessTrsno;
    @Column(name = "Response_Update_Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseUpdateTime;
    @Size(max = 1)
    @Column(name = "Success_To_Failure_Flag")
    private String successToFailureFlag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Debit_Amount")
    private BigDecimal debitAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trs_No")
    private float trsNo;
    @Size(max = 150)
    @Column(name = "Update_By")
    private String updateBy;
    @Column(name = "Update_Dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Initiated_Bank_Name")
    private String initiatedBankName;

    public NeftOwDetails() {
    }

    public NeftOwDetails(String uniqueCustomerRefNo) {
        this.uniqueCustomerRefNo = uniqueCustomerRefNo;
    }

    public NeftOwDetails(String uniqueCustomerRefNo, String paymentType, String beneficiaryName, BigDecimal txnAmt, String paymentDate, String creditAccountNo, String outsideBankIfscCode, String debitAccountNo, String instNo, Date instDate, Date dt, Date trantime, String status, String orgBrnid, String destBrnid, String enterBy, BigDecimal debitAmount, float trsNo, String initiatedBankName) {
        this.uniqueCustomerRefNo = uniqueCustomerRefNo;
        this.paymentType = paymentType;
        this.beneficiaryName = beneficiaryName;
        this.txnAmt = txnAmt;
        this.paymentDate = paymentDate;
        this.creditAccountNo = creditAccountNo;
        this.outsideBankIfscCode = outsideBankIfscCode;
        this.debitAccountNo = debitAccountNo;
        this.instNo = instNo;
        this.instDate = instDate;
        this.dt = dt;
        this.trantime = trantime;
        this.status = status;
        this.orgBrnid = orgBrnid;
        this.destBrnid = destBrnid;
        this.enterBy = enterBy;
        this.debitAmount = debitAmount;
        this.trsNo = trsNo;
        this.initiatedBankName = initiatedBankName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getUniqueCustomerRefNo() {
        return uniqueCustomerRefNo;
    }

    public void setUniqueCustomerRefNo(String uniqueCustomerRefNo) {
        this.uniqueCustomerRefNo = uniqueCustomerRefNo;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    public void setBeneficiaryCode(String beneficiaryCode) {
        this.beneficiaryCode = beneficiaryCode;
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCreditAccountNo() {
        return creditAccountNo;
    }

    public void setCreditAccountNo(String creditAccountNo) {
        this.creditAccountNo = creditAccountNo;
    }

    public String getOutsideBankIfscCode() {
        return outsideBankIfscCode;
    }

    public void setOutsideBankIfscCode(String outsideBankIfscCode) {
        this.outsideBankIfscCode = outsideBankIfscCode;
    }

    public String getDebitAccountNo() {
        return debitAccountNo;
    }

    public void setDebitAccountNo(String debitAccountNo) {
        this.debitAccountNo = debitAccountNo;
    }

    public String getBeneficiaryEmailId() {
        return beneficiaryEmailId;
    }

    public void setBeneficiaryEmailId(String beneficiaryEmailId) {
        this.beneficiaryEmailId = beneficiaryEmailId;
    }

    public String getBeneficiaryMobileNo() {
        return beneficiaryMobileNo;
    }

    public void setBeneficiaryMobileNo(String beneficiaryMobileNo) {
        this.beneficiaryMobileNo = beneficiaryMobileNo;
    }

    public String getCmsBankRefNo() {
        return cmsBankRefNo;
    }

    public void setCmsBankRefNo(String cmsBankRefNo) {
        this.cmsBankRefNo = cmsBankRefNo;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public Date getInstDate() {
        return instDate;
    }

    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getTrantime() {
        return trantime;
    }

    public void setTrantime(Date trantime) {
        this.trantime = trantime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOrgBrnid() {
        return orgBrnid;
    }

    public void setOrgBrnid(String orgBrnid) {
        this.orgBrnid = orgBrnid;
    }

    public String getDestBrnid() {
        return destBrnid;
    }

    public void setDestBrnid(String destBrnid) {
        this.destBrnid = destBrnid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthby() {
        return authby;
    }

    public void setAuthby(String authby) {
        this.authby = authby;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSenderCommModeType() {
        return senderCommModeType;
    }

    public void setSenderCommModeType(String senderCommModeType) {
        this.senderCommModeType = senderCommModeType;
    }

    public String getSenderCommMode() {
        return senderCommMode;
    }

    public void setSenderCommMode(String senderCommMode) {
        this.senderCommMode = senderCommMode;
    }

    public String getRemmitInfo() {
        return remmitInfo;
    }

    public void setRemmitInfo(String remmitInfo) {
        this.remmitInfo = remmitInfo;
    }

    public String getOutwardFileName() {
        return outwardFileName;
    }

    public void setOutwardFileName(String outwardFileName) {
        this.outwardFileName = outwardFileName;
    }

    public String getCPSMSMessageId() {
        return cPSMSMessageId;
    }

    public void setCPSMSMessageId(String cPSMSMessageId) {
        this.cPSMSMessageId = cPSMSMessageId;
    }

    public String getCPSMSBatchNo() {
        return cPSMSBatchNo;
    }

    public void setCPSMSBatchNo(String cPSMSBatchNo) {
        this.cPSMSBatchNo = cPSMSBatchNo;
    }

    public String getCPSMSTranIdCrTranId() {
        return cPSMSTranIdCrTranId;
    }

    public void setCPSMSTranIdCrTranId(String cPSMSTranIdCrTranId) {
        this.cPSMSTranIdCrTranId = cPSMSTranIdCrTranId;
    }

    public Float getDebitSuccessTrsno() {
        return debitSuccessTrsno;
    }

    public void setDebitSuccessTrsno(Float debitSuccessTrsno) {
        this.debitSuccessTrsno = debitSuccessTrsno;
    }

    public Date getResponseUpdateTime() {
        return responseUpdateTime;
    }

    public void setResponseUpdateTime(Date responseUpdateTime) {
        this.responseUpdateTime = responseUpdateTime;
    }

    public String getSuccessToFailureFlag() {
        return successToFailureFlag;
    }

    public void setSuccessToFailureFlag(String successToFailureFlag) {
        this.successToFailureFlag = successToFailureFlag;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public float getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(float trsNo) {
        this.trsNo = trsNo;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getInitiatedBankName() {
        return initiatedBankName;
    }

    public void setInitiatedBankName(String initiatedBankName) {
        this.initiatedBankName = initiatedBankName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uniqueCustomerRefNo != null ? uniqueCustomerRefNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftOwDetails)) {
            return false;
        }
        NeftOwDetails other = (NeftOwDetails) object;
        if ((this.uniqueCustomerRefNo == null && other.uniqueCustomerRefNo != null) || (this.uniqueCustomerRefNo != null && !this.uniqueCustomerRefNo.equals(other.uniqueCustomerRefNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftOwDetails[ uniqueCustomerRefNo=" + uniqueCustomerRefNo + " ]";
    }
}
