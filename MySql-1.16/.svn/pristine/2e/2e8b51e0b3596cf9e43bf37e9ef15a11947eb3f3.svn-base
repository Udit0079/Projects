/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.customer.CBSCustIdentityDetailsTo;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;

public class CustGeneralInfo {

    private CustomerDetail customerDetail;
    String accountManager;
    String allowSweeps;
    List<SelectItem> allowSweepsOption;
    String tradeFinanceCustomer;
    List<SelectItem> tradeFinanceCustomerOption;
    String swiftCodeStatus;
    List<SelectItem> swiftCodeStatusOption;
    String combinedStatement;
    List<SelectItem> combinedStatementOption;
    String statementFrequency;
    List<SelectItem> statementFrequencyOption;
    String statementFrequencyWeekNo;
    List<SelectItem> statementFrequencyWeekNoOption;
    String statementFreWeekDay;
    List<SelectItem> statementFreWeekDayOption;
    String statementFreNp;
    List<SelectItem> statementFreNpOption;
    String chargeStatus;
    List<SelectItem> chargeStatusOption;
    String chargeLevelCode;
    String abbChargeCode;
    String epsChargeCode;
    String swiftFocus;
    String swiftCode;
    boolean swiftFlag;
    String bcbfId;
    String salary;
    String statementFreqWeekNo;
    List<SelectItem> statementFreqWeekNoOption;
    Date statementFrequencyDate;
    List<SelectItem> chargeLevelCodeOption;
    List<SelectItem> abbChargeCodeOption;
    List<SelectItem> epsChargeCodeOption;
    String paperRemitence;
    String deliveryChannelChargeCode;
    String acctLevelChanges;
    String custLevelChanges;
    String taxSlab;
    String itFileNo;
    String tdsCode;
    String tdsCustId;
    Date tdsFormRcvdate;
    String tdsExempRefNo;
    Date tdsExemEndDate;
    String custFloorLim;
    String remarks;
    String tinNo;
    String salesTaxNo;
    String exciseNo;
    String voterIdNo;
    String drivingLiscNo;
    @ManagedProperty(value = "#tradeFinanceInfo")
    TradeFinanceInfo tradeFinanceInfo;
    CustomerMasterDelegate masterDelegate;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String nregaJobCard;
    private Date dlExpiry;
    private String passportNo;
    private Date passportExpDt;
    private String incomeRange;
    private List<SelectItem> incomeRangeList;
    private String networth;
    private Date networthAsOn;
    private String qualification;
    private List<SelectItem> qualificationList;
    private String exposed;
    private List<SelectItem> exposedList;
    private String legelPoiDoc;
    private List<SelectItem> legelPoiDocList;
    private String tanNo;
    private String cinNo;
    private boolean custGeneralInfoComDisable;
    private boolean custGeneralInfoIndDisable;
    Date date = new Date();
    //Add on 28/10/2015
    private String voterIdNoChgFlag;
    private String naregaJobCardChgFlag;
    private String dlNoChgFlag;
    private String dlDtChgFlag;
    private String passportNoChgFlag;
    private String passportExpDtChgFlag;
    private String tanChgFlag;
    private String cinChgFlag;
    private String tinChgFlag;
    private String legalPoiDocChgFlag;
    private String incomeRangeChgFlag;
    private String networthChgFlag;
    private String networthAsOnChgFlag;
    private String eduQualificationChgFlag;
    private String exposedChgFlag;
    private String salaryChgFlag;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    //Addition on 23/11/2015
    private String otherIdentity;
    private String otherIdentityChgFlag;
    //End here
    //Addition on 05/01/2017
    private List<SelectItem> tinIssuingCountryList;
    private String tinIssuingCountry;
    private String tinIssuingCountryChgFlag;
    private String identityNo;
    private String identityNoChgFlag;
    private Date idExpiryDate;
    private String idExpiryDateChgFlag;
    private String tinIssuingCountryDisplay = "none";
    private String othrPoiDisplay = "none";
    private String othrPoiChgFlag;
    private String optLegalPoiDoc;
    private String optIdentityNo;
    private Date optIdExpiryDate;
    private String optOtherIdentity;
    private String optTinIssuingCountry;
    private String optTinIssuingCountryDisplay = "none";
    private String optOthrPoiDisplay = "none";
    private List<CBSCustIdentityDetailsTo> custIdList;
    private String customerEntityGn;
    private String identityEdit = "none";

    //end
    public CustGeneralInfo() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            custIdList = new ArrayList<CBSCustIdentityDetailsTo>();
            initializeForm();
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public String onblurAcctManager() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher acctManagerCheck = p.matcher(this.accountManager);
        if (acctManagerCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Account Manager.");
            return "Please Enter Non Numeric Value in Account Manager.";
        } else if (this.accountManager.contains("0") || this.accountManager.contains("1") || this.accountManager.contains("2") || this.accountManager.contains("3") || this.accountManager.contains("4") || this.accountManager.contains("5")
                || this.accountManager.contains("6") || this.accountManager.contains("7") || this.accountManager.contains("8") || this.accountManager.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Account Manager.");
            return "Please Enter Non Numeric Value in Account Manager.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public void tfiVisibility() {
//        if (this.tradeFinanceCustomer.equalsIgnoreCase("y")) {
//            getCustomerDetail().setTfi1("*");
//            getCustomerDetail().setTfi2("*");
//        } else {
//            getCustomerDetail().setTfi1("");
//            getCustomerDetail().setTfi2("");
//            getCustomerDetail().setErrorMsg("");
//        }
    }

    public void onblurSwiftCodeStatus() {
        if (this.swiftCodeStatus.equalsIgnoreCase("Y")) {
            swiftFlag = false;
            swiftFocus = "true";
        } else if (this.swiftCodeStatus.equalsIgnoreCase("N")) {
            swiftFlag = true;
            swiftFocus = "false";
        }
    }

    public String onblurStatementFreqDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            long strDateDiff = CbsUtil.dayDiff(statementFrequencyDate, new Date());
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return msg1 = "You can't select the date after the current date.";
            } else {
                getCustomerDetail().setErrorMsg("");
                return msg1 = "true";
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
        return msg1;
    }

    public boolean onblurSalary() {
        if (!this.salary.equalsIgnoreCase("")) {
            if (this.salary.contains(".")) {
                if (this.salary.substring(this.salary.indexOf(".") + 1).length() > 2) {
                    getCustomerDetail().setErrorMsg("Please Fill The Salary Upto Two Decimal Places.");
                    return false;
                }
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher salaryCheck = p.matcher(this.salary);
            if (!salaryCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Salary in General Information.");
                return false;
            } else {
                getCustomerDetail().setErrorMsg("");
                return true;
            }
        }
        return true;
    }

    public void onChangePOI() {
        this.othrPoiDisplay = "none";
        this.otherIdentity = "";
        if (this.legelPoiDoc.equalsIgnoreCase("T")) {
            try {
                this.tinIssuingCountryDisplay = "";
                this.setTinIssuingCountryList(new ArrayList<SelectItem>());
                List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("302");
                this.getTinIssuingCountryList().add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO regTo : functionList) {
                    this.getTinIssuingCountryList().add(new SelectItem(regTo.getCbsRefRecTypePKTO().getRefCode(), regTo.getRefDesc()));
                }
                if (!functionList.isEmpty()) {
                    this.setTinIssuingCountry("IN");
                }
            } catch (ApplicationException ex) {
                getCustomerDetail().setErrorMsg(ex.getLocalizedMessage());
            }
        } else {
            this.tinIssuingCountryDisplay = "none";
            this.setTinIssuingCountry("0");
        }
        if (this.legelPoiDoc.equalsIgnoreCase("Z") || this.legelPoiDoc.equalsIgnoreCase("O")) {
            this.othrPoiDisplay = "";
        }
    }

    public void checkDuplicateidentity() {
        getCustomerDetail().setErrorMsg("");
        try {
            if (!(this.optIdentityNo == null || this.optIdentityNo.equalsIgnoreCase(""))) {
                masterDelegate.isIdentityExists(this.optIdentityNo);
            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getLocalizedMessage());
            return;
        }
    }

    public void checkmappedidentityNo() {
        getCustomerDetail().setErrorMsg("");
        try {
            if (!(this.identityNo == null || this.identityNo.equalsIgnoreCase(""))) {
                masterDelegate.isCustomerIdentityNoExists(this.identityNo);

            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getLocalizedMessage());
            return;
        }
    }

    public void onChangeOptPOI() {
        this.optOthrPoiDisplay = "none";
        this.optOtherIdentity = "";
        if (this.optLegalPoiDoc.equalsIgnoreCase("T")) {
            try {
                this.optTinIssuingCountryDisplay = "";
                this.setTinIssuingCountryList(new ArrayList<SelectItem>());
                List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("302");
                this.getTinIssuingCountryList().add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO regTo : functionList) {
                    this.getTinIssuingCountryList().add(new SelectItem(regTo.getCbsRefRecTypePKTO().getRefCode(), regTo.getRefDesc()));
                }
                if (!functionList.isEmpty()) {
                    this.setOptTinIssuingCountry("IN");
                }
            } catch (ApplicationException ex) {
                getCustomerDetail().setErrorMsg(ex.getLocalizedMessage());
            }
        } else {
            this.optTinIssuingCountryDisplay = "none";
            this.setOptTinIssuingCountry("0");
        }
        if (this.optLegalPoiDoc.equalsIgnoreCase("Z") || this.optLegalPoiDoc.equalsIgnoreCase("O")) {
            this.optOthrPoiDisplay = "";
        }
    }

    public void onAddPOI() {
        try {
            getCustomerDetail().setErrorMsg("");
            if (this.optLegalPoiDoc == null || this.optLegalPoiDoc.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Select POI in Other Identity Details Portion on Proof of identity tab");
                return;
            }
            if (this.optIdentityNo == null || this.optIdentityNo.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Fill Identity No. in Other Identity Details Portion on Proof of identity tab");
                return;
            }
            if (this.optIdentityNo == null || this.optIdentityNo.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Fill Identity No. in Other Identity Details Portion on Proof of identity tab");
                return;
            }
            if (this.optLegalPoiDoc.equalsIgnoreCase("O") || this.optLegalPoiDoc.equalsIgnoreCase("Z")) {
                if (this.optOtherIdentity == null || this.optOtherIdentity.equalsIgnoreCase("")) {
                    customerDetail.setErrorMsg("Please Fill Other POI on Proof of identity tab in Other Identity Details Portion on Proof of identity tab");
                    return;
                }
            }
            if (this.optLegalPoiDoc.equalsIgnoreCase("A") || this.optLegalPoiDoc.equalsIgnoreCase("D")) {
                if (this.optIdExpiryDate == null) {
                    customerDetail.setErrorMsg("Please Fill Id Expiry Date in Other Identity Details Portion on  Proof of identity tab");
                    return;
                }
            }
            if (this.optIdExpiryDate != null) {
                long diff = CbsUtil.dayDiff(this.optIdExpiryDate, new Date());
                if (diff > 0) {
                    getCustomerDetail().setErrorMsg("Id. Expiry Date can not be less than current date in Other Identity Details Portion on Proof of identity tab");
                    return;
                }
            }
            if (this.customerEntityGn.equalsIgnoreCase("02") && this.optLegalPoiDoc.equalsIgnoreCase("T")) {
                if ((this.optTinIssuingCountry == null || this.optTinIssuingCountry.equalsIgnoreCase("0")
                        || this.optTinIssuingCountry.equalsIgnoreCase(""))) {
                    customerDetail.setErrorMsg("Please Select Tin Issueing Country in Other Identity Details Portion on Proof of identity tab");
                    return;
                }
            }
            if(this.identityNo.equalsIgnoreCase(this.optIdentityNo)){
                customerDetail.setErrorMsg("This identity no. already define in proof of identity field.Please fill some other identity.");
                return;
            }
            if (!(this.optIdentityNo == null || this.optIdentityNo.equalsIgnoreCase(""))) {
                masterDelegate.isIdentityExists(this.optIdentityNo);
            }
            
             for (CBSCustIdentityDetailsTo tempObj : custIdList) {
                if (tempObj.getIdentificationType().equalsIgnoreCase(this.optLegalPoiDoc)) {
                    customerDetail.setErrorMsg("Duplicate Entry Not Allowed. !");
                    return;

                }
            }
            if (this.customerEntityGn.equalsIgnoreCase("01")) {
                if (this.optLegalPoiDoc.equalsIgnoreCase("A")) {
                    if (this.optIdentityNo.length() != 8) {
                        getCustomerDetail().setErrorMsg("Please Fill 8 digit Passport No. as Identity No. in Other Identity Details Portion on Proof of identity tab.");
                        return;
                    }
                    Pattern passportNoPattern = Pattern.compile("(([a-zA-Z]{1})\\d{7})");
                    Matcher matcher = passportNoPattern.matcher(this.optIdentityNo);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill valid Passport No. as Identity No. in Other Identity Details Portion on Proof of identity tab.");
                        return;
                    }
                } else if (this.optLegalPoiDoc.equalsIgnoreCase("E")) {
                    if (this.optIdentityNo.length() != 12) {
                        getCustomerDetail().setErrorMsg("Please Fill 12 digit Aadhaar No. as Identity No. in Other Identity Details Portion on Proof of identity tab.");
                        return;
                    }
                    if (!VerhoeffCheckDigit.VERHOEFF_CHECK_DIGIT.isValid(this.optIdentityNo)) {
                        getCustomerDetail().setErrorMsg("Please Fill Valid Aadhaar No. as Identity No. in Other Identity Details Portion on Proof of identity tab.");
                        return;
                    }
                } else if (this.optLegalPoiDoc.equalsIgnoreCase("C")) {
                    if (this.optIdentityNo.length() != 10) {
                        getCustomerDetail().setErrorMsg("Please Fill 10 digit PAN/GIR No. as Identity No. in Other Identity Details Portion on Proof of identity tab.");
                        return;
                    }
                    Pattern panNoPattern = Pattern.compile("((([a-zA-Z]{5})\\d{4})[a-zA-Z]{1})");
                    Matcher matcher = panNoPattern.matcher(this.optIdentityNo);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please Fill Valid PAN/GIR No. as Identity No. in Other Identity Details Portion on Proof of identity tab.");
                        return;
                    }
                }
            } else if (this.customerEntityGn.equalsIgnoreCase("02")) {
            }

            CBSCustIdentityDetailsTo custId = new CBSCustIdentityDetailsTo();
            if (this.customerEntityGn.equalsIgnoreCase("01") && !this.optLegalPoiDoc.equalsIgnoreCase("Z")) {
                custId.setIdTypeDesc(masterDelegate.getRecRefDiscription("311", this.optLegalPoiDoc));
            } else if (this.customerEntityGn.equalsIgnoreCase("02") && !this.optLegalPoiDoc.equalsIgnoreCase("O")) {
                custId.setIdTypeDesc(masterDelegate.getRecRefDiscription("304", this.optLegalPoiDoc));
            } else if (this.customerEntityGn.equalsIgnoreCase("01") && this.optLegalPoiDoc.equalsIgnoreCase("Z")) {
                custId.setIdTypeDesc(this.optOtherIdentity);
                custId.setOtherIdentificationType(this.optOtherIdentity == null || this.optOtherIdentity.equalsIgnoreCase("") ? "" : this.optOtherIdentity);
            } else if (this.customerEntityGn.equalsIgnoreCase("02") && this.optLegalPoiDoc.equalsIgnoreCase("O")) {
                custId.setIdTypeDesc(this.optOtherIdentity);
                custId.setOtherIdentificationType(this.optOtherIdentity == null || this.optOtherIdentity.equalsIgnoreCase("") ? "" : this.optOtherIdentity);
            }
            custId.setOtherIdentificationType(this.optOtherIdentity == null || this.optOtherIdentity.equalsIgnoreCase("") ? "" : this.optOtherIdentity);
            custId.setIdentificationType(this.optLegalPoiDoc);
            custId.setIdentityNo(this.optIdentityNo);
            custId.setIdentityExpiryDate(this.optIdExpiryDate == null ? "" : dmy.format(this.optIdExpiryDate));
            custId.setTinIssuingCountry(this.optTinIssuingCountry == null || this.optTinIssuingCountry.equalsIgnoreCase("0") ? "" : this.optTinIssuingCountry);
            custId.setTinIssuingCountryDesc(masterDelegate.getRecRefDiscription("302", this.optTinIssuingCountry));
            custIdList.add(custId);

            this.optLegalPoiDoc = "";
            this.optIdentityNo = "";
            this.setOptIdExpiryDate(null);
            this.optTinIssuingCountryDisplay = "none";
            this.optOthrPoiDisplay = "none";

        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getLocalizedMessage());
        }
    }

    public void onDeletePOI(CBSCustIdentityDetailsTo custIdentityDetails) {
        custIdList.remove(custIdentityDetails);
    }

    public void initializeForm() {
        try {
            allowSweepsOption = new ArrayList<SelectItem>();
            allowSweepsOption.add(new SelectItem("0", "--Select--"));
            allowSweepsOption.add(new SelectItem("Y", "Yes"));
            allowSweepsOption.add(new SelectItem("N", "No"));
            tradeFinanceCustomerOption = new ArrayList<SelectItem>();
            tradeFinanceCustomerOption.add(new SelectItem("0", "--Select--"));
            tradeFinanceCustomerOption.add(new SelectItem("Y", "Yes"));
            tradeFinanceCustomerOption.add(new SelectItem("N", "No"));
            swiftCodeStatusOption = new ArrayList<SelectItem>();
            swiftCodeStatusOption.add(new SelectItem("0", "--Select--"));
            swiftCodeStatusOption.add(new SelectItem("Y", "Yes"));
            swiftCodeStatusOption.add(new SelectItem("N", "No"));
            combinedStatementOption = new ArrayList<SelectItem>();
            combinedStatementOption.add(new SelectItem("0", "--Select--"));
            combinedStatementOption.add(new SelectItem("Y", "Yes"));
            combinedStatementOption.add(new SelectItem("N", "No"));
            statementFrequencyOption = new ArrayList<SelectItem>();
            statementFrequencyOption.add(new SelectItem("0", "--Select--"));
            statementFrequencyOption.add(new SelectItem("D"));
            statementFrequencyOption.add(new SelectItem("W"));
            statementFrequencyOption.add(new SelectItem("M"));
            statementFrequencyOption.add(new SelectItem("Q"));
            statementFrequencyOption.add(new SelectItem("Y"));
            statementFreqWeekNoOption = new ArrayList<SelectItem>();
            statementFreqWeekNoOption.add(new SelectItem("0", "--Select--"));
            statementFreqWeekNoOption.add(new SelectItem("1"));
            statementFreqWeekNoOption.add(new SelectItem("2"));
            statementFreqWeekNoOption.add(new SelectItem("3"));
            statementFreqWeekNoOption.add(new SelectItem("4"));
            statementFreqWeekNoOption.add(new SelectItem("M"));
            statementFreqWeekNoOption.add(new SelectItem("L"));
            statementFreWeekDayOption = new ArrayList<SelectItem>();
            statementFreWeekDayOption.add(new SelectItem("0", "--Select--"));
            statementFreWeekDayOption.add(new SelectItem("1"));
            statementFreWeekDayOption.add(new SelectItem("2"));
            statementFreWeekDayOption.add(new SelectItem("3"));
            statementFreWeekDayOption.add(new SelectItem("4"));
            statementFreWeekDayOption.add(new SelectItem("5"));
            statementFreWeekDayOption.add(new SelectItem("6"));
            statementFreWeekDayOption.add(new SelectItem("7"));
            statementFreNpOption = new ArrayList<SelectItem>();
            statementFreNpOption.add(new SelectItem("0", "--Select--"));
            statementFreNpOption.add(new SelectItem("N"));
            statementFreNpOption.add(new SelectItem("P"));
            statementFreNpOption.add(new SelectItem("S"));
            chargeStatusOption = new ArrayList<SelectItem>();
            chargeStatusOption.add(new SelectItem("0", "--Select--"));
            chargeStatusOption.add(new SelectItem("Y", "Yes"));
            chargeStatusOption.add(new SelectItem("N", "No"));

            chargeLevelCodeOption = new ArrayList<SelectItem>();
            chargeLevelCodeOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> listForChargeLevelCodeGenInfo = masterDelegate.getFunctionValues("133");
            for (CbsRefRecTypeTO recTypeTO : listForChargeLevelCodeGenInfo) {
                chargeLevelCodeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

            abbChargeCodeOption = new ArrayList<SelectItem>();
            abbChargeCodeOption.add(new SelectItem("0", "--Select--"));
            epsChargeCodeOption = new ArrayList<SelectItem>();
            epsChargeCodeOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> listForAbbChargeLevelCodeGenInfo = masterDelegate.getFunctionValues("108");
            for (CbsRefRecTypeTO recTypeTO : listForAbbChargeLevelCodeGenInfo) {
                abbChargeCodeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                epsChargeCodeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

            legelPoiDocList = new ArrayList<SelectItem>();
            List<CbsRefRecTypeTO> functionList = null;
            incomeRangeList = new ArrayList<SelectItem>();

            qualificationList = new ArrayList<SelectItem>();
            qualificationList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("307"); //Individual Qualification
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                qualificationList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

            exposedList = new ArrayList<SelectItem>();
            functionList = masterDelegate.getFunctionValues("308"); //Individual Polictically Exposed
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                exposedList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }

    }

    public void selectFieldValues() {
        try {
            CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(getCustomerDetail().getCustId());
            List<CBSCustIdentityDetailsTo> custIdentityDetailsList = masterDelegate.getCustIdentityDetailsByCustId(getCustomerDetail().getCustId());
//            if (custTO.getAccountManager() == null || custTO.getAccountManager().equalsIgnoreCase("")) {
//                this.setAccountManager("");
//            } else {
//                this.setAccountManager(custTO.getAccountManager());
//            }
//            if (custTO.getAllowSweeps() == null || custTO.getAllowSweeps().equalsIgnoreCase("")) {
//                this.setAllowSweeps("0");
//            } else {
//                this.setAllowSweeps(custTO.getAllowSweeps());
//            }
//            if (custTO.getTradeFinanceFlag() == null || custTO.getTradeFinanceFlag().equalsIgnoreCase("")) {
//                this.setTradeFinanceCustomer("0");
//            } else {
//                this.setTradeFinanceCustomer(custTO.getTradeFinanceFlag());
//            }
//            if (custTO.getSwiftCodeStatus() == null || custTO.getSwiftCodeStatus().equalsIgnoreCase("")) {
//                this.setSwiftCodeStatus("0");
//            } else {
//                this.setSwiftCodeStatus(custTO.getSwiftCodeStatus());
//            }
//            if (custTO.getSwiftCode() == null || custTO.getSwiftCode().equalsIgnoreCase("")) {
//                this.setSwiftCode("");
//            } else {
//                this.setSwiftCode(custTO.getSwiftCode());
//            }
//            if (custTO.getBcbfid() == null || custTO.getBcbfid().equalsIgnoreCase("")) {
//                this.setBcbfId("");
//            } else {
//                this.setBcbfId(custTO.getBcbfid());
//            }
//            if (custTO.getCombinedStmtFlag() == null || custTO.getCombinedStmtFlag().equalsIgnoreCase("")) {
//                this.setCombinedStatement("0");
//            } else {
//                this.setCombinedStatement(custTO.getCombinedStmtFlag());
//            }
//            if (custTO.getStmtFreqType() == null || custTO.getStmtFreqType().equalsIgnoreCase("")) {
//                this.setStatementFrequency("0");
//            } else {
//                this.setStatementFrequency(custTO.getStmtFreqType());
//            }
//            if (custTO.getStmtFreqWeekNo() == null || custTO.getStmtFreqWeekNo().equalsIgnoreCase("")) {
//                this.setStatementFreqWeekNo("0");
//            } else {
//                this.setStatementFreqWeekNo(custTO.getStmtFreqWeekNo());
//            }
//            if (custTO.getStmtFreqWeekDay() == null || custTO.getStmtFreqWeekDay().equalsIgnoreCase("")) {
//                this.setStatementFreWeekDay("0");
//            } else {
//                this.setStatementFreWeekDay(custTO.getStmtFreqWeekDay());
//            }
//            if (custTO.getStmtFreqStartDate() == null || custTO.getStmtFreqStartDate().equalsIgnoreCase("")) {
//                this.setStatementFrequencyDate(new Date());
//            } else {
//                this.setStatementFrequencyDate(ymd.parse(custTO.getStmtFreqStartDate()));
//            }
//            if (custTO.getStmtFreqNP() == null || custTO.getStmtFreqNP().equalsIgnoreCase("")) {
//                this.setStatementFreNp("0");
//            } else {
//                this.setStatementFreNp(custTO.getStmtFreqNP());
//            }
//            if (custTO.getChargeStatus() == null || custTO.getChargeStatus().equalsIgnoreCase("")) {
//                this.setChargeStatus("0");
//            } else {
//                this.setChargeStatus(custTO.getChargeStatus());
//            }
//            if (custTO.getChargeLevelCode() == null || custTO.getChargeLevelCode().equalsIgnoreCase("")) {
//                this.setChargeLevelCode("0");
//            } else {
//                this.setChargeLevelCode(custTO.getChargeLevelCode());
//            }
//            if (custTO.getaBBChargeCode() == null || custTO.getaBBChargeCode().equalsIgnoreCase("")) {
//                this.setAbbChargeCode("0");
//            } else {
//                this.setAbbChargeCode(custTO.getaBBChargeCode());
//            }
//            if (custTO.getePSChargeCode() == null || custTO.getePSChargeCode().equalsIgnoreCase("")) {
//                this.setEpsChargeCode("0");
//            } else {
//                this.setEpsChargeCode(custTO.getePSChargeCode());
//            }
//            if (custTO.getPaperRemittance() == null || custTO.getPaperRemittance().equalsIgnoreCase("")) {
//                this.setPaperRemitence("");
//            } else {
//                this.setPaperRemitence(custTO.getPaperRemittance());
//            }
//            if (custTO.getDeliveryChannelChargeCode() == null || custTO.getDeliveryChannelChargeCode().equalsIgnoreCase("")) {
//                this.setDeliveryChannelChargeCode("");
//            } else {
//                this.setDeliveryChannelChargeCode(custTO.getDeliveryChannelChargeCode());
//            }
//            if (custTO.getAccountLevelCharges() == null || custTO.getAccountLevelCharges().equalsIgnoreCase("")) {
//                this.setAcctLevelChanges("");
//            } else {
//                this.setAcctLevelChanges(custTO.getAccountLevelCharges());
//            }
//            if (custTO.getCustLevelCharges() == null || custTO.getCustLevelCharges().equalsIgnoreCase("")) {
//                this.setCustLevelChanges("");
//            } else {
//                this.setCustLevelChanges(custTO.getCustLevelCharges());
//            }
//            if (custTO.getTaxSlab() == null || custTO.getTaxSlab().equalsIgnoreCase("")) {
//                this.setTaxSlab("");
//            } else {
//                this.setTaxSlab(custTO.getTaxSlab());
//            }
//            if (custTO.getiTFileNo() == null || custTO.getiTFileNo().equalsIgnoreCase("")) {
//                this.setItFileNo("");
//            } else {
//                this.setItFileNo(custTO.getiTFileNo());
//            }
//            if (custTO.gettDSCode() == null || custTO.gettDSCode().equalsIgnoreCase("")) {
//                this.setTdsCode("");
//            } else {
//                this.setTdsCode(custTO.gettDSCode());
//            }
//            if (custTO.gettDSCustomerId() == null || custTO.gettDSCustomerId().equalsIgnoreCase("")) {
//                this.setTdsCustId("");
//            } else {
//                this.setTdsCustId(custTO.gettDSCustomerId());
//            }
//            if (custTO.gettDSFormReceiveDate() == null) {
//                this.setTdsFormRcvdate(new Date());
//            } else {
//                this.setTdsFormRcvdate(custTO.gettDSFormReceiveDate());
//            }
//            if (custTO.gettDSExemptionReferenceNo() == null || custTO.gettDSExemptionReferenceNo().equalsIgnoreCase("")) {
//                this.setTdsExempRefNo("");
//            } else {
//                this.setTdsExempRefNo(custTO.gettDSExemptionReferenceNo());
//            }
//            if (custTO.gettDSExemptionEndDate() == null) {
//                this.setTdsExemEndDate(new Date());
//            } else {
//                this.setTdsExemEndDate(custTO.gettDSExemptionEndDate());
//            }
//            if (custTO.gettDSFloorLimit() == null) {
//                this.setCustFloorLim("0.00");
//            } else {
//                this.setCustFloorLim(custTO.gettDSFloorLimit().toString());
//            }
//            if (custTO.getExemptionRemarks() == null || custTO.getExemptionRemarks().equalsIgnoreCase("")) {
//                this.setRemarks("");
//            } else {
//                this.setRemarks(custTO.getExemptionRemarks());
//            }
//            if (custTO.gettINNumber() == null || custTO.gettINNumber().equalsIgnoreCase("")) {
//                this.setTinNo("");
//            } else {
//                this.setTinNo(custTO.gettINNumber());
//            }
//            if (custTO.getSalesTaxNo() == null || custTO.getSalesTaxNo().equalsIgnoreCase("")) {
//                this.setSalesTaxNo("");
//            } else {
//                this.setSalesTaxNo(custTO.getSalesTaxNo());
//            }
//            if (custTO.getExciseNo() == null || custTO.getExciseNo().equalsIgnoreCase("")) {
//                this.setExciseNo("");
//            } else {
//                this.setExciseNo(custTO.getExciseNo());
//            }

            if (custIdentityDetailsList.size() > 0) {
                custIdList = new ArrayList<CBSCustIdentityDetailsTo>();
                for (CBSCustIdentityDetailsTo obj : custIdentityDetailsList) {
                    if (this.customerEntityGn.equalsIgnoreCase("01") && !obj.getIdentificationType().equalsIgnoreCase("Z")) {
                        obj.setIdTypeDesc(masterDelegate.getRecRefDiscription("311", obj.getIdentificationType()));
                    } else if (this.customerEntityGn.equalsIgnoreCase("02") && !obj.getIdentificationType().equalsIgnoreCase("O")) {
                        obj.setIdTypeDesc(masterDelegate.getRecRefDiscription("304", obj.getIdentificationType()));
                    } else if (this.customerEntityGn.equalsIgnoreCase("01") && obj.getIdentificationType().equalsIgnoreCase("Z")) {
                        obj.setIdTypeDesc(obj.getOtherIdentificationType());
                    } else if (this.customerEntityGn.equalsIgnoreCase("02") && obj.getIdentificationType().equalsIgnoreCase("O")) {
                        obj.setIdTypeDesc(obj.getOtherIdentificationType());
                    }
                    obj.setTinIssuingCountryDesc(masterDelegate.getRecRefDiscription("302", obj.getTinIssuingCountry()));
                    obj.setIdentityExpiryDate(obj.getIdentityExpiryDate().equalsIgnoreCase("") ? "" : dmy.format(ymd.parse(obj.getIdentityExpiryDate())));
                    custIdList.add(obj);
                }
            }
            if (custTO.getLegalDocument().equalsIgnoreCase("T")) {
                try {
                    this.tinIssuingCountryDisplay = "";
                    this.setTinIssuingCountryList(new ArrayList<SelectItem>());
                    List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("302");
                    this.getTinIssuingCountryList().add(new SelectItem("0", ""));
                    for (CbsRefRecTypeTO regTo : functionList) {
                        this.getTinIssuingCountryList().add(new SelectItem(regTo.getCbsRefRecTypePKTO().getRefCode(), regTo.getRefDesc()));
                    }
                } catch (ApplicationException ex) {
                    getCustomerDetail().setErrorMsg(ex.getLocalizedMessage());
                }
            } else {
                this.tinIssuingCountryDisplay = "none";
                this.setTinIssuingCountryList(new ArrayList<SelectItem>());
                this.getTinIssuingCountryList().add(new SelectItem("0", ""));
            }
            if (custTO.getVoterIDNo() == null || custTO.getVoterIDNo().equalsIgnoreCase("")) {
                this.setVoterIdNo("");
            } else {
                this.setVoterIdNo(custTO.getVoterIDNo());
            }
            if (custTO.getDrivingLicenseNo() == null || custTO.getDrivingLicenseNo().equalsIgnoreCase("")) {
                this.setDrivingLiscNo("");
            } else {
                this.setDrivingLiscNo(custTO.getDrivingLicenseNo());
            }
            //New Addition
//            this.nregaJobCard = (custTO.getNregaJobCard() == null || custTO.getNregaJobCard().equals(""))
//                    ? "" : custTO.getNregaJobCard();
//            this.dlExpiry = custTO.getDlExpiry() == null ? date : dmy.parse(custTO.getDlExpiry());
//            this.passportNo = (custTO.getPassportNo() == null || custTO.getPassportNo().equals(""))
//                    ? "" : custTO.getPassportNo();
//            this.passportExpDt = custTO.getExpirydate() == null ? date : custTO.getExpirydate();
//            this.tanNo = (custTO.getTan() == null || custTO.getTan().equals(""))
//                    ? "" : custTO.getTan();
//            this.cinNo = (custTO.getCin() == null || custTO.getCin().equals(""))
//                    ? "" : custTO.getCin();
//            this.tinNo = (custTO.gettINNumber() == null || custTO.gettINNumber().equals(""))
//                    ? "" : custTO.gettINNumber();
            this.legelPoiDoc = (custTO.getLegalDocument() == null || custTO.getLegalDocument().equals(""))
                    ? "0" : custTO.getLegalDocument();
            this.incomeRange = (custTO.getIncomeRange() == null || custTO.getIncomeRange().equals(""))
                    ? "" : custTO.getIncomeRange();
            this.networth = (custTO.getNetworth() == null || custTO.getNetworth().toString().equals("")) ? "" : custTO.getNetworth().toString();
            this.networthAsOn = (custTO.getNetworthAsOn() == null || custTO.getNetworthAsOn().equals(""))
                    ? date : dmy.parse(custTO.getNetworthAsOn());
            this.qualification = (custTO.getQualification() == null || custTO.getQualification().equals(""))
                    ? "" : custTO.getQualification();
            this.exposed = (custTO.getPoliticalExposed() == null || custTO.getPoliticalExposed().equals(""))
                    ? "" : custTO.getPoliticalExposed();
            this.salary = (custTO.getSalary() == null || custTO.getSalary().toString().equals(""))
                    ? "" : custTO.getSalary().toString();
            //Addition on 23/1/2015

            this.identityNo = (custTO.getIdentityNo() == null || custTO.getIdentityNo().equals(""))
                    ? "" : custTO.getIdentityNo();
            this.idExpiryDate = (custTO.getIdExpiryDate() == null || custTO.getIdExpiryDate().equals(""))
                    ? null : ymd.parse(custTO.getIdExpiryDate());
            this.tinIssuingCountry = (custTO.getTinIssuingCountry() == null || custTO.getTinIssuingCountry().equals(""))
                    ? "0" : custTO.getTinIssuingCountry();
            if (custTO.getLegalDocument().equalsIgnoreCase("Z") || custTO.getLegalDocument().equalsIgnoreCase("O")) {
                this.othrPoiDisplay = "";
                this.otherIdentity = (custTO.getOtherIdentity() == null || custTO.getOtherIdentity().equals(""))
                        ? "" : custTO.getOtherIdentity();
            } else {
                this.othrPoiDisplay = "none";
                this.otherIdentity = "";
            }

            //End here

            //Addition on 28/10/2015
            makeChangeFlagToUnChange();
            if (getCustomerDetail().getFunction().equalsIgnoreCase("V")) {
                CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.
                        getCustomerLastChangeDetail(getCustomerDetail().getVerifyCustId());
                if (hisTo != null) {
//                    this.voterIdNoChgFlag = this.voterIdNo.equalsIgnoreCase((hisTo.getVoterIDNo() == null || hisTo.getVoterIDNo().equals("")) ? "" : hisTo.getVoterIDNo()) ? "color: #000000" : "color: #FF0000";
//                    this.naregaJobCardChgFlag = this.nregaJobCard.equalsIgnoreCase((hisTo.getNregaJobCard() == null || hisTo.getNregaJobCard().equals("")) ? "" : hisTo.getNregaJobCard()) ? "color: #000000" : "color: #FF0000";
//                    this.dlNoChgFlag = this.drivingLiscNo.equalsIgnoreCase((hisTo.getDrivingLicenseNo() == null || hisTo.getDrivingLicenseNo().equals("")) ? "" : hisTo.getDrivingLicenseNo()) ? "color: #000000" : "color: #FF0000";
//                    this.dlDtChgFlag = this.dlExpiry.compareTo((hisTo.getDlExpiry() == null || hisTo.getDlExpiry().equals("")) ? dmy.parse("01/01/1900") : dmy.parse(hisTo.getDlExpiry())) == 0 ? "color: #000000" : "color: #FF0000";
//                    this.passportNoChgFlag = this.passportNo.equalsIgnoreCase((hisTo.getPassportNo() == null || hisTo.getPassportNo().equals("")) ? "" : hisTo.getPassportNo()) ? "color: #000000" : "color: #FF0000";
//                    this.passportExpDtChgFlag = this.dlExpiry.compareTo((hisTo.getExpirydate() == null || hisTo.getExpirydate().equals("")) ? ymd.parse("19000101") : ymd.parse(hisTo.getExpirydate())) == 0 ? "color: #000000" : "color: #FF0000";
//                    this.tanChgFlag = this.tanNo.equalsIgnoreCase((hisTo.getTan() == null || hisTo.getTan().equals("")) ? "" : hisTo.getTan()) ? "color: #000000" : "color: #FF0000";
//                    this.cinChgFlag = this.cinNo.equalsIgnoreCase((hisTo.getCin() == null || hisTo.getCin().equals("")) ? "" : hisTo.getCin()) ? "color: #000000" : "color: #FF0000";
//                    this.tinChgFlag = this.tinNo.equalsIgnoreCase((hisTo.gettINNumber() == null || hisTo.gettINNumber().equals("")) ? "" : hisTo.gettINNumber()) ? "color: #000000" : "color: #FF0000";
                    this.legalPoiDocChgFlag = this.legelPoiDoc.equalsIgnoreCase((hisTo.getLegalDocument() == null || hisTo.getLegalDocument().equals("")) ? "0" : hisTo.getLegalDocument()) ? "color: #000000" : "color: #FF0000";
                    this.incomeRangeChgFlag = this.incomeRange.equalsIgnoreCase((hisTo.getIncomeRange() == null || hisTo.getIncomeRange().equals("")) ? "" : hisTo.getIncomeRange()) ? "color: #000000" : "color: #FF0000";
                    this.networthChgFlag = this.networth.equalsIgnoreCase((hisTo.getNetworth() == null || hisTo.getNetworth().toString().equals("")) ? "" : hisTo.getNetworth().toString()) ? "color: #000000" : "color: #FF0000";
                    this.networthAsOnChgFlag = this.networthAsOn.compareTo((hisTo.getNetworthAsOn() == null || hisTo.getNetworthAsOn().equals("")) ? dmy.parse("01/01/1900") : dmy.parse(hisTo.getNetworthAsOn())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.eduQualificationChgFlag = this.qualification.equalsIgnoreCase((hisTo.getQualification() == null || hisTo.getQualification().equals("")) ? "" : hisTo.getQualification()) ? "color: #000000" : "color: #FF0000";
                    this.exposedChgFlag = this.exposed.equalsIgnoreCase((hisTo.getPoliticalExposed() == null || hisTo.getPoliticalExposed().equals("")) ? "" : hisTo.getPoliticalExposed()) ? "color: #000000" : "color: #FF0000";
                    this.salaryChgFlag = this.salary.equalsIgnoreCase((hisTo.getSalary() == null || hisTo.getSalary().equals("")) ? "" : hisTo.getSalary().toString()) ? "color: #000000" : "color: #FF0000";
                    this.otherIdentityChgFlag = this.otherIdentity.equalsIgnoreCase((hisTo.getOtherIdentity() == null || hisTo.getOtherIdentity().equals("")) ? "" : hisTo.getOtherIdentity()) ? "color: #000000" : "color: #FF0000";
                    this.identityNoChgFlag = this.identityNo.equalsIgnoreCase((hisTo.getIdentityNo() == null || hisTo.getIdentityNo().equals("")) ? "" : hisTo.getIdentityNo()) ? "color: #000000" : "color: #FF0000";
                    this.tinIssuingCountryChgFlag = this.tinIssuingCountry.equalsIgnoreCase((hisTo.getTinIssuingCountry() == null || hisTo.getTinIssuingCountry().equals("0") || hisTo.getTinIssuingCountry().equals("")) ? "0" : hisTo.getTinIssuingCountry()) ? "color: #000000" : "color: #FF0000";
                }
            }
            //End here
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }

    }

    public void refreshForm() {
//        this.setAccountManager("");
//        this.setAllowSweeps("0");
//        this.setTradeFinanceCustomer("0");

//        this.setSwiftCode("");
//        this.setSwiftCodeStatus("0");
//        this.setBcbfId("");
//        this.setCombinedStatement("0");

//        this.setStatementFrequency("0");
//        this.setStatementFreqWeekNo("0");
//        this.setStatementFreWeekDay("0");
//        this.setStatementFreNp("0");

//        this.setChargeStatus("0");
//        this.setChargeLevelCode("0");
//        this.setAbbChargeCode("0");
//        this.setEpsChargeCode("0");

//        this.setPaperRemitence("");
//        this.setDeliveryChannelChargeCode("");
//        this.setAcctLevelChanges("");
//        this.setCustLevelChanges("");

//        this.setTaxSlab("");
//        this.setTdsCode("");
//        this.setTdsCustId("");
//        this.setTdsExempRefNo("");

//        this.setRemarks("");
//        this.setItFileNo("");
//        this.setPanGirNo("");

//        this.setPanNoOption("0");
//        this.setPanNoVisibility("false");
//        this.custGeneralInfoComDisable = true;
//        this.custGeneralInfoIndDisable = true;
//        this.setSalesTaxNo("");
//        this.setExciseNo("");
//        this.setDrivingLiscNo("");

        this.setVoterIdNo("");
        this.setNregaJobCard("");
        this.setDrivingLiscNo("");
        this.setDlExpiry(null);
        this.setPassportNo("");
        this.setPassportExpDt(null);
        this.setTanNo("");
        this.setCinNo("");
        this.setTinNo("");
        this.setLegelPoiDoc("0");
        this.setIncomeRange("0");
        this.setNetworth("");
        this.setNetworthAsOn(null);
        this.setQualification("0");
        this.setExposed("0");
        this.setSalary("");
        //Addition on 23/11/2015
        this.setOtherIdentity("");
        this.setIdentityNo("");
        this.setIdExpiryDate(null);
        this.setTinIssuingCountry("0");
        this.custIdList = new ArrayList<CBSCustIdentityDetailsTo>();
        this.optLegalPoiDoc = "";
        this.optIdentityNo = "";
        this.setOptIdExpiryDate(null);
//        this.setPrefPoaAdd("0");
        //Addition on 28/10/2015
        makeChangeFlagToUnChange();
    }

    public String validation() {
        String s7 = onblurAcctManager();
        if (!s7.equalsIgnoreCase("true")) {
            return s7;
        }
//        if (this.panNoOption.equals("0")) {
//            return "Please Select PAN/GIR No on First General Information Tab.";
//        }
//        if (this.panNoOption.equals("0") && !this.panGirNo.equals("")) {
//            return "Please Select PAN/GIR No on First General Information Tab.";
//        }
//        if (this.panNoOption.equalsIgnoreCase("PAN No") && this.panGirNo.length() < 10) {
//            return "Please Fill 10 Digit PAN No on First General Information Tab.";
//        }
//        if (this.panNoOption.equalsIgnoreCase("Form 60") && this.panGirNo.equals("")) {
//            return "Please Fill Form 60 Field on First General Information Tab.";
//        }
        if (this.tradeFinanceCustomer.equalsIgnoreCase("y")) {
            if (!(this.tradeFinanceInfo.tfiName.equalsIgnoreCase("0") || this.tradeFinanceInfo.tfiName.equalsIgnoreCase(""))) {
                if ((this.tradeFinanceInfo.tfiAdd1.equalsIgnoreCase("") || this.tradeFinanceInfo.tfiAdd1.equalsIgnoreCase("0"))) {
                    return "Please fill the Address Line1 in Trade Finance Information";

                }
            }
        }
        return "ok";
    }

    //Add On 28/10/2015
    public void makeChangeFlagToUnChange() {
        this.setVoterIdNoChgFlag("color: #000000");
        this.setNaregaJobCardChgFlag("color: #000000");
        this.setDlNoChgFlag("color: #000000");
        this.setDlDtChgFlag("color: #000000");
        this.setPassportNoChgFlag("color: #000000");
        this.setPassportExpDtChgFlag("color: #000000");
        this.setTanChgFlag("color: #000000");
        this.setCinChgFlag("color: #000000");
        this.setTinChgFlag("color: #000000");
        this.setLegalPoiDocChgFlag("color: #000000");
        this.setIncomeRangeChgFlag("color: #000000");
        this.setNetworthChgFlag("color: #000000");
        this.setNetworthAsOnChgFlag("color: #000000");
        this.setEduQualificationChgFlag("color: #000000");
        this.setExposedChgFlag("color: #000000");
        this.setSalaryChgFlag("color: #000000");
        this.setIdentityNoChgFlag("color: #000000");
        this.setIdExpiryDateChgFlag("color: #000000");
        this.setOtherIdentityChgFlag("color: #000000");
    }

    //Getter And Setter
    public TradeFinanceInfo getTradeFinanceInfo() {
        return tradeFinanceInfo;
    }

    public void setTradeFinanceInfo(TradeFinanceInfo tradeFinanceInfo) {
        this.tradeFinanceInfo = tradeFinanceInfo;
    }

    public String getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getAbbChargeCode() {
        return abbChargeCode;
    }

    public void setAbbChargeCode(String abbChargeCode) {
        this.abbChargeCode = abbChargeCode;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public List<SelectItem> getAllowSweepsOption() {
        return allowSweepsOption;
    }

    public void setAllowSweepsOption(List<SelectItem> allowSweepsOption) {
        this.allowSweepsOption = allowSweepsOption;
    }

    public String getChargeLevelCode() {
        return chargeLevelCode;
    }

    public void setChargeLevelCode(String chargeLevelCode) {
        this.chargeLevelCode = chargeLevelCode;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public List<SelectItem> getChargeStatusOption() {
        return chargeStatusOption;
    }

    public void setChargeStatusOption(List<SelectItem> chargeStatusOption) {
        this.chargeStatusOption = chargeStatusOption;
    }

    public String getCombinedStatement() {
        return combinedStatement;
    }

    public void setCombinedStatement(String combinedStatement) {
        this.combinedStatement = combinedStatement;
    }

    public List<SelectItem> getCombinedStatementOption() {
        return combinedStatementOption;
    }

    public void setCombinedStatementOption(List<SelectItem> combinedStatementOption) {
        this.combinedStatementOption = combinedStatementOption;
    }

    public String getEpsChargeCode() {
        return epsChargeCode;
    }

    public void setEpsChargeCode(String epsChargeCode) {
        this.epsChargeCode = epsChargeCode;
    }

    public String getStatementFreNp() {
        return statementFreNp;
    }

    public void setStatementFreNp(String statementFreNp) {
        this.statementFreNp = statementFreNp;
    }

    public List<SelectItem> getStatementFreNpOption() {
        return statementFreNpOption;
    }

    public void setStatementFreNpOption(List<SelectItem> statementFreNpOption) {
        this.statementFreNpOption = statementFreNpOption;
    }

    public String getStatementFreWeekDay() {
        return statementFreWeekDay;
    }

    public void setStatementFreWeekDay(String statementFreWeekDay) {
        this.statementFreWeekDay = statementFreWeekDay;
    }

    public List<SelectItem> getStatementFreWeekDayOption() {
        return statementFreWeekDayOption;
    }

    public void setStatementFreWeekDayOption(List<SelectItem> statementFreWeekDayOption) {
        this.statementFreWeekDayOption = statementFreWeekDayOption;
    }

    public String getStatementFrequency() {
        return statementFrequency;
    }

    public void setStatementFrequency(String statementFrequency) {
        this.statementFrequency = statementFrequency;
    }

    public List<SelectItem> getStatementFrequencyOption() {
        return statementFrequencyOption;
    }

    public void setStatementFrequencyOption(List<SelectItem> statementFrequencyOption) {
        this.statementFrequencyOption = statementFrequencyOption;
    }

    public String getStatementFrequencyWeekNo() {
        return statementFrequencyWeekNo;
    }

    public void setStatementFrequencyWeekNo(String statementFrequencyWeekNo) {
        this.statementFrequencyWeekNo = statementFrequencyWeekNo;
    }

    public List<SelectItem> getStatementFrequencyWeekNoOption() {
        return statementFrequencyWeekNoOption;
    }

    public void setStatementFrequencyWeekNoOption(List<SelectItem> statementFrequencyWeekNoOption) {
        this.statementFrequencyWeekNoOption = statementFrequencyWeekNoOption;
    }

    public String getSwiftCodeStatus() {
        return swiftCodeStatus;
    }

    public void setSwiftCodeStatus(String swiftCodeStatus) {
        this.swiftCodeStatus = swiftCodeStatus;
    }

    public List<SelectItem> getSwiftCodeStatusOption() {
        return swiftCodeStatusOption;
    }

    public void setSwiftCodeStatusOption(List<SelectItem> swiftCodeStatusOption) {
        this.swiftCodeStatusOption = swiftCodeStatusOption;
    }

    public String getTradeFinanceCustomer() {
        return tradeFinanceCustomer;
    }

    public void setTradeFinanceCustomer(String tradeFinanceCustomer) {
        this.tradeFinanceCustomer = tradeFinanceCustomer;
    }

    public List<SelectItem> getTradeFinanceCustomerOption() {
        return tradeFinanceCustomerOption;
    }

    public void setTradeFinanceCustomerOption(List<SelectItem> tradeFinanceCustomerOption) {
        this.tradeFinanceCustomerOption = tradeFinanceCustomerOption;
    }

    public String getBcbfId() {
        return bcbfId;
    }

    public void setBcbfId(String bcbfId) {
        this.bcbfId = bcbfId;
    }

    public String getStatementFreqWeekNo() {
        return statementFreqWeekNo;
    }

    public void setStatementFreqWeekNo(String statementFreqWeekNo) {
        this.statementFreqWeekNo = statementFreqWeekNo;
    }

    public List<SelectItem> getStatementFreqWeekNoOption() {
        return statementFreqWeekNoOption;
    }

    public void setStatementFreqWeekNoOption(List<SelectItem> statementFreqWeekNoOption) {
        this.statementFreqWeekNoOption = statementFreqWeekNoOption;
    }

    public Date getStatementFrequencyDate() {
        return statementFrequencyDate;
    }

    public void setStatementFrequencyDate(Date statementFrequencyDate) {
        this.statementFrequencyDate = statementFrequencyDate;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public boolean isSwiftFlag() {
        return swiftFlag;
    }

    public void setSwiftFlag(boolean swiftFlag) {
        this.swiftFlag = swiftFlag;
    }

    public String getSwiftFocus() {
        return swiftFocus;
    }

    public void setSwiftFocus(String swiftFocus) {
        this.swiftFocus = swiftFocus;
    }

    public List<SelectItem> getAbbChargeCodeOption() {
        return abbChargeCodeOption;
    }

    public void setAbbChargeCodeOption(List<SelectItem> abbChargeCodeOption) {
        this.abbChargeCodeOption = abbChargeCodeOption;
    }

    public List<SelectItem> getChargeLevelCodeOption() {
        return chargeLevelCodeOption;
    }

    public void setChargeLevelCodeOption(List<SelectItem> chargeLevelCodeOption) {
        this.chargeLevelCodeOption = chargeLevelCodeOption;
    }

    public List<SelectItem> getEpsChargeCodeOption() {
        return epsChargeCodeOption;
    }

    public void setEpsChargeCodeOption(List<SelectItem> epsChargeCodeOption) {
        this.epsChargeCodeOption = epsChargeCodeOption;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAcctLevelChanges() {
        return acctLevelChanges;
    }

    public void setAcctLevelChanges(String acctLevelChanges) {
        this.acctLevelChanges = acctLevelChanges;
    }

    public String getCustFloorLim() {
        return custFloorLim;
    }

    public void setCustFloorLim(String custFloorLim) {
        this.custFloorLim = custFloorLim;
    }

    public String getCustLevelChanges() {
        return custLevelChanges;
    }

    public void setCustLevelChanges(String custLevelChanges) {
        this.custLevelChanges = custLevelChanges;
    }

    public String getDeliveryChannelChargeCode() {
        return deliveryChannelChargeCode;
    }

    public void setDeliveryChannelChargeCode(String deliveryChannelChargeCode) {
        this.deliveryChannelChargeCode = deliveryChannelChargeCode;
    }

    public String getDrivingLiscNo() {
        return drivingLiscNo;
    }

    public void setDrivingLiscNo(String drivingLiscNo) {
        this.drivingLiscNo = drivingLiscNo;
    }

    public String getExciseNo() {
        return exciseNo;
    }

    public void setExciseNo(String exciseNo) {
        this.exciseNo = exciseNo;
    }

    public String getItFileNo() {
        return itFileNo;
    }

    public void setItFileNo(String itFileNo) {
        this.itFileNo = itFileNo;
    }

//    public String getPanGirNo() {
//        return panGirNo;
//    }
//
//    public void setPanGirNo(String panGirNo) {
//        this.panGirNo = panGirNo;
//    }
//
//    public String getPanNoOption() {
//        return panNoOption;
//    }
//
//    public void setPanNoOption(String panNoOption) {
//        this.panNoOption = panNoOption;
//    }
//
//    public List<SelectItem> getPanNoOptionList() {
//        return panNoOptionList;
//    }
//
//    public void setPanNoOptionList(List<SelectItem> panNoOptionList) {
//        this.panNoOptionList = panNoOptionList;
//    }
//
//    public String getPanNoVisibility() {
//        return panNoVisibility;
//    }
//
//    public void setPanNoVisibility(String panNoVisibility) {
//        this.panNoVisibility = panNoVisibility;
//    }
    public String getPaperRemitence() {
        return paperRemitence;
    }

    public void setPaperRemitence(String paperRemitence) {
        this.paperRemitence = paperRemitence;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSalesTaxNo() {
        return salesTaxNo;
    }

    public void setSalesTaxNo(String salesTaxNo) {
        this.salesTaxNo = salesTaxNo;
    }

    public String getTaxSlab() {
        return taxSlab;
    }

    public void setTaxSlab(String taxSlab) {
        this.taxSlab = taxSlab;
    }

    public String getTdsCode() {
        return tdsCode;
    }

    public void setTdsCode(String tdsCode) {
        this.tdsCode = tdsCode;
    }

    public String getTdsCustId() {
        return tdsCustId;
    }

    public void setTdsCustId(String tdsCustId) {
        this.tdsCustId = tdsCustId;
    }

    public Date getTdsExemEndDate() {
        return tdsExemEndDate;
    }

    public void setTdsExemEndDate(Date tdsExemEndDate) {
        this.tdsExemEndDate = tdsExemEndDate;
    }

    public String getTdsExempRefNo() {
        return tdsExempRefNo;
    }

    public void setTdsExempRefNo(String tdsExempRefNo) {
        this.tdsExempRefNo = tdsExempRefNo;
    }

    public Date getTdsFormRcvdate() {
        return tdsFormRcvdate;
    }

    public void setTdsFormRcvdate(Date tdsFormRcvdate) {
        this.tdsFormRcvdate = tdsFormRcvdate;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getVoterIdNo() {
        return voterIdNo;
    }

    public void setVoterIdNo(String voterIdNo) {
        this.voterIdNo = voterIdNo;
    }

    public String getNregaJobCard() {
        return nregaJobCard;
    }

    public void setNregaJobCard(String nregaJobCard) {
        this.nregaJobCard = nregaJobCard;
    }

    public Date getDlExpiry() {
        return dlExpiry;
    }

    public void setDlExpiry(Date dlExpiry) {
        this.dlExpiry = dlExpiry;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Date getPassportExpDt() {
        return passportExpDt;
    }

    public void setPassportExpDt(Date passportExpDt) {
        this.passportExpDt = passportExpDt;
    }

    public String getIncomeRange() {
        return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
        this.incomeRange = incomeRange;
    }

    public List<SelectItem> getIncomeRangeList() {
        return incomeRangeList;
    }

    public void setIncomeRangeList(List<SelectItem> incomeRangeList) {
        this.incomeRangeList = incomeRangeList;
    }

    public String getNetworth() {
        return networth;
    }

    public void setNetworth(String networth) {
        this.networth = networth;
    }

    public Date getNetworthAsOn() {
        return networthAsOn;
    }

    public void setNetworthAsOn(Date networthAsOn) {
        this.networthAsOn = networthAsOn;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<SelectItem> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<SelectItem> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getExposed() {
        return exposed;
    }

    public void setExposed(String exposed) {
        this.exposed = exposed;
    }

    public List<SelectItem> getExposedList() {
        return exposedList;
    }

    public void setExposedList(List<SelectItem> exposedList) {
        this.exposedList = exposedList;
    }

    public String getLegelPoiDoc() {
        return legelPoiDoc;
    }

    public void setLegelPoiDoc(String legelPoiDoc) {
        this.legelPoiDoc = legelPoiDoc;
    }

    public List<SelectItem> getLegelPoiDocList() {
        return legelPoiDocList;
    }

    public void setLegelPoiDocList(List<SelectItem> legelPoiDocList) {
        this.legelPoiDocList = legelPoiDocList;
    }

    public String getTanNo() {
        return tanNo;
    }

    public void setTanNo(String tanNo) {
        this.tanNo = tanNo;
    }

    public String getCinNo() {
        return cinNo;
    }

    public void setCinNo(String cinNo) {
        this.cinNo = cinNo;
    }

    public boolean isCustGeneralInfoComDisable() {
        return custGeneralInfoComDisable;
    }

    public void setCustGeneralInfoComDisable(boolean custGeneralInfoComDisable) {
        this.custGeneralInfoComDisable = custGeneralInfoComDisable;
    }

    public boolean isCustGeneralInfoIndDisable() {
        return custGeneralInfoIndDisable;
    }

    public void setCustGeneralInfoIndDisable(boolean custGeneralInfoIndDisable) {
        this.custGeneralInfoIndDisable = custGeneralInfoIndDisable;
    }

    public String getVoterIdNoChgFlag() {
        return voterIdNoChgFlag;
    }

    public void setVoterIdNoChgFlag(String voterIdNoChgFlag) {
        this.voterIdNoChgFlag = voterIdNoChgFlag;
    }

    public String getNaregaJobCardChgFlag() {
        return naregaJobCardChgFlag;
    }

    public void setNaregaJobCardChgFlag(String naregaJobCardChgFlag) {
        this.naregaJobCardChgFlag = naregaJobCardChgFlag;
    }

    public String getDlNoChgFlag() {
        return dlNoChgFlag;
    }

    public void setDlNoChgFlag(String dlNoChgFlag) {
        this.dlNoChgFlag = dlNoChgFlag;
    }

    public String getDlDtChgFlag() {
        return dlDtChgFlag;
    }

    public void setDlDtChgFlag(String dlDtChgFlag) {
        this.dlDtChgFlag = dlDtChgFlag;
    }

    public String getPassportNoChgFlag() {
        return passportNoChgFlag;
    }

    public void setPassportNoChgFlag(String passportNoChgFlag) {
        this.passportNoChgFlag = passportNoChgFlag;
    }

    public String getPassportExpDtChgFlag() {
        return passportExpDtChgFlag;
    }

    public void setPassportExpDtChgFlag(String passportExpDtChgFlag) {
        this.passportExpDtChgFlag = passportExpDtChgFlag;
    }

    public String getTanChgFlag() {
        return tanChgFlag;
    }

    public void setTanChgFlag(String tanChgFlag) {
        this.tanChgFlag = tanChgFlag;
    }

    public String getCinChgFlag() {
        return cinChgFlag;
    }

    public void setCinChgFlag(String cinChgFlag) {
        this.cinChgFlag = cinChgFlag;
    }

    public String getTinChgFlag() {
        return tinChgFlag;
    }

    public void setTinChgFlag(String tinChgFlag) {
        this.tinChgFlag = tinChgFlag;
    }

    public String getLegalPoiDocChgFlag() {
        return legalPoiDocChgFlag;
    }

    public void setLegalPoiDocChgFlag(String legalPoiDocChgFlag) {
        this.legalPoiDocChgFlag = legalPoiDocChgFlag;
    }

    public String getIncomeRangeChgFlag() {
        return incomeRangeChgFlag;
    }

    public void setIncomeRangeChgFlag(String incomeRangeChgFlag) {
        this.incomeRangeChgFlag = incomeRangeChgFlag;
    }

    public String getNetworthChgFlag() {
        return networthChgFlag;
    }

    public void setNetworthChgFlag(String networthChgFlag) {
        this.networthChgFlag = networthChgFlag;
    }

    public String getNetworthAsOnChgFlag() {
        return networthAsOnChgFlag;
    }

    public void setNetworthAsOnChgFlag(String networthAsOnChgFlag) {
        this.networthAsOnChgFlag = networthAsOnChgFlag;
    }

    public String getEduQualificationChgFlag() {
        return eduQualificationChgFlag;
    }

    public void setEduQualificationChgFlag(String eduQualificationChgFlag) {
        this.eduQualificationChgFlag = eduQualificationChgFlag;
    }

    public String getExposedChgFlag() {
        return exposedChgFlag;
    }

    public void setExposedChgFlag(String exposedChgFlag) {
        this.exposedChgFlag = exposedChgFlag;
    }

    public String getSalaryChgFlag() {
        return salaryChgFlag;
    }

    public void setSalaryChgFlag(String salaryChgFlag) {
        this.salaryChgFlag = salaryChgFlag;
    }

    public String getOtherIdentity() {
        return otherIdentity;
    }

    public void setOtherIdentity(String otherIdentity) {
        this.otherIdentity = otherIdentity;
    }

    public String getOtherIdentityChgFlag() {
        return otherIdentityChgFlag;
    }

    public void setOtherIdentityChgFlag(String otherIdentityChgFlag) {
        this.otherIdentityChgFlag = otherIdentityChgFlag;
    }

    public String getTinIssuingCountry() {
        return tinIssuingCountry;
    }

    public void setTinIssuingCountry(String tinIssuingCountry) {
        this.tinIssuingCountry = tinIssuingCountry;
    }

    public String getTinIssuingCountryChgFlag() {
        return tinIssuingCountryChgFlag;
    }

    public void setTinIssuingCountryChgFlag(String tinIssuingCountryChgFlag) {
        this.tinIssuingCountryChgFlag = tinIssuingCountryChgFlag;
    }

    public List<SelectItem> getTinIssuingCountryList() {
        return tinIssuingCountryList;
    }

    public void setTinIssuingCountryList(List<SelectItem> tinIssuingCountryList) {
        this.tinIssuingCountryList = tinIssuingCountryList;
    }

    public Date getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(Date idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getIdExpiryDateChgFlag() {
        return idExpiryDateChgFlag;
    }

    public void setIdExpiryDateChgFlag(String idExpiryDateChgFlag) {
        this.idExpiryDateChgFlag = idExpiryDateChgFlag;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdentityNoChgFlag() {
        return identityNoChgFlag;
    }

    public void setIdentityNoChgFlag(String identityNoChgFlag) {
        this.identityNoChgFlag = identityNoChgFlag;
    }

    public String getTinIssuingCountryDisplay() {
        return tinIssuingCountryDisplay;
    }

    public void setTinIssuingCountryDisplay(String tinIssuingCountryDisplay) {
        this.tinIssuingCountryDisplay = tinIssuingCountryDisplay;
    }

    public String getOthrPoiDisplay() {
        return othrPoiDisplay;
    }

    public void setOthrPoiDisplay(String othrPoiDisplay) {
        this.othrPoiDisplay = othrPoiDisplay;
    }

    public String getOthrPoiChgFlag() {
        return othrPoiChgFlag;
    }

    public void setOthrPoiChgFlag(String othrPoiChgFlag) {
        this.othrPoiChgFlag = othrPoiChgFlag;
    }

    public List<CBSCustIdentityDetailsTo> getCustIdList() {
        return custIdList;
    }

    public void setCustIdList(List<CBSCustIdentityDetailsTo> custIdList) {
        this.custIdList = custIdList;
    }

    public String getOptTinIssuingCountry() {
        return optTinIssuingCountry;
    }

    public void setOptTinIssuingCountry(String optTinIssuingCountry) {
        this.optTinIssuingCountry = optTinIssuingCountry;
    }

    public String getOptIdentityNo() {
        return optIdentityNo;
    }

    public void setOptIdentityNo(String optIdentityNo) {
        this.optIdentityNo = optIdentityNo;
    }

    public Date getOptIdExpiryDate() {
        return optIdExpiryDate;
    }

    public void setOptIdExpiryDate(Date optIdExpiryDate) {
        this.optIdExpiryDate = optIdExpiryDate;
    }

    public String getOptTinIssuingCountryDisplay() {
        return optTinIssuingCountryDisplay;
    }

    public void setOptTinIssuingCountryDisplay(String optTinIssuingCountryDisplay) {
        this.optTinIssuingCountryDisplay = optTinIssuingCountryDisplay;
    }

    public String getOptOthrPoiDisplay() {
        return optOthrPoiDisplay;
    }

    public void setOptOthrPoiDisplay(String optOthrPoiDisplay) {
        this.optOthrPoiDisplay = optOthrPoiDisplay;
    }

    public String getOptLegalPoiDoc() {
        return optLegalPoiDoc;
    }

    public void setOptLegalPoiDoc(String optLegalPoiDoc) {
        this.optLegalPoiDoc = optLegalPoiDoc;
    }

    public String getOptOtherIdentity() {
        return optOtherIdentity;
    }

    public void setOptOtherIdentity(String optOtherIdentity) {
        this.optOtherIdentity = optOtherIdentity;
    }

    public String getCustomerEntityGn() {
        return customerEntityGn;
    }

    public void setCustomerEntityGn(String customerEntityGn) {
        this.customerEntityGn = customerEntityGn;
    }

    public String getIdentityEdit() {
        return identityEdit;
    }

    public void setIdentityEdit(String identityEdit) {
        this.identityEdit = identityEdit;
    }
}
