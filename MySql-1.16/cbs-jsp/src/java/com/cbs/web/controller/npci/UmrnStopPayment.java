/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.other.AutoMandateTo;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class UmrnStopPayment extends BaseBean {

    private String message;
    private String umrn;
    private String function;
    private String creditorName;
    private String collectionAmt;
    private String maxAmt;
    private String frequency;
    private String category;
    private String debtorName;
    private String debtorAccount;
    private String btnValue;
    private Map map;
    private List<SelectItem> functionList;
    private NpciMandateFacadeRemote npciMandateRemote;
    private CommonReportMethodsRemote commonReport;

    public UmrnStopPayment() {
        try {
            npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            initData();
            resetForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("S", "STOP PAYMENT"));

            setMap(); //For MMS Category
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setMap() {
        try {
            map = new HashMap();
            List list = commonReport.getRefRecList("326");
            if (list.isEmpty()) {
                this.setMessage("Please fill MMS category.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                map.put(ele.get(0).toString().trim(), ele.get(1).toString().trim());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void functionAction() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setMessage("Please select function.");
                return;
            }
            this.setBtnValue("");
            if (this.function.equals("S")) {
                this.setBtnValue("Stop Payment");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getUmrnDetails() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setMessage("Please select function.");
                return;
            }
            if (this.umrn == null | this.umrn.equals("") || this.umrn.trim().length() < 20) {
                this.setMessage("Please fill UMRN of 20 digit.");
                return;
            }
            List<AutoMandateTo> dataList = npciMandateRemote.getPreviousMandateDetail(this.umrn);
            if (dataList.isEmpty()) {
                this.setMessage("There is no detail for this UMRN.");
                return;
            }
            //Refresh the fields
            this.setCreditorName("");
            this.setCollectionAmt("");
            this.setMaxAmt("");
            this.setFrequency("");
            this.setCategory("");
            this.setDebtorName("");
            this.setDebtorAccount("");

            AutoMandateTo to = dataList.get(0);
            this.setCreditorName(to.getCdtrNm());
            this.setCollectionAmt(to.getColltnAmt().toString());
            this.setMaxAmt(to.getMaxAmt().toString());
            this.setFrequency(to.getOcrncsFrqcy());
            this.setCategory(map.get(to.getTpSvcLvlPrtry()).toString());
            this.setDebtorName(to.getDbtrNm());
            this.setDebtorAccount(to.getDbtrAcctIdOthrId());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setMessage("Please select function.");
                return;
            }
            if (this.umrn == null | this.umrn.equals("") || this.umrn.trim().length() < 20) {
                this.setMessage("Please fill UMRN of 20 digit.");
                return;
            }
            if (this.debtorAccount == null || this.debtorAccount.equals("")) {
                this.setMessage("There should be Debtor A/c No.");
                return;
            }
            String result = npciMandateRemote.updateMandateStatus(this.umrn, this.debtorAccount, getOrgnBrCode(), getUserName());
            if (result.equalsIgnoreCase("true")) {
                resetForm();
                this.setMessage("UMRN has been stopped successfully.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setMessage("");
        this.setFunction("0");
        this.setUmrn("");
        this.setCreditorName("");
        this.setCollectionAmt("");
        this.setMaxAmt("");
        this.setFrequency("");
        this.setCategory("");
        this.setDebtorName("");
        this.setDebtorAccount("");
        this.setBtnValue("");
    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getCollectionAmt() {
        return collectionAmt;
    }

    public void setCollectionAmt(String collectionAmt) {
        this.collectionAmt = collectionAmt;
    }

    public String getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(String maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(String debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }
}
