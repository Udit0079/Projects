/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.constant.Months;
import com.cbs.dto.TdIntDetail;
import com.cbs.facade.intcal.TDInterestCalulationFacadeRemote;
import com.cbs.facade.intcal.TdInterestCalFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/**
 *
 * @author root
 */
public class TermDepositMonthlyInterestProvision extends BaseBean {

    private String errorMessage;
    private String message;
    private String acType;
    private List<SelectItem> acTypeList;
    private String interestOption;
    private List<SelectItem> interestOptionList;
    private String lblMonthEnd;
    private String monthEnd;
    private List<SelectItem> monthEndList;
    private String finYear;
    private List<SelectItem> finYearList;
    private int maxDays;
//    private String list;
//    private boolean calcFlag1;
    private final String tdInterestCalculationJndiName = "TDInterestCalulationFacade";
    private TDInterestCalulationFacadeRemote tdInterestCalculationMainRemote = null;
    private TdInterestCalFacadeRemote intCal = null;
    private CommonReportMethodsRemote common;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public TermDepositMonthlyInterestProvision() {
        try {
            tdInterestCalculationMainRemote = (TDInterestCalulationFacadeRemote) ServiceLocator.getInstance().lookup(tdInterestCalculationJndiName);
            intCal = (TdInterestCalFacadeRemote) ServiceLocator.getInstance().lookup("TdInterestCalFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setErrorMessage("");
            setMessage("");
            setLblMonthEnd("Month :");

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int mDay = cal.get(Calendar.MONTH);

//            monthEndList = new ArrayList<SelectItem>();
//            monthEndList.add(new SelectItem("--Select--"));
//
//            monthEndList.add(new SelectItem("1", "JUNE"));
//            monthEndList.add(new SelectItem("2", "SEPTEMBER"));
//            monthEndList.add(new SelectItem("3", "DECEMBER"));
//            monthEndList.add(new SelectItem("0", "MARCH"));

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
            setMonthEndQuarEnd();
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void setMonthEndQuarEnd() {
        this.setErrorMessage("");
        this.setMessage("");
        try {

            monthEndList = new ArrayList<SelectItem>();
            monthEndList.add(new SelectItem("--Select--"));

            monthEndList.add(new SelectItem("1", Months.JANUARY.getName()));
            monthEndList.add(new SelectItem("2", Months.FEBRUARY.getName()));
            monthEndList.add(new SelectItem("3", Months.MARCH.getName()));
            monthEndList.add(new SelectItem("4", Months.APRIL.getName()));
            monthEndList.add(new SelectItem("5", Months.MAY.getName()));
            monthEndList.add(new SelectItem("6", Months.JUNE.getName()));
            monthEndList.add(new SelectItem("7", Months.JULY.getName()));
            monthEndList.add(new SelectItem("8", Months.AUGUST.getName()));
            monthEndList.add(new SelectItem("9", Months.SEPTEMBER.getName()));
            monthEndList.add(new SelectItem("10", Months.OCTOBER.getName()));
            monthEndList.add(new SelectItem("11", Months.NOVEMBER.getName()));
            monthEndList.add(new SelectItem("12", Months.DECEMBER.getName()));

        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void setdescription() {
        this.setErrorMessage("");
        this.setMessage("");
        try {

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
                String intopt = ele.get(3).toString();
                for (int i = 0; i < intopt.length(); i++) {
                    if (intopt.charAt(i) == 'C') {
                        interestOptionList.add(new SelectItem("CUMULATIVE"));
                    }
                    if (intopt.charAt(i) == 'Q') {
                        interestOptionList.add(new SelectItem("QUARTERLY"));
                    }
//                    if (intopt.charAt(i) == 'M') {
//                        interestOptionList.add(new SelectItem("MONTHLY"));
//                    }
                    if (intopt.charAt(i) == 'S') {
                        interestOptionList.add(new SelectItem("SIMPLE"));
                    }
                    if (intopt.charAt(i) == 'Y') {
                        interestOptionList.add(new SelectItem("YEARLY"));
                    }
                    if (intopt.charAt(i) == 'H') {
                        interestOptionList.add(new SelectItem("HALFLY"));
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

    public void printReport() {

        String tmpDate;
        String tmpFDate;
        String tmpMonth;
        int month;
        String monthtmp;
        this.setErrorMessage("");
        this.setMessage("");

        try {

            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Account Type.");
                return;
            }
            if (this.interestOption.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Interest Option.");
                return;
            }
            if (this.monthEnd.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Month End / Quarter End.");
                return;
            }
            if (this.finYear.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Financial year");
                return;
            }

//            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
//                tmpMonth = this.monthEnd;
//                month = Integer.parseInt(tmpMonth) + 1;
//                monthtmp = Integer.toString(month);
//            } else {
//                tmpMonth = this.monthEnd;
//                month = ((Integer.parseInt(tmpMonth) + 1) * 3);
//                monthtmp = Integer.toString(month);
//            }
//            int length = monthtmp.length();
//            int addedZero = 2 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                monthtmp = "0" + monthtmp;
//            }
//            int year = Integer.parseInt(this.finYear);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(year, month - 1, 1);
//            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//            tmpDate = maxDays + "/" + monthtmp + "/" + year;
//            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
//                tmpFDate = "01" + "/" + monthtmp + "/" + year;
//            } else {
//                tmpFDate = tmpDate;
//            }



//            String frDt = tmpFDate.substring(6) + tmpFDate.substring(3, 5) + tmpFDate.substring(0, 2);
//            String toDt = tmpDate.substring(6) + tmpDate.substring(3, 5) + tmpDate.substring(0, 2);
//            String currDt = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);

            String toDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.monthEnd), Integer.parseInt(this.finYear)));
            String frDt = toDt.substring(0, 4) + toDt.substring(4, 6) + "01";




            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("PntBy", getUserName());
            //fillParams.put("RpName", repName);
            // fillParams.put("RpDate", monthName + "-" + finYear);
            fillParams.put("RpName", "Td Monthly Provision Interest Report");
            fillParams.put("pbankName", brNameAndAddList.get(0).toString());
            fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());

            List<TdIntDetail> resultList = tdInterestCalculationMainRemote.tdProvInterestMonthlyData(frDt, toDt, this.interestOption.substring(0, 1), acType, getOrgnBrCode());

            if (resultList.isEmpty()) {
                this.setMessage("Data Does not exist!");
                return;
            }

            new ReportBean().jasperReport("TdMonthlyInterestProvisionReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Td Monthly Provision Interest Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            resetForm();

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {


        String tmpDate;
        String tmpFDate;
        String tmpMonth;
        int month;
        String monthtmp;
        this.setErrorMessage("");
        this.setMessage("");

        try {

            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Account Type.");
                return;
            }
            if (this.interestOption.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Interest Option.");
                return;
            }
            if (this.monthEnd.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Month");
                return;
            }
            if (this.finYear.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Financial year");
                return;
            }

//            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
//                tmpMonth = this.monthEnd;
//                month = Integer.parseInt(tmpMonth) + 1;
//                monthtmp = Integer.toString(month);
//            } else {
//                tmpMonth = this.monthEnd;
//                month = ((Integer.parseInt(tmpMonth) + 1) * 3);
//                monthtmp = Integer.toString(month);
//            }
//            int length = monthtmp.length();
//            int addedZero = 2 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                monthtmp = "0" + monthtmp;
//            }
//            int year = Integer.parseInt(this.finYear);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(year, month - 1, 1);
//            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//            tmpDate = maxDays + "/" + monthtmp + "/" + year;
//            if (this.interestOption.equalsIgnoreCase("MONTHLY")) {
//                tmpFDate = "01" + "/" + monthtmp + "/" + year;
//            } else {
//                tmpFDate = tmpDate;
//            }

            String toDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.monthEnd), Integer.parseInt(this.finYear)));
            String frDt = toDt.substring(0, 4) + toDt.substring(4, 6) + "01";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("PntBy", getUserName());
            //fillParams.put("RpName", repName);
            // fillParams.put("RpDate", monthName + "-" + finYear);
            fillParams.put("RpName", "Td Monthly Provision Interest Report");
            fillParams.put("pbankName", brNameAndAddList.get(0).toString());
            fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());

            List<TdIntDetail> resultList = tdInterestCalculationMainRemote.tdProvInterestMonthlyData(frDt, toDt, this.interestOption.substring(0, 1), acType, getOrgnBrCode());

            if (resultList.isEmpty()) {
                this.setMessage("Data Does not exist!");
                return;
            }

            new ReportBean().openPdf("Td Monthly Provision Interest Report", "TdMonthlyInterestProvisionReport", new JRBeanCollectionDataSource(resultList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Td Monthly Provision Interest Report");
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcType("--Select--");
            this.setInterestOption("--Select--");
            this.setMonthEnd("--Select--");
            this.setFinYear("--Select--");
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

    public String getLblMonthEnd() {
        return lblMonthEnd;
    }

    public void setLblMonthEnd(String lblMonthEnd) {
        this.lblMonthEnd = lblMonthEnd;
    }
}
