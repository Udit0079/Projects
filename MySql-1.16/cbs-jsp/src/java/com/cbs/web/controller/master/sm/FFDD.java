/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeFlexiFixedDepositsDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FFDD {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for ffdd.jsp file
    String automaticallyCreateDeposits;
    private ArrayList<SelectItem> comboAutomaticallyCreateDeposits;
    String autoCrPerdMonths;
    String autoCrPerdDays;
    String autoCrFreqType;
    String autoCrFreqWeekNo;
    String autoCrFreqWeekDay;
    String autoCrFreqNP;
    private ArrayList<SelectItem> comboAutoCrFreqType;
    private ArrayList<SelectItem> comboAutoCrFreqWeekDay;
    String autoCrFreqStartDate;
    private ArrayList<SelectItem> comboAutoCrFreqNP;
    String createDepositIfOperativeAccountMoreThan;
    String createDepositsInStepsOf;
    String linkToOperativeAccount;
    private ArrayList<SelectItem> comboLinkToOperativeAccount;
    String breakDepositInStepsOf;
    String foreClosureInterestMethod;
    String addPreferentialtoPenaltyRate;
    private ArrayList<SelectItem> comboAddPreferentialtoPenaltyRate;
    private ArrayList<SelectItem> comboAutoCrFreqWeekNo;
    private ArrayList<SelectItem> comboForeClosureInterestMethod;
    private boolean ffDDFlag;
    //Getter-Setter for ffdd.jsp file

    public String getAddPreferentialtoPenaltyRate() {
        return addPreferentialtoPenaltyRate;
    }

    public void setAddPreferentialtoPenaltyRate(String addPreferentialtoPenaltyRate) {
        this.addPreferentialtoPenaltyRate = addPreferentialtoPenaltyRate;
    }

    public String getAutoCrFreqNP() {
        return autoCrFreqNP;
    }

    public void setAutoCrFreqNP(String autoCrFreqNP) {
        this.autoCrFreqNP = autoCrFreqNP;
    }

    public String getAutoCrFreqStartDate() {
        return autoCrFreqStartDate;
    }

    public void setAutoCrFreqStartDate(String autoCrFreqStartDate) {
        this.autoCrFreqStartDate = autoCrFreqStartDate;
    }

    public String getAutoCrFreqType() {
        return autoCrFreqType;
    }

    public void setAutoCrFreqType(String autoCrFreqType) {
        this.autoCrFreqType = autoCrFreqType;
    }

    public String getAutoCrFreqWeekDay() {
        return autoCrFreqWeekDay;
    }

    public void setAutoCrFreqWeekDay(String autoCrFreqWeekDay) {
        this.autoCrFreqWeekDay = autoCrFreqWeekDay;
    }

    public String getAutoCrFreqWeekNo() {
        return autoCrFreqWeekNo;
    }

    public void setAutoCrFreqWeekNo(String autoCrFreqWeekNo) {
        this.autoCrFreqWeekNo = autoCrFreqWeekNo;
    }

    public String getAutoCrPerdDays() {
        return autoCrPerdDays;
    }

    public void setAutoCrPerdDays(String autoCrPerdDays) {
        this.autoCrPerdDays = autoCrPerdDays;
    }

    public String getAutoCrPerdMonths() {
        return autoCrPerdMonths;
    }

    public void setAutoCrPerdMonths(String autoCrPerdMonths) {
        this.autoCrPerdMonths = autoCrPerdMonths;
    }

    public String getAutomaticallyCreateDeposits() {
        return automaticallyCreateDeposits;
    }

    public void setAutomaticallyCreateDeposits(String automaticallyCreateDeposits) {
        this.automaticallyCreateDeposits = automaticallyCreateDeposits;
    }

    public String getBreakDepositInStepsOf() {
        return breakDepositInStepsOf;
    }

    public void setBreakDepositInStepsOf(String breakDepositInStepsOf) {
        this.breakDepositInStepsOf = breakDepositInStepsOf;
    }

    public ArrayList<SelectItem> getComboAddPreferentialtoPenaltyRate() {
        return comboAddPreferentialtoPenaltyRate;
    }

    public void setComboAddPreferentialtoPenaltyRate(ArrayList<SelectItem> comboAddPreferentialtoPenaltyRate) {
        this.comboAddPreferentialtoPenaltyRate = comboAddPreferentialtoPenaltyRate;
    }

    public ArrayList<SelectItem> getComboAutoCrFreqNP() {
        return comboAutoCrFreqNP;
    }

    public void setComboAutoCrFreqNP(ArrayList<SelectItem> comboAutoCrFreqNP) {
        this.comboAutoCrFreqNP = comboAutoCrFreqNP;
    }

    public ArrayList<SelectItem> getComboAutoCrFreqType() {
        return comboAutoCrFreqType;
    }

    public void setComboAutoCrFreqType(ArrayList<SelectItem> comboAutoCrFreqType) {
        this.comboAutoCrFreqType = comboAutoCrFreqType;
    }

    public ArrayList<SelectItem> getComboAutoCrFreqWeekDay() {
        return comboAutoCrFreqWeekDay;
    }

    public void setComboAutoCrFreqWeekDay(ArrayList<SelectItem> comboAutoCrFreqWeekDay) {
        this.comboAutoCrFreqWeekDay = comboAutoCrFreqWeekDay;
    }

    public ArrayList<SelectItem> getComboAutomaticallyCreateDeposits() {
        return comboAutomaticallyCreateDeposits;
    }

    public void setComboAutomaticallyCreateDeposits(ArrayList<SelectItem> comboAutomaticallyCreateDeposits) {
        this.comboAutomaticallyCreateDeposits = comboAutomaticallyCreateDeposits;
    }

    public ArrayList<SelectItem> getComboLinkToOperativeAccount() {
        return comboLinkToOperativeAccount;
    }

    public void setComboLinkToOperativeAccount(ArrayList<SelectItem> comboLinkToOperativeAccount) {
        this.comboLinkToOperativeAccount = comboLinkToOperativeAccount;
    }

    public String getCreateDepositIfOperativeAccountMoreThan() {
        return createDepositIfOperativeAccountMoreThan;
    }

    public void setCreateDepositIfOperativeAccountMoreThan(String createDepositIfOperativeAccountMoreThan) {
        this.createDepositIfOperativeAccountMoreThan = createDepositIfOperativeAccountMoreThan;
    }

    public String getCreateDepositsInStepsOf() {
        return createDepositsInStepsOf;
    }

    public void setCreateDepositsInStepsOf(String createDepositsInStepsOf) {
        this.createDepositsInStepsOf = createDepositsInStepsOf;
    }

    public String getForeClosureInterestMethod() {
        return foreClosureInterestMethod;
    }

    public void setForeClosureInterestMethod(String foreClosureInterestMethod) {
        this.foreClosureInterestMethod = foreClosureInterestMethod;
    }

    public String getLinkToOperativeAccount() {
        return linkToOperativeAccount;
    }

    public void setLinkToOperativeAccount(String linkToOperativeAccount) {
        this.linkToOperativeAccount = linkToOperativeAccount;
    }

    public ArrayList<SelectItem> getComboAutoCrFreqWeekNo() {
        return comboAutoCrFreqWeekNo;
    }

    public void setComboAutoCrFreqWeekNo(ArrayList<SelectItem> comboAutoCrFreqWeekNo) {
        this.comboAutoCrFreqWeekNo = comboAutoCrFreqWeekNo;
    }

    public ArrayList<SelectItem> getComboForeClosureInterestMethod() {
        return comboForeClosureInterestMethod;
    }

    public void setComboForeClosureInterestMethod(ArrayList<SelectItem> comboForeClosureInterestMethod) {
        this.comboForeClosureInterestMethod = comboForeClosureInterestMethod;
    }

    public boolean isFfDDFlag() {
        return ffDDFlag;
    }

    public void setFfDDFlag(boolean ffDDFlag) {
        this.ffDDFlag = ffDDFlag;
    }

    public void automaticallyCreateDeposits() {
        setComboAutomaticallyCreateDeposits(new ArrayList<SelectItem>());
        comboAutomaticallyCreateDeposits.add(new SelectItem(" ", " "));
        comboAutomaticallyCreateDeposits.add(new SelectItem("Y", "YES"));
        comboAutomaticallyCreateDeposits.add(new SelectItem("N", "NO"));
    }

    public void addPreferentialtoPenaltyRate() {
        setComboAddPreferentialtoPenaltyRate(new ArrayList<SelectItem>());
        comboAddPreferentialtoPenaltyRate.add(new SelectItem(" ", ""));
        comboAddPreferentialtoPenaltyRate.add(new SelectItem("Y", "YES"));
        comboAddPreferentialtoPenaltyRate.add(new SelectItem("N", "NO"));
    }

    public void selectFFDDDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeFlexiFixedDepositsDetailsTO cbsSchemeFlexiFixedDepositsDetailsTO = schemeMasterManagementDelegate.getFfddDetails(schemeMaster.schemeCode);
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutomaticallyCreateDeposits() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutomaticallyCreateDeposits().equalsIgnoreCase(""))) {
                setAutomaticallyCreateDeposits(cbsSchemeFlexiFixedDepositsDetailsTO.getAutomaticallyCreateDeposits());
            } else {
                this.setAutomaticallyCreateDeposits("N");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrPerdMonths() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrPerdMonths().equalsIgnoreCase(""))) {
                setAutoCrPerdMonths(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrPerdMonths());
            } else {
                this.setAutoCrPerdMonths("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrPerdDays() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrPerdDays().equalsIgnoreCase(""))) {
                setAutoCrPerdDays(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrPerdDays());
            } else {
                this.setAutoCrPerdDays("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqType() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqType().equalsIgnoreCase(""))) {
                setAutoCrFreqType(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqType());
            } else {
                this.setAutoCrFreqType("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqWeekNo() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqWeekNo().equalsIgnoreCase(""))) {
                setAutoCrFreqWeekNo(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqWeekNo());
            } else {
                this.setAutoCrFreqWeekNo("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqWeekDay() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqWeekDay().equalsIgnoreCase(""))) {
                setAutoCrFreqWeekDay(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqWeekDay());
            } else {
                this.setAutoCrFreqWeekDay("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqStartDate() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqStartDate().equalsIgnoreCase(""))) {
                setAutoCrFreqStartDate(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqStartDate());
            } else {
                this.setAutoCrFreqStartDate("");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqNp() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqNp().equalsIgnoreCase(""))) {
                setAutoCrFreqNP(cbsSchemeFlexiFixedDepositsDetailsTO.getAutoCrFreqNp());
            } else {
                this.setAutoCrFreqNP("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getCreateDepositIfOperativeAccountMoreThan() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getCreateDepositIfOperativeAccountMoreThan().toString().equalsIgnoreCase(""))) {
                setCreateDepositIfOperativeAccountMoreThan(cbsSchemeFlexiFixedDepositsDetailsTO.getCreateDepositIfOperativeAccountMoreThan().toString());
            } else {
                this.setCreateDepositIfOperativeAccountMoreThan("0.00");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getCreateDepositsInStepsOf().toString() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getCreateDepositsInStepsOf().toString().equalsIgnoreCase(""))) {
                setCreateDepositsInStepsOf(cbsSchemeFlexiFixedDepositsDetailsTO.getCreateDepositsInStepsOf().toString());
            } else {
                this.setCreateDepositsInStepsOf("0.00");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getLinkToOperativeAccount() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getLinkToOperativeAccount().equalsIgnoreCase(""))) {
                setLinkToOperativeAccount(cbsSchemeFlexiFixedDepositsDetailsTO.getLinkToOperativeAccount());
            } else {
                this.setLinkToOperativeAccount("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getBreakDepositInStepsOf() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getBreakDepositInStepsOf().toString().equalsIgnoreCase(""))) {
                setBreakDepositInStepsOf(cbsSchemeFlexiFixedDepositsDetailsTO.getBreakDepositInStepsOf().toString());
            } else {
                this.setBreakDepositInStepsOf("0.00");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getForeClosureInterestMethod() != null || cbsSchemeFlexiFixedDepositsDetailsTO.getForeClosureInterestMethod().equalsIgnoreCase("")) {
                setForeClosureInterestMethod(cbsSchemeFlexiFixedDepositsDetailsTO.getForeClosureInterestMethod());
            } else {
                this.setForeClosureInterestMethod("0");
            }
            if (cbsSchemeFlexiFixedDepositsDetailsTO.getAddPreferentialToPenaltyRate() != null || (!cbsSchemeFlexiFixedDepositsDetailsTO.getAddPreferentialToPenaltyRate().equalsIgnoreCase(""))) {
                setAddPreferentialtoPenaltyRate(cbsSchemeFlexiFixedDepositsDetailsTO.getAddPreferentialToPenaltyRate());
            } else {
                this.setAddPreferentialtoPenaltyRate("0");
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshFFDDForm() {
        this.setAddPreferentialtoPenaltyRate("0");
        this.setAutoCrFreqNP("0");
        this.setAutoCrFreqStartDate("");
        this.setAutoCrFreqType("0");
        this.setAutoCrFreqWeekDay("0");
        this.setAutoCrFreqWeekNo("0");
        this.setAutoCrPerdDays("0");
        this.setAutoCrPerdMonths("0");
        this.setAutomaticallyCreateDeposits("N");
        this.setBreakDepositInStepsOf("0.00");
        this.setCreateDepositIfOperativeAccountMoreThan("0.00");
        this.setCreateDepositsInStepsOf("0.00");
        this.setForeClosureInterestMethod("0");
        this.setLinkToOperativeAccount("0");
    }

    public void enableFFDDForm() {
        this.ffDDFlag = false;
    }

    public void disableFFDDForm() {
        this.ffDDFlag = true;
    }
    public void autoCrFreqWeekDay() {
        setComboAutoCrFreqWeekDay(new ArrayList<SelectItem>());
        comboAutoCrFreqWeekDay.add(new SelectItem(" ", " "));
        comboAutoCrFreqWeekDay.add(new SelectItem("1", "SUN"));
        comboAutoCrFreqWeekDay.add(new SelectItem("2", "MON"));
        comboAutoCrFreqWeekDay.add(new SelectItem("3", "TUE"));
        comboAutoCrFreqWeekDay.add(new SelectItem("4", "WED"));
        comboAutoCrFreqWeekDay.add(new SelectItem("5", "THU"));
        comboAutoCrFreqWeekDay.add(new SelectItem("6", "FRI"));
        comboAutoCrFreqWeekDay.add(new SelectItem("7", "SAT"));
    }
    public void autoCrFreqType() {
        setComboAutoCrFreqType(new ArrayList<SelectItem>());
        comboAutoCrFreqType.add(new SelectItem(" ", " "));
        comboAutoCrFreqType.add(new SelectItem("D", "DAILY"));
        comboAutoCrFreqType.add(new SelectItem("W", "WEEKLY"));
        comboAutoCrFreqType.add(new SelectItem("M", "MONTHLY"));
        comboAutoCrFreqType.add(new SelectItem("Q", "QUARTERLY"));
        comboAutoCrFreqType.add(new SelectItem("Y", "YEARLY"));
    }
    public void autoCrFreqWeekNo() {
        setComboAutoCrFreqWeekNo(new ArrayList<SelectItem>());
        comboAutoCrFreqWeekNo.add(new SelectItem(" ", " "));
        comboAutoCrFreqWeekNo.add(new SelectItem("1", "FIRST WEEK"));
        comboAutoCrFreqWeekNo.add(new SelectItem("2", "SECOND WEEK"));
        comboAutoCrFreqWeekNo.add(new SelectItem("3", "THIRD WEEK"));
        comboAutoCrFreqWeekNo.add(new SelectItem("4", "FOURTH WEEK"));
        comboAutoCrFreqWeekNo.add(new SelectItem("M", "MIDDLE WEEK(2/3)"));
        comboAutoCrFreqWeekNo.add(new SelectItem("L", "LAST WEEK"));
    }
    public void autoCrFreqNP() {
        setComboAutoCrFreqNP(new ArrayList<SelectItem>());
        comboAutoCrFreqNP.add(new SelectItem(" ", " "));
        comboAutoCrFreqNP.add(new SelectItem("N", "NEXT"));
        comboAutoCrFreqNP.add(new SelectItem("P", "PREVIOUS"));
        comboAutoCrFreqNP.add(new SelectItem("S", "SKIP"));
    }
    public void comboForeClosureInterestMethod() {
        setComboForeClosureInterestMethod(new ArrayList<SelectItem>());
        comboForeClosureInterestMethod.add(new SelectItem(" ", " "));
        comboForeClosureInterestMethod.add(new SelectItem("D", "DAILY"));
        comboForeClosureInterestMethod.add(new SelectItem("M", "MONTHLY"));
    }
    public void linkToOperativeAccount() {
        setComboLinkToOperativeAccount(new ArrayList<SelectItem>());
        comboLinkToOperativeAccount.add(new SelectItem(" ", " "));
        comboLinkToOperativeAccount.add(new SelectItem("Y", "YES"));
        comboLinkToOperativeAccount.add(new SelectItem("N", "NO"));
    }

    /** Creates a new instance of FFDD */
    public FFDD() {
       
        this.automaticallyCreateDeposits();
        this.addPreferentialtoPenaltyRate();
        autoCrFreqWeekDay();
        autoCrFreqType();
        autoCrFreqWeekNo();
        autoCrFreqNP();
        linkToOperativeAccount();
        comboForeClosureInterestMethod();
        refreshFFDDForm();
        
    }
}
