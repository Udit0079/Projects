/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.td;

import com.cbs.constant.Months;
import com.cbs.dto.TdsDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.TDInterestCalulationFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.other.ReportDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ProjectedTdsCalculation extends BaseBean {

    String year;

    String monthType;

    List<SelectItem> monthTypeOption;

    int monthToIntConvert;

    String result;

    String resultLastDate;

    String accToBeCredited;

    String glHead;

    List<ReportDetail> reportDetail;

    String newFirstDate;

    String error;

    String flag;

    int currentRow;

    String yearend;
    
    private List<SelectItem> finYearList;

    private String viewID = "/pages/master/sm/test.jsp";

    private boolean calcFlag;

    private boolean cFlag;

    private boolean postFlag;

    private final String jndiHomeNameTdCal = "TermDepositeCalculationManagementFacade";

    private TermDepositeCalculationManagementFacadeRemote tdCalMgmtRemote = null;

    private final String jndiHomeNameLoan = "BankAndLoanMasterFacade";

    private BankAndLoanMasterFacadeRemote loanMasterRemote = null;
    
     private final String tdInterestCalculationJndiName = "TDInterestCalulationFacade";
    
    private TDInterestCalulationFacadeRemote tdInterestCalculationMainRemote = null;

    List<TdsDetail> tdsPostResultList;
    
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonBeanRemote = null;

    String a, b;
    String brnName;
    List<SelectItem> brnList;

    public List<SelectItem> getBrnList() {
        return brnList;
    }
    public void setBrnList(List<SelectItem> brnList) {
        this.brnList = brnList;
    }
    public String getBrnName() {
        return brnName;
    }
    public void setBrnName(String brnName) {
        this.brnName = brnName;
    }
    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public ReportDetail getReport() {
        return report;
    }

    public void setReport(ReportDetail report) {
        this.report = report;
    }

    public List<ReportDetail> getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(List<ReportDetail> reportDetail) {
        this.reportDetail = reportDetail;
    }
    ReportDetail report;

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public String getAccToBeCredited() {
        return accToBeCredited;
    }

    public void setAccToBeCredited(String accToBeCredited) {
        this.accToBeCredited = accToBeCredited;
    }

    public String getResultLastDate() {
        return resultLastDate;
    }

    public void setResultLastDate(String resultLastDate) {
        this.resultLastDate = resultLastDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<SelectItem> getMonthTypeOption() {
        return monthTypeOption;
    }

    public void setMonthTypeOption(List<SelectItem> monthTypeOption) {
        this.monthTypeOption = monthTypeOption;
    }

    public String getMonthType() {
        return monthType;
    }

    public void setMonthType(String monthType) {
        this.monthType = monthType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean iscFlag() {
        return cFlag;
    }

    public void setcFlag(boolean cFlag) {
        this.cFlag = cFlag;
    }

    public boolean isPostFlag() {
        return postFlag;
    }

    public void setPostFlag(boolean postFlag) {
        this.postFlag = postFlag;
    }

    public List<SelectItem> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<SelectItem> finYearList) {
        this.finYearList = finYearList;
    }
    
    

    public ProjectedTdsCalculation() {
        try {
            tdCalMgmtRemote = (TermDepositeCalculationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdCal);
            loanMasterRemote = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoan);
            tdInterestCalculationMainRemote = (TDInterestCalulationFacadeRemote) ServiceLocator.getInstance().lookup(tdInterestCalculationJndiName);
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            getCurrentYear();
            loadMonths();
            getFirstDateResult();
            getLastDateResult();
            getTableDetails();
            yearend = "";
            this.setCalcFlag(false);
            this.setcFlag(false);
            this.setPostFlag(true);
            List brncode = new ArrayList();
            if(this.getOrgnBrCode().equalsIgnoreCase("90") || this.getOrgnBrCode().equalsIgnoreCase("0A")  ){
                brncode = commonBeanRemote.getAlphacodeExcludingHo();
            } else {
                brncode = commonBeanRemote.getBranchCodeList(this.getOrgnBrCode());
            }
            brnList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    brnList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (ApplicationException ex) {
            this.setError(ex.getMessage());
        } catch (Exception ex) {
            this.setError(ex.getMessage());
        }
    }

    public void getCurrentYear() {
        try {
            finYearList = new ArrayList<SelectItem>();
            List tempList = tdInterestCalculationMainRemote.finYearTD(getOrgnBrCode());
            
            Vector ele = (Vector) tempList.get(0);
            finYearList.add(new SelectItem(ele.get(0).toString()));
            finYearList.add(new SelectItem(ele.get(1).toString()));
            
        } catch (Exception e) {
        }
        
    }

    public void loadMonths() {
        monthTypeOption = new ArrayList<SelectItem>();
        monthTypeOption.add(new SelectItem("--Select--"));

        monthTypeOption.add(new SelectItem(Months.JUNE.getName()));

        monthTypeOption.add(new SelectItem(Months.SEPTEMBER.getName()));

        monthTypeOption.add(new SelectItem(Months.DECEMBER.getName()));

        monthTypeOption.add(new SelectItem(Months.MARCH.getName()));
    }

    public String getFirstDateResult() {
        try {
            if (this.monthType != null) {
                if (this.monthType.equalsIgnoreCase("--Select--")) {
                    this.setError("Please Select Month.");
                } else {
                    this.setError("");
                }
                if (this.monthType.equalsIgnoreCase("JUNE")) {
                    monthToIntConvert = 4;
                } else if (this.monthType.equalsIgnoreCase("SEPTEMBER")) {
                    monthToIntConvert = 7;
                } else if (this.monthType.equalsIgnoreCase("DECEMBER")) {
                    monthToIntConvert = 10;
                } else if (this.monthType.equalsIgnoreCase("MARCH")) {
                    monthToIntConvert = 1;
                }
                result = tdCalMgmtRemote.getFirstDate(monthToIntConvert, Integer.parseInt(year));
            }
        } catch (Exception ex) {
            this.setError(ex.getMessage());
        }
        return result;
    }

    public String getLastDateResult() {
        try {
            if (this.monthType != null) {
                if (this.monthType.equalsIgnoreCase("JUNE")) {
                    monthToIntConvert = 6;
                } else if (this.monthType.equalsIgnoreCase("SEPTEMBER")) {
                    monthToIntConvert = 9;
                } else if (this.monthType.equalsIgnoreCase("DECEMBER")) {
                    monthToIntConvert = 12;
                } else if (this.monthType.equalsIgnoreCase("MARCH")) {
                    monthToIntConvert = 3;
                }
                if (monthToIntConvert == 3 || monthToIntConvert == 6 || monthToIntConvert == 9) {
                    String newFirstDate = "01/" + "0" + String.valueOf(monthToIntConvert) + "/" + year;
                    resultLastDate = tdCalMgmtRemote.getLastdate(newFirstDate);
                    this.setAccToBeCredited(resultLastDate);
                } else if (monthToIntConvert == 12) {
                    newFirstDate = "01/" + String.valueOf(monthToIntConvert) + "/" + year;
                    resultLastDate = tdCalMgmtRemote.getLastdate(newFirstDate);
                    this.setAccToBeCredited(resultLastDate);
                }
            }

        } catch (Exception ex) {
            this.setError(ex.getMessage());
        }
        return resultLastDate;
    }

    public void getTableDetails() {
        try {
            this.setCalcFlag(false);

            if (this.monthType != null) {
                b = getFirstDateResult();
                a = getLastDateResult();
                this.setAccToBeCredited("");
                if (this.monthType.equalsIgnoreCase("--Select--")) {
                    this.setError("Please Select Month.");
                    flag = "false";
                    return;
                } else {
                    this.setError("");
                    flag = "true";
                }

                glHead = tdCalMgmtRemote.getGlHead((a.substring(6, 10) + a.substring(3, 5) + a.substring(0, 2)), brnName);

                this.setAccToBeCredited(glHead);

                List<TdsDetail> tdsCalResultList = tdCalMgmtRemote.projectedTdsCalculation((b.substring(6, 10) + b.substring(3, 5) + b.substring(0, 2)),
                        (a.substring(6, 10) + a.substring(3, 5) + a.substring(0, 2)), brnName);
                tdsPostResultList = tdsCalResultList;
                reportDetail = new ArrayList<ReportDetail>();
                if (tdsCalResultList.size() > 0 && tdsCalResultList.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                    int k = 1;
                    for (TdsDetail tdsDetail : tdsCalResultList) {
                        report = new ReportDetail();
                        report.setsNo(String.valueOf(k++));
                        report.setCustId(tdsDetail.getCustId());

                        report.setAccNo(tdsDetail.getAccNo());
                        report.setName(tdsDetail.getName());
                        report.setVoucherNo(tdsDetail.getVoucherNo());
                        report.setOption(tdsDetail.getOption());
                        report.setPanNo(tdsDetail.getPanNo());

                        report.setStatus(tdsDetail.getStatus());
                        report.setTdsDeducted(tdsDetail.getTdsDeducted());
                        report.setTdsCalculated(tdsDetail.getTdsCalculated());
                        report.setTdsToBeDed(tdsDetail.getTdsToBeDed());
                        report.setIntPaid(tdsDetail.getIntPaid());
                        report.setIntCalculated(tdsDetail.getIntCalculated());
                        reportDetail.add(report);
                    }
                    calcFlag = true;
                    this.setcFlag(true);
                    this.setPostFlag(false);
                    yearEnd();
                    String fromDt = 01 + "/" + 04 + "/" + yearend;
                   // int nYear = (Integer.parseInt(yearend.toString()) + 1);
                    //String nYear1 = Integer.toString(nYear);
                    //String toDt = 31 + "/" + 03 + "/" + nYear1;
                    String toDt = a.substring(0, 2) + "/" + a.substring(3, 5) + "/" + a.substring(6, 10);
                    String report = "TDS Calculation For Current Fin Year";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDate", getTodayDate());
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pFromDate", fromDt);
                    fillParams.put("pTodate", toDt);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                    try {
                        new ReportBean().jasperReport("TdsCalculation", "text/html", new JRBeanCollectionDataSource(tdsCalResultList), fillParams, report);
                    } catch (Exception e) {
                        setError(e.getLocalizedMessage());
                    }
                } else {
                    calcFlag = false;
                    this.setError("Data does not exist.");
                }
            }
        } catch (ApplicationException ex) {
            this.setError(ex.getMessage());
        } catch (Exception ex) {
            this.setError(ex.getMessage());
        }
    }

    public void yearEnd() {
        try {
            List resultList = new ArrayList();
            resultList = loanMasterRemote.finYearDropDownLoanInterestParameter();
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    yearend = ele.get(0).toString();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refreshForm() {
        setError("");
        this.setMonthType("--Select--");
        this.setAccToBeCredited("");
        this.setCalcFlag(false);
        this.setcFlag(false);
        this.setPostFlag(true);
        reportDetail = new ArrayList<ReportDetail>();
    }

    public String exitForm() {
        refreshForm();
        this.setError("");
        return "case1";
    }
}
