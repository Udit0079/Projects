/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.entity.customer.CbsCustMisinfo;
import com.cbs.entity.customer.CbsCustomerMasterDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

public class CustomerDetail extends BaseBean {

    String msgForStaff;
    String funcToSelect;
    String funcFlag;
    String dobFlag;
    List<SelectItem> funcToSelectOption;
    String custLevelChanges;
    String acctLevelChanges;
    boolean flagForNri;
    int flagForCreditRate;
    String tdsCode;
    String errorMsg;
    String custId;
    boolean flag = false;
    List tooMuchParam;
    String passFlag;
    boolean flagForSelect = false;
    int currentRow;
    String function;
    String functionValue;
    String dependents;
    List tooMuchParamKYC;
    List<SelectItem> functionOption;
    CustomerMasterDelegate masterDelegate;
    private List<SelectItem> verifyCustIdList;
    private String verifyCustId;
    private String custIdInputDisFlag = "";
    private String custIdComboDisFlag = "none";
    private String mapMessageDisFlag = "none";
    private String mapMessage;
    Map<String, String> map;
    private boolean disableTab;
    //Addition on 20/10/2015
    private boolean chqAlertFlag;
    private String confirmationMsg;
    //End here
    //Addition on 16/04/2016
    private boolean badPersonAlertFlag;
    private String illegalMsg;
    //End here
    //Addition on 20/10/2015
    private String ckycNo;
    private String ckycNoDisFlag = "none";
    //end
    private boolean custFullNameMismatchFlag;

    public boolean isDisableTab() {
        return disableTab;
    }

    public void setDisableTab(boolean disableTab) {
        this.disableTab = disableTab;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(String mapMessage) {
        this.mapMessage = mapMessage;
    }

    public String getCustIdComboDisFlag() {
        return custIdComboDisFlag;
    }

    public void setCustIdComboDisFlag(String custIdComboDisFlag) {
        this.custIdComboDisFlag = custIdComboDisFlag;
    }

    public String getCustIdInputDisFlag() {
        return custIdInputDisFlag;
    }

    public void setCustIdInputDisFlag(String custIdInputDisFlag) {
        this.custIdInputDisFlag = custIdInputDisFlag;
    }

    public String getMapMessageDisFlag() {
        return mapMessageDisFlag;
    }

    public void setMapMessageDisFlag(String mapMessageDisFlag) {
        this.mapMessageDisFlag = mapMessageDisFlag;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public String getVerifyCustId() {
        return verifyCustId;
    }

    public void setVerifyCustId(String verifyCustId) {
        this.verifyCustId = verifyCustId;
    }

    public List<SelectItem> getVerifyCustIdList() {
        return verifyCustIdList;
    }

    public void setVerifyCustIdList(List<SelectItem> verifyCustIdList) {
        this.verifyCustIdList = verifyCustIdList;
    }

    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public List getTooMuchParamKYC() {
        return tooMuchParamKYC;
    }

    public void setTooMuchParamKYC(List tooMuchParamKYC) {
        this.tooMuchParamKYC = tooMuchParamKYC;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isFlagForSelect() {
        return flagForSelect;
    }

    public void setFlagForSelect(boolean flagForSelect) {
        this.flagForSelect = flagForSelect;
    }

    public List getTooMuchParam() {
        return tooMuchParam;
    }

    public void setTooMuchParam(List tooMuchParam) {
        this.tooMuchParam = tooMuchParam;
    }

    public String getMsgForStaff() {
        return msgForStaff;
    }

    public void setMsgForStaff(String msgForStaff) {
        this.msgForStaff = msgForStaff;
    }

    public boolean isFlagForNri() {
        return flagForNri;
    }

    public void setFlagForNri(boolean flagForNri) {
        this.flagForNri = flagForNri;
    }

    public String getIntroTelex() {
        return introTelex;
    }

    public void setIntroTelex(String introTelex) {
        this.introTelex = introTelex;
    }
    String introTelex;

    public Date getNreBecomResident() {
        return nreBecomResident;
    }

    public void setNreBecomResident(Date nreBecomResident) {
        this.nreBecomResident = nreBecomResident;
    }
    Date nreBecomResident;

    public String getTdsCode() {
        return tdsCode;
    }

    public void setTdsCode(String tdsCode) {
        this.tdsCode = tdsCode;
    }

    public String getItFileNo() {
        return itFileNo;
    }

    public void setItFileNo(String itFileNo) {
        this.itFileNo = itFileNo;
    }
    String itFileNo;

    public String getAcctLevelChanges() {
        return acctLevelChanges;
    }

    public void setAcctLevelChanges(String acctLevelChanges) {
        this.acctLevelChanges = acctLevelChanges;
    }

    public String getCustLevelChanges() {
        return custLevelChanges;
    }

    public void setCustLevelChanges(String custLevelChanges) {
        this.custLevelChanges = custLevelChanges;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(String functionValue) {
        this.functionValue = functionValue;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
    String sNo;

    public String getCurCod() {
        return curCod;
    }

    public void setCurCod(String curCod) {
        this.curCod = curCod;
    }
    String curCod;

    public String getFuncToSelect() {
        return funcToSelect;
    }

    public void setFuncToSelect(String funcToSelect) {
        this.funcToSelect = funcToSelect;
    }

    public List<SelectItem> getFuncToSelectOption() {
        return funcToSelectOption;
    }

    public void setFuncToSelectOption(List<SelectItem> funcToSelectOption) {
        this.funcToSelectOption = funcToSelectOption;
    }

    public String getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(String passFlag) {
        this.passFlag = passFlag;
    }

    public String getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(String funcFlag) {
        this.funcFlag = funcFlag;
    }

    public String getDobFlag() {
        return dobFlag;
    }

    public void setDobFlag(String dobFlag) {
        this.dobFlag = dobFlag;
    }

    public int getFlagForCreditRate() {
        return flagForCreditRate;
    }

    public void setFlagForCreditRate(int flagForCreditRate) {
        this.flagForCreditRate = flagForCreditRate;
    }

    public List<SelectItem> getFunctionOption() {
        return functionOption;
    }

    public void setFunctionOption(List<SelectItem> functionOption) {
        this.functionOption = functionOption;
    }
    boolean flag6;

    public boolean isFlag6() {
        return flag6;
    }

    public void setFlag6(boolean flag6) {
        this.flag6 = flag6;
    }

    //Addition On 20/10/2015
    public boolean isChqAlertFlag() {
        return chqAlertFlag;
    }

    public void setChqAlertFlag(boolean chqAlertFlag) {
        this.chqAlertFlag = chqAlertFlag;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    //End here
    //Addition on 16/04/2016
    public boolean isBadPersonAlertFlag() {
        return badPersonAlertFlag;
    }

    public void setBadPersonAlertFlag(boolean badPersonAlertFlag) {
        this.badPersonAlertFlag = badPersonAlertFlag;
    }

    public String getIllegalMsg() {
        return illegalMsg;
    }

    public void setIllegalMsg(String illegalMsg) {
        this.illegalMsg = illegalMsg;
    }

    //End here 
    public String getCkycNo() {
        return ckycNo;
    }

    public void setCkycNo(String ckycNo) {
        this.ckycNo = ckycNo;
    }

    public String getCkycNoDisFlag() {
        return ckycNoDisFlag;
    }

    public void setCkycNoDisFlag(String ckycNoDisFlag) {
        this.ckycNoDisFlag = ckycNoDisFlag;
    }

    public boolean isCustFullNameMismatchFlag() {
        return custFullNameMismatchFlag;
    }

    public void setCustFullNameMismatchFlag(boolean custFullNameMismatchFlag) {
        this.custFullNameMismatchFlag = custFullNameMismatchFlag;
    }

    public CustomerDetail() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            mapMessage = "";
            verifyCustIdList = new ArrayList<SelectItem>();
            map = new HashMap<String, String>();
            getDropDownList();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getDropDownList() {
        try {
            functionOption = new ArrayList<SelectItem>();
            functionOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> currencyCodeOptionList = masterDelegate.getFunctionValues("179");
            for (CbsRefRecTypeTO recTypeTO : currencyCodeOptionList) {
                functionOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void functionRefDesc() {
        if (this.function.equalsIgnoreCase("0")) {
            this.setErrorMsg("Please Select Function.");
            return;
        }
        this.setCustId("");
        this.setErrorMsg("");
        custIdComboDisFlag = "none";
        mapMessageDisFlag = "none";
        custIdInputDisFlag = "";
        if (function.equalsIgnoreCase("A")) {
            this.setFunctionValue("ADD");
            this.flag = true;
            funcFlag = "true";
            flagForSelect = false;
            disableTab = false;
        } else if (this.function.equalsIgnoreCase("I")) {
            this.setFunctionValue("INQUIRY");
            this.flag = false;
            funcFlag = "false";
            flagForSelect = true;
            disableTab = false;
        } else if (this.function.equalsIgnoreCase("M")) {
            this.setFunctionValue("MODIFY");
            this.flag = false;
            funcFlag = "false";
            flagForSelect = false;
            disableTab = false;
        } else if (this.function.equalsIgnoreCase("S")) {
            this.setFunctionValue("SUSPEND");
            this.flag = false;
            funcFlag = "false";
            flagForSelect = false;
            disableTab = true;
        } else if (this.function.equalsIgnoreCase("U")) {
            functionValue = "UNSUSPEND";
            this.flag = false;
            funcFlag = "false";
            flagForSelect = false;
            disableTab = true;
        } else if (this.function.equalsIgnoreCase("V")) {
            functionValue = "VERIFY";
            this.flag = false;
            funcFlag = "false";
            flagForSelect = false;
            custIdComboDisFlag = "";
            mapMessageDisFlag = "";
            custIdInputDisFlag = "none";
            mapMessage = "";
            disableTab = false;
            getUnVerifyRecords();
        } else {
            this.setErrorMsg("Please fill the right entry");
            functionValue = "";
            disableTab = false;
        }
    }

    public void getUnVerifyRecords() {
        try {
            String mapMsg = "";
            verifyCustIdList = new ArrayList<SelectItem>();
            map = new HashMap<String, String>();
            List<Object[]> unVerifyCustIdList = masterDelegate.getUnVerifyCustIdsByBrnCode(getOrgnBrCode());
            verifyCustIdList.add(new SelectItem("0", "--Select--"));
            if (!unVerifyCustIdList.isEmpty()) {
                for (int i = 0; i < unVerifyCustIdList.size(); i++) {
                    Object[] objArray = unVerifyCustIdList.get(i);
                    CbsCustomerMasterDetail cbsCustomerMasterDetail = (CbsCustomerMasterDetail) objArray[0];
                    CbsCustMisinfo cbsCustMISInfo = (CbsCustMisinfo) objArray[1];

                    if (cbsCustomerMasterDetail != null) {
                        verifyCustIdList.add(new SelectItem(cbsCustomerMasterDetail.getCustomerid()));
                    }
                    if (cbsCustMISInfo != null) {
                        if (cbsCustMISInfo.getStatusCode() == null ? false : cbsCustMISInfo.getStatusCode().equalsIgnoreCase("A")) {
                            mapMsg = "Added Customer Verification";
                        } else if (cbsCustMISInfo.getStatusCode() == null ? false : cbsCustMISInfo.getStatusCode().equalsIgnoreCase("M")) {
                            mapMsg = "Modified Customer Verification";
                        } else if (cbsCustMISInfo.getStatusCode() == null ? false : cbsCustMISInfo.getStatusCode().equalsIgnoreCase("S")) {
                            mapMsg = "Suspended Customer Verification";
                        } else if (cbsCustMISInfo.getStatusCode() == null ? false : cbsCustMISInfo.getStatusCode().equalsIgnoreCase("U")) {
                            mapMsg = "Un-Suspended Customer Verification";
                        }
                        map.put(cbsCustMISInfo.getCustomerId(), mapMsg);
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public String exitForm() {
        this.setErrorMsg("");
        this.setFunction("0");
        this.setFunctionValue("");
        this.setCustId("");
        return "case1";
    }
//
//    /*********************************************Changes on 14/12/2010*************************************/

    public void cacelButton() {
        flagForSelect = false;
        disableTab = false;
        custIdComboDisFlag = "none";
        mapMessageDisFlag = "none";
        custIdInputDisFlag = "";
        mapMessage = "";
        verifyCustId = "0";
        this.setErrorMsg("");
        this.setFunction("0");
        this.setFunctionValue("");
        this.setCustId("");
        //20/10/2015
        this.setChqAlertFlag(false);
        this.setConfirmationMsg("");
        //End here
    }
}
