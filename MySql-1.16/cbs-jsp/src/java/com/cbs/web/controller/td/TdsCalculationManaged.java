/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.td;

import com.cbs.constant.Months;
import com.cbs.dto.TdsDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.TDInterestCalulationFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.other.ReportDetail;

import java.util.List;
import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class TdsCalculationManaged extends BaseBean {
    
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
    
    private String viewID = "/pages/master/sm/test.jsp";
    
    private boolean calcFlag;
    
    private boolean cFlag;
    
    private boolean postFlag;
    
    private List<SelectItem> finYearList;
    
    private final String jndiHomeNameTdCal = "TermDepositeCalculationManagementFacade";
    
    private TermDepositeCalculationManagementFacadeRemote tdCalMgmtRemote = null;
    
    private final String jndiHomeNameLoan = "BankAndLoanMasterFacade";
    
    private BankAndLoanMasterFacadeRemote loanMasterRemote = null;
    
    private final String tdInterestCalculationJndiName = "TDInterestCalulationFacade";
    
    private TDInterestCalulationFacadeRemote tdInterestCalculationMainRemote = null;
    
    List<TdsDetail> tdsPostResultList;
    
    String a,b;

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
    
    public TdsCalculationManaged() {
        try {
            tdCalMgmtRemote = (TermDepositeCalculationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdCal);
            loanMasterRemote = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoan);
            tdInterestCalculationMainRemote = (TDInterestCalulationFacadeRemote) ServiceLocator.getInstance().lookup(tdInterestCalculationJndiName);
            getCurrentYear();
            loadMonths();
            getFirstDateResult();
            getLastDateResult();
            getTableDetails();
            yearend = "";
            this.setCalcFlag(false);
            this.setcFlag(false);
            this.setPostFlag(true);
        } catch (ApplicationException ex) {
            this.setError(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setError(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void getCurrentYear() {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        String YEAR = sdf.format(date);
//        year = YEAR.substring(6, 10);
//    }

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
        
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        int mDay = cal.get(Calendar.MONTH);
        
       // if (mDay == 3 || mDay == 4 || mDay == 5) {
            monthTypeOption.add(new SelectItem(Months.JUNE.getName()));
       //} else if (mDay == 6 || mDay == 7 || mDay == 8) {
            monthTypeOption.add(new SelectItem(Months.SEPTEMBER.getName()));
       // } else if (mDay == 9 || mDay == 10 || mDay == 11) {
            monthTypeOption.add(new SelectItem(Months.DECEMBER.getName()));
       // } else {
            monthTypeOption.add(new SelectItem(Months.MARCH.getName()));
       // }
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
                
                glHead = tdCalMgmtRemote.getGlHead((a.substring(6, 10) + a.substring(3, 5) + a.substring(0, 2)), getOrgnBrCode());

                this.setAccToBeCredited(glHead);
                /**********************************insert into data1**********************************************/
                /**************************************************************************************************/
                List<TdsDetail> tdsCalResultList = tdCalMgmtRemote.tdsCalculationBeforeIntCalc((b.substring(6, 10) + b.substring(3, 5) + b.substring(0, 2)),
                        (a.substring(6, 10) + a.substring(3, 5) + a.substring(0, 2)), getOrgnBrCode());
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
                    int nYear = (Integer.parseInt(yearend.toString()) + 1);
                    String nYear1 = Integer.toString(nYear);
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
           // Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setError(ex.getMessage());
           // Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void postAction() {
        try {
            this.setcFlag(false);
            this.setPostFlag(true);
            
            if (tdsPostResultList.size() > 0 && tdsPostResultList.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                /*
                for (TdsDetail tdsDetail : tdsPostResultList) {
                    double iPaid = tdsDetail.getIntPaid();
                    double iCalc = tdsDetail.getIntCalculated();
                    if((iCalc!=0)){
                        this.setError("Please Post Interest First");
                        return;
                    }
                }*/
                
                double tTds=0;
                for (TdsDetail tdsDetail : tdsPostResultList) {
                    double tDed = tdsDetail.getTdsToBeDed();
                    tTds = tTds + tDed;
                }
                
//                if(tTds==0){
//                    this.setError("No TDS Find To Deduct");
//                    return;
//                }
                    
                String msg = tdCalMgmtRemote.tdsPostBeforeIntCalc(tdsPostResultList, this.getAccToBeCredited(), getOrgnBrCode(), (b.substring(6, 10) + b.substring(3, 5) + b.substring(0, 2)),
                    (a.substring(6, 10) + a.substring(3, 5) + a.substring(0, 2)), getUserName());
                    
                if(msg.substring(0,4).equalsIgnoreCase("true")){
                    this.setError("TDS reserved successfully and will be deducted at the time of Interest Posting.");
                }else{
                    this.setError(msg);
                }
            } else {
                this.setError("Data does not exist.");
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
