/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
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

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "eps_n06message")
@NamedQueries({
    @NamedQuery(name = "EPSN06Message.findAll", query = "SELECT e FROM EPSN06Message e"),
    @NamedQuery(name = "EPSN06Message.findByTranType", query = "SELECT e FROM EPSN06Message e WHERE e.tranType = :tranType"),
    @NamedQuery(name = "EPSN06Message.findByPaySysId", query = "SELECT e FROM EPSN06Message e WHERE e.paySysId = :paySysId"),
    @NamedQuery(name = "EPSN06Message.findBySubMsgType", query = "SELECT e FROM EPSN06Message e WHERE e.subMsgType = :subMsgType"),
    @NamedQuery(name = "EPSN06Message.findByUtr", query = "SELECT e FROM EPSN06Message e WHERE e.utr = :utr"),
    @NamedQuery(name = "EPSN06Message.findByTranId", query = "SELECT e FROM EPSN06Message e WHERE e.tranId = :tranId"),
    @NamedQuery(name = "EPSN06Message.findByTranRefNo", query = "SELECT e FROM EPSN06Message e WHERE e.tranRefNo = :tranRefNo"),
    @NamedQuery(name = "EPSN06Message.findByTranDate", query = "SELECT e FROM EPSN06Message e WHERE e.tranDate = :tranDate"),
    @NamedQuery(name = "EPSN06Message.findByMsgStatus", query = "SELECT e FROM EPSN06Message e WHERE e.msgStatus = :msgStatus"),
    @NamedQuery(name = "EPSN06Message.findByRejectReasonCode", query = "SELECT e FROM EPSN06Message e WHERE e.rejectReasonCode = :rejectReasonCode"),
    @NamedQuery(name = "EPSN06Message.findByRejectionReason", query = "SELECT e FROM EPSN06Message e WHERE e.rejectionReason = :rejectionReason"),
    @NamedQuery(name = "EPSN06Message.findByMsgPriority", query = "SELECT e FROM EPSN06Message e WHERE e.msgPriority = :msgPriority"),
    @NamedQuery(name = "EPSN06Message.findByValueDate", query = "SELECT e FROM EPSN06Message e WHERE e.valueDate = :valueDate"),
    @NamedQuery(name = "EPSN06Message.findByDebitAccId", query = "SELECT e FROM EPSN06Message e WHERE e.debitAccId = :debitAccId"),
    @NamedQuery(name = "EPSN06Message.findByCreditAccId", query = "SELECT e FROM EPSN06Message e WHERE e.creditAccId = :creditAccId"),
    @NamedQuery(name = "EPSN06Message.findByTranAmount", query = "SELECT e FROM EPSN06Message e WHERE e.tranAmount = :tranAmount"),
    @NamedQuery(name = "EPSN06Message.findByChargeEventId", query = "SELECT e FROM EPSN06Message e WHERE e.chargeEventId = :chargeEventId"),
    @NamedQuery(name = "EPSN06Message.findByInstrumentType", query = "SELECT e FROM EPSN06Message e WHERE e.instrumentType = :instrumentType"),
    @NamedQuery(name = "EPSN06Message.findByInstrumentBrnCode", query = "SELECT e FROM EPSN06Message e WHERE e.instrumentBrnCode = :instrumentBrnCode"),
    @NamedQuery(name = "EPSN06Message.findByInstrumentNo", query = "SELECT e FROM EPSN06Message e WHERE e.instrumentNo = :instrumentNo"),
    @NamedQuery(name = "EPSN06Message.findByInstrumentDate", query = "SELECT e FROM EPSN06Message e WHERE e.instrumentDate = :instrumentDate"),
    @NamedQuery(name = "EPSN06Message.findByTranParticulars", query = "SELECT e FROM EPSN06Message e WHERE e.tranParticulars = :tranParticulars"),
    @NamedQuery(name = "EPSN06Message.findBySenderBankCode", query = "SELECT e FROM EPSN06Message e WHERE e.senderBankCode = :senderBankCode"),
    @NamedQuery(name = "EPSN06Message.findBySenderBranchCode", query = "SELECT e FROM EPSN06Message e WHERE e.senderBranchCode = :senderBranchCode"),
    @NamedQuery(name = "EPSN06Message.findBySenderIFSCode", query = "SELECT e FROM EPSN06Message e WHERE e.senderIFSCode = :senderIFSCode"),
    @NamedQuery(name = "EPSN06Message.findByReceiverBankCode", query = "SELECT e FROM EPSN06Message e WHERE e.receiverBankCode = :receiverBankCode"),
    @NamedQuery(name = "EPSN06Message.findByReceiverBranchCode", query = "SELECT e FROM EPSN06Message e WHERE e.receiverBranchCode = :receiverBranchCode"),
    @NamedQuery(name = "EPSN06Message.findByReceiverIFSCode", query = "SELECT e FROM EPSN06Message e WHERE e.receiverIFSCode = :receiverIFSCode"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstBnkCode", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstBnkCode = :orderingInstBnkCode"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstBrnCode", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstBrnCode = :orderingInstBrnCode"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstIFSCode", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstIFSCode = :orderingInstIFSCode"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstId", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstId = :orderingInstId"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstName", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstName = :orderingInstName"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstAddress1", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstAddress1 = :orderingInstAddress1"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstAddress2", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstAddress2 = :orderingInstAddress2"),
    @NamedQuery(name = "EPSN06Message.findByOrderingInstAddress3", query = "SELECT e FROM EPSN06Message e WHERE e.orderingInstAddress3 = :orderingInstAddress3"),
    @NamedQuery(name = "EPSN06Message.findByOrderingCustname", query = "SELECT e FROM EPSN06Message e WHERE e.orderingCustname = :orderingCustname"),
    @NamedQuery(name = "EPSN06Message.findByOrderingCustAddress1", query = "SELECT e FROM EPSN06Message e WHERE e.orderingCustAddress1 = :orderingCustAddress1"),
    @NamedQuery(name = "EPSN06Message.findByOrderingCustAddress2", query = "SELECT e FROM EPSN06Message e WHERE e.orderingCustAddress2 = :orderingCustAddress2"),
    @NamedQuery(name = "EPSN06Message.findByOrderingCustAddress3", query = "SELECT e FROM EPSN06Message e WHERE e.orderingCustAddress3 = :orderingCustAddress3"),
    @NamedQuery(name = "EPSN06Message.findByAccInstBnkCode", query = "SELECT e FROM EPSN06Message e WHERE e.accInstBnkCode = :accInstBnkCode"),
    @NamedQuery(name = "EPSN06Message.findByAccInstBrnCode", query = "SELECT e FROM EPSN06Message e WHERE e.accInstBrnCode = :accInstBrnCode"),
    @NamedQuery(name = "EPSN06Message.findByAccInstBrnIFSCode", query = "SELECT e FROM EPSN06Message e WHERE e.accInstBrnIFSCode = :accInstBrnIFSCode"),
    @NamedQuery(name = "EPSN06Message.findByAccInstInsId", query = "SELECT e FROM EPSN06Message e WHERE e.accInstInsId = :accInstInsId"),
    @NamedQuery(name = "EPSN06Message.findByAccInstName", query = "SELECT e FROM EPSN06Message e WHERE e.accInstName = :accInstName"),
    @NamedQuery(name = "EPSN06Message.findByAccInstAddress1", query = "SELECT e FROM EPSN06Message e WHERE e.accInstAddress1 = :accInstAddress1"),
    @NamedQuery(name = "EPSN06Message.findByAccInstAddress2", query = "SELECT e FROM EPSN06Message e WHERE e.accInstAddress2 = :accInstAddress2"),
    @NamedQuery(name = "EPSN06Message.findByAccInstAddress3", query = "SELECT e FROM EPSN06Message e WHERE e.accInstAddress3 = :accInstAddress3"),
    @NamedQuery(name = "EPSN06Message.findByBeneficiaryAccId", query = "SELECT e FROM EPSN06Message e WHERE e.beneficiaryAccId = :beneficiaryAccId"),
    @NamedQuery(name = "EPSN06Message.findByBeneficiaryCustName", query = "SELECT e FROM EPSN06Message e WHERE e.beneficiaryCustName = :beneficiaryCustName"),
    @NamedQuery(name = "EPSN06Message.findByBeneficiaryCustAddress1", query = "SELECT e FROM EPSN06Message e WHERE e.beneficiaryCustAddress1 = :beneficiaryCustAddress1"),
    @NamedQuery(name = "EPSN06Message.findByBeneficiaryCustAddress2", query = "SELECT e FROM EPSN06Message e WHERE e.beneficiaryCustAddress2 = :beneficiaryCustAddress2"),
    @NamedQuery(name = "EPSN06Message.findByBeneficiaryCustAddress3", query = "SELECT e FROM EPSN06Message e WHERE e.beneficiaryCustAddress3 = :beneficiaryCustAddress3"),
    @NamedQuery(name = "EPSN06Message.findByPaymentDetailsAddLine1", query = "SELECT e FROM EPSN06Message e WHERE e.paymentDetailsAddLine1 = :paymentDetailsAddLine1"),
    @NamedQuery(name = "EPSN06Message.findByPaymentDetailsAddLine2", query = "SELECT e FROM EPSN06Message e WHERE e.paymentDetailsAddLine2 = :paymentDetailsAddLine2"),
    @NamedQuery(name = "EPSN06Message.findByPaymentDetailsAddLine3", query = "SELECT e FROM EPSN06Message e WHERE e.paymentDetailsAddLine3 = :paymentDetailsAddLine3"),
    @NamedQuery(name = "EPSN06Message.findByPaymentDetailsAddLine4", query = "SELECT e FROM EPSN06Message e WHERE e.paymentDetailsAddLine4 = :paymentDetailsAddLine4"),
    @NamedQuery(name = "EPSN06Message.findByPaymentChargeDetails", query = "SELECT e FROM EPSN06Message e WHERE e.paymentChargeDetails = :paymentChargeDetails"),
    @NamedQuery(name = "EPSN06Message.findBySenderToRecLine1", query = "SELECT e FROM EPSN06Message e WHERE e.senderToRecLine1 = :senderToRecLine1"),
    @NamedQuery(name = "EPSN06Message.findBySenderToRecLine2", query = "SELECT e FROM EPSN06Message e WHERE e.senderToRecLine2 = :senderToRecLine2"),
    @NamedQuery(name = "EPSN06Message.findBySenderToRecLine3", query = "SELECT e FROM EPSN06Message e WHERE e.senderToRecLine3 = :senderToRecLine3"),
    @NamedQuery(name = "EPSN06Message.findBySenderToRecLine4", query = "SELECT e FROM EPSN06Message e WHERE e.senderToRecLine4 = :senderToRecLine4"),
    @NamedQuery(name = "EPSN06Message.findBySenderToRecLine5", query = "SELECT e FROM EPSN06Message e WHERE e.senderToRecLine5 = :senderToRecLine5"),
    @NamedQuery(name = "EPSN06Message.findBySenderToRecLine6", query = "SELECT e FROM EPSN06Message e WHERE e.senderToRecLine6 = :senderToRecLine6")})
public class EPSN06Message extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "TranType")
    private String tranType;
    @Basic(optional = false)
    @Column(name = "PaySysId")
    private String paySysId;
    @Basic(optional = false)
    @Column(name = "SubMsgType")
    private String subMsgType;
    @Id
    @Basic(optional = false)
    @Column(name = "UTR")
    private String utr;
    @Basic(optional = false)
    @Column(name = "TranId")
    private String tranId;
    @Basic(optional = false)
    @Column(name = "TranRefNo")
    private String tranRefNo;
    @Basic(optional = false)
    @Column(name = "TranDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;
    @Basic(optional = false)
    @Column(name = "MsgStatus")
    private String msgStatus;
    @Column(name = "RejectReasonCode")
    private String rejectReasonCode;
    @Column(name = "RejectionReason")
    private String rejectionReason;
    @Basic(optional = false)
    @Column(name = "MsgPriority")
    private int msgPriority;
    @Basic(optional = false)
    @Column(name = "ValueDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Basic(optional = false)
    @Column(name = "DebitAccId")
    private String debitAccId;
    @Basic(optional = false)
    @Column(name = "CreditAccId")
    private String creditAccId;
    @Basic(optional = false)
    @Column(name = "TranAmount")
    private double tranAmount;
    @Column(name = "ChargeEventId")
    private String chargeEventId;
    @Column(name = "ChargeAccountID")
    private String chargeAccountID;
    @Basic(optional = false)
    @Column(name = "InstrumentType")
    private String instrumentType;
    @Column(name = "InstrumentBrnCode")
    private String instrumentBrnCode;
    @Column(name = "InstrumentNo")
    private String instrumentNo;
    @Basic(optional = false)
    @Column(name = "InstrumentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date instrumentDate;
    @Column(name = "TranParticulars")
    private String tranParticulars;
    @Basic(optional = false)
    @Column(name = "SenderBankCode")
    private String senderBankCode;
    @Basic(optional = false)
    @Column(name = "SenderBranchCode")
    private String senderBranchCode;
    @Basic(optional = false)
    @Column(name = "SenderIFSCode")
    private String senderIFSCode;
    @Basic(optional = false)
    @Column(name = "ReceiverBankCode")
    private String receiverBankCode;
    @Basic(optional = false)
    @Column(name = "ReceiverBranchCode")
    private String receiverBranchCode;
    @Basic(optional = false)
    @Column(name = "ReceiverIFSCode")
    private String receiverIFSCode;
    @Basic(optional = false)
    @Column(name = "OrderingInstBnkCode")
    private String orderingInstBnkCode;
    @Basic(optional = false)
    @Column(name = "OrderingInstBrnCode")
    private String orderingInstBrnCode;
    @Basic(optional = false)
    @Column(name = "OrderingInstIFSCode")
    private String orderingInstIFSCode;
    @Column(name = "OrderingInstId")
    private String orderingInstId;
    @Column(name = "OrderingInstName")
    private String orderingInstName;
    @Column(name = "OrderingInstAddress1")
    private String orderingInstAddress1;
    @Column(name = "OrderingInstAddress2")
    private String orderingInstAddress2;
    @Column(name = "OrderingInstAddress3")
    private String orderingInstAddress3;
    @Basic(optional = false)
    @Column(name = "OrderingCustname")
    private String orderingCustname;
    @Basic(optional = false)
    @Column(name = "OrderingCustAddress1")
    private String orderingCustAddress1;
    @Column(name = "OrderingCustAddress2")
    private String orderingCustAddress2;
    @Column(name = "OrderingCustAddress3")
    private String orderingCustAddress3;
    @Column(name = "AccInstBnkCode")
    private String accInstBnkCode;
    @Column(name = "AccInstBrnCode")
    private String accInstBrnCode;
    @Column(name = "AccInstBrnIFSCode")
    private String accInstBrnIFSCode;
    @Column(name = "AccInstInsId")
    private String accInstInsId;
    @Column(name = "AccInstName")
    private String accInstName;
    @Column(name = "AccInstAddress1")
    private String accInstAddress1;
    @Column(name = "AccInstAddress2")
    private String accInstAddress2;
    @Column(name = "AccInstAddress3")
    private String accInstAddress3;
    @Basic(optional = false)
    @Column(name = "BeneficiaryAccId")
    private String beneficiaryAccId;
    @Basic(optional = false)
    @Column(name = "BeneficiaryCustName")
    private String beneficiaryCustName;
    @Basic(optional = false)
    @Column(name = "BeneficiaryCustAddress1")
    private String beneficiaryCustAddress1;
    @Column(name = "BeneficiaryCustAddress2")
    private String beneficiaryCustAddress2;
    @Column(name = "BeneficiaryCustAddress3")
    private String beneficiaryCustAddress3;
    @Column(name = "PaymentDetailsAddLine1")
    private String paymentDetailsAddLine1;
    @Column(name = "PaymentDetailsAddLine2")
    private String paymentDetailsAddLine2;
    @Column(name = "PaymentDetailsAddLine3")
    private String paymentDetailsAddLine3;
    @Column(name = "PaymentDetailsAddLine4")
    private String paymentDetailsAddLine4;
    @Column(name = "PaymentChargeDetails")
    private String paymentChargeDetails;
    @Column(name = "SenderToRecLine1")
    private String senderToRecLine1;
    @Column(name = "SenderToRecLine2")
    private String senderToRecLine2;
    @Column(name = "SenderToRecLine3")
    private String senderToRecLine3;
    @Column(name = "SenderToRecLine4")
    private String senderToRecLine4;
    @Column(name = "SenderToRecLine5")
    private String senderToRecLine5;
    @Column(name = "SenderToRecLine6")
    private String senderToRecLine6;
    @Column(name = "AddedBy")
    private String addeddBy;
    @Column(name = "ModifiedBy")
    private String modifiedBy;

    public EPSN06Message() {
    }

    public EPSN06Message(String utr) {
        this.utr = utr;
    }

    public EPSN06Message(String utr, String tranType, String paySysId, String subMsgType, String tranId, String tranRefNo, Date tranDate, String msgStatus, int msgPriority, Date valueDate, String debitAccId, String creditAccId, double tranAmount, String instrumentType, Date instrumentDate, String senderBankCode, String senderBranchCode, String senderIFSCode, String receiverBankCode, String receiverBranchCode, String receiverIFSCode, String orderingInstBnkCode, String orderingInstBrnCode, String orderingInstIFSCode, String orderingCustname, String orderingCustAddress1, String beneficiaryAccId, String beneficiaryCustName, String beneficiaryCustAddress1) {
        this.utr = utr;
        this.tranType = tranType;
        this.paySysId = paySysId;
        this.subMsgType = subMsgType;
        this.tranId = tranId;
        this.tranRefNo = tranRefNo;
        this.tranDate = tranDate;
        this.msgStatus = msgStatus;
        this.msgPriority = msgPriority;
        this.valueDate = valueDate;
        this.debitAccId = debitAccId;
        this.creditAccId = creditAccId;
        this.tranAmount = tranAmount;
        this.instrumentType = instrumentType;
        this.instrumentDate = instrumentDate;
        this.senderBankCode = senderBankCode;
        this.senderBranchCode = senderBranchCode;
        this.senderIFSCode = senderIFSCode;
        this.receiverBankCode = receiverBankCode;
        this.receiverBranchCode = receiverBranchCode;
        this.receiverIFSCode = receiverIFSCode;
        this.orderingInstBnkCode = orderingInstBnkCode;
        this.orderingInstBrnCode = orderingInstBrnCode;
        this.orderingInstIFSCode = orderingInstIFSCode;
        this.orderingCustname = orderingCustname;
        this.orderingCustAddress1 = orderingCustAddress1;
        this.beneficiaryAccId = beneficiaryAccId;
        this.beneficiaryCustName = beneficiaryCustName;
        this.beneficiaryCustAddress1 = beneficiaryCustAddress1;
    }

    public EPSN06Message(String tranType, String utr, String paySysId, String subMsgType, String tranId, String tranRefNo, Date tranDate, String msgStatus, String rejectReasonCode, String rejectionReason, int msgPriority, Date valueDate, String debitAccId, String creditAccId, double tranAmount, String chargeEventId, String chargeAccountID, String instrumentType, String instrumentBrnCode, String instrumentNo, Date instrumentDate, String tranParticulars, String senderBankCode, String senderBranchCode, String senderIFSCode, String receiverBankCode, String receiverBranchCode, String receiverIFSCode, String orderingInstBnkCode, String orderingInstBrnCode, String orderingInstIFSCode, String orderingInstId, String orderingInstName, String orderingInstAddress1, String orderingInstAddress2, String orderingInstAddress3, String orderingCustname, String orderingCustAddress1, String orderingCustAddress2, String orderingCustAddress3, String accInstBnkCode, String accInstBrnCode, String accInstBrnIFSCode, String accInstInsId, String accInstName, String accInstAddress1, String accInstAddress2, String accInstAddress3, String beneficiaryCustAddress2, String beneficiaryCustAddress3, String paymentDetailsAddLine1, String paymentDetailsAddLine2, String paymentDetailsAddLine3, String paymentDetailsAddLine4, String paymentChargeDetails, String senderToRecLine1, String senderToRecLine2, String senderToRecLine3, String senderToRecLine4, String senderToRecLine5, String senderToRecLine6, String beneficiaryAccId, String beneficiaryCustName, String beneficiaryCustAddress1, String addedBy, String modifiedBy) {
        this.tranType = tranType;
        this.utr = utr;
        this.paySysId = paySysId;
        this.subMsgType = subMsgType;
        this.tranId = tranId;
        this.tranRefNo = tranRefNo;
        this.tranDate = tranDate;
        this.msgStatus = msgStatus;
        this.rejectReasonCode = rejectReasonCode;
        this.rejectionReason = rejectionReason;
        this.msgPriority = msgPriority;
        this.valueDate = valueDate;
        this.debitAccId = debitAccId;
        this.creditAccId = creditAccId;
        this.tranAmount = tranAmount;
        this.chargeEventId = chargeEventId;
        this.chargeAccountID = chargeAccountID;
        this.instrumentType = instrumentType;
        this.instrumentBrnCode = instrumentBrnCode;
        this.instrumentNo = instrumentNo;
        this.instrumentDate = instrumentDate;
        this.tranParticulars = tranParticulars;
        this.senderBankCode = senderBankCode;
        this.senderBranchCode = senderBranchCode;
        this.senderIFSCode = senderIFSCode;
        this.receiverBankCode = receiverBankCode;
        this.receiverBranchCode = receiverBranchCode;
        this.receiverIFSCode = receiverIFSCode;
        this.orderingInstBnkCode = orderingInstBnkCode;
        this.orderingInstBrnCode = orderingInstBrnCode;
        this.orderingInstIFSCode = orderingInstIFSCode;
        this.orderingInstId = orderingInstId;
        this.orderingInstName = orderingInstName;
        this.orderingInstAddress1 = orderingInstAddress1;
        this.orderingInstAddress2 = orderingInstAddress2;
        this.orderingInstAddress3 = orderingInstAddress3;
        this.orderingCustname = orderingCustname;
        this.orderingCustAddress1 = orderingCustAddress1;
        this.orderingCustAddress2 = orderingCustAddress2;
        this.orderingCustAddress3 = orderingCustAddress3;
        this.accInstBnkCode = accInstBnkCode;
        this.accInstBrnCode = accInstBrnCode;
        this.accInstBrnIFSCode = accInstBrnIFSCode;
        this.accInstInsId = accInstInsId;
        this.accInstName = accInstName;
        this.accInstAddress1 = accInstAddress1;
        this.accInstAddress2 = accInstAddress2;
        this.accInstAddress3 = accInstAddress3;
        this.beneficiaryCustAddress2 = beneficiaryCustAddress2;
        this.beneficiaryCustAddress3 = beneficiaryCustAddress3;
        this.paymentDetailsAddLine1 = paymentDetailsAddLine1;
        this.paymentDetailsAddLine2 = paymentDetailsAddLine2;
        this.paymentDetailsAddLine3 = paymentDetailsAddLine3;
        this.paymentDetailsAddLine4 = paymentDetailsAddLine4;
        this.paymentChargeDetails = paymentChargeDetails;
        this.senderToRecLine1 = senderToRecLine1;
        this.senderToRecLine2 = senderToRecLine2;
        this.senderToRecLine3 = senderToRecLine3;
        this.senderToRecLine4 = senderToRecLine4;
        this.senderToRecLine5 = senderToRecLine5;
        this.senderToRecLine6 = senderToRecLine6;
        this.beneficiaryAccId = beneficiaryAccId;
        this.beneficiaryCustName = beneficiaryCustName;
        this.beneficiaryCustAddress1 = beneficiaryCustAddress1;
        this.addeddBy = addedBy;
        this.modifiedBy = modifiedBy;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public String getSubMsgType() {
        return subMsgType;
    }

    public void setSubMsgType(String subMsgType) {
        this.subMsgType = subMsgType;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getTranRefNo() {
        return tranRefNo;
    }

    public void setTranRefNo(String tranRefNo) {
        this.tranRefNo = tranRefNo;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getRejectReasonCode() {
        return rejectReasonCode;
    }

    public void setRejectReasonCode(String rejectReasonCode) {
        this.rejectReasonCode = rejectReasonCode;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public int getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(int msgPriority) {
        this.msgPriority = msgPriority;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getDebitAccId() {
        return debitAccId;
    }

    public void setDebitAccId(String debitAccId) {
        this.debitAccId = debitAccId;
    }

    public String getCreditAccId() {
        return creditAccId;
    }

    public void setCreditAccId(String creditAccId) {
        this.creditAccId = creditAccId;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getChargeEventId() {
        return chargeEventId;
    }

    public void setChargeEventId(String chargeEventId) {
        this.chargeEventId = chargeEventId;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getInstrumentBrnCode() {
        return instrumentBrnCode;
    }

    public void setInstrumentBrnCode(String instrumentBrnCode) {
        this.instrumentBrnCode = instrumentBrnCode;
    }

    public String getInstrumentNo() {
        return instrumentNo;
    }

    public void setInstrumentNo(String instrumentNo) {
        this.instrumentNo = instrumentNo;
    }

    public Date getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(Date instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public String getTranParticulars() {
        return tranParticulars;
    }

    public void setTranParticulars(String tranParticulars) {
        this.tranParticulars = tranParticulars;
    }

    public String getSenderBankCode() {
        return senderBankCode;
    }

    public void setSenderBankCode(String senderBankCode) {
        this.senderBankCode = senderBankCode;
    }

    public String getSenderBranchCode() {
        return senderBranchCode;
    }

    public void setSenderBranchCode(String senderBranchCode) {
        this.senderBranchCode = senderBranchCode;
    }

    public String getSenderIFSCode() {
        return senderIFSCode;
    }

    public void setSenderIFSCode(String senderIFSCode) {
        this.senderIFSCode = senderIFSCode;
    }

    public String getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }

    public String getReceiverBranchCode() {
        return receiverBranchCode;
    }

    public void setReceiverBranchCode(String receiverBranchCode) {
        this.receiverBranchCode = receiverBranchCode;
    }

    public String getReceiverIFSCode() {
        return receiverIFSCode;
    }

    public void setReceiverIFSCode(String receiverIFSCode) {
        this.receiverIFSCode = receiverIFSCode;
    }

    public String getOrderingInstBnkCode() {
        return orderingInstBnkCode;
    }

    public void setOrderingInstBnkCode(String orderingInstBnkCode) {
        this.orderingInstBnkCode = orderingInstBnkCode;
    }

    public String getOrderingInstBrnCode() {
        return orderingInstBrnCode;
    }

    public void setOrderingInstBrnCode(String orderingInstBrnCode) {
        this.orderingInstBrnCode = orderingInstBrnCode;
    }

    public String getOrderingInstIFSCode() {
        return orderingInstIFSCode;
    }

    public void setOrderingInstIFSCode(String orderingInstIFSCode) {
        this.orderingInstIFSCode = orderingInstIFSCode;
    }

    public String getOrderingInstId() {
        return orderingInstId;
    }

    public void setOrderingInstId(String orderingInstId) {
        this.orderingInstId = orderingInstId;
    }

    public String getOrderingInstName() {
        return orderingInstName;
    }

    public void setOrderingInstName(String orderingInstName) {
        this.orderingInstName = orderingInstName;
    }

    public String getOrderingInstAddress1() {
        return orderingInstAddress1;
    }

    public void setOrderingInstAddress1(String orderingInstAddress1) {
        this.orderingInstAddress1 = orderingInstAddress1;
    }

    public String getOrderingInstAddress2() {
        return orderingInstAddress2;
    }

    public void setOrderingInstAddress2(String orderingInstAddress2) {
        this.orderingInstAddress2 = orderingInstAddress2;
    }

    public String getOrderingInstAddress3() {
        return orderingInstAddress3;
    }

    public void setOrderingInstAddress3(String orderingInstAddress3) {
        this.orderingInstAddress3 = orderingInstAddress3;
    }

    public String getOrderingCustname() {
        return orderingCustname;
    }

    public void setOrderingCustname(String orderingCustname) {
        this.orderingCustname = orderingCustname;
    }

    public String getOrderingCustAddress1() {
        return orderingCustAddress1;
    }

    public void setOrderingCustAddress1(String orderingCustAddress1) {
        this.orderingCustAddress1 = orderingCustAddress1;
    }

    public String getOrderingCustAddress2() {
        return orderingCustAddress2;
    }

    public void setOrderingCustAddress2(String orderingCustAddress2) {
        this.orderingCustAddress2 = orderingCustAddress2;
    }

    public String getOrderingCustAddress3() {
        return orderingCustAddress3;
    }

    public void setOrderingCustAddress3(String orderingCustAddress3) {
        this.orderingCustAddress3 = orderingCustAddress3;
    }

    public String getAccInstBnkCode() {
        return accInstBnkCode;
    }

    public void setAccInstBnkCode(String accInstBnkCode) {
        this.accInstBnkCode = accInstBnkCode;
    }

    public String getAccInstBrnCode() {
        return accInstBrnCode;
    }

    public void setAccInstBrnCode(String accInstBrnCode) {
        this.accInstBrnCode = accInstBrnCode;
    }

    public String getAccInstBrnIFSCode() {
        return accInstBrnIFSCode;
    }

    public void setAccInstBrnIFSCode(String accInstBrnIFSCode) {
        this.accInstBrnIFSCode = accInstBrnIFSCode;
    }

    public String getAccInstInsId() {
        return accInstInsId;
    }

    public void setAccInstInsId(String accInstInsId) {
        this.accInstInsId = accInstInsId;
    }

    public String getAccInstName() {
        return accInstName;
    }

    public void setAccInstName(String accInstName) {
        this.accInstName = accInstName;
    }

    public String getAccInstAddress1() {
        return accInstAddress1;
    }

    public void setAccInstAddress1(String accInstAddress1) {
        this.accInstAddress1 = accInstAddress1;
    }

    public String getAccInstAddress2() {
        return accInstAddress2;
    }

    public void setAccInstAddress2(String accInstAddress2) {
        this.accInstAddress2 = accInstAddress2;
    }

    public String getAccInstAddress3() {
        return accInstAddress3;
    }

    public void setAccInstAddress3(String accInstAddress3) {
        this.accInstAddress3 = accInstAddress3;
    }

    public String getBeneficiaryAccId() {
        return beneficiaryAccId;
    }

    public void setBeneficiaryAccId(String beneficiaryAccId) {
        this.beneficiaryAccId = beneficiaryAccId;
    }

    public String getBeneficiaryCustName() {
        return beneficiaryCustName;
    }

    public void setBeneficiaryCustName(String beneficiaryCustName) {
        this.beneficiaryCustName = beneficiaryCustName;
    }

    public String getBeneficiaryCustAddress1() {
        return beneficiaryCustAddress1;
    }

    public void setBeneficiaryCustAddress1(String beneficiaryCustAddress1) {
        this.beneficiaryCustAddress1 = beneficiaryCustAddress1;
    }

    public String getBeneficiaryCustAddress2() {
        return beneficiaryCustAddress2;
    }

    public void setBeneficiaryCustAddress2(String beneficiaryCustAddress2) {
        this.beneficiaryCustAddress2 = beneficiaryCustAddress2;
    }

    public String getBeneficiaryCustAddress3() {
        return beneficiaryCustAddress3;
    }

    public void setBeneficiaryCustAddress3(String beneficiaryCustAddress3) {
        this.beneficiaryCustAddress3 = beneficiaryCustAddress3;
    }

    public String getPaymentDetailsAddLine1() {
        return paymentDetailsAddLine1;
    }

    public void setPaymentDetailsAddLine1(String paymentDetailsAddLine1) {
        this.paymentDetailsAddLine1 = paymentDetailsAddLine1;
    }

    public String getPaymentDetailsAddLine2() {
        return paymentDetailsAddLine2;
    }

    public void setPaymentDetailsAddLine2(String paymentDetailsAddLine2) {
        this.paymentDetailsAddLine2 = paymentDetailsAddLine2;
    }

    public String getPaymentDetailsAddLine3() {
        return paymentDetailsAddLine3;
    }

    public void setPaymentDetailsAddLine3(String paymentDetailsAddLine3) {
        this.paymentDetailsAddLine3 = paymentDetailsAddLine3;
    }

    public String getPaymentDetailsAddLine4() {
        return paymentDetailsAddLine4;
    }

    public void setPaymentDetailsAddLine4(String paymentDetailsAddLine4) {
        this.paymentDetailsAddLine4 = paymentDetailsAddLine4;
    }

    public String getPaymentChargeDetails() {
        return paymentChargeDetails;
    }

    public void setPaymentChargeDetails(String paymentChargeDetails) {
        this.paymentChargeDetails = paymentChargeDetails;
    }

    public String getSenderToRecLine1() {
        return senderToRecLine1;
    }

    public void setSenderToRecLine1(String senderToRecLine1) {
        this.senderToRecLine1 = senderToRecLine1;
    }

    public String getSenderToRecLine2() {
        return senderToRecLine2;
    }

    public void setSenderToRecLine2(String senderToRecLine2) {
        this.senderToRecLine2 = senderToRecLine2;
    }

    public String getSenderToRecLine3() {
        return senderToRecLine3;
    }

    public void setSenderToRecLine3(String senderToRecLine3) {
        this.senderToRecLine3 = senderToRecLine3;
    }

    public String getSenderToRecLine4() {
        return senderToRecLine4;
    }

    public void setSenderToRecLine4(String senderToRecLine4) {
        this.senderToRecLine4 = senderToRecLine4;
    }

    public String getSenderToRecLine5() {
        return senderToRecLine5;
    }

    public void setSenderToRecLine5(String senderToRecLine5) {
        this.senderToRecLine5 = senderToRecLine5;
    }

    public String getSenderToRecLine6() {
        return senderToRecLine6;
    }

    public void setSenderToRecLine6(String senderToRecLine6) {
        this.senderToRecLine6 = senderToRecLine6;
    }

    public String getChargeAccountID() {
        return chargeAccountID;
    }

    public void setChargeAccountID(String chargeAccountID) {
        this.chargeAccountID = chargeAccountID;
    }

    public String getAddeddBy() {
        return addeddBy;
    }

    public void setAddeddBy(String addeddBy) {
        this.addeddBy = addeddBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        if (!(object instanceof EPSN06Message)) {
            return false;
        }
        EPSN06Message other = (EPSN06Message) object;
        if ((this.utr == null && other.utr != null) || (this.utr != null && !this.utr.equals(other.utr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.neftrtgs.EPSN06Message[utr=" + utr + "]";
    }
}
