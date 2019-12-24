package com.cbs.web.controller.ho.bankGuarantee;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.report.ExceptionReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.AccountStatusSecurityFacadeRemote;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.loan.LoanAccountsDetailsRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.ho.bankGuarantee.BankGuaranteeFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import com.cbs.dto.TdLienMarkingGrid;
import com.cbs.dto.BankGuaranteePojo;
import com.cbs.servlets.Init;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

public class BankGuarantee extends BaseBean {

    private String message;
    private String function;
    private String functionFlag;
    private List<SelectItem> functionList;
    private List<BankGuaranteePojo> gridDetail;
    private String action;
    private List<SelectItem> actionList;
    private String acno, acNoLen, oldAcno;
    private String benfiName;
    private String benfiAddress;
    private String city;
    private String verGuarantee;
    private String cityCode;
    String expiryReNewDate;
    private String purpose;
    private String status;
    private int GuaranteeNo;
    private String secutiType;
    private String savepopup = "true";
    private String issueType;
    private String issueDisplay = "none";
    private List<SelectItem> issueTypeList;
    private String firstDisplay = "none";
    String guaranteeExpiryDate1;
    private List<SelectItem> purposeList;
//    private String userName;
    private List<SelectItem> cityList;
    private String state;
    private int gridModifySize;
    private List<SelectItem> stateList;
    private String mode;
    private List<SelectItem> modeList;
    private String perDistrictChgFlag;
    private String pinCode;
    private String name;
    private String margin1;
    private double odLimit;
    private String orgnBrCode;
    private double taxCharges;
    private String balance;
    private String verifyflag;
    private boolean taxflag = false;
    private boolean disableFlag = true;
    private boolean flag = true;
    private boolean flag1 = true;
    private boolean deleteflag = true;
    private String flag2 = "true";
    private boolean flagRefresh = false;
    private boolean flagExit = false;
    private String expiryDate;
    private String renewExpiryDate;
    private String guaranteeExpiryDate;
    private String date1;
    private String roiOnSecurity;
    private String classification;
    private String classificationCode;
    private String guaranteeIssuedBy;
    private List<SelectItem> guaranteeIssuedByList;
    private String purposeCode;
    private List<SelectItem> classificationList;
    private String validityCode;
    private String validityIn;
    private List<SelectItem> validityInList;
    private int period;
    private double comissionAmt;
    private double totalcomissionAmt;
    private String newAcno;
    private List globalList;
    private String guaranteeInvokingDueDt;
    private String entryBy;
    private String authBy;
    private String entryDt;
    private String updateDt;
    private int txnId;
    private double commissionAmtPerPeriod;
    private BigDecimal guaranteeAmt;
    List<SelectItem> schemeCodeOption;
    private List<SelectItem> assetDescOption;
    List<SelectItem> actionGuaranteeList;
    private String remarks;
    CustomerMasterDelegate masterDelegate;
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    AdvancesInformationTrackingRemote aitr;
    private CommonReportMethodsRemote CommonReportMethodsBean;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private BankGuaranteeFacadeRemote bankGuarRemort;
    private FtsPostingMgmtFacadeRemote fts = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    private Date dt = new Date();
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    LoanAccountsDetailsRemote loanAcDetails;
    private CommonReportMethodsRemote comReportRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private final String jndiHomeNameComPost = "CommonReportMethods";
    private HttpServletRequest req;
    private String reportType;
    private List<SelectItem> reportTypeList;
    // aditya added
    private String fromDate;
    private String toDate;
    private String tillDate;
    private String displaypaneldate = "none";
    private String displaypanel = "";
    private String displaytype = "none";
    private String displayFromDate = "none";
    private String displayToDate = "none";
    private String displayTillDate = "none";
    private boolean printDisable = true;
    private List<BankGuaranteePojo> reportData;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public boolean isPrintDisable() {
        return printDisable;
    }

    public void setPrintDisable(boolean printDisable) {
        this.printDisable = printDisable;
    }

    public String getDisplaypanel() {
        return displaypanel;
    }

    public void setDisplaypanel(String displaypanel) {
        this.displaypanel = displaypanel;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getRenewExpiryDate() {
        return renewExpiryDate;
    }

    public void setRenewExpiryDate(String renewExpiryDate) {
        this.renewExpiryDate = renewExpiryDate;
    }

    public String getDisplayFromDate() {
        return displayFromDate;
    }

    public void setDisplayFromDate(String displayFromDate) {
        this.displayFromDate = displayFromDate;
    }

    public String getDisplayToDate() {
        return displayToDate;
    }

    public void setDisplayToDate(String displayToDate) {
        this.displayToDate = displayToDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVerifyflag() {
        return verifyflag;
    }

    public void setVerifyflag(String verifyflag) {
        this.verifyflag = verifyflag;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getGuaranteeInvokingDueDt() {
        return guaranteeInvokingDueDt;
    }

    public void setGuaranteeInvokingDueDt(String guaranteeInvokingDueDt) {
        this.guaranteeInvokingDueDt = guaranteeInvokingDueDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    public String getSecutiType() {
        return secutiType;
    }

    public void setSecutiType(String secutiType) {
        this.secutiType = secutiType;
    }

    public String getVerGuarantee() {
        return verGuarantee;
    }

    public void setVerGuarantee(String verGuarantee) {
        this.verGuarantee = verGuarantee;
    }

    public int getGuaranteeNo() {
        return GuaranteeNo;
    }

    public void setGuaranteeNo(int GuaranteeNo) {
        this.GuaranteeNo = GuaranteeNo;
    }

    public List<BankGuaranteePojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BankGuaranteePojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getMargin1() {
        return margin1;
    }

    public void setMargin1(String margin1) {
        this.margin1 = margin1;
    }

    public String getGuaranteeExpiryDate1() {
        return guaranteeExpiryDate1;
    }

    public void setGuaranteeExpiryDate1(String guaranteeExpiryDate1) {
        this.guaranteeExpiryDate1 = guaranteeExpiryDate1;
    }

    public String getValidityCode() {
        return validityCode;
    }

    public void setValidityCode(String validityCode) {
        this.validityCode = validityCode;
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

    public List<SelectItem> getAssetDescOption() {
        return assetDescOption;
    }

    public void setAssetDescOption(List<SelectItem> assetDescOption) {
        this.assetDescOption = assetDescOption;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<SelectItem> getActionList() {
        return actionList;
    }

    public void setActionList(List<SelectItem> actionList) {
        this.actionList = actionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public Double getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(Double odLimit) {
        this.odLimit = odLimit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPerDistrictChgFlag() {
        return perDistrictChgFlag;
    }

    public void setPerDistrictChgFlag(String perDistrictChgFlag) {
        this.perDistrictChgFlag = perDistrictChgFlag;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getBenfiName() {
        return benfiName;
    }

    public void setBenfiName(String benfiName) {
        this.benfiName = benfiName;
    }

    public String getBenfiAddress() {
        return benfiAddress;
    }

    public void setBenfiAddress(String benfiAddress) {
        this.benfiAddress = benfiAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public double getCommissionAmtPerPeriod() {
        return commissionAmtPerPeriod;
    }

    public void setCommissionAmtPerPeriod(double commissionAmtPerPeriod) {
        this.commissionAmtPerPeriod = commissionAmtPerPeriod;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public List<SelectItem> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<SelectItem> classificationList) {
        this.classificationList = classificationList;
    }

    public String getValidityIn() {
        return validityIn;
    }

    public void setValidityIn(String validityIn) {
        this.validityIn = validityIn;
    }

    public List<SelectItem> getValidityInList() {
        return validityInList;
    }

    public void setValidityInList(List<SelectItem> validityInList) {
        this.validityInList = validityInList;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public List<SelectItem> getCityList() {
        return cityList;
    }

    public void setCityList(List<SelectItem> cityList) {
        this.cityList = cityList;
    }

    public List<SelectItem> getStateList() {
        return stateList;
    }

    public void setStateList(List<SelectItem> stateList) {
        this.stateList = stateList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getGuaranteeExpiryDate() {
        return guaranteeExpiryDate;
    }

    public void setGuaranteeExpiryDate(String guaranteeExpiryDate) {
        this.guaranteeExpiryDate = guaranteeExpiryDate;
    }

    public String getExpiryReNewDate() {
        return expiryReNewDate;
    }

    public void setExpiryReNewDate(String expiryReNewDate) {
        this.expiryReNewDate = expiryReNewDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getComissionAmt() {
        return comissionAmt;
    }

    public void setComissionAmt(double comissionAmt) {
        this.comissionAmt = comissionAmt;
    }

    public double getTotalcomissionAmt() {
        return totalcomissionAmt;
    }

    public void setTotalcomissionAmt(double totalcomissionAmt) {
        this.totalcomissionAmt = totalcomissionAmt;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public double getTaxCharges() {
        return taxCharges;
    }

    public void setTaxCharges(double taxCharges) {
        this.taxCharges = taxCharges;
    }

    public List getGlobalList() {
        return globalList;
    }

    public void setGlobalList(List globalList) {
        this.globalList = globalList;
    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEntryDt() {
        return entryDt;
    }

    public void setEntryDt(String entryDt) {
        this.entryDt = entryDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public BigDecimal getGuaranteeAmt() {
        return guaranteeAmt;
    }

    public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
        this.guaranteeAmt = guaranteeAmt;
    }

    public String getFirstDisplay() {
        return firstDisplay;
    }

    public void setFirstDisplay(String firstDisplay) {
        this.firstDisplay = firstDisplay;
    }

    public List<SelectItem> getActionGuaranteeList() {
        return actionGuaranteeList;
    }

    public void setActionGuaranteeList(List<SelectItem> actionGuaranteeList) {
        this.actionGuaranteeList = actionGuaranteeList;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getGuaranteeIssuedBy() {
        return guaranteeIssuedBy;
    }

    public void setGuaranteeIssuedBy(String guaranteeIssuedBy) {
        this.guaranteeIssuedBy = guaranteeIssuedBy;
    }

    public List<SelectItem> getGuaranteeIssuedByList() {
        return guaranteeIssuedByList;
    }

    public void setGuaranteeIssuedByList(List<SelectItem> guaranteeIssuedByList) {
        this.guaranteeIssuedByList = guaranteeIssuedByList;
    }

    public boolean isTaxflag() {
        return taxflag;
    }

    public void setTaxflag(boolean taxflag) {
        this.taxflag = taxflag;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getDisplaytype() {
        return displaytype;
    }

    public void setDisplaytype(String displaytype) {
        this.displaytype = displaytype;
    }

    public String getDisplaypaneldate() {
        return displaypaneldate;
    }

    public void setDisplaypaneldate(String displaypaneldate) {
        this.displaypaneldate = displaypaneldate;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public List<SelectItem> getIssueTypeList() {
        return issueTypeList;
    }

    public void setIssueTypeList(List<SelectItem> issueTypeList) {
        this.issueTypeList = issueTypeList;
    }

    public String getIssueDisplay() {
        return issueDisplay;
    }

    public void setIssueDisplay(String issueDisplay) {
        this.issueDisplay = issueDisplay;
    }

    public String getDisplayTillDate() {
        return displayTillDate;
    }

    public void setDisplayTillDate(String displayTillDate) {
        this.displayTillDate = displayTillDate;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public boolean isFlagRefresh() {
        return flagRefresh;
    }

    public void setFlagRefresh(boolean flagRefresh) {
        this.flagRefresh = flagRefresh;
    }

    public boolean isFlagExit() {
        return flagExit;
    }

    public void setFlagExit(boolean flagExit) {
        this.flagExit = flagExit;
    }

    public int getGridModifySize() {
        return gridModifySize;
    }

    public void setGridModifySize(int gridModifySize) {
        this.gridModifySize = gridModifySize;
    }

    public String getSavepopup() {
        return savepopup;
    }

    public void setSavepopup(String savepopup) {
        this.savepopup = savepopup;
    }

    public boolean isDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(boolean deleteflag) {
        this.deleteflag = deleteflag;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public BankGuarantee() {
        try {
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            masterDelegate = new CustomerMasterDelegate();
            testEJBSEC = (AccountStatusSecurityFacadeRemote) ServiceLocator.getInstance().lookup("AccountStatusSecurityFacade");
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            openingFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            loanInterestCalculationBean = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            lienMark = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            comReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameComPost);
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            loanAcDetails = (LoanAccountsDetailsRemote) ServiceLocator.getInstance().lookup("LoanAccountsDetailsFacade");
            CommonReportMethodsBean = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            bankGuarRemort = (BankGuaranteeFacadeRemote) ServiceLocator.getInstance().lookup("BankGuaranteeFacade");
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("A", "Add"));
            functionList.add(new SelectItem("M", "Modify"));
            functionList.add(new SelectItem("VR", "Verify"));
            functionList.add(new SelectItem("D", "Delete"));
            functionList.add(new SelectItem("Vi", "View"));
            date1 = ymd.format(dt);
            reportTypeList = new ArrayList<SelectItem>();
            cityList = new ArrayList();
            globalList = new ArrayList();
            this.setFlag(true);
            this.setFlag1(true);
            this.flagRefresh = false;
            this.flagExit = false;
            this.setTaxflag(false);
            this.setDisableFlag(true);
            validityInAction();
            /**
             * THIS CODE IS FOR SECURITY DETAIL*
             */
            getTableValues();
            setEstimationDateLbl("Estimation Date");
            setValuationAmtLbl("Valuation Amt");
            setRevalutionDateLbl("Revalution Date");
            MarkSecurity();
            /**
             * FOR TD LIEN MARKING*
             */
            this.setMessageLM("");
            gridDetailLM = new ArrayList<TdLienMarkingGrid>();
            issueTypeList = new ArrayList<SelectItem>();

            lienMarkOptionList = new ArrayList<SelectItem>();
            lienMarkOptionList.add(new SelectItem("--Select--"));
            lienMarkOptionList.add(new SelectItem("Yes"));
//            lienMarkOptionList.add(new SelectItem("No"));

            intTypeOption = new ArrayList<SelectItem>();
            intTypeOption.add(new SelectItem("0", ""));
//            intTypeOption.add(new SelectItem("Ab", "Absolute Fix"));
//            intTypeOption.add(new SelectItem("Fl", "Floating"));

//            intTypeOption.add(new SelectItem("Fi", "Fixed"));
            List resultList1 = CommonReportMethodsBean.getRefRecList("202");
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele1 = (Vector) resultList1.get(i);
                    intTypeOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            actionGuaranteeList = new ArrayList<SelectItem>();
            actionGuaranteeList.add(new SelectItem("--Select--"));

            stateList = new ArrayList<SelectItem>();
            stateList.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> stateOptionList = masterDelegate.getFunctionValues("002");
            for (CbsRefRecTypeTO recTypeTO : stateOptionList) {
                stateList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            List resultList2 = CommonReportMethodsBean.getRefRecList("401");
            if (!resultList2.isEmpty()) {
                assetDescOption = new ArrayList<SelectItem>();
                assetDescOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele1 = (Vector) resultList2.get(i);
                    assetDescOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            classificationAction();
//            typeOfSecurityList = new ArrayList<SelectItem>();
//            typeOfSecurityList.add(new SelectItem("0", ""));
//            typeOfSecurityList.add(new SelectItem("C", "COLLATERAL"));
//            typeOfSecurityList.add(new SelectItem("P", "PRIMARY"));
//            /**
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void validityPeriodlostFocus() {
        try {
            setValidityIn(validityIn);
            this.setMessage("");
            if (this.function.equalsIgnoreCase("M") && this.action.equalsIgnoreCase("R")) {
                if (period > 0) {
                    this.flag = false;
                } else {
                    setMessage("Please Modify Validity In/Peroid ,Its Mandatory.");
                    this.flag = true;
                }
                if (validityIn.equalsIgnoreCase("D")) {
                    renewExpiryDate = CbsUtil.dateAdd(ymd.format(ymmd.parse(guaranteeExpiryDate1)), period);
                    guaranteeExpiryDate1 = ymmd.format(ymd.parse(renewExpiryDate));
                    guaranteeExpiryDate = dmy.format(ymmd.parse(ymmd.format(ymd.parse(renewExpiryDate))));
                } else if (validityIn.equalsIgnoreCase("M")) {
                    renewExpiryDate = CbsUtil.monthAdd(ymd.format(ymmd.parse(guaranteeExpiryDate1)), period);
                   guaranteeExpiryDate1 = ymmd.format(ymd.parse(renewExpiryDate));
                    guaranteeExpiryDate = dmy.format(ymmd.parse(ymmd.format(ymd.parse(renewExpiryDate))));
                } else {
                    renewExpiryDate = CbsUtil.yearAdd(ymd.format(ymmd.parse(guaranteeExpiryDate1)), period);
                    guaranteeExpiryDate1 = ymmd.format(ymd.parse(renewExpiryDate));
                    guaranteeExpiryDate = dmy.format(ymmd.parse(ymmd.format(ymd.parse(renewExpiryDate))));
                }

            } else {
                if (validityIn.equalsIgnoreCase("D")) {
                    guaranteeExpiryDate1 = CbsUtil.dateAdd(date1, period);
                    guaranteeExpiryDate = dmy.format(ymd.parse(guaranteeExpiryDate1));
                } else if (validityIn.equalsIgnoreCase("M")) {
                    guaranteeExpiryDate1 = CbsUtil.monthAdd(date1, period);
                    guaranteeExpiryDate = dmy.format(ymd.parse(guaranteeExpiryDate1));
                } else {
                    guaranteeExpiryDate1 = CbsUtil.yearAdd(date1, period);
                    guaranteeExpiryDate = dmy.format(ymd.parse(guaranteeExpiryDate1));
                }
            }

//            nextDate();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

//    public void nextDate() {
//        try {
//            StringBuffer sBuffer = new StringBuffer(guaranteeExpiryDate1);
//            String year = sBuffer.substring(3, 4);
//            String mon = sBuffer.substring(5, 6);
//            String dd = sBuffer.substring(7, 8);
//            String modifiedFromDate = dd + '/' + mon + '/' + year;
//            int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
//            java.util.Date dateSelectedFrom = null;
//            dateSelectedFrom = dmy.parse(modifiedFromDate);
//            String nextDate = dmy.format(dateSelectedFrom.getTime() + MILLIS_IN_DAY);
//            nextDate = guaranteeInvokingDueDt;
//
//
//        } catch (Exception e) {
//            this.setMessage(e.getLocalizedMessage());
//        }
//    }
    public void getCitys() {
        try {
            List list = new ArrayList();
            list = bankGuarRemort.getCityList(state);
            cityList = new ArrayList<SelectItem>();
            cityList.add(new SelectItem("0", "--Select--"));
            for (int m = 0; m < list.size(); m++) {
                Vector ele = (Vector) list.get(m);
                cityList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getInterestDetails() {
        try {
            String code = "", intcode = "";
            List list = aitr.getIntCodeIntTypeSchmCode(acno);
            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                List listForSchemeCode = new ArrayList();
                listForSchemeCode = aitr.schemeCodeDet(vec.get(0).toString());
                schemeCodeOption = new ArrayList<SelectItem>();
                schemeCodeOption.add(new SelectItem("0", ""));
                for (int i = 0; i < listForSchemeCode.size(); i++) {
                    Vector element = (Vector) listForSchemeCode.get(i);
                    code = element.get(0).toString();
                    schemeCodeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));

                }
                setLoanDuration(vec.get(3).toString());
                setSchemeCode(code);
                List listForIntTable = new ArrayList();
                listForIntTable = aitr.interestTableCode(code);
                intTableOption = new ArrayList<SelectItem>();
                intTableOption.add(new SelectItem("0", ""));
                for (int i = 0; i < listForIntTable.size(); i++) {
                    Vector element = (Vector) listForIntTable.get(i);
                    intcode = element.get(0).toString();
                    intTableOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
                this.setIntTable(intcode);
                this.setIntType(vec.get(2).toString());
            } else {
                this.setIntTable("0");
                this.setSchemeCode("0");
                this.setIntType("0");
                this.setSancAmount("0.0");
            }
//            }

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getNewAccNo() {
        setMessage("");
        this.getUserName();
        setAction(action);
        guaranteeIssuedOnAction();
        if (!(function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("M"))) {
            if (this.acno.equalsIgnoreCase("") || this.acno.length() == 0) {
                this.setMessage("Please Enter Account No.");
                return;
            }
            if (!acno.matches("[0-9a-zA-z]*")) {
                this.setMessage("Please Enter Valid  Account No.");
                return;
            }
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Account number is not valid.");
                return;
            }
            try {
                newAcno = fts.getNewAccountNumber(this.acno);
                String currentBrnCode = fts.getCurrentBrnCode(newAcno);
                if (!currentBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                    setMessage("Sorry! You can not save/update other branch's account details.");
                    return;
                }
                setNewAcno(newAcno);
                String code = this.newAcno;
                int length = code.length();
                int addedZero = 6 - length;
                for (int i = 1; i <= addedZero; i++) {
                    code = "0" + code;
                }
                getInterestDetails();
                List listForIntTable = new ArrayList();
                listForIntTable = aitr.interestTableCode(code);
                intTableOption = new ArrayList<SelectItem>();
                intTableOption.add(new SelectItem("0", ""));
                for (int m = 0; m < listForIntTable.size(); m++) {
                    Vector element = (Vector) listForIntTable.get(m);
                    intTableOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }

                List acctInfo = loanAcDetails.accountDetail(newAcno);
                if (acctInfo.isEmpty()) {
                    gridDetail = new ArrayList<BankGuaranteePojo>();
                    this.setMessage("Account No. Does Not Exist.");
                    Date orgnDt = new Date();
                    this.setAcno1("");
                    this.setLblGroupID("none");
                    this.setLblGroupType("none");
                    this.setDdGroupId("none");
                    this.setDdGroupType("none");
                    resetPage();
                    return;
                } else {
                    for (int i = 0; i < acctInfo.size(); i++) {
                        Vector ele = (Vector) acctInfo.get(i);
                        if (ele.get(3).toString().equalsIgnoreCase("9")) {
                            this.setMessage("Account has been closed.");
                        } else {
                            roiOnSecurity = loanInterestCalculationBean.getROI(intTable, Double.parseDouble(ele.get(4).toString()), ymd.format(dt));
                            additionalRoi = "0";

                            custId = ele.get(9).toString();
                            custId1 = ele.get(10).toString();
                            custId2 = ele.get(11).toString();
                            custId3 = ele.get(12).toString();
                            custId4 = ele.get(13).toString();
                            disableRetireAge();
                            this.setName(ele.get(1).toString());
                            this.setSancAmount(ele.get(4).toString());
                            this.setCommissionAmtPerPeriod(0.00);
                            double bal = CommonReportMethodsBean.getBalanceOnDate(this.newAcno, ymd.format(dt));
                            setBalance(String.valueOf(bal));
                            List<ExceptionReportPojo> list = new ArrayList<>();
                            list = bankGuarRemort.getAccountDetail(this.newAcno);
                            if (!list.isEmpty()) {
                                ExceptionReportPojo lt = list.get(0);
                                setOdLimit(odLimit = lt.getOdLimit());
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                this.setMessage(ex.getMessage());
            }
        }
    }

    public void modesList() {
        try {
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("Percentage"));
            modeList.add(new SelectItem("Amount"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void issueTypeBlur() {
        try {
            if (function.equalsIgnoreCase("Vi") && (action.equalsIgnoreCase("I") || action.equalsIgnoreCase("R"))) {
                this.issueDisplay = "";
                this.displayTillDate = "none";
                this.displayFromDate = "";
                this.displayToDate = "";
                issueTypeList = new ArrayList<SelectItem>();
                issueTypeList.add(new SelectItem("All", "All"));
                issueTypeList.add(new SelectItem("AA", "All Active"));
                issueTypeList.add(new SelectItem("EI", "Expiry In"));
            } else if (function.equalsIgnoreCase("Vi") && (action.equalsIgnoreCase("V") || action.equalsIgnoreCase("C"))) {
                issueDisplay = "none";
                displayTillDate = "none";
                displayToDate = "";
                displayFromDate = "";
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void TotalcomAmt() {
        try {
            if (!((function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("M"))
                    && (action.equalsIgnoreCase("V") || action.equalsIgnoreCase("C")))) {
                this.taxflag = false;
                totalcomissionAmt = period * comissionAmt;
                setTotalcomissionAmt(totalcomissionAmt);
                taxCharges = bankGuarRemort.getTaxCharges(comissionAmt, date1);
                setTaxCharges(taxCharges);
            } else {
                this.taxflag = true;

            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void reRefresh() {
        currentItem = new ArrayList<TdLienMarkingGrid>();
        actionGuaranteeList = new ArrayList<SelectItem>();
        setMessage(" ");
        setAction("--Select--");
        setVerGuarantee("--Select--");
        setAcno("");
        setName("");
        setOdLimit(0.0);
        setBalance("");
        setBenfiName("");
        setBenfiAddress("");
        setPinCode("");
        setState("--Select--");
        setCity("");
        setClassification("--Select--");
        setGuaranteeIssuedBy("--Select--");
        setPurpose("--Select--");
        setPeriod(0);
        setGuaranteeExpiryDate("");
        verifyflag = "none";
        firstDisplay = "none";
        printDisable = true;
        issueDisplay = "none";
        displayTillDate = "none";
        setIssueType("--Select--");
        setGuaranteeAmt(new BigDecimal(0.0));
        setComissionAmt(0.0);
        setTotalcomissionAmt(0.0);
        setTaxCharges(0.0);
        getUserName();
        taxflag = false;
        flag = true;
        flag1 = true;
    }

    public void blurAction() {
        reRefresh();
        try {
            actionList = new ArrayList<SelectItem>();
            if (function.equalsIgnoreCase("A")) {
                actionList.add(new SelectItem("I", "Issue"));
                flag = false;
                flag1 = true;
                deleteflag = true;
                this.flagRefresh = false;
                this.flagExit = false;
//                this.fflag = false;
                this.displaypanel = "";
                this.displaypaneldate = "none";
                this.issueDisplay = "none";
                this.displaytype = "none";
                this.displayFromDate = "none";
                this.displayToDate = "none";
                printDisable = true;
                // ended

            } else if (function.equalsIgnoreCase("M")) {
                actionList.add(new SelectItem("I", "Issue"));
                actionList.add(new SelectItem("R", "Renew"));
                actionList.add(new SelectItem("V", "Invoke/claim"));
                actionList.add(new SelectItem("C", "Surrender/Close"));
                flag = false;
                flag1 = true;
                deleteflag = true;
                this.flagRefresh = false;
                this.flagExit = false;
//                this.fflag = false;
                // aditya added
                this.displaypanel = "";
                this.issueDisplay = "none";
                this.displaypaneldate = "none";
                this.displaytype = "none";
                this.displayFromDate = "none";
                this.displayToDate = "none";
                printDisable = true;
                // ended
            } else if (function.equalsIgnoreCase("VR")) {
                actionList.add(new SelectItem("I", "Issue"));
                actionList.add(new SelectItem("R", "Renew"));
                actionList.add(new SelectItem("V", "Invoke/claim"));
                actionList.add(new SelectItem("C", "Surrender"));
                flag = true;
                flag1 = false;
                this.flagRefresh = false;
                this.flagExit = false;
                deleteflag = true;
//                this.fflag = false;
                // aditya added
                this.displaypanel = "";
                this.issueDisplay = "none";
                this.displaypaneldate = "none";
                this.displaytype = "none";
                this.displayFromDate = "none";
                this.displayToDate = "none";
                printDisable = true;
                // ended
            } else if (function.equalsIgnoreCase("D")) {
                actionList.add(new SelectItem("I", "Issue"));
                flag = true;
                flag1 = true;
                deleteflag = false;
                this.flagRefresh = false;
                this.flagExit = false;
//                this.fflag = false;
                this.displaypanel = "";
                this.displaypaneldate = "none";
                this.issueDisplay = "none";
                this.displaytype = "none";
                this.displayFromDate = "none";
                this.displayToDate = "none";
                printDisable = true;
            } else {
                actionList.add(new SelectItem("I", "Issue"));
                actionList.add(new SelectItem("R", "Renew"));
                actionList.add(new SelectItem("V", "Invoke/claim"));
                actionList.add(new SelectItem("C", "Surrender"));
                reportTypeList = new ArrayList<SelectItem>();
                reportTypeList.add(new SelectItem("S", "Summary"));
                reportTypeList.add(new SelectItem("D", "Detail"));
                flag = true;
                deleteflag = true;
                flag1 = true;
                this.flagRefresh = false;
                this.flagExit = false;
//                this.fflag = false;
                this.issueDisplay = "none";
                // aditya added
                this.displaypanel = "none";
                this.displaypaneldate = "";
                this.displaytype = "";
                this.displayFromDate = "";
                this.displayToDate = "";
                this.displayTillDate = "none";
                this.fromDate = getTodayDate();
                this.toDate = getTodayDate();
                this.tillDate = getTodayDate();
                this.printDisable = false;
                // ended
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void dateblur() {
        try {
            if (function.equalsIgnoreCase("Vi") && action.equalsIgnoreCase("I") && issueType.equalsIgnoreCase("AA")) {
                this.displayFromDate = "none";
                this.displayToDate = "none";
                this.displayTillDate = "";
            } else {
                this.displayFromDate = "";
                this.displayToDate = "";
                this.displayTillDate = "none";
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void guaranteeBlurRefresh() {
        currentItem = new ArrayList<TdLienMarkingGrid>();
        actionGuaranteeList = new ArrayList<SelectItem>();
        setMessage(" ");
        setVerGuarantee("--Select--");
        setAcno("");
        setName("");
        setOdLimit(0.0);
        setBalance("");
        setBenfiName("");
        setBenfiAddress("");
        setPinCode("");
        setState("--Select--");
        setCity("");
        setClassification("--Select--");
        setGuaranteeIssuedBy("--Select--");
        setPurpose("--Select--");
        setPeriod(0);
        setGuaranteeExpiryDate("");
        setIssueType("--Select--");
        setGuaranteeAmt(new BigDecimal(0.0));
        setComissionAmt(0.0);
        setTotalcomissionAmt(0.0);
        setTaxCharges(0.0);
        getUserName();
    }

    public void guaranteeBlur() {
        try {
            guaranteeBlurRefresh();
            String authValue = "N";
            issueTypeBlur();
            if (function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("M") || function.equalsIgnoreCase("D")) {
                if (function.equalsIgnoreCase("M")) {
                    authValue = "Y";
                    //action = "I";
                }
                this.firstDisplay = " ";
                List data = bankGuarRemort.verifyGNo(function, action, authValue, orgnBrCode);
                if (!data.isEmpty()) {
                    actionGuaranteeList = new ArrayList<SelectItem>();
                    actionGuaranteeList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < data.size(); i++) {
                        Vector ele = (Vector) data.get(i);
                        actionGuaranteeList.add(new SelectItem(ele.get(0).toString(), "[" + ele.get(0).toString() + "]" + "[" + ele.get(1).toString() + "]" + "[" + ele.get(2).toString() + "]"));
                    }
                } else {
                    this.setMessage("");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void verifyBlur() {
        try {
            this.flag1 = (false);
            message = "";
            if (!verGuarantee.equalsIgnoreCase("--Select--")) {
                GuaranteeNo = Integer.parseInt(verGuarantee);
            }
            if (function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("M") || function.equalsIgnoreCase("D")) {
                List veri = bankGuarRemort.verifyCase(function, getGuaranteeNo(), action);
                if (!veri.isEmpty()) {
                    for (int i = 0; i < veri.size(); i++) {
                        Vector ele = (Vector) veri.get(i);
                        setAcno(ele.get(0).toString());
                        setName(ftsPostRemote.getCustName(ele.get(0).toString()));
                        double bal = CommonReportMethodsBean.getBalanceOnDate(ele.get(0).toString(), ymd.format(dt));
                        setBalance(String.valueOf(bal));

                        String code = ele.get(0).toString();
                        int length = code.length();
                        int addedZero = 6 - length;
                        for (int iv = 1; iv <= addedZero; iv++) {
                            code = "0" + code;
                        }
                        getInterestDetails();
                        List listForIntTable = new ArrayList();
                        listForIntTable = aitr.interestTableCode(code);
                        intTableOption = new ArrayList<SelectItem>();
                        intTableOption.add(new SelectItem("0", ""));
                        for (int m = 0; m < listForIntTable.size(); m++) {
                            Vector element = (Vector) listForIntTable.get(m);
                            intTableOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                        }

                        List acctInfo = loanAcDetails.accountDetail(ele.get(0).toString());
                        if (!acctInfo.isEmpty()) {
                            for (int k = 0; k < acctInfo.size(); k++) {
                                Vector ele10 = (Vector) acctInfo.get(k);
                                setOdLimit(Double.parseDouble(ele10.get(4).toString()));
                                custId = ele10.get(9).toString();
                                custId1 = ele10.get(10).toString();
                                custId2 = ele10.get(11).toString();
                                custId3 = ele10.get(12).toString();
                                custId4 = ele10.get(13).toString();
                                roiOnSecurity = loanInterestCalculationBean.getROI(intTable, Double.parseDouble(ele10.get(4).toString()), ymd.format(dt));
                                additionalRoi = "0";
                                this.setSancAmount(ele10.get(4).toString());
                                disableRetireAge();
                            }
                        }

                        setBenfiName(ele.get(1).toString());
                        setBenfiAddress(ele.get(2).toString());
                        stateList = new ArrayList<SelectItem>();
                        stateList.add(new SelectItem("0", "--Select--"));
                        List<CbsRefRecTypeTO> stateOptionLis = masterDelegate.getFunctionValues("002");
                        for (CbsRefRecTypeTO recTypeTO : stateOptionLis) {
                            stateList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                        }
                        setState(ele.get(4).toString());
                        List list = new ArrayList();
                        list = bankGuarRemort.getCityList(state);
                        cityList = new ArrayList<SelectItem>();
                        cityList.add(new SelectItem("0", "--Select--"));
                        for (int m = 0; m < list.size(); m++) {
                            Vector ele1 = (Vector) list.get(m);
                            cityList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                        }
                        setCity(ele.get(3).toString());
                        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\[0-9]+)?)+");
                        setPinCode(ele.get(5).toString());
                        List resultList2 = CommonReportMethodsBean.getRefRecList("418");
                        if (!resultList2.isEmpty()) {
                            classificationList = new ArrayList<SelectItem>();
                            classificationList.add(new SelectItem("--Select--"));
                            for (int j = 0; j < resultList2.size(); j++) {
                                Vector ele1 = (Vector) resultList2.get(j);
                                classificationList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                                classificationCode = ele1.get(0).toString();
                            }
                        }
                        setClassification(ele.get(6).toString());
                        List resultList4 = CommonReportMethodsBean.getRefRecList("420");
                        if (!resultList4.isEmpty()) {
                            guaranteeIssuedByList = new ArrayList<SelectItem>();
                            guaranteeIssuedByList.add(new SelectItem("--Select--"));
                            for (int k = 0; k < resultList4.size(); k++) {
                                Vector ele1 = (Vector) resultList4.get(k);
                                guaranteeIssuedByList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                                guaranteeIssuedBy = ele1.get(0).toString();
                            }
                        }
//                        setGuaranteeIssuedBy(ele.get(22).toString());
                        List resultLis3 = aitr.getListAsPerRequirement("419", getClassification(), "0", "0", "0", "0", ymd.format(new Date()), 0);
                        purposeList = new ArrayList<SelectItem>();
                        purposeList.add(new SelectItem("--Select--"));
                        if (!resultLis3.isEmpty()) {
                            for (int j = 0; j < resultLis3.size(); j++) {
                                Vector ele1 = (Vector) resultLis3.get(j);
                                purposeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                                purposeCode = ele1.get(0).toString();
                            }
                        }
                        setPurpose(ele.get(7).toString());
                        validityInList = new ArrayList<SelectItem>();
                        validityInList.add(new SelectItem("D", "Day"));
                        validityInList.add(new SelectItem("M", "Month"));
                        validityInList.add(new SelectItem("Y", "Year"));
                        setValidityIn(ele.get(8).toString());
                        setValidityCode(ele.get(8).toString());
                        setPeriod(Integer.parseInt(ele.get(9).toString()));
                        if (function.equalsIgnoreCase("M") && action.equalsIgnoreCase("R")) {
                            setPeriod(0);
                        }

                        setGuaranteeAmt(new BigDecimal(String.valueOf(formatter.format(new BigDecimal(ele.get(10).toString()).doubleValue()))));
//                        setMode(ele.get(11).toString());
                        if (!((function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("M"))
                                && (action.equalsIgnoreCase("V") || action.equalsIgnoreCase("C")
                                || action.equalsIgnoreCase("I")))) {
                            if (function.equalsIgnoreCase("VR") && action.equalsIgnoreCase("R")) {
                                setGuaranteeIssuedBy(ele.get(22).toString());
                                setComissionAmt(Double.parseDouble(ele.get(13).toString()));
                                setTaxCharges(Double.parseDouble(ele.get(14).toString()));
                                this.taxflag = true;
                            } else {
                                setGuaranteeIssuedBy(ele.get(22).toString());
                                setComissionAmt(Double.parseDouble(ele.get(13).toString()));
                                setTaxCharges(Double.parseDouble(ele.get(14).toString()));
                                this.taxflag = false;
                            }
                        } else {
                            setGuaranteeIssuedBy(ele.get(22).toString());
                            setComissionAmt(Double.parseDouble(ele.get(13).toString()));
                            setTaxCharges(Double.parseDouble(ele.get(14).toString()));
                            this.taxflag = true;
                        }
                        setGuaranteeExpiryDate(dmy.format(ymmd.parse(ele.get(15).toString())));
                        setGuaranteeExpiryDate1((ele.get(15).toString()));
                        this.expiryReNewDate = (ele.get(15).toString());
                        setGuaranteeInvokingDueDt(dmy.format(ymmd.parse(ele.get(16).toString())));
                        if (function.equalsIgnoreCase("VR")) {
                            String currentBrnCode = fts.getCurrentBrnCode(ele.get(0).toString());
                            if (!currentBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                                this.flag1 = true;
                                message = "Sorry! You can not Verify other branch's account details.";
                                return;
                            }
                            if (ele.get(17).toString().equalsIgnoreCase(this.getUserName())) {
                                this.flag1 = true;
                                message = "You Cannot Verify Your Own Entry.";
                                return;
                            } else {
                                setEntryBy(ele.get(17).toString());
                                this.flag1 = false;
                            }
                        } else if (function.equalsIgnoreCase("D")) {
                            setEntryBy(ele.get(17).toString());
                            this.flag1 = true;
                            this.flag = true;
                            this.deleteflag = false;
                        } else {
                            setEntryBy(ele.get(17).toString());
                            this.flag1 = true;
                            this.flag = false;
                        }
//                        setUserName(ele.get(17).toString());
//                        setAuthBy(ele.get(18).toString());
                        setEntryDt(ele.get(19).toString());
//                        setUpdateDt(ele.get(20).toString());
                        setTxnId(Integer.parseInt(ele.get(21).toString()));
                    }
                }
                currentItem = new ArrayList<TdLienMarkingGrid>();
                if (this.function.equalsIgnoreCase("M") && this.action.equalsIgnoreCase("R") && this.guaranteeExpiryDate1.equalsIgnoreCase(this.expiryReNewDate)) {
                    setMessage("Please Modify Validity In/Peroid ,Its Mandatory.");
                    this.flag = true;
                }
                List grid = bankGuarRemort.verifyGridCase(String.valueOf(GuaranteeNo));
                if (!grid.isEmpty()) {
                    gridModifySize = grid.size();
                    for (int i = 0; i < grid.size(); i++) {
                        TdLienMarkingGrid bk = new TdLienMarkingGrid();
                        Vector ele = (Vector) grid.get(i);
                        bk.setAcNo(ele.get(0).toString());
                        marksSecuritiesList = new ArrayList<SelectItem>();
                        marksSecuritiesList.add(new SelectItem("1", "Term Deposite"));
                        marksSecuritiesList.add(new SelectItem("2", "Other"));
                        if (ele.get(6).toString() == null || ele.get(6).toString().equalsIgnoreCase("")) {
                            bk.setLienAcNo(" ");
                            setMarkSecurities("2");
                        } else {
                            bk.setLienAcNo(ele.get(6).toString());
                            setMarkSecurities("1");
                        }
                        bk.setVoucherNo(ele.get(8).toString());
                        bk.setSno(Integer.parseInt(ele.get(1).toString()));
                        bk.setReciept(ele.get(1).toString());
                        bk.setPrintAmt(String.valueOf(formatter.format(new BigDecimal(ele.get(9).toString()).doubleValue())));
//                        bk.setPrintAmt(ele.get(9).toString());
                        bk.setLienValue(String.valueOf(formatter.format(new BigDecimal(ele.get(2).toString()).doubleValue())));
                        bk.setFddt(dmy.format(ymmd.parse(ele.get(3).toString())));
                        bk.setMatDt(dmy.format(ymmd.parse(ele.get(4).toString())));
                        bk.setLien(ele.get(5).toString());
                        bk.setStatus(ele.get(5).toString());
                        bk.setDetails(ele.get(7).toString());
                        String newStatus = "";
                        if (ele.get(10).toString().equalsIgnoreCase("E")) {
                            newStatus = "EXPIRED";
                        } else if (ele.get(10).toString().equalsIgnoreCase("A")) {
                            newStatus = "ACTIVE";
                        }
                        bk.setGuarSecStatus(newStatus);
                        currentItem.add(bk);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
        }
    }

    public void pattern() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\[0-9]+)?)+");
            if (this.pinCode == null || this.pinCode.equalsIgnoreCase("")) {
                message = "Please fill Postal Code";
                return;
            }
            if (this.pinCode.length() < 6) {
                message = "Please fill 6 Digit Postal Code";
                return;
            }

            Matcher pinCheck = p.matcher(this.pinCode);
            if (!pinCheck.matches()) {
                message = "Invalid Postal Code";
                return;
            }
//            guaranteeIssuedOnAction();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void guaranteeIssuedOnAction() {
        try {
            if (!((function.equalsIgnoreCase("VR") || function.equalsIgnoreCase("M"))
                    && (action.equalsIgnoreCase("V") || action.equalsIgnoreCase("C")))) {
                List resultList3 = CommonReportMethodsBean.getRefRecList("420");
                if (!resultList3.isEmpty()) {
                    guaranteeIssuedByList = new ArrayList<SelectItem>();
                    guaranteeIssuedByList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < resultList3.size(); i++) {
                        Vector ele1 = (Vector) resultList3.get(i);
                        guaranteeIssuedByList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                        guaranteeIssuedBy = ele1.get(0).toString();
                    }
                }
            }
//            guaranteeIssuedOnList.add(new SelectItem("--Select--"));
//            guaranteeIssuedOnList.add(new SelectItem("BK", "Own"));
//            guaranteeIssuedOnList.add(new SelectItem("BB", "Bank Of Baroda"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void classificationAction() {
        try {

            List resultList2 = CommonReportMethodsBean.getRefRecList("418");
            if (!resultList2.isEmpty()) {
                classificationList = new ArrayList<SelectItem>();
                classificationList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele1 = (Vector) resultList2.get(i);
                    classificationList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                    classificationCode = ele1.get(0).toString();
                }
            }
//            classificationList = new ArrayList<SelectItem>();
//            classificationList.add(new SelectItem("Finance", "Finance"));
//            classificationList.add(new SelectItem("Performance", "Performance"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void validityInAction() {
        try {
            setClassification(classification);
            validityInList = new ArrayList<SelectItem>();
            validityInList.add(new SelectItem("D", "Day"));
            validityInList.add(new SelectItem("M", "Month"));
            validityInList.add(new SelectItem("Y", "Year"));
            modesList();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void purposeInAction() {
        try {
            List resultList2 = aitr.getListAsPerRequirement("419", getClassification(), "0", "0", "0", "0", ymd.format(new Date()), 0);
            purposeList = new ArrayList<SelectItem>();
            purposeList.add(new SelectItem("--Select--"));
            if (!resultList2.isEmpty()) {
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele1 = (Vector) resultList2.get(i);
                    purposeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                    purposeCode = ele1.get(0).toString();
                }
            }

//            purposeList = new ArrayList<SelectItem>();
//            purposeList.add(new SelectItem("SALES TAX DUE"));
//            purposeList.add(new SelectItem("OTHERS"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    /**
     * THIS CODE IS FOR SECURITY CODE TAB *
     */
    //Security detail
    private String acno1;
    private String nameSec = "";
    private String estimationDateLbl;
    private String revalutionDateLbl;
    private String ValuationAmtLbl;
    private Date estimationDate = new Date();
    private String securityNature;
    private String securityType;
    private Date revalutionDate = new Date();
    String custId, custId1, custId2, custId3, custId4;
    private String securityDesc1;
    private String securityDesc2;
    private String securityDesc3;
    private String particular;
    private String valuationAmt;
    private String lienValue;
    private String margin;
    private String remark;
    private String status1;
    private String otherAc;
    private int currentRowSD;
    private String securityflag;
    private String markSecurities = "0";
    private boolean disableSecDec3;
    private boolean saveDisable;
    private boolean updateDisable1;
    private String flagSecurity;
    private String groupCode;
    private String typeOfSecurity;
    private String secODRoi;
    private String secODScheme;
    private String groupType;
    private String groupID;
    AccountOpeningFacadeRemote openingFacadeRemote;
    private List<SelectItem> statusList;
    private List<SelectItem> typeOfSecurityList;
    private List<SelectItem> securityNatureList;
    private List<SelectItem> securityDesc1List;
    private List<SelectItem> securityDesc2List;
    private List<SelectItem> securityDesc3List;
    private List<SelectItem> marksSecuritiesList;
    private List<SelectItem> securitiesTypeList;
    String minorCommunity;
    String remarks1;
    String relName;
    String relOwner;
    String createdByUId;
    String creationDate;
    String lastUpdateUId;
    String lastUpdationDate;
    String totalModifications;
    //    String acNo;
//    String acOpenDt;
//    String partyName;
    // String disbursement;
    String netWorth1;
    String industry;
    String marginMoney;
    Date docDate;
    Date renewalDate;
    Date docExprDate;
    String loanDuration;
    String relation;
    String sancAmount;
    boolean disableSancAmt;
    String drawingPwrInd;
    String monthlyIncome1;
    String applicantCategory;
    String categoryOpt;
    String sector;
    String subSector;
    String purposeOfAdvance;
    String modeOfAdvance;
    String typeOfAdvance;
    String secured;
    String guarnteeCover;
    String industryType;
    String sickness;
    String exposure;
    String exposureCategory;
    String exposureCategoryPurpose;
    String restructuring;
    String sanctionLevel;
    String sanctionAuth;
    String intTable;
    String schemeCode;
    String intType;
    String npaClass;
    String courts;
    String modeOfSettle;
    String debtWaiverReason;
    String assetClassReason;
    String popuGroup;
    List<SelectItem> sectorOption;
    List<SelectItem> subSectorOption;
    List<SelectItem> purposeOfAdvanceOption;
    List<SelectItem> securedOption;
    List<SelectItem> guarnteeCoverOption;
    List<SelectItem> industryTypeOption;
    List<SelectItem> sicknessOption;
    List<SelectItem> exposureOption;
    List<SelectItem> exposureCategroyOption;
    List<SelectItem> exposureCatePurposeOption;
    List<SelectItem> restructuringOption;
    List<SelectItem> sanctionLevelOption;
    List<SelectItem> sanctionAuthOption;
    List<SelectItem> intTableOption;
    List<SelectItem> intTypeOption;
    List<SelectItem> npaClassOption;
    List<SelectItem> courtsOption;
    List<SelectItem> modeOfSettleOption;
    List<SelectItem> debtWaiverReasonOption;
    List<SelectItem> assetClassReasonOption;
    List<SelectItem> popuGroupOption;
    List<SelectItem> relationOption;
    List<SelectItem> drawingPwrIndOption;
    List<SelectItem> applicantCategoryOption;
    List<SelectItem> categoryOptOption;
    List<SelectItem> minorCommunityOption;
    List<SelectItem> relOwnerOption;
    List<SelectItem> relNameOption;
    List<SelectItem> industryOption;
    private String additionalRoi;

    public String getCustId1() {
        return custId1;
    }

    public void setCustId1(String custId1) {
        this.custId1 = custId1;
    }

    public String getCustId2() {
        return custId2;
    }

    public void setCustId2(String custId2) {
        this.custId2 = custId2;
    }

    public String getCustId3() {
        return custId3;
    }

    public void setCustId3(String custId3) {
        this.custId3 = custId3;
    }

    public String getCustId4() {
        return custId4;
    }

    public void setCustId4(String custId4) {
        this.custId4 = custId4;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelOwner() {
        return relOwner;
    }

    public void setRelOwner(String relOwner) {
        this.relOwner = relOwner;
    }

    public String getMinorCommunity() {
        return minorCommunity;
    }

    public void setMinorCommunity(String minorCommunity) {
        this.minorCommunity = minorCommunity;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCreatedByUId() {
        return createdByUId;
    }

    public void setCreatedByUId(String createdByUId) {
        this.createdByUId = createdByUId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdateUId() {
        return lastUpdateUId;
    }

    public void setLastUpdateUId(String lastUpdateUId) {
        this.lastUpdateUId = lastUpdateUId;
    }

    public String getLastUpdationDate() {
        return lastUpdationDate;
    }

    public void setLastUpdationDate(String lastUpdationDate) {
        this.lastUpdationDate = lastUpdationDate;
    }

    public String getTotalModifications() {
        return totalModifications;
    }

    public void setTotalModifications(String totalModifications) {
        this.totalModifications = totalModifications;
    }

    public String getNetWorth1() {
        return netWorth1;
    }

    public void setNetWorth1(String netWorth1) {
        this.netWorth1 = netWorth1;
    }

    public String getMarginMoney() {
        return marginMoney;
    }

    public void setMarginMoney(String marginMoney) {
        this.marginMoney = marginMoney;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSancAmount() {
        return sancAmount;
    }

    public void setSancAmount(String sancAmount) {
        this.sancAmount = sancAmount;
    }

    public boolean isDisableSancAmt() {
        return disableSancAmt;
    }

    public void setDisableSancAmt(boolean disableSancAmt) {
        this.disableSancAmt = disableSancAmt;
    }

    public String getDrawingPwrInd() {
        return drawingPwrInd;
    }

    public void setDrawingPwrInd(String drawingPwrInd) {
        this.drawingPwrInd = drawingPwrInd;
    }

    public Date getDocExprDate() {
        return docExprDate;
    }

    public void setDocExprDate(Date docExprDate) {
        this.docExprDate = docExprDate;
    }

    public String getMonthlyIncome1() {
        return monthlyIncome1;
    }

    public void setMonthlyIncome1(String monthlyIncome1) {
        this.monthlyIncome1 = monthlyIncome1;
    }

    public String getApplicantCategory() {
        return applicantCategory;
    }

    public void setApplicantCategory(String applicantCategory) {
        this.applicantCategory = applicantCategory;
    }

    public String getCategoryOpt() {
        return categoryOpt;
    }

    public void setCategoryOpt(String categoryOpt) {
        this.categoryOpt = categoryOpt;
    }

    public List<SelectItem> getRelationOption() {
        return relationOption;
    }

    public void setRelationOption(List<SelectItem> relationOption) {
        this.relationOption = relationOption;
    }

    public List<SelectItem> getDrawingPwrIndOption() {
        return drawingPwrIndOption;
    }

    public void setDrawingPwrIndOption(List<SelectItem> drawingPwrIndOption) {
        this.drawingPwrIndOption = drawingPwrIndOption;
    }

    public List<SelectItem> getApplicantCategoryOption() {
        return applicantCategoryOption;
    }

    public void setApplicantCategoryOption(List<SelectItem> applicantCategoryOption) {
        this.applicantCategoryOption = applicantCategoryOption;
    }

    public List<SelectItem> getCategoryOptOption() {
        return categoryOptOption;
    }

    public void setCategoryOptOption(List<SelectItem> categoryOptOption) {
        this.categoryOptOption = categoryOptOption;
    }

    public List<SelectItem> getMinorCommunityOption() {
        return minorCommunityOption;
    }

    public void setMinorCommunityOption(List<SelectItem> minorCommunityOption) {
        this.minorCommunityOption = minorCommunityOption;
    }

    public List<SelectItem> getRelOwnerOption() {
        return relOwnerOption;
    }

    public void setRelOwnerOption(List<SelectItem> relOwnerOption) {
        this.relOwnerOption = relOwnerOption;
    }

    public List<SelectItem> getRelNameOption() {
        return relNameOption;
    }

    public void setRelNameOption(List<SelectItem> relNameOption) {
        this.relNameOption = relNameOption;
    }

    public List<SelectItem> getIndustryOption() {
        return industryOption;
    }

    public void setIndustryOption(List<SelectItem> industryOption) {
        this.industryOption = industryOption;
    }

    public String getIntType() {
        return intType;
    }

    public void setIntType(String intType) {
        this.intType = intType;
    }

    public String getNpaClass() {
        return npaClass;
    }

    public void setNpaClass(String npaClass) {
        this.npaClass = npaClass;
    }

    public String getCourts() {
        return courts;
    }

    public void setCourts(String courts) {
        this.courts = courts;
    }

    public String getModeOfSettle() {
        return modeOfSettle;
    }

    public void setModeOfSettle(String modeOfSettle) {
        this.modeOfSettle = modeOfSettle;
    }

    public String getDebtWaiverReason() {
        return debtWaiverReason;
    }

    public void setDebtWaiverReason(String debtWaiverReason) {
        this.debtWaiverReason = debtWaiverReason;
    }

    public String getAssetClassReason() {
        return assetClassReason;
    }

    public void setAssetClassReason(String assetClassReason) {
        this.assetClassReason = assetClassReason;
    }

    public String getPopuGroup() {
        return popuGroup;
    }

    public void setPopuGroup(String popuGroup) {
        this.popuGroup = popuGroup;
    }

    public String getModeOfAdvance() {
        return modeOfAdvance;
    }

    public void setModeOfAdvance(String modeOfAdvance) {
        this.modeOfAdvance = modeOfAdvance;
    }

    public String getIntTable() {
        return intTable;
    }

    public void setIntTable(String intTable) {
        this.intTable = intTable;
    }

    public List<SelectItem> getSchemeCodeOption() {
        return schemeCodeOption;
    }

    public void setSchemeCodeOption(List<SelectItem> schemeCodeOption) {
        this.schemeCodeOption = schemeCodeOption;
    }

    public List<SelectItem> getIntTableOption() {
        return intTableOption;
    }

    public void setIntTableOption(List<SelectItem> intTableOption) {
        this.intTableOption = intTableOption;
    }

    public List<SelectItem> getIntTypeOption() {
        return intTypeOption;
    }

    public void setIntTypeOption(List<SelectItem> intTypeOption) {
        this.intTypeOption = intTypeOption;
    }

    public List<SelectItem> getNpaClassOption() {
        return npaClassOption;
    }

    public void setNpaClassOption(List<SelectItem> npaClassOption) {
        this.npaClassOption = npaClassOption;
    }

    public List<SelectItem> getCourtsOption() {
        return courtsOption;
    }

    public void setCourtsOption(List<SelectItem> courtsOption) {
        this.courtsOption = courtsOption;
    }

    public List<SelectItem> getModeOfSettleOption() {
        return modeOfSettleOption;
    }

    public void setModeOfSettleOption(List<SelectItem> modeOfSettleOption) {
        this.modeOfSettleOption = modeOfSettleOption;
    }

    public List<SelectItem> getDebtWaiverReasonOption() {
        return debtWaiverReasonOption;
    }

    public void setDebtWaiverReasonOption(List<SelectItem> debtWaiverReasonOption) {
        this.debtWaiverReasonOption = debtWaiverReasonOption;
    }

    public List<SelectItem> getAssetClassReasonOption() {
        return assetClassReasonOption;
    }

    public void setAssetClassReasonOption(List<SelectItem> assetClassReasonOption) {
        this.assetClassReasonOption = assetClassReasonOption;
    }

    public List<SelectItem> getPopuGroupOption() {
        return popuGroupOption;
    }

    public void setPopuGroupOption(List<SelectItem> popuGroupOption) {
        this.popuGroupOption = popuGroupOption;
    }

    public List<SelectItem> getSecuredOption() {
        return securedOption;
    }

    public void setSecuredOption(List<SelectItem> securedOption) {
        this.securedOption = securedOption;
    }

    public List<SelectItem> getSicknessOption() {
        return sicknessOption;
    }

    public void setSicknessOption(List<SelectItem> sicknessOption) {
        this.sicknessOption = sicknessOption;
    }

    public List<SelectItem> getRestructuringOption() {
        return restructuringOption;
    }

    public void setRestructuringOption(List<SelectItem> restructuringOption) {
        this.restructuringOption = restructuringOption;
    }

    public List<SelectItem> getSanctionLevelOption() {
        return sanctionLevelOption;
    }

    public void setSanctionLevelOption(List<SelectItem> sanctionLevelOption) {
        this.sanctionLevelOption = sanctionLevelOption;
    }

    public List<SelectItem> getSanctionAuthOption() {
        return sanctionAuthOption;
    }

    public void setSanctionAuthOption(List<SelectItem> sanctionAuthOption) {
        this.sanctionAuthOption = sanctionAuthOption;
    }

    public String getExposureCategoryPurpose() {
        return exposureCategoryPurpose;
    }

    public void setExposureCategoryPurpose(String exposureCategoryPurpose) {
        this.exposureCategoryPurpose = exposureCategoryPurpose;
    }

    public List<SelectItem> getGuarnteeCoverOption() {
        return guarnteeCoverOption;
    }

    public void setGuarnteeCoverOption(List<SelectItem> guarnteeCoverOption) {
        this.guarnteeCoverOption = guarnteeCoverOption;
    }

    public List<SelectItem> getIndustryTypeOption() {
        return industryTypeOption;
    }

    public void setIndustryTypeOption(List<SelectItem> industryTypeOption) {
        this.industryTypeOption = industryTypeOption;
    }

    public List<SelectItem> getExposureOption() {
        return exposureOption;
    }

    public void setExposureOption(List<SelectItem> exposureOption) {
        this.exposureOption = exposureOption;
    }

    public List<SelectItem> getExposureCategroyOption() {
        return exposureCategroyOption;
    }

    public void setExposureCategroyOption(List<SelectItem> exposureCategroyOption) {
        this.exposureCategroyOption = exposureCategroyOption;
    }

    public List<SelectItem> getExposureCatePurposeOption() {
        return exposureCatePurposeOption;
    }

    public void setExposureCatePurposeOption(List<SelectItem> exposureCatePurposeOption) {
        this.exposureCatePurposeOption = exposureCatePurposeOption;
    }

    public String getTypeOfAdvance() {
        return typeOfAdvance;
    }

    public void setTypeOfAdvance(String typeOfAdvance) {
        this.typeOfAdvance = typeOfAdvance;
    }

    public String getSecured() {
        return secured;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }

    public String getGuarnteeCover() {
        return guarnteeCover;
    }

    public void setGuarnteeCover(String guarnteeCover) {
        this.guarnteeCover = guarnteeCover;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getExposureCategory() {
        return exposureCategory;
    }

    public void setExposureCategory(String exposureCategory) {
        this.exposureCategory = exposureCategory;
    }

    public String getRestructuring() {
        return restructuring;
    }

    public void setRestructuring(String restructuring) {
        this.restructuring = restructuring;
    }

    public String getSanctionLevel() {
        return sanctionLevel;
    }

    public void setSanctionLevel(String sanctionLevel) {
        this.sanctionLevel = sanctionLevel;
    }

    public String getSanctionAuth() {
        return sanctionAuth;
    }

    public void setSanctionAuth(String sanctionAuth) {
        this.sanctionAuth = sanctionAuth;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getSubSector() {
        return subSector;
    }

    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<SelectItem> getSectorOption() {
        return sectorOption;
    }

    public void setSectorOption(List<SelectItem> sectorOption) {
        this.sectorOption = sectorOption;
    }

    public List<SelectItem> getSubSectorOption() {
        return subSectorOption;
    }

    public void setSubSectorOption(List<SelectItem> subSectorOption) {
        this.subSectorOption = subSectorOption;
    }

    public String getSecODRoi() {
        return secODRoi;
    }

    public void setSecODRoi(String secODRoi) {
        this.secODRoi = secODRoi;
    }

    public String getSecODScheme() {
        return secODScheme;
    }

    public void setSecODScheme(String secODScheme) {
        this.secODScheme = secODScheme;
    }

    public List<SelectItem> getPurposeOfAdvanceOption() {
        return purposeOfAdvanceOption;
    }

    public void setPurposeOfAdvanceOption(List<SelectItem> purposeOfAdvanceOption) {
        this.purposeOfAdvanceOption = purposeOfAdvanceOption;
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

    public String getAcno1() {
        return acno1;
    }

    public void setAcno1(String acno1) {
        this.acno1 = acno1;
    }

    public String getNameSec() {
        return nameSec;
    }

    public void setNameSec(String nameSec) {
        this.nameSec = nameSec;
    }

    public String getEstimationDateLbl() {
        return estimationDateLbl;
    }

    public void setEstimationDateLbl(String estimationDateLbl) {
        this.estimationDateLbl = estimationDateLbl;
    }

    public String getRevalutionDateLbl() {
        return revalutionDateLbl;
    }

    public void setRevalutionDateLbl(String revalutionDateLbl) {
        this.revalutionDateLbl = revalutionDateLbl;
    }

    public String getValuationAmtLbl() {
        return ValuationAmtLbl;
    }

    public void setValuationAmtLbl(String ValuationAmtLbl) {
        this.ValuationAmtLbl = ValuationAmtLbl;
    }

    public Date getEstimationDate() {
        return estimationDate;
    }

    public void setEstimationDate(Date estimationDate) {
        this.estimationDate = estimationDate;
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

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getValuationAmt() {
        return valuationAmt;
    }

    public void setValuationAmt(String valuationAmt) {
        this.valuationAmt = valuationAmt;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getOtherAc() {
        return otherAc;
    }

    public void setOtherAc(String otherAc) {
        this.otherAc = otherAc;
    }

    public String getSecurityflag() {
        return securityflag;
    }

    public void setSecurityflag(String securityflag) {
        this.securityflag = securityflag;
    }

    public String getMarkSecurities() {
        return markSecurities;
    }

    public void setMarkSecurities(String markSecurities) {
        this.markSecurities = markSecurities;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isUpdateDisable1() {
        return updateDisable1;
    }

    public void setUpdateDisable1(boolean updateDisable1) {
        this.updateDisable1 = updateDisable1;
    }

    public String getFlagSecurity() {
        return flagSecurity;
    }

    public void setFlagSecurity(String flagSecurity) {
        this.flagSecurity = flagSecurity;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getTypeOfSecurity() {
        return typeOfSecurity;
    }

    public void setTypeOfSecurity(String typeOfSecurity) {
        this.typeOfSecurity = typeOfSecurity;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public List<SelectItem> getTypeOfSecurityList() {
        return typeOfSecurityList;
    }

    public void setTypeOfSecurityList(List<SelectItem> typeOfSecurityList) {
        this.typeOfSecurityList = typeOfSecurityList;
    }

    public List<SelectItem> getSecurityNatureList() {
        return securityNatureList;
    }

    public void setSecurityNatureList(List<SelectItem> securityNatureList) {
        this.securityNatureList = securityNatureList;
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

    public List<SelectItem> getMarksSecuritiesList() {
        return marksSecuritiesList;
    }

    public void setMarksSecuritiesList(List<SelectItem> marksSecuritiesList) {
        this.marksSecuritiesList = marksSecuritiesList;
    }

    public List<SelectItem> getSecuritiesTypeList() {
        return securitiesTypeList;
    }

    public void setSecuritiesTypeList(List<SelectItem> securitiesTypeList) {
        this.securitiesTypeList = securitiesTypeList;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public TdLienMarkingGrid getCurrentItemSD() {
        return currentItemSD;
    }

    public void setCurrentItemSD(TdLienMarkingGrid currentItemSD) {
        this.currentItemSD = currentItemSD;
    }
    AccountStatusSecurityFacadeRemote testEJBSEC;
    private TdLienMarkingGrid currentItemSD = new TdLienMarkingGrid();

    public void resetPage() throws ParseException {
        setMessage("");
        resetValues();
        refreshOnInvalidAccNo();
        setMarkSecurities("");
        currentItem.clear();
    }

    public void getTableValues() {
        try {
            List resultList = new ArrayList();
            resultList = testEJBSEC.getStatus();
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", " "));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                statusList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
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
            marksSecuritiesList.add(new SelectItem("1", "Term Deposite"));
            marksSecuritiesList.add(new SelectItem("2", "Other"));

//        } catch (ApplicationException e) {
//            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void changeLabel() {
        if (setSecurityDescription1().equalsIgnoreCase("false")) {
            return;
        }
        if (securityType.equalsIgnoreCase("DATED")) {
            setEstimationDateLbl("Issue Date");
            setValuationAmtLbl("Principle Amt");
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

    public String setSecurityDescription1() {
        setMessageLM("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessageLM("Please Select the Security Type.");
            return "false";
        }
        try {
            List resultList = new ArrayList();

            resultList = openingFacadeRemote.getSecurityDesc1(securityType);
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

//        } catch (ApplicationException e) {
//            message = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
        return "true";
    }

    public String setSecurityDescription2() {
        setMessageLM("");
//        fflag = false;
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessageLM("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageLM("Please Select the securityDesc1.");
            return "false";
        }
        try {
            List resultList = new ArrayList();

            resultList = openingFacadeRemote.getSecurityDesc2(securityType, securityDesc1);
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
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
        return "true";
    }

    public String setSecurityDescription3() {
        setMessageLM("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessageLM("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageLM("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0") || securityDesc2.equalsIgnoreCase("")) {
            setMessageLM("Please Select the securityDesc2.");
            return "false";
        }
        try {
            List resultList = new ArrayList();

            resultList = openingFacadeRemote.getSecurityDesc3(securityType, securityDesc1, securityDesc2);
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

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
        return "true";
    }

    public void setSecurityCode() {
        if (!(remark == null || remark.equals(""))) {
            remark = remark + ":A/C:" + otherAc;
        }
        try {
            groupCode = testEJBSEC.SecurityCode(securityType, securityDesc2, securityDesc3);
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
    }

    public void viewData() {
        String code = this.acno;
//        int length = code.length();
//        int addedZero = 6 - length;
//        for (int i = 1; i <= addedZero; i++) {
//            code = "0" + code;
//        }
//        acNo = this.orgnBrCode + this.acctType + code + this.agentCode;
        try {
            List selectResultFromLoanBorrowerTable = aitr.selectCustDetails(this.acno);
            if (selectResultFromLoanBorrowerTable.isEmpty()) {
                if (!functionFlag.equalsIgnoreCase("A")) {
                    setMessageLM("Invalid account number.");
                    setMessage("");
                    refreshOnInvalidAccNo();
                }
            } else {
                for (int i = 0; i < selectResultFromLoanBorrowerTable.size(); i++) {
                    Vector vecForLoanBorrowerDtl = (Vector) selectResultFromLoanBorrowerTable.get(i);
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    setSector(vecForLoanBorrowerDtl.get(0).toString());
                    List subSecCodeDet = aitr.getSubSector(vecForLoanBorrowerDtl.get(0).toString());
                    subSectorOption = new ArrayList<SelectItem>();
                    subSectorOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < subSecCodeDet.size(); k++) {
                        Vector element = (Vector) subSecCodeDet.get(k);
                        code = element.get(0).toString();
                        subSectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setSubSector(vecForLoanBorrowerDtl.get(1).toString());
                    List purOfAdvOptDet = aitr.getListAsPerRequirement("184", vecForLoanBorrowerDtl.get(0).toString(), vecForLoanBorrowerDtl.get(1).toString(), vecForLoanBorrowerDtl.get(3).toString(), "0", "0", ymd.format(new Date()), Float.parseFloat(getSancAmount()));
                    purposeOfAdvanceOption = new ArrayList<SelectItem>();
                    purposeOfAdvanceOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < purOfAdvOptDet.size(); k++) {
                        Vector element = (Vector) purOfAdvOptDet.get(k);
                        code = element.get(0).toString();
                        purposeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setPurposeOfAdvance(vecForLoanBorrowerDtl.get(8).toString());
                    setModeOfAdvance(vecForLoanBorrowerDtl.get(3).toString());
                    setTypeOfAdvance(vecForLoanBorrowerDtl.get(4).toString());
                    setSecured(vecForLoanBorrowerDtl.get(5).toString());
                    List guarnteeCoverOptDet = aitr.getListAsPerRequirement("188", vecForLoanBorrowerDtl.get(0).toString(), vecForLoanBorrowerDtl.get(1).toString(), vecForLoanBorrowerDtl.get(3).toString(), "0", "0", ymd.format(new Date()), 0);
                    guarnteeCoverOption = new ArrayList<SelectItem>();
                    guarnteeCoverOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < guarnteeCoverOptDet.size(); k++) {
                        Vector element = (Vector) guarnteeCoverOptDet.get(k);
                        code = element.get(0).toString();
                        guarnteeCoverOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setGuarnteeCover(vecForLoanBorrowerDtl.get(6).toString());
                    setIndustryType(vecForLoanBorrowerDtl.get(7).toString());
                    setSickness(vecForLoanBorrowerDtl.get(2).toString());
                    setExposure(vecForLoanBorrowerDtl.get(9).toString());
                    setRestructuring(vecForLoanBorrowerDtl.get(10).toString());
                    setSanctionLevel(vecForLoanBorrowerDtl.get(11).toString());
                    setSanctionAuth(vecForLoanBorrowerDtl.get(12).toString());
                    List schemeCodeDet = aitr.schemeCodeDet(vecForLoanBorrowerDtl.get(15).toString());
                    schemeCodeOption = new ArrayList<SelectItem>();
                    schemeCodeOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < schemeCodeDet.size(); k++) {
                        Vector element = (Vector) schemeCodeDet.get(k);
                        code = element.get(0).toString();
                        schemeCodeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));

                    }
                    setSchemeCode(vecForLoanBorrowerDtl.get(15).toString());
                    List listForIntTable = new ArrayList();
                    listForIntTable = aitr.interestTableCode(code);
                    intTableOption = new ArrayList<SelectItem>();
                    intTableOption.add(new SelectItem("0", ""));
                    for (int m = 0; m < listForIntTable.size(); m++) {
                        Vector element = (Vector) listForIntTable.get(m);
                        intTableOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setIntTable(vecForLoanBorrowerDtl.get(13).toString());
                    setIntType(vecForLoanBorrowerDtl.get(14).toString());
                    setNpaClass(vecForLoanBorrowerDtl.get(16).toString());
                    setCourts(vecForLoanBorrowerDtl.get(17).toString());
                    setModeOfSettle(vecForLoanBorrowerDtl.get(18).toString());
                    setDebtWaiverReason(vecForLoanBorrowerDtl.get(19).toString());
                    setAssetClassReason(vecForLoanBorrowerDtl.get(20).toString());
                    setPopuGroup(vecForLoanBorrowerDtl.get(21).toString());
                    setCreatedByUId(vecForLoanBorrowerDtl.get(22).toString());
                    String crtionDate = vecForLoanBorrowerDtl.get(23).toString();
                    crtionDate = crtionDate.substring(8, 10) + "/" + crtionDate.substring(5, 7) + "/" + crtionDate.substring(0, 4);
                    setCreationDate(crtionDate);
                    setLastUpdateUId(vecForLoanBorrowerDtl.get(24).toString());
                    String lastUpdateDate = vecForLoanBorrowerDtl.get(25).toString();
                    if (!lastUpdateDate.equalsIgnoreCase("")) {
                        lastUpdateDate = lastUpdateDate.substring(8, 10) + "/" + lastUpdateDate.substring(5, 7) + "/" + lastUpdateDate.substring(0, 4);
                    }
                    if (lastUpdateDate.equals("01/01/1900")) {
                        setLastUpdationDate("");
                    } else {
                        setLastUpdationDate(lastUpdateDate);
                    }
                    setTotalModifications(vecForLoanBorrowerDtl.get(26).toString());
                    setNetWorth1(new BigDecimal(Double.parseDouble(vecForLoanBorrowerDtl.get(27).toString())).toString());
                    setMarginMoney(new BigDecimal(Double.parseDouble(vecForLoanBorrowerDtl.get(28).toString())).toString());
                    String docDt = vecForLoanBorrowerDtl.get(29).toString();
                    docDt = docDt.substring(8, 10) + "/" + docDt.substring(5, 7) + "/" + docDt.substring(0, 4);
                    setDocDate(df.parse(docDt));
                    String renewDt = vecForLoanBorrowerDtl.get(30).toString();
                    renewDt = renewDt.substring(8, 10) + "/" + renewDt.substring(5, 7) + "/" + renewDt.substring(0, 4);
                    setRenewalDate(df.parse(renewDt));
                    setLoanDuration(vecForLoanBorrowerDtl.get(31).toString());
                    String expDate = vecForLoanBorrowerDtl.get(32).toString();
                    expDate = expDate.substring(8, 10) + "/" + expDate.substring(5, 7) + "/" + expDate.substring(0, 4);
                    setDocExprDate(df.parse(expDate));
                    setRelation(vecForLoanBorrowerDtl.get(33).toString());
                    setSancAmount(new BigDecimal(Double.parseDouble(vecForLoanBorrowerDtl.get(34).toString())).toString());
                    setDrawingPwrInd(vecForLoanBorrowerDtl.get(35).toString());
                    setMonthlyIncome1(new BigDecimal(Double.parseDouble(vecForLoanBorrowerDtl.get(36).toString())).toString());
                    setApplicantCategory(vecForLoanBorrowerDtl.get(37).toString());
//                    List categoryOptDet = aitr.getReferenceCode("230");
                    List categoryOptDet = aitr.getListAsPerRequirement("209", vecForLoanBorrowerDtl.get(37).toString(), "0", "0", "0", "0", ymd.format(new Date()), 0);
                    categoryOptOption = new ArrayList<SelectItem>();
                    categoryOptOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < categoryOptDet.size(); k++) {
                        Vector element = (Vector) categoryOptDet.get(k);
                        code = element.get(0).toString();
                        categoryOptOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setCategoryOpt(vecForLoanBorrowerDtl.get(38).toString());
//                    List minorCommunityDet = aitr.getReferenceCode("231");
                    List minorCommunityDet = aitr.getListAsPerRequirement("204", vecForLoanBorrowerDtl.get(37).toString(), vecForLoanBorrowerDtl.get(38).toString(), "0", "0", "0", ymd.format(new Date()), 0);
                    minorCommunityOption = new ArrayList<SelectItem>();
                    minorCommunityOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < minorCommunityDet.size(); k++) {
                        Vector element = (Vector) minorCommunityDet.get(k);
                        code = element.get(0).toString();
                        minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setMinorCommunity(vecForLoanBorrowerDtl.get(39).toString());
                    setRemarks1(vecForLoanBorrowerDtl.get(40).toString());
                    setRelName(vecForLoanBorrowerDtl.get(41).toString());
//                    List relOwnerDet = aitr.getReferenceCode("004");
                    List relOwnerDet = aitr.getListAsPerRequirement("004", vecForLoanBorrowerDtl.get(33).toString(), "0", "0", "0", "0", ymd.format(new Date()), 0);
                    relOwnerOption = new ArrayList<SelectItem>();
                    relOwnerOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < relOwnerDet.size(); k++) {
                        Vector element = (Vector) relOwnerDet.get(k);
                        code = element.get(0).toString();
                        relOwnerOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setRelOwner(vecForLoanBorrowerDtl.get(42).toString());
//                    List expCateCodeDet = aitr.exposureCategory();
                    List expCateCodeDet = aitr.getListAsPerRequirement("230", vecForLoanBorrowerDtl.get(9).toString(), "0", "0", "0", "0", ymd.format(new Date()), 0);
                    exposureCategroyOption = new ArrayList<SelectItem>();
                    subSectorOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < expCateCodeDet.size(); k++) {
                        Vector element = (Vector) expCateCodeDet.get(k);
                        code = element.get(0).toString();
                        exposureCategroyOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setExposureCategory(vecForLoanBorrowerDtl.get(43).toString());
//                    List expCatePurCodeDet = aitr.exposureCategoryPurpose();
                    List expCatePurCodeDet = aitr.getListAsPerRequirement("231", vecForLoanBorrowerDtl.get(9).toString(), vecForLoanBorrowerDtl.get(43).toString(), "0", "0", "0", ymd.format(new Date()), 0);
                    exposureCatePurposeOption = new ArrayList<SelectItem>();
                    exposureCatePurposeOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < expCatePurCodeDet.size(); k++) {
                        Vector element = (Vector) expCatePurCodeDet.get(k);
                        code = element.get(0).toString();
                        exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setExposureCategoryPurpose(vecForLoanBorrowerDtl.get(44).toString());
                    List industryWise = aitr.getListAsPerRequirement("445", vecForLoanBorrowerDtl.get(45).toString(), "0", "0", "0", "0", ymd.format(new Date()), 0);
                    industryTypeOption = new ArrayList<SelectItem>();
                    industryTypeOption.add(new SelectItem("0", ""));
                    for (int k = 0; k < industryWise.size(); k++) {
                        Vector element = (Vector) industryWise.get(k);
                        code = element.get(0).toString();
                        industryTypeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                    }
                    setIndustry(vecForLoanBorrowerDtl.get(45).toString());
                    List groupTypeList = aitr.groupIdList();
                    listGroupType = new ArrayList<SelectItem>();
                    List chkList = aitr.custIdDetail(custId);
                    if (!chkList.isEmpty()) {
                        Vector vect1 = (Vector) chkList.get(0);
                        if (vect1.get(0).toString().equalsIgnoreCase("02") || this.groupID.equalsIgnoreCase("2")) {
                            for (int t = 0; t < groupTypeList.size(); t++) {
                                Vector vect = (Vector) groupTypeList.get(t);
                                listGroupType.add(new SelectItem(vect.get(0).toString(), vect.get(0).toString()));
                            }
                        }
                    }
                    setGroupID(vecForLoanBorrowerDtl.get(46).toString());;
                    setGroupType(vecForLoanBorrowerDtl.get(47).toString());
                }
            }
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
    }

    public void resetValues() {
        setMessageLM("");
        setMessage("");
        setLienValue("");
//        setMargin("");
        setOtherAc("");
        setParticular("");
        setRemark("");
        setMargin("");
        setSecurityDesc1("");
        setSecurityDesc2("");
        setSecurityDesc3("");
        setSecurityNature("");
        setSecurityType("");
        setStatus(" ");
        setValuationAmt("");
        setSecODRoi(" ");
        setSecODScheme("");

    }

    public void refreshOnInvalidAccNo() {
        gridDetailLM = new ArrayList<TdLienMarkingGrid>();
        setMessageLM("");
        setOldAcctNoLM("");
        setCustName("");
        setJtName("");
        setOprMode("");
        setTypeOfSecurity("");
        setRecieptNo("");
        setControlNo("");
        setPrinAmount("");
        setStatusLien("");
        setLienMarkOption("");
        setDetail("");
        setRemarks1("");
    }

    public String validation() {
        String msg = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        this.savepopup = "true";
        if (securityType == null || securityType.equals("0")) {
            setMessageLM("Please Select the Security Type.");
            return "false";
        }
        if (securityNature == null || securityNature.equals("0")) {
            setMessageLM("Please Select Security Nature.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0")) {
            setMessageLM("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0")) {
            setMessageLM("Please Select the securityDesc2.");
            return "false";
        }
        if (particular == null || particular.equals("")) {
            setMessageLM("Please Enter Particulars.");
            return "false";
        }

        if (!(valuationAmt == null || valuationAmt.equals(""))) {
            if (!valuationAmt.matches("[0-9.]*")) {
                setMessageLM("Enter Numeric Value for Valuation Amount/Maturity Value.");
                return "false";
            }

            if (this.valuationAmt.contains(".")) {
                if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                    setMessageLM("Invalid Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                    return "false";
                }
            }
            if (Float.parseFloat(valuationAmt) < 0) {
                setMessageLM("Please Enter Valid  Valuation Amount/Maturity Value.");
                return "false";
            }
        }
        if (!(lienValue == null || lienValue.equals(""))) {
            if (!lienValue.matches("[0-9.]*")) {
                setMessageLM("Enter Numeric Value for Lien Value.");
                return "false";
            }
            if (this.lienValue.contains(".")) {
                if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                    setMessageLM("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                    return "false";
                }
            }
            if (Float.parseFloat(lienValue) < 0) {
                setMessageLM("Please Enter Valid  Lien Value.");
                return "false";
            }
        }
        if (!(margin != null || margin.equals(""))) {
            Matcher marginCheck = p.matcher(margin);
            if (!marginCheck.matches()) {
                setMargin("");
                setMessageLM("Enter Numeric Value for Margin.");
                return "false";
            } else {
                if (this.margin.contains(".")) {
                    if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                        setMessageLM("Invalid  Margin.Please Fill The Margin Correctly.");
                        return "false";
                    }
                }
                if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                    setMargin("");
                    setMessageLM("Please Enter Valid Margin(1 to 99.9).");
                    return "false";
                }
            }
        }

        if (securityType.equalsIgnoreCase("DATED") || securityType.equalsIgnoreCase("NON-DATED")) {
            if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
                if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
                    setMessageLM("Please Enter Valid Valuation Amount / Maturity Value.");
                    return "false";
                } else {
                    Matcher valuationAmtCheck = p.matcher(valuationAmt);
                    if (!valuationAmtCheck.matches()) {
                        setValuationAmt("");
                        setMessageLM("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (!valuationAmt.matches("[0-9.]*")) {
                        setMessageLM("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (this.valuationAmt.contains(".")) {
                        if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                            setMessageLM("Invalid  Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                            return "false";
                        }
                    }
                    if (Float.parseFloat(valuationAmt) < 0) {
                        setMessageLM("Please Enter Valid  Valuation Amount/Maturity Value.");
                        return "false";
                    }
                }
            }
        }
        if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
            if (lienValue == null || lienValue.equalsIgnoreCase("")) {
                setLienValue("");
                setMessageLM("Please Enter Valid Lien Amount.");
                return "false";
            } else {
                Matcher lienValueCheck = p.matcher(lienValue);
                if (!lienValueCheck.matches()) {
                    setLienValue("");
                    setMessageLM("Enter Numeric Value for Lien.");
                    return "false";
                }
                if (!lienValue.matches("[0-9.]*")) {
                    setMessageLM("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        setMessageLM("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) < 0) {
                    setMessageLM("Please Enter Valid  Lien Value.");
                    return "false";
                }
                if (margin == null || margin.equalsIgnoreCase("")) {
                    setMessageLM("Please Enter Margin.");
                    return "false";
                } else {
                    Matcher marginCheck = p.matcher(margin);
                    if (!marginCheck.matches()) {
                        setMargin("");
                        setMessageLM("Enter Numeric Value for Margin.");
                        return "false";
                    } else {
                        if (this.margin.contains(".")) {
                            if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                                setMessageLM("Invalid  Margin.Please Fill The Margin Correctly.");
                                return "false";
                            }
                        }

                        if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                            setMargin("");
                            setMessageLM("Please Enter Valid Margin(1 to 99.9).");
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
                setMessageLM("Please Enter Numeric Value for Valuation Amount / Maturity Value.");
                return "false";
            } else if (!lienValueCheck.matches()) {
                setMessageLM("Enter Numeric Value for Lien.");
                return "false";
            } else {
//                if (!lienValue.matches("[0-9]*")) {
//                    setMessage("Enter Numeric Value for Lien.");
//                    return "false";
//                }
                if (!lienValue.matches("[0-9.]*")) {
                    setMessageLM("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        setMessageLM("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) < 0) {
                    setMessageLM("Please Enter Valid  Lien Value.");
                    return "false";
                }
//                if (!valuationAmt.matches("[0-9]*")) {
//                    setMessage("Please Enter Numeric Value for Valuation Amount / Maturity Value.");
//                    return "false";
//                }
                if (!valuationAmt.matches("[0-9.]*")) {
                    setMessageLM("Enter Numeric Value for Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (this.valuationAmt.contains(".")) {
                    if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                        setMessageLM("Invalid  Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(valuationAmt) < 0) {
                    setMessageLM("Please Enter Valid  Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (Float.parseFloat(lienValue) > Float.parseFloat(valuationAmt)) {
                    if (securityType.equalsIgnoreCase("DATED")) {
                        setMessageLM("Maturity Value Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                    if (securityType.equalsIgnoreCase("NON-DATED")) {
                        setMessageLM("Valuation Amt. Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                }
            }
        }

        if (estimationDate.after(dt)) {
            setMessageLM(" Estimation or Issue Date cannot be greater Than Present Date.");
            return "false";
        }

        if (estimationDate.after(revalutionDate)) {
            if (securityType.equalsIgnoreCase("DATED")) {
                setMessageLM("Maturity date should be greater than Issue date.");
                return "false";
            }
            if (securityType.equalsIgnoreCase("NON-DATED")) {
                setMessageLM("Estimation Date should be greater than Revalution date.");
                return "false";
            }
        }

        if (status == null || status.equals("0")) {
            setMessageLM("Please Select the Status.");
            return "false";
        }

        return "true";
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
        try {
            viewData();
            List resultList = openingFacadeRemote.SetValueAcctToScheme(schemeCode);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                String OdAllowWithSlab = ele.get(5).toString();
                if (OdAllowWithSlab.equalsIgnoreCase("Y")) {
                    String secDesc2Code = openingFacadeRemote.getSecurityDesc2Code(securityType, securityDesc1, securityDesc2);
                    List schemeCodeList = aitr.getListAsPerRequirement("203", "203", secDesc2Code, "0", "0", "0", ymd.format(new Date()), 0);
                    if (!schemeCodeList.isEmpty()) {
                        Vector element = (Vector) schemeCodeList.get(0);
                        this.setSecODScheme(element.get(0).toString());
                        this.setSecODRoi(loanInterestCalculationBean.getROI(element.get(0).toString(), Double.parseDouble(sancAmount), ymd.format(dt)));
                        List resultList1 = openingFacadeRemote.SetValueAcctToScheme(element.get(0).toString());
                        if (!resultList.isEmpty()) {
                            Vector ele1 = (Vector) resultList1.get(0);
                            this.setMargin(ele1.get(6).toString());
                        }
                    } else {
                        this.setSecODScheme("");
                        this.setSecODRoi("0");
                        this.setMargin("");
                    }
                }
            }
        } catch (ApplicationException e) {
            messageLM = e.getMessage();
        }
        if (setSecurityDescription3().equalsIgnoreCase("false")) {
            return;
        }
        if (securityDesc2 != null) {
            if (securityDesc2.equalsIgnoreCase("PLEDGE")) {
                setDisableSecDec3(false);
                securityflag = "true";
            } else if (securityDesc2.equalsIgnoreCase("merchandise")) {
                setDisableSecDec3(false);
                securityflag = "true";
            }

        }
    }

    public void setRemarksSec() {
        setMessage("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setMessageLM("Please Select the Security Type.");
            return;
        }

        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageLM("Please Select the securityDesc1.");
            return;
        }
        if (securityDesc1.equalsIgnoreCase("0")) {
            setRemark(securityType);
        } else {
            setRemark(securityType + ":" + securityDesc1);
        }
    }

    public void saveSecurityDetails() {
        setMessage("");
        setMessageLM("");

//        if (otherAc == null || otherAc.equalsIgnoreCase("")) {
//            return;
//        }
        this.savepopup = "true";
        if (validation().equalsIgnoreCase("False")) {
            this.savepopup = "false";
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
        if (secODRoi == null || secODRoi.equalsIgnoreCase("")) {
            secODRoi = "0";
        }

        try {
            setSecurityCode();
            TdLienMarkingGrid dt = new TdLienMarkingGrid();
//            currentItem = new ArrayList<TdLienMarkingGrid>();
            dt.setSecurityType(securityType);
            dt.setSecurityDesc1(securityDesc1);
            dt.setSecurityDesc2(securityDesc2);
            if (securityDesc3 == null) {
                dt.setSecurityDesc3("");
            } else {
                dt.setSecurityDesc3(securityDesc3);
            }
            dt.setParticulars(particular);
            List resultList = new ArrayList();
            resultList = testEJBSEC.getStatus();
            Vector ele = (Vector) resultList.get(0);
            dt.setStatus(ele.get(1).toString());
            dt.setGuarSecStatus("Active");
            dt.setLien(ele.get(1).toString());
//           String revDate = dateFormat.format(revalutionDate);
            String revDate = ymmd.format(revalutionDate);
            dt.setMatDate(revDate);
            dt.setMatDt(dmy.format(ymmd.parse(revDate)));
            dt.setSecurity(securityNature);
            dt.setOtherAc(otherAc);
            dt.setMatValue(valuationAmt);
            dt.setLienValue(lienValue);
//            String strDate = dateFormat.format(estimationDate);
            String strDate = ymmd.format(estimationDate);
            dt.setEstimationDt(strDate);
            dt.setMargin(margin);
            dt.setDetails(remark);
            dt.setFddt(dmy.format(ymmd.parse(strDate)));
            dt.setPrintAmt(valuationAmt);
            dt.setAcNo(acno);
            dt.setLienAcNo("");
            dt.setTypeOfSec(secutiType);
            currentItem.add(dt);
            resetValues();

            double lienTotalAmt = 0d;
            for (TdLienMarkingGrid dataItem : currentItem) {
                if (dataItem.getGuarSecStatus().equalsIgnoreCase("Active")) {
                    lienTotalAmt += Double.parseDouble(dataItem.getLienValue());
                }
            }

            if (lienTotalAmt < this.guaranteeAmt.doubleValue()) {
                setMessage("Please AddOn Proper Security.");
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception e) {
            messageLM = e.getMessage();
        }
    }

    public void fetchCurrentRowSEC(ActionEvent event) {

        String sNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Maturity Value"));
        currentRowSD = currentRowSD + 1;
        currentRowSD = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (TdLienMarkingGrid item : currentItem) {

            if (item.getMatValue().equals(sNo)) {
                currentItemSD = item;
                break;
            }
        }
    }
    /**
     * END OF CODE FOR SECURITY CODE TAB*
     */
    /**
     * *****************************************THIS CODE IS FOR TD LIEN
     * MARKING FORM********************************
     */
    //private List<DlAcctOpenReg> dlAcctOpen;
    Context ctxLien;
    LoanGenralFacadeRemote lienMark;
    private String messageLM;
    private String acctNoLM;
    private String custName;
    private String jtName;
    private String oprMode;
    private String lienMarkOption;
    private List<SelectItem> lienMarkOptionList;
    int currentRowLM;
    private List<TdLienMarkingGrid> gridDetailLM;
    private List<TdLienMarkingGrid> currentItem;
    private TdLienMarkingGrid currentItemLM = new TdLienMarkingGrid();
    private String recieptNo;
    private String controlNo;
    private String prinAmount;
    private String detail;
    private String statusLien;
    private String auth;
    private String oldAcctNoLM;
    private boolean flag1LM;
    private boolean flag2LM;
    public String disableretirPanel;
    public String frAcno;
    public String frRtno;
    public String ReciptNo;
    public String frControlNo;
    public String frPrintAmt;
    public String frMadeDate;
    public String frMaturityDt;
    public String frLienMark;

    public String getDisableretirPanel() {
        return disableretirPanel;
    }

    public void setDisableretirPanel(String disableretirPanel) {
        this.disableretirPanel = disableretirPanel;
    }

    public int getCurrentRowLM() {
        return currentRowLM;
    }

    public void setCurrentRowLM(int currentRowLM) {
        this.currentRowLM = currentRowLM;
    }

    public String getOldAcctNoLM() {
        return oldAcctNoLM;
    }

    public void setOldAcctNoLM(String oldAcctNoLM) {
        this.oldAcctNoLM = oldAcctNoLM;
    }

    public String getAcctNoLM() {
        return acctNoLM;
    }

    public void setAcctNoLM(String acctNoLM) {
        this.acctNoLM = acctNoLM;
    }

    public TdLienMarkingGrid getCurrentItemLM() {
        return currentItemLM;
    }

    public void setCurrentItemLM(TdLienMarkingGrid currentItemLM) {
        this.currentItemLM = currentItemLM;
    }

    public boolean isFlag1LM() {
        return flag1LM;
    }

    public void setFlag1LM(boolean flag1LM) {
        this.flag1LM = flag1LM;
    }

    public boolean isFlag2LM() {
        return flag2LM;
    }

    public void setFlag2LM(boolean flag2LM) {
        this.flag2LM = flag2LM;
    }

    public List<TdLienMarkingGrid> getGridDetailLM() {
        return gridDetailLM;
    }

    public void setGridDetailLM(List<TdLienMarkingGrid> gridDetailLM) {
        this.gridDetailLM = gridDetailLM;
    }

    public String getMessageLM() {
        return messageLM;
    }

    public void setMessageLM(String messageLM) {
        this.messageLM = messageLM;
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

    public List<TdLienMarkingGrid> getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(List<TdLienMarkingGrid> currentItem) {
        this.currentItem = currentItem;
    }

    public String getFrAcno() {
        return frAcno;
    }

    public void setFrAcno(String frAcno) {
        this.frAcno = frAcno;
    }

    public String getFrRtno() {
        return frRtno;
    }

    public void setFrRtno(String frRtno) {
        this.frRtno = frRtno;
    }

    public String getFrControlNo() {
        return frControlNo;
    }

    public void setFrControlNo(String frControlNo) {
        this.frControlNo = frControlNo;
    }

    public String getFrPrintAmt() {
        return frPrintAmt;
    }

    public void setFrPrintAmt(String frPrintAmt) {
        this.frPrintAmt = frPrintAmt;
    }

    public String getFrMadeDate() {
        return frMadeDate;
    }

    public void setFrMadeDate(String frMadeDate) {
        this.frMadeDate = frMadeDate;
    }

    public String getFrMaturityDt() {
        return frMaturityDt;
    }

    public void setFrMaturityDt(String frMaturityDt) {
        this.frMaturityDt = frMaturityDt;
    }

    public String getFrLienMark() {
        return frLienMark;
    }

    public void setFrLienMark(String frLienMark) {
        this.frLienMark = frLienMark;
    }

    public void customerDetail() {
        this.setMessageLM("");
        this.setCustName("");
        this.setOprMode("");
        this.setJtName("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.getUserName();
        this.setFlag1LM(true);
        gridDetailLM = new ArrayList<TdLienMarkingGrid>();
//        currentItem = new ArrayList<TdLienMarkingGrid>();
        try {

            if (this.oldAcctNoLM == null || this.oldAcctNoLM.equalsIgnoreCase("") || this.oldAcctNoLM.length() == 0) {
                this.setMessageLM("Please Enter Account No.");
                return;
            }
            if (!oldAcctNoLM.matches("[0-9a-zA-z]*")) {
                setMessageLM("Please Enter Valid  Account No.");
                return;
            }
            //if (oldAcctNoLM.length() != 12) {
            if (!this.oldAcctNoLM.equalsIgnoreCase("") && ((this.oldAcctNoLM.length() != 12) && (this.oldAcctNoLM.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessageLM("Please Enter Account No Carefully.");
                return;
            }
            acctNoLM = fts.getNewAccountNumber(oldAcctNoLM);
            setAcctNoLM(acctNoLM);

            String acnature = fts.getAccountNature(acctNoLM);
            if (!acnature.equalsIgnoreCase(CbsConstant.FIXED_AC) && !acnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                this.setMessageLM("Please Enter Account No. Of 'FD' Nature.");
                return;
            }
            String accno = bankGuarRemort.getAccountValidation(acctNoLM);
            if (accno.isEmpty()) {
                this.setMessageLM("Please Enter Active Account No.");
                return;
            }

            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            resultList = lienMark.customerDetail(this.acctNoLM);
            //      String acnature = fts.getAccountNature(acNo);
            //  resultList1 = lienMark.gridDetailLoad(this.acctNoLM, this.acctNoLM.substring(2, 4));
            resultList1 = lienMark.gridDetailLoad(this.acctNoLM, acnature);
            String authResult = lienMark.auth(this.getUserName(), this.orgnBrCode);
            String fdCustIdMatch = "Y";
            this.setAuth(authResult);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCustName(ele.get(0).toString());
                    this.setOprMode(ele.get(1).toString());
                    this.setJtName(ele.get(2).toString());
                    if (!custId.equalsIgnoreCase("")) {
                        if (!ele.get(3).toString().equalsIgnoreCase("") && ele.get(3).toString().equalsIgnoreCase(custId)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(4).toString().equalsIgnoreCase("") && ele.get(4).toString().equalsIgnoreCase(custId)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(5).toString().equalsIgnoreCase("") && ele.get(5).toString().equalsIgnoreCase(custId)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(6).toString().equalsIgnoreCase("") && ele.get(6).toString().equalsIgnoreCase(custId)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(7).toString().equalsIgnoreCase("") && ele.get(7).toString().equalsIgnoreCase(custId)) {
                            fdCustIdMatch = "N";
                        }
                    }
                    if (!custId1.equalsIgnoreCase("")) {
                        if (!ele.get(3).toString().equalsIgnoreCase("") && ele.get(3).toString().equalsIgnoreCase(custId1)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(4).toString().equalsIgnoreCase("") && ele.get(4).toString().equalsIgnoreCase(custId1)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(5).toString().equalsIgnoreCase("") && ele.get(5).toString().equalsIgnoreCase(custId1)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(6).toString().equalsIgnoreCase("") && ele.get(6).toString().equalsIgnoreCase(custId1)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(7).toString().equalsIgnoreCase("") && ele.get(7).toString().equalsIgnoreCase(custId1)) {
                            fdCustIdMatch = "N";
                        }
                    }
                    if (!custId2.equalsIgnoreCase("")) {
                        if (!ele.get(3).toString().equalsIgnoreCase("") && ele.get(3).toString().equalsIgnoreCase(custId2)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(4).toString().equalsIgnoreCase("") && ele.get(4).toString().equalsIgnoreCase(custId2)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(5).toString().equalsIgnoreCase("") && ele.get(5).toString().equalsIgnoreCase(custId2)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(6).toString().equalsIgnoreCase("") && ele.get(6).toString().equalsIgnoreCase(custId2)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(7).toString().equalsIgnoreCase("") && ele.get(7).toString().equalsIgnoreCase(custId2)) {
                            fdCustIdMatch = "N";
                        }
                    }
                    if (!custId3.equalsIgnoreCase("")) {
                        if (!ele.get(3).toString().equalsIgnoreCase("") && ele.get(3).toString().equalsIgnoreCase(custId3)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(4).toString().equalsIgnoreCase("") && ele.get(4).toString().equalsIgnoreCase(custId3)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(5).toString().equalsIgnoreCase("") && ele.get(5).toString().equalsIgnoreCase(custId3)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(6).toString().equalsIgnoreCase("") && ele.get(6).toString().equalsIgnoreCase(custId3)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(7).toString().equalsIgnoreCase("") && ele.get(7).toString().equalsIgnoreCase(custId3)) {
                            fdCustIdMatch = "N";
                        }
                    }
                    if (!custId4.equalsIgnoreCase("")) {
                        if (!ele.get(3).toString().equalsIgnoreCase("") && ele.get(3).toString().equalsIgnoreCase(custId4)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(4).toString().equalsIgnoreCase("") && ele.get(4).toString().equalsIgnoreCase(custId4)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(5).toString().equalsIgnoreCase("") && ele.get(5).toString().equalsIgnoreCase(custId4)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(6).toString().equalsIgnoreCase("") && ele.get(6).toString().equalsIgnoreCase(custId4)) {
                            fdCustIdMatch = "N";
                        } else if (!ele.get(7).toString().equalsIgnoreCase("") && ele.get(7).toString().equalsIgnoreCase(custId4)) {
                            fdCustIdMatch = "N";
                        }
                    }

                }
            } else {
                this.setMessageLM("Account No. Does Not Exists !!!");
                return;
            }
            List addRoiList = openingFacadeRemote.interestCodeAdditionalRoi(intTable, Double.parseDouble(sancAmount));
            if (!addRoiList.isEmpty()) {
                Vector addRoiVect = (Vector) addRoiList.get(0);
                if (fdCustIdMatch.equalsIgnoreCase("Y")) {
                    additionalRoi = addRoiVect.get(0).toString();
                } else {
                    additionalRoi = "0";
                }
            } else {
                additionalRoi = "0";
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
                    dt.setIntOpt(intTable);
                    dt.setMargineRoi(roiOnSecurity);
                    dt.setAddRoi(additionalRoi);
                    dt.setApplicableRoi(String.valueOf(Double.parseDouble(ele.get(8).toString()) + Double.parseDouble(roiOnSecurity) + Double.parseDouble(additionalRoi)));
                    dt.setStatus(ele.get(9).toString());
                    dt.setSeqNo(ele.get(10).toString());
                    if (ele.get(11).toString().equalsIgnoreCase("Y")) {
                        dt.setLien("Yes");
                    } else {
                        dt.setLien("No");
                    }
                    gridDetailLM.add(dt);
                }
            } else {
                this.setMessageLM("No Voucher Exists in This Account No.");
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
            messageLM = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
    }

    public void select() {
        this.flagRefresh = false;
        this.flagExit = false;
        setMessage("");
        this.setEntryDt(date1);
        try {
            if (this.function.equalsIgnoreCase("M")) {
//            if (!acno1.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
//                setMessage("Sorry! You can not save/update other branch's account details.");
//                return;
//            }       
                if (currentItemSD.getStatus().equalsIgnoreCase("ACTIVE")
                        || currentItemSD.getStatus().equalsIgnoreCase("MAT DATE EXPIRED")) {
                    currentItem.get(currentRowSD).setStatus("EXPIRED");
                } else {
                    setMessage("Already EXPIRED");
                    return;
                }

                String resultList;
                String resultCheck;
                String guarNo = String.valueOf(this.GuaranteeNo);

                resultList = bankGuarRemort.updateSecuritySecStatus(entryDt, guarNo, currentItemSD.getSno(), orgnBrCode);
//                resultList = bankGuarRemort.UpdateSecurityStatus(getUserName(), entryDt, guarNo, this.guaranteeAmt, currentItemSD.getSno(), orgnBrCode);
//            resultList = testEJBSEC.UpdateSecurityTable(getUserName(), entryDt, guarNo, currentItemSD.getSno());
                setMessage(resultList);
                currentItem = new ArrayList<TdLienMarkingGrid>();
                List grid = bankGuarRemort.verifyGridCase(String.valueOf(GuaranteeNo));
                if (!grid.isEmpty()) {
                    for (int i = 0; i < grid.size(); i++) {
                        TdLienMarkingGrid bk = new TdLienMarkingGrid();
                        Vector ele = (Vector) grid.get(i);
                        bk.setAcNo(ele.get(0).toString());
                        marksSecuritiesList = new ArrayList<SelectItem>();
                        marksSecuritiesList.add(new SelectItem("1", "Term Deposite"));
                        marksSecuritiesList.add(new SelectItem("2", "Other"));
                        if (ele.get(6).toString() == null || ele.get(6).toString().equalsIgnoreCase("")) {
                            bk.setLienAcNo(" ");
                            setMarkSecurities("2");
                        } else {
                            bk.setLienAcNo(ele.get(6).toString());
                            setMarkSecurities("1");
                        }
                        bk.setVoucherNo(ele.get(8).toString());
                        bk.setSno(Integer.parseInt(ele.get(1).toString()));
                        bk.setReciept(ele.get(1).toString());

                        bk.setPrintAmt(String.valueOf(formatter.format(new BigDecimal(ele.get(9).toString()).doubleValue())));
                        bk.setLienValue(String.valueOf(formatter.format(new BigDecimal(ele.get(2).toString()).doubleValue())));
                        bk.setFddt(dmy.format(ymmd.parse(ele.get(3).toString())));
                        bk.setMatDt(dmy.format(ymmd.parse(ele.get(4).toString())));
                        bk.setLien(ele.get(5).toString());
                        bk.setStatus(ele.get(5).toString());
                        bk.setDetails(ele.get(7).toString());
                        String newStatus = "";
                        if (ele.get(10).toString().equalsIgnoreCase("E")) {
                            newStatus = "EXPIRED";
                        } else if (ele.get(10).toString().equalsIgnoreCase("A")) {
                            newStatus = "ACTIVE";
                        }
                        bk.setGuarSecStatus(newStatus);
//                        bk.setGuarSecStatus(ele.get(10).toString());
                        currentItem.add(bk);
                    }
                }

                resultCheck = bankGuarRemort.verifyCheckAmt(guarNo, guaranteeAmt.doubleValue());
                if (!resultCheck.equalsIgnoreCase("true")) {
                    this.flagRefresh = true;
                    this.flagExit = true;
                    setMessage("Please AddOn Proper Security.");
                    flag = true;
                    return;
                } else {
                    this.flagRefresh = false;
                    this.flagExit = false;
                    flag = false;
                }

            } else {
                setMessage("Sorry! You can only change the status in Verification Case.");
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fillValuesofGridInFieldsLM() {
        this.setMessageLM("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setDetail("");
        this.setLienMarkOption("--Select--");
        try {
            if (currentItem != null) {
                for (int i = 0; i < currentItem.size(); i++) {
                    if (currentItem.get(i).getVoucherNo().equalsIgnoreCase(currentItemLM.getVoucherNo())) {
                        return;
                    }
                }
            }
            TdLienMarkingGrid dt = new TdLienMarkingGrid();
            dt.setAcNo(acno);
            dt.setVoucherNo(currentItemLM.getVoucherNo());
            dt.setReciept(currentItemLM.getReciept());
            dt.setSeqNo(currentItemLM.getSeqNo());
            dt.setPrintAmt(currentItemLM.getPrintAmt());
            dt.setLienValue(currentItemLM.getPrintAmt());
            dt.setFddt(currentItemLM.getTdmatDt());
            dt.setMatDt(currentItemLM.getMatDt());
            dt.setLienAcNo(currentItemLM.getAcNo());
            dt.setTypeOfSec(secutiType);
            setControlNo(currentItemLM.getSeqNo());
            setRecieptNo(currentItemLM.getReciept());
            setStatusLien(currentItemLM.getLien());
            if (currentItemLM.getLien().equalsIgnoreCase("Yes")) {
                this.setLienMarkOption("No");
                dt.setLien("Unactive");
                setMessageLM(" This Receipt Is Already Lien Marked. Please Select Another Account / Receipt.");
                return;
            } else {
                this.setLienMarkOption("Yes");
                dt.setLien("Active");
                dt.setGuarSecStatus("Active");
            }
            dt.setChkLien(lienMarkOption);
            dt.setSecurity(typeOfSecurity);


            currentItem.add(dt);
            String result = lienMark.tdLienPresentAmount(this.acctNoLM, Float.parseFloat(currentItemLM.getVoucherNo()), Float.parseFloat(currentItemLM.getPrintAmt()));
            if (result == null) {
                this.setMessageLM("PROBLEM IN GETTING PRESENT AMOUNT !!!");
                this.setFlag1LM(true);
                return;
            } else {
                int n = result.indexOf("*");
                this.setDetail(result.substring(0, n));
                this.setPrinAmount(result.substring(n + 1));
                this.setFlag1LM(true);
            }

//            double lienTotalAmt = 0d;
//            for (TdLienMarkingGrid dataItem : currentItem) {
//                if (dataItem.getLienAcNo().equalsIgnoreCase("")) {
//                    lienTotalAmt += Double.parseDouble(dataItem.getLienValue());
//                } else {
//                    lienTotalAmt += Double.parseDouble(dataItem.getPrintAmt());
//                }
//
//            }
//
//            if (lienTotalAmt < this.guaranteeAmt) {
//                setMessage("Please AddOn Proper Security.");
//                this.flag = true;
//            } else {
//                this.flag = false;
//            }

            if (this.function.equalsIgnoreCase("M") && this.action.equalsIgnoreCase("R") && (this.period <= 0)) {
                setMessage("Please Update Validity In/Peroid ,Its Mandatory.");
                this.flag = true;

            } else {
                this.flag = false;
            }


        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
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
                if (flag == null) {
                    valuesForDlForm();
                } else if (flag.equalsIgnoreCase("true")) {
                    valuesForDlForm();
                }
            }
        } catch (Exception e) {
            message = e.getMessage();
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
             * ****These are the values which are send to DL account form
             * grid******
             */
            //   System.out.println("currentItemLM.getVoucherNo():======" + currentItemLM.getVoucherNo());
            Details = currentItemLM.getAcNo() + "/" + currentItemLM.getVoucherNo() + "; ROI:" + currentItemLM.getRoi() + " ; Present Amount:" + this.prinAmount;
            //   System.out.println("Details:=====" + Details);
            details = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):" + Details;
            //String finalString = "DATED" + "," + "PRIMARY" + "," + "LIEN" + "," + currentItem.getAcNo().substring(2, 4) + "," + this.prinAmount + "," + this.prinAmount + "," + currentItem.getMatDt() + "," + currentItem.getFddt() + "," + details + "," + this.userName + "," + this.todayDate + "," + currentItem.getAcNo();
            ///////////////////    dlTableValues();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void bilLcBg() {
        try {
            if (this.lienMarkOption.equalsIgnoreCase("Yes")) {
                Details = currentItemLM.getAcNo() + "/" + currentItemLM.getVoucherNo() + " ; ROI:" + currentItemLM.getRoi() + " ; Present Amount:" + this.prinAmount;
                String finalString1 = " PRIMARY" + "," + " Lien Marking Against TD" + "," + "TD" + "," + this.prinAmount + "," + this.prinAmount + "," + currentItemLM.getMatDt() + "," + currentItemLM.getFddt() + "," + detail + "," + this.getUserName() + "," + this.date1;
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    String DLAccOpen_Lien = "";
    String BillLcBg_Lien = "";
    String loanLienCall = "True";

    public void verifyLienDetailInfo() {
        this.setFlag1(false);
        String result = "";
        try {
            this.mode = "";
            this.cityCode = "";
            this.setUpdateDt(date1);
            this.setEntryDt(date1);
            this.setAuthBy(getUserName());

            if (this.action.equalsIgnoreCase("R") || this.action.equalsIgnoreCase("I")) {
                if (this.action.equalsIgnoreCase("R")) {
                    result = bankGuarRemort.updateRenewEntry(GuaranteeNo, guaranteeAmt.doubleValue(), updateDt, entryDt, entryBy, action, currentItem, authBy, orgnBrCode, guaranteeIssuedBy, comissionAmt, acno);
                    if (result.equalsIgnoreCase("TRUE")) {
                        reFresh();
                        this.setMessage("Bank Guarantee verified successfully");
                    } else {
                        reFresh();
                        this.setMessage(result);
                    }
                } else {
                    List list = new ArrayList();
                    list = bankGuarRemort.verifyIssueCase(action, GuaranteeNo);
                    if (!list.isEmpty()) {
                        if (list.size() > 1) {
                            result = bankGuarRemort.updateRenewEntry(GuaranteeNo, guaranteeAmt.doubleValue(), updateDt, entryDt, entryBy, action, currentItem, authBy, orgnBrCode, guaranteeIssuedBy, comissionAmt, acno);
                            if (result.equalsIgnoreCase("TRUE")) {
                                reFresh();
                                this.setMessage("Bank Guarantee verified successfully");
                            } else {
                                reFresh();
                                this.setMessage(result);
                            }

                        } else {
                            result = bankGuarRemort.updateLienEntry(GuaranteeNo, guaranteeAmt.doubleValue(), comissionAmt, acno, entryDt, entryBy, action, authBy, currentItem, orgnBrCode, updateDt, guaranteeIssuedBy);
                            if (result.equalsIgnoreCase("TRUE")) {
                                reFresh();
                                this.setMessage("Bank Guarantee verified successfully");
                                return;
                            } else {
                                reFresh();
                                this.setMessage(result);
                            }

                        }
                    }
                }
            } else {
                result = bankGuarRemort.updateLienEntry(GuaranteeNo, guaranteeAmt.doubleValue(), comissionAmt, acno, entryDt, entryBy, action, authBy, currentItem, orgnBrCode, updateDt, guaranteeIssuedBy);
                if (result.equalsIgnoreCase("TRUE")) {
                    reFresh();
                    this.setMessage("Bank Guarantee verified successfully");
                    return;
                } else {
                    reFresh();
                    this.setMessage(result);
                }
            }
        } catch (Exception e) {
            message = result;
        }
    }

    public String savevalidtions() {
        if (acno == null || acno.equalsIgnoreCase("")) {
            setMessage("Please Enter Account No.");
            return "false";
        }
        if (benfiName == null || benfiName.equalsIgnoreCase("")) {
            setMessage("Please Enter Benficiary Name.");
            return "false";
        }

        if (benfiAddress == null || benfiAddress.equalsIgnoreCase("")) {
            setMessage("Please Enter Benficiary Address.");
            return "false";
        }

        if (state == null || state.equalsIgnoreCase("--Select--") || state.equalsIgnoreCase("0")) {
            setMessage("Please Select the State.");
            return "false";
        }

        if (city == null || city.equalsIgnoreCase("--Select--") || city.equalsIgnoreCase("0")) {
            setMessage("Please Select the City.");
            return "false";
        }

        if (pinCode == null || pinCode.equalsIgnoreCase("")) {
            setMessage("Please Enter Postal Code.");
            return "false";
        }

        if (guaranteeIssuedBy == null || guaranteeIssuedBy.equalsIgnoreCase("--Select--")) {
            setMessage("Please Select the Guarantee Issued By.");
            return "false";
        }

        if (classification == null || classification.equalsIgnoreCase("--Select--")) {
            setMessage("Please Select the Classification.");
            return "false";
        }

        if (purpose == null || purpose.equalsIgnoreCase("--Select--")) {
            setMessage("Please Select the Purpose.");
            return "false";
        }

        if (guaranteeAmt.doubleValue() < 0) {
            setMessage("Please Enter Valid  Guarantee Amt.");
            return "false";
        }

        if (comissionAmt < 0) {
            setMessage("Please Enter Valid Comission Amt.");
            return "false";
        }
        if (this.function.equalsIgnoreCase("M") && this.action.equalsIgnoreCase("R") && (this.period <= 0)) {
            setMessage("Please Update Validity In/Peroid ,Its Mandatory.");
            return "false";
        }
        return "true";
    }

    public void deleteDetailInfo() {
        setMessage("");
        this.setDeleteflag(false);
        try {
            String result = "";
            result = bankGuarRemort.deleteEntry(GuaranteeNo, guaranteeAmt.doubleValue(), action, currentItem, orgnBrCode, entryDt, entryBy);
            System.out.println("adfitya");
            if (result.equalsIgnoreCase("TRUE")) {
                reFresh();
                this.setMessage("Bank Guarantee Deleted successfully");
                return;
            } else {
                reFresh();
                this.setMessage(result);
            }

        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void saveDetailInfo() {
        setMessage("");
        this.setFlag(false);
        try {
            if (savevalidtions().equalsIgnoreCase("False")) {
                return;
            }

            this.setMode("");
            this.setMargin1("date1");
            this.setUpdateDt("");
            this.setGuaranteeInvokingDueDt(date1);
            this.setEntryDt(date1);
            this.setAuthBy(" ");

            this.setEntryBy(getUserName());
            if (this.function.equalsIgnoreCase("M") && (this.action.equalsIgnoreCase("I") || this.action.equalsIgnoreCase("R"))) {
                if (this.action.equalsIgnoreCase("R")) {
                    for (int i = 0; i < currentItem.size(); i++) {
                        if (dmy.parse(currentItem.get(i).getMatDt()).compareTo(ymd.parse(entryDt)) < 0 && currentItem.get(i).getGuarSecStatus().equalsIgnoreCase("ACTIVE")) {
                            reFresh();
                            this.setMessage("In case of Renew Maturity Date is less then the Current Date so, Please modify the security.");
                            return;
                        }
                    }
                } else {
                    if (this.gridModifySize == this.currentItem.size()) {
                        reFresh();
                        this.setMessage("In case of Issue, Please modify the security.");
                        return;
                    }
                }
                String result = bankGuarRemort.saveIssueEntry(GuaranteeNo, acno, action, benfiName, benfiAddress, city, state,
                        pinCode, classification, guaranteeIssuedBy, purpose, validityIn, period,
                        guaranteeAmt.doubleValue(), mode, comissionAmt, totalcomissionAmt, taxCharges,
                        guaranteeExpiryDate1, guaranteeInvokingDueDt, entryBy, authBy, entryDt,
                        updateDt, txnId, orgnBrCode, currentItem);
                if (result.contains("true")) {
                    reFresh();
                    this.setMessage("Bank Guarantee Generated Successfully :" + result.substring(result.indexOf(":")));
                    return;
                } else {
//                reFresh();
                    this.setMessage(result);
                    return;
                }


            } else {
                if (this.function.equalsIgnoreCase("M") && this.action.equalsIgnoreCase("C")) {
                    if (this.gridModifySize != this.currentItem.size()) {
                        this.setMessage("In surrender/Close Case you cannot add the new Security");
                        return;
                    }
                }
                this.setTxnId(0);
                String result = bankGuarRemort.saveLienEntry(GuaranteeNo, acno, action, benfiName, benfiAddress, city, state,
                        pinCode, classification, guaranteeIssuedBy, purpose, validityIn, period,
                        guaranteeAmt.doubleValue(), mode, comissionAmt, totalcomissionAmt, taxCharges,
                        guaranteeExpiryDate1, guaranteeInvokingDueDt, entryBy, authBy, entryDt,
                        updateDt, txnId, orgnBrCode, currentItem);

                if (result.contains("true")) {
                    reFresh();
                    this.setMessage("Bank Guarantee Generated Successfully :" + result.substring(result.indexOf(":")));
                    return;
                } else {
//                reFresh();
                    this.setMessage(result);
                    return;
                }
            }

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void saveDetailLM() {
        this.setMessageLM("");
        this.setMessage("");
        this.flag2LM = true;
        String lAcNO = acno1;
        try {

            DLAccOpen_Lien = "True";
            if (typeOfSecurity == null || typeOfSecurity.equalsIgnoreCase("")) {
                this.setMessageLM("Please Select the Type Of Security");
                return;
            }
            String tmpSecType = typeOfSecurity;
            dlAccCustIdOpen();
            if (this.lienMarkOption.equalsIgnoreCase("--Select--")) {
                this.setMessageLM("Please Select Lien Marking.");
                return;
            }

            if (this.lienMarkOption.equalsIgnoreCase("YES")) {
                List list = fts.autoPayLienStatus(currentItemLM.getAcNo(), Double.parseDouble(this.recieptNo));
                if (!list.isEmpty()) {
                    Vector autoVec = (Vector) list.get(0);
                    String autoPay = autoVec.get(1).toString();
                    if (autoPay.equalsIgnoreCase("Y")) {
                        setMessageLM("This Receipt is Marked for Auto Payment, So Can not be Lien.");
                        return;
                    }
                }
            }
            if (margin == null || margin.equalsIgnoreCase("")) {
                List odAsPerRoiList = openingFacadeRemote.SetValueAcctToScheme(schemeCode);
                if (!odAsPerRoiList.isEmpty()) {
                    Vector ele = (Vector) odAsPerRoiList.get(0);
                    String OdAllowWithSlab = ele.get(5).toString();
                    margin = ele.get(6).toString();
                } else {
                    margin = "0";
                }
            }
            if (additionalRoi == null || additionalRoi.equalsIgnoreCase("")) {
                additionalRoi = "0";
            }
            double lienTotalAmt = 0d;
            for (TdLienMarkingGrid dataItem : currentItem) {
                if (dataItem.getGuarSecStatus().equalsIgnoreCase("Active")) {
                    lienTotalAmt += Double.parseDouble(dataItem.getLienValue());
                }
            }

            if (lienTotalAmt < this.guaranteeAmt.doubleValue()) {
                setMessage("Please AddOn Proper Security.");
                this.flag = true;
            } else {
                this.flag = false;
            }

            this.setFlag1LM(true);
            this.setRecieptNo("");
            this.setControlNo("");
            this.setPrinAmount("");
            this.setStatusLien("");
            this.setPurpose("");
            this.setLienMarkOption("--Select--");
            this.setDetail("");
            refreshOnInvalidAccNo();

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            messageLM = e.getMessage();
        }
    }
//    public void resetForm() {
//        this.setMessageLM("");
//        this.setCustName("");
//        this.setAcctNoLM("");
//        this.setOldAcctNoLM("");
//        this.setJtName("");
//        this.setOprMode("");
//        this.setRecieptNo("");
//        this.setControlNo("");
//        this.setPrinAmount("");
//        this.setStatusLien("");
//        this.setTypeOfSecurity("0");
//        this.setLienMarkOption("--Select--");
//        this.setDetail("");
//        additionalRoi = "0";
//        this.setFlag1LM(true);
//        gridDetailLM = new ArrayList<TdLienMarkingGrid>();
//    }
    /**
     * END OF LIEN MARKING*
     */
    private boolean fflag;
    private List<SelectItem> listGroupType;
    private String lblGroupID = "none";
    private String ddGroupId = "none";
    private String lblGroupType = "none";
    private String ddGroupType = "none";

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFflag() {
        return fflag;
    }

    public void setFflag(boolean fflag) {
        this.fflag = fflag;
    }

    public List<SelectItem> getListGroupType() {
        return listGroupType;
    }

    public void setListGroupType(List<SelectItem> listGroupType) {
        this.listGroupType = listGroupType;
    }

    public String getDdGroupId() {
        return ddGroupId;
    }

    public void setDdGroupId(String ddGroupId) {
        this.ddGroupId = ddGroupId;
    }

    public String getDdGroupType() {
        return ddGroupType;
    }

    public void setDdGroupType(String ddGroupType) {
        this.ddGroupType = ddGroupType;
    }

    public String getLblGroupID() {
        return lblGroupID;
    }

    public void setLblGroupID(String lblGroupID) {
        this.lblGroupID = lblGroupID;
    }

    public String getLblGroupType() {
        return lblGroupType;
    }

    public void setLblGroupType(String lblGroupType) {
        this.lblGroupType = lblGroupType;
    }

    public void disableRetireAge() {
        setDisableretirPanel("");
        try {
            List acctInfo1 = loanAcDetails.accountDetail(acno);
            if (!acctInfo1.isEmpty()) {
                Vector acInfoVect = (Vector) acctInfo1.get(0);
                if (!acInfoVect.get(6).toString().equalsIgnoreCase("IND")) {
                    this.setDisableretirPanel("none");
                }
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void reFresh() {
        try {
            this.flagRefresh = false;
            this.flagExit = false;
            currentItem = new ArrayList<TdLienMarkingGrid>();
            actionGuaranteeList = new ArrayList<SelectItem>();
            setMessage(" ");
            displaypaneldate = "none";
            setFunction("--Select--");
            setAction("--Select--");
            setVerGuarantee("--Select--");
            setAcno("");
            setName("");
            setOdLimit(0.0);
            setBalance("");
            setBenfiName("");
            setBenfiAddress("");
            setPinCode("");
            setState("--Select--");
            setCity("");
            setGuaranteeIssuedBy("--Select--");
            setClassification("--Select--");
            setPurpose("--Select--");
            setPeriod(0);
            setGuaranteeExpiryDate("");
            verifyflag = "none";
            issueDisplay = "none";
            firstDisplay = "none";
            setGuaranteeAmt(new BigDecimal(0.0));
            setComissionAmt(0.0);
            setTotalcomissionAmt(0.0);
            setTaxCharges(0.0);
            setFlag(true);
            setFlag1(true);
            setReportType("--Select--");
            setFromDate(dmy.format(ymd.parse(date1)));
            setToDate(dmy.format(ymd.parse(date1)));
            setDisplaytype("none");
            setDisplayFromDate("none");
            setDisplayToDate("none");
            setPrintDisable(true);
            resetValues();
            refreshOnInvalidAccNo();
            getUserName();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public String exitBtnAction() {
        return "case1";
    }

    public void printPdfReport() {
        try {
            reportData = new ArrayList<BankGuaranteePojo>();
            reportData = bankGuarRemort.getReportData(this.getAction(), this.getFromDate(), this.getToDate(), this.getOrgnBrCode(), this.getReportType(), this.issueType, this.tillDate);
            if (reportData.size() > 0) {

                String currentAction;
                String issueName = "";
                if (getAction().equalsIgnoreCase("I")) {
                    currentAction = "Issue";
                } else if (getAction().equalsIgnoreCase("R")) {
                    currentAction = "Renew";
                } else if (getAction().equalsIgnoreCase("V")) {
                    currentAction = "Invoke/claim";
                } else {
                    currentAction = "Surrender";
                }
                String repName = "BankGuaranteeDetailReport";
                Map fillParams = new HashMap();
                List brNameAndAddList = CommonReportMethodsBean.getBranchNameandAddress(this.orgnBrCode);
                fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
                fillParams.put("pReportName", repName);
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                if (issueType == (null)) {
                    issueType = "";
                } else {

                    if (issueType.equalsIgnoreCase("AA")) {
                        issueName = "All Active";
                    } else if (issueType.equalsIgnoreCase("EI")) {
                        issueName = "Expiry In";
                    } else {
                        issueName = "All";
                    }
                    fillParams.put("issueType", issueName);
                }
                if ((action.equalsIgnoreCase("I") || action.equalsIgnoreCase("R")) && issueType.equalsIgnoreCase("AA")) {
                    fillParams.put("fromDate", "01/01/1900");
                    fillParams.put("toDate", tillDate);
                } else {
                    fillParams.put("fromDate", getFromDate());
                    fillParams.put("toDate", getToDate());
                }
                fillParams.put("printBy", getUserName());
                fillParams.put("printDate", getTodayDate());
                fillParams.put("action", currentAction);
                new ReportBean().openPdf(repName, "BankGuaranteeUpdationReport", new JRBeanCollectionDataSource(reportData), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                this.setMessage("No Data Found");
            }

        } catch (Exception e) {
            this.setMessage("No Data Found");
        }

    }

    public void clearModelPanel() throws ParseException {
        resetPage();
    }

    public void saveEnableSEC() {
        this.setSaveDisable(false);
    }

    public void MarkSecurity() {

//        if (markSecurities.equalsIgnoreCase("3")) {
//            flagSecurity = "false";
//            setSaveDisable(true);
//        } else
        if (markSecurities.equalsIgnoreCase("2")) {
            secutiType = "O";
            flagSecurity = "false";
            setSaveDisable(true);
        } else if (markSecurities.equalsIgnoreCase("1")) {
            secutiType = "F";
            setSaveDisable(true);
            flagSecurity = "false";
        }
    }
}
