/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeDepositsSchemeParametersMaintananceTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class DSPM {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for dspm.jsp file
    private String depositAmountMinimum;
    private String depositAmountMaximum;
    private String depositAmountSteps;
    private String periodMiniMonths;
    private String periodMiniDaysMain;
    private String periodMaxMonthsMain;
    private String periodMaxDaysMain;
    private String periodStepsMonths;
    private String periodStepDays;
    private String depositReportTemplate;
    private String depositType;
    private String autoRenewalMain;
    private String maxRenewalAllowed;
    private String renewalPeriodMonths;
    private String renewalPeriodDays;
    private String renewalAllowedPeriod;
    private String autoRenewalGracePeriod;
    private String sundryDepositPlaceholder;
    private String repaymentReportCode;
    private String valueDatedClosure;
    private String callDepositNoticePeriodMonths;
    private String callDepositNoticePeriodDays;
    private String delayInstallmentTblCode;
    private String delayWithinMonth;
    private String delayAllowedPeriodMonths;
    private String delayAllowedPeriodDays;
    private String penalFeePlaceholder;
    private String penalFeeReportCode;
    private String printPBDR;
    private String matAmtToleranceLimit;
    private String useInventory;
    private String inventoryClass;
    private String inventoryType;
    private String inventoryLoanClass;
    private String inventoryLoanCode;
    private String commissionPlaceholder;
    private String serviceChargeTblCode;
    private String commissionTblCode;
    private String prePartClosurePenaltyCode;
    private String commissionReportCode;
    private String allowSweepsmain;
    private String allowPartClosure;
    private boolean dspmFlag;
    private List<SelectItem> ddDepositType;
    private List<SelectItem> ddautoRenewal;
    private List<SelectItem> ddPrintPB;
    private List<SelectItem> ddMatAmt;
    private List<SelectItem> ddDspmTrnRefNo;
    private List<SelectItem> ddPurposeOfAdvance;

    //Getter-Setter for dspm.jsp file
    public List<SelectItem> getDdPurposeOfAdvance() {
        return ddPurposeOfAdvance;
    }

    public void setDdPurposeOfAdvance(List<SelectItem> ddPurposeOfAdvance) {
        this.ddPurposeOfAdvance = ddPurposeOfAdvance;
    }

    public String getAllowPartClosure() {
        return allowPartClosure;
    }

    public void setAllowPartClosure(String allowPartClosure) {
        this.allowPartClosure = allowPartClosure;
    }

    public String getAllowSweepsmain() {
        return allowSweepsmain;
    }

    public void setAllowSweepsmain(String allowSweepsmain) {
        this.allowSweepsmain = allowSweepsmain;
    }

    public String getAutoRenewalGracePeriod() {
        return autoRenewalGracePeriod;
    }

    public void setAutoRenewalGracePeriod(String autoRenewalGracePeriod) {
        this.autoRenewalGracePeriod = autoRenewalGracePeriod;
    }

    public String getAutoRenewalMain() {
        return autoRenewalMain;
    }

    public void setAutoRenewalMain(String autoRenewalMain) {
        this.autoRenewalMain = autoRenewalMain;
    }

    public String getCallDepositNoticePeriodDays() {
        return callDepositNoticePeriodDays;
    }

    public void setCallDepositNoticePeriodDays(String callDepositNoticePeriodDays) {
        this.callDepositNoticePeriodDays = callDepositNoticePeriodDays;
    }

    public String getCallDepositNoticePeriodMonths() {
        return callDepositNoticePeriodMonths;
    }

    public void setCallDepositNoticePeriodMonths(String callDepositNoticePeriodMonths) {
        this.callDepositNoticePeriodMonths = callDepositNoticePeriodMonths;
    }

    public String getCommissionPlaceholder() {
        return commissionPlaceholder;
    }

    public void setCommissionPlaceholder(String commissionPlaceholder) {
        this.commissionPlaceholder = commissionPlaceholder;
    }

    public String getCommissionReportCode() {
        return commissionReportCode;
    }

    public void setCommissionReportCode(String commissionReportCode) {
        this.commissionReportCode = commissionReportCode;
    }

    public String getCommissionTblCode() {
        return commissionTblCode;
    }

    public void setCommissionTblCode(String commissionTblCode) {
        this.commissionTblCode = commissionTblCode;
    }

    public List<SelectItem> getDdDepositType() {
        return ddDepositType;
    }

    public void setDdDepositType(List<SelectItem> ddDepositType) {
        this.ddDepositType = ddDepositType;
    }

    public List<SelectItem> getDdMatAmt() {
        return ddMatAmt;
    }

    public void setDdMatAmt(List<SelectItem> ddMatAmt) {
        this.ddMatAmt = ddMatAmt;
    }

    public List<SelectItem> getDdPrintPB() {
        return ddPrintPB;
    }

    public void setDdPrintPB(List<SelectItem> ddPrintPB) {
        this.ddPrintPB = ddPrintPB;
    }

    public List<SelectItem> getDdautoRenewal() {
        return ddautoRenewal;
    }

    public void setDdautoRenewal(List<SelectItem> ddautoRenewal) {
        this.ddautoRenewal = ddautoRenewal;
    }

    public String getDelayAllowedPeriodDays() {
        return delayAllowedPeriodDays;
    }

    public void setDelayAllowedPeriodDays(String delayAllowedPeriodDays) {
        this.delayAllowedPeriodDays = delayAllowedPeriodDays;
    }

    public String getDelayAllowedPeriodMonths() {
        return delayAllowedPeriodMonths;
    }

    public void setDelayAllowedPeriodMonths(String delayAllowedPeriodMonths) {
        this.delayAllowedPeriodMonths = delayAllowedPeriodMonths;
    }

    public String getDelayInstallmentTblCode() {
        return delayInstallmentTblCode;
    }

    public void setDelayInstallmentTblCode(String delayInstallmentTblCode) {
        this.delayInstallmentTblCode = delayInstallmentTblCode;
    }

    public String getDelayWithinMonth() {
        return delayWithinMonth;
    }

    public void setDelayWithinMonth(String delayWithinMonth) {
        this.delayWithinMonth = delayWithinMonth;
    }

    public String getDepositAmountMaximum() {
        return depositAmountMaximum;
    }

    public void setDepositAmountMaximum(String depositAmountMaximum) {
        this.depositAmountMaximum = depositAmountMaximum;
    }

    public String getDepositAmountMinimum() {
        return depositAmountMinimum;
    }

    public void setDepositAmountMinimum(String depositAmountMinimum) {
        this.depositAmountMinimum = depositAmountMinimum;
    }

    public String getDepositAmountSteps() {
        return depositAmountSteps;
    }

    public void setDepositAmountSteps(String depositAmountSteps) {
        this.depositAmountSteps = depositAmountSteps;
    }

    public String getDepositReportTemplate() {
        return depositReportTemplate;
    }

    public void setDepositReportTemplate(String depositReportTemplate) {
        this.depositReportTemplate = depositReportTemplate;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getInventoryClass() {
        return inventoryClass;
    }

    public void setInventoryClass(String inventoryClass) {
        this.inventoryClass = inventoryClass;
    }

    public String getInventoryLoanClass() {
        return inventoryLoanClass;
    }

    public void setInventoryLoanClass(String inventoryLoanClass) {
        this.inventoryLoanClass = inventoryLoanClass;
    }

    public String getInventoryLoanCode() {
        return inventoryLoanCode;
    }

    public void setInventoryLoanCode(String inventoryLoanCode) {
        this.inventoryLoanCode = inventoryLoanCode;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getMatAmtToleranceLimit() {
        return matAmtToleranceLimit;
    }

    public void setMatAmtToleranceLimit(String matAmtToleranceLimit) {
        this.matAmtToleranceLimit = matAmtToleranceLimit;
    }

    public String getMaxRenewalAllowed() {
        return maxRenewalAllowed;
    }

    public void setMaxRenewalAllowed(String maxRenewalAllowed) {
        this.maxRenewalAllowed = maxRenewalAllowed;
    }

    public String getPenalFeePlaceholder() {
        return penalFeePlaceholder;
    }

    public void setPenalFeePlaceholder(String penalFeePlaceholder) {
        this.penalFeePlaceholder = penalFeePlaceholder;
    }

    public String getPenalFeeReportCode() {
        return penalFeeReportCode;
    }

    public void setPenalFeeReportCode(String penalFeeReportCode) {
        this.penalFeeReportCode = penalFeeReportCode;
    }

    public String getPeriodMaxDaysMain() {
        return periodMaxDaysMain;
    }

    public void setPeriodMaxDaysMain(String periodMaxDaysMain) {
        this.periodMaxDaysMain = periodMaxDaysMain;
    }

    public String getPeriodMaxMonthsMain() {
        return periodMaxMonthsMain;
    }

    public void setPeriodMaxMonthsMain(String periodMaxMonthsMain) {
        this.periodMaxMonthsMain = periodMaxMonthsMain;
    }

    public String getPeriodMiniDaysMain() {
        return periodMiniDaysMain;
    }

    public void setPeriodMiniDaysMain(String periodMiniDaysMain) {
        this.periodMiniDaysMain = periodMiniDaysMain;
    }

    public String getPeriodMiniMonths() {
        return periodMiniMonths;
    }

    public void setPeriodMiniMonths(String periodMiniMonths) {
        this.periodMiniMonths = periodMiniMonths;
    }

    public String getPeriodStepDays() {
        return periodStepDays;
    }

    public void setPeriodStepDays(String periodStepDays) {
        this.periodStepDays = periodStepDays;
    }

    public String getPeriodStepsMonths() {
        return periodStepsMonths;
    }

    public void setPeriodStepsMonths(String periodStepsMonths) {
        this.periodStepsMonths = periodStepsMonths;
    }

    public String getPrePartClosurePenaltyCode() {
        return prePartClosurePenaltyCode;
    }

    public void setPrePartClosurePenaltyCode(String prePartClosurePenaltyCode) {
        this.prePartClosurePenaltyCode = prePartClosurePenaltyCode;
    }

    public String getPrintPBDR() {
        return printPBDR;
    }

    public void setPrintPBDR(String printPBDR) {
        this.printPBDR = printPBDR;
    }

    public String getRenewalAllowedPeriod() {
        return renewalAllowedPeriod;
    }

    public void setRenewalAllowedPeriod(String renewalAllowedPeriod) {
        this.renewalAllowedPeriod = renewalAllowedPeriod;
    }

    public String getRenewalPeriodDays() {
        return renewalPeriodDays;
    }

    public void setRenewalPeriodDays(String renewalPeriodDays) {
        this.renewalPeriodDays = renewalPeriodDays;
    }

    public String getRenewalPeriodMonths() {
        return renewalPeriodMonths;
    }

    public void setRenewalPeriodMonths(String renewalPeriodMonths) {
        this.renewalPeriodMonths = renewalPeriodMonths;
    }

    public String getRepaymentReportCode() {
        return repaymentReportCode;
    }

    public void setRepaymentReportCode(String repaymentReportCode) {
        this.repaymentReportCode = repaymentReportCode;
    }

    public String getServiceChargeTblCode() {
        return serviceChargeTblCode;
    }

    public void setServiceChargeTblCode(String serviceChargeTblCode) {
        this.serviceChargeTblCode = serviceChargeTblCode;
    }

    public String getSundryDepositPlaceholder() {
        return sundryDepositPlaceholder;
    }

    public void setSundryDepositPlaceholder(String sundryDepositPlaceholder) {
        this.sundryDepositPlaceholder = sundryDepositPlaceholder;
    }

    public String getUseInventory() {
        return useInventory;
    }

    public void setUseInventory(String useInventory) {
        this.useInventory = useInventory;
    }

    public String getValueDatedClosure() {
        return valueDatedClosure;
    }

    public void setValueDatedClosure(String valueDatedClosure) {
        this.valueDatedClosure = valueDatedClosure;
    }

    public boolean isDspmFlag() {
        return dspmFlag;
    }

    public void setDspmFlag(boolean dspmFlag) {
        this.dspmFlag = dspmFlag;
    }

    public List<SelectItem> getDdDspmTrnRefNo() {
        return ddDspmTrnRefNo;
    }

    public void setDdDspmTrnRefNo(List<SelectItem> ddDspmTrnRefNo) {
        this.ddDspmTrnRefNo = ddDspmTrnRefNo;
    }

    /** Creates a new instance of DSPM */
    public DSPM() {
       
        try {
            ddDepositType = new ArrayList<SelectItem>();
            ddDepositType.add(new SelectItem("0", ""));
            ddDepositType.add(new SelectItem("P", "Pool linked"));
            ddDepositType.add(new SelectItem("F", "FFD"));
            ddDepositType.add(new SelectItem("O", "Others"));

            ddautoRenewal = new ArrayList<SelectItem>();
            ddautoRenewal.add(new SelectItem("0", ""));
            ddautoRenewal.add(new SelectItem("L", "Limited"));
            ddautoRenewal.add(new SelectItem("U", "Unlimited"));
            ddautoRenewal.add(new SelectItem("N", "No Auto Renewal"));

            ddDspmTrnRefNo = new ArrayList<SelectItem>();
            ddDspmTrnRefNo.add(new SelectItem("0", ""));
            ddDspmTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddDspmTrnRefNo.add(new SelectItem("N", "No"));

            ddPrintPB = new ArrayList<SelectItem>();
            ddPrintPB.add(new SelectItem("0", ""));
            ddPrintPB.add(new SelectItem("P", "Passbook"));
            ddPrintPB.add(new SelectItem("R", "Receipt"));
            ddPrintPB.add(new SelectItem("B", "Both"));

            ddMatAmt = new ArrayList<SelectItem>();
            ddMatAmt.add(new SelectItem("0", ""));
            ddMatAmt.add(new SelectItem("F", "Fixed Amount"));
            ddMatAmt.add(new SelectItem("P", "Percentage of Installment Amount"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs1 = schemeMasterManagementDelegate.getCurrencyCode("041");
            if (CbsRefRecTypeTOs1.size() > 0) {
                ddPurposeOfAdvance = new ArrayList<SelectItem>();
                ddPurposeOfAdvance.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs1) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddPurposeOfAdvance.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            } else {
                ddPurposeOfAdvance = new ArrayList<SelectItem>();
                ddPurposeOfAdvance.add(new SelectItem("0", ""));
            }


        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectDspmDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeDepositsSchemeParametersMaintananceTO singleTO = schemeMasterManagementDelegate.selectDepositParameterMaintenance(schemeMaster.getSchemeCode());
            if (singleTO != null) {
                this.setDepositAmountMinimum(singleTO.getDepositAmountMinimum().toString());
                this.setDepositAmountMaximum(singleTO.getDepositAmountMaximum().toString());
                this.setDepositAmountSteps(singleTO.getDepositAmountSteps().toString());

                if (singleTO.getPeriodMiniMonths() == null || singleTO.getPeriodMiniMonths().equalsIgnoreCase("")) {
                    this.setPeriodMiniMonths("");
                } else {
                    this.setPeriodMiniMonths(singleTO.getPeriodMiniMonths());
                }
                if (singleTO.getPeriodMiniDays() == null || singleTO.getPeriodMiniDays().equalsIgnoreCase("")) {
                    this.setPeriodMiniDaysMain("");
                } else {
                    this.setPeriodMiniDaysMain(singleTO.getPeriodMiniDays());
                }
                if (singleTO.getPeriodMaxMonths() == null || singleTO.getPeriodMaxMonths().equalsIgnoreCase("")) {
                    this.setPeriodMaxMonthsMain("");
                } else {
                    this.setPeriodMaxMonthsMain(singleTO.getPeriodMaxMonths());
                }
                if (singleTO.getPeriodMaxDays() == null || singleTO.getPeriodMaxDays().equalsIgnoreCase("")) {
                    this.setPeriodMaxDaysMain("");
                } else {
                    this.setPeriodMaxDaysMain(singleTO.getPeriodMaxDays());
                }
                if (singleTO.getPeriodStepsMonths() == null || singleTO.getPeriodStepsMonths().equalsIgnoreCase("")) {
                    this.setPeriodStepsMonths("");
                } else {
                    this.setPeriodStepsMonths(singleTO.getPeriodStepsMonths());
                }
                if (singleTO.getPeriodStepsDays() == null || singleTO.getPeriodStepsDays().equalsIgnoreCase("")) {
                    this.setPeriodStepDays("");
                } else {
                    this.setPeriodStepDays(singleTO.getPeriodStepsDays());
                }
                if (singleTO.getDepositReportTemplate() == null || singleTO.getDepositReportTemplate().equalsIgnoreCase("")) {
                    this.setDepositReportTemplate("");
                } else {
                    this.setDepositReportTemplate(singleTO.getDepositReportTemplate());
                }
                if (singleTO.getDepositType() == null || singleTO.getDepositType().equalsIgnoreCase("")) {
                    this.setDepositType("0");
                } else {
                    this.setDepositType(singleTO.getDepositType());
                }
                if (singleTO.getAutoRenewal() == null || singleTO.getAutoRenewal().equalsIgnoreCase("")) {
                    this.setAutoRenewalMain("0");
                } else {
                    this.setAutoRenewalMain(singleTO.getAutoRenewal());
                }
                if (singleTO.getMaxRenewalAllowed() == null || singleTO.getMaxRenewalAllowed().equalsIgnoreCase("")) {
                    this.setMaxRenewalAllowed("");
                } else {
                    this.setMaxRenewalAllowed(singleTO.getMaxRenewalAllowed());
                }
                if (singleTO.getRenewalPeriodMonths() == null || singleTO.getRenewalPeriodMonths().equalsIgnoreCase("")) {
                    this.setRenewalPeriodMonths("");
                } else {
                    this.setRenewalPeriodMonths(singleTO.getRenewalPeriodMonths());
                }
                if (singleTO.getRenewalPeriodDays() == null || singleTO.getRenewalPeriodDays().equalsIgnoreCase("")) {
                    this.setRenewalPeriodDays("");
                } else {
                    this.setRenewalPeriodDays(singleTO.getRenewalPeriodDays());
                }
                if (singleTO.getRenewalAllowedPeriod() == null || singleTO.getRenewalAllowedPeriod().equalsIgnoreCase("")) {
                    this.setRenewalAllowedPeriod("");
                } else {
                    this.setRenewalAllowedPeriod(singleTO.getRenewalAllowedPeriod());
                }
                if (singleTO.getAutoRenewalGracePeriod() == null || singleTO.getAutoRenewalGracePeriod().equalsIgnoreCase("")) {
                    this.setAutoRenewalGracePeriod("");
                } else {
                    this.setAutoRenewalGracePeriod(singleTO.getAutoRenewalGracePeriod());
                }
                if (singleTO.getSundryDepositPlaceholder() == null || singleTO.getSundryDepositPlaceholder().equalsIgnoreCase("")) {
                    this.setSundryDepositPlaceholder("");
                } else {
                    this.setSundryDepositPlaceholder(singleTO.getSundryDepositPlaceholder());
                }
                if (singleTO.getRepaymentReportCode() == null || singleTO.getRepaymentReportCode().equalsIgnoreCase("")) {
                    this.setRepaymentReportCode("");
                } else {
                    this.setRepaymentReportCode(singleTO.getRepaymentReportCode());
                }
                if (singleTO.getValueDatedClosure() == null || singleTO.getValueDatedClosure().equalsIgnoreCase("")) {
                    this.setValueDatedClosure("0");
                } else {
                    this.setValueDatedClosure(singleTO.getValueDatedClosure());
                }
                if (singleTO.getCallDepositNoticePeriodMonths() == null || singleTO.getCallDepositNoticePeriodMonths().equalsIgnoreCase("")) {
                    this.setCallDepositNoticePeriodMonths("");
                } else {
                    this.setCallDepositNoticePeriodMonths(singleTO.getCallDepositNoticePeriodMonths());
                }
                if (singleTO.getCallDepositNoticePeriodDays() == null || singleTO.getCallDepositNoticePeriodDays().equalsIgnoreCase("")) {
                    this.setCallDepositNoticePeriodDays("");
                } else {
                    this.setCallDepositNoticePeriodDays(singleTO.getCallDepositNoticePeriodDays());
                }
                if (singleTO.getDelayInstallmentTblCode() == null || singleTO.getDelayInstallmentTblCode().equalsIgnoreCase("")) {
                    this.setDelayInstallmentTblCode("");
                } else {
                    this.setDelayInstallmentTblCode(singleTO.getDelayInstallmentTblCode());
                }
                if (singleTO.getDelayWithinMonth() == null || singleTO.getDelayWithinMonth().equalsIgnoreCase("")) {
                    this.setDelayWithinMonth("");
                } else {
                    this.setDelayWithinMonth(singleTO.getDelayWithinMonth());
                }
                if (singleTO.getDelayAllowedPeriodMonths() == null || singleTO.getDelayAllowedPeriodMonths().equalsIgnoreCase("")) {
                    this.setDelayAllowedPeriodMonths("");
                } else {
                    this.setDelayAllowedPeriodMonths(singleTO.getDelayAllowedPeriodMonths());
                }
                if (singleTO.getDelayAllowedPeriodDays() == null || singleTO.getDelayAllowedPeriodDays().equalsIgnoreCase("")) {
                    this.setDelayAllowedPeriodDays("");
                } else {
                    this.setDelayAllowedPeriodDays(singleTO.getDelayAllowedPeriodDays());
                }
                if (singleTO.getPenalFeePlaceholder() == null || singleTO.getPenalFeePlaceholder().equalsIgnoreCase("")) {
                    this.setPenalFeePlaceholder("");
                } else {
                    this.setPenalFeePlaceholder(singleTO.getPenalFeePlaceholder());
                }
                if (singleTO.getPenalFeeReportCode() == null || singleTO.getPenalFeeReportCode().equalsIgnoreCase("")) {
                    this.setPenalFeeReportCode("");
                } else {
                    this.setPenalFeeReportCode(singleTO.getPenalFeeReportCode());
                }
                if (singleTO.getPrintPbDr() == null || singleTO.getPrintPbDr().equalsIgnoreCase("")) {
                    this.setPrintPBDR("0");
                } else {
                    this.setPrintPBDR(singleTO.getPrintPbDr());
                }
                if (singleTO.getMatAmtToleranceLimit() == null || singleTO.getMatAmtToleranceLimit().equalsIgnoreCase("")) {
                    this.setMatAmtToleranceLimit("0");
                } else {
                    this.setMatAmtToleranceLimit(singleTO.getMatAmtToleranceLimit());
                }
                if (singleTO.getUseInventory() == null || singleTO.getUseInventory().equalsIgnoreCase("")) {
                    this.setUseInventory("0");
                } else {
                    this.setUseInventory(singleTO.getUseInventory());
                }
                if (singleTO.getInventoryClass() == null || singleTO.getInventoryClass().equalsIgnoreCase("")) {
                    this.setInventoryClass("");
                } else {
                    this.setInventoryClass(singleTO.getInventoryClass());
                }
                if (singleTO.getInventoryType() == null || singleTO.getInventoryType().equalsIgnoreCase("")) {
                    this.setInventoryType("");
                } else {
                    this.setInventoryType(singleTO.getInventoryType());
                }
                if (singleTO.getInventoryLoanClass() == null || singleTO.getInventoryLoanClass().equalsIgnoreCase("")) {
                    this.setInventoryLoanClass("");
                } else {
                    this.setInventoryLoanClass(singleTO.getInventoryLoanClass());
                }
                if (singleTO.getInventoryLoanCode() == null || singleTO.getInventoryLoanCode().equalsIgnoreCase("")) {
                    this.setInventoryLoanCode("");
                } else {
                    this.setInventoryLoanCode(singleTO.getInventoryLoanCode());
                }
                if (singleTO.getCommissionPlaceholder() == null || singleTO.getCommissionPlaceholder().equalsIgnoreCase("")) {
                    this.setCommissionPlaceholder("");
                } else {
                    this.setCommissionPlaceholder(singleTO.getCommissionPlaceholder());
                }
                if (singleTO.getServiceChargeTblCode() == null || singleTO.getServiceChargeTblCode().equalsIgnoreCase("")) {
                    this.setServiceChargeTblCode("");
                } else {
                    this.setServiceChargeTblCode(singleTO.getServiceChargeTblCode());
                }
                if (singleTO.getCommissionTblCode() == null || singleTO.getCommissionTblCode().equalsIgnoreCase("")) {
                    this.setCommissionTblCode("0");
                } else {
                    this.setCommissionTblCode(singleTO.getCommissionTblCode());
                }
                if (singleTO.getPreOrPartClosurePenaltyCode() == null || singleTO.getPreOrPartClosurePenaltyCode().equalsIgnoreCase("")) {
                    this.setPrePartClosurePenaltyCode("");
                } else {
                    this.setPrePartClosurePenaltyCode(singleTO.getPreOrPartClosurePenaltyCode());
                }
                if (singleTO.getCommissionReportCode() == null || singleTO.getCommissionReportCode().equalsIgnoreCase("")) {
                    this.setCommissionReportCode("");
                } else {
                    this.setCommissionReportCode(singleTO.getCommissionReportCode());
                }
                if (singleTO.getAllowSweeps() == null || singleTO.getAllowSweeps().equalsIgnoreCase("")) {
                    this.setAllowSweepsmain("0");
                } else {
                    this.setAllowSweepsmain(singleTO.getAllowSweeps());
                }
                if (singleTO.getAllowPartClosure() == null || singleTO.getAllowPartClosure().equalsIgnoreCase("")) {
                    this.setAllowPartClosure("0");
                } else {
                    this.setAllowPartClosure(singleTO.getAllowPartClosure());
                }
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshDspmForm() {
        this.setDepositAmountMinimum("0.00");
        this.setDepositAmountMaximum("0.00");
        this.setDepositAmountSteps("0.00");
        this.setPeriodMiniMonths("");
        this.setPeriodMiniDaysMain("");
        this.setPeriodMaxMonthsMain("");
        this.setPeriodMaxDaysMain("");
        this.setPeriodStepsMonths("");
        this.setPeriodStepDays("");
        this.setDepositReportTemplate("");
        this.setDepositType("0");
        this.setAutoRenewalMain("0");
        this.setMaxRenewalAllowed("");
        this.setRenewalPeriodMonths("");
        this.setRenewalPeriodDays("");
        this.setRenewalAllowedPeriod("");
        this.setAutoRenewalGracePeriod("");
        this.setSundryDepositPlaceholder("");
        this.setRepaymentReportCode("");
        this.setValueDatedClosure("0");
        this.setCallDepositNoticePeriodMonths("");
        this.setCallDepositNoticePeriodDays("");
        this.setDelayInstallmentTblCode("");
        this.setDelayWithinMonth("");
        this.setDelayAllowedPeriodMonths("");
        this.setDelayAllowedPeriodDays("");
        this.setPenalFeePlaceholder("");
        this.setPenalFeeReportCode("");
        this.setPrintPBDR("0");
        this.setMatAmtToleranceLimit("0");
        this.setUseInventory("0");
        this.setInventoryClass("");
        this.setInventoryType("");
        this.setInventoryLoanClass("");
        this.setInventoryLoanCode("");
        this.setCommissionPlaceholder("");
        this.setServiceChargeTblCode("");
        this.setCommissionTblCode("0");
        this.setPrePartClosurePenaltyCode("");
        this.setCommissionReportCode("");
        this.setAllowSweepsmain("0");
        this.setAllowPartClosure("0");
    }

    public void enableDspmForm() {
        this.dspmFlag = false;
    }

    public void disableDspmForm() {
        this.dspmFlag = true;
    }
}
