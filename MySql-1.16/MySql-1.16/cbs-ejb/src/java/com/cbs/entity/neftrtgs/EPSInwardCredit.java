/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "eps_inwardcredit")
@NamedQueries({
    @NamedQuery(name = "EPSInwardCredit.findAll", query = "SELECT e FROM EPSInwardCredit e"),
    @NamedQuery(name = "EPSInwardCredit.findByBlockABeginIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.blockABeginIdentifier = :blockABeginIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findBySenderBankAppIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.senderBankAppIdentifier = :senderBankAppIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByMsgIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.msgIdentifier = :msgIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByIOIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.iOIdentifier = :iOIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByMsgType", query = "SELECT e FROM EPSInwardCredit e WHERE e.msgType = :msgType"),
    @NamedQuery(name = "EPSInwardCredit.findBySubMsgType", query = "SELECT e FROM EPSInwardCredit e WHERE e.subMsgType = :subMsgType"),
    @NamedQuery(name = "EPSInwardCredit.findBySenderIFSC", query = "SELECT e FROM EPSInwardCredit e WHERE e.senderIFSC = :senderIFSC"),
    @NamedQuery(name = "EPSInwardCredit.findByReceiverIFSC", query = "SELECT e FROM EPSInwardCredit e WHERE e.receiverIFSC = :receiverIFSC"),
    @NamedQuery(name = "EPSInwardCredit.findByDeliveryNotFlag", query = "SELECT e FROM EPSInwardCredit e WHERE e.deliveryNotFlag = :deliveryNotFlag"),
    @NamedQuery(name = "EPSInwardCredit.findByOpenNotFlag", query = "SELECT e FROM EPSInwardCredit e WHERE e.openNotFlag = :openNotFlag"),
    @NamedQuery(name = "EPSInwardCredit.findByNonDeliveryWarnFlag", query = "SELECT e FROM EPSInwardCredit e WHERE e.nonDeliveryWarnFlag = :nonDeliveryWarnFlag"),
    @NamedQuery(name = "EPSInwardCredit.findByObsolescencePeriod", query = "SELECT e FROM EPSInwardCredit e WHERE e.obsolescencePeriod = :obsolescencePeriod"),
    @NamedQuery(name = "EPSInwardCredit.findByMur", query = "SELECT e FROM EPSInwardCredit e WHERE e.mur = :mur"),
    @NamedQuery(name = "EPSInwardCredit.findByPossDuplEmiFlag", query = "SELECT e FROM EPSInwardCredit e WHERE e.possDuplEmiFlag = :possDuplEmiFlag"),
    @NamedQuery(name = "EPSInwardCredit.findByServiceIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.serviceIdentifier = :serviceIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByOrgDate", query = "SELECT e FROM EPSInwardCredit e WHERE e.orgDate = :orgDate"),
    @NamedQuery(name = "EPSInwardCredit.findByOrgTime", query = "SELECT e FROM EPSInwardCredit e WHERE e.orgTime = :orgTime"),
    @NamedQuery(name = "EPSInwardCredit.findByTestTrainFlag", query = "SELECT e FROM EPSInwardCredit e WHERE e.testTrainFlag = :testTrainFlag"),
    @NamedQuery(name = "EPSInwardCredit.findBySequenceNo", query = "SELECT e FROM EPSInwardCredit e WHERE e.sequenceNo = :sequenceNo"),
    @NamedQuery(name = "EPSInwardCredit.findByFiller", query = "SELECT e FROM EPSInwardCredit e WHERE e.filler = :filler"),
    @NamedQuery(name = "EPSInwardCredit.findByUtr", query = "SELECT e FROM EPSInwardCredit e WHERE e.utr = :utr"),
    @NamedQuery(name = "EPSInwardCredit.findByPriorityFlag", query = "SELECT e FROM EPSInwardCredit e WHERE e.priorityFlag = :priorityFlag"),
    @NamedQuery(name = "EPSInwardCredit.findByBlockAEndIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.blockAEndIdentifier = :blockAEndIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByBlock4BeginIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.block4BeginIdentifier = :block4BeginIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByTranRefNo", query = "SELECT e FROM EPSInwardCredit e WHERE e.tranRefNo = :tranRefNo"),
    @NamedQuery(name = "EPSInwardCredit.findByValueDate", query = "SELECT e FROM EPSInwardCredit e WHERE e.valueDate = :valueDate"),
    @NamedQuery(name = "EPSInwardCredit.findByCurrency", query = "SELECT e FROM EPSInwardCredit e WHERE e.currency = :currency"),
    @NamedQuery(name = "EPSInwardCredit.findByAmount", query = "SELECT e FROM EPSInwardCredit e WHERE e.amount = :amount"),
    @NamedQuery(name = "EPSInwardCredit.findByOrderingCustomer", query = "SELECT e FROM EPSInwardCredit e WHERE e.orderingCustomer = :orderingCustomer"),
    @NamedQuery(name = "EPSInwardCredit.findByBeneficiaryAccount", query = "SELECT e FROM EPSInwardCredit e WHERE e.beneficiaryAccount = :beneficiaryAccount"),
    @NamedQuery(name = "EPSInwardCredit.findByBeneficiaryCustomer", query = "SELECT e FROM EPSInwardCredit e WHERE e.beneficiaryCustomer = :beneficiaryCustomer"),
    @NamedQuery(name = "EPSInwardCredit.findByBlock4EndIdentifier", query = "SELECT e FROM EPSInwardCredit e WHERE e.block4EndIdentifier = :block4EndIdentifier"),
    @NamedQuery(name = "EPSInwardCredit.findByMsgStatus", query = "SELECT e FROM EPSInwardCredit e WHERE e.msgStatus = :msgStatus"),
    @NamedQuery(name = "EPSInwardCredit.findByReasonCode", query = "SELECT e FROM EPSInwardCredit e WHERE e.reasonCode = :reasonCode"),
    @NamedQuery(name = "EPSInwardCredit.findByRejectReason", query = "SELECT e FROM EPSInwardCredit e WHERE e.rejectReason = :rejectReason"),
    @NamedQuery(name = "EPSInwardCredit.findByPaySysId", query = "SELECT e FROM EPSInwardCredit e WHERE e.paySysId = :paySysId")})
public class EPSInwardCredit extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "BlockABeginIdentifier")
    private String blockABeginIdentifier;
    @Basic(optional = false)
    @Column(name = "SenderBankAppIdentifier")
    private String senderBankAppIdentifier;
    @Basic(optional = false)
    @Column(name = "MsgIdentifier")
    private String msgIdentifier;
    @Basic(optional = false)
    @Column(name = "IOIdentifier")
    private String iOIdentifier;
    @Basic(optional = false)
    @Column(name = "MsgType")
    private String msgType;
    @Basic(optional = false)
    @Column(name = "SubMsgType")
    private String subMsgType;
    @Basic(optional = false)
    @Column(name = "SenderIFSC")
    private String senderIFSC;
    @Basic(optional = false)
    @Column(name = "ReceiverIFSC")
    private String receiverIFSC;
    @Basic(optional = false)
    @Column(name = "DeliveryNotFlag")
    private String deliveryNotFlag;
    @Basic(optional = false)
    @Column(name = "OpenNotFlag")
    private String openNotFlag;
    @Basic(optional = false)
    @Column(name = "NonDeliveryWarnFlag")
    private String nonDeliveryWarnFlag;
    @Basic(optional = false)
    @Column(name = "ObsolescencePeriod")
    private String obsolescencePeriod;
    @Basic(optional = false)
    @Column(name = "MUR")
    private String mur;
    @Basic(optional = false)
    @Column(name = "PossDuplEmiFlag")
    private String possDuplEmiFlag;
    @Basic(optional = false)
    @Column(name = "ServiceIdentifier")
    private String serviceIdentifier;
    @Basic(optional = false)
    @Column(name = "OrgDate")
    private String orgDate;
    @Basic(optional = false)
    @Column(name = "OrgTime")
    private String orgTime;
    @Basic(optional = false)
    @Column(name = "TestTrainFlag")
    private String testTrainFlag;
    @Basic(optional = false)
    @Column(name = "SequenceNo")
    private String sequenceNo;
    @Basic(optional = false)
    @Column(name = "Filler")
    private String filler;
    @Id
    @Basic(optional = false)
    @Column(name = "UTR")
    private String utr;
    @Basic(optional = false)
    @Column(name = "PriorityFlag")
    private String priorityFlag;
    @Basic(optional = false)
    @Column(name = "BlockAEndIdentifier")
    private String blockAEndIdentifier;
    @Basic(optional = false)
    @Column(name = "Block4BeginIdentifier")
    private String block4BeginIdentifier;
    @Basic(optional = false)
    @Column(name = "TranRefNo")
    private String tranRefNo;
    @Basic(optional = false)
    @Column(name = "ValueDate")
    private String valueDate;
    @Basic(optional = false)
    @Column(name = "Currency")
    private String currency;
    @Basic(optional = false)
    @Column(name = "Amount")
    private double amount;
    @Column(name = "OrderingCustomer")
    private String orderingCustomer;
    @Basic(optional = false)
    @Column(name = "BeneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "BeneficiaryCustomer")
    private String beneficiaryCustomer;
    @Basic(optional = false)
    @Column(name = "Block4EndIdentifier")
    private String block4EndIdentifier;
    @Basic(optional = false)
    @Column(name = "MsgStatus")
    private String msgStatus;
    @Column(name = "ReasonCode")
    private String reasonCode;
    @Column(name = "RejectReason")
    private String rejectReason;
    @Basic(optional = false)
    @Column(name = "PaySysId")
    private String paySysId;

    public EPSInwardCredit() {
    }

    public EPSInwardCredit(String utr) {
        this.utr = utr;
    }

    public EPSInwardCredit(String utr, String blockABeginIdentifier, String senderBankAppIdentifier, String msgIdentifier, String iOIdentifier, String msgType, String subMsgType, String senderIFSC, String receiverIFSC, String deliveryNotFlag, String openNotFlag, String nonDeliveryWarnFlag, String obsolescencePeriod, String mur, String possDuplEmiFlag, String serviceIdentifier, String orgDate, String orgTime, String testTrainFlag, String sequenceNo, String filler, String priorityFlag, String blockAEndIdentifier, String block4BeginIdentifier, String tranRefNo, String valueDate, String currency, double amount, String beneficiaryAccount, String block4EndIdentifier, String msgStatus, String paySysId) {
        this.utr = utr;
        this.blockABeginIdentifier = blockABeginIdentifier;
        this.senderBankAppIdentifier = senderBankAppIdentifier;
        this.msgIdentifier = msgIdentifier;
        this.iOIdentifier = iOIdentifier;
        this.msgType = msgType;
        this.subMsgType = subMsgType;
        this.senderIFSC = senderIFSC;
        this.receiverIFSC = receiverIFSC;
        this.deliveryNotFlag = deliveryNotFlag;
        this.openNotFlag = openNotFlag;
        this.nonDeliveryWarnFlag = nonDeliveryWarnFlag;
        this.obsolescencePeriod = obsolescencePeriod;
        this.mur = mur;
        this.possDuplEmiFlag = possDuplEmiFlag;
        this.serviceIdentifier = serviceIdentifier;
        this.orgDate = orgDate;
        this.orgTime = orgTime;
        this.testTrainFlag = testTrainFlag;
        this.sequenceNo = sequenceNo;
        this.filler = filler;
        this.priorityFlag = priorityFlag;
        this.blockAEndIdentifier = blockAEndIdentifier;
        this.block4BeginIdentifier = block4BeginIdentifier;
        this.tranRefNo = tranRefNo;
        this.valueDate = valueDate;
        this.currency = currency;
        this.amount = amount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.block4EndIdentifier = block4EndIdentifier;
        this.msgStatus = msgStatus;
        this.paySysId = paySysId;
    }

    public String getBlockABeginIdentifier() {
        return blockABeginIdentifier;
    }

    public void setBlockABeginIdentifier(String blockABeginIdentifier) {
        this.blockABeginIdentifier = blockABeginIdentifier;
    }

    public String getSenderBankAppIdentifier() {
        return senderBankAppIdentifier;
    }

    public void setSenderBankAppIdentifier(String senderBankAppIdentifier) {
        this.senderBankAppIdentifier = senderBankAppIdentifier;
    }

    public String getMsgIdentifier() {
        return msgIdentifier;
    }

    public void setMsgIdentifier(String msgIdentifier) {
        this.msgIdentifier = msgIdentifier;
    }

    public String getIOIdentifier() {
        return iOIdentifier;
    }

    public void setIOIdentifier(String iOIdentifier) {
        this.iOIdentifier = iOIdentifier;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSubMsgType() {
        return subMsgType;
    }

    public void setSubMsgType(String subMsgType) {
        this.subMsgType = subMsgType;
    }

    public String getSenderIFSC() {
        return senderIFSC;
    }

    public void setSenderIFSC(String senderIFSC) {
        this.senderIFSC = senderIFSC;
    }

    public String getReceiverIFSC() {
        return receiverIFSC;
    }

    public void setReceiverIFSC(String receiverIFSC) {
        this.receiverIFSC = receiverIFSC;
    }

    public String getDeliveryNotFlag() {
        return deliveryNotFlag;
    }

    public void setDeliveryNotFlag(String deliveryNotFlag) {
        this.deliveryNotFlag = deliveryNotFlag;
    }

    public String getOpenNotFlag() {
        return openNotFlag;
    }

    public void setOpenNotFlag(String openNotFlag) {
        this.openNotFlag = openNotFlag;
    }

    public String getNonDeliveryWarnFlag() {
        return nonDeliveryWarnFlag;
    }

    public void setNonDeliveryWarnFlag(String nonDeliveryWarnFlag) {
        this.nonDeliveryWarnFlag = nonDeliveryWarnFlag;
    }

    public String getObsolescencePeriod() {
        return obsolescencePeriod;
    }

    public void setObsolescencePeriod(String obsolescencePeriod) {
        this.obsolescencePeriod = obsolescencePeriod;
    }

    public String getMur() {
        return mur;
    }

    public void setMur(String mur) {
        this.mur = mur;
    }

    public String getPossDuplEmiFlag() {
        return possDuplEmiFlag;
    }

    public void setPossDuplEmiFlag(String possDuplEmiFlag) {
        this.possDuplEmiFlag = possDuplEmiFlag;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getOrgDate() {
        return orgDate;
    }

    public void setOrgDate(String orgDate) {
        this.orgDate = orgDate;
    }

    public String getOrgTime() {
        return orgTime;
    }

    public void setOrgTime(String orgTime) {
        this.orgTime = orgTime;
    }

    public String getTestTrainFlag() {
        return testTrainFlag;
    }

    public void setTestTrainFlag(String testTrainFlag) {
        this.testTrainFlag = testTrainFlag;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(String priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public String getBlockAEndIdentifier() {
        return blockAEndIdentifier;
    }

    public void setBlockAEndIdentifier(String blockAEndIdentifier) {
        this.blockAEndIdentifier = blockAEndIdentifier;
    }

    public String getBlock4BeginIdentifier() {
        return block4BeginIdentifier;
    }

    public void setBlock4BeginIdentifier(String block4BeginIdentifier) {
        this.block4BeginIdentifier = block4BeginIdentifier;
    }

    public String getTranRefNo() {
        return tranRefNo;
    }

    public void setTranRefNo(String tranRefNo) {
        this.tranRefNo = tranRefNo;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(String orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public void setBeneficiaryCustomer(String beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public String getBlock4EndIdentifier() {
        return block4EndIdentifier;
    }

    public void setBlock4EndIdentifier(String block4EndIdentifier) {
        this.block4EndIdentifier = block4EndIdentifier;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utr != null ? utr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPSInwardCredit)) {
            return false;
        }
        EPSInwardCredit other = (EPSInwardCredit) object;
        if ((this.utr == null && other.utr != null) || (this.utr != null && !this.utr.equals(other.utr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSInwardCredit[utr=" + utr + "]";
    }
}
