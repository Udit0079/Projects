/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.cpsms;

import com.cbs.dto.cpsms.CpsmsCommonDTO;
import com.cbs.facade.cpsms.CPSMSMgmtFacadeRemote;
import com.cbs.facade.cpsms.CPSMSViewMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PrintAdviceBean extends BaseBean {

    private String errorMessage;
    private String exactDt;
    private String paymentMessageId;
    private String batchNo;
    private String displayFlag = "none";
    private String totalDebit;
    private String totalCredit;
    private CpsmsCommonDTO currentItem;
    private List<CpsmsCommonDTO> dataList;
    //Pop Up Panel Properties
    private String viewPaymentMessageId;
    private String viewBatchNo;
    private String viewTotalDebit;
    private String viewTotalCredit;
    private List<CpsmsCommonDTO> batchDetailList;
    private CPSMSViewMgmtFacadeRemote cpsmsViewRemote = null;
    private CPSMSMgmtFacadeRemote cpsmsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private static final Object LOCK = new Object();

    public PrintAdviceBean() {
        try {
            cpsmsViewRemote = (CPSMSViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CPSMSViewMgmtFacade");
            cpsmsRemote = (CPSMSMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CPSMSMgmtFacade");
            refreshForm();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void exactDtProcess() {
        try {
            if (this.exactDt == null || this.exactDt.equals("")) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.exactDt)) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            dataList = new ArrayList<CpsmsCommonDTO>();
            dataList = cpsmsViewRemote.getPrintAdvicePaymentFileDetail(ymd.format(dmy.parse(this.exactDt)), getOrgnBrCode());
            this.setErrorMessage("Please select a row to process or view.");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void setDataToProcess() {
        try {
            this.setDisplayFlag("none");
            this.setPaymentMessageId("");
            this.setBatchNo("");
            this.setTotalDebit("");
            this.setTotalCredit("");

            if (currentItem == null) {
                this.setErrorMessage("Please select a row to process.");
                return;
            }
            this.setDisplayFlag("");
            this.setPaymentMessageId(currentItem.getMessageId());
            this.setBatchNo(currentItem.getBatchNo());
            this.setTotalDebit(currentItem.getTotalDebit().toString());
            this.setTotalCredit(currentItem.getTotalCredit().toString());
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void showBatchDetail() {
        this.setErrorMessage("");
        try {
            this.setViewPaymentMessageId("");
            this.setViewBatchNo("");
            this.setViewTotalDebit("");
            this.setViewTotalCredit("");
            batchDetailList = new ArrayList<CpsmsCommonDTO>();
            if (currentItem == null) {
                this.setErrorMessage("Please select a row to view batch detail.");
                return;
            }
            if (this.exactDt == null || this.exactDt.equals("")) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.exactDt)) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            this.setViewPaymentMessageId(currentItem.getMessageId());
            this.setViewBatchNo(currentItem.getBatchNo());
            this.setViewTotalDebit(currentItem.getTotalDebit().toString());
            this.setViewTotalCredit(currentItem.getTotalCredit().toString());
            batchDetailList = cpsmsViewRemote.getPrintAdvicePaymentFileBatchDetail(ymd.format(dmy.parse(this.exactDt)),
                    currentItem.getMessageId(), currentItem.getBatchNo());
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processButtonAction() {
        this.setErrorMessage("");
        try {
            String validateMessage = validateProcessField();
            if (!validateMessage.equalsIgnoreCase("true")) {
                this.setErrorMessage(validateMessage);
                return;
            }
            String msg = "";
            synchronized (LOCK) {
                msg = cpsmsRemote.printAdviceBatchProcessing(ymd.format(dmy.parse(this.exactDt)),
                        this.paymentMessageId, this.batchNo, getUserName(), getOrgnBrCode());
            }
            if (!msg.equalsIgnoreCase("true")) {
                this.setErrorMessage(msg);
                return;
            }
            processTimeFieldRefresh();
            exactDtProcess();
            this.setErrorMessage("Batch has been processed successfully.");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processReturnAction() {
        this.setErrorMessage("");
        try {
            String validateMessage = validateProcessField();
            if (!validateMessage.equalsIgnoreCase("true")) {
                this.setErrorMessage(validateMessage);
                return;
            }
            String msg = "";
            synchronized (LOCK) {
                msg = cpsmsRemote.printAdviceBatchReturnProcessing(ymd.format(dmy.parse(this.exactDt)),
                        this.paymentMessageId, this.batchNo);
            }
            if (!msg.equalsIgnoreCase("true")) {
                this.setErrorMessage(msg);
                return;
            }
            processTimeFieldRefresh();
            exactDtProcess();
            this.setErrorMessage("Batch has been returned successfully.");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public String validateProcessField() {
        if (this.paymentMessageId == null || this.paymentMessageId.equals("")) {
            return "There should be proper file message id.";
        }
        if (this.batchNo == null || this.batchNo.equals("")) {
            return "There should be proper batch no.";
        }
        if (currentItem == null) {
            return "Please select a row to process the batch.";
        }
        if (!(this.paymentMessageId.equalsIgnoreCase(currentItem.getMessageId())
                && this.batchNo.equalsIgnoreCase(currentItem.getBatchNo()))) {
            return "Selected row is not matched with file message id and batch no.";
        }
        return "true";
    }

    public void processTimeFieldRefresh() {
        this.setErrorMessage("");
        this.setDisplayFlag("none");
        this.setPaymentMessageId("");
        this.setBatchNo("");
        this.setTotalDebit("");
        this.setTotalCredit("");
        dataList = new ArrayList<CpsmsCommonDTO>();
        currentItem = null;
        this.setViewPaymentMessageId("");
        this.setViewBatchNo("");
        this.setViewTotalDebit("");
        this.setViewTotalCredit("");
        batchDetailList = new ArrayList<CpsmsCommonDTO>();
    }

    public void refreshForm() {
        this.setErrorMessage("");
        this.setExactDt(getTodayDate());
        this.setDisplayFlag("none");
        this.setPaymentMessageId("");
        this.setBatchNo("");
        this.setTotalDebit("");
        this.setTotalCredit("");
        dataList = new ArrayList<CpsmsCommonDTO>();
        currentItem = null;
        this.setViewPaymentMessageId("");
        this.setViewBatchNo("");
        this.setViewTotalDebit("");
        this.setViewTotalCredit("");
        batchDetailList = new ArrayList<CpsmsCommonDTO>();
        this.setErrorMessage("Please fill date.");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter and setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExactDt() {
        return exactDt;
    }

    public void setExactDt(String exactDt) {
        this.exactDt = exactDt;
    }

    public String getPaymentMessageId() {
        return paymentMessageId;
    }

    public void setPaymentMessageId(String paymentMessageId) {
        this.paymentMessageId = paymentMessageId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.displayFlag = displayFlag;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public CpsmsCommonDTO getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CpsmsCommonDTO currentItem) {
        this.currentItem = currentItem;
    }

    public List<CpsmsCommonDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<CpsmsCommonDTO> dataList) {
        this.dataList = dataList;
    }

    public String getViewPaymentMessageId() {
        return viewPaymentMessageId;
    }

    public void setViewPaymentMessageId(String viewPaymentMessageId) {
        this.viewPaymentMessageId = viewPaymentMessageId;
    }

    public String getViewBatchNo() {
        return viewBatchNo;
    }

    public void setViewBatchNo(String viewBatchNo) {
        this.viewBatchNo = viewBatchNo;
    }

    public String getViewTotalDebit() {
        return viewTotalDebit;
    }

    public void setViewTotalDebit(String viewTotalDebit) {
        this.viewTotalDebit = viewTotalDebit;
    }

    public String getViewTotalCredit() {
        return viewTotalCredit;
    }

    public void setViewTotalCredit(String viewTotalCredit) {
        this.viewTotalCredit = viewTotalCredit;
    }

    public List<CpsmsCommonDTO> getBatchDetailList() {
        return batchDetailList;
    }

    public void setBatchDetailList(List<CpsmsCommonDTO> batchDetailList) {
        this.batchDetailList = batchDetailList;
    }
}
