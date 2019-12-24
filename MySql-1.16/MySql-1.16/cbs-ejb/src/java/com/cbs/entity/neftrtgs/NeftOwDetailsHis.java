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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "neft_ow_details_his")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NeftOwDetailsHis.findAll", query = "SELECT n FROM NeftOwDetailsHis n"),
    @NamedQuery(name = "NeftOwDetailsHis.findByPaymentType", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.paymentType = :paymentType"),
    @NamedQuery(name = "NeftOwDetailsHis.findByUniqueCustomerRefNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.neftOwDetailsHisPK.uniqueCustomerRefNo = :uniqueCustomerRefNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findBySNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.neftOwDetailsHisPK.sNo = :sNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByBeneficiaryName", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.beneficiaryName = :beneficiaryName"),
    @NamedQuery(name = "NeftOwDetailsHis.findByBeneficiaryCode", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.beneficiaryCode = :beneficiaryCode"),
    @NamedQuery(name = "NeftOwDetailsHis.findByTxnAmt", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.txnAmt = :txnAmt"),
    @NamedQuery(name = "NeftOwDetailsHis.findByPaymentDate", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.paymentDate = :paymentDate"),
    @NamedQuery(name = "NeftOwDetailsHis.findByCreditAccountNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.creditAccountNo = :creditAccountNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByOutsideBankIfscCode", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.outsideBankIfscCode = :outsideBankIfscCode"),
    @NamedQuery(name = "NeftOwDetailsHis.findByDebitAccountNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.debitAccountNo = :debitAccountNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByBeneficiaryEmailId", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.beneficiaryEmailId = :beneficiaryEmailId"),
    @NamedQuery(name = "NeftOwDetailsHis.findByBeneficiaryMobileNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.beneficiaryMobileNo = :beneficiaryMobileNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByCmsBankRefNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.cmsBankRefNo = :cmsBankRefNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByUtrNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.utrNo = :utrNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByInstNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.instNo = :instNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByInstDate", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.instDate = :instDate"),
    @NamedQuery(name = "NeftOwDetailsHis.findByDt", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.dt = :dt"),
    @NamedQuery(name = "NeftOwDetailsHis.findByTrantime", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.trantime = :trantime"),
    @NamedQuery(name = "NeftOwDetailsHis.findByStatus", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.status = :status"),
    @NamedQuery(name = "NeftOwDetailsHis.findByReason", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.reason = :reason"),
    @NamedQuery(name = "NeftOwDetailsHis.findByDetails", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.details = :details"),
    @NamedQuery(name = "NeftOwDetailsHis.findByOrgBrnid", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.orgBrnid = :orgBrnid"),
    @NamedQuery(name = "NeftOwDetailsHis.findByDestBrnid", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.destBrnid = :destBrnid"),
    @NamedQuery(name = "NeftOwDetailsHis.findByEnterBy", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.enterBy = :enterBy"),
    @NamedQuery(name = "NeftOwDetailsHis.findByModifiedBy", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "NeftOwDetailsHis.findByChargeType", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.chargeType = :chargeType"),
    @NamedQuery(name = "NeftOwDetailsHis.findByChargeAmount", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.chargeAmount = :chargeAmount"),
    @NamedQuery(name = "NeftOwDetailsHis.findByFileName", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.fileName = :fileName"),
    @NamedQuery(name = "NeftOwDetailsHis.findBySenderCommModeType", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.senderCommModeType = :senderCommModeType"),
    @NamedQuery(name = "NeftOwDetailsHis.findBySenderCommMode", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.senderCommMode = :senderCommMode"),
    @NamedQuery(name = "NeftOwDetailsHis.findByRemmitInfo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.remmitInfo = :remmitInfo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByOutwardFileName", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.outwardFileName = :outwardFileName"),
    @NamedQuery(name = "NeftOwDetailsHis.findByCPSMSMessageId", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.cPSMSMessageId = :cPSMSMessageId"),
    @NamedQuery(name = "NeftOwDetailsHis.findByCPSMSBatchNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.cPSMSBatchNo = :cPSMSBatchNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByCPSMSTranIdCrTranId", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.cPSMSTranIdCrTranId = :cPSMSTranIdCrTranId"),
    @NamedQuery(name = "NeftOwDetailsHis.findByDebitSuccessTrsno", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.debitSuccessTrsno = :debitSuccessTrsno"),
    @NamedQuery(name = "NeftOwDetailsHis.findByResponseUpdateTime", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.responseUpdateTime = :responseUpdateTime"),
    @NamedQuery(name = "NeftOwDetailsHis.findBySuccessToFailureFlag", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.successToFailureFlag = :successToFailureFlag"),
    @NamedQuery(name = "NeftOwDetailsHis.findByDebitAmount", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.debitAmount = :debitAmount"),
    @NamedQuery(name = "NeftOwDetailsHis.findByTrsNo", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.trsNo = :trsNo"),
    @NamedQuery(name = "NeftOwDetailsHis.findByUpdateBy", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.updateBy = :updateBy"),
    @NamedQuery(name = "NeftOwDetailsHis.findByUpdateDt", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.updateDt = :updateDt"),
    @NamedQuery(name = "NeftOwDetailsHis.findByInitiatedBankName", query = "SELECT n FROM NeftOwDetailsHis n WHERE n.initiatedBankName = :initiatedBankName")})
public class NeftOwDetailsHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NeftOwDetailsHisPK neftOwDetailsHisPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PaymentType")
    private String paymentType;
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
    @Column(name = "STATUS")
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EnterBy")
    private String enterBy;
    @Size(max = 30)
    @Column(name = "ModifiedBy")
    private String modifiedBy;
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
    @Size(max = 50)
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

    public NeftOwDetailsHis() {
    }

    public NeftOwDetailsHis(NeftOwDetailsHisPK neftOwDetailsHisPK) {
        this.neftOwDetailsHisPK = neftOwDetailsHisPK;
    }

    public NeftOwDetailsHis(NeftOwDetailsHisPK neftOwDetailsHisPK, String paymentType, String beneficiaryName, BigDecimal txnAmt, String paymentDate, String creditAccountNo, String outsideBankIfscCode, String debitAccountNo, String instNo, Date instDate, Date dt, Date trantime, String status, String orgBrnid, String destBrnid, String enterBy, BigDecimal debitAmount, float trsNo, String initiatedBankName) {
        this.neftOwDetailsHisPK = neftOwDetailsHisPK;
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

    public NeftOwDetailsHis(String uniqueCustomerRefNo, long sNo) {
        this.neftOwDetailsHisPK = new NeftOwDetailsHisPK(uniqueCustomerRefNo, sNo);
    }

    public NeftOwDetailsHisPK getNeftOwDetailsHisPK() {
        return neftOwDetailsHisPK;
    }

    public void setNeftOwDetailsHisPK(NeftOwDetailsHisPK neftOwDetailsHisPK) {
        this.neftOwDetailsHisPK = neftOwDetailsHisPK;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        hash += (neftOwDetailsHisPK != null ? neftOwDetailsHisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftOwDetailsHis)) {
            return false;
        }
        NeftOwDetailsHis other = (NeftOwDetailsHis) object;
        if ((this.neftOwDetailsHisPK == null && other.neftOwDetailsHisPK != null) || (this.neftOwDetailsHisPK != null && !this.neftOwDetailsHisPK.equals(other.neftOwDetailsHisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftOwDetailsHis[ neftOwDetailsHisPK=" + neftOwDetailsHisPK + " ]";
    }
}
