/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.dto.FidilityTablePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sipl
 */
public class FidelityAccountOpen extends BaseBean {

    private String message;
    private String function;
    private String focusId;
    private String custIdStyle;
    private String acNoStyle;
    private String verifyStyle;
    private String custId;
    private String acNumber;
    private String newAccType;
    private String flag;
    private String acTypeDesc;
    private Date openDt = new Date();
    private String firstName;
    private String dateofBirth;
    private String fatherName;
    private String businessDesig;
    private String permanentAdd;
    private String correspodenceAdd;
    private String panGir;
    private String bondAmt;
    private String prAmt;
    private String btnValue = "Save";
    private boolean saveDisable;
    private String acNo;
    private String desgFlag;
    private String fieldDisFlag;
    private String opFlag;
    private String enterBy = "";
    private List<SelectItem> functionList;
    private List<SelectItem> accountList;
    private List<SelectItem> acctTypeOption;
    private List<SelectItem> businessDesigList;
    private List<SelectItem> schemeCodeList;
    ShareTransferFacadeRemote remoteObject;
    private CommonReportMethodsRemote reportMethodsRemote = null;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    private GeneralMasterFacadeRemote genFacadeRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();

    public String getAcNumber() {
        return acNumber;
    }

    public void setAcNumber(String acNumber) {
        this.acNumber = acNumber;
    }

    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<SelectItem> accountList) {
        this.accountList = accountList;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getBondAmt() {
        return bondAmt;
    }

    public void setBondAmt(String bondAmt) {
        this.bondAmt = bondAmt;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getBusinessDesig() {
        return businessDesig;
    }

    public void setBusinessDesig(String businessDesig) {
        this.businessDesig = businessDesig;
    }

    public List<SelectItem> getBusinessDesigList() {
        return businessDesigList;
    }

    public void setBusinessDesigList(List<SelectItem> businessDesigList) {
        this.businessDesigList = businessDesigList;
    }

    public String getCorrespodenceAdd() {
        return correspodenceAdd;
    }

    public void setCorrespodenceAdd(String correspodenceAdd) {
        this.correspodenceAdd = correspodenceAdd;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustIdStyle() {
        return custIdStyle;
    }

    public void setCustIdStyle(String custIdStyle) {
        this.custIdStyle = custIdStyle;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
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

    public String getNewAccType() {
        return newAccType;
    }

    public void setNewAccType(String newAccType) {
        this.newAccType = newAccType;
    }

    public Date getOpenDt() {
        return openDt;
    }

    public void setOpenDt(Date openDt) {
        this.openDt = openDt;
    }

    public String getPanGir() {
        return panGir;
    }

    public void setPanGir(String panGir) {
        this.panGir = panGir;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public String getPrAmt() {
        return prAmt;
    }

    public void setPrAmt(String prAmt) {
        this.prAmt = prAmt;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getVerifyStyle() {
        return verifyStyle;
    }

    public void setVerifyStyle(String verifyStyle) {
        this.verifyStyle = verifyStyle;
    }

    public String getAcNoStyle() {
        return acNoStyle;
    }

    public void setAcNoStyle(String acNoStyle) {
        this.acNoStyle = acNoStyle;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getDesgFlag() {
        return desgFlag;
    }

    public void setDesgFlag(String desgFlag) {
        this.desgFlag = desgFlag;
    }

    public String getFieldDisFlag() {
        return fieldDisFlag;
    }

    public void setFieldDisFlag(String fieldDisFlag) {
        this.fieldDisFlag = fieldDisFlag;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getOpFlag() {
        return opFlag;
    }

    public void setOpFlag(String opFlag) {
        this.opFlag = opFlag;
    }
    
    public FidelityAccountOpen() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            genFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");

            setTodayDate(sdf.format(date));
            setCustIdStyle("block");
            setAcNoStyle("none");
            setVerifyStyle("none");
            this.setFlag("true");
            this.setDesgFlag("true");
            this.setFieldDisFlag("true");
            this.setOpFlag("false");
            getListValues();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getListValues() {
        try {
            accountList = new ArrayList<SelectItem>();
            String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "-Select-"));
            functionList.add(new SelectItem("1", "Add"));

            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("3", "Verify"));
            }
            functionList.add(new SelectItem("4", "Enquiry"));

            List accountTyperesultList = reportMethodsRemote.getFidiltyTypeList();
            if (accountTyperesultList.size() < 0) {
                acctTypeOption = new ArrayList<SelectItem>();
                acctTypeOption.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                acctTypeOption = new ArrayList<SelectItem>();
                acctTypeOption.add(new SelectItem("0", "--SELECT--"));
                for (int i = 0; i < accountTyperesultList.size(); i++) {
                    Vector ele = (Vector) accountTyperesultList.get(i);
                    acctTypeOption.add(new SelectItem(ele.get(0).toString()));
                }
            }

            businessDesigList = new ArrayList<SelectItem>();
            businessDesigList.add(new SelectItem("0", "--Select--"));
            List glList = genFacadeRemote.getRefCodeAndDescByNo("020");
            if (glList.isEmpty()) {
                this.setMessage("Please fill data in CBS REF REC TYPE for 020");
                return;
            } else {
                for (int i = 0; i < glList.size(); i++) {
                    Vector v1 = (Vector) glList.get(i);
                    businessDesigList.add(new SelectItem(v1.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chenageOperation() {
        try {
            setMessage("");            
            if (function.equals("")) {
                setMessage("Please select Function.");
                return;
            } else if (function.equals("1")) {
                clear();
                setCustIdStyle("block");
                setAcNoStyle("none");
                setVerifyStyle("none");
                setBtnValue("Save");
                setFocusId("txtCustId");
                this.setFlag("false");
                this.setFieldDisFlag("false");
                this.setDesgFlag("false");
                this.setOpFlag("false");
                setSaveDisable(false);
            } else if (function.equals("3")) {
                clear();
                getUnAuthAccounts();
                setCustIdStyle("none");
                setAcNoStyle("none");
                setVerifyStyle("block");
                setBtnValue("Verify");
                setFocusId("ddAccount");
                this.setFlag("true");
                this.setFieldDisFlag("true");
                this.setDesgFlag("true");
                this.setBondAmt("");
                this.setPrAmt("");
                this.setBusinessDesig("0");
                this.setOpFlag("true");
                setSaveDisable(false);
            } else {
                clear();
                setCustIdStyle("none");
                setAcNoStyle("block");
                setVerifyStyle("none");
                setBtnValue("Save");
                setFocusId("txtShareholder");
                setSaveDisable(true);
                this.setFlag("true");
                this.setFieldDisFlag("true");
                this.setDesgFlag("true");
                this.setBondAmt("");
                this.setPrAmt("");
                this.setBusinessDesig("0");
                this.setOpFlag("true");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void desigLostFocus() {
        try {
            setMessage("");
            if (this.businessDesig.equalsIgnoreCase("0")) {
                setMessage("Please select Designation.");
                return;
            } else {
                List<FidilityTablePojo> resultList = genFacadeRemote.getFidilityDetailsList(this.businessDesig);
                if (!resultList.isEmpty()) {
                    for (FidilityTablePojo entity : resultList) {
                        this.setBondAmt(entity.getBondAmount());
                        this.setPrAmt(entity.getPrAmount());
                    }
                } else {
                    this.setMessage("There is no data.");
                    this.setBondAmt("");
                    this.setPrAmt("");
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getCustomerDetail() {
        String p1;
        String p2;
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            setMessage("");
            if (this.custId.equalsIgnoreCase("") || this.custId == null) {
                this.setCustId("");
                setMessage("Please Enter the Customer ID.");
                return;
            } else {
                Matcher customerIdCheck = p.matcher(this.custId);
                if (!customerIdCheck.matches()) {
                    this.setCustId("");
                    setMessage("Please Enter the Numeric Value For Customer Id.");
                    return;
                }
            }
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getExistingCustDetail(this.custId);
            if (resultList.isEmpty()) {
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                return;
            } else {
                String temp = this.custId;
                this.setCustId(temp);
                Vector ele = (Vector) resultList.get(0);

                if (ele.get(1).toString().length() != 0 || !ele.get(1).toString().equalsIgnoreCase("") || ele.get(1) != null) {
                    this.setFirstName(ele.get(1).toString());
                } else {
                    this.setFirstName("");
                }

                p1 = ele.get(2).toString();
                p2 = ele.get(3).toString();
                if (p1.equalsIgnoreCase("") || p1.length() == 0) {
                    p1 = "";
                }

                if (p2.equalsIgnoreCase("") || p2.length() == 0) {
                    p2 = "";
                }
                String p3 = p1 + p2;
                if (p3.equalsIgnoreCase("")) {
                    this.setPermanentAdd("");
                } else {
                    this.setPermanentAdd(p3);
                }

                String cr1 = ele.get(4).toString();
                String cr2 = ele.get(5).toString();
                if (cr1.equalsIgnoreCase("") || cr1.length() == 0) {
                    cr1 = "";
                }

                if (cr2.equalsIgnoreCase("") || cr2.length() == 0) {
                    cr2 = "";
                }
                String c3 = cr1 + cr2;
                if (c3.equalsIgnoreCase("")) {
                    this.setCorrespodenceAdd("");
                } else {
                    this.setCorrespodenceAdd(c3);
                }

                if (!ele.get(6).toString().equalsIgnoreCase("") || ele.get(6).toString().length() != 0) {
                    this.setFatherName(ele.get(6).toString());
                } else {
                    this.setFatherName("");
                }

                if (!ele.get(8).toString().equalsIgnoreCase("") || ele.get(8).toString().length() != 0) {
                    this.setPanGir(ele.get(8).toString());
                } else {
                    this.setPanGir("");
                }
                if (!ele.get(9).toString().equalsIgnoreCase("") || ele.get(9).toString().length() != 0) {
                    this.setDateofBirth(ele.get(9).toString());
                } else {
                    this.setDateofBirth("");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void acTypeLostFocus() {
        try {
            List resultList7 = acOpenFacadeRemote.schemeCodeCombo(this.newAccType);
            schemeCodeList = new ArrayList<SelectItem>();
            schemeCodeList.add(new SelectItem("", "--Select--"));
            if (!resultList7.isEmpty()) {
                for (int i = 0; i < resultList7.size(); i++) {
                    Vector ele7 = (Vector) resultList7.get(i);
                    schemeCodeList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                }
            } else {
                this.setMessage("No Scheme Defined For This Account Type");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void saveAction() {
        try {
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            if (function.equalsIgnoreCase("1")) {
                acNo = "";
            }

            if (function.equalsIgnoreCase("3")) {
                acNo = acNumber;
                if (acNo.equals("")) {
                    this.setMessage("Please select Account number.");
                    return;
                }
                if (this.enterBy.equalsIgnoreCase(getUserName())) {
                    this.setMessage("You can not Verify your own transaction");
                    return;
                }
            }            
            String result = acOpenFacadeRemote.saveFidilityAccountOpen(function, custId, acNumber, ymd.format(openDt), businessDesig,
                    firstName, newAccType, getUserName(), getOrgnBrCode(), acTypeDesc, Double.parseDouble(this.bondAmt), Double.parseDouble(this.prAmt));
            setMessage(result);
            refreshButtonAction();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String validations() {
        try {
            if (function == null || function.equalsIgnoreCase("")) {
                this.setMessage("Please select the Function");
                return "false";
            }

            if (function.equalsIgnoreCase("1")) {
                if (custId.equalsIgnoreCase("") || custId == null) {
                    this.setMessage("Please Fill The Customer Id");
                    return "false";
                }

                if (newAccType.equalsIgnoreCase("0")) {
                    this.setMessage("Please Select Account Type");
                    return "false";
                }

                if (acTypeDesc.equalsIgnoreCase("")) {
                    this.setMessage("Please Select Scheme Code");
                    return "false";
                }

                if (this.businessDesig.equalsIgnoreCase("0")) {
                    setMessage("Please select Designation.");
                    return "false";
                }

                if (this.bondAmt.equalsIgnoreCase("")) {
                    setMessage("Master Data Not Filled For This Designation");
                    return "false";
                }

                if (this.prAmt.equalsIgnoreCase("")) {
                    setMessage("Master Data Not Filled For This Designation");
                    return "false";
                }
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "flase";
    }

    public void candidateDeatails() {
        setMessage("");
        try {
            String fNo = this.getAcNo();
            if(function.equals("3")){
                fNo = this.getAcNumber();
            }            
            List resultList = acOpenFacadeRemote.getDetail(fNo);
            if (resultList.size() > 0) {
                this.setNewAccType("0");
                this.setFlag("true");
                this.setAcTypeDesc("");
                this.setFieldDisFlag("true");
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        setFirstName(ele.get(0).toString());
                    }

                    if (ele.get(1) != null) {
                        setFatherName(ele.get(1).toString());
                    }

                    String cr1 = ele.get(2).toString();
                    String cr2 = ele.get(3).toString();
                    if (cr1.equalsIgnoreCase("") || cr1.length() == 0) {
                        cr1 = "";
                    }

                    if (cr2.equalsIgnoreCase("") || cr2.length() == 0) {
                        cr2 = "";
                    }
                    String c3 = cr1 + cr2;
                    if (c3.equalsIgnoreCase("")) {
                        this.setCorrespodenceAdd("");
                    } else {
                        this.setCorrespodenceAdd(c3);
                    }

                    String p1 = ele.get(4).toString();
                    String p2 = ele.get(5).toString();
                    if (p1.equalsIgnoreCase("") || p1.length() == 0) {
                        p1 = "";
                    }

                    if (p2.equalsIgnoreCase("") || p2.length() == 0) {
                        p2 = "";
                    }
                    String p3 = p1 + p2;
                    if (p3.equalsIgnoreCase("")) {
                        this.setPermanentAdd("");
                    } else {
                        this.setPermanentAdd(p3);
                    }

                    if (ele.get(6) != null) {
                        if (ele.get(6).toString().equals("")) {
                            setBusinessDesig("0");
                        } else {
                            setBusinessDesig(ele.get(6).toString());
                        }
                    }

                    if (ele.get(7) != null) {
                        this.setBondAmt(ele.get(7).toString());
                    }
                    if (ele.get(8) != null) {
                        this.setPrAmt(ele.get(8).toString());
                    }

                    if (ele.get(9) != null) {
                        setOpenDt(sdf.parse(ele.get(9).toString()));
                    }

                    if (!ele.get(10).toString().equalsIgnoreCase("") || ele.get(10).toString().length() != 0) {
                        this.setPanGir(ele.get(10).toString());
                    } else {
                        this.setPanGir("");
                    }
                    
                    if (!ele.get(11).toString().equalsIgnoreCase("") || ele.get(11).toString().length() != 0) {
                        this.setDateofBirth(ele.get(11).toString());
                    } else {
                        this.setDateofBirth("");
                    }
                    
                    if (ele.get(12) != null) {
                        this.setEnterBy(ele.get(12).toString());
                    }
                }
            }else{
                setMessage("Data Not Exist For This Account Number");
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void getUnAuthAccounts() {
        try {
            accountList = new ArrayList<SelectItem>();
            List list = acOpenFacadeRemote.getUnAuthAccountList(this.getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                accountList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshButtonAction() {
        this.setFunction("");
        setTodayDate(sdf.format(date));
        this.setCustId("");
        this.setAcNo("");
        this.setAcNumber("");
        this.setNewAccType("0");
        this.setAcTypeDesc("");
        this.setFirstName("");
        this.setDateofBirth("");
        this.setFatherName("");
        this.setPermanentAdd("");
        this.setCorrespodenceAdd("");
        this.setPanGir("");
        this.setBondAmt("");
        this.setPrAmt("");
        setCustIdStyle("block");
        setAcNoStyle("none");
        setVerifyStyle("none");
        this.setFlag("true");
        this.setDesgFlag("true");
        this.setFieldDisFlag("true");
        this.setOpFlag("false");
        this.setBusinessDesig("0");
        getUnAuthAccounts();
    }
    
    public void clear() {
        setTodayDate(sdf.format(date));
        this.setCustId("");
        this.setAcNo("");
        this.setAcNumber("");
        this.setNewAccType("0");
        this.setAcTypeDesc("");
        this.setFirstName("");
        this.setDateofBirth("");
        this.setFatherName("");
        this.setPermanentAdd("");
        this.setCorrespodenceAdd("");
        this.setPanGir("");
        this.setBondAmt("");
        this.setPrAmt("");                
    }

    public String btnExit() {
        this.setMessage("");
        refreshButtonAction();
        return "case1";
    }
}