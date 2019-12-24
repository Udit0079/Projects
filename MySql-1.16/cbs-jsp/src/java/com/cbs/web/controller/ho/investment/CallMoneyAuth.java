/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.investment.CallMoneyAuthPojo;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class CallMoneyAuth extends BaseBean {

    private String message;
    private String function;
    private String ctrlNo;
    private String processBtnCaption;
    private String confirmationMsg;
    private boolean processBtnVisible;
    private List<SelectItem> functionList;
    private List<CallMoneyAuthPojo> gridDetail;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public List<CallMoneyAuthPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CallMoneyAuthPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public String getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(String ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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

    public String getProcessBtnCaption() {
        return processBtnCaption;
    }

    public void setProcessBtnCaption(String processBtnCaption) {
        this.processBtnCaption = processBtnCaption;
    }

    public boolean isProcessBtnVisible() {
        return processBtnVisible;
    }

    public void setProcessBtnVisible(boolean processBtnVisible) {
        this.processBtnVisible = processBtnVisible;
    }


    public CallMoneyAuth() {
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            addFunctionList();
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void addFunctionList() {
        functionList = new ArrayList<SelectItem>();
        functionList.add(new SelectItem("A", "Authorize"));
        functionList.add(new SelectItem("D", "Delete"));
    }

    public void onChangeFunction() {
        this.setMessage("");
        this.setCtrlNo("");
        this.confirmationMsg = "";
        this.processBtnVisible = true;
        this.setMessage("Please fill control no to authorize or delete !");
        if (this.function.equals("A")) {
            this.processBtnCaption = "Authorize";
        } else if (this.function.equals("D")) {
            this.processBtnCaption = "Delete";
        }
    }

    public void onBlurCtrlNo() {
        this.setMessage("");
        if (this.ctrlNo == null || this.ctrlNo.equalsIgnoreCase("") || this.ctrlNo.equals("0.0") || this.ctrlNo.equalsIgnoreCase("0")) {
            this.setMessage("Please fill controll no. !");
            return;
        }
        this.processBtnVisible = false;
        if (this.function.equals("A")) {
            this.setMessage("Now you can authorize the entry !");
        } else if (this.function.equals("D")) {
            this.setMessage("Now you can delete the entry !");
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("A")) {
            this.setConfirmationMsg("Are you sure to authorize this entry!");
            return;
        } else if (this.function.equals("D")) {
            this.setConfirmationMsg("Are you sure to delete this entry!");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        if (this.function.equals("A")) {
            authorizationProcess();
        } else if (this.function.equals("D")) {
            deleteProcess();
        }
    }

    public void authorizationProcess() {
        this.setMessage("");
        try {
            if (this.ctrlNo == null || this.ctrlNo.equalsIgnoreCase("") || this.ctrlNo.equals("0.0") || this.ctrlNo.equalsIgnoreCase("0")) {
                this.setMessage("Please fill controll no. !");
                return;
            }
            String msg = remoteObj.authorizeEntry(Long.parseLong(this.ctrlNo), getUserName(), getOrgnBrCode());
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("Authorization over !");
                getTableData();
                this.setCtrlNo("");
                this.function = "A";
                this.confirmationMsg = "";
                this.processBtnVisible = true;
                this.processBtnCaption = "Authorize";
                return;
            } else {
                this.setMessage(msg);
            }
        } catch (ApplicationException e) {
            if (e.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("Please Fill Correct Control Number.");
            } else {
                this.setMessage(e.getMessage());
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteProcess() {
        this.setMessage("");
        try {
            if (this.ctrlNo == null || this.ctrlNo.equalsIgnoreCase("") || this.ctrlNo.equals("0.0") || this.ctrlNo.equalsIgnoreCase("0")) {
                this.setMessage("Please fill controll no. !");
                return;
            }
            InvestmentCallMaster entity = remoteObj.getInvestCallMasterByCtrl(Long.parseLong(this.ctrlNo));
            if (entity == null) {
                this.setMessage("please fill correct control no. !");
                return;
            } else {
                String msg = remoteObj.deleteInvestmentCallMaster(Long.parseLong(this.ctrlNo));
                if (msg.equalsIgnoreCase("true")) {
                    this.setMessage("Entry has been deleted successfully !");
                    getTableData();
                    this.setCtrlNo("");
                    this.function = "D";
                    this.confirmationMsg = "";
                    this.processBtnVisible = true;
                    this.processBtnCaption = "Delete";
                    return;
                } else {
                    this.setMessage(msg);
                }
            }
        } catch (ApplicationException e) {
            if (e.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("Please Fill Correct Control Number.");
            } else {
                this.setMessage(e.getMessage());
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getTableData() {
        gridDetail = new ArrayList<CallMoneyAuthPojo>();
        try {
            List<InvestmentCallMaster> entityList = remoteObj.getUnAuhorizeCallMaster();
            if (!entityList.isEmpty()) {
                for (int j = 0; j < entityList.size(); j++) {
                    InvestmentCallMaster entity = entityList.get(j);
                    CallMoneyAuthPojo pojo = new CallMoneyAuthPojo();
                    pojo.setSno(j + 1);
                    pojo.setCtrlNo(entity.getCtrlNo().intValue());
                    pojo.setDealDt(dmy.format(entity.getDealDt()));
                    pojo.setCompDt(dmy.format(entity.getCompletionDt()));
                    pojo.setRoi(entity.getRoi());
                    pojo.setAmount(formatter.format(entity.getAmt()));
                    pojo.setNoOfDays(entity.getNoOfDays());
                    pojo.setEnterBy(entity.getEnterBy());
                    pojo.setDetails(entity.getDetails());
                    pojo.setIntAmount(entity.getIntAmt());
                    pojo.setGlheadAcno(entity.getDrGlHead());
                    gridDetail.add(pojo);
                }
            } else {
                this.setMessage("There is no pending entry to authorize or delete !");
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fieldReset() {
        this.setMessage("");
        this.setCtrlNo("");
        this.function = "A";
        this.confirmationMsg = "";
        this.processBtnVisible = true;
        this.processBtnCaption = "Authorize";
    }

    public void resetForm() {
        fieldReset();
        this.setMessage("Please select function for authorize or delete !");
        getTableData();
    }

    public String exitBtnAction() {
        try {
            fieldReset();
            gridDetail = new ArrayList<CallMoneyAuthPojo>();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
