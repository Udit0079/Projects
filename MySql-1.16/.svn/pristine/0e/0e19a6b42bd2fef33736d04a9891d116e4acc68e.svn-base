/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLoanSchemeDetailsTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.CbsSchemePopUpFormsTO;
import com.cbs.servlets.Init;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class SchemeMaster extends BaseBean {

//Variables For schememaster.jsp file
    private static final Logger logger = Logger.getLogger(SchemeMaster.class);
    String message;
    String functionFlag;
    List<SelectItem> functionOption;
    String functionValue;
    String currencyType;
    boolean flag = false;
    List<SelectItem> ddCurrencyCode;
    String schemeCode;
    String schemeType;
    boolean schemeTypeflag = false;
    List<SelectItem> ddSchemeTypeCode;
    boolean schemTypeDisable;
    String pageType;
    List<SelectItem> ddPageOption;
    String formName;
    String viewID = "/pages/master/sm/test.jsp";
    private List<SelectItem> ddtrnRefNo;
    private boolean popUpFlag;
    private boolean currencyCodeFlag;
    String msg;
    CommonReportMethodsRemote common;

    //Getter-Setter for schememaster.jsp file
    public boolean isCurrencyCodeFlag() {
        return currencyCodeFlag;
    }

    public void setCurrencyCodeFlag(boolean currencyCodeFlag) {
        this.currencyCodeFlag = currencyCodeFlag;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public List<SelectItem> getDdCurrencyCode() {
        return ddCurrencyCode;
    }

    public void setDdCurrencyCode(List<SelectItem> ddCurrencyCode) {
        this.ddCurrencyCode = ddCurrencyCode;
    }

    public List<SelectItem> getDdPageOption() {
        return ddPageOption;
    }

    public void setDdPageOption(List<SelectItem> ddPageOption) {
        this.ddPageOption = ddPageOption;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public List<SelectItem> getFunctionOption() {
        return functionOption;
    }

    public void setFunctionOption(List<SelectItem> functionOption) {
        this.functionOption = functionOption;
    }

    public String getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(String functionValue) {
        this.functionValue = functionValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public boolean isSchemTypeDisable() {
        return schemTypeDisable;
    }

    public void setSchemTypeDisable(boolean schemTypeDisable) {
        this.schemTypeDisable = schemTypeDisable;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public List<SelectItem> getDdSchemeTypeCode() {
        return ddSchemeTypeCode;
    }

    public void setDdSchemeTypeCode(List<SelectItem> ddSchemeTypeCode) {
        this.ddSchemeTypeCode = ddSchemeTypeCode;
    }

    public boolean isSchemeTypeflag() {
        return schemeTypeflag;
    }

    public void setSchemeTypeflag(boolean schemeTypeflag) {
        this.schemeTypeflag = schemeTypeflag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public List<SelectItem> getDdtrnRefNo() {
        return ddtrnRefNo;
    }

    public void setDdtrnRefNo(List<SelectItem> ddtrnRefNo) {
        this.ddtrnRefNo = ddtrnRefNo;
    }

    public boolean isPopUpFlag() {
        return popUpFlag;
    }

    public void setPopUpFlag(boolean popUpFlag) {
        this.popUpFlag = popUpFlag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //Functionality for schememaster.jsp file
    /** Creates a new instance of SchemeMaster */
    public SchemeMaster() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            String orgnBrIp = WebUtil.getClientIP(this.getHttpRequest());
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            setOrgnBrCode(Init.IP2BR.getBranch(localhost));
            setUserName(getHttpRequest().getRemoteUser());

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            functionOption = new ArrayList<SelectItem>();
            functionOption.add(new SelectItem("A", "ADD"));
            functionOption.add(new SelectItem("M", "MODIFY"));
            functionOption.add(new SelectItem("I", "INQUIRY"));

            ddPageOption = new ArrayList<SelectItem>();
            ddPageOption.add(new SelectItem("0", "--Select--"));

            //For Currency Code
            List<CbsRefRecTypeTO> cbsRefRecTOs = schemeMasterManagementDelegate.getCurrencyCode("176");
            if (cbsRefRecTOs.size() > 0) {
                ddCurrencyCode = new ArrayList<SelectItem>();
                ddCurrencyCode.add(new SelectItem("0", "--Select--"));
                for (CbsRefRecTypeTO cbsRefRec : cbsRefRecTOs) {
                    CbsRefRecTypePKTO cbsRefPK = cbsRefRec.getCbsRefRecTypePKTO();
                    ddCurrencyCode.add(new SelectItem(cbsRefPK.getRefCode(), cbsRefRec.getRefDesc()));
                }
            } else {
                ddCurrencyCode = new ArrayList<SelectItem>();
                ddCurrencyCode.add(new SelectItem("0", "--Select--"));
            }

            //For Scheme Type
//            List<CbsRefRecTypeTO> cbsRefRecTypeTOs = schemeMasterManagementDelegate.getCurrencyCode("046");            
//            if (cbsRefRecTypeTOs.size() > 0) {
//                ddSchemeTypeCode = new ArrayList<SelectItem>();
//                ddSchemeTypeCode.add(new SelectItem("0", "--Select--"));
//                for (CbsRefRecTypeTO cbsRefRec : cbsRefRecTypeTOs) {
//                    CbsRefRecTypePKTO cbsRefPK = cbsRefRec.getCbsRefRecTypePKTO();
//                    ddSchemeTypeCode.add(new SelectItem(cbsRefPK.getRefCode(), cbsRefPK.getRefCode()));
//                }
//            } else {
//                ddSchemeTypeCode = new ArrayList<SelectItem>();
//                ddSchemeTypeCode.add(new SelectItem("0", "--Select--"));
//            }
            List atm = common.getAccTypeExcludePO();
            if (atm.size() > 0) {
                ddSchemeTypeCode = new ArrayList<SelectItem>();
                ddSchemeTypeCode.add(new SelectItem("0", "--Select--"));
                for (int j = 0; j < atm.size(); j++) {
                    Vector vec = (Vector) atm.get(j);
                    ddSchemeTypeCode.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
                }
            } else {
                ddSchemeTypeCode = new ArrayList<SelectItem>();
                ddSchemeTypeCode.add(new SelectItem("0", "--Select--"));
            }

            ddtrnRefNo = new ArrayList<SelectItem>();
            ddtrnRefNo.add(new SelectItem("0", ""));
            ddtrnRefNo.add(new SelectItem("Y", "Yes"));
            ddtrnRefNo.add(new SelectItem("N", "No"));
            flag = true;

        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void funtionAction() {
        try {
            this.flag = false;
            if (functionFlag == null || functionFlag.equalsIgnoreCase("")) {
                setMessage("Please Enter the Function you want to perform-->(A==> ADD, M==> Modify, I= Enquery) ");
                return;
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                List<CbsRefRecTypeTO> cbsRefRecTOs = schemeMasterManagementDelegate.getCurrencyCode("179");
                if (cbsRefRecTOs.size() > 0) {
                    for (CbsRefRecTypeTO cbsRefRec : cbsRefRecTOs) {
                        CbsRefRecTypePKTO cbsRefPK = cbsRefRec.getCbsRefRecTypePKTO();
                        if (this.functionFlag.equalsIgnoreCase(cbsRefPK.getRefCode())) {
                            this.setFunctionValue(cbsRefRec.getRefDesc());
                        }
                    }
                }
                if (this.functionFlag.equalsIgnoreCase("A")) {
                    this.setMessage("");
                } else if (this.functionFlag.equalsIgnoreCase("M")) {
                    this.setMessage("");
                    this.popUpFlag = true;
                } else if (this.functionFlag.equalsIgnoreCase("I")) {
                    this.setMessage("");
                    this.popUpFlag = true;
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void schemeCorrespondData() {
        try {
            this.setMessage("");
            if (this.functionFlag.equalsIgnoreCase("A")) {
                this.flag = false;
                this.schemeTypeflag = false;
                this.currencyCodeFlag = false;
                String msg = "";
            } else if (this.functionFlag.equalsIgnoreCase("M")) {
                this.flag = false;
                this.schemeTypeflag = false;
                this.currencyCodeFlag = false;
                getCBSSchLoanSchDtlsTO();
            } else if (this.functionFlag.equalsIgnoreCase("I")) {
                this.flag = true;
                this.schemeTypeflag = true;
                this.currencyCodeFlag = true;
                getCBSSchLoanSchDtlsTO();
                getForms();
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getCBSSchLoanSchDtlsTO() {
        CbsSchemeLoanSchemeDetailsTO cbsSchLoanSchDtlsTO = new CbsSchemeLoanSchemeDetailsTO();
        try {
            //According to Mr Alok Yadav to use tables for join.
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            cbsSchLoanSchDtlsTO = schemeMasterManagementDelegate.getSchTypeAndCurCodeBySchCode(this.schemeCode);
            if (cbsSchLoanSchDtlsTO != null) {
                this.setCurrencyType(cbsSchLoanSchDtlsTO.getCurrencyCode());
                this.setSchemeType(cbsSchLoanSchDtlsTO.getSchemeType());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getForms() {
        try {
//            if (this.functionFlag.equalsIgnoreCase("I")) {       //Modified by Ankit for because it is already being set by above method.
//                this.setSchemeType(this.schemeCode.substring(0, 2));
//            }
            ddPageOption = new ArrayList<SelectItem>();
            ddPageOption.add(new SelectItem("0", "--Select--"));
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemePopUpFormsTO> cbsSchemePopUpFormsTOs = schemeMasterManagementDelegate.getForms(this.schemeType);
            if (cbsSchemePopUpFormsTOs.size() > 0) {
                for (CbsSchemePopUpFormsTO popUp : cbsSchemePopUpFormsTOs) {
                    ddPageOption.add(new SelectItem(popUp.getFormId(), popUp.getFormName()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getPopUpForms() {
        this.setMessage("");
        try {
            if (this.functionFlag.equalsIgnoreCase("") || this.functionFlag == null) {
                this.setMessage("Please select function.");
                return;
            }
            if (this.currencyType.equalsIgnoreCase("") || this.currencyType == null || this.currencyType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select currency code.");
                return;
            }
            if (this.schemeCode.equalsIgnoreCase("") || this.schemeCode == null) {
                this.setMessage("Please fill scheme code.");
                return;
            }
            if (this.schemeType.equalsIgnoreCase("") || this.schemeType == null || this.schemeType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select scheme type.");
                return;
            }
            if (this.pageType == null || this.pageType.equalsIgnoreCase("")) {
                this.setMessage("Please select form");
                return;
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsSchemePopUpFormsTO cbsSchemePopUpForms = schemeMasterManagementDelegate.getPopForms(this.pageType);
                this.setFormName(cbsSchemePopUpForms.getFormName());
                this.setViewID("/pages/master/sm/" + this.pageType + ".jsp");
                showingMessageOnForms();
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refresh() {
        this.setMessage("");
        this.setFunctionFlag("A");
        this.setCurrencyType("0");
        this.setSchemeCode("");
        this.setSchemeType("0");
        this.setPageType("0");
        this.setFunctionValue("");
        this.flag = true;
        this.schemeTypeflag = false;
        this.currencyCodeFlag = false;
    }

    public String exitForm() {
        refresh();
        return "case1";
    }

    public void showingMessageOnForms() {
        try {
            OperationSchemeMaster operationSchemeMaster = new OperationSchemeMaster();
            SchemeMasterManagementDelegate delegateObj = new SchemeMasterManagementDelegate();
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("ad")) {
                msg = delegateObj.validSchemeMasterAd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("cad")) {
                msg = delegateObj.validSchemeMasterCad(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("aom")) {
                msg = delegateObj.validSchemeMasterAom(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("cd")) {
                msg = delegateObj.validSchemeMasterCd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    operationSchemeMaster.refreshList();
                    popUpFlag = false;
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("crbosd")) {
                msg = delegateObj.validSchemeMasterCrbosd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("csdd")) {
                msg = delegateObj.validSchemeMasterCsdd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("dd")) {
                msg = delegateObj.validSchemeMasterDd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("dfd")) {
                msg = delegateObj.validSchemeMasterDfd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("didd")) {
                msg = delegateObj.validSchemeMasterDidd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("doip")) {
                msg = delegateObj.validSchemeMasterDoip(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("dspm")) {
                msg = delegateObj.validSchemeMasterDspm(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("ffdd")) {
                msg = delegateObj.validSchemeMasterFfdd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("gshsd")) {
                msg = delegateObj.validSchemeMasterGshsd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("iscd")) {
                msg = delegateObj.validSchemeMasterIscd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("led")) {
                msg = delegateObj.validSchemeMasterLed(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("lfdcw")) {
                msg = delegateObj.validSchemeMasterLfdcw(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("lid")) {
                msg = delegateObj.validSchemeMasterLid(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("lpd")) {
                msg = delegateObj.validSchemeMasterLpd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("lpesd")) {
                msg = delegateObj.validSchemeMasterLpesd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("lrcd")) {
                msg = delegateObj.validSchemeMasterLrcd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("lsd")) {
                msg = delegateObj.validSchemeMasterLsd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    operationSchemeMaster.refreshList();
                    popUpFlag = false;
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("pm")) {
                msg = delegateObj.validSchemeMasterPm(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("sfcd")) {
                msg = delegateObj.validSchemeMasterSfcd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("spm")) {
                msg = delegateObj.validSchemeMasterSpm(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("strd")) {
                msg = delegateObj.validSchemeMasterStrd(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("svi")) {
                msg = delegateObj.validSchemeMasterSvi(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("tedcw")) {
                msg = delegateObj.validSchemeMasterTedcw(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
            if (functionFlag.equalsIgnoreCase("A") && pageType.equalsIgnoreCase("trccw")) {
                msg = delegateObj.validSchemeMasterTrccw(schemeCode);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Same code is already exist");
                    popUpFlag = false;
                    operationSchemeMaster.refreshList();
                } else {
                    popUpFlag = true;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
