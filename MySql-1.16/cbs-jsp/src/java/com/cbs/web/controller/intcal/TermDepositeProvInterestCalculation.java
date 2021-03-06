/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.constant.Months;
import com.cbs.dto.TdIntDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.TDInterestCalulationFacadeRemote;
import com.cbs.facade.intcal.TdInterestCalFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.TdInterestCalGrid;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class TermDepositeProvInterestCalculation extends BaseBean {

    private String errorMessage;
    private String message;
    private String acType;
    private List<SelectItem> acTypeList;
    private String glAcNo;
    private String interestOption;
    private List<SelectItem> interestOptionList;
    private String lblMonthEnd;
    private String monthEnd;
    private List<SelectItem> monthEndList;
    private String finYear;
    private List<SelectItem> finYearList;
    private String debtAmt;
    private String crAmt;
    private String acNoToBeCr;
    private int maxDays;
    private String list;
    private boolean calcFlag1;
    private final String tdInterestCalculationJndiName = "TDInterestCalulationFacade";
    private TDInterestCalulationFacadeRemote tdInterestCalculationMainRemote = null;
    private TdInterestCalFacadeRemote intCal = null;
    private String viewID = "/pages/master/sm/test.jsp";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGlAcNo() {
        return glAcNo;
    }

    public void setGlAcNo(String glAcNo) {
        this.glAcNo = glAcNo;
    }

    public String getInterestOption() {
        return interestOption;
    }

    public void setInterestOption(String interestOption) {
        this.interestOption = interestOption;
    }

    public List<SelectItem> getInterestOptionList() {
        return interestOptionList;
    }

    public void setInterestOptionList(List<SelectItem> interestOptionList) {
        this.interestOptionList = interestOptionList;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(String monthEnd) {
        this.monthEnd = monthEnd;
    }

    public List<SelectItem> getMonthEndList() {
        return monthEndList;
    }

    public void setMonthEndList(List<SelectItem> monthEndList) {
        this.monthEndList = monthEndList;
    }

    public String getLblMonthEnd() {
        return lblMonthEnd;
    }

    public void setLblMonthEnd(String lblMonthEnd) {
        this.lblMonthEnd = lblMonthEnd;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public List<SelectItem> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<SelectItem> finYearList) {
        this.finYearList = finYearList;
    }

    public String getAcNoToBeCr() {
        return acNoToBeCr;
    }

    public void setAcNoToBeCr(String acNoToBeCr) {
        this.acNoToBeCr = acNoToBeCr;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getDebtAmt() {
        return debtAmt;
    }

    public void setDebtAmt(String debtAmt) {
        this.debtAmt = debtAmt;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public boolean isCalcFlag1() {
        return calcFlag1;
    }

    public void setCalcFlag1(boolean calcFlag1) {
        this.calcFlag1 = calcFlag1;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Creates a new instance of TermDepositeInterestCalculation
     */
    public TermDepositeProvInterestCalculation() {
        try {
            tdInterestCalculationMainRemote = (TDInterestCalulationFacadeRemote) ServiceLocator.getInstance().lookup(tdInterestCalculationJndiName);
            intCal = (TdInterestCalFacadeRemote) ServiceLocator.getInstance().lookup("TdInterestCalFacade");
            setErrorMessage("");
            setMessage("");
            setLblMonthEnd("Quarter End :");
            setCalcFlag1(false);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int mDay = cal.get(Calendar.MONTH);

            monthEndList = new ArrayList<SelectItem>();
            monthEndList.add(new SelectItem("--Select--"));

            monthEndList.add(new SelectItem("1", "JUNE"));
            monthEndList.add(new SelectItem("2", "SEPTEMBER"));
            monthEndList.add(new SelectItem("3", "DECEMBER"));
            monthEndList.add(new SelectItem("0", "MARCH"));

            List tempList = new ArrayList();
            finYearList = new ArrayList<SelectItem>();
            tempList = tdInterestCalculationMainRemote.finYearTD(getOrgnBrCode());
            if (!tempList.isEmpty()) {
                finYearList.add(new SelectItem("--Select--"));
                Vector ele = (Vector) tempList.get(0);
                finYearList.add(new SelectItem(ele.get(0).toString()));
                finYearList.add(new SelectItem(ele.get(1).toString()));
            } else {
                finYearList.add(new SelectItem("--Select--"));
            }

            acctTypeCombo();
            interestOptionList = new ArrayList<SelectItem>();
            interestOptionList.add(new SelectItem("--Select--"));
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void acctTypeCombo() {
        try {
            List tempList = new ArrayList();
            tempList = tdInterestCalculationMainRemote.acctTypeCombo();
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--Select--"));
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    Vector ele = (Vector) tempList.get(i);
                    acTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            } else {
                acTypeList = new ArrayList<SelectItem>();
                acTypeList.add(new SelectItem("--Select--"));
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void setdescription() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setCalcFlag1(false);
            this.setGlAcNo("");
            this.setDebtAmt("");
            this.setAcNoToBeCr("");
            this.setCrAmt("");
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type !!!");
                interestOptionList = new ArrayList<SelectItem>();
                interestOptionList.add(new SelectItem("--Select--"));
                return;
            }
            List tempList = new ArrayList();
            tempList = tdInterestCalculationMainRemote.setGLAcNoAndInterestOption(this.acType);

            interestOptionList = new ArrayList<SelectItem>();
            interestOptionList.add(new SelectItem("--Select--"));
            if (!tempList.isEmpty()) {
                Vector ele = (Vector) tempList.get(0);
                this.setGlAcNo(getOrgnBrCode() + ele.get(2).toString() + "01");
                this.setAcNoToBeCr(getOrgnBrCode() + ele.get(4).toString() + "01");
                String intopt = ele.get(3).toString();
                for (int i = 0; i < intopt.length(); i++) {
                    if (intopt.charAt(i) == 'C') {
                        interestOptionList.add(new SelectItem("CUMULATIVE"));
                    }
                    if (intopt.charAt(i) == 'Q') {
                        interestOptionList.add(new SelectItem("QUARTERLY"));
                    }
                    if (intopt.charAt(i) == 'M') {
                        interestOptionList.add(new SelectItem("MONTHLY"));
                    }
                    if (intopt.charAt(i) == 'S') {
                        interestOptionList.add(new SelectItem("SIMPLE"));
                    }
                }

            } else {
                interestOptionList = new ArrayList<SelectItem>();
                interestOptionList.add(new SelectItem("--Select--"));
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void setMonthEndQuarEnd() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setDebtAmt("0.00");
            monthEndList = new ArrayList<SelectItem>();
            monthEndList.add(new SelectItem("--Select--"));
            this.setCalcFlag1(false);
            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
                this.setLblMonthEnd("Month End :");
                monthEndList.add(new SelectItem("0", Months.JANUARY.getName()));
                monthEndList.add(new SelectItem("1", Months.FEBRUARY.getName()));
                monthEndList.add(new SelectItem("2", Months.MARCH.getName()));
                monthEndList.add(new SelectItem("3", Months.APRIL.getName()));
                monthEndList.add(new SelectItem("4", Months.MAY.getName()));
                monthEndList.add(new SelectItem("5", Months.JUNE.getName()));
                monthEndList.add(new SelectItem("6", Months.JULY.getName()));
                monthEndList.add(new SelectItem("7", Months.AUGUST.getName()));
                monthEndList.add(new SelectItem("8", Months.SEPTEMBER.getName()));
                monthEndList.add(new SelectItem("9", Months.OCTOBER.getName()));
                monthEndList.add(new SelectItem("10", Months.NOVEMBER.getName()));
                monthEndList.add(new SelectItem("11", Months.DECEMBER.getName()));
            } else {
                this.setLblMonthEnd("Quarter End :");
                monthEndList.add(new SelectItem("1", Months.JUNE.getName()));
                monthEndList.add(new SelectItem("2", Months.SEPTEMBER.getName()));
                monthEndList.add(new SelectItem("3", Months.DECEMBER.getName()));
                monthEndList.add(new SelectItem("0", Months.MARCH.getName()));
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void monthEndlostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCalcFlag1(false);
            this.setDebtAmt("0.00");
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    List<TdIntDetail> tdIntCalList;

    public void callForProvCalculateAndPost() {
        String tmpDate;
        String tmpFDate;
        String tmpMonth;
        int month;
        String monthtmp;
        String mode = "CAL";
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type.");
                return;
            }
            if (this.interestOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Interest Option.");
                return;
            }
            if (this.monthEnd.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Month End / Quarter End.");
                return;
            }
            if (this.finYear.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Financial year");
                return;
            }
            if (this.calcFlag1 == false) {
                mode = "CAL";
            } else {
                mode = "POST";
            }
            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
                tmpMonth = this.monthEnd;
                month = Integer.parseInt(tmpMonth) + 1;
                monthtmp = Integer.toString(month);
            } else {
                tmpMonth = this.monthEnd;
                month = ((Integer.parseInt(tmpMonth) + 1) * 3);
                monthtmp = Integer.toString(month);
            }
            int length = monthtmp.length();
            int addedZero = 2 - length;
            for (int i = 1; i <= addedZero; i++) {
                monthtmp = "0" + monthtmp;
            }
            int year = Integer.parseInt(this.finYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, 1);
            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            tmpDate = maxDays + "/" + monthtmp + "/" + year;
            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
                tmpFDate = "01" + "/" + monthtmp + "/" + year;
            } else {
                tmpFDate = tmpDate;
            }

            String frDt = tmpFDate.substring(6) + tmpFDate.substring(3, 5) + tmpFDate.substring(0, 2);
            String toDt = tmpDate.substring(6) + tmpDate.substring(3, 5) + tmpDate.substring(0, 2);
            String currDt = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);

            if (mode.equalsIgnoreCase("POST")) {
                if (this.debtAmt == null || this.debtAmt.equalsIgnoreCase("") || this.debtAmt.length() == 0 || Float.parseFloat(this.debtAmt) < 0) {
                    this.setErrorMessage("Sorry this debit amount cannot be posted");
                    return;
                }
                //String msg = tdInterestCalculationMainRemote.postProvInterestTd(glAcNo, acNoToBeCr, Double.parseDouble(crAmt),getUserName(), getOrgnBrCode());
                this.setCalcFlag1(false);
//                if (msg.equalsIgnoreCase("Yes")) {
//                    this.setMessage("Interest posted successfully");
//                } else {
//                    this.setErrorMessage(msg);
//                }
            } else {
                tdIntCalList = new ArrayList<TdIntDetail>();
                tdIntCalList = tdInterestCalculationMainRemote.tdProvInterestCalculation(frDt, toDt, this.acType, this.interestOption.substring(0, 1), getUserName(),
                        this.glAcNo, currDt, getOrgnBrCode());

                if (tdIntCalList.isEmpty()) {
                    this.setCalcFlag1(false);
                    this.setErrorMessage("Data does not exist");
                } else if (tdIntCalList.size() > 0 && tdIntCalList.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                    double intAmt = 0d;
                    int i = 1;
                    for (TdIntDetail tdIntDetail : tdIntCalList) {
                        TdInterestCalGrid detail = new TdInterestCalGrid();
                        intAmt = intAmt + tdIntDetail.getInterest();
                    }
                    this.setDebtAmt(String.valueOf(intAmt));
                    this.setCrAmt(String.valueOf(intAmt));
                    this.setMessage("Interest calculated successfully.");
                    this.setCalcFlag1(true);
                    String repName = "Provisional FD Int. Cal. Report For " + interestOption;
                    String report = "Provisional FD Interest Calculation Report";
                    Map fillParams = new HashMap();
                    String rep;

                    String monthName = "";
                    if (interestOption.equalsIgnoreCase("MONTHLY")) {
                        rep = "Month End     : ";
                        monthName = getMonthName(Integer.parseInt(monthEnd));
                    } else {
                        rep = "Quarter End     : ";
                        if (monthEnd.equalsIgnoreCase("0")) {
                            monthName = "March";
                        } else if (monthEnd.equalsIgnoreCase("1")) {
                            monthName = "June";
                        } else if (monthEnd.equalsIgnoreCase("2")) {
                            monthName = "September";
                        } else if (monthEnd.equalsIgnoreCase("3")) {
                            monthName = "December";
                        }
                    }
                    fillParams.put("PntBy", getUserName());
                    fillParams.put("RpName", repName);
                    fillParams.put("RpDate", monthName + "-" + finYear);
                    fillParams.put("ReportName", rep);
                    new ReportBean().jasperReport("TD_INT_CAL_PROVISION", "text/html", new JRBeanCollectionDataSource(tdIntCalList),
                            fillParams, report);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                } else {
                    this.setCalcFlag1(false);
                    this.setErrorMessage(tdIntCalList.get(0).getMsg());
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    private String getMonthName(int month) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        return monthName[month];
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcType("--Select--");
            this.setInterestOption("--Select--");
            this.setMonthEnd("--Select--");
            this.setFinYear("--Select--");

            this.setGlAcNo("");
            this.setDebtAmt("");
            this.setAcNoToBeCr("");
            this.setCrAmt("");
            this.setCalcFlag1(false);
        } catch (Exception ex) {
            this.setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
        return "case1";
    }
}
