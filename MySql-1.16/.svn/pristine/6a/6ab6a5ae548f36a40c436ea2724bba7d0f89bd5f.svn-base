/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.neftrtgs.SSSFileGeneratorFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.PMBSRegistrationGrid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class SSSAcnoRegistration extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String acno, accountNo, acNoLen;
    private String custName;
    private String custDob;
    private String custAadhar;
    private String custEmailId;
    private String custMobile;
    private String custAdd;
    private String nomName;
    private String nomDob;
    private String nomAdd;
    private String emailId;
    private String mobile;
    private String nomRelationship;
    private List<SelectItem> nomRelationshipList;
    private String nomAadhar;
    private String guardianName;
    private String guardianAdd;
    private String spouseName;
    private String spouseAadhar;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String disability;
    private List<SelectItem> disabilityList;
    private boolean disableDisab;
    private String disabilityDetails;
    private String agentCode;
    private String agentName;
    private List<SelectItem> agentCodeList;
    private String autoDebitFreq;
    private List<SelectItem> autoDebitFreqList;
    private String autoDebitDate;
    private String enrolFlag;
    private String enrolDate;
    private String cancelDate;
    private String enterBy;
    private String enterDate;
    private String auth;
    private String authBy;
    private String enrollCancelation;
    private List<SelectItem> enrollCancelationList;
    private String married;
    private String minorFlag;
    private boolean minorDiable;
    private boolean enrollDiable;
    private List<SelectItem> minorFlagList;
    private List<PMBSRegistrationGrid> gridDetail;
    private PMBSRegistrationGrid currentItem = new PMBSRegistrationGrid();
    int currentRow;
    int age;
    private boolean btnFlag;
    private String btnValue;
    private String postCode;
    private String cityCode;
    private String stateCode;
    private String vendorCode;
    private String gender;
    private String addLine1;
    private String addLine2;
    private List<SelectItem> vendorCodeList;
    private ShareTransferFacadeRemote remoteObject;
    private DDSManagementFacadeRemote transactionsDeleteRemote;
    private SSSFileGeneratorFacadeRemote reportRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote sssRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    private String popupCustFlag = "false";
    private String apyStateCode;
    private List<SelectItem> apyStateCodeList;
    //***************APY*****************
    private String pran;
    private String pensionAmt;
    private String contributionAmt;
    private String incomeTax;
    private List<SelectItem> incomeTaxList;
    private String swalban;
    private List<SelectItem> swalbanList;
    //************Pop-up*****************
    private final String jndiTxnHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    String accNo;
    String accountName;
    String opMode;
    String openDate;
    String jtName;
    String accInstruction;
    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String dpLimit;
    private String scheduleNo;
    private List<SelectItem> scheduleNoList;
    private String sigData;
    private String imageData;
    private String confirmationMsg;

    public SSSAcnoRegistration() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            transactionsDeleteRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            reportRemote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            sssRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiTxnHomeName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setAcNoLen(getAcNoLength());
            String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "--Select--"));
            functionList.add(new SelectItem("1", "Add"));
            functionList.add(new SelectItem("2", "Modify"));
            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("3", "Verify"));
            }
            functionList.add(new SelectItem("4", "Delete"));
            agentCodeList = new ArrayList<SelectItem>();
            agentCodeList.add(new SelectItem("", "--SELECT--"));
            List agCode = transactionsDeleteRemote.getAgCode(getOrgnBrCode());
            if (!agCode.isEmpty()) {
                for (int i = 0; i < agCode.size(); i++) {
                    Vector vec = (Vector) agCode.get(i);
                    agentCodeList.add(new SelectItem(vec.get(0).toString()));
                }
            }

            enrollCancelationList = new ArrayList<SelectItem>();
            enrollCancelationList.add(new SelectItem("S", "--Select--"));
            enrollCancelationList.add(new SelectItem("E", "Enrollment"));
            enrollCancelationList.add(new SelectItem("R", "Registered"));
            enrollCancelationList.add(new SelectItem("L", "Calculate To Premium"));
            enrollCancelationList.add(new SelectItem("C", "Cancel"));

            autoDebitFreqList = new ArrayList<SelectItem>();
            autoDebitFreqList.add(new SelectItem("S", "--Select--"));
            autoDebitFreqList.add(new SelectItem("M", "Monthly"));
            autoDebitFreqList.add(new SelectItem("Y", "Yearly"));

            disabilityList = new ArrayList<SelectItem>();
            disabilityList.add(new SelectItem("S", "--Select--"));
            disabilityList.add(new SelectItem("N", "No"));
            disabilityList.add(new SelectItem("Y", "Yes"));

            minorFlagList = new ArrayList<SelectItem>();
            minorFlagList.add(new SelectItem("S", "--Select--"));
            minorFlagList.add(new SelectItem("N", "No"));
            minorFlagList.add(new SelectItem("Y", "Yes"));
            //***************APY*****************
            incomeTaxList = new ArrayList<SelectItem>();
            incomeTaxList.add(new SelectItem("", "--Select--"));
            incomeTaxList.add(new SelectItem("N", "No"));
            incomeTaxList.add(new SelectItem("Y", "Yes"));

            swalbanList = new ArrayList<SelectItem>();
            swalbanList.add(new SelectItem("", "--Select--"));
            swalbanList.add(new SelectItem("N", "No"));
            swalbanList.add(new SelectItem("Y", "Yes"));

            pmSchemeCode();
            getApyState();
            setBtnValue("Save");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("1")) {
            this.setConfirmationMsg("Are you sure to add this detail ?");
        } else if (this.function.equals("2")) {
            this.setConfirmationMsg("Are you sure to modify this detail ?");
        } else if (this.function.equals("3")) {
            this.setConfirmationMsg("Are you sure to verify this detail ?");
        } else if (this.function.equals("4")) {
            this.setConfirmationMsg("Are you sure to delete this detail ?");
        }
    }

    public void getVendorc() {
        try {
            vendorCodeList = new ArrayList<SelectItem>();
            //List list = sssRemote.getRefRecList("216");
            List list = reportRemote.getVendors(this.schemeCode);
            vendorCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                vendorCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void minorDisb() {
        if (minorFlag.equalsIgnoreCase("N")) {
            this.setMinorDiable(true);
        } else {
            this.setMinorDiable(false);
        }
    }

    public void nomRelation() {
        setMessage("");
        try {
            nomRelationshipList = new ArrayList<SelectItem>();
            List list;
            if (this.vendorCode.equalsIgnoreCase("LIC") || this.schemeCode.equalsIgnoreCase("PMSBY") && this.vendorCode.equalsIgnoreCase("UIC")) {
                list = sssRemote.getRefRecList("218");
            } else {
                list = sssRemote.getRefRecList("217");
            }
            nomRelationshipList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                nomRelationshipList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pmSchemeCode() {
        try {
            schemeCodeList = new ArrayList<SelectItem>();
            List list = sssRemote.getRefRecList("215");
            schemeCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                schemeCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getApyState() {
        try {
            apyStateCodeList = new ArrayList<SelectItem>();
            List list = sssRemote.getRefRecList("219");
            apyStateCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                apyStateCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void chgFunction() {
        try {
            setMessage("");
            if (function.equals("")) {
            } else if (function.equals("1")) {
                gridDetail = new ArrayList<PMBSRegistrationGrid>();
                this.setMessage("");
                this.setSchemeCode("S");
                this.setVendorCode("S");
                this.setAcno("");
                this.setCustName("");
                this.setCustAadhar("");
                this.setCustAdd("");
                this.setCustDob("");
                this.setCustEmailId("");
                this.setCustMobile("");
                this.setMarried("");
                this.btnValue = "Save";
                this.setEnrollCancelation("E");
                this.setDisability("N");
                this.setEnrollDiable(true);
                this.setBtnFlag(false);
                this.setAccountNo("");
            } else if (function.equals("2")) {
                gridDetail = new ArrayList<PMBSRegistrationGrid>();
                this.setMessage("");
                this.setSchemeCode("S");
                this.setVendorCode("S");
                this.setAcno("");
                this.setCustName("");
                this.setCustAadhar("");
                this.setCustAdd("");
                this.setCustDob("");
                this.setCustEmailId("");
                this.setCustMobile("");
                this.setMarried("");
                this.btnValue = "Update";
                this.setBtnFlag(false);
                this.setAccountNo("");
            } else if (function.equals("3")) {
                gridDetail = new ArrayList<PMBSRegistrationGrid>();
                this.setMessage("");
                this.setSchemeCode("S");
                this.setVendorCode("S");
                this.setAcno("");
                this.setCustName("");
                this.setCustAadhar("");
                this.setCustAdd("");
                this.setCustDob("");
                this.setCustEmailId("");
                this.setCustMobile("");
                this.setMarried("");
                this.btnValue = "Verify";
                this.setBtnFlag(false);
                this.setAccountNo("");
            } else if (function.equals("4")) {
                gridDetail = new ArrayList<PMBSRegistrationGrid>();
                this.setMessage("");
                this.setSchemeCode("S");
                this.setVendorCode("S");
                this.setAcno("");
                this.setCustName("");
                this.setCustAadhar("");
                this.setCustAdd("");
                this.setCustDob("");
                this.setCustEmailId("");
                this.setCustMobile("");
                this.setMarried("");
                this.btnValue = "Delete";
                this.setBtnFlag(false);
                this.setAccountNo("");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chenageOperation() {
        setMessage("");
        try {
            Pattern p = Pattern.compile("[0-9]*");

            if (function.equals("")) {
                throw new ApplicationException("Please select Function.");

            } else if (function.equals("1")) {

                if (this.accountNo.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please fill Account Number");
                }

                //if (this.acno.length() != 12) {
                if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    throw new ApplicationException("Account Number Should be Proper");
                }

                Matcher matcher = p.matcher(this.accountNo);
                if (!matcher.matches()) {
                    throw new ApplicationException("Account Number should be in numeric digits.");
                }
                acno = ftsRemote.getNewAccountNumber(this.accountNo);
                
                if (this.acno.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please fill Account Number");
                }

                if (this.acno.length() != 12) {
                    throw new ApplicationException("Account Number Should be 12 Digit");
                }
                
//                if (!chkAcno.equalsIgnoreCase(acno)) {
//                    throw new ApplicationException("Acount No. Invalid");
//                }
                if (!acno.substring(0, 2).equals(getOrgnBrCode())) {
                    throw new ApplicationException("Registration allow only from base branch.");
                }
                List list = reportRemote.chkSSSAcReg(this.schemeCode, this.vendorCode, this.acno);
                if (!list.isEmpty()) {
                    throw new ApplicationException("This Account Number already registrated !");
                }
                String msg = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                String acNature = sssRemote.getAcNatureByAcNo(acno);
                if (!acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    throw new ApplicationException("Account Number should be only saving nature !");
                }
                double premiumAmt = reportRemote.getPremiumAmt(this.schemeCode, this.vendorCode);
                double bal = sssRemote.getBalanceOnDate(acno, ymd.format(dt));

                if (!schemeCode.equalsIgnoreCase("APY")) {
                    if (bal < premiumAmt) {
                        throw new ApplicationException("Balance should be greater than Premium Amount");
                    }
                }

                popupCustFlag = "false";
                List custList = reportRemote.getCustDetailByAcno(acno);
                if (!custList.isEmpty()) {
                    Vector vtr = (Vector) custList.get(0);
                    setCustName(vtr.get(0).toString());
                    String addl1 = vtr.get(1) == null ? "" : vtr.get(1).toString();
                    setCustAdd(addl1);
                    setCustAadhar(vtr.get(2) == null ? "" : vtr.get(2).toString());
                    String cDob = vtr.get(3) == null ? "" : vtr.get(3).toString();
                    setCustDob(cDob);
                    this.age = CbsUtil.yearDiff(dmy.parse(cDob), dmy.parse(dmy.format(dt)));

                    String Mobile = vtr.get(4) == null ? "" : vtr.get(4).toString();
                    setCustMobile(Mobile);
                    setCustEmailId(vtr.get(5) == null ? "" : vtr.get(5).toString());
                    String maritalStatus = vtr.get(6) == null ? "" : vtr.get(6).toString();
                    setMarried(maritalStatus.equalsIgnoreCase("M") ? "MARRIED" : maritalStatus.equalsIgnoreCase("U") ? "UNMARRIED" : maritalStatus.equalsIgnoreCase("W") ? "WIDOW" : "");

                    String pCode = vtr.get(8) == null ? "" : vtr.get(8).toString();
                    String cCode = vtr.get(9) == null ? "" : vtr.get(9).toString();
                    String sCode = vtr.get(10) == null ? "" : vtr.get(10).toString();
                    String gen = vtr.get(11) == null ? "" : vtr.get(11).toString();
                    String addl2 = vtr.get(12) == null ? "" : vtr.get(12).toString();
                    String mailCity = vtr.get(13) == null ? "" : vtr.get(13).toString();
                    String mailPostalCode = vtr.get(14) == null ? "" : vtr.get(14).toString();
                    String mailAddLine1 = vtr.get(15) == null ? "" : vtr.get(15).toString();

                    if (pCode.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                        //throw new ApplicationException("Pin,City,State,Gender,Dob,Address Line1,Address Line2,Marital Status,Maling City,Maling Pin and Mobile No these are mandatory field,First Please check all mandatory field from customer detail form !");
                    }

                    if (cCode.equalsIgnoreCase("") || cCode.matches("[0-9]*")) {
                        popupCustFlag = "true";
                        // throw new ApplicationException("Please first update City from customer detail form !");
                    }
                    if (sCode.equalsIgnoreCase("") || sCode.matches("[0-9]*")) {
                        popupCustFlag = "true";
                        // throw new ApplicationException("Please first update State from customer detail form !");
                    }

                    if (gen.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                        //throw new ApplicationException("Please first update Gender customer detail form !");
                    }

                    if (maritalStatus.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                        // throw new ApplicationException("Please first update Marital Status customer detail form !");
                    }

                    if (addl1.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                        //throw new ApplicationException("Please first update Address Line1 from customer detail form !");
                    }

                    if (addl2.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                        //throw new ApplicationException("Please first update Address Line2 from customer detail form !");
                    }

                    if (cDob.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                    }

                    if (Mobile.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                    }

                    if (mailCity.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                    }

                    if (mailPostalCode.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                    }

                    if (mailAddLine1.equalsIgnoreCase("")) {
                        popupCustFlag = "true";
                    }
                }
                this.setDisability("N");
                gridDetail = new ArrayList<PMBSRegistrationGrid>();
                setBtnValue("Save");
            } else if (function.equals("2")) {
                 acno = ftsRemote.getNewAccountNumber(this.accountNo);
                if (this.acno.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please fill Account Number");
                }

                if (this.acno.length() != 12) {
                    throw new ApplicationException("Account Number Should be 12 Digit");
                }

                Matcher matcher = p.matcher(this.acno);
                if (!matcher.matches()) {
                    throw new ApplicationException("Account Number should be in numeric digits.");
                }
                gridLoad();
                setBtnValue("Update");
            } else if (function.equals("3")) {
                gridLoad();
                setBtnValue("Verify");
            } else if (function.equals("4")) {
                gridLoad();
                setBtnValue("Delete");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getAgName() {
        setMessage("");
        try {
            setAgentName(transactionsDeleteRemote.getAgentName(this.agentCode, getOrgnBrCode()));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void autoDebitFreqFunc() {
        setMessage("");
        try {
            this.setAutoDebitDate(dmy.format(dt));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void disabilityFucn() {
        if (disability.equalsIgnoreCase("N")) {
            setDisableDisab(true);
        } else if (disability.equalsIgnoreCase("Y")) {
            setDisableDisab(false);
        }
    }

    public void gridLoad() {
        setMessage("");
        try {
            List pmList = reportRemote.getPmRegDetail(this.function, this.schemeCode, this.vendorCode, getOrgnBrCode(), this.accountNo);
            gridDetail = new ArrayList<PMBSRegistrationGrid>();
            if (!pmList.isEmpty()) {
                for (int i = 0; i < pmList.size(); i++) {
                    Vector vtr = (Vector) pmList.get(i);
                    PMBSRegistrationGrid obj = new PMBSRegistrationGrid();
                    obj.setAcNo(vtr.get(0).toString());
                    obj.setCustName(ftsRemote.ftsGetCustName(vtr.get(0).toString()));
                    obj.setNomName(vtr.get(1).toString());
                    obj.setNomDob(vtr.get(2).toString());

                    obj.setNomAdd(vtr.get(3).toString());
                    obj.setNomRelationship(vtr.get(4).toString());
                    obj.setNomAadhar(vtr.get(5).toString());
                    obj.setGuardianName(vtr.get(6).toString());

                    obj.setGuardianAdd(vtr.get(7).toString());
                    obj.setSpouseName(vtr.get(8).toString());
                    obj.setSpouseAadhar(vtr.get(9).toString());
                    obj.setSchemeCode(vtr.get(10).toString().equalsIgnoreCase("PMJJBY") ? "JEEVAN JYOTI BIMA YOJANA " : vtr.get(10).toString().equalsIgnoreCase("PMSBY") ? "SURAKSHA BIMA YOJANA" : vtr.get(10).toString().equalsIgnoreCase("APY") ? "ATAL PENSION YOJNA" : vtr.get(10).toString());

                    obj.setDisability(vtr.get(11).toString());
                    obj.setDisabilityDetails(vtr.get(12).toString());
                    obj.setAutoDebitDate(vtr.get(13).toString());
                    obj.setEnrolFlag(vtr.get(14).toString());
                    obj.setEnrolDate(vtr.get(15).toString());

                    obj.setEnterBy(vtr.get(16).toString());
                    obj.setAuth(vtr.get(17).toString());
                    obj.setVendorCode(vtr.get(18).toString().equalsIgnoreCase("LIC") ? "LIC OF INDIA" : vtr.get(18).toString().equalsIgnoreCase("OIC") ? "ORIENTAL INSURANCE" : vtr.get(18).toString().equalsIgnoreCase("SBI") ? "SBI LIFE INSURANCE" : vtr.get(18).toString());
                    obj.setMinorFa(vtr.get(19).toString());
                    obj.setSwavalamb(vtr.get(20).toString());
                    obj.setPran(vtr.get(21).toString());
                    obj.setIncomeTaxp(vtr.get(22).toString());
                    obj.setApyPenAmt(Double.parseDouble(vtr.get(23).toString()));
                    obj.setApyConAmt(Double.parseDouble(vtr.get(24).toString()));
                    obj.setApyState(vtr.get(25).toString());
                    gridDetail.add(obj);
                }
            } else {
                if (this.function.equalsIgnoreCase("3")) {
                    throw new ApplicationException("No Data for Verify");
                } else if (this.function.equalsIgnoreCase("4")) {
                    throw new ApplicationException("No Data for Delete");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void select() {
        this.setMessage("");
        try {
            this.setAcno(currentItem.getAcNo());
            this.setNomName(currentItem.getNomName());
            this.setNomAadhar(currentItem.getNomAadhar());
            this.setNomAdd(currentItem.getNomAdd());
            this.setNomDob(currentItem.getNomDob());
            this.setNomRelationship(currentItem.getNomRelationship());
            this.setSpouseAadhar(currentItem.getSpouseAadhar());
            this.setGuardianName(currentItem.getGuardianName());
            this.setGuardianAdd(currentItem.getGuardianAdd());
            this.setSpouseName(currentItem.getSpouseName());
            //this.setSchemeCode(currentItem.getSchemeCode().equalsIgnoreCase("JEEVAN JYOTI BIMA YOJANA") ? "PMJJBY" : currentItem.getSchemeCode().equalsIgnoreCase("SURAKSHA BIMA YOJANA") ? "PMSBY" : currentItem.getSchemeCode().equalsIgnoreCase("ATAL PENSION YOJNA") ? "APY" : currentItem.getSchemeCode());
            this.setAgentCode(currentItem.getAgentCode());
            this.setEnrolDate(currentItem.getEnrolDate());
            this.setEnrolFlag(currentItem.getEnrolFlag());
            this.setEnterBy(currentItem.getEnterBy());
            this.setEnterDate(currentItem.getEnterDate());
            this.setAutoDebitFreq(currentItem.getAutoDebitFreq());
            this.setAutoDebitDate(currentItem.getAutoDebitDate());
            this.setDisability(currentItem.getDisability());
            this.setDisabilityDetails(currentItem.getDisabilityDetails());
            this.setEnrollCancelation(currentItem.getEnrolFlag());
            this.setNomRelationship(currentItem.getNomRelationship());
            this.setAuth(currentItem.getAuth());
            // this.setVendorCode(currentItem.getVendorCode().equalsIgnoreCase("LIC OF INDIA") ? "LIC" : currentItem.getVendorCode().equalsIgnoreCase("SBI LIFE INSURANCE") ? "SBI" : currentItem.getVendorCode().equalsIgnoreCase("ORIENTAL INSURANCE") ? "OIC" : currentItem.getVendorCode());
            this.setMinorFlag(currentItem.getMinorFa());
            if (this.schemeCode.equalsIgnoreCase("APY")) {
                this.setSwalban(currentItem.getSwavalamb());
                this.setPran(currentItem.getPran());
                this.setIncomeTax(currentItem.getIncomeTaxp());
                this.setPensionAmt(String.valueOf(currentItem.getApyPenAmt()));
                this.setContributionAmt(String.valueOf(currentItem.getApyConAmt()));
                this.setApyStateCode(currentItem.getApyState());
            }
            this.setBtnFlag(false);
            this.setEnrollDiable(false);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public boolean validateField() throws ApplicationException {
        this.setMessage("");
        Pattern p = Pattern.compile("[0-9]*");
        Pattern pm = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
        try {
            if ((this.function == null) || (this.getFunction().equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Select Function");
            }
            if (this.schemeCode.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Scheme code !");
            }
            if (this.vendorCode.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select vendor code !");
            }
            List custList = reportRemote.getCustDetailByAcno(acno);
            Vector ele = (Vector) custList.get(0);

            String addl1 = ele.get(1) == null ? "" : ele.get(1).toString();
            String cDob = ele.get(3) == null ? "" : ele.get(3).toString();
            String MobileNo = ele.get(4) == null ? "" : ele.get(4).toString();
            String maritalStatus = ele.get(6) == null ? "" : ele.get(6).toString();
            String pCode = ele.get(8) == null ? "" : ele.get(8).toString();
            String cCode = ele.get(9) == null ? "" : ele.get(9).toString();
            String sCode = ele.get(10) == null ? "" : ele.get(10).toString();
            String gen = ele.get(11) == null ? "" : ele.get(11).toString();
            String addl2 = ele.get(12) == null ? "" : ele.get(12).toString();
            String mailCity = ele.get(13) == null ? "" : ele.get(13).toString();
            String mailPostalCode = ele.get(14) == null ? "" : ele.get(14).toString();

            if (cDob.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Customer DOB customer detail form !");
            }
            this.age = CbsUtil.yearDiff(dmy.parse(cDob), dt);
            this.setAge(age);

            if (this.schemeCode.equalsIgnoreCase("PMJJBY")) {
                String pmjbyDt1 = CbsUtil.yearAdd(ymd.format(dmy.parse(cDob)), 18);
                if (ymd.parse(pmjbyDt1).after(new Date())) {
                    throw new ApplicationException("Age should be between 18 and 50.");
                }
                String pmjbyDt = CbsUtil.yearAdd(ymd.format(dmy.parse(cDob)), 50);
                if (dmy.parse(getTodayDate()).after(ymd.parse(pmjbyDt))) {
                    throw new ApplicationException("Age should be between 18 and 50.");
                }
            } else if (this.schemeCode.equalsIgnoreCase("PMSBY")) {

                String pmSbyDt1 = CbsUtil.yearAdd(ymd.format(dmy.parse(cDob)), 18);
                if (ymd.parse(pmSbyDt1).after(new Date())) {
                    throw new ApplicationException("Age should be between 18 and 70.");
                }
                String pmSbyDt = CbsUtil.yearAdd(ymd.format(dmy.parse(cDob)), 70);
                if (dmy.parse(getTodayDate()).after(ymd.parse(pmSbyDt))) {
                    throw new ApplicationException("Age should be between 18 and 70.");
                }
            } else if (this.schemeCode.equalsIgnoreCase("APY")) {

                String apyDt1 = CbsUtil.yearAdd(ymd.format(dmy.parse(cDob)), 18);
                if (ymd.parse(apyDt1).after(new Date())) {
                    throw new ApplicationException("Age should be between 18 and 40.");
                }
                String apyDt = CbsUtil.yearAdd(ymd.format(dmy.parse(cDob)), 40);
                if (dmy.parse(getTodayDate()).after(ymd.parse(apyDt))) {
                    throw new ApplicationException("Age should be between 18 and 40.");
                }
            }

            if (MobileNo.length() != 10) {
                throw new ApplicationException("Customer Mobile No only should be 10 digits.");
            }

            if (pCode.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Pin Code from customer detail form !");
            }
            if (pCode.length() != 6) {
                throw new ApplicationException("Pin should be 6 digits !");
            }

            if (cCode.equalsIgnoreCase("") || cCode.matches("[0-9]*")) {
                throw new ApplicationException("Please first update City from customer detail form !");
            }
            if (sCode.equalsIgnoreCase("") || sCode.matches("[0-9]*")) {
                throw new ApplicationException("Please first update State from customer detail form !");
            }

            if (gen.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Gender customer detail form !");
            }

            if (maritalStatus.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Marital Status customer detail form !");
            }

            if (addl1.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Address Line1 from customer detail form !");
            }

            if (addl2.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Address Line2 from customer detail form !");
            }

            if (mailCity.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Maling City from customer detail form !");
            }

            if (mailPostalCode.equalsIgnoreCase("")) {
                throw new ApplicationException("Please first update Maling Pin from customer detail form !");
            }

            if (this.function.equalsIgnoreCase("1")) {
                if (this.getMarried().equalsIgnoreCase("MARRIED") || this.getMarried().equalsIgnoreCase("WIDOW")) {
                    if (this.spouseName.equalsIgnoreCase("")) {
                        throw new ApplicationException("Please Enter Spouse Name !");
                    }
                }
            }

            if (!this.spouseName.equalsIgnoreCase("")) {
                Matcher spMatcher = pm.matcher(this.spouseName);
                if (!spMatcher.matches()) {
                    throw new ApplicationException("Please Enter Spouse Name Correctly");
                }
            }

            if (!this.spouseAadhar.equalsIgnoreCase("")) {
                Matcher spMatcher1 = p.matcher(this.spouseAadhar);
                if (!spMatcher1.matches()) {
                    throw new ApplicationException("Spouse Aadhar No. should be numeric digist");
                }
            }

            if (this.nomName.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Enter Nominee Name");
            }
            Matcher nomMatcher = pm.matcher(this.nomName);
            if (!nomMatcher.matches()) {
                throw new ApplicationException("Please Enter Nominee Name Correctly");
            }

            if (this.nomDob.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Enter Nominee DOB.");
            }

            if (!Validator.validateDate(this.nomDob)) {
                throw new ApplicationException("Please fill Proper date of birth");
            }

            if ((dmy.parse(this.nomDob)).after(dt)) {
                throw new ApplicationException("date of birth should be less than current date");
            }

            int minorAge = CbsUtil.yearDiff(dmy.parse(this.nomDob), dt);
            if (this.minorFlag.equalsIgnoreCase("N")) {
                if (minorAge < 18) {
                    throw new ApplicationException("Nominee DOB should be greater than 18 year.");
                }
            } else {
                if (minorAge > 18) {
                    throw new ApplicationException("Nominee DOB should be less than 18 year.");
                }
            }

            if (this.nomAdd.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Enter Nominee Address.");
            }

            if (!this.nomAdd.matches("[a-zA-Z0-9,-/ ]*")) {
                throw new ApplicationException("Please Enter Nominee Address Correctly");
            }
            if (!this.nomAadhar.equalsIgnoreCase("")) {
                if (this.nomAadhar.length() != 12) {
                    throw new ApplicationException("Nominee Aadhar No. should be 12 digits.");
                }
                Matcher nomaadMatcher = p.matcher(this.nomAadhar);
                if (!nomaadMatcher.matches()) {
                    throw new ApplicationException("Nominee Aadhar No. should be numeric digist");
                }
            }

            if (this.nomRelationship.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Nominee Relationship");
            }

            if (this.minorFlag.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Minor Flag !");
            }

            if (this.minorFlag.equalsIgnoreCase("Y")) {
                if (this.guardianName.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please Enter Guardian Name !");
                }

                if (this.guardianAdd.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please Enter Guardian Address !");
                }
            }

            if (!this.guardianName.equalsIgnoreCase("")) {
                Matcher guarMatcher = pm.matcher(this.guardianName);
                if (!guarMatcher.matches()) {
                    throw new ApplicationException("Please Enter Guardian Name Correctly");
                }
            }

            if (!this.guardianAdd.equalsIgnoreCase("")) {
                if (!this.guardianAdd.matches("[a-zA-Z0-9,-/ ]*")) {
                    throw new ApplicationException("Please Enter Guardian Address Correctly");
                }
            }

            if (this.enrollCancelation.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Enrollment Type !");
            }

            if (disability.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select disaility.");
            }

            String brCode = sssRemote.getBrncodeByAcno(acno);
            if (!brCode.equalsIgnoreCase(getOrgnBrCode())) {
                throw new ApplicationException("Registration only won branch!");
            }

            if (!(this.function.equalsIgnoreCase("2") && enrollCancelation.equalsIgnoreCase("C"))) { //Modify with cancel
                String msg = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            if (this.schemeCode.equalsIgnoreCase("APY")) {

                if (this.swalban == null || this.swalban.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please select swalamban Sub");
                }
                if (pran == null || pran.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please fill PRAN");
                }
                if (pran.length() != 12) {
                    throw new ApplicationException("PRAN should be 12 digits");
                }
                if (!pran.matches("[0-9]*")) {
                    throw new ApplicationException("PRAN should be numeric form");
                }
                if (this.pensionAmt == null || this.pensionAmt.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please fill Pension Amount");
                }

                if (this.pensionAmt.equalsIgnoreCase("0")) {
                    throw new ApplicationException("Pension Amount should not be zero !");
                }
                if (!(this.pensionAmt.contains("1000") || this.pensionAmt.contains("2000") || this.pensionAmt.contains("3000") || this.pensionAmt.contains("4000") || this.pensionAmt.contains("5000"))) {
                    throw new ApplicationException("Please Pension Amount fill only 1000,2000,3000,4000 and 5000 !");
                }
                if (this.contributionAmt == null || this.contributionAmt.equalsIgnoreCase("0")) {
                    throw new ApplicationException("Contribution Amount should not be blank and zero !");
                }

                if (this.incomeTax == null || incomeTax.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please select Income tax Period.");
                }

                if (this.apyStateCode == null || this.apyStateCode.equalsIgnoreCase("") || this.apyStateCode.equalsIgnoreCase("S")) {
                    throw new ApplicationException("Please select State Code.");
                }
            }

            if (function.equalsIgnoreCase("3")) {
                if (this.enterBy.equalsIgnoreCase(getUserName())) {
                    throw new ApplicationException("You can not Verify your own transaction");
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    public void retrieveContributationAmount() {
        setMessage("");
        try {
            if (this.pensionAmt == null || this.pensionAmt.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Pension Amount");
            }
            if (!(this.pensionAmt.contains("1000") || this.pensionAmt.contains("2000") || this.pensionAmt.contains("3000") || this.pensionAmt.contains("4000") || this.pensionAmt.contains("5000"))) {
                throw new ApplicationException("Please Pension Amount fill only 1000 , 2000 , 3000 , 4000  and  5000 !");
            }
            double contAmt = reportRemote.getContributationAmount(age, pensionAmt);
            this.setContributionAmt(String.valueOf(contAmt));

            if (sssRemote.getBalanceOnDate(acno, ymd.format(dt)) < contAmt) {
                throw new ApplicationException("Balance should be greater than Contribution Amount");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void saveMasterDetail() {
        setMessage("");
        try {
            if ((this.function == null) || (this.getFunction().equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Select Function");
            }
            if (this.function.equalsIgnoreCase("1")) {
                List list = reportRemote.chkSSSAcReg(this.schemeCode, this.vendorCode, this.acno);
                if (!list.isEmpty()) {
                    setMessage("This Account Number already registrated !");
                    return;
                }
            }

            if (getBtnValue().equalsIgnoreCase("Verify")) {
                if (this.acno == null || this.acno.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please Select only one row from grid !");
                }
                this.auth = "Y";
            } else if (getBtnValue().equalsIgnoreCase("Delete")) {
                if (this.acno == null || this.acno.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please Select only one row from grid !");
                }
            }

            if (schemeCode.equalsIgnoreCase("APY")) {
                if (this.contributionAmt == null || this.contributionAmt.equalsIgnoreCase("") || this.contributionAmt.equalsIgnoreCase("0")) {
                    throw new ApplicationException("Contribution Amount should not be blank and zero !");
                }
                if (sssRemote.getBalanceOnDate(acno, ymd.format(dt)) < Double.parseDouble(contributionAmt)) {
                    throw new ApplicationException("Balance should be greater than Contribution Amount");
                }
            } else {
                if (!(this.function.equalsIgnoreCase("2") && enrollCancelation.equalsIgnoreCase("C"))) { //Modify with cancel
                    if (sssRemote.getBalanceOnDate(acno, ymd.format(dt)) < reportRemote.getPremiumAmt(this.schemeCode, this.vendorCode)) {
                        throw new ApplicationException("Balance should be greater than Premium Amount");
                    }
                }
            }
            if (getBtnValue().equalsIgnoreCase("Save")) {
                this.auth = "N";
            }
            if (this.agentCode == null || this.agentCode.equalsIgnoreCase("--Select--")) {
                this.agentCode = "";
            }
            if (enrollCancelation.equalsIgnoreCase("C")) {
                cancelDate = ymd.format(dt);
            } else {
                cancelDate = "NULL";
            }
            if (!this.schemeCode.equalsIgnoreCase("APY")) {
                pran = "";
                pensionAmt = "0";
                contributionAmt = "0";
                incomeTax = "";
                swalban = "";
                this.apyStateCode = "";
            }
            if (validateField()) {
                String result = reportRemote.pmRegistrationDetailSaveUpdateVeryfy(getBtnValue(), this.acno, this.nomName, ymd.format(dmy.parse(this.nomDob)),
                        this.nomAdd, this.nomRelationship, this.nomAadhar, this.guardianName, this.guardianAdd, this.spouseName, this.spouseAadhar,
                        this.schemeCode, this.disability, this.disabilityDetails, this.enrollCancelation, ymd.format(dt), cancelDate, this.getUserName(),
                        ymd.format(dt), this.auth, this.getUserName(), getOrgnBrCode(), this.minorFlag, this.vendorCode, this.pran, this.pensionAmt,
                        this.contributionAmt, this.incomeTax, this.swalban, this.apyStateCode);
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    if (function.equalsIgnoreCase("1")) {
                        this.setBtnFlag(true);
                        this.setFunction("1");
                        gridLoad();
                        refreshForm1();
                        this.setMessage("Data has been saved successfully.");
                    } else if (function.equalsIgnoreCase("2")) {
                        refreshForm();
                        this.setMessage("Data has been Updated successfully.");
                        this.setBtnFlag(true);
                    } else if (function.equalsIgnoreCase("3")) {
                        refreshForm1();
                        this.setMessage("Data has been verified successfully and generated batch no is " + result.substring(4));
                        this.setBtnFlag(true);
                        gridDetail.remove(currentRow);
                    } else if (function.equalsIgnoreCase("4")) {
                        refreshForm1();
                        this.setMessage("Data has been Delete successfully.");
                        this.setBtnFlag(true);
                        gridDetail.remove(currentRow);
                    }
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        setFunction("");
        setAcno("");
        setCustName("");
        setCustAdd("");
        setCustAadhar("");
        setCustDob("");
        setCustEmailId("");
        setCustMobile("");
        setCancelDate("");
        setSpouseName("");
        setSpouseAadhar("");
        setSchemeCode("");
        setGuardianName("");
        setGuardianAdd("");
        setNomName("");
        setNomAdd("");
        setNomAadhar("");
        setNomDob("");
        setNomRelationship("");
        setAgentCode("");
        setAgentName("");
        setAuth("");
        setAuth("");
        setAuthBy("");
        setAutoDebitDate("");
        setAutoDebitFreq("");
        setEmailId("");
        setEnrollCancelation("S");
        setDisability("S");
        setDisabilityDetails("");
        setMarried("");
        setVendorCode("S");
        this.btnValue = "Save";
        this.setBtnFlag(true);
        this.setSwalban("");
        this.setIncomeTax("");
        this.setPensionAmt("");
        this.setContributionAmt("");
        this.setPran("");
        this.setApyStateCode("S");
        this.setEnrollDiable(false);
        setMinorFlag("S");
        gridDetail = new ArrayList<PMBSRegistrationGrid>();
        setAccountNo("");
    }

    public void refreshForm1() {
        setMessage("");
        setFunction("");
        setAcno("");
        setCustName("");
        setCustAdd("");
        setCustAadhar("");
        setCustDob("");
        setCustEmailId("");
        setCustMobile("");
        setCancelDate("");
        setSpouseName("");
        setSpouseAadhar("");
        setSchemeCode("");
        setGuardianName("");
        setGuardianAdd("");
        setNomName("");
        setNomAdd("");
        setNomAadhar("");
        setNomDob("");
        setNomRelationship("");
        setAgentCode("");
        setAgentName("");
        setAuth("");
        setAuth("");
        setAuthBy("");
        setAutoDebitDate("");
        setAutoDebitFreq("");
        setEmailId("");
        setEnrollCancelation("S");
        setDisability("S");
        setDisabilityDetails("");
        setMarried("");
        setVendorCode("S");
        this.btnValue = "Save";
        this.setBtnFlag(true);
        this.setSwalban("");
        this.setIncomeTax("");
        this.setPensionAmt("");
        this.setContributionAmt("");
        this.setPran("");
        this.setApyStateCode("S");

    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getApyStateCode() {
        return apyStateCode;
    }

    public void setApyStateCode(String apyStateCode) {
        this.apyStateCode = apyStateCode;
    }

    public List<SelectItem> getApyStateCodeList() {
        return apyStateCodeList;
    }

    public void setApyStateCodeList(List<SelectItem> apyStateCodeList) {
        this.apyStateCodeList = apyStateCodeList;
    }

    public boolean isMinorDiable() {
        return minorDiable;
    }

    public void setMinorDiable(boolean minorDiable) {
        this.minorDiable = minorDiable;
    }

    public boolean isEnrollDiable() {
        return enrollDiable;
    }

    public void setEnrollDiable(boolean enrollDiable) {
        this.enrollDiable = enrollDiable;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public boolean isDisableDisab() {
        return disableDisab;
    }

    public void setDisableDisab(boolean disableDisab) {
        this.disableDisab = disableDisab;
    }

    public List<SelectItem> getAutoDebitFreqList() {
        return autoDebitFreqList;
    }

    public void setAutoDebitFreqList(List<SelectItem> autoDebitFreqList) {
        this.autoDebitFreqList = autoDebitFreqList;
    }

    public List<SelectItem> getDisabilityList() {
        return disabilityList;
    }

    public void setDisabilityList(List<SelectItem> disabilityList) {
        this.disabilityList = disabilityList;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustDob() {
        return custDob;
    }

    public void setCustDob(String custDob) {
        this.custDob = custDob;
    }

    public String getCustAadhar() {
        return custAadhar;
    }

    public void setCustAadhar(String custAadhar) {
        this.custAadhar = custAadhar;
    }

    public String getCustEmailId() {
        return custEmailId;
    }

    public void setCustEmailId(String custEmailId) {
        this.custEmailId = custEmailId;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getCustAdd() {
        return custAdd;
    }

    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    public List<PMBSRegistrationGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<PMBSRegistrationGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public PMBSRegistrationGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(PMBSRegistrationGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getNomName() {
        return nomName;
    }

    public void setNomName(String nomName) {
        this.nomName = nomName;
    }

    public String getNomDob() {
        return nomDob;
    }

    public void setNomDob(String nomDob) {
        this.nomDob = nomDob;
    }

    public String getNomAdd() {
        return nomAdd;
    }

    public void setNomAdd(String nomAdd) {
        this.nomAdd = nomAdd;
    }

    public String getNomRelationship() {
        return nomRelationship;
    }

    public void setNomRelationship(String nomRelationship) {
        this.nomRelationship = nomRelationship;
    }

    public List<SelectItem> getNomRelationshipList() {
        return nomRelationshipList;
    }

    public void setNomRelationshipList(List<SelectItem> nomRelationshipList) {
        this.nomRelationshipList = nomRelationshipList;
    }

    public String getNomAadhar() {
        return nomAadhar;
    }

    public void setNomAadhar(String nomAadhar) {
        this.nomAadhar = nomAadhar;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianAdd() {
        return guardianAdd;
    }

    public void setGuardianAdd(String guardianAdd) {
        this.guardianAdd = guardianAdd;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSpouseAadhar() {
        return spouseAadhar;
    }

    public void setSpouseAadhar(String spouseAadhar) {
        this.spouseAadhar = spouseAadhar;
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

    public String getPopupCustFlag() {
        return popupCustFlag;
    }

    public void setPopupCustFlag(String popupCustFlag) {
        this.popupCustFlag = popupCustFlag;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getDisabilityDetails() {
        return disabilityDetails;
    }

    public void setDisabilityDetails(String disabilityDetails) {
        this.disabilityDetails = disabilityDetails;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
    }

    public String getAutoDebitFreq() {
        return autoDebitFreq;
    }

    public void setAutoDebitFreq(String autoDebitFreq) {
        this.autoDebitFreq = autoDebitFreq;
    }

    public String getAutoDebitDate() {
        return autoDebitDate;
    }

    public void setAutoDebitDate(String autoDebitDate) {
        this.autoDebitDate = autoDebitDate;
    }

    public String getEnrolFlag() {
        return enrolFlag;
    }

    public void setEnrolFlag(String enrolFlag) {
        this.enrolFlag = enrolFlag;
    }

    public String getEnrolDate() {
        return enrolDate;
    }

    public void setEnrolDate(String enrolDate) {
        this.enrolDate = enrolDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnrollCancelation() {
        return enrollCancelation;
    }

    public void setEnrollCancelation(String enrollCancelation) {
        this.enrollCancelation = enrollCancelation;
    }

    public List<SelectItem> getEnrollCancelationList() {
        return enrollCancelationList;
    }

    public void setEnrollCancelationList(List<SelectItem> enrollCancelationList) {
        this.enrollCancelationList = enrollCancelationList;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
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

    public String getMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(String minorFlag) {
        this.minorFlag = minorFlag;
    }

    public List<SelectItem> getMinorFlagList() {
        return minorFlagList;
    }

    public void setMinorFlagList(List<SelectItem> minorFlagList) {
        this.minorFlagList = minorFlagList;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public List<SelectItem> getVendorCodeList() {
        return vendorCodeList;
    }

    public void setVendorCodeList(List<SelectItem> vendorCodeList) {
        this.vendorCodeList = vendorCodeList;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddLine1() {
        return addLine1;
    }

    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    public String getAddLine2() {
        return addLine2;
    }

    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    //************Pop-up method***************** 
    public void getSignatureDetail() {
        try {
            this.imageData = null;
            int Toper;
            String signature;
            // accNo = currentItem.getAcNo();
            accNo = this.acno;
            accountName = "";
            List list = txnAuthRemote.getDataForSignature(accNo);
            System.out.println("list    " + list);
            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                jtName = vec.get(0).toString() + " " + vec.get(1).toString() + " " + vec.get(2).toString() + " " + vec.get(3).toString();
                opMode = vec.get(5).toString();
                Toper = Integer.parseInt(opMode);
                if (Toper == 1) {
                    if ((Toper == 1 && !accNo.equalsIgnoreCase("CA")) && (Toper == 1 && !accNo.equalsIgnoreCase("CC"))) {
                        opMode = "Self";
                    } else {
                        opMode = "Self";
                    }
                } else {
                    List list2 = txnAuthRemote.selectForOperationMode(opMode);
                    Vector v2 = (Vector) list2.get(0);
                    this.setOpMode(v2.get(0).toString());

                }

                accInstruction = vec.get(4).toString();
                String accOpenDate = vec.get(6).toString().substring(6) + "/" + vec.get(6).toString().substring(4, 6) + "/" + vec.get(6).toString().substring(0, 4);
                openDate = accOpenDate;
                this.setDpLimit(vec.get(8).toString());
                this.setAccountName(vec.get(9).toString());
                List<String> custIdList = txnRemote.getCustIdByAccount(accNo);
                if (!custIdList.isEmpty()) {
                    this.setCustId(custIdList.get(0));
                    this.setAnnualTurnover(custIdList.get(1));
                    this.setRiskCategorization(custIdList.get(2));
                    this.setCustPanNo(custIdList.get(3));
                    this.setProfession(custIdList.get(4));
                } else {
                    this.setCustId("");
                    this.setAnnualTurnover("");
                    this.setRiskCategorization("");
                    this.setCustPanNo("");
                    this.setProfession("");
                }
            }
            signature = txnAuthRemote.getSignatureDetails(accNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(accNo);
                String filePath = directoryPath + encryptAcno + ".xml";
                sigData = CbsUtil.readImageFromXMLFile(new File(filePath));
//                this.setErrorMsg("");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (FileNotFoundException e) {
            this.setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (sigData != null) {
            byte[] sigBytes = Base64.decode(sigData);
            out.write(sigBytes);
        }
    }

    //************Pop-up Getter and Setter*****************
    public String getSigData() {
        return sigData;
    }

    public void setSigData(String sigData) {
        this.sigData = sigData;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getCustPanNo() {
        return custPanNo;
    }

    public void setCustPanNo(String custPanNo) {
        this.custPanNo = custPanNo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public List<SelectItem> getScheduleNoList() {
        return scheduleNoList;
    }

    public void setScheduleNoList(List<SelectItem> scheduleNoList) {
        this.scheduleNoList = scheduleNoList;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    //***************APY*****************
    public String getPran() {
        return pran;
    }

    public void setPran(String pran) {
        this.pran = pran;
    }

    public String getPensionAmt() {
        return pensionAmt;
    }

    public void setPensionAmt(String pensionAmt) {
        this.pensionAmt = pensionAmt;
    }

    public String getContributionAmt() {
        return contributionAmt;
    }

    public void setContributionAmt(String contributionAmt) {
        this.contributionAmt = contributionAmt;
    }

    public String getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(String incomeTax) {
        this.incomeTax = incomeTax;
    }

    public List<SelectItem> getIncomeTaxList() {
        return incomeTaxList;
    }

    public void setIncomeTaxList(List<SelectItem> incomeTaxList) {
        this.incomeTaxList = incomeTaxList;
    }

    public String getSwalban() {
        return swalban;
    }

    public void setSwalban(String swalban) {
        this.swalban = swalban;
    }

    public List<SelectItem> getSwalbanList() {
        return swalbanList;
    }

    public void setSwalbanList(List<SelectItem> swalbanList) {
        this.swalbanList = swalbanList;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }    
}
