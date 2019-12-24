/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeLoanPreEiSetupDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
public class LPESD {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for lpesd.jsp file
    private String normalHolidayPeriodMonths;
    private String maxHolidayPeriodAllowedMonths;
    private String intDuringHolidayPeriod;
    private String intFreqDuringHolidayPeriodType;
    private String intFreqDuringHolidayPeriodWeekNo;
    private String intFreqDuringHolidayPeriodWeekDay;
    private String intFreqDuringHolidayPeriodStartDate;
    private String intFreqDuringHolidayPeriodNp;
    private String multipleDisbursementsAllowed;
    private String autoProcessAfterHolidayPeriod;
    private String oddDaysInt;
    private boolean lpesdFlag;
    private String residualBalanceWaiverLimit;
    private String residualBalanceAbsorbLimit;
    private String placeholderForResidualBalanceWaiver;
    private String placeholderForResidualBalanceAbsorb;
    private String maxCycleForPromptPaymentDiscount;
    private String eventIDForPromptPaymentDiscount;
    private List<SelectItem> ddintDuringHolidayPeriod;
    List preEISetupDetails = new ArrayList();
    private List<SelectItem> ddturnoverFreqType;
    private List<SelectItem> ddturnoverFreqNo;
    private List<SelectItem> ddturnoverFreqDay;
    private List<SelectItem> ddturnoverFreqNp;
    private List<SelectItem> ddtrnRefNo;

    public List getPreEISetupDetails() {
        return preEISetupDetails;
    }

    public void setPreEISetupDetails(List preEISetupDetails) {
        this.preEISetupDetails = preEISetupDetails;
    }

    //Getter-Setter for lpesd.jsp file
    public List<SelectItem> getDdtrnRefNo() {
        return ddtrnRefNo;
    }

    public void setDdtrnRefNo(List<SelectItem> ddtrnRefNo) {
        this.ddtrnRefNo = ddtrnRefNo;
    }

    public List<SelectItem> getDdturnoverFreqDay() {
        return ddturnoverFreqDay;
    }

    public void setDdturnoverFreqDay(List<SelectItem> ddturnoverFreqDay) {
        this.ddturnoverFreqDay = ddturnoverFreqDay;
    }

    public List<SelectItem> getDdturnoverFreqNo() {
        return ddturnoverFreqNo;
    }

    public void setDdturnoverFreqNo(List<SelectItem> ddturnoverFreqNo) {
        this.ddturnoverFreqNo = ddturnoverFreqNo;
    }

    public List<SelectItem> getDdturnoverFreqNp() {
        return ddturnoverFreqNp;
    }

    public void setDdturnoverFreqNp(List<SelectItem> ddturnoverFreqNp) {
        this.ddturnoverFreqNp = ddturnoverFreqNp;
    }

    public List<SelectItem> getDdturnoverFreqType() {
        return ddturnoverFreqType;
    }

    public void setDdturnoverFreqType(List<SelectItem> ddturnoverFreqType) {
        this.ddturnoverFreqType = ddturnoverFreqType;
    }

    public boolean isLpesdFlag() {
        return lpesdFlag;
    }

    public void setLpesdFlag(boolean lpesdFlag) {
        this.lpesdFlag = lpesdFlag;
    }

    public String getAutoProcessAfterHolidayPeriod() {
        return autoProcessAfterHolidayPeriod;
    }

    public void setAutoProcessAfterHolidayPeriod(String autoProcessAfterHolidayPeriod) {
        this.autoProcessAfterHolidayPeriod = autoProcessAfterHolidayPeriod;
    }

    public List<SelectItem> getDdintDuringHolidayPeriod() {
        return ddintDuringHolidayPeriod;
    }

    public void setDdintDuringHolidayPeriod(List<SelectItem> ddintDuringHolidayPeriod) {
        this.ddintDuringHolidayPeriod = ddintDuringHolidayPeriod;
    }

    public String getEventIDForPromptPaymentDiscount() {
        return eventIDForPromptPaymentDiscount;
    }

    public void setEventIDForPromptPaymentDiscount(String eventIDForPromptPaymentDiscount) {
        this.eventIDForPromptPaymentDiscount = eventIDForPromptPaymentDiscount;
    }

    public String getIntDuringHolidayPeriod() {
        return intDuringHolidayPeriod;
    }

    public void setIntDuringHolidayPeriod(String intDuringHolidayPeriod) {
        this.intDuringHolidayPeriod = intDuringHolidayPeriod;
    }

    public String getIntFreqDuringHolidayPeriodNp() {
        return intFreqDuringHolidayPeriodNp;
    }

    public void setIntFreqDuringHolidayPeriodNp(String intFreqDuringHolidayPeriodNp) {
        this.intFreqDuringHolidayPeriodNp = intFreqDuringHolidayPeriodNp;
    }

    public String getIntFreqDuringHolidayPeriodStartDate() {
        return intFreqDuringHolidayPeriodStartDate;
    }

    public void setIntFreqDuringHolidayPeriodStartDate(String intFreqDuringHolidayPeriodStartDate) {
        this.intFreqDuringHolidayPeriodStartDate = intFreqDuringHolidayPeriodStartDate;
    }

    public String getIntFreqDuringHolidayPeriodType() {
        return intFreqDuringHolidayPeriodType;
    }

    public void setIntFreqDuringHolidayPeriodType(String intFreqDuringHolidayPeriodType) {
        this.intFreqDuringHolidayPeriodType = intFreqDuringHolidayPeriodType;
    }

    public String getIntFreqDuringHolidayPeriodWeekDay() {
        return intFreqDuringHolidayPeriodWeekDay;
    }

    public void setIntFreqDuringHolidayPeriodWeekDay(String intFreqDuringHolidayPeriodWeekDay) {
        this.intFreqDuringHolidayPeriodWeekDay = intFreqDuringHolidayPeriodWeekDay;
    }

    public String getIntFreqDuringHolidayPeriodWeekNo() {
        return intFreqDuringHolidayPeriodWeekNo;
    }

    public void setIntFreqDuringHolidayPeriodWeekNo(String intFreqDuringHolidayPeriodWeekNo) {
        this.intFreqDuringHolidayPeriodWeekNo = intFreqDuringHolidayPeriodWeekNo;
    }

    public String getMaxCycleForPromptPaymentDiscount() {
        return maxCycleForPromptPaymentDiscount;
    }

    public void setMaxCycleForPromptPaymentDiscount(String maxCycleForPromptPaymentDiscount) {
        this.maxCycleForPromptPaymentDiscount = maxCycleForPromptPaymentDiscount;
    }

    public String getMaxHolidayPeriodAllowedMonths() {
        return maxHolidayPeriodAllowedMonths;
    }

    public void setMaxHolidayPeriodAllowedMonths(String maxHolidayPeriodAllowedMonths) {
        this.maxHolidayPeriodAllowedMonths = maxHolidayPeriodAllowedMonths;
    }

    public String getMultipleDisbursementsAllowed() {
        return multipleDisbursementsAllowed;
    }

    public void setMultipleDisbursementsAllowed(String multipleDisbursementsAllowed) {
        this.multipleDisbursementsAllowed = multipleDisbursementsAllowed;
    }

    public String getNormalHolidayPeriodMonths() {
        return normalHolidayPeriodMonths;
    }

    public void setNormalHolidayPeriodMonths(String normalHolidayPeriodMonths) {
        this.normalHolidayPeriodMonths = normalHolidayPeriodMonths;
    }

    public String getOddDaysInt() {
        return oddDaysInt;
    }

    public void setOddDaysInt(String oddDaysInt) {
        this.oddDaysInt = oddDaysInt;
    }

    public String getPlaceholderForResidualBalanceAbsorb() {
        return placeholderForResidualBalanceAbsorb;
    }

    public void setPlaceholderForResidualBalanceAbsorb(String placeholderForResidualBalanceAbsorb) {
        this.placeholderForResidualBalanceAbsorb = placeholderForResidualBalanceAbsorb;
    }

    public String getPlaceholderForResidualBalanceWaiver() {
        return placeholderForResidualBalanceWaiver;
    }

    public void setPlaceholderForResidualBalanceWaiver(String placeholderForResidualBalanceWaiver) {
        this.placeholderForResidualBalanceWaiver = placeholderForResidualBalanceWaiver;
    }

    public String getResidualBalanceAbsorbLimit() {
        return residualBalanceAbsorbLimit;
    }

    public void setResidualBalanceAbsorbLimit(String residualBalanceAbsorbLimit) {
        this.residualBalanceAbsorbLimit = residualBalanceAbsorbLimit;
    }

    public String getResidualBalanceWaiverLimit() {
        return residualBalanceWaiverLimit;
    }

    public void setResidualBalanceWaiverLimit(String residualBalanceWaiverLimit) {
        this.residualBalanceWaiverLimit = residualBalanceWaiverLimit;
    }

    /** Creates a new instance of LPESD */
    public LPESD() {
       
        try {
            ddturnoverFreqType = new ArrayList<SelectItem>();
            ddturnoverFreqType.add(new SelectItem("0", ""));
            ddturnoverFreqType.add(new SelectItem("D", "Daily"));
            ddturnoverFreqType.add(new SelectItem("W", "Weekly"));
            ddturnoverFreqType.add(new SelectItem("M", "Monthly"));
            ddturnoverFreqType.add(new SelectItem("Q", "Quarterly"));
            ddturnoverFreqType.add(new SelectItem("H", "HalfYearly"));
            ddturnoverFreqType.add(new SelectItem("Y", "Yearly"));

            ddturnoverFreqNo = new ArrayList<SelectItem>();
            ddturnoverFreqNo.add(new SelectItem("0", ""));
            ddturnoverFreqNo.add(new SelectItem("1", "First Week"));
            ddturnoverFreqNo.add(new SelectItem("2", "Second week"));
            ddturnoverFreqNo.add(new SelectItem("3", "Third week"));
            ddturnoverFreqNo.add(new SelectItem("4", "Forth week"));
            ddturnoverFreqNo.add(new SelectItem("M", "Middle Week"));
            ddturnoverFreqNo.add(new SelectItem("L", "Last week"));

            ddturnoverFreqDay = new ArrayList<SelectItem>();
            ddturnoverFreqDay.add(new SelectItem("0", ""));
            ddturnoverFreqDay.add(new SelectItem("1", "Sun"));
            ddturnoverFreqDay.add(new SelectItem("2", "Mon"));
            ddturnoverFreqDay.add(new SelectItem("3", "Tue"));
            ddturnoverFreqDay.add(new SelectItem("4", "Wed"));
            ddturnoverFreqDay.add(new SelectItem("5", "Thu"));
            ddturnoverFreqDay.add(new SelectItem("6", "Fri"));
            ddturnoverFreqDay.add(new SelectItem("7", "Sat"));

            ddturnoverFreqNp = new ArrayList<SelectItem>();
            ddturnoverFreqNp.add(new SelectItem("0", ""));
            ddturnoverFreqNp.add(new SelectItem("N", "Next"));
            ddturnoverFreqNp.add(new SelectItem("P", "Previous"));
            ddturnoverFreqNp.add(new SelectItem("S", "Skip"));

            ddtrnRefNo = new ArrayList<SelectItem>();
            ddtrnRefNo.add(new SelectItem("0", ""));
            ddtrnRefNo.add(new SelectItem("Y", "Yes"));
            ddtrnRefNo.add(new SelectItem("N", "No"));

            ddintDuringHolidayPeriod = new ArrayList<SelectItem>();
            ddintDuringHolidayPeriod.add(new SelectItem("0", ""));
            ddintDuringHolidayPeriod.add(new SelectItem("A", "APPLY"));
            ddintDuringHolidayPeriod.add(new SelectItem("C", "CAPITALISE"));
            ddintDuringHolidayPeriod.add(new SelectItem("S", "SINGLE DEMAND"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void selectLpesdDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeLoanPreEiSetupDetailsTO singleTO = schemeMasterManagementDelegate.getLoanPreEiDetails(schemeMaster.getSchemeCode());
            if (singleTO != null) {
                if (singleTO.getNormalHolidayPeriodMonths() == null || singleTO.getNormalHolidayPeriodMonths().equalsIgnoreCase("")) {
                    this.setNormalHolidayPeriodMonths("");
                } else {
                    this.setNormalHolidayPeriodMonths(singleTO.getNormalHolidayPeriodMonths());
                }
                if (singleTO.getMaxHolidayPeriodAllowedMonths() == null || singleTO.getMaxHolidayPeriodAllowedMonths().equalsIgnoreCase("")) {
                    this.setMaxHolidayPeriodAllowedMonths("");
                } else {
                    this.setMaxHolidayPeriodAllowedMonths(singleTO.getMaxHolidayPeriodAllowedMonths());
                }
                if (singleTO.getIntDuringHolidayPeriod() == null || singleTO.getIntDuringHolidayPeriod().equalsIgnoreCase("")) {
                    this.setIntDuringHolidayPeriod("");
                } else {
                    this.setIntDuringHolidayPeriod(singleTO.getIntDuringHolidayPeriod());
                }
                if (singleTO.getIntFreqDuringHolidayPeriodType() == null || singleTO.getIntFreqDuringHolidayPeriodType().equalsIgnoreCase("")) {
                    this.setIntFreqDuringHolidayPeriodType("");
                } else {
                    this.setIntFreqDuringHolidayPeriodType(singleTO.getIntFreqDuringHolidayPeriodType());
                }
                if (singleTO.getIntFreqDuringHolidayPeriodWeekNo() == null || singleTO.getIntFreqDuringHolidayPeriodWeekNo().equalsIgnoreCase("")) {
                    this.setIntFreqDuringHolidayPeriodWeekNo("");
                } else {
                    this.setIntFreqDuringHolidayPeriodWeekNo(singleTO.getIntFreqDuringHolidayPeriodWeekNo());
                }
                if (singleTO.getIntFreqDuringHolidayPeriodWeekDay() == null || singleTO.getIntFreqDuringHolidayPeriodWeekDay().equalsIgnoreCase("")) {
                    this.setIntFreqDuringHolidayPeriodWeekDay("");
                } else {
                    this.setIntFreqDuringHolidayPeriodWeekDay(singleTO.getIntFreqDuringHolidayPeriodWeekDay());
                }
                if (singleTO.getIntFreqDuringHolidayPeriodStartDate() == null || singleTO.getIntFreqDuringHolidayPeriodStartDate().equalsIgnoreCase("")) {
                    this.setIntFreqDuringHolidayPeriodStartDate("");
                } else {
                    this.setIntFreqDuringHolidayPeriodStartDate(singleTO.getIntFreqDuringHolidayPeriodStartDate());
                }
                if (singleTO.getIntFreqDuringHolidayPeriodNp() == null || singleTO.getIntFreqDuringHolidayPeriodNp().equalsIgnoreCase("")) {
                    this.setIntFreqDuringHolidayPeriodNp("");
                } else {
                    this.setIntFreqDuringHolidayPeriodNp(singleTO.getIntFreqDuringHolidayPeriodNp());
                }
                if (singleTO.getMultipleDisbursementsAllowed() == null || singleTO.getMultipleDisbursementsAllowed().equalsIgnoreCase("")) {
                    this.setMultipleDisbursementsAllowed("");
                } else {
                    this.setMultipleDisbursementsAllowed(singleTO.getMultipleDisbursementsAllowed());
                }
                if (singleTO.getAutoProcessAfterHolidayPeriod() == null || singleTO.getAutoProcessAfterHolidayPeriod().equalsIgnoreCase("")) {
                    this.setAutoProcessAfterHolidayPeriod("");
                } else {
                    this.setAutoProcessAfterHolidayPeriod(singleTO.getAutoProcessAfterHolidayPeriod());
                }
                if (singleTO.getOddDaysInt() == null) {
                    this.setOddDaysInt("");
                } else {
                    this.setOddDaysInt(singleTO.getOddDaysInt().toString());
                }
                if (singleTO.getResidualBalanceWaiverLimit() == null) {
                    this.setResidualBalanceWaiverLimit("");
                } else {
                    this.setResidualBalanceWaiverLimit(singleTO.getResidualBalanceWaiverLimit().toString());
                }
                if (singleTO.getResidualBalanceAbsorbLimit() == null) {
                    this.setResidualBalanceAbsorbLimit("");
                } else {
                    this.setResidualBalanceAbsorbLimit(singleTO.getResidualBalanceAbsorbLimit().toString());
                }
                if (singleTO.getPlaceholderForResidualBalanceWaiver() == null || singleTO.getPlaceholderForResidualBalanceWaiver().equalsIgnoreCase("")) {
                    this.setPlaceholderForResidualBalanceWaiver("");
                } else {
                    this.setPlaceholderForResidualBalanceWaiver(singleTO.getPlaceholderForResidualBalanceWaiver());
                }
                if (singleTO.getPlaceholderForResidualBalanceAbsorb() == null || singleTO.getPlaceholderForResidualBalanceAbsorb().equalsIgnoreCase("")) {
                    this.setPlaceholderForResidualBalanceAbsorb("");
                } else {
                    this.setPlaceholderForResidualBalanceAbsorb(singleTO.getPlaceholderForResidualBalanceAbsorb());
                }
                if (singleTO.getMaxCycleForPromptPaymentDiscount() == null) {
                    this.setMaxCycleForPromptPaymentDiscount("");
                } else {
                    this.setMaxCycleForPromptPaymentDiscount(singleTO.getMaxCycleForPromptPaymentDiscount().toString());
                }
                if (singleTO.getEventIdForPromptPaymentDiscount() == null || singleTO.getEventIdForPromptPaymentDiscount().equalsIgnoreCase("")) {
                    this.setEventIDForPromptPaymentDiscount("");
                } else {
                    this.setEventIDForPromptPaymentDiscount(singleTO.getEventIdForPromptPaymentDiscount());
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

    public void refreshLpesdForm() {
        try {
            this.setNormalHolidayPeriodMonths("");
            this.setMaxHolidayPeriodAllowedMonths("");
            this.setIntDuringHolidayPeriod("");
            this.setIntFreqDuringHolidayPeriodType("");
            this.setIntFreqDuringHolidayPeriodWeekNo("");
            this.setIntFreqDuringHolidayPeriodWeekDay("");
            this.setIntFreqDuringHolidayPeriodStartDate("");
            this.setIntFreqDuringHolidayPeriodNp("");
            this.setMultipleDisbursementsAllowed("");
            this.setAutoProcessAfterHolidayPeriod("");
            this.setOddDaysInt("0");
            this.setResidualBalanceWaiverLimit("0.00");
            this.setResidualBalanceAbsorbLimit("0.00");
            this.setPlaceholderForResidualBalanceWaiver("");
            this.setPlaceholderForResidualBalanceAbsorb("");
            this.setMaxCycleForPromptPaymentDiscount("0.00");
            this.setEventIDForPromptPaymentDiscount("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void enableLpesdForm() {
        this.lpesdFlag = false;
    }

    public void disableLpesdForm() {
        this.lpesdFlag = true;
    }
}
