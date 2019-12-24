/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLoanPrepaymentDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Ankit
 */
public class LPD {
    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;
    private String prepaymentIntReductiomMethod;
    private String applyPrepaymentCharges;
    private String minAmountforPrepayment;
    private String noPrepaymentChargesAfterMonths;
    private String noPrepaymentChargesAfterDays;
    private String limitForPrepaymentInAYear;
    private String limitIndicatorForPrepayment;
    private List<SelectItem> ddlimitIndicatorForPrepayment;
    private List<SelectItem> ddyearIndicatorForPrepaymentLimit;
    private String prepaymentNotAcceptedBeforeMonths;
    private String prepaymentNotAcceptedBeforeDays;
    private String applyPrepaymentChargs;
    private List<SelectItem> ddapplyPrepaymentChargs;
    private String noticeReqdForPrepayment;
    private String minNoticePeriodMonths;
    private String minNoticePeriodDay;
    private String validityOfTheNoticePeriodMonths;
    private String validityOfTheNoticePeriodDays;
    private String eIflowId;
    private String principalFlowId;
    private String disbursementFlowId;
    private String collectionFlowId;
    private String intDemandFlowId;
    private String penalIntDemandFlowId;
    private String overdueIntDemandFlowId;
    private String pastDueCollectionFlowId;
    private String chargeDemandFlowId;
    private String yearIndicatorForPrepaymentLimit;
    private String stbIDRTranToDorman;
    private String bIDRTranToDorman;
    private String stchequeIssued;
    private String stxtPrincipalFlowId;
    private String stxtDisbursementFlowId;
    private String stxtCollectionFlowId;
    private String stxtIntDemandFlowId;
    private String stxtPenalIntDemandFlowId;
    private String stxtOverdueIntDemandFlowId;
    private String stxtPastDueCollectionFlowId;
    private String stxtChargeDemandFlowId;
    private boolean lpdFlag;
    private String stxtEIflowId;
    private String applyPrepaymentChargs1;
    private List<SelectItem> lpdDdtrnRefNo;
    List prepaymentDetails = new ArrayList();

    /** Creates a new instance of LPD */
    public LPD() {
      
        lpdDdtrnRefNo = new ArrayList<SelectItem>();
        lpdDdtrnRefNo.add(new SelectItem("0", ""));
        lpdDdtrnRefNo.add(new SelectItem("Y", "Yes"));
        lpdDdtrnRefNo.add(new SelectItem("N", "No"));
        ddapplyPrepaymentChargs = new ArrayList<SelectItem>();
        ddapplyPrepaymentChargs.add(new SelectItem("0", ""));
        ddapplyPrepaymentChargs.add(new SelectItem("M", "Month End"));
        ddapplyPrepaymentChargs.add(new SelectItem("F", "Fortnight End"));
        ddlimitIndicatorForPrepayment = new ArrayList<SelectItem>();
        ddlimitIndicatorForPrepayment.add(new SelectItem("0", ""));
        ddlimitIndicatorForPrepayment.add(new SelectItem("L", "PercentageOfLoanAmount"));
        ddlimitIndicatorForPrepayment.add(new SelectItem("B", "PercentageOfBalanceOfLoanAcct"));
        ddyearIndicatorForPrepaymentLimit = new ArrayList<SelectItem>();
        ddyearIndicatorForPrepaymentLimit.add(new SelectItem("0", ""));
        ddyearIndicatorForPrepaymentLimit.add(new SelectItem("C", "CalendarYear"));
        ddyearIndicatorForPrepaymentLimit.add(new SelectItem("F", "FinancialYear"));
        ddyearIndicatorForPrepaymentLimit.add(new SelectItem("A", "AcctAnniversaryYear"));
    }

    public String getStxtEIflowId() {
        return stxtEIflowId;
    }

    public void setStxtEIflowId(String stxtEIflowId) {
        this.stxtEIflowId = stxtEIflowId;
    }

    public String getStxtChargeDemandFlowId() {
        return stxtChargeDemandFlowId;
    }

    public void setStxtChargeDemandFlowId(String stxtChargeDemandFlowId) {
        this.stxtChargeDemandFlowId = stxtChargeDemandFlowId;
    }

    public String getStxtCollectionFlowId() {
        return stxtCollectionFlowId;
    }

    public void setStxtCollectionFlowId(String stxtCollectionFlowId) {
        this.stxtCollectionFlowId = stxtCollectionFlowId;
    }

    public String getStxtDisbursementFlowId() {
        return stxtDisbursementFlowId;
    }

    public void setStxtDisbursementFlowId(String stxtDisbursementFlowId) {
        this.stxtDisbursementFlowId = stxtDisbursementFlowId;
    }

    public String getStxtIntDemandFlowId() {
        return stxtIntDemandFlowId;
    }

    public void setStxtIntDemandFlowId(String stxtIntDemandFlowId) {
        this.stxtIntDemandFlowId = stxtIntDemandFlowId;
    }

    public String getStxtOverdueIntDemandFlowId() {
        return stxtOverdueIntDemandFlowId;
    }

    public void setStxtOverdueIntDemandFlowId(String stxtOverdueIntDemandFlowId) {
        this.stxtOverdueIntDemandFlowId = stxtOverdueIntDemandFlowId;
    }

    public String getStxtPastDueCollectionFlowId() {
        return stxtPastDueCollectionFlowId;
    }

    public void setStxtPastDueCollectionFlowId(String stxtPastDueCollectionFlowId) {
        this.stxtPastDueCollectionFlowId = stxtPastDueCollectionFlowId;
    }

    public String getStxtPenalIntDemandFlowId() {
        return stxtPenalIntDemandFlowId;
    }

    public void setStxtPenalIntDemandFlowId(String stxtPenalIntDemandFlowId) {
        this.stxtPenalIntDemandFlowId = stxtPenalIntDemandFlowId;
    }

    public String getStxtPrincipalFlowId() {
        return stxtPrincipalFlowId;
    }

    public void setStxtPrincipalFlowId(String stxtPrincipalFlowId) {
        this.stxtPrincipalFlowId = stxtPrincipalFlowId;
    }


    public String getStchequeIssued() {
        return stchequeIssued;
    }

    public void setStchequeIssued(String stchequeIssued) {
        this.stchequeIssued = stchequeIssued;
    }

    public String getApplyPrepaymentChargs1() {
        return applyPrepaymentChargs1;
    }

    public void setApplyPrepaymentChargs1(String applyPrepaymentChargs1) {
        this.applyPrepaymentChargs1 = applyPrepaymentChargs1;
    }

    public List getPrepaymentDetails() {
        return prepaymentDetails;
    }

    public void setPrepaymentDetails(List prepaymentDetails) {
        this.prepaymentDetails = prepaymentDetails;
    }

    public List<SelectItem> getLpdDdtrnRefNo() {
        return lpdDdtrnRefNo;
    }

    public void setLpdDdtrnRefNo(List<SelectItem> lpdDdtrnRefNo) {
        this.lpdDdtrnRefNo = lpdDdtrnRefNo;
    }

    public String getMinAmountforPrepayment() {
        return minAmountforPrepayment;
    }

    public void setMinAmountforPrepayment(String minAmountforPrepayment) {
        this.minAmountforPrepayment = minAmountforPrepayment;
    }

    public String getApplyPrepaymentCharges() {
        return applyPrepaymentCharges;
    }

    public void setApplyPrepaymentCharges(String applyPrepaymentCharges) {
        this.applyPrepaymentCharges = applyPrepaymentCharges;
    }

    public String getChargeDemandFlowId() {
        return chargeDemandFlowId;
    }

    public void setChargeDemandFlowId(String chargeDemandFlowId) {
        this.chargeDemandFlowId = chargeDemandFlowId;
    }

    public String getCollectionFlowId() {
        return collectionFlowId;
    }

    public void setCollectionFlowId(String collectionFlowId) {
        this.collectionFlowId = collectionFlowId;
    }

    public List<SelectItem> getDdapplyPrepaymentChargs() {
        return ddapplyPrepaymentChargs;
    }

    public void setDdapplyPrepaymentChargs(List<SelectItem> ddapplyPrepaymentChargs) {
        this.ddapplyPrepaymentChargs = ddapplyPrepaymentChargs;
    }

    public String getApplyPrepaymentChargs() {
        return applyPrepaymentChargs;
    }

    public void setApplyPrepaymentChargs(String applyPrepaymentChargs) {
        this.applyPrepaymentChargs = applyPrepaymentChargs;
    }

    public List<SelectItem> getDdlimitIndicatorForPrepayment() {
        return ddlimitIndicatorForPrepayment;
    }

    public void setDdlimitIndicatorForPrepayment(List<SelectItem> ddlimitIndicatorForPrepayment) {
        this.ddlimitIndicatorForPrepayment = ddlimitIndicatorForPrepayment;
    }

    public List<SelectItem> getDdyearIndicatorForPrepaymentLimit() {
        return ddyearIndicatorForPrepaymentLimit;
    }

    public void setDdyearIndicatorForPrepaymentLimit(List<SelectItem> ddyearIndicatorForPrepaymentLimit) {
        this.ddyearIndicatorForPrepaymentLimit = ddyearIndicatorForPrepaymentLimit;
    }

    public String getDisbursementFlowId() {
        return disbursementFlowId;
    }

    public void setDisbursementFlowId(String disbursementFlowId) {
        this.disbursementFlowId = disbursementFlowId;
    }

    public String geteIflowId() {
        return eIflowId;
    }

    public void seteIflowId(String eIflowId) {
        this.eIflowId = eIflowId;
    }

    public String getIntDemandFlowId() {
        return intDemandFlowId;
    }

    public void setIntDemandFlowId(String intDemandFlowId) {
        this.intDemandFlowId = intDemandFlowId;
    }

    public String getLimitForPrepaymentInAYear() {
        return limitForPrepaymentInAYear;
    }

    public void setLimitForPrepaymentInAYear(String limitForPrepaymentInAYear) {
        this.limitForPrepaymentInAYear = limitForPrepaymentInAYear;
    }

    public String getLimitIndicatorForPrepayment() {
        return limitIndicatorForPrepayment;
    }

    public void setLimitIndicatorForPrepayment(String limitIndicatorForPrepayment) {
        this.limitIndicatorForPrepayment = limitIndicatorForPrepayment;
    }

    public String getMinNoticePeriodDay() {
        return minNoticePeriodDay;
    }

    public void setMinNoticePeriodDay(String minNoticePeriodDay) {
        this.minNoticePeriodDay = minNoticePeriodDay;
    }

    public String getMinNoticePeriodMonths() {
        return minNoticePeriodMonths;
    }

    public void setMinNoticePeriodMonths(String minNoticePeriodMonths) {
        this.minNoticePeriodMonths = minNoticePeriodMonths;
    }

    public String getNoPrepaymentChargesAfterDays() {
        return noPrepaymentChargesAfterDays;
    }

    public void setNoPrepaymentChargesAfterDays(String noPrepaymentChargesAfterDays) {
        this.noPrepaymentChargesAfterDays = noPrepaymentChargesAfterDays;
    }

    public String getNoPrepaymentChargesAfterMonths() {
        return noPrepaymentChargesAfterMonths;
    }

    public void setNoPrepaymentChargesAfterMonths(String noPrepaymentChargesAfterMonths) {
        this.noPrepaymentChargesAfterMonths = noPrepaymentChargesAfterMonths;
    }

    public String getNoticeReqdForPrepayment() {
        return noticeReqdForPrepayment;
    }

    public void setNoticeReqdForPrepayment(String noticeReqdForPrepayment) {
        this.noticeReqdForPrepayment = noticeReqdForPrepayment;
    }

    public String getOverdueIntDemandFlowId() {
        return overdueIntDemandFlowId;
    }

    public void setOverdueIntDemandFlowId(String overdueIntDemandFlowId) {
        this.overdueIntDemandFlowId = overdueIntDemandFlowId;
    }

    public String getPastDueCollectionFlowId() {
        return pastDueCollectionFlowId;
    }

    public void setPastDueCollectionFlowId(String pastDueCollectionFlowId) {
        this.pastDueCollectionFlowId = pastDueCollectionFlowId;
    }

    public String getPenalIntDemandFlowId() {
        return penalIntDemandFlowId;
    }

    public void setPenalIntDemandFlowId(String penalIntDemandFlowId) {
        this.penalIntDemandFlowId = penalIntDemandFlowId;
    }

    public String getPrepaymentIntReductionMethod() {
        return prepaymentIntReductiomMethod;
    }

    public void setPrepaymentIntReductionMethod(String prepaymentIntReductiomMethod) {
        this.prepaymentIntReductiomMethod = prepaymentIntReductiomMethod;
    }

    public String getPrepaymentNotAcceptedBeforeDays() {
        return prepaymentNotAcceptedBeforeDays;
    }

    public void setPrepaymentNotAcceptedBeforeDays(String prepaymentNotAcceptedBeforeDays) {
        this.prepaymentNotAcceptedBeforeDays = prepaymentNotAcceptedBeforeDays;
    }

    public String getPrepaymentNotAcceptedBeforeMonths() {
        return prepaymentNotAcceptedBeforeMonths;
    }

    public void setPrepaymentNotAcceptedBeforeMonths(String prepaymentNotAcceptedBeforeMonths) {
        this.prepaymentNotAcceptedBeforeMonths = prepaymentNotAcceptedBeforeMonths;
    }

    public String getPrincipalFlowId() {
        return principalFlowId;
    }

    public void setPrincipalFlowId(String principalFlowId) {
        this.principalFlowId = principalFlowId;
    }

    public String getValidityOfTheNoticePeriodDays() {
        return validityOfTheNoticePeriodDays;
    }

    public void setValidityOfTheNoticePeriodDays(String validityOfTheNoticePeriodDays) {
        this.validityOfTheNoticePeriodDays = validityOfTheNoticePeriodDays;
    }

    public String getValidityOfTheNoticePeriodMonths() {
        return validityOfTheNoticePeriodMonths;
    }

    public void setValidityOfTheNoticePeriodMonths(String validityOfTheNoticePeriodMonths) {
        this.validityOfTheNoticePeriodMonths = validityOfTheNoticePeriodMonths;
    }

    public String getYearIndicatorForPrepaymentLimit() {
        return yearIndicatorForPrepaymentLimit;
    }

    public void setYearIndicatorForPrepaymentLimit(String yearIndicatorForPrepaymentLimit) {
        this.yearIndicatorForPrepaymentLimit = yearIndicatorForPrepaymentLimit;
    }

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public String getbIDRTranToDorman() {
        return bIDRTranToDorman;
    }

    public void setbIDRTranToDorman(String bIDRTranToDorman) {
        this.bIDRTranToDorman = bIDRTranToDorman;
    }

    public String getStbIDRTranToDorman() {
        return stbIDRTranToDorman;
    }

    public void setStbIDRTranToDorman(String stbIDRTranToDorman) {
        this.stbIDRTranToDorman = stbIDRTranToDorman;
    }

    public boolean isLpdFlag() {
        return lpdFlag;
    }

    public void setLpdFlag(boolean lpdFlag) {
        this.lpdFlag = lpdFlag;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }

    public void selectLPDDetails() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeLoanPrepaymentDetailsTO cbsSchemeLoanPrepaymentDetailsTO = schemeMasterManagementDelegate.getLPDDetails(schemeMaster.schemeCode);
            if (cbsSchemeLoanPrepaymentDetailsTO.getApplyPrepaymentCharges() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getApplyPrepaymentCharges().equalsIgnoreCase(""))) {
                setApplyPrepaymentCharges(cbsSchemeLoanPrepaymentDetailsTO.getApplyPrepaymentCharges());
            } else {
                this.setApplyPrepaymentCharges("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getChargeDemandFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getChargeDemandFlowId().equalsIgnoreCase(""))) {
                setChargeDemandFlowId(cbsSchemeLoanPrepaymentDetailsTO.getChargeDemandFlowId());
                descChargeDemandFlowId();
            } else {
                this.setChargeDemandFlowId("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentIntReductionMethod() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentIntReductionMethod().equalsIgnoreCase(""))) {
                setPrepaymentIntReductionMethod(cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentIntReductionMethod());

            } else {
                this.setPrepaymentIntReductionMethod("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getMinAmountForPrepayment() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getMinAmountForPrepayment().toString().equalsIgnoreCase(""))) {
                setMinAmountforPrepayment(cbsSchemeLoanPrepaymentDetailsTO.getMinAmountForPrepayment().toString());
            } else {
                this.setMinAmountforPrepayment("0.0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getNoPrepaymentChargesAfterMonths() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getNoPrepaymentChargesAfterMonths().toString().equalsIgnoreCase(""))) {
                setNoPrepaymentChargesAfterMonths(cbsSchemeLoanPrepaymentDetailsTO.getNoPrepaymentChargesAfterMonths().toString());
            } else {
                this.setNoPrepaymentChargesAfterMonths("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getNoPrepaymentChargesAfterDays() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getNoPrepaymentChargesAfterDays().toString().equalsIgnoreCase(""))) {
                setNoPrepaymentChargesAfterDays(cbsSchemeLoanPrepaymentDetailsTO.getNoPrepaymentChargesAfterDays().toString());
            } else {
                this.setNoPrepaymentChargesAfterDays("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getLimitForPrepaymentInAYear() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getLimitForPrepaymentInAYear().toString().equalsIgnoreCase(""))) {
                setLimitForPrepaymentInAYear(cbsSchemeLoanPrepaymentDetailsTO.getLimitForPrepaymentInAYear().toString());
            } else {
                this.setLimitForPrepaymentInAYear("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getLimitIndicatorForPrepayment() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getLimitIndicatorForPrepayment().toString().equalsIgnoreCase(""))) {
                setLimitIndicatorForPrepayment(cbsSchemeLoanPrepaymentDetailsTO.getLimitIndicatorForPrepayment().toString());
            } else {
                this.setMinAmountforPrepayment("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getYearIndicatorForPrepaymentLimit() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getYearIndicatorForPrepaymentLimit().equalsIgnoreCase(""))) {
                setYearIndicatorForPrepaymentLimit(cbsSchemeLoanPrepaymentDetailsTO.getYearIndicatorForPrepaymentLimit());
            } else {
                this.setYearIndicatorForPrepaymentLimit("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentNotAcceptedBeforeMonths() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentNotAcceptedBeforeMonths().equalsIgnoreCase(""))) {
                setPrepaymentNotAcceptedBeforeMonths(cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentNotAcceptedBeforeMonths());
            } else {
                this.setPrepaymentNotAcceptedBeforeMonths("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentNotAcceptedBeforeDays() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentNotAcceptedBeforeDays().equalsIgnoreCase(""))) {
                setPrepaymentNotAcceptedBeforeDays(cbsSchemeLoanPrepaymentDetailsTO.getPrepaymentNotAcceptedBeforeDays());
            } else {
                this.setPrepaymentNotAcceptedBeforeDays("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getNoticeReqdForPrepayment() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getNoticeReqdForPrepayment().equalsIgnoreCase(""))) {
                setNoticeReqdForPrepayment(cbsSchemeLoanPrepaymentDetailsTO.getNoticeReqdForPrepayment());
            } else {
                this.setNoticeReqdForPrepayment("0");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getMinNoticePeriodMonths() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getMinNoticePeriodMonths().equalsIgnoreCase(""))) {
                setMinNoticePeriodMonths(cbsSchemeLoanPrepaymentDetailsTO.getMinNoticePeriodMonths());
            } else {
                this.setMinNoticePeriodMonths("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getMinNoticePeriodDay() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getMinNoticePeriodDay().equalsIgnoreCase(""))) {
                setMinNoticePeriodDay(cbsSchemeLoanPrepaymentDetailsTO.getMinNoticePeriodDay());
            } else {
                this.setMinNoticePeriodDay("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getValidityOfTheNoticePeriodMonths() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getValidityOfTheNoticePeriodMonths().equalsIgnoreCase(""))) {
                setValidityOfTheNoticePeriodMonths(cbsSchemeLoanPrepaymentDetailsTO.getValidityOfTheNoticePeriodMonths());
            } else {
                this.setValidityOfTheNoticePeriodMonths("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getValidityofthenoticeperioddays() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getValidityofthenoticeperioddays().equalsIgnoreCase(""))) {
                setValidityOfTheNoticePeriodDays(cbsSchemeLoanPrepaymentDetailsTO.getValidityofthenoticeperioddays());
            } else {
                this.setValidityOfTheNoticePeriodDays("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getEiFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getEiFlowId().equalsIgnoreCase(""))) {
                seteIflowId(cbsSchemeLoanPrepaymentDetailsTO.getEiFlowId());
                descEIflowId();
            } else {
                this.seteIflowId("");
                
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPrincipalFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPrincipalFlowId().equalsIgnoreCase(""))) {
                setPrincipalFlowId(cbsSchemeLoanPrepaymentDetailsTO.getPrincipalFlowId());
                  descPrincipalFlowId();
            } else {
                this.setPrincipalFlowId("");
              
            }

            if (cbsSchemeLoanPrepaymentDetailsTO.getDisbursementFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getDisbursementFlowId().equalsIgnoreCase(""))) {
                setDisbursementFlowId(cbsSchemeLoanPrepaymentDetailsTO.getDisbursementFlowId());
                 descDisbursementFlowId();
            } else {
                this.setDisbursementFlowId("");
               
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getCollectionFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getCollectionFlowId().equalsIgnoreCase(""))) {
                setCollectionFlowId(cbsSchemeLoanPrepaymentDetailsTO.getCollectionFlowId());
                 descCollectionFlowId();
            } else {
                this.setCollectionFlowId("");
               
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getIntDemandFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getIntDemandFlowId().equalsIgnoreCase(""))) {
                setIntDemandFlowId(cbsSchemeLoanPrepaymentDetailsTO.getIntDemandFlowId());
                 descIntDemandFlowId();
            } else {
                this.setIntDemandFlowId("");
               
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPenalIntDemandFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPenalIntDemandFlowId().equalsIgnoreCase(""))) {
                setPenalIntDemandFlowId(cbsSchemeLoanPrepaymentDetailsTO.getPenalIntDemandFlowId());
                  descPenalIntDemandFlowId();
            } else {
                this.setPenalIntDemandFlowId("");
              
            }

            if (cbsSchemeLoanPrepaymentDetailsTO.getOverdueIntDemandFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getOverdueIntDemandFlowId().equalsIgnoreCase(""))) {
                setOverdueIntDemandFlowId(cbsSchemeLoanPrepaymentDetailsTO.getOverdueIntDemandFlowId());
                 descOverdueIntDemandFlowId();
            } else {
                this.setOverdueIntDemandFlowId("");
               
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPastDueCollectionFlowId() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPastDueCollectionFlowId().equalsIgnoreCase(""))) {
                setPastDueCollectionFlowId(cbsSchemeLoanPrepaymentDetailsTO.getPastDueCollectionFlowId());
                 descPastDueCollectionFlowId();
            } else {
                this.setPastDueCollectionFlowId("");
            }
            if (cbsSchemeLoanPrepaymentDetailsTO.getPayOffIntToBeCollectedTill() != null || (!cbsSchemeLoanPrepaymentDetailsTO.getPayOffIntToBeCollectedTill().equalsIgnoreCase(""))) {
                setApplyPrepaymentChargs1(cbsSchemeLoanPrepaymentDetailsTO.getPayOffIntToBeCollectedTill());
            } else {
                this.setApplyPrepaymentChargs1("0");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }
      public void descEIflowId() {
        schemeMaster.setMessage("");
        try {
            if (this.eIflowId.equals("")) {
                this.setStxtEIflowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.eIflowId);
                this.setStxtEIflowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtEIflowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }
     public void descPrincipalFlowId() {
        schemeMaster.setMessage("");
        try {
            if (this.principalFlowId.equals("")) {
                this.setStxtPrincipalFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.principalFlowId);
                this.setStxtPrincipalFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtPrincipalFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }
     public void descDisbursementFlowId()
     {
        schemeMaster.setMessage("");
        try {
            if (this.disbursementFlowId.equals("")) {
                this.setStxtDisbursementFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.disbursementFlowId);
                this.setStxtDisbursementFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtDisbursementFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

     }
      public void descIntDemandFlowId()
     {
        schemeMaster.setMessage("");
        try {
            if (this.intDemandFlowId.equals("")) {
                this.setStxtIntDemandFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.intDemandFlowId);
                this.setStxtIntDemandFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtIntDemandFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

     }

     public void descPenalIntDemandFlowId()
     {
        schemeMaster.setMessage("");
        try {
            if (this.penalIntDemandFlowId.equals("")) {
                this.setStxtPenalIntDemandFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.penalIntDemandFlowId);
                this.setStxtPenalIntDemandFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtPenalIntDemandFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

     }
     public void descCollectionFlowId()
     {
          schemeMaster.setMessage("");
        try {
            if (this.collectionFlowId.equals("")) {
                this.setStxtCollectionFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.collectionFlowId);
                this.setStxtCollectionFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtCollectionFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
     }
      public void descOverdueIntDemandFlowId()
     {
        schemeMaster.setMessage("");
        try {
            if (this.overdueIntDemandFlowId.equals("")) {
                this.setStxtOverdueIntDemandFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.overdueIntDemandFlowId);
                this.setStxtOverdueIntDemandFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtOverdueIntDemandFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

     }
       public void descPastDueCollectionFlowId()
     {
        schemeMaster.setMessage("");
        try {
            if (this.pastDueCollectionFlowId.equals("")) {
                this.setStxtPastDueCollectionFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.pastDueCollectionFlowId);
                this.setStxtPastDueCollectionFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtPastDueCollectionFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

     }
     public void descChargeDemandFlowId()
     {
        schemeMaster.setMessage("");
        try {
            if (this.chargeDemandFlowId.equals("")) {
                this.setStxtChargeDemandFlowId("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chargeDemandFlowId);
                this.setStxtChargeDemandFlowId(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStxtChargeDemandFlowId("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

     }

    public void refreshLPDForm() {
        setPrepaymentIntReductionMethod("0");
        setApplyPrepaymentCharges("0");
        setMinAmountforPrepayment("0.0");
        setNoPrepaymentChargesAfterMonths("");
        setNoPrepaymentChargesAfterDays("");
        setLimitForPrepaymentInAYear("0");
        setLimitIndicatorForPrepayment("0");
        setYearIndicatorForPrepaymentLimit("0");
        setPrepaymentNotAcceptedBeforeMonths("");
        setPrepaymentNotAcceptedBeforeDays("");
        setNoticeReqdForPrepayment("0");
        setMinNoticePeriodMonths("");
        setMinNoticePeriodDay("");
        setValidityOfTheNoticePeriodMonths("");
        setValidityOfTheNoticePeriodDays("");
        seteIflowId("");
        setPrincipalFlowId("");
        setDisbursementFlowId("");
        setCollectionFlowId("");
        setIntDemandFlowId("");
        setPenalIntDemandFlowId("");
        setOverdueIntDemandFlowId("");
        setPastDueCollectionFlowId("");
        setChargeDemandFlowId("");
        stxtChargeDemandFlowId="";
        stxtCollectionFlowId="";
        stxtDisbursementFlowId="";
        stxtEIflowId="";
        stxtIntDemandFlowId="";
        stxtOverdueIntDemandFlowId="";
        stxtPastDueCollectionFlowId="";
        stxtPenalIntDemandFlowId="";
        stxtPrincipalFlowId="";

    }

    public void enableLPDForm() {
        lpdFlag = false;
    }

    public void disableLPDForm() {
        lpdFlag = true;
    }
}
