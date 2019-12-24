/*
 * Created By    :   ROHIT KRISHNA GUPTA
 * Creation Date :   12 Oct 2010
 */
package com.cbs.web.controller.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.DisbursementSchedule;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.web.pojo.loan.EmploymentDetailsGrid;
import com.cbs.web.pojo.loan.GuarantorDetailGrid;
import com.cbs.web.pojo.loan.CompanyDetailGrid;
import com.cbs.web.pojo.loan.CompanyDetailGridLoad;
import com.cbs.web.pojo.loan.InsuranceDetailGrid;
import com.cbs.web.pojo.loan.LegalDocumentsGrid;
import com.cbs.facade.admin.AccountStatusSecurityFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.loan.LoanAccountsDetailsRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.admin.SecurityDetail;
import com.cbs.web.pojo.admin.AcStatus;
import com.cbs.web.pojo.admin.TdLienMarkingGrid;
import com.cbs.web.pojo.intcal.AmortizationSchedule;
import com.cbs.web.pojo.loan.DisbursementDetail;
import com.cbs.web.pojo.loan.InstallmentPlan;
import com.hrms.web.utils.WebUtil;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class LoanAccountsDetails extends BaseBean {

    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    private CommonReportMethodsRemote CommonReportMethodsBean;
    LoanAccountsDetailsRemote loanAcDetails;
    AdvancesInformationTrackingRemote aitr;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    Date date = new Date();
    // NumberFormat formatter = new DecimalFormat("#0.00");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<SelectItem> functionValList;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String acctType;
    private String acctNo;
    private String oldAcctNo;
    private String agentCode = "01";
    private String partyName;
    private String acOpenDt;
    private String acNo;
    private String firmName;
    private String comAdd;
    private String comPhoneNo;
    private String monthlyIncome;
    private Date fromDt;
    private Date toDt;
    private String designation;
    private String reasonOfLeaving;
    private String empId;
    private boolean tabDisableFlag;
    private List<SelectItem> acTypeList;
    private HttpServletRequest req;
    int currentRow;
    public String disableDirPanel;
    private List<EmploymentDetailsGrid> gridDetail;
    private EmploymentDetailsGrid currentItem = new EmploymentDetailsGrid();
    /**
     * **These Variables For Legal Documents Tab**************
     */
    private Date dueDt;
    private Date expDt;
    private Date recDt;
    private String docCode;
    private String remarks;
    private String delFlag;
    private String forOtherDetails;
    private String scanFlag;
    private String freeText1;
    private String freeText2;
    private String freeText3;
    private String freeText4;
    private String freeText5;
    private String freeText6;
    private String freeText7;
    private String freeText8;
    private String freeText9;
    private String freeText10;
    private boolean otherDetailFlag;
    private List<SelectItem> docList;
    private List<SelectItem> delFlagList;
    private List<SelectItem> scanFlagList;
    private List<SelectItem> forOtherDetailList;
    int currentRow1;
    private List<LegalDocumentsGrid> gridDetail1;
    private LegalDocumentsGrid currentItem1 = new LegalDocumentsGrid();
    /**
     * **************END OF LEGAL DOCS************************
     */
    /**
     * ***These Variables Are For Loan Share Details**********
     */
    private String shareType;
    private List<SelectItem> shareTypeList;
    private String appShareMoney;
    private String nominee;
    private Date membershipDt;
    private String nominalMem;
    private List<SelectItem> nominalMemList;
    private String nominalMemNo;
    private String nomMemLable;
    /**
     * *********End Of Loan Share Details Variables************
     */
    /**
     * ****These Variables Are For Guarantor Account Details***
     */
    private String guarAcChk;
    private List<SelectItem> guarAcChkList;
    private String guarAcNo;
    private String oldGuarAcNo;
    private String guarName;
    private String guarAddress;
    private String guarFatherName;
    private String guarPhoneNo;
    private String guarAge;
    private String guarRetirementAge;
    private String guarOccupation;
    private String guarOfficePhpneNo;
    private String guarFirmName;
    private String guarDesignation;
    private String guarOffAddress;
    private String guarNetWorth;
    private String guarActionFlag;
    private String popupGuartFlag = "false";
    private Integer guarNo;
    private boolean gFlag;
    private boolean gFlag1;
    int currentRow2;
    private List<GuarantorDetailGrid> gridDetail2;
    private GuarantorDetailGrid currentItem2 = new GuarantorDetailGrid();
    private boolean guarFlag1;
    private boolean guarFlag2;
    private boolean guarFlag3;
    private boolean guarFlag4;
    private boolean guarFlag5;
    private String actulNetWorth;
    private String dirCustId;
    private String retirementAge;
    public String disableretirPanel;
    private String dirName, acNoLen;
    AccountOpeningFacadeRemote openingFacadeRemote;
    private String groupType;
    private String groupID;
    private List<SelectItem> listGroupType;
    private List<SelectItem> listGroupId;
    private String lblGroupID = "none";
    private String ddGroupId = "none";
    private String lblGroupType = "none";
    private String ddGroupType = "none";
    private boolean disableRel;
    private boolean disableRelOwn;
    NumberFormat formatter2 = new DecimalFormat("0.00");

    public boolean isDisableRel() {
        return disableRel;
    }

    public void setDisableRelOwn(boolean disableRelOwn) {
        this.disableRelOwn = disableRelOwn;
    }

    public void setDisableRel(boolean disableRel) {
        this.disableRel = disableRel;
    }

    public boolean isDisableRelOwn() {
        return disableRelOwn;
    }

    public String getDisableretirPanel() {
        return disableretirPanel;
    }

    public void setDisableretirPanel(String disableretirPanel) {
        this.disableretirPanel = disableretirPanel;
    }

    public String getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(String retirementAge) {
        this.retirementAge = retirementAge;
    }

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public List<SelectItem> getFunctionValList() {
        return functionValList;
    }

    public void setFunctionValList(List<SelectItem> functionValList) {
        this.functionValList = functionValList;
    }

    public boolean isGuarFlag1() {
        return guarFlag1;
    }

    public void setGuarFlag1(boolean guarFlag1) {
        this.guarFlag1 = guarFlag1;
    }

    public boolean isGuarFlag2() {
        return guarFlag2;
    }

    public void setGuarFlag2(boolean guarFlag2) {
        this.guarFlag2 = guarFlag2;
    }

    public boolean isGuarFlag3() {
        return guarFlag3;
    }

    public void setGuarFlag3(boolean guarFlag3) {
        this.guarFlag3 = guarFlag3;
    }

    public boolean isGuarFlag4() {
        return guarFlag4;
    }

    public void setGuarFlag4(boolean guarFlag4) {
        this.guarFlag4 = guarFlag4;
    }

    public boolean isGuarFlag5() {
        return guarFlag5;
    }

    public void setGuarFlag5(boolean guarFlag5) {
        this.guarFlag5 = guarFlag5;
    }

    public String getActulNetWorth() {
        return actulNetWorth;
    }

    public void setActulNetWorth(String actulNetWorth) {
        this.actulNetWorth = actulNetWorth;
    }

    public String getOldGuarAcNo() {
        return oldGuarAcNo;
    }

    public void setOldGuarAcNo(String oldGuarAcNo) {
        this.oldGuarAcNo = oldGuarAcNo;
    }
    /**
     * ****End Of Guarntor Account Detail Tab Variable*********
     */
    /**
     * ****These Variables Are For Insurance Details***
     */
    private String assetDesc;
    private String assetValue;
    private String insuranceCmpnyName;
    private List<SelectItem> insuranceCmpnyNameList;
    private String insuranceType;
    private List<SelectItem> insuranceTypeList;
    private String coverNoteNo;
    private String insurancceStatus;
    private List<SelectItem> insurancceStatusList;
    private Date insIssueDt;
    private Date insExpDt;
    private String insuranceAmt;
    private String insuranceDetails;
    private boolean insFlag;
    int currentRow3;
    private List<InsuranceDetailGrid> gridDetail3;
    private InsuranceDetailGrid currentItem3 = new InsuranceDetailGrid();
    /**
     * ****End Of Insurance Detail Tab Variable*********
     */
    /**
     * ****These Variables Are For Company Details***
     */
    private String cmpnyName;
    private String regOffice;
    private String regOfficeLocation;
    private Date incDate;
    private String authCapital;
    private String suscribedCapital;
    private String netWorth;
    private String dirName1;
    private String dirName2;
    private String dirName3;
    private String dirName4;
    private String dirName5;
    private String dirName6;
    private String dirName7;
    private String grCompName;
    private String grCompAdd;
    private String grCompPhNo;
    private boolean comFlag;
    private boolean cFlag;
    int currentRow4;
    private List<CompanyDetailGridLoad> gridDetail4;
    private CompanyDetailGridLoad currentItem4 = new CompanyDetailGridLoad();
    int currentRow5;
    private List<CompanyDetailGrid> gridDetail5;
    private CompanyDetailGrid currentItem5 = new CompanyDetailGrid();
    /**
     * ****End Of Company Detail Tab Variable*********
     */
    /**
     * These Variables Are For Nishant tab*
     */
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
    String custId, custId1, custId2, custId3, custId4;
    String intType;
    String schemeCode;
    String npaClass;
    String courts;
    String modeOfSettle;
    String debtWaiverReason;
    String assetClassReason;
    String popuGroup;
    String function;
    // String errorMsg;
    String functionValue;
    boolean flag1;
    boolean flag2;
    String createdByUId;
    String creationDate;
    String lastUpdateUId;
    String lastUpdationDate;
    String totalModifications;
//    String acNo;
//    String acOpenDt;
//    String partyName;
    String status;
    // String disbursement;
    String netWorth1;
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
    String minorCommunity;
    String remarks1;
    String relName;
    String relOwner;
    boolean flagForAppCateg;
    String industry;
    String businessIndustryType;
    String businessIndustryTypeInvisiable = "none";
    List<SelectItem> sectorOption;
    List<SelectItem> subSectorOption;
    List<SelectItem> purposeOfAdvanceOption;
    List<SelectItem> modeOfAdvanceOption;
    List<SelectItem> typeOfAdvanceOption;
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
    List<SelectItem> schemeCodeOption;
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
    List<SelectItem> businessIndustryTypeList;
    /**
     * End Of Nishant tab*
     */
    /**
     * THESE VARIABLES ARE FOR SHIPRA TAB*
     */
    // private String showMsg;
//    private String todayDate;
//    private String userName;
    private String acno;
    private String functionFlag;
    private String functionDesc;
    private String sanctionAmt;
//    private BigDecimal sanctionAmt;
//    private String sanctionDate;
    private Date sanctionDate;
    private String processingFee;
    private String remarkAIT;
    private String sno;
    private String flowId;
    private String remarksDisbursment;
    private String deleteFlagDisburest;
    private String disbursetmentAmt = "0";
    private String disbursetmentType;
    private Date disbursetmentDate;
    int count1Disbst = 0;
    int count2Disbst = 0;
    private int currentRowDisbst;
    float totalDisburstment = (float) 0.0;
    int selectCountDisbst = 0;
    private boolean disableFlagDisbst;
    private boolean disableFlagDisbst1;
    private boolean disableFlagAmt;
    private boolean disableFlagProcessing;
    private boolean disableRemarks;
    private boolean disableFlagDate;
    // private boolean disableFlagReferenceType;
    private List<DisbursementSchedule> disbstSch;
    private List<DisbursementDetail> disbstDtl;
    private DisbursementSchedule currentItemDisbst = new DisbursementSchedule();
    private List<DisbursementSchedule> disbstDtlTmp = new ArrayList<DisbursementSchedule>();
    private List<SelectItem> deleteDisburest;
    private List<SelectItem> disburestType;
    private List<SelectItem> assetDescOption;
//    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
//    Date date = new Date();
    /**
     * SHIPRA's 2nd Tab*
     */
    /**
     * ******************************************* RepaymentSchedule
     * ***************************************************
     */
    private String investmentType;
    private String periodicity;
    private String sanctionedAmt;
    private String osBalance;
    private String periods;
    private Date startDate = new Date();
    private String rateOfInst;
    private String intPrinciple;
    private String statusmsg;
    private String intServe;
    private String flag;
    private boolean resheduleDisable;
    private boolean updateDisable;
    private boolean calculateDisable;
    private boolean intServeDisable;
    private boolean intPrincipleDisable;
    private boolean roiDisable;
    private String roiOnSecurity;
    private String additionalRoi;
    String instType;
    private String secODScheme;
    private String secODRoi;
    private List<SelectItem> investmentTypeList;
    private List<SelectItem> periodList;
    private List<InstallmentPlan> instPlan;
    private List<SelectItem> intServeList;
    private List<SelectItem> intPrincipleList;
    private List<AmortizationSchedule> amortSch;

    public List<SelectItem> getAssetDescOption() {
        return assetDescOption;
    }

    public void setAssetDescOption(List<SelectItem> assetDescOption) {
        this.assetDescOption = assetDescOption;
    }

    public String getSecODScheme() {
        return secODScheme;
    }

    public void setSecODScheme(String secODScheme) {
        this.secODScheme = secODScheme;
    }

    public String getSecODRoi() {
        return secODRoi;
    }

    public void setSecODRoi(String secODRoi) {
        this.secODRoi = secODRoi;
    }

    public boolean isDisableFlagDisbst1() {
        return disableFlagDisbst1;
    }

    public void setDisableFlagDisbst1(boolean disableFlagDisbst1) {
        this.disableFlagDisbst1 = disableFlagDisbst1;
    }

    /**
     * END 2nd tab*
     */
    /**
     * END OF SHIPRA TAB*
     */
    public String getAcOpenDt() {
        return acOpenDt;
    }

    public void setAcOpenDt(String acOpenDt) {
        this.acOpenDt = acOpenDt;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getComAdd() {
        return comAdd;
    }

    public void setComAdd(String comAdd) {
        this.comAdd = comAdd;
    }

    public String getComPhoneNo() {
        return comPhoneNo;
    }

    public void setComPhoneNo(String comPhoneNo) {
        this.comPhoneNo = comPhoneNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public Date getFromDt() {
        return fromDt;
    }

    public void setFromDt(Date fromDt) {
        this.fromDt = fromDt;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getReasonOfLeaving() {
        return reasonOfLeaving;
    }

    public void setReasonOfLeaving(String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public EmploymentDetailsGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmploymentDetailsGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<EmploymentDetailsGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<EmploymentDetailsGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isTabDisableFlag() {
        return tabDisableFlag;
    }

    public void setTabDisableFlag(boolean tabDisableFlag) {
        this.tabDisableFlag = tabDisableFlag;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
    }

    public Date getExpDt() {
        return expDt;
    }

    public void setExpDt(Date expDt) {
        this.expDt = expDt;
    }

    public Date getRecDt() {
        return recDt;
    }

    public void setRecDt(Date recDt) {
        this.recDt = recDt;
    }

    public List<SelectItem> getDocList() {
        return docList;
    }

    public void setDocList(List<SelectItem> docList) {
        this.docList = docList;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LegalDocumentsGrid getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(LegalDocumentsGrid currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<LegalDocumentsGrid> getGridDetail1() {
        return gridDetail1;
    }

    public void setGridDetail1(List<LegalDocumentsGrid> gridDetail1) {
        this.gridDetail1 = gridDetail1;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getFreeText1() {
        return freeText1;
    }

    public void setFreeText1(String freeText1) {
        this.freeText1 = freeText1;
    }

    public String getFreeText10() {
        return freeText10;
    }

    public void setFreeText10(String freeText10) {
        this.freeText10 = freeText10;
    }

    public String getFreeText2() {
        return freeText2;
    }

    public void setFreeText2(String freeText2) {
        this.freeText2 = freeText2;
    }

    public String getFreeText3() {
        return freeText3;
    }

    public void setFreeText3(String freeText3) {
        this.freeText3 = freeText3;
    }

    public String getFreeText4() {
        return freeText4;
    }

    public void setFreeText4(String freeText4) {
        this.freeText4 = freeText4;
    }

    public String getFreeText5() {
        return freeText5;
    }

    public void setFreeText5(String freeText5) {
        this.freeText5 = freeText5;
    }

    public String getFreeText6() {
        return freeText6;
    }

    public void setFreeText6(String freeText6) {
        this.freeText6 = freeText6;
    }

    public String getFreeText7() {
        return freeText7;
    }

    public void setFreeText7(String freeText7) {
        this.freeText7 = freeText7;
    }

    public String getFreeText8() {
        return freeText8;
    }

    public void setFreeText8(String freeText8) {
        this.freeText8 = freeText8;
    }

    public String getFreeText9() {
        return freeText9;
    }

    public void setFreeText9(String freeText9) {
        this.freeText9 = freeText9;
    }

    public String getScanFlag() {
        return scanFlag;
    }

    public void setScanFlag(String scanFlag) {
        this.scanFlag = scanFlag;
    }

    public List<SelectItem> getDelFlagList() {
        return delFlagList;
    }

    public void setDelFlagList(List<SelectItem> delFlagList) {
        this.delFlagList = delFlagList;
    }

    public List<SelectItem> getScanFlagList() {
        return scanFlagList;
    }

    public void setScanFlagList(List<SelectItem> scanFlagList) {
        this.scanFlagList = scanFlagList;
    }

    public String getForOtherDetails() {
        return forOtherDetails;
    }

    public void setForOtherDetails(String forOtherDetails) {
        this.forOtherDetails = forOtherDetails;
    }

    public List<SelectItem> getForOtherDetailList() {
        return forOtherDetailList;
    }

    public void setForOtherDetailList(List<SelectItem> forOtherDetailList) {
        this.forOtherDetailList = forOtherDetailList;
    }

    public boolean isOtherDetailFlag() {
        return otherDetailFlag;
    }

    public void setOtherDetailFlag(boolean otherDetailFlag) {
        this.otherDetailFlag = otherDetailFlag;
    }

    public String getAppShareMoney() {
        return appShareMoney;
    }

    public void setAppShareMoney(String appShareMoney) {
        this.appShareMoney = appShareMoney;
    }

    public Date getMembershipDt() {
        return membershipDt;
    }

    public void setMembershipDt(Date membershipDt) {
        this.membershipDt = membershipDt;
    }

    public String getNominalMem() {
        return nominalMem;
    }

    public void setNominalMem(String nominalMem) {
        this.nominalMem = nominalMem;
    }

    public List<SelectItem> getNominalMemList() {
        return nominalMemList;
    }

    public void setNominalMemList(List<SelectItem> nominalMemList) {
        this.nominalMemList = nominalMemList;
    }

    public String getNominalMemNo() {
        return nominalMemNo;
    }

    public void setNominalMemNo(String nominalMemNo) {
        this.nominalMemNo = nominalMemNo;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public List<SelectItem> getShareTypeList() {
        return shareTypeList;
    }

    public void setShareTypeList(List<SelectItem> shareTypeList) {
        this.shareTypeList = shareTypeList;
    }

    public String getNomMemLable() {
        return nomMemLable;
    }

    public void setNomMemLable(String nomMemLable) {
        this.nomMemLable = nomMemLable;
    }

    public String getGuarAcChk() {
        return guarAcChk;
    }

    public void setGuarAcChk(String guarAcChk) {
        this.guarAcChk = guarAcChk;
    }

    public List<SelectItem> getGuarAcChkList() {
        return guarAcChkList;
    }

    public void setGuarAcChkList(List<SelectItem> guarAcChkList) {
        this.guarAcChkList = guarAcChkList;
    }

    public String getGuarAcNo() {
        return guarAcNo;
    }

    public void setGuarAcNo(String guarAcNo) {
        this.guarAcNo = guarAcNo;
    }

    public String getGuarAddress() {
        return guarAddress;
    }

    public void setGuarAddress(String guarAddress) {
        this.guarAddress = guarAddress;
    }

    public String getGuarAge() {
        return guarAge;
    }

    public void setGuarAge(String guarAge) {
        this.guarAge = guarAge;
    }

    public String getGuarDesignation() {
        return guarDesignation;
    }

    public void setGuarDesignation(String guarDesignation) {
        this.guarDesignation = guarDesignation;
    }

    public String getGuarFatherName() {
        return guarFatherName;
    }

    public void setGuarFatherName(String guarFatherName) {
        this.guarFatherName = guarFatherName;
    }

    public String getGuarFirmName() {
        return guarFirmName;
    }

    public void setGuarFirmName(String guarFirmName) {
        this.guarFirmName = guarFirmName;
    }

    public String getGuarName() {
        return guarName;
    }

    public void setGuarName(String guarName) {
        this.guarName = guarName;
    }

    public String getGuarNetWorth() {
        return guarNetWorth;
    }

    public void setGuarNetWorth(String guarNetWorth) {
        this.guarNetWorth = guarNetWorth;
    }

    public String getGuarOccupation() {
        return guarOccupation;
    }

    public void setGuarOccupation(String guarOccupation) {
        this.guarOccupation = guarOccupation;
    }

    public String getGuarOffAddress() {
        return guarOffAddress;
    }

    public void setGuarOffAddress(String guarOffAddress) {
        this.guarOffAddress = guarOffAddress;
    }

    public String getGuarOfficePhpneNo() {
        return guarOfficePhpneNo;
    }

    public void setGuarOfficePhpneNo(String guarOfficePhpneNo) {
        this.guarOfficePhpneNo = guarOfficePhpneNo;
    }

    public String getGuarPhoneNo() {
        return guarPhoneNo;
    }

    public void setGuarPhoneNo(String guarPhoneNo) {
        this.guarPhoneNo = guarPhoneNo;
    }

    public String getGuarRetirementAge() {
        return guarRetirementAge;
    }

    public void setGuarRetirementAge(String guarRetirementAge) {
        this.guarRetirementAge = guarRetirementAge;
    }

    public String getPopupGuartFlag() {
        return popupGuartFlag;
    }

    public void setPopupGuartFlag(String popupGuartFlag) {
        this.popupGuartFlag = popupGuartFlag;
    }

    public Integer getGuarNo() {
        return guarNo;
    }

    public void setGuarNo(Integer guarNo) {
        this.guarNo = guarNo;
    }

    public GuarantorDetailGrid getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(GuarantorDetailGrid currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public List<GuarantorDetailGrid> getGridDetail2() {
        return gridDetail2;
    }

    public void setGridDetail2(List<GuarantorDetailGrid> gridDetail2) {
        this.gridDetail2 = gridDetail2;
    }

    public String getGuarActionFlag() {
        return guarActionFlag;
    }

    public void setGuarActionFlag(String guarActionFlag) {
        this.guarActionFlag = guarActionFlag;
    }

    public boolean isgFlag() {
        return gFlag;
    }

    public void setgFlag(boolean gFlag) {
        this.gFlag = gFlag;
    }

    public boolean isgFlag1() {
        return gFlag1;
    }

    public void setgFlag1(boolean gFlag1) {
        this.gFlag1 = gFlag1;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(String assetValue) {
        this.assetValue = assetValue;
    }

    public String getCoverNoteNo() {
        return coverNoteNo;
    }

    public void setCoverNoteNo(String coverNoteNo) {
        this.coverNoteNo = coverNoteNo;
    }

    public Date getInsExpDt() {
        return insExpDt;
    }

    public void setInsExpDt(Date insExpDt) {
        this.insExpDt = insExpDt;
    }

    public Date getInsIssueDt() {
        return insIssueDt;
    }

    public void setInsIssueDt(Date insIssueDt) {
        this.insIssueDt = insIssueDt;
    }

    public String getInsurancceStatus() {
        return insurancceStatus;
    }

    public void setInsurancceStatus(String insurancceStatus) {
        this.insurancceStatus = insurancceStatus;
    }

    public List<SelectItem> getInsurancceStatusList() {
        return insurancceStatusList;
    }

    public void setInsurancceStatusList(List<SelectItem> insurancceStatusList) {
        this.insurancceStatusList = insurancceStatusList;
    }

    public String getInsuranceAmt() {
        return insuranceAmt;
    }

    public void setInsuranceAmt(String insuranceAmt) {
        this.insuranceAmt = insuranceAmt;
    }

    public String getInsuranceCmpnyName() {
        return insuranceCmpnyName;
    }

    public void setInsuranceCmpnyName(String insuranceCmpnyName) {
        this.insuranceCmpnyName = insuranceCmpnyName;
    }

    public String getInsuranceDetails() {
        return insuranceDetails;
    }

    public void setInsuranceDetails(String insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public InsuranceDetailGrid getCurrentItem3() {
        return currentItem3;
    }

    public void setCurrentItem3(InsuranceDetailGrid currentItem3) {
        this.currentItem3 = currentItem3;
    }

    public int getCurrentRow3() {
        return currentRow3;
    }

    public void setCurrentRow3(int currentRow3) {
        this.currentRow3 = currentRow3;
    }

    public List<InsuranceDetailGrid> getGridDetail3() {
        return gridDetail3;
    }

    public void setGridDetail3(List<InsuranceDetailGrid> gridDetail3) {
        this.gridDetail3 = gridDetail3;
    }

    public List<SelectItem> getInsuranceCmpnyNameList() {
        return insuranceCmpnyNameList;
    }

    public void setInsuranceCmpnyNameList(List<SelectItem> insuranceCmpnyNameList) {
        this.insuranceCmpnyNameList = insuranceCmpnyNameList;
    }

    public List<SelectItem> getInsuranceTypeList() {
        return insuranceTypeList;
    }

    public void setInsuranceTypeList(List<SelectItem> insuranceTypeList) {
        this.insuranceTypeList = insuranceTypeList;
    }

    public boolean isInsFlag() {
        return insFlag;
    }

    public void setInsFlag(boolean insFlag) {
        this.insFlag = insFlag;
    }

    public String getAuthCapital() {
        return authCapital;
    }

    public void setAuthCapital(String authCapital) {
        this.authCapital = authCapital;
    }

    public String getCmpnyName() {
        return cmpnyName;
    }

    public void setCmpnyName(String cmpnyName) {
        this.cmpnyName = cmpnyName;
    }

    public Date getIncDate() {
        return incDate;
    }

    public void setIncDate(Date incDate) {
        this.incDate = incDate;
    }

    public String getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }

    public String getRegOffice() {
        return regOffice;
    }

    public void setRegOffice(String regOffice) {
        this.regOffice = regOffice;
    }

    public String getRegOfficeLocation() {
        return regOfficeLocation;
    }

    public void setRegOfficeLocation(String regOfficeLocation) {
        this.regOfficeLocation = regOfficeLocation;
    }

    public String getSuscribedCapital() {
        return suscribedCapital;
    }

    public void setSuscribedCapital(String suscribedCapital) {
        this.suscribedCapital = suscribedCapital;
    }

    public int getCurrentRow4() {
        return currentRow4;
    }

    public void setCurrentRow4(int currentRow4) {
        this.currentRow4 = currentRow4;
    }

    public CompanyDetailGrid getCurrentItem5() {
        return currentItem5;
    }

    public void setCurrentItem5(CompanyDetailGrid currentItem5) {
        this.currentItem5 = currentItem5;
    }

    public int getCurrentRow5() {
        return currentRow5;
    }

    public void setCurrentRow5(int currentRow5) {
        this.currentRow5 = currentRow5;
    }

    public List<CompanyDetailGrid> getGridDetail5() {
        return gridDetail5;
    }

    public void setGridDetail5(List<CompanyDetailGrid> gridDetail5) {
        this.gridDetail5 = gridDetail5;
    }

    public CompanyDetailGridLoad getCurrentItem4() {
        return currentItem4;
    }

    public void setCurrentItem4(CompanyDetailGridLoad currentItem4) {
        this.currentItem4 = currentItem4;
    }

    public List<CompanyDetailGridLoad> getGridDetail4() {
        return gridDetail4;
    }

    public void setGridDetail4(List<CompanyDetailGridLoad> gridDetail4) {
        this.gridDetail4 = gridDetail4;
    }

    public String getDirName1() {
        return dirName1;
    }

    public void setDirName1(String dirName1) {
        this.dirName1 = dirName1;
    }

    public String getDirName2() {
        return dirName2;
    }

    public void setDirName2(String dirName2) {
        this.dirName2 = dirName2;
    }

    public String getDirName3() {
        return dirName3;
    }

    public void setDirName3(String dirName3) {
        this.dirName3 = dirName3;
    }

    public String getDirName4() {
        return dirName4;
    }

    public void setDirName4(String dirName4) {
        this.dirName4 = dirName4;
    }

    public String getDirName5() {
        return dirName5;
    }

    public void setDirName5(String dirName5) {
        this.dirName5 = dirName5;
    }

    public String getDirName6() {
        return dirName6;
    }

    public void setDirName6(String dirName6) {
        this.dirName6 = dirName6;
    }

    public String getDirName7() {
        return dirName7;
    }

    public void setDirName7(String dirName7) {
        this.dirName7 = dirName7;
    }

    public String getGrCompAdd() {
        return grCompAdd;
    }

    public void setGrCompAdd(String grCompAdd) {
        this.grCompAdd = grCompAdd;
    }

    public String getGrCompName() {
        return grCompName;
    }

    public void setGrCompName(String grCompName) {
        this.grCompName = grCompName;
    }

    public String getGrCompPhNo() {
        return grCompPhNo;
    }

    public void setGrCompPhNo(String grCompPhNo) {
        this.grCompPhNo = grCompPhNo;
    }

    public boolean iscFlag() {
        return cFlag;
    }

    public void setcFlag(boolean cFlag) {
        this.cFlag = cFlag;
    }

    public boolean isComFlag() {
        return comFlag;
    }

    public void setComFlag(boolean comFlag) {
        this.comFlag = comFlag;
    }

    public String getApplicantCategory() {
        return applicantCategory;
    }

    public void setApplicantCategory(String applicantCategory) {
        this.applicantCategory = applicantCategory;
    }

    public List<SelectItem> getApplicantCategoryOption() {
        return applicantCategoryOption;
    }

    public void setApplicantCategoryOption(List<SelectItem> applicantCategoryOption) {
        this.applicantCategoryOption = applicantCategoryOption;
    }

    public String getAssetClassReason() {
        return assetClassReason;
    }

    public void setAssetClassReason(String assetClassReason) {
        this.assetClassReason = assetClassReason;
    }

    public List<SelectItem> getAssetClassReasonOption() {
        return assetClassReasonOption;
    }

    public void setAssetClassReasonOption(List<SelectItem> assetClassReasonOption) {
        this.assetClassReasonOption = assetClassReasonOption;
    }

    public String getCategoryOpt() {
        return categoryOpt;
    }

    public void setCategoryOpt(String categoryOpt) {
        this.categoryOpt = categoryOpt;
    }

    public List<SelectItem> getCategoryOptOption() {
        return categoryOptOption;
    }

    public void setCategoryOptOption(List<SelectItem> categoryOptOption) {
        this.categoryOptOption = categoryOptOption;
    }

    public String getCourts() {
        return courts;
    }

    public void setCourts(String courts) {
        this.courts = courts;
    }

    public List<SelectItem> getCourtsOption() {
        return courtsOption;
    }

    public void setCourtsOption(List<SelectItem> courtsOption) {
        this.courtsOption = courtsOption;
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

    public String getDebtWaiverReason() {
        return debtWaiverReason;
    }

    public void setDebtWaiverReason(String debtWaiverReason) {
        this.debtWaiverReason = debtWaiverReason;
    }

    public List<SelectItem> getDebtWaiverReasonOption() {
        return debtWaiverReasonOption;
    }

    public void setDebtWaiverReasonOption(List<SelectItem> debtWaiverReasonOption) {
        this.debtWaiverReasonOption = debtWaiverReasonOption;
    }

//    public String getDisbursement() {
//        return disbursement;
//    }
//
//    public void setDisbursement(String disbursement) {
//        this.disbursement = disbursement;
//    }
    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getDocExprDate() {
        return docExprDate;
    }

    public void setDocExprDate(Date docExprDate) {
        this.docExprDate = docExprDate;
    }

    public String getDrawingPwrInd() {
        return drawingPwrInd;
    }

    public void setDrawingPwrInd(String drawingPwrInd) {
        this.drawingPwrInd = drawingPwrInd;
    }

    public List<SelectItem> getDrawingPwrIndOption() {
        return drawingPwrIndOption;
    }

    public void setDrawingPwrIndOption(List<SelectItem> drawingPwrIndOption) {
        this.drawingPwrIndOption = drawingPwrIndOption;
    }

////////////    public String getErrorMsg() {
////////////        return errorMsg;
////////////    }
////////////
////////////    public void setErrorMsg(String errorMsg) {
////////////        this.errorMsg = errorMsg;
////////////    }
    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public List<SelectItem> getExposureOption() {
        return exposureOption;
    }

    public void setExposureOption(List<SelectItem> exposureOption) {
        this.exposureOption = exposureOption;
    }

    public String getExposureCategory() {
        return exposureCategory;
    }

    public void setExposureCategory(String exposureCategory) {
        this.exposureCategory = exposureCategory;
    }

    public String getExposureCategoryPurpose() {
        return exposureCategoryPurpose;
    }

    public void setExposureCategoryPurpose(String exposureCategoryPurpose) {
        this.exposureCategoryPurpose = exposureCategoryPurpose;
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

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlagForAppCateg() {
        return flagForAppCateg;
    }

    public void setFlagForAppCateg(boolean flagForAppCateg) {
        this.flagForAppCateg = flagForAppCateg;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(String functionValue) {
        this.functionValue = functionValue;
    }

    public String getGuarnteeCover() {
        return guarnteeCover;
    }

    public void setGuarnteeCover(String guarnteeCover) {
        this.guarnteeCover = guarnteeCover;
    }

    public List<SelectItem> getGuarnteeCoverOption() {
        return guarnteeCoverOption;
    }

    public void setGuarnteeCoverOption(List<SelectItem> guarnteeCoverOption) {
        this.guarnteeCoverOption = guarnteeCoverOption;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public List<SelectItem> getIndustryTypeOption() {
        return industryTypeOption;
    }

    public void setIndustryTypeOption(List<SelectItem> industryTypeOption) {
        this.industryTypeOption = industryTypeOption;
    }

    public String getIntTable() {
        return intTable;
    }

    public void setIntTable(String intTable) {
        this.intTable = intTable;
    }

    public List<SelectItem> getIntTableOption() {
        return intTableOption;
    }

    public void setIntTableOption(List<SelectItem> intTableOption) {
        this.intTableOption = intTableOption;
    }

    public String getIntType() {
        return intType;
    }

    public void setIntType(String intType) {
        this.intType = intType;
    }

    public List<SelectItem> getIntTypeOption() {
        return intTypeOption;
    }

    public void setIntTypeOption(List<SelectItem> intTypeOption) {
        this.intTypeOption = intTypeOption;
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

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getMarginMoney() {
        return marginMoney;
    }

    public void setMarginMoney(String marginMoney) {
        this.marginMoney = marginMoney;
    }

    public String getMinorCommunity() {
        return minorCommunity;
    }

    public void setMinorCommunity(String minorCommunity) {
        this.minorCommunity = minorCommunity;
    }

    public List<SelectItem> getMinorCommunityOption() {
        return minorCommunityOption;
    }

    public void setMinorCommunityOption(List<SelectItem> minorCommunityOption) {
        this.minorCommunityOption = minorCommunityOption;
    }

    public String getModeOfAdvance() {
        return modeOfAdvance;
    }

    public void setModeOfAdvance(String modeOfAdvance) {
        this.modeOfAdvance = modeOfAdvance;
    }

    public List<SelectItem> getModeOfAdvanceOption() {
        return modeOfAdvanceOption;
    }

    public void setModeOfAdvanceOption(List<SelectItem> modeOfAdvanceOption) {
        this.modeOfAdvanceOption = modeOfAdvanceOption;
    }

    public String getModeOfSettle() {
        return modeOfSettle;
    }

    public void setModeOfSettle(String modeOfSettle) {
        this.modeOfSettle = modeOfSettle;
    }

    public List<SelectItem> getModeOfSettleOption() {
        return modeOfSettleOption;
    }

    public void setModeOfSettleOption(List<SelectItem> modeOfSettleOption) {
        this.modeOfSettleOption = modeOfSettleOption;
    }

    public String getMonthlyIncome1() {
        return monthlyIncome1;
    }

    public void setMonthlyIncome1(String monthlyIncome1) {
        this.monthlyIncome1 = monthlyIncome1;
    }

    public String getNetWorth1() {
        return netWorth1;
    }

    public void setNetWorth1(String netWorth1) {
        this.netWorth1 = netWorth1;
    }

    public String getNpaClass() {
        return npaClass;
    }

    public void setNpaClass(String npaClass) {
        this.npaClass = npaClass;
    }

    public List<SelectItem> getNpaClassOption() {
        return npaClassOption;
    }

    public void setNpaClassOption(List<SelectItem> npaClassOption) {
        this.npaClassOption = npaClassOption;
    }

    public String getPopuGroup() {
        return popuGroup;
    }

    public void setPopuGroup(String popuGroup) {
        this.popuGroup = popuGroup;
    }

    public List<SelectItem> getPopuGroupOption() {
        return popuGroupOption;
    }

    public void setPopuGroupOption(List<SelectItem> popuGroupOption) {
        this.popuGroupOption = popuGroupOption;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public List<SelectItem> getPurposeOfAdvanceOption() {
        return purposeOfAdvanceOption;
    }

    public void setPurposeOfAdvanceOption(List<SelectItem> purposeOfAdvanceOption) {
        this.purposeOfAdvanceOption = purposeOfAdvanceOption;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public List<SelectItem> getRelationOption() {
        return relationOption;
    }

    public void setRelationOption(List<SelectItem> relationOption) {
        this.relationOption = relationOption;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getRestructuring() {
        return restructuring;
    }

    public void setRestructuring(String restructuring) {
        this.restructuring = restructuring;
    }

    public List<SelectItem> getRestructuringOption() {
        return restructuringOption;
    }

    public void setRestructuringOption(List<SelectItem> restructuringOption) {
        this.restructuringOption = restructuringOption;
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

    public String getSanctionAuth() {
        return sanctionAuth;
    }

    public void setSanctionAuth(String sanctionAuth) {
        this.sanctionAuth = sanctionAuth;
    }

    public List<SelectItem> getSanctionAuthOption() {
        return sanctionAuthOption;
    }

    public void setSanctionAuthOption(List<SelectItem> sanctionAuthOption) {
        this.sanctionAuthOption = sanctionAuthOption;
    }

    public String getSanctionLevel() {
        return sanctionLevel;
    }

    public void setSanctionLevel(String sanctionLevel) {
        this.sanctionLevel = sanctionLevel;
    }

    public List<SelectItem> getSanctionLevelOption() {
        return sanctionLevelOption;
    }

    public void setSanctionLevelOption(List<SelectItem> sanctionLevelOption) {
        this.sanctionLevelOption = sanctionLevelOption;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getSchemeCodeOption() {
        return schemeCodeOption;
    }

    public void setSchemeCodeOption(List<SelectItem> schemeCodeOption) {
        this.schemeCodeOption = schemeCodeOption;
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

    public String getSecured() {
        return secured;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }

    public List<SelectItem> getSecuredOption() {
        return securedOption;
    }

    public void setSecuredOption(List<SelectItem> securedOption) {
        this.securedOption = securedOption;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public List<SelectItem> getSicknessOption() {
        return sicknessOption;
    }

    public void setSicknessOption(List<SelectItem> sicknessOption) {
        this.sicknessOption = sicknessOption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubSector() {
        return subSector;
    }

    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public List<SelectItem> getSubSectorOption() {
        return subSectorOption;
    }

    public void setSubSectorOption(List<SelectItem> subSectorOption) {
        this.subSectorOption = subSectorOption;
    }

    public String getTotalModifications() {
        return totalModifications;
    }

    public void setTotalModifications(String totalModifications) {
        this.totalModifications = totalModifications;
    }

    public String getTypeOfAdvance() {
        return typeOfAdvance;
    }

    public void setTypeOfAdvance(String typeOfAdvance) {
        this.typeOfAdvance = typeOfAdvance;
    }

    public List<SelectItem> getTypeOfAdvanceOption() {
        return typeOfAdvanceOption;
    }

    public void setTypeOfAdvanceOption(List<SelectItem> typeOfAdvanceOption) {
        this.typeOfAdvanceOption = typeOfAdvanceOption;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public List<AmortizationSchedule> getAmortSch() {
        return amortSch;
    }

    public void setAmortSch(List<AmortizationSchedule> amortSch) {
        this.amortSch = amortSch;
    }

    public boolean isCalculateDisable() {
        return calculateDisable;
    }

    public void setCalculateDisable(boolean calculateDisable) {
        this.calculateDisable = calculateDisable;
    }

    public int getCount1Disbst() {
        return count1Disbst;
    }

    public void setCount1Disbst(int count1Disbst) {
        this.count1Disbst = count1Disbst;
    }

    public int getCount2Disbst() {
        return count2Disbst;
    }

    public void setCount2Disbst(int count2Disbst) {
        this.count2Disbst = count2Disbst;
    }

    public DisbursementSchedule getCurrentItemDisbst() {
        return currentItemDisbst;
    }

    public void setCurrentItemDisbst(DisbursementSchedule currentItemDisbst) {
        this.currentItemDisbst = currentItemDisbst;
    }

    public int getCurrentRowDisbst() {
        return currentRowDisbst;
    }

    public void setCurrentRowDisbst(int currentRowDisbst) {
        this.currentRowDisbst = currentRowDisbst;
    }

    public List<SelectItem> getDeleteDisburest() {
        return deleteDisburest;
    }

    public void setDeleteDisburest(List<SelectItem> deleteDisburest) {
        this.deleteDisburest = deleteDisburest;
    }

    public String getDeleteFlagDisburest() {
        return deleteFlagDisburest;
    }

    public void setDeleteFlagDisburest(String deleteFlagDisburest) {
        this.deleteFlagDisburest = deleteFlagDisburest;
    }

    public boolean isDisableFlagAmt() {
        return disableFlagAmt;
    }

    public void setDisableFlagAmt(boolean disableFlagAmt) {
        this.disableFlagAmt = disableFlagAmt;
    }

    public boolean isDisableFlagDate() {
        return disableFlagDate;
    }

    public void setDisableFlagDate(boolean disableFlagDate) {
        this.disableFlagDate = disableFlagDate;
    }

    public boolean isDisableFlagDisbst() {
        return disableFlagDisbst;
    }

    public void setDisableFlagDisbst(boolean disableFlagDisbst) {
        this.disableFlagDisbst = disableFlagDisbst;
    }

    public boolean isDisableFlagProcessing() {
        return disableFlagProcessing;
    }

    public void setDisableFlagProcessing(boolean disableFlagProcessing) {
        this.disableFlagProcessing = disableFlagProcessing;
    }

    public boolean isDisableRemarks() {
        return disableRemarks;
    }

    public void setDisableRemarks(boolean disableRemarks) {
        this.disableRemarks = disableRemarks;
    }

    public List<DisbursementDetail> getDisbstDtl() {
        return disbstDtl;
    }

    public void setDisbstDtl(List<DisbursementDetail> disbstDtl) {
        this.disbstDtl = disbstDtl;
    }

    public List<DisbursementSchedule> getDisbstDtlTmp() {
        return disbstDtlTmp;
    }

    public void setDisbstDtlTmp(List<DisbursementSchedule> disbstDtlTmp) {
        this.disbstDtlTmp = disbstDtlTmp;
    }

    public List<DisbursementSchedule> getDisbstSch() {
        return disbstSch;
    }

    public void setDisbstSch(List<DisbursementSchedule> disbstSch) {
        this.disbstSch = disbstSch;
    }

    public List<SelectItem> getDisburestType() {
        return disburestType;
    }

    public void setDisburestType(List<SelectItem> disburestType) {
        this.disburestType = disburestType;
    }

    public String getDisbursetmentAmt() {
        return disbursetmentAmt;
    }

    public void setDisbursetmentAmt(String disbursetmentAmt) {
        this.disbursetmentAmt = disbursetmentAmt;
    }

    public Date getDisbursetmentDate() {
        return disbursetmentDate;
    }

    public void setDisbursetmentDate(Date disbursetmentDate) {
        this.disbursetmentDate = disbursetmentDate;
    }

    public String getDisbursetmentType() {
        return disbursetmentType;
    }

    public void setDisbursetmentType(String disbursetmentType) {
        this.disbursetmentType = disbursetmentType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc;
    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public List<InstallmentPlan> getInstPlan() {
        return instPlan;
    }

    public void setInstPlan(List<InstallmentPlan> instPlan) {
        this.instPlan = instPlan;
    }

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public String getIntPrinciple() {
        return intPrinciple;
    }

    public void setIntPrinciple(String intPrinciple) {
        this.intPrinciple = intPrinciple;
    }

    public boolean isIntPrincipleDisable() {
        return intPrincipleDisable;
    }

    public void setIntPrincipleDisable(boolean intPrincipleDisable) {
        this.intPrincipleDisable = intPrincipleDisable;
    }

    public List<SelectItem> getIntPrincipleList() {
        return intPrincipleList;
    }

    public void setIntPrincipleList(List<SelectItem> intPrincipleList) {
        this.intPrincipleList = intPrincipleList;
    }

    public String getIntServe() {
        return intServe;
    }

    public void setIntServe(String intServe) {
        this.intServe = intServe;
    }

    public boolean isIntServeDisable() {
        return intServeDisable;
    }

    public void setIntServeDisable(boolean intServeDisable) {
        this.intServeDisable = intServeDisable;
    }

    public List<SelectItem> getIntServeList() {
        return intServeList;
    }

    public void setIntServeList(List<SelectItem> intServeList) {
        this.intServeList = intServeList;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public List<SelectItem> getInvestmentTypeList() {
        return investmentTypeList;
    }

    public void setInvestmentTypeList(List<SelectItem> investmentTypeList) {
        this.investmentTypeList = investmentTypeList;
    }

    public String getOsBalance() {
        return osBalance;
    }

    public void setOsBalance(String osBalance) {
        this.osBalance = osBalance;
    }

    public List<SelectItem> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<SelectItem> periodList) {
        this.periodList = periodList;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(String processingFee) {
        this.processingFee = processingFee;
    }

    public String getRateOfInst() {
        return rateOfInst;
    }

    public void setRateOfInst(String rateOfInst) {
        this.rateOfInst = rateOfInst;
    }

    public String getRemarkAIT() {
        return remarkAIT;
    }

    public void setRemarkAIT(String remarkAIT) {
        this.remarkAIT = remarkAIT;
    }

    public String getRemarksDisbursment() {
        return remarksDisbursment;
    }

    public void setRemarksDisbursment(String remarksDisbursment) {
        this.remarksDisbursment = remarksDisbursment;
    }

    public boolean isResheduleDisable() {
        return resheduleDisable;
    }

    public void setResheduleDisable(boolean resheduleDisable) {
        this.resheduleDisable = resheduleDisable;
    }

    public boolean isRoiDisable() {
        return roiDisable;
    }

    public void setRoiDisable(boolean roiDisable) {
        this.roiDisable = roiDisable;
    }

    public String getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(String sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Date getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Date sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getSanctionedAmt() {
        return sanctionedAmt;
    }

    public void setSanctionedAmt(String sanctionedAmt) {
        this.sanctionedAmt = sanctionedAmt;
    }

    public int getSelectCountDisbst() {
        return selectCountDisbst;
    }

    public void setSelectCountDisbst(int selectCountDisbst) {
        this.selectCountDisbst = selectCountDisbst;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatusmsg() {
        return statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        this.statusmsg = statusmsg;
    }

    public AdvancesInformationTrackingRemote getTestEJB() {
        return aitr;
    }

    public void setTestEJB(AdvancesInformationTrackingRemote aitr) {
        this.aitr = aitr;
    }

    public float getTotalDisburstment() {
        return totalDisburstment;
    }

    public void setTotalDisburstment(float totalDisburstment) {
        this.totalDisburstment = totalDisburstment;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public List<SelectItem> getRelNameOption() {
        return relNameOption;
    }

    public void setRelNameOption(List<SelectItem> relNameOption) {
        this.relNameOption = relNameOption;
    }

    public String getRelOwner() {
        return relOwner;
    }

    public void setRelOwner(String relOwner) {
        this.relOwner = relOwner;
    }

    public List<SelectItem> getRelOwnerOption() {
        return relOwnerOption;
    }

    public void setRelOwnerOption(List<SelectItem> relOwnerOption) {
        this.relOwnerOption = relOwnerOption;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<SelectItem> getIndustryOption() {
        return industryOption;
    }

    public void setIndustryOption(List<SelectItem> industryOption) {
        this.industryOption = industryOption;
    }

    public String getBusinessIndustryType() {
        return businessIndustryType;
    }

    public void setBusinessIndustryType(String businessIndustryType) {
        this.businessIndustryType = businessIndustryType;
    }

    public List<SelectItem> getBusinessIndustryTypeList() {
        return businessIndustryTypeList;
    }

    public void setBusinessIndustryTypeList(List<SelectItem> businessIndustryTypeList) {
        this.businessIndustryTypeList = businessIndustryTypeList;
    }

    public String getBusinessIndustryTypeInvisiable() {
        return businessIndustryTypeInvisiable;
    }

    public void setBusinessIndustryTypeInvisiable(String businessIndustryTypeInvisiable) {
        this.businessIndustryTypeInvisiable = businessIndustryTypeInvisiable;
    }

    /**
     * Creates a new instance of LoanAccountsDetails
     */
    public LoanAccountsDetails() {
        try {
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            lienMark = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            testEJBSEC = (AccountStatusSecurityFacadeRemote) ServiceLocator.getInstance().lookup("AccountStatusSecurityFacade");
            openingFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            loanInterestCalculationBean = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            CommonReportMethodsBean = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanAcDetails = (LoanAccountsDetailsRemote) ServiceLocator.getInstance().lookup("LoanAccountsDetailsFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setAcNoLen(getAcNoLength());
            //setUserName("manager1");
            //Date date = new Date();
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            schemeCodeOption = new ArrayList<SelectItem>();
            schemeCodeOption.add(new SelectItem("0", ""));
            intTableOption = new ArrayList<SelectItem>();
            intTableOption.add(new SelectItem("0", ""));
            setToDt(date);
            setFromDt(date);
            setDueDt(date);
            setExpDt(date);
            setRecDt(date);
            setInsIssueDt(date);
            setInsExpDt(date);
            setMembershipDt(date);
            setIncDate(date);
            setRelOwner("");
            this.disableRel = false;
            this.disableRelOwn = false;
            this.setNomMemLable("Member No. :");
            this.setTabDisableFlag(true);
            this.setOtherDetailFlag(false);
            setgFlag(false);
            setgFlag1(false);
            setComFlag(true);
            this.setErrorMessage("");
            this.setMessage("");
            guarActionFlag = "SAVE";
            setInsFlag(true);
            actTypeCombo();
            setIntPrincipleList(new ArrayList<SelectItem>());
            functionValList = new ArrayList<SelectItem>();
            functionValList.add(new SelectItem("--Select--"));
            functionValList.add(new SelectItem("A", "ADD"));
            functionValList.add(new SelectItem("M", "MODIFY"));
            functionValList.add(new SelectItem("I", "ENQUIRY"));
            docList = new ArrayList<SelectItem>();
            docList.add(new SelectItem("--Select--"));
            delFlagList = new ArrayList<SelectItem>();
            delFlagList.add(new SelectItem("--Select--"));
            delFlagList.add(new SelectItem("Yes"));
            delFlagList.add(new SelectItem("No"));
            scanFlagList = new ArrayList<SelectItem>();
            scanFlagList.add(new SelectItem("--Select--"));
            scanFlagList.add(new SelectItem("Yes"));
            scanFlagList.add(new SelectItem("No"));
            forOtherDetailList = new ArrayList<SelectItem>();
            forOtherDetailList.add(new SelectItem("--Select--"));
            forOtherDetailList.add(new SelectItem("Yes"));
            forOtherDetailList.add(new SelectItem("No"));
            shareTypeList = new ArrayList<SelectItem>();
            shareTypeList.add(new SelectItem("--Select--"));
            shareTypeList.add(new SelectItem("REGULAR"));
            shareTypeList.add(new SelectItem("NOMINAL"));
            nominalMemList = new ArrayList<SelectItem>();
            nominalMemList.add(new SelectItem("--Select--"));
            nominalMemList.add(new SelectItem("Yes"));
            nominalMemList.add(new SelectItem("No"));
            guarAcChkList = new ArrayList<SelectItem>();
            guarAcChkList.add(new SelectItem("--Select--"));
            guarAcChkList.add(new SelectItem("YA", " Has A/c. No. "));
            guarAcChkList.add(new SelectItem("YC", " Has Cust Id. "));
            guarAcChkList.add(new SelectItem("YF", " Folio No "));
            this.setGuarFlag1(false);
            this.setGuarFlag2(false);
            this.setGuarFlag3(false);
            this.setGuarFlag4(false);
            this.setGuarFlag5(false);
            insuranceStatusCombo();
            insuranceCompanyCombo();
            insuranceTypeCombo();
            /**
             * For Nishant Tab*
             */
            setNetWorth1("0.0");
            setLoanDuration("0");
            setMarginMoney("0.0");
            setMargin("");
            setSancAmount("0.0");
            setMonthlyIncome1("0.0");
            getDropDownValues();
            /**
             * END Nishant Tab*
             */
            /**
             * FOR SHIPRA TAB*
             */
            disbstSch = new ArrayList<DisbursementSchedule>();
            disbstDtl = new ArrayList<DisbursementDetail>();
            instPlan = new ArrayList<InstallmentPlan>();
            amortSch = new ArrayList<AmortizationSchedule>();
            onLoadDisburest();
            onLoadRepaymentSchedule();
            setResheduleDisable(true);
            setSno("");
            /**
             * END OF SHIPRA TAB*
             */
            /**
             * THIS CODE IS FOR SECURITY DETAIL*
             */
            getTableValues();
            setSaveDisable(true);
            setUpdateDisable1(true);
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
            this.setErrorMessageLM("");
            this.setMessageLM("");
            lienMarkOptionList = new ArrayList<SelectItem>();
            lienMarkOptionList.add(new SelectItem("--Select--"));
            lienMarkOptionList.add(new SelectItem("Yes"));
            lienMarkOptionList.add(new SelectItem("No"));

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
            // Added by Manish kumar
            List resultList2 = CommonReportMethodsBean.getRefRecList("401");
            if (!resultList2.isEmpty()) {
                assetDescOption = new ArrayList<SelectItem>();
                assetDescOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele1 = (Vector) resultList2.get(i);
                    assetDescOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }

//            typeOfSecurityList = new ArrayList<SelectItem>();
//            typeOfSecurityList.add(new SelectItem("0", ""));
//            typeOfSecurityList.add(new SelectItem("C", "COLLATERAL"));
//            typeOfSecurityList.add(new SelectItem("P", "PRIMARY"));
            this.setFlag1(true);
            securityDetailsTableValues();

            /**
             * FOR ACCOUNT STATUS *
             */
            acNumber();
            setReportDate(sdf.parse(todayDate));
            // setCode1("01");
            reFresh();
            setWefDate(sdf.parse(todayDate));
            setWefDate1(ymd.format(wefDate));
            lienacNumber();
            setFlagLienMark(true);
            this.disableDirPanel = "none";
            this.disableretirPanel = "";
            disableRetireAge();
            this.lblGroupID = "";
            this.lblGroupType = "";
            this.ddGroupId = "";
            this.ddGroupType = "";
            listGroupId = new ArrayList<>();
            listGroupType = new ArrayList<>();
            listGroupId.add(new SelectItem("0", "--Select--"));
            listGroupId.add(new SelectItem("1", "Own As A Group"));
            listGroupId.add(new SelectItem("2", "Under A Group"));
            List listForGroupType = aitr.groupIdList();
            listGroupType.add(new SelectItem("0", "--Select--"));
            if (!listForGroupType.isEmpty()) {
                for (int e = 0; e < listForGroupType.size(); e++) {
                    Vector evect = (Vector) listForGroupType.get(e);
                    listGroupType.add(new SelectItem(evect.get(0).toString(), evect.get(0).toString().concat("(").concat(evect.get(1).toString()).concat(")")));
                }
            }
            onblurGroupId();
            /**
             * END OF SECURITY DETAIL*
             */
            onActionBusinessIndustryType();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void onblurRelationVal() {
        try {
            if (relation.equalsIgnoreCase("DIRREL") || relation.equalsIgnoreCase("MGRREL") || relation.equalsIgnoreCase("SECREL")) {
                this.disableDirPanel = "";
            } else {
                this.disableDirPanel = "none";
            }
            List resultList = new ArrayList();
            relOwnerOption = new ArrayList<SelectItem>();
            relOwnerOption.add(new SelectItem("0", ""));
//            if(this.getRelation().equalsIgnoreCase("DIRREL")) {
//                resultList = aitr.refRecCode("004");
            resultList = aitr.getListAsPerRequirement("004", getRelation(), "0", "0", "0", "0", ymd.format(new Date()), 0);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    relOwnerOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
//            } else {
//                relOwnerOption.add(new SelectItem("SEL","SELF"));
//            }
//            relNameData(this.getRelation());
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurSectorVal() {
        try {
            List resultList = new ArrayList();
            List listForSubSector = new ArrayList();
            listForSubSector = aitr.getListAsPerRequirement("183", getSector(), "0", "0", "0", "0", ymd.format(new Date()), Float.parseFloat(getSancAmount()));
            subSectorOption = new ArrayList<SelectItem>();
            subSectorOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSubSector.size(); i++) {
                Vector element = (Vector) listForSubSector.get(i);
                subSectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurIndustry() {
        try {
            List industryWise = CommonReportMethodsBean.getRefRecList("445");
            if (!industryWise.isEmpty()) {
                industryOption = new ArrayList<SelectItem>();
                industryOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < industryWise.size(); i++) {
                    Vector ele1 = (Vector) industryWise.get(i);
                    industryOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onActionBusinessIndustryType() {
        try {
            List industryWise = CommonReportMethodsBean.getRefRecList("555");
            if (!industryWise.isEmpty()) {
                businessIndustryTypeList = new ArrayList<SelectItem>();
                businessIndustryTypeList.add(new SelectItem("", "--Select--"));
                for (int i = 0; i < industryWise.size(); i++) {
                    Vector ele1 = (Vector) industryWise.get(i);
                    businessIndustryTypeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurModeOfAdvanceVal() {
        try {
            List listForPurposeOfAdvanceList = new ArrayList();
            listForPurposeOfAdvanceList = aitr.getListAsPerRequirement("184", getSector(), getSubSector(), getModeOfAdvance(), "0", "0", ymd.format(new Date()), Float.parseFloat(getSancAmount()));
            purposeOfAdvanceOption = new ArrayList<SelectItem>();
            purposeOfAdvanceOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForPurposeOfAdvanceList.size(); i++) {
                Vector element = (Vector) listForPurposeOfAdvanceList.get(i);
                purposeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            List listForGuarCoverList = new ArrayList();
            listForGuarCoverList = aitr.getListAsPerRequirement("188", getSector(), getSubSector(), getModeOfAdvance(), "0", "0", ymd.format(new Date()), 0);
            guarnteeCoverOption = new ArrayList<SelectItem>();
            guarnteeCoverOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForGuarCoverList.size(); i++) {
                Vector element = (Vector) listForGuarCoverList.get(i);
                guarnteeCoverOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onBlurPurposeOfAdvVal() {
        try {
            List listForExposure = new ArrayList();
            listForExposure = aitr.getListAsPerRequirement("191", getSickness(), "0", "0", "0", "0", ymd.format(new Date()), 0);
            exposureOption = new ArrayList<SelectItem>();
            exposureCategroyOption = new ArrayList<SelectItem>();
            String exposure = "";
            for (int i = 0; i < listForExposure.size(); i++) {
                Vector element = (Vector) listForExposure.get(i);
                exposureOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                exposure = element.get(0).toString();
            }
            setExposure(exposure);
            List listForExposureCategory = new ArrayList();
            String exposureCategory = "";
            listForExposureCategory = aitr.getListAsPerRequirement("230", getSickness(), exposure, "0", "0", "0", ymd.format(new Date()), 0);
            for (int i = 0; i < listForExposureCategory.size(); i++) {
                Vector element = (Vector) listForExposureCategory.get(i);
                exposureCategory = element.get(0).toString();
                exposureCategroyOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            setExposureCategory(exposureCategory);
            onblurExposureVal();


            onblurExposureCategoryVal();
            List industryWise = CommonReportMethodsBean.getRefRecList("445");
            if (!industryWise.isEmpty()) {
                industryOption = new ArrayList<SelectItem>();
                industryOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < industryWise.size(); i++) {
                    Vector ele1 = (Vector) industryWise.get(i);
                    industryOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurExposureVal() {
        try {
            List listForExposureCategory = new ArrayList();
            onblurExposureCategoryVal();
            if (!getExposureCategory().equalsIgnoreCase("0")) {
                setExposureCategory("0");
            }
            listForExposureCategory = aitr.getListAsPerRequirement("230", getExposure(), getExposureCategory(), getExposureCategoryPurpose(), "0", "0", ymd.format(new Date()), 0);
            exposureCategroyOption = new ArrayList<SelectItem>();
            exposureCategroyOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForExposureCategory.size(); i++) {
                Vector element = (Vector) listForExposureCategory.get(i);
                exposureCategroyOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////////
            List listForExposureCatePurpose = new ArrayList();
            listForExposureCatePurpose = aitr.getListAsPerRequirement("231", getExposure(), getExposureCategory(), getExposureCategoryPurpose(), "0", "0", ymd.format(new Date()), 0);
            exposureCatePurposeOption = new ArrayList<SelectItem>();
            exposureCatePurposeOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForExposureCatePurpose.size(); i++) {
                Vector element = (Vector) listForExposureCatePurpose.get(i);
                exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurExposureCategoryVal() {
        try {
            List listForExposureCatePurpose = new ArrayList();
            listForExposureCatePurpose = aitr.getListAsPerRequirement("231", getExposure(), getExposureCategory(), getExposureCategoryPurpose(), "0", "0", ymd.format(new Date()), 0);
            exposureCatePurposeOption = new ArrayList<SelectItem>();
            exposureCatePurposeOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForExposureCatePurpose.size(); i++) {
                Vector element = (Vector) listForExposureCatePurpose.get(i);
                exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void relNameData(String relCode) {
        try {
            List resultList = new ArrayList();
            relNameOption = new ArrayList<SelectItem>();
            relNameOption.add(new SelectItem(" ", " "));
            if (this.getRelation().equalsIgnoreCase("DIR")) {
                resultList = aitr.relNameDetail(relCode);
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        relNameOption.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                    }
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void actTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.actTypeCombo();
            if (resultList.isEmpty()) {
                acTypeList = new ArrayList<SelectItem>();
                acTypeList.add(new SelectItem("--Select--"));
                return;
            } else {
                acTypeList = new ArrayList<SelectItem>();
                acTypeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        acTypeList.add(new SelectItem(ee.toString()));
                    }
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void docListCombo() {
        try {
            List resultList = new ArrayList();
            String accountCode = fts.getAccountNature(acNo);
            resultList = loanAcDetails.docListCombo(accountCode);
            //  System.out.println("resultList -->" + resultList);
            if (resultList.isEmpty()) {
                docList = new ArrayList<SelectItem>();
                docList.add(new SelectItem("--Select--"));
                return;
            } else {
                docList = new ArrayList<SelectItem>();
                docList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        docList.add(new SelectItem(ee.toString()));
                    }
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getAccountDetail() {
        this.setErrorMessage("");
        this.setMessage("");
//        if (this.acctType.equalsIgnoreCase("--Select--")) {
//            this.setErrorMessage("Please Select Account Type.");
//            return;
//        }
        if (this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
            this.setErrorMessage("Please Enter Account No.");
            return;
        }
        if (!oldAcctNo.matches("[0-9a-zA-z]*")) {
            this.setErrorMessage("Please Enter Valid  Account No.");
            return;
        }
        //if (oldAcctNo.length() != 12) {
        if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setErrorMessage("Account number is not valid.");
            return;
        }

        try {
            acctNo = fts.getNewAccountNumber(oldAcctNo);
            String currentBrnCode = fts.getCurrentBrnCode(acctNo);
            if (!currentBrnCode.equalsIgnoreCase(getOrgnBrCode()) && !functionFlag.equalsIgnoreCase("I")) {
                setErrorMessage("Sorry! You can not save/update other branch's account details.");
                return;
            }
            setAcctNo(acctNo);
            docList = new ArrayList<SelectItem>();
            docList.add(new SelectItem("--Select--"));
            this.setTabDisableFlag(true);
            String code = this.acctNo;
            int length = code.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                code = "0" + code;
            }
//        acNo = this.orgnBrCode + this.acctType + code + this.agentCode;
//        acno1 = this.orgnBrCode + this.acctType + code + this.agentCode;
//            businessIndustryTypeList = new ArrayList<SelectItem>();
//            businessIndustryTypeList.add(new SelectItem("--Select--"));
            List entitylist = CommonReportMethodsBean.getCustEntityType(acctNo);
            Vector vtr = (Vector) entitylist.get(0);
            if (vtr.get(0).toString().equalsIgnoreCase("02")) {
                this.businessIndustryTypeInvisiable = "";
                //onActionBusinessIndustryType();

            } else {
                this.businessIndustryTypeInvisiable = "none";
            }
            acNo = acctNo;
            acno1 = acctNo;
            if (!((CommonReportMethodsBean.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.TERM_LOAN))
                    || (CommonReportMethodsBean.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                    || (CommonReportMethodsBean.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.CURRENT_AC)))) {
                this.setErrorMessage("Invalid account number.");
                return;
            }
            List acctInfo = loanAcDetails.accountDetail(acNo);
            //   System.out.println("acctInfo -->" + acctInfo);
            if (acctInfo.isEmpty()) {
                gridDetail = new ArrayList<EmploymentDetailsGrid>();
                gridDetail1 = new ArrayList<LegalDocumentsGrid>();
                gridDetail2 = new ArrayList<GuarantorDetailGrid>();
                gridDetail3 = new ArrayList<InsuranceDetailGrid>();
                gridDetail4 = new ArrayList<CompanyDetailGridLoad>();
                gridDetail5 = new ArrayList<CompanyDetailGrid>();
                disbstDtl = new ArrayList<DisbursementDetail>();
                instPlan = new ArrayList<InstallmentPlan>();
                disbstSch = new ArrayList<DisbursementSchedule>();
                this.setErrorMessage("Account No. Does Not Exist.");
                this.setTabDisableFlag(true);
                this.setPartyName("");
                this.setAcNo("");
                this.setAcOpenDt("");

                this.setAppShareMoney("");
                this.setNominee("");
                this.setShareType("");
                this.setNominalMem("");
                this.setNominalMemNo("");
                resestDisburestment();
                Date orgnDt = new Date();
                this.setMembershipDt(orgnDt);
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
                        gridDetail = new ArrayList<EmploymentDetailsGrid>();
                        this.setErrorMessage("Account has been closed.");
                        gridDetail = new ArrayList<EmploymentDetailsGrid>();
                        gridDetail1 = new ArrayList<LegalDocumentsGrid>();
                        gridDetail2 = new ArrayList<GuarantorDetailGrid>();
                        gridDetail3 = new ArrayList<InsuranceDetailGrid>();
                        gridDetail4 = new ArrayList<CompanyDetailGridLoad>();
                        gridDetail5 = new ArrayList<CompanyDetailGrid>();
                        instPlan = new ArrayList<InstallmentPlan>();
                        disbstDtl = new ArrayList<DisbursementDetail>();
                        disbstSch = new ArrayList<DisbursementSchedule>();
                        this.setTabDisableFlag(true);
                        this.setPartyName("");
                        this.setAcOpenDt("");
                        this.setName("");
                        this.setAppShareMoney("");
                        this.setNominee("");
                        this.setShareType("");
                        this.setNominalMem("");
                        this.setNominalMemNo("");
                        Date orgnDt = new Date();
                        this.setMembershipDt(orgnDt);
                        this.lblGroupID = "none";
                        this.lblGroupType = "none";
                        this.ddGroupId = "none";
                        this.ddGroupType = "none";
                        getInterestDetails();
                        roiOnSecurity = loanInterestCalculationBean.getROI(intTable, Double.parseDouble(ele.get(4).toString()), ymd.format(date));
                        additionalRoi = "0";
                        this.setPartyName(ele.get(1).toString());
                        this.setName(ele.get(1).toString());
                        this.setAcNo(ele.get(0).toString());
                        this.setAcOpenDt(ele.get(2).toString());
                        this.setSanctionAmt(ele.get(4).toString());
                        this.setSancAmount(ele.get(4).toString());
                        this.setLoanDuration(ele.get(5).toString());
                        custId = ele.get(9).toString();
                        custId1 = ele.get(10).toString();
                        custId2 = ele.get(11).toString();
                        custId3 = ele.get(12).toString();
                        custId4 = ele.get(13).toString();
                        viewDataCall();
                        gridLoad();
                        docListCombo();
                        gridLoadForLegalDocs();
                        setValuesOnLoanShareDetailForm();
                        gridLoadForGauranterDetail();
                        gridLoadForInsuranceDetail();
                        gridLoadForGroupCompanyDetail();
                        onLoadCOmpanyDetails();
                        setValuesInAllFields();
                        securityDetailsTableValues();
                        marginLoad();
                        disableRetireAge();
                        List listForacNo = aitr.chkAcNo(acNo);
                        if (!listForacNo.isEmpty()) {
                            Vector vect = (Vector) listForacNo.get(0);
                            setRetirementAge(vect.get(49).toString());
                        }
                        onblurIndustry();
                        //onActionBusinessIndustryType();
                        this.lblGroupID = "";
                        this.lblGroupType = "";
                        this.ddGroupId = "";
                        this.ddGroupType = "";
                        listGroupId = new ArrayList<>();
                        listGroupType = new ArrayList<>();
                        listGroupId.add(new SelectItem("0", "--Select--"));
                        listGroupId.add(new SelectItem("1", "Own As A Group"));
                        listGroupId.add(new SelectItem("2", "Under A Group"));
                        List listForGroupType = aitr.groupIdList();
                        if (functionFlag.equalsIgnoreCase("A")) {
                            listGroupType.add(new SelectItem("0", "--Select--"));
                            if (!listForGroupType.isEmpty()) {
                                for (int e = 0; e < listForGroupType.size(); e++) {
                                    Vector evect = (Vector) listForGroupType.get(e);
                                    listGroupType.add(new SelectItem(evect.get(0).toString(), evect.get(0).toString().concat("(").concat(evect.get(1).toString()).concat(")")));
                                }
                            }
                        } else if (functionFlag.equalsIgnoreCase("M")) {
                            listGroupType.clear();
                            if (groupID.equalsIgnoreCase("0")) {
                                this.groupType = "0";
                            } else if (groupID.equalsIgnoreCase("1")) {
                                List list = CommonReportMethodsBean.getCustIdfromacNo(acNo);
                                listGroupType.add(new SelectItem(list.get(0).toString(), list.get(0).toString()));
                            } else if (groupID.equalsIgnoreCase("2")) {
                                if (!listForGroupType.isEmpty()) {
                                    for (int e = 0; e < listForGroupType.size(); e++) {
                                        Vector evect = (Vector) listForGroupType.get(e);
                                        listGroupType.add(new SelectItem(evect.get(0).toString(), evect.get(0).toString().concat("(").concat(evect.get(1).toString()).concat(")")));
                                    }
                                }
                            }
                        }

                        if (this.functionFlag.equalsIgnoreCase("A")) {
                            this.setErrorMessage("Sorry, This Account can not be addded because Account has been closed!!!");
                        }
                        if (this.functionFlag.equalsIgnoreCase("M")) {
                            this.setErrorMessage("Sorry, This Account can not be modified because Account has been closed!!!");
                        }
                        if (this.functionFlag.equalsIgnoreCase("I")) {
                            this.setErrorMessage("This Account has been Closed!!!");
                        }
                        flag2 = true;
                        return;
                    } else {
                        this.setTabDisableFlag(false);
                        getInterestDetails();
                        roiOnSecurity = loanInterestCalculationBean.getROI(intTable, Double.parseDouble(ele.get(4).toString()), ymd.format(date));
                        additionalRoi = "0";
                        this.setPartyName(ele.get(1).toString());
                        this.setName(ele.get(1).toString());
                        this.setAcNo(ele.get(0).toString());
                        this.setAcOpenDt(ele.get(2).toString());
                        this.setSanctionAmt(ele.get(4).toString());
                        this.setSancAmount(ele.get(4).toString());
                        this.setLoanDuration(ele.get(5).toString());
                        custId = ele.get(9).toString();
                        custId1 = ele.get(10).toString();
                        custId2 = ele.get(11).toString();
                        custId3 = ele.get(12).toString();
                        custId4 = ele.get(13).toString();
                        disableRetireAge();
                        List listForacNo = aitr.chkAcNo(acNo);
                        if (!listForacNo.isEmpty()) {
                            Vector vect = (Vector) listForacNo.get(0);
                            setRetirementAge(vect.get(49).toString());
                            if (this.functionFlag.equalsIgnoreCase("A")) {
                                this.setErrorMessage("This A/c. Is Already Exists In Borrower Detail So In Borrower Detail You Can Only Modify Or Enquiry By Using Function M Or I.");
                                return;
                            }
                        }

                        viewDataCall();
                        gridLoad();
                        docListCombo();
                        gridLoadForLegalDocs();

                        setValuesOnLoanShareDetailForm();
                        gridLoadForGauranterDetail();
                        gridLoadForInsuranceDetail();
                        gridLoadForGroupCompanyDetail();
                        onLoadCOmpanyDetails();
                        setValuesInAllFields();
                        securityDetailsTableValues();
                        marginLoad();
                        onblurIndustry();
                        // onActionBusinessIndustryType();
//                        this.lblGroupID="none";
//                        this.lblGroupType="none";
//                        this.ddGroupId="none";
//                        this.ddGroupType="none";
                        listGroupId = new ArrayList<>();
                        listGroupType = new ArrayList<>();
//                        List chkList = aitr.custIdDetail(custId);
//                        if(!chkList.isEmpty()) {
//                            Vector vect =(Vector) chkList.get(0);
//                            if(vect.get(0).toString().equalsIgnoreCase("02")) {
                        this.lblGroupID = "";
                        this.lblGroupType = "";
                        this.ddGroupId = "";
                        this.ddGroupType = "";
                        listGroupId = new ArrayList<>();
                        listGroupType = new ArrayList<>();
                        listGroupId.add(new SelectItem("0", "--Select--"));
                        listGroupId.add(new SelectItem("1", "Own As A Group"));
                        listGroupId.add(new SelectItem("2", "Under A Group"));
                        List listForGroupType = aitr.groupIdList();
                        listGroupType.add(new SelectItem("0", "--Select--"));
                        if (!listForGroupType.isEmpty()) {
                            for (int e = 0; e < listForGroupType.size(); e++) {
                                Vector evect = (Vector) listForGroupType.get(e);
                                listGroupType.add(new SelectItem(evect.get(0).toString(), evect.get(0).toString().concat("(").concat(evect.get(1).toString()).concat(")")));
                            }
                        }
                        if (functionFlag.equalsIgnoreCase("M")) {
                            listGroupType.clear();
                            if (groupID.equalsIgnoreCase("0")) {
                                this.groupType = "0";
                            } else if (groupID.equalsIgnoreCase("1")) {
                                List list = CommonReportMethodsBean.getCustIdfromacNo(acNo);
                                Vector vec = (Vector) list.get(0);
                                listGroupType.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
                            } else if (groupID.equalsIgnoreCase("2")) {
                                if (!listForGroupType.isEmpty()) {
                                    for (int e = 0; e < listForGroupType.size(); e++) {
                                        Vector evect = (Vector) listForGroupType.get(e);
                                        listGroupType.add(new SelectItem(evect.get(0).toString(), evect.get(0).toString().concat("(").concat(evect.get(1).toString()).concat(")")));
                                    }
                                }
                            }
                        }

                        this.disableDirPanel = "none";
                        if (this.functionFlag.equalsIgnoreCase("A") || this.functionFlag.equalsIgnoreCase("M") || this.functionFlag.equalsIgnoreCase("I")) {
                            List listForRelation = new ArrayList();
                            listForRelation = aitr.getReferenceCode("210");
                            relationOption = new ArrayList<SelectItem>();
                            relationOption.add(new SelectItem("0", ""));
                            for (int j = 0; j < listForRelation.size(); j++) {
                                Vector element = (Vector) listForRelation.get(j);
                                relationOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                            }
                            List dirRelList = aitr.dirRelList(this.custId, ymd.format(new Date()));
                            if (!dirRelList.isEmpty()) {
                                for (int s = 0; s < dirRelList.size(); s++) {
                                    Vector vect = (Vector) dirRelList.get(s);
                                    this.setRelation(vect.get(2).toString());
                                    this.setRelOwner(vect.get(1).toString());
                                    relOwnerOption = new ArrayList<SelectItem>();
                                    relationOption = new ArrayList<SelectItem>();
                                    relationOption.add(new SelectItem(vect.get(2).toString(), vect.get(5).toString()));
                                    relOwnerOption.add(new SelectItem(vect.get(1).toString(), vect.get(4).toString()));
                                    if (this.relation.equalsIgnoreCase("DIRREL") || this.relation.equalsIgnoreCase("MGRREL") || this.relation.equalsIgnoreCase("SECREL")) {
                                        this.disableDirPanel = "";
                                        this.setDirCustId(vect.get(3).toString());
                                        String custinfo = openingFacadeRemote.custIdInformation(vect.get(3).toString());
                                        flag = "false";
                                        String[] values = null;
                                        String spliter = ": ";
                                        values = custinfo.split(spliter);
                                        String title = values[0];
                                        String custNames = values[1];
                                        setDirName(custNames);
                                    }
                                }
                                this.disableRel = true;
                                this.disableRelOwn = true;
                            }
                        }
//                                viewDataCall();
//                            }
//                        }
                    }
                }
            }
            String tlLimitUpdation = "N";
            List tlLimitUpdationList = aitr.getCbsParameterinfoValue("TL_LIMIT_INCREASE");
            if (!tlLimitUpdationList.isEmpty()) {
                Vector tlLimitUpdationVect = (Vector) tlLimitUpdationList.get(0);
                tlLimitUpdation = tlLimitUpdationVect.get(0).toString();
            }
            if (tlLimitUpdation.equalsIgnoreCase("Y") && (CommonReportMethodsBean.getAcNatureByAcType(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                this.disableSancAmt = false;
            } else {
                this.disableSancAmt = true;
            }
            onblurGroupId();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getInterestDetails() {
        try {
            String code = "", intcode = "";
//            if (functionFlag.equalsIgnoreCase("A")) {
            List list = aitr.getIntCodeIntTypeSchmCode(acNo);
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
                this.setSancAmount(vec.get(5).toString());
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

    public void gridLoad() {
        this.setTabDisableFlag(false);
        gridDetail = new ArrayList<EmploymentDetailsGrid>();
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.gridDetail(acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    EmploymentDetailsGrid detail = new EmploymentDetailsGrid();
                    detail.setsNo(ele.get(9).toString());
                    detail.setFirmName(ele.get(0).toString());
                    detail.setFirmAdd(ele.get(1).toString());
                    detail.setFirmPhoneNo(ele.get(2).toString());
                    detail.setFromDt(ele.get(3).toString());
                    detail.setToDt(ele.get(4).toString());
                    detail.setDesignation(ele.get(5).toString());
                    detail.setMonthlyIncome(new BigDecimal(Float.parseFloat(ele.get(6).toString())));
                    detail.setReasonToQuit(ele.get(7).toString());
                    detail.setEmpId(ele.get(8).toString());
                    gridDetail.add(detail);
                }
            } else {
                gridDetail = new ArrayList<EmploymentDetailsGrid>();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }

    }

    public void gridLoadForLegalDocs() {
        this.setTabDisableFlag(false);
        gridDetail1 = new ArrayList<LegalDocumentsGrid>();
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.legalDocsGridDetail(acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LegalDocumentsGrid detail = new LegalDocumentsGrid();
                    detail.setAcNo(ele.get(0).toString());
                    List docN = loanAcDetails.legalDocsNameDetail(ele.get(1).toString());
                    Vector ele1 = (Vector) docN.get(0);
                    detail.setDocCode(ele1.get(0).toString());
                    detail.setDueDate(ele.get(2).toString());
                    detail.setRecieveDt(ele.get(3).toString());
                    detail.setExpDt(ele.get(4).toString());
                    detail.setRemarks(ele.get(5).toString());
                    detail.setDelFlag(ele.get(6).toString());
                    detail.setScanFlag(ele.get(7).toString());
                    gridDetail1.add(detail);
                }
            } else {
                gridDetail1 = new ArrayList<LegalDocumentsGrid>();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.firmName.equalsIgnoreCase("") || this.firmName.length() == 0) {
                this.setErrorMessage("Please Enter Customer Employer Name.");
                return;
            }
            if (this.comAdd.equalsIgnoreCase("") || this.comAdd.length() == 0) {
                this.setErrorMessage("Please Enter Customer Employer Address.");
                return;
            }
            if (this.designation.equalsIgnoreCase("") || this.designation.length() == 0) {
                this.setErrorMessage("Please Enter Designation.");
                return;
            }
            if (this.empId.equalsIgnoreCase("") || this.empId.length() == 0) {
                this.setErrorMessage("Please Enter Employee ID.");
                return;
            }
            Pattern spl = Pattern.compile("([0-9]+)");
            if (this.comPhoneNo.equalsIgnoreCase("") || this.comPhoneNo.length() == 0) {
                this.setErrorMessage("Please Enter Company Phone No.");
                return;
            }
            Matcher comPhoneNoCheck = spl.matcher(this.comPhoneNo);
            if (!comPhoneNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Company Phone No.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.monthlyIncome.equalsIgnoreCase("") || this.monthlyIncome.length() == 0) {
                this.setErrorMessage("Please Enter Monthly Income.");
                return;
            }
            Matcher monthlyIncomeCheck = p.matcher(monthlyIncome);
            if (!monthlyIncomeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Monthly Income.");
                return;
            }
            if (Float.parseFloat(monthlyIncome) < 0) {
                this.setErrorMessage("Please Enter Valid Monthly Income.");
                return;
            }
            if (this.reasonOfLeaving.equalsIgnoreCase("") || this.reasonOfLeaving.length() == 0) {
                this.setErrorMessage("Please Enter Reason Of Leaving");
                return;
            }
            if (this.fromDt == null) {
                this.setErrorMessage("Please Enter From Date.");
                return;
            }
            if (this.toDt == null) {
                this.setErrorMessage("Please Enter To Date.");
                return;
            }
            //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = loanAcDetails.saveEmploymentDetail(this.orgnBrCode, this.acNo, this.firmName, this.comAdd, this.comPhoneNo, this.designation, this.monthlyIncome, ymd.format(this.fromDt), ymd.format(this.toDt), this.reasonOfLeaving, this.empId, this.userName, todayDate.substring(6) + todayDate.substring(3, 5) + todayDate.substring(0, 2));
            if (result == null) {
                this.setErrorMessage("Not Saved");
                gridLoad();
                return;
            } else {
                if (result.equalsIgnoreCase("Record Saved Successfully")) {
                    this.setMessage("Record Saved Successfully.");
                    this.setFirmName("");
                    this.setComAdd("");
                    this.setComPhoneNo("");
                    this.setMonthlyIncome("");
                    this.setDesignation("");
                    this.setEmpId("");
                    this.setReasonOfLeaving("");
                    //Date date = new Date();
                    this.setFromDt(date);
                    this.setToDt(date);
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    gridLoad();
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (EmploymentDetailsGrid item : gridDetail) {
            if (item.getsNo().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = loanAcDetails.deleteRecord(orgnBrCode, this.acNo, currentItem.getsNo());
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
                this.setFirmName("");
                this.setComAdd("");
                this.setComPhoneNo("");
                this.setMonthlyIncome("");
                this.setDesignation("");
                this.setEmpId("");
                this.setReasonOfLeaving("");
                //Date date = new Date();
                this.setFromDt(date);
                this.setToDt(date);
                gridLoad();
            } else {
                this.setErrorMessage("Record Not Deleted.");
                this.setFirmName("");
                this.setComAdd("");
                this.setComPhoneNo("");
                this.setMonthlyIncome("");
                this.setDesignation("");
                this.setEmpId("");
                this.setReasonOfLeaving("");
                // Date date = new Date();
                this.setFromDt(date);
                this.setToDt(date);
                gridLoad();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetEmpDetails() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFirmName("");
        this.setComAdd("");
        this.setComPhoneNo("");
        this.setMonthlyIncome("");
        this.setDesignation("");
        this.setEmpId("");
        this.setReasonOfLeaving("");
        //Date date = new Date();
        this.setFromDt(date);
        this.setToDt(date);
    }

    public void saveDocumentDetails() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (this.docCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Document.");
                return;
            }
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.delFlag.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Del Doc.");
                return;
            }
            if (this.scanFlag.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Scan Doc.");
                return;
            }
            if (this.forOtherDetails.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select For Other Details.");
                return;
            }
            if (this.forOtherDetails.equalsIgnoreCase("Yes")) {
                if (this.freeText1.equalsIgnoreCase("") || this.freeText1.length() == 0) {
                    this.setErrorMessage("Please Enter Other Details..");
                    return;
                }
            }
            if (this.dueDt == null) {
                this.setErrorMessage("Please Enter Due Date.");
                return;
            }
            if (this.recDt == null) {
                this.setErrorMessage("Please Enter Recieve Date.");
                return;
            }
            if (this.expDt == null) {
                this.setErrorMessage("Please Enter Expiry Date.");
                return;
            }

            String result = loanAcDetails.saveDocumentDetails(this.orgnBrCode, this.acNo, this.docCode, ymd.format(this.dueDt), ymd.format(this.expDt), ymd.format(this.recDt), this.remarks, this.delFlag, this.scanFlag, this.freeText1, this.freeText2, this.freeText3, this.freeText4, this.freeText5, this.freeText6, this.freeText7, this.freeText8, this.freeText9, this.freeText10);
            if (result == null) {
                this.setErrorMessage("Not Saved");
                gridLoadForLegalDocs();
                return;
            } else {
                if (result.equalsIgnoreCase("Saved Succesfully")) {
                    this.setMessage("Document Detail Saved Succesfully.");
//                    Date date = new Date();
                    this.setDueDt(date);
                    this.setExpDt(date);
                    this.setRecDt(date);
                    this.setDocCode("--Select--");
                    this.setDelFlag("--Select--");
                    this.setScanFlag("--Select--");
                    this.setForOtherDetails("--Select--");
                    this.setRemarks("");
                    gridLoadForLegalDocs();
                } else {
                    this.setErrorMessage(result);
                    gridLoadForLegalDocs();
                    return;
                }

            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void forOtherDetailsMethod() {
        this.setFreeText1("");
        this.setFreeText2("");
        this.setFreeText3("");
        this.setFreeText4("");
        this.setFreeText5("");
        this.setFreeText6("");
        this.setFreeText7("");
        this.setFreeText8("");
        this.setFreeText9("");
        this.setFreeText10("");
        if (this.forOtherDetails.equalsIgnoreCase("--Select--")) {
            this.setOtherDetailFlag(false);
        }
        if (this.forOtherDetails.equalsIgnoreCase("Yes")) {
            this.setOtherDetailFlag(true);
        }
        if (this.forOtherDetails.equalsIgnoreCase("No")) {
            this.setOtherDetailFlag(false);
        }
    }

    public void fetchCurrentRow1(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (LegalDocumentsGrid item : gridDetail1) {
            if (item.getDocCode().equalsIgnoreCase(ac)) {
                currentItem1 = item;
            }
        }
    }

    public void deleteRecordOfDocuments() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = loanAcDetails.deleteRecordFromLoanAcDoctable(this.orgnBrCode, this.acNo, currentItem1.getDocCode());
            if (result == null) {
                this.setErrorMessage("Not Deleted");
                return;
            } else {
                if (result.equals("Document Record Deleted Successfully")) {
                    this.setMessage(result);
                    //Date date = new Date();
                    this.setDueDt(date);
                    this.setExpDt(date);
                    this.setRecDt(date);
                    this.setDocCode("--Select--");
                    this.setDelFlag("--Select--");
                    this.setScanFlag("--Select--");
                    this.setForOtherDetails("--Select--");
                    this.setRemarks("");
                    gridLoadForLegalDocs();
                } else {
                    this.setErrorMessage(result);
                    //   Date date = new Date();
                    this.setDueDt(date);
                    this.setExpDt(date);
                    this.setRecDt(date);
                    this.setDocCode("--Select--");
                    this.setDelFlag("--Select--");
                    this.setScanFlag("--Select--");
                    this.setForOtherDetails("--Select--");
                    this.setRemarks("");
                    gridLoadForLegalDocs();
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetDocumentDetailForm() {
        this.setErrorMessage("");
        this.setMessage("");
        //Date date = new Date();
        this.setDueDt(date);
        this.setExpDt(date);
        this.setRecDt(date);
        this.setDocCode("--Select--");
        this.setDelFlag("--Select--");
        this.setScanFlag("--Select--");
        this.setForOtherDetails("--Select--");
        this.setRemarks("");
    }

    public void setValuesOnLoanShareDetailForm() {
        try {
            String result = loanAcDetails.loanShareDtOnLoad(this.acNo);
            if (result == null || result.equalsIgnoreCase("")) {
                return;
            } else {
                if (result.contains(":")) {
                    String[] values = null;
                    try {
                        String spliter = ":";
                        values = result.split(spliter);
                        this.setAppShareMoney(new BigDecimal(Double.parseDouble(values[0].toString())).toString());
                        //this.setAppShareMoney(    new BigDecimal(values[0].toString()).toString());
                        this.setNominee(values[1]);
                        this.setShareType(values[2]);
                        this.setNominalMem(values[3]);
                        this.setNominalMemNo(values[4]);
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date orgnDt = sdf.parse(values[5]);
                        this.setMembershipDt(orgnDt);
                    } catch (Exception e) {
                        message = e.getMessage();
                    }
                } else {
                    this.setErrorMessage(result);
                    this.setMessage("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void setMemberNoLable() {
        if (this.nominalMem.equalsIgnoreCase("--Select--")) {
            this.setNomMemLable("Member No. :");
        }
        if (this.nominalMem.equalsIgnoreCase("Yes")) {
            this.setNomMemLable("Nominal Member No. :");
        }
        if (this.nominalMem.equalsIgnoreCase("No")) {
            this.setNomMemLable("Member No. :");
        }

    }

    public void saveLoanShareDetails() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (this.shareType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Share Type.");
                return;
            }
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.appShareMoney.equalsIgnoreCase("") || this.appShareMoney.length() == 0) {
                this.setErrorMessage("Please Enter Share Money.");
                return;
            }
            Matcher appShareMoneyCheck = p.matcher(appShareMoney);
            if (!appShareMoneyCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Share Money.");
                return;
            }
            if (Float.parseFloat(appShareMoney) < 0) {
                this.setErrorMessage("Valid Share Money Cannot be Less Than 0.");
                return;
            }
            if (this.nominee.equalsIgnoreCase("") || this.nominee.length() == 0) {
                this.setErrorMessage("Please Enter Nominee Name.");
                return;
            }
            if (this.nominalMem.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Nominal Member.");
                return;
            }
            if (this.nominalMemNo.equalsIgnoreCase("") || this.nominalMemNo.length() == 0) {
                this.setErrorMessage("Please Enter Nominee Member No.");
                return;
            }
            if (this.membershipDt == null) {
                this.setErrorMessage("Please Enter Membership Date.");
                return;
            }
            //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            String result = loanAcDetails.loanShareDtSave(this.acNo, this.shareType.substring(0, 1), Float.parseFloat(this.appShareMoney), this.nominee, ymd.format(this.membershipDt), this.nominalMem.substring(0, 1), this.nominalMemNo);
            if (result == null) {
                this.setErrorMessage("Not Updated");
                return;
            } else {
                if (result.equals("DATA UPDATED SUCCESSFULLY")) {
                    this.setMessage(result);
                    //   Date date = new Date();
                    this.setMembershipDt(date);
                    this.setShareType("--Select--");
                    this.setNominalMem("--Select--");
                    this.setAppShareMoney("");
                    this.setNominee("");
                    this.setNominalMemNo("");
                } else {
                    this.setErrorMessage(result);
                    //   Date date = new Date();
                    this.setMembershipDt(date);
                    this.setShareType("--Select--");
                    this.setNominalMem("--Select--");
                    this.setAppShareMoney("");
                    this.setNominee("");
                    this.setNominalMemNo("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetLoanShareDetailForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setMembershipDt(date);
        this.setShareType("--Select--");
        this.setNominalMem("--Select--");
        this.setAppShareMoney("");
        this.setNominee("");
        this.setNominalMemNo("");
        this.setNomMemLable("Member No. :");
    }

    public void gridLoadForGauranterDetail() {
        gridDetail2 = new ArrayList<GuarantorDetailGrid>();
        try {
            List resultList = new ArrayList();
            if (this.functionFlag.equalsIgnoreCase("I") || this.functionFlag.equalsIgnoreCase("M")) {
                this.acNo = acno1;
            }
            resultList = loanAcDetails.loanGuarantorDtOnLoad(this.acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    GuarantorDetailGrid detail = new GuarantorDetailGrid();
                    detail.setGuarName(ele.get(0).toString());
                    detail.setGuarFatherName(ele.get(1).toString());
                    detail.setGuarAge(ele.get(2).toString());
                    detail.setGuarRetAge(ele.get(3).toString());
                    detail.setGuarAddress(ele.get(4).toString());
                    detail.setGuarPhoneNo(ele.get(5).toString());
                    detail.setGuarOccupation(ele.get(6).toString());
                    detail.setGuarFirmName(ele.get(7).toString());
                    detail.setGuarfirmAdd(ele.get(8).toString());
                    detail.setGuarFirmPhoneNo(ele.get(9).toString());
                    detail.setGuarDesignation(ele.get(10).toString());
                    detail.setGuarNetWorth(new BigDecimal(ele.get(11).toString()).toString());
                    detail.setGuarAcNo(ele.get(12).toString());
                    detail.setGuarCustFlag(ele.get(13).toString());
                    gridDetail2.add(detail);
                }
            } else {
                gridDetail2 = new ArrayList<GuarantorDetailGrid>();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void guarontorDetailIfCustomer() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setGuarName("");
        this.setGuarAddress("");
        this.setGuarFatherName("");
        this.setGuarPhoneNo("");
        this.setGuarAge("");
        this.setGuarFlag1(false);
        this.setGuarFlag2(false);
        this.setGuarFlag3(false);
        this.setGuarFlag4(false);
        this.setGuarFlag5(false);
        try {
            String accSeq = CommonReportMethodsBean.getAccseq();
            String bnkCode = fts.getBankCode();
            popupGuartFlag = "false";
            if (this.guarAcChk.equalsIgnoreCase("YA")) {
                String acNoCustId = aitr.getGuarCustId(acNo);
                String acNoGivenCustId = aitr.getGuarCustId(oldGuarAcNo);
                if (acNoCustId.equalsIgnoreCase(acNoGivenCustId)) {
                    this.setErrorMessage("THIS ACCOUNT NO CANNOT GIVE GUARANTEE FOR SELF !!!.");
                    return;
                }
                if (this.oldGuarAcNo == null || this.oldGuarAcNo.equalsIgnoreCase("") || this.oldGuarAcNo.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER GUARANTOR A/C. NO.");
                    return;
                }
                if (!oldGuarAcNo.matches("[0-9a-zA-z]*")) {
                    setErrorMessage("Please Enter Valid  Account No.");
                    return;
                }
                //if (this.oldGuarAcNo.length() != 12) {
                if (!this.oldGuarAcNo.equalsIgnoreCase("") && ((this.oldGuarAcNo.length() != 12) && (this.oldGuarAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMessage("PLEASE ENTER PROPER GUARANTOR A/C. NO.");
                    return;
                }
                guarAcNo = fts.getNewAccountNumber(oldGuarAcNo);
                setGuarAcNo(guarAcNo);

                String result = loanAcDetails.acNoCheckForGuar(this.guarAcNo);
                //  if (result.contains("!")) {
                if (accSeq.equalsIgnoreCase("M")) {
                    // pojo.setPaymentAmt(formatter2.format(Double.parseDouble(intAmt)));
                    result = "true" + aitr.getGuarCustId(this.guarAcNo);
                    if (!bnkCode.equalsIgnoreCase("INDR")) {
                        String guNetworth = String.valueOf(formatter2.format(Double.parseDouble(aitr.getGuarantorLimit(schemeCode)) * Double.parseDouble(aitr.getGuarantorSalary(aitr.getGuarCustId(this.guarAcNo)))));
                        this.setActulNetWorth("Actul NetWorth :" + guNetworth);
                        String guarNetWorthVal = aitr.guarNetWorthChk(this.guarAcChk, acNo, schemeCode, oldGuarAcNo);
                        this.setGuarNetWorth(formatter2.format(Double.parseDouble(guarNetWorthVal)));
                    }
//                        if (guarNetWorthVal.equalsIgnoreCase("true")) {
//                            this.setErrorMessage("Your Guarantor(already guarantee for another account) NetWorth Amount is less than sanction amount");
//                            return;
//                        } else {
//                            result = "true" + aitr.getGuarCustId(this.guarAcNo);
//                        }
                }
                // }

                if (result == null || result.equalsIgnoreCase("") || result.length() == 0) {
                    this.setErrorMessage("PROBLEM OCCURED IN GETTING GUARANTOR A/C. NO. DETAIL.");
                    return;
                } else {
                    if (result.contains("!")) {
                        this.setErrorMessage(result);
                        return;
                    } else {
                        if (result.equalsIgnoreCase("false")) {
                            this.setErrorMessage("CUSTOMER ID NOT FOUND FOR THIS ACCOUNT NO. !!!");
                            return;
                        } else {
                            result = result.substring(4);
                            if (accSeq.equalsIgnoreCase("M")) {
                                String guarantorIs = aitr.getGuarantorIs(this.schemeCode);
                                if (guarantorIs.equalsIgnoreCase("Y")) {
                                    String guarantorDob = aitr.getGuarantorDob(result);
                                    String guaDobDt = CbsUtil.yearAdd(guarantorDob, 60);
                                    String loanPeriodDt = CbsUtil.monthAdd(ymd.format(sdf.parse(getTodayDate())), Integer.parseInt(aitr.getLoanPerid(acNo)));
                                    if (ymd.parse(loanPeriodDt).after(ymd.parse(guaDobDt))) {
                                        popupGuartFlag = "true";
                                    }
                                }
                            }
                            List resultList = loanAcDetails.guarontorDetailIfBankCust(result);
                            if (!resultList.isEmpty()) {
                                for (int i = 0; i < resultList.size(); i++) {
                                    Vector resultListVec = (Vector) resultList.get(i);
                                    if (resultListVec.get(0).toString().equalsIgnoreCase("") || resultListVec.get(0).toString().length() == 0) {
                                        this.setGuarFlag1(false);
                                    } else {
                                        this.setGuarFlag1(true);
                                        this.setGuarName(resultListVec.get(0).toString());
                                    }
                                    if (resultListVec.get(1).toString().equalsIgnoreCase("") || resultListVec.get(1).toString().length() == 0) {
                                        this.setGuarFlag2(false);
                                    } else {
                                        this.setGuarFlag2(true);
                                        this.setGuarAddress(resultListVec.get(1).toString());
                                    }
                                    if (resultListVec.get(2).toString().equalsIgnoreCase("") || resultListVec.get(2).toString().length() == 0) {
                                        this.setGuarFlag3(false);
                                    } else {
                                        this.setGuarFlag3(true);
                                        this.setGuarFatherName(resultListVec.get(2).toString());
                                    }
                                    if (resultListVec.get(3).toString().equalsIgnoreCase("") || resultListVec.get(3).toString().length() == 0) {
                                        this.setGuarFlag4(false);
                                    } else {
                                        this.setGuarFlag4(true);
                                        this.setGuarPhoneNo(resultListVec.get(3).toString());
                                    }
                                    if (resultListVec.get(4).toString().equalsIgnoreCase("") || resultListVec.get(4).toString().length() == 0) {
                                        this.setGuarFlag5(false);
                                    } else {
                                        this.setGuarFlag5(true);
                                        this.setGuarAge(resultListVec.get(4).toString());
                                    }
                                }
                            } else {
                                this.setErrorMessage("THIS ACCOUNT NO. IS NOT PRESENT IN CUSTOMER DETAIL INFORMATION RECORD!!!");
                                return;
                            }
                        }
                    }
                }
            }
            if (this.guarAcChk.equalsIgnoreCase("YC") || this.guarAcChk.equalsIgnoreCase("YF")) {
                if (this.guarAcChk.equalsIgnoreCase("YF")) {
                    oldGuarAcNo = aitr.getCustIdAsPerFolioNo(this.oldGuarAcNo);
                } else {
                    if (this.oldGuarAcNo == null || this.oldGuarAcNo.equalsIgnoreCase("") || this.oldGuarAcNo.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER GUARANTOR CUST ID.");
                        return;
                    }
                    if (!oldGuarAcNo.matches("[0-9a-zA-z]*")) {
                        setErrorMessage("Please Enter Valid  Cust Id.");
                        return;
                    }
                }
                String acNoCustId = aitr.getGuarCustId(acNo);
                if (oldGuarAcNo.equalsIgnoreCase(acNoCustId)) {
                    this.setErrorMessage("THIS FOLIO NO/CUST ID CANNOT GIVE GUARANTEE FOR SELF !!!.");
                    return;
                }

                guarAcNo = oldGuarAcNo;
                setGuarAcNo(guarAcNo);
                if (accSeq.equalsIgnoreCase("M")) {
                    String guarantorIs = aitr.getGuarantorIs(this.schemeCode);
                    if (guarantorIs.equalsIgnoreCase("Y")) {
                        String guarantorDob = aitr.getGuarantorDob(this.oldGuarAcNo);
                        String guaDobDt = CbsUtil.yearAdd(guarantorDob, 60);
                        String loanPeriodDt = CbsUtil.monthAdd(ymd.format(sdf.parse(getTodayDate())), Integer.parseInt(aitr.getLoanPerid(acNo)));
                        if (ymd.parse(loanPeriodDt).after(ymd.parse(guaDobDt))) {
                            popupGuartFlag = "true";
                        }
                    }
                }
                if (accSeq.equalsIgnoreCase("M")) {
                    // List list = aitr.getGuarChk(oldGuarAcNo);
//                    if (!list.isEmpty()) { 
                    if (!bnkCode.equalsIgnoreCase("INDR")) {
                        String guarNetWorthVal = aitr.guarNetWorthChk(this.guarAcChk, acNo, schemeCode, oldGuarAcNo);
                        this.setGuarNetWorth(String.valueOf(CbsUtil.round(Double.parseDouble(guarNetWorthVal), 0)));
                        String guNetworth = String.valueOf(Double.parseDouble(aitr.getGuarantorLimit(schemeCode)) * Double.parseDouble(aitr.getGuarantorSalary(oldGuarAcNo)));
                        this.setActulNetWorth("Actul NetWorth :" + guNetworth);
                    }
//                        if (guarNetWorth.equalsIgnoreCase("true")) {
//                            this.setErrorMessage("Your Guarantor(already guarantee for another account) NetWorth Amount is less than sanction amount");
//                            return;
//                        }
//                    }
                }
                List resultList = loanAcDetails.guarontorDetailIfBankCust(guarAcNo);
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector resultListVec = (Vector) resultList.get(i);
                        if (resultListVec.get(0).toString().equalsIgnoreCase("") || resultListVec.get(0).toString().length() == 0) {
                            this.setGuarFlag1(false);
                        } else {
                            this.setGuarFlag1(true);
                            this.setGuarName(resultListVec.get(0).toString());
                        }
                        if (resultListVec.get(1).toString().equalsIgnoreCase("") || resultListVec.get(1).toString().length() == 0) {
                            this.setGuarFlag2(false);
                        } else {
                            this.setGuarFlag2(true);
                            this.setGuarAddress(resultListVec.get(1).toString());
                        }
                        if (resultListVec.get(2).toString().equalsIgnoreCase("") || resultListVec.get(2).toString().length() == 0) {
                            this.setGuarFlag3(false);
                        } else {
                            this.setGuarFlag3(true);
                            this.setGuarFatherName(resultListVec.get(2).toString());
                        }
                        if (resultListVec.get(3).toString().equalsIgnoreCase("") || resultListVec.get(3).toString().length() == 0) {
                            this.setGuarFlag4(false);
                        } else {
                            this.setGuarFlag4(true);
                            this.setGuarPhoneNo(resultListVec.get(3).toString());
                        }
                        if (resultListVec.get(4).toString().equalsIgnoreCase("") || resultListVec.get(4).toString().length() == 0) {
                            this.setGuarFlag5(false);
                        } else {
                            this.setGuarFlag5(true);
                            this.setGuarAge(resultListVec.get(4).toString());
                        }
                    }
                } else {
                    this.setErrorMessage("THIS CUST ID. IS NOT PRESENT IN CUSTOMER DETAIL INFORMATION RECORD!!!");
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void saveGuarantorDetails() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.guarAcChk.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Check The Gaurantor Has A/c. No Or Not.If Yes Then Fill Guarantor A/c. No.");
                return;
            }
            if (this.guarAcChk.equalsIgnoreCase("YA")) {
                if (this.guarAcNo.equalsIgnoreCase("") || this.guarAcNo.length() == 0) {
                    this.setErrorMessage("Please Enter Guarantor A/c. No..");
                    return;
                }
                //if (this.guarAcNo.length() != 12) {
                if (!this.guarAcNo.equalsIgnoreCase("") && ((this.guarAcNo.length() != 12) && (this.guarAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMessage("Please Enter Proper Guarantor A/c. No..");
                    return;
                }
            }

            if (this.guarAcChk.equalsIgnoreCase("YC")) {
                if (this.guarAcNo.equalsIgnoreCase("") || this.guarAcNo.length() == 0) {
                    this.setErrorMessage("Please Enter Guarantor Cust Id.");
                    return;
                }
            }

            if (this.guarAcChk.equalsIgnoreCase("YF")) {
                if (this.guarAcNo.equalsIgnoreCase("") || this.guarAcNo.length() == 0) {
                    this.setErrorMessage("Please Enter Folio No.");
                    return;
                }
            }

            if (this.guarName.equalsIgnoreCase("") || this.guarName.length() == 0) {
                this.setErrorMessage("Please Enter Guarantor name.");
                return;
            }
            if (this.guarAddress.equalsIgnoreCase("") || this.guarAddress.length() == 0) {
                this.setErrorMessage("Please Enter Guarantor Address.");
                return;
            }
            if (this.guarAddress.length() > 60) {
                this.guarAddress = this.guarAddress.substring(0, 60);
            }
//        if (this.guarFatherName.equalsIgnoreCase("") || this.guarFatherName.length() == 0) {
//            this.setErrorMessage("Please Enter Guarantor Father Name.");
//            return;
//        }
            Pattern spl = Pattern.compile("([0-9]+)");
//            if (this.guarPhoneNo.equalsIgnoreCase("") || this.guarPhoneNo.length() == 0) {
//                this.setErrorMessage("Please Enter Guarantor Phone No.");
//                return;
//            }
            if (this.guarPhoneNo.length() > 0) {
                Matcher guarPhoneNoCheck = spl.matcher(this.guarPhoneNo);
                if (!guarPhoneNoCheck.matches()) {
                    this.setErrorMessage("Please Enter Valid Phone No.");
                    return;
                }
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.guarAge.equalsIgnoreCase("") || this.guarAge.length() == 0) {
                this.setErrorMessage("Please Enter Guarantor Age.");
                return;
            }
            Matcher guarAgeCheck = p.matcher(guarAge);
            if (!guarAgeCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Guarantor Age.");
                return;
            }
            if (Float.parseFloat(guarAge) < 0) {
                this.setErrorMessage("Age Cannot Be Less Than Zero.");
                return;
            }

            if (this.guarRetirementAge == null || this.guarRetirementAge.equalsIgnoreCase("") || this.guarRetirementAge.length() == 0) {
                this.guarRetirementAge = null;
            } else {
                if (!guarRetirementAge.matches("[0-9]*")) {
                    setGuarRetirementAge("");
                    this.setErrorMessage("Please Enter Numeric Value for Guar Retirement Age.");
                    return;
                }

                if (Float.parseFloat(guarRetirementAge) < 0) {
                    this.setErrorMessage("Retirement Age Cannot Be Less Than Zero.");
                    return;
                }
            }

//        Matcher guarRetirementAgeCheck = p.matcher(guarRetirementAge);
//        if (!guarRetirementAgeCheck.matches()) {
//            this.setErrorMessage("Please Enter Valid Retirement Age.");
//            return;
//        }
//        if (this.guarOccupation.equalsIgnoreCase("") || this.guarOccupation.length() == 0) {
//            this.setErrorMessage("Please Enter Guarantor Occupation.");
//            return;
//        }
            if (this.guarOfficePhpneNo != null || !this.guarOfficePhpneNo.equalsIgnoreCase("") || this.guarOfficePhpneNo.length() != 0) {
                if (!this.guarOfficePhpneNo.equalsIgnoreCase("")) {
                    Matcher guarOfficePhpneNoCheck = spl.matcher(this.guarOfficePhpneNo);
                    if (!guarOfficePhpneNoCheck.matches()) {
                        this.setErrorMessage("Please Enter Valid Office Phone No.");
                        return;
                    }
                }
            }
//        if (this.guarFirmName.equalsIgnoreCase("") || this.guarFirmName.length() == 0) {
//            this.setErrorMessage("Please Enter Guarantor Firm Name.");
//            return;
//        }
//        if (this.guarDesignation.equalsIgnoreCase("") || this.guarDesignation.length() == 0) {
//            this.setErrorMessage("Please Enter Guarantor Designation.");
//            return;
//        }
//        if (this.guarOffAddress.equalsIgnoreCase("") || this.guarOffAddress.length() == 0) {
//            this.setErrorMessage("Please Enter Guarantor Office Address.");
//            return;
//        }
            if (this.guarNetWorth.equalsIgnoreCase("") || this.guarNetWorth.length() == 0) {
                this.setErrorMessage("Please Enter Net Worth.");
                return;
            }

            Matcher guarNetWorthCheck = p.matcher(guarNetWorth);
            if (!guarNetWorthCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Net Worth.");
                return;
            }
            if (Float.parseFloat(guarNetWorth) < 0) {
                this.setErrorMessage("Net Worth Cannot Be less Than Zero.");
                return;
            }

            String accSeq = CommonReportMethodsBean.getAccseq();
            if (accSeq.equalsIgnoreCase("M")) {
                if (!fts.getBankCode().equalsIgnoreCase("INDR")) {
                    String actualAmt[] = actulNetWorth.split(":");
                    if (Double.parseDouble(guarNetWorth) > Double.parseDouble(actualAmt[1])) {
                        this.setErrorMessage("Net Worth Cannot Be greater than Actual Net Worth !");
                        return;
                    }
                }
            }
//            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = loanAcDetails.gurnatorDtSave(this.guarActionFlag, this.userName, this.guarAcChk, this.guarAcNo, this.acNo, this.guarName, this.guarFatherName, Integer.parseInt(this.guarAge), this.guarRetirementAge, this.guarAddress, this.guarPhoneNo, this.guarOccupation, this.guarFirmName, this.guarOffAddress, this.guarOfficePhpneNo, this.guarDesignation, Float.parseFloat(this.guarNetWorth));
            if (result == null) {
                this.setErrorMessage("Not Saved.");
                gridLoadForGauranterDetail();
                setgFlag(false);
                return;
            } else {
                if (result.equals("DATA SAVED SUCCESSFULLY") || result.equalsIgnoreCase("DATA UPDATED SUCCESSFULLY")) {
                    this.setMessage(result);
                    gridLoadForGauranterDetail();
                    this.setGuarAcChk("--Select--");
                    this.setGuarAcNo("");
                    this.setOldGuarAcNo("");
                    this.setGuarName("");
                    this.setGuarAddress("");
                    this.setGuarPhoneNo("");
                    this.setGuarFatherName("");
                    this.setGuarOffAddress("");
                    this.setGuarOfficePhpneNo("");
                    this.setGuarFirmName("");
                    this.setGuarAge("");
                    this.setGuarRetirementAge("");
                    this.setGuarOccupation("");
                    this.setGuarDesignation("");
                    this.setGuarNetWorth("");
                    setgFlag(false);

                } else {
                    this.setErrorMessage(result);
                    gridLoadForGauranterDetail();
                    this.setGuarAcChk("--Select--");
                    this.setGuarAcNo("");
                    this.setGuarName("");
                    this.setGuarAddress("");
                    this.setGuarPhoneNo("");
                    this.setGuarFatherName("");
                    this.setGuarOffAddress("");
                    this.setGuarOfficePhpneNo("");
                    this.setGuarFirmName("");
                    this.setGuarAge("");
                    this.setGuarRetirementAge("");
                    this.setGuarOccupation("");
                    this.setGuarDesignation("");
                    this.setGuarNetWorth("");
                    setgFlag(false);
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }

    }

    public void resetGuarantorDetails() {
        this.setErrorMessage("");
        this.setMessage("");
        gridLoadForGauranterDetail();
        this.setGuarAcChk("--Select--");
        this.setOldGuarAcNo("");
        this.setGuarAcNo("");
        this.setGuarName("");
        this.setGuarAddress("");
        this.setGuarPhoneNo("");
        this.setGuarFatherName("");
        this.setGuarOffAddress("");
        this.setGuarOfficePhpneNo("");
        this.setGuarFirmName("");
        this.setGuarAge("");
        this.setGuarRetirementAge("");
        this.setGuarOccupation("");
        this.setGuarDesignation("");
        this.setGuarNetWorth("");
        this.setActulNetWorth("");
        setgFlag(false);
        setgFlag1(false);
        setGuarActionFlag("SAVE");
    }

    public void guarAcChkMethod() {
        this.setGuarFlag1(false);
        this.setGuarFlag2(false);
        this.setGuarFlag3(false);
        this.setGuarFlag4(false);
        this.setGuarFlag5(false);
        this.setErrorMessage("");
        this.setMessage("");
        this.setOldGuarAcNo("");
        this.setGuarAcNo("");
        this.setGuarName("");
        this.setGuarAddress("");
        this.setGuarPhoneNo("");
        this.setGuarFatherName("");
        this.setGuarOffAddress("");
        this.setGuarOfficePhpneNo("");
        this.setGuarFirmName("");
        this.setGuarAge("");
        this.setGuarRetirementAge("");
        this.setGuarOccupation("");
        this.setGuarDesignation("");
        this.setGuarNetWorth("");
        this.setActulNetWorth("");

        if (this.guarAcChk.equalsIgnoreCase("--Select--")) {
            setgFlag1(false);
        }
        if (this.guarAcChk.equalsIgnoreCase("YA") || this.guarAcChk.equalsIgnoreCase("YC") || this.guarAcChk.equalsIgnoreCase("YF")) {
            setgFlag1(true);
        }
        if (this.guarAcChk.equalsIgnoreCase("No")) {
            setgFlag1(false);
        }
    }

    public void fetchCurrentRow2(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("guarName"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (GuarantorDetailGrid item : gridDetail2) {
            if (item.getGuarName().equalsIgnoreCase(ac)) {
                currentItem2 = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        setgFlag(true);
        setGuarActionFlag("UPDATE");
        this.setErrorMessage("");
        this.setMessage("");
        this.setGuarFlag1(true);
        this.setGuarFlag2(false);
        this.setGuarFlag3(true);
        this.setGuarFlag4(false);
        this.setGuarFlag5(true);
        this.setGuarAcChk(currentItem2.getGuarCustFlag());
//        if (currentItem2.getGuarCustFlag().contains("Y")) {
//            this.setGuarAcChk("Yes");
//        } else {
//            this.setGuarAcChk("No");
//        }
        if (currentItem2.getGuarAcNo().equalsIgnoreCase("")) {
            setgFlag1(false);
        } else {
            setgFlag1(true);
        }
        this.setGuarAcNo(currentItem2.getGuarAcNo());
        this.setGuarName(currentItem2.getGuarName());
        this.setGuarAddress(currentItem2.getGuarAddress());
        this.setGuarPhoneNo(currentItem2.getGuarPhoneNo());
        this.setGuarFatherName(currentItem2.getGuarFatherName());
        this.setGuarOffAddress(currentItem2.getGuarfirmAdd());
        this.setGuarOfficePhpneNo(currentItem2.getGuarFirmPhoneNo());
        this.setGuarFirmName(currentItem2.getGuarFirmName());
        this.setGuarAge(currentItem2.getGuarAge());
        this.setGuarRetirementAge(currentItem2.getGuarRetAge());
        this.setGuarOccupation(currentItem2.getGuarOccupation());
        this.setGuarDesignation(currentItem2.getGuarDesignation());
        this.setGuarNetWorth(currentItem2.getGuarNetWorth());
    }

    public void deleteRecordOfGuarantor() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = loanAcDetails.loanGuarantorDtDelete(this.userName, this.acNo, currentItem2.getGuarName(), currentItem2.getGuarFatherName(), currentItem2.getGuarAddress(), currentItem2.getGuarPhoneNo());
            if (result == null) {
                this.setErrorMessage("Not Deleted");
                setgFlag1(false);
                return;
            } else {
                if (result.equals("DATA DELETED SUCCESSFULLY")) {
                    this.setMessage(result);
                    this.setGuarAcChk("--Select--");
                    this.setGuarAcNo("");
                    this.setGuarName("");
                    this.setGuarAddress("");
                    this.setGuarPhoneNo("");
                    this.setGuarFatherName("");
                    this.setGuarOffAddress("");
                    this.setGuarOfficePhpneNo("");
                    this.setGuarFirmName("");
                    this.setGuarAge("");
                    this.setGuarRetirementAge("");
                    this.setGuarOccupation("");
                    this.setGuarDesignation("");
                    this.setGuarNetWorth("");
                    setgFlag1(false);
                    setGuarActionFlag("SAVE");
                    gridLoadForGauranterDetail();
                } else {
                    this.setErrorMessage(result);
                    this.setGuarAcChk("--Select--");
                    this.setGuarAcNo("");
                    this.setGuarName("");
                    this.setGuarAddress("");
                    this.setGuarPhoneNo("");
                    this.setGuarFatherName("");
                    this.setGuarOffAddress("");
                    this.setGuarOfficePhpneNo("");
                    this.setGuarFirmName("");
                    this.setGuarAge("");
                    this.setGuarRetirementAge("");
                    this.setGuarOccupation("");
                    this.setGuarDesignation("");
                    this.setGuarNetWorth("");
                    setgFlag1(false);
                    setGuarActionFlag("SAVE");
                    gridLoadForGauranterDetail();
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void insuranceStatusCombo() {
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.insuranceStatusCombo();
            if (resultList.isEmpty()) {
                insurancceStatusList = new ArrayList<SelectItem>();
                insurancceStatusList.add(new SelectItem("--Select--"));
                return;
            } else {
                insurancceStatusList = new ArrayList<SelectItem>();
                insurancceStatusList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    insurancceStatusList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void insuranceTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.insuranceTypeCombo();
            if (resultList.isEmpty()) {
                insuranceTypeList = new ArrayList<SelectItem>();
                insuranceTypeList.add(new SelectItem("--Select--"));
                return;
            } else {
                insuranceTypeList = new ArrayList<SelectItem>();
                insuranceTypeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    insuranceTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void insuranceCompanyCombo() {
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.insuranceCompanyCombo();
            // System.out.println("loanAcDetails -->"+loanAcDetails);
            if (resultList.isEmpty()) {
                insuranceCmpnyNameList = new ArrayList<SelectItem>();
                insuranceCmpnyNameList.add(new SelectItem("--Select--"));
                return;
            } else {
                insuranceCmpnyNameList = new ArrayList<SelectItem>();
                insuranceCmpnyNameList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    insuranceCmpnyNameList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void gridLoadForInsuranceDetail() {
        setInsFlag(true);
        gridDetail3 = new ArrayList<InsuranceDetailGrid>();
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.insuranceGridLoad(this.acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    InsuranceDetailGrid detail = new InsuranceDetailGrid();
                    detail.setsNo(ele.get(0).toString());
                    detail.setAssetDesc(ele.get(10).toString());
                    detail.setAssetVal(ele.get(11).toString());
                    List resultList1 = loanAcDetails.setInsuranceCompany(ele.get(12).toString());
                    if (!resultList1.isEmpty()) {
                        Vector ele1 = (Vector) resultList1.get(0);
                        detail.setInsComName(ele1.get(0).toString());
                    }
                    List resultList2 = loanAcDetails.setInsuranceType(ele.get(13).toString());
                    if (!resultList2.isEmpty()) {
                        Vector ele2 = (Vector) resultList2.get(0);
                        detail.setInsType(ele2.get(0).toString());
                    }
                    detail.setCoverNoteNo(ele.get(2).toString());
                    detail.setFromDt(ele.get(3).toString());
                    detail.setToDt(ele.get(4).toString());
                    detail.setPremiumPaid(ele.get(5).toString());
                    detail.setParticulars(ele.get(6).toString());
                    detail.setStatus(ele.get(7).toString());
                    detail.setEnterBy(ele.get(8).toString());
                    detail.setEnteryDate(ele.get(9).toString());
                    gridDetail3.add(detail);
                }
            } else {
                gridDetail3 = new ArrayList<InsuranceDetailGrid>();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fetchCurrentRow3(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
        currentRow3 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (InsuranceDetailGrid item : gridDetail3) {
            if (item.getsNo().equalsIgnoreCase(ac)) {
                currentItem3 = item;
            }
        }
    }

    public void fillValuesofGridInFieldsInInsurance() {
        setInsFlag(false);
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setAssetDesc(currentItem3.getAssetDesc());
            this.setAssetValue(currentItem3.getAssetVal());
            this.setInsuranceCmpnyName(currentItem3.getInsComName());
            this.setInsuranceType(currentItem3.getInsType());
            this.setCoverNoteNo(currentItem3.getCoverNoteNo());
            this.setInsurancceStatus(currentItem3.getStatus());
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date orgnDt = sdf.parse(currentItem3.getFromDt());
            Date orgnDt1 = sdf.parse(currentItem3.getToDt());
            this.setInsExpDt(orgnDt1);
            this.setInsIssueDt(orgnDt);
            this.setInsuranceAmt(currentItem3.getPremiumPaid());
            this.setInsuranceDetails(currentItem3.getParticulars());
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void saveInsuranceDetail() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.assetDesc.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Asset.");
                return;
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.assetValue.equalsIgnoreCase("") || this.assetValue.length() == 0) {
                this.setErrorMessage("Please Enter Asset Value");
                return;
            }
            Matcher assetValueCheck = p.matcher(assetValue);
            if (!assetValueCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Asset Value.");
                return;
            }
            if (Float.parseFloat(assetValue) < 0) {
                this.setErrorMessage("Asset Value Cannot Be Less Than Zero.");
                return;
            }
            if (this.insuranceCmpnyName.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Insurance Company.");
                return;
            }
            if (this.insuranceType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Insurance Type.");
                return;
            }
            if (this.coverNoteNo.equalsIgnoreCase("") || this.coverNoteNo.length() == 0) {
                this.setErrorMessage("Please Enter Cover Note No.");
                return;
            }
            if (this.insurancceStatus.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Insurance Status.");
                return;
            }
            if (this.insuranceAmt.equalsIgnoreCase("") || this.insuranceAmt.length() == 0) {
                this.setErrorMessage("Please Enter Insurance Amount");
                return;
            }
            Matcher insuranceAmtCheck = p.matcher(insuranceAmt);
            if (!insuranceAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Insurance Amount.");
                return;
            }
            if (Float.parseFloat(insuranceAmt) < 0) {
                this.setErrorMessage("Insurance Amount Cannot Be Less Than Zero.");
                return;
            }
            if (this.insuranceDetails.equalsIgnoreCase("") || this.insuranceDetails.length() == 0) {
                this.setErrorMessage("Please Enter Insurance Detail.");
                return;
            }
            if (this.insIssueDt == null) {
                this.setErrorMessage("Please Enter Issue Date.");
                return;
            }
            if (this.insExpDt == null) {
                this.setErrorMessage("Please Enter Expiry Date.");
                return;
            }
            //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = loanAcDetails.saveInsuranceDetail(this.acNo, this.assetDesc, Float.parseFloat(this.assetValue), this.insuranceCmpnyName, this.insuranceType, this.coverNoteNo, this.insurancceStatus, ymd.format(this.insIssueDt), ymd.format(this.insExpDt), Float.parseFloat(insuranceAmt), this.insuranceDetails, this.userName, this.orgnBrCode);
            if (result == null) {
                this.setErrorMessage("Not Saved.");
                gridLoadForGauranterDetail();
                this.setAssetDesc("");
                this.setAssetValue("");
                this.setInsurancceStatus("--Select--");
                this.setInsuranceCmpnyName("--Select--");
                this.setInsuranceType("--Select--");
                this.setCoverNoteNo("");
//                Date date = new Date();
                this.setInsExpDt(date);
                this.setInsIssueDt(date);
                this.setInsuranceAmt("");
                this.setInsuranceDetails("");
                setInsFlag(true);
                return;
            } else {
                if (result.equals("DATA SAVED SUCCESSFULLY")) {
                    this.setMessage(result);
                    gridLoadForInsuranceDetail();
                    this.setAssetDesc("");
                    this.setAssetValue("");
                    this.setInsurancceStatus("--Select--");
                    this.setInsuranceCmpnyName("--Select--");
                    this.setInsuranceType("--Select--");
                    this.setCoverNoteNo("");
//                    Date date = new Date();
                    this.setInsExpDt(date);
                    this.setInsIssueDt(date);
                    this.setInsuranceAmt("");
                    this.setInsuranceDetails("");
                    setInsFlag(true);
                } else {
                    this.setErrorMessage(result);
                    gridLoadForInsuranceDetail();
                    this.setAssetDesc("");
                    this.setAssetValue("");
                    this.setInsurancceStatus("--Select--");
                    this.setInsuranceCmpnyName("--Select--");
                    this.setInsuranceType("--Select--");
                    this.setCoverNoteNo("");
//                    Date date = new Date();
                    this.setInsExpDt(date);
                    this.setInsIssueDt(date);
                    this.setInsuranceAmt("");
                    this.setInsuranceDetails("");
                    setInsFlag(true);
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void updateInsuranceDetail() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.assetDesc.equalsIgnoreCase("") || this.assetDesc.length() == 0) {
                this.setErrorMessage("Please Enter Asset Description.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.assetValue.equalsIgnoreCase("") || this.assetValue.length() == 0) {
                this.setErrorMessage("Please Enter Asset Value");
                return;
            }
            Matcher assetValueCheck = p.matcher(assetValue);
            if (!assetValueCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Asset Value.");
                return;
            }
            if (Float.parseFloat(assetValue) < 0) {
                this.setErrorMessage("Asset Value Cannot Be Less Than Zero.");
                return;
            }
            if (this.insuranceCmpnyName.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Insurance Company.");
                return;
            }
            if (this.insuranceType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Insurance Type.");
                return;
            }
            if (this.coverNoteNo.equalsIgnoreCase("") || this.coverNoteNo.length() == 0) {
                this.setErrorMessage("Please Enter Cover Note No.");
                return;
            }
            if (this.insurancceStatus.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Insurance Status.");
                return;
            }
            if (this.insuranceAmt.equalsIgnoreCase("") || this.insuranceAmt.length() == 0) {
                this.setErrorMessage("Please Enter Insurance Amount");
                return;
            }
            Matcher insuranceAmtCheck = p.matcher(insuranceAmt);
            if (!insuranceAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Insurance Amount.");
                return;
            }
            if (Float.parseFloat(insuranceAmt) < 0) {
                this.setErrorMessage("Insurance Amount Cannot Be Less Than Zero.");
                return;
            }
            if (this.insuranceDetails.equalsIgnoreCase("") || this.insuranceDetails.length() == 0) {
                this.setErrorMessage("Please Enter Insurance Detail");
                return;
            }
            if (this.insIssueDt == null) {
                this.setErrorMessage("Please Enter Issue Date.");
                return;
            }
            if (this.insExpDt == null) {
                this.setErrorMessage("Please Enter Expiry Date.");
                return;
            }
            //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = loanAcDetails.updateInsuranceDetail(this.acNo, Integer.parseInt(currentItem3.getsNo()), this.assetDesc, Float.parseFloat(this.assetValue), this.insuranceCmpnyName, this.insuranceType, this.coverNoteNo, this.insurancceStatus, ymd.format(this.insIssueDt), ymd.format(this.insExpDt), Float.parseFloat(insuranceAmt), this.insuranceDetails, this.userName);
            if (result == null) {
                this.setErrorMessage("Not Updated.");
                gridLoadForGauranterDetail();
                this.setAssetDesc("");
                this.setAssetValue("");
                this.setInsurancceStatus("--Select--");
                this.setInsuranceCmpnyName("--Select--");
                this.setInsuranceType("--Select--");
                this.setCoverNoteNo("");
                //Date date = new Date();
                this.setInsExpDt(date);
                this.setInsIssueDt(date);
                this.setInsuranceAmt("");
                this.setInsuranceDetails("");
                setInsFlag(true);
                return;
            } else {
                if (result.equals("DATA UPDATED SUCCESSFULLY")) {
                    this.setMessage(result);
                    gridLoadForInsuranceDetail();
                    this.setAssetDesc("");
                    this.setAssetValue("");
                    this.setInsurancceStatus("--Select--");
                    this.setInsuranceCmpnyName("--Select--");
                    this.setInsuranceType("--Select--");
                    this.setCoverNoteNo("");
                    //Date date = new Date();
                    this.setInsExpDt(date);
                    this.setInsIssueDt(date);
                    this.setInsuranceAmt("");
                    this.setInsuranceDetails("");
                    setInsFlag(true);
                } else {
                    this.setErrorMessage(result);
                    gridLoadForInsuranceDetail();
                    this.setAssetDesc("");
                    this.setAssetValue("");
                    this.setInsurancceStatus("--Select--");
                    this.setInsuranceCmpnyName("--Select--");
                    this.setInsuranceType("--Select--");
                    this.setCoverNoteNo("");
                    //Date date = new Date();
                    this.setInsExpDt(date);
                    this.setInsIssueDt(date);
                    this.setInsuranceAmt("");
                    this.setInsuranceDetails("");
                    setInsFlag(true);
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetInsuranceDetailForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setAssetDesc("");
        this.setAssetValue("");
        this.setInsurancceStatus("--Select--");
        this.setInsuranceCmpnyName("--Select--");
        this.setInsuranceType("--Select--");
        this.setCoverNoteNo("");
        //Date date = new Date();
        this.setInsExpDt(date);
        this.setInsIssueDt(date);
        this.setInsuranceAmt("");
        this.setInsuranceDetails("");
        setInsFlag(true);
    }

    public void fetchCurrentRow5(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name"));
        currentRow5 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (CompanyDetailGrid item : gridDetail5) {
            if (item.getName().equalsIgnoreCase(ac)) {
                currentItem5 = item;
            }
        }
    }

    public void gridLoadForGroupCompanyDetail() {
        gridDetail5 = new ArrayList<CompanyDetailGrid>();
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.groupCompanyGridLoad(this.acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    CompanyDetailGrid detail = new CompanyDetailGrid();
                    detail.setName(ele.get(0).toString());
                    detail.setAddress(ele.get(1).toString());
                    detail.setPhNo(ele.get(2).toString());
                    gridDetail5.add(detail);
                }
            } else {
                gridDetail5 = new ArrayList<CompanyDetailGrid>();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onLoadCOmpanyDetails() {
        setComFlag(true);
        gridDetail4 = new ArrayList<CompanyDetailGridLoad>();
        try {
            List resultList = new ArrayList();
            resultList = loanAcDetails.companyDetailOnLoad(this.acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    CompanyDetailGridLoad detail = new CompanyDetailGridLoad();
                    detail.setCmpnyName(ele.get(0).toString());
                    detail.setRegOffice(ele.get(1).toString());
                    detail.setLocation(ele.get(2).toString());
                    detail.setIncorpDate(ele.get(3).toString());
                    detail.setAuthCapital(new BigDecimal(Double.parseDouble(ele.get(4).toString())).toString());
                    detail.setSubCapital(new BigDecimal(Double.parseDouble(ele.get(5).toString())).toString());
                    detail.setNetworth(new BigDecimal(Double.parseDouble(ele.get(6).toString())).toString());
                    detail.setDirname1(ele.get(7).toString());
                    detail.setDirname2(ele.get(8).toString());
                    detail.setDirname3(ele.get(9).toString());
                    detail.setDirname4(ele.get(10).toString());
                    detail.setDirname5(ele.get(11).toString());
                    detail.setDirname6(ele.get(12).toString());
                    detail.setDirname7(ele.get(13).toString());
                    gridDetail4.add(detail);
                }
            } else {
                gridDetail4 = new ArrayList<CompanyDetailGridLoad>();
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fetchCurrentRow4(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmpnyName"));
        currentRow4 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (CompanyDetailGridLoad item : gridDetail4) {
            if (item.getCmpnyName().equalsIgnoreCase(ac)) {
                currentItem4 = item;
            }
        }
    }

    public void setValuesOfGridInFieldsInCmpnyDetails() {
        this.setErrorMessage("");
        this.setMessage("");
        setComFlag(false);
        try {
            this.setCmpnyName(currentItem4.getCmpnyName());
            this.setRegOffice(currentItem4.getRegOffice());
            this.setRegOfficeLocation(currentItem4.getLocation());
            //  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date orgnDt = sdf.parse(currentItem4.getIncorpDate());
            this.setIncDate(orgnDt);
            this.setAuthCapital(currentItem4.getAuthCapital());
            this.setSuscribedCapital(currentItem4.getSubCapital());
            this.setNetWorth(currentItem4.getNetworth());
            this.setDirName1(currentItem4.getDirname1());
            this.setDirName2(currentItem4.getDirname2());
            this.setDirName3(currentItem4.getDirname3());
            this.setDirName4(currentItem4.getDirname4());
            this.setDirName5(currentItem4.getDirname5());
            this.setDirName6(currentItem4.getDirname6());
            this.setDirName7(currentItem4.getDirname7());
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetCompanyDetails() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCmpnyName("");
        this.setRegOffice("");
        this.setRegOfficeLocation("");
        //  Date date = new Date();
        this.setIncDate(date);
        this.setAuthCapital("");
        this.setSuscribedCapital("");
        this.setNetWorth("");
        this.setDirName1("");
        this.setDirName2("");
        this.setDirName3("");
        this.setDirName4("");
        this.setDirName5("");
        this.setDirName6("");
        this.setDirName7("");
        setComFlag(true);
    }

    public void saveCompanyDetails() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.cmpnyName.equalsIgnoreCase("") || this.cmpnyName.length() == 0) {
                this.setErrorMessage("Please Enter Company Name.");
                return;
            }
            if (this.regOffice.equalsIgnoreCase("") || this.regOffice.length() == 0) {
                this.setErrorMessage("Please Enter Company Regd. Office.");
                return;
            }
            if (this.regOfficeLocation.equalsIgnoreCase("") || this.regOfficeLocation.length() == 0) {
                this.setErrorMessage("Please Enter Company Location.");
                return;
            }
            if (this.incDate == null) {
                this.setErrorMessage("Please Enter Incorporation Date.");
                return;
            }
            //   SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = loanAcDetails.saveCompanyDetail(this.acNo, this.cmpnyName, this.regOffice, this.regOfficeLocation, ymd.format(this.incDate), this.authCapital, this.suscribedCapital, this.netWorth, this.dirName1, this.dirName2, this.dirName3, this.dirName4, this.dirName5, this.dirName6, this.dirName7, this.userName);
            if (result == null) {
                this.setErrorMessage("Not Saved.");
                onLoadCOmpanyDetails();
//                this.setCmpnyName("");
//                this.setRegOffice("");
//                this.setRegOfficeLocation("");
//                Date date = new Date();
                this.setIncDate(date);
//                this.setAuthCapital("");
//                this.setSuscribedCapital("");
//                this.setNetWorth("");
//                this.setDirName1("");
//                this.setDirName2("");
//                this.setDirName3("");
//                this.setDirName4("");
//                this.setDirName5("");
//                this.setDirName6("");
//                this.setDirName7("");
                setComFlag(true);
                return;
            } else {
                if (result.equals("COMPANY DETAIL SAVED SUCCESSFULLY")) {
                    this.setMessage(result);
                    onLoadCOmpanyDetails();
                    this.setCmpnyName("");
                    this.setRegOffice("");
                    this.setRegOfficeLocation("");
//                    Date date = new Date();
                    this.setIncDate(date);
                    this.setAuthCapital("");
                    this.setSuscribedCapital("");
                    this.setNetWorth("");
                    this.setDirName1("");
                    this.setDirName2("");
                    this.setDirName3("");
                    this.setDirName4("");
                    this.setDirName5("");
                    this.setDirName6("");
                    this.setDirName7("");
                    setComFlag(true);
                } else {
                    this.setErrorMessage(result);
                    onLoadCOmpanyDetails();
                    setComFlag(true);
//                    this.setCmpnyName("");
//                    this.setRegOffice("");
//                    this.setRegOfficeLocation("");
//                    Date date = new Date();
                    this.setIncDate(date);
//                    this.setAuthCapital("");
//                    this.setSuscribedCapital("");
//                    this.setNetWorth("");
//                    this.setDirName1("");
//                    this.setDirName2("");
//                    this.setDirName3("");
//                    this.setDirName4("");
//                    this.setDirName5("");
//                    this.setDirName6("");
//                    this.setDirName7("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void updateCompanyDetail() {
        this.setErrorMessage("");
        this.setMessage("");
//        if (this.acctType.equalsIgnoreCase("--Select--")) {
//            this.setErrorMessage("Please Select Account Type.");
//            return;
//        }
        if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
            this.setErrorMessage("Please Enter Account No.");
            return;
        }
        if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
            this.setErrorMessage("No Account No is Selected.");
            return;
        }
        if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
            this.setErrorMessage("No Party Name Exists.");
            return;
        }
        if (this.cmpnyName.equalsIgnoreCase("") || this.cmpnyName.length() == 0) {
            this.setErrorMessage("Please Enter Company Name.");
            return;
        }
        if (this.regOffice.equalsIgnoreCase("") || this.regOffice.length() == 0) {
            this.setErrorMessage("Please Enter Company Regd. Office.");
            return;
        }
        if (this.regOfficeLocation.equalsIgnoreCase("") || this.regOfficeLocation.length() == 0) {
            this.setErrorMessage("Please Enter Company Location.");
            return;
        }
        try {
            //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = loanAcDetails.updateCompanyDetail(this.acNo, this.cmpnyName, this.regOffice, this.regOfficeLocation, ymd.format(this.incDate), this.authCapital, this.suscribedCapital, this.netWorth, this.dirName1, this.dirName2, this.dirName3, this.dirName4, this.dirName5, this.dirName6, this.dirName7, this.userName, currentItem4.getCmpnyName(), currentItem4.getRegOffice(), currentItem4.getLocation());
            if (result == null) {
                this.setErrorMessage("Not Saved.");
                onLoadCOmpanyDetails();
                setComFlag(true);
                this.setCmpnyName("");
                this.setRegOffice("");
                this.setRegOfficeLocation("");
                //Date date = new Date();
                this.setIncDate(date);
                this.setAuthCapital("");
                this.setSuscribedCapital("");
                this.setNetWorth("");
                this.setDirName1("");
                this.setDirName2("");
                this.setDirName3("");
                this.setDirName4("");
                this.setDirName5("");
                this.setDirName6("");
                this.setDirName7("");
                return;
            } else {
                if (result.equals("COMPANY DETAIL UPDATED SUCCESSFULLY")) {
                    this.setMessage(result);
                    onLoadCOmpanyDetails();
                    setComFlag(true);
                    this.setCmpnyName("");
                    this.setRegOffice("");
                    this.setRegOfficeLocation("");
//                    Date date = new Date();
                    this.setIncDate(date);
                    this.setAuthCapital("");
                    this.setSuscribedCapital("");
                    this.setNetWorth("");
                    this.setDirName1("");
                    this.setDirName2("");
                    this.setDirName3("");
                    this.setDirName4("");
                    this.setDirName5("");
                    this.setDirName6("");
                    this.setDirName7("");
                } else {
                    this.setErrorMessage(result);
                    onLoadCOmpanyDetails();
                    setComFlag(true);
                    this.setCmpnyName("");
                    this.setRegOffice("");
                    this.setRegOfficeLocation("");
//                    Date date = new Date();
                    this.setIncDate(date);
                    this.setAuthCapital("");
                    this.setSuscribedCapital("");
                    this.setNetWorth("");
                    this.setDirName1("");
                    this.setDirName2("");
                    this.setDirName3("");
                    this.setDirName4("");
                    this.setDirName5("");
                    this.setDirName6("");
                    this.setDirName7("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void saveGroupCompDetail() {
        this.setErrorMessage("");
        this.setMessage("");

        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("No Account No is Selected.");
                return;
            }
            if (this.partyName.equalsIgnoreCase("") || this.partyName.length() == 0) {
                this.setErrorMessage("No Party Name Exists.");
                return;
            }
            if (this.grCompName.equalsIgnoreCase("") || this.grCompName.length() == 0) {
                this.setErrorMessage("Please Enter Group Company Name.");
                return;
            }
            if (this.grCompAdd.equalsIgnoreCase("") || this.grCompAdd.length() == 0) {
                this.setErrorMessage("Please Enter Group Company Address.");
                return;
            }
            if (this.grCompPhNo.equalsIgnoreCase("") || this.grCompPhNo.length() == 0) {
                this.setErrorMessage("Please Enter Group Company Phone No.");
                return;
            }

            String result = loanAcDetails.saveGroupCompanyDetail(this.acNo, this.grCompName, this.grCompAdd, this.grCompPhNo, this.userName);
            if (result == null) {
                this.setErrorMessage("Not Saved.");
                gridLoadForGroupCompanyDetail();
                this.setGrCompAdd("");
                this.setGrCompName("");
                this.setGrCompPhNo("");
                return;
            } else {
                if (result.equalsIgnoreCase("GROUP COMPANY DETAIL SAVED SUCCESSFULLY")) {
                    this.setMessage(result);
                    gridLoadForGroupCompanyDetail();
                    this.setGrCompAdd("");
                    this.setGrCompName("");
                    this.setGrCompPhNo("");
                } else {
                    this.setErrorMessage(result);
                    gridLoadForGroupCompanyDetail();
                    this.setGrCompAdd("");
                    this.setGrCompName("");
                    this.setGrCompPhNo("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void deleteFromGroupCompGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = loanAcDetails.deleteFromGroupCompDetailGrid(this.acNo, currentItem5.getName(), currentItem5.getAddress(), currentItem5.getPhNo());
            if (result == null) {
                this.setErrorMessage("Not Deleted");
                gridLoadForGroupCompanyDetail();
                this.setGrCompAdd("");
                this.setGrCompName("");
                this.setGrCompPhNo("");
                return;
            } else {
                if (result.equals("Group Company Record Deleted Successfully")) {
                    this.setMessage(result);
                    this.setGrCompAdd("");
                    this.setGrCompName("");
                    this.setGrCompPhNo("");
                    gridLoadForGroupCompanyDetail();
                } else {
                    this.setErrorMessage(result);
                    this.setGrCompAdd("");
                    this.setGrCompName("");
                    this.setGrCompPhNo("");
                    gridLoadForGroupCompanyDetail();
                    return;
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetGroupCompFields() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setGrCompAdd("");
        this.setGrCompName("");
        this.setGrCompPhNo("");
    }

    /**
     * this is nishant code*
     */
    public void getDropDownValues() {
        try {

            List listForSector = new ArrayList();
            listForSector = aitr.getSector();
            sectorOption = new ArrayList<SelectItem>();
            sectorOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSector.size(); i++) {
                Vector element = (Vector) listForSector.get(i);
                sectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////SUB SECTOR//////////////////////////////////////////////////
            List listForSubSector = new ArrayList();
            listForSubSector = aitr.getListAsPerRequirement("183", "0", "0", "0", "0", "0", ymd.format(new Date()), Float.parseFloat(getSancAmount()));
            subSectorOption = new ArrayList<SelectItem>();
            subSectorOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSubSector.size(); i++) {
                Vector element = (Vector) listForSubSector.get(i);
                subSectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////
            List listForPurAdvance = new ArrayList();
            listForPurAdvance = aitr.getListAsPerRequirement("184", "0", "0", "0", "0", "0", ymd.format(new Date()), Float.parseFloat(getSancAmount()));
            purposeOfAdvanceOption = new ArrayList<SelectItem>();
            purposeOfAdvanceOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForPurAdvance.size(); i++) {
                Vector element = (Vector) listForPurAdvance.get(i);
                purposeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////////
            List listForModeAdvance = new ArrayList();
            listForModeAdvance = aitr.modeOfAdvance();
            modeOfAdvanceOption = new ArrayList<SelectItem>();
            modeOfAdvanceOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForModeAdvance.size(); i++) {
                Vector element = (Vector) listForModeAdvance.get(i);
                modeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////
            List listForTypeAdvance = new ArrayList();
            listForTypeAdvance = aitr.typeOfAdvance();
            typeOfAdvanceOption = new ArrayList<SelectItem>();
            typeOfAdvanceOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForTypeAdvance.size(); i++) {
                Vector element = (Vector) listForTypeAdvance.get(i);
                typeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////////
            List listForSecured = new ArrayList();
            listForSecured = aitr.secured();
            securedOption = new ArrayList<SelectItem>();
            securedOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSecured.size(); i++) {
                Vector element = (Vector) listForSecured.get(i);
                securedOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////
            List listForGuarCover = new ArrayList();
            listForGuarCover = aitr.guarnteeCover();
            guarnteeCoverOption = new ArrayList<SelectItem>();
            guarnteeCoverOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForGuarCover.size(); i++) {
                Vector element = (Vector) listForGuarCover.get(i);
                guarnteeCoverOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////////////
            List listForIndusType = new ArrayList();
            listForIndusType = aitr.indusType();
            industryTypeOption = new ArrayList<SelectItem>();
            industryTypeOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForIndusType.size(); i++) {
                Vector element = (Vector) listForIndusType.get(i);
                industryTypeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////////
            List listForSickness = new ArrayList();
            listForSickness = aitr.sickness();
            sicknessOption = new ArrayList<SelectItem>();
            sicknessOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSickness.size(); i++) {
                Vector element = (Vector) listForSickness.get(i);
                sicknessOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////Exposure////////////////////////////////////////////////////
            List listForExposure = new ArrayList();
            listForExposure = aitr.purposeOfAdvance("191", "0", "0", "0");
            exposureOption = new ArrayList<SelectItem>();
            exposureOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForExposure.size(); i++) {
                Vector element = (Vector) listForExposure.get(i);
                exposureOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////////
            List listForExposureCategory = new ArrayList();
            listForExposureCategory = aitr.purposeOfAdvance("230", "0", "0", "0");
            exposureCategroyOption = new ArrayList<SelectItem>();
            exposureCategroyOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForExposureCategory.size(); i++) {
                Vector element = (Vector) listForExposureCategory.get(i);
                exposureCategroyOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////////
            List listForExposureCatePurpose = new ArrayList();
            listForExposureCatePurpose = aitr.purposeOfAdvance("231", "0", "0", "0");
            exposureCatePurposeOption = new ArrayList<SelectItem>();
            exposureCatePurposeOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForExposureCatePurpose.size(); i++) {
                Vector element = (Vector) listForExposureCatePurpose.get(i);
                exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////
            List listForRestructure = new ArrayList();
            listForRestructure = aitr.restructure();
            restructuringOption = new ArrayList<SelectItem>();
            restructuringOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForRestructure.size(); i++) {
                Vector element = (Vector) listForRestructure.get(i);
                restructuringOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////
            List listForSancLvl = new ArrayList();
            listForSancLvl = aitr.sanctionLevel();
            sanctionLevelOption = new ArrayList<SelectItem>();
            sanctionLevelOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSancLvl.size(); i++) {
                Vector element = (Vector) listForSancLvl.get(i);
                sanctionLevelOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
            List listForSancAuth = new ArrayList();
            listForSancAuth = aitr.sanctionAuth();
            sanctionAuthOption = new ArrayList<SelectItem>();
            sanctionAuthOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForSancAuth.size(); i++) {
                Vector element = (Vector) listForSancAuth.get(i);
                sanctionAuthOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////
            List listForNpaClass = new ArrayList();
            listForNpaClass = aitr.npaClass();
            npaClassOption = new ArrayList<SelectItem>();
            npaClassOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForNpaClass.size(); i++) {
                Vector element = (Vector) listForNpaClass.get(i);
                npaClassOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////
            List listForCourts = new ArrayList();
            listForCourts = aitr.courts();
            courtsOption = new ArrayList<SelectItem>();
            courtsOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForCourts.size(); i++) {
                Vector element = (Vector) listForCourts.get(i);
                courtsOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////
            List listForModeSettlement = new ArrayList();
            listForModeSettlement = aitr.modeOfSetlement();
            modeOfSettleOption = new ArrayList<SelectItem>();
            modeOfSettleOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForModeSettlement.size(); i++) {
                Vector element = (Vector) listForModeSettlement.get(i);
                modeOfSettleOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////
            List listForDWR = new ArrayList();
            listForDWR = aitr.getDWR();
            debtWaiverReasonOption = new ArrayList<SelectItem>();
            debtWaiverReasonOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForDWR.size(); i++) {
                Vector element = (Vector) listForDWR.get(i);
                debtWaiverReasonOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////
            List listForAssetClassReason = new ArrayList();
            listForAssetClassReason = aitr.assetClassReason();
            assetClassReasonOption = new ArrayList<SelectItem>();
            assetClassReasonOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForAssetClassReason.size(); i++) {
                Vector element = (Vector) listForAssetClassReason.get(i);
                assetClassReasonOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////
            List listForPopuGroup = new ArrayList();
            listForPopuGroup = aitr.populationGrp();
            popuGroupOption = new ArrayList<SelectItem>();
            popuGroupOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForPopuGroup.size(); i++) {
                Vector element = (Vector) listForPopuGroup.get(i);
                popuGroupOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////

            //////////////////////////////////////////////////////////////////////////
//            List listForIntType = new ArrayList();
//            listForIntType = aitr.interestType();
//            intTypeOption = new ArrayList<SelectItem>();
//            intTypeOption.add(new SelectItem("0", ""));
//            for (int i = 0; i < listForIntType.size(); i++) {
//                Vector element = (Vector) listForIntType.get(i);
//                intTypeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
//            }
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            List listForRelation = new ArrayList();
            listForRelation = aitr.getReferenceCode("210");
            relationOption = new ArrayList<SelectItem>();
            relationOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForRelation.size(); i++) {
                Vector element = (Vector) listForRelation.get(i);
                relationOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
//            drawingPwrIndOption = new ArrayList<SelectItem>();
//            drawingPwrIndOption.add(new SelectItem("0", ""));
//            drawingPwrIndOption.add(new SelectItem("D", "Derived from Security"));
//            drawingPwrIndOption.add(new SelectItem("E", "Equal to the Sanction Limit"));
//            drawingPwrIndOption.add(new SelectItem("M", "Maintained for NPA"));
            List listDrawingPwrInd = new ArrayList();
            listDrawingPwrInd = aitr.getReferenceCode("232");
            drawingPwrIndOption = new ArrayList<SelectItem>();
            drawingPwrIndOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listDrawingPwrInd.size(); i++) {
                Vector element = (Vector) listDrawingPwrInd.get(i);
                drawingPwrIndOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////
            List listForAppliCateg = new ArrayList();
            listForAppliCateg = aitr.getReferenceCode("208");
            applicantCategoryOption = new ArrayList<SelectItem>();
            applicantCategoryOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForAppliCateg.size(); i++) {
                Vector element = (Vector) listForAppliCateg.get(i);
                applicantCategoryOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
            List listForCategOption = new ArrayList();
//            listForCategOption = aitr.getReferenceCode("209");
            listForCategOption = aitr.getListAsPerRequirement("209", "0", "0", "0", "0", "0", ymd.format(new Date()), 0);
            categoryOptOption = new ArrayList<SelectItem>();
            categoryOptOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForCategOption.size(); i++) {
                Vector element = (Vector) listForCategOption.get(i);
                categoryOptOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////
            List listForMinorCommu = new ArrayList();
//            listForMinorCommu = aitr.getReferenceCode("204");
            listForMinorCommu = aitr.getListAsPerRequirement("204", "0", "0", "0", "0", "0", ymd.format(new Date()), 0);
            minorCommunityOption = new ArrayList<SelectItem>();
            minorCommunityOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForMinorCommu.size(); i++) {
                Vector element = (Vector) listForMinorCommu.get(i);
                minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void functionAction() {
        try {
            List functionList = aitr.getReferenceCode1("179");
            Vector vecFunction = (Vector) functionList.get(0);
            for (int i = 0; i < functionList.size(); i++) {
                Vector ele = (Vector) functionList.get(i);
                if (functionFlag.equalsIgnoreCase(ele.get(0).toString())) {
                    setFunctionValue(ele.get(1).toString());
                }
            }
            if (functionFlag.equalsIgnoreCase("")) {
                setFunctionValue("");
//                setAcNo("");
                refreshOnInvalidAccNo();
                setErrorMessage("Please choose the function.");
            } else if (functionFlag.equalsIgnoreCase("A")) {
                setErrorMessage("");
//                setAcNo("");
                //refreshOnInvalidAccNo();
                flag1 = false;
                flag2 = false;
            } else if (functionFlag.equalsIgnoreCase("M")) {
                setErrorMessage("");
//                setAcNo("");
                refreshOnInvalidAccNo();
                flag1 = false;
                flag2 = false;
            } else if (functionFlag.equalsIgnoreCase("I")) {
                setErrorMessage("");
//                setAcNo("");
                refreshOnInvalidAccNo();
                flag1 = false;
                flag2 = true;
            } else {
                setErrorMessage("Invalid function choice.Fill A or M or I.");
                refreshOnInvalidAccNo();
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void disableRetireAge() {
        setDisableretirPanel("");
        try {
            List acctInfo1 = loanAcDetails.accountDetail(acNo);
            String retirement = "0";
            List code = CommonReportMethodsBean.getCode("RETIREMENT_DISPLAY_IN_CC");
            if (!code.isEmpty()) {
                retirement = code.get(0).toString();
            }
            if (!acctInfo1.isEmpty()) {
                Vector acInfoVect = (Vector) acctInfo1.get(0);
                if ((!acInfoVect.get(6).toString().equalsIgnoreCase("IND")) && retirement.equalsIgnoreCase("0")) {
                    this.setDisableretirPanel("none");
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        }
    }

    public void advancInfoTrackActivities() {
        setErrorMessage("");
        setMessage("");

        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String documentDate;
            String renewDate;
            String documentExprDate;
            // String code = this.acctNo;
//            int length = code.length();
//            int addedZero = 6 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                code = "0" + code;
//            }
            // acNo = this.orgnBrCode + this.acctType + code + this.agentCode;
            acNo = this.acctNo;
            if (docDate == null) {
                documentDate = "01/01/1900";
            } else {
                documentDate = sdf.format(docDate);
            }
            if (renewalDate == null) {
                renewDate = "01/01/1900";
            } else {
                renewDate = sdf.format(renewalDate);
            }
            if (docExprDate == null) {
                documentExprDate = "01/01/1900";
            } else {
                documentExprDate = sdf.format(docExprDate);
            }

            if (this.marginMoney.equalsIgnoreCase("") || this.marginMoney == null) {
                marginMoney = "0";
            }
            if (this.netWorth1.equalsIgnoreCase("") || this.netWorth1 == null) {
                netWorth1 = "0";
            }
            if (this.loanDuration == null || this.loanDuration.equalsIgnoreCase("") || this.loanDuration.equals("0")) {
                setErrorMessage("Please fill loan duration.");
                return;
            }
            if (this.sancAmount == null || this.sancAmount.equalsIgnoreCase("")) {
                sancAmount = "0";
            }
            if (this.monthlyIncome1.equalsIgnoreCase("") || this.monthlyIncome1 == null) {
                monthlyIncome1 = "0";
            }
            if (this.getSector().equalsIgnoreCase("0")) {
                this.setErrorMessage("Please select sector.");
                return;
            }
            if (subSector.equalsIgnoreCase("0")) {
                setErrorMessage("Please select sub sector.");
                return;
            }
            if (modeOfAdvance.equalsIgnoreCase("0")) {
                setErrorMessage("Please select mode of advance.");
                return;
            }
            if (this.secured.equalsIgnoreCase("0")) {
                setErrorMessage("Please select Type of Advances.");
                return;
            }
            if (typeOfAdvance.equalsIgnoreCase("0")) {
                setErrorMessage("Please select Term of Advance.");
                return;
            }
            /*if (purposeOfAdvance.equalsIgnoreCase("0")) {
             setErrorMessage("Please select Sub-Sector's Category(for Quarterly).");
             return;
             }
             if (guarnteeCover.equalsIgnoreCase("0")) {
             setErrorMessage("Please select Sub-Sector's Category(for Yearly).");
             return;
             }*/

            if (sickness.equalsIgnoreCase("0")) { //
                setErrorMessage("Please select Purpose of Advances.");
                return;
            }

            if (exposure != null && !exposure.equalsIgnoreCase("0") && (exposureCategory == null || exposureCategory.equalsIgnoreCase("0"))) {
                setErrorMessage("Please select exposure category .");
                return;
            }


            if (exposure != null && !exposure.equalsIgnoreCase("0") && exposureCategory != null && !exposureCategory.equalsIgnoreCase("0")) {
                if (exposureCategoryPurpose.equalsIgnoreCase("0")) {
                    setErrorMessage("Please select exposure category purpose.");
                    return;
                }
            }
//            else {
//                if (exposureCategory.equalsIgnoreCase("0")) {
//                    setErrorMessage("Please select exposure category.");
//                    return;
//                } else {
//                    if (exposureCategoryPurpose.equalsIgnoreCase("0")) {
//                        setErrorMessage("Please select exposure category purpose.");
//                        return;
//                    }
//                }
//            }

            if (schemeCode.equalsIgnoreCase("0")) {
                setErrorMessage("Please select scheme code.");
                return;
            }
            if (intTable.equalsIgnoreCase("0")) {
                setErrorMessage("Please select interest table.");
                return;
            }
            if (intType.equalsIgnoreCase("0")) {
                setErrorMessage("Please select interest type.");
                return;
            }

            if (applicantCategory.equalsIgnoreCase("0")) {
                setErrorMessage("Please select Applicant Category.");
                return;
            } else if (applicantCategory.equalsIgnoreCase("WK")) {
                if (categoryOpt.equalsIgnoreCase("0")) {
                    setErrorMessage("Please select Category Option.");
                    return;
                } else if (categoryOpt.equalsIgnoreCase("MC")) {
                    if (minorCommunity.equalsIgnoreCase("0")) {
                        setErrorMessage("Please select Minority Community.");
                        return;
                    }
                }
            }

            /*if (relation.equalsIgnoreCase("0")) {
             setErrorMessage("Please select Director.");
             return;
             } else*/ if ((!relation.equalsIgnoreCase("OTH")) && (!relation.equalsIgnoreCase("0"))) {
                if (relOwner.equalsIgnoreCase("0")) {
                    setErrorMessage("Please select Relation.");
                    return;
                }
            }
            if (drawingPwrInd.equalsIgnoreCase("0")) {
                setErrorMessage("Please select Drawing Power Indicator(DP).");
                return;
            }

            if (popuGroup.equalsIgnoreCase("0")) {
                setErrorMessage("Please select Population Group.");
                return;
            }
            if (sanctionLevel.equalsIgnoreCase("0")) {
                setErrorMessage("Please select sanction level.");
                return;
            }
            if (sanctionAuth.equalsIgnoreCase("0")) {
                setErrorMessage("Please select sanctioning authority.");
                return;
            }
            /*if (industryType.equalsIgnoreCase("0")) {
             setErrorMessage("Please select industory type.");
             return;
             }*/

            if (!npaClass.equalsIgnoreCase("0")) {
                if (assetClassReason.equalsIgnoreCase("0")) {
                    setErrorMessage("Please select Asset Class Reason.");
                    return;
                }
            }
            if (/*relation.equalsIgnoreCase("DIR") || */relation.equalsIgnoreCase("DIRREL")) {
                if (dirCustId.equalsIgnoreCase("")) {
                    setErrorMessage("Please Fill The Director's Customer ID !!!!!");
                    return;
                }
            } else {
                this.dirCustId = "";
            }

            List acctInfo = loanAcDetails.accountDetail(acNo);
            if (!acctInfo.isEmpty()) {
                Vector acInfoVect = (Vector) acctInfo.get(0);
                if (Double.parseDouble(this.getSancAmount()) != Double.parseDouble(acInfoVect.get(4).toString())) {
                    List tlLimitUpdationList = aitr.getCbsParameterinfoValue("TL_LIMIT_INCREASE");
                    if (!tlLimitUpdationList.isEmpty()) {
                        Vector tlLimitUpdationVect = (Vector) tlLimitUpdationList.get(0);
                        String tlLimitUpdation = tlLimitUpdationVect.get(0).toString();
                        if (tlLimitUpdation.equalsIgnoreCase("Y")) {
                            if (restructuring.equalsIgnoreCase("0")) {
                                setErrorMessage("Please select restructuring.");
                                return;
                            }
                        }
                    }
                }
                if (Double.parseDouble(this.getLoanDuration()) != Double.parseDouble(acInfoVect.get(5).toString())) {
                    if (restructuring.equalsIgnoreCase("0")) {
                        setErrorMessage("Please select restructuring.");
                        return;
                    }
                }

                if (acInfoVect.get(6).toString().equalsIgnoreCase("IND")) {
                    if (retirementAge.equalsIgnoreCase("")) {
                        retirementAge = "0";
                    }
                    if (Integer.parseInt(this.retirementAge) == 0 || this.retirementAge.equalsIgnoreCase("")) {
                        setErrorMessage("Please enter Retirement Age!!!");
                        return;
                    }
                }
            }
            if (industry.equalsIgnoreCase("--Select--")) {
                setErrorMessage("Please Select Industry Wise Exposure!!!");
                return;
            }
            List chkList = aitr.custIdDetail(custId);
            if (!chkList.isEmpty()) {
                Vector vect = (Vector) chkList.get(0);
                if (vect.get(0).toString().equalsIgnoreCase("02") && groupID.equalsIgnoreCase("2")) {
                    if (this.groupType.equalsIgnoreCase("0")) {
                        setErrorMessage("Please Select The Group Details...");
                        return;
                    }
                }
            }
            if (groupID == null) {
                this.groupID = "1";
            }
            if (groupID.equalsIgnoreCase("1")) {
                this.groupType = this.custId;
            }
            if (groupType == null) {
                setErrorMessage("Please Select The Group Details First");
                return;
            }

            List entitylist = CommonReportMethodsBean.getCustEntityType(acctNo);
            Vector vtr = (Vector) entitylist.get(0);
            if (vtr.get(0).toString().equalsIgnoreCase("02")) {

                if (businessIndustryType == null || businessIndustryType.equalsIgnoreCase("")) {
                    setErrorMessage("Please Select Business / Industry Type");
                    return;
                }

            }
            if (functionFlag.equalsIgnoreCase("A")) {
//                checkNullValues();
//                String erMsg = getErrorMsg();
//                if (erMsg.equals("")) {

                /*if (restructuring.equalsIgnoreCase("0")) {
                 setErrorMessage("Please select restructuring.");
                 return;
                 }*/
                /*if (npaClass.equalsIgnoreCase("0")) {
                 setErrorMsg("Please select NPA classification.");
                 return;
                 }*/
                /*if (courts.equalsIgnoreCase("0")) {
                 setErrorMsg("Please select courts.");
                 return;
                 }*/
                /*if (modeOfSettle.equalsIgnoreCase("0")) {
                 setErrorMessage("Please select mode of settlement.");
                 return;
                 }
                 if (debtWaiverReason.equalsIgnoreCase("0")) {
                 setErrorMessage("Please select Debt Waiver Reason.");
                 return;
                 }*/
                String saveResult = aitr.saveCustDetails(this.acNo, this.sector, this.subSector, this.sickness, this.modeOfAdvance, this.typeOfAdvance,
                        this.secured, this.guarnteeCover, this.industryType, this.purposeOfAdvance, this.exposure, this.restructuring,
                        this.sanctionLevel, this.sanctionAuth, this.courts, this.modeOfSettle, this.debtWaiverReason, this.assetClassReason, this.popuGroup, this.intTable, this.intType, this.schemeCode, this.npaClass, this.userName, this.netWorth1, this.marginMoney, documentDate, renewDate, this.loanDuration,
                        documentExprDate, this.relation, this.sancAmount, this.drawingPwrInd, this.monthlyIncome1, this.applicantCategory,
                        categoryOpt, minorCommunity, remarks1, this.getRelName(), this.getRelOwner(), exposureCategory, exposureCategoryPurpose, this.dirCustId, this.retirementAge, this.industry, this.groupID, this.groupType, this.businessIndustryType == null ? "" : this.businessIndustryType);
                if (saveResult.equalsIgnoreCase("Data has been posted successfully...")) {
                    refreshOnInvalidAccNo();
                    setMessage(saveResult);
                } else {
                    setMessage(saveResult);
                }
//                }
            } else if (functionFlag.equalsIgnoreCase("M")) {
                List chk = aitr.isExist(custId);
                if (!chk.isEmpty()) {
                    String acnoList = "", subq = "";
                    for (int n = 0; n < chk.size(); n++) {
                        Vector vect = (Vector) chk.get(n);
                        acnoList = acnoList.concat(subq).concat(vect.get(0).toString());
                        subq = " , ";
                    }
                    List list = CommonReportMethodsBean.getCustIdfromacNo(acNo);
                    Vector vec = (Vector) list.get(0);
                    List res = aitr.checkGroupId(vec.get(0).toString());
                    if (!res.isEmpty() && groupID.equalsIgnoreCase("0")) {
                        String custId = "";
                        for (int i = 0; i < res.size(); i++) {
                            custId += res.get(i).toString() + " ";
                        }
                        this.setMessage(" Please select group for " + custId);
                        return;
                    }
                    if (groupID.equalsIgnoreCase("0")) {
                        setMessage("Group Details exist in this account.Please remove the details first of the account/s ".concat(acnoList));
                        return;
                    }
                }
                System.out.println("intType:::2" + intType);
                String updateResult = aitr.modifyCustDetails(acNo, sector, subSector, sickness, modeOfAdvance, typeOfAdvance,
                        secured, guarnteeCover, industryType, purposeOfAdvance, exposure, restructuring,
                        sanctionLevel, sanctionAuth, courts, modeOfSettle, debtWaiverReason, assetClassReason, popuGroup, intTable, intType, schemeCode, npaClass, userName, netWorth1, marginMoney, documentDate, renewDate, loanDuration,
                        documentExprDate, relation, sancAmount, drawingPwrInd, monthlyIncome1, applicantCategory,
                        categoryOpt, minorCommunity, remarks1, this.getRelName(), this.getRelOwner(), exposureCategory, exposureCategoryPurpose, this.dirCustId, this.retirementAge,
                        this.industry, this.groupID, this.groupType, this.businessIndustryType == null ? "" : this.businessIndustryType);
                if (updateResult.equalsIgnoreCase("Data has been updated successfully...")) {
                    refreshOnInvalidAccNo();
                    setMessage(updateResult);
                } else {
                    setMessage(updateResult);
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }

    }

    public void visibilityOfCatgOption() {
        try {
            List listForCategOption = new ArrayList();
            listForCategOption = aitr.getListAsPerRequirement("209", getApplicantCategory(), getCategoryOpt(), getMinorCommunity(), "0", "0", ymd.format(new Date()), 0);
            categoryOptOption = new ArrayList<SelectItem>();
            categoryOptOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForCategOption.size(); i++) {
                Vector element = (Vector) listForCategOption.get(i);
                categoryOptOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////
            List listForMinorCommu = new ArrayList();
            listForMinorCommu = aitr.getListAsPerRequirement("204", getApplicantCategory(), getCategoryOpt(), getMinorCommunity(), "0", "0", ymd.format(new Date()), 0);
            minorCommunityOption = new ArrayList<SelectItem>();
            minorCommunityOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForMinorCommu.size(); i++) {
                Vector element = (Vector) listForMinorCommu.get(i);
                minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
//        if (applicantCategory.equalsIgnoreCase("GN")) {
//            setCategoryOpt("");
//            flagForAppCateg = true;
//        } else if (applicantCategory.equalsIgnoreCase("WK")) {
//            flagForAppCateg = false;
//        } else {
//            flagForAppCateg = false;
//        }
    }

    public void onblurCategoryWiseMinorityVal() {
        try {
            List listForMinorCommu = new ArrayList();
            listForMinorCommu = aitr.getListAsPerRequirement("204", getApplicantCategory(), getCategoryOpt(), getMinorCommunity(), "0", "0", ymd.format(new Date()), 0);
            minorCommunityOption = new ArrayList<SelectItem>();
            minorCommunityOption.add(new SelectItem("0", ""));
            for (int i = 0; i < listForMinorCommu.size(); i++) {
                Vector element = (Vector) listForMinorCommu.get(i);
                minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void viewDataCall() {
        if (functionFlag.equalsIgnoreCase("I")) {
            setMessage("");
            setErrorMessage("");
            viewData();
            //viewDataFromAccMaster();
            flag2 = true;
        } else if (functionFlag.equalsIgnoreCase("M")) {
            setMessage("");
            setErrorMessage("");
            viewData();
            //viewDataFromAccMaster();
            flag2 = false;
        } else if (functionFlag.equalsIgnoreCase("A")) {
            setMessage("");
            setErrorMessage("");
            //viewDataFromAccMaster();
            flag2 = false;
        } else {
            setMessage("");
            setErrorMessage("Invalid function choice.Fill A or M or I.");
        }
    }

//    public void viewDataFromAccMaster() {
//        try {
//            ctx = new InitialContext();
//            aitr = (AdvancesInformationTrackingRemote) ctx.lookup(AdvancesInformationTrackingRemote.class.getName());
//            List selectFromAccMaster = aitr.getDetailsFromAccMaster(acNo);
//            if (selectFromAccMaster.isEmpty()) {
//                setAcOpenDt("");
//                setPartyName("");
//                setStatus("");
//            } else {
//                for (int i = 0; i < selectFromAccMaster.size(); i++) {
//                    Vector vecForAccMaster = (Vector) selectFromAccMaster.get(i);
//                    String acOpenDate = vecForAccMaster.get(2).toString();
//                    acOpenDate = acOpenDate.substring(6) + "/" + acOpenDate.substring(4, 6) + "/" + acOpenDate.substring(0, 4);
//                    setAcOpenDt(acOpenDate);
//                    setStatus(vecForAccMaster.get(3).toString());
//                    setPartyName(vecForAccMaster.get(1).toString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public void viewData() {
        String code = this.acctNo;
//        int length = code.length();
//        int addedZero = 6 - length;
//        for (int i = 1; i <= addedZero; i++) {
//            code = "0" + code;
//        }
//        acNo = this.orgnBrCode + this.acctType + code + this.agentCode;
        try {
            List selectResultFromLoanBorrowerTable = aitr.selectCustDetails(this.acctNo);
            if (selectResultFromLoanBorrowerTable.isEmpty()) {
                if (!functionFlag.equalsIgnoreCase("A")) {
                    setErrorMessage("Invalid account number.");
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
                    setSancAmount(sancAmount);
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
                    setBusinessIndustryType(vecForLoanBorrowerDtl.get(48).toString());
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void refreshOnInvalidAccNo() {
        setSector("");
        setSubSector("");
        setPurposeOfAdvance("");
        setModeOfAdvance("");
        setTypeOfAdvance("");
        setSecured("");
        setGuarnteeCover("");
        setIndustryType("");
        setSickness("");
        setExposure("");
        setExposureCategory("");
        setExposureCategoryPurpose("");
        setRestructuring("");
        setSanctionLevel("");
        setSanctionAuth("");
        setIntTable("");
        setIntType("");
        setSchemeCode("");
        setNpaClass("");
        setCourts("");
        setModeOfSettle("");
        setDebtWaiverReason("");
        setAssetClassReason("");
        setPopuGroup("");
        setCreatedByUId("");
        setCreationDate("");
        setLastUpdateUId("");
        setLastUpdationDate("");
        setTotalModifications("");
        //setAcOpenDate("");
        setStatus("");
        //setPartyname("");
        setNetWorth1("0.0");
        setLoanDuration("0");
        setMarginMoney("0.0");
        setSecODRoi("0");
        setSecODScheme("");
        setMargin("");
        setDocDate(null);
        setRenewalDate(null);
        setLoanDuration("");
        setDocExprDate(null);
        setRelation("");
        setSancAmount("0.0");
        setDisableSancAmt(false);
        setDrawingPwrInd("");
        setMonthlyIncome1("0.0");
        setApplicantCategory("");
        setCategoryOpt("");
        setMinorCommunity("");
        setRemarks1("");
        setMessage("");
        this.setRelOwner("");
        setRelName("");
        this.setDisableretirPanel("");
        this.setRetirementAge("");
        this.setIndustry("");
        this.lblGroupID = "";
        this.lblGroupType = "";
        this.ddGroupId = "";
        this.ddGroupType = "";
        this.setGroupID("0");
        this.setGroupType("0");
        this.disableRel = false;
        this.disableRelOwn = false;
        this.setRelationOption(new ArrayList<SelectItem>());
        this.setRelOwnerOption(new ArrayList<SelectItem>());
        this.setBusinessIndustryTypeInvisiable("none");
        this.setBusinessIndustryType("");
//        this.setDisbursement("");
    }

    /**
     * Code for shipra tab*
     */
    public void onLoadDisburest() {
        try {

            setDisburestType(new ArrayList<SelectItem>());
            disburestType.add(new SelectItem("0", " "));
            disburestType.add(new SelectItem("S", "Single"));
            disburestType.add(new SelectItem("M", "Multiple"));

            setDeleteDisburest(new ArrayList<SelectItem>());
            deleteDisburest.add(new SelectItem("0", " "));
            deleteDisburest.add(new SelectItem("Y", "Yes"));
            deleteDisburest.add(new SelectItem("N", "No"));

        } catch (Exception e) {
            message = e.getMessage();
        }

    }

    public void setDataDisburest() {
        List disburestList = new ArrayList();
        try {
            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I") || functionFlag.equalsIgnoreCase("A")) {

                disburestList = aitr.getappLoanDetails(acNo);
                if (disburestList.size() > 0) {
                    Vector docVector = (Vector) disburestList.get(0);
//                    setSanctionAmt(new BigDecimal(Double.parseDouble(docVector.get(0).toString())).toString());
                    setSanctionAmt(sancAmount);
                    setSanctionDate(sdf.parse(docVector.get(1).toString()));
                    if (docVector.get(2) == null) {
                        setProcessingFee("");
                    } else {

                        setProcessingFee(docVector.get(2).toString());
                    }
                    if (docVector.get(3) == null) {
                        setRemarkAIT("");

                    } else {
                        setRemarkAIT(docVector.get(3).toString());
                    }
                    setDisableFlagAmt(false);
                    setDisableFlagDate(false);
                    setDisableRemarks(false);
                    setDisableFlagProcessing(false);
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void SetValueDisburest() throws ParseException {
        try {
            setErrorMessage("");
            setMessage("");
            int addAmtCount = 0;
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<DisbursementSchedule> disbstSchTemp = disbstSch;
            if (validateDisbursementSchedule().equalsIgnoreCase("false")) {
                return;
            }
            if (disbursetmentType.equalsIgnoreCase("S")) {
                if (disbstSchTemp.size() == 1) {
                    setErrorMessage("Only Single Disbursetment Can Perform");
                    return;
                }
            }
            //totalDisburstment = totalDisburstment + Float.parseFloat(disbursetmentAmt.toString());
            Float amt = null;
            if (sanctionAmt == null || sanctionAmt.equalsIgnoreCase("")) {
                setErrorMessage("SanctionAmt Can Not be Blank");
                totalDisburstment = 0.0f;
                return;
            } else {
                Matcher sanctionAmtCheck = p.matcher(sanctionAmt);
                if (!sanctionAmtCheck.matches()) {
                    setErrorMessage("Enter Numeric Value for sanction Amtount.");
                    return;
                }
            }
            if (!sanctionAmt.matches("[0-9.]*")) {
                setErrorMessage("Please Enter Numeric Value For Amount Sanctioned.");
            }
            if (this.sanctionAmt.contains(".")) {
                if (this.sanctionAmt.indexOf(".") != this.sanctionAmt.lastIndexOf(".")) {
                    setErrorMessage("Invalid  Amount Sanctioned.Please Fill The Amount Sanctioned Correctly.");
                }
            }
            if (Double.parseDouble(sanctionAmt) < 0) {
                setErrorMessage("Please Enter Valid  Amount Sanctioned.");
            }

            //  System.out.println("disbstSch -->" + disbstSch.size());
            /**
             * **************** Add By Shipra *************
             */
            if (disbstSch.isEmpty()) {
                if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("A")) {
                    totalDisburstment = Float.parseFloat(getDisbursetmentAmt());
                    if (disbursetmentDate.before(sanctionDate)) {
                        setErrorMessage("Disburstment Date can not be Less than Sanction Date");
                        totalDisburstment = 0.0f;
                        return;
                    }

                    if (totalDisburstment > Double.parseDouble(getSanctionAmt())) {
                        setErrorMessage("Total Disburstment can not be Greater than SanctionAmt");
                        totalDisburstment = 0.0f;
                        return;

                    }
                }

            } /**
             * **************** Ended *********************
             */
            else if (disbstSch.size() > 0) {
                // System.out.println("totalDisburstment -->" + totalDisburstment);
                if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("A")) {
                    totalDisburstment = Float.parseFloat(getDisbursetmentAmt());
                    for (int i = 0; i < disbstSchTemp.size(); i++) {
                        String disbDelFlag = disbstSchTemp.get(i).getDeleteFlagDisburest();
                        //  System.out.println("disbDelFlag -->" + disbDelFlag);
                        if (disbDelFlag.equalsIgnoreCase("N")) {
                            if (deleteFlagDisburest.equalsIgnoreCase("N")) {
                                totalDisburstment = totalDisburstment + Float.parseFloat(disbstSchTemp.get(i).getDisbursetmentAmt());
                                if (disbursetmentDate.before(sanctionDate)) {
                                    setErrorMessage("Disburstment Date can not be Less than Sanction Date");
                                    totalDisburstment = 0.0f;
                                    return;
                                }
                                if (totalDisburstment > Float.parseFloat(getSanctionAmt())) {
                                    // System.out.println("totalDisburstment --> 5" + totalDisburstment);
                                    setErrorMessage("Total Disburstment can not be Greater than SanctionAmt");
                                    totalDisburstment = 0.0f;
                                    return;
                                }
                            }
                        }
                    }
                    for (int i = 0; i < disbstSchTemp.size(); i++) {
                        try {
                            String disbursetmentDatetmp = disbstSchTemp.get(i).getDisbursetmentDate();
                            String disbursetAmtTbl = disbstSchTemp.get(i).getDisbursetmentAmt();
                            String disbDelFlag = disbstSchTemp.get(i).getDeleteFlagDisburest();
                            if (sdf.parse(disbursetmentDatetmp).equals(disbursetmentDate)) {
                                if (disbDelFlag.equalsIgnoreCase("N")) {
                                    if (deleteFlagDisburest.equalsIgnoreCase("N")) {
                                        amt = Float.parseFloat(getDisbursetmentAmt()) + Float.parseFloat(disbursetAmtTbl);
                                        disbstSch.get(i).setDisbursetmentAmt(amt.toString());
                                        addAmtCount = 1;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            message = e.getMessage();
                        }
                    }
                }

            }
            setErrorMessage("");
            DisbursementSchedule disDetail = new DisbursementSchedule();
            if (amt == null) {
                disDetail.setSno(getSno());
                disDetail.setDisbursetmentAmt(getDisbursetmentAmt());
                disDetail.setDisbursetmentDate(sdf.format(getDisbursetmentDate()));
                disDetail.setFlowId(getFlowId());
                disDetail.setRemarksDisbursment(getRemarksDisbursment());
                disDetail.setAcno(acNo);
                disDetail.setDeleteFlagDisburest(deleteFlagDisburest);
                if (functionFlag.equalsIgnoreCase("A")) {
                    disDetail.setCounterSaveUpdate("Save");
                }

                disbstSch.add(disDetail);
                amt = null;
            }
            totalDisburstment = 0.0f;
            resestDisburestDetail();
            if (functionFlag.equalsIgnoreCase("M")) {
                if (selectCountDisbst != 1) {
                    for (int i = 0; i < disbstSchTemp.size(); i++) {
                        String disbursetmentDatetmp = disbstSchTemp.get(i).getDisbursetmentDate().toString();
                        if (sdf.parse(disbursetmentDatetmp).equals(disbursetmentDate)) {
                            disDetail.setCounterSaveUpdate("Save");
                            disbstDtlTmp.add(disDetail);
                            resestDisburestDetail();
                            return;
                        }
                    }
                } else {

                    if (currentItemDisbst.getDisbursetmentDate().equalsIgnoreCase(sdf.format(disbursetmentDate))) {
                        if (!currentItemDisbst.getDisbursetmentAmt().equalsIgnoreCase(disbursetmentAmt) || !currentItemDisbst.getDeleteFlagDisburest().equalsIgnoreCase(deleteFlagDisburest)) {
                            disDetail.setCounterSaveUpdate("Update");
                            resestDisburestDetail();
                            disbstDtlTmp.add(disDetail);
                        }
                        selectCountDisbst = 0;
                    }
                }
            }
            addAmtCount = 0;
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fetchCurrentRowDisburest(ActionEvent event) {
        String sNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
        currentRowDisbst = currentRowDisbst + 1;
        currentRowDisbst = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DisbursementSchedule itemDisbst : disbstSch) {
            if (itemDisbst.getDisbursetmentAmt().equals(sNo)) {
                currentItemDisbst = itemDisbst;
                break;
            }
        }
    }

    public void selectDisburest() {
        disableFlagDisbst1 = true;
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            selectCountDisbst = 1;
            if (!(flowId.equalsIgnoreCase("") && currentItemDisbst.getFlowId() != null)) {
                if (!flowId.equalsIgnoreCase(currentItemDisbst.getFlowId())) {
                    if (!sdf.format(disbursetmentDate).equalsIgnoreCase(currentItemDisbst.getDisbursetmentDate())) {
                        //  if (!disbursetmentAmt.equalsIgnoreCase(currentItemDisbst.getDisbursetmentAmt())) {
                        if (Float.parseFloat(disbursetmentAmt) != Float.parseFloat(currentItemDisbst.getDisbursetmentAmt())) {
                            count2Disbst = count1Disbst;
                            count1Disbst = count1Disbst + 1;
                            if (count2Disbst != count1Disbst) {
                                SetValueDisburest();
                            }
                        } else {
                            count1Disbst = 0;
                        }
                    }
                }
            }
            setDisbursetmentAmt(currentItemDisbst.getDisbursetmentAmt());
            setDisbursetmentDate(sdf.parse(currentItemDisbst.getDisbursetmentDate()));
            setFlowId(currentItemDisbst.getFlowId());
            setRemarksDisbursment(currentItemDisbst.getRemarksDisbursment());
            setDeleteFlagDisburest(currentItemDisbst.getDeleteFlagDisburest());
            disbstSch.remove(currentRowDisbst);
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void setValuesDisbursementScheduleTbl() {
        setErrorMessage("");
        setMessage("");
        disbstSch = new ArrayList<DisbursementSchedule>();
        List detailList = new ArrayList();
        try {

            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I")) {
                detailList = aitr.getDisbursementSchedule(acNo);
                for (int i = 0; i < detailList.size(); i++) {
                    DisbursementSchedule detail = new DisbursementSchedule();
                    Vector detailVector = (Vector) detailList.get(i);
                    detail.setFlowId(detailVector.get(0).toString());
                    detail.setDisbursetmentDate(detailVector.get(1).toString());
                    detail.setDisbursetmentAmt(detailVector.get(2).toString());
                    detail.setRemarksDisbursment(detailVector.get(3).toString());
                    detail.setDeleteFlagDisburest(detailVector.get(4).toString());
                    setSno(detailVector.get(5).toString());
                    disbstSch.add(detail);
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void setValuesDisbursementDetailTbl() {
        setErrorMessage("");
        setMessage("");
        disbstDtl = new ArrayList<DisbursementDetail>();
        List detailList = new ArrayList();
        try {
            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I") || functionFlag.equalsIgnoreCase("A")) {
                detailList = aitr.getDisbursementDetail(acNo);
                for (int i = 0; i < detailList.size(); i++) {
                    DisbursementDetail detail = new DisbursementDetail();
                    Vector detailVector = (Vector) detailList.get(i);
                    detail.setSno(detailVector.get(0).toString());
                    detail.setDisbursetmentAmt(detailVector.get(1).toString());
                    detail.setDisbursetmentDate(detailVector.get(2).toString());
                    disbstDtl.add(detail);
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public String validateDisbursementSchedule() {
//        setErrorMessage("");
//        setMessage("");
        String msg = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (flowId.equalsIgnoreCase("")) {
            msg = msg + "Please Enter The Flow Id.";
        }

        if (disbursetmentDate == null) {
            msg = msg + "Please Enter The Disbursetment Date.";
        }
        if (remarksDisbursment.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Remarks .";
        }

        if (deleteFlagDisburest.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Delete Flag.";
        }

        if (disbursetmentAmt.equalsIgnoreCase("0")) {
            msg = msg + "Please Enter Disbursetment Amount.";
        } else {
            if (!disbursetmentAmt.matches("[0-9.]*")) {
                msg = msg + "Enter Numeric Value for Disbursetment Amount.";
            } else {
                if (this.disbursetmentAmt.contains(".")) {
                    if (this.disbursetmentAmt.indexOf(".") != this.disbursetmentAmt.lastIndexOf(".")) {
                        msg = msg + "Invalid  Disbursetment Amount.Please Fill The Disbursetment Amount Correctly.";
                    }
                } else {
                    if (Float.parseFloat(disbursetmentAmt) < 0) {
                        msg = msg + "Please Enter Valid  Disbursetment Amount.";
                    }
                }
            }
        }

        if (!msg.equalsIgnoreCase("")) {
            setErrorMessage(msg);
            return "False";
        }
        return "true";
    }

    public void saveActionDisbursementSchedule() {
        setErrorMessage("");
        setMessage("");
        String msg = "";
        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (sanctionAmt.length() == 0 || sanctionAmt.equalsIgnoreCase("")) {
                setErrorMessage("Please Enter Amount Sanctioned.");
                return;
            } else {

                if (!sanctionAmt.matches("[0-9.]*")) {
                    setErrorMessage("Please Enter Numeric Value For Amount Sanctioned.");
                    return;
                }
                if (this.sanctionAmt.contains(".")) {
                    if (this.sanctionAmt.indexOf(".") != this.sanctionAmt.lastIndexOf(".")) {
                        setErrorMessage("Invalid  Amount Sanctioned.Please Fill The Amount Sanctioned Correctly.");
                        return;
                    }
                }
                if (Float.parseFloat(sanctionAmt) < 0) {
                    setErrorMessage("Please Enter Valid  Amount Sanctioned.");
                    return;
                }
            }
            if (sanctionDate == null) {
                setErrorMessage("Please Enter Sanction Date.");
                return;
            }

            if (functionFlag.equalsIgnoreCase("A")) {
                if (disbstSch.isEmpty()) {
                    setErrorMessage("Please Enter Data in Disbursement Schedule");
                    return;
                }
            } else {
                if (disbstDtlTmp.isEmpty()) {
                    setErrorMessage("Please Enter Data in Disbursement Schedule");
                    return;
                }
            }

            // String code = this.acctNo;
//            int length = code.length();
//            int addedZero = 6 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                code = "0" + code;
//            }
            // acNo = this.orgnBrCode + this.acctType + code + this.agentCode;
            if (functionFlag.equalsIgnoreCase("A")) {
                String brcode = fts.getCurrentBrnCode(acNo);
                msg = aitr.saveDisbursementSchedule(disbstSch, brcode, ymd.format(sdf.parse(todayDate)), acctNo);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Data Saved Succesfully ");
                } else if (msg.equalsIgnoreCase("Check the today date you have passed")) {
                    setMessage(msg);
                }

            }
            if (functionFlag.equalsIgnoreCase("M")) {
                //System.out.println("modify");
                msg = aitr.saveDisbursementSchedule(disbstDtlTmp, orgnBrCode, ymd.format(sdf.parse(todayDate)), acNo);
                if (msg.equalsIgnoreCase("true")) {
                    setMessage("Data Updated Succesfully ");
                } else if (msg.equalsIgnoreCase("Check the today date you have passed")) {
                    setMessage(msg);
                }
            }
            totalDisburstment = 0;
            disbstDtlTmp.clear();
            disbstSch.clear();

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resestDisburestDetail() {
        String d = "";
        setDisbursetmentAmt("");
        setFlowId("");
        setRemarksDisbursment("");
        setAcno("");
        setDeleteFlagDisburest("");
        setSno("");
    }

    public void disableDisburest() {
        setDisableFlagDisbst(true);
        setDisableFlagDisbst1(true);
        setDisableFlagAmt(true);
        setDisableFlagDate(true);
        setDisableRemarks(true);
        setDisableFlagProcessing(true);
    }

    public void enableDisburest() {
        setDisableFlagDisbst(false);
        setDisableFlagDisbst1(false);
        setDisableFlagAmt(false);
        setDisableFlagDate(false);
        setDisableRemarks(false);
        setDisableFlagProcessing(false);
    }

    public void onLoadRepaymentSchedule() {
        setInvestmentTypeList(new ArrayList<SelectItem>());
        investmentTypeList.add(new SelectItem("0", ""));
        investmentTypeList.add(new SelectItem("EIMWOMP", "Equal Installment Without M.P"));
        investmentTypeList.add(new SelectItem("EIWIMP", "Equated Installment With M.P"));
        investmentTypeList.add(new SelectItem("EPWIMP", "Equated Principle with M.P"));
        investmentTypeList.add(new SelectItem("EPWOMP", "Equated Principle without M.P"));
        investmentTypeList.add(new SelectItem("CSTMZE", "Customize"));

        setPeriodList(new ArrayList<SelectItem>());
        periodList.add(new SelectItem("0", " "));
        periodList.add(new SelectItem("D", "Daily"));
        periodList.add(new SelectItem("W", "Weekly"));
        periodList.add(new SelectItem("M", "Monthly"));
        periodList.add(new SelectItem("Q", "Quarterly"));
        periodList.add(new SelectItem("HY", "Half Yearly"));
        periodList.add(new SelectItem("Y", "Yearly"));
        setStatusmsg("To form a new Amortization Schedule Choose the Type of Installment Plan");

        setIntServeList(new ArrayList<SelectItem>());
        intServeList.add(new SelectItem("0", " "));
        intServeList.add(new SelectItem("Y", "Yes"));
        intServeList.add(new SelectItem("N", "No"));

        setIntPrincipleList(new ArrayList<SelectItem>());
        intPrincipleList.add(new SelectItem("0", " "));
        intPrincipleList.add(new SelectItem("EIDEM", "Equated Installment Demand"));
        intPrincipleList.add(new SelectItem("INDEM", "Interest Demand"));
        intPrincipleList.add(new SelectItem("PRDEM", "Principle Demand"));
        intPrincipleList.add(new SelectItem("SPI", "Simple(Prin+Int)"));
        setIntPrincipleDisable(true);
        setIntServeDisable(true);

    }

    public void setStartDateOnPeriodicity() {
        setMessage("");
        setErrorMessage("");
        try {
            if (instPlan.isEmpty()) {
                int moratoriumpd = CommonReportMethodsBean.getMoratoriumpd(acNo);
                String mortariumDt = CbsUtil.monthAdd(ymd.format(sdf.parse(acOpenDt)), moratoriumpd);
                if (investmentType.equalsIgnoreCase("EIWIMP") || investmentType.equalsIgnoreCase("EPWIMP")) {
                    if (periodicity.equalsIgnoreCase("D")) {
                        startDate = ymd.parse(CbsUtil.dateAdd(mortariumDt, 1));
                    } else if (periodicity.equalsIgnoreCase("W")) {
                        startDate = ymd.parse(CbsUtil.dateAdd(mortariumDt, 7));
                    } else if (periodicity.equalsIgnoreCase("M")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(mortariumDt, 1));
                    } else if (periodicity.equalsIgnoreCase("Q")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(mortariumDt, 3));
                    } else if (periodicity.equalsIgnoreCase("HY")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(mortariumDt, 6));
                    } else if (periodicity.equalsIgnoreCase("Y")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(mortariumDt, 12));
                    }
                } else if (investmentType.equalsIgnoreCase("EIMWOMP") || investmentType.equalsIgnoreCase("EPWOMP")) {
                    if (periodicity.equalsIgnoreCase("D")) {
                        startDate = ymd.parse(CbsUtil.dateAdd(ymd.format(sdf.parse(acOpenDt)), 1));
                    } else if (periodicity.equalsIgnoreCase("W")) {
                        startDate = ymd.parse(CbsUtil.dateAdd(ymd.format(sdf.parse(acOpenDt)), 7));
                    } else if (periodicity.equalsIgnoreCase("M")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(ymd.format(sdf.parse(acOpenDt)), 1));
                    } else if (periodicity.equalsIgnoreCase("Q")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(ymd.format(sdf.parse(acOpenDt)), 3));
                    } else if (periodicity.equalsIgnoreCase("HY")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(ymd.format(sdf.parse(acOpenDt)), 6));
                    } else if (periodicity.equalsIgnoreCase("Y")) {
                        startDate = ymd.parse(CbsUtil.monthAdd(ymd.format(sdf.parse(acOpenDt)), 12));
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void calculateActionRepayment() {
//        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
//        Date date = new Date();
        setMessage("");
        setErrorMessage("");
        String msg = "";
        String resultList;
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (startDate == null) {
            msg = msg + "Please Enter the start Date.";
        }
        if (investmentType == null || investmentType.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Type Of Investment Plan.";
        } else {

            if (investmentType.equalsIgnoreCase("CSTMZE")) {
                if (intPrinciple.equalsIgnoreCase("0") || intPrinciple == null) {
                    msg = msg + "Please Select Int/ Principle.";
                }
                String intFreq = "";
                if (intPrinciple.equalsIgnoreCase("SPI")) {
                    try {
                        intFreq = CommonReportMethodsBean.getIntAppFreq(acNo);
                        if (!intFreq.equalsIgnoreCase("S")) {
                            msg = msg + " Please fill Simple A/c No.!";
                        }
                    } catch (Exception ex) {
                        message = ex.getMessage();
                    }
                    if (!periodicity.equalsIgnoreCase("M")) {
                        msg = msg + " Please Select Periodicity only Monthly Option !";
                    }
                }
            } else if (investmentType.equalsIgnoreCase("EIWIMP") || investmentType == null) {

                if (intServe.equalsIgnoreCase("0")) {
                    msg = msg + "Please Select Interest Serve.";
                }
            }

        }
        if (periods.equalsIgnoreCase("") || periods.equalsIgnoreCase("0")) {
            msg = msg + "Please Enter Period.";
        } else {
            Matcher period = p.matcher(periods);
            if (!period.matches()) {
                msg = msg + " Period Should be numeric";
            }
            if (!periods.matches("[0-9.]*")) {
                msg = msg + "Period Should be numeric.";
                return;
            }
            if (periodicity.equalsIgnoreCase("M")) {
                try {
                    int mDiff = CbsUtil.monthDiff(sdf.parse(getAcOpenDt()), new Date());
                    if (Integer.parseInt(periods) + mDiff > Integer.parseInt(getLoanDuration())) {
                        msg = msg + "Reschudle Period must be equal or less than Loan Duration, Please update Loan duration first";
                    }
                } catch (ParseException ex) {
                    message = ex.getMessage();
                }
            }
        }
        if (periodicity.equalsIgnoreCase("0")) {
            msg = msg + "Please Select Periodicity.";
        }
        if (!((investmentType.equalsIgnoreCase("CSTMZE") && intPrinciple.equalsIgnoreCase("PRDEM"))
                || (investmentType.equalsIgnoreCase("EPWOMP")) || (investmentType.equalsIgnoreCase("EPWIMP")))) {
            if (rateOfInst.equalsIgnoreCase("") || rateOfInst.equalsIgnoreCase("0") || rateOfInst.equalsIgnoreCase("0.0")) {
                msg = msg + "Please Enter Rate Of Interest.";
            } else {
                Matcher rate = p.matcher(rateOfInst);
                if (!rate.matches()) {
                    msg = msg + " Rate Of Interest Should be numeric";
                } else if (Float.parseFloat(rateOfInst) <= 0) {
                    msg = msg + "ROI Can not be Zero";
                }
            }
        }

        if (!msg.equalsIgnoreCase("")) {
            setErrorMessage(msg);
            return;
        } else {
            try {
                amortSch.clear();
                int i, j, k, l, m, n, o, q;

                if (functionFlag.equalsIgnoreCase("A") || functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I")) {
                    if (investmentType.equalsIgnoreCase("CSTMZE") && intPrinciple.equalsIgnoreCase("SPI")) {
                        resultList = aitr.Calculate(acNo, Integer.parseInt(instType), Float.parseFloat(osBalance), Integer.parseInt(periods),
                                Float.parseFloat(rateOfInst), ymd.format(startDate), Float.parseFloat(sanctionedAmt), periodicity, intPrinciple, "S");
                    } else {
                        resultList = aitr.Calculate(acNo, Integer.parseInt(instType), Float.parseFloat(osBalance), Integer.parseInt(periods),
                                Float.parseFloat(rateOfInst), ymd.format(startDate), Float.parseFloat(sanctionedAmt), periodicity, intPrinciple, "C");
                    }

                    if (resultList.contains("[")) {
                        String[] values = null;
                        resultList = resultList.replace("]", "");
                        resultList = resultList.replace("[", "");
                        resultList = resultList.replace(":", "");
                        String spliter = ", ";
                        values = resultList.split(spliter);
                        for (i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, q = 7; i < (values.length); i = i + 8, j = j + 8, k = k + 8, l = l + 8, m = m + 8, n = n + 8, o = o + 8, q = q + 8) {
                            AmortizationSchedule rd = new AmortizationSchedule();
                            rd.setSno(values[i]);
                            rd.setDueDate(values[j]);
                            rd.setPrincipleAmt(formatter2.format(Double.parseDouble(values[k])));
                            rd.setInterestAmt(formatter2.format(Double.parseDouble(values[l])));
                            rd.setInstallment(formatter2.format(Double.parseDouble(values[m])));
                            rd.setPeriodicity(values[n]);
                            rd.setOpeningPrinciple(formatter2.format(Double.parseDouble(values[o])));
                            rd.setClosingPrinciple(formatter2.format(Double.parseDouble(values[q]) < 0 ? 0 : Double.parseDouble(values[q])));
                            amortSch.add(rd);
                        }
                    } else {
                        this.setErrorMessage(resultList);
                    }
                }
            } catch (ApplicationException e) {
                message = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
            }
        }
        if (isUpdateDisable() == false) {
            setStatusmsg("If Satisfied with the Amortization Schedule then #Click Update to Save New Schedule or Close to Exit");
        } else {
            setStatusmsg("If Satisfied with the Amortization Schedule then # Click Reschedule to Save New Schedule or Close to Exit");
        }

    }

    public void resheduleActionRepayment() {
        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setErrorMessage("Account has been closed!");
                return;
            }
            if (periodicity.equalsIgnoreCase("M")) {
                int mDiff = CbsUtil.monthDiff(sdf.parse(getAcOpenDt()), new Date());
                if (Integer.parseInt(periods) + mDiff > Integer.parseInt(getLoanDuration())) {
                    throw new ApplicationException("Reschudle Period must be equal or less than Loan Duration, Please update Loan duration first");
                }
            }
            String result = null;

            List resultList = new ArrayList();
            String a[][] = new String[amortSch.size()][8];
            for (int i = 0; i < amortSch.size(); i++) {

                a[i][0] = acno;
                a[i][1] = amortSch.get(i).getDueDate().toString();
                a[i][2] = amortSch.get(i).getPrincipleAmt().toString();
                a[i][3] = amortSch.get(i).getInterestAmt().toString();
                a[i][4] = amortSch.get(i).getInstallment().toString();
                a[i][5] = amortSch.get(i).getPeriodicity().toString();
                a[i][6] = String.valueOf(amortSch.get(i).getOpeningPrinciple());
                a[i][7] = String.valueOf(amortSch.get(i).getClosingPrinciple());

            }
            for (int i = 0; i < amortSch.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    resultList.add(combinedArray);
                }
            }

            if (functionFlag.equalsIgnoreCase("A") || functionFlag.equalsIgnoreCase("M")) {
                result = aitr.UpdateReShedule(acNo, resultList, "R", userName);
                // this.setErrorMessage(result);
                this.setMessage(result);
            }
            setValuesInstallmentPlanTbl();
            resetRepaymentSchedule();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void updateActionRepayment() {
        try {
            String result = null;
            if (periodicity.equalsIgnoreCase("M")) {
                int mDiff = CbsUtil.monthDiff(sdf.parse(getAcOpenDt()), new Date());
                if (Integer.parseInt(periods) + mDiff > Integer.parseInt(getLoanDuration())) {
                    throw new ApplicationException("Reschudle Period must be equal or less than Loan Duration, Please update Loan duration first");
                }
            }

            List resultList = new ArrayList();
            String a[][] = new String[amortSch.size()][8];
            for (int i = 0; i < amortSch.size(); i++) {

                a[i][0] = acno;
                a[i][1] = amortSch.get(i).getDueDate().toString();
                a[i][2] = amortSch.get(i).getPrincipleAmt().toString();
                a[i][3] = amortSch.get(i).getInterestAmt().toString();
                a[i][4] = amortSch.get(i).getInstallment().toString();
                a[i][5] = amortSch.get(i).getPeriodicity().toString();
                a[i][6] = String.valueOf(amortSch.get(i).getOpeningPrinciple());
                a[i][7] = String.valueOf(amortSch.get(i).getClosingPrinciple());
            }
            for (int i = 0; i < amortSch.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    resultList.add(combinedArray);
                }
            }

            if (functionFlag.equalsIgnoreCase("A") || functionFlag.equalsIgnoreCase("M")) {
                result = aitr.UpdateReShedule(acNo, resultList, "U", userName);
                //  this.setErrorMessage(result);
                this.setMessage(result);
            }
            setValuesInstallmentPlanTbl();
            resetRepaymentSchedule();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void setValuesInstallmentPlanTbl() {
        instPlan = new ArrayList<InstallmentPlan>();
        List instList = new ArrayList();
        double openingPrin = 0;
        try {
            Map<String, Double> getOpBalmap = new HashMap<String, Double>();
            openingPrin = aitr.repaymentOpeingPrinciple(getAcNo());

            if (!aitr.isEmirescheduled(getAcNo()).isEmpty()) {
                List list = aitr.getOpeningBalByRescheduleDate(getAcNo());
                if (!list.isEmpty()) {
                    Vector vtr = (Vector) list.get(0);
                    double outBal = Math.abs(new Double(vtr.get(1).toString()));
                    getOpBalmap.put(vtr.get(0).toString(),outBal );
                }
            }

            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I") || functionFlag.equalsIgnoreCase("A")) {
                instList = aitr.getInstallmentPlan(acNo);
                System.out.println("instList -->" + instList.size());
                if (instList.size() > 0) {
                    for (int i = 0; i < instList.size(); i++) {
                        InstallmentPlan intPlan = new InstallmentPlan();
                        Vector detailVector = (Vector) instList.get(i);
                        intPlan.setSno(detailVector.get(0).toString());
                        intPlan.setDueDate(detailVector.get(1).toString());
                        if (getOpBalmap.containsKey(detailVector.get(1).toString())) {
                            openingPrin = getOpBalmap.get(detailVector.get(1).toString()) == null ? openingPrin : getOpBalmap.get(detailVector.get(1).toString());
                        }
                        intPlan.setOpeningPrinciple(new BigDecimal(formatter2.format(openingPrin < 0 ? 0 : openingPrin)));
                        intPlan.setPrincipleAmt(formatter2.format(Double.parseDouble(detailVector.get(2).toString())));
                        intPlan.setInterestAmt(formatter2.format(Double.parseDouble(detailVector.get(3).toString())));
                        intPlan.setInstallment(formatter2.format(Double.parseDouble(detailVector.get(4).toString())));
                        openingPrin = (openingPrin - Double.parseDouble(detailVector.get(2).toString()));
                        intPlan.setClosingPrinciple(new BigDecimal(formatter2.format(openingPrin < 0 ? 0 : openingPrin)));
                        intPlan.setStatus(detailVector.get(5).toString());
                        intPlan.setPayDate(detailVector.get(6).toString());
                        intPlan.setEnteredBy(detailVector.get(7).toString());
                        intPlan.setRemarks(detailVector.get(8).toString());
                        instPlan.add(intPlan);
                        setCalculateDisable(false);
                        amortSch.clear();
                    }
                    setResheduleDisable(false);
                    setUpdateDisable(true);

                } else {
                    System.out.println("!!!!!!");
                    instPlan = new ArrayList<InstallmentPlan>();
                    setResheduleDisable(true);
                    setUpdateDisable(false);
                }
                setRoiDisable(false);
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void changeEventOfInstallmentType() {
        setMessage("");
        try {
            setStatusmsg("Choose the Periodicity(Monthly/Quarterly) from the List, Choose Int/ Principal Amt,Enter Period to be made and Starting Date and then # Click Calculate Button to form a new Amortization Schedule");
            // System.out.println("investmentType -->" + investmentType);
            //  System.out.println("intServe -->" + intServe);
            if (investmentType.equalsIgnoreCase("EIMWOMP")) {
                instType = "1";
                setIntServeDisable(true);
                setIntPrincipleDisable(true);
            } else if (investmentType.equalsIgnoreCase("EIWIMP")) {
                setIntServeDisable(false);
                setIntPrincipleDisable(true);
                if (intServe == null) {
                    setErrorMessage("Please Select Interest Serve");
                    return;
                } else if (intServe.equalsIgnoreCase("0")) {
                    setErrorMessage("Please Select Interest Serve");
                    return;
                } else if (intServe.equalsIgnoreCase("Y")) {
                    instType = "2";
                } else if (intServe.equalsIgnoreCase("N")) {
                    instType = "3";
                }
            } else if (investmentType.equalsIgnoreCase("EPWIMP")) {
                setIntServeDisable(true);
                setIntPrincipleDisable(true);
                instType = "4";
            } else if (investmentType.equalsIgnoreCase("EPWOMP")) {
                setIntServeDisable(true);
                setIntPrincipleDisable(true);
                instType = "5";
            } else if (investmentType.equalsIgnoreCase("CSTMZE")) {
                setIntServeDisable(true);
                setIntPrincipleDisable(false);
                instType = "6";
            }
            // System.out.println("instType -->" + instType);
            setSanctionAmt();
            setRoi();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void changeEventOfInterestServe() {
        setMessage("");
        setErrorMessage("");
        try {
            if (intServe == null) {
                setErrorMessage("Please Select Interest Serve");
                return;
            } else if (intServe.equalsIgnoreCase("0")) {
                setErrorMessage("Please Select Interest Serve");
                return;
            } else if (intServe.equalsIgnoreCase("Y")) {
                instType = "2";
            } else if (intServe.equalsIgnoreCase("N")) {
                instType = "3";
            }
            setSanctionAmt();
            setRoi();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void setSanctionAmt() {
        setMessage("");
        setErrorMessage("");
        List sanction = new ArrayList();
        List osBalanceLst = new ArrayList();
        try {

            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I") || functionFlag.equalsIgnoreCase("A")) {
                sanction = aitr.getSanctionAmt(acNo);
                if (sanction.isEmpty()) {
                    setMessage("sanction Amt does not Exists");
                    return;
                }
                Vector sanctionAmtVector = (Vector) sanction.get(0);
                setSanctionedAmt(sanctionAmtVector.get(0).toString());
                // formatter.format(acctProduct)
                osBalanceLst = aitr.getOsBalance(acNo);
                Vector osBalanceVector = (Vector) osBalanceLst.get(0);
                // setOsBalance(osBalanceVector.get(0).toString());
                // setOsBalance(String.valueOf(CbsUtil.round(Double.parseDouble(osBalanceVector.get(0).toString()), 2)));
                setOsBalance(formatter2.format(CbsUtil.round(Double.parseDouble(osBalanceVector.get(0).toString()), 2)));

                //String.valueOf(CbsUtil.round(Double.parseDouble(osBalanceVector.get(0).toString()), 2));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void setRoi() {
        setMessage("");
        setErrorMessage("");
        String roi;
//        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {

            if (functionFlag.equalsIgnoreCase("M") || functionFlag.equalsIgnoreCase("I") || functionFlag.equalsIgnoreCase("A")) {
                roi = loanInterestCalculationBean.getRoiLoanAccount(((Double.parseDouble(osBalance) == 0 ? Double.parseDouble(sanctionAmt) : Double.parseDouble(osBalance)) * -1), ymd.format(startDate), oldAcctNo);
                System.out.println("roi -->" + roi);
                if (roi.contains("Data")) {
                    setErrorMessage("Roi Not Found");
                    setRateOfInst("0.0");
                } else {
                    setRateOfInst(roi);
                }
                setRoiDisable(true);
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetRepaymentSchedule() throws ParseException {
        amortSch.clear();
        setInvestmentType("0");
        setIntServe("0");
        setSanctionAmt("");
        setOsBalance("");
        setPeriodicity("0");
        setRateOfInst("");
        setIntServe("");
        setPeriods("");
        setIntPrinciple("");
        setSanctionedAmt("");
        setStartDate(sdf.parse(sdf.format(date)));
//        setMessage("");
        setErrorMessage("");

    }

    /**
     * **************************************************************************************************************************
     */
    public void functionAction1() {
        this.setErrorMessage("");
        setMessage("");
        this.setMessage("");
        if (functionFlag == null || functionFlag.equalsIgnoreCase("--Select--") || functionFlag.equalsIgnoreCase("")) {
            setErrorMessage("Please Select The Function You Want To Perform.");
            return;
        }
//        if ((functionFlag.equalsIgnoreCase("A")) || (functionFlag.equalsIgnoreCase("M")) || (functionFlag.equalsIgnoreCase("I"))) {
//        } else {
//            if (!functionFlag.equalsIgnoreCase("A")) {
//                setErrorMessage("Please Enter only A,M,I for ADD MODIFY or ENQUIRY");
//                return;
//            }
//            if (!functionFlag.equalsIgnoreCase("M")) {
//                setErrorMessage("Please Enter only A,M,I for ADD MODIFY or ENQUIRY");
//                return;
//            }
//            if (!functionFlag.equalsIgnoreCase("I")) {
//                setErrorMessage("Please Enter only A,M,I for ADD MODIFY or ENQUIRY");
//                return;
//            }
//        }
        try {
            List functionList = new ArrayList();

            functionList = aitr.getReferenceCode("179");
            for (int i = 0; i < functionList.size(); i++) {
                Vector ele = (Vector) functionList.get(i);
                if (functionFlag.equalsIgnoreCase(ele.get(0).toString())) {
                    this.setFunctionDesc(ele.get(1).toString());
                }
            }
            if (functionFlag.equalsIgnoreCase("A")) {

                setUpdateDisable(false);
                setResheduleDisable(true);
                setErrorMessage("");
                setMessage("");
                enableField();
                setErrorMessage("");
                setAcNo("");
                refreshOnInvalidAccNo();
                flag1 = false;
                flag2 = false;
                onblurIndustry();
                //  resetVAlues();
            } else if (functionFlag.equalsIgnoreCase("M")) {
                setUpdateDisable(false);
                setResheduleDisable(true);
                enableField();
                setErrorMessage("");
                setMessage("");
                setErrorMessage("");
                setAcNo("");
                refreshOnInvalidAccNo();
                onblurIndustry();
                flag1 = false;
                flag2 = false;
                //  resetVAlues();
            } else if (functionFlag.equalsIgnoreCase("I")) {
                setUpdateDisable(true);
                setResheduleDisable(true);
                disableField();
                setErrorMessage("");
                setMessage("");
                setErrorMessage("");
                setAcNo("");
                refreshOnInvalidAccNo();
                flag1 = false;
                flag2 = true;
                //resetVAlues();
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void disableField() {
        disableDisburest();
    }

    public void enableField() {
        enableDisburest();
    }

    public void resetVAlues() throws ParseException {
        resestDisburestment();
        setMessage("");
        setErrorMessage("");
        setFunctionFlag("--Select--");
        setFunctionDesc("");
        setAcno("");
        setDisbursetmentType("");
        instPlan.clear();
    }

    public void resestDisburestment() throws ParseException {
        resestDisburestDetail();
        disbstSch.clear();
        disbstDtl.clear();
        setSanctionAmt("");
        setSanctionDate(sdf.parse(todayDate));
        setProcessingFee("");
        setRemarkAIT("");

    }

    public void setValuesInAllFields() {
        setValuesDisbursementDetailTbl();
        setValuesDisbursementScheduleTbl();
        setDataDisburest();
        setValuesInstallmentPlanTbl();
    }

    /**
     * End Of shipra tab*
     */
    public String exitForm() {
        try {
            this.setFunctionFlag("--Select--");
            this.setAcctType("--Select--");
            this.setAcctNo("");
            this.setPartyName("");
            this.setAcOpenDt("");
            resetGroupCompFields();
            resetCompanyDetails();
            resetInsuranceDetailForm();
            resetGuarantorDetails();
            resetLoanShareDetailForm();
            resetDocumentDetailForm();
            resetEmpDetails();
            resestDisburestment();
            resetVAlues();
            resetRepaymentSchedule();
            resestDisburestDetail();
            refreshOnInvalidAccNo();
            resetValues();
            resetPage();
            resetForm();
            reFresh1();
            clearModelPanel();
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "case1";
    }
    /**
     * THIS CODE IS FOR SECURITY CODE TAB *
     */
    // private String acno1 = "06TL01044001";
    private String acno1;
    private String name = "";
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
    private String TypeOfSecurity;
    private List<SelectItem> statusList;
    private List<SelectItem> typeOfSecurityList;
    private List<SelectItem> securityNatureList;
    private List<SelectItem> securityDesc1List;
    private List<SelectItem> securityDesc2List;
    private List<SelectItem> securityDesc3List;
    private List<SelectItem> marksSecuritiesList;
    private List<SelectItem> securitiesTypeList;

    public String getSecurityflag() {
        return securityflag;
    }

    public void setSecurityflag(String securityflag) {
        this.securityflag = securityflag;
    }

    public boolean isUpdateDisable1() {
        return updateDisable1;
    }

    public void setUpdateDisable1(boolean updateDisable1) {
        this.updateDisable1 = updateDisable1;
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
    AccountStatusSecurityFacadeRemote testEJBSEC;
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

    public String getAcno1() {
        return acno1;
    }

    public void setAcno1(String acno1) {
        this.acno1 = acno1;
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

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
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

    public void getTableValues() {
        try {
            List resultList = new ArrayList();

            resultList = testEJBSEC.getStatus();
            if (resultList.size() <= 0) {
                //  setMessage("No Data Exist");
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

        } catch (ApplicationException e) {
            message = e.getMessage();
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
        try {
//            viewData();
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
                        this.setSecODRoi(loanInterestCalculationBean.getROI(element.get(0).toString(), Double.parseDouble(sancAmount), ymd.format(date)));
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
                } else {
                    this.setSecODScheme("");
                    this.setSecODRoi("0");
                    this.setMargin("");
                }
            }

//        if (setSecurityDescription3().equalsIgnoreCase("false")) {
//            return;
//        }
            if (securityDesc2 != null) {
                if (securityDesc2.equalsIgnoreCase("PLEDGE")) {
                    setDisableSecDec3(false);
                    securityflag = "true";
                } else if (securityDesc2.equalsIgnoreCase("merchandise")) {
                    setDisableSecDec3(false);
                    securityflag = "true";
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        }
    }

    public void marginLoad() {
        try {
            List resultList = openingFacadeRemote.SetValueAcctToScheme(schemeCode);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                String OdAllowWithSlab = ele.get(5).toString();
                if (OdAllowWithSlab.equalsIgnoreCase("Y")) {
                    this.setMargin(ele.get(6).toString());
                } else {
                    this.setSecODScheme("");
                    this.setSecODRoi("0");
                    this.setMargin("");
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        }
    }

    public String setSecurityDescription1() {
        setMessage("");
        setErrorMessage("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the Security Type.");
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

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "true";
    }

    public String setSecurityDescription2() {
        setMessage("");
        setErrorMessage("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the securityDesc1.");
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
            message = e.getMessage();
        }
        return "true";
    }

    public String setSecurityDescription3() {
        setMessage("");
        setErrorMessage("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0") || securityDesc2.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the securityDesc2.");
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
            message = e.getMessage();
        }
        return "true";
    }

    public void saveSecurityDetails() throws ParseException {
        setErrorMessage("");
        setMessage("");
        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setMessageAcct("Account has been closed!");
                return;
            }
        } catch (ApplicationException ex) {
            setMessageAcct(ex.getMessage());
        }

        if (acno1 == null || acno1.equalsIgnoreCase("")) {
            return;
        }
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
        if (secODRoi == null || secODRoi.equalsIgnoreCase("")) {
            secODRoi = "0";
        }

        try {
            setSecurityCode();
            String msg;

            msg = testEJBSEC.saveSecurityDetail(acno1, securityNature, securityType, status1,
                    securityDesc1, securityDesc2, securityDesc3, particular, otherAc,
                    Float.parseFloat(valuationAmt), ymd.format(revalutionDate), ymd.format(estimationDate), Float.parseFloat(lienValue), userName, remark,
                    ymd.format(sdf.parse(todayDate)), Float.parseFloat(margin), "N", groupCode, secODRoi, secODScheme);
            setMessage(msg);
            if (msg.equalsIgnoreCase("Data Saved Successfully")) {
                securityDetailsTableValues();
                resetValues();
            }

        } catch (ApplicationException e) {
            message = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            message = e.getMessage();
            e.printStackTrace();
        }
    }

    public void resetPage() {
        setErrorMessage("");
        setMessage("");
        resetValues();
        securityDetail.clear();
        securityDetailsTableValues();
    }

    public void resetValues() {
        setLienValue("");
//        setMargin("");
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
        setSecODRoi("0");
        setSecODScheme("");
        this.disableRel = false;
        this.disableRelOwn = false;
        this.disableDirPanel = "none";

    }

    public void setRemarksSec() {
        setErrorMessage("");
        setMessage("");
        if (securityType == null || securityType.equals("0") || securityType.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the Security Type.");
            return;
        }

        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setErrorMessage("Please Select the securityDesc1.");
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
            setErrorMessage("Please Select the Security Type.");
            return "false";
        }
        if (securityNature == null || securityNature.equals("0")) {
            setErrorMessage("Please Select Security Nature.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0")) {
            setErrorMessage("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0")) {
            setErrorMessage("Please Select the securityDesc2.");
            return "false";
        }
        if (particular == null || particular.equals("")) {
            setErrorMessage("Please Enter Particulars.");
            return "false";
        }

        if (!(valuationAmt == null || valuationAmt.equals(""))) {
//            Matcher valuationAmtCheck = p.matcher(valuationAmt);
//            if (!valuationAmtCheck.matches()) {
//                setValuationAmt("");
//                setMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
//                 System.out.println("Enter Numeric Value for Valuation Amount/Maturity Value.");
//                return "false";
//            }
            if (!valuationAmt.matches("[0-9.]*")) {
                setErrorMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                return "false";
            }

            if (this.valuationAmt.contains(".")) {
                if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                    setErrorMessage("Invalid Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                    return "false";
                }
            }
            if (Float.parseFloat(valuationAmt) < 0) {
                setErrorMessage("Please Enter Valid  Valuation Amount/Maturity Value.");
                return "false";
            }
        }
        if (!(lienValue == null || lienValue.equals(""))) {
//            Matcher lienValueChec = p.matcher(lienValue);
//            if (!lienValueChec.matches()) {
//                setMessage("Enter Numeric Value for Lien Value.");
//                System.out.println("Enter Numeric Value for Lien Value.");
//                return "false";
//            }
            if (!lienValue.matches("[0-9.]*")) {
                setErrorMessage("Enter Numeric Value for Lien Value.");
                return "false";
            }
            if (this.lienValue.contains(".")) {
                if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                    setErrorMessage("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                    return "false";
                }
            }
            if (Float.parseFloat(lienValue) < 0) {
                setErrorMessage("Please Enter Valid  Lien Value.");
                return "false";
            }
        }
        if (!(margin != null || margin.equals(""))) {
            Matcher marginCheck = p.matcher(margin);
            if (!marginCheck.matches()) {
                setMargin("");
                setErrorMessage("Enter Numeric Value for Margin.");
                return "false";
            } else {
                if (this.margin.contains(".")) {
                    if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                        setErrorMessage("Invalid  Margin.Please Fill The Margin Correctly.");
                        return "false";
                    }
                }
                if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                    setMargin("");
                    setErrorMessage("Please Enter Valid Margin(1 to 99.9).");
                    return "false";
                }
            }
        }

        if (securityType.equalsIgnoreCase("DATED") || securityType.equalsIgnoreCase("NON-DATED")) {
            if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
                if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
                    setErrorMessage("Please Enter Valid Valuation Amount / Maturity Value.");
                    return "false";
                } else {
                    Matcher valuationAmtCheck = p.matcher(valuationAmt);
                    if (!valuationAmtCheck.matches()) {
                        setValuationAmt("");
                        setErrorMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (!valuationAmt.matches("[0-9.]*")) {
                        setErrorMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (this.valuationAmt.contains(".")) {
                        if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                            setErrorMessage("Invalid  Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                            return "false";
                        }
                    }
                    if (Float.parseFloat(valuationAmt) < 0) {
                        setErrorMessage("Please Enter Valid  Valuation Amount/Maturity Value.");
                        return "false";
                    }
                }
            }
        }
        if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
            if (lienValue == null || lienValue.equalsIgnoreCase("")) {
                setLienValue("");
                setErrorMessage("Please Enter Valid Lien Amount.");
                return "false";
            } else {
                Matcher lienValueCheck = p.matcher(lienValue);
                if (!lienValueCheck.matches()) {
                    setLienValue("");
                    setErrorMessage("Enter Numeric Value for Lien.");
                    return "false";
                }
                if (!lienValue.matches("[0-9.]*")) {
                    setErrorMessage("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        setErrorMessage("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) < 0) {
                    setErrorMessage("Please Enter Valid  Lien Value.");
                    return "false";
                }
                if (margin == null || margin.equalsIgnoreCase("")) {
                    setErrorMessage("Please Enter Margin.");
                    return "false";
                } else {
                    Matcher marginCheck = p.matcher(margin);
                    if (!marginCheck.matches()) {
                        setMargin("");
                        setErrorMessage("Enter Numeric Value for Margin.");
                        return "false";
                    } else {
                        if (this.margin.contains(".")) {
                            if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                                setErrorMessage("Invalid  Margin.Please Fill The Margin Correctly.");
                                return "false";
                            }
                        }

                        if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                            setMargin("");
                            setErrorMessage("Please Enter Valid Margin(1 to 99.9).");
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
                setErrorMessage("Please Enter Numeric Value for Valuation Amount / Maturity Value.");
                return "false";
            } else if (!lienValueCheck.matches()) {
                setErrorMessage("Enter Numeric Value for Lien.");
                return "false";
            } else {
//                if (!lienValue.matches("[0-9]*")) {
//                    setMessage("Enter Numeric Value for Lien.");
//                    return "false";
//                }
                if (!lienValue.matches("[0-9.]*")) {
                    setErrorMessage("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        setErrorMessage("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) < 0) {
                    setErrorMessage("Please Enter Valid  Lien Value.");
                    return "false";
                }
//                if (!valuationAmt.matches("[0-9]*")) {
//                    setMessage("Please Enter Numeric Value for Valuation Amount / Maturity Value.");
//                    return "false";
//                }
                if (!valuationAmt.matches("[0-9.]*")) {
                    setErrorMessage("Enter Numeric Value for Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (this.valuationAmt.contains(".")) {
                    if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                        setErrorMessage("Invalid  Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(valuationAmt) < 0) {
                    setErrorMessage("Please Enter Valid  Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (Float.parseFloat(lienValue) > Float.parseFloat(valuationAmt)) {
                    if (securityType.equalsIgnoreCase("DATED")) {
                        setErrorMessage("Maturity Value Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                    if (securityType.equalsIgnoreCase("NON-DATED")) {
                        setErrorMessage("Valuation Amt. Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                }
            }
        }

        if (estimationDate.after(date)) {
            setErrorMessage(" Estimation or Issue Date cannot be greater Than Present Date.");
            return "false";
        }

        if (estimationDate.after(revalutionDate)) {
            if (securityType.equalsIgnoreCase("DATED")) {
                setErrorMessage("Maturity date should be greater than Issue date.");
                return "false";
            }
            if (securityType.equalsIgnoreCase("NON-DATED")) {
                setErrorMessage("Estimation Date should be greater than Revalution date.");
                return "false";
            }
        }

        if (status1 == null || status1.equals("0")) {
            setErrorMessage("Please Select the Status.");
            return "false";
        }

        return "true";
    }

    public void securityDetailsTableValues() {

        try {
            viewData();
            securityDetail = new ArrayList<SecurityDetail>();
            List resultList = new ArrayList();
            resultList = testEJBSEC.getSecurityTableValues(acno1);

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
                    mt.setLienValue(Float.parseFloat(tableVector.get(1).toString()));
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
                    mt.setDPLimit(tableVector.get(4).toString());
                    mt.setIntTable(tableVector.get(19).toString());
                    mt.setDepositRoi(tableVector.get(16).toString());
                    mt.setAddRoi(tableVector.get(20).toString());
                    mt.setAuthBy(tableVector.get(21).toString());
                    String margineRoi = "0";
                    if (tableVector.get(5).toString().equalsIgnoreCase("P")) {
                        List odAsPerRoiList = openingFacadeRemote.SetValueAcctToScheme(schemeCode);
                        if (!odAsPerRoiList.isEmpty()) {
                            Vector ele = (Vector) odAsPerRoiList.get(0);
                            String OdAllowWithSlab = ele.get(5).toString();
                            margin = ele.get(6).toString();
                            if (OdAllowWithSlab.equalsIgnoreCase("Y") && !tableVector.get(19).toString().equalsIgnoreCase("")) {
                                margineRoi = loanInterestCalculationBean.getROI(tableVector.get(19).toString(), Double.parseDouble(dpLt.toString()), ymd.format(date));
                            }
                        }
                    }
                    mt.setMargineRoi(margineRoi);
                    mt.setApplicableRoi(String.valueOf(Double.parseDouble(tableVector.get(16).toString()) + Double.parseDouble(margineRoi) + Double.parseDouble(tableVector.get(20).toString())));
                    securityDetail.add(mt);
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fetchCurrentRowSEC(ActionEvent event) {

        String sNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Maturity Value"));
        currentRowSD = currentRowSD + 1;
        currentRowSD = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (SecurityDetail item : securityDetail) {

            if (item.getMatValue().equals(sNo)) {
                currentItemSD = item;
                break;
            }
        }
    }

    public void deleteSEC() {

//        if(currentItemSD.getEnteredDate() == null){
//            return;
//        }
        if (!acno1.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
            setErrorMessage("Sorry! You can not save/update other branch's account details.");
            return;
        }
        try {
            String resultList;
            resultList = testEJBSEC.deleteSecurityTable(currentItemSD.getEnteredDate(), todayDate, acno1, currentItemSD.getSno());
            setMessage(resultList);
            // securityDetail.remove(currentRowSD);
            securityDetailsTableValues();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void Updateselect() {
        try {
            if (!acno1.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
                setErrorMessage("Sorry! You can not save/update other branch's account details.");
                return;
            }
            if (currentItemSD.particular == null) {
                return;
            }
            String acctNature;
            List resultList;
            acctNature = testEJBSEC.acctNature(currentItemSD.particular);
            if (acctNature != null) {
                if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                        || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    return;
                }
            }
            if (currentItemSD.status.equalsIgnoreCase("EXPIRED")) {
                setErrorMessage("Already Expired");
                return;
            }
            resultList = testEJBSEC.toUpdate(acno1, currentItemSD.sno);
            if (resultList.size() <= 0) {
                setErrorMessage("Data is Not Set");
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
                    setStatus1("1");
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
                setUpdateDisable1(false);
                setSaveDisable(true);
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void dirDetail() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher custIdCheck = p.matcher(this.dirCustId);
        if (!custIdCheck.matches()) {
            this.setMessage("Please Enter Numeric Customer ID.");
            return;
        } else {
            this.setMessage("");
        }
        try {
            String custinfo = openingFacadeRemote.custIdInformation(dirCustId);
            flag = "false";
            String[] values = null;
            String spliter = ": ";
            values = custinfo.split(spliter);
            String title = values[0];
            String custNames = values[1];
            setDirName(custNames);
        } catch (Exception ex) {
            flag = "true";
            setDirCustId("");
            setMessage(ex.getMessage());
        }
    }

    public void UpdateTable() throws ParseException {
        setMessage("");
        setErrorMessage("");
        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setMessageAcct("Account has been closed!");
                return;
            }
        } catch (ApplicationException ex) {
            setMessageAcct(ex.getMessage());
        }
        if (acno1 == null || acno1.equalsIgnoreCase("")) {
            return;
        }
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
            String resultList;
            resultList = testEJBSEC.UpdateSecurity(acno1, securityNature, securityType, status1,
                    securityDesc1, securityDesc2, securityDesc3, particular, otherAc,
                    Float.parseFloat(valuationAmt), ymd.format(revalutionDate), ymd.format(estimationDate), Float.parseFloat(lienValue), userName, remark,
                    ymd.format(sdf.parse(todayDate)), Float.parseFloat(margin), currentItemSD.getSno(), groupCode);
            if (resultList.equalsIgnoreCase("Record Has Been Updated")) {
                setMessage("Record Has Been Updated Successfully");
                securityDetailsTableValues();
                resetValues();
            }
            setUpdateDisable1(false);
            securityDetailsTableValues();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void select() {
        setMessage("");
        setErrorMessage("");
        try {
            if (!acno1.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
                setErrorMessage("Sorry! You can not save/update other branch's account details.");
                return;
            }
            if (currentItemSD.getStatus().equalsIgnoreCase("ACTIVE")
                    || currentItemSD.getStatus().equalsIgnoreCase("MAT DATE EXPIRED")) {
                securityDetail.get(currentRowSD).setStatus("EXPIRED");
            } else {
                setErrorMessage("Already EXPIRED");
                return;
            }
            String resultList;
            resultList = testEJBSEC.UpdateSecurityTable(userName, ymd.format(sdf.parse(todayDate)), acno1, currentItemSD.getSno());
            //   resultList = testEJBSEC.UpdateSecurityTable(currentItemSD.getEnteredDate(), todayDate, acno1, currentItemSD.getSno());
            setMessage(resultList);
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
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
            message = e.getMessage();
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
    private String errorMessageLM;
    private String messageLM;
    private String acctNoLM;
    private String custName;
    private String jtName;
    private String oprMode;
    private String lienMarkOption;
    private List<SelectItem> lienMarkOptionList;
    int currentRowLM;
    private List<TdLienMarkingGrid> gridDetailLM;
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

    public String getErrorMessageLM() {
        return errorMessageLM;
    }

    public void setErrorMessageLM(String errorMessageLM) {
        this.errorMessageLM = errorMessageLM;
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

    public void customerDetail() {
        this.setErrorMessageLM("");
        this.setMessageLM("");
        this.setCustName("");
        this.setOprMode("");
        this.setJtName("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setFlag1LM(true);
        gridDetailLM = new ArrayList<TdLienMarkingGrid>();
        try {
//            if (this.acctNoLM == null || this.acctNoLM.equalsIgnoreCase("") || this.acctNoLM.length() == 0) {
//                this.setErrorMessageLM("Please Enter Account No.");
//                return;
//            }
//            if (this.acctNoLM.length() < 12) {
//                this.setErrorMessageLM("Please Enter 12 Digit Account No Carefully.");
//                return;
//            }
//            if ((!this.acctNoLM.substring(2, 4).equalsIgnoreCase("FD"))) {
//                this.setErrorMessageLM("Please Enter Account No. Of 'FD' Nature.");
//                return;
//            }

            if (this.oldAcctNoLM == null || this.oldAcctNoLM.equalsIgnoreCase("") || this.oldAcctNoLM.length() == 0) {
                this.setErrorMessageLM("Please Enter Account No.");
                return;
            }
            if (!oldAcctNoLM.matches("[0-9a-zA-z]*")) {
                setErrorMessageLM("Please Enter Valid  Account No.");
                return;
            }
            //if (oldAcctNoLM.length() != 12) {
            if (!this.oldAcctNoLM.equalsIgnoreCase("") && ((this.oldAcctNoLM.length() != 12) && (this.oldAcctNoLM.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessageLM("Please Enter Account No Carefully.");
                return;
            }
            acctNoLM = fts.getNewAccountNumber(oldAcctNoLM);
            setAcctNoLM(acctNoLM);

            String acnature = fts.getAccountNature(acctNoLM);
            if (!acnature.equalsIgnoreCase(CbsConstant.FIXED_AC) && !acnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                this.setErrorMessageLM("Please Enter Account No. Of 'FD' Nature.");;
                return;
            }


            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            resultList = lienMark.customerDetail(this.acctNoLM);
            //      String acnature = fts.getAccountNature(acNo);
            //  resultList1 = lienMark.gridDetailLoad(this.acctNoLM, this.acctNoLM.substring(2, 4));
            resultList1 = lienMark.gridDetailLoad(this.acctNoLM, acnature);
            String authResult = lienMark.auth(this.userName, this.orgnBrCode);
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
                this.setErrorMessageLM("Account No. Does Not Exists !!!");
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
                this.setErrorMessageLM("No Voucher Exists in This Account No.");
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fillValuesofGridInFieldsLM() {
        this.setErrorMessageLM("");
        this.setMessageLM("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setDetail("");
        this.setLienMarkOption("--Select--");
        try {
            /**
             * FOR CHECKING DUPLICATE ENTRY*
             */
//            List<DlAcctOpenReg> tmpTable = dlAcctOpen;
//            for (int i = 0; i < tmpTable.size(); i++) {
//                String tmpAcno = tmpTable.get(i).getAcno();
//               String tmpVouchNo = tmpTable.get(i).getVouchreNo();
//                if (tmpAcno.equalsIgnoreCase(currentItem.getAcNo()) && tmpVouchNo.equalsIgnoreCase(currentItem.getVoucherNo())) {
//                    this.setErrorMessage(currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + " Already Selected For Lien.");
//                    this.setFlag1(true);
//                    return;
//                }
//            }
            /**
             * END*
             */
            this.setControlNo(currentItemLM.getSeqNo());
            this.setRecieptNo(currentItemLM.getReciept());
            this.setStatusLien(currentItemLM.getLien());
            if (currentItemLM.getLien().equalsIgnoreCase("Yes")) {
                this.setLienMarkOption("No");
            } else {
                this.setLienMarkOption("Yes");
            }
            String result = lienMark.tdLienPresentAmount(this.acctNoLM, Float.parseFloat(currentItemLM.getVoucherNo()), Float.parseFloat(currentItemLM.getPrintAmt()));
            if (result == null) {
                this.setErrorMessageLM("PROBLEM IN GETTING PRESENT AMOUNT !!!");
                this.setFlag1LM(true);
                return;
            } else {
                int n = result.indexOf("*");
                this.setDetail(result.substring(0, n));
                this.setPrinAmount(result.substring(n + 1));
                this.setFlag1LM(false);
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
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
                //List<DlAcctOpenReg> tmpTable = dlAcctOpen;
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
                String finalString1 = " PRIMARY" + "," + " Lien Marking Against TD" + "," + "TD" + "," + this.prinAmount + "," + this.prinAmount + "," + currentItemLM.getMatDt() + "," + currentItemLM.getFddt() + "," + detail + "," + this.userName + "," + this.todayDate;
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    String DLAccOpen_Lien = "";
    String BillLcBg_Lien = "";
    String loanLienCall = "True";

    public void saveDetailLM() {
        this.setErrorMessageLM("");
        this.setMessageLM("");
        this.flag2LM = true;
        String lAcNO = acno1;
        try {
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setMessageAcct("Account has been closed!");
                return;
            }
            DLAccOpen_Lien = "True";
            if (TypeOfSecurity == null || TypeOfSecurity.equalsIgnoreCase("")) {
                this.setErrorMessageLM("Please Select the Type Of Security");
                return;
            }
            String tmpSecType = TypeOfSecurity;
            dlAccCustIdOpen();
            if (this.lienMarkOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessageLM("Please Select Lien Marking.");
                return;
            }

            if (this.lienMarkOption.equalsIgnoreCase("YES")) {
                List list = fts.autoPayLienStatus(currentItemLM.getAcNo(), Double.parseDouble(this.recieptNo));
                if (!list.isEmpty()) {
                    Vector autoVec = (Vector) list.get(0);
                    String autoPay = autoVec.get(1).toString();
                    if (autoPay.equalsIgnoreCase("Y")) {
                        setErrorMessageLM("This Receipt is Marked for Auto Payment, So Can not be Lien.");
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

            String result = lienMark.saveLienMarkingDetail(Float.parseFloat(this.recieptNo), Float.parseFloat(currentItemLM.getVoucherNo()), currentItemLM.getAcNo().substring(2, 4), currentItemLM.getAcNo(), lAcNO, this.lienMarkOption, this.auth, this.userName, this.detail, this.loanLienCall, tmpSecType, DLAccOpen_Lien, BillLcBg_Lien, getOrgnBrCode(), roiOnSecurity, intTable, margin, additionalRoi);
            if (result == null) {
                this.setErrorMessageLM("PROBLEM IN SAVE THE RECORD !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessageLM(result);
                } else {
                    this.setMessageLM(result);
                }
                securityDetailsTableValues();
            }
            this.setFlag1LM(true);
            this.setRecieptNo("");
            this.setControlNo("");
            this.setPrinAmount("");
            this.setStatusLien("");
            this.setLienMarkOption("--Select--");
            this.setDetail("");
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetForm() {
        this.setErrorMessageLM("");
        this.setMessageLM("");
        this.setCustName("");
        this.setAcctNoLM("");
        this.setOldAcctNoLM("");
        this.setJtName("");
        this.setOprMode("");
        this.setRecieptNo("");
        this.setControlNo("");
        this.setPrinAmount("");
        this.setStatusLien("");
        this.setTypeOfSecurity("0");
        this.setLienMarkOption("--Select--");
        this.setDetail("");
        additionalRoi = "0";
        this.setFlag1LM(true);
        gridDetailLM = new ArrayList<TdLienMarkingGrid>();
    }
    /**
     * END OF LIEN MARKING*
     */
    /**
     * START OF ACCOUNT STATUS FORM CODE*
     */
    Context ctxAcct;
    private String messageAcct;
    // private String acctype;
    private List<SelectItem> acctOption;
    private String acnoAcct;
    private String code;
    private String oldCode;
    // private String code1;
    private String remarksAC;
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

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
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

//    public String getAcctype() {
//        return acctype;
//    }
//
//    public void setAcctype(String acctype) {
//        this.acctype = acctype;
//    }
    public List<SelectItem> getAcctOption() {
        return acctOption;
    }

    public void setAcctOption(List<SelectItem> acctOption) {
        this.acctOption = acctOption;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemarksAC() {
        return remarksAC;
    }

    public void setRemarksAC(String remarksAC) {
        this.remarksAC = remarksAC;
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

//    public String getCode1() {
//        return code1;
//    }
//
//    public void setCode1(String code1) {
//        this.code1 = code1;
//    }
    public List<AcStatus> getIncis() {
        return incis;
    }

    public void setIncis(List<AcStatus> incis) {
        this.incis = incis;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirCustId() {
        return dirCustId;
    }

    public void setDirCustId(String dirCustId) {
        this.dirCustId = dirCustId;
    }

    public String getDisableDirPanel() {
        return disableDirPanel;
    }

    public void setDisableDirPanel(String disableDirPanel) {
        this.disableDirPanel = disableDirPanel;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<SelectItem> getListGroupId() {
        return listGroupId;
    }

    public void setListGroupId(List<SelectItem> listGroupId) {
        this.listGroupId = listGroupId;
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

    /**
     * Creates a new instance of AccountStatus
     */
    public void acNumber() {
        try {
            List resultList = new ArrayList();
            resultList = testEJBSEC.getAllStatusList();
            acctOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void lienacNumber() {
        try {
            List resultList = new ArrayList();
            resultList = testEJBSEC.lienAcNo();
            lienacctOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    lienacctOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void custName() {
        try {
            lienAmt = "0";
            lienAcNo = "";
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            if (this.acctype.equalsIgnoreCase("--Select--")) {
//                this.setMessage("Please Select Account Number");
//                setName("");
//                setpStatus("");
//                incis.clear();
//                return;
//            }
//            if ((code.equals("")) || (code == null)) {
//                this.setMessage("Please Enter Account Number");
//                setName("");
//                setpStatus("");
//                incis.clear();
//                return;
//            }

//            Matcher codeCheck = p.matcher(code);
//            if (!codeCheck.matches()) {
//                this.setMessage("Please Enter Valid Account Number.");
//                this.setCode("");
//                setNameAcct("");
//                setpStatus("");
//                incis.clear();
//                return;
//            }
//            int length = code.length();
//            int addedZero = 6 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                code = "0" + code;
//            }
            if (this.oldCode == null || this.oldCode.equalsIgnoreCase("") || this.oldCode.length() == 0) {
                this.setMessage("Please Enter the Account No.");
                this.setCode("");
                this.setOldCode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (!oldCode.matches("[0-9a-zA-z]*")) {
                setMessage("Please Enter Valid  Account No.");
                this.setCode("");
                this.setOldCode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            //if (oldCode.length() != 12) {
            if (!this.oldCode.equalsIgnoreCase("") && ((this.oldCode.length() != 12) && (this.oldCode.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Account number is not valid.");
                this.setCode("");
                this.setOldCode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            String acNocode = fts.getNewAccountNumber(oldCode);
            setCode(acNocode);
            acnoAcct = acNocode;
            String accountCode = fts.getAccountCode(acnoAcct);
            List list = testEJBSEC.getCustNameAndStatus(acnoAcct, accountCode);
            if (list.isEmpty()) {
                reFresh1();
                throw new ApplicationException("Data does not exists");
            }

            fflag = "true";
            Vector values = (Vector) list.get(0);
            this.setCustomer(values.get(0).toString());
            this.setStatusAcct(values.get(1).toString());
            this.setpStatus(values.get(2).toString());
            setMessageAcct("");
            setNameAcct(customer);

            setRemarks(pStatus);
            griddata(acnoAcct);

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void griddata(String acno) {
        incis = new ArrayList<AcStatus>();
        try {
            List list = new ArrayList();
            list = testEJBSEC.getStatusHistory(acno);
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                AcStatus tab = new AcStatus();
                tab.setAcctNo(v.get(0).toString());
                tab.setRemark(v.get(1).toString());
                tab.setSpFlag(v.get(2).toString());
                tab.setDate(v.get(3).toString());
                tab.setAmount(v.get(4).toString());
                tab.setAuth(v.get(5).toString());
                tab.setEnterBy(v.get(6).toString());
                tab.setEfftDate(v.get(7).toString());
                incis.add(tab);
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
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

        } catch (Exception e) {
            message = e.getMessage();
        }

    }

    public void save() {
        try {
            setMessage("");
            List acStatusList = fts.getAccountPresentStatus(acno1);
            if (acStatusList.get(0).equals("9")) {
                setMessageAcct("Account has been closed!");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if ((code.equals("")) || (code == null)) {
                this.setMessage("Please Enter Account Number");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (todayDate.compareTo(sdf.format(wefDate)) < 0) {
                this.setMessage("WEF Date is Greater than Today");
                setWefDate(sdf.parse(todayDate));
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            Matcher codeCheck = p.matcher(code);
            if (!codeCheck.matches()) {
                this.setMessage("Please Enter Valid Account Number.");
                this.setCode("");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            if (this.nStatus.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select New Status");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }

            if ((remarks.equals("")) || (remarks == null)) {
                this.setMessage("Please Enter Remarks");
                setNameAcct("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (nStatus.equalsIgnoreCase("Lien Marked")) {
//                if (this.lienacctype.equalsIgnoreCase("--Select--")) {
//                    this.setMessageAcct("Please Select Lien Account Number");
//                    return;
//                }
//                if ((liencode.equals("")) || (liencode == null)) {
//                    this.setMessageAcct("Please Enter Lien Account Number");
//                    return;
//                }
//                Matcher liencodeCheck = p.matcher(liencode);
//                if (!liencodeCheck.matches()) {
//                    this.setMessageAcct("Please Enter Valid Lien Account Number.");
//                    return;
//                }
                if (!(liencode.equals("") || (liencode == null))) {
                    int length1 = liencode.length();
                    int addedZero1 = 6 - length1;
                    for (int i = 1; i <= addedZero1; i++) {
                        liencode = "0" + liencode;
                    }
                    lienAcNo = this.orgnBrCode + this.lienacctype + liencode + "01";
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

            if (pStatus.equalsIgnoreCase(nStatus)) {
                setMessageAcct("Current Status and New Status Can'nt be Same!");
                return;
            } else {
                String rs = testEJBSEC.lienMarked(acnoAcct, "Lien Marked For:" + acno1, userName, "10", wefDate1, Float.parseFloat(lienAmt), acno1, roiOnSecurity, intTable, margin);
                //String rs = testEJBSEC.cbsSaveAcctStatus(acnoAcct, "Lien Marked For:" + acno1, userName, "10", wefDate1, Float.parseFloat(lienAmt), acno1);
                if (rs.equalsIgnoreCase("Status Changed Successfully")) {
                    setMessageAcct(rs);
                    reFresh1();
                } else {
                    setMessageAcct(rs);
                }
                securityDetailsTableValues();
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void reFresh() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setMessageAcct("");
            setNameAcct("");
            setOldCode("");
            setpStatus("");
            setCode("");
            setRemarks("");
            // setAcctype("--Select--");
            setnStatus("--Select--");
            setLienAcNo("");
            setLienAmt("");
            setLienacctype("--Select--");
            setLiencode("");
            fflag = "false";
            flagAcct = "false";
            setReportDate(sdf.parse(todayDate));
            setWefDate(sdf.parse(todayDate));
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void reFresh1() {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setNameAcct("");
            setpStatus("");
            setOldCode("");
            setCode("");
            setRemarks("");
            //  setAcctype("--Select--");
            setnStatus("--Select--");
            setLienAcNo("");
            setLienAmt("");
            setLienacctype("--Select--");
            setLiencode("");
            fflag = "false";
            flagAcct = "false";
            setReportDate(sdf1.parse(todayDate));
            setWefDate(sdf1.parse(todayDate));
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurWefDate() {
        onblurDate(this.wefDate);
    }

    public void onblurReportDate() {
        onblurDate(this.reportDate);
    }

    public String onblurDate(Date dt) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            List dateDif = testEJBSEC.dateDiffWefDate(sdf1.format(dt));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessageAcct("You can't select date after the current date.");
                return msg1 = "You can't select date after the current date.";
            } else {
                this.setMessageAcct("");
                return msg1 = "true";
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
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

    public void saveEnableSEC() {
        this.setSaveDisable(false);
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

    public void onblurGroupId() {
        try {
            List listForGroupType = aitr.groupIdList();
            listGroupType.clear();
            if (groupID.equalsIgnoreCase("0")) {
                listGroupType.add(new SelectItem("0", "--Select--"));

            } else if (groupID.equalsIgnoreCase("1")) {
                List list = CommonReportMethodsBean.getCustIdfromacNo(acNo);
                Vector vec = (Vector) list.get(0);
                listGroupType.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
            } else if (groupID.equalsIgnoreCase("2")) {
                if (!listForGroupType.isEmpty()) {
                    for (int e = 0; e < listForGroupType.size(); e++) {
                        Vector evect = (Vector) listForGroupType.get(e);
                        listGroupType.add(new SelectItem(evect.get(0).toString(), evect.get(0).toString().concat("(").concat(evect.get(1).toString()).concat(")")));
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }
}
