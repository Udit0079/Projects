/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public class NamedQueryConstant {

    public static final String ROLE_URL_FIND_BY_ROLE_PARENT_CODE = "CbsRoleUrlMaster.findByRoleNameAndParentCode";
    public static final String ROLE_URL_FIND_BY_ROLE_AND_DISPLAY_NAME = "CbsRoleUrlMaster.findByRoleNameAndDisplayName";
    public static final String USER_FIND_BY_USERID = "Securityinfo.findByUserId";
    public static final String BANK_DAYS_FIND_BY_BD_PK = "BankDays.findByBDPK";
    public static final String ACCOUNT_TYPE_FIND_ALL = "AccountTypeMaster.findAll";
    public static final String TR_BAL_MAST_FIND_ALL = "TrBalMast.findAll";
    /*
     * Join Queries
     */
    public static final String BANK_ADDRESS_BY_BR_CODE = "SELECT b.bankname,d.branchName, d.city FROM Bnkadd b,BranchMaster d "
            + "where b.alphacode = d.alphaCode and d.brnCode = :brnCode";
    public static final String SCHEME_TYPE_AND_CURRENCY_CODE_BY_SCHEME_CODE = "SELECT l.currencyCode,l.schemeType FROM CbsSchemeGeneralSchemeParameterMaster g,CbsSchemeLoanSchemeDetails l "
            + "where g.schemeCode = l.schemeCode and g.schemeCode = :schemeCode and l.schemeCode = :schemeCode";
    public static final String GET_FORMS_FOR_SCHEME_TYPE_SCHEME_MASTER = "SELECT f FROM CbsSchemePopUpForms f where f.formId in(SELECT m.formId FROM CbsSchemeTypeFormMapping m where m.schemeType in (SELECT n.acctNature FROM AccountTypeMaster n where n.acctCode=:schemeType)) ";
    public static final String GET_DEPOSIT_OVERDUE_INT_PARAMETER = "SELECT b.schemeType,b.currencyCode ,b.overdueGlSubHeadCode,b.overdueInterestCode,b.overdueInterestTblCodeType,b.overdueInterestCalcMethod,"
            + "a.renewalPeriodExcd,a.maximumPeriod,a.maximumAmount,a.minorDepPreclosure,a.extension,a.splCatgClosure,a.matAmtTolerance,a.nilPenalty,"
            + "a.discontinuedInstl,a.transferIn,a.acctVerBalCheck,a.systemDrTransAllwd,a.duplicateOrReprintReport,a.prematureClosure,"
            + "a.noticePerdBelowMinNoticePerd,a.defaultValueForPreferentialIntChgd,a.backValueDatedAccountOpening,"
            + "a.futureValueDatedAccountOpening FROM CbsSchemeExceptionCodeForDepositsScheme a,CbsSchemeDepositOverdueInterestParameters b "
            + "where a.schemeCode = b.schemeCode and a.schemeCode = :schemeCode";
    public static final String GET_GL_SUB_HEAD_SCHEME_DETAILS = "SELECT b,a.acName FROM Gltable a, CbsGlSubHeadSchemeDetails b where a.acNo = b.cbsGlSubHeadSchemeDetailsPK.glSubHeadCode and b.cbsGlSubHeadSchemeDetailsPK.schemeCode = :schemeCode";
    public static final String GET_DATA_GL_TABLE_ACC_TO_SCHEME_TOD_DETAILS = "SELECT a,b FROM CbsSchemeTodExceptionDetailsCurrencyWise a, CbsExceptionMaster b where a.todException = b.exceptionCode"
            + " and a.cbsSchemeTodExceptionDetailsCurrencyWisePK.schemeCode = :schemeCode";
    public static final String TOD_SERIAL_NUMBER = "SELECT max(a.cbsSchemeTodExceptionDetailsCurrencyWisePK.todSrlNo) FROM CbsSchemeTodExceptionDetailsCurrencyWise a";
    public static final String GET_CUSTOMER_MASTER_BY_PK = "SELECT a FROM Customermaster a WHERE a.customermasterPK.custno=:custno AND a.customermasterPK.actype=:actype AND a.customermasterPK.agcode=:agcode AND a.customermasterPK.brncode=:brncode";
    public static final String GET_TD_CUSTOMER_MASTER_BY_PK = "SELECT a FROM TdCustomermaster a WHERE a.tdCustomermasterPK.custno=:custNo AND a.tdCustomermasterPK.actype=:actype AND a.tdCustomermasterPK.brncode=:brncode";
    public static final String GET_UN_VERIFY_CUST_ID = "SELECT c,d FROM CbsCustomerMasterDetail c,CbsCustMisinfo d WHERE c.customerid=d.customerId and c.primaryBrCode = :primaryBrCode and c.auth='N'";
    /**
     * NeftRtgs Queries
     */
    public static final String EPSINWARD_CREDIT_BY_PAYSYSID_MSGSTATUS = "select e from  EPSInwardCredit e where e.paySysId=:paySysId and e.msgStatus=:msgStatus";
    public static final String MSGSUBTYPE_BY_PAYSYSID_SCREENFLAG = "SELECT e.msgSubType FROM  EPSMessageCategory e where e.paySysId = :paySysId and e.screenFlag = :screenFlag";
    public static final String CHARGE_EVENT_ID_BY_PAYSYS_ID = "select e.chargeEventID from  EPSChargeDetails e where e.paySysId=:paySysId";
    public static final String GET_ALPHACODE = "select b.alphaCode from BranchMaster b where b.brnCode=:brnCode";
    public static final String GET_TRSNO = "select max(r.trsno) from ReconvmastTrans r";
    public static final String IFSCODE_BY_BANK_BRANCH_CODE = "select e.iFSCode from EPSBranchMaster e where e.bankCode=:bankCode and e.branchCode=:branchCode";
    public static final String NEFT_DETAILS_BY_NEFT_BANK_AND_PROCESS = "select a from CbsAutoNeftDetails a where a.neftBankName=:neftBankName and a.process=:process";
    public static final String NEFT_DETAILS_BY_NEFT_BANK_AND_PROCESS_AND_PROCESS_TYPE = "select a from CbsAutoNeftDetails a where a.neftBankName=:neftBankName and a.process=:process and a.processType=:processType";
    public static final String NEFT_MISMATCH_BANK_NAME = "select a from CbsAutoNeftDetails a where a.process=:process and a.iwFileType<>''";
    /**
     * *
     * Investment Named Queries
     */
    public static final String GET_GOVERNMENT_SECURITY_LIST = "SELECT Distinct f.acName FROM Gltable f where substring(f.acNo,5,6) between :fromNo and :toNo";
    public static final String GET_INVESTMENT_SECURITY_TO_MODIFY = "SELECT e FROM InvestmentSecurityMaster e WHERE e.maturityYear>=:maturityYear";
    public static final String GET_INVESTMENT_SECURITY_TO_DELETE = "SELECT e FROM InvestmentSecurityMaster e WHERE e.maturityYear<:maturityYear AND e.delFlag<>:delFlag";
    public static final String CHECK_DUPLICATE_SECURITY_MASTER = "SELECT e FROM InvestmentSecurityMaster e WHERE e.maturityYear=:maturityYear AND e.roi=:roi AND e.securityName=:securityName";
    public static final String DELETE_INVESTMENT_SECURITY_MASTER = "UPDATE InvestmentSecurityMaster e SET e.delFlag=:delFlag,e.tranTime=:trantime,e.lastUpdateBy=:lastUpdate WHERE e.sno=:sno";
    public static final String GET_CONTROL_NO_FOR_MASTER_CLOSE = "SELECT e FROM InvestmentGoidates e,InvestmentMaster f WHERE e.ctrlno=f.investmentMasterPK.controlno AND e.status=:status and f.investmentMasterPK.sectype=:secType ";
    public static final String GET_CONTROL_NO_DETAILS = "SELECT a,b FROM InvestmentGoidates a,InvestmentMaster b WHERE a.ctrlno=b.investmentMasterPK.controlno AND a.ctrlno=:controllNo AND b.investmentMasterPK.sectype=:secType";
    public static final String GET_TOTAL_SUM_OF_DAYS = "SELECT SUM(e.days) FROM InvestmentAmortizationDetails e";
    public static final String GET_TOTAL_SUM_OF_AMORT_AMT = "SELECT SUM(e.amortAmt) FROM InvestmentAmortizationDetails e WHERE e.years<=:years AND e.status=:status";
    public static final String GET_MAX_CTRLNO_INVESTMENT_MASTER = "SELECT max(e.investmentMasterPK.controlno) FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype<>:sectype";
    public static final String UPDATE_INVEST_MASTER_BY_CTRLNO = "UPDATE InvestmentMaster e SET e.closedt=:curDt WHERE e.investmentMasterPK.controlno=:ctrlNo";
    public static final String UPDATE_INVEST_GOIDATES_BY_CTRLNO = "UPDATE InvestmentGoidates e SET e.closeddt=:closeDt,e.status=:status WHERE e.ctrlno=:ctrlNo";
    public static final String UPDATE_AMORT_DETAILS_BY_CTRLNO = "UPDATE InvestmentAmortizationDetails e SET e.oldCtrl=:oldCtrlNo,e.status='P',e.lastUpdateDt=:closeDt WHERE e.investmentAmortizationDetailsPK.ctrlNo=:ctrlNo AND e.status=:status";
    public static final String GET_INVESTMENT_MASTER_BY_CTRLNO_AND_SECTYPE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.controlno=:ctrlNo AND e.investmentMasterPK.sectype=:secType";
    public static final String GET_INVESTMENT_GOIDATES_BY_CTRLNO = "SELECT e FROM InvestmentGoidates e WHERE e.ctrlno=:ctrlNo";
    public static final String GET_CONTROL_NO_FOR_AMORT_CALC = "SELECT e FROM InvestmentGoidates e,InvestmentMaster f WHERE e.ctrlno=f.investmentMasterPK.controlno AND e.status=:stat AND e.ctrlno not in(SELECT DISTINCT i.investmentAmortizationDetailsPK.ctrlNo FROM InvestmentAmortizationDetails i) and f.investmentMasterPK.sectype =:secType and f.bookvalue > f.facevalue";
    public static final String GET_CONTROL_NO_DETAILS_AMORT_CALC = "SELECT a,b FROM InvestmentGoidates a,InvestmentMaster b WHERE a.ctrlno=b.investmentMasterPK.controlno AND a.ctrlno=:controllNo AND a.ctrlno not in(SELECT DISTINCT i.investmentAmortizationDetailsPK.ctrlNo FROM InvestmentAmortizationDetails i)";
    public static final String GET_DUE_REPORT_BETWEEN_RANGE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.matdt between :frmDt and :toDt";
    public static final String GET_DUE_REPORT_TILL_DATE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.matdt<=:tillDate";
    public static final String GET_SELLAR_NAME = "SELECT DISTINCT e.sellername FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype<>:secType order by e.sellername";
    public static final String GET_ALL_REPORT_ALL_SELLAR = "SELECT e FROM InvestmentMaster e WHERE e.settledt between :frDt and :asOnDt AND (e.closedt is null or e.closedt>:asOnDt) AND e.investmentMasterPK.sectype<>:sTp ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_ALL_REPORT_ALL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.settledt between :frDt and :asOnDt AND e.closedt<=:asOnDt AND e.investmentMasterPK.sectype<>:sTp ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_ALL_REPORT_ALL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.closedt between :frDt and :asOnDt AND e.investmentMasterPK.sectype<>:sTp ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_ALL_REPORT_ALL_SELLAR_ALL = "SELECT e FROM InvestmentMaster e WHERE e.settledt between :frDt and :asOnDt AND e.investmentMasterPK.sectype<>:sTp ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_ALL_REPORT_INDIVISUAL_SELLAR = "SELECT e FROM InvestmentMaster e WHERE e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND (e.closedt is null or e.closedt>:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_ALL_REPORT_INDIVISUAL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND e.closedt<=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_ALL_REPORT_INDIVISUAL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.sellername=:sellar AND e.closedt between :frDt and :asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_ALL_REPORT_INDIVISUAL_SELLAR_ALL = "SELECT e FROM InvestmentMaster e WHERE e.sellername=:sellar AND e.settledt between :frDt and :asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";

    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt<=:asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt>=:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt>=:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND e.closedt<=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_ALL = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_MARKING = "SELECT e FROM InvestmentMaster e  WHERE  e.investmentMasterPK.sectype=:repType AND e.settledt<=:asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt>=:asOnDt) and e.marking=:marking order by e.investmentMasterPK.controlno" ;
    public static final String GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt>=:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND  e.closedt<=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";

//    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt<=:asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt>:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt>:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
////    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND e.closedt<=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.closedt between :frDt and :asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_ALL = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.settledt between :frDt and :asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_INDIVISUAL_REPORT_ALL_SELLAR_MARKING = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.marking=:marking AND e.settledt<=:asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt=:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND (e.closedt is null or e.closedt=:asOnDt) ORDER BY e.matdt,e.investmentMasterPK.controlno";
////    public static final String GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt AND  e.closedt<=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
//    public static final String GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR_CLOSE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.sellername=:sellar AND e.closedt between :frDt and :asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";

    public static final String GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR_ALL = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:repType AND e.sellername=:sellar AND e.settledt between :frDt and :asOnDt AND e.matdt>=:asOnDt ORDER BY e.matdt,e.investmentMasterPK.controlno";
    public static final String LOAD_S_NAME = "SELECT DISTINCT e.sellername FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:secType order by e.sellername";
    //public static final String GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER = "SELECT a,b FROM InvestmentMaster a,InvestmentSecurityMaster b WHERE a.investmentMasterPK.sectype=:repType AND a.secdesc=b.securityName AND (a.closedt is null OR a.closedt>=:maxTillDt) And a.transdt<=:maxTillDt ORDER BY a.matdt";
    public static final String GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.investmentMasterPK.controlno=b.ctrlno AND (a.closedt is null OR a.closedt>=:maxTillDt) And a.transdt<=:maxTillDt ORDER BY a.matdt";
    public static final String GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER_GOINT = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND (a.closedt is null OR a.closedt>=:maxTillDt) And a.transdt<=:maxTillDt ORDER BY a.matdt";
    public static final String GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER_GOINT_C = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND a.closedt<=:maxTillDt And a.transdt<=:maxTillDt ORDER BY a.matdt";
    public static final String GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER_GOINT_ALL = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt And a.transdt<=:maxTillDt ORDER BY a.matdt";
    //public static final String GET_INVESTMENT_MASTER_SECURITY_MASTER = "SELECT a,b FROM InvestmentMaster a,InvestmentSecurityMaster b WHERE a.investmentMasterPK.sectype=:repType AND a.sellername=:sellerName AND a.secdesc=b.securityName AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    public static final String GET_INVESTMENT_MASTER_SECURITY_MASTER = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    public static final String GET_INVESTMENT_MASTER_SECURITY_MASTER_C = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND a.closedt<=:maxTillDt ORDER BY a.matdt";
    public static final String GET_INVESTMENT_MASTER_SECURITY_MASTER_ALL = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype=:repType AND a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt ORDER BY a.matdt";
    public static final String GET_UNAUTHORIZE_CALL_MASTER = "SELECT e FROM InvestmentCallMaster e WHERE e.auth=:auth ORDER BY e.dealDt";
    public static final String GET_MAX_CTRL_NO = "SELECT MAX(e.ctrlNo) FROM InvestmentCallMaster e";
    public static final String DELETE_INVESTMENT_CALL_MASTER_BY_CTRL = "DELETE FROM InvestmentCallMaster e WHERE e.ctrlNo=:ctrlNo";
    public static final String UPDATE_INVESTMENT_CALL_MASTER_BY_CTRL = "UPDATE InvestmentCallMaster e SET e.auth=:auth,e.authBy=:authBy,e.status='Active' WHERE e.ctrlNo=:ctrlNo";
    public static final String GET_MAX_CTRL_NO_INVESTMENT_FDR_DETAILS = "SELECT MAX(e.ctrlNo) FROM InvestmentFdrDetails e";
    public static final String GET_INVESTMENT_DETAIL_AND_DATES = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status AND a.ctrlNo=:ctrlNo";
    public static final String GET_INVESTMENT_DETAIL_AND_DATES_FOR_INT_REC_POST_FDR = "SELECT a.ctrlNo,a.lastIntPaidDt,a.intOpt,a.totAmtIntRec,a.purchaseDt,a.matDt,b.faceValue,b.roi,b.sellerName,b.secDesc,b.bookValue,b.fdrNo,b.enterBy,b.authBy,b.tranTime,a.amtIntTrec,a.amtIntAccr,a.intPdt FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.intOpt=:iOpt and a.ctrlNo=b.ctrlNo AND b.status=:status AND a.purchaseDt<=:purDt AND a.intOpt is not null";
    public static final String GET_INT_POST_HISTORY = "SELECT MAX(e.todate) FROM IntPostHistory e WHERE e.intPostHistoryPK.actype=:actype";
    //public static final String GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status ORDER BY a.ctrlNo";
    public static final String GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo ORDER BY a.ctrlNo";
    //public static final String GET_INVEST_MAST_SEC_ALL = "SELECT a,b FROM InvestmentMaster a,InvestmentSecurityMaster b WHERE a.sellername=:sellerName AND a.secdesc=b.securityName AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    //public static final String GET_INVEST_MAST_SEC_ALL = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    public static final String GET_INVEST_MAST_SEC_ALL = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    public static final String GET_INVEST_MAST_SEC_ALL_C = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND a.closedt<=:maxTillDt ORDER BY a.matdt";
    public static final String GET_INVEST_MAST_SEC_ALL_STATUS_A = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.sellername=:sellerName AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt ORDER BY a.matdt";
    //public static final String GET_ALL_INVEST_MAST_SEC = "SELECT a,b FROM InvestmentMaster a,InvestmentSecurityMaster b WHERE a.investmentMasterPK.sectype<>:repType AND a.secdesc=b.securityName AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    public static final String GET_ALL_INVEST_MAST_SEC = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype<>:repType AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND (a.closedt is null OR a.closedt>=:maxTillDt) ORDER BY a.matdt";
    public static final String GET_ALL_INVEST_MAST_SEC_C = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype<>:repType AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt AND a.closedt>=:maxTillDt ORDER BY a.matdt";
    public static final String GET_ALL_INVEST_MAST_SEC_ALL = "SELECT a,b FROM InvestmentMaster a,InvestmentGoidates b WHERE a.investmentMasterPK.sectype<>:repType AND a.investmentMasterPK.controlno=b.ctrlno and b.lastintpaiddt between :frDt and :maxTillDt ORDER BY a.matdt";
    //public static final String GET_DUE_DATE_REPORT_ACTIVE = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status ORDER BY a.matDt,b.sellerName";
    public static final String GET_DUE_DATE_REPORT_ACTIVE = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND (a.closedDt is null or a.closedDt>:asOnDt) AND a.purchaseDt<:asOnDt AND a.matDt>=:asOnDt ORDER BY b.sellerName ,a.matDt ";
    public static final String GET_DUE_DATE_REPORT_ACTIVE1 = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND (a.closedDt is null or a.closedDt>:asOnDt) AND a.purchaseDt<:asOnDt and a.ctrlNo not in (select c.ctrlNo from InvestmentFdrDetails c where c.renewStatus = 'R' and c.tranTime >:asOnDt) ORDER BY a.matDt";
    public static final String GET_DUE_DATE_REPORT_RENEWED = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status AND b.renewStatus=:renewStatus ORDER BY b.sellerName ASC,a.matDt";
    public static final String GET_DUE_DATE_REPORT_CLOSE = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status AND a.closedDt BETWEEN :frDt AND :toDt ORDER BY a.closedDt";
    public static final String GET_MAX_CTRL_NO_INVESTMENT_SHARE = "SELECT MAX(e.ctrlNo) FROM InvestmentShare e";
    public static final String GET_CTRL_NO_INVESTMENT_SHARE = "SELECT e FROM InvestmentShare e WHERE e.status=:status ORDER BY e.ctrlNo";
    public static final String GET_CTRL_NO_INVESTMENT_CALL = "SELECT e FROM InvestmentCallMaster e WHERE e.status=:status ORDER BY e.ctrlNo";
    public static final String UPDATE_AMORT_DETAILS = "UPDATE InvestmentAmortizationDetails e SET e.status=:status,e.lastUpdateBy=:lastUpdate,e.lastUpdateDt=:lastUpDt WHERE e.investmentAmortizationDetailsPK.slno=:sno AND e.investmentAmortizationDetailsPK.ctrlNo=:ctrNo AND e.years=:year AND e.status<>:status";
    public static final String GET_CONTROL_NO_FOR_SINGLE_POST = "SELECT f FROM InvestmentAmortizationDetails f WHERE f.investmentAmortizationDetailsPK.ctrlNo=:ctrNo AND f.years<=:dtTo AND f.status =:status";
    public static final String GET_CONTROL_NO_FOR_AMORT_POST = "SELECT DISTINCT f.investmentAmortizationDetailsPK.ctrlNo FROM InvestmentGoidates e,InvestmentAmortizationDetails f WHERE e.ctrlno=f.investmentAmortizationDetailsPK.ctrlNo AND e.status=:status AND f.status =:val";
    public static final String GET_AMORT_HIST_CTRL = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.yearAmort = :yearAmort AND i.monthAmort = :monthAmort AND i.ctrlno = :ctrlno";
    public static final String GET_AMORT_HIST_ALL = "SELECT i FROM InvestmentAmortPostHistory i WHERE i.yearAmort = :yearAmort AND i.monthAmort = :monthAmort";
    public static final String GET_TOTAL_AMORT_AMOUNT = "SELECT SUM(A.amortAmt) FROM InvestmentAmortizationDetails A WHERE A.years<=:dtTo AND A.status=:status AND A.investmentAmortizationDetailsPK.ctrlNo=:ctrNo";
    public static final String GET_AMORT_REPORT_LIST = "SELECT A.ctrlno,B.settledt,A.matdt,B.facevalue,B.bookvalue,B.secdesc FROM InvestmentGoidates A,InvestmentMaster B WHERE A.ctrlno=B.investmentMasterPK.controlno AND A.ctrlno=:ctrNo AND A.ctrlno IN (SELECT distinct i.investmentAmortizationDetailsPK.ctrlNo FROM InvestmentAmortizationDetails i)";
    public static final String UPDATE_INVESTMENT_FDR_DATES = "UPDATE InvestmentFdrDates e SET e.amtIntTrec=:intRec,e.totAmtIntRec=:totInt WHERE e.ctrlNo=:ctrNo";
    public static final String GET_FDR_CTRL_DETAIL_DATA = "SELECT b.fdrNo,a.purchaseDt,a.matDt,b.sellerName,b.intOpt,b.faceValue,b.bookValue,b.branch,b.roi,a.totAmtIntRec,a.amtIntTrec FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND a.ctrlNo=:ctrNo";
    public static final String GET_CTRL_NO_INVESTMENT_FDR_DETAILS = "SELECT Distinct e.ctrlNo FROM InvestmentFdrDetails e WHERE e.status=:status";
    public static final String GET_DUE_DATE_REPORT_ACTIVE_SELLAR = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status AND b.sellerName=:sName ORDER BY a.matDt,b.sellerName";
    //public static final String GET_INVESTMENT_DETAIL_FOR_INT_REC_POST_SEC = "SELECT i.investmentMasterPK.controlno,g.lastIntPaidDtAccr,gr.dramt,i.settledt,i.matdt,i.facevalue,i.roi,i.sellername,i.secdesc,i.bookvalue,gr.enterby,gr.authby,gr.trantime from InvestmentMaster i, InvestmentGoidates g,GoiRecon gr where i.investmentMasterPK.controlno = g.ctrlno and i.investmentMasterPK.controlno = gr.goiReconPK.ctrlno and g.lastintpaiddt = (select max(dt.lastintpaiddt) from InvestmentGoidates dt) and gr.goiReconPK.dt = g.lastintpaiddt and g.status=:status and g.lastintpaiddt <=:purDt and gr.trantype = 8";
    public static final String GET_INVESTMENT_DETAIL_FOR_INT_REC_POST_SEC = "SELECT i.investmentMasterPK.controlno,g.lastIntPaidDtAccr,g.amtIntAccr,i.settledt,i.matdt,i.facevalue,i.roi,i.sellername,i.secdesc,i.bookvalue,g.accrIntBy,g.accrIntBy from InvestmentMaster i, InvestmentGoidates g where i.investmentMasterPK.controlno = g.ctrlno and g.lastIntPaidDtAccr = (select max(dt.lastIntPaidDtAccr) from InvestmentGoidates dt) and g.status=:status and g.lastIntPaidDtAccr <=:purDt";
    public static final String UPDATE_INT_INVEST_GOIDATES_BY_CTRLNO = "UPDATE InvestmentGoidates e SET e.lastintpaiddt=:lastintpaiddt,e.totIntRec=:amtinttrec,e.hyIntBy=:hyIntBy , e.amtIntAccr=:amtIntAccr ,e.totAmtIntAccr=:totAmtIntAccr WHERE e.ctrlno=:ctrlno";
  
    public static final String GET_GL_BY_CTRL_NO = "SELECT DISTINCT i.fdrReconPK.acno from FdrRecon i where i.ctrlNo=:ctrlNo";
    public static final String UPDATE_FDR_DETAILS_BY_CTRL_NO = "UPDATE InvestmentFdrDetails e SET e.status=:status WHERE e.ctrlNo=:ctrlNo";
    public static final String UPDATE_FDR_DATES_BY_CTRL_NO = "UPDATE InvestmentFdrDates e SET e.closedDt=:dt WHERE e.ctrlNo=:ctrlNo";
    public static final String GET_ALL_SELLER_NAME = "SELECT DISTINCT e.sellername FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype =:secTp order by e.sellername";
    public static final String GET_MAX_CTRLNO_INVESTMENT_MASTER_SEC = "SELECT max(e.investmentMasterPK.controlno) FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype<>:sectype";
    public static final String GET_TOTAL_AMORT_AMT_BAL = "SELECT SUM(A.amortAmt) FROM InvestmentAmortizationDetails A WHERE A.status=:status AND A.investmentAmortizationDetailsPK.ctrlNo=:ctrNo";
    public static final String UPDATE_AMORT_ALL_BAL_DETAILS = "UPDATE InvestmentAmortizationDetails e SET e.status=:status,e.lastUpdateBy=:lastUpdate,e.lastUpdateDt=:lastUpDt WHERE e.investmentAmortizationDetailsPK.ctrlNo=:ctrNo AND e.status<>:status";
    public static final String UPDATE_INVEST_MASTER_ACCDINT_BY_CTRLNO = "UPDATE InvestmentGoidates e SET e.amtIntAccr=:acdint,e.totAmtIntAccr=:acdint WHERE e.ctrlno=:ctrlno";
    public static final String GET_ALL_DUE_REPORT_BETWEEN_RANGE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype<>:repType AND e.matdt between :frmDt and :toDt";
    public static final String GET_ALL_DUE_REPORT_TILL_DATE = "SELECT e FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype<>:repType AND e.matdt<=:tillDate";
    public static final String GET_SEQ_NO_INVESTMENT_SECURITY = "SELECT e FROM InvestmentSecurityAuthDetail e WHERE e.orgnBrCode=:brnCode and e.auth='N' ORDER BY e.maxSec";
    public static final String GET_SEQ_NO_INVESTMENT_SECURITY_DETAIL = "SELECT e FROM InvestmentSecurityAuthDetail e WHERE e.maxSec=:seqNo and e.auth='N'";
    public static final String DELETE_SEQ_NO_INVESTMENT_SECURITY_AUTH = "DELETE FROM InvestmentSecurityAuthDetail e WHERE e.maxSec=:seqNo";
    public static final String GET_MAX_SEQ_INVESTMENT_SECURITY_AUTH = "SELECT max(e.maxSec) FROM InvestmentSecurityAuthDetail e";
    public static final String GET_ALL_INVESTMENT_MASTER_SECURITY_FOR_ACCR_POST = "SELECT a,c FROM InvestmentMaster a,InvestmentGoidates c WHERE a.investmentMasterPK.sectype=:repType AND (a.closedt is null OR a.closedt>=:maxTillDt) And a.transdt<=:maxTillDt and a.investmentMasterPK.controlno = c.ctrlno ORDER BY a.investmentMasterPK.controlno";
    public static final String GET_CONTROL_NO_SECURITY_DESC_WISE = "SELECT e FROM InvestmentGoidates e,InvestmentMaster f WHERE e.ctrlno=f.investmentMasterPK.controlno AND e.status=:status and f.investmentMasterPK.sectype=:secType and f.secdesc=:secDesc";
    public static final String GET_SECURITY_DESC = "SELECT DISTINCT e.secdesc FROM InvestmentMaster e WHERE e.investmentMasterPK.sectype=:secType order by e.secdesc";
    public static final String LOAD_BANK_NAME = "SELECT DISTINCT e.sellerName FROM InvestmentFdrDetails e order by e.sellerName";
    public static final String GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT_BANK_WISE = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.sellerName =:sellername AND b.status=:status ORDER BY a.ctrlNo";
    public static final String GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT_ALL_BANK_STATUS_WISE = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.status=:status ORDER BY a.ctrlNo";
    public static final String GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT_BANK_ALL_STATUS = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND b.sellerName =:sellername ORDER BY a.ctrlNo";
    public static final String GET_DUE_DATE_REPORT_OBTAINED = "SELECT a,b FROM InvestmentFdrDates a,InvestmentFdrDetails b WHERE a.ctrlNo=b.ctrlNo AND a.purchaseDt BETWEEN :frDt AND :toDt ORDER BY a.purchaseDt";
    /**
     * *
     * SMS Named Queries
     */
    public static final String FIND_MAX_TXN_ID = "SELECT max(m.txnId) FROM MbChargeMaster m";
    public static final String FIND_BY_STATUS = "SELECT m FROM MbChargeMaster m WHERE m.status like 'A'";
    public static final String FIND_MAX_TXN_ID_MB_CHARGE_POSTING_HISTORY = "SELECT max(m.txnId) FROM MbChargePostingHistory m";
    public static final String FIND_MAX_TXN_ID_MB_CHARGE_POSTING_INDIVIDUAL_HISTORY = "SELECT max(m.txnId) FROM MbChargePostingIndivisualHistory m";
//    public static final String FIND_MAX_TO_DATE_BY_ACNO = "SELECT max(m.toDate) FROM MbChargePostingIndivisualHistory m WHERE m.acno = :acno and m.messageType = :messageType";
    public static final String FIND_MAX_TO_DATE_BY_ACNO_MESSAGE_TYPE = "SELECT max(m.toDate) FROM MbChargePostingIndivisualHistory m WHERE m.acno = :acno";
    public static final String FIND_MAX_TO_DATE_BY_BRANCH = "SELECT max(m.toDate) FROM MbChargePostingHistory m WHERE substring(m.crAcno,1,2)=:brncode";
    public static final String FIND_MIN_CREATED_DATE_FOR_BRANCH = "SELECT min(m.createdDate) FROM MbSubscriberTab m WHERE substring(m.acno,1,2)=:brncode";
    public static final String GET_MAX_TXN_ID_MB_PULL_MSG_TAB = "SELECT MAX(e.mbPullMsgTabPK.txnId) FROM MbPullMsgTab e";
    public static final String FIND_MAX_TXN_ID_MB_PUSH_MSG_TAB = "SELECT MAX(m.mbPushMsgTabPK.txnid) FROM MbPushMsgTab m";
    public static final String GET_DATA_FOR_CHARGE = "SELECT m.acno,count(m.message) FROM MbPushMsgTab m where m.acno not like '' and m.messageStatus=2 and m.messageType=:type and substring(m.acno,1,2)=:orgnBrCode  group by m.acno";
    public static final String GET_DATA_FOR_CHARGE_BY_ACNO = "SELECT m.acno,count(m.message) FROM MbPushMsgTab m where m.acno=:acno and  m.acno not like '' and m.messageStatus=2 and m.messageType=:type  group by m.acno";
    public static final String GET_ALL_SENDED_TXN_ID = "SELECT m FROM MbPushMsgTab m where m.acno=:acno and m.messageStatus=2 and m.messageType=:type";
    public static final String FIND_DISTINCT_MESSAGE_TYPE = "SELECT DISTINCT m.messageType FROM MbPushMsgTab m WHERE m.dt>=:fromdt and m.dt<=:todt";
    public static final String TOTAL_SEND_MESSAGE = "SELECT COUNT(m) FROM MbPushMsgTab m WHERE m.messageStatus=2 AND m.messageType=:messageType AND m.dt>=:fromdt AND m.dt<=:todt";
    public static final String FIND_CREATION_DATE_BY_ACNO = "SELECT m.createdDate FROM MbSubscriberTab m WHERE m.acno = :acno";
    public static final String FIND_ALL_MB_NO = "SELECT  m.acno, m.mobileNo FROM MbSubscriberTab m WHERE m.status=:status and m.auth=:auth and m.authStatus=:authStatus and substring(m.acno,1,2)=:orgnBrCode order by m.acno";
    public static final String ALL_SUBSCRIBER_BY_MOBILE = "SELECT e FROM MbSubscriberTab e WHERE e.mobileNo=:mobileNo AND e.status=:status AND e.auth=:auth AND e.authStatus=:authStatus";
    public static final String SUBSCRIBER_BY_MOBILE_AND_ACCOUNT_CODE = "SELECT e FROM MbSubscriberTab e WHERE e.mobileNo=:mobileNo AND substring(e.acno,3,2)=:accode";
    public static final String FIND_ALL_STAFF_MOBILE_NO = "SELECT  m.acno, m.mobileNo FROM MbSubscriberTab m WHERE m.acnoType=:type AND m.status=:status AND m.auth=:auth AND m.authStatus=:authStatus AND substring(m.acno,1,2)=:orgnBrCode order by m.acno";
    public static final String SUBSCRIBER_ACNO_BY_MOBILE_ACCOUNT_CODE_AND_PIN = "SELECT e.acno FROM MbSubscriberTab e WHERE e.mobileNo=:mobileNo AND substring(e.acno,3,2)=:accode AND e.pinNo=:pin";
    public static final String CBS_CHARGE_DETAIL_BY_TYPE_NAME_AND_ACTYPE = "SELECT e FROM CbsChargeDetail e WHERE e.chargeType=:chargeType  AND e.cbsChargeDetailPK.chargeName=:chargeName AND e.cbsChargeDetailPK.acType=:acType";
    public static final String ALL_SMS_ACCOUNT_OF_BARNCH_FROM_TD_ACCOUNTMASTER = "SELECT m.acno FROM MbSubscriberTab m WHERE m.acno in(SELECT t.acno FROM TDAccountMaster t WHERE t.accStatus<>9 and t.curBrCode=:curBrCode)AND m.createdDate<=:toDt order by m.acno";
    public static final String ALL_SMS_ACCOUNT_OF_BARNCH_FROM_ACCOUNTMASTER = "SELECT m.acno FROM MbSubscriberTab m WHERE m.acno in(SELECT a.aCNo FROM AccountMaster a WHERE a.accStatus<>9 and a.curBrCode=:curBrCode) AND m.createdDate<=:toDt order by m.acno";
//    public static final String ALL_SMS_REGISTERED_ACTIVE_ACCOUNT = "SELECT m.acno FROM MbSubscriberTab m WHERE m.status=1 AND m.auth='Y' AND m.authStatus='V' AND substring(m.acno,1,2)=:curBrCode AND m.createdDate<=:toDt order by m.acno";
    public static final String ALL_SMS_REGISTERED_ACTIVE_ACCOUNT = "SELECT m.billDeductionAcno FROM MbSubscriberTab m WHERE m.status=1 AND m.auth='Y' AND m.authStatus='V' AND substring(m.billDeductionAcno,1,2)=:curBrCode AND m.createdDate<=:toDt AND substring(m.billDeductionAcno,3,2) NOT IN(SELECT a.acctCode FROM AccountTypeMaster a WHERE a.acctNature NOT IN('SB','CA')) order by m.billDeductionAcno";
    public static final String CHECK_ALREADY_POSTED_ACCOUNT = "SELECT max(m.toDate) FROM MbChargePostingIndivisualHistory m WHERE m.acno=:acno";
    public static final String GET_ALL_ACCOUNT_TO_DELETE = "SELECT e FROM MbSubscriberTab e WHERE e.auth=:auth AND e.authStatus=:authStatus";
    public static final String GET_ALL_ACCOUNT_TO_VERIFY = "SELECT e FROM MbSubscriberTab e WHERE e.auth=:auth and substring(e.acno,1,2)=:orgnBrCode";
    public static final String CHECK_ACCOUNT_BY_AUTH_AND_AUTHSTATUS = "SELECT e FROM MbSubscriberTab e WHERE e.auth=:auth AND e.authStatus=:authStatus AND e.acno=:acno";
    public static final String CHECK_ACCOUNT_BY_AUTH_AUTHSTATUS_AND_STATUS = "SELECT e FROM MbSubscriberTab e WHERE e.status=:status and e.auth=:auth AND e.authStatus=:authStatus AND e.acno=:acno";
    public static final String GET_MAX_TXN_ID_MB_SUBSCRIBER_TAB_HIS_FOR_ACCOUNT = "SELECT MAX(e.mbSubscriberTabHisPK.txnId) FROM MbSubscriberTabHis e WHERE e.mbSubscriberTabHisPK.acno=:acno";
    public static final String FIND_BY_ACNO_AND_SERVICE = "SELECT e FROM MbSubscriberServices e WHERE e.mbSubscriberServicesPK.acno=:acno and e.mbSubscriberServicesPK.services=:services";
    public static final String UPDATE_ALL_PUSHED_ENTITY = "UPDATE MbPushMsgTab m set m.messageStatus=10 WHERE m.messageStatus=:messageStatus AND m.mbPushMsgTabPK.dt>=:curDt AND m.mbPushMsgTabPK.dt<:nextDt";
    public static final String ENTITY_TO_PUSH_ON_CURRENT_DATE = "SELECT m FROM MbPushMsgTab m WHERE m.messageStatus=:messageStatus AND m.mbPushMsgTabPK.dt>=:curDt AND m.mbPushMsgTabPK.dt<:nextDt";
    public static final String MB_SUBSCRIBER_SERVICES_BY_ACNO_AND_SERVICETYPE = "SELECT e.mbSubscriberServicesPK.services MbSubscriberServices e WHERE e.mbSubscriberServicesPK.acno=:acno AND e.serviceType=:serviceType";
//    public static final String ALL_SMS_REGISTERED_ACTIVE_ACCOUNT_WITHOUT_STAFF = "SELECT m.acno FROM MbSubscriberTab m WHERE m.status=1 AND m.auth='Y' AND m.authStatus='V' AND substring(m.acno,1,2)=:curBrCode AND m.createdDate<=:toDt AND m.acnoType<>:acnoType order by m.acno";
    public static final String ALL_SMS_REGISTERED_ACTIVE_ACCOUNT_WITHOUT_STAFF = "SELECT m.billDeductionAcno FROM MbSubscriberTab m WHERE m.status=1 AND m.auth='Y' AND m.authStatus='V' AND substring(m.billDeductionAcno,1,2)=:curBrCode AND m.createdDate<=:toDt AND m.acnoType<>:acnoType AND substring(m.billDeductionAcno,3,2) NOT IN(SELECT a.acctCode FROM AccountTypeMaster a WHERE a.acctNature NOT IN('SB','CA')) order by m.billDeductionAcno";
    public static final String GET_ALL_FIXED_SENDED_SMS = "SELECT m FROM MbPushMsgTab m where m.acno=:acno and m.messageStatus=2";
    public static final String FIND_MAX_TO_DATE_BY_BRANCH_AND_ACTYPE = "SELECT max(m.toDate) FROM MbChargePostingHistory m WHERE substring(m.crAcno,1,2)=:brncode and m.acType=:acType";
    /**
     * *
     * Upload Neft-Rtgs Queries
     */
    public static final String FIND_MAX_TXN_ID_NEFT_RTGS_LOGGING = "SELECT MAX(m.neftRtgsLoggingPK.txnId) FROM NeftRtgsLogging m";
    public static final String GET_STATUS_ENTITY_BY_DATE = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt>=:frDt AND m.neftRtgsStatusPK.dt<=:toDt";
    public static final String GET_STATUS_ENTITY_BY_DATE_AND_STATUS = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt>=:frDt AND m.neftRtgsStatusPK.dt<=:toDt AND m.status=:status";
    public static final String GET_STATUS_ENTITY_BY_DATE_PROCESS = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt>=:frDt AND m.neftRtgsStatusPK.dt<=:toDt AND m.process=:processType AND m.neftBankName=:neftBankName AND m.reason<>:reason";
    public static final String GET_STATUS_ENTITY_BY_DATE_AND_STATUS_PROCESS = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt>=:frDt AND m.neftRtgsStatusPK.dt<=:toDt AND m.status=:status AND m.process=:processType AND m.neftBankName=:neftBankName and m.reason<>'THIS UTR ALREADY PROCESSED.'";
    public static final String GET_STATUS_ENTITY_BY_DATE_AND_DUPLICATE_PROCESS = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt>=:frDt AND m.neftRtgsStatusPK.dt<=:toDt AND m.reason=:status AND m.process=:processType AND m.neftBankName=:neftBankName";
    public static final String GET_ENTITY_BY_CURRENT_DATE_AND_STATUS = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt<=:curDt AND m.status=:status AND m.process=:processType AND m.neftBankName=:neftBankName";
    public static final String FIND_MAX_TXN_ID_NEFT_RTGS_STATUS = "SELECT MAX(m.neftRtgsStatusPK.txnId) FROM NeftRtgsStatus m";
    public static final String GET_ALL_DETAIL_CUSTOMER_REF_NO = "SELECT f FROM NeftOwDetails f where f.orgBrnid=:orgBrnid AND f.status=:status AND f.auth=:auth AND f.dt=:currentDt";
    public static final String GET_OW_UNAUTH_ENTRY_BRANCH = "SELECT f FROM NeftOwDetails f where f.orgBrnid=:orgBrnid AND f.status=:status AND f.auth=:auth and f.trsNo=:trsNo AND f.dt=:currentDt";
    public static final String GET_SNO_NEFT_HIS_CUSTOMER_REF_NO = "SELECT MAX(f.neftOwDetailsHisPK.sNo) FROM NeftOwDetailsHis f where f.neftOwDetailsHisPK.uniqueCustomerRefNo=:uCRefNo";
    public static final String GET_CUST_NAME_FOR_DEBIT_AC = "SELECT f.custname FROM AccountMaster f where f.aCNo=:acNo";
    public static final String UPDATE_NEFT_RTGS_DETAILS_AUTH = "UPDATE NeftOwDetails e SET e.auth=:auth,e.authby=:authBy WHERE e.uniqueCustomerRefNo=:custRefNo";
    public static final String ALL_NEFT_OW_DETAILS_DATA = "SELECT e FROM NeftOwDetails e WHERE e.status=:status AND e.auth=:auth AND e.initiatedBankName=:initiatedBankName";
    public static final String GET_ENTITY_BY_UTR_AND_STATUS = "SELECT m FROM NeftRtgsStatus m WHERE m.utr=:utr AND m.status=:status";
    public static final String ALL_NEFT_OW_DETAILS_DATA_BASED_ON_FILE_NAME_UCUST_REF = "SELECT e FROM NeftOwDetails e WHERE e.fileName=:fileName AND e.uniqueCustomerRefNo=:uniqueCustomerRefNo";
    public static final String GET_ENTITY_BY_TXN_TYPE_AND_STATUS = "SELECT m FROM NeftRtgsStatus m WHERE m.txnType=:txnType AND m.status=:status AND m.reason<>:reason AND m.process=:process AND m.neftBankName=:neftBankName";
    public static final String GET_UNSUCCESS_ENTITY = "SELECT m FROM NeftRtgsStatus m WHERE m.status=:status AND m.reason<>:reason AND m.process=:process AND m.neftBankName=:neftBankName";
    public static final String GET_ENTITY_BY_UTR_STATUS_AND_REASON = "SELECT m FROM NeftRtgsStatus m WHERE m.utr=:utr AND m.status=:status AND m.reason<>:reason";
    public static final String GET_PENDING_ENTITY = "SELECT m FROM NeftOwDetails m WHERE m.status <>:fail AND m.status<>:success AND m.status<>:verify AND m.auth=:auth AND m.initiatedBankName=:initiatedBankName";
    // mismatch processed
    public static final String GET_STATUS_ENTITY_BY_DATE_AND_PROCESSED_MISMATCH = "SELECT m FROM NeftRtgsStatus m WHERE m.neftRtgsStatusPK.dt>=:frDt AND m.neftRtgsStatusPK.dt<=:toDt AND m.details=:status AND m.process=:processType AND m.neftBankName=:neftBankName";

    //Npci
    public static final String DUPLICATE_DATE_SEQ_FILE = "SELECT m FROM CbsNpciInwardNonAadhaar m WHERE m.originatorCode=:originatorCode AND m.fileComingDt=:fileComingDt AND m.fileSeqNo=:fileSeqNo";
    public static final String FIND_BY_UCR_AND_IW_TYPE = "SELECT m FROM CbsNpciInward m WHERE m.userCreditReference=:ucr AND m.iwType=:iwType";
    public static final String FIND_H2H_MAX_SR_NO = "SELECT MAX(m.neftFileDetailsPK.seqNo) FROM NeftFileDetails m WHERE m.neftFileDetailsPK.fileDate=:curDate";
    //CustomerMaster Detail 
    public static final String CUSTOMER_LAST_CHANGE_DETAIL = "SELECT m FROM CbsCustomerMasterDetailHis m WHERE m.customerid=:customerid AND m.txnid=:txnid";
    public static final String CUSTOMER_LAST_CHANGE_DETAIL_FOR_MINOR = "SELECT m FROM CBSCustMinorInfoHis m WHERE m.customerId=:customerid AND m.txnid=:txnid";
    public static final String CUSTOMER_LAST_CHANGE_DETAIL_FOR_MIS = "SELECT m FROM CbsCustMisinfoHis m WHERE m.customerId=:customerid AND m.txnid=:txnid";
    public static final String CUSTOMER_LAST_CHANGE_DETAIL_FOR_NRE = "SELECT m FROM CbsCustNreinfoHis m WHERE m.customerId=:customerid AND m.txnid=:txnid";
    public static final String CONTROLLIST_AS_PER_STATUS = "SELECT i.ctrlNo FROM InvestmentFdrDetails i WHERE i.status=:stat";
    // fdr
    public static final String DETAILS_AS_PER_CTRLNO = "SELECT  i  FROM InvestmentFdrDates i WHERE i.ctrlNo=:cno";
    public static final String CONTROL_NO_DETAILS = "SELECT i FROM InvestmentFdrDetails i WHERE i.ctrlNo=:cno";
}
