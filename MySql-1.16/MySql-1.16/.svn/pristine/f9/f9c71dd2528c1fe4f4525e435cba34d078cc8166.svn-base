/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLoanInterestDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class LID {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;
    private String intOnPrincipal;
    private String penalIntOnPrincipalDemandOverdue;
    private String principalDemandOverdueAtEndOfMonths;
    private String principalOverduePeriodMonths;
    private String principalOverduePeriodDays;
    private String intOnIntDemand;
    private String penalIntOnIntDemandOverdue;
    private String intDemandOverdueAtEndOfMonth;
    private String intOverduePeriodMonths;
    private String intOverduePeriodDays;
    private String overdueIntOnPrincipal;
    private String applyPreferentialForOverdueInt;
    private String intRateBasedOnLoanAmount;
    private String applyLateFeeForDelayedPayment;
    private String gracePeriodForLateFeeMonths;
    private String gracePeriodForLateFeeDays;
    private String toleranceLimitForDpdCycleAmount;
    private String toleranceLimitForDpdCycleType;
    private String considerToleranceForLateFee;
    private String createIntDemandFromRepSchedule;
    private String rephasementCarryOverdueDemands;
    private String priorityLoan;
    private String agriLoan;
    private String intLimit;
    private String coveredByDicge;
    private String dICGCFeeFlowId;
    private String dICGCFeeAccountPlaceholder;
    private String hirerDetails;
    private String aodOrAosType;
    private String subsidyAvailable;
    private String refinanceScheme;
    private boolean flagDisable;
    private List<SelectItem> ddAodOrAosType;
    private List<SelectItem> ddtrnRefNo;
    List interestDetails = new ArrayList();

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }

    public boolean isFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
    }

    public List<SelectItem> getDdtrnRefNo() {
        return ddtrnRefNo;
    }

    public void setDdtrnRefNo(List<SelectItem> ddtrnRefNo) {
        this.ddtrnRefNo = ddtrnRefNo;
    }

    public String getAgriLoan() {
        return agriLoan;
    }

    public void setAgriLoan(String agriLoan) {
        this.agriLoan = agriLoan;
    }

    public String getAodOrAosType() {
        return aodOrAosType;
    }

    public void setAodOrAosType(String aodOrAosType) {
        this.aodOrAosType = aodOrAosType;
    }

    public String getApplyLateFeeForDelayedPayment() {
        return applyLateFeeForDelayedPayment;
    }

    public void setApplyLateFeeForDelayedPayment(String applyLateFeeForDelayedPayment) {
        this.applyLateFeeForDelayedPayment = applyLateFeeForDelayedPayment;
    }

    public String getApplyPreferentialForOverdueInt() {
        return applyPreferentialForOverdueInt;
    }

    public void setApplyPreferentialForOverdueInt(String applyPreferentialForOverdueInt) {
        this.applyPreferentialForOverdueInt = applyPreferentialForOverdueInt;
    }

    public String getConsiderToleranceForLateFee() {
        return considerToleranceForLateFee;
    }

    public void setConsiderToleranceForLateFee(String considerToleranceForLateFee) {
        this.considerToleranceForLateFee = considerToleranceForLateFee;
    }

    public String getCoveredByDicge() {
        return coveredByDicge;
    }

    public void setCoveredByDicge(String coveredByDicge) {
        this.coveredByDicge = coveredByDicge;
    }

    public String getCreateIntDemandFromRepSchedule() {
        return createIntDemandFromRepSchedule;
    }

    public void setCreateIntDemandFromRepSchedule(String createIntDemandFromRepSchedule) {
        this.createIntDemandFromRepSchedule = createIntDemandFromRepSchedule;
    }

    public String getdICGCFeeAccountPlaceholder() {
        return dICGCFeeAccountPlaceholder;
    }

    public void setdICGCFeeAccountPlaceholder(String dICGCFeeAccountPlaceholder) {
        this.dICGCFeeAccountPlaceholder = dICGCFeeAccountPlaceholder;
    }

    public String getdICGCFeeFlowId() {
        return dICGCFeeFlowId;
    }

    public void setdICGCFeeFlowId(String dICGCFeeFlowId) {
        this.dICGCFeeFlowId = dICGCFeeFlowId;
    }

    public List<SelectItem> getDdAodOrAosType() {
        return ddAodOrAosType;
    }

    public void setDdAodOrAosType(List<SelectItem> ddAodOrAosType) {
        this.ddAodOrAosType = ddAodOrAosType;
    }

    public String getGracePeriodForLateFeeDays() {
        return gracePeriodForLateFeeDays;
    }

    public void setGracePeriodForLateFeeDays(String gracePeriodForLateFeeDays) {
        this.gracePeriodForLateFeeDays = gracePeriodForLateFeeDays;
    }

    public String getGracePeriodForLateFeeMonths() {
        return gracePeriodForLateFeeMonths;
    }

    public void setGracePeriodForLateFeeMonths(String gracePeriodForLateFeeMonths) {
        this.gracePeriodForLateFeeMonths = gracePeriodForLateFeeMonths;
    }

    public String getHirerDetails() {
        return hirerDetails;
    }

    public void setHirerDetails(String hirerDetails) {
        this.hirerDetails = hirerDetails;
    }

    public String getIntDemandOverdueAtEndOfMonth() {
        return intDemandOverdueAtEndOfMonth;
    }

    public void setIntDemandOverdueAtEndOfMonth(String intDemandOverdueAtEndOfMonth) {
        this.intDemandOverdueAtEndOfMonth = intDemandOverdueAtEndOfMonth;
    }

    public String getIntLimit() {
        return intLimit;
    }

    public void setIntLimit(String intLimit) {
        this.intLimit = intLimit;
    }

    public String getIntOnIntDemand() {
        return intOnIntDemand;
    }

    public void setIntOnIntDemand(String intOnIntDemand) {
        this.intOnIntDemand = intOnIntDemand;
    }

    public String getIntOnPrincipal() {
        return intOnPrincipal;
    }

    public void setIntOnPrincipal(String intOnPrincipal) {
        this.intOnPrincipal = intOnPrincipal;
    }

    public String getIntOverduePeriodDays() {
        return intOverduePeriodDays;
    }

    public void setIntOverduePeriodDays(String intOverduePeriodDays) {
        this.intOverduePeriodDays = intOverduePeriodDays;
    }

    public String getIntOverduePeriodMonths() {
        return intOverduePeriodMonths;
    }

    public void setIntOverduePeriodMonths(String intOverduePeriodMonths) {
        this.intOverduePeriodMonths = intOverduePeriodMonths;
    }

    public String getIntRateBasedOnLoanAmount() {
        return intRateBasedOnLoanAmount;
    }

    public void setIntRateBasedOnLoanAmount(String intRateBasedOnLoanAmount) {
        this.intRateBasedOnLoanAmount = intRateBasedOnLoanAmount;
    }

    public List getInterestDetails() {
        return interestDetails;
    }

    public void setInterestDetails(List interestDetails) {
        this.interestDetails = interestDetails;
    }

    public String getOverdueIntOnPrincipal() {
        return overdueIntOnPrincipal;
    }

    public void setOverdueIntOnPrincipal(String overdueIntOnPrincipal) {
        this.overdueIntOnPrincipal = overdueIntOnPrincipal;
    }

    public String getPenalIntOnIntDemandOverdue() {
        return penalIntOnIntDemandOverdue;
    }

    public void setPenalIntOnIntDemandOverdue(String penalIntOnIntDemandOverdue) {
        this.penalIntOnIntDemandOverdue = penalIntOnIntDemandOverdue;
    }

    public String getPenalIntOnPrincipalDemandOverdue() {
        return penalIntOnPrincipalDemandOverdue;
    }

    public void setPenalIntOnPrincipalDemandOverdue(String penalIntOnPrincipalDemandOverdue) {
        this.penalIntOnPrincipalDemandOverdue = penalIntOnPrincipalDemandOverdue;
    }

    public String getPrincipalDemandOverdueAtEndOfMonths() {
        return principalDemandOverdueAtEndOfMonths;
    }

    public void setPrincipalDemandOverdueAtEndOfMonths(String principalDemandOverdueAtEndOfMonths) {
        this.principalDemandOverdueAtEndOfMonths = principalDemandOverdueAtEndOfMonths;
    }

    public String getPrincipalOverduePeriodDays() {
        return principalOverduePeriodDays;
    }

    public void setPrincipalOverduePeriodDays(String principalOverduePeriodDays) {
        this.principalOverduePeriodDays = principalOverduePeriodDays;
    }

    public String getPrincipalOverduePeriodMonths() {
        return principalOverduePeriodMonths;
    }

    public void setPrincipalOverduePeriodMonths(String principalOverduePeriodMonths) {
        this.principalOverduePeriodMonths = principalOverduePeriodMonths;
    }

    public String getPriorityLoan() {
        return priorityLoan;
    }

    public void setPriorityLoan(String priorityLoan) {
        this.priorityLoan = priorityLoan;
    }

    public String getRefinanceScheme() {
        return refinanceScheme;
    }

    public void setRefinanceScheme(String refinanceScheme) {
        this.refinanceScheme = refinanceScheme;
    }

    public String getRephasementCarryOverdueDemands() {
        return rephasementCarryOverdueDemands;
    }

    public void setRephasementCarryOverdueDemands(String rephasementCarryOverdueDemands) {
        this.rephasementCarryOverdueDemands = rephasementCarryOverdueDemands;
    }

    public String getSubsidyAvailable() {
        return subsidyAvailable;
    }

    public void setSubsidyAvailable(String subsidyAvailable) {
        this.subsidyAvailable = subsidyAvailable;
    }

    public String getToleranceLimitForDpdCycleAmount() {
        return toleranceLimitForDpdCycleAmount;
    }

    public void setToleranceLimitForDpdCycleAmount(String toleranceLimitForDpdCycleAmount) {
        this.toleranceLimitForDpdCycleAmount = toleranceLimitForDpdCycleAmount;
    }

    public String getToleranceLimitForDpdCycleType() {
        return toleranceLimitForDpdCycleType;
    }

    public void setToleranceLimitForDpdCycleType(String toleranceLimitForDpdCycleType) {
        this.toleranceLimitForDpdCycleType = toleranceLimitForDpdCycleType;
    }

    public LID() {
        
        try {
            ddtrnRefNo = new ArrayList<SelectItem>();
            ddtrnRefNo.add(new SelectItem("0", ""));
            ddtrnRefNo.add(new SelectItem("Y", "Yes"));
            ddtrnRefNo.add(new SelectItem("N", "No"));
            ddAodOrAosType = new ArrayList<SelectItem>();
            ddAodOrAosType.add(new SelectItem("0", ""));
            ddAodOrAosType.add(new SelectItem("L", "Loan Paper Date"));
            ddAodOrAosType.add(new SelectItem("M", "First Default Date"));
            ddAodOrAosType.add(new SelectItem("R", "Repayment Date"));
            setToleranceLimitForDpdCycleAmount("0.0");
            setIntLimit("0.0");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refreshLidForm() {
        schemeMaster.setMessage("");
        try {
            setIntOnPrincipal("0");
            setPenalIntOnPrincipalDemandOverdue("0");
            setPrincipalDemandOverdueAtEndOfMonths("0");
            setPrincipalOverduePeriodMonths("");
            setPrincipalOverduePeriodDays("");
            setIntOnIntDemand("0");
            setPenalIntOnIntDemandOverdue("0");
            setIntDemandOverdueAtEndOfMonth("0");
            setIntOverduePeriodMonths("0");
            setIntOverduePeriodDays("0");
            setOverdueIntOnPrincipal("0");
            setApplyPreferentialForOverdueInt("0");
            setIntRateBasedOnLoanAmount("0");
            setApplyLateFeeForDelayedPayment("0");
            setGracePeriodForLateFeeMonths("");
            setGracePeriodForLateFeeDays("");
            setToleranceLimitForDpdCycleAmount("0.0");
            setToleranceLimitForDpdCycleType("");
            setConsiderToleranceForLateFee("0");
            setCreateIntDemandFromRepSchedule("0");
            setRephasementCarryOverdueDemands("0");
            setPriorityLoan("0");
            setAgriLoan("0");
            setIntLimit("0.0");
            setCoveredByDicge("0");
            setdICGCFeeFlowId("0");
            setdICGCFeeAccountPlaceholder("");
            setHirerDetails("0");
            setAodOrAosType("0");
            setSubsidyAvailable("0");
            setRefinanceScheme("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void enableLidForm(){
        flagDisable=false;
    }
     public void disableLidForm(){
        flagDisable=true;
    }

    public void selectsschemeLoanInterestDetails() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeLoanInterestDetailsTO> singleTO = schemeMasterManagementDelegate.getLoaninterestDetails(schemeMaster.getSchemeCode());
            if (singleTO != null) {
                for (CbsSchemeLoanInterestDetailsTO intDetails : singleTO) {
                    if (intDetails.getIntOnPrincipal() == null || intDetails.getIntOnPrincipal().toString().equalsIgnoreCase("")) {
                        setIntOnPrincipal("0");
                    } else {
                        setIntOnPrincipal(intDetails.getIntOnPrincipal().toString());
                    }
                    if (intDetails.getPenalIntOnPrincipalDemandOverdue() == null || intDetails.getPenalIntOnPrincipalDemandOverdue().toString().equalsIgnoreCase("")) {
                        setPenalIntOnPrincipalDemandOverdue("0");
                    } else {
                        setPenalIntOnPrincipalDemandOverdue(intDetails.getPenalIntOnPrincipalDemandOverdue().toString());
                    }
                    if (intDetails.getPrincipalDemandOverdueAtEndOfMonths() == null || intDetails.getPrincipalDemandOverdueAtEndOfMonths().toString().equalsIgnoreCase("")) {
                        setPrincipalDemandOverdueAtEndOfMonths("");
                    } else {
                        setPrincipalDemandOverdueAtEndOfMonths(intDetails.getPrincipalDemandOverdueAtEndOfMonths().toString());
                    }
                    if (intDetails.getPrincipalOverduePeriodMonths() == null || intDetails.getPrincipalOverduePeriodMonths().toString().equalsIgnoreCase("")) {
                        setPrincipalOverduePeriodMonths("");
                    } else {
                        setPrincipalOverduePeriodMonths(intDetails.getPrincipalOverduePeriodMonths().toString());
                    }
                    if (intDetails.getPrincipalOverduePeriodDays() == null || intDetails.getPrincipalOverduePeriodDays().toString().equalsIgnoreCase("")) {
                        setPrincipalOverduePeriodDays("");
                    } else {
                        setPrincipalOverduePeriodDays(intDetails.getPrincipalOverduePeriodDays().toString());
                    }
                    if (intDetails.getIntOnIntDemand() == null || intDetails.getIntOnIntDemand().toString().equalsIgnoreCase("")) {
                        setIntOnIntDemand("0");
                    } else {
                        setIntOnIntDemand(intDetails.getIntOnIntDemand().toString());
                    }
                    if (intDetails.getPenalIntOnIntDemandOverdue() == null || intDetails.getPenalIntOnIntDemandOverdue().toString().equalsIgnoreCase("")) {
                        setPenalIntOnIntDemandOverdue("0");
                    } else {
                        setPenalIntOnIntDemandOverdue(intDetails.getPenalIntOnIntDemandOverdue().toString());
                    }
                    if (intDetails.getIntDemandOverdueAtEndOfMonth() == null || intDetails.getIntDemandOverdueAtEndOfMonth().toString().equalsIgnoreCase("")) {
                        setIntDemandOverdueAtEndOfMonth("0");
                    } else {
                        setIntDemandOverdueAtEndOfMonth(intDetails.getIntDemandOverdueAtEndOfMonth().toString());
                    }
                    if (intDetails.getIntOverduePeriodMonths() == null || intDetails.getIntOverduePeriodMonths().toString().equalsIgnoreCase("")) {
                          setIntOverduePeriodMonths("0");
                    } else {
                           setIntOverduePeriodMonths(intDetails.getIntOverduePeriodMonths().toString());
                    }
                    if (intDetails.getIntOverduePeriodDays() == null || intDetails.getIntOverduePeriodDays().toString().equalsIgnoreCase("")) {
                        setIntOverduePeriodDays("");
                    } else {
                        setIntOverduePeriodDays(intDetails.getIntOverduePeriodDays().toString());
                    }
                    if (intDetails.getOverdueIntOnPrincipal() == null || intDetails.getOverdueIntOnPrincipal().toString().equalsIgnoreCase("")) {
                        setOverdueIntOnPrincipal("0");
                    } else {
                        setOverdueIntOnPrincipal(intDetails.getOverdueIntOnPrincipal().toString());
                    }
                    if (intDetails.getApplyPreferentialForOverdueInt() == null || intDetails.getApplyPreferentialForOverdueInt().toString().equalsIgnoreCase("")) {
                        setApplyPreferentialForOverdueInt("0");
                    } else {
                        setApplyPreferentialForOverdueInt(intDetails.getApplyPreferentialForOverdueInt().toString());
                    }
                    if (intDetails.getIntRateBasedOnLoanAmount() == null || intDetails.getIntRateBasedOnLoanAmount().toString().equalsIgnoreCase("")) {
                        setIntRateBasedOnLoanAmount("0");
                    } else {
                        setIntRateBasedOnLoanAmount(intDetails.getIntRateBasedOnLoanAmount().toString());
                    }
                    if (intDetails.getApplyLateFeeForDelayedPayment() == null || intDetails.getApplyLateFeeForDelayedPayment().toString().equalsIgnoreCase("")) {
                        setApplyLateFeeForDelayedPayment("0");
                    } else {
                        setApplyLateFeeForDelayedPayment(intDetails.getApplyLateFeeForDelayedPayment().toString());
                    }
                    if (intDetails.getGracePeriodForLateFeeMonths() == null || intDetails.getGracePeriodForLateFeeMonths().toString().equalsIgnoreCase("")) {
                        setGracePeriodForLateFeeMonths("");
                    } else {
                        setGracePeriodForLateFeeMonths(intDetails.getGracePeriodForLateFeeMonths().toString());
                    }
                    if (intDetails.getGracePeriodForLateFeeDays() == null || intDetails.getGracePeriodForLateFeeDays().toString().equalsIgnoreCase("")) {
                        setGracePeriodForLateFeeDays("");
                    } else {
                        setGracePeriodForLateFeeDays(intDetails.getGracePeriodForLateFeeDays().toString());
                    }
                    if (intDetails.getToleranceLimitForDpdCycleAmount() == null || intDetails.getToleranceLimitForDpdCycleAmount().toString().equalsIgnoreCase("")) {
                        setToleranceLimitForDpdCycleAmount("0.0");
                    } else {
                        setToleranceLimitForDpdCycleAmount(intDetails.getToleranceLimitForDpdCycleAmount().toString());
                    }
                    if (intDetails.getToleranceLimitForDpdCycleType() == null || intDetails.getToleranceLimitForDpdCycleType().toString().equalsIgnoreCase("")) {
                        setToleranceLimitForDpdCycleType("");
                    } else {
                        setToleranceLimitForDpdCycleType(intDetails.getToleranceLimitForDpdCycleType().toString());
                    }
                    if (intDetails.getConsiderToleranceForLateFee() == null || intDetails.getConsiderToleranceForLateFee().toString().equalsIgnoreCase("")) {
                        setConsiderToleranceForLateFee("0");
                    } else {
                        setConsiderToleranceForLateFee(intDetails.getConsiderToleranceForLateFee().toString());
                    }
                    if (intDetails.getCreateIntDemandFromRepSchedule() == null || intDetails.getCreateIntDemandFromRepSchedule().toString().equalsIgnoreCase("")) {
                        setCreateIntDemandFromRepSchedule("0");
                    } else {
                        setCreateIntDemandFromRepSchedule(intDetails.getCreateIntDemandFromRepSchedule().toString());
                    }
                    if (intDetails.getRephasementCarryOverdueDemands() == null || intDetails.getRephasementCarryOverdueDemands().toString().equalsIgnoreCase("")) {
                        setRephasementCarryOverdueDemands("0");
                    } else {
                        setRephasementCarryOverdueDemands(intDetails.getRephasementCarryOverdueDemands().toString());
                    }
                    if (intDetails.getPriorityLoan() == null || intDetails.getPriorityLoan().toString().equalsIgnoreCase("")) {
                        setPriorityLoan("0");
                    } else {
                        setPriorityLoan(intDetails.getPriorityLoan().toString());
                    }
                    if (intDetails.getAgriLoan() == null || intDetails.getAgriLoan().toString().equalsIgnoreCase("")) {
                        setAgriLoan("0");
                    } else {
                        setAgriLoan(intDetails.getAgriLoan().toString());
                    }
                    if (intDetails.getIntLimit() == null || intDetails.getIntLimit().toString().equalsIgnoreCase("")) {
                        setIntLimit("0.0");
                    } else {
                        setIntLimit(intDetails.getIntLimit().toString());
                    }
                    if (intDetails.getCoveredByDicge() == null || intDetails.getCoveredByDicge().toString().equalsIgnoreCase("")) {
                        setCoveredByDicge("0");
                    } else {
                        setCoveredByDicge(intDetails.getCoveredByDicge().toString());
                    }
                    if (intDetails.getDicgcFeeFlowId() == null || intDetails.getDicgcFeeFlowId().toString().equalsIgnoreCase("")) {
                        setdICGCFeeFlowId("0");
                    } else {
                        setdICGCFeeFlowId(intDetails.getDicgcFeeFlowId().toString());
                    }
                    if (intDetails.getDicgcFeeAccountPlaceholer() == null || intDetails.getDicgcFeeAccountPlaceholer().toString().equalsIgnoreCase("")) {
                        setdICGCFeeAccountPlaceholder("");
                    } else {
                        setdICGCFeeAccountPlaceholder(intDetails.getDicgcFeeAccountPlaceholer().toString());
                    }
                    if (intDetails.getHirerDetails() == null || intDetails.getHirerDetails().toString().equalsIgnoreCase("")) {
                        setHirerDetails("0");
                    } else {
                        setHirerDetails(intDetails.getHirerDetails().toString());
                    }
                    if (intDetails.getAodOrAosType() == null || intDetails.getAodOrAosType().toString().equalsIgnoreCase("")) {
                        setAodOrAosType("0");
                    } else {
                        setAodOrAosType(intDetails.getAodOrAosType().toString());
                    }
                    if (intDetails.getSubsidyAvailable() == null || intDetails.getSubsidyAvailable().toString().equalsIgnoreCase("")) {
                        setSubsidyAvailable("0");
                    } else {
                        setSubsidyAvailable(intDetails.getSubsidyAvailable().toString());
                    }
                    if (intDetails.getRefinanceScheme() == null || intDetails.getRefinanceScheme().toString().equalsIgnoreCase("")) {
                        setRefinanceScheme("");
                    } else {
                        setRefinanceScheme(intDetails.getRefinanceScheme().toString());
                    }
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
}
