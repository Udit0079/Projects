/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeDepositInterestDefinitionDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDepositInterestDefinitionDetailsTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.DepositInt;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class DIDD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables For didd.jsp file
    private String interestMethod;
    private String maxDepositPeriodMonths;
    private String maxDepositPeriodDays;
    private String baseAmtInd;
    private String compoundingPeriod;
    private String compoundingBase;
    private String minCompoundingPeriod;
    private String minCompoundingBase;
    private String brokenPeriodMethod;
    private String brokenPeriodBase;
    private String deleteFlag2;
    private int currentRow3;
    private int countDeposit1;
    private int countDeposit2;
    boolean disableInterest;
    private boolean diddFlag;
    private int schemeDeposit = 0;
    private List<SelectItem> ddBaseAmtInd;
    private List<SelectItem> ddBrokenPeriodMthd;
    private List<SelectItem> ddDiddTrnRefNo;
    private List<DepositInt> deposit;
    private DepositInt currentItem3 = new DepositInt();
    private List<DepositInt> deposittmp;

    //Getter-Setter for didd.jsp file
    public List<SelectItem> getDdDiddTrnRefNo() {
        return ddDiddTrnRefNo;
    }

    public void setDdDiddTrnRefNo(List<SelectItem> ddDiddTrnRefNo) {
        this.ddDiddTrnRefNo = ddDiddTrnRefNo;
    }

    public boolean isDiddFlag() {
        return diddFlag;
    }

    public void setDiddFlag(boolean diddFlag) {
        this.diddFlag = diddFlag;
    }

    public String getBaseAmtInd() {
        return baseAmtInd;
    }

    public void setBaseAmtInd(String baseAmtInd) {
        this.baseAmtInd = baseAmtInd;
    }

    public String getBrokenPeriodBase() {
        return brokenPeriodBase;
    }

    public void setBrokenPeriodBase(String brokenPeriodBase) {
        this.brokenPeriodBase = brokenPeriodBase;
    }

    public String getBrokenPeriodMethod() {
        return brokenPeriodMethod;
    }

    public void setBrokenPeriodMethod(String brokenPeriodMethod) {
        this.brokenPeriodMethod = brokenPeriodMethod;
    }

    public String getCompoundingBase() {
        return compoundingBase;
    }

    public void setCompoundingBase(String compoundingBase) {
        this.compoundingBase = compoundingBase;
    }

    public String getCompoundingPeriod() {
        return compoundingPeriod;
    }

    public void setCompoundingPeriod(String compoundingPeriod) {
        this.compoundingPeriod = compoundingPeriod;
    }

    public int getCountDeposit1() {
        return countDeposit1;
    }

    public void setCountDeposit1(int countDeposit1) {
        this.countDeposit1 = countDeposit1;
    }

    public int getCountDeposit2() {
        return countDeposit2;
    }

    public void setCountDeposit2(int countDeposit2) {
        this.countDeposit2 = countDeposit2;
    }

    public DepositInt getCurrentItem3() {
        return currentItem3;
    }

    public void setCurrentItem3(DepositInt currentItem3) {
        this.currentItem3 = currentItem3;
    }

    public int getCurrentRow3() {
        return currentRow3;
    }

    public void setCurrentRow3(int currentRow3) {
        this.currentRow3 = currentRow3;
    }

    public List<SelectItem> getDdBaseAmtInd() {
        return ddBaseAmtInd;
    }

    public void setDdBaseAmtInd(List<SelectItem> ddBaseAmtInd) {
        this.ddBaseAmtInd = ddBaseAmtInd;
    }

    public List<SelectItem> getDdBrokenPeriodMthd() {
        return ddBrokenPeriodMthd;
    }

    public void setDdBrokenPeriodMthd(List<SelectItem> ddBrokenPeriodMthd) {
        this.ddBrokenPeriodMthd = ddBrokenPeriodMthd;
    }

    public String getDeleteFlag2() {
        return deleteFlag2;
    }

    public void setDeleteFlag2(String deleteFlag2) {
        this.deleteFlag2 = deleteFlag2;
    }

    public List<DepositInt> getDeposit() {
        return deposit;
    }

    public void setDeposit(List<DepositInt> deposit) {
        this.deposit = deposit;
    }

    public List<DepositInt> getDeposittmp() {
        return deposittmp;
    }

    public void setDeposittmp(List<DepositInt> deposittmp) {
        this.deposittmp = deposittmp;
    }

    public boolean isDisableInterest() {
        return disableInterest;
    }

    public void setDisableInterest(boolean disableInterest) {
        this.disableInterest = disableInterest;
    }

    public String getInterestMethod() {
        return interestMethod;
    }

    public void setInterestMethod(String interestMethod) {
        this.interestMethod = interestMethod;
    }

    public String getMaxDepositPeriodDays() {
        return maxDepositPeriodDays;
    }

    public void setMaxDepositPeriodDays(String maxDepositPeriodDays) {
        this.maxDepositPeriodDays = maxDepositPeriodDays;
    }

    public String getMaxDepositPeriodMonths() {
        return maxDepositPeriodMonths;
    }

    public void setMaxDepositPeriodMonths(String maxDepositPeriodMonths) {
        this.maxDepositPeriodMonths = maxDepositPeriodMonths;
    }

    public String getMinCompoundingBase() {
        return minCompoundingBase;
    }

    public void setMinCompoundingBase(String minCompoundingBase) {
        this.minCompoundingBase = minCompoundingBase;
    }

    public String getMinCompoundingPeriod() {
        return minCompoundingPeriod;
    }

    public void setMinCompoundingPeriod(String minCompoundingPeriod) {
        this.minCompoundingPeriod = minCompoundingPeriod;
    }

    public int getSchemeDeposit() {
        return schemeDeposit;
    }

    public void setSchemeDeposit(int schemeDeposit) {
        this.schemeDeposit = schemeDeposit;
    }

    /** Creates a new instance of DIDD */
    public DIDD() {
       
        try {
            deposit = new ArrayList<DepositInt>();
            deposittmp = new ArrayList<DepositInt>();

            ddBaseAmtInd = new ArrayList<SelectItem>();
            ddBaseAmtInd.add(new SelectItem("0", ""));
            ddBaseAmtInd.add(new SelectItem("P", "PRINCIPAL"));
            ddBaseAmtInd.add(new SelectItem("C", "CUMULATIVE"));

            ddBrokenPeriodMthd = new ArrayList<SelectItem>();
            ddBrokenPeriodMthd.add(new SelectItem("0", ""));
            ddBrokenPeriodMthd.add(new SelectItem("S", "SIMPLE"));
            ddBrokenPeriodMthd.add(new SelectItem("T", "COMPOUND"));

            ddDiddTrnRefNo = new ArrayList<SelectItem>();
            ddDiddTrnRefNo.add(new SelectItem("0", ""));
            ddDiddTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddDiddTrnRefNo.add(new SelectItem("N", "No"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for didd.jsp file
    public void selectDIDDDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeDepositInterestDefinitionDetailsTO> toS = schemeMasterManagementDelegate.getDIDDDetails(schemeMaster.getSchemeCode());
            if (toS.size() > 0) {
                for (CbsSchemeDepositInterestDefinitionDetailsTO listTO : toS) {
                    DepositInt di = new DepositInt();
                    CbsSchemeDepositInterestDefinitionDetailsPKTO objPkTo = listTO.getCbsSchemeDepositInterestDefinitionDetailsPKTO();
                    if (objPkTo.getInterestMethod() == null || objPkTo.getInterestMethod().equalsIgnoreCase("")) {
                        di.setTblInterestMethod("");
                    } else {
                        di.setTblInterestMethod(objPkTo.getInterestMethod());
                    }
                    if (listTO.getMaxDepositPeriodMonths() == null || listTO.getMaxDepositPeriodMonths().equalsIgnoreCase("")) {
                        di.setTblMaxDepositPeriodMonths("");
                    } else {
                        di.setTblMaxDepositPeriodMonths(listTO.getMaxDepositPeriodMonths());
                    }
                    if (listTO.getMaxDepositPeriodDays() == null || listTO.getMaxDepositPeriodDays().equalsIgnoreCase("")) {
                        di.setTblMaxDepositPeriodDays("");
                    } else {
                        di.setTblMaxDepositPeriodDays(listTO.getMaxDepositPeriodDays());
                    }
                    if (listTO.getBaseAmountInd() == null || listTO.getBaseAmountInd().equalsIgnoreCase("")) {
                        di.setTblBaseAmtInd("");
                    } else {
                        di.setTblBaseAmtInd(listTO.getBaseAmountInd());
                    }
                    if (listTO.getCompoundingPeriod() == null || listTO.getCompoundingPeriod().equalsIgnoreCase("")) {
                        di.setTblCompoundingPeriod("");
                    } else {
                        di.setTblCompoundingPeriod(listTO.getCompoundingPeriod());
                    }
                    if (listTO.getCompoundingBase() == null || listTO.getCompoundingBase().equalsIgnoreCase("")) {
                        di.setTblCompoundingBase("");
                    } else {
                        di.setTblCompoundingBase(listTO.getCompoundingBase());
                    }
                    if (listTO.getMinCompoundingPeriod() == null || listTO.getMinCompoundingPeriod().equalsIgnoreCase("")) {
                        di.setTblMinCompoundingPeriod("");
                    } else {
                        di.setTblMinCompoundingPeriod(listTO.getMinCompoundingPeriod());
                    }
                    if (listTO.getMinCompoundingBase() == null || listTO.getMinCompoundingBase().equalsIgnoreCase("")) {
                        di.setTblMinCompoundingBase("");
                    } else {
                        di.setTblMinCompoundingBase(listTO.getMinCompoundingBase());
                    }
                    if (listTO.getBrokenPeriodMthd() == null || listTO.getBrokenPeriodMthd().equalsIgnoreCase("")) {
                        di.setTblBrokenPeriodMethod("");
                    } else {
                        di.setTblBrokenPeriodMethod(listTO.getBrokenPeriodMthd());
                    }
                    if (listTO.getBrokenPeriodBase() == null || listTO.getBrokenPeriodBase().equalsIgnoreCase("")) {
                        di.setTblBrokenPeriodBase("");
                    } else {
                        di.setTblBrokenPeriodBase(listTO.getBrokenPeriodBase());
                    }
                    if (listTO.getDelFlag() == null || listTO.getDelFlag().equalsIgnoreCase("")) {
                        di.setTblDeleteFlag("");
                    } else {
                        di.setTblDeleteFlag(listTO.getDelFlag());
                    }
                    di.setCounterSaveUpdateDeposit("G");
                    deposit.add(di);
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

    public void setDepositInterestTable() {
        this.disableInterest = false;
        List<DepositInt> depositTemp = deposit;

        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (schemeDeposit == 0) {
                for (int i = 0; i < depositTemp.size(); i++) {
                    String tmpCompare = depositTemp.get(i).getTblInterestMethod();
                    if (tmpCompare.equalsIgnoreCase(interestMethod)) {
                        schemeMaster.setMessage("interest Method Already Exists In The Table. Please Select other interest Method.");
                        return;
                    }
                }
            }
        }

        DepositInt stk2 = new DepositInt();
        stk2.setTblInterestMethod(interestMethod);
        stk2.setTblMaxDepositPeriodMonths(maxDepositPeriodMonths);
        stk2.setTblMaxDepositPeriodDays(maxDepositPeriodDays);
        stk2.setTblBaseAmtInd(baseAmtInd);
        stk2.setTblCompoundingPeriod(compoundingPeriod);
        stk2.setTblCompoundingBase(compoundingBase);
        stk2.setTblMinCompoundingPeriod(minCompoundingPeriod);
        stk2.setTblMinCompoundingBase(minCompoundingBase);
        stk2.setTblBrokenPeriodMethod(brokenPeriodMethod);
        stk2.setTblBrokenPeriodBase(brokenPeriodBase);
        stk2.setTblDeleteFlag(deleteFlag2);
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            stk2.setCounterSaveUpdateDeposit("S");
            refreshDIDDForm();
        }

        deposit.add(stk2);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (schemeDeposit != 1) {
                for (int i = 0; i < depositTemp.size(); i++) {
                    String referenceTypetmp = depositTemp.get(i).getTblInterestMethod();
                    if (!referenceTypetmp.equalsIgnoreCase(interestMethod)) {
                        stk2.setCounterSaveUpdateDeposit("S");
                        deposittmp.add(stk2);
                        refreshDIDDForm();
                        return;
                    }
                }
            }
            if (currentItem3.getTblInterestMethod() == null || currentItem3.getTblInterestMethod().equalsIgnoreCase("")) {
                stk2.setCounterSaveUpdateDeposit("S");
                deposittmp.add(stk2);
                refreshDIDDForm();
                return;
            } else if (currentItem3.getTblInterestMethod().equalsIgnoreCase(interestMethod)) {
                if (!currentItem3.getTblInterestMethod().equalsIgnoreCase(interestMethod)
                        || !currentItem3.getTblMaxDepositPeriodMonths().equalsIgnoreCase(maxDepositPeriodMonths)
                        || !currentItem3.getTblMaxDepositPeriodDays().equalsIgnoreCase(maxDepositPeriodDays)
                        || !currentItem3.getTblBaseAmtInd().equalsIgnoreCase(baseAmtInd)
                        || !currentItem3.getTblCompoundingPeriod().equalsIgnoreCase(compoundingPeriod)
                        || !currentItem3.getTblCompoundingBase().equalsIgnoreCase(compoundingBase)
                        || !currentItem3.getTblMinCompoundingPeriod().equalsIgnoreCase(minCompoundingPeriod)
                        || !currentItem3.getTblMinCompoundingBase().equalsIgnoreCase(minCompoundingBase)
                        || !currentItem3.getTblBrokenPeriodMethod().equalsIgnoreCase(brokenPeriodMethod)
                        || !currentItem3.getTblBrokenPeriodBase().equalsIgnoreCase(brokenPeriodBase)
                        || !currentItem3.getTblDeleteFlag().equalsIgnoreCase(deleteFlag2)) {
                    stk2.setCounterSaveUpdateDeposit("U");
                    refreshDIDDForm();
                    deposittmp.add(stk2);
                }
                schemeDeposit = 0;
            }
        }
    }

    public void selectDepositInterest() {
        this.disableInterest = true;
        schemeMaster.setMessage("");
        schemeDeposit = 1;

        if (!(interestMethod.equalsIgnoreCase("0") && currentItem3.getTblInterestMethod() != null)) {
            if (!interestMethod.equalsIgnoreCase("")) {
                if (!interestMethod.equalsIgnoreCase(currentItem3.getTblInterestMethod())) {
                    countDeposit2 = countDeposit1;
                    countDeposit1 = countDeposit1 + 1;
                    if (countDeposit2 != countDeposit1) {

                        setDepositInterestTable();
                    }
                } else {
                    countDeposit1 = 0;
                }
            }
        }

        setInterestMethod(currentItem3.getTblInterestMethod());
        setMaxDepositPeriodMonths(currentItem3.getTblMaxDepositPeriodMonths());
        setMaxDepositPeriodDays(currentItem3.getTblMaxDepositPeriodDays());
        setBaseAmtInd(currentItem3.getTblBaseAmtInd());
        setCompoundingPeriod(currentItem3.getTblCompoundingPeriod());
        setCompoundingBase(currentItem3.getTblCompoundingBase());
        setMinCompoundingPeriod(currentItem3.getTblMinCompoundingPeriod());
        setMinCompoundingBase(currentItem3.getTblMinCompoundingBase());
        setBrokenPeriodMethod(currentItem3.getTblBrokenPeriodMethod());
        setBrokenPeriodBase(currentItem3.getTblBrokenPeriodBase());
        setDeleteFlag2(currentItem3.getTblDeleteFlag());
        deposit.remove(currentRow3);
    }

    public void refreshDIDDForm() {
        this.setInterestMethod("");
        this.setMaxDepositPeriodMonths("");
        this.setMaxDepositPeriodDays("");
        this.setBaseAmtInd("0");
        this.setCompoundingPeriod("");
        this.setCompoundingBase("");
        this.setMinCompoundingPeriod("");
        this.setMinCompoundingBase("");
        this.setBrokenPeriodMethod("0");
        this.setBrokenPeriodBase("");
        this.setDeleteFlag2("0");
    }

    public void enableDIDDForm() {
        this.diddFlag = false;
    }

    public void disableDIDDForm() {
        this.diddFlag = true;
    }
}
