/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;


import com.cbs.dto.loan.CbsSchemeLoanRepaymentCycleDefinitionPKTO;
import com.cbs.dto.loan.CbsSchemeLoanRepaymentCycleDefinitionTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.LoanRepaymentCycleDefination;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class LRCD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for lrcd.jsp file
    private String acOpenFromDate;
    private String acOpenToDate;
    private String repaymentStartDate;
    private String monthIndicator;
    private String delFlagAw;
    private int currentRowLoanRepayment;
    int count1LoanRepayment = 0;
    int count2LoanRepayment;
    int selectCountLoanRepay = 0;
    private LoanRepaymentCycleDefination currentItemLoanRepayment = new LoanRepaymentCycleDefination();
    private List<LoanRepaymentCycleDefination> loanRepay;
    private List<LoanRepaymentCycleDefination> loanRepayTmp;
    private ArrayList<SelectItem> comboMonthIndicator;
    private ArrayList<SelectItem> comboDelFlag;

    //Getter-Setter for lrcd.jsp file
    public List<LoanRepaymentCycleDefination> getLoanRepayTmp() {
        return loanRepayTmp;
    }

    public void setLoanRepayTmp(List<LoanRepaymentCycleDefination> loanRepayTmp) {
        this.loanRepayTmp = loanRepayTmp;
    }

    public int getSelectCountLoanRepay() {
        return selectCountLoanRepay;
    }

    public void setSelectCountLoanRepay(int selectCountLoanRepay) {
        this.selectCountLoanRepay = selectCountLoanRepay;
    }

    public int getCount1LoanRepayment() {
        return count1LoanRepayment;
    }

    public void setCount1LoanRepayment(int count1LoanRepayment) {
        this.count1LoanRepayment = count1LoanRepayment;
    }

    public int getCount2LoanRepayment() {
        return count2LoanRepayment;
    }

    public void setCount2LoanRepayment(int count2LoanRepayment) {
        this.count2LoanRepayment = count2LoanRepayment;
    }

    public String getAcOpenFromDate() {
        return acOpenFromDate;
    }

    public void setAcOpenFromDate(String acOpenFromDate) {
        this.acOpenFromDate = acOpenFromDate;
    }

    public String getAcOpenToDate() {
        return acOpenToDate;
    }

    public void setAcOpenToDate(String acOpenToDate) {
        this.acOpenToDate = acOpenToDate;
    }

    public ArrayList<SelectItem> getComboDelFlag() {
        return comboDelFlag;
    }

    public void setComboDelFlag(ArrayList<SelectItem> comboDelFlag) {
        this.comboDelFlag = comboDelFlag;
    }

    public ArrayList<SelectItem> getComboMonthIndicator() {
        return comboMonthIndicator;
    }

    public void setComboMonthIndicator(ArrayList<SelectItem> comboMonthIndicator) {
        this.comboMonthIndicator = comboMonthIndicator;
    }

    public LoanRepaymentCycleDefination getCurrentItemLoanRepayment() {
        return currentItemLoanRepayment;
    }

    public void setCurrentItemLoanRepayment(LoanRepaymentCycleDefination currentItemLoanRepayment) {
        this.currentItemLoanRepayment = currentItemLoanRepayment;
    }

    public int getCurrentRowLoanRepayment() {
        return currentRowLoanRepayment;
    }

    public void setCurrentRowLoanRepayment(int currentRowLoanRepayment) {
        this.currentRowLoanRepayment = currentRowLoanRepayment;
    }

    public String getDelFlagAw() {
        return delFlagAw;
    }

    public void setDelFlagAw(String delFlagAw) {
        this.delFlagAw = delFlagAw;
    }

    public List<LoanRepaymentCycleDefination> getLoanRepay() {
        return loanRepay;
    }

    public void setLoanRepay(List<LoanRepaymentCycleDefination> loanRepay) {
        this.loanRepay = loanRepay;
    }

    public String getMonthIndicator() {
        return monthIndicator;
    }

    public void setMonthIndicator(String monthIndicator) {
        this.monthIndicator = monthIndicator;
    }

    public String getRepaymentStartDate() {
        return repaymentStartDate;
    }

    public void setRepaymentStartDate(String repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
    }

    /** Creates a new instance of LRCD */
    public LRCD() {
     
        try {
            loanRepay = new ArrayList<LoanRepaymentCycleDefination>();
            loanRepayTmp = new ArrayList<LoanRepaymentCycleDefination>();

            comboMonthIndicator = new ArrayList<SelectItem>();
            comboMonthIndicator.add(new SelectItem("0", ""));
            comboMonthIndicator.add(new SelectItem("C", "CURRENT"));
            comboMonthIndicator.add(new SelectItem("N", "NEXT"));

            comboDelFlag = new ArrayList<SelectItem>();
            comboDelFlag.add(new SelectItem("0", ""));
            comboDelFlag.add(new SelectItem("Y", "YES"));
            comboDelFlag.add(new SelectItem("N", "NO"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for lrcd.jsp file
    public void selectLrcdDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeLoanRepaymentCycleDefinitionTO> listTOs = schemeMasterManagementDelegate.getLrcddetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (CbsSchemeLoanRepaymentCycleDefinitionTO singleTO : listTOs) {
                    CbsSchemeLoanRepaymentCycleDefinitionPKTO pKTo = singleTO.getCbsSchemeLoanRepaymentCycleDefinitionPKTO();
                    LoanRepaymentCycleDefination tblObj = new LoanRepaymentCycleDefination();
                    if (pKTo.getSchemeCode() == null || pKTo.getSchemeCode().equalsIgnoreCase("")) {
                        tblObj.setSchemeCode("");
                    } else {
                        tblObj.setSchemeCode(pKTo.getSchemeCode());
                    }
                    if (pKTo.getAccountOpenFromDate() == null || pKTo.getAccountOpenFromDate().equalsIgnoreCase("")) {
                        tblObj.setAcOpenFromDate("");
                    } else {
                        tblObj.setAcOpenFromDate(pKTo.getAccountOpenFromDate());
                    }
                    if (singleTO.getAccountOpenToDate() == null || singleTO.getAccountOpenToDate().equalsIgnoreCase("")) {
                        tblObj.setAcOpenToDate("");
                    } else {
                        tblObj.setAcOpenToDate(singleTO.getAccountOpenToDate());
                    }
                    if (singleTO.getRepaymentStartDate() == null || singleTO.getRepaymentStartDate().equalsIgnoreCase("")) {
                        tblObj.setRepaymentStartDate("");
                    } else {
                        tblObj.setRepaymentStartDate(singleTO.getRepaymentStartDate());
                    }
                    if (singleTO.getCurrentOrNextMonthIndicator() == null || singleTO.getCurrentOrNextMonthIndicator().equalsIgnoreCase("")) {
                        tblObj.setMonthIndicator("");
                    } else {
                        tblObj.setMonthIndicator(singleTO.getCurrentOrNextMonthIndicator());
                    }
                    if (singleTO.getDelFlag() == null || singleTO.getDelFlag().equalsIgnoreCase("")) {
                        tblObj.setDelFlag("");
                    } else {
                        tblObj.setDelFlag(singleTO.getDelFlag());
                    }

                    tblObj.setCounterSaveUpdate("G");
                    loanRepay.add(tblObj);
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

    public void setValuesInLoanRepayment() {
        validateLoanRepayment();
        List<LoanRepaymentCycleDefination> loanRepayTemp = loanRepay;
        if (!repaymentStartDate.equalsIgnoreCase("") && !acOpenFromDate.equalsIgnoreCase("") && !acOpenToDate.equalsIgnoreCase("")) {
            if ((Integer.parseInt(acOpenFromDate) >= 1 && Integer.parseInt(acOpenFromDate) <= 31) && (Integer.parseInt(acOpenToDate) >= 1 && Integer.parseInt(acOpenToDate) <= 31) && (Integer.parseInt(repaymentStartDate) >= 1 && Integer.parseInt(repaymentStartDate) <= 31)) {

                if (Integer.parseInt(acOpenFromDate) < Integer.parseInt(acOpenToDate)) {
                    int t = 0;
                    for (int k = 0; k < loanRepayTemp.size(); k++) {
                        String acOpenFromTmp = loanRepayTemp.get(k).getAcOpenFromDate();
                        String acOpenToTmp = loanRepayTemp.get(k).getAcOpenToDate();
                        if (Integer.parseInt(acOpenFromTmp) <= Integer.parseInt(acOpenFromDate) && Integer.parseInt(acOpenToTmp) >= Integer.parseInt(acOpenFromDate)) {
                            schemeMaster.setMessage("Please Enter Valid A/c Open From Date");
                            t = 1;
                            k = loanRepayTemp.size();
                        } else {
                            t = 0;
                        }
                    }
                    if (t == 0) {
                        if (validateLoanRepayment().equalsIgnoreCase("true")) {
                            LoanRepaymentCycleDefination loan = new LoanRepaymentCycleDefination();
                            loan.setAcOpenFromDate(getAcOpenFromDate());
                            loan.setAcOpenToDate(getAcOpenToDate());
                            loan.setDelFlag(getDelFlagAw());
                            loan.setMonthIndicator(getMonthIndicator());
                            loan.setRepaymentStartDate(getRepaymentStartDate());
                            if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
                                loan.setCounterSaveUpdate("S");
                                refreshLrcdForm();
                            }
                            loanRepay.add(loan);
                            schemeMaster.setMessage("");
                            if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
                                if (selectCountLoanRepay != 1) {
                                    for (int i = 0; i < loanRepayTemp.size(); i++) {
                                        String acOpenFromDatetmp = loanRepayTemp.get(i).getAcOpenFromDate();
                                        if (!acOpenFromDatetmp.equalsIgnoreCase(acOpenFromDate)) {
                                            loan.setCounterSaveUpdate("S");
                                            loanRepayTmp.add(loan);
                                            refreshLrcdForm();
                                            return;
                                        }
                                    }
                                }

                                if (currentItemLoanRepayment.getAcOpenFromDate() == null || currentItemLoanRepayment.getAcOpenFromDate().equalsIgnoreCase("")) {
                                    loan.setCounterSaveUpdate("S");
                                    loanRepayTmp.add(loan);
                                    refreshLrcdForm();
                                    return;
                                } else if (currentItemLoanRepayment.getAcOpenFromDate().equalsIgnoreCase(acOpenFromDate)) {
                                    if (!currentItemLoanRepayment.getAcOpenFromDate().equalsIgnoreCase(acOpenFromDate) || !currentItemLoanRepayment.getAcOpenToDate().equalsIgnoreCase(acOpenToDate)
                                            || !currentItemLoanRepayment.getRepaymentStartDate().equalsIgnoreCase(repaymentStartDate) || !currentItemLoanRepayment.getDelFlag().equalsIgnoreCase(delFlagAw) || !currentItemLoanRepayment.getMonthIndicator().equalsIgnoreCase(monthIndicator)) {
                                        loan.setCounterSaveUpdate("U");
                                        loanRepayTmp.add(loan);
                                        refreshLrcdForm();

                                    }
                                }
                                selectCountLoanRepay = 0;
                            }
                        }
                    }
                } else {
                    schemeMaster.setMessage("A/c Open From Date is less than A/c open To Date");
                }
            } else {
                schemeMaster.setMessage("Please Enter Currect Month Values");
            }
        } else {
            schemeMaster.setMessage("Please Enter FromDate/ ToDate/ StartDate Values");
        }
    }

    public void selectLoanRepayment() {
        schemeMaster.setMessage("");
        selectCountLoanRepay = 1;
        if ((acOpenFromDate != null && currentItemLoanRepayment.getAcOpenFromDate() != null)) {
            if (!acOpenFromDate.equalsIgnoreCase("")) {
                if (!acOpenFromDate.equalsIgnoreCase(currentItemLoanRepayment.getAcOpenFromDate())) {
                    count2LoanRepayment = count1LoanRepayment;
                    count1LoanRepayment = count1LoanRepayment + 1;
                    if (count2LoanRepayment != count1LoanRepayment) {
                        setValuesInLoanRepayment();
                    }
                } else {
                    count1LoanRepayment = 0;
                }
            }
        }
        setAcOpenFromDate(currentItemLoanRepayment.getAcOpenFromDate());
        setAcOpenToDate(currentItemLoanRepayment.getAcOpenToDate());
        setRepaymentStartDate(currentItemLoanRepayment.getRepaymentStartDate());
        setMonthIndicator(currentItemLoanRepayment.getMonthIndicator());
        setDelFlagAw(currentItemLoanRepayment.getDelFlag());
        loanRepay.remove(currentRowLoanRepayment);
    }

    public String validateLoanRepayment() {
        String msg = "";
        if (schemeMaster.getSchemeCode() == null || schemeMaster.getSchemeCode().equalsIgnoreCase("")) {
            msg = msg + "Please  Enter Scheme Code";
        }

        if (acOpenFromDate == null || acOpenFromDate.equalsIgnoreCase("")) {
            msg = msg + "Please Enter A/C Open From Date";
        }
        if (acOpenToDate == null || acOpenToDate.equalsIgnoreCase("")) {
            msg = msg + "Please Enter A/C Open To Date";
        }

        if (repaymentStartDate == null || repaymentStartDate.equalsIgnoreCase("")) {
            msg = msg + "Please Enter Repayment Start Date";
        }
        if (monthIndicator == null || monthIndicator.equalsIgnoreCase("0")) {
            msg = msg + "Please Enter Month Indicator";
        }
        if (delFlagAw == null || delFlagAw.equalsIgnoreCase("0")) {
            msg = msg + "Please Enter Delete Flag";
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }

    public void refreshLrcdForm() {
        this.setAcOpenToDate("");
        this.setAcOpenFromDate("");
        this.setRepaymentStartDate("");
        this.setDelFlagAw("0");
        this.setMonthIndicator("0");
    }
}
