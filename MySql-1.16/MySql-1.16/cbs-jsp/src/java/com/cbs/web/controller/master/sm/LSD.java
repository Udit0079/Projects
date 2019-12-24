/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLoanSchemeDetailsTO;
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
public class LSD {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;
    private String periodMinMonths;
    private String periodMaxMonths;
    private String periodMiniDays;
    private String periodMaxDays;
    private String amountMin;
    private String amountMax;
    private String repaymentMethod;
    private List<SelectItem> loanRepaymentMethod;
    private String holdInOpenAccountForAmount;
    private String upfrontInstallmentCollection;
    private String intBaseMethod;
    private String intProduct;
    private List<SelectItem> intProductMethod;
    private String route;
    private List<SelectItem> routeFlag;
    private String chargeRoute;
    private String intOrChrgAccount;
    private String equatedInstallments;
    private String eiINAdvance;
    private List<SelectItem> eIINAdvance;
    private String eiFormulaFlag;
    private List<SelectItem> eIFormulaFlag;
    private String eiRoundingOffAmount;
    private List<SelectItem> eIRoundingOffAmount;
    private String roundingOffInd;
    private List<SelectItem> eIRoundingOffInd;
    private String compoundingFreq;
    private String eiPaymentFreq;
    private String interestRestFreq;
    private String interestRestBasis;
    private String upfrontInterestCollection;
    private String discountedInterest;
    private boolean lsdFlag;
    private String intAmortization;
    private List<SelectItem> ddturnoverDetailFlag;
    private List<SelectItem> ddstmtFrqType;

    /** Creates a new instance of LSD */
    public LSD() {
       
        loanRepaymentMethod = new ArrayList<SelectItem>();
        loanRepaymentMethod.add(new SelectItem("0", ""));
        loanRepaymentMethod.add(new SelectItem("E", "RecoverFromoperA/cToTheExtentOfEffAvailAmt"));
        loanRepaymentMethod.add(new SelectItem("T", "RecoverShortFallAmtByGrantingTOD"));
        loanRepaymentMethod.add(new SelectItem("P", "PostDatedChq"));
        loanRepaymentMethod.add(new SelectItem("D", "ElectronicsClearing"));
        loanRepaymentMethod.add(new SelectItem("N", "NoBatchRecovery"));
        ddturnoverDetailFlag = new ArrayList<SelectItem>();
        ddturnoverDetailFlag.add(new SelectItem("0", ""));
        ddturnoverDetailFlag.add(new SelectItem("Y", "Yes"));
        ddturnoverDetailFlag.add(new SelectItem("N", "No"));
        ddstmtFrqType = new ArrayList<SelectItem>();
        ddstmtFrqType.add(new SelectItem("0", ""));
        ddstmtFrqType.add(new SelectItem("D", "Daily"));
        ddstmtFrqType.add(new SelectItem("W", "Weekly"));
        ddstmtFrqType.add(new SelectItem("M", "Monthly"));
        ddstmtFrqType.add(new SelectItem("Q", "Quarterly"));
        ddstmtFrqType.add(new SelectItem("H", "HalfYearly"));
        ddstmtFrqType.add(new SelectItem("Y", "Yearly"));
        intProductMethod = new ArrayList<SelectItem>();
        intProductMethod.add(new SelectItem("0", ""));
        intProductMethod.add(new SelectItem("E", "EOD Balance"));
        intProductMethod.add(new SelectItem("S", "Schedule Balance"));
        routeFlag = new ArrayList<SelectItem>();
        routeFlag.add(new SelectItem("0", ""));
        routeFlag.add(new SelectItem("L", "Loan Account"));
        routeFlag.add(new SelectItem("O", "Other Operative Account (SB, CA, CC, OD)"));
        eIINAdvance = new ArrayList<SelectItem>();
        eIINAdvance.add(new SelectItem("0", ""));
        eIINAdvance.add(new SelectItem("A", "Advance"));
        eIINAdvance.add(new SelectItem("D", "Day Of Month"));
        eIFormulaFlag = new ArrayList<SelectItem>();
        eIFormulaFlag.add(new SelectItem("0", ""));
        eIFormulaFlag.add(new SelectItem("P", "Pmt Formula"));
        eIFormulaFlag.add(new SelectItem("M", "EMI Formula"));
        eIFormulaFlag.add(new SelectItem("F", "Flat Rate"));
        eIFormulaFlag.add(new SelectItem("R", "Rule of 78"));
        eIRoundingOffAmount = new ArrayList<SelectItem>();
        eIRoundingOffAmount.add(new SelectItem("0", ""));
        eIRoundingOffAmount.add(new SelectItem("U", "Upward"));
        eIRoundingOffAmount.add(new SelectItem("D", "Downword"));
        eIRoundingOffInd = new ArrayList<SelectItem>();
        eIRoundingOffInd.add(new SelectItem("0", ""));
        eIRoundingOffInd.add(new SelectItem("H", "Next"));
        eIRoundingOffInd.add(new SelectItem("L", "Previous"));
        eIRoundingOffInd.add(new SelectItem("N", "Near"));
    }

    public boolean isLsdFlag() {
        return lsdFlag;
    }

    public void setLsdFlag(boolean lsdFlag) {
        this.lsdFlag = lsdFlag;
    }

    public List<SelectItem> getDdstmtFrqType() {
        return ddstmtFrqType;
    }

    public void setDdstmtFrqType(List<SelectItem> ddstmtFrqType) {
        this.ddstmtFrqType = ddstmtFrqType;
    }

    public List<SelectItem> getDdturnoverDetailFlag() {
        return ddturnoverDetailFlag;
    }

    public void setDdturnoverDetailFlag(List<SelectItem> ddturnoverDetailFlag) {
        this.ddturnoverDetailFlag = ddturnoverDetailFlag;
    }

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }

    public String getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(String amountMax) {
        this.amountMax = amountMax;
    }

    public String getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(String amountMin) {
        this.amountMin = amountMin;
    }

    public String getChargeRoute() {
        return chargeRoute;
    }

    public void setChargeRoute(String chargeRoute) {
        this.chargeRoute = chargeRoute;
    }

    public String getCompoundingFreq() {
        return compoundingFreq;
    }

    public void setCompoundingFreq(String compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public String getDiscountedInterest() {
        return discountedInterest;
    }

    public void setDiscountedInterest(String discountedInterest) {
        this.discountedInterest = discountedInterest;
    }

    public List<SelectItem> geteIFormulaFlag() {
        return eIFormulaFlag;
    }

    public void seteIFormulaFlag(List<SelectItem> eIFormulaFlag) {
        this.eIFormulaFlag = eIFormulaFlag;
    }

    public List<SelectItem> geteIINAdvance() {
        return eIINAdvance;
    }

    public void seteIINAdvance(List<SelectItem> eIINAdvance) {
        this.eIINAdvance = eIINAdvance;
    }

    public List<SelectItem> geteIRoundingOffAmount() {
        return eIRoundingOffAmount;
    }

    public void seteIRoundingOffAmount(List<SelectItem> eIRoundingOffAmount) {
        this.eIRoundingOffAmount = eIRoundingOffAmount;
    }

    public List<SelectItem> geteIRoundingOffInd() {
        return eIRoundingOffInd;
    }

    public void seteIRoundingOffInd(List<SelectItem> eIRoundingOffInd) {
        this.eIRoundingOffInd = eIRoundingOffInd;
    }

    public String getEiFormulaFlag() {
        return eiFormulaFlag;
    }

    public void setEiFormulaFlag(String eiFormulaFlag) {
        this.eiFormulaFlag = eiFormulaFlag;
    }

    public String getEiINAdvance() {
        return eiINAdvance;
    }

    public void setEiINAdvance(String eiINAdvance) {
        this.eiINAdvance = eiINAdvance;
    }

    public String getEiPaymentFreq() {
        return eiPaymentFreq;
    }

    public void setEiPaymentFreq(String eiPaymentFreq) {
        this.eiPaymentFreq = eiPaymentFreq;
    }

    public String getEiRoundingOffAmount() {
        return eiRoundingOffAmount;
    }

    public void setEiRoundingOffAmount(String eiRoundingOffAmount) {
        this.eiRoundingOffAmount = eiRoundingOffAmount;
    }

    public String getEquatedInstallments() {
        return equatedInstallments;
    }

    public void setEquatedInstallments(String equatedInstallments) {
        this.equatedInstallments = equatedInstallments;
    }

    public String getHoldInOpenAccountForAmount() {
        return holdInOpenAccountForAmount;
    }

    public void setHoldInOpenAccountForAmount(String holdInOpenAccountForAmount) {
        this.holdInOpenAccountForAmount = holdInOpenAccountForAmount;
    }

    public String getIntAmortization() {
        return intAmortization;
    }

    public void setIntAmortization(String intAmortization) {
        this.intAmortization = intAmortization;
    }

    public String getIntBaseMethod() {
        return intBaseMethod;
    }

    public void setIntBaseMethod(String intBaseMethod) {
        this.intBaseMethod = intBaseMethod;
    }

    public String getIntOrChrgAccount() {
        return intOrChrgAccount;
    }

    public void setIntOrChrgAccount(String intOrChrgAccount) {
        this.intOrChrgAccount = intOrChrgAccount;
    }

    public String getIntProduct() {
        return intProduct;
    }

    public void setIntProduct(String intProduct) {
        this.intProduct = intProduct;
    }

    public List<SelectItem> getIntProductMethod() {
        return intProductMethod;
    }

    public void setIntProductMethod(List<SelectItem> intProductMethod) {
        this.intProductMethod = intProductMethod;
    }

    public String getInterestRestBasis() {
        return interestRestBasis;
    }

    public void setInterestRestBasis(String interestRestBasis) {
        this.interestRestBasis = interestRestBasis;
    }

    public String getInterestRestFreq() {
        return interestRestFreq;
    }

    public void setInterestRestFreq(String interestRestFreq) {
        this.interestRestFreq = interestRestFreq;
    }

    public List<SelectItem> getLoanRepaymentMethod() {
        return loanRepaymentMethod;
    }

    public void setLoanRepaymentMethod(List<SelectItem> loanRepaymentMethod) {
        this.loanRepaymentMethod = loanRepaymentMethod;
    }

    public String getPeriodMaxDays() {
        return periodMaxDays;
    }

    public void setPeriodMaxDays(String periodMaxDays) {
        this.periodMaxDays = periodMaxDays;
    }

    public String getPeriodMaxMonths() {
        return periodMaxMonths;
    }

    public void setPeriodMaxMonths(String periodMaxMonths) {
        this.periodMaxMonths = periodMaxMonths;
    }

    public String getPeriodMinMonths() {
        return periodMinMonths;
    }

    public void setPeriodMinMonths(String periodMinMonths) {
        this.periodMinMonths = periodMinMonths;
    }

    public String getPeriodMiniDays() {
        return periodMiniDays;
    }

    public void setPeriodMiniDays(String periodMiniDays) {
        this.periodMiniDays = periodMiniDays;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getRoundingOffInd() {
        return roundingOffInd;
    }

    public void setRoundingOffInd(String roundingOffInd) {
        this.roundingOffInd = roundingOffInd;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<SelectItem> getRouteFlag() {
        return routeFlag;
    }

    public void setRouteFlag(List<SelectItem> routeFlag) {
        this.routeFlag = routeFlag;
    }

    public String getUpfrontInstallmentCollection() {
        return upfrontInstallmentCollection;
    }

    public void setUpfrontInstallmentCollection(String upfrontInstallmentCollection) {
        this.upfrontInstallmentCollection = upfrontInstallmentCollection;
    }

    public String getUpfrontInterestCollection() {
        return upfrontInterestCollection;
    }

    public void setUpfrontInterestCollection(String upfrontInterestCollection) {
        this.upfrontInterestCollection = upfrontInterestCollection;
    }

    public void selectLSDDetails() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeLoanSchemeDetailsTO cbsSchemeLoanSchemeDetailsTO = schemeMasterManagementDelegate.getLSDDetails(schemeMaster.schemeCode);
            if (cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMiniMonths() != null || (!cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMiniMonths().equalsIgnoreCase(""))) {
                setPeriodMinMonths(cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMiniMonths());
            } else {
                this.setPeriodMinMonths("0");
            }
            if (cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMiniDays()!= null || (!cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMiniDays().equalsIgnoreCase(""))) {
                setPeriodMiniDays(cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMiniDays());
            } else {
                this.setPeriodMiniDays("0");
            }
            if (cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMaxMonths()!= null || (!cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMaxMonths().equalsIgnoreCase(""))) {
                setPeriodMaxMonths(cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMaxMonths());
            } else {
                this.setPeriodMaxMonths("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMaxDays()!= null || (!cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMaxDays().equalsIgnoreCase(""))) {
                setPeriodMaxDays(cbsSchemeLoanSchemeDetailsTO.getLoanPeriodMaxDays());
            } else {
                this.setPeriodMaxDays("0");
            }
            if (cbsSchemeLoanSchemeDetailsTO.getLoanIntOrChrgAccountPlaceholder() != null || (!cbsSchemeLoanSchemeDetailsTO.getLoanIntOrChrgAccountPlaceholder().equalsIgnoreCase(""))) {
                setIntOrChrgAccount(cbsSchemeLoanSchemeDetailsTO.getLoanIntOrChrgAccountPlaceholder());
            } else {
                this.setIntOrChrgAccount("");
            }
            if (cbsSchemeLoanSchemeDetailsTO.getLoanAmountMin() != null || (!cbsSchemeLoanSchemeDetailsTO.getLoanAmountMin().toString().equalsIgnoreCase(""))) {
                setAmountMin(cbsSchemeLoanSchemeDetailsTO.getLoanAmountMin().toString());
            } else {
                this.setAmountMin("0.0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getLoanAmountMax() != null || (!cbsSchemeLoanSchemeDetailsTO.getLoanAmountMax().toString().equalsIgnoreCase(""))) {
                setAmountMax(cbsSchemeLoanSchemeDetailsTO.getLoanAmountMax().toString());
            } else {
                this.setAmountMax("0.0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getLoanRepaymentMethod() != null || (!cbsSchemeLoanSchemeDetailsTO.getLoanRepaymentMethod().equalsIgnoreCase(""))) {
                setRepaymentMethod(cbsSchemeLoanSchemeDetailsTO.getLoanRepaymentMethod());
            } else {
                this.setRepaymentMethod("0");
            }
              if (cbsSchemeLoanSchemeDetailsTO.getHoldInOpenAccountForAmountDue() != null || (!cbsSchemeLoanSchemeDetailsTO.getHoldInOpenAccountForAmountDue().equalsIgnoreCase(""))) {
                setHoldInOpenAccountForAmount(cbsSchemeLoanSchemeDetailsTO.getHoldInOpenAccountForAmountDue());
            } else {
                this.setHoldInOpenAccountForAmount("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getUpfrontInstallmentCollection() != null || (!cbsSchemeLoanSchemeDetailsTO.getUpfrontInstallmentCollection().equalsIgnoreCase(""))) {
                setUpfrontInstallmentCollection(cbsSchemeLoanSchemeDetailsTO.getUpfrontInstallmentCollection());
            } else {
                this.setUpfrontInstallmentCollection("0");
            }
              if (cbsSchemeLoanSchemeDetailsTO.getIntBaseMethod() != null || (!cbsSchemeLoanSchemeDetailsTO.getIntBaseMethod().equalsIgnoreCase(""))) {
                setIntBaseMethod(cbsSchemeLoanSchemeDetailsTO.getIntBaseMethod());
            } else {
                this.setIntBaseMethod("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getIntProductMethod() != null || (!cbsSchemeLoanSchemeDetailsTO.getIntProductMethod().equalsIgnoreCase(""))) {
                setIntProduct(cbsSchemeLoanSchemeDetailsTO.getIntProductMethod());
            } else {
                this.setIntProduct("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getIntRouteFlag() != null || (!cbsSchemeLoanSchemeDetailsTO.getIntRouteFlag().equalsIgnoreCase(""))) {
                setRoute(cbsSchemeLoanSchemeDetailsTO.getIntRouteFlag());
            } else {
                this.setRoute("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getChrargeRouteFlag()!= null || (!cbsSchemeLoanSchemeDetailsTO.getChrargeRouteFlag().equalsIgnoreCase(""))) {
                setChargeRoute(cbsSchemeLoanSchemeDetailsTO.getChrargeRouteFlag());
            } else {
                this.setChargeRoute("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getEquatedInstallments()!= null || (!cbsSchemeLoanSchemeDetailsTO.getEquatedInstallments().equalsIgnoreCase(""))) {
                setEquatedInstallments(cbsSchemeLoanSchemeDetailsTO.getEquatedInstallments());
            } else {
                this.setEquatedInstallments("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getEiInAdvance()!= null || (!cbsSchemeLoanSchemeDetailsTO.getEiInAdvance().equalsIgnoreCase(""))) {
                setEiINAdvance(cbsSchemeLoanSchemeDetailsTO.getEiInAdvance());
            } else {
                this.setEiINAdvance("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getEiFormulaFlag()!= null || (!cbsSchemeLoanSchemeDetailsTO.getEiFormulaFlag().equalsIgnoreCase(""))) {
                setEiFormulaFlag(cbsSchemeLoanSchemeDetailsTO.getEiFormulaFlag());
            } else {
                this.setEiFormulaFlag("0");
            }
              if (cbsSchemeLoanSchemeDetailsTO.getEiRoundingOffAmount()!= null || (!cbsSchemeLoanSchemeDetailsTO.getEiRoundingOffAmount().equalsIgnoreCase(""))) {
                setEiRoundingOffAmount(cbsSchemeLoanSchemeDetailsTO.getEiRoundingOffAmount());
            } else {
                this.setEiRoundingOffAmount("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getCompoundingFreq()!= null || (!cbsSchemeLoanSchemeDetailsTO.getCompoundingFreq().equalsIgnoreCase(""))) {
                setCompoundingFreq(cbsSchemeLoanSchemeDetailsTO.getCompoundingFreq());
            } else {
                this.setCompoundingFreq("0");
            }
               if (cbsSchemeLoanSchemeDetailsTO.getEiPaymentFreq()!= null || (!cbsSchemeLoanSchemeDetailsTO.getEiPaymentFreq().equalsIgnoreCase(""))) {
                setEiPaymentFreq(cbsSchemeLoanSchemeDetailsTO.getEiPaymentFreq());
            } else {
                this.setEiPaymentFreq("0");
            }
             if (cbsSchemeLoanSchemeDetailsTO.getInterestRestFreq()!= null || (!cbsSchemeLoanSchemeDetailsTO.getInterestRestFreq().equalsIgnoreCase(""))) {
                setInterestRestFreq(cbsSchemeLoanSchemeDetailsTO.getInterestRestFreq());
            } else {
                this.setInterestRestFreq("0");
            }   
             if (cbsSchemeLoanSchemeDetailsTO.getInterestRestBasis()!= null || (!cbsSchemeLoanSchemeDetailsTO.getInterestRestBasis().equalsIgnoreCase(""))) {
                setInterestRestBasis(cbsSchemeLoanSchemeDetailsTO.getInterestRestBasis());
            } else {
                this.setInterestRestBasis("0");
            }
              if (cbsSchemeLoanSchemeDetailsTO.getUpfrontInterestCollection()!= null || (!cbsSchemeLoanSchemeDetailsTO.getUpfrontInterestCollection().equalsIgnoreCase(""))) {
                setUpfrontInterestCollection(cbsSchemeLoanSchemeDetailsTO.getUpfrontInterestCollection());
            } else {
                this.setUpfrontInterestCollection("0");
            }
              if (cbsSchemeLoanSchemeDetailsTO.getDiscountedInterest()!= null || (!cbsSchemeLoanSchemeDetailsTO.getDiscountedInterest().equalsIgnoreCase(""))) {
                setDiscountedInterest(cbsSchemeLoanSchemeDetailsTO.getDiscountedInterest());
            } else {
                this.setDiscountedInterest("0");
            }
            if (cbsSchemeLoanSchemeDetailsTO.getIntAmortizationByRule78()!= null || (!cbsSchemeLoanSchemeDetailsTO.getIntAmortizationByRule78().equalsIgnoreCase(""))) {
                setIntAmortization(cbsSchemeLoanSchemeDetailsTO.getIntAmortizationByRule78());
            } else {
                this.setIntAmortization("0");
            }
            if (cbsSchemeLoanSchemeDetailsTO.getEiRoundingOffInd()!= null || (!cbsSchemeLoanSchemeDetailsTO.getEiRoundingOffInd().equalsIgnoreCase(""))) {
                setRoundingOffInd(cbsSchemeLoanSchemeDetailsTO.getEiRoundingOffInd());
            } else {
                this.setRoundingOffInd("0");
            }
                
             
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void refreshLSDForm() {
        setPeriodMinMonths("0");
        setPeriodMiniDays("0");
        setPeriodMaxMonths("0");
        setPeriodMaxDays("0");
        setIntOrChrgAccount("");
        setAmountMin("0.0");
        setAmountMax("0.0");
        setRepaymentMethod("0");
        setHoldInOpenAccountForAmount("0");
        setUpfrontInstallmentCollection("0");
        setIntBaseMethod("0");
        setIntProduct("0");
        setRoute("0");
        setChargeRoute("0");
        setEquatedInstallments("0");
        setEiINAdvance("0");
        setEiFormulaFlag("0");
        setEiRoundingOffAmount("0");
        setRoundingOffInd("0");
        setCompoundingFreq("0");
        setEiPaymentFreq("0");
        setInterestRestFreq("0");
        setInterestRestBasis("0");
        setUpfrontInterestCollection("0");
        setDiscountedInterest("0");
        setIntAmortization("0");
        
    }

    public void enableLSDForm() {
        lsdFlag = false;
    }

    public void disableLSDForm() {
        lsdFlag = true;
    }
}
