/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.DlAcctOpenReg;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.AccountStatusSecurityFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;

import com.cbs.web.pojo.admin.TdLienMarkingGrid;
import com.cbs.web.pojo.admin.AcStatus;

import com.cbs.web.pojo.admin.SecurityDetail;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class SecurityDetails extends BaseBean {

    HttpServletRequest req;
    private String message;
    private String acno;
    private String name;
    private String estimationDateLbl;
    private String revalutionDateLbl;
    private String ValuationAmtLbl;
    private Date estimationDate = new Date();
    private String securityNature;
    private String securityType;
    private Date revalutionDate = new Date();
    private String securityDesc1;
    private String securityDesc2;
    private String securityDesc3;
    private String particular;
    private String valuationAmt;
    private String lienValue;
    private String margin;
    private String remark;
    private String status;
    private String otherAc;
    private int currentRowSD;
    private String markSecurities = "0";
    private boolean disableSecDec3;
    private boolean saveDisable;
    private boolean updateDisable;
    private String flagSecurity;
    private String groupCode;
    private String TypeOfSecurity;
    private List<SelectItem> statusList;
    private List<SelectItem> typeOfSecurityList;
    private List<SelectItem> securityNatureList;
    private List<SelectItem> securityDesc1List;
    private List<SelectItem> securityDesc2List;
    private List<SelectItem> securityDesc3List;
    private List<SelectItem> marksSecuritiesList;
    private List<SelectItem> securitiesTypeList;
    private String acCloseFlag;
    Validator validator;
    AccountOpeningFacadeRemote acOpenFacadeRemote;
    AccountStatusSecurityFacadeRemote statusMaintenanceFacade;
    CommonReportMethodsRemote reportMethodsRemote;
    FtsPostingMgmtFacadeRemote fts;

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public String getTypeOfSecurity() {
        return TypeOfSecurity;
    }

    public void setTypeOfSecurity(String TypeOfSecurity) {
        this.TypeOfSecurity = TypeOfSecurity;
    }

    public List<SelectItem> getSecuritiesTypeList() {
        return securitiesTypeList;
    }

    public void setSecuritiesTypeList(List<SelectItem> securitiesTypeList) {
        this.securitiesTypeList = securitiesTypeList;
    }
    private List<SecurityDetail> securityDetail;
    private SecurityDetail currentItemSD = new SecurityDetail();

    public String getFlagSecurity() {
        return flagSecurity;
    }

    public void setFlagSecurity(String flagSecurity) {
        this.flagSecurity = flagSecurity;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getMarkSecurities() {
        return markSecurities;
    }

    public void setMarkSecurities(String markSecurities) {
        this.markSecurities = markSecurities;
    }

    public List<SelectItem> getMarksSecuritiesList() {
        return marksSecuritiesList;
    }

    public void setMarksSecuritiesList(List<SelectItem> marksSecuritiesList) {
        this.marksSecuritiesList = marksSecuritiesList;
    }

    public SecurityDetail getCurrentItemSD() {
        return currentItemSD;
    }

    public void setCurrentItemSD(SecurityDetail currentItemSD) {
        this.currentItemSD = currentItemSD;
    }

    public int getCurrentRowSD() {
        return currentRowSD;
    }

    public void setCurrentRowSD(int currentRowSD) {
        this.currentRowSD = currentRowSD;
    }

    public boolean isDisableSecDec3() {
        return disableSecDec3;
    }

    public void setDisableSecDec3(boolean disableSecDec3) {
        this.disableSecDec3 = disableSecDec3;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SecurityDetail> getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(List<SecurityDetail> securityDetail) {
        this.securityDetail = securityDetail;
    }

    public List<SelectItem> getSecurityDesc1List() {
        return securityDesc1List;
    }

    public void setSecurityDesc1List(List<SelectItem> securityDesc1List) {
        this.securityDesc1List = securityDesc1List;
    }

    public List<SelectItem> getSecurityDesc2List() {
        return securityDesc2List;
    }

    public void setSecurityDesc2List(List<SelectItem> securityDesc2List) {
        this.securityDesc2List = securityDesc2List;
    }

    public List<SelectItem> getSecurityDesc3List() {
        return securityDesc3List;
    }

    public void setSecurityDesc3List(List<SelectItem> securityDesc3List) {
        this.securityDesc3List = securityDesc3List;
    }

    public String getValuationAmtLbl() {
        return ValuationAmtLbl;
    }

    public void setValuationAmtLbl(String ValuationAmtLbl) {
        this.ValuationAmtLbl = ValuationAmtLbl;
    }

    public String getRevalutionDateLbl() {
        return revalutionDateLbl;
    }

    public void setRevalutionDateLbl(String revalutionDateLbl) {
        this.revalutionDateLbl = revalutionDateLbl;
    }

    public Date getEstimationDate() {
        return estimationDate;
    }

    public void setEstimationDate(Date estimationDate) {
        this.estimationDate = estimationDate;
    }

    public String getEstimationDateLbl() {
        return estimationDateLbl;
    }

    public void setEstimationDateLbl(String estimationDateLbl) {
        this.estimationDateLbl = estimationDateLbl;
    }

    public String getLienValue() {
        return lienValue;
    }

    public void setLienValue(String lienValue) {
        this.lienValue = lienValue;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getOtherAc() {
        return otherAc;
    }

    public void setOtherAc(String otherAc) {
        this.otherAc = otherAc;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRevalutionDate() {
        return revalutionDate;
    }

    public void setRevalutionDate(Date revalutionDate) {
        this.revalutionDate = revalutionDate;
    }

    public String getSecurityDesc1() {
        return securityDesc1;
    }

    public void setSecurityDesc1(String securityDesc1) {
        this.securityDesc1 = securityDesc1;
    }

    public String getSecurityDesc2() {
        return securityDesc2;
    }

    public void setSecurityDesc2(String securityDesc2) {
        this.securityDesc2 = securityDesc2;
    }

    public String getSecurityDesc3() {
        return securityDesc3;
    }

    public void setSecurityDesc3(String securityDesc3) {
        this.securityDesc3 = securityDesc3;
    }

    public String getSecurityNature() {
        return securityNature;
    }

    public void setSecurityNature(String securityNature) {
        this.securityNature = securityNature;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValuationAmt() {
        return valuationAmt;
    }

    public void setValuationAmt(String valuationAmt) {
        this.valuationAmt = valuationAmt;
    }

    public List<SelectItem> getSecurityNatureList() {
        return securityNatureList;
    }

    public void setSecurityNatureList(List<SelectItem> securityNatureList) {
        this.securityNatureList = securityNatureList;
    }

    public List<SelectItem> getTypeOfSecurityList() {
        return typeOfSecurityList;
    }

    public void setTypeOfSecurityList(List<SelectItem> typeOfSecurityList) {
        this.typeOfSecurityList = typeOfSecurityList;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcCloseFlag() {
        return acCloseFlag;
    }

    public void setAcCloseFlag(String acCloseFlag) {
        this.acCloseFlag = acCloseFlag;
    }
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public SecurityDetails() {
        req = getRequest();
        try {
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            statusMaintenanceFacade = (AccountStatusSecurityFacadeRemote) ServiceLocator.getInstance().lookup("AccountStatusSecurityFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            lienMark = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            //setUserName("Manager1");
            setUserName(req.getRemoteUser());
            validator = new Validator();
            if (req.getParameter("code") == null || req.getParameter("code").toString().equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(req.getParameter("code").toString());
                this.setAcno(req.getParameter("code").toString().substring(0, 12));
                this.setName(req.getParameter("code").toString().substring(req.getParameter("code").toString().indexOf(":", 0) + 1));
                securityDetailsTableValues();
            }
            setMessage("");
            getTableValues();
            setSaveDisable(true);
            setUpdateDisable(true);
            securityDetail = new ArrayList<SecurityDetail>();
            setEstimationDateLbl("Estimation Date");
            setValuationAmtLbl("Valuation Amt");
            setRevalutionDateLbl("Revalution Date");
            MarkSecurity();
            securityDesc1List = new ArrayList<SelectItem>();
            securityDesc1List.add(new SelectItem("0", " "));
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc2List.add(new SelectItem("0", " "));
            securityDesc3List = new ArrayList<SelectItem>();
            securityDesc3List.add(new SelectItem("0", " "));

            /**
             * FOR TD LIEN MARKING*
             */
            this.setErrorMessage("");
            this.setMessage("");
            lienMarkOptionList = new ArrayList<SelectItem>();
            lienMarkOptionList.add(new SelectItem("--Select--"));
            lienMarkOptionList.add(new SelectItem("Yes"));
            lienMarkOptionList.add(new SelectItem("No"));

//            typeOfSecurityList = new ArrayList<SelectItem>();
//            typeOfSecurityList.add(new SelectItem("0", ""));
//            typeOfSecurityList.add(new SelectItem("C", "COLLATERAL"));
//            typeOfSecurityList.add(new SelectItem("P", "PRIMARY"));

            this.setFlag1(true);
            securityDetailsTableValues();

            /**
             * FOR ACCOUNT STATUS *
             */
            setReportDate(sdf.parse(getTodayDate()));
            reFresh();
            setWefDate(sdf.parse(getTodayDate()));
            setWefDate1(ymd.format(wefDate));
            lienacNumber();
            setFlagLienMark(true);

        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }

    }

    public void getTableValues() {
        try {
            List resultList = new ArrayList();
            resultList = statusMaintenanceFacade.getStatus();
            if (resultList.size() <= 0) {
                setMessage("No Data Exist");
                return;
            } else {
                statusList = new ArrayList<SelectItem>();
                statusList.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    statusList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            typeOfSecurityList = new ArrayList<SelectItem>();
            typeOfSecurityList.add(new SelectItem("0", " "));
            typeOfSecurityList.add(new SelectItem("DATED", "DATED"));
            typeOfSecurityList.add(new SelectItem("NON-DATED", "NON-DATED"));

            securityNatureList = new ArrayList<SelectItem>();
            securityNatureList.add(new SelectItem("0", " "));
            securityNatureList.add(new SelectItem("P", "PRIMARY"));
            securityNatureList.add(new SelectItem("C", "COLLATERAL"));

            marksSecuritiesList = new ArrayList<SelectItem>();
            marksSecuritiesList.add(new SelectItem("1", "Recurring Deposite/Mini Deposite"));
            marksSecuritiesList.add(new SelectItem("2", "Term Deposite"));
            marksSecuritiesList.add(new SelectItem("3", "Other"));

        } catch (Exception ex) {
            //System.err.println("Caught an unexpected exception!");
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void changeLabel() {
        if (setSecurityDescription1().equalsIgnoreCase("false")) {
            return;
        }
        if (securityType.equalsIgnoreCase("DATED")) {
            setEstimationDateLbl("Issue Date");
            setValuationAmtLbl("Maturity Value");
            setRevalutionDateLbl("Maturity Date");
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc3List = new ArrayList<SelectItem>();
            setDisableSecDec3(true);
        } else if (securityType.equalsIgnoreCase("NON-DATED")) {
            setEstimationDateLbl("Estimation Date");
            setValuationAmtLbl("Valuation Amt");
            setRevalutionDateLbl("Revalution Date");
            setDisableSecDec3(false);
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc3List = new ArrayList<SelectItem>();
        } else {
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc3List = new ArrayList<SelectItem>();
            securityDesc1List = new ArrayList<SelectItem>();


        }
    }

    public void onChangeOFSecurityDesc1() {
        if (setSecurityDescription2().equalsIgnoreCase("false")) {
            return;
        }
        if (securityDesc2 != null) {
            if (securityDesc1.equalsIgnoreCase("secured advances")) {
                setDisableSecDec3(false);
            }
            if (securityDesc1.equalsIgnoreCase("bill purchased/discount")) {
                setDisableSecDec3(true);
            }
            if (securityDesc1.equalsIgnoreCase("unsecured advances")) {
                setDisableSecDec3(true);
            }
        }
    }

    public void onChangeOFSecurityDesc2() {
        if (setSecurityDescription3().equalsIgnoreCase("false")) {
            return;
        }
        if (securityDesc2 != null) {
            if (securityDesc2.equalsIgnoreCase("PLEDGE")) {
                setDisableSecDec3(false);
            } else if (securityDesc2.equalsIgnoreCase("merchandise")) {
                setDisableSecDec3(false);
            }

        }
    }

    public String setSecurityDescription1() {
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessage("Please Select the Security Type.");
            return "false";
        }
        try {
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getSecurityDesc1(securityType);
            if (resultList.size() <= 0) {
                securityDesc1List.clear();
                //  setMessage("No Data Exist");
                return "false";
            } else {
                securityDesc1List = new ArrayList<SelectItem>();
                securityDesc1List.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc1List.add(new SelectItem(ele.get(0).toString()));
                }
            }
            return "true";

        } catch (Exception ex) {
            //System.err.println("Caught an unexpected exception!");
            setMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public String setSecurityDescription2() {
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessage("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessage("Please Select the securityDesc1.");
            return "false";
        }
        try {
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getSecurityDesc2(securityType, securityDesc1);
            if (resultList.size() <= 0) {
                //  setMessage("No Data Exist");
                return "false";
            } else {
                securityDesc2List = new ArrayList<SelectItem>();
                securityDesc2List.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc2List.add(new SelectItem(ele.get(0).toString()));
                }
            }
            return "true";
        } catch (Exception ex) {
            //System.err.println("Caught an unexpected exception!");
            setMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public String setSecurityDescription3() {
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessage("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessage("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0") || securityDesc2.equalsIgnoreCase("")) {
            setMessage("Please Select the securityDesc2.");
            return "false";
        }
        try {
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getSecurityDesc3(securityType, securityDesc1, securityDesc2);
            if (resultList.size() <= 0) {
                // setMessage("No Data Exist");
                return "false";
            } else {
                securityDesc3List = new ArrayList<SelectItem>();
                securityDesc3List.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc3List.add(new SelectItem(ele.get(0).toString()));
                }
            }

            return "true";
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public void saveSecurityDetails() throws ParseException {
        System.out.println("remark -->" + remark);
        setMessage("");
        if (validation().equalsIgnoreCase("False")) {
            return;
        }
        if (securityDesc1 == null || securityDesc1.equalsIgnoreCase("0")) {
            setSecurityDesc1("");
        }
        if (securityDesc2 == null || securityDesc2.equalsIgnoreCase("0")) {
            setSecurityDesc2("");
        }
        if (securityDesc3 == null || securityDesc3.equalsIgnoreCase("0")) {
            setSecurityDesc3("");
        }
        if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
            setValuationAmt("0");
        }
        if (lienValue == null || lienValue.equalsIgnoreCase("")) {
            setLienValue("0");
        }
        if (margin == null || margin.equalsIgnoreCase("")) {
            setMargin("0");
        }
        try {
            setSecurityCode();
            String msg;
            msg = statusMaintenanceFacade.saveSecurityDetail(acno, securityNature, securityType, status,
                    securityDesc1, securityDesc2, securityDesc3, particular, otherAc,
                    Float.parseFloat(valuationAmt), ymd.format(revalutionDate), ymd.format(estimationDate), Float.parseFloat(lienValue), getUserName(), remark,
                    ymd.format(sdf.parse(getTodayDate())), Float.parseFloat(margin), "N", groupCode, "0", "");
            setMessage(msg);
            if (msg.equalsIgnoreCase("Data Saved Successfully")) {
                securityDetailsTableValues();
                resetValues();
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void resetPage() {
        setMessage("");
        resetValues();
        securityDetail = new ArrayList<SecurityDetail>();
    }

    public void resetValues() {
        setLienValue("");
        setMargin("");
        setOtherAc("");
        setParticular("");
        setRemark("");
        setSecurityDesc1("");
        setSecurityDesc2("");
        setSecurityDesc3("");
        setSecurityNature("0");
        setSecurityType("");
        setStatus("0");
        setValuationAmt("");
    }

    public void setRemarks() {

        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessage("Please Select the Security Type.");
            return;
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessage("Please Select the securityDesc1.");
            return;
        }
        if (securityDesc1.equalsIgnoreCase("0")) {
            setRemark(securityType);
        } else {
            setRemark(securityType + ":" + securityDesc1);
        }
    }

    public String validation() throws ParseException {
        String msg = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (securityType == null || securityType.equals("0")) {
            setMessage("Please Select the Security Type.");
            return "false";
        }
        if (securityNature == null || securityNature.equals("0")) {
            setMessage("Please Select Security Nature.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0")) {
            setMessage("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0")) {
            setMessage("Please Select the securityDesc2.");
            return "false";
        }
        if (particular == null || particular.equals("")) {
            setMessage("Please Enter Particulars.");
            return "false";
        }

        if (!(valuationAmt == null || valuationAmt.equals(""))) {
            Matcher valuationAmtCheck = p.matcher(valuationAmt);
            if (!valuationAmtCheck.matches()) {
                setMessage("Valuation Amount/Maturity Value should be Numerical.");
                return "false";
            }
            if (!valuationAmt.matches("[0-9.]*")) {
                setMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                return "false";
            }
            if (this.valuationAmt.contains(".")) {
                if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                    this.setMessage("Invalid Valuation Amount/Maturity Value. Please Fill The Valuation Amount/Maturity Value Correctly.");
                    return "false";
                } else if (this.valuationAmt.substring(valuationAmt.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Enter The Valuation Amount/Maturity Value Upto Two Decimal Places.");
                    return "false";
                }
            }
        }
        if (!(lienValue == null || lienValue.equals(""))) {
            Matcher lienValueChec = p.matcher(lienValue);
            if (!lienValueChec.matches()) {
                setMessage("Lien Value No should be Numerical.");
                return "false";
            }
            if (!lienValue.matches("[0-9.]*")) {
                setMessage("Enter Numeric Value for Lien Value.");
                return "false";
            }
            if (this.lienValue.contains(".")) {
                if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                    this.setMessage("Invalid Lien Value. Please Fill The Lien Value Correctly.");
                    return "false";
                } else if (this.lienValue.substring(lienValue.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please Enter The Lien Value Upto Two Decimal Places.");
                    return "false";
                }
            }
        }
        if (!(margin != null || margin.equals(""))) {
            Matcher marginCheck = p.matcher(margin);
            if (!marginCheck.matches()) {
                setMessage("Margin should be Numerical.");
                return "false";
            } else {
                if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                    setMessage("Please Enter Valid Margin(1 to 99.9).");
                    return "false";
                }
            }
        }
        if (securityType.equalsIgnoreCase("DATED") || securityType.equalsIgnoreCase("NON-DATED")) {
            if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
                if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
                    setMessage("Please Enter Valid Maturity Value.");
                    return "false";
                } else {
                    Matcher valuationAmtCheck = p.matcher(valuationAmt);
                    if (!valuationAmtCheck.matches()) {
                        setMessage("valuation Amount should be Numerical.");
                        return "false";
                    }
                    if (!valuationAmt.matches("[0-9.]*")) {
                        setMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (this.valuationAmt.contains(".")) {
                        if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                            this.setMessage("Invalid Valuation Amount/Maturity Value. Please Fill The Valuation Amount/Maturity Value Correctly.");
                            return "false";
                        } else if (this.valuationAmt.substring(valuationAmt.indexOf(".") + 1).length() > 2) {
                            this.setMessage("Please Enter The Valuation Amount/Maturity Value Upto Two Decimal Places.");
                            return "false";
                        }
                    }
                }

            }
        }
        if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
            if (lienValue == null || lienValue.equalsIgnoreCase("")) {
                setMessage("Please Enter Valid Lien Amount.");
                return "false";
            } else {
                Matcher lienValueCheck = p.matcher(lienValue);
                if (!lienValueCheck.matches()) {
                    setMessage("Lien Value No should be Numerical.");
                    return "false";
                }
                if (!lienValue.matches("[0-9.]*")) {
                    setMessage("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        this.setMessage("Invalid Lien Value. Please Fill The Lien Value Correctly.");
                        return "false";
                    } else if (this.lienValue.substring(lienValue.indexOf(".") + 1).length() > 2) {
                        this.setMessage("Please Enter The Lien Value Upto Two Decimal Places.");
                        return "false";
                    }
                }
                if (margin == null || margin.equalsIgnoreCase("")) {
                    setMessage("Please Enter Margin .");
                    return "false";
                } else {
                    Matcher marginCheck = p.matcher(margin);
                    if (!marginCheck.matches()) {
                        setMessage("Margin should be Numerical.");
                        return "false";
                    } else {
                        if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                            setMessage("Please Enter Valid Margin(1 to 99.9).");
                            return "false";
                        }
                    }
                }
            }
        }

        if (!(lienValue.equalsIgnoreCase("") || lienValue == null) && !(valuationAmt.equalsIgnoreCase("")
                || valuationAmt == null)) {
            Matcher valuationAmtCheck = p.matcher(valuationAmt);
            Matcher lienValueCheck = p.matcher(lienValue);
            if (!valuationAmtCheck.matches()) {
                setMessage("valuation Amount should be Numerical");
                return "false";
            } else if (!lienValueCheck.matches()) {
                setMessage("Lien Value No should be Numerical");
                return "false";
            } else {
                if (!lienValue.matches("[0-9.]*")) {
                    setMessage("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        this.setMessage("Invalid Lien Value. Please Fill The Lien Value Correctly.");
                        return "false";
                    } else if (this.lienValue.substring(lienValue.indexOf(".") + 1).length() > 2) {
                        this.setMessage("Please Enter The Lien Value Upto Two Decimal Places.");
                        return "false";
                    }
                }

                if (!valuationAmt.matches("[0-9.]*")) {
                    setMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (this.valuationAmt.contains(".")) {
                    if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                        this.setMessage("Invalid Valuation Amount/Maturity Value. Please Fill The Valuation Amount/Maturity Value Correctly.");
                        return "false";
                    } else if (this.valuationAmt.substring(valuationAmt.indexOf(".") + 1).length() > 2) {
                        this.setMessage("Please Enter The Valuation Amount/Maturity Value Upto Two Decimal Places.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) > Float.parseFloat(valuationAmt)) {
                    if (securityType.equalsIgnoreCase("DATED")) {
                        setMessage("Maturity Value Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                    if (securityType.equalsIgnoreCase("NON-DATED")) {
                        setMessage("Valuation Amt. Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                }
            }
        }
        if (estimationDate.after(date)) {
            setMessage(" Estimation or Issue Date cannot be greater Than Present Date.");
            return "false";
        }

        if (estimationDate.after(revalutionDate)) {
            if (securityType.equalsIgnoreCase("DATED")) {
                setMessage("Maturity date should be greater than Issue date.");
                return "false";
            }
            if (securityType.equalsIgnoreCase("NON-DATED")) {
                setMessage("Estimation Date should be greater than Revalution date.");
                return "false";
            }
        }

        if (status == null || status.equals("0")) {
            setMessage("Please Select the Status.");
            return "false";
        }

        return "true";
    }

    public void securityDetailsTableValues() {
        //   System.out.println("securityDetailsTableValues");
        try {
            securityDetail = new ArrayList<SecurityDetail>();
            List resultList = new ArrayList();
            resultList = statusMaintenanceFacade.getSecurityTableValues(acno);
            // System.out.println("resultList -->" + resultList);
            if (resultList.size() <= 0) {
                // setMessage("No Data Exist");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector tableVector = (Vector) resultList.get(i);
                    SecurityDetail mt = new SecurityDetail();
                    mt.setSno(Integer.parseInt(tableVector.get(0).toString()));
                    mt.setMatValue(Float.parseFloat(tableVector.get(1).toString()));
                    if (tableVector.get(2).toString().equalsIgnoreCase("01/01/1900")) {
                        mt.setMatDate("");
                    } else {
                        mt.setMatDate(tableVector.get(2).toString());
                    }
                    if (tableVector.get(3).toString().equalsIgnoreCase("01/01/1900")) {
                        mt.setIssueDate("");
                    } else {
                        mt.setIssueDate(tableVector.get(3).toString());
                    }
                    mt.setLienValue(Float.parseFloat(tableVector.get(4).toString()));
                    mt.setType(tableVector.get(5).toString());
                    if (tableVector.get(6) != null) {
                        if (tableVector.get(6).toString().equalsIgnoreCase("01/01/1900")) {
                            mt.setLtSTMDate("");
                        } else {
                            mt.setLtSTMDate(tableVector.get(6).toString());
                        }
                    } else {
                        mt.setLtSTMDate("");
                    }

                    if (tableVector.get(7) != null) {
                        if (tableVector.get(7).toString().equalsIgnoreCase("01/01/1900")) {
                            mt.setNxSTMDate("");
                        } else {
                            mt.setNxSTMDate(tableVector.get(7).toString());
                        }
                    } else {
                        mt.setNxSTMDate("");
                    }

                    mt.setSTMFrequency(tableVector.get(8).toString());
                    mt.setRemark(tableVector.get(9).toString());
                    mt.setStatus(tableVector.get(10).toString());
                    mt.setEnteredBy(tableVector.get(11).toString());

                    if (tableVector.get(12) != null) {
                        if (tableVector.get(12).toString().equalsIgnoreCase("01/01/1900")) {
                            mt.setEnteredDate("");
                        } else {
                            mt.setEnteredDate(tableVector.get(12).toString());
                        }
                    } else {
                        mt.setEnteredDate("");
                    }

                    mt.setParticular(tableVector.get(13).toString());

                    if (tableVector.get(15) != null) {
                        if (tableVector.get(15).toString().equalsIgnoreCase("01/01/1900")) {
                            mt.setSTMDate("");
                        } else {
                            mt.setSTMDate(tableVector.get(15).toString());
                        }
                    } else {
                        mt.setSTMDate("");
                    }

                    float tempMargin = Float.parseFloat(tableVector.get(14).toString());
                    float tempLienVal = Float.parseFloat(tableVector.get(4).toString());
                    Float dpLt;
                    tempMargin = (1 - (tempMargin) / 100);
                    if ((tempMargin) > 0) {
                        dpLt = tempLienVal * tempMargin;
                    } else {
                        dpLt = tempLienVal;
                    }
                    mt.setDPLimit(dpLt.toString());
                    securityDetail.add(mt);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {

        String sNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Maturity Value"));
        currentRowSD = currentRowSD + 1;
        currentRowSD = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (SecurityDetail item : securityDetail) {
            System.out.println("item.getSno():" + item.getSno());
            if (item.getMatValue().equals(sNo)) {
                currentItemSD = item;
                break;
            }
        }
    }

    public void delete() {
        System.out.println("delete");
//        if(currentItemSD.getEnteredDate() == null){
//            return;
//        }

        try {
            String resultList;
            resultList = statusMaintenanceFacade.deleteSecurityTable(currentItemSD.getEnteredDate(), getTodayDate(), acno, currentItemSD.getSno());
            setMessage(resultList);
            // securityDetail.remove(currentRowSD);
            securityDetailsTableValues();

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    public void Updateselect() {
        try {
            //System.out.println("update");
            if (currentItemSD.particular == null) {
                return;
            }
            //System.out.println("currentItemSD.particular -->" + currentItemSD.particular);
            String acctNature;
            List resultList;
            acctNature = statusMaintenanceFacade.acctNature(currentItemSD.particular);
            //System.out.println("acctNature -->" + acctNature);
            if (acctNature != null) {
                if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                        || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    return;
                }
            }
            resultList = statusMaintenanceFacade.toUpdate(acno, currentItemSD.sno);
            //System.out.println("resultList -->" + resultList);
            if (resultList.size() < 0) {
                setMessage("Data is Not Set");
            } else {
                markSecurities = "3";
                Vector resultListV = (Vector) resultList.get(0);

                setParticular(resultListV.get(0).toString());
                setValuationAmt(resultListV.get(1).toString());
                if (!resultListV.get(2).toString().equalsIgnoreCase("")) {
                    setRevalutionDate(sdf.parse(resultListV.get(2).toString()));
                }
                setLienValue(resultListV.get(3).toString());

                if (resultListV.get(4).toString().equalsIgnoreCase("ACTIVE")) {
                    setStatus("1");
                }
                if (!resultListV.get(5).toString().equalsIgnoreCase("")) {
                    setEstimationDate(sdf.parse(resultListV.get(5).toString()));
                }
                setRemark(resultListV.get(6).toString());
                setSecurityNature(resultListV.get(7).toString());
                setMargin(resultListV.get(9).toString());
                setSecurityType(resultListV.get(10).toString());
                setSecurityDescription1();
                setSecurityDesc1(resultListV.get(8).toString());
                setSecurityDescription2();
                setSecurityDesc2(resultListV.get(11).toString());
                setSecurityDescription3();
                setSecurityDesc3(resultListV.get(12).toString());
                setUpdateDisable(false);
                setSaveDisable(true);

            }



        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    public void UpdateTable() throws ParseException {
        setMessage("");
        if (validation().equalsIgnoreCase("False")) {
            return;
        }

        if (securityDesc1 == null || securityDesc1.equalsIgnoreCase("0")) {
            setSecurityDesc1("");
        }
        if (securityDesc2 == null || securityDesc2.equalsIgnoreCase("0")) {
            setSecurityDesc2("");
        }
        if (securityDesc3 == null || securityDesc3.equalsIgnoreCase("0")) {
            setSecurityDesc3("");
        }
        if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
            setValuationAmt("0");
        }
        if (lienValue == null || lienValue.equalsIgnoreCase("")) {
            setLienValue("0");
        }
        if (margin == null || margin.equalsIgnoreCase("")) {
            setMargin("0");
        }
        try {
            setSecurityCode();
            // System.out.println("gropcode "+groupCode);
            String resultList;
            resultList = statusMaintenanceFacade.UpdateSecurity(acno, securityNature, securityType, status,
                    securityDesc1, securityDesc2, securityDesc3, particular, otherAc,
                    Float.parseFloat(valuationAmt), ymd.format(revalutionDate), ymd.format(estimationDate), Float.parseFloat(lienValue), getUserName(), remark,
                    ymd.format(sdf.parse(getTodayDate())), Float.parseFloat(margin), currentItemSD.getSno(), groupCode);
            if (resultList.equalsIgnoreCase("Record Has Been Updated")) {
                setMessage(resultList);
                securityDetailsTableValues();
                resetValues();
            }
            setUpdateDisable(false);
            securityDetailsTableValues();
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    public void select() {

        try {
            if (currentItemSD.getStatus().equalsIgnoreCase("ACTIVE")
                    || currentItemSD.getStatus().equalsIgnoreCase("MAT DATE EXPIRED")) {
                securityDetail.get(currentRowSD).setStatus("EXPIRED");
            } else {
                setMessage("Already EXPIRED");
                return;
            }

            String resultList;
            resultList = statusMaintenanceFacade.UpdateSecurityTable(getUserName(), ymd.format(sdf.parse(getTodayDate())), acno, currentItemSD.getSno());
            setMessage(resultList);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    public void setSecurityCode() {
        // System.out.println("dsfdsfdfsdf");
//        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
//           // setMessage("Please Select the Security Type.");
//            return;
//        }
//        if (securityDesc3 == null || securityDesc3.equals("0") || securityDesc3.equalsIgnoreCase("")) {
//           // setMessage("Please Select the securityDesc1.");
//            return;
//        }
//        if (securityDesc2 == null || securityDesc2.equals("0") || securityDesc2.equalsIgnoreCase("")) {
//           // setMessage("Please Select the securityDesc2.");
//            return;
//        }
        if (!(remark == null || remark.equals(""))) {
            remark = remark + ":A/C:" + otherAc;
        }
        try {
            groupCode = statusMaintenanceFacade.SecurityCode(securityType, securityDesc2, securityDesc3);
            //System.out.println("groupCode -->"+groupCode);

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }
    /**
     * *****************************************THIS CODE IS FOR TD LIEN MARKING FORM********************************
     */
    private List<DlAcctOpenReg> dlAcctOpen;
    LoanGenralFacadeRemote lienMark;
    private String errorMessage;
    private String messageLM;
    private String acctNo;
    private String custName;
    private String jtName;
    private String oprMode;
    private String lienMarkOption;
    private List<SelectItem> lienMarkOptionList;
    int currentRow;
    private List<TdLienMarkingGrid> gridDetail;
    private TdLienMarkingGrid currentItem = new TdLienMarkingGrid();
    private String recieptNo;
    private String controlNo;
    private String prinAmount;
    private String detail;
    private String statusLien;
    private String auth;
    private boolean flag1;
    private boolean flag2;

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageLM() {
        return messageLM;
    }

    public void setMessageLM(String messageLM) {
        this.messageLM = messageLM;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOprMode() {
        return oprMode;
    }

    public void setOprMode(String oprMode) {
        this.oprMode = oprMode;
    }

    public String getLienMarkOption() {
        return lienMarkOption;
    }

    public void setLienMarkOption(String lienMarkOption) {
        this.lienMarkOption = lienMarkOption;
    }

    public List<SelectItem> getLienMarkOptionList() {
        return lienMarkOptionList;
    }

    public void setLienMarkOptionList(List<SelectItem> lienMarkOptionList) {
        this.lienMarkOptionList = lienMarkOptionList;
    }

    public TdLienMarkingGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdLienMarkingGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TdLienMarkingGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TdLienMarkingGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getPrinAmount() {
        return prinAmount;
    }

    public void setPrinAmount(String prinAmount) {
        this.prinAmount = prinAmount;
    }

    public String getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(String recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatusLien() {
        return statusLien;
    }

    public void setStatusLien(String statusLien) {
        this.statusLien = statusLien;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public void customerDetail() {
        this.setErrorMessage("");
        this.setMessageLM("");
        this.setCustName("");
        this.setOprMode("");
        this.setJtName("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setFlag1(true);
        gridDetail = new ArrayList<TdLienMarkingGrid>();
        try {
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acctNo.length() < 12) {
                this.setErrorMessage("Please Enter 12 Digit Account No Carefully.");
                return;
            }
            String acctCode = fts.getAccountCode(acctNo);
            String acNature = fts.getAccountNature(acctNo);
            if (!acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) && !acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                this.setErrorMessage("Please Enter Account No. Of 'FD' Nature.");
                return;
            }
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            //has to be changed
            resultList = lienMark.customerDetail(this.acctNo);
            resultList1 = lienMark.gridDetailLoad(this.acctNo, acctCode);
            String authResult = lienMark.auth(this.getUserName(), this.getOrgnBrCode());
            this.setAuth(authResult);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCustName(ele.get(0).toString());
                    this.setOprMode(ele.get(1).toString());
                    this.setJtName(ele.get(2).toString());
                }
            } else {
                this.setErrorMessage("Account No. Does Not Exists !!!");
                return;
            }
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    TdLienMarkingGrid dt = new TdLienMarkingGrid();
                    dt.setAcNo(ele.get(0).toString());
                    dt.setVoucherNo(ele.get(1).toString());
                    dt.setReciept(ele.get(2).toString());
                    dt.setPrintAmt(ele.get(3).toString());
                    dt.setFddt(ele.get(4).toString());
                    dt.setMatDt(ele.get(5).toString());
                    dt.setTdmatDt(ele.get(6).toString());
                    dt.setIntOpt(ele.get(7).toString());
                    dt.setRoi(ele.get(8).toString());
                    dt.setStatus(ele.get(9).toString());
                    dt.setSeqNo(ele.get(10).toString());
                    if (ele.get(11).toString().equalsIgnoreCase("Y")) {
                        dt.setLien("Yes");
                    } else {
                        dt.setLien("No");
                    }

                    gridDetail.add(dt);
                }
            } else {
                this.setErrorMessage("No Voucher Exists in This Account No.");
                return;
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessageLM("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setDetail("");
        this.setLienMarkOption("--Select--");
        try {
            this.setControlNo(currentItem.getSeqNo());
            this.setRecieptNo(currentItem.getReciept());
            this.setStatusLien(currentItem.getLien());
            if (currentItem.getLien().equalsIgnoreCase("Yes")) {
                this.setLienMarkOption("No");
            } else {
                this.setLienMarkOption("Yes");
            }
            String result = lienMark.tdLienPresentAmount(this.acctNo, Float.parseFloat(currentItem.getVoucherNo()), Float.parseFloat(currentItem.getPrintAmt()));
            if (result == null) {
                this.setErrorMessage("PROBLEM IN GETTING PRESENT AMOUNT !!!");
                this.setFlag1(true);
                return;
            } else {
                int n = result.indexOf("*");
                this.setDetail(result.substring(0, n));
                this.setPrinAmount(result.substring(n + 1));
                this.setFlag1(false);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    String detailOfDlAccOpFrm = "";
    String lienValOfDlAccOpFrm = "";
    String Details = "";
    String descriptionMsg = "";

    public void dlAccCustIdOpen() {
        try {
            if (this.lienMarkOption.equalsIgnoreCase("YES")) {
                String flag = null;
                /**
                 * FOR CHECKING DUPLICATE ENTRY*
                 */
                List<DlAcctOpenReg> tmpTable = dlAcctOpen;

//////                for (int i = 0; i < tmpTable.size(); i++) {
//////                    String tmpAcno = tmpTable.get(i).getAcno();
//////////////////////////////                    String tmpVouchNo = tmpTable.get(i).getVoucherNo();
//////////////////////////////                    if (tmpAcno.equalsIgnoreCase(currentItem.getAcNo()) && tmpVouchNo.equalsIgnoreCase(currentItem.getVoucherNo())) {
//////////////////////////////                        this.setErrorMessage(currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + " Already Selected For Lien.");
//////////////////////////////                        flag = "false";
//////////////////////////////                    } else {
//////////////////////////////                        flag = "true";
//////////////////////////////                    }
                ////  }

                /**
                 * END*
                 */
                if (flag == null) {
                    valuesForDlForm();
                } else if (flag.equalsIgnoreCase("true")) {
                    valuesForDlForm();
                }

            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    String details;

    public void valuesForDlForm() {

        try {
            String result = lienMark.dlAcOpen();
            if (result == null) {
                descriptionMsg = "";
            } else {
                descriptionMsg = result;
            }
            /**
             * ****These are the values which are send to DL account form grid******
             */
            Details = currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + "; ROI:" + currentItem.getRoi() + " ; Present Amount:" + this.prinAmount;
            details = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):" + Details;
            //String finalString = "DATED" + "," + "PRIMARY" + "," + "LIEN" + "," + currentItem.getAcNo().substring(2, 4) + "," + this.prinAmount + "," + this.prinAmount + "," + currentItem.getMatDt() + "," + currentItem.getFddt() + "," + details + "," + this.userName + "," + this.todayDate + "," + currentItem.getAcNo();
            ///////////////////    dlTableValues();

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void bilLcBg() {
        try {
            if (this.lienMarkOption.equalsIgnoreCase("Yes")) {
                Details = currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + " ; ROI:" + currentItem.getRoi() + " ; Present Amount:" + this.prinAmount;
                String finalString1 = " PRIMARY" + "," + " Lien Marking Against TD" + "," + "TD" + "," + this.prinAmount + "," + this.prinAmount + "," + currentItem.getMatDt() + "," + currentItem.getFddt() + "," + detail + "," + this.getUserName() + "," + this.getTodayDate();
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    String DLAccOpen_Lien = "";
    String BillLcBg_Lien = "";
    String loanLienCall = "True";

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessageLM("");
        this.flag2 = true;
        String lAcNO = acno;
        try {
            // System.out.println("securitiesType -->" + TypeOfSecurity);
            DLAccOpen_Lien = "True";
            if (TypeOfSecurity == null || TypeOfSecurity.equalsIgnoreCase("")) {
                this.setErrorMessage("Please Select the Type Of Security");
                return;
            }
            String tmpSecType = TypeOfSecurity;
            dlAccCustIdOpen();

            if (this.lienMarkOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Lien Marking.");
                return;
            }
            //System.out.println("securitiesType -->" + TypeOfSecurity);
            String acCode = fts.getAccountCode(currentItem.getAcNo());
            String result = lienMark.saveLienMarkingDetail(Float.parseFloat(this.recieptNo), Float.parseFloat(currentItem.getVoucherNo()), acCode, currentItem.getAcNo(), lAcNO, this.lienMarkOption, this.auth, this.getUserName(), this.detail, this.loanLienCall, tmpSecType, DLAccOpen_Lien, BillLcBg_Lien, getOrgnBrCode(), "0", "", "0", "0");
            //System.out.println("result:=======" + result);
            if (result == null) {
                this.setErrorMessage("PROBLEM IN SAVE THE RECORD !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                } else {
                    this.setMessageLM(result);
                }
                securityDetailsTableValues();
            }

            this.setFlag1(true);
            this.setRecieptNo("");
            this.setControlNo("");
            this.setPrinAmount("");
            this.setStatusLien("");
            this.setLienMarkOption("--Select--");
            this.setDetail("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessageLM("");
        this.setCustName("");
        this.setAcctNo("");
        this.setJtName("");
        this.setOprMode("");
        this.setRecieptNo("");
        this.setControlNo("");
        this.setPrinAmount("");
        this.setStatusLien("");
        this.setTypeOfSecurity("0");
        this.setLienMarkOption("--Select--");
        this.setDetail("");
        this.setFlag1(true);
        gridDetail = new ArrayList<TdLienMarkingGrid>();

    }
    /**
     * START OF ACCOUNT STATUS FORM CODE*
     */
    private String messageAcct;
//    private String acctype;
//    private List<SelectItem> acctOption;
    private String acnoAcct;
//    private String code;
//    private String code1;
    private String oldcode;
    private String remarks;
    private String nameAcct;
    private String pStatus;
    private String nStatus;
    private Date wefDate;
    private Date reportDate;
    private List<AcStatus> incis;
    private String customer;
    private String statusAcct;
    private String lienAmt;
    private String lienAcNo;
    private String liencode;
    private String lienacctype;
    private List<SelectItem> lienacctOption;
    private String flagAcct;
    private String fflag = "false";
    private Date todate;
    private String wefDate1;
    private boolean flagLienMark;

    public String getOldcode() {
        return oldcode;
    }

    public void setOldcode(String oldcode) {
        this.oldcode = oldcode;
    }

    public boolean isFlagLienMark() {
        return flagLienMark;
    }

    public void setFlagLienMark(boolean flagLienMark) {
        this.flagLienMark = flagLienMark;
    }

    public String getWefDate1() {
        return wefDate1;
    }

    public void setWefDate1(String wefDate1) {
        this.wefDate1 = wefDate1;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public String getFflag() {
        return fflag;
    }

    public void setFflag(String fflag) {
        this.fflag = fflag;
    }

    public String getFlagAcct() {
        return flagAcct;
    }

    public void setFlagAcct(String flagAcct) {
        this.flagAcct = flagAcct;
    }

    public List<SelectItem> getLienacctOption() {
        return lienacctOption;
    }

    public void setLienacctOption(List<SelectItem> lienacctOption) {
        this.lienacctOption = lienacctOption;
    }

    public String getLienacctype() {
        return lienacctype;
    }

    public void setLienacctype(String lienacctype) {
        this.lienacctype = lienacctype;
    }

    public String getLiencode() {
        return liencode;
    }

    public void setLiencode(String liencode) {
        this.liencode = liencode;
    }

    public String getLienAcNo() {
        return lienAcNo;
    }

    public void setLienAcNo(String lienAcNo) {
        this.lienAcNo = lienAcNo;
    }

    public String getLienAmt() {
        return lienAmt;
    }

    public void setLienAmt(String lienAmt) {
        this.lienAmt = lienAmt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAcnoAcct() {
        return acnoAcct;
    }

    public void setAcnoAcct(String acnoAcct) {
        this.acnoAcct = acnoAcct;
    }

    public String getMessageAcct() {
        return messageAcct;
    }

    public void setMessageAcct(String messageAcct) {
        this.messageAcct = messageAcct;
    }

    public String getNameAcct() {
        return nameAcct;
    }

    public void setNameAcct(String nameAcct) {
        this.nameAcct = nameAcct;
    }

    public String getStatusAcct() {
        return statusAcct;
    }

    public void setStatusAcct(String statusAcct) {
        this.statusAcct = statusAcct;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getnStatus() {
        return nStatus;
    }

    public void setnStatus(String nStatus) {
        this.nStatus = nStatus;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getWefDate() {
        return wefDate;
    }

    public void setWefDate(Date wefDate) {
        this.wefDate = wefDate;
    }

    public List<AcStatus> getIncis() {
        return incis;
    }

    public void setIncis(List<AcStatus> incis) {
        this.incis = incis;
    }

    public void lienacNumber() {
        try {
            List resultList = new ArrayList();
            resultList = statusMaintenanceFacade.lienAcNo();
            lienacctOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    lienacctOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void custName() {
        try {
            String rresult = "";
            lienAmt = "0";
            lienAcNo = "";
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if ((oldcode.equals("")) || (oldcode == null)) {
                this.setMessageAcct("Please Enter Account Number");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (oldcode.length() < 12) {
                this.setMessageAcct("Please Enter Valid 12 digits Account Number.");
                this.setOldcode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            if (!validator.validateStringAllNoSpace(oldcode)) {
                this.setMessageAcct("Please Enter Valid Account Number.");
                this.setOldcode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            acnoAcct = fts.getNewAccountNumber(oldcode);
            if (!fts.getCurrentBrnCode(acnoAcct).equalsIgnoreCase(getOrgnBrCode())) {
                setMessageAcct("This is not your branch's account no. !");
                return;
            }
            String acctype = fts.getAccountCode(acnoAcct);

            //rresult = statusMaintenanceFacade.getCustNameAndStatus(acnoAcct, acctype);
            if (!rresult.equals(":")) {
                fflag = "true";
                if (rresult.contains(":")) {
                    String[] values = null;
                    String spliter = ":";
                    values = rresult.split(spliter);
                    this.setCustomer(values[0]);
                    this.setStatusAcct(values[1]);
                }
                setMessageAcct("");
                setNameAcct(customer);
                if (statusAcct.equals("1")) {
                    setpStatus("Operative");
                } else if (statusAcct.equals("2")) {
                    setpStatus("InOperative");
                } else if (statusAcct.equals("4")) {
                    setpStatus("Frozen");
                } else if (statusAcct.equals("5")) {
                    setpStatus("Recalled");
                } else if (statusAcct.equals("7")) {
                    setpStatus("Withdrawal Stopped");
                } else if (statusAcct.equals("8")) {
                    setpStatus("Operation Stopped");
                } else if (statusAcct.equals("10")) {
                    setpStatus("Lien Marked");
                } else if (statusAcct.equals("11")) {
                    setpStatus("SUB STANDARD");
                } else if (statusAcct.equals("12")) {
                    setpStatus("DOUBT FUL");
                } else if (statusAcct.equals("13")) {
                    setpStatus("LOSS");
                } else if (statusAcct.equals("14")) {
                    setpStatus("PROTESTED BILL");
                } else if (statusAcct.equals("3")) {
                    setpStatus("NP-Loss");
                } else if (statusAcct.equals("6")) {
                    setpStatus("Decreed");
                } else if (statusAcct.equals("9")) {
                    setpStatus("Closed");
                } else {
                    setpStatus("Operative");
                }
                setRemarks(pStatus);
                griddata(acnoAcct);
            } else {
                setMessageAcct("No Table Details Exist for this Account");
                reFresh1();
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void griddata(String acno) {
        incis = new ArrayList<AcStatus>();
        try {
            List list = new ArrayList();
            list = statusMaintenanceFacade.getStatusHistory(acno);
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                AcStatus tab = new AcStatus();
                tab.setAcctNo(v.get(0).toString());
                tab.setRemark(v.get(1).toString());
                if (v.get(2).toString().equals("1")) {
                    tab.setSpFlag("Operative");
                } else if (v.get(2).toString().equals("2")) {
                    tab.setSpFlag("InOperative");
                } else if (v.get(2).toString().equals("4")) {
                    tab.setSpFlag("Frozen");
                } else if (v.get(2).toString().equals("5")) {
                    tab.setSpFlag("Recalled");
                } else if (v.get(2).toString().equals("7")) {
                    tab.setSpFlag("Withdrawal Stopped");
                } else if (v.get(2).toString().equals("8")) {
                    tab.setSpFlag("Operation Stopped");
                } else if (v.get(2).toString().equals("10")) {
                    tab.setSpFlag("Lien Marked");
                } else if (v.get(2).toString().equals("11")) {
                    tab.setSpFlag("SUB STANDARD");
                } else if (v.get(2).toString().equals("12")) {
                    tab.setSpFlag("DOUBT FUL");
                } else if (v.get(2).toString().equals("13")) {
                    tab.setSpFlag("LOSS");
                } else if (v.get(2).toString().equals("14")) {
                    tab.setSpFlag("PROTESTED BILL");
                } else if (v.get(2).toString().equals("3")) {
                    tab.setSpFlag("NP-Loss");
                } else if (v.get(2).toString().equals("6")) {
                    tab.setSpFlag("Decreed");
                } else if (v.get(2).toString().equals("9")) {
                    tab.setSpFlag("Closed");
                } else {
                    tab.setSpFlag("Operative");
                }
                tab.setDate(v.get(3).toString());
                tab.setAmount(v.get(4).toString());
                tab.setAuth(v.get(5).toString());
                tab.setEnterBy(v.get(6).toString());
                tab.setEfftDate(v.get(7).toString());
                incis.add(tab);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void save1() {
        try {
            if (nStatus.equals("Lien Marked")) {
                flagAcct = "true";
                return;
            } else {
                flagAcct = "false";
                lienAmt = "0";
                lienAcNo = "";
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    public void save() {
        try {
            String DD = "0";
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if ((oldcode.equals("")) || (oldcode == null)) {
                this.setMessageAcct("Please Enter Account Number");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (getTodayDate().compareTo(sdf.format(wefDate)) < 0) {
                this.setMessageAcct("WEF Date is Greater than Today");
                setWefDate(sdf.parse(getTodayDate()));
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            if (!validator.validateStringAllNoSpace(oldcode)) {
                this.setMessageAcct("Please Enter Valid Account Number.");
                this.setOldcode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            if (this.nStatus.equalsIgnoreCase("--Select--")) {
                this.setMessageAcct("Please Select New Status");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            if ((remarks.equals("")) || (remarks == null)) {
                this.setMessageAcct("Please Enter Remarks");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (nStatus.equals("Operative")) {
                DD = "0";
            } else if (nStatus.equals("InOperative")) {
                DD = "1";
            } else if (nStatus.equals("Frozen")) {
                DD = "2";
            } else if (nStatus.equals("Recalled")) {
                DD = "3";
            } else if (nStatus.equals("Withdrawal Stopped")) {
                DD = "4";
            } else if (nStatus.equals("Operation Stopped")) {
                DD = "5";
            } else if (nStatus.equals("Lien Marked")) {
                DD = "6";
            } else if (nStatus.equals("NP-SubStandard")) {
                DD = "7";
            } else if (nStatus.equals("NP-Doubtful")) {
                DD = "8";
            } else if (nStatus.equals("NP-Loss")) {
                DD = "9";
            } else if (nStatus.equals("Protested Bill")) {
                DD = "10";
            }
            if (nStatus.equals("Lien Marked")) {
                if (!(liencode.equals("") || (liencode == null))) {
                    int length1 = liencode.length();
                    int addedZero1 = 6 - length1;
                    for (int i = 1; i <= addedZero1; i++) {
                        liencode = "0" + liencode;
                    }
                    lienAcNo = this.getOrgnBrCode() + this.lienacctype + liencode + "01";
                }
            }

            String s1 = onblurDate(wefDate);
            if (!s1.equalsIgnoreCase("true")) {
                this.setMessageAcct(s1);
                return;
            } else {
                this.setMessageAcct("");
            }
            String s2 = onblurDate(reportDate);
            if (!s2.equalsIgnoreCase("true")) {
                this.setMessageAcct(s2);
                return;
            } else {
                this.setMessageAcct("");
            }

            if (pStatus.equals(nStatus)) {
                setMessageAcct("Current Status and New Status Can'nt be Same!");
                return;
            } else {
//                System.out.println("lienAmtlienAmtlienAmt" + lienAmt);
//                System.out.println("lienAcNolienAcNolienAcNo" + lienAcNo);
                //String rs = statusMaintenanceFacade.cbsSaveAcctStatus(acnoAcct, remarks, getUserName(),  nStatus, wefDate1, Float.parseFloat(lienAmt), acno);
                // String rs = asRemote.CBS_SAVE_ACCT_STATUS(acnoAcct, remarks, userName, DD, nStatus, wefDate1, Float.parseFloat(lienAmt),lienAcNo);
                //Change by Dhirendra Singh 
                String rs = statusMaintenanceFacade.lienMarked(acnoAcct, remarks, getUserName(), nStatus, wefDate1, Float.parseFloat(lienAmt), acno, "0", "", "");
                if (rs.equalsIgnoreCase("Status Changed Successfully")) {
                    setMessageAcct(rs);
                    reFresh1();
                } else {
                    setMessageAcct(rs);
                }
                securityDetailsTableValues();
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void reFresh() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setMessageAcct("");
            setNameAcct("");
            setpStatus("");
            setOldcode("");
            setRemarks("");
            setnStatus("--Select--");
            setLienAcNo("");
            setLienAmt("");
            setLienacctype("--Select--");
            setLiencode("");
            fflag = "false";
            flagAcct = "false";
            acnoAcct = "";
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void reFresh1() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setNameAcct("");
            setpStatus("");
            setOldcode("");
            setRemarks("");
            setnStatus("--Select--");
            setLienAcNo("");
            setLienAmt("");
            setLienacctype("--Select--");
            setLiencode("");
            fflag = "false";
            acnoAcct = "";
            flagAcct = "false";
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void onblurWefDate() {
        onblurDate(this.wefDate);
    }

    public void onblurReportDate() {
        onblurDate(this.reportDate);
    }

    public String onblurDate(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            List dateDif = statusMaintenanceFacade.dateDiffWefDate(sdf.format(dt));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessageAcct("You can't select date after the current date.");
                return msg1 = "You can't select date after the current date.";
            } else {
                this.setMessageAcct("");
                return msg1 = "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return msg1;
    }

    public String exitBtnAction() {
        reFresh();
        return "case1";
    }

    /**
     * END OF ACCOUNT STATUS FORM CODE*
     */
    public void clearModelPanel() throws ParseException {
        resetForm();
        resetPage();
        setMarkSecurities("");
        setMessageAcct("");
        reFresh();
        securityDetailsTableValues();
    }

    public String exitForm() {
        return "case1";
    }

    public void saveEnable() {
        setSaveDisable(false);
    }

    public void MarkSecurity() {

        if (markSecurities.equalsIgnoreCase("3")) {
            flagSecurity = "true";
            setSaveDisable(false);
        } else if (markSecurities.equalsIgnoreCase("2")) {
            flagSecurity = "false";
            setSaveDisable(true);
        } else if (markSecurities.equalsIgnoreCase("1")) {
            setSaveDisable(true);
            flagSecurity = "false";
        }
    }

    public String exitToAcCloseForm() {
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.acCloseFlag == null || this.acCloseFlag.equalsIgnoreCase("")) {
                resetForm();
                resetPage();
                reFresh();
                return "case1";
            } else {
                this.setAcCloseFlag(null);
                resetForm();
                resetPage();
                reFresh();
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/AccountClosingRegister.jsp?code=" + 1);
                //((HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/accountmanager/AccountClosingRegister.jsp?code="+1);
                return "case2";
            }
        } catch (IOException ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return "case1";
    }
}
