/*
 Document   : LoanInterestParameter
 Created on : 24 Mar, 2011, 11:46:48 AM
 Author     : Zeeshan Waris
 */
package com.cbs.web.controller.master;

import com.cbs.dto.YearEndDatePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;

public final class LoanInterestParameter extends BaseBean {

    private String message;
    private String financialYear;
    private String acctType;
    private String interestType;
    private String intOption;
    private String focusId;
    private boolean interestTypeDisabled;
    private String branch;
    private String frDt;
    private List<SelectItem> branchList;
    private List<SelectItem> penalInterestOption;
    private List<SelectItem> intCodeList;
    private List<SelectItem> finYearOption;
    private List<SelectItem> interestTypeOption;
    private String nature;
    private List<SelectItem> natureList;
    private String parametreCharge;
    private List<SelectItem> parametreChargeList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    BankAndLoanMasterFacadeRemote testEJB;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private RbiReportFacadeRemote rbiFacade;
    private FtsPostingMgmtFacadeRemote ftsFacade;
    private CommonReportMethodsRemote common;

    public List<SelectItem> getIntCodeList() {
        return intCodeList;
    }

    public void setIntCodeList(List<SelectItem> intCodeList) {
        this.intCodeList = intCodeList;
    }

    public boolean isInterestTypeDisabled() {
        return interestTypeDisabled;
    }

    public void setInterestTypeDisabled(boolean interestTypeDisabled) {
        this.interestTypeDisabled = interestTypeDisabled;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getFinYearOption() {
        return finYearOption;
    }

    public void setFinYearOption(List<SelectItem> finYearOption) {
        this.finYearOption = finYearOption;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getIntOption() {
        return intOption;
    }

    public void setIntOption(String intOption) {
        this.intOption = intOption;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public List<SelectItem> getInterestTypeOption() {
        return interestTypeOption;
    }

    public void setInterestTypeOption(List<SelectItem> interestTypeOption) {
        this.interestTypeOption = interestTypeOption;
    }

    public List<SelectItem> getPenalInterestOption() {
        return penalInterestOption;
    }

    public void setPenalInterestOption(List<SelectItem> penalInterestOption) {
        this.penalInterestOption = penalInterestOption;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public List<SelectItem> getNatureList() {
        return natureList;
    }

    public void setNatureList(List<SelectItem> natureList) {
        this.natureList = natureList;
    }

    public String getParametreCharge() {
        return parametreCharge;
    }

    public void setParametreCharge(String parametreCharge) {
        this.parametreCharge = parametreCharge;
    }

    public List<SelectItem> getParametreChargeList() {
        return parametreChargeList;
    }

    public void setParametreChargeList(List<SelectItem> parametreChargeList) {
        this.parametreChargeList = parametreChargeList;
    }

    public LoanInterestParameter() {
        try {
            testEJB = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            rbiFacade = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            parametreChargeList = new ArrayList<SelectItem>();
            parametreChargeList.add(new SelectItem("0", "--SELECT--"));
            parametreChargeList.add(new SelectItem("Parameter", "Parameter"));
            parametreChargeList.add(new SelectItem("Charge", "Charge"));

            //penalInterestOption = new ArrayList<SelectItem>();
//            penalInterestOption.add(new SelectItem("P", "Penal"));
//            penalInterestOption.add(new SelectItem("I", "Interest"));
//            penalInterestOption.add(new SelectItem("D", "Deposit"));
//            penalInterestOption.add(new SelectItem("NA", "Not Applicable"));

            interestTypeOption = new ArrayList<SelectItem>();
            interestTypeOption.add(new SelectItem("0", "--SELECT--"));
            interestTypeOption.add(new SelectItem("M", "Monthly"));
            interestTypeOption.add(new SelectItem("Q", "Quarterly"));
            interestTypeOption.add(new SelectItem("H", "Half Yearly"));
            interestTypeOption.add(new SelectItem("Y", "Yearly"));
            // getAcctTypeList();
            getFinYearList();
            this.setMessage("");
            branchList = new ArrayList<SelectItem>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void allNatureList() {
        this.setMessage("");
        try {
            List list = new ArrayList();
            if (parametreCharge == null || parametreCharge.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Parameter/Charge");
                return;
            }
            if (parametreCharge.equalsIgnoreCase("Parameter")) {
                list = common.getTdsDoctype("381");
            } else {
                list = common.getTdsDoctype("382");
            }
            if (!list.isEmpty()) {
                penalInterestOption = new ArrayList<SelectItem>();
                penalInterestOption.add(new SelectItem("0", "--Select--"));

                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    penalInterestOption.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                }
            }


            natureList = new ArrayList<SelectItem>();
            if (parametreCharge.equalsIgnoreCase("Parameter")) {
                natureList.add(new SelectItem("0", "--Select--"));
            } else {
                natureList.add(new SelectItem("A", "ALL"));
            }
            List acNatureList = common.getAllAcNature();
            if (!acNatureList.isEmpty()) {
                for (int i = 0; i < acNatureList.size(); i++) {
                    Vector vtr = (Vector) acNatureList.get(i);
                    natureList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void acctTypeList() {
        setMessage("");
        try {
            List resultList = new ArrayList();
            intCodeList = new ArrayList<SelectItem>();
            //resultList = testEJB.getTLDLCASBAcTypeDropDownLoanInterestParameter();
            if (parametreCharge.equalsIgnoreCase("Parameter")) {
                intCodeList.add(new SelectItem("0", "--Select--"));
            } else {
                intCodeList.add(new SelectItem("A", "ALL"));
            }
            resultList = common.getActCodeByAcNature(nature);
            if (!resultList.isEmpty()) {
//                intCodeList = new ArrayList<SelectItem>();
//                intCodeList.add(new SelectItem("--SELECT--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    intCodeList.add(new SelectItem(ele.get(0).toString()));
                }
            } else {
                intCodeList = new ArrayList<SelectItem>();
                intCodeList.add(new SelectItem("A", "ALL"));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getFinYearList() {
        try {
            List resultList = new ArrayList();
            resultList = testEJB.finYearDropDownLoanInterestParameter();
            if (!resultList.isEmpty()) {
                finYearOption = new ArrayList<SelectItem>();
                finYearOption.add(new SelectItem("--SELECT--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    finYearOption.add(new SelectItem(ele.get(0).toString()));
                }
            } else {
                finYearOption = new ArrayList<SelectItem>();
                finYearOption.add(new SelectItem("--SELECT--"));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setIntOptions() {
        try {
            if (parametreCharge.equalsIgnoreCase("Charge")) {
                this.setInterestType("0");
                this.setInterestTypeDisabled(false);
                this.setFocusId("ddInterestType");
            } else {
                String acNature = ftsFacade.getAcNatureByCode(acctType);
                if (acNature.equalsIgnoreCase("SB")) {
                    this.setInterestType("I");
                    this.setInterestTypeDisabled(true);
                    this.setFocusId("ddIntCode");
                } else {
                    this.setInterestType("0");
                    this.setInterestTypeDisabled(false);
                    this.setFocusId("ddInterestType");
                }
            }
        } catch (ApplicationException ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void setFinancialYearWiseDate() {
        try {
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo) rbiFacade.getYearEndDataAccordingFinYear(getBranch().equalsIgnoreCase("0A") ? "90" : getBranch(), financialYear);
            this.setFrDt(sdf.format(ymd.parse(yDate.getMinDate())));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveBtnAction() {
        try {
            this.setMessage("");
            if (parametreCharge == null || parametreCharge.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Parameter/Charge");
                return;
            }

            if (parametreCharge.equalsIgnoreCase("Parameter")) {
                if (nature == null || nature.equalsIgnoreCase("0")) {
                    this.setMessage("Please Select Account Nature");
                    return;
                }
                if (acctType == null || acctType.equals("0")) {
                    this.setMessage("Please Select The Financial A/C Type");
                    return;
                }
            }
            if (interestType.equals("0")) {
                this.setMessage("Please Select The Interest Type");
                return;
            }
            if (intOption.equals("0")) {
                this.setMessage("Please Select The Interest Option");
                return;
            }
            if (financialYear.equals("--SELECT--")) {
                this.setMessage("Please Select The Financial Year");
                return;
            }
            String result = testEJB.interestSaveLoanInterestParameter(financialYear, acctType, interestType, intOption, getUserName(), ymd.format(sdf.parse(getTodayDate())), branch, ymd.format(sdf.parse(frDt)), parametreCharge, nature);
            this.setMessage(result);
            refreshPage();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        this.setMessage("");
        setNature("0");
        setParametreCharge("0");
        setFinancialYear("--SELECT--");
        setAcctType("0");
        setBranch("ALL");
        setInterestType("0");
        setIntOption("0");
        setFrDt(sdf.format(new Date()));
        this.setInterestTypeDisabled(false);
    }

    public void refreshPage() {
        setNature("0");
        setParametreCharge("0");
        setFinancialYear("--SELECT--");
        setAcctType("0");
        setBranch("ALL");
        setInterestType("0");
        setIntOption("0");
        setFrDt(sdf.format(new Date()));
    }

    public String exitForm() {
        refresh();
        return "case1";
    }
}
