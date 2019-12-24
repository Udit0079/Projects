/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.web.pojo.neftrtgs.Block4Items;
import com.cbs.pojo.neftrtgs.sbi.BlockAItems;
import com.cbs.entity.neftrtgs.EPSN06Message;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Administrator
 */
public class RTGSCriteria extends BaseBean {

    Context ctx;
    EPSN06Message utrData;
    private boolean jmsFlag;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String message;
    private List<SelectItem> functionList;
    private List<SelectItem> tranTypeList;
    private List<SelectItem> paySystemIdList;
    private Date transDt;
    private String paySysId;
    private List<SelectItem> messageTypeOption;
    private String branchId;
    private String function;
    private boolean functionValue;
    private String msgType;
    private String utrNumber;
    private String tranId;
    private String payTrnRefNo;
    public String tranType;
    public String criteriaRenderValue;
    private Date valueDate;
    private String drAccId;
    private String transAmount;
    private String setTransDt;
    private String msgStatus;
    private String rejectReasCode;
    private String rejectReason;
    private String drCustName;
    private String crAccId;
    private String crCustName;
    private String instrumentType;
    private Date instDate;
    private List<SelectItem> instTypeList;
    private boolean instrValue;
    private String tranAmt1;
    private String chargeEventID;
    private List<SelectItem> chargeEventIDOption;
    private String chargeAccountID;
    private String chargeAccCustName;
    private String instrNo;
    private String tranParticular;
    private String receiverBankCode;
    private String receiverBranchCode;
    private String receiverIfsCode;
    private String orderingBankCode;
    private String orderingBranchCode;
    private String orderingIfsCode;
    private String senderCorrBnkCode;
    private String senderCorrBranchCode;
    private String senderCorrIfsCode;
    private String rejectReasonCode;
    private String orderingInstId;
    private String orderingInstName;
    private String orderingInstAddress1;
    private String orderingInstAddress2;
    private String orderingInstAddress3;
    private String orderingCustName;
    private String orderingCustAddress1;
    private String orderingCustAddress2;
    private String orderingCustAddress3;
    private String accInstBnkCode;
    private String accInstBrnCode;
    private String accInstBrnIFSCode;
    private String accInstInsId;
    private String accInstName;
    private String accInstAddress1;
    private String accInstAddress2;
    private String accInstAddress3;
    private String beneficiaryCustAddress2;
    private String beneficiaryCustAddress3;
    private String paymentDetailsAddLine1;
    private String paymentDetailsAddLine2;
    private String paymentDetailsAddLine3;
    private String paymentDetailsAddLine4;
    private String paymentChargeDetails;
    private String senderToRecLine1;
    private String senderToRecLine2;
    private String senderToRecLine3;
    private String senderToRecLine4;
    private String senderToRecLine5;
    private String senderToRecLine6;
    private String beneficiaryAccId;
    private String beneficiaryCustName;
    private String beneficiaryCustAddress1;
    private int msgpriority;
    private String sendFunction;
    public String sendTranType;
    private String sendBranchId;
    private String sendPaySysId;
    private String sendMsgType;
    private String sendUtrNumber;
    private String sendTranId;
    private String SendPayTrnRefNo;
    private boolean buttonFlag;
    private boolean drAccIDEnable;
    private String currentDate;
    private String loggedInUser;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public boolean isJmsFlag() {
        return jmsFlag;
    }

    public void setJmsFlag(boolean jmsFlag) {
        this.jmsFlag = jmsFlag;
    }

    public boolean isDrAccIDEnable() {
        return drAccIDEnable;
    }

    public void setDrAccIDEnable(boolean drAccIDEnable) {
        this.drAccIDEnable = drAccIDEnable;
    }

    public boolean isButtonFlag() {
        return buttonFlag;
    }

    public void setButtonFlag(boolean buttonFlag) {
        this.buttonFlag = buttonFlag;
    }

    public String getSendPayTrnRefNo() {
        return SendPayTrnRefNo;
    }

    public void setSendPayTrnRefNo(String SendPayTrnRefNo) {
        this.SendPayTrnRefNo = SendPayTrnRefNo;
    }

    public String getSendBranchId() {
        return sendBranchId;
    }

    public void setSendBranchId(String sendBranchId) {
        this.sendBranchId = sendBranchId;
    }

    public String getSendFunction() {
        return sendFunction;
    }

    public void setSendFunction(String sendFunction) {
        this.sendFunction = sendFunction;
    }

    public String getSendMsgType() {
        return sendMsgType;
    }

    public void setSendMsgType(String sendMsgType) {
        this.sendMsgType = sendMsgType;
    }

    public String getSendPaySysId() {
        return sendPaySysId;
    }

    public void setSendPaySysId(String sendPaySysId) {
        this.sendPaySysId = sendPaySysId;
    }

    public String getSendTranId() {
        return sendTranId;
    }

    public void setSendTranId(String sendTranId) {
        this.sendTranId = sendTranId;
    }

    public String getSendTranType() {
        return sendTranType;
    }

    public void setSendTranType(String sendTranType) {
        this.sendTranType = sendTranType;
    }

    public String getSendUtrNumber() {
        return sendUtrNumber;
    }

    public void setSendUtrNumber(String sendUtrNumber) {
        this.sendUtrNumber = sendUtrNumber;
    }

    public String getTranAmt1() {
        return tranAmt1;
    }

    public void setTranAmt1(String tranAmt1) {
        this.tranAmt1 = tranAmt1;
    }

    public int getMsgpriority() {
        return msgpriority;
    }

    public void setMsgpriority(int msgpriority) {
        this.msgpriority = msgpriority;
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

    public String getBeneficiaryAccId() {
        return beneficiaryAccId;
    }

    public void setBeneficiaryAccId(String beneficiaryAccId) {
        this.beneficiaryAccId = beneficiaryAccId;
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

    public String getBeneficiaryCustName() {
        return beneficiaryCustName;
    }

    public void setBeneficiaryCustName(String beneficiaryCustName) {
        this.beneficiaryCustName = beneficiaryCustName;
    }

    public String getOrderingBankCode() {
        return orderingBankCode;
    }

    public void setOrderingBankCode(String orderingBankCode) {
        this.orderingBankCode = orderingBankCode;
    }

    public String getOrderingBranchCode() {
        return orderingBranchCode;
    }

    public void setOrderingBranchCode(String orderingBranchCode) {
        this.orderingBranchCode = orderingBranchCode;
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

    public String getOrderingCustName() {
        return orderingCustName;
    }

    public void setOrderingCustName(String orderingCustName) {
        this.orderingCustName = orderingCustName;
    }

    public String getOrderingIfsCode() {
        return orderingIfsCode;
    }

    public void setOrderingIfsCode(String orderingIfsCode) {
        this.orderingIfsCode = orderingIfsCode;
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

    public String getPaymentChargeDetails() {
        return paymentChargeDetails;
    }

    public void setPaymentChargeDetails(String paymentChargeDetails) {
        this.paymentChargeDetails = paymentChargeDetails;
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

    public String getReceiverIfsCode() {
        return receiverIfsCode;
    }

    public void setReceiverIfsCode(String receiverIfsCode) {
        this.receiverIfsCode = receiverIfsCode;
    }

    public String getRejectReasonCode() {
        return rejectReasonCode;
    }

    public void setRejectReasonCode(String rejectReasonCode) {
        this.rejectReasonCode = rejectReasonCode;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getSenderCorrBnkCode() {
        return senderCorrBnkCode;
    }

    public void setSenderCorrBnkCode(String senderCorrBnkCode) {
        this.senderCorrBnkCode = senderCorrBnkCode;
    }

    public String getSenderCorrBranchCode() {
        return senderCorrBranchCode;
    }

    public void setSenderCorrBranchCode(String senderCorrBranchCode) {
        this.senderCorrBranchCode = senderCorrBranchCode;
    }

    public String getSenderCorrIfsCode() {
        return senderCorrIfsCode;
    }

    public void setSenderCorrIfsCode(String senderCorrIfsCode) {
        this.senderCorrIfsCode = senderCorrIfsCode;
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

    public String getChargeAccCustName() {
        return chargeAccCustName;
    }

    public void setChargeAccCustName(String chargeAccCustName) {
        this.chargeAccCustName = chargeAccCustName;
    }

    public String getChargeAccountID() {
        return chargeAccountID;
    }

    public void setChargeAccountID(String chargeAccountID) {
        this.chargeAccountID = chargeAccountID;
    }

    public String getChargeEventID() {
        return chargeEventID;
    }

    public void setChargeEventID(String chargeEventID) {
        this.chargeEventID = chargeEventID;
    }

    public List<SelectItem> getChargeEventIDOption() {
        return chargeEventIDOption;
    }

    public void setChargeEventIDOption(List<SelectItem> chargeEventIDOption) {
        this.chargeEventIDOption = chargeEventIDOption;
    }

    public String getCrAccId() {
        return crAccId;
    }

    public void setCrAccId(String crAccId) {
        this.crAccId = crAccId;
    }

    public String getCrCustName() {
        return crCustName;
    }

    public void setCrCustName(String crCustName) {
        this.crCustName = crCustName;
    }

    public String getDrCustName() {
        return drCustName;
    }

    public void setDrCustName(String drCustName) {
        this.drCustName = drCustName;
    }

    public Date getInstDate() {
        return instDate;
    }

    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    public List<SelectItem> getInstTypeList() {
        return instTypeList;
    }

    public void setInstTypeList(List<SelectItem> instTypeList) {
        this.instTypeList = instTypeList;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public boolean isInstrValue() {
        return instrValue;
    }

    public void setInstrValue(boolean instrValue) {
        this.instrValue = instrValue;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getRejectReasCode() {
        return rejectReasCode;
    }

    public void setRejectReasCode(String rejectReasCode) {
        this.rejectReasCode = rejectReasCode;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getTranParticular() {
        return tranParticular;
    }

    public void setTranParticular(String tranParticular) {
        this.tranParticular = tranParticular;
    }

    public String getSetTransDt() {
        return setTransDt;
    }

    public void setSetTransDt(String setTransDt) {
        this.setTransDt = setTransDt;
    }

    public String getDrAccId() {
        return drAccId;
    }

    public void setDrAccId(String drAccId) {
        this.drAccId = drAccId;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getCriteriaRenderValue() {
        return criteriaRenderValue;
    }

    public void setCriteriaRenderValue(String criteriaRenderValue) {
        this.criteriaRenderValue = criteriaRenderValue;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getPayTrnRefNo() {
        return payTrnRefNo;
    }

    public void setPayTrnRefNo(String payTrnRefNo) {
        this.payTrnRefNo = payTrnRefNo;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getUtrNumber() {
        return utrNumber;
    }

    public void setUtrNumber(String utrNumber) {
        this.utrNumber = utrNumber;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public boolean isFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(boolean functionValue) {
        this.functionValue = functionValue;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public List<SelectItem> getMessageTypeOption() {
        return messageTypeOption;
    }

    public void setMessageTypeOption(List<SelectItem> messageTypeOption) {
        this.messageTypeOption = messageTypeOption;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public Date getTransDt() {
        return transDt;
    }

    public void setTransDt(Date transDt) {
        this.transDt = transDt;
    }

    public List<SelectItem> getPaySystemIdList() {
        return paySystemIdList;
    }

    public void setPaySystemIdList(List<SelectItem> paySystemIdList) {
        this.paySystemIdList = paySystemIdList;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /** Creates a new instance of RTGSCriteria */
    public RTGSCriteria() {
        try {
            Date date = new Date();
            this.setTransDt(date);
            this.setValueDate(date);
            this.setInstDate(date);

            this.setCurrentDate(getTodayDate());
            this.setLoggedInUser(getUserName());
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("Add", "Add"));
            functionList.add(new SelectItem("Modify", "Modify"));
            functionList.add(new SelectItem("Verify", "Verify"));
            functionList.add(new SelectItem("Cancel", "Cancel"));
            functionList.add(new SelectItem("Inquire", "Inquire"));

            tranTypeList = new ArrayList<SelectItem>();
            tranTypeList.add(new SelectItem("0", "--SELECT--"));
            tranTypeList.add(new SelectItem("Cash", "Cash"));
            tranTypeList.add(new SelectItem("Transfer", "Transfer"));
            tranType = "0";
            paySystemIdList = new ArrayList<SelectItem>();
            paySystemIdList.add(new SelectItem("0", "--SELECT--"));
            paySystemIdList.add(new SelectItem("NEFT", "NEFT"));
            paySystemIdList.add(new SelectItem("RTGS", "RTGS"));
            paySysId = "0";

            instTypeList = new ArrayList<SelectItem>();
            instTypeList.add(new SelectItem("VCH", "VCH"));
            instTypeList.add(new SelectItem("CHQ", "CHQ"));

            messageTypeOption = new ArrayList<SelectItem>();
            functionValue = true;
            function = "Add";
            criteriaRenderValue = "true";
            this.setTranAmt1("INR");
            instrValue = true;
            buttonFlag = false;
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getAlphaCode();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    /***********Start Criteria Detail Methods****************************/
    public void getAlphaCode() {
        try {
            String alphaCods = remoteInterface.getAlphaCode(getOrgnBrCode());
            if (alphaCods.equalsIgnoreCase("")) {
                this.setMessage("Branch Id Does Not Exist");
                return;
            } else {
                this.setBranchId(alphaCods);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getGeneralDetails() {
        this.setMessage("");
        try {
            if (tranType.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Tran. Type");
                return "Fail";
            } else if (paySysId.equals("0")) {
                this.setMessage("Please Select PaySys Id");
                return "Fail";
            } else if ((msgType == null) || (msgType.equalsIgnoreCase(""))) {
                this.setMessage("Please Select Message Type");
                return "Fail";
            } else if (transDt == null) {
                this.setMessage("Please Enter Tran. Date");
                return "Fail";
            } else if ((branchId == null) || (branchId.equalsIgnoreCase(""))) {
                this.setMessage("Branch Id Does Not Exist");
                return "Fail";
            } else if (tranType.equalsIgnoreCase("Cash") && paySysId.equalsIgnoreCase("RTGS")) {
                this.setMessage("Cash Type is only allowd for NEFT");
                return "Fail";
            } else {
                drAccIDEnable = false;
                if (this.tranType.equalsIgnoreCase("Cash")) {
                    List<String> cashInfoList = remoteInterface.getCashInHandAccInfo(paySysId, getOrgnBrCode());
                    if (cashInfoList.size() > 0) {
                        this.setDrAccId(cashInfoList.get(0));
                        this.setDrCustName(cashInfoList.get(1));
                        this.setCrAccId(cashInfoList.get(2));
                        this.setOrderingCustName(cashInfoList.get(1));
                    }
                    drAccIDEnable = true;
                }

                this.setSendFunction(function);
                this.setSendMsgType(msgType);
                this.setSetTransDt((sdf.format(transDt)));
                this.setSendUtrNumber(utrNumber);
                this.setSendBranchId(branchId);
                this.setSendPaySysId(paySysId);
                this.setSendTranType(tranType);
                this.setSendTranId(tranId);
                criteriaRenderValue = "false";
                if (paySysId.equalsIgnoreCase("NEFT")) {
                    msgpriority = 99;
                } else if (paySysId.equalsIgnoreCase("RTGS")) {
                    msgpriority = 21;
                }
                getChargeEventIdInfo();
            }
            return "Success";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return "Fail";
        }
    }

    public void getMessageTypeInfo() {
        messageTypeOption = new ArrayList<SelectItem>();
        this.setMessage("");
        try {
            List<String> msgTypes = remoteInterface.getMessageType(paySysId);
            if (msgTypes.size() > 0) {
                int n = msgTypes.size();
                for (int i = 0; i < n; i++) {
                    messageTypeOption.add(new SelectItem(msgTypes.get(i)));
                }
            } else {
                this.setMessage("Message Type Does Not Exist");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getFunctionDetails() {
        try {
            if (function.equalsIgnoreCase("Add")) {
                functionValue = true;
                buttonFlag = false;
                this.setUtrNumber("");
                this.setPayTrnRefNo("");
                getRefreshCriteriaGeneralAllDetal();
            } else if (function.equalsIgnoreCase("Inquire")) {
                buttonFlag = true;
                functionValue = false;
                this.setUtrNumber("");
                this.setPayTrnRefNo("");
            } else {
                functionValue = false;
                buttonFlag = false;
                this.setUtrNumber("");
                this.setPayTrnRefNo("");
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getUTRNumber() {
        try {
            if ((paySysId == null) || (paySysId.equalsIgnoreCase("")) || (paySysId.equalsIgnoreCase("0"))) {
                this.setMessage("Please Select PaySys Id");
                return;
            }
            if ((msgType == null) || (msgType.equalsIgnoreCase(""))) {
                this.setMessage("Message type does not exist");
                return;
            }
            if (function.equalsIgnoreCase("Add")) {
                String utrNumbers = remoteInterface.getUTRNumber(paySysId);
                if (!utrNumbers.contains(":")) {
                    this.setMessage(utrNumbers);
                    return;
                } else {
                    String[] values = null;
                    String spliter = ":";
                    values = utrNumbers.split(spliter);
                    String utrNo = values[0];
                    String tranRefNo = values[1];
                    this.setUtrNumber(utrNo);
                    this.setPayTrnRefNo(tranRefNo);
                }
            } else {
                this.setUtrNumber("");
                this.setPayTrnRefNo("");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getRefreshCriteria() {
        this.setMessage("");
        try {
            this.setFunction("Add");
            this.setTranType("0");
            this.setPaySysId("0");
            this.setMsgType("");
            this.setUtrNumber("");
            this.setTranId("");
            this.setPayTrnRefNo("");
            Date date = new Date();
            this.setTransDt(date);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAllDetails() {
        this.setMessage("");
        try {
            if (msgpriority == 0) {
                this.setMessage("Please Enter Message Priority");
                return;
            }
            if (valueDate == null) {
                this.setMessage("Please Enter Value Date");
                return;
            }
            if ((drAccId == null) || (drAccId.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Dr. A/C ID");
                return;
            }
            if ((transAmount == null) || (transAmount.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Trans Amount.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(transAmount);
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Trans Amount.");
                return;
            }
            if (Double.parseDouble(transAmount) < 0) {
                this.setMessage("Please Enter Valid Trans Amount.");
                return;
            }
            if ((transAmount == null) || (transAmount.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Tran Amount");
                return;
            }
            criteriaRenderValue = "allValue";
            getOrderingInstDetails();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getRTGSCriteriaDetails() {
        this.setMessage("");
        try {
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return criteriaRenderValue = "true";
    }

    public void getChargeEventIdInfo() {
        chargeEventIDOption = new ArrayList<SelectItem>();
        this.setMessage("");
        try {
            List<String> chargeTypes = remoteInterface.getChargeEvent(paySysId);
            chargeEventIDOption.add(new SelectItem("0", "--SELECT--"));
            if (chargeTypes.size() > 0) {
                for (int i = 0; i < chargeTypes.size(); i++) {
                    chargeEventIDOption.add(new SelectItem(chargeTypes.get(i)));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void instrumentType() {
        this.setMessage("");
        try {
            if (instrumentType.equalsIgnoreCase("CHQ")) {
                instrValue = false;
            } else {
                instrValue = true;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getDrAccountInformation() {
        this.setMessage("");
        try {
            if (drAccId.length() != 12) {
                this.setMessage("Please Enter 12 Digit Account Number");
                return;
            }
            if ((paySysId == null) || (paySysId.equalsIgnoreCase(""))) {
                this.setMessage("Paysys ID Does Not Exists");
                return;
            }
            String drAccDetails = remoteInterface.getDrAccInformation(drAccId, paySysId);
            if (!drAccDetails.contains(":")) {
                this.setMessage(drAccDetails);
                this.setDrCustName("");
                this.setCrAccId("");
                this.setCrCustName("");
            } else {
                String[] values = null;
                String spliter = ":";
                values = drAccDetails.split(spliter);
                String drCustNames = values[0];
                String crAccountId = values[1];
                String crAccCustName = values[2];
                this.setDrCustName(drCustNames);
                this.setCrAccId(crAccountId);
                this.setCrCustName(crAccCustName);
                this.setOrderingCustName(drCustNames);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getChargeAccountInDetails() {
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(transAmount);
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Trans Amount.");
                return;
            }
            if (Double.parseDouble(transAmount) < 0.0) {
                this.setMessage("Please Enter Valid Trans Amount.");
                return;
            }
            if ((paySysId == null) || (paySysId.equalsIgnoreCase(""))) {
                this.setMessage("Paysys ID Does Not Exists");
                return;
            }

            if (!chargeEventID.equalsIgnoreCase("0")) {
                if ((this.chargeAccountID.equalsIgnoreCase("")) || (this.chargeAccountID == null)) {
                    this.setMessage("Please enter the Charge Account ID");
                    return;
                }
                if (chargeAccountID.length() != 12) {
                    this.setMessage("Please Enter 12 Digit Account Number");
                    return;
                }
            }

            String drAccDetails = remoteInterface.getDrAccInformation(chargeAccountID, paySysId);
            if (!drAccDetails.contains(":")) {
                this.setMessage(drAccDetails);
                this.setChargeAccCustName("");
            } else {
                String[] values = null;
                String spliter = ":";
                values = drAccDetails.split(spliter);
                String drCustNames = values[0];
                this.setChargeAccCustName(drCustNames);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getRefreshGeneralDetails() {
        this.setMessage("");
        try {
            this.setDrAccId("");
            this.setDrCustName("");
            this.setCrCustName("");
            this.setTransAmount("");
            this.setChargeAccountID("");
            this.setChargeAccCustName("");
            this.setInstrNo("");
            this.setTranParticular("");
            Date date = new Date();
            this.setValueDate(date);
            this.setInstDate(date);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getGeneralFromAllDetail() {
        this.setMessage("");
        try {
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return criteriaRenderValue = "false";
    }

    public void getReceiverIfsCodes() {
        try {
            String ifsCode = remoteInterface.getReceiverDetails(receiverBankCode, receiverBranchCode);
            if (ifsCode.equalsIgnoreCase("")) {
                this.setReceiverIfsCode("");
                this.setMessage("IFS Code Does Not Exists");
                return;
            } else {
                this.setReceiverIfsCode(ifsCode);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getOrderingInstDetails() {
        try {
            String ordering = remoteInterface.getOrderingDetails(getOrgnBrCode());
            if (!ordering.contains(":")) {
                setOrderingBankCode("");
                setOrderingBranchCode("");
                setOrderingIfsCode("");
                this.setMessage(ordering);
                return;
            } else {
                String[] values = null;
                String spliter = ":";
                values = ordering.split(spliter);
                setOrderingBankCode(values[0]);
                setOrderingBranchCode(values[1]);
                setOrderingIfsCode(values[2]);
                setSenderCorrBnkCode(values[3]);
                setSenderCorrBranchCode(values[4]);
                setSenderCorrIfsCode(values[5]);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveButton() {
        try {
            if ((receiverBankCode == null) || (receiverBankCode.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Receiver Bank Code");
                return;
            }
            if ((receiverBranchCode == null) || (receiverBranchCode.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Receiver Branch Code");
                return;
            }
            if ((receiverIfsCode == null) || (receiverIfsCode.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Receiver Ifs Code");
                return;
            }
            if ((orderingCustName == null) || (orderingCustName.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Ordering Customer Name");
                return;
            }
            if ((orderingCustAddress1 == null) || (orderingCustAddress1.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Ordering Customer Address1");
                return;
            }
            if ((beneficiaryAccId == null) || (beneficiaryAccId.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter beneficiary Account Id");
                return;
            }
            if ((beneficiaryCustName == null) || (beneficiaryCustName.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter beneficiary Customer Name");
                return;
            }
            if ((beneficiaryCustAddress1 == null) || (beneficiaryCustAddress1.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter beneficiary Customer Address1");
                return;
            }
            jmsFlag = false;
            if (function.equalsIgnoreCase("Add")) {
                this.tranId = "";
                this.msgStatus = "Entered";
                if (this.instrumentType.equalsIgnoreCase("VCH")) {
                    this.instrNo = "";
                }
                EPSN06Message saveObj = new EPSN06Message(this.tranType, this.utrNumber, this.paySysId, this.msgType, this.tranId, this.payTrnRefNo, this.transDt, this.msgStatus, this.rejectReasonCode, this.rejectReason, this.msgpriority, this.valueDate, this.drAccId, this.crAccId, Double.parseDouble(this.transAmount), this.chargeEventID, this.chargeAccountID, this.instrumentType, "",
                        this.instrNo, this.instDate, this.tranParticular, this.senderCorrBnkCode, this.senderCorrBranchCode, this.senderCorrIfsCode, this.receiverBankCode, this.receiverBranchCode, this.receiverIfsCode, this.orderingBankCode, this.orderingBranchCode, this.orderingIfsCode, this.orderingInstId, this.orderingInstName, this.orderingInstAddress1, this.orderingInstAddress2, this.orderingInstAddress3,
                        this.orderingCustName, this.orderingCustAddress1, this.orderingCustAddress2, this.orderingCustAddress3, this.accInstBnkCode, this.accInstBrnCode, this.accInstBrnIFSCode, this.accInstInsId, this.accInstName, this.accInstAddress1, this.accInstAddress2, this.accInstAddress3, this.beneficiaryCustAddress2, this.beneficiaryCustAddress3, this.paymentDetailsAddLine1,
                        this.paymentDetailsAddLine2, this.paymentDetailsAddLine3, this.paymentDetailsAddLine4, this.paymentChargeDetails, this.senderToRecLine1, this.senderToRecLine2, this.senderToRecLine3, this.senderToRecLine4, this.senderToRecLine5, this.senderToRecLine6, this.beneficiaryAccId, this.beneficiaryCustName, this.beneficiaryCustAddress1, getUserName(), "");
                String rsmsg = remoteInterface.saveButton(saveObj);
                if (rsmsg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been successfuly saved");
                    getRefreshCriteriaGeneralAllDetal();
                } else {
                    this.setMessage("Data does not saved ");
                }
            } else if (function.equalsIgnoreCase("Verify")) {
                if (this.utrData.getAddeddBy().equalsIgnoreCase(getUserName())) {
                    this.setMessage("You are not authorized person");
                    return;
                }
                if (this.utrData.getMsgStatus().equalsIgnoreCase("Verified")) {
                    this.setMessage("It has been already verified");
                    return;
                }
                this.msgStatus = "Processed";
                if (this.instrumentType.equalsIgnoreCase("VCH")) {
                    this.instrNo = "";
                }
                
                tranId = getUniqueSeqNo();
                if ((tranId.equalsIgnoreCase("")) || (tranId == null)) {
                    this.setMessage("Problem in Sequence No generation");
                    return;
                }

                EPSN06Message updateObj = new EPSN06Message(this.tranType, this.utrNumber, this.paySysId, this.msgType, this.tranId, this.payTrnRefNo, this.transDt, this.msgStatus, this.rejectReasonCode, this.rejectReason, this.msgpriority, this.valueDate, this.drAccId, this.crAccId, Double.parseDouble(this.transAmount), this.chargeEventID, this.chargeAccountID, this.instrumentType, "",
                        this.instrNo, this.instDate, this.tranParticular, this.senderCorrBnkCode, this.senderCorrBranchCode, this.senderCorrIfsCode, this.receiverBankCode, this.receiverBranchCode, this.receiverIfsCode, this.orderingBankCode, this.orderingBranchCode, this.orderingIfsCode, this.orderingInstId, this.orderingInstName, this.orderingInstAddress1, this.orderingInstAddress2, this.orderingInstAddress3,
                        this.orderingCustName, this.orderingCustAddress1, this.orderingCustAddress2, this.orderingCustAddress3, this.accInstBnkCode, this.accInstBrnCode, this.accInstBrnIFSCode, this.accInstInsId, this.accInstName, this.accInstAddress1, this.accInstAddress2, this.accInstAddress3, this.beneficiaryCustAddress2, this.beneficiaryCustAddress3, this.paymentDetailsAddLine1,
                        this.paymentDetailsAddLine2, this.paymentDetailsAddLine3, this.paymentDetailsAddLine4, this.paymentChargeDetails, this.senderToRecLine1, this.senderToRecLine2, this.senderToRecLine3, this.senderToRecLine4, this.senderToRecLine5, this.senderToRecLine6, this.beneficiaryAccId, this.beneficiaryCustName, this.beneficiaryCustAddress1, utrData.getAddeddBy(), getUserName());
                String updateMsg = remoteInterface.updateAllInformation(updateObj);
                if (updateMsg.equalsIgnoreCase("true")) {

                    /****Code added by Dinesh to send message to queue****/
                    /****Code to create BlockA***************************/
                    BlockAItems blockA = new BlockAItems();

                    blockA.setBlockAIdentifier("{A:");
                    blockA.setBnkAppIdentifier("CBS");                    //It will change according to the Bank
                    blockA.setMsgIdentifier("F01");
                    blockA.setiOIdentifier("O");
                    blockA.setMsgType("298");

                    blockA.setSubMsgType(this.msgType);
                    blockA.setSenderIFSC(this.senderCorrIfsCode);
                    blockA.setReceiverIFSC(this.receiverIfsCode);
                    blockA.setObsolescencePeriod("000");                        //It depends upon DeliveryNotificationFlag, if that is 1, otherwise this field is ignored.

                    blockA.setMsgUserReference("XXXXXXXXXXXXXXXX");
                    blockA.setPossibleDuplicateFlag(2);
                    if (this.paySysId.equalsIgnoreCase("NEFT")) {
                        blockA.setDeliveryMonitoringFlag(1);
                        blockA.setOpenNotificationFlag(2);
                        blockA.setNonDeliveryWarningFlag(2);
                        blockA.setServiceIdentifier("EFT");
                        blockA.setTestingAndTrainingFlag(2);                    //For NEFT it must be always 2
                    } else {
                        blockA.setDeliveryMonitoringFlag(1);                  //It can be changed for RTGS
                        blockA.setOpenNotificationFlag(2);                      //It can be changed for RTGS
                        blockA.setNonDeliveryWarningFlag(2);
                        blockA.setServiceIdentifier("XXX");
                        blockA.setTestingAndTrainingFlag(2);                    //It can be changed for RTGS
                    }

                    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                    blockA.setOriginatingDate(ymd.format(new Date()));

                    String hour = "", minute = "";
                    Calendar cal = Calendar.getInstance();

                    Integer h = (Integer) cal.get(Calendar.HOUR);
                    if (h.toString().length() == 1) {
                        hour = "0" + h.toString();
                    } else {
                        hour = h.toString();
                    }

                    Integer m = (Integer) cal.get(Calendar.MINUTE);
                    if (m.toString().length() == 1) {
                        minute = "0" + m.toString();
                    } else {
                        minute = m.toString();
                    }

                    blockA.setOriginatingTime(hour + minute);
                    blockA.setSequenceNumber(this.tranId);
                    blockA.setFiller("XXXXXXXXX");

                    blockA.setUniqueTransactionReference(this.utrNumber);
                    blockA.setRtgsPriority(this.msgpriority);
                    blockA.setBlockAEndIdentifier("}");

                    /****Addition end************************************/
                    /****Code to create Block4***************************/
                    Block4Items block4 = new Block4Items();

                    block4.setBlockBeginIdentifier("{4:");
                    block4.setTransactionReferenceNo(":20:" + this.payTrnRefNo);
                    SimpleDateFormat ymdf = new SimpleDateFormat("ddMMyyyy");

                    block4.setValueDateCurrencyAndAmount(":32A:" + ymdf.format(new Date()) + "INR" + this.transAmount);

                    block4.setOrderingCustomer(":50:" + this.orderingCustName);
                    block4.setBeneficiaryCustomer(":59:/" + this.beneficiaryAccId + " " + this.beneficiaryCustName);
                    //block4.setRemittanceInformation(":70:" + this.tranParticular);
                    block4.setBlockEndIdentifier("-}");

                    /****Addition end************************************/
                    String n06Msg = blockA.toString() + block4.toString();
                    try {
                        ctx = new InitialContext();
                        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("jms/QCFactory");
                        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
                        QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                        Queue queue = (Queue) ctx.lookup("jms/outboundQueue");
                        QueueSender queueSender = queueSession.createSender(queue);

                        TextMessage textMessage = queueSession.createTextMessage(n06Msg);
                        System.out.println("Sending following messages: " + textMessage.getText());
                        queueSender.send(textMessage);
                        jmsFlag = true;
                        queueSender.close();
                        queueSession.close();
                        queueConnection.close();
                    } catch (Exception ex) {
                        this.setMessage(ex.getMessage());
                    }

                    if (jmsFlag) {
                        String transMsg = remoteInterface.insertTransaction(utrData.getAddeddBy(), getUserName(), this.drAccId, this.crAccId, Double.valueOf(this.transAmount.trim()).doubleValue(), this.tranParticular, this.instrumentType, this.instrNo, this.transDt, this.chargeEventID, this.chargeAccountID);
                        if (transMsg.equalsIgnoreCase("TRUE")) {
                            this.setMessage("Data has been Verified successfuly");
                            getRefreshCriteriaGeneralAllDetal();
                        } else {
                            this.setMessage("There is problem in transaction");
                        }
                    }
                    /**********************Addition end*****************************/
                } else {
                    this.setMessage(updateMsg);
                }

            } else if (function.equalsIgnoreCase("Cancel")) {
                if (this.utrData.getMsgStatus().equalsIgnoreCase("Cancel")) {
                    this.setMessage("It has been already Canceled");
                    return;
                }
                this.msgStatus = "Cancel";
                if (this.instrumentType.equalsIgnoreCase("VCH")) {
                    this.instrNo = "";
                }
                EPSN06Message updateObj = new EPSN06Message(this.tranType, this.utrNumber, this.paySysId, this.msgType, this.tranId, this.payTrnRefNo, this.transDt, this.msgStatus, this.rejectReasonCode, this.rejectReason, this.msgpriority, this.valueDate, this.drAccId, this.crAccId, Double.parseDouble(this.transAmount), this.chargeEventID, this.chargeAccountID, this.instrumentType, "",
                        this.instrNo, this.instDate, this.tranParticular, this.senderCorrBnkCode, this.senderCorrBranchCode, this.senderCorrIfsCode, this.receiverBankCode, this.receiverBranchCode, this.receiverIfsCode, this.orderingBankCode, this.orderingBranchCode, this.orderingIfsCode, this.orderingInstId, this.orderingInstName, this.orderingInstAddress1, this.orderingInstAddress2, this.orderingInstAddress3,
                        this.orderingCustName, this.orderingCustAddress1, this.orderingCustAddress2, this.orderingCustAddress3, this.accInstBnkCode, this.accInstBrnCode, this.accInstBrnIFSCode, this.accInstInsId, this.accInstName, this.accInstAddress1, this.accInstAddress2, this.accInstAddress3, this.beneficiaryCustAddress2, this.beneficiaryCustAddress3, this.paymentDetailsAddLine1,
                        this.paymentDetailsAddLine2, this.paymentDetailsAddLine3, this.paymentDetailsAddLine4, this.paymentChargeDetails, this.senderToRecLine1, this.senderToRecLine2, this.senderToRecLine3, this.senderToRecLine4, this.senderToRecLine5, this.senderToRecLine6, this.beneficiaryAccId, this.beneficiaryCustName, this.beneficiaryCustAddress1, utrData.getAddeddBy(), getUserName());
                String updateMsg = remoteInterface.updateAllInformation(updateObj);
                if (updateMsg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been Cancel successfuly");
                    getRefreshCriteriaGeneralAllDetal();
                } else {
                    this.setMessage(updateMsg);
                }
            } else if (function.equalsIgnoreCase("Modify")) {
                if (this.instrumentType.equalsIgnoreCase("VCH")) {
                    this.instrNo = "";
                }
                EPSN06Message updateObj = new EPSN06Message(this.tranType, this.utrNumber, this.paySysId, this.msgType, this.tranId, this.payTrnRefNo, this.transDt, this.msgStatus, this.rejectReasonCode, this.rejectReason, this.msgpriority, this.valueDate, this.drAccId, this.crAccId, Double.parseDouble(this.transAmount), this.chargeEventID, this.chargeAccountID, this.instrumentType, "",
                        this.instrNo, this.instDate, this.tranParticular, this.senderCorrBnkCode, this.senderCorrBranchCode, this.senderCorrIfsCode, this.receiverBankCode, this.receiverBranchCode, this.receiverIfsCode, this.orderingBankCode, this.orderingBranchCode, this.orderingIfsCode, this.orderingInstId, this.orderingInstName, this.orderingInstAddress1, this.orderingInstAddress2, this.orderingInstAddress3,
                        this.orderingCustName, this.orderingCustAddress1, this.orderingCustAddress2, this.orderingCustAddress3, this.accInstBnkCode, this.accInstBrnCode, this.accInstBrnIFSCode, this.accInstInsId, this.accInstName, this.accInstAddress1, this.accInstAddress2, this.accInstAddress3, this.beneficiaryCustAddress2, this.beneficiaryCustAddress3, this.paymentDetailsAddLine1,
                        this.paymentDetailsAddLine2, this.paymentDetailsAddLine3, this.paymentDetailsAddLine4, this.paymentChargeDetails, this.senderToRecLine1, this.senderToRecLine2, this.senderToRecLine3, this.senderToRecLine4, this.senderToRecLine5, this.senderToRecLine6, this.beneficiaryAccId, this.beneficiaryCustName, this.beneficiaryCustAddress1, utrData.getAddeddBy(), getUserName());
                String updateMsg = remoteInterface.updateAllInformation(updateObj);
                if (updateMsg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been Modify successfuly");
                } else {
                    this.setMessage(updateMsg);
                }
                getRefreshCriteriaGeneralAllDetal();
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getRefreshAllDetails() {
        this.setMessage("");
        try {
            this.setReceiverBankCode("");
            this.setReceiverBranchCode("");
            this.setReceiverIfsCode("");
            this.setOrderingInstId("");
            this.setOrderingInstName("");
            this.setOrderingInstAddress1("");
            this.setOrderingInstAddress2("");
            this.setOrderingInstAddress3("");
            this.setOrderingCustName("");
            this.setOrderingCustAddress1("");
            this.setOrderingCustAddress2("");
            this.setOrderingCustAddress3("");
            this.setAccInstBnkCode("");
            this.setAccInstBrnCode("");
            this.setAccInstBrnIFSCode("");
            this.setAccInstInsId("");
            this.setAccInstName("");
            this.setAccInstAddress1("");
            this.setAccInstAddress2("");
            this.setAccInstAddress3("");
            this.setBeneficiaryAccId("");
            this.setBeneficiaryCustName("");
            this.setBeneficiaryCustAddress1("");
            this.setBeneficiaryCustAddress2("");
            this.setBeneficiaryCustAddress3("");
            this.setPaymentDetailsAddLine1("");
            this.setPaymentDetailsAddLine2("");
            this.setPaymentDetailsAddLine3("");
            this.setPaymentDetailsAddLine4("");
            this.setPaymentChargeDetails("");
            this.setSenderToRecLine1("");
            this.setSenderToRecLine2("");
            this.setSenderToRecLine3("");
            this.setSenderToRecLine4("");
            this.setSenderToRecLine5("");
            this.setSenderToRecLine6("");

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    /********************End All Detail Methods************************/
    public void getRefreshCriteriaGeneralAllDetal() {
        try {
            this.setFunction("Add");
            this.setTranType("0");
            this.setPaySysId("0");
            this.setMsgType("");
            this.setUtrNumber("");
            this.setTranId("");
            this.setPayTrnRefNo("");
            this.setSendFunction("");
            this.setSendMsgType("");
            this.setSendUtrNumber("");
            this.setSendBranchId("");
            this.setSendPaySysId("");
            this.setSendTranType("");
            this.setSendTranId("");
            this.setDrAccId("");
            this.setDrCustName("");
            this.setCrCustName("");
            this.setTransAmount("");
            this.setChargeAccountID("");
            this.setChargeAccCustName("");
            this.setInstrNo("");
            this.setTranParticular("");
            this.setSetTransDt("");
            this.setReceiverBankCode("");
            this.setReceiverBranchCode("");
            this.setReceiverIfsCode("");
            this.setOrderingInstId("");
            this.setOrderingInstName("");
            this.setOrderingInstAddress1("");
            this.setOrderingInstAddress2("");
            this.setOrderingInstAddress3("");
            this.setOrderingCustName("");
            this.setOrderingCustAddress1("");
            this.setOrderingCustAddress2("");
            this.setOrderingCustAddress3("");
            this.setAccInstBnkCode("");
            this.setAccInstBrnCode("");
            this.setAccInstBrnIFSCode("");
            this.setAccInstInsId("");
            this.setAccInstName("");
            this.setAccInstAddress1("");
            this.setAccInstAddress2("");
            this.setAccInstAddress3("");
            this.setBeneficiaryAccId("");
            this.setBeneficiaryCustName("");
            this.setBeneficiaryCustAddress1("");
            this.setBeneficiaryCustAddress2("");
            this.setBeneficiaryCustAddress3("");
            this.setPaymentDetailsAddLine1("");
            this.setPaymentDetailsAddLine2("");
            this.setPaymentDetailsAddLine3("");
            this.setPaymentDetailsAddLine4("");
            this.setPaymentChargeDetails("");
            this.setSenderToRecLine1("");
            this.setSenderToRecLine2("");
            this.setSenderToRecLine3("");
            this.setSenderToRecLine4("");
            this.setSenderToRecLine5("");
            this.setSenderToRecLine6("");
            Date date = new Date();
            this.setValueDate(date);
            this.setInstDate(date);
            this.setTransDt(date);
            this.setMsgStatus("");
            this.setRejectReasonCode("");
            this.setRejectReason("");
            this.setCrAccId("");
            this.setMsgpriority(0);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurUtrNo() {
        this.setMessage("");
        try {
            if (utrNumber.equalsIgnoreCase("")) {
                this.setMessage("Please enter utr number");
                return;
            }
            if ((function.equalsIgnoreCase("Verify")) || (function.equalsIgnoreCase("Modify")) || (function.equalsIgnoreCase("Cancel")) || (function.equalsIgnoreCase("Inquire"))) {
                utrData = remoteInterface.onBlurUtrNumber(utrNumber);
                if (utrData != null) {
                    this.setTranType(utrData.getTranType());
                    this.setPaySysId(utrData.getPaySysId());
                    this.setMsgType(utrData.getSubMsgType());
                    messageTypeOption.add(new SelectItem(utrData.getSubMsgType()));
                    this.setTranId(utrData.getTranId());
                    this.setPayTrnRefNo(utrData.getTranRefNo());
                    this.setTransDt(utrData.getTranDate());
                    this.setMsgStatus(utrData.getMsgStatus());
                    this.setRejectReasCode(utrData.getRejectReasonCode());
                    this.setRejectReason(utrData.getRejectionReason());
                    this.setMsgpriority(utrData.getMsgPriority());
                    this.setValueDate(utrData.getValueDate());
                    this.setDrAccId(utrData.getDebitAccId());
                    this.setCrAccId(utrData.getCreditAccId());
                    this.setTransAmount(String.valueOf(utrData.getTranAmount()));
                    this.setChargeEventID(utrData.getChargeEventId());
                    this.setChargeAccountID(utrData.getChargeAccountID());
                    this.setInstrumentType(utrData.getInstrumentType());
                    this.setInstrNo(utrData.getInstrumentNo());
                    this.setInstDate(utrData.getInstrumentDate());
                    this.setTranParticular(utrData.getTranParticulars());
                    this.setSenderCorrBnkCode(utrData.getSenderBankCode());
                    this.setSenderCorrBranchCode(utrData.getSenderBranchCode());
                    this.setSenderCorrIfsCode(utrData.getSenderIFSCode());
                    this.setReceiverBankCode(utrData.getReceiverBankCode());
                    this.setReceiverBranchCode(utrData.getReceiverBranchCode());
                    this.setReceiverIfsCode(utrData.getReceiverIFSCode());
                    this.setOrderingBankCode(utrData.getOrderingInstBnkCode());
                    this.setOrderingBranchCode(utrData.getOrderingInstBrnCode());
                    this.setOrderingIfsCode(utrData.getOrderingInstIFSCode());
                    this.setOrderingInstId(utrData.getOrderingInstId());
                    this.setOrderingInstName(utrData.getOrderingInstName());
                    this.setOrderingInstAddress1(utrData.getOrderingInstAddress1());
                    this.setOrderingInstAddress2(utrData.getOrderingInstAddress2());
                    this.setOrderingInstAddress3(utrData.getOrderingInstAddress3());
                    this.setOrderingCustName(utrData.getOrderingCustname());
                    this.setOrderingCustAddress1(utrData.getOrderingCustAddress1());
                    this.setOrderingCustAddress2(utrData.getOrderingCustAddress2());
                    this.setOrderingCustAddress3(utrData.getOrderingCustAddress3());
                    this.setAccInstBnkCode(utrData.getAccInstBnkCode());
                    this.setAccInstBrnCode(utrData.getAccInstBrnCode());
                    this.setAccInstBrnIFSCode(utrData.getAccInstBrnIFSCode());
                    this.setAccInstInsId(utrData.getAccInstInsId());
                    this.setAccInstName(utrData.getAccInstName());
                    this.setAccInstAddress1(utrData.getAccInstAddress1());
                    this.setAccInstAddress2(utrData.getAccInstAddress2());
                    this.setAccInstAddress3(utrData.getAccInstAddress3());
                    this.setBeneficiaryAccId(utrData.getBeneficiaryAccId());
                    this.setBeneficiaryCustName(utrData.getBeneficiaryCustName());
                    this.setBeneficiaryCustAddress1(utrData.getBeneficiaryCustAddress1());
                    this.setBeneficiaryCustAddress2(utrData.getBeneficiaryCustAddress2());
                    this.setBeneficiaryCustAddress3(utrData.getBeneficiaryCustAddress3());
                    this.setPaymentDetailsAddLine1(utrData.getPaymentDetailsAddLine1());
                    this.setPaymentDetailsAddLine2(utrData.getPaymentDetailsAddLine2());
                    this.setPaymentDetailsAddLine3(utrData.getPaymentDetailsAddLine3());
                    this.setPaymentDetailsAddLine4(utrData.getPaymentDetailsAddLine4());
                    this.setPaymentChargeDetails(utrData.getPaymentChargeDetails());
                    this.setSenderToRecLine1(utrData.getSenderToRecLine1());
                    this.setSenderToRecLine2(utrData.getSenderToRecLine2());
                    this.setSenderToRecLine3(utrData.getSenderToRecLine3());
                    this.setSenderToRecLine4(utrData.getSenderToRecLine4());
                    this.setSenderToRecLine5(utrData.getSenderToRecLine5());
                    this.setSenderToRecLine6(utrData.getSenderToRecLine6());
                } else {
                    this.setMessage("");
                }
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    /**********added by dinesh**********************/
    static SecureRandom random = new SecureRandom();
    //private static String getUniqueSeqNo() {

    private String getUniqueSeqNo() {
        try {
            return new BigInteger(45, random).toString(32);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            return null;
        }
    }

    public void getChargeCashAccNo() {
        if ((this.tranType.equalsIgnoreCase("Cash")) && (!chargeEventID.equalsIgnoreCase("0"))) {
            this.setChargeAccountID(this.getDrAccId());
        }
    }

    /*****************end here***********************/
    public String exitBtnAction() {
        return "case1";
    }
}
