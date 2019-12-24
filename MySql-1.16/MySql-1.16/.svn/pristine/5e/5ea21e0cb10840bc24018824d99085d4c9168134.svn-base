/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.misc.StrAlertFacadeRemote;
import com.cbs.pojo.BankLevelSTRPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class AlertIndicatorInfoCustIdWise extends BaseBean {

    private String message;
    private String custid;
    private String custName;
    private String dob;
    private String fatherName;
    private String alertType;
    private List<SelectItem> alertTypeList;
    private String alertCode;
    private List<SelectItem> alertCodeList;
    private String alertSubCode;
    private List<SelectItem> alertSubCodeList;
    private String subCodeDesc;
    private String btnValue;
    //private boolean btnFlag;
    private boolean disableAlert;
    private String confirmationMsg;
    private String gridDisplay;
    private AdvancesInformationTrackingRemote aitr;
    private StrAlertFacadeRemote strAlertRemote;
    List<BankLevelSTRPojo> strAlertList;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public AlertIndicatorInfoCustIdWise() {

        try {
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            strAlertRemote = (StrAlertFacadeRemote) ServiceLocator.getInstance().lookup("StrAlertFacade");
            selectAlertType();
            this.setBtnValue("Save");
            getRfaStrData();
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void populateMessage() {
        setMessage("");
        this.setConfirmationMsg("Are you sure to add this detail ?");

//        if (this.function.equals("A")) {
//            this.setConfirmationMsg("Are you sure to add this detail ?");
//        } else if (this.function.equals("U")) {
//            this.setConfirmationMsg("Are you sure to modify this detail ?");
//        } else if (this.function.equals("D")) {
//            this.setConfirmationMsg("Are you sure to delete this detail ?");
//        }
    }

    public void onCustIdAction() {
        setMessage("");
        try {
            if (!strAlertRemote.getBranchByCustId(custid).equalsIgnoreCase(getOrgnBrCode())) {
                throw new ApplicationException("Registration allow only from base branch !");
            }
            List custList = strAlertRemote.getCustDataById(custid);
            if (!custList.isEmpty()) {
                for (int i = 0; i < custList.size(); i++) {
                    Vector vtr = (Vector) custList.get(i);
                    this.setCustName(vtr.get(0).toString());
                    this.setDob(vtr.get(1).toString());
                    this.setFatherName(vtr.get(2).toString());
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void selectAlertType() {
        this.setMessage("");
        try {
            alertTypeList = new ArrayList<SelectItem>();
            alertTypeList.add(new SelectItem(" ", "--Select--"));
            List l3 = aitr.getListAsPerRequirement("350", "0", "0", "0", "0", "0", ymd.format(new Date()),0);
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                //if(v3.get(0).toString().equalsIgnoreCase("3") || v3.get(0).toString().equalsIgnoreCase("8")){
                alertTypeList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
                //}
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onAlertCode() {
        this.setMessage("");
        try {
            alertCodeList = new ArrayList<SelectItem>();
            if (alertType.equalsIgnoreCase("RFA")) {
                setAlertSubCode("");
                this.disableAlert = true;
                this.setGridDisplay("");
                getRfaStrData();
            } else {

                this.disableAlert = false;
                this.setGridDisplay("none");
                alertCodeList = new ArrayList<SelectItem>();
                alertCodeList.add(new SelectItem(" ", "--Select--"));
                List l3 = aitr.getListAsPerRequirement("351", getAlertType(), "0", "0", "0", "0",ymd.format(new Date()),0);
                for (int i = 0; i < l3.size(); i++) {
                    Vector v3 = (Vector) l3.get(i);
                    // if (v3.get(0).toString().equalsIgnoreCase("EI") || v3.get(0).toString().equalsIgnoreCase("TM")) {
                    alertCodeList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
                    // }
                }
            }


        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onAlertCodeSub() {
        this.setMessage("");
        this.setSubCodeDesc("");
        try {
            alertSubCodeList = new ArrayList<SelectItem>();
            alertSubCodeList.add(new SelectItem(" ", "--Select--"));
            List l3 = aitr.getListAsPerRequirement("352", getAlertType(), getAlertCode(), "0", "0", "0",ymd.format(new Date()),0);
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                alertSubCodeList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onAlertCodeSubDesc() {
        this.setMessage("");
        this.subCodeDesc = this.getAlertSubCode();
    }

    public void checkUnCheck() {
    }

    public void getRfaStrData() {
        try {
            strAlertList = new ArrayList<BankLevelSTRPojo>();
            List rfaList = strAlertRemote.getRfaAlertList(this.custid);
            if (!rfaList.isEmpty()) {
                for (int i = 0; i < rfaList.size(); i++) {
                    BankLevelSTRPojo pojo = new BankLevelSTRPojo();
                    Vector vtr = (Vector) rfaList.get(i);
                    pojo.setSno(i + 1);
                    pojo.setAlertType(vtr.get(0).toString());
                    pojo.setDescription(vtr.get(1).toString());
                    pojo.setFromAmt(vtr.get(2).toString());
                    pojo.setToAmt(vtr.get(3).toString());
                    pojo.setNoOfTxn(vtr.get(4).toString());
                    pojo.setDayMonth(vtr.get(5).toString());
                    //pojo.setCheckBox(pojo.isCheckBox());
                    strAlertList.add(pojo);
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveMasterDetail() {
        setMessage("");
        String result = "";
        try {
            if (!strAlertRemote.getBranchByCustId(custid).equalsIgnoreCase(getOrgnBrCode())) {
                throw new ApplicationException("Registration allow only from base branch !");
            }
            if (alertType.equalsIgnoreCase("RFA")) {
                result = strAlertRemote.getCustIdLevelStr(custid, strAlertList, getOrgnBrCode(), getUserName(), true);
            } else {
                result = strAlertRemote.getSaveAlertIndicator(custid, dob, alertSubCode, getOrgnBrCode(), getUserName());
            }
            if (result.equalsIgnoreCase("true")) {
                setMessage("Data has been save successfully !");
            }
            clear();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        clear();
    }

    public void clear() {
        setCustid("");
        setCustName("");
        setDob("");
        setFatherName("");
        setAlertType("");
        setAlertCode("");
        setAlertSubCode("");
        setSubCodeDesc("");
        strAlertList = new ArrayList<BankLevelSTRPojo>();
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public List<SelectItem> getAlertTypeList() {
        return alertTypeList;
    }

    public void setAlertTypeList(List<SelectItem> alertTypeList) {
        this.alertTypeList = alertTypeList;
    }

    public String getAlertCode() {
        return alertCode;
    }

    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }

    public List<SelectItem> getAlertCodeList() {
        return alertCodeList;
    }

    public void setAlertCodeList(List<SelectItem> alertCodeList) {
        this.alertCodeList = alertCodeList;
    }

    public String getAlertSubCode() {
        return alertSubCode;
    }

    public void setAlertSubCode(String alertSubCode) {
        this.alertSubCode = alertSubCode;
    }

    public List<SelectItem> getAlertSubCodeList() {
        return alertSubCodeList;
    }

    public void setAlertSubCodeList(List<SelectItem> alertSubCodeList) {
        this.alertSubCodeList = alertSubCodeList;
    }

    public String getSubCodeDesc() {
        return subCodeDesc;
    }

    public void setSubCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public List<BankLevelSTRPojo> getStrAlertList() {
        return strAlertList;
    }

    public void setStrAlertList(List<BankLevelSTRPojo> strAlertList) {
        this.strAlertList = strAlertList;
    }

 

    public String getGridDisplay() {
        return gridDisplay;
    }

    public void setGridDisplay(String gridDisplay) {
        this.gridDisplay = gridDisplay;
    }

    public boolean isDisableAlert() {
        return disableAlert;
    }

    public void setDisableAlert(boolean disableAlert) {
        this.disableAlert = disableAlert;
    }

     
}
