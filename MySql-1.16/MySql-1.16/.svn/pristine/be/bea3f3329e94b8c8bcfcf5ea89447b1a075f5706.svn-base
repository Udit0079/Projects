package com.cbs.web.controller.txn;

import com.cbs.constant.AccStatusEnum;
import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.TxnDetailBean;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.misc.StrAlertFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
//import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.intcal.IntCalTable;
import com.cbs.web.pojo.txn.AccountTypeDescPojo;
import com.cbs.web.pojo.txn.BillTypeBean;
import com.cbs.web.pojo.txn.GLHeadGrid;
import com.cbs.web.pojo.txn.LoanDisbursementGrid;
import com.cbs.web.pojo.txn.PayOrderOutstandingGrid;
import com.cbs.web.pojo.txn.StopPaymentGrid;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Transactions extends BaseBean {

    public CashDenominationDtl cashDenominationObj;
    // Added by Manish kumar
    private String searchType;
    private List searchTypeList;
    private String searchValue;
    private String searchLabel;
    private boolean aadharAlert;
    private boolean aadharParam;
    private String npaAcNo;
    private String npaAcName;
    private String focusId;
    private String displayNPA;
    private String newNpaAcNo;
    //private String poAccnoFlag;

//    public String getPoAccnoFlag() {
//        return poAccnoFlag;
//    }
//
//    public void setPoAccnoFlag(String poAccnoFlag) {
//        this.poAccnoFlag = poAccnoFlag;
//    }
    public String getNewNpaAcNo() {
        return newNpaAcNo;
    }

    public void setNewNpaAcNo(String newNpaAcNo) {
        this.newNpaAcNo = newNpaAcNo;
    }

    public String getDisplayNPA() {
        return displayNPA;
    }

    public void setDisplayNPA(String displayNPA) {
        this.displayNPA = displayNPA;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getNpaAcNo() {
        return npaAcNo;
    }

    public void setNpaAcNo(String npaAcNo) {
        this.npaAcNo = npaAcNo;
    }

    public String getNpaAcName() {
        return npaAcName;
    }

    public void setNpaAcName(String npaAcName) {
        this.npaAcName = npaAcName;
    }

    public boolean isAadharAlert() {
        return aadharAlert;
    }

    public void setAadharAlert(boolean aadharAlert) {
        this.aadharAlert = aadharAlert;
    }

    public CashDenominationDtl getCashDenominationObj() {
        return cashDenominationObj;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public List getSearchTypeList() {
        return searchTypeList;
    }

    public void setSearchTypeList(List searchTypeList) {
        this.searchTypeList = searchTypeList;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchLabel() {
        return searchLabel;
    }

    public void setSearchLabel(String searchLabel) {
        this.searchLabel = searchLabel;
    }

    public void setCashDenominationObj(CashDenominationDtl cashDenominationObj) {
        this.cashDenominationObj = cashDenominationObj;
    }

    public Transactions() {
        try {
            this.setPanConfirmMsg("");

            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxnAuth);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            strAlertRemote = (StrAlertFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameStrPost);

            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup(jndiAdvancesPost);
            acFacade = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup(jndiAcFacade);
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiLoanName);
            ibrFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup(jndiIbrName);
            try {
                long interval = txnRemote.getPollInterval();
                if (interval == 0) {
                    setPollEnabled(false);
                    setPollInterval(0);
                } else {
                    setPollEnabled(true);
                    setPollInterval(interval);
                    setPymtAlertMsg("Your Monthly Rent of CBS Services has been overdue. Please make the Payment ASAP for uninterrupted Services.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            getDataOnLoad();
            getGlobalDataOnLoad();
            int intPostOnDeposit = Integer.parseInt(ftsPostRemote.getCodeByReportName("INT_POST_DEPOSIT"));
            if (intPostOnDeposit == 0) {
                intDtlVar = "";
            } else {
                intDtlVar = "Ctrl+L-Loan Int Detail.";
            }

            txnCashParam = 0;
            txnCashParam = Integer.parseInt(ftsPostRemote.getCodeByReportName("TELLER_DENOMINITION"));
            cashMode = ftsPostRemote.getCashMode(getOrgnBrCode());

            this.setAcNoLen(getAcNoLength());
            setFocusId("ddRelated");
            setDisplayNPA("none");

            //Cash Denomination
            cashDenominationObj.setErrorMessage1("");
            cashDenominationObj.setDisableDenoNo(false);
            cashDenominationObj.setDenoNo("");
            cashDenominationObj.setDenoValue("");
            cashDenominationObj.setCurrencyAmount("000");
            cashDenominationObj.setDenominationTable(new LinkedList<DDSDenominationGrid>());
            cashDenominationObj.setTyDenoValue("");
            cashDenominationObj.setAcDenoNat("");
            cashDenominationObj.setTyFlg("0");
            cashDenominationObj.setTyCaption("Recived/Return :");
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getDataOnLoad() {
        try {
            tranTypeList = new ArrayList<SelectItem>();
            tranTypeList.add(new SelectItem(" ", "--Select--"));
            List l1 = txnRemote.getDropdownDataOnLoadTranType("7");
            for (int i = 0; i < l1.size(); i++) {
                Vector v1 = (Vector) l1.get(i);
                tranTypeList.add(new SelectItem(v1.get(0).toString(), v1.get(1).toString()));
            }
            crDrList = new ArrayList<SelectItem>();
            crDrList.add(new SelectItem(" ", "--Select--"));
            List l2 = txnRemote.getDropdownDataOnLoad("2");
            for (int i = 0; i < l2.size(); i++) {
                Vector v2 = (Vector) l2.get(i);
                crDrList.add(new SelectItem(v2.get(0).toString(), v2.get(1).toString()));
            }
            relatedToList = new ArrayList<SelectItem>();
            relatedToList.add(new SelectItem(" ", "--Select--"));
            List l3 = txnRemote.getDropdownDataOnLoad("42");
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                relatedToList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
            }
            chargeDtlList = new ArrayList<SelectItem>();
            chargeDtlList.add(new SelectItem(" ", "--Select--"));
            List l4 = txnRemote.getDropdownDataOnLoad("37");
            for (int i = 0; i < l4.size(); i++) {
                Vector v4 = (Vector) l4.get(i);
                chargeDtlList.add(new SelectItem(v4.get(0).toString(), v4.get(1).toString()));
            }
            byList = new ArrayList<SelectItem>();
            byList.add(new SelectItem(" ", "--Select--"));
            List l5 = txnRemote.getBy("54");
            for (int i = 0; i < l5.size(); i++) {
                Vector v5 = (Vector) l5.get(i);
                byList.add(new SelectItem(v5.get(0).toString(), v5.get(1).toString()));
            }

            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem(" ", "--Select--"));

            alphaCodeList = new ArrayList<SelectItem>();
            alphaCodeList.add(new SelectItem(" ", "--Select--"));

            poModeList = new ArrayList<SelectItem>();
            poModeList.add(new SelectItem(" ", "--Select--"));

            dataList = new ArrayList<TxnDetailBean>();

            cashPOTxnBeanList = new ArrayList<TxnDetailBean>();

            tempXferList = new ArrayList<TxnDetailBean>();

            this.setAdhoclimit(new BigDecimal("0.00"));
            this.setBalance(new BigDecimal("0.00"));
            this.setPendingBalance(new BigDecimal("0.00"));

            this.setTokenNumber("0");
            this.setSubTokenNo("A");
            this.setChqNo("");
            this.setAmountTxn("");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.setValueDate(sdf.format(new Date()));
            this.setChqueDate(null);

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getGlobalDataOnLoad() {
        List list7 = txnRemote.selectForGlobalVariables(getOrgnBrCode());
        if (!list7.isEmpty()) {
            Vector v7 = (Vector) list7.get(0);
            gCashMod = v7.get(2).toString();
        }
        if (gCashMod.equals("Y")) {
            tokenFlag = "";
        }
        List list8 = txnRemote.selectBulkReportsFromParameterInfoReport();
        for (int i = 0; i < list8.size(); i++) {
            Vector v8 = (Vector) list8.get(i);
            if (v8.get(1).toString().equalsIgnoreCase("CCODEXPIRY")) {
                if (Integer.parseInt(v8.get(0).toString()) == 1) {
                    ccodExpiryFlag = true;
                } else {
                    ccodExpiryFlag = false;
                }
            } else if (v8.get(1).toString().equalsIgnoreCase("MSGMODULE_ACTIVE")) {
                if (Integer.parseInt(v8.get(0).toString()) == 1) {
                    msgModuleActive = true;
                } else {
                    msgModuleActive = false;
                }
            } else if (v8.get(1).toString().equalsIgnoreCase("PAN AMOUNT")) {
                if (Integer.parseInt(v8.get(0).toString()) == 0) {
                    panamount = "50000";
                } else {
                    panamount = String.valueOf(Float.parseFloat(v8.get(0).toString()) * 1000);
                }
            } else if (v8.get(1).toString().equalsIgnoreCase("STAXMODULE_ACTIVE")) {
                if (Integer.parseInt(v8.get(0).toString()) == 0) {
                    staxModuleActive = false;
                } else {
                    staxModuleActive = true;
                }
            } else if (v8.get(1).toString().equalsIgnoreCase("VALUE DATE ACTIVE")) {
                if (Integer.parseInt(v8.get(0).toString()) == 0) {
                    valueDatedFlag = "true";
                } else {
                    valueDatedFlag = "false";
                }
            } else if (v8.get(1).toString().equalsIgnoreCase("PO-MULTIPLE-ENTRY")) {
                if (Integer.parseInt(v8.get(0).toString()) == 0) {
                    poMultipleEntry = false;
                } else {
                    poMultipleEntry = true;
                }
            } else if (v8.get(1).toString().equalsIgnoreCase("AADHAR-ALERT")) {
                if (Integer.parseInt(v8.get(0).toString()) == 0) {
                    aadharParam = false;
                } else {
                    aadharParam = true;
                }
            }
        }
    }
    String accountStatus = "";

    public void accNoLostFocus() {
        NumberFormat formatter = new DecimalFormat("#.##");
        loanDisbFlag = "false";
        String optStatus = null, acctOpenDate = null;

        seqNoYearFlag = "none";
        tranTypeVisibility = "false";
        setAccNo("");
        setMessage("");
        setErrorMsg("");
        setAadharAlert(false);
        setDisplayNPA("none");
        try {
            if (this.oldAcno.equalsIgnoreCase("") || this.oldAcno == null || this.oldAcno.equalsIgnoreCase("null")) {
                flag1 = "true";
                resetFormExcludeXferGrid();
                this.setErrorMsg("Please Enter Proper Account Number.");
                return;
            }
            if (!this.oldAcno.equalsIgnoreCase("") && ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                flag1 = "false";
                resetFormExcludeXferGrid();
                this.setErrorMsg("Please Fill Proper Account Number.");
                return;
            }
            accNo = ftsPostRemote.getNewAccountNumber(oldAcno);
            curBrnCode = ftsPostRemote.getCurrentBrnCode(accNo);
            String chkAccNo = txnRemote.checkForAccountNo(this.accNo);
            if (!chkAccNo.equals("TRUE")) {
                flag1 = "false";
                resetFormExcludeXferGrid();
                this.setErrorMsg(chkAccNo);
                return;
            }
            msgFlag = 0;
            TmpPostFlag = 1;

            this.setRelatedTo(" ");
            this.setDetails("");
            this.setBy(" ");
            this.setTokenNumber("0");
            this.setSubTokenNo("A");

            this.setChqNo("");
            this.setAmountTxn("");
            this.setChqueDate(null);

            this.setValueDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            this.setSeqNo("0");
            this.setSeqYear("0");
            this.setAlertType("");
            this.setAlertCode("");
            this.setAlertSubCode("");
            this.setSubCodeDesc("");

            relatedToVisibleFlag = "false";
            chqNoFlag = "false";
            chqDateFlag = "false";
            flag2 = "false";

            cancelBillTypeTxn();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            Tempbd = ymd.format(new Date());
            String msg = ftsPostRemote.ftsDateValidate(Tempbd, getOrgnBrCode());

            if (!msg.equalsIgnoreCase("TRUE")) {
                this.setErrorMsg(msg);
                flag1 = "false";
                return;
            }
            flag1 = "true";
            AcNature = ftsPostRemote.getAccountNature(accNo);

            /**
             * ************* Pay Order (GL Head ) ***********
             */
            if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                limitFlag = "none";
                dplimitFlag = "none";
                limitDpLimitFlag = "none";

                List selectGLTable = txnRemote.selectFromGLTable(accNo);
                if (selectGLTable.isEmpty()) {
                    this.setErrorMsg("Account No does not exist.");
                    flag1 = "false";
                    return;
                }
                flag1 = "true";

                this.setAccNoMsg("");
                this.setChqFaci("");
                this.setJtName("");
                this.setOpMode("");

                this.setAdhoclimit(new BigDecimal("0.00"));
                this.setAccInstruction("");
                this.setPendingBalance(new BigDecimal("0.00"));

                this.setFlag3("none");
                this.setLimitDpLimitFlag("none");
                accountStatus = "";

                Vector vecGLTable = (Vector) selectGLTable.get(0);
                TmpPostFlag = Integer.parseInt(vecGLTable.get(2).toString());
                msgFlag = Integer.parseInt(vecGLTable.get(3).toString());

                if (TmpPostFlag == 0 || TmpPostFlag == 99) {
                    this.setErrorMsg("Entry for this Account No is not allowed.");
                    flag1 = "false";
                    return;
                }
                if (msgModuleActive == true && msgFlag == 0) {
                    this.setErrorMsg("Entry for this Account No is not alowed.");
                    flag1 = "false";
                    return;
                }
                if (msgFlag == 50) {
                    this.setErrorMsg("Entry in Share Capital Head is not allowed from this Screen");
                    flag1 = "false";
                    return;
                }

                if (!(curBrnCode.equalsIgnoreCase(this.getOrgnBrCode())) && (TmpPostFlag == 11 || TmpPostFlag == 12)) {
                    this.setErrorMsg("Txn is not allowed for other branch's GL A/C.");
                    flag1 = "false";
                    return;
                }

                if (!(ftsPostRemote.getCodeForReportName("REMOTE_GL_TXN").equals(1) && msgFlag != 4)) {
                    if (!(curBrnCode.equalsIgnoreCase(this.getOrgnBrCode()))
                            && !(ftsPostRemote.getOfficeAccountNo(accNo).equalsIgnoreCase("true")
                            || ftsPostRemote.isATMHead(accNo, getOrgnBrCode()).equalsIgnoreCase("true"))) {
                        this.setErrorMsg("Txn is not allowed for other branch's GL A/C.");
                        flag1 = "false";

                        this.setAccountName("");
                        this.setChqFaci("");

                        this.setJtName("");
                        this.setOpMode("");

                        this.setOpenDate("");
                        this.setAdhoclimit(new BigDecimal("0.00"));

                        this.setBalance(new BigDecimal("0"));
                        this.setAccInstruction("");

                        this.setPendingBalance(new BigDecimal("0.00"));
                        this.setLabelCrDr("");

                        this.setFlag3("none");
                        this.setLimitDpLimitFlag("none");

                        return;
                    }
                }
                this.setAccNoMsg("");
                this.setAccountName(vecGLTable.get(0).toString());
                if (msgFlag == 4) {
                    flag2 = "true";
                }
                List selectReconTdReconCaRecon = txnRemote.selectFromReconTdReconCaRecon(accNo, AcNature);
                if (!selectReconTdReconCaRecon.isEmpty()) {
                    Vector vecReconTdReconCaRecon = (Vector) selectReconTdReconCaRecon.get(0);
                    balance = new BigDecimal(vecReconTdReconCaRecon.get(0).toString());
                }
                if (Double.parseDouble(balance.toString()) < 0) {
                    labelCrDr = " Dr";
                    //Added By Dhirendra Singh for removing (-) singn
                    balance = balance.abs();
                } else {
                    labelCrDr = " Cr";
                }
            } else {
                List listForDSFDMSOF;
                String entityType = "";
                List<String> custIdList = txnRemote.getCustIdByAccount(accNo);
                if (!custIdList.isEmpty()) {
                    this.setCustId(custIdList.get(0));
                    this.setAnnualTurnover(custIdList.get(1));
                    this.setRiskCategorization(custIdList.get(2));

                    this.setCustPanNo(custIdList.get(3));
                    this.setProfession(custIdList.get(4));
                    this.setCustAadharNo(custIdList.get(5));
                    entityType = custIdList.get(6);
                    acctCategory = custIdList.get(7);
                } else {
                    this.setCustId("");
                    this.setAnnualTurnover("");
                    this.setRiskCategorization("");

                    this.setCustPanNo("");
                    this.setProfession("");
                    this.setCustAadharNo("");
                }
                if (aadharParam && getCustAadharNo().equals("") && entityType.equals("01")) {
                    setAadharAlert(true);
                }

                if (((AcNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (AcNature.equalsIgnoreCase(CbsConstant.MS_AC)))) {
                    limitFlag = "none";
                    dplimitFlag = "none";

                    limitDpLimitFlag = "none";

                    String chkMsg = txnAuthRemote.fdFidilityChk(accNo);

                    if (chkMsg.equalsIgnoreCase("true")) {
                        listForDSFDMSOF = txnRemote.selectFidiltyDtl(accNo);
                    } else {
                        listForDSFDMSOF = txnRemote.selectForFDMS(accNo);
                    }
                    Vector v1 = (Vector) listForDSFDMSOF.get(0);
                    accountStatus = v1.get(9).toString();
                    acctOpenDate = v1.get(8).toString();
                    this.accountName = v1.get(1).toString();

                    jtName = v1.get(2).toString() + " " + v1.get(3).toString() + " " + v1.get(4).toString() + " " + v1.get(5).toString();
                    opMode = v1.get(7).toString();

                    openDate = acctOpenDate.substring(6) + "/" + acctOpenDate.substring(4, 6) + "/" + acctOpenDate.substring(0, 4);
                    adhoclimit = new BigDecimal(Float.parseFloat("0"));

                    accInstruction = v1.get(6).toString();
                    balance = new BigDecimal("0");
                    this.setChqFaci("");
                } else {
                    if (AcNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || AcNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        limitFlag = "none";
                        dplimitFlag = "none";
                        limitDpLimitFlag = "none";
                    } else {
                        limitFlag = "";
                        dplimitFlag = "";
                        limitDpLimitFlag = "";
                    }
////                    if (AcNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
////                        listForDSFDMSOF = txnRemote.selectForDS(accNo);
////                    } else {
                    listForDSFDMSOF = txnRemote.selectFromAcctMaster(accNo);
                    //
                    Vector v1 = (Vector) listForDSFDMSOF.get(0);
                    accountStatus = v1.get(10).toString();
                    acctOpenDate = v1.get(9).toString();

                    optStatus = v1.get(12).toString();
                    this.accountName = v1.get(1).toString();
                    jtName = v1.get(2).toString() + " " + v1.get(3).toString() + " " + v1.get(4).toString() + " " + v1.get(5).toString();
                    opMode = v1.get(7).toString();

                    openDate = acctOpenDate.substring(6) + "/" + acctOpenDate.substring(4, 6) + "/" + acctOpenDate.substring(0, 4);
                    adhoclimit = new BigDecimal(Float.parseFloat(v1.get(11).toString()));
                    accInstruction = v1.get(6).toString();

                    dpLimit = new BigDecimal(Float.parseFloat(v1.get(8).toString()));
                    if (Integer.parseInt(v1.get(13).toString()) == 1) {
                        this.setChqFaci("Yes");
                    } else {
                        this.setChqFaci("No");
                    }
                }
                AcFlag = Integer.parseInt(accountStatus);
                if (AcFlag == 9 || AcFlag == 15) {
                    flag1 = "false";
                    //resetFormExcludeXferGrid();
                    this.setBalance(new BigDecimal("0.00"));
                    this.setPendingBalance(new BigDecimal("0.00"));
                    this.setLabelCrDr("");
                    this.setErrorMsg(AccStatusEnum.getStatusMsg(AcFlag));
                    return;
                }
                this.setMessage(AccStatusEnum.getStatusMsg(AcFlag));
                List list2 = txnRemote.selectForOperationMode(opMode);
                Vector v2 = (Vector) list2.get(0);
                this.setOpMode(v2.get(0).toString());
                if ((AcNature.equals(CbsConstant.TERM_LOAN) || AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.CURRENT_AC))
                        && (accountStatus.equals("11") || accountStatus.equals("12") || accountStatus.equals("13") || accountStatus.equals("14"))) {
                    int index = getIndexOfNPACharges(relatedToList);
                    if (index == -1) {
                        relatedToList.add(new SelectItem("102", "NPA CHARGES"));
                    }
                } else {
                    int index = getIndexOfNPACharges(relatedToList);
                    if (index >= 0) {
                        relatedToList.remove(index);
                    }
                }
                if (!AcNature.equalsIgnoreCase(CbsConstant.OF_AC) && !AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    long datediff = CbsUtil.monthDiff(ymd.parse(acctOpenDate), ymd.parse(Tempbd));
                    if (datediff < 7) {
                        newAccNo = "New Account";
                        flag3 = "";
                    } else {
                        newAccNo = "";
                        flag3 = "none";
                    }
                }
                if ((AcNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                    String chExp = txnRemote.loanExpCheck(accNo, Tempbd, acctOpenDate);
                    if (!chExp.equalsIgnoreCase("true")) {
                        newAccNo = chExp;
                        flag3 = "";
                    } else {
                        if (!newAccNo.equalsIgnoreCase("New Account")) {
                            newAccNo = "";
                            flag3 = "none";
                        }
                    }
                }
                String chKyc = txnRemote.kycExpCheck(accNo, Tempbd);
                if (!chKyc.equalsIgnoreCase("true")) {
                    newAccNo = chKyc;
                    flag3 = "";
                } else {
                    if (!newAccNo.equalsIgnoreCase("New Account")) {
                        newAccNo = "";
                        flag3 = "none";
                    }
                }
                if (AcFlag == 10) {
                    List selectFromAccStatus = txnRemote.selectFromAccountStatus(accNo);
                    if (!selectFromAccStatus.isEmpty()) {
                        Vector vecForAcctStatus = (Vector) selectFromAccStatus.get(0);
                        this.setAccNoMsg("Account Has been Lien Marked for amount of Rs." + vecForAcctStatus.get(0).toString());
                    } else {
                        this.setErrorMsg("Account Has been Lien Marked.");
                        flag1 = "false";
                        this.setBalance(new BigDecimal("0.00"));
                        this.setPendingBalance(new BigDecimal("0.00"));
                        this.setLabelCrDr("");
                        return;
                    }
                }
//                else if (AcFlag == 9) {
//                    this.setErrorMsg("Account has been Closed.");
//                    flag1 = "false";
//                    this.setBalance(new BigDecimal("0.00"));
//                    this.setPendingBalance(new BigDecimal("0.00"));
//                    this.setLabelCrDr("");
//                    return;
//                }
                if (AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || AcNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    if (this.accNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        List selectFromLoanMisDtls = txnRemote.selectFromLoanMisDetails(accNo, Tempbd);
                        if (!selectFromLoanMisDtls.isEmpty()) {
                            this.setAccNoMsg("Document is Time Barred.");
                        }
                    }
                    if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && !this.accNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        List selectFromLoanAppParam = txnRemote.selectFromLoanAppParameter(accNo);
                        if (!selectFromLoanAppParam.isEmpty()) {
                            Vector vecLoanAppParam = (Vector) selectFromLoanAppParam.get(0);
                            limitDpLimitFlag = "";
                            limit = new BigDecimal(Float.parseFloat(vecLoanAppParam.get(0).toString()));
                        }
                    } else {
                        limit = dpLimit;
                        limitDpLimitFlag = "";
                        limitFlag = "";
                        dplimitFlag = "none";
                    }
                } else {
                    limitDpLimitFlag = "none";
                    limitFlag = "none";
                    dplimitFlag = "none";
                }
                List selectReconTdReconCaRecon = txnRemote.selectFromReconTdReconCaRecon(accNo, AcNature);
                if (!selectReconTdReconCaRecon.isEmpty()) {
                    Vector vecReconTdReconCaRecon = (Vector) selectReconTdReconCaRecon.get(0);
                    balance = new BigDecimal(vecReconTdReconCaRecon.get(0).toString());
                }
                List listForPendingBal = txnRemote.selectPendingBalance(accNo);
                if (!listForPendingBal.isEmpty()) {
                    Vector vecForPendingBal = (Vector) listForPendingBal.get(0);
                    if (vecForPendingBal.get(0).toString().equalsIgnoreCase("0.0") || vecForPendingBal.get(0).toString().equalsIgnoreCase("0")) {
                        this.setPendingBalance(new BigDecimal(".00"));
                    } else {
                        this.setPendingBalance(new BigDecimal(vecForPendingBal.get(0).toString()));
                    }
                }
                if (AcNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    TmpPostFlag = Integer.parseInt(optStatus);
                    List listForStopPay = txnRemote.selectForStopBalance(accNo, AcNature);
                    if (!listForStopPay.isEmpty()) {
                        this.setAccNoMsg("STOP PAYMENT");
                    } else {
                        this.setAccNoMsg("");
                    }
                } else {
                    this.setAccNoMsg("");
                }
                availLimit = "0.00";
                if (Double.parseDouble(balance.toString()) < 0) {
                    labelCrDr = " Dr";
                    //Added By Dhirendra Singh for removing (-) singn
                    balance = balance.abs();
                    if (balance.compareTo(limit) >= 0) {
                        availLimit = "0.00";
                    } else {
                        if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            availLimit = formatter.format(limit.add(adhoclimit).subtract(balance));
                        }
                    }
                } else {
                    labelCrDr = " Cr";
                    if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        availLimit = formatter.format(limit.add(adhoclimit).add(balance));
                    }
                }

                String acChk = ftsPostRemote.getAcnoByPurpose("MIR-HEAD");
                if (!acChk.equalsIgnoreCase("")) {
                    if (acChk.equalsIgnoreCase(accNo.substring(2))) {
                        this.setMessage("Remember To Fill Details, Start With Member No.");
                    }
                }
                getSignatureDetail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag1 = "false";
            resetFormExcludeXferGrid();
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getNPAAcctDetails() {
        try {
            setMessage("");
            setNpaAcName("");
            setNewNpaAcNo("");
            if (npaAcNo == null || npaAcNo.equalsIgnoreCase("") || ((this.npaAcNo.length() != 12) && (this.npaAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                throw new ApplicationException("Please enter proper account number.");
            }
            newNpaAcNo = ftsPostRemote.getNewAccountNumber(npaAcNo);
            if (!newNpaAcNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
                throw new ApplicationException("Entry allow only from base branch.");
            }
            String acNature = ftsPostRemote.getAccountNature(newNpaAcNo);
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                List acnochkList = txnRemote.getNpaAcnoChechByAcno(newNpaAcNo);
                if (acnochkList.isEmpty()) {
                    throw new ApplicationException("This Account number does not NPA.");
                }
            } else {
                throw new ApplicationException("Account No not in TL,DL and CA Nature!");
            }
            List custDetailList = txnRemote.getCustomerDetailByAcno(newNpaAcNo);
            if (custDetailList.isEmpty()) {
                throw new ApplicationException("Account does not exits!");
            }
            Vector ele = (Vector) custDetailList.get(0);
            npaAcName = ele.get(1).toString();
            setFocusId("txtDetail");
        } catch (Exception e) {
            setNpaAcNo("");
            setNewNpaAcNo("");
            setFocusId("stxtNpa");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void loadDisbursementGrid() {
        try {
            setLoanDisbFlag("false");
            if (accNo.equals("")) {
                throw new ApplicationException("Please fill the account number");
            }
            double totamt1 = 0;
            if (AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                loanDisbGridList = new ArrayList<LoanDisbursementGrid>();
                List listForLoanDisbursement = txnRemote.selectFromLoanDisbursement(accNo);
                setLoanDisbFlag("true");
                for (int i = 0; i <= listForLoanDisbursement.size(); i++) {
                    loanDisbGrid = new LoanDisbursementGrid();

                    if (i < listForLoanDisbursement.size()) {
                        Vector vecForLoanDisbursement = (Vector) listForLoanDisbursement.get(i);
                        loanDisbGrid.setSrNo(vecForLoanDisbursement.get(1).toString());
                        loanDisbGrid.setDisburseAmt(vecForLoanDisbursement.get(2).toString());
                        String dt = vecForLoanDisbursement.get(3).toString();

                        loanDisbGrid.setDisbDate(dt.substring(8, 10) + "/" + dt.substring(5, 7) + "/" + dt.substring(0, 4));
                        loanDisbGrid.setRemarks(vecForLoanDisbursement.get(4).toString());
                        totamt1 = totamt1 + Double.parseDouble(vecForLoanDisbursement.get(2).toString());
                    } else if (i == listForLoanDisbursement.size()) {
                        loanDisbGrid.setSrNo("TOTAL");
                        loanDisbGrid.setDisburseAmt(String.valueOf(totamt1));
                    }
                    loanDisbGridList.add(loanDisbGrid);
                }
            }
        } catch (Exception e) {
            setLoanDisbFlag("false");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void tranTypeLostFocus() {
        try {
            if (this.accNo == null || this.accNo.equals("")) {
                this.setErrorMsg("Please fill the A/C No.");
                return;
            }
            if (this.tranType.equalsIgnoreCase("0")) {
                if (AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.TERM_LOAN) || AcNature.equals(CbsConstant.RECURRING_AC)) {
                    this.setSelectCrDr("1");
                    flag4 = "none";
                } else {
                    this.setSelectCrDr("0");
                    TrnType = 0;
                    flag4 = "none";
                }
            }
            if (this.tranType.equalsIgnoreCase("1")) {
                this.setSelectCrDr("1");
                TrnType = 1;
                flag4 = "none";
            }
            if (this.tranType.equalsIgnoreCase("2")) {
                this.setSelectCrDr("0");
                TrnType = 2;
                flag4 = "";
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void selectChqDate() {
        try {
            if (chqueDate == null) {
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String sixMonthChqDt = "20120331"; //According to Mayank sir, It will change after discussion.
            String chqDt = sdf.format(chqueDate);

            Long longTempDate = Long.parseLong(Tempbd);
            Long longChqDate = Long.parseLong(chqDt);

            if (!validateInstNoDtAgainstOpenDt(chqueDate).equalsIgnoreCase("true")) {
                this.setErrorMsg("Cheque date should not be less than account opening date !");
                return;
            }
            if (longChqDate.compareTo(longTempDate) > 0) {
                String By = null;
                if (this.by.equalsIgnoreCase("1")) {
                    By = "CHEQUE";
                } else if (this.by.equalsIgnoreCase("2")) {
                    By = "W/S";
                } else if (this.by.equalsIgnoreCase("3")) {
                    By = "VOUCHER";
                } else if (this.by.equalsIgnoreCase("4")) {
                    By = "LOOSE CHEQUE";
                }
                this.setMessage("Post dated" + " " + By + " " + "is not allowed");
                chqDateFocus = "true";
                return;
            }
            this.setMessage("");
            if (this.selectCrDr.equalsIgnoreCase("1") && (this.by.equalsIgnoreCase("1") || this.by.equalsIgnoreCase("4"))) {
                Long longChqDt = Long.parseLong(CbsUtil.monthAdd(chqDt, 3));

                int compareChqDt = sdf.parse(chqDt).compareTo(sdf.parse(sixMonthChqDt));
                if (compareChqDt <= 0) {
                    longChqDt = Long.parseLong(CbsUtil.monthAdd(chqDt, 6));
                }
                if (longChqDt.compareTo(longTempDate) < 0) {
                    this.setErrorMsg("Instrument Date is more than three months old.");
                    chqDateFocus = "true";
                    return;
                }
            }
            chqDateFocus = "false";
            this.setMessage("");
            if (this.relatedTo.equalsIgnoreCase("10") && this.tranType.equalsIgnoreCase("0") && this.selectCrDr.equalsIgnoreCase("1") && this.by.equalsIgnoreCase("1")) {
                chqFocus = "true";
                tokenFocus = "false";
                amountFocus = "false";
                return;
            }
            if (this.by.equalsIgnoreCase("1") || this.by.equalsIgnoreCase("4")) {
                chqFocus = "true";
                tokenFocus = "false";
                amountFocus = "false";
                return;
            }
            if (this.selectCrDr.equalsIgnoreCase("1") && (this.by.equalsIgnoreCase("1") || this.by.equalsIgnoreCase("4"))) {
                chqFocus = "true";
                tokenFocus = "false";
                amountFocus = "false";
                return;
            }
            chqFocus = "false";
            tokenFocus = "false";
            amountFocus = "true";
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public boolean amountTxnLostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.amountTxn == null || this.amountTxn.toString().equalsIgnoreCase("")) {
            this.setErrorMsg("Please Fill Amount Field.");
            amountTxnFocus = "true";
            return false;
        } else if (this.amountTxn.toString().equalsIgnoreCase("0") || this.amountTxn.toString().equalsIgnoreCase("0.0")) {
            this.setErrorMsg("Please Fill Amount Field.");
            amountTxnFocus = "true";
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.amountTxn.toString());
            if (!amountTxnCheck.matches()) {
                this.setErrorMsg("Invalid Entry.");
                amountTxnFocus = "true";
                return false;
            }
        }
        if (validateAmount() != true) {
            amountTxnFocus = "true";
            return false;
        }
        this.setErrorMsg("");
        amountTxnFocus = "false";
        relatedToVisibleFlag = "false";
        if (TmpPostFlag == 11 || TmpPostFlag == 12) {
            if (!dataList.isEmpty()) {
                if (accNo.equals(dataList.get(0).getAcNo()) && this.selectCrDr.equals(dataList.get(0).getTy())) {
                    if (TmpPostFlag == 11) {
                        this.setMessage("Multiple Debit is not allow in this same Batch No. of this A/c No-" + accNo);
                    } else {
                        this.setMessage("Multiple credit is not allow in this same Batch No. of this A/c No-" + accNo);
                    }
                    return false;
                }
            }
            if (Double.parseDouble(this.seqNo) == 0 && Double.parseDouble(this.seqYear) == 0) {
                if (contra(TmpPostFlag, this.selectCrDr)) {
                    this.setRelatedTo("9");

                    seqNoYearFlag = "";
                    this.setSeqNo("0");
                    this.setSeqYear("0");

                    relatedToVisibleFlag = "true";
                    if (TmpPostFlag == 11) {
                        Sundrytable = "bill_sundry";
                        seqNoYearFlag = "";
                        if ((this.seqNo.equals("") || this.seqNo.equals("0") || this.seqNo.equals("0.0")) || (this.seqYear.equals("") || this.seqYear.equals("0") || this.seqYear.equals("0.0"))) {
                            seqNoYearFocus = "true";
                        } else {
                            seqNoYearFocus = "false";
                        }

                    } else if (TmpPostFlag == 12) {
                        Sundrytable = "bill_suspense";
                        seqNoYearFlag = "";
                        if ((this.seqNo.equals("") || this.seqNo.equals("0") || this.seqNo.equals("0.0")) || (this.seqYear.equals("") || this.seqYear.equals("0") || this.seqYear.equals("0.0"))) {
                            seqNoYearFocus = "true";
                        } else {
                            seqNoYearFocus = "false";
                        }
                    } else if (TmpPostFlag == 13) {
                        Sundrytable = "bill_clgadjustment";
                        seqNoYearFlag = "";
                        if ((this.seqNo.equals("") || this.seqNo.equals("0") || this.seqNo.equals("0.0")) || (this.seqYear.equals("") || this.seqYear.equals("0") || this.seqYear.equals("0.0"))) {
                            seqNoYearFocus = "true";
                        } else {
                            seqNoYearFocus = "false";
                        }
                    } else {
                        this.setRelatedTo("0");
                    }
                } else {
                    seqNoYearFlag = "none";
                    this.setRelatedTo("0");
                    relatedToVisibleFlag = "false";
                }
            } else {
                seqNoYearFocus = "false";
                relatedToVisibleFlag = "false";
            }
        } else {
            seqNoYearFlag = "none";
            relatedToVisibleFlag = "false";
        }
        try {
            if (!accNo.substring(2, 4).equalsIgnoreCase("06")) {
                String strTmpMsg = strAlertRemote.getStrAlert(accNo, Tempbd, selectCrDr, tranType, amountTxn);
                if (!strTmpMsg.equalsIgnoreCase("")) {
                    alertSubCode = strTmpMsg;
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return true;
    }

    public void onblurSundrySeqNo() {
        try {
            if (this.seqNo.equals("")) {
                this.setSeqNo("0");
            } else if (!this.seqNo.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                this.setSeqNo("0");
            } else if (Double.parseDouble(this.seqNo) < 0) {
                this.setSeqNo("0");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public boolean validateAmount() {
        if (this.amountTxn.toString().contains(".")) {
            if (this.amountTxn.toString().indexOf(".") != this.amountTxn.toString().lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                return false;
            } else if (this.amountTxn.toString().substring(amountTxn.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
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

    public boolean validatePOAmount() {
        if (this.poAmount.toString().contains(".")) {
            if (this.poAmount.toString().indexOf(".") != this.poAmount.toString().lastIndexOf(".")) {
                this.setPoError("Invalid Amount.Please Fill The Amount Correctly.");
                return false;
            } else if (this.poAmount.toString().substring(poAmount.toString().indexOf(".") + 1).length() > 2) {
                this.setPoError("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setPoError("");
                return true;
            }
        } else {
            this.setPoError("");
            return true;
        }
    }

    public boolean contra(int a1, String a2) {
        if (a1 == 11 && a2.equalsIgnoreCase("1")) {
            return true;
        }
        if (a1 == 12 && a2.equalsIgnoreCase("0")) {
            return true;
        }
        return false;
    }

    public void tyLostFocus() {
        try {
            if (this.accNo == null || this.accNo.equals("")) {
                throw new ApplicationException("Please fill the A/C No.");
            }
            poddFlag = "false";
            //poAccnoFlag = "none";
            this.setMessage("");

//            if(txnCashParam == 1){
//               if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) && this.getTranType().equalsIgnoreCase("0") && this.selectCrDr.equalsIgnoreCase("0") && msgFlag == 4) {
//                   throw new ApplicationException("Pay order can not be issued from cash for the time being ");
//               }
//            }
            if (this.tranType.equalsIgnoreCase("0")) {
                if (this.selectCrDr.equalsIgnoreCase("0")) {
                    if (AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.TERM_LOAN)
                            || AcNature.equals(CbsConstant.RECURRING_AC)) {
                        this.setBy("3");
                        this.setRelatedTo("1");
                    } else if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        this.setBy("3");
                        if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 4) {
                            poddFlag = "true";
                            poddOnLoad();
                        } else if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 99) {
                            poddFlag = "false";
                        }
                    } else {
                        this.setBy("3");
                        this.setRelatedTo("0");
                    }
                } else if (this.selectCrDr.equalsIgnoreCase("1")) {
                    if (AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.TERM_LOAN)) {
                        this.setBy("3");
                        this.setRelatedTo("6");
                    } else if (AcNature.equals(CbsConstant.RECURRING_AC)) {
                        this.setBy("3");
                        this.setRelatedTo("0");
                    } else if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        this.setBy("3");
                        if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 4) {
                            poddFlag = "true";
                            poddOnLoad();
                        } else if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 99) {
                            poddFlag = "false";
                        }
                    } else {
                        this.setBy("2");
                        this.setRelatedTo("0");
                    }
                }
            }
            if (this.tranType.equalsIgnoreCase("1")) {
                if (this.selectCrDr.equalsIgnoreCase("0")) {
                    if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        this.setBy("3");
                        if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 4) {
                            throw new ApplicationException("You can not issue the Pay Order from clearing.");
                        } else if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 99) {
                            poddFlag = "false";
                        }
                    }
                } else if (this.selectCrDr.equalsIgnoreCase("1")) {
                    if (AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.TERM_LOAN)
                            || AcNature.equals(CbsConstant.RECURRING_AC)) {
                        this.setBy("1");
                        this.setRelatedTo("6");
                    } else if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        this.setBy("3");
                        if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 4) {
                            poddFlag = "true";
                            poddOnLoad();
                        } else if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 99) {
                            poddFlag = "false";
                        }

                    } else {
                        this.setBy("1");
                        this.setRelatedTo("0");
                    }
                }
            }
            if (this.tranType.equalsIgnoreCase("2")) {
                try {
                    boolean poIssueBatch = false;
                    boolean poPaidBatch = false;

                    if (msgFlag != 4) {
                        for (TxnDetailBean obj : cashPOTxnBeanList) {
                            if (txnRemote.getMsgFlag(obj.getAcNo()) == 4) {
                                if (obj.getTy().equals("0")) {
                                    poIssueBatch = true;
                                    break;
                                } else {
                                    poPaidBatch = true;
                                    break;
                                }
                            }
                        }
                        //To Do Change in the following condition (in case of NCCBL (For Party account single debit))
                        if (poMultipleEntry && (poIssueBatch || poPaidBatch)) {
                            setAmountTxn(totalPoAmt.toString());
                        }
                    }
                    if (this.selectCrDr.equalsIgnoreCase("0")) {
                        if (poMultipleEntry && msgFlag != 4) {
                            if (poIssueBatch) {
                                throw new ApplicationException("Normal credit entry does not allow in PO/DD Issue Batch");
                            }
                            if (poPaidBatch) {
                                for (TxnDetailBean obj : cashPOTxnBeanList) {
                                    if (obj.getTy().equals("0")) {
                                        throw new ApplicationException("Only one credit entry is allow in PO/DD Payment/Cancellation Batch");
                                    }
                                }
                            }
                        }
                        if (AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.TERM_LOAN)
                                || AcNature.equals(CbsConstant.RECURRING_AC)) {
                            this.setBy("3");
                            this.setRelatedTo("1");
                        } else if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            this.setBy("3");
                            if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 4) {
                                for (TxnDetailBean obj : cashPOTxnBeanList) {
                                    if (!ftsPostRemote.getAccountNature(obj.getAcNo()).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                                        throw new ApplicationException("If you want to Isuue PO/DD then Please do the Credit first in PO/DD Head.");
                                    } else if (poMultipleEntry && obj.getTy().equals("1")) {
                                        throw new ApplicationException("In a single transfer batch you can make only payment/cancel of PO/DD");
                                    }
                                }
                                poddFlag = "true";
                                // poAccnoFlag = "";
                                poddOnLoad();
                            } else if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 99) {
                                poddFlag = "false";
                            }
                        } else {
                            this.setBy("3");
                            this.setRelatedTo("0");
                        }
                    } else if (this.selectCrDr.equalsIgnoreCase("1")) {
                        if (poMultipleEntry && msgFlag != 4) {
                            if (poPaidBatch) {
                                throw new ApplicationException("Normal debit entry does not allow in PO/DD Payment/Cancellation Batch");
                            }
                            if (poIssueBatch) {
                                for (TxnDetailBean obj : cashPOTxnBeanList) {
                                    if (obj.getTy().equals("1")) {
                                        throw new ApplicationException("Only one manual debit entry is allow in PO/DD Issue Batch");
                                    }
                                }
                            }
                        }
                        if (AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.TERM_LOAN)) {
                            this.setBy("2");
                            this.setRelatedTo("6");
                        } else if (AcNature.equals(CbsConstant.RECURRING_AC)) {
                            this.setBy("2");
                            this.setRelatedTo("0");
                        } else if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            this.setBy("3");
                            if (msgFlag == 5) {
                                setDisplayNPA("");
                                setRelatedTo("103");
                                setFocusId("stxtNpa");
                            } else {
                                setDisplayNPA("none");
                                setFocusId("ddRelated");
                            }
                            if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 4) {
                                for (TxnDetailBean obj : cashPOTxnBeanList) {
                                    if (!ftsPostRemote.getAccountNature(obj.getAcNo()).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                                        throw new ApplicationException("If you want to Isuue PO/DD then Please do the Credit first in PO/DD Head.");
                                    } else if (poMultipleEntry && obj.getTy().equals("0")) {
                                        throw new ApplicationException("In a single transfer batch you can only issue PO/DD");
                                    }
                                }
                                poddFlag = "true";
                                poddOnLoad();
                            } else if ((TmpPostFlag == 2 || TmpPostFlag == 10) && msgFlag == 99) {
                                poddFlag = "false";
                            }
                        } else {
                            this.setBy("2");
                            this.setRelatedTo("0");
                        }
                    }
                } catch (Exception e) {
                    resetFormExcludeXferGrid();
                    this.setErrorMsg(e.getMessage());
                }
            }
        } catch (Exception e) {
            resetForm();
            this.setErrorMsg(e.getMessage());
        }
    }

    public void cmbByLostFocus() {
        Date date = new Date();
        try {
            if (this.by.equals("1") || this.by.equals("4")) {
                this.setChqueDate(date);
            } else {
                this.setChqueDate(null);
            }

            if (this.by.equalsIgnoreCase("1")) {
                PaymentBy = 1;
                chqNoFlag = "false";
                chqDateFlag = "false";
            } else if (this.by.equalsIgnoreCase("2")) {
                PaymentBy = 2;
                chqNoFlag = "true";
                this.setChqNo("");
                chqDateFlag = "true";
            } else if (this.by.equalsIgnoreCase("4")) {
                PaymentBy = 4;
                chqNoFlag = "false";
                chqDateFlag = "false";
            } else {
                PaymentBy = 3;
                this.setChqNo("");
                chqNoFlag = "true";
                chqDateFlag = "true";
            }
            if (gCashMod.equals("Y") && this.tranType.equalsIgnoreCase("0") && this.selectCrDr.equalsIgnoreCase("1")) {
                tokenFocus = "true";
            } else {
                tokenFocus = "false";
                if (PaymentBy == 1 || PaymentBy == 4) {
                    if (chqDateFlag.equalsIgnoreCase("false")) {
                        chqFocus = "true";
                    }
                } else if (this.relatedTo.equalsIgnoreCase("10") && this.tranType.equalsIgnoreCase("0") && this.selectCrDr.equalsIgnoreCase("1")) {
                    tokenFocus = "true";
                } else if (this.selectCrDr.equalsIgnoreCase("1") && (PaymentBy == 2 || PaymentBy == 3) && TrnType == 0 && gCashMod.equals("Y")) {// && gCashMod = "Y"
                    chqFocus = "true";
                } else {
                    amountFocus = "true";
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    /**
     * *****************************Method For
     * F4***********************************
     */
    public void acctViewAuthUnAuth() {
        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();
            if (this.accNo.equals("") || this.accNo == null) {
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String dt = CbsUtil.monthAdd(ymd.format(sdf.parse(getTodayDate())), -1);
            String oneMonthBeforeDate = sdf.format(ymd.parse(dt));

            String openBal = txnRemote.getOpeningBalF4(accNo, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {
                this.setOpenBalance(new BigDecimal(openBal));
            }
            String prsntBal = txnRemote.getOpeningBalF4(accNo, getTodayDate());
            if (!prsntBal.equals("FALSE")) {
                this.setPresentBalance(new BigDecimal(prsntBal));
            }
            txnDetailList = txnRemote.accViewAuth(accNo, getTodayDate(), getOrgnBrCode());

            txnDetailUnAuthList = txnRemote.accViewUnauth(accNo, getTodayDate(), getOrgnBrCode());

        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void cmdSaveCashClick() {
        try {

//            if(txnCashParam == 1){
//                if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) && this.getTranType().equalsIgnoreCase("0") && this.selectCrDr.equalsIgnoreCase("0") && msgFlag == 4) {
//                    throw new ApplicationException("Pay order can not be issued from cash for the time being ");
//                }
//            }
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            panRepFlag = "false";
            balConfirm = "false";
            ccodMsg = "";
            this.setAccNoMsg("");

            ValidationFlag = false;
            if (Validation() == true) {
                return;
            }
            if (accountStatus.equalsIgnoreCase("2")) {
                throw new ApplicationException("Transaction is Not Allowed in Inoperative Accout.");
            }
            if (tranType.equalsIgnoreCase("0") && cashMode.equalsIgnoreCase("N") && (txnCashParam == 1)) {
                if (cashDenominationObj.getDenominationTable().isEmpty()) {
                    throw new ApplicationException("Please Fill Denomination Detail With F8 Option.");
                }
                if (Double.parseDouble(cashDenominationObj.getCurrencyAmount()) != Double.parseDouble(amountTxn)) {
                    throw new ApplicationException("Total Denomination Currency amount is differ from Txn Amount. Please fill correct Denomination Details with F8 Option.");
                }

                // Refresh Grid In Case Of Cr/Dr Chage After Filling Denomination and Press Save Button Directly
                if (!(cashDenominationObj.getTyDenoValue().equalsIgnoreCase(this.getSelectCrDr()))) {
                    cashDenominationObj.setDenominationTable(new ArrayList<DDSDenominationGrid>());
                    cashDenominationObj.setDenoNo("");
                    cashDenominationObj.setDenoValue("");
                    cashDenominationObj.setErrorMessage1("");
                    cashDenominationObj.setDisableDenoNo(false);
                    cashDenominationObj.setTyDenoValue(" ");
                    cashDenominationObj.setCurrencyAmount("0.00");
                    cashDenominationObj.setAcDenoNat("");
                    cashDenominationObj.setTyFlg("0");
                    cashDenominationObj.setTyCaption("Recived/Return :");
                    throw new ApplicationException("Please Fill Denomination Detail With F8 Option.");
                }
            }

            if (this.accNo == null) {
                throw new ApplicationException("Please fill the A/C No.");
            }
            if (this.accNo.equals("") || this.accNo.length() == 0) {
                throw new ApplicationException("Please fill the A/C No.");
            }
            String tmpMsg = ftsPostRemote.getGlTranInfo(accNo, Integer.parseInt(this.selectCrDr), Double.parseDouble(this.amountTxn));
            if (!tmpMsg.equalsIgnoreCase("True")) {
                throw new ApplicationException(tmpMsg);
            }
            String acOrgnCode = ftsPostRemote.getCurrentBrnCode(accNo);
            if (this.tranType.equals("1") && !getOrgnBrCode().equals(acOrgnCode) && this.selectCrDr.equals("0")) {
                throw new ApplicationException("Clearing is not allowed for other branches!");
            }
            tmpMsg = checkForInterBranchTxn();
            if (!tmpMsg.equalsIgnoreCase("true")) {
                throw new ApplicationException(tmpMsg);
            }
            if (rdInstallmentNotAllowedByOtherMode().equalsIgnoreCase("false")) {
                throw new ApplicationException("Credit is allowed by INSTALLMENT and INTEREST in RD!");
            }
            tmpMsg = checkOfficeAccount();
            if (!tmpMsg.equalsIgnoreCase("true")) {
                throw new ApplicationException(tmpMsg);
            }
            if (!checkDuplicatePayOrderPayment(this.accNo, this.selectCrDr, this.getPoSeqNo()).equalsIgnoreCase("true")) {
                throw new ApplicationException("There is already a payment entry for this Pay Order / Demand Draft");
            }

            if (this.selectCrDr.equalsIgnoreCase("1") && this.by.equalsIgnoreCase("1") && this.chqueDate != null
                    && !validateInstNoDtAgainstOpenDt(chqueDate).equalsIgnoreCase("true")) {
                throw new ApplicationException("Cheque date should not be less than account opening date !");
            }

            String chMsg = valMemDtl();
            if (!chMsg.equalsIgnoreCase("true")) {
                throw new ApplicationException(chMsg);
            }

            if (!AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                String chKyc = txnRemote.kycExpCheck(accNo, Tempbd);
                if (!chKyc.equalsIgnoreCase("true")) {
                    if (selectCrDr.equalsIgnoreCase("0") && tranType.equals("0")) {
                        String lmtAmt = ftsPostRemote.getCodeFromCbsParameterInfo("KYC_LIMIT_AMT");
                        if (Double.parseDouble(amountTxn.toString()) > Double.parseDouble(lmtAmt)) {
                            throw new ApplicationException("Please Update KYC To Deposit More Than " + lmtAmt);
                        }
                    }
                }

                if (selectCrDr.equalsIgnoreCase("1") && tranType.equals("0")) {
                    /*
                     * BLACK money transaction stop 20161109
                     */
                    String blackMoneyParam = ftsPostRemote.getCodeFromCbsParameterInfo("CASH_TRN_RESTRI");
                    /*0=OFF; 1 = ON
                     */
                    if (blackMoneyParam.equalsIgnoreCase("1")) {
                        String restriDate = ftsPostRemote.getCodeFromCbsParameterInfo("CASH_RESTRI_START_DATE");
                        String cashPerDayTrn = ftsPostRemote.getCodeFromCbsParameterInfo("CASH_PER_DAY_TRAN");
                        String maxNoOfDays = ftsPostRemote.getCodeFromCbsParameterInfo("CASH_MAX_NO_OF_DAYS");
                        String cashMaxTranWeek = ftsPostRemote.getCodeFromCbsParameterInfo("CASH_MAX_TRAN_WEEK");

                        String caOpenPrd = ftsPostRemote.getCodeFromCbsParameterInfo("CA_OPEN_PERIOD_MONTH");
                        String caAtLeastDep = ftsPostRemote.getCodeFromCbsParameterInfo("CA_AT_LEAST_DEP");
                        String caAtLeastWth = ftsPostRemote.getCodeFromCbsParameterInfo("CA_AT_LEAST_WTH");
                        String caCashPerDayTran = ftsPostRemote.getCodeFromCbsParameterInfo("CA_CASH_PER_DAY_TRAN");
                        String caCashMaxTran = ftsPostRemote.getCodeFromCbsParameterInfo("CA_CASH_MAX_TRAN_WEEK");
                        String caCashMaxNoOfDays = ftsPostRemote.getCodeFromCbsParameterInfo("CA_CASH_MAX_NO_OF_DAYS");
                        //String caDivsible = ftsPostRemote.getCodeFromCbsParameterInfo("CA_DIVISIBLE");
                        String depositIncDate = ftsPostRemote.getCodeFromCbsParameterInfo("DEP_INC_DATE");

                        // Start Deposit Circular RBI/2016-17/163 DCM.No.1437/10.27.00/2016-17
                        String depCirCr = "0", depCirDr = "0", depAdd = "0";
                        List depCirLst = ftsPostRemote.getTotDebitAndCreditAfterCir(accNo, depositIncDate);
                        if (!depCirLst.isEmpty()) {
                            Vector depCirVector = (Vector) depCirLst.get(0);
                            depCirCr = depCirVector.get(0).toString();
                            depCirDr = depCirVector.get(1).toString();
                        }

                        cashMaxTranWeek = Double.toString(Double.parseDouble(cashMaxTranWeek) + Double.parseDouble(depCirCr));
                        caCashMaxTran = Double.toString(Double.parseDouble(caCashMaxTran) + Double.parseDouble(depCirCr));
                        // End Deposit Circular RBI/2016-17/163 DCM.No.1437/10.27.00/2016-17

                        if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            long dayDiff = CbsUtil.dayDiff(ymd.parse(restriDate), ymd.parse(Tempbd)) + 1;
                            Double caNoOfWeek = dayDiff / Double.parseDouble(caCashMaxNoOfDays);
                            if (caNoOfWeek > 1) {
                                if ((caNoOfWeek - caNoOfWeek.intValue()) > 0) {
                                    restriDate = CbsUtil.dateAdd(restriDate, (caNoOfWeek.intValue() * Integer.parseInt(caCashMaxNoOfDays)));
                                } else {
                                    restriDate = CbsUtil.dateAdd(restriDate, (((caNoOfWeek.intValue() - 1) * Integer.parseInt(caCashMaxNoOfDays))));
                                }
                            }
                            double caTranAmtBetDate = ftsPostRemote.getDebitAmtByAcno(accNo, restriDate, Tempbd);
                            /*WEEKLY CHECKING*/
                            if (caTranAmtBetDate + Double.parseDouble(amountTxn) <= Double.parseDouble(caCashMaxTran)) {
                                double caSameDateCashDrAmt = ftsPostRemote.getDebitAmtByAcno(accNo, Tempbd, Tempbd);
                                if (caSameDateCashDrAmt + Double.parseDouble(amountTxn) > Double.parseDouble(caCashPerDayTran)) {
                                    throw new ApplicationException("Debit amount should not greater than same days cash limit.");
                                } else {
                                    String acDt = openDate.substring(6) + openDate.substring(3, 5) + openDate.substring(0, 2);
                                    long datediff = CbsUtil.monthDiff(ymd.parse(acDt), ymd.parse(Tempbd));
                                    if (datediff < Long.parseLong(caOpenPrd)) {
                                        double tranAmtBetDate = ftsPostRemote.getDebitAmtByAcno(accNo, restriDate, Tempbd);
                                        /*WEEKLY CHECKING*/
                                        if (tranAmtBetDate + Double.parseDouble(amountTxn) <= Double.parseDouble(cashMaxTranWeek)) {
                                            double sameDateCashDrAmt = ftsPostRemote.getDebitAmtByAcno(accNo, Tempbd, Tempbd);
                                            if (sameDateCashDrAmt + Double.parseDouble(amountTxn) > Double.parseDouble(cashPerDayTrn)) {
                                                throw new ApplicationException("Debit amount should not greater than same days cash limit.");
                                            }
                                        } else {
                                            throw new ApplicationException("Debit amount should not greater than " + maxNoOfDays + " days cash limit.");
                                        }
                                    } else {
                                        String drCount = "0", crCount = "0";
                                        List tranNoList = ftsPostRemote.getTotDebitAndCreditTran(accNo, CbsUtil.monthAdd(Tempbd, Integer.parseInt(caOpenPrd) * -1));
                                        if (!tranNoList.isEmpty()) {
                                            Vector countVector = (Vector) tranNoList.get(0);
                                            crCount = countVector.get(0).toString();
                                            drCount = countVector.get(1).toString();
                                            if (!((Integer.parseInt(crCount) >= Integer.parseInt(caAtLeastDep))
                                                    || (Integer.parseInt(drCount) >= Integer.parseInt(caAtLeastWth)))) {
                                                double tranAmtBetDate = ftsPostRemote.getDebitAmtByAcno(accNo, restriDate, Tempbd);
                                                /*WEEKLY CHECKING*/
                                                if (tranAmtBetDate + Double.parseDouble(amountTxn) <= Double.parseDouble(cashMaxTranWeek)) {
                                                    double sameDateCashDrAmt = ftsPostRemote.getDebitAmtByAcno(accNo, Tempbd, Tempbd);
                                                    if (sameDateCashDrAmt + Double.parseDouble(amountTxn) > Double.parseDouble(cashPerDayTrn)) {
                                                        throw new ApplicationException("Debit amount should not greater than same days cash limit.");
                                                    }
                                                } else {
                                                    throw new ApplicationException("At least " + caAtLeastWth + " Debit / " + caAtLeastDep + " Credit required for operational account");
                                                }
                                            }
//                                            else{
//                                                if(!(Double.parseDouble(amountTxn) % Double.parseDouble(caDivsible)==0)){
//                                                    throw new ApplicationException("Amount can be withdraw only in divisible of " + caDivsible);
//                                                }
//                                            }
                                        }
                                    }
                                }
                            } else {
                                throw new ApplicationException("Debit amount should not greater than " + caCashMaxNoOfDays + " days cash limit.");
                            }
                        } else {
                            long dayDiff = CbsUtil.dayDiff(ymd.parse(restriDate), ymd.parse(Tempbd)) + 1;
                            Double noOfWeek = dayDiff / Double.parseDouble(maxNoOfDays);
                            if (noOfWeek > 1) {
                                if ((noOfWeek - noOfWeek.intValue()) > 0) {
                                    restriDate = CbsUtil.dateAdd(restriDate, (noOfWeek.intValue() * Integer.parseInt(maxNoOfDays)));
                                } else {
                                    restriDate = CbsUtil.dateAdd(restriDate, (((noOfWeek.intValue() - 1) * Integer.parseInt(maxNoOfDays))));
                                }
                            }

                            double tranAmtBetDate = ftsPostRemote.getDebitAmtByAcno(accNo, restriDate, Tempbd);
                            /*WEEKLY CHECKING*/
                            if (tranAmtBetDate + Double.parseDouble(amountTxn) <= Double.parseDouble(cashMaxTranWeek)) {
                                double sameDateCashDrAmt = ftsPostRemote.getDebitAmtByAcno(accNo, Tempbd, Tempbd);
                                if (sameDateCashDrAmt + Double.parseDouble(amountTxn) > Double.parseDouble(cashPerDayTrn)) {
                                    throw new ApplicationException("Debit amount should not greater than same days cash limit.");
                                }
                            } else {
                                throw new ApplicationException("Debit amount should not greater than " + maxNoOfDays + " days cash limit.");
                            }
                        }
                    }
                }
            }

            /**
             * * Small Account Checking.
             */
            if ((this.tranType.equals("0") || this.tranType.equals("2")) && this.selectCrDr.equals("0")) {
                List paramList = ftsPostRemote.getBaseParameter(ftsPostRemote.getAccountCode(accNo));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    BigDecimal maxDepInFinYear = new BigDecimal(paramVector.get(1).toString());
                    BigDecimal balAtAnyTime = new BigDecimal(paramVector.get(3).toString());

                    paramList = ftsPostRemote.getCurrentFinYear(getOrgnBrCode());
                    paramVector = (Vector) paramList.get(0);
                    String minFinDt = paramVector.get(0).toString();

                    BigDecimal totalFinYearDeposit = txnRemote.getFinYearDepositAsWellAsMonthWithdrawalTillDate(accNo, minFinDt);
                    BigDecimal totalFinYearPlusCrAmt = totalFinYearDeposit.add(new BigDecimal(this.amountTxn));
                    if (totalFinYearPlusCrAmt.compareTo(maxDepInFinYear) == 1) {
                        throw new ApplicationException("You can not deposit more than financial year limit. Remaining deposit limit is " + maxDepInFinYear.subtract(totalFinYearDeposit) + " only");
                    }
                    BigDecimal actualBalance = new BigDecimal(ftsPostRemote.ftsGetBal(accNo));
                    BigDecimal actualBalPlusCrAmt = actualBalance.add(new BigDecimal(this.amountTxn));
                    if (actualBalPlusCrAmt.compareTo(balAtAnyTime) == 1) {
                        throw new ApplicationException("You can not deposit more than balance limit. Remaining deposit balance limit is " + balAtAnyTime.subtract(actualBalance) + " only");
                    }
                }
            }
            if ((this.tranType.equals("0") || this.tranType.equals("2")) && this.selectCrDr.equals("1")) {
                List paramList = ftsPostRemote.getBaseParameter(ftsPostRemote.getAccountCode(accNo));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    BigDecimal cashTrfDrInMonth = new BigDecimal(paramVector.get(2).toString());

                    BigDecimal totalMonthWithdrawal = txnRemote.getFinYearDepositAsWellAsMonthWithdrawalTillDate(accNo, "");
                    BigDecimal totalMonthWithPlusDrAmt = totalMonthWithdrawal.add(new BigDecimal(this.amountTxn));
                    if (totalMonthWithPlusDrAmt.compareTo(cashTrfDrInMonth) == 1) {
                        throw new ApplicationException("You can not withdrawal more than month limit. Remaining withdrawal limit is " + cashTrfDrInMonth.subtract(totalMonthWithdrawal) + " only");
                    }
                }
                /*Checking to stop for Lien DDS & RD Account */
                if ((AcNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || AcNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) && AcFlag == 10) {
                    throw new ApplicationException("This account is Lein Marked so you can not debit this acoount.");
                }

                if (AcNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    List isPendingAuthList = ftsPostRemote.isVerifyPending(accNo);
                    if (!isPendingAuthList.isEmpty()) {
                        throw new ApplicationException("This account Verification is Pending..");
                    }
                }
            }
            /**
             * Checking of Remote Branch Cash Transaction
             */
            if (this.tranType.equals("0")) {
                int code = txnRemote.getRemoteCashLimitFlag();
                if (code == 1) {
                    if (!acOrgnCode.equalsIgnoreCase(getOrgnBrCode())) {
                        List limitList = txnRemote.getRemoteTranLimits(ftsPostRemote.getAccountCode(accNo));
                        if (!limitList.isEmpty()) {
                            Vector limitElement = (Vector) limitList.get(0);
                            BigDecimal crLimit = new BigDecimal(limitElement.get(1).toString());
                            BigDecimal drLimit = new BigDecimal(limitElement.get(2).toString());
                            if (this.selectCrDr.equals("0")) {
                                BigDecimal totalCr = txnRemote.getTotalCashTranAmtOnCurrentDt(accNo, acOrgnCode, "C");

                                BigDecimal totalCrAmt = totalCr.add(new BigDecimal(this.amountTxn));

                                if (totalCrAmt.compareTo(crLimit) == 1) {
                                    throw new ApplicationException("You can not deposit more than remote branch limit. Remaining deposit limit is " + crLimit.subtract(totalCr) + " only");
                                }
                            } else if (this.selectCrDr.equals("1")) {
                                if (!txnRemote.isWSAllow(accNo) && !this.by.equalsIgnoreCase("1")) {
                                    throw new ApplicationException("Cash Withdrawal allow only through Cheque. So please select proper By option.");
                                }
                                BigDecimal totalDr = txnRemote.getTotalCashTranAmtOnCurrentDt(accNo, acOrgnCode, "D");

                                BigDecimal totalDrAmt = totalDr.add(new BigDecimal(this.amountTxn));

                                if (totalDrAmt.compareTo(drLimit) == 1) {
                                    throw new ApplicationException("You can not withdrawal more than remote branch limit. Remaining withdrawal limit is " + drLimit.subtract(totalDr) + " only");
                                }
                            }
                        }
                    }
                }
            }
            // ValidationFlag = false;
            String msg = ftsPostRemote.ftsDateValidate(Tempbd, getOrgnBrCode());
            if (!msg.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException(msg);
            }
//            if (Validation() == true) {
//                return;
//            }
            if (ccodExpiryFlag == true) {
                if (this.selectCrDr.equals("1") && (this.accNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())
                        || this.accNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode()))) {
                    int ccodiy = txnRemote.ccodStockExp(accNo);
                    if (ccodiy == 95) {
                        ccodMsg = "Security Expired.";
                        this.setAccNoMsg("Security Expired.");
                    } else if (ccodiy == 96) {
                        ccodMsg = "Insurance Due.";
                        this.setAccNoMsg("Insurance Due.");
                    } else if (ccodiy == 97) {
                        ccodMsg = "Sanctioned Date Expired.";
                        this.setAccNoMsg("Sanctioned Date Expired.");
                    }
                }
            }
            /**
             * Code Added For ThreshHold Limit Checking *
             */
            if ((AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || AcNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && this.selectCrDr.equals("0")) {
                int thLmtParam = 0;
                List chParamThresh = ftsPostRemote.getThreshLmtParam();
                if (!(chParamThresh == null || chParamThresh.isEmpty())) {
                    Vector verLst = (Vector) chParamThresh.get(0);
                    thLmtParam = Integer.parseInt(verLst.get(0).toString());
                }
                if (thLmtParam == 1) {
                    String chkThresh = ftsPostRemote.isThreshLmtExceed(accNo, Double.parseDouble(this.getAmountTxn().toString()), Tempbd);
                    if (!chkThresh.equalsIgnoreCase("true")) {
                        throw new ApplicationException(chkThresh);
                    }
                }
            }
            if (AcNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && (this.relatedTo.equals("1") || this.relatedTo.equals("9"))) {
                if (checkRDInstall() == false) {
                    amountTxnFocus = "true";
                    return;
                }
                this.setErrorMsg("");
            }
            if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && this.selectCrDr.equals("1")) {
                txnRemote.checkStockStatementExpiry(accNo, Tempbd);
            }

            if ((AcNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || AcNature.equalsIgnoreCase(CbsConstant.MS_AC)) && this.selectCrDr.equals("1")) {
                double tdReconBal = 0;
                List saveList6 = txnRemote.selectFromTDRecon(accNo, Tempbd);
                if (!saveList6.isEmpty()) {
                    Vector v6 = (Vector) saveList6.get(0);
                    tdReconBal = Double.parseDouble(v6.get(0).toString());
                }
                if ((tdReconBal - Double.parseDouble(this.amountTxn.toString())) < 0) {
                    throw new ApplicationException("Please Check the Statement Balance.");
                }
            }
            if ((AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || AcNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) && this.selectCrDr.equals("1") && this.relatedTo.equals("6")) {
                if (loanDisbCheck(this.balance.doubleValue()) == false) {
                    throw new ApplicationException("Disburse Amount Can't be Excess to Sanctioned Amount");
                }
            }
            if (gCashMod.equalsIgnoreCase("Y") && this.tranType.equals("0")) {
                txnRemote.checkCashClose(getOrgnBrCode(), Tempbd);
            }

            if (Integer.parseInt(relatedTo) == 8) {
                this.setRelatedTo(getChargeDtl());
            }
            if ((AcNature.equals(CbsConstant.TERM_LOAN) || AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.CURRENT_AC)) && Integer.parseInt(getRelatedTo()) == 10) {
                List npaList = txnRemote.checkForNPA(AcNature, accNo);
                if (npaList.isEmpty()) {
                    throw new ApplicationException("Before NPA Status Marked Write-Off is not Allowed");
                }
            }
            if ((AcNature.equals(CbsConstant.TERM_LOAN) || AcNature.equals(CbsConstant.DEMAND_LOAN) || AcNature.equals(CbsConstant.CURRENT_AC)) && this.selectCrDr.equals("1")
                    && (accountStatus.equals("11") || accountStatus.equals("12") || accountStatus.equals("13") || accountStatus.equals("14")) && !(relatedTo.equals("102") && tranType.equals("2"))) {
                throw new ApplicationException("Debit Transaction does not allow in NPA Accounts");
            }
            this.setMessage("");
            if (!AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (this.tranType.equals("0") && this.selectCrDr.equals("0")) {
                    this.setPanConfirmMsg("");
                    double tmpPanAmt = 0;
                    List saveList1 = txnRemote.selectFromToknCrReconCash(accNo, Tempbd);
                    if (!saveList1.isEmpty()) {
                        for (int i = 0; i < saveList1.size(); i++) {
                            Vector saveVec1 = (Vector) saveList1.get(i);
                            tmpPanAmt = tmpPanAmt + Double.parseDouble(saveVec1.get(0).toString());
                        }
                    }
                    tmpPanAmt = tmpPanAmt + Double.parseDouble(amountTxn.toString());  // amountTxn.doubleValue()
                    if (tmpPanAmt >= Double.parseDouble(panamount)) {
                        String panNumber = "";
                        String tableName = tableNameForPanamount(AcNature);
                        List saveList2 = txnRemote.selectPanNo(tableName, accNo);

                        if (!saveList2.isEmpty()) {
                            Vector saveVec2 = (Vector) saveList2.get(0);
                            panNumber = saveVec2.get(0).toString();

                            if (!(panNumber == null || panNumber.equals(""))) {
                                this.setPanConfirmMsg("A/c PAN Number is :" + panNumber + " . Are you sure ?");
                                panRepFlag = "true";
                                return;
                            }
                        }
                        if (panNumber.equals("") || panNumber.length() < 10) {
                            this.setPanConfirmMsg("PAN number does not exist. Do you want to continue ?");
                            panRepFlag = "true";
                            return;
                        }
                    }
                }
                if (this.selectCrDr.equals("1")) {
                    String chkBalResult = ftsPostRemote.chkBal(this.getAccNo(), Integer.parseInt(this.selectCrDr), Integer.parseInt(this.relatedTo), AcNature);
                    if (!chkBalResult.equalsIgnoreCase("TRUE")) {
                        String chkBalance = ftsPostRemote.checkBalForOdLimit(accNo, Double.parseDouble(this.getAmountTxn().toString()), getUserName());
                        if (getOrgnBrCode().equals(acOrgnCode) && chkBalance.equalsIgnoreCase("99")) {
                            balConfirmMsg = "Limit Exceed for : " + accNo + ". \n To Proceed press Yes, \n To Cancel press No";
                            balConfirm = "true";
                            return;
                        } else if (!chkBalance.equalsIgnoreCase("1")) {
                            throw new ApplicationException("Balance Exceeds.");
                        }
                    }
                }
                //To stop the RD account credit through installment if maturity date has been expired.
                if (AcNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && this.selectCrDr.equalsIgnoreCase("0") && this.relatedTo.equals("1")) {
                    List list = ftsPostRemote.getAccountDetailFromAccountMaster(accNo);
                    if (list.isEmpty()) {
                        throw new ApplicationException("There is no data for this A/c no.");
                    }
                    Vector detailVec = (Vector) list.get(0);
                    if (ymd.parse(detailVec.get(0).toString()).compareTo(ymd.parse(ymd.format(new Date()))) < 0) {
                        throw new ApplicationException("This A/c's maturity date has been expired.");
                    }
                }
                //End Here
            }

            if (msgFlag == 4) {
                savePoTxnDetails();
            } else {
                saveCashDetail();
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.setErrorMsg(e.getMessage());
        }
    }

    public String checkForInterBranchTxn() {
        try {
            List list = txnRemote.checkLoanAuthorization(accNo);

            if (AcNature.equals(CbsConstant.SAVING_AC)) {
                if (curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("0") || this.selectCrDr.equals("1"))) {
                    return "true";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("0") || this.selectCrDr.equals("1"))) {
                    return "true";
                }
            } else if (this.accNo.substring(2, 4).equals(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                if (!list.isEmpty()) {
                    return "This A/C Is Unauthorized. Transaction Is Not Allowed";
                }
                if (curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("0") || this.selectCrDr.equals("1"))) {
                    return "true";
                }
            } else if (this.accNo.substring(2, 4).equals(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())
                    || this.accNo.substring(2, 4).equals(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())
                    || this.accNo.substring(2, 4).equals(CbsAcCodeConstant.KISAN_CREDIT_AC.getAcctCode())) {
                if (!list.isEmpty()) {
                    return "This A/C Is Unauthorized. Transaction Is Not Allowed";
                }
                if (curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("0"))) {
                    return "true";
                } else if (curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("1"))) {
                    if (!(this.relatedTo.equals("3")) && !(this.relatedTo.equals("4")) && !(this.relatedTo.equals("5")) && !(this.relatedTo.equals("7")) && !(this.relatedTo.equals("8")) && !(this.relatedTo.equals("21")) && !(this.relatedTo.equals("22")) && !(this.relatedTo.equals("23")) && !(this.relatedTo.equals("24")) && !(this.relatedTo.equals("25")) && !(this.relatedTo.equals("26")) && !(this.relatedTo.equals("27")) && !(this.relatedTo.equals("28")) && !(this.relatedTo.equals("29")) && !(this.relatedTo.equals("30")) && !(this.relatedTo.equals("31")) && !(this.relatedTo.equals("32"))) {
                        return "true";
                    }

                } else if (!curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("0"))) {
                    return "true";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("1"))) {
                    if (!(this.relatedTo.equals("3")) && !(this.relatedTo.equals("4")) && !(this.relatedTo.equals("5")) && !(this.relatedTo.equals("7")) && !(this.relatedTo.equals("8")) && !(this.relatedTo.equals("21")) && !(this.relatedTo.equals("22")) && !(this.relatedTo.equals("23")) && !(this.relatedTo.equals("24")) && !(this.relatedTo.equals("25")) && !(this.relatedTo.equals("26")) && !(this.relatedTo.equals("27")) && !(this.relatedTo.equals("28")) && !(this.relatedTo.equals("29")) && !(this.relatedTo.equals("30")) && !(this.relatedTo.equals("31")) && !(this.relatedTo.equals("32"))) {
                        return "true";
                    }
                }
            } else if (AcNature.equals(CbsConstant.RECURRING_AC) || AcNature.equals(CbsConstant.DEPOSIT_SC)) {
                if (curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("0")) {
                    return "true";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("0")) {
                    return "true";
                } else if (curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("1")) {
                    return "true";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("1")) {
                    return "Only Credit is allowed for other branch RD accounts.";
                }
            } else if (AcNature.equals(CbsConstant.DEMAND_LOAN)
                    || AcNature.equals(CbsConstant.TERM_LOAN)) {
                if (!list.isEmpty()) {
                    return "This A/C Is Unauthorized. Transaction Is Not Allowed";
                }
                if (curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("0")) {
                    return "true";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("0")) {
                    return "true";
                } else if (curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("1")) {
                    if (this.tranType.equals("0")) {
                        return "Sorry!! Debit Transaction Is Not Allowed For This Account";
                    } else {
                        return "true";
                    }
                } else if (!curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("1")) {
                    return "Only Credit is allowed for other branch DL/TL accounts.";
                }
            } else if (AcNature.equals(CbsConstant.FIXED_AC) || AcNature.equals(CbsConstant.MS_AC)) {
                if (curBrnCode.equals(getOrgnBrCode()) && (this.selectCrDr.equals("0") || this.selectCrDr.equals("1"))) {
                    return "true";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("0")) {
                    return "NO Cr/Dr is allowed for other branch FD accounts.";
                } else if (!curBrnCode.equals(getOrgnBrCode()) && this.selectCrDr.equals("1")) {
                    return "NO Cr/Dr is allowed for other branch FD accounts.";
                }
            } else if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                /*                   New Code Added For Interbranch PO     */
                if (!(ftsPostRemote.getCodeForReportName("REMOTE_GL_TXN").equals(1) && msgFlag != 4)) {
                    if (!(curBrnCode.equalsIgnoreCase(this.getOrgnBrCode())) && !(ftsPostRemote.getOfficeAccountNo(accNo).equalsIgnoreCase("true")
                            || ftsPostRemote.isATMHead(accNo, getOrgnBrCode()).equalsIgnoreCase("true"))) {
                        return "Txn is not allowed for other branch's GL A/C.";
                    }
                }
                /*             End Of New Code Added For Interbranch PO    */
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return "true";
    }

    public void saveCashDetail() {
        try {
            List<TxnDetailBean> cashTxnList = new ArrayList<TxnDetailBean>();
            String cashSaveResult;

            if (this.tranType.equals("0") && this.selectCrDr.equals("0")) {
                TxnDetailBean txnCashBean = createTxnBeanObj();
                txnCashBean.setAcNo(this.getAccNo());
                txnCashBean.setTranType(this.getTranType());
                txnCashBean.setTy("0");

                txnCashBean.setAmount(Double.parseDouble(amountTxn.toString()));
                txnCashBean.setDetails(this.getDetails());
                txnCashBean.setPayBy(this.getBy());

                String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
                txnCashBean.setValueDate(valDt);
                float recNo = ftsPostRemote.getRecNo();
                txnCashBean.setRecNo(recNo);
                txnCashBean.setAlertSubCode(this.alertSubCode);
                cashTxnList.add(txnCashBean);
                cashSaveResult = txnRemote.saveTxnDetails(cashTxnList, Sundrytable, cashDenominationObj.getDenominationTable(),AcNature,getCustId(),getCustPanNo(),acctCategory);
                if (cashSaveResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    double amtMsg = Double.parseDouble(amountTxn.toString());
                    resetFormExcludeAccNo();
                    if (!ccodMsg.equals("")) {
                        this.setAccNoMsg(ccodMsg);
                    }
                    this.setMessage("Generated Voucher No. is:-   " + (long) Float.parseFloat(cashSaveResult.substring(4)) + ",  Last Posted A/C is:-   " + this.getAccNo() + "    Credit Amount is : " + amtMsg);
                } else {
                    if (!ccodMsg.equals("")) {
                        this.setAccNoMsg(ccodMsg);
                    }
                    this.setErrorMsg(cashSaveResult);
                }
            }

            if (this.tranType.equals("0") && this.selectCrDr.equals("1")) {

                if (this.by.equals("1") && AcNature.equals(CbsConstant.PAY_ORDER) && !this.chqNo.equals("")) {
                    this.setErrorMsg("This Instrument No. Does Not Exist");
                    return;
                }
                if (this.by.equals("1") && this.getChqueDate() == null) {
                    this.setErrorMsg("Please fill Cheque date.");
                    return;
                }
                if (this.by.equals("4") && this.getChqueDate() == null) {
                    this.setErrorMsg("Please fill Loose Cheque date.");
                    return;
                }

                this.setErrorMsg("");
                TxnDetailBean txnCashBean = createTxnBeanObj();
                txnCashBean.setAcNo(this.getAccNo());
                txnCashBean.setTranType(this.getTranType());
                txnCashBean.setTy("1");

                txnCashBean.setAmount(Double.parseDouble(amountTxn.toString()));
                txnCashBean.setDetails(this.getDetails());
                txnCashBean.setPayBy(this.getBy());

                String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
                txnCashBean.setValueDate(valDt);
                Float recNo = ftsPostRemote.getRecNo();
                txnCashBean.setRecNo(recNo);
                txnCashBean.setAlertSubCode(this.alertSubCode);

                cashTxnList.add(txnCashBean);
                cashSaveResult = txnRemote.saveTxnDetails(cashTxnList, Sundrytable, cashDenominationObj.getDenominationTable(),AcNature,getCustId(),getCustPanNo(),acctCategory);
                if (cashSaveResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    double amtMsg = Double.parseDouble(amountTxn.toString());
                    resetFormExcludeAccNo();
                    if (!ccodMsg.equals("")) {
                        this.setAccNoMsg(ccodMsg);
                    }
                    this.setMessage("Generated Txn No. is:-   " + recNo.longValue() + ",  Last Posted A/C is:-   " + this.getAccNo() + "    Debit Amount is : " + amtMsg);
                } else {
                    if (!ccodMsg.equals("")) {
                        this.setAccNoMsg(ccodMsg);
                    }
                    this.setErrorMsg(cashSaveResult);
                }
            }
            if (this.tranType.equals("1") && this.selectCrDr.equals("0")) {
                if (!AcNature.equals(CbsConstant.PAY_ORDER) && !this.relatedTo.equals("9")) {
                    this.setErrorMsg("Clearing is not allowed.");
                    return;
                } else {
                    TxnDetailBean txnCashBean = createTxnBeanObj();
                    txnCashBean.setAcNo(this.getAccNo());
                    txnCashBean.setTranType(this.getTranType());
                    txnCashBean.setTy("0");

                    txnCashBean.setAmount(Double.parseDouble(amountTxn.toString()));
                    txnCashBean.setDetails(this.getDetails());
                    txnCashBean.setPayBy(this.getBy());

                    String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
                    txnCashBean.setValueDate(valDt);
                    Float recNo = ftsPostRemote.getRecNo();
                    txnCashBean.setRecNo(recNo);
                    txnCashBean.setAlertSubCode(this.alertSubCode);

                    cashTxnList.add(txnCashBean);
                    cashSaveResult = txnRemote.saveTxnDetails(cashTxnList, Sundrytable, cashDenominationObj.getDenominationTable(),AcNature,getCustId(),getCustPanNo(),acctCategory);
                    if (cashSaveResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        resetFormExcludeAccNo();
                        if (!ccodMsg.equals("")) {
                            this.setAccNoMsg(ccodMsg);
                        }
                        this.setMessage("Data has been saved successfully, Generated Txn No. is:-   " + recNo.longValue());
                    } else {
                        if (!ccodMsg.equals("")) {
                            this.setAccNoMsg(ccodMsg);
                        }
                        this.setErrorMsg(cashSaveResult);
                    }
                }
            }
            if (this.tranType.equals("1") && this.selectCrDr.equals("1")) {
                if (!AcNature.equals(CbsConstant.SAVING_AC) && !AcNature.equals(CbsConstant.CURRENT_AC) && !AcNature.equals(CbsConstant.PAY_ORDER)) {
                    this.setErrorMsg("Inward clearing is not allowed.");
                    return;
                }
                if (this.by.equals("1") && AcNature.equals(CbsConstant.PAY_ORDER) && !this.chqNo.equals("")) {
                    this.setErrorMsg("This Instrument No. Does Not Exist");
                    return;
                }

                if (this.by.equals("1") && this.getChqueDate() == null) {
                    this.setErrorMsg("Please fill Cheque date.");
                    return;
                }
                if (this.by.equals("4") && this.getChqueDate() == null) {
                    this.setErrorMsg("Please fill Loose Cheque date.");
                    return;
                }
                this.setErrorMsg("");

                TxnDetailBean txnCashBean = createTxnBeanObj();
                txnCashBean.setAcNo(this.getAccNo());
                txnCashBean.setTranType(this.getTranType());
                txnCashBean.setTy("1");

                txnCashBean.setAmount(Double.parseDouble(amountTxn.toString()));
                txnCashBean.setDetails(this.getDetails());
                txnCashBean.setPayBy(this.getBy());

                String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
                txnCashBean.setValueDate(valDt);
                Float recNo = ftsPostRemote.getRecNo();
                txnCashBean.setRecNo(recNo);
                txnCashBean.setAlertSubCode(this.alertSubCode);
                cashTxnList.add(txnCashBean);
                cashSaveResult = txnRemote.saveTxnDetails(cashTxnList, Sundrytable, cashDenominationObj.getDenominationTable(),AcNature,getCustId(),getCustPanNo(),acctCategory);
                if (cashSaveResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    resetFormExcludeAccNo();
                    if (!ccodMsg.equals("")) {
                        this.setAccNoMsg(ccodMsg);
                    }
                    this.setMessage("Data has been saved successfully, Generated Txn No. is:-   " + recNo.longValue());
                } else {
                    if (!ccodMsg.equals("")) {
                        this.setAccNoMsg(ccodMsg);
                    }
                    this.setErrorMsg(cashSaveResult);
                }
            }
            if (this.tranType.equals("2") && msgFlag != 4) {
                if (this.by.equals("1") && AcNature.equals(CbsConstant.PAY_ORDER) && !this.chqNo.equals("")) {
                    this.setErrorMsg("This Instrument No. Does Not Exist");
                    return;
                }
                //To Do Change in the following condition (in case of NCCBL (For Party account single debit))
                if (poMultipleEntry && !AcNature.equals(CbsConstant.PAY_ORDER) && this.selectCrDr.equals("1") && totalPoAmt.compareTo(new BigDecimal(0)) > 0
                        && totalPoAmt.compareTo(new BigDecimal(amountTxn)) != 0) {
                    this.setErrorMsg("Please fill only PO/DD Amount in Amount field. Commision and Tax will be automatically deducted by the System");
                    return;
                }
                /*                  New Code Added By Nishant Kansal                */
                String chkBalResult = ftsPostRemote.chkBal(this.getAccNo(), Integer.parseInt(this.selectCrDr), Integer.parseInt(this.relatedTo), AcNature);
                if (!chkBalResult.equalsIgnoreCase("TRUE")) {
                    double totAmt = 0;
                    if (poMultipleEntry && this.selectCrDr.equals("1") && totalPoAmt.compareTo(new BigDecimal(0)) > 0) {
                        totAmt = Double.parseDouble(this.getAmountTxn()) + Double.parseDouble(totalComm.toString()) + Double.parseDouble(totalStax.toString());
                    } else {
                        totAmt = Double.parseDouble(this.getAmountTxn());
                    }

                    String chkBalance = ftsPostRemote.checkBalance(accNo, totAmt, getUserName());
                    if (!chkBalance.equalsIgnoreCase("TRUE")) {
                        this.setErrorMsg(chkBalance);
                        return;
                    }
                }
                /*                  End Of New Code Added By Nishant Kansal                */
                TxnDetailBean txnBean = createTxnBeanObj();
                txnBean.setAcNo(this.getAccNo());
                txnBean.setTranType(this.getTranType());
                if (this.selectCrDr.equals("0")) {
                    txnBean.setTy("0");
                } else if (this.selectCrDr.equals("1")) {
                    txnBean.setTy("1");
                }
                if (this.selectCrDr.equals("0")) {
                    txnBean.setAmount(Double.parseDouble(this.getAmountTxn().toString()));
                    txnBean.setCrAmt(Double.parseDouble(this.getAmountTxn().toString()));
                } else if (this.selectCrDr.equals("1")) {
                    txnBean.setAmount(Double.parseDouble(this.getAmountTxn().toString()));
                    txnBean.setDrAmt(Double.parseDouble(this.getAmountTxn().toString()));
                }
                txnBean.setDetails(this.getDetails());
                txnBean.setInstNo(this.getChqNo());

                String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);

                txnBean.setValueDate(valDt);
                txnBean.setRecNo(ftsPostRemote.getRecNo());
                managedSundrySuspenseTxn(txnBean);
                txnBean.setAlertSubCode(this.alertSubCode);

                if (msgFlag == 5 && this.selectCrDr.equals("1") && this.relatedTo.equalsIgnoreCase("103")) {
                    txnBean.setTdAcNo(newNpaAcNo);
                }

                cashPOTxnBeanList.add(txnBean);
                dataList.add(txnBean);
                //To Do Change in the following condition (in case of NCCBL (For Party account single debit))
                if (poMultipleEntry && this.selectCrDr.equals("1") && totalPoAmt.compareTo(new BigDecimal(0)) > 0) {
                    if (totalComm.compareTo(new BigDecimal(0)) > 0) {
                        addTxnObjInList(totalComm.toString(), "COMMISION FOR " + detailsBillType, "126");
                    }
                    if (totalStax.compareTo(new BigDecimal(0)) > 0) {
                        addTxnObjInList(totalStax.toString(), "GST FOR " + detailsBillType, "71");
                    }
                }
                checkDrCrXfer();

                if (this.selectCrDr.equals("1") && this.relatedTo.equalsIgnoreCase("6")) {
                    String code = "";
                    String schFlg = "";
                    String schTrFlg = "";
                    float calPer = 0;
                    List list = aitr.getIntCodeIntTypeSchmCode(accNo);
                    if (!list.isEmpty()) {
                        Vector element = (Vector) list.get(0);
                        code = element.get(0).toString();
                    }
                    List schList = new ArrayList();
                    schList = acFacade.chkSchDetails(code);
                    if (!schList.isEmpty()) {
                        Vector schLst = (Vector) schList.get(0);
                        schFlg = schLst.get(0).toString();
                        schTrFlg = schLst.get(3).toString();
                        calPer = Float.parseFloat(schLst.get(4).toString());
                    }
                    /**
                     * * **
                     * **********************************************************
                     */
                    //             Code For MIR Adjustment             //
                    List memChkLoan = acFacade.loanForMemInfo(accNo);
                    if (!memChkLoan.isEmpty()) {
                        Vector eleV1 = (Vector) memChkLoan.get(0);
                        String MemNo = eleV1.get(0).toString();

                        List chList = acFacade.memMirDue(MemNo);
                        Vector eleV = (Vector) chList.get(0);

                        if (!eleV.get(0).toString().equalsIgnoreCase("0.0")) {
                            double dAmt = Double.parseDouble(eleV.get(0).toString());
                            String acChk = ftsPostRemote.getAcnoByPurpose("MIR-HEAD");
                            String acPlHead = ftsPostRemote.getAcnoByPurpose("MIR-PL-HEAD");

                            TxnDetailBean txnBean2 = createTxnBeanObj();
                            txnBean2.setAcNo(getOrgnBrCode() + acChk);
                            txnBean2.setTranType(this.getTranType());
                            txnBean2.setTy("0");
                            txnBean2.setAmount(dAmt);
                            txnBean2.setCrAmt(dAmt);

                            txnBean2.setDetails(MemNo + " " + this.getDetails());
                            txnBean2.setInstNo("");
                            String valDt1 = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
                            txnBean2.setValueDate(valDt1);

                            txnBean2.setAlertSubCode(this.alertSubCode);

                            txnBean2.setRecNo(ftsPostRemote.getRecNo());
                            managedSundrySuspenseTxn(txnBean2);
                            cashPOTxnBeanList.add(txnBean2);
                            dataList.add(txnBean2);
                            checkDrCrXfer();

                            TxnDetailBean txnBean3 = createTxnBeanObj();
                            txnBean3.setAcNo(getOrgnBrCode() + acPlHead);
                            txnBean3.setTranType(this.getTranType());
                            txnBean3.setTy("0");
                            txnBean3.setAmount(dAmt * 2.0 / 100);
                            txnBean3.setCrAmt(dAmt * 2.0 / 100);

                            txnBean3.setDetails(MemNo + " " + this.getDetails());
                            txnBean3.setInstNo("");
                            txnBean3.setValueDate(valDt1);

                            txnBean3.setRecNo(ftsPostRemote.getRecNo());
                            managedSundrySuspenseTxn(txnBean3);
                            cashPOTxnBeanList.add(txnBean3);
                            dataList.add(txnBean3);
                            checkDrCrXfer();
                        }
                    }

                    if (schFlg.equalsIgnoreCase("Y") && schTrFlg.equalsIgnoreCase("Y")) {
                        String thrAcno = ftsPostRemote.getThriftAccountNumber(accNo);
                        this.setSelectCrDr("0");
                        TxnDetailBean txnBean1 = createTxnBeanObj();
                        txnBean1.setAcNo(thrAcno);
                        txnBean1.setTranType(this.getTranType());
                        txnBean1.setTy("0");
                        txnBean1.setAmount((Double.parseDouble(this.getAmountTxn().toString()) * calPer) / 100);
                        txnBean1.setCrAmt((Double.parseDouble(this.getAmountTxn().toString()) * calPer) / 100);

                        txnBean1.setDetails(this.getDetails());
                        txnBean1.setInstNo("");
                        String valDt1 = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
                        txnBean1.setValueDate(valDt1);
                        txnBean1.setAlertSubCode(this.alertSubCode);
                        txnBean1.setRecNo(ftsPostRemote.getRecNo());
                        managedSundrySuspenseTxn(txnBean1);
                        cashPOTxnBeanList.add(txnBean1);
                        dataList.add(txnBean1);
                        checkDrCrXfer();
                    }
                }
                this.setChqNo("");
                this.setChqueDate(null);
                this.setAmountTxn("");
                this.setRelatedTo(" ");
                this.setDetails("");
                this.setBy("");
            }

        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    private void addTxnObjInList(String amount, String details, String tranDesc) throws ApplicationException {
        try {
            this.setBy("3");
            TxnDetailBean txnBean = createTxnBeanObj();
            txnBean.setAcNo(this.getAccNo());
            txnBean.setTranType(this.getTranType());
            txnBean.setTy("1");

            txnBean.setAmount(Double.parseDouble(amount));
            txnBean.setDrAmt(Double.parseDouble(amount));
            txnBean.setDetails(details);
            txnBean.setTranDesc(tranDesc);
            String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);

            txnBean.setValueDate(valDt);
            txnBean.setRecNo(ftsPostRemote.getRecNo());
            cashPOTxnBeanList.add(txnBean);
            dataList.add(txnBean);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    private void managedSundrySuspenseTxn(TxnDetailBean txnBean) throws ApplicationException {
        try {
            int counter = 0;
            int postFlag;
            tempXferList.add(txnBean);
            for (int loop = 0; loop < tempXferList.size(); loop++) {
                String acno = tempXferList.get(loop).getAcNo();
                String tyValue = tempXferList.get(loop).getTy();

                postFlag = txnRemote.getPostFlag(acno);
                if (postFlag == 11 && contra(postFlag, tyValue) == true) {
                    counter = 1;
                    break;
                } else if (postFlag == 12 && contra(postFlag, tyValue) == true) {
                    counter = 2;
                    break;
                } else if (postFlag == 13 && contra(postFlag, tyValue) == true) {
                    counter = 2;
                    break;
                }
            }
            if (counter == 1) {
                for (int loop = 0; loop < tempXferList.size(); loop++) {
                    String acno = tempXferList.get(loop).getAcNo();
                    String tyValue = tempXferList.get(loop).getTy();
                    postFlag = txnRemote.getPostFlag(acno);
                    if (postFlag == 11 && contra(postFlag, tyValue) == true) {
                        continue;
                    } else {
                        if (tyValue.equals("1")) {
                            this.setMessage("Only one Dr entry is allowed in this context");
                            tempXferList.remove(txnBean);
                            return;
                        }
                    }
                }
            } else if (counter == 2) {
                for (int loop = 0; loop < tempXferList.size(); loop++) {
                    String acno = tempXferList.get(loop).getAcNo();
                    String tyValue = tempXferList.get(loop).getTy();
                    postFlag = txnRemote.getPostFlag(acno);
                    if (postFlag == 12 && contra(postFlag, tyValue) == true) {
                        continue;
                    } else {
                        if (tyValue.equals("0")) {
                            this.setMessage("Only one Credit entry is allowed in this context");
                            tempXferList.remove(txnBean);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private void checkDrCrXfer() throws ApplicationException {
        try {
            NumberFormat formatter = new DecimalFormat("#.##");
            this.setSumCr(new BigDecimal("0.00"));
            this.setSumDr(new BigDecimal("0.00"));
            for (TxnDetailBean tmpBean : dataList) {
                if (tmpBean.getCrAmt() == null) {
                    tmpBean.setCrAmt(0.0d);
                }
                if (tmpBean.getDrAmt() == null) {
                    tmpBean.setDrAmt(0.0d);
                }

                if (!tmpBean.getCrAmt().toString().equals("")) {
                    this.sumCr = this.sumCr.add(new BigDecimal(tmpBean.getCrAmt().toString()));
                }

                if (!tmpBean.getDrAmt().toString().equals("")) {
                    this.sumDr = this.sumDr.add(new BigDecimal(tmpBean.getDrAmt().toString()));
                }

                if (formatter.format(Double.parseDouble(this.sumCr.toString())).toString().equals(formatter.format(Double.parseDouble(this.sumDr.toString())).toString())) {
                    flag5 = "false";
                    flag6 = "true";
                } else {
                    flag5 = "true";
                    flag6 = "false";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void saveXferDetail() {
        try {
            String msg = ftsPostRemote.ftsDateValidate(Tempbd, getOrgnBrCode());
            if (!msg.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException(msg);
            }
            if (sumCr.compareTo(sumDr) != 0) {
                throw new ApplicationException("Transfer Batch is incomplete");
            }
            String xFerResult = txnRemote.saveTransferDetails(getOrgnBrCode(), cashPOTxnBeanList, Tempbd, getUserName());
            resetXferDetails();
            resetForm();

            if (!ccodMsg.equals("")) {
                this.setAccNoMsg(ccodMsg);
            }
            this.setMessage("Generated Batch No. is:- " + (long) Float.parseFloat(xFerResult));
            flag5 = "true";
        } catch (Exception e) {
            if (!ccodMsg.equals("")) {
                this.setAccNoMsg(ccodMsg);
            }
            flag5 = "true";
            flag6 = "false";
            this.setErrorMsg(e.getMessage());
        }
    }

    public void poAccNumLostFocus() {
        if (this.poAccNum.equals("") || this.poAccNum == null) {
            this.setPoError("Please Enter Account No.");
            poAccNumFocus = "true";
            return;
        }
        try {
            if (Double.parseDouble(this.getPoAmount().toString()) <= 0 || !this.getPoAmount().toString().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Amount should be numeric or greater than zero");
            } else if (this.inFavOf.equals("")) {
                throw new ApplicationException("Please fill the In Favour Of");
            } else if (this.inFavOf.contains("'")) {
                infavOfFlag = "true";
                throw new ApplicationException("Single Quote does not allow in InfavourOf");
            }
            String poAcno = ftsPostRemote.getNewAccountNumber(poAccNum);
            List list = txnRemote.getDetailsPOCustName(poAcno, getOrgnBrCode());
            Vector accountVector = (Vector) list.get(0);

            this.setPoCustName(accountVector.get(0).toString());
            String accstatus = accountVector.get(1).toString();
            String custState = accountVector.get(2).toString();
            String branchState = accountVector.get(3).toString();

            if (!accstatus.equals("1")) {
                throw new ApplicationException("Account Number must be active");
            }

            if (!custState.equalsIgnoreCase(branchState) && this.poMode.equals("ISSUED") && Double.parseDouble(this.getPoComm().toString()) >= 0) {
                taxMap = ibrFacade.getIgstTaxComponent(Double.parseDouble(this.getPoComm().toString()), Tempbd);
            }
            if (this.poCustName.equals("")) {
                throw new ApplicationException("Please fill the Customer Name");
            } else if (this.poCustName.contains("'")) {
                throw new ApplicationException("Single Quote does not allow in Customer Name");
            }
            poAccNumFocus = "false";
            loadBillGrid();

        } catch (Exception e) {
            setPoAccNum("");
            poAccNumFocus = "true";
            this.setPoError(e.getMessage());
        }
    }

    public void savePoTxnDetails() {
        DecimalFormat dcFormat = new DecimalFormat("#.##");
        List<TxnDetailBean> cashPOTxnList;
        try {
            if (this.poMode.equals("CANCELLED") || this.poMode.equals("PAID")) {
                if (this.by.equals("1") && AcNature.equals(CbsConstant.PAY_ORDER) && !this.chqNo.equals("")) {
                    this.setErrorMsg("This Instrument No. Does Not Exist");
                    return;
                }
            }
            if (this.poBy.equals("TRANSFER")) {
                cashPOTxnList = new ArrayList<TxnDetailBean>();
                this.setDetails(getPoDetails());
                TxnDetailBean tempTxnBen = createTxnBeanObj();
                tempTxnBen.setRecNo(recno);
                managedSundrySuspenseTxn(tempTxnBen);

                cashPOTxnList.add(tempTxnBen);
                cashPOTxnBeanList.add(tempTxnBen);

                if (this.poMode.equalsIgnoreCase("ISSUED") && Double.parseDouble(this.getPoComm().toString()) != 0) {
                    detailsBillType = this.billType;
                    totalPoAmt = totalPoAmt.add(new BigDecimal(tempTxnBen.getAmount()));
                    totalComm = totalComm.add(this.getPoComm());

                    this.setAccNo(actno);
                    this.setPoAmount(this.getPoComm());
                    this.setDetails("COMM. FOR " + this.billType + " DRAWN ON " + this.getAlphaCode() + " FAVOURING OF " + this.inFavOf);

                    tempTxnBen = createTxnBeanObj();
                    tempTxnBen.setTranDesc("126");
                    cashPOTxnList.add(tempTxnBen);

                    cashPOTxnBeanList.add(tempTxnBen);
                    tempXferList.add(tempTxnBen);
                    if (staxModuleActive) {
                        Set<Entry<String, Double>> set = taxMap.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();

                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            this.setAccNo(getOrgnBrCode() + keyArray[1]);

                            this.setPoAmount(new BigDecimal(dcFormat.format(entry.getValue())));
                            this.setDetails(keyArray[0].toUpperCase() + " FOR " + this.billType + " DRAWN ON " + this.getAlphaCode() + " FAVOURING OF " + this.inFavOf);

                            tempTxnBen = createTxnBeanObj();
                            tempTxnBen.setTranDesc("71");
                            cashPOTxnList.add(tempTxnBen);

                            cashPOTxnBeanList.add(tempTxnBen);
                            tempXferList.add(tempTxnBen);
                        }
                        totalStax = totalStax.add(new BigDecimal(dcFormat.format(getTotalSTaxAmt())));
                    }
                }
                for (int i = 0; i < cashPOTxnList.size(); i++) {
                    txnDetailGrid = new TxnDetailBean();
                    txnDetailGrid.setAcNo(cashPOTxnList.get(i).getAcNo().toString());
                    txnDetailGrid.setTranTypeDesc(cashPOTxnList.get(i).getTranTypeDesc().toString());
                    txnDetailGrid.setTyDesc(cashPOTxnList.get(i).getTyDesc().toString());

                    if (cashPOTxnList.get(i).getCrAmt() == null) {
                        txnDetailGrid.setCrAmt(0.0d);
                    } else {
                        txnDetailGrid.setCrAmt(cashPOTxnList.get(i).getCrAmt());
                    }
                    if (cashPOTxnList.get(i).getDrAmt() == null) {
                        txnDetailGrid.setDrAmt(0.0d);
                    } else {
                        txnDetailGrid.setDrAmt(cashPOTxnList.get(i).getDrAmt());
                    }
                    dataList.add(txnDetailGrid);
                }
                checkDrCrXfer();
                setDetails("");
            } else {
                cashPOTxnList = new ArrayList<TxnDetailBean>();
                this.setDetails(getPoDetails());
                TxnDetailBean tempTxnBen = createTxnBeanObj();

                tempTxnBen.setRecNo(recno);
                cashPOTxnList.add(tempTxnBen);
                if (this.poMode.equalsIgnoreCase("ISSUED") && Double.parseDouble(this.getPoComm().toString()) != 0) {
                    this.setAccNo(actno);
                    this.setPoAmount(this.getPoComm());

                    this.setDetails("COMM. FOR " + this.billType + " DRAWN ON " + this.getAlphaCode() + " FAVOURING OF " + this.inFavOf);
                    tempTxnBen = createTxnBeanObj();
                    tempTxnBen.setTranDesc("126");
                    cashPOTxnList.add(tempTxnBen);

                    if (staxModuleActive) {
                        Set<Entry<String, Double>> set = taxMap.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();

                        double sTax = getTotalSTaxAmt();
                        double diff = (Math.ceil(sTax) - sTax) / taxMap.size();

                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            this.setAccNo(getOrgnBrCode() + keyArray[1]);

                            //  if (keyArray[3].equals("R")) {
                            //      double sTax = getTotalSTaxAmt();
                            //      double diff = Math.ceil(sTax) - sTax;
                            this.setPoAmount(new BigDecimal(Double.parseDouble(entry.getValue().toString()) + diff));
                            // } else {
                            //      this.setPoAmount(new BigDecimal(entry.getValue().toString()));
                            //   }
                            this.setDetails(keyArray[0].toUpperCase() + " FOR " + this.billType + " DRAWN ON " + this.getAlphaCode() + " FAVOURING OF " + this.inFavOf);
                            tempTxnBen = createTxnBeanObj();
                            tempTxnBen.setTranDesc("71");
                            cashPOTxnList.add(tempTxnBen);
                        }
                    }
                }
                String msg = txnRemote.saveTxnDetails(cashPOTxnList, Sundrytable, cashDenominationObj.getDenominationTable(),AcNature,getCustId(),getCustPanNo(),acctCategory);
                resetFormExcludeAccNo();
                if (msg.substring(0, 4).equalsIgnoreCase("True")) {
                    this.setMessage("Data successfully saved and Generated Txn No. is:-   " + recno.longValue());
                } else {
                    setMessage(msg);
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public boolean loanDisbCheck(double outstanding) {
        double totDisAmt;
        totDisAmt = 0;
        List list = txnRemote.selectFromLoanDisb(accNo);
        if (!list.isEmpty()) {
            Vector v = (Vector) list.get(0);
            totDisAmt = Double.parseDouble(v.get(0).toString());
        }
        if (outstanding != 0) {
            if (totDisAmt > Double.parseDouble(limit.toString())) {
                return false;
            }
        }
        return true;
    }

    public boolean checkRDInstall() {
        int checkamt = 0;
        List list = txnRemote.selectRDInstall(accNo);
        if (!list.isEmpty()) {
            Vector v = (Vector) list.get(0);
            if (this.amountTxn.toString().contains(".")) {//.toString()
                this.setErrorMsg("Amount should be in multiple of the Installment Amount " + v.get(0).toString());
                return false;
            }
            if (Double.parseDouble(v.get(0).toString()) > 0) {
                checkamt = (int) (Double.parseDouble(this.amountTxn.toString()) % Double.parseDouble(v.get(0).toString()));
            }
            if (checkamt != 0) {
                this.setErrorMsg("Amount should be in multiple of the Installment Amount " + v.get(0).toString());
                return false;
            }
        }
        if (relatedTo.equalsIgnoreCase("1")) {
            List checkList = txnRemote.checkForUnpaidRdInstallment(accNo);
            if (checkList.isEmpty()) {
                this.setErrorMsg("All installments for this A/C has been paid.You can not paid further installments.");
                return false;
            }
            List checkListAmt = txnRemote.checkForUnpaidRdInstallmentAmt(accNo);
            if (!checkListAmt.isEmpty()) {
                Vector vecAmt = (Vector) checkListAmt.get(0);
                if (Double.parseDouble(amountTxn.toString()) > Double.parseDouble(vecAmt.get(0).toString())) {
                    this.setErrorMsg("You can not pay more then total installment amount for this A/C.");
                    return false;
                }

            }
        } else if (relatedTo.equalsIgnoreCase("9") && selectCrDr.equalsIgnoreCase("1")) {
            List amtList = txnRemote.getLastTransAmtForAccount(accNo);
            if (!amtList.isEmpty()) {
                for (int v = 0; v < amtList.size(); v++) {
                    Vector lastAmtVector = (Vector) amtList.get(v);
                    BigDecimal lastTransAmt = new BigDecimal(lastAmtVector.get(0).toString());
                    int compareValue = new BigDecimal(amountTxn).compareTo(lastTransAmt);
                    if (compareValue == 0) {
                        return true;
                    }
                }
            }
            this.setErrorMsg("Please fill correct amount for this contra.");
            return false;
        }
        return true;
    }

    public boolean draftPayInstStatus() {
        List list = txnRemote.selectFromBillLost(chqNo, getOrgnBrCode());
        if (!list.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean Validation() {
        try {
            String By = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (this.accNo.equals("") || this.accNo.length() == 0) {
                ValidationFlag = true;
                this.setErrorMsg("Please fill the A/C No.");
                return ValidationFlag;
            }

            if (this.tranType.equals(" ")) {
                ValidationFlag = true;
                this.setErrorMsg("Please select transaction type.");
                return ValidationFlag;
            }

            if (this.selectCrDr.equals(" ")) {
                ValidationFlag = true;
                this.setErrorMsg("Please select Cr/Dr.");
                return ValidationFlag;
            }

            if (this.relatedTo.equals(" ")) {
                ValidationFlag = true;
                this.setErrorMsg("Please select related to.");
                return ValidationFlag;
            }

            if (this.by.equals(" ")) {
                ValidationFlag = true;
                this.setErrorMsg("Please select transaction pay by.");
                return ValidationFlag;
            }

            if (this.by.equalsIgnoreCase("1")) {
                By = "CHEQUE";
            } else if (this.by.equalsIgnoreCase("2")) {
                By = "W/S";
            } else if (this.by.equalsIgnoreCase("3")) {
                By = "VOUCHER";
            } else if (this.by.equalsIgnoreCase("4")) {
                By = "LOOSE CHEQUE";
            }

            if (this.valueDate.equals("") || this.valueDate == null) {
                this.setValueDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.by.equals("2")) {
                if (this.chqueDate != null) {
                    String chqDate = sdf.format(chqueDate);
                    Long longTempDate = Long.parseLong(Tempbd);
                    Long longChqDate = Long.parseLong(chqDate.substring(0, 4) + chqDate.substring(4, 6) + chqDate.substring(6));
                    if (longTempDate.compareTo(longChqDate) != 0) {
                        ValidationFlag = true;
                        this.setErrorMsg("Please Check the W/S Date");
                        return ValidationFlag;
                    }
                }
            }
            if (this.by.equals("3")) {
                if (this.chqueDate != null) {
                    String chqDt = sdf.format(chqueDate);
                    Long longTempDate = Long.parseLong(Tempbd);
                    Long longChqDate = Long.parseLong(chqDt.substring(0, 4) + chqDt.substring(4, 6) + chqDt.substring(6));
                    if (longTempDate.compareTo(longChqDate) != 0) {
                        ValidationFlag = true;
                        this.setErrorMsg("Please Check the Vch. Date");
                        return ValidationFlag;
                    }
                }
            }

            if (this.amountTxn == null || this.amountTxn.toString().equalsIgnoreCase("")) {
                this.setErrorMsg("Please Fill Amount Field.");
                ValidationFlag = true;
                return ValidationFlag;
            } else if (this.amountTxn.toString().equalsIgnoreCase("0") || this.amountTxn.toString().equalsIgnoreCase("0.0")) {
                this.setErrorMsg("Please Fill Amount Field.");
                ValidationFlag = true;
                return ValidationFlag;
            } else {
                Matcher amountTxnCheck = p.matcher(this.amountTxn.toString());
                if (!amountTxnCheck.matches()) {
                    this.setErrorMsg("Invalid Entry.");
                    ValidationFlag = true;
                    return ValidationFlag;
                }
            }
            if (!txnRemote.selectProductCode(accNo.substring(2, 4)).equalsIgnoreCase("SS")) {
                if (this.selectCrDr.equals("1") && this.by.equals("1") && (this.chqNo.equals("") || this.chqNo.equals("0") || this.chqNo.length() == 0)) {
                    ValidationFlag = true;
                    chqNoFlag = "false";
                    chqFocus = "true";
                    this.setErrorMsg("Please fill cheque no.");
                    return ValidationFlag;
                }
            }
            if (this.selectCrDr.equals("1") && this.by.equals("1") && (this.chqNo.equals("") || this.chqNo.equals("0") || this.chqNo.length() == 0)) {
                ValidationFlag = true;
                chqNoFlag = "false";
                chqFocus = "true";
                this.setErrorMsg("Please fill cheque no.");
                return ValidationFlag;
            }
            if (this.selectCrDr.equals("1") && this.by.equals("4") && (this.chqNo.equals("") || this.chqNo.equals("0") || this.chqNo.length() == 0)) {
                ValidationFlag = true;
                chqNoFlag = "false";
                chqFocus = "true";
                this.setErrorMsg("Please fill cheque no.");
                return ValidationFlag;
            }
            /*Start of New Modifications according to Manab Sir*/
            if (this.selectCrDr.equals("1") && (this.by.equals("1") || this.by.equals("4"))) {
                if (chqNo.contains(".")) {
                    ValidationFlag = true;
                    chqNoFlag = "false";
                    chqFocus = "true";
                    this.setErrorMsg("Decimal is not allowed in cheque no.");
                    return ValidationFlag;
                }
                if (Double.parseDouble(chqNo) < 0) {
                    ValidationFlag = true;
                    chqNoFlag = "false";
                    chqFocus = "true";
                    this.setErrorMsg("Cheque no. can not be negative");
                    return ValidationFlag;
                }

            }
            /*End of New Modifications according to Manab Sir*/

            /*              Start New Code Added            */
            if (this.selectCrDr.equals("0") && this.by.equals("1") && (this.chqNo.equals("") || this.chqNo.equals("0") || this.chqNo.length() == 0)) {
                ValidationFlag = true;
                chqNoFlag = "false";
                chqFocus = "true";
                this.setErrorMsg("Please fill cheque no.");
                return ValidationFlag;
            }

            if (this.selectCrDr.equals("0") && this.by.equals("4") && (this.chqNo.equals("") || this.chqNo.equals("0") || this.chqNo.length() == 0)) {
                ValidationFlag = true;
                chqNoFlag = "false";
                chqFocus = "true";
                this.setErrorMsg("Please fill cheque no.");
                return ValidationFlag;
            }
            if (this.relatedTo.equals("9") && this.details.equals("")) {
                ValidationFlag = true;
                this.setErrorMsg("Please fill Details.");
                return ValidationFlag;
            }
            /*              End New Code Added            */

            if (seqNoYearFlag.equalsIgnoreCase("true")) {
                if (this.seqNo.equalsIgnoreCase("")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please fill seq. no.");
                    return ValidationFlag;
                }
                if (this.seqYear.equalsIgnoreCase("")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please fill Year.");
                    return ValidationFlag;
                }
            }
            Long longTempDate = Long.parseLong(Tempbd);
            if (this.chqueDate != null) {
                String cqDt = sdf.format(chqueDate);
                Long longChqDate = Long.parseLong(cqDt);
                if (longChqDate.compareTo(longTempDate) > 0) {
                    ValidationFlag = true;
                    this.setErrorMsg("Post Dated" + " " + By + " " + "Is Not Allowed");
                    return ValidationFlag;
                }
            }
            if (this.selectCrDr.equals("1") && (this.by.equals("1") || this.by.equals("4")) && this.chqueDate != null) {

                String sixMonthChqDt = "20120331"; //According to Mayank sir, It will change after discussion.
                Long longChqDt = Long.parseLong(CbsUtil.monthAdd(sdf.format(chqueDate), 3));

                int compareChqDt = chqueDate.compareTo(sdf.parse(sixMonthChqDt));
                if (compareChqDt <= 0) {
                    longChqDt = Long.parseLong(CbsUtil.monthAdd(sdf.format(chqueDate), 6));
                }
                if (longChqDt.compareTo(longTempDate) < 0) {
                    this.setErrorMsg("Instrument Date is more than three months old.");
                    chqDateFocus = "true";
                    ValidationFlag = true;
                    return ValidationFlag;
                }
            }

            if (Double.parseDouble(amountTxn.toString()) < 0) { // amountTxn.doubleValue()
                this.setErrorMsg("Amount cannot be negative");
                ValidationFlag = true;
                return ValidationFlag;
            }

            /*                Start Of Repayment Schedule Code         */
            if ((AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || AcNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                    && this.relatedTo.equals("1") && this.selectCrDr.equals("0")) {
                List repaymentScheduleInfoList = txnRemote.getRepaymentScheduleInfo(accNo);
                if (!repaymentScheduleInfoList.isEmpty()) {
                    Vector repaymentScheduleInfoVec = (Vector) repaymentScheduleInfoList.get(0);
                    if (repaymentScheduleInfoVec.get(0).toString().equals("0")) {
                        ValidationFlag = true;
                        this.setErrorMsg("Repayment Schedule is not complete. Please fill Repayment Schedule.");
                        return ValidationFlag;
                    }

                } else {
                    ValidationFlag = true;
                    this.setErrorMsg("Repayment Schedule is not complete.Please fill Repayment Schedule.");
                    return ValidationFlag;
                }
            }
            /*                End Of Repayment Schedule Code         */

            /*     Validation for cheque date                   */
            if (this.chqueDate != null) {
                String chqDate = sdf.format(this.chqueDate);
                if (Integer.parseInt(chqDate.substring(6)) > 31 || Integer.parseInt(chqDate.substring(6)) <= 0) {
                    ValidationFlag = true;
                    this.setErrorMsg("Invalid cheque date.");
                    return ValidationFlag;
                }
                if (Integer.parseInt(chqDate.substring(4, 6)) > 12 || Integer.parseInt(chqDate.substring(4, 6)) <= 0) {
                    ValidationFlag = true;
                    this.setErrorMsg("Invalid cheque date.");
                    return ValidationFlag;
                }
            }

            /*     End of Validation for cheque date code       */

            /*     Validation for value date                   */
            if (Integer.parseInt(this.valueDate.substring(0, 2)) > 31 || Integer.parseInt(this.valueDate.substring(0, 2)) <= 0) {
                ValidationFlag = true;
                this.setErrorMsg("Invalid value date.");
                return ValidationFlag;
            }
            if (Integer.parseInt(this.valueDate.substring(3, 5)) > 12 || Integer.parseInt(this.valueDate.substring(3, 5)) <= 0) {
                ValidationFlag = true;
                this.setErrorMsg("Invalid value date.");
                return ValidationFlag;
            }
            String currentDate = sdf.format(new Date());
            String stringValueOfValueDate = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
            Long longValueOfCurrentDate = Long.parseLong(currentDate);
            Long longValueOfValueDate = Long.parseLong(stringValueOfValueDate);
            if (longValueOfValueDate.compareTo(longValueOfCurrentDate) > 0) {
                ValidationFlag = true;
                this.setErrorMsg("Value Date can't be greater then Current Date.");
                return ValidationFlag;
            }
            if (AcNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                if (longValueOfValueDate.compareTo(longValueOfCurrentDate) < 0) {
                    ValidationFlag = true;
                    this.setErrorMsg("Value Date can't be less then Current Date for RD A/C");
                    return ValidationFlag;
                }
            }
            if (this.tranType.equals("0") && longValueOfValueDate.compareTo(longValueOfCurrentDate) < 0) {
                ValidationFlag = true;
                this.setErrorMsg("Value Date can't be less then Current Date for Cash transaction.");
                return ValidationFlag;
            } else if (this.tranType.equals("1") && longValueOfValueDate.compareTo(longValueOfCurrentDate) < 0) {
                ValidationFlag = true;
                this.setErrorMsg("Value Date can't be less then Current Date for Clearing transaction.");
                return ValidationFlag;
            }
            /*     End of Validation for value date code       */

            if (!this.tokenNumber.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                ValidationFlag = true;
                this.setErrorMsg("Please enter numeric value in Token No.");
                return ValidationFlag;
            }
            if (this.tokenNumber.contains(".")) {
                ValidationFlag = true;
                this.setErrorMsg("Decimal is not allowed in Token No.");
                return ValidationFlag;
            }
            if (Double.parseDouble(this.tokenNumber) < 0) {
                ValidationFlag = true;
                this.setErrorMsg("Token No can not be negative");
                return ValidationFlag;
            }

            if (this.gCashMod.equalsIgnoreCase("Y") && this.tranType.equals("0") && this.selectCrDr.equals("1")) {
                if (this.tokenNumber.equals("0") || this.tokenNumber.equals("0.0") || this.tokenNumber.equals("")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please enter Token No because Cashier Module is On");
                    return ValidationFlag;
                }
                if (this.subTokenNo.equals("")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please enter Sub Token because Cashier Module is On");
                    return ValidationFlag;
                }

                if (this.duplicateToken.equalsIgnoreCase("true")) {
                    this.setErrorMsg("This Token already issue. Please issue another token.");
                    ValidationFlag = true;
                    return ValidationFlag;
                }

            }

            /* Code for Sundry,Suspense,Credit Transaction is not allow as per hcbl or Naroz  */
            if ((TmpPostFlag == 11 || TmpPostFlag == 12) && AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (this.tranType.equals("1")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Sundry and Suspense Credit is not allow.");
                    return ValidationFlag;
                }
            }
            if (TmpPostFlag == 11 || TmpPostFlag == 12) {
                if (!dataList.isEmpty()) {
                    if (accNo.equals(dataList.get(0).getAcNo()) && this.selectCrDr.equals(dataList.get(0).getTy())) {
                        ValidationFlag = true;
                        if (TmpPostFlag == 11) {
                            this.setErrorMsg("Multiple Debit is not allow in this same Batch No. of this A/c No-" + accNo);
                        } else {
                            this.setErrorMsg("Multiple credit is not allow in this same Batch No. of this A/c No-" + accNo);
                        }
                        return ValidationFlag;
                    }
                }
            }
            /*                 Code for Sundry,Suspense,ClgAdjustment Transaction              */
            if ((TmpPostFlag == 11 || TmpPostFlag == 12) && seqNoYearFlag.equals("")) {
                if (this.seqNo.equals("")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please fill sequence no.");
                    return ValidationFlag;
                }
                if (this.seqNo == null) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please fill sequence no.");
                    return ValidationFlag;
                }
                if (this.seqYear.equals("")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please fill year");
                    return ValidationFlag;
                }
                if (this.seqYear == null) {
                    ValidationFlag = true;
                    this.setErrorMsg("Please fill year");
                    return ValidationFlag;
                }
                if (!this.relatedTo.equals("9")) {
                    ValidationFlag = true;
                    this.setErrorMsg("Only Contra entry is allowed for this A/C.");
                    return ValidationFlag;
                }
                List list = txnRemote.checkForSundryTableAmount(accNo, seqNo, seqYear, Sundrytable);
                if (list.isEmpty()) {
                    this.setSeqNo("0");
                    this.setSeqYear("0");
                    ValidationFlag = true;
                    this.setErrorMsg("Sequence No. Does Not Exist");
                    return ValidationFlag;
                }

                if (this.relatedTo.equals("9") && this.tranType.equals("0")) {
                    String interTable = null;
                    if (TmpPostFlag == 11) {
                        interTable = "bill_sundry_dt";
                    } else if (TmpPostFlag == 12) {
                        interTable = "bill_suspense_dt";
                    } else if (TmpPostFlag == 13) {
                        interTable = "bill_ClgAdjustment_dt";
                    }
                    List listOfTotalAmount = txnRemote.getSumOfAmountFromIntermediateTable(interTable, this.seqNo, this.seqYear);
                    Vector vecOfTotalAmount = (Vector) listOfTotalAmount.get(0);
                    if (Double.parseDouble(vecOfTotalAmount.get(0).toString()) == 0) {
                        if (Double.parseDouble(amountTxn.toString()) != Double.parseDouble(sundryAmt.toString())) {
                            ValidationFlag = true;
                            this.setErrorMsg("Please Check the Contra Amount");
                            return ValidationFlag;
                        }
                    } else {
                        if ((Double.parseDouble(amountTxn.toString()) + Double.parseDouble(vecOfTotalAmount.get(0).toString())) != Double.parseDouble(sundryAmt.toString())) {
                            ValidationFlag = true;
                            this.setErrorMsg("Please Check the Contra Amount");
                            return ValidationFlag;
                        }
                    }
//                    if (Double.parseDouble(amountTxn.toString()) != Double.parseDouble(sundryAmt.toString())) {
//                        ValidationFlag = true;
//                        this.setErrorMsg("Please Check the Contra Amount");
//                        return ValidationFlag;
//                    }
                }
                if (this.relatedTo.equals("9") && this.tranType.equals("1")) {
                    //if (Double.parseDouble(amountTxn.toString()) != Double.parseDouble(sundryAmt.toString())) {
                    ValidationFlag = true;
                    this.setErrorMsg("Sundry and Suspense Payment is not allow.");
                    return ValidationFlag;
                    // }
                }
                if (this.relatedTo.equals("9") && this.tranType.equals("2")) {
                    String interTable = null;
                    if (TmpPostFlag == 11) {
                        interTable = "bill_sundry_dt";
                    } else if (TmpPostFlag == 12) {
                        interTable = "bill_suspense_dt";
                    } else if (TmpPostFlag == 13) {
                        interTable = "bill_ClgAdjustment_dt";
                    }
                    List listOfTotalAmount = txnRemote.getSumOfAmountFromIntermediateTable(interTable, this.seqNo, this.seqYear);
                    Vector vecOfTotalAmount = (Vector) listOfTotalAmount.get(0);
                    if (Double.parseDouble(vecOfTotalAmount.get(0).toString()) == 0) {
                        if (Double.parseDouble(amountTxn.toString()) > Double.parseDouble(sundryAmt.toString())) {
                            ValidationFlag = true;
                            this.setErrorMsg("Please Check the Contra Amount");
                            return ValidationFlag;
                        }
                    } else {
                        if (CbsUtil.round(Double.parseDouble(amountTxn.toString()) + Double.parseDouble(vecOfTotalAmount.get(0).toString()), 2) > Double.parseDouble(sundryAmt.toString())) {
                            ValidationFlag = true;
                            this.setErrorMsg("Please Check the Contra Amount");
                            return ValidationFlag;
                        }
                    }
                }
            }
            /*                End of Sundry,Suspense,ClgAdjustment Transaction              */
            List getUnAuthAccList = txnRemote.getDetailsOfUnAuthAccounts(accNo);
            if (!getUnAuthAccList.isEmpty()) {
                ValidationFlag = true;
                this.setErrorMsg("This A/C is unauthorize.Transaction for unauthorized accounts is not possible.");
                return ValidationFlag;
            }
            if (msgFlag == 5 && tranType.equals("2") && selectCrDr.equals("1") && newNpaAcNo.equals("")) {
                ValidationFlag = true;
                this.setErrorMsg("Please fill the NPA Account Number");
                return ValidationFlag;
            }

        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return ValidationFlag = false;
    }

    public void onblurSundryYear() {
        try {
            this.setErrorMsg("");
            if (this.seqYear.equals("")) {
                this.setSeqYear("0");
            } else if (!this.seqYear.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                this.setSeqYear("0");
            } else if (Double.parseDouble(this.seqYear) < 0) {
                this.setSeqYear("0");
            }
            if (this.relatedTo.equalsIgnoreCase("9")) {
                if (Sundrytable.equals("")) {
                    seqNoYearFlag = "none";
                    return;
                }
                List list = txnRemote.checkForSundryTableAmount(accNo, seqNo, seqYear, Sundrytable);
                if (list.isEmpty()) {
                    this.setErrorMsg("Sequence Number does not exist");
                    this.setSeqNo("0");
                    this.setSeqYear("0");
                    return;
                } else {
                    Vector vector = (Vector) list.get(0);
                    sundryAmt = new BigDecimal(vector.get(0).toString());
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public String tableNameForPanamount(String accNature) {
        if (accNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || accNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
            return "td_customermaster";
        } else {
            return "customermaster";
        }
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail() {
        try {
            this.imageData = null;
            String sign;
            sign = txnAuthRemote.getSignatureDetails(this.accNo);
            if (!sign.equalsIgnoreCase("Signature not found")) {
                String imageCode = sign.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(accNo);
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
        } catch (FileNotFoundException e) {
            this.setErrorMsg("");
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void selectPOOutstandDetails() {
        try {
            poList = new ArrayList<PayOrderOutstandingGrid>();
            List list = txnRemote.selectForCtrlIKey(getOrgnBrCode());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector v = (Vector) list.get(i);
                    poGrid = new PayOrderOutstandingGrid();
                    poGrid.setFinYear(v.get(0).toString());
                    poGrid.setInFavourOf(v.get(5).toString());
                    poGrid.setInstAmt(v.get(3).toString());
                    poGrid.setInstNo(v.get(2).toString());
                    poGrid.setIssueDate(v.get(4).toString().substring(8, 10) + "/" + v.get(4).toString().substring(5, 7) + "/" + v.get(4).toString().substring(0, 4));
                    poGrid.setIssuedBy(v.get(6).toString());
                    poGrid.setSeqNo(v.get(1).toString());
                    poList.add(poGrid);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void selectStopPayment() {
        stopPayList = new ArrayList<StopPaymentGrid>();
        try {
            if (this.accNo.equalsIgnoreCase("") || this.accNo == null) {
                return;
            }
            List listCASB = txnRemote.selectForCtrlS(accNo, getOrgnBrCode());
            if (!listCASB.isEmpty()) {
                for (int i = 0; i < listCASB.size(); i++) {
                    stopPayGrid = new StopPaymentGrid();
                    Vector vCASB = (Vector) listCASB.get(i);
                    String date = vCASB.get(2).toString();
                    stopPayGrid.setSrNo(i + 1);
                    stopPayGrid.setIssueDate(date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4));
                    stopPayGrid.setInstrumentNo(vCASB.get(1).toString());
                    stopPayList.add(stopPayGrid);
                }
            }

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void poddOnLoad() {
        try {
            lblAlpha = "Alpha Code";
            poModeList = new ArrayList<SelectItem>();
            poModeList.add(new SelectItem(" ", "--Select--"));

            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem(" ", "--Select--"));

            String bNature = "";
            List l1 = txnRemote.selectBillType(accNo);
            for (int i = 0; i < l1.size(); i++) {
                Vector v1 = (Vector) l1.get(i);
                billTypeList.add(new SelectItem(v1.get(0).toString(), v1.get(0).toString()));
                bNature = v1.get(1).toString();
            }

            alphaCodeList = new ArrayList<SelectItem>();
            alphaCodeList.add(new SelectItem(" ", "--Select--"));

            if (bNature.equalsIgnoreCase("DD")) {
                lblAlpha = "Payable At";
                List l2 = aitr.refRecCode("001");
                for (int i = 0; i < l2.size(); i++) {
                    Vector v2 = (Vector) l2.get(i);
                    alphaCodeList.add(new SelectItem(v2.get(0).toString(), v2.get(1).toString()));
                }
            } else {
                List l2 = txnRemote.selectAlphaCode(getOrgnBrCode());
                for (int i = 0; i < l2.size(); i++) {
                    Vector v2 = (Vector) l2.get(i);
                    alphaCodeList.add(new SelectItem(v2.get(0).toString(), v2.get(0).toString()));
                }
            }

            if (this.selectCrDr.equals("0")) {
                poModeList.add(new SelectItem("ISSUED", "ISSUED"));
            } else if (this.selectCrDr.equals("1")) {
                poModeList.add(new SelectItem("PAID", "PAYMENT"));
                poModeList.add(new SelectItem("CANCELLED", "CANCELLED"));
            }

            if (this.tranType.equals("0")) {
                this.setPoBy("CASH");
            } else if (tranType.equals("1")) {
                this.setPoBy("CLEARING");
            } else if (tranType.equals("2")) {
                this.setPoBy("TRANSFER");
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void poBillTypeLostFocus() {
        try {
            seqNoFlag = "none";
            List list7 = txnRemote.getParentACode(billType, getOrgnBrCode());
            Vector v7 = (Vector) list7.get(0);
            parentACode = v7.get(0).toString();

            if (!this.billType.equals(" ")) {
                List list = txnRemote.checkCommFlag(billType);
                Vector v = (Vector) list.get(0);
                if (v.get(0).toString().equalsIgnoreCase("Y")) {
                    commFlag = "";
                } else {
                    commFlag = "none";
                }
                if (this.poMode.equals("ISSUED")) {
                    if (this.billType.equals("PO")) {
                        this.setAlphaCode(parentACode);
                    }
                } else {
                    if (this.billType.equals("PO")) {
                        this.setAlphaCode(parentACode);
                    }
                    seqNoFlag = "";
                }
            }
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }

    public void poModeLostFocus() {
        if (this.poMode.equalsIgnoreCase("ISSUED")) {
            saveCaptionFlag = "Save";
            poSaveFlag = "false";
            poInstNoFlag = "none";
            poIssueDtFlag = "none";
            seqNoFlag = "none";
        } else if (this.poMode.equalsIgnoreCase("CANCELLED") || this.poMode.equalsIgnoreCase("PAID")) {
            saveCaptionFlag = "ClearInstr";
            poSaveFlag = "true";
            seqNoFlag = "";
            poInstNoFlag = "";
            poIssueDtFlag = "";
        }
    }

    public void poSeqNoLostFocus() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        poSeqIssuDtFlag = "false";
        NumberFormat formatter = new DecimalFormat("#.##");
        this.setPoError("");
        try {
            String sixMonthChqDt = "20120331";
            if (this.poIssueDate == null) {
                this.setPoError("First fill Issue date.");
                poSeqIssuDtFlag = "true";
                return;
            }
            if (this.poSeqNo.equals("") || poSeqNo == null) {
                this.setPoError("Please fill sequence no.");
                poSeqIssuDtFlag = "true";
                return;
            }
            Long longChqDt = Long.parseLong(CbsUtil.monthAdd(sdf.format(poIssueDate), 3));

            int compareChqDt = sdf.parse(sdf.format(poIssueDate)).compareTo(sdf.parse(sixMonthChqDt));
            if (compareChqDt <= 0) {
                longChqDt = Long.parseLong(CbsUtil.monthAdd(sdf.format(poIssueDate), 6));
            }
            if (longChqDt.compareTo(Long.parseLong(Tempbd)) < 0) {
                this.setPoError("Instrument Date is more than three months old.");
                poSeqIssuDtFlag = "true";
                return;
            }
            List listBrnNameAdd = txnRemote.getParentACode(billType, getOrgnBrCode());
            List billNat = txnRemote.strBillNature(this.billType);
            Vector vBillNat = (Vector) billNat.get(0);
            String bNature = vBillNat.get(0).toString();

            if (this.poMode.equalsIgnoreCase("CANCELLED")) {
                String billTable = getBillTable(bNature);
                List listBillTable = txnRemote.getPoDetails(billTable, this.poSeqNo, sdf.format(poIssueDate), getOrgnBrCode());
                if (listBillTable.isEmpty()) {
                    this.setPoError(this.billType + "No. Does Not Exist!");
                    poSeqIssuDtFlag = "true";
                    this.setPoSeqNo("");
                    this.setPoInstNo("");
                    this.setPoBrnchName("");
                    this.setPoBrnchAdd("");
                    this.setPoAmount(new BigDecimal("0"));
                    this.setPoComm(new BigDecimal("0"));
                    this.setInFavOf("");
                    this.setPoCustName("");
                    return;
                } else {
                    poSeqIssuDtFlag = "false";
                    Vector vBillTable = (Vector) listBillTable.get(0);
                    this.setPoCustName(vBillTable.get(0).toString());
                    this.setPoAmount(new BigDecimal(formatter.format(vBillTable.get(2))));
                    this.setInFavOf(vBillTable.get(3).toString());
                    this.setPoInstNo(vBillTable.get(6).toString());
                    this.setAlphaCode(vBillTable.get(4).toString());

                    if (!listBrnNameAdd.isEmpty()) {
                        Vector vBrnNameAdd = (Vector) listBrnNameAdd.get(0);
                        this.setPoBrnchName(vBrnNameAdd.get(1).toString());
                        this.setPoBrnchAdd(vBrnNameAdd.get(2).toString());
                    }
                    String dt = vBillTable.get(5).toString();
                    this.setPoIssueDate(sdf.parse(dt.substring(0, 4) + dt.substring(5, 7) + dt.substring(8, 10)));
                    //this.setPoIssueDate(sdf.parse(vBillTable.get(5).toString()));
                }
            } else if (this.poMode.equalsIgnoreCase("PAID") && bNature.equalsIgnoreCase("PO") || bNature.equalsIgnoreCase("DD")) {
                if (Float.parseFloat(this.poSeqNo) <= 0) {
                    this.setPoError("Please,Check the Sequence No.");
                    poSeqIssuDtFlag = "true";
                    return;
                }
                String billTable = getBillTable(bNature);
                List listBillPO = txnRemote.getPoDetails(billTable, this.poSeqNo, sdf.format(poIssueDate), getOrgnBrCode());
                if (listBillPO.isEmpty()) {
                    this.setPoError("SeqNo. Does Not Exist!");
                    poSeqIssuDtFlag = "true";
                    this.setPoSeqNo("");
                    this.setPoInstNo("");
                    this.setPoBrnchName("");
                    this.setPoBrnchAdd("");
                    this.setPoAmount(new BigDecimal("0"));
                    this.setPoComm(new BigDecimal("0"));
                    this.setInFavOf("");
                    this.setPoCustName("");
                    return;
                } else {
                    poSeqIssuDtFlag = "false";
                    Vector vBillPO = (Vector) listBillPO.get(0);
                    this.setPoCustName(vBillPO.get(0).toString());
                    this.setPoAmount(new BigDecimal(formatter.format(vBillPO.get(2))));
                    this.setInFavOf(vBillPO.get(3).toString());
                    this.setPoInstNo(vBillPO.get(6).toString());
                    this.setAlphaCode(vBillPO.get(4).toString());

                    if (!listBrnNameAdd.isEmpty()) {
                        Vector vBrnNameAdd = (Vector) listBrnNameAdd.get(0);
                        this.setPoBrnchName(vBrnNameAdd.get(1).toString());
                        this.setPoBrnchAdd(vBrnNameAdd.get(2).toString());
                    }
                    String dt = vBillPO.get(5).toString();
                    this.setPoIssueDate(sdf2.parse(dt.substring(8, 10) + "/" + dt.substring(5, 7) + "/" + dt.substring(0, 4)));
                }
                //check if po is exist in the batch
                if (ifPoExsitInXferBatch(bNature, getPoInstNo(), getPoIssueDate().getTime(), getPoAmount().doubleValue())) {
                    this.setPoError("This PO has been already added into the current batch.");
                    this.setPoSeqNo("");
                    this.setPoInstNo("");
                    this.setPoBrnchName("");
                    this.setPoBrnchAdd("");
                    this.setPoAmount(new BigDecimal("0"));
                    this.setPoComm(new BigDecimal("0"));
                    this.setInFavOf("");
                    this.setPoCustName("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setPoError(e.getMessage());
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }

    private boolean ifPoExsitInXferBatch(String type, String billNo, long issueDt, double billAmt) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (TxnDetailBean tmpBean : cashPOTxnBeanList) {
                String tmpDetails = tmpBean.getDetails();
                if (tmpDetails.indexOf("~`") > -1) {
                    String[] poEle = tmpDetails.split("~`");

                    long poDate = sdf.parse(poEle[10]).getTime();
                    String orgBr = poEle[5].substring(0, 2);

                    double poAmt = Double.parseDouble(poEle[8]);
                    if (getOrgnBrCode().equals(orgBr) && type.equalsIgnoreCase(poEle[4]) && billNo.equals(poEle[3])
                            && issueDt == poDate && billAmt == poAmt) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void poAmountLostFocus() {
        int payby = 0;
        poComm = new BigDecimal("0");
        this.setPoError("");
        poAmtFocus = "false";
        this.setPoError("");
        try {
            if (this.poAmount == null) {
                poAmtFocus = "true";
                return;
            }
            if (validatePOAmount() != true) {
                poAmtFocus = "true";
                return;
            }
            this.setPoError("");
            // amt = this.poAmount;
            if (this.poMode.equals("PAID")) {
                if (this.billType.equals("BC") || this.billType.equals("PO") || this.billType.equals("TPO")) {
                    return;
                } else {
                    return;
                }
            } else if (this.poMode.equals("ISSUED")) {
                if (Double.parseDouble(this.poAmount.toString()) != 0) {
                    if (this.poBy.equalsIgnoreCase("TRANSFER")) {
                        payby = 1;
                    } else if (this.poBy.equalsIgnoreCase("CASH")) {
                        payby = 0;
                    }
                    setPoComm(new BigDecimal(txnRemote.getCommission(Tempbd, this.billType, payby, Double.parseDouble(this.poAmount.toString()))));
                    if (payby == 0 && getPoComm().equals(new BigDecimal("0")) && !this.billType.equals("TPO")) {
                        this.setPoError("Amount slab not defined for " + this.billType + " bill type!!!");
                        // chkAmtSlab = true;
                        return;
                    } else {
                        poSaveFlag = "false";
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setPoError(e.getMessage());
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }
    private Map<String, Double> taxMap = new HashMap<String, Double>();

    public void txtCommLostFocus() {
        try {
            if (this.getPoComm() == null) {
                this.setPoComm(new BigDecimal("0"));
            }
            this.setPoError("");
            if (staxModuleActive && this.poMode.equals("ISSUED")) {
                if (Double.parseDouble(this.getPoComm().toString()) >= 0) {
                    taxMap = ibrFacade.getTaxComponent(Double.parseDouble(this.getPoComm().toString()), Tempbd);
                }
            } else if (this.poMode.equals("CANCELLED")) {
                saveCaptionFlag = "OK";
            }
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }

    public void infavofLostFocus() {
        try {
            this.setPoError("");
            if (this.inFavOf.equals("")) {
                infavOfFlag = "true";
                throw new ApplicationException("Please fill the In Favour Of");
            } else {
                infavOfFlag = "false";
            }
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }

    public void poCustNameLostFocus() {
        try {
            this.setPoError("");
            if (this.alphaCode.equals(" ")) {
                throw new ApplicationException("Please select alpha code.");
            }
            if (this.poMode.equals("CANCELLED") || this.poMode.equals("PAID")) {
                if (this.poInstNo.equals("")) {
                    throw new ApplicationException("Please fill the correct PO number.");
                }
                poSaveFlag = "false";
            }
            if (Double.parseDouble(this.getPoAmount().toString()) <= 0 || !this.getPoAmount().toString().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Amount should be numeric or greater than zero");
            } else if (this.inFavOf.equals("")) {
                throw new ApplicationException("Please fill the In Favour Of");
            } else if (this.inFavOf.contains("'")) {
                infavOfFlag = "true";
                throw new ApplicationException("Single Quote does not allow in InfavourOf");
            } else if (this.poCustName.equals("")) {
                throw new ApplicationException("Please fill the Customer Name");
            } else if (this.poCustName.contains("'")) {
                throw new ApplicationException("Single Quote does not allow in Customer Name");
            }
            loadBillGrid();
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }

    private double getTotalSTaxAmt() throws ApplicationException {
        try {
            double sTax = 0;
            Set<Entry<String, Double>> set = taxMap.entrySet();
            Iterator<Entry<String, Double>> it = set.iterator();
            while (it.hasNext()) {
                Entry entry = it.next();
                sTax = sTax + Double.parseDouble(entry.getValue().toString());
            }
            return sTax;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void loadBillGrid() {
        try {
            billTypeBeanList = new ArrayList<BillTypeBean>();
            billTypeGrid = new BillTypeBean();

            billTypeGrid.setBillType(billType);
            billTypeGrid.setAcNo(this.getAccNo());

            billTypeGrid.setPoAmount(new BigDecimal(this.getPoAmount().toString()));
            billTypeGrid.setInstNo("");

            billTypeGrid.setDetails(billType + " drawn on " + this.getAlphaCode() + " favouring " + this.getInFavOf());
            billTypeBeanList.add(billTypeGrid);

            if (this.poMode.equals("ISSUED") && Double.parseDouble(this.getPoComm().toString()) >= 0) {
                List list1 = txnRemote.checkCommFlag(billType);
                this.setPoError("");
                Vector v1 = (Vector) list1.get(0);
                actno = getOrgnBrCode() + v1.get(2).toString() + "01";

                billTypeGrid = new BillTypeBean();
                billTypeGrid.setBillType("");
                billTypeGrid.setAcNo(actno);

                billTypeGrid.setDetails(" Comm. for " + billType);
                billTypeGrid.setPoAmount(this.getPoComm());
                billTypeGrid.setInstNo("");

                billTypeBeanList.add(billTypeGrid);
                if (staxModuleActive) {
                    Set<Entry<String, Double>> set = taxMap.entrySet();
                    Iterator<Entry<String, Double>> it = set.iterator();

                    double sTax = getTotalSTaxAmt();
                    double diff = (Math.ceil(sTax) - sTax) / taxMap.size();

                    while (it.hasNext()) {
                        Entry entry = it.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        billTypeGrid = new BillTypeBean();
                        billTypeGrid.setBillType("");

                        billTypeGrid.setAcNo(getOrgnBrCode() + keyArray[1]);

                        billTypeGrid.setDetails(keyArray[0].toUpperCase());
                        billTypeGrid.setPoAmount(new BigDecimal(entry.getValue().toString()));

                        if (this.poBy.equalsIgnoreCase("CASH")) {
                            billTypeGrid.setPoAmount(new BigDecimal(Double.parseDouble(entry.getValue().toString()) + diff));
                        } else {
                            billTypeGrid.setPoAmount(new BigDecimal(entry.getValue().toString()));
                        }
                        billTypeGrid.setInstNo("");
                        billTypeBeanList.add(billTypeGrid);
                    }
                }
            } else if (this.poMode.equals("CANCELLED") || this.poMode.equals("PAID")) {
                billTypeGrid = new BillTypeBean();
                billTypeGrid.setBillType("Total Amt to be Paid");
                billTypeGrid.setAcNo("");
                billTypeGrid.setPoAmount(new BigDecimal(this.getPoAmount().toString()));

                billTypeGrid.setInstNo(this.getPoInstNo());
                billTypeGrid.setDetails("By Customer");
                billTypeBeanList.add(billTypeGrid);
            }
        } catch (Exception e) {
            this.setPoError(e.getMessage());
        }
    }

    public String getBillTable(String bNature) {
        if (bNature.equals("PO")) {
            return "bill_po";
        } else if (bNature.equals("TPO")) {
            return "bill_tpo";
        } else if (bNature.equals("AD")) {
            return "bill_ad";
        } else if (bNature.equals("DD")) {
            return "bill_dd";
        } else {
            return "bill_hoothers";
        }
    }

    public String saveBillTypeTxn() {
        try {
            NumberFormat formattter = new DecimalFormat("#.00");
            if (this.poMode.equals(" ")) {
                this.setPoError("Please select PO/DD mode.");
                return flag10 = "true";
            }
            if (this.billType.equals(" ")) {
                this.setPoError("Please select bill type.");
                return flag10 = "true";
            }
            if (this.poBy.equals("")) {
                this.setPoError("Please select pay by.");
                return flag10 = "true";
            }
            if (this.alphaCode.equals(" ")) {
                this.setPoError("Please select alpha code.");
                return flag10 = "true";
            }
            if (this.poAmount.toString().equals("0") || this.poAmount.toString().equals("0.0")) {
                this.setPoError("Please fill amount.");
                return flag10 = "true";
            }
            if (this.inFavOf.equals("")) {
                this.setPoError("Please fill in favour of.");
                return flag10 = "true";
            }
            if (this.inFavOf.contains("'")) {
                this.setPoError("Single Quote does not allow in InfavourOf");
                return flag10 = "true";
            }
            if (this.poCustName.equals("")) {
                this.setPoError("Please fill customer name.");
                return flag10 = "true";
            }
            if (this.poCustName.contains("'")) {
                this.setPoError("Single Quote does not allow in Customer Name");
                return flag10 = "true";
            }
            if (billTypeBeanList.isEmpty()) {
                this.setPoError("Data must be exist in the Right Side data table");
                return flag10 = "true";
            }
            /*          Code required for Invalid Sequence No.    */
            if (this.selectCrDr.equals("1")) {
                if (this.poSeqNo.equals("")) {
                    this.setPoError("Please fill sequence no.");
                    return flag10 = "true";
                } else if (this.poSeqNo == null) {
                    this.setPoError("Please fill sequence no.");
                    return flag10 = "true";
                } else if (this.poSeqNo.equals("0") || this.poSeqNo.equals("0.0")) {
                    this.setPoError("Please fill sequence no.");
                    return flag10 = "true";
                } else if (Double.parseDouble(this.poSeqNo) < 0) {
                    this.setPoError("Please fill sequence no.");
                    return flag10 = "true";
                } else if (poSeqIssuDtFlag.equalsIgnoreCase("true")) {
                    this.setPoError("Please check your sequence no.");
                    return flag10 = "true";
                }
            }
            /*         End of Code required for Invalid Sequence No.    */

            if (this.poMode.equals("CANCELLED") && this.poBy.equals("CLEARING")) {
                this.setPoError("Cancellation not allow in clearing!!!");
                return flag10 = "true";
            }
            if (commFlag.equals("none")) {
                setPoComm(new BigDecimal(0));
            }

            if (this.tranType.equals("0") && this.selectCrDr.equals("1")) {
                this.setAmountTxn(this.getPoAmount().toString());
                this.setChqNo(this.poInstNo);
                this.setChqueDate(this.getPoIssueDate());
                flag2 = "true";
                chqDateFlag = "true";
                chqNoFlag = "true";
            } else if (this.tranType.equals("2") && this.selectCrDr.equals("1")) {
                this.setAmountTxn(this.getPoAmount().toString());
                this.setChqNo(this.poInstNo);
                this.setChqueDate(this.getPoIssueDate());
                flag2 = "true";
                chqDateFlag = "true";
                chqNoFlag = "true";
            } else {
                BigDecimal tmpAmt = new BigDecimal(0);
                if (this.tranType.equals("0")) {
                    tmpAmt = this.getPoAmount().add(getPoComm()).add(new BigDecimal(Math.ceil(getTotalSTaxAmt())));
                } else {
                    tmpAmt = this.getPoAmount().add(getPoComm()).add(new BigDecimal(getTotalSTaxAmt()));
                }
                this.setDetails(this.billType + " DRAWN ON " + this.getAlphaCode() + " FAVOURING OF " + this.inFavOf);
                this.setAmountTxn(formattter.format(tmpAmt));
                this.setChqNo("");
                this.setChqueDate(this.getPoIssueDate());
                flag2 = "true";
                chqDateFlag = "false";
                chqNoFlag = "false";
            }
            return flag10 = "false";
        } catch (Exception e) {
            this.setPoError(e.getMessage());
            return flag10 = "true";
        }

    }

    private TxnDetailBean createTxnBeanObj() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        TxnDetailBean txnBean = new TxnDetailBean();

        txnBean.setAcNo(this.accNo);
        if (msgFlag == 4) {
            txnBean.setAmount(Double.parseDouble(this.getPoAmount().toString()));
        } else {
            txnBean.setAmount(Double.parseDouble(this.getAmountTxn().toString()));
        }

        if (this.selectCrDr.equals("0")) {
            txnBean.setTyDesc("CREDIT");
            if (msgFlag == 4) {
                txnBean.setCrAmt(Double.parseDouble(this.poAmount.toString()));
                txnBean.setDrAmt(0d);
            }

        } else if (this.selectCrDr.equals("1")) {
            txnBean.setTyDesc("DEBIT");
            if (msgFlag == 4) {
                txnBean.setDrAmt(Double.parseDouble(this.poAmount.toString()));
                txnBean.setCrAmt(0d);
            }

        }
        txnBean.setDestBrId(curBrnCode);

        if (msgFlag == 4) {
            txnBean.setDetails(this.getDetails());
        } else {
            txnBean.setDetails(this.getDetails());
        }

        txnBean.setDt(sdf.format(new Date()));
        txnBean.setEnterBy(this.getUserName());

        if (msgFlag == 4) {
            String poIssueTempDt = "";
            if (this.getPoIssueDate() != null) {
                poIssueTempDt = sdf.format(this.getPoIssueDate());
            }
            txnBean.setInstDt(poIssueTempDt);
        } else {
            if (this.by.equals("1") || this.by.equals("4")) {
                txnBean.setInstDt(sdf.format(this.chqueDate));
            } else {
                txnBean.setInstDt(null);
            }

        }

        if (msgFlag == 4) {
            txnBean.setInstNo(this.poInstNo);
        } else {
            txnBean.setInstNo(this.chqNo);
        }

        txnBean.setOrgnBrId(this.getOrgnBrCode());

        txnBean.setPayBy(this.by);
        txnBean.setTranDesc(this.relatedTo);
        txnBean.setTranType(this.tranType);

        if (this.tranType.equals("0")) {
            txnBean.setTranTypeDesc("CASH");
        } else if (this.tranType.equals("1")) {
            txnBean.setTranTypeDesc("CLEARING");
            txnBean.setScreenFlag("T");
        } else if (this.tranType.equals("2")) {
            txnBean.setTranTypeDesc("TRANSFER");
        }

        txnBean.setTy(this.selectCrDr);
        if (!this.tokenNumber.equals("")) {
            txnBean.setTokenNoDr(Float.valueOf(this.tokenNumber));
        } else {
            txnBean.setTokenNoDr(Float.valueOf("0"));
        }
        if (gCashMod.equals("Y")) {
            if (!this.subTokenNo.equals("")) {
                txnBean.setSubTokenNo(this.subTokenNo);
            } else {
                txnBean.setSubTokenNo("A");
            }
        }
        txnBean.setTdAcNo("");
        String valDt = this.valueDate.substring(6) + this.valueDate.substring(3, 5) + this.valueDate.substring(0, 2);
        txnBean.setValueDate(valDt);

        if (seqNo == null) {
            txnBean.setSqNo(0d);
        } else {
            txnBean.setSqNo(Double.parseDouble(this.getSeqNo()));
        }

        if (seqYear == null) {
            txnBean.setFinYear(0);
        } else {
            txnBean.setFinYear(Integer.parseInt(this.getSeqYear()));
        }
        return txnBean;
    }

    public String getPoDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

        if (this.getPoSeqNo() == null || this.getPoSeqNo().equals("")) {
            this.setPoSeqNo("0");
        }
        if (this.getPoInstNo() == null || this.getPoInstNo().equals("")) {
            this.setPoInstNo("");
        }
        String tempDt = "";
        if (this.getPoIssueDate() != null) {
            tempDt = sdf.format(this.getPoIssueDate());
        }
        List billNat = txnRemote.strBillNature(billType);
        Vector vBillNat = (Vector) billNat.get(0);
        String bNature = vBillNat.get(0).toString();
        if (bNature.equals("PO")) {
            setBillTypeNo("110");
        }
        if (bNature.equals("TPO")) {
            setBillTypeNo("120");
        }
        if (bNature.equals("AD")) {
            setBillTypeNo("130");
        }
        if (bNature.equals("DD")) {
            setBillTypeNo("140");
        }
        if (bNature.equals("SRF")) {
            setBillTypeNo("150");
        }
        String poDetails = "";
        try {
            recno = ftsPostRemote.getRecNo();
            poDetails = this.getDetails();
            poDetails = poDetails + "~`" + getBillTypeNo() + "~`" + this.getPoSeqNo() + "~`" + this.getPoInstNo() + "~`" + this.getBillType() + "~`" + this.getAccNo()
                    + "~`" + this.getPoCustName() + "~`" + this.getAlphaCode() + "~`" + this.getPoAmount() + "~`" + sdf.format(new Date()) + "~`"
                    + tempDt + "~`" + this.getPoMode() + "~`" + "90" + "~`" + this.getPoComm() + "~`" + this.getTranType()
                    + "~`" + this.getSelectCrDr() + "~`" + this.getInFavOf() + "~`" + this.getUserName() + "~`" + "" + "~`" + sdf1.format(new Date()) + "~`" + String.valueOf(recno);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return poDetails;
    }

    public void onblurPOAlphaCode() {
        try {
            if (!this.alphaCode.equals(" ")) {
                List list = txnRemote.getParentACode(billType, getOrgnBrCode());
                if (!list.isEmpty()) {
                    Vector v = (Vector) list.get(0);
                    this.setPoBrnchName(v.get(1).toString());
                    this.setPoBrnchAdd(v.get(2).toString() + " " + v.get(3).toString());
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void cancelClick() {
        resetFormExcludeXferGrid();
    }

    private void resetForm() {
        this.setAccNoMsg("");
        this.setAccNo("");
        this.setOldAcno("");
        this.setAccountName("");

        this.setChqFaci("");
        this.setJtName("");
        this.setOpMode("");
        this.setOpenDate("");

        this.setAdhoclimit(new BigDecimal("0.00"));
        this.setBalance(new BigDecimal("0.00"));
        this.setAccInstruction("");
        this.setPendingBalance(new BigDecimal("0.00"));

        this.setTranType(" ");
        this.setSelectCrDr(" ");
        this.setRelatedTo(" ");
        this.setDetails("");

        this.setBy("");
        this.setTokenNumber("0");
        this.setSubTokenNo("A");
        this.setChqNo("");
        this.setChqueDate(null);
        this.setValueDate(null);

        this.setAmountTxn("");
        this.setErrorMsg("");
        this.setMessage("");
        this.setLabelCrDr("");

        this.setChqNoFlag("false");
        this.setChqDateFlag("false");
        this.setFlag2("false");
        this.setNpaAcNo("");
        this.setNpaAcName("");
        this.setNewNpaAcNo("");
        this.setFocusId("ddRelated");
        setDisplayNPA("none");

        dataList = new ArrayList<TxnDetailBean>();
        cashPOTxnBeanList = new ArrayList<TxnDetailBean>();
        billTypeBeanList = new ArrayList<BillTypeBean>();

        this.setSumCr(new BigDecimal("0"));
        this.setSumDr(new BigDecimal("0"));
        this.setFlag6("false");

        this.setLimitDpLimitFlag("none");
        this.setLimitFlag("none");
        this.setDplimitFlag("none");

        this.setLimit(new BigDecimal("0"));
        this.setDpLimit(new BigDecimal("0"));
        this.setFlag3("none");   //New Changes

        this.setSeqNoYearFlag("none");
        this.setSeqNo("0");
        this.setSeqYear("0");
        this.setRelatedToVisibleFlag("false");
        tempXferList = new ArrayList<TxnDetailBean>();
        this.setPanConfirmMsg("");
        this.setBalConfirmMsg("");

        this.setCustId("");
        this.setAnnualTurnover("");
        this.setRiskCategorization("");
        this.setCustPanNo("");
        this.setProfession("");
        this.setCustAadharNo("");

        totalPoAmt = new BigDecimal(0);
        totalComm = new BigDecimal(0);
        totalStax = new BigDecimal(0);
        detailsBillType = "";
    }

    private void resetFormExcludeAccNo() {
        this.setAccNoMsg("");
        this.setAccountName("");
        this.setChqFaci("");

        this.setJtName("");
        this.setOpMode("");
        this.setOpenDate("");

        this.setAdhoclimit(new BigDecimal("0.00"));
        this.setBalance(new BigDecimal("0.00"));
        this.setAccInstruction("");
        this.setPendingBalance(new BigDecimal("0.00"));

        this.setTranType(" ");
        this.setSelectCrDr(" ");
        this.setRelatedTo(" ");
        this.setDetails("");
        this.setBy("");

        this.setTokenNumber("0");
        this.setSubTokenNo("A");
        this.setChqNo("");
        this.setChqueDate(null);
        this.setValueDate(null);
        this.setAmountTxn("");

        this.setErrorMsg("");
        this.setMessage("");
        this.setLabelCrDr("");

        this.setChqNoFlag("false");
        this.setChqDateFlag("false");
        this.setFlag2("false");

        dataList = new ArrayList<TxnDetailBean>();
        cashPOTxnBeanList = new ArrayList<TxnDetailBean>();
        billTypeBeanList = new ArrayList<BillTypeBean>();

        this.setSumCr(new BigDecimal("0"));
        this.setSumDr(new BigDecimal("0"));
        this.setFlag6("false");

        this.setLimitDpLimitFlag("none");
        this.setLimitFlag("none");
        this.setDplimitFlag("none");
        this.setLimit(new BigDecimal("0"));
        this.setDpLimit(new BigDecimal("0"));
        this.setFlag3("none");   //New Changes

        this.setSeqNoYearFlag("none");
        this.setSeqNo("0");
        this.setSeqYear("0");
        this.setRelatedToVisibleFlag("false");
        tempXferList = new ArrayList<TxnDetailBean>();
        this.setPanConfirmMsg("");

        this.setCustId("");
        this.setAnnualTurnover("");
        this.setRiskCategorization("");
        this.setCustPanNo("");
        this.setProfession("");
        this.setCustAadharNo("");
        this.setNpaAcNo("");
        this.setNpaAcName("");
        this.setNewNpaAcNo("");
        setDisplayNPA("none");
        this.setFocusId("ddRelated");

        cashDenominationObj.setDenominationTable(new ArrayList<DDSDenominationGrid>());
        cashDenominationObj.setDenoNo("");
        cashDenominationObj.setDenoValue("");
        cashDenominationObj.setErrorMessage1("");
        cashDenominationObj.setDisableDenoNo(false);
        cashDenominationObj.setTyDenoValue(" ");
        cashDenominationObj.setCurrencyAmount("0.00");
        cashDenominationObj.setAcDenoNat("");
        cashDenominationObj.setTyFlg("0");
        cashDenominationObj.setTyCaption("Recived/Return :");
    }

    private void resetFormExcludeXferGrid() {
        this.setAccNoMsg("");
        this.setAccNo("");
        this.setOldAcno("");
        this.setAccountName("");
        this.setChqFaci("");

        this.setJtName("");
        this.setOpMode("");
        this.setOpenDate("");
        this.setAdhoclimit(new BigDecimal("0.00"));

        this.setBalance(new BigDecimal("0.00"));
        this.setAccInstruction("");
        this.setPendingBalance(new BigDecimal("0.00"));
        this.setTranType(" ");

        this.setSelectCrDr(" ");
        this.setRelatedTo(" ");
        this.setDetails("");
        this.setBy("");
        this.setTokenNumber("0");
        this.setSubTokenNo("A");

        this.setChqNo("");
        this.setChqueDate(null);
        this.setAmountTxn("");
        this.setErrorMsg("");
        this.setMessage("");

        this.setLabelCrDr("");
        this.setChqNoFlag("false");
        this.setChqDateFlag("false");

        this.setFlag2("false");
        billTypeBeanList = new ArrayList<BillTypeBean>();

        this.setFlag6("false");

        this.setLimitDpLimitFlag("none");
        this.setLimitFlag("none");
        this.setDplimitFlag("none");
        this.setLimit(new BigDecimal("0"));
        this.setDpLimit(new BigDecimal("0"));
        this.setFlag3("none");   //New Changes

        this.setSeqNoYearFlag("none");
        this.setSeqNo("0");
        this.setSeqYear("0");
        this.setRelatedToVisibleFlag("false");
        tempXferList = new ArrayList<TxnDetailBean>();
        this.setPanConfirmMsg("");
        this.setBalConfirmMsg("");

        this.setCustId("");
        this.setAnnualTurnover("");
        this.setRiskCategorization("");
        this.setCustPanNo("");
        this.setProfession("");
        this.setCustAadharNo("");
        this.setNpaAcNo("");
        this.setNpaAcName("");
        this.setNewNpaAcNo("");
        setDisplayNPA("none");
        this.setFocusId("ddRelated");
        intCalc = new ArrayList<IntCalTable>();

        cashDenominationObj.setDenominationTable(new ArrayList<DDSDenominationGrid>());
        cashDenominationObj.setDenoNo("");
        cashDenominationObj.setDenoValue("");
        cashDenominationObj.setErrorMessage1("");
        cashDenominationObj.setDisableDenoNo(false);
        cashDenominationObj.setTyDenoValue(" ");
        cashDenominationObj.setCurrencyAmount("0.00");
        cashDenominationObj.setAcDenoNat("");
        cashDenominationObj.setTyFlg("0");
        cashDenominationObj.setTyCaption("Recived/Return :");
    }

    public void cancelBillTypeTxn() {
        try {
            if (billTypeBeanList != null) {
                billTypeBeanList = new ArrayList<BillTypeBean>();
            }
            this.setBillType(" ");
            this.setPoMode(" ");

            this.setAlphaCode(" ");
            this.setPoBrnchName("");
            this.setPoBrnchAdd("");

            this.setPoAmount(new BigDecimal("0"));
            this.setPoComm(new BigDecimal("0"));
            this.setPoSeqNo("");

            this.setPoInstNo("");
            this.setPoIssueDate(new Date());
            this.setPoBy("");

            this.setInFavOf("");
            this.setPoCustName("");
            this.setPoError("");

            this.setPoAccNum("");
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void resetXferDetails() {
        this.setErrorMsg("");
    }

    public void cancelXferBatch() {
        try {
            if (!dataList.isEmpty()) {
                resetForm();
                this.setFlag5("true");
                this.setMessage("Batch has cancelled.");
            } else {
                resetForm();
                this.setFlag5("true");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void delete() {
        try {
            this.setSumCr(this.sumCr.subtract(new BigDecimal(currentItem.getCrAmt())));
            this.setSumDr(this.sumDr.subtract(new BigDecimal(currentItem.getDrAmt())));
            dataList.remove(currentRow);
            cashPOTxnBeanList.remove(currentRow);// Change made by Nishant Kansal on Date 15/02/2011
            tempXferList.remove(currentRow);// Change made by Nishant Kansal on Date 17/09/2011
            if (Double.parseDouble(this.sumCr.toString()) == Double.parseDouble(this.sumDr.toString())) {
                flag5 = "false";
                flag6 = "true";
            } else {
                flag5 = "true";
                flag6 = "false";
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void accTypeNatureDesc() {
        try {
            AccountTypeDescPojo pojo;
            accTypeDescList = new ArrayList<AccountTypeDescPojo>();
            List resultList = ftsPostRemote.getAcctTypeDesc();

            for (Object object : resultList) {
                Vector element = (Vector) object;
                pojo = new AccountTypeDescPojo();

                pojo.setAcctCode((String) element.get(0));
                pojo.setAccType((String) element.get(1));

                pojo.setAccNature((String) element.get(2));
                pojo.setAccDesc((String) element.get(3));
                accTypeDescList.add(pojo);
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void stopSingleQuote() {
        this.setErrorMsg("");
        int singleQuote = this.details.indexOf("'");
        if (singleQuote != -1) {
            this.setErrorMsg("Single Quote is not allowed in the details !");
            return;
        }
        String chMsg = valMemDtl();
        if (!chMsg.equalsIgnoreCase("true")) {
            this.setErrorMsg(chMsg);
            return;
        }
    }

    public String valMemDtl() {
        String memMsg = "true";
        try {
            String acChk = ftsPostRemote.getAcnoByPurpose("MIR-HEAD");
            if (!acChk.equalsIgnoreCase("")) {
                if (acChk.equalsIgnoreCase(accNo.substring(2)) && (this.selectCrDr.equalsIgnoreCase("1"))) {
                    if (this.details.equalsIgnoreCase("")) {
                        memMsg = "Please Fill Details, Starting With Member No !";
                    } else {
                        String memAc = this.details.substring(0, 12);
                        List resultList = acFacade.memInfo(memAc);
                        if (resultList.isEmpty()) {
                            memMsg = "Either Member No is not Exist or You Have Filled Wrong Member No !";
                        }
                    }
                }
            }
        } catch (ApplicationException ex) {
            setErrorMsg(ex.getMessage());
        }
        return memMsg;
    }

    public String exit() {
        resetForm();
        return "case1";
    }

    public void checkDuplicateToken() {
        try {
            if (gCashMod.equals("Y") && tranType.equals("0") && selectCrDr.equals("1")) {
                if (!this.tokenNumber.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                    duplicateToken = "true";
                    this.setErrorMsg("Please enter numeric value in Token No.");
                    return;
                }
                if (this.tokenNumber.contains(".")) {
                    duplicateToken = "true";
                    this.setErrorMsg("Decimal is not allowed in Token No.");
                    return;
                }
                if (Double.parseDouble(this.tokenNumber) < 0) {
                    duplicateToken = "true";
                    this.setErrorMsg("Token No can not be negative");
                    return;
                }
                if (tokenNumber.equals("") || tokenNumber.equals("0") || subTokenNo.equals("")) {
                    duplicateToken = "true";
                    setErrorMsg("Please fill the Token and Sub Token number.");
                    return;
                }
                String result = ftsPostRemote.checkDuplicateToken(tokenNumber, subTokenNo, getOrgnBrCode(), Tempbd);
                if (!result.equals("True")) {
                    duplicateToken = "true";
                    setErrorMsg(result);
                } else {
                    duplicateToken = "false";
                    setErrorMsg("");
                }
            } else {
                duplicateToken = "false";
            }
        } catch (ApplicationException ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public String rdInstallmentNotAllowedByOtherMode() {
        String result = "true";
        try {
            String acNature = ftsPostRemote.getAccountNature(accNo);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && this.selectCrDr.equalsIgnoreCase("0")) {
                if (this.relatedTo.equalsIgnoreCase("1") || this.relatedTo.equalsIgnoreCase("3")) {
                    result = "true";
                } else {
                    result = "false";
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public String checkOfficeAccount() {
        String result = "true";
        Integer messageFlag = 0;
        try {
            String officeAccountNo = ftsPostRemote.getOfficeAccountNo(accNo);
            if (officeAccountNo.equalsIgnoreCase("true")) {
                if (this.tranType.equals("1")) {
                    return "Clearing transaction is not allowed for office account.";
                }
                List headDataList = ftsPostRemote.getOfficeHeadDetails(accNo);
                if (!headDataList.isEmpty()) {
                    Vector element = (Vector) headDataList.get(0);
                    messageFlag = Integer.parseInt(element.get(2).toString());
                } else {
                    return "Head is not defined in gltable.";
                }
                if (messageFlag == 2) {
                    if (this.selectCrDr.equalsIgnoreCase("1")) {
                        result = "Debit transaction is not allowed for this head.";
                    }
                }
                if (messageFlag == 1) {
                    if (this.selectCrDr.equalsIgnoreCase("0")) {
                        result = "Credit transaction is not allowed for this head.";
                    }
                }
                if (messageFlag == 3) {
                    if (!this.getOrgnBrCode().equalsIgnoreCase(ftsPostRemote.getCurrentBrnCode(accNo))) {
                        if (this.selectCrDr.equalsIgnoreCase("1")) {
                            result = "Debit transaction is not allowed for this head from other branch.";
                        }
                    } else {
                        if (this.selectCrDr.equalsIgnoreCase("0")) {
                            result = "Credit transaction is not allowed for this head from base branch.";
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public String checkDdAccount() {
        String result = "true";
        Integer messageFlag = 0;
        try {
            int ddParam = 0;
            List chDd = ftsPostRemote.chkDdflag();
            if (!(chDd == null || chDd.isEmpty())) {
                Vector verLst = (Vector) chDd.get(0);
                ddParam = Integer.parseInt(verLst.get(0).toString());
            }
            if (ddParam == 1) {
                String ddAccountNo = ftsPostRemote.getDdAccountNo(accNo);
                if (ddAccountNo.equalsIgnoreCase("true")) {
                    if (this.tranType.equals("1")) {
                        return "Clearing transaction is not allowed for DD account.";
                    }
                    List headDataList = ftsPostRemote.getOfficeHeadDetails(accNo);
                    if (!headDataList.isEmpty()) {
                        Vector element = (Vector) headDataList.get(0);
                        messageFlag = Integer.parseInt(element.get(2).toString());
                    } else {
                        return "Head is not defined in gltable.";
                    }
                    if (messageFlag == 0) {
                        if (this.selectCrDr.equalsIgnoreCase("1")) {
                            result = "Debit transaction is not allowed for this head.";
                        }
                    }
                    if (messageFlag == 1) {
                        if (this.selectCrDr.equalsIgnoreCase("0")) {
                            result = "Credit transaction is not allowed for this head.";
                        }
                    }
                    if (messageFlag == 3) {
                        if (!this.getOrgnBrCode().equalsIgnoreCase(ftsPostRemote.getCurrentBrnCode(accNo))) {
                            if (this.selectCrDr.equalsIgnoreCase("1")) {
                                result = "Debit transaction is not allowed for this head from other branch.";
                            }
                        } else {
                            if (this.selectCrDr.equalsIgnoreCase("0")) {
                                result = "Credit transaction is not allowed for this head from base branch.";
                            }
                        }
                    }
                    if (messageFlag == 4) {
                        if (this.getOrgnBrCode().equalsIgnoreCase(ftsPostRemote.getCurrentBrnCode(accNo))) {
                            result = "Credit/Debit transaction is not allowed for this head from base branch.";
                        } else if (!this.getOrgnBrCode().equalsIgnoreCase(ftsPostRemote.getCurrentBrnCode(accNo)) && !ftsPostRemote.getCurrentBrnCode(accNo).equalsIgnoreCase("90")) {
                            result = "Credit/Debit transaction is not allowed for this head from Other branch.";
                        } else if (!this.getOrgnBrCode().equalsIgnoreCase(ftsPostRemote.getCurrentBrnCode(accNo)) && ftsPostRemote.getCurrentBrnCode(accNo).equalsIgnoreCase("90")) {
                            if (this.selectCrDr.equalsIgnoreCase("1")) {
                                result = "Debit transaction is not allowed for this head from Other branch.";
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public String checkDuplicatePayOrderPayment(String passedAccount, String drCrType, String tempSeqNo) {
        String result = "true";
        try {
            String acNature = ftsPostRemote.getAccountNature(passedAccount);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) && ftsPostRemote.isPoDdGlhead(passedAccount.substring(2, 10)) && drCrType.equalsIgnoreCase("1")) {
                List resultList = txnRemote.getUnAuthPayOrderPayment(passedAccount);
                if (!resultList.isEmpty()) {
                    if (tempSeqNo == null || tempSeqNo.equals("")) {
                        tempSeqNo = "";
                    }
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector element = (Vector) resultList.get(i);
                        String[] detailsArray = element.get(0).toString().split("~`");
                        if (detailsArray.length > 2) {
                            if (detailsArray[2].trim().equalsIgnoreCase(tempSeqNo)) {
                                return result = "false";
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public String validateInstNoDtAgainstOpenDt(Date chequeDt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        String result = "true";
        try {
            String acNature = ftsPostRemote.getAccountNature(accNo);
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                if (this.selectCrDr.equalsIgnoreCase("1") && (this.by.equalsIgnoreCase("1") || this.by.equalsIgnoreCase("4"))) {
                    Long longChqDate = Long.parseLong(sdf.format(chqueDate));
                    if (longChqDate.compareTo(Long.parseLong(sdf.format(dmy.parse(this.openDate)))) < 0) {
                        result = "false";
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public void intDetails() {
        try {
            if (((AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (AcNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                    || (AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)))) {
                if (!(accNo.equalsIgnoreCase("") || accNo == null)) {
                    intCalc = new ArrayList<IntCalTable>();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    int intPostOnDeposit = 0;
                    intPostOnDeposit = Integer.parseInt(ftsPostRemote.getCodeByReportName("INT_POST_DEPOSIT"));
                    if (intPostOnDeposit == 1) {
                        LoanIntCalcList resultList = beanRemote.txnLoanIntCalc(ymmd.format(new Date()), accNo, getOrgnBrCode());
                        if (resultList == null) {
                            throw new ApplicationException("Data does not exist");
                        }
                        IntCalTable mt = new IntCalTable();
                        mt.setsNo(Integer.toString(1));
                        mt.setAcNo(resultList.getAcNo());
                        mt.setFirstDt(sdf.format(ymmd.parse(resultList.getFirstDt())));
                        mt.setLastDt(sdf.format(ymmd.parse(resultList.getLastDt())));
                        mt.setRoi(new BigDecimal(formatter.format(resultList.getRoi())));
                        mt.setTotalInt(new BigDecimal(formatter.format(resultList.getTotalInt() * -1)));
                        intCalc.add(mt);
                    }
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    private int getIndexOfNPACharges(List<SelectItem> dataList) throws ApplicationException {
        try {
            int index = -1;
            for (int i = 0; i < dataList.size(); i++) {
                SelectItem si = dataList.get(i);
                if (si.getValue().toString().equals("102")) {
                    index = i;
                    break;
                }
            }
            return index;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void selectGlHeadOnPressF6() {
        try {
            // Added by manish kumar
            String query = "";
            List listF6 = new ArrayList();
            searchTypeList = new ArrayList<SelectItem>();
            searchTypeList.add(new SelectItem("Select", "--Select--"));
            searchTypeList.add(new SelectItem("name", "Name"));
            searchTypeList.add(new SelectItem("acno", "Account No."));
            this.searchType = "";
            this.searchValue = "";
            query = "select AcName,acno from gltable where substring(acno,1,2)='" + getOrgnBrCode() + "' order by AcName";
            listF6 = txnRemote.getDataForF6(query);
            //List listF6 = txnRemote.getDataForF6(getOrgnBrCode()); // old code
//            if(listForF6!=null){
//                listForF6.clear();
//            }

            if (listF6.isEmpty()) {
                throw new ApplicationException("No Data For GL Head.");
            }
            this.setErrorMsg("");
            listForF6 = new ArrayList<GLHeadGrid>();

            for (int i = 0; i < listF6.size(); i++) {
                glHeadGrid = new GLHeadGrid();
                Vector vecF6 = (Vector) listF6.get(i);
                glHeadGrid.setAccName(vecF6.get(0).toString());
                glHeadGrid.setGlhead(vecF6.get(1).toString());
                listForF6.add(glHeadGrid);
            }
            listForF6Temp = listForF6;
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }
    // Added by Manish kumar

    public void setSearchLabelValue() {
        this.searchValue = "";
        if (this.searchType.equalsIgnoreCase("name")) {
            this.setSearchLabel("Enter Name");
        } else {
            this.setSearchLabel("Enter Account No.");
        }
    }

    public void searchedItem() {
        String query = "";
        List listF6 = new ArrayList();
        try {
            if (this.searchType.equalsIgnoreCase("name") && (!this.searchValue.equalsIgnoreCase("") || this.searchValue != null)) {
                query = "select AcName,acno from gltable where substring(acno,1,2)='" + getOrgnBrCode() + "' and AcName like '" + this.searchValue + "%' order by AcName";
                listF6 = txnRemote.getDataForF6(query);
            }
            if (this.searchType.equalsIgnoreCase("acno") && (!this.searchValue.equalsIgnoreCase("") || this.searchValue != null)) {
                if (this.searchValue.length() <= 12) {
                    query = "select AcName,acno from gltable where substring(acno,1,2)='" + getOrgnBrCode() + "' and acno like '" + this.searchValue + "%' order by AcName";
                    listF6 = txnRemote.getDataForF6(query);
                } else {
                    this.setErrorMsg("Account number maximum 12 digit allowed !");
                    return;
                }

            }
            if (listF6.isEmpty()) {
                throw new ApplicationException("No Data For GL Head.");
            }
            this.setErrorMsg("");
            listForF6 = new ArrayList<GLHeadGrid>();

            for (int i = 0; i < listF6.size(); i++) {
                glHeadGrid = new GLHeadGrid();
                Vector vecF6 = (Vector) listF6.get(i);
                glHeadGrid.setAccName(vecF6.get(0).toString());
                glHeadGrid.setGlhead(vecF6.get(1).toString());
                listForF6.add(glHeadGrid);
            }
            listForF6Temp = listForF6;
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }
    //---

    public void otherActDetails() {
        try {
            if (!(accNo.equalsIgnoreCase("") || accNo == null)) {
                otherActList = new ArrayList<TdReceiptIntDetailPojo>();

                List dataList = txnRemote.getOtherActDetails(accNo);
                if (dataList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                TdReceiptIntDetailPojo pojo;

                for (int i = 0; i < dataList.size(); i++) {
                    Vector vect = (Vector) dataList.get(i);

                    pojo = new TdReceiptIntDetailPojo();

                    pojo.setCustId(vect.get(0).toString());
                    pojo.setAcNo(vect.get(1).toString());
                    pojo.setName(vect.get(2).toString());

                    otherActList.add(pojo);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void selectAlertType() {
        this.setErrorMsg("");
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            alertTypeList = new ArrayList<SelectItem>();
            alertTypeList.add(new SelectItem(" ", "--Select--"));
            List l3 = aitr.getListAsPerRequirement("350", "0", "0", "0", "0", "0", ymd.format(new Date()), 0);
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                //if(v3.get(0).toString().equalsIgnoreCase("3") || v3.get(0).toString().equalsIgnoreCase("8")){
                alertTypeList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
                //}
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onAlertCode() {
        this.setErrorMsg("");
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            alertCodeList = new ArrayList<SelectItem>();
            alertCodeList.add(new SelectItem(" ", "--Select--"));
            List l3 = aitr.getListAsPerRequirement("351", getAlertType(), "0", "0", "0", "0", ymd.format(new Date()), 0);
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                if (v3.get(0).toString().equalsIgnoreCase("EI") || v3.get(0).toString().equalsIgnoreCase("TM")) {
                    alertCodeList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onAlertCodeSub() {
        this.setErrorMsg("");
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            alertSubCodeList = new ArrayList<SelectItem>();
            alertSubCodeList.add(new SelectItem(" ", "--Select--"));
            List l3 = aitr.getListAsPerRequirement("352", getAlertType(), getAlertCode(), "0", "0", "0", ymd.format(new Date()), 0);
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                alertSubCodeList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onAlertCodeSubDesc() {
        this.setErrorMsg("");
        this.subCodeDesc = this.getAlertSubCode();
    }

    public void onStrAlertMod(String flag) {
        if (flag.equalsIgnoreCase("Y")) {
            if (alertSubCode.contains("!")) {
                alertSubCode = alertSubCode.concat("!Y");
            }
        } else if (flag.equalsIgnoreCase("N")) {
            if (alertSubCode.contains("!")) {
                alertSubCode = alertSubCode.concat("!N");
            }
        }
    }
    private BigDecimal totalPoAmt = new BigDecimal(0);
    private BigDecimal totalComm = new BigDecimal(0);
    private BigDecimal totalStax = new BigDecimal(0);
    private String detailsBillType = "";
    private int msgFlag;
    private List<GLHeadGrid> listForF6;
    private List<GLHeadGrid> listForF6Temp;
    private GLHeadGrid glHeadGrid;
    private String errorMsg;
    private List<SelectItem> tranTypeList;
    private String tranType;
    private List<SelectItem> crDrList;
    private String selectCrDr;
    private List<SelectItem> relatedToList;
    private String relatedTo;
    private List<SelectItem> chargeDtlList;
    private String chargeDtl;
    private List<SelectItem> byList;
    private String by;
    private String accNo;
    private int TmpPostFlag;
    // private String mydate;
    private String Tempbd;
    private String message;
    private String accNoMsg;
    private String AcNature;
    private String limitDpLimitFlag = "none";
    private String limitFlag = "none";
    private String dplimitFlag = "none";
    private List<LoanDisbursementGrid> loanDisbGridList;
    private LoanDisbursementGrid loanDisbGrid;
    private String loanDisbFlag;
    private String flag1;
    private String accountName;
    private String flag2;
    private int AcFlag = 1;
    private String flag3 = "none";
    private BigDecimal limit = new BigDecimal("0");
    private BigDecimal dpLimit;
    private BigDecimal balance;
    private BigDecimal pendingBalance;
    private BigDecimal adhoclimit;
    private String chqFacility;
    private String jtName;
    private String opMode;
    private String openDate;
    private String accInstruction;
    private int TrnType;
    private String flag4 = "none";
    private String flag5 = "true";
    private String chqFocus;
    private String tokenFocus;
    private String amountFocus;
    private String amountTxn;
    private String amountTxnFocus;
    private int ty;
    private Date chqueDate;
    private String valueDate;
    private String chqDateFocus;
    private int PaymentBy;
    private String chqNoFlag;
    private String chqDateFlag;
    private String chqNo;
    private String tokenFlag = "none";
    private boolean msgModuleActive;
    private String newAccNo;
    private String labelCrDr;
    private String chqFaci;
    private String seqNoYearFlag = "none";
    private String tranTypeVisibility = "false";
    private String seqNoYearFocus = "false";
    private String Sundrytable;
    private String panamount;
    private String panRepFlag;
    private boolean ValidationFlag;
    private String seqNo = "0";
    private String seqYear = "0";
    private boolean ccodExpiryFlag;
    private String details;
    private String gCashMod;
    private String imageData;
    private boolean poMultipleEntry;
    private List<PayOrderOutstandingGrid> poList;
    private PayOrderOutstandingGrid poGrid;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    private List<StopPaymentGrid> stopPayList;
    private StopPaymentGrid stopPayGrid;
    private String poddFlag = "false";
    private String billType;
    private List<SelectItem> billTypeList;
    private String alphaCode;
    private List<SelectItem> alphaCodeList;
    private String poMode;
    private List<SelectItem> poModeList;
    private String poBy;
    private String poError;
    private String commFlag = "none";
    private String parentACode;
    private String seqNoFlag = "none";
    private String poInstNoFlag = "none";
    private String poIssueDtFlag = "none";
    private String saveCaptionFlag = "Save";
    private Date poIssueDate;
    private String poSeqNo;
    private String poInstNo;
    private String poBrnchName;
    private String poBrnchAdd;
    private BigDecimal poAmount;
    private BigDecimal poComm;
    private String inFavOf;
    private String poCustName;
    private String poSaveFlag = "false";
    private String infavOfFlag;
    private boolean staxModuleActive;
    private String poAmtFocus;
    private String actno;
    private BillTypeBean billTypeGrid;
    private List<BillTypeBean> billTypeBeanList;
    private String tokenNumber;
    private String subTokenNo;
    private String duplicateToken = "false";
    private List<TxnDetailBean> dataList;
    private String poAccNum;
    private String poAccNumFocus;
    private BigDecimal sumCr;
    private BigDecimal sumDr;
    List<TxnDetailBean> cashPOTxnBeanList;
    private String flag6;
    private String poSeqIssuDtFlag;
    private String flag10;
    private List<BillTypeBean> billTypePOBeanList = new ArrayList<BillTypeBean>();
    private TxnDetailBean currentItem = new TxnDetailBean();
    private int currentRow;
    private BigDecimal openBalance;
    private String billTypeNo;
    private BigDecimal presentBalance;
    private Float recno;
    private String ccodMsg;
    private BigDecimal sundryAmt;
    private String relatedToVisibleFlag = "false";
    private List<TxnDetailBean> tempXferList;
    private String valueDatedFlag;
    private String oldAcno;
    private String curBrnCode;
    private String panConfirmMsg;
    private String balConfirmMsg;
    private String balConfirm;
    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String totalDebit;
    private String intDtlVar;
    private String pymtAlertMsg;
    private boolean pollEnabled;
    private long pollInterval;
    private String lblAlpha;
    private List<TdReceiptIntDetailPojo> otherActList;
    private List<AccountTypeDescPojo> accTypeDescList;
    private List<IntCalTable> intCalc;
    private String alertType;
    private List<SelectItem> alertTypeList;
    private String alertCode;
    private List<SelectItem> alertCodeList;
    private String alertSubCode;
    private List<SelectItem> alertSubCodeList;
    private String subCodeDesc;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameTxnAuth = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameStrPost = "StrAlertFacade";
    private StrAlertFacadeRemote strAlertRemote = null;
    private final String jndiAdvancesPost = "AdvancesInformationTrackingFacade";
    private AdvancesInformationTrackingRemote aitr = null;
    private final String jndiAcFacade = "AccountOpeningFacade";
    private AccountOpeningFacadeRemote acFacade = null;
    private final String jndiLoanName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private InterBranchTxnFacadeRemote ibrFacade = null;
    private final String jndiIbrName = "InterBranchTxnFacade";
    String viewID = "/pages/master/sm/test.jsp";
    private boolean popUpFlag;
    private String acNoLen;
    private String custAadharNo;
    private String availLimit;
    private String acctCategory;

    public String getAvailLimit() {
        return availLimit;
    }

    public void setAvailLimit(String availLimit) {
        this.availLimit = availLimit;
    }

    public List<TdReceiptIntDetailPojo> getOtherActList() {
        return otherActList;
    }

    public void setOtherActList(List<TdReceiptIntDetailPojo> otherActList) {
        this.otherActList = otherActList;
    }

    public long getPollInterval() {
        return pollInterval;
    }

    public void setPollInterval(long pollInterval) {
        this.pollInterval = pollInterval;
    }

    public boolean isPollEnabled() {
        return pollEnabled;
    }

    public void setPollEnabled(boolean pollEnabled) {
        this.pollEnabled = pollEnabled;
    }

    public String getPymtAlertMsg() {
        return pymtAlertMsg;
    }

    public void setPymtAlertMsg(String pymtAlertMsg) {
        this.pymtAlertMsg = pymtAlertMsg;
    }

    public String getIntDtlVar() {
        return intDtlVar;
    }

    public void setIntDtlVar(String intDtlVar) {
        this.intDtlVar = intDtlVar;
    }

    public String getBalConfirm() {
        return balConfirm;
    }

    public void setBalConfirm(String balConfirm) {
        this.balConfirm = balConfirm;
    }

    public String getBalConfirmMsg() {
        return balConfirmMsg;
    }

    public void setBalConfirmMsg(String balConfirmMsg) {
        this.balConfirmMsg = balConfirmMsg;
    }

    public String getPanConfirmMsg() {
        return panConfirmMsg;
    }

    public void setPanConfirmMsg(String panConfirmMsg) {
        this.panConfirmMsg = panConfirmMsg;
    }

    public String getRelatedToVisibleFlag() {
        return relatedToVisibleFlag;
    }

    public void setRelatedToVisibleFlag(String relatedToVisibleFlag) {
        this.relatedToVisibleFlag = relatedToVisibleFlag;
    }

    public BigDecimal getSundryAmt() {
        return sundryAmt;
    }

    public void setSundryAmt(BigDecimal sundryAmt) {
        this.sundryAmt = sundryAmt;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getBillTypeNo() {
        return billTypeNo;
    }

    public void setBillTypeNo(String billTypeNo) {
        this.billTypeNo = billTypeNo;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public String getDuplicateToken() {
        return duplicateToken;
    }

    public void setDuplicateToken(String duplicateToken) {
        this.duplicateToken = duplicateToken;
    }

    public TxnDetailBean getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TxnDetailBean currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<BillTypeBean> getBillTypePOBeanList() {
        return billTypePOBeanList;
    }

    public void setBillTypePOBeanList(List<BillTypeBean> billTypePOBeanList) {
        this.billTypePOBeanList = billTypePOBeanList;
    }

    public String getFlag10() {
        return flag10;
    }

    public void setFlag10(String flag10) {
        this.flag10 = flag10;
    }

    public String getPoSeqIssuDtFlag() {
        return poSeqIssuDtFlag;
    }

    public void setPoSeqIssuDtFlag(String poSeqIssuDtFlag) {
        this.poSeqIssuDtFlag = poSeqIssuDtFlag;
    }

    public String getFlag6() {
        return flag6;
    }

    public void setFlag6(String flag6) {
        this.flag6 = flag6;
    }

    public List<TxnDetailBean> getCashPOTxnBeanList() {
        return cashPOTxnBeanList;
    }

    public void setCashPOTxnBeanList(List<TxnDetailBean> cashPOTxnBeanList) {
        this.cashPOTxnBeanList = cashPOTxnBeanList;
    }

    public String getFlag5() {
        return flag5;
    }

    public void setFlag5(String flag5) {
        this.flag5 = flag5;
    }

    public BigDecimal getSumCr() {
        return sumCr;
    }

    public void setSumCr(BigDecimal sumCr) {
        this.sumCr = sumCr;
    }

    public BigDecimal getSumDr() {
        return sumDr;
    }

    public void setSumDr(BigDecimal sumDr) {
        this.sumDr = sumDr;
    }

    public String getPoAccNumFocus() {
        return poAccNumFocus;
    }

    public void setPoAccNumFocus(String poAccNumFocus) {
        this.poAccNumFocus = poAccNumFocus;
    }

    public String getPoAccNum() {
        return poAccNum;
    }

    public void setPoAccNum(String poAccNum) {
        this.poAccNum = poAccNum;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getSubTokenNo() {
        return subTokenNo;
    }

    public void setSubTokenNo(String subTokenNo) {
        this.subTokenNo = subTokenNo;
    }

    public int getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(int msgFlag) {
        this.msgFlag = msgFlag;
    }

    public GLHeadGrid getGlHeadGrid() {
        return glHeadGrid;
    }

    public void setGlHeadGrid(GLHeadGrid glHeadGrid) {
        this.glHeadGrid = glHeadGrid;
    }

    public List<GLHeadGrid> getListForF6() {
        return listForF6;
    }

    public void setListForF6(List<GLHeadGrid> listForF6) {
        this.listForF6 = listForF6;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getChargeDtl() {
        return chargeDtl;
    }

    public void setChargeDtl(String chargeDtl) {
        this.chargeDtl = chargeDtl;
    }

    public List<SelectItem> getChargeDtlList() {
        return chargeDtlList;
    }

    public void setChargeDtlList(List<SelectItem> chargeDtlList) {
        this.chargeDtlList = chargeDtlList;
    }

    public List<SelectItem> getCrDrList() {
        return crDrList;
    }

    public void setCrDrList(List<SelectItem> crDrList) {
        this.crDrList = crDrList;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public List<SelectItem> getByList() {
        return byList;
    }

    public void setByList(List<SelectItem> byList) {
        this.byList = byList;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    public List<SelectItem> getRelatedToList() {
        return relatedToList;
    }

    public void setRelatedToList(List<SelectItem> relatedToList) {
        this.relatedToList = relatedToList;
    }

    public String getSelectCrDr() {
        return selectCrDr;
    }

    public void setSelectCrDr(String selectCrDr) {
        this.selectCrDr = selectCrDr;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccNoMsg() {
        return accNoMsg;
    }

    public void setAccNoMsg(String accNoMsg) {
        this.accNoMsg = accNoMsg;
    }

    public String getDplimitFlag() {
        return dplimitFlag;
    }

    public void setDplimitFlag(String dplimitFlag) {
        this.dplimitFlag = dplimitFlag;
    }

    public String getLimitFlag() {
        return limitFlag;
    }

    public void setLimitFlag(String limitFlag) {
        this.limitFlag = limitFlag;
    }

    public String getLimitDpLimitFlag() {
        return limitDpLimitFlag;
    }

    public void setLimitDpLimitFlag(String limitDpLimitFlag) {
        this.limitDpLimitFlag = limitDpLimitFlag;
    }

    public LoanDisbursementGrid getLoanDisbGrid() {
        return loanDisbGrid;
    }

    public void setLoanDisbGrid(LoanDisbursementGrid loanDisbGrid) {
        this.loanDisbGrid = loanDisbGrid;
    }

    public List<LoanDisbursementGrid> getLoanDisbGridList() {
        return loanDisbGridList;
    }

    public void setLoanDisbGridList(List<LoanDisbursementGrid> loanDisbGridList) {
        this.loanDisbGridList = loanDisbGridList;
    }

    public String getLoanDisbFlag() {
        return loanDisbFlag;
    }

    public void setLoanDisbFlag(String loanDisbFlag) {
        this.loanDisbFlag = loanDisbFlag;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public int getAcFlag() {
        return AcFlag;
    }

    public void setAcFlag(int AcFlag) {
        this.AcFlag = AcFlag;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public BigDecimal getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(BigDecimal dpLimit) {
        this.dpLimit = dpLimit;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(BigDecimal pendingBalance) {
        this.pendingBalance = pendingBalance;
    }

    public BigDecimal getAdhoclimit() {
        return adhoclimit;
    }

    public void setAdhoclimit(BigDecimal adhoclimit) {
        this.adhoclimit = adhoclimit;
    }

    public String getChqFacility() {
        return chqFacility;
    }

    public void setChqFacility(String chqFacility) {
        this.chqFacility = chqFacility;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
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

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getFlag4() {
        return flag4;
    }

    public void setFlag4(String flag4) {
        this.flag4 = flag4;
    }

    public String getAmountFocus() {
        return amountFocus;
    }

    public void setAmountFocus(String amountFocus) {
        this.amountFocus = amountFocus;
    }

    public String getChqFocus() {
        return chqFocus;
    }

    public void setChqFocus(String chqFocus) {
        this.chqFocus = chqFocus;
    }

    public String getTokenFocus() {
        return tokenFocus;
    }

    public void setTokenFocus(String tokenFocus) {
        this.tokenFocus = tokenFocus;
    }

    public String getAmountTxn() {
        return amountTxn;
    }

    public void setAmountTxn(String amountTxn) {
        this.amountTxn = amountTxn;
    }

    public String getAmountTxnFocus() {
        return amountTxnFocus;
    }

    public void setAmountTxnFocus(String amountTxnFocus) {
        this.amountTxnFocus = amountTxnFocus;
    }

    public Date getChqueDate() {
        return chqueDate;
    }

    public void setChqueDate(Date chqueDate) {
        this.chqueDate = chqueDate;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public String getChqDateFocus() {
        return chqDateFocus;
    }

    public void setChqDateFocus(String chqDateFocus) {
        this.chqDateFocus = chqDateFocus;
    }

    public String getChqDateFlag() {
        return chqDateFlag;
    }

    public void setChqDateFlag(String chqDateFlag) {
        this.chqDateFlag = chqDateFlag;
    }

    public String getChqNoFlag() {
        return chqNoFlag;
    }

    public void setChqNoFlag(String chqNoFlag) {
        this.chqNoFlag = chqNoFlag;
    }

    public String getChqNo() {
        return chqNo;
    }

    public void setChqNo(String chqNo) {
        this.chqNo = chqNo;
    }

    public String getTokenFlag() {
        return tokenFlag;
    }

    public void seteTokenFlag(String eTokenFlag) {
        this.tokenFlag = eTokenFlag;
    }

    public boolean isMsgModuleActive() {
        return msgModuleActive;
    }

    public void setMsgModuleActive(boolean msgModuleActive) {
        this.msgModuleActive = msgModuleActive;
    }

    public String getNewAccNo() {
        return newAccNo;
    }

    public void setNewAccNo(String newAccNo) {
        this.newAccNo = newAccNo;
    }

    public String getLabelCrDr() {
        return labelCrDr;
    }

    public void setLabelCrDr(String labelCrDr) {
        this.labelCrDr = labelCrDr;
    }

    public String getChqFaci() {
        return chqFaci;
    }

    public void setChqFaci(String chqFaci) {
        this.chqFaci = chqFaci;
    }

    public String getSeqNoYearFlag() {
        return seqNoYearFlag;
    }

    public void setSeqNoYearFlag(String seqNoYearFlag) {
        this.seqNoYearFlag = seqNoYearFlag;
    }

    public String getTranTypeVisibility() {
        return tranTypeVisibility;
    }

    public void setTranTypeVisibility(String tranTypeVisibility) {
        this.tranTypeVisibility = tranTypeVisibility;
    }

    public String getSeqNoYearFocus() {
        return seqNoYearFocus;
    }

    public void setSeqNoYearFocus(String seqNoYearFocus) {
        this.seqNoYearFocus = seqNoYearFocus;
    }

    public String getPanamount() {
        return panamount;
    }

    public void setPanamount(String panamount) {
        this.panamount = panamount;
    }

    public String getPanRepFlag() {
        return panRepFlag;
    }

    public void setPanRepFlag(String panRepFlag) {
        this.panRepFlag = panRepFlag;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSeqYear() {
        return seqYear;
    }

    public void setSeqYear(String seqYear) {
        this.seqYear = seqYear;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public PayOrderOutstandingGrid getPoGrid() {
        return poGrid;
    }

    public void setPoGrid(PayOrderOutstandingGrid poGrid) {
        this.poGrid = poGrid;
    }

    public List<PayOrderOutstandingGrid> getPoList() {
        return poList;
    }

    public void setPoList(List<PayOrderOutstandingGrid> poList) {
        this.poList = poList;
    }

    public TxnDetailBean getTxnDetailGrid() {
        return txnDetailGrid;
    }

    public void setTxnDetailGrid(TxnDetailBean txnDetailGrid) {
        this.txnDetailGrid = txnDetailGrid;
    }

    public List<TxnDetailBean> getTxnDetailList() {
        return txnDetailList;
    }

    public void setTxnDetailList(List<TxnDetailBean> txnDetailList) {
        this.txnDetailList = txnDetailList;
    }

    public StopPaymentGrid getStopPayGrid() {
        return stopPayGrid;
    }

    public void setStopPayGrid(StopPaymentGrid stopPayGrid) {
        this.stopPayGrid = stopPayGrid;
    }

    public List<StopPaymentGrid> getStopPayList() {
        return stopPayList;
    }

    public void setStopPayList(List<StopPaymentGrid> stopPayList) {
        this.stopPayList = stopPayList;
    }

    public TxnDetailBean getTxnDetailUnAuthGrid() {
        return txnDetailUnAuthGrid;
    }

    public void setTxnDetailUnAuthGrid(TxnDetailBean txnDetailUnAuthGrid) {
        this.txnDetailUnAuthGrid = txnDetailUnAuthGrid;
    }

    public List<TxnDetailBean> getTxnDetailUnAuthList() {
        return txnDetailUnAuthList;
    }

    public void setTxnDetailUnAuthList(List<TxnDetailBean> txnDetailUnAuthList) {
        this.txnDetailUnAuthList = txnDetailUnAuthList;
    }

    public String getPoddFlag() {
        return poddFlag;
    }

    public void setPoddFlag(String poddFlag) {
        this.poddFlag = poddFlag;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public List<SelectItem> getAlphaCodeList() {
        return alphaCodeList;
    }

    public void setAlphaCodeList(List<SelectItem> alphaCodeList) {
        this.alphaCodeList = alphaCodeList;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public String getPoMode() {
        return poMode;
    }

    public void setPoMode(String poMode) {
        this.poMode = poMode;
    }

    public List<SelectItem> getPoModeList() {
        return poModeList;
    }

    public void setPoModeList(List<SelectItem> poModeList) {
        this.poModeList = poModeList;
    }

    public String getPoBy() {
        return poBy;
    }

    public void setPoBy(String poBy) {
        this.poBy = poBy;
    }

    public String getPoError() {
        return poError;
    }

    public void setPoError(String poError) {
        this.poError = poError;
    }

    public String getCommFlag() {
        return commFlag;
    }

    public void setCommFlag(String commFlag) {
        this.commFlag = commFlag;
    }

    public String getParentACode() {
        return parentACode;
    }

    public void setParentACode(String parentACode) {
        this.parentACode = parentACode;
    }

    public String getSeqNoFlag() {
        return seqNoFlag;
    }

    public void setSeqNoFlag(String seqNoFlag) {
        this.seqNoFlag = seqNoFlag;
    }

    public String getPoInstNoFlag() {
        return poInstNoFlag;
    }

    public void setPoInstNoFlag(String poInstNoFlag) {
        this.poInstNoFlag = poInstNoFlag;
    }

    public String getPoIssueDtFlag() {
        return poIssueDtFlag;
    }

    public void setPoIssueDtFlag(String poIssueDtFlag) {
        this.poIssueDtFlag = poIssueDtFlag;
    }

    public String getSaveCaptionFlag() {
        return saveCaptionFlag;
    }

    public void setSaveCaptionFlag(String saveCaptionFlag) {
        this.saveCaptionFlag = saveCaptionFlag;
    }

    public Date getPoIssueDate() {
        return poIssueDate;
    }

    public void setPoIssueDate(Date poIssueDate) {
        this.poIssueDate = poIssueDate;
    }

    public String getPoSeqNo() {
        return poSeqNo;
    }

    public void setPoSeqNo(String poSeqNo) {
        this.poSeqNo = poSeqNo;
    }

    public String getInFavOf() {
        return inFavOf;
    }

    public void setInFavOf(String inFavOf) {
        this.inFavOf = inFavOf;
    }

    public BigDecimal getPoAmount() {
        return poAmount;
    }

    public void setPoAmount(BigDecimal poAmount) {
        this.poAmount = poAmount;
    }

    public String getPoBrnchAdd() {
        return poBrnchAdd;
    }

    public void setPoBrnchAdd(String poBrnchAdd) {
        this.poBrnchAdd = poBrnchAdd;
    }

    public String getPoBrnchName() {
        return poBrnchName;
    }

    public void setPoBrnchName(String poBrnchName) {
        this.poBrnchName = poBrnchName;
    }

    public BigDecimal getPoComm() {
        return poComm;
    }

    public void setPoComm(BigDecimal poComm) {
        this.poComm = poComm;
    }

    public String getPoCustName() {
        return poCustName;
    }

    public void setPoCustName(String poCustName) {
        this.poCustName = poCustName;
    }

    public String getPoInstNo() {
        return poInstNo;
    }

    public void setPoInstNo(String poInstNo) {
        this.poInstNo = poInstNo;
    }

    public String getPoSaveFlag() {
        return poSaveFlag;
    }

    public void setPoSaveFlag(String poSaveFlag) {
        this.poSaveFlag = poSaveFlag;
    }

    public String getInfavOfFlag() {
        return infavOfFlag;
    }

    public void setInfavOfFlag(String infavOfFlag) {
        this.infavOfFlag = infavOfFlag;
    }

    public boolean isStaxModuleActive() {
        return staxModuleActive;
    }

    public void setStaxModuleActive(boolean staxModuleActive) {
        this.staxModuleActive = staxModuleActive;
    }

    public String getPoAmtFocus() {
        return poAmtFocus;
    }

    public void setPoAmtFocus(String poAmtFocus) {
        this.poAmtFocus = poAmtFocus;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public List<BillTypeBean> getBillTypeBeanList() {
        return billTypeBeanList;
    }

    public void setBillTypeBeanList(List<BillTypeBean> billTypeBeanList) {
        this.billTypeBeanList = billTypeBeanList;
    }

    public BillTypeBean getBillTypeGrid() {
        return billTypeGrid;
    }

    public void setBillTypeGrid(BillTypeBean billTypeGrid) {
        this.billTypeGrid = billTypeGrid;
    }

    public List<TxnDetailBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<TxnDetailBean> dataList) {
        this.dataList = dataList;
    }

    public BigDecimal getPresentBalance() {
        return presentBalance;
    }

    public void setPresentBalance(BigDecimal presentBalance) {
        this.presentBalance = presentBalance;
    }

    public float getRecno() {
        return recno;
    }

    public void setRecno(float recno) {
        this.recno = recno;
    }

    public String getCcodMsg() {
        return ccodMsg;
    }

    public void setCcodMsg(String ccodMsg) {
        this.ccodMsg = ccodMsg;
    }

    public List<TxnDetailBean> getTempXferList() {
        return tempXferList;
    }

    public void setTempXferList(List<TxnDetailBean> tempXferList) {
        this.tempXferList = tempXferList;
    }

    public String getValueDatedFlag() {
        return valueDatedFlag;
    }

    public void setValueDatedFlag(String valueDatedFlag) {
        this.valueDatedFlag = valueDatedFlag;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getCurBrnCode() {
        return curBrnCode;
    }

    public void setCurBrnCode(String curBrnCode) {
        this.curBrnCode = curBrnCode;
    }

    public List<AccountTypeDescPojo> getAccTypeDescList() {
        return accTypeDescList;
    }

    public void setAccTypeDescList(List<AccountTypeDescPojo> accTypeDescList) {
        this.accTypeDescList = accTypeDescList;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public List<IntCalTable> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<IntCalTable> intCalc) {
        this.intCalc = intCalc;
    }

    public String getLblAlpha() {
        return lblAlpha;
    }

    public void setLblAlpha(String lblAlpha) {
        this.lblAlpha = lblAlpha;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public List<SelectItem> getAlertTypeList() {
        return alertTypeList;
    }

    public void setAlertTypeList(List<SelectItem> alertTypeList) {
        this.alertTypeList = alertTypeList;
    }

    public String getAlertCode() {
        return alertCode;
    }

    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }

    public List<SelectItem> getAlertCodeList() {
        return alertCodeList;
    }

    public void setAlertCodeList(List<SelectItem> alertCodeList) {
        this.alertCodeList = alertCodeList;
    }

    public String getAlertSubCode() {
        return alertSubCode;
    }

    public void setAlertSubCode(String alertSubCode) {
        this.alertSubCode = alertSubCode;
    }

    public List<SelectItem> getAlertSubCodeList() {
        return alertSubCodeList;
    }

    public void setAlertSubCodeList(List<SelectItem> alertSubCodeList) {
        this.alertSubCodeList = alertSubCodeList;
    }

    public String getSubCodeDesc() {
        return subCodeDesc;
    }

    public void setSubCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isPopUpFlag() {
        return popUpFlag;
    }

    public void setPopUpFlag(boolean popUpFlag) {
        this.popUpFlag = popUpFlag;
    }
    public int txnCashParam;
    public String cashMode;

    public int getTxnCashParam() {
        return txnCashParam;
    }

    public void setTxnCashParam(int txnCashParam) {
        this.txnCashParam = txnCashParam;
    }

    public String getCashMode() {
        return cashMode;
    }

    public void setCashMode(String cashMode) {
        this.cashMode = cashMode;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public List<GLHeadGrid> getListForF6Temp() {
        return listForF6Temp;
    }

    public void setListForF6Temp(List<GLHeadGrid> listForF6Temp) {
        this.listForF6Temp = listForF6Temp;
    }

    public String getCustAadharNo() {
        return custAadharNo;
    }

    public void setCustAadharNo(String custAadharNo) {
        this.custAadharNo = custAadharNo;
    }

    public void pageInclude() {
        if (txnCashParam == 1) {
            this.setPopUpFlag(true);
            cashDenominationObj.setDenominationTable(new ArrayList<DDSDenominationGrid>());
            cashDenominationObj.setDenoNo("");
            cashDenominationObj.setDenoValue("");
            cashDenominationObj.setErrorMessage1("");
            cashDenominationObj.setDisableDenoNo(false);
            cashDenominationObj.setTotalAmount(this.amountTxn);
            cashDenominationObj.setTyDenoValue(this.selectCrDr);
            cashDenominationObj.setCurrencyAmount("0.00");
            cashDenominationObj.setTyFlg("0");
            cashDenominationObj.setAcDenoNat(AcNature);

            cashDenominationObj.setTyList(new ArrayList<SelectItem>());
            if (this.selectCrDr.equalsIgnoreCase("0")) {
                cashDenominationObj.getTyList().add(new SelectItem("0", "Recieved"));
                cashDenominationObj.getTyList().add(new SelectItem("3", "Return"));
                cashDenominationObj.setTyCaption("Recived/Return :");
            } else {
                cashDenominationObj.getTyList().add(new SelectItem("1", "Paid"));
                cashDenominationObj.getTyList().add(new SelectItem("4", "Return"));
                cashDenominationObj.setTyCaption("Return/Paid :");
            }
        } else {
            this.setPopUpFlag(false);
        }

    }
}
