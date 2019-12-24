/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.cpsms;

import com.cbs.dto.neftrtgs.NeftOwDetailsTO;
import com.cbs.facade.cpsms.CPSMSMgmtFacadeRemote;
import com.cbs.facade.cpsms.CPSMSViewMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

public class InterBankResponseBean extends BaseBean {

    private String errorMessage;
    private String fileType;
    private String paymentReceivedDt;
    private String messageId;
    private String batchNo;
    private String displayDate;
    private String displayMessageId;
    private String displayBatchNo;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> paymentReceivedDtList;
    private List<SelectItem> messageIdList;
    private List<SelectItem> batchNoList;
    private List<NeftOwDetailsTO> gridDetail;
    private CPSMSViewMgmtFacadeRemote cpsmsViewRemote = null;
    private CPSMSMgmtFacadeRemote cpsmsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private static final Object LOCK = new Object();

    public InterBankResponseBean() {
        try {
            cpsmsViewRemote = (CPSMSViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CPSMSViewMgmtFacade");
            cpsmsRemote = (CPSMSMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CPSMSMgmtFacade");
            initData();
            resetForm();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        fileTypeList = new ArrayList<SelectItem>();
        paymentReceivedDtList = new ArrayList<SelectItem>();
        messageIdList = new ArrayList<SelectItem>();
        batchNoList = new ArrayList<SelectItem>();
        gridDetail = new ArrayList<NeftOwDetailsTO>();
        try {
            fileTypeList.add(new SelectItem("0", "--Select--"));
            fileTypeList.add(new SelectItem("IBB", "Inter Bank Bulk Response"));
            fileTypeList.add(new SelectItem("IBI", "Inter Bank Individual Response"));

            paymentReceivedDtList.add(new SelectItem("0", "--Select--"));
            messageIdList.add(new SelectItem("0", "--Select--"));
            batchNoList.add(new SelectItem("0", "--Select--"));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onBlurFileType() {
        try {
            if (this.fileType == null || this.fileType.equals("0")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            paymentReceivedDtList = new ArrayList<SelectItem>();
            paymentReceivedDtList.add(new SelectItem("0", "--Select--"));

            List<String> dateList = cpsmsViewRemote.retrievePaymentReceivedDates(this.fileType, getOrgnBrCode());
            if (dateList.isEmpty()) {
                this.setErrorMessage("There is no payment received to generate the response.");
                return;
            }
            for (String obj : dateList) {
                paymentReceivedDtList.add(new SelectItem(obj));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onBlurPaymentReceivedDt() {
        try {
            if (this.fileType == null || this.fileType.equals("0")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.paymentReceivedDt == null || this.paymentReceivedDt.equals("0")) {
                this.setErrorMessage("Please select payment received date.");
                return;
            }
            messageIdList = new ArrayList<SelectItem>();
            messageIdList.add(new SelectItem("0", "--Select--"));
            List<String> returnList = cpsmsViewRemote.retrievePaymentReceivedMessageId(this.fileType, getOrgnBrCode(), ymd.format(dmy.parse(this.paymentReceivedDt)));
            if (returnList.isEmpty()) {
                this.setErrorMessage("There is no payment received to generate the response.");
                return;
            }
            for (String obj : returnList) {
                messageIdList.add(new SelectItem(obj));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onBlurMessageId() {
        try {
            if (this.fileType == null || this.fileType.equals("0")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.paymentReceivedDt == null || this.paymentReceivedDt.equals("0")) {
                this.setErrorMessage("Please select payment received date.");
                return;
            }
            if (this.messageId == null || this.messageId.equals("0")) {
                this.setErrorMessage("Please select messageId.");
                return;
            }

            batchNoList = new ArrayList<SelectItem>();
            batchNoList.add(new SelectItem("0", "--Select--"));

            List<String> returnList = cpsmsViewRemote.retrievePaymentReceivedBatchNo(this.fileType, getOrgnBrCode(), 
                    ymd.format(dmy.parse(this.paymentReceivedDt)), this.messageId);
            if (returnList.isEmpty()) {
                this.setErrorMessage("There is no payment received to generate the response.");
                return;
            }
            for (String obj : returnList) {
                batchNoList.add(new SelectItem(obj));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onBlurBatchNo() {
        try {
            if (this.fileType == null || this.fileType.equals("0")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.paymentReceivedDt == null || this.paymentReceivedDt.equals("0")) {
                this.setErrorMessage("Please select payment received date.");
                return;
            }
            if (this.messageId == null || this.messageId.equals("0")) {
                this.setErrorMessage("Please select messageId.");
                return;
            }
            if (this.batchNo == null || this.batchNo.equals("0")) {
                this.setErrorMessage("Please select batch no.");
                return;
            }
            List<NeftOwDetailsTO> resultList;
            synchronized (LOCK) {
                resultList = cpsmsViewRemote.getInterBankOutwardDetail(this.fileType, ymd.format(dmy.parse(this.paymentReceivedDt)),
                        this.messageId, this.batchNo, getOrgnBrCode());
            }
            if (resultList.isEmpty()) {
                this.setErrorMessage("There is no data to generate the inter bank response.");
                return;
            }
            gridDetail = resultList;
            this.setDisplayDate(this.paymentReceivedDt);
            this.setDisplayMessageId(this.messageId);
            this.setDisplayBatchNo(this.batchNo);
            this.setErrorMessage("Now you can generate the response.");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            if (this.displayDate == null || this.displayDate.trim().equals("")) {
                this.setErrorMessage("Date can not be blank.");
                return;
            }
            if (this.displayMessageId == null || this.displayMessageId.trim().equals("")) {
                this.setErrorMessage("Message Id can not be blank.");
                return;
            }
            if (this.displayBatchNo == null || this.displayBatchNo.trim().equals("")) {
                this.setErrorMessage("Batch No can not be blank.");
                return;
            }
            if (gridDetail.isEmpty()) {
                this.setErrorMessage("There is no data to generate the response.");
                return;
            }
            String result = "";
            synchronized (LOCK) {
                result = cpsmsRemote.generateInterBankResponse(this.fileType, ymd.format(dmy.parse(this.displayDate)), this.displayMessageId,
                        this.displayBatchNo, gridDetail, getUserName(), getOrgnBrCode());
            }
            if (!result.equalsIgnoreCase("true")) {
                this.setErrorMessage("There is no data to generate the response.");
                return;
            }
            resetForm();
            this.setErrorMessage("Response has been generated successfully.");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setFileType("0");
        this.setPaymentReceivedDt("0");
        this.setMessageId("0");
        this.setBatchNo("0");
        this.setDisplayDate("");
        this.setDisplayMessageId("");
        this.setDisplayBatchNo("");
        gridDetail = new ArrayList<NeftOwDetailsTO>();
    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPaymentReceivedDt() {
        return paymentReceivedDt;
    }

    public void setPaymentReceivedDt(String paymentReceivedDt) {
        this.paymentReceivedDt = paymentReceivedDt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getDisplayMessageId() {
        return displayMessageId;
    }

    public void setDisplayMessageId(String displayMessageId) {
        this.displayMessageId = displayMessageId;
    }

    public String getDisplayBatchNo() {
        return displayBatchNo;
    }

    public void setDisplayBatchNo(String displayBatchNo) {
        this.displayBatchNo = displayBatchNo;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public List<SelectItem> getPaymentReceivedDtList() {
        return paymentReceivedDtList;
    }

    public void setPaymentReceivedDtList(List<SelectItem> paymentReceivedDtList) {
        this.paymentReceivedDtList = paymentReceivedDtList;
    }

    public List<SelectItem> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<SelectItem> messageIdList) {
        this.messageIdList = messageIdList;
    }

    public List<SelectItem> getBatchNoList() {
        return batchNoList;
    }

    public void setBatchNoList(List<SelectItem> batchNoList) {
        this.batchNoList = batchNoList;
    }

    public List<NeftOwDetailsTO> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NeftOwDetailsTO> gridDetail) {
        this.gridDetail = gridDetail;
    }
}
