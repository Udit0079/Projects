/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.facade.loan.LoanAccountsDetailsRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.AccGenInfo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Dhirendra Singh Date 02/02/2013
 */
public class AccountOpen extends BaseBean {

    ShareTransferFacadeRemote shareRemoteObject;
    private final String jndiHomeName = "InventorySplitAndMergeFacade";
    InventorySplitAndMergeFacadeRemote beanRemote;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    LoanAccountsDetailsRemote loanAcDetails;
    private String custId;
    private String function;
    private String message;
    private String folioNo;
    private String folioNumber;
    private Date registerDt = new Date();
    private String shareValue;
    private String purpose;
    private String dividentPayable;
    private String dividentPayableAcno;
    private String firstName;
    private String dateofBirth;
    private String fatherName;
    private String category;
    private String businessDesig;
    private String firmHolderName;
    private String witnessFirstName;
    private String witnessFirstAddress;
    private String witnessSecondName;
    private String witnessSecondAddress;
    private String nomineeName;
    private String nomineeAddress;
    private String nomineeAge;
    private String nomineeRelation;
    private String operationMode;
    private String jtName1;
    private String jtName2;
    private String jtFirstId;
    private String jtSecondId;
    private String remark;
    private String enterBy = "";
    private String btnValue = "Save";
    private List<SelectItem> functionList;
    private List<SelectItem> purposeList;
    private List<SelectItem> dividendPayableList;
    private List<SelectItem> categoryList;
    private List<SelectItem> operatingModeList;
    private List<SelectItem> accountList;
    private String bnkName;
    private List<SelectItem> bnkNameList;
    private String beneficiaryAcNo, acNoLen, newAcno;
    private String ifscCode;
    private String beneficiaryName;
    Date date = new Date();
    private String shareVal;
    boolean folioNoDisable;
    boolean jtName1Disable;
    boolean jtName2Disable;
    private String custIdStyle;
    private String folioNoStyle;
    private String verifyStyle;
    boolean acnoDisable;
    boolean benefNameDisable;
    boolean benefIfscDisable;
    private String focusId;
    //private boolean updateDisable;
    private DDSReportFacadeRemote ddsRemote;
    private CommonReportMethodsRemote common;
    private String branch;
    private List<SelectItem> branchList;
    private String classification;
    private String acNature;
    private String acType;
    private List<SelectItem> acNatureList;
    private List<SelectItem> acTypeList;
    private List<SelectItem> classificationList;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String priority;
    private List<SelectItem> priorityList;
    private List<AccGenInfo> acnoGenInfo;
    AccGenInfo currentItem = new AccGenInfo();
    private String memberStyle;
    private String occupation;
    private List<SelectItem> occupationList;
    private Date nomineeDob;
    private String intruduser;
    private String docName;
    private List<SelectItem> docNameList;
    private String docDetail;
    private String panNo;
    private String actCateg;
    private List<SelectItem> actCategList;
    //private boolean updateDisable;
    private boolean saveDisable;
    Validator validator;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    PostalDetailFacadeRemote postalRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    String alert;
    String salary2;
    private Date joinDt;
    private boolean joinDtDisFlag;
    private boolean salDisFlag;
    private String chkMem;
    private boolean gPayDisFlag;
    String gPay;
    private String area;
    private List<SelectItem> areaList;
    private List<SelectItem> businessDesigList;
    private String intruduserName;
    private String occupationPanStyle;

    public AccountOpen() {
        try {
            clear();
            shareRemoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            beanRemote = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            loanAcDetails = (LoanAccountsDetailsRemote) ServiceLocator.getInstance().lookup("LoanAccountsDetailsFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            postalRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            getBankName();
            getListValues();
            getshareValue();
            validator = new Validator();
            setCustIdStyle("block");
            setFolioNoStyle("none");
            setVerifyStyle("none");
            setJtName1("");
            setJtName2("");
            this.setMessage("");
            alert = "";
            this.setFunction("");
            this.setFolioNo("");
            getMemDetail();
            // new code for army
            List list = common.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

            classificationList = new ArrayList<SelectItem>();
            classificationList.add(new SelectItem("D", "Advance"));
            classificationList.add(new SelectItem("C", "Deposit"));

            priorityList = new ArrayList<SelectItem>();
            priorityList.add(new SelectItem("0", "--Select--"));
            priorityList.add(new SelectItem("P", "Primary"));
            priorityList.add(new SelectItem("S", "Secondry"));

            occupationList = new ArrayList<SelectItem>();
            occupationList.add(new SelectItem("0", "--Select--"));
            List resultList3 = common.getOcupationDetails();
            if (!resultList3.isEmpty()) {
                for (int i = 0; i < resultList3.size(); i++) {
                    Vector ele3 = (Vector) resultList3.get(i);
                    occupationList.add(new SelectItem(ele3.get(0).toString(), ele3.get(1).toString()));
                }
            }

            docNameList = new ArrayList<SelectItem>();
            docNameList.add(new SelectItem("0", "--Select--"));
            List resultList5 = common.getDocumentDetails();
            if (!resultList5.isEmpty()) {
                for (int i = 0; i < resultList5.size(); i++) {
                    Vector ele5 = (Vector) resultList5.get(i);
                    docNameList.add(new SelectItem(ele5.get(0).toString(), ele5.get(1).toString()));
                }
            }
            this.setMemberStyle("none");
            setOccupationPanStyle("none");

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getBankName() {
        try {
            bnkNameList = new ArrayList<SelectItem>();
            List list = shareRemoteObject.getBankNameList();
            bnkNameList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                bnkNameList.add(new SelectItem(vtr.get(0).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void getMemDetail() {
        try {
            String meminfo = acOpenFacadeRemote.getMemDetails();
            if (!meminfo.equalsIgnoreCase("true")) {
                this.setJoinDtDisFlag(true);
                this.setSalDisFlag(true);
                this.setgPayDisFlag(true);
                this.setChkMem("false");
            } else {
                this.setJoinDtDisFlag(false);
                this.setSalDisFlag(false);
                this.setgPayDisFlag(false);
                this.setChkMem("true");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getListValues() {
        try {
            accountList = new ArrayList<SelectItem>();
            String levelId = shareRemoteObject.getLevelId(getUserName(), getOrgnBrCode());

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "-Select-"));
            functionList.add(new SelectItem("1", "Add"));
            functionList.add(new SelectItem("2", "Modify"));

            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("3", "Verify"));
            }

            functionList.add(new SelectItem("4", "Enquiry"));

            List resultList = new ArrayList();
            resultList = shareRemoteObject.getPurposeList();
            if (resultList.size() < 0) {
                purposeList = new ArrayList<SelectItem>();
                purposeList.add(new SelectItem("0", " "));
                return;
            } else {
                purposeList = new ArrayList<SelectItem>();
                purposeList.add(new SelectItem("0", "-Select-"));
                purposeList.add(new SelectItem("IF ANY", "-IF ANY-"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    purposeList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            }

            List accountTyperesultList = shareRemoteObject.getAcctType();
            if (accountTyperesultList.size() < 0) {
                dividendPayableList = new ArrayList<SelectItem>();
                dividendPayableList.add(new SelectItem("0", " "));
                return;
            } else {
                dividendPayableList = new ArrayList<SelectItem>();
                dividendPayableList.add(new SelectItem("0", "-Select-"));
                for (int i = 0; i < accountTyperesultList.size(); i++) {
                    Vector ele = (Vector) accountTyperesultList.get(i);
                    dividendPayableList.add(new SelectItem(ele.get(0).toString()));
                }
            }

            List operatingModeresultList = shareRemoteObject.getOperatingMode();
            if (operatingModeresultList.size() < 0) {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", ""));
                return;
            } else {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < operatingModeresultList.size(); i++) {
                    Vector ele = (Vector) operatingModeresultList.get(i);
                    operatingModeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            resultList = shareRemoteObject.categoryList();
            if (resultList.size() < 0) {
                categoryList = new ArrayList<SelectItem>();
                categoryList.add(new SelectItem("0", " "));
                return;
            } else {
                categoryList = new ArrayList<SelectItem>();
                categoryList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    categoryList.add(new SelectItem(ele.get(0).toString()));
                }
            }

            //List areaLst = remoteObject.areaList();
            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("", "--Select--"));
            resultList = common.getRefRecList("019");
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            businessDesigList = new ArrayList<SelectItem>();
            businessDesigList.add(new SelectItem("", "--Select--"));
            resultList = common.getRefRecList("020");
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    businessDesigList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

//            if (!areaLst.isEmpty()) {
//                for (int i = 0; i < areaLst.size(); i++) {
//                    Vector ele = (Vector) areaLst.get(i);
//                    areaList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                }
//            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void classificationAction() {
        this.setMessage("");
        acNatureList = new ArrayList<SelectItem>();

        try {
            if (this.classification == null
                    || this.classification.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select A/c Classification.");
                return;
            }
            acNatureList.add(new SelectItem("S", "--Select--"));
            String clafi = "'" + this.classification + "'";
            List list = ddsRemote.getAccountNatureClassification(clafi);
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acNatureList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAcTypeForNature() {

        try {
            if (acNature.equalsIgnoreCase("S")) {
                setMessage("Please select the Account Nature");
                return;
            } else {
                acTypeList = new ArrayList<SelectItem>();
                acTypeList.add(new SelectItem("S", "--Select--"));
                List list = common.getAcTypeDescByClassificationAndNature(this.classification, this.acNature);
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    acTypeList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setScheme() {
        setMessage(message);
        try {
            if (acType.equalsIgnoreCase("S")) {
                setMessage("Please select the Account Type");
                return;
            } else {
                if (function.equalsIgnoreCase("2")) {
                    List<String> actList = new ArrayList();
                    if (acnoGenInfo.size() > 0) {
                        for (int i = 0; i < acnoGenInfo.size(); i++) {
                            actList.add(acnoGenInfo.get(i).getActType());
                        }
                    }
                    if (actList.contains(this.acType)) {
                        setMessage("Account No already open this account type !");
                        return;
                    }
                }
                List chkAcno = shareRemoteObject.checkAcno(this.branch, this.acType);
                if (!chkAcno.isEmpty()) {
                    setMessage("Account No already open");
                    return;
                }
                List resultList7 = acOpenFacadeRemote.schemeCodeCombo(this.acType);
                schemeCodeList = new ArrayList<SelectItem>();
                schemeCodeList.add(new SelectItem("S", "--Select--"));
                if (!resultList7.isEmpty()) {
                    for (int i = 0; i < resultList7.size(); i++) {
                        Vector ele7 = (Vector) resultList7.get(i);
                        schemeCodeList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                    }
                }
            }
        } catch (Exception e) {

            setMessage(e.getMessage());
        }
    }

    public void calAge() {
        setMessage("");
        setNomineeAge("0");
        Integer age;
        try {
//            if (nomineeDob == null) {
//                setMessage("Please fill nomenee date of birth !");
//                return;
//            }
            age = CbsUtil.yearDiff(this.nomineeDob, date);
            nomineeAge = age.toString();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void editData() {
        setMessage("");
        try {
            List acNoList = new ArrayList<AccGenInfo>();
            acNoList = shareRemoteObject.getFolioAcnoInfo(folioNo, "2");
            for (int i = 0; i < acNoList.size(); i++) {
                Vector acv = (Vector) acNoList.get(i);
                AccGenInfo obj = new AccGenInfo();
                obj.setAcNo(acv.get(0).toString());
                obj.setSchemeCode(acv.get(1).toString());
                double odLimit = Double.parseDouble(acv.get(2).toString());
                obj.setActType(ftsPostRemote.getAccountCode(acv.get(0).toString()));
                obj.setActNature(ftsPostRemote.getAccountNature(acv.get(0).toString()));
                obj.setBranch(common.getAlphacodeByBrncode(ftsPostRemote.getCurrentBrnCode(acv.get(0).toString())));
                // double outStandingBal = common.getBalanceOnDate(acv.get(0).toString(),ymd.format(sdf.parse(getTodayDate())));
                if (odLimit > 0) {
                    obj.setPriority("P");
                } else {
                    obj.setPriority("S");
                }
                obj.setsNo(String.valueOf(acnoGenInfo.size() + 1));
                acnoGenInfo.add(obj);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void accGenInfo() {
        setMessage("");
        if (acnoGenInfo == null) {
            acnoGenInfo = new ArrayList<AccGenInfo>();
        } else if (acnoGenInfo.size() < 1) {
            acnoGenInfo = new ArrayList<AccGenInfo>();
        }
        try {

            if (getAcNature().equalsIgnoreCase("S")) {
                setMessage("Please select the Account Nature");
                return;
            } else {
                if (getAcType().equalsIgnoreCase("S")) {
                    setMessage("Please select the Account Type");
                    return;
                } else {
                    if (getSchemeCode().equalsIgnoreCase("S")) {
                        setMessage("Please select the Scheme Code");
                        return;
                    } else {
                        if (getPriority().equalsIgnoreCase("0")) {
                            setMessage("Please select the Priority");
                            return;
                        } else {
                            if (acnoGenInfo.size() > 0) {
                                boolean flag = true;
                                for (int z = 0; z < acnoGenInfo.size(); z++) {
                                    if (common.getBrncodeByAlphacode(acnoGenInfo.get(z).getBranch()).equalsIgnoreCase(branch) && acnoGenInfo.get(z).getActNature().equalsIgnoreCase(acNature) && acnoGenInfo.get(z).getActType().equalsIgnoreCase(acType)) {
                                        setMessage("Please Check the data, you have selected");
                                        flag = false;
                                        return;
                                    }
                                    if (priority.equalsIgnoreCase("P") && priority.equalsIgnoreCase(acnoGenInfo.get(z).getPriority())) {
                                        setMessage("Please don't select --Primary-- Priority 2nd times");
                                        flag = false;
                                        return;
                                    }
                                }
                                if (flag == true) {
                                    AccGenInfo obj = new AccGenInfo();
                                    obj.setActType(acType);
                                    obj.setSchemeCode(schemeCode);
                                    obj.setActNature(acNature);
                                    obj.setBranch(common.getAlphacodeByBrncode(branch));
                                    obj.setPriority(priority);
                                    obj.setsNo(String.valueOf(acnoGenInfo.size() + 1));
                                    acnoGenInfo.add(obj);
                                }
                            } else {
                                AccGenInfo obj = new AccGenInfo();
                                obj.setActType(acType);
                                obj.setSchemeCode(schemeCode);
                                obj.setActNature(acNature);
                                obj.setBranch(common.getAlphacodeByBrncode(branch));
                                obj.setPriority(priority);
                                obj.setsNo(String.valueOf(1));
                                acnoGenInfo.add(obj);
                            }
                        }
                    }
                }
            }
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
                setCustIdStyle("block");
                setFolioNoStyle("none");
                setVerifyStyle("none");
                setBtnValue("Save");
                setFocusId("txtCustId");
                setSaveDisable(false);
                setMemberStyle("none");
                setOccupationPanStyle("none");
                clear();
            } else if (function.equals("2")) {
                accountList = new ArrayList<SelectItem>();
                setCustIdStyle("none");
                setFolioNoStyle("block");
                setVerifyStyle("none");
                setBtnValue("Update");
                setFocusId("txtShareholder");
                setSaveDisable(false);
                String accSeq = common.getAccseq();
                if (accSeq.equalsIgnoreCase("M")) {
                    setMemberStyle("");
                    setOccupationPanStyle("");
                } else {
                    setMemberStyle("none");
                    setOccupationPanStyle("none");
                }

                clear();
            } else if (function.equals("3")) {
                getUnAuthAccounts();
                setCustIdStyle("none");
                setFolioNoStyle("none");
                setVerifyStyle("block");
                setBtnValue("Verify");
                setFocusId("ddAccount");
                setSaveDisable(false);
                setMemberStyle("none");
                clear();
            } else {
                setCustIdStyle("none");
                setFolioNoStyle("block");
                setVerifyStyle("none");
                setBtnValue("Update");
                setFocusId("txtShareholder");
                setSaveDisable(true);
                setMemberStyle("none");
                clear();
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUnAuthAccounts() {
        try {
            accountList = new ArrayList<SelectItem>();
            List list = shareRemoteObject.getUnAuthAccountList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                accountList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getCustomerDetail() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custIdCheck = p.matcher(this.getCustId());
            if (!custIdCheck.matches()) {
                this.setMessage("Please Enter Numeric Customer ID.");
                return;
            } else {
                this.setMessage("");
            }
            String custinfo = acOpenFacadeRemote.custIdInformation(custId);
            if (custinfo.equals("This Customer ID Does Not Exists")) {
                setMessage(custinfo);
                return;
            } else {
                String[] values = custinfo.split(":");
                setDateofBirth(values[7]);
                setFirstName(values[0] + " " + values[1]);
                setFatherName(values[4]);
                setPanNo(values[6]);
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getshareValue() {
        try {
            setMessage("");
            if (registerDt.after(date)) {
                setMessage("Date selected cannot be greater than today's date");
                return;
            }
            List resultList;
            resultList = shareRemoteObject.getshareAmount(ymd.format(registerDt));
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    shareVal = ele.get(0).toString();
                }
            } else {
                shareVal = "0";
            }
            setShareValue(shareVal);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void operationModeValueChange() {
        try {
            if (operationMode.equalsIgnoreCase("1")) {
                jtName1Disable = true;
                jtName2Disable = true;
            } else if (operationMode.equalsIgnoreCase("2") || operationMode.equalsIgnoreCase("9")) {
                jtName1Disable = false;
                jtName2Disable = true;
            } else {
                jtName1Disable = false;
                jtName2Disable = false;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void operationModeChangeByBnkName() {
        setMessage("");
        try {
            if (bnkName == null || bnkName.equalsIgnoreCase("S")) {
                this.setMessage("Please select Bank Name !");
                setBeneficiaryAcNo("");
                setBeneficiaryName("");
                setIfscCode("");
                setFocusId("ddCategory");
                benefNameDisable = true;
                benefIfscDisable = true;
                return;
            }
            if (bnkName.equalsIgnoreCase("CCBL") || bnkName.equalsIgnoreCase("OWN")) {
                setFocusId("txtBeneciaryacno");
                benefNameDisable = true;
                benefIfscDisable = true;
            } else if (bnkName.equalsIgnoreCase("OTHER")) {
                setFocusId("txtBeneciaryacno");
                benefNameDisable = false;
                benefIfscDisable = false;
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void purposeValueChange() {
        setMessage("");
        try {
            if (purpose.equalsIgnoreCase("IF ANY")) {
                //acnoDisable = true;
                setFocusId("idBnkNameList");
            } else {
                //acnoDisable = false;
                setFocusId("idBnkNameList");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void candidateDeatails() {
        setMessage("");
        try {
            String fNo = this.getFolioNo();
            String memberParameter = common.getAccseq();
            if (function.equals("3")) {
                fNo = this.getFolioNumber();
            }
            List resultList = shareRemoteObject.editDetail(fNo);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        setFirstName(ele.get(0).toString() + " " + ele.get(1).toString() + " " + ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        setDateofBirth(sdf.format(ymd.parse(ele.get(3).toString())));
                    }
                    if (ele.get(4) != null) {
                        setFatherName(ele.get(4).toString());
                    }
                    if (ele.get(5) != null) {
                        if (ele.get(5).toString().equals("")) {
                            setPurpose("IF ANY");
                        } else {
                            setPurpose(ele.get(5).toString());
                        }
                    }
                    if (ele.get(6) != null) {
                        setDividentPayable(ele.get(6).toString());
                        setDividentPayableAcno(ele.get(6).toString());
                    }
                    if (ele.get(7) != null) {
                        setCategory(ele.get(7).toString());
                    }
                    if (ele.get(8) != null) {
                        setBusinessDesig(ele.get(8).toString());
                    }
                    if (ele.get(9) != null) {
                        setFirmHolderName(ele.get(9).toString());
                    }
                    if (ele.get(10) != null) {
                        Date test = (Date) ele.get(10);
                        setRegisterDt(test);
                    }
                    if (ele.get(11) != null) {
                        setWitnessFirstName(ele.get(11).toString());
                    }
                    if (ele.get(12) != null) {
                        setWitnessFirstAddress(ele.get(12).toString());
                    }
                    if (ele.get(13) != null) {
                        setWitnessSecondName(ele.get(13).toString());
                    }
                    if (ele.get(14) != null) {
                        setWitnessSecondAddress(ele.get(14).toString());
                    }
                    if (ele.get(15) != null) {
                        setNomineeName(ele.get(15).toString());
                    }
                    if (ele.get(16) != null) {
                        setNomineeAddress(ele.get(16).toString());
                    }
                    if (ele.get(17) != null) {
                        setNomineeAge(ele.get(17).toString());
                    }
                    if (ele.get(18) != null) {
                        setNomineeRelation(ele.get(18).toString());
                    }
                    if (ele.get(19) != null) {
                        setRemark(ele.get(19).toString());
                    }
                    if (ele.get(20) != null) {
                        if (ele.get(20).toString().equalsIgnoreCase("SELF")) {
                            setOperationMode("1");
                        } else {
                            setOperationMode(ele.get(20).toString());
                        }
                    }
                    if (ele.get(21) != null) {
                        setJtFirstId(ele.get(21).toString());
                    }
                    if (ele.get(22) != null) {
                        setJtName1(ele.get(22).toString());
                    }
                    if (ele.get(23) != null) {
                        setJtSecondId(ele.get(23).toString());
                    }
                    if (ele.get(24) != null) {
                        setJtName2(ele.get(24).toString());
                    }
                    if (ele.get(25) != null) {
                        enterBy = ele.get(25).toString();
                    }
                    if (ele.get(26) != null) {
                        joinDt = sf.parse(ele.get(26).toString());
                    }
                    if (ele.get(27) != null) {
                        salary2 = ele.get(27).toString();
                    }
                    if (ele.get(28) != null) {
                        gPay = ele.get(28).toString();
                    }
                    if (ele.get(29) != null) {
                        area = ele.get(29).toString();
                    }
                    if (ele.get(30) != null) {
                        beneficiaryAcNo = ele.get(30).toString();
                    }
                    if (ele.get(31) != null) {
                        beneficiaryName = ele.get(31).toString();
                    }
                    if (ele.get(32) != null) {
                        ifscCode = ele.get(32).toString();
                    }
                    if (ele.get(33) != null) {
                        bnkName = ele.get(33).toString();
                    }
                    if (ele.get(34) != null) {
                        panNo = ele.get(34).toString();
                    }

                    if (ele.get(35) != null) {
                        if (!(ele.get(35).toString().equalsIgnoreCase("01/01/1900") || ele.get(35).toString().trim().equalsIgnoreCase(""))) {
                            nomineeDob = sdf.parse(ele.get(35).toString());
                        }
                    }
                    
                   setBranch(common.getBrncodeByAlphacode(ele.get(36).toString()));

//                    if (ele.get(35) != null) {
//                        nomineeDob = sdf.parse(ele.get(35).toString());
//                    } 
                }
            }
            if (function.equalsIgnoreCase("2")) {
                if (memberParameter.equalsIgnoreCase("M")) {
                    List occpList = common.getOccupationByfolioNo(fNo);
                    if (!occpList.isEmpty()) {
                        Vector vtr = (Vector) occpList.get(0);
                        this.occupation = vtr.get(0).toString();
                    } else {
                        this.occupation = "9";
                    }

                    //this.nomineeDob = sdf.parse(common.getNomDobByfolioNo(fNo));
                    List intruduserList = common.getIntroducerAcNo(fNo);
                    if (!intruduserList.isEmpty()) {
                        for (int i = 0; i < intruduserList.size(); i++) {
                            Vector vtr = (Vector) intruduserList.get(i);
                            this.intruduser = vtr.get(0).toString();
                            if (!this.intruduser.equalsIgnoreCase("")) {
                                this.intruduserName = ftsPostRemote.ftsGetCustName(this.intruduser);
                            }
                        }
                    }

                }
            }
            editData();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void acnoStatusCheck() {
        try {
            if (bnkName.equals("OWN")) {
                setBeneficiaryName("");
                setIfscCode("");
                if (!beneficiaryAcNo.equalsIgnoreCase("")) {
                    //if (beneficiaryAcNo.length() < 12) {
                    if (!this.beneficiaryAcNo.equalsIgnoreCase("") && ((this.beneficiaryAcNo.length() != 12) && (this.beneficiaryAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                        this.setMessage("Please fill proper Beneficiary A/c No.");
                        return;
                    }
                    if (!beneficiaryAcNo.matches("[0-9.]*")) {
                        this.setMessage("Please fill Numeric Value in Beneficiary A/c No.");
                        return;
                    }

                    newAcno = ftsPostRemote.getNewAccountNumber(beneficiaryAcNo);
                    List acctInfo;
                    //For Ca Account not in table cbs_loan_acc_mast_sec so commit this code.
//                    if (common.getAccseq().equalsIgnoreCase("M")) {
                    acctInfo = loanAcDetails.accountDetailForArmy(newAcno);
//                    } else {
//                        acctInfo = loanAcDetails.accountDetail(newAcno);
//                    }

                    if (!acctInfo.isEmpty()) {
                        Vector vtr = (Vector) acctInfo.get(0);
                        if (vtr.get(3).toString().equalsIgnoreCase("9")) {
                            this.setMessage("Account has been closed !");
                            setBeneficiaryAcNo("");
                            setBeneficiaryName("");
                            setFocusId("txtBeneciaryacno");
                            return;
                        } else {
                            beneficiaryName = vtr.get(1).toString();
                            this.setMessage("");
                            setFocusId("ddCategory");
                            return;
                        }
                    }
                } else {
                    setFocusId("idBnkNameList");
                }
            } else if (bnkName.equals("OTHER")) {
                setFocusId("txtBeneficiaryName");
                return;
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void saveAction() {
        try {
            if (alert.equalsIgnoreCase("This Account has been Closed")) {
                this.setMessage("Dividend Payable In Account is closed account,please fill another account");
                return;
            }

            if (function == null || (function.equalsIgnoreCase(""))) {
                this.setMessage("Please Select The Function");
                return;
            }

            if (function.equalsIgnoreCase("1")) {
                folioNo = "";
            }
            if (function.equalsIgnoreCase("2") && this.getFolioNo().equals("")) {
                this.setMessage("Please fill Folio number.");
                return;
            }
            if (function.equalsIgnoreCase("3")) {
                folioNo = folioNumber;
                if (folioNo.equals("")) {
                    this.setMessage("Please select Folio number.");
                    return;
                }
                if (this.enterBy.equalsIgnoreCase(getUserName())) {
                    this.setMessage("You can not Verify your own transaction");
                    return;
                }
            }
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            if (purpose.equalsIgnoreCase("IF ANY")) {
                purpose = "";
                dividentPayable = "";
            }

            if (nomineeName == null || nomineeName.equalsIgnoreCase("")) {
                nomineeAge = "0";
            }

            if (this.getChkMem().equalsIgnoreCase("true")) {
                if (this.getJoinDt() == null) {
                    this.setMessage("Please Fill Date Of Joining");
                    return;
                } else {
                    int monDiff = CbsUtil.monthDiff(joinDt, date);
                    int mDiff = acOpenFacadeRemote.chkMonDetails();
                    if (mDiff != 0) {
                        if (monDiff <= mDiff) {
                            this.setMessage("Joining Date is Not Proper For member");
                            return;
                        }
                    }
                }
                if (salaryLostFocus() != true) {
                    return;
                }
            }
            if (salary2.equalsIgnoreCase("") || salary2 == null) {
                salary2 = "0";
            }
            if (gPay.equalsIgnoreCase("") || gPay == null) {
                gPay = "0";
            }

//            if (occupation.equalsIgnoreCase("--Select--")) {
//                setMessage("Please Select Occupation");
//                return;
//            }

            if (nomineeDob == null) {
                setNomineeAge("0");
            }

            String accSeq = common.getAccseq();
//            if (function.equalsIgnoreCase("2") && accSeq.equalsIgnoreCase("M")) {
//                if (acnoGenInfo.size() <= 0) {
//                    if (occupation == null || occupation.equalsIgnoreCase("0")) {
//                        setMessage("Please Select occupation");
//                        return;
//                    }
//
//                    if (!panNo.equalsIgnoreCase("")) {
//                        if ((!panNo.contains("Form")) && panNo.length() != 10) {
//                            setMessage("Please fill 10 digits Pan No.");
//                            return;
//                        }
//                    }
//
//                    if (nomineeDob == null) {
//                        setMessage("Please fill nominee date of birth");
//                        return;
//                    }
//
//                    if (intruduser == null || intruduser.equalsIgnoreCase("")) {
//                        setMessage("Please fill Introduser Account No.");
//                        return;
//                    }
//                    if (intruduser.length() > 12) {
//                        setMessage("Please fill Introduser Account No. 12 digits");
//                        return;
//                    }
//
//                    if (docName.equalsIgnoreCase("0")) {
//                        setMessage("Please Select Document Name");
//                        return;
//                    }
//
//                    if (docDetail == null || docDetail.equalsIgnoreCase("")) {
//                        setMessage("Please fill Document Detail");
//                        return;
//                    }
//
//                    if (acNature == null || acNature.equalsIgnoreCase("S")) {
//                        setMessage("Please select account nature !");
//                        return;
//                    }
//                    if (acType == null || acType.equalsIgnoreCase("S")) {
//                        setMessage("Please select account type !");
//                        return;
//                    }
//                    if (schemeCode == null || schemeCode.equalsIgnoreCase("S")) {
//                        setMessage("Please select scheme code !");
//                        return;
//                    }
//                    if (priority.equalsIgnoreCase("0")) {
//                        setMessage("Please select Priority !");
//                        return;
//                    }
//                }
//
//            }

//            if (accSeq.equalsIgnoreCase("M") && function.equalsIgnoreCase("2")) {
//                List chkAcno = remoteObject.checkAcno(this.branch, this.acType);
//                if (!chkAcno.isEmpty()) {
//                    setMessage("Account No already open");
//                    return;
//                }
//            } else 
            if (function.equalsIgnoreCase("1")) {
                List acNoList = new ArrayList<AccGenInfo>();
                acNoList = shareRemoteObject.getFolioAcnoInfo(folioNo, "1");
            }
//            List <AccGenInfo> acOpenList = null ;
//            if (accSeq.equalsIgnoreCase("M") && function.equalsIgnoreCase("2")) {
//                custId = remoteObject.getcustIdByFolioNo(folioNo);
//            }
            if (function.equalsIgnoreCase("2")) {
                custId = shareRemoteObject.getcustIdByFolioNo(folioNo);
            }

            if (purpose.equalsIgnoreCase("IF ANY")) {
//                purpose = "";
                dividentPayable = "";
            }
//            String result = shareRemoteObject.saveAccountOpen(acnoGenInfo, function, custId, folioNo, beneficiaryAcNo, ymd.format(registerDt), witnessFirstName,
//                    witnessFirstAddress, witnessSecondName, witnessSecondAddress, nomineeName, nomineeAddress, nomineeAge, nomineeRelation,
//                    remark, operationMode, jtFirstId, jtSecondId, getUserName(), purpose, businessDesig,
//                    firmHolderName, category, ymd.format(date), getOrgnBrCode(), jtName1, jtName2, ymd.format(joinDt),
//                    salary2, gPay, area, beneficiaryAcNo, beneficiaryName, ifscCode, bnkName, panNo,
//                    ymd.format(nomineeDob == null ? sf.parse("1900-01-01") : nomineeDob), occupation, docName, docDetail, intruduser, actCateg);
            String result = shareRemoteObject.saveAccountOpen(acnoGenInfo, function, custId, folioNo, newAcno, ymd.format(registerDt), witnessFirstName,
                    witnessFirstAddress, witnessSecondName, witnessSecondAddress, nomineeName, nomineeAddress, nomineeAge, nomineeRelation,
                    remark, operationMode, jtFirstId, jtSecondId, getUserName(), purpose, businessDesig,
                    firmHolderName, category, ymd.format(date), getOrgnBrCode(), jtName1, jtName2, ymd.format(joinDt),
                    salary2, gPay, area, newAcno, beneficiaryName, ifscCode, bnkName, panNo,
                    ymd.format(nomineeDob == null ? sf.parse("1900-01-01") : nomineeDob), occupation, docName, docDetail, intruduser, actCateg,common.getAlphacodeByBrncode(branch));

//                                acOpenList,mode, custId, folioNo, relacno, registerDt, wtFName,
//                                wtFAdd, wtSName, wtSAdd, nomineeName, nomineeAdd, nomineeAge, nomineeRelation,
//                                remark, operationMode, jtId1, jtId2, userName, purpose, businessDesig,
//                                firmHolderName, category, date, orgnBrCode, jtName1, jtName2, jDt, 
//                                Sal, gPay, area, benefAcNo, benefName, ifscCode, bnkName, panNo, 
//                                nomineeDob, occupation, docName, docDetail, introAcNo
//            String result = remoteObject.saveAccountOpen(function, custId, folioNo, beneficiaryAcNo, ymd.format(registerDt), witnessFirstName,
//                    witnessFirstAddress, witnessSecondName, witnessSecondAddress, nomineeName, nomineeAddress, nomineeAge, nomineeRelation,
//                    remark, operationMode, jtFirstId, jtSecondId, getUserName(), purpose, businessDesig, firmHolderName, category, ymd.format(date),
//                    getOrgnBrCode(), jtName1, jtName2, ymd.format(joinDt), salary2, gPay, area, beneficiaryAcNo, beneficiaryName, ifscCode, bnkName);
            if (result.contains("Generated Customer Account No is")) {
                setMessage(result);
                clear();
            } else if (result.contains("Account Details successfully updated")) {
                setMessage(result);
                clear();
            } else if (result.contains("Account Details successfully verified")) {
                setMessage(result);
                clear();
            } else {
                setMessage(result);
            }
//            setMessage(result);
//            clear();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        }
    }

    public String validations() {
        try {
            if (registerDt == null) {
                this.setMessage("Please fill Reg.Date");
                return "false";
            }
            if (purpose.equalsIgnoreCase("0")) {
                this.setMessage("Please select the Purpose");
                return "false";
            }
            if (bnkName.equalsIgnoreCase("OWN") && beneficiaryAcNo.equalsIgnoreCase("")) {
                setMessage("Please Enter alpha numeric value for Beneficiary A/c No.");
                return "false";
            }
            if (bnkName.equalsIgnoreCase("OTHER")) {
                if (beneficiaryAcNo.equalsIgnoreCase("")) {
                    setMessage("Please Enter alpha numeric value for Beneficiary A/c No.");
                    return "false";
                }
                if (!beneficiaryName.equalsIgnoreCase("")) {
                    if (!beneficiaryName.matches("[a-zA-Z ]*")) {
                        setNomineeName("");
                        setMessage("Please Enter alpha numeric Value for Beneficiary Name.");
                        return "false";
                    }
                }
                if (!ifscCode.equalsIgnoreCase("")) {
                    if (!ifscCode.equalsIgnoreCase("") && !ifscCode.matches("[a-zA-Z0-9,-/ ]*")) {
                        setNomineeAddress("");
                        setMessage("Please Enter alpha numeric Value for IFSC Code.");
                        return "false";
                    }
                    if (ifscCode.length() < 11) {
                        this.setMessage("Please Enter 11 alpha numeric  digists Value for IFSC Code.");
                        return "false";
                    }

                }
            }
            if (category.equalsIgnoreCase("0")) {
                this.setMessage("Please select the Category");
                return "false";
            }
            if (!witnessFirstName.equalsIgnoreCase("")) {
                if (!witnessFirstName.matches("[a-zA-Z ]*")) {
                    setNomineeName("");
                    setMessage("Please Enter alpha numeric Value for Witness First Name.");
                    return "false";
                }
                if (!witnessFirstAddress.equalsIgnoreCase("") && !witnessFirstAddress.matches("[a-zA-Z0-9,-/ ]*")) {
                    setNomineeAddress("");
                    setMessage("Please Enter alpha numeric Value for Witness First Address");
                    return "false";
                }
            }
            if (!witnessSecondName.equalsIgnoreCase("")) {
                if (!witnessSecondName.matches("[a-zA-Z ]*")) {
                    setNomineeName("");
                    setMessage("Please Enter alpha numeric Value for Witness First Name.");
                    return "false";
                }
                if (!witnessSecondAddress.equalsIgnoreCase("") && !witnessSecondAddress.matches("[a-zA-Z0-9,-/ ]*")) {
                    setNomineeAddress("");
                    setMessage("Please Enter alpha numeric Value for Witness First Address");
                    return "false";
                }
            }
            if (!nomineeName.equalsIgnoreCase("")) {
                if (!nomineeName.matches("[a-zA-Z ]*")) {
                    setNomineeName("");
                    setMessage("Please Enter alpha numeric Value for Nominee Name.");
                    return "false";
                }
                if (nomineeAddress == null || nomineeAddress.equalsIgnoreCase("")) {
                    setMessage("Please Enter Nominee Address");
                    return "false";
                } else {
                    if (!nomineeAddress.matches("[a-zA-Z0-9,-/ ]*")) {
                        setNomineeAddress("");
                        setMessage("Please Enter alpha numeric Value for Nominee Address.");
                        return "false";
                    }
                }
                if (nomineeAge == null || nomineeAge.equalsIgnoreCase("")) {
                    setMessage("Please Enter the Nominee Age");
                    return "false";
                }
                if (!nomineeAge.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Nominee Age");
                    return "false";
                }
                if (nomineeRelation == null || nomineeRelation.equalsIgnoreCase("")) {
                    setMessage("Please Enter Nominee Relationship");
                    return "false";
                } else {
                    if (!nomineeRelation.matches("[a-zA-Z ]*")) {
                        setNomineeRelation("");
                        setMessage("Please Enter alpha numeric Value for Nominee Relationship.");
                        return "false";
                    }
                }
            }

            if (operationMode.equalsIgnoreCase("0")) {
                this.setMessage("Please select the Operation Mode");
                return "false";
            } else {
                if (operationMode.equalsIgnoreCase("2") || operationMode.equalsIgnoreCase("9")) {
                    if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                        this.setMessage("Joint Customer Id 1 cannot be empty.");
                        return "false";
                    }
                } else if (operationMode.equalsIgnoreCase("5")) {
                    if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                        this.setMessage("Both Customer Id names cannot be empty.");
                        return "false";
                    } else if (jtName2 == null || jtName2.equalsIgnoreCase("")) {
                        this.setMessage("Both Customer Id names cannot be empty.");
                        return "false";
                    }
                }
            }

            return "true";
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "false";
    }

    public void getJointNameOne() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custIdCheck = p.matcher(this.getJtFirstId());
            if (!custIdCheck.matches()) {
                this.setMessage("Please Enter Numeric Customer ID for Jt Name 1.");
                setFocusId("txtJointIdFirst");
                return;
            } else {
                this.setMessage("");
            }
            String custinfo = acOpenFacadeRemote.custIdInformation(this.getJtFirstId());
            if (custinfo.equals("This Customer ID Does Not Exists")) {
                setMessage(custinfo);
                setFocusId("txtJointIdFirst");
                return;
            } else {
                String[] values = custinfo.split(":");
                setJtName1(values[1]);
                if (operationMode.equals("5")) {
                    setFocusId("txtJointIdSecond");
                } else {
                    setFocusId("txtRemarks");
                }
            }
        } catch (ApplicationException ex) {
            setFocusId("txtJointIdFirst");
            this.setMessage(ex.getMessage());
        }
    }

    public void getJointNameTwo() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custIdCheck = p.matcher(this.getJtSecondId());
            if (!custIdCheck.matches()) {
                this.setMessage("Please Enter Numeric Customer ID for Jt Name2.");
                setFocusId("txtJointIdSecond");
                return;
            } else {
                this.setMessage("");
            }
            String custinfo = acOpenFacadeRemote.custIdInformation(getJtSecondId());
            if (custinfo.equals("This Customer ID Does Not Exists")) {
                setMessage(custinfo);
                setFocusId("txtJointIdSecond");
                return;
            } else {
                String[] values = custinfo.split(":");
                setJtName2(values[1]);
                setFocusId("txtRemarks");
            }
        } catch (ApplicationException ex) {
            setFocusId("txtJointIdSecond");
            this.setMessage(ex.getMessage());
        }
    }

    public void clear() {
        setFolioNo("");
        setFirstName("");
        setDateofBirth("");
        setFatherName("");
        setWitnessFirstName("");
        setWitnessFirstAddress("");
        setWitnessSecondName("");
        setWitnessSecondAddress("");
        setNomineeName("");
        setNomineeAddress("");
        setNomineeAge("");
        setNomineeRelation("");
        setRemark("");
        setOperationMode("");
        setJtName1("");
        setJtName2("");
        setRegisterDt(new Date());
        setPurpose("0");
        setDividentPayable("");
        setDividentPayableAcno("");
        setBusinessDesig("");
        setFirmHolderName("");
        setCategory("0");
        setCustId("");
        setJtFirstId("");
        setJtSecondId("");
        this.setJoinDt(date);
        this.setSalary2("");
        this.setgPay("");
        this.setArea("S");
        getMemDetail();
        getUnAuthAccounts();
        this.setBeneficiaryAcNo("");
        this.setBeneficiaryName("");
        this.setIfscCode("");
        this.setBnkName("S");
        acnoGenInfo = new ArrayList<AccGenInfo>();
        this.setAcNature("S");
        this.setAcType("S");
        this.setSchemeCode("S");
        this.setDocName("--Select--");
        this.setOccupation("--Select--");
        this.setDocDetail("");
        this.setIntruduser("");
        this.setPanNo("");
        this.setPriority("0");
        this.setFunction("");
        this.setIntruduserName("");
        this.nomineeDob = null;

    }

    public void refreshButtonAction() {
        setMessage("");
        clear();
    }

    public String btnExit() {
        return "case1";
    }

    public void getNewAcnoDtl() {
        setMessage("");
        if (dividentPayableAcno.equalsIgnoreCase("")) {
            setMessage("Please insert dividend payable account");
            return;
        } else if (dividentPayableAcno.length() < 12 || (!validator.validateString(dividentPayableAcno))) {
            setMessage("Please insert a valid account no.");
            return;
        }
        try {
            dividentPayable = ftsPostRemote.getNewAccountNumber(dividentPayableAcno);
            String acStatus = beanRemote.introducerAcDetail(dividentPayable);
            if (acStatus.equals("9")) {
                this.setMessage("This Account has been Closed");
                alert = "This Account has been Closed";
            }
            introducerDetail();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void introducerDetail() {
        try {
            this.setMessage("");
            alert = "";
            String resultList = beanRemote.introducerAcDetail(dividentPayable);
            System.out.println("resultList" + resultList);
            if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                if (resultList.substring(4).equalsIgnoreCase("CLOSED")) {
                    this.setMessage("This Account has been Closed");
                    alert = "This Account has been Closed";
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public boolean salaryLostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getSalary2() == null || this.getSalary2().toString().equalsIgnoreCase("")) {
            this.setMessage("Please Fill salary Field.");
            return false;
        } else if (this.getSalary2().toString().equalsIgnoreCase("0") || this.getSalary2().toString().equalsIgnoreCase("0.0")) {
            this.setMessage("Please Fill salary Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getSalary2().toString());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Entry.");
                return false;
            }
        }
        if (validateAmount() != true) {
            return false;
        }
        return true;
    }

    public boolean validateAmount() {
        if (this.getSalary2().toString().contains(".")) {
            if (this.getSalary2().toString().indexOf(".") != this.getSalary2().toString().lastIndexOf(".")) {
                this.setMessage("Invalid salary.Please Fill The salary Correctly.");
                return false;
            } else if (this.getSalary2().toString().substring(salary2.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The salary Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public void onBlurCustName() {
        setMessage("");
        try {
            this.intruduserName = ftsPostRemote.ftsGetCustName(this.intruduser);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<SelectItem> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(List<SelectItem> occupationList) {
        this.occupationList = occupationList;
    }

    public Date getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(Date nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getIntruduser() {
        return intruduser;
    }

    public void setIntruduser(String intruduser) {
        this.intruduser = intruduser;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<SelectItem> getDocNameList() {
        return docNameList;
    }

    public void setDocNameList(List<SelectItem> docNameList) {
        this.docNameList = docNameList;
    }

    public String getDocDetail() {
        return docDetail;
    }

    public void setDocDetail(String docDetail) {
        this.docDetail = docDetail;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public AccGenInfo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AccGenInfo currentItem) {
        this.currentItem = currentItem;
    }

    public List<AccGenInfo> getAcnoGenInfo() {
        return acnoGenInfo;
    }

    public void setAcnoGenInfo(List<AccGenInfo> acnoGenInfo) {
        this.acnoGenInfo = acnoGenInfo;
    }

    public String getMemberStyle() {
        return memberStyle;
    }

    public void setMemberStyle(String memberStyle) {
        this.memberStyle = memberStyle;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<SelectItem> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<SelectItem> priorityList) {
        this.priorityList = priorityList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public List<SelectItem> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<SelectItem> classificationList) {
        this.classificationList = classificationList;
    }

    public String getCustIdStyle() {
        return custIdStyle;
    }

    public void setCustIdStyle(String custIdStyle) {
        this.custIdStyle = custIdStyle;
    }

    public String getFolioNoStyle() {
        return folioNoStyle;
    }

    public void setFolioNoStyle(String folioNoStyle) {
        this.folioNoStyle = folioNoStyle;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getDividentPayableAcno() {
        return dividentPayableAcno;
    }

    public void setDividentPayableAcno(String dividentPayableAcno) {
        this.dividentPayableAcno = dividentPayableAcno;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isAcnoDisable() {
        return acnoDisable;
    }

    public void setAcnoDisable(boolean acnoDisable) {
        this.acnoDisable = acnoDisable;
    }

    public boolean isJtName1Disable() {
        return jtName1Disable;
    }

    public void setJtName1Disable(boolean jtName1Disable) {
        this.jtName1Disable = jtName1Disable;
    }

    public boolean isJtName2Disable() {
        return jtName2Disable;
    }

    public void setJtName2Disable(boolean jtName2Disable) {
        this.jtName2Disable = jtName2Disable;
    }

    public boolean isFolioNoDisable() {
        return folioNoDisable;
    }

    public void setFolioNoDisable(boolean folioNoDisable) {
        this.folioNoDisable = folioNoDisable;
    }

    public String getBusinessDesig() {
        return businessDesig;
    }

    public void setBusinessDesig(String businessDesig) {
        this.businessDesig = businessDesig;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SelectItem> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public List<SelectItem> getDividendPayableList() {
        return dividendPayableList;
    }

    public void setDividendPayableList(List<SelectItem> dividendPayableList) {
        this.dividendPayableList = dividendPayableList;
    }

    public String getDividentPayable() {
        return dividentPayable;
    }

    public void setDividentPayable(String dividentPayable) {
        this.dividentPayable = dividentPayable;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFirmHolderName() {
        return firmHolderName;
    }

    public void setFirmHolderName(String firmHolderName) {
        this.firmHolderName = firmHolderName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getNomineeAge() {
        return nomineeAge;
    }

    public void setNomineeAge(String nomineeAge) {
        this.nomineeAge = nomineeAge;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public List<SelectItem> getOperatingModeList() {
        return operatingModeList;
    }

    public void setOperatingModeList(List<SelectItem> operatingModeList) {
        this.operatingModeList = operatingModeList;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<SelectItem> getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(List<SelectItem> purposeList) {
        this.purposeList = purposeList;
    }

    public Date getRegisterDt() {
        return registerDt;
    }

    public void setRegisterDt(Date registerDt) {
        this.registerDt = registerDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShareValue() {
        return shareValue;
    }

    public void setShareValue(String shareValue) {
        this.shareValue = shareValue;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
    public String getWitnessFirstAddress() {
        return witnessFirstAddress;
    }

    public void setWitnessFirstAddress(String witnessFirstAddress) {
        this.witnessFirstAddress = witnessFirstAddress;
    }

    public String getWitnessFirstName() {
        return witnessFirstName;
    }

    public void setWitnessFirstName(String witnessFirstName) {
        this.witnessFirstName = witnessFirstName;
    }

    public String getWitnessSecondAddress() {
        return witnessSecondAddress;
    }

    public void setWitnessSecondAddress(String witnessSecondAddress) {
        this.witnessSecondAddress = witnessSecondAddress;
    }

    public String getWitnessSecondName() {
        return witnessSecondName;
    }

    public void setWitnessSecondName(String witnessSecondName) {
        this.witnessSecondName = witnessSecondName;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getJtFirstId() {
        return jtFirstId;
    }

    public void setJtFirstId(String jtFirstId) {
        this.jtFirstId = jtFirstId;
    }

    public String getJtSecondId() {
        return jtSecondId;
    }

    public void setJtSecondId(String jtSecondId) {
        this.jtSecondId = jtSecondId;
    }

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<SelectItem> accountList) {
        this.accountList = accountList;
    }

    public String getVerifyStyle() {
        return verifyStyle;
    }

    public void setVerifyStyle(String verifyStyle) {
        this.verifyStyle = verifyStyle;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public Date getJoinDt() {
        return joinDt;
    }

    public void setJoinDt(Date joinDt) {
        this.joinDt = joinDt;
    }

    public String getSalary2() {
        return salary2;
    }

    public void setSalary2(String salary2) {
        this.salary2 = salary2;
    }

    public boolean isJoinDtDisFlag() {
        return joinDtDisFlag;
    }

    public void setJoinDtDisFlag(boolean joinDtDisFlag) {
        this.joinDtDisFlag = joinDtDisFlag;
    }

    public boolean isSalDisFlag() {
        return salDisFlag;
    }

    public void setSalDisFlag(boolean salDisFlag) {
        this.salDisFlag = salDisFlag;
    }

    public String getChkMem() {
        return chkMem;
    }

    public void setChkMem(String chkMem) {
        this.chkMem = chkMem;
    }

    public String getgPay() {
        return gPay;
    }

    public void setgPay(String gPay) {
        this.gPay = gPay;
    }

    public boolean isgPayDisFlag() {
        return gPayDisFlag;
    }

    public void setgPayDisFlag(boolean gPayDisFlag) {
        this.gPayDisFlag = gPayDisFlag;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<SelectItem> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SelectItem> areaList) {
        this.areaList = areaList;
    }

    public List<SelectItem> getBusinessDesigList() {
        return businessDesigList;
    }

    public void setBusinessDesigList(List<SelectItem> businessDesigList) {
        this.businessDesigList = businessDesigList;
    }

    public String getBeneficiaryAcNo() {
        return beneficiaryAcNo;
    }

    public void setBeneficiaryAcNo(String beneficiaryAcNo) {
        this.beneficiaryAcNo = beneficiaryAcNo;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public List<SelectItem> getBnkNameList() {
        return bnkNameList;
    }

    public void setBnkNameList(List<SelectItem> bnkNameList) {
        this.bnkNameList = bnkNameList;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public boolean isBenefIfscDisable() {
        return benefIfscDisable;
    }

    public void setBenefIfscDisable(boolean benefIfscDisable) {
        this.benefIfscDisable = benefIfscDisable;
    }

    public boolean isBenefNameDisable() {
        return benefNameDisable;
    }

    public void setBenefNameDisable(boolean benefNameDisable) {
        this.benefNameDisable = benefNameDisable;
    }

    public String getIntruduserName() {
        return intruduserName;
    }

    public void setIntruduserName(String intruduserName) {
        this.intruduserName = intruduserName;
    }

    public String getActCateg() {
        return actCateg;
    }

    public void setActCateg(String actCateg) {
        this.actCateg = actCateg;
    }

    public List<SelectItem> getActCategList() {
        return actCategList;
    }

    public void setActCategList(List<SelectItem> actCategList) {
        this.actCategList = actCategList;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getOccupationPanStyle() {
        return occupationPanStyle;
    }

    public void setOccupationPanStyle(String occupationPanStyle) {
        this.occupationPanStyle = occupationPanStyle;
    }
    
    
}