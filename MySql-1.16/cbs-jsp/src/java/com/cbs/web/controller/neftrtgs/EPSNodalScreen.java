/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.web.pojo.neftrtgs.Block4Items;
import com.cbs.pojo.neftrtgs.sbi.BlockAItems;
import com.cbs.web.pojo.neftrtgs.NodalGridItems;
import com.cbs.entity.neftrtgs.EPSInwardCredit;
import com.cbs.entity.neftrtgs.EPSNodalBranchDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 *
 * @author root
 */
public class EPSNodalScreen extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String paySysId;
    private List<SelectItem> paySysIdList;
    private String msgStatus;
    private List<SelectItem> msgStatusList;
    private String message;
    private boolean msgStatusFlag;
    private List<NodalGridItems> unAuthList;
    private NodalGridItems item;
    private boolean submitButtonFlag;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public boolean isSubmitButtonFlag() {
        return submitButtonFlag;
    }

    public void setSubmitButtonFlag(boolean submitButtonFlag) {
        this.submitButtonFlag = submitButtonFlag;
    }

    public NodalGridItems getItem() {
        return item;
    }

    public void setItem(NodalGridItems item) {
        this.item = item;
    }

    public List<NodalGridItems> getUnAuthList() {
        return unAuthList;
    }

    public void setUnAuthList(List<NodalGridItems> unAuthList) {
        this.unAuthList = unAuthList;
    }

    public boolean isMsgStatusFlag() {
        return msgStatusFlag;
    }

    public void setMsgStatusFlag(boolean msgStatusFlag) {
        this.msgStatusFlag = msgStatusFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public List<SelectItem> getMsgStatusList() {
        return msgStatusList;
    }

    public void setMsgStatusList(List<SelectItem> msgStatusList) {
        this.msgStatusList = msgStatusList;
    }

    public String getPaySysId() {
        return paySysId;
    }

    public void setPaySysId(String paySysId) {
        this.paySysId = paySysId;
    }

    public List<SelectItem> getPaySysIdList() {
        return paySysIdList;
    }

    public void setPaySysIdList(List<SelectItem> paySysIdList) {
        this.paySysIdList = paySysIdList;
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

    /** Creates a new instance of EPSNodalScreen */
    public EPSNodalScreen() {
        try {
            msgStatusFlag = true;
            submitButtonFlag = true;
            this.setCurrentDate(getTodayDate());
            this.setLoggedInUser(getUserName());

            paySysIdList = new ArrayList<SelectItem>();
            paySysIdList.add(new SelectItem("NEFT", "NEFT"));
            paySysIdList.add(new SelectItem("RTGS", "RTGS"));

            msgStatusList = new ArrayList<SelectItem>();
            msgStatusList.add(new SelectItem("Failed", "Failed"));
            msgStatusList.add(new SelectItem("Received", "Received"));

            unAuthList = new ArrayList();
            this.setMessage("");
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getMessageStatus() {
        if (this.paySysId.equalsIgnoreCase("NEFT")) {
            msgStatusFlag = false;
        } else {
            msgStatusFlag = false;
        }
    }

    public void getReturnData() {
        List<EPSInwardCredit> dataToAuthorize = null;
        String messageStatus = "";
        if (this.paySysId.equalsIgnoreCase("NEFT")) {
            messageStatus = this.msgStatus;
        } else {
            messageStatus = "Failed";
        }
        try {
            unAuthList = new ArrayList<NodalGridItems>();
            dataToAuthorize = remoteInterface.getNodalGridData(this.paySysId, messageStatus);
            if (dataToAuthorize.size() > 0) {
                submitButtonFlag = false;
                for (int i = 0; i < dataToAuthorize.size(); i++) {
                    item = new NodalGridItems();
                    EPSInwardCredit creditObj = (EPSInwardCredit) dataToAuthorize.get(i);
                    item.setsNo(i + 1);
                    item.setMsgType(creditObj.getMsgType());
                    item.setSubMsgType(creditObj.getSubMsgType());
                    item.setSenderIFSC(creditObj.getSenderIFSC());
                    item.setReceiverIFSC(creditObj.getReceiverIFSC());
                    item.setUtr(creditObj.getUtr());
                    item.setAmount(creditObj.getAmount());
                    item.setBeneficiaryAcc(creditObj.getBeneficiaryAccount());
                    unAuthList.add(item);
                }
            } else {
                submitButtonFlag = true;
                this.setMessage("No Data Found !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            String neftInwardAcc = inwardAccPlaceAccount("NEFT");
            if ((this.paySysId.equalsIgnoreCase("NEFT")) && (this.msgStatus.equalsIgnoreCase("Received"))) {
                if (unAuthList.size() > 0) {
                    for (int i = 0; i < unAuthList.size(); i++) {
                        item = unAuthList.get(i);
                        EPSInwardCredit inwardCrObj = remoteInterface.getInwardCrByUTR(item.getUtr());
                        String msg = remoteInterface.insertTransaction("EPSSystem", getUserName(), neftInwardAcc, item.getBeneficiaryAcc(), item.getAmount(), "NEFT Process", "VCH", "", new Date(), "", "");
                        if (msg.equalsIgnoreCase("true")) {
                            inwardCrObj.setMsgStatus("Processed");
                            remoteInterface.updateInwardCreditByUTR(inwardCrObj);
                            this.setMessage("Process has been completed successfully");
                        } else {
                            inwardCrObj.setMsgStatus("Failed");
                            remoteInterface.updateInwardCreditByUTR(inwardCrObj);
                            this.setMessage("There are some message to be returned");
                        }
                    }
                }
                refreshForm();
            } else if ((this.paySysId.equalsIgnoreCase("NEFT")) && (this.msgStatus.equalsIgnoreCase("Failed"))) {
                sendCBSToSFMS();
                refreshForm();
            } else if ((this.paySysId.equalsIgnoreCase("RTGS")) && (this.msgStatus.equalsIgnoreCase("Failed"))) {
                sendCBSToSFMS();
                refreshForm();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String inwardAccPlaceAccount(String paySysId) {
        String nodalInwardAccount = "";
        try {
            List<EPSNodalBranchDetail> nodalObj = remoteInterface.getNodalRTGSDrAcc(paySysId);
            EPSNodalBranchDetail entity = nodalObj.get(0);
            nodalInwardAccount = entity.getInwardAccPlaceHolder();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return nodalInwardAccount;
    }

    public void sendCBSToSFMS() {
        try {
            if (unAuthList.size() > 0) {
                for (int i = 0; i < unAuthList.size(); i++) {
                    item = unAuthList.get(i);
                    EPSInwardCredit inwardCrObj = remoteInterface.getInwardCrByUTR(item.getUtr());
                    /************Creation of returning message*****************/
                    /****Code to create BlockA***************************/
                    BlockAItems blockA = new BlockAItems();

                    blockA.setBlockAIdentifier("{A:");
                    blockA.setBnkAppIdentifier("CBS");
                    blockA.setMsgIdentifier("F01");
                    blockA.setiOIdentifier("O");
                    blockA.setMsgType("298");

                    blockA.setSubMsgType(inwardCrObj.getMsgType());
                    blockA.setSenderIFSC(inwardCrObj.getReceiverIFSC());
                    blockA.setReceiverIFSC(inwardCrObj.getSenderIFSC());
                    blockA.setObsolescencePeriod("000");

                    blockA.setMsgUserReference("XXXXXXXXXXXXXXXX");
                    blockA.setPossibleDuplicateFlag(2);
                    if (this.paySysId.equalsIgnoreCase("NEFT")) {
                        blockA.setDeliveryMonitoringFlag(1);
                        blockA.setOpenNotificationFlag(2);
                        blockA.setNonDeliveryWarningFlag(2);
                        blockA.setServiceIdentifier("EFT");
                        blockA.setTestingAndTrainingFlag(2);
                    } else {
                        blockA.setDeliveryMonitoringFlag(1);
                        blockA.setOpenNotificationFlag(2);
                        blockA.setNonDeliveryWarningFlag(2);
                        blockA.setServiceIdentifier("XXX");
                        blockA.setTestingAndTrainingFlag(2);
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
                    blockA.setSequenceNumber(inwardCrObj.getSequenceNo());
                    blockA.setFiller("XXXXXXXXX");

                    blockA.setUniqueTransactionReference(inwardCrObj.getUtr());
                    blockA.setRtgsPriority(Integer.parseInt(inwardCrObj.getPriorityFlag()));
                    blockA.setBlockAEndIdentifier("}");

                    /****Code to create Block4***************************/
                    Block4Items block4 = new Block4Items();

                    block4.setBlockBeginIdentifier("{4:");
                    block4.setTransactionReferenceNo(":20:" + inwardCrObj.getTranRefNo());
                    SimpleDateFormat ymdf = new SimpleDateFormat("ddMMyyyy");
                    block4.setValueDateCurrencyAndAmount(":32A:" + ymdf.format(new Date()) + "INR" + inwardCrObj.getAmount());
                    block4.setBlockEndIdentifier("-}");

                    /****Addition end************************************/
                    String returnMessage = blockA.toString() + block4.toString();
                    /**********************end here****************************/
                    InitialContext ctx = new InitialContext();
                    QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("jms/QCFactory");
                    QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
                    QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                    Queue queue = (Queue) ctx.lookup("jms/outboundQueue");
                    QueueSender queueSender = queueSession.createSender(queue);

                    TextMessage textMessage = queueSession.createTextMessage(returnMessage);
                    System.out.println("Sending ack message: " + textMessage.getText());
                    queueSender.send(textMessage);
                    queueSender.close();
                    queueSession.close();
                    queueConnection.close();
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.paySysId = "NEFT";
        this.msgStatus = "Failed";
        msgStatusFlag = true;
        submitButtonFlag = true;
        unAuthList = new ArrayList();
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
