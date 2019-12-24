/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.SortedByFlag;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class NpaStatus extends BaseBean {

    private String message;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String labelStatus = "";
    private String status;
    private String dropStatus = "";
    private List<SelectItem> statusList;
    private String secondRowFirst = "";
    private String acType;
    private List<SelectItem> acTypeList;
    private String frDt;
    private String toDt;
    private String secondRowSecond = "none";
    private String tillDt;
    private String secondRowThird = "none";
    private String forQuarter;
    private List<SelectItem> forQuarterList;
    private String year;
    private String thirdRow = "none";
    private String acStatus;
    private List<SelectItem> acStatusList;
    private String npaReportAcType;
    private List<SelectItem> npaReportAcTypeList;
    private List<SelectItem> yearList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "NpaReportFacade";
    private NpaReportFacadeRemote remote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private String branchCode;
    private List<SelectItem> branchCodeList;

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public List<SelectItem> getAcStatusList() {
        return acStatusList;
    }

    public void setAcStatusList(List<SelectItem> acStatusList) {
        this.acStatusList = acStatusList;
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getDropStatus() {
        return dropStatus;
    }

    public void setDropStatus(String dropStatus) {
        this.dropStatus = dropStatus;
    }

    public String getForQuarter() {
        return forQuarter;
    }

    public void setForQuarter(String forQuarter) {
        this.forQuarter = forQuarter;
    }

    public List<SelectItem> getForQuarterList() {
        return forQuarterList;
    }

    public void setForQuarterList(List<SelectItem> forQuarterList) {
        this.forQuarterList = forQuarterList;
    }

    public String getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getSecondRowFirst() {
        return secondRowFirst;
    }

    public void setSecondRowFirst(String secondRowFirst) {
        this.secondRowFirst = secondRowFirst;
    }

    public String getSecondRowSecond() {
        return secondRowSecond;
    }

    public void setSecondRowSecond(String secondRowSecond) {
        this.secondRowSecond = secondRowSecond;
    }

    public String getSecondRowThird() {
        return secondRowThird;
    }

    public void setSecondRowThird(String secondRowThird) {
        this.secondRowThird = secondRowThird;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(String thirdRow) {
        this.thirdRow = thirdRow;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNpaReportAcType() {
        return npaReportAcType;
    }

    public void setNpaReportAcType(String npaReportAcType) {
        this.npaReportAcType = npaReportAcType;
    }

    public List<SelectItem> getNpaReportAcTypeList() {
        return npaReportAcTypeList;
    }

    public void setNpaReportAcTypeList(List<SelectItem> npaReportAcTypeList) {
        this.npaReportAcTypeList = npaReportAcTypeList;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public NpaStatus() {
        try {
            acStatusList = new ArrayList<SelectItem>();
            npaReportAcTypeList = new ArrayList<SelectItem>();
            forQuarterList = new ArrayList<SelectItem>();
            yearList = new ArrayList<SelectItem>();
            remote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);

            fillOnLoadList();
            this.setFrDt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setTillDt(getTodayDate());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillOnLoadList() {
        try {
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "NPA Status Report"));
            this.labelStatus = "";
            this.dropStatus = "";

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "CONSOLIDATED REPORT"));
            statusList.add(new SelectItem("1", "A/c STATUSWISE REPORT"));

            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("ALL"));
            List accountCodeList = remote.getAccountType();
            if (!accountCodeList.isEmpty()) {
                for (int i = 0; i < accountCodeList.size(); i++) {
                    Vector element = (Vector) accountCodeList.get(i);
                    acTypeList.add(new SelectItem(element.get(0).toString()));
                }
            }
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void reportTypeAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMessage("Please select report type !");
                return;
            }
            if (this.reportType.equals("0")) {
                this.labelStatus = "";
                this.dropStatus = "";
                this.secondRowFirst = "";
                this.secondRowSecond = "none";
                this.secondRowThird = "none";
                this.thirdRow = "none";
            } else if (this.reportType.equals("1")) {
                npaReportAcTypeList = new ArrayList<SelectItem>();
                this.labelStatus = "none";
                this.dropStatus = "none";
                this.secondRowFirst = "none";
                this.secondRowSecond = "";
                this.secondRowThird = "none";
                this.thirdRow = "none";
                List resultList = remote.getNpaReportAccountType();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector element = (Vector) resultList.get(i);
                        npaReportAcTypeList.add(new SelectItem(element.get(0).toString()));
                    }
                }
            } else if (this.reportType.equals("2")) {
                forQuarterList = new ArrayList<SelectItem>();
                yearList = new ArrayList<SelectItem>();
                this.labelStatus = "none";
                this.dropStatus = "none";
                this.secondRowFirst = "none";
                this.secondRowSecond = "none";
                this.secondRowThird = "";
                this.thirdRow = "none";

                Integer m = 2;
                for (int i = 0; m <= 11; i++) {
                    if (i != 0) {
                        m = m + 3;
                    }
                    if (m <= 11) {
                        String monthName = CbsUtil.getMonthForInt(m);
                        String monNumber = String.valueOf(m + 1);
                        forQuarterList.add(new SelectItem(monNumber, monthName));
                    }
                }

                Calendar now = Calendar.getInstance();
                for (int i = -5; i < 6; i++) {
                    int year = now.get(Calendar.YEAR) + i;
                    yearList.add(new SelectItem(String.valueOf(year)));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void statusAction() {
        this.setMessage("");
        acStatusList = new ArrayList<SelectItem>();
        try {
            if (this.status == null || this.status.equals("")) {
                this.setMessage("Please select status !");
                return;
            }
            if (this.status.equals("0")) {
                this.thirdRow = "none";
            } else if (this.status.equals("1")) {
                this.thirdRow = "";
                List resultList = remote.getAcStatus();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector element = (Vector) resultList.get(i);
                        acStatusList.add(new SelectItem(element.get(0).toString()));
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void asOnDateAction() {
        this.setMessage("");
        try {
            if (this.toDt == null || this.toDt.equals("")) {
                this.setMessage("Please select from date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
            if (result == false) {
                this.setMessage("Please select proper as on date !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fromDateAction() {
        this.setMessage("");
        try {
            if (this.frDt == null || this.frDt.equals("")) {
                this.setMessage("Please select from date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.frDt);
            if (result == false) {
                this.setMessage("Please select proper as on date !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void tillAction() {
        this.setMessage("");
        try {
            if (this.tillDt == null || this.tillDt.equals("")) {
                this.setMessage("Please select from date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.tillDt);
            if (result == false) {
                this.setMessage("Please select proper till date !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
        String bankName = "", bankAddress = "";
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMessage("Please select report type !");
                return;
            }

            if (!this.getBranchCode().equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(this.branchCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }

            if (this.reportType.equals("0")) {
                if (this.toDt == null || this.toDt.equals("")) {
                    this.setMessage("Please select from date !");
                    return;
                }
                boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
                if (result == false) {
                    this.setMessage("Please select proper as on date !");
                    return;
                }
                String rbiParameter = "Y";
                if (this.status.equals("0")) {
                    resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), "", this.branchCode, rbiParameter);
                } else if (this.status.equals("1")) {
                    if (this.acStatus == null || this.acStatus.equalsIgnoreCase("")) {
                        this.setMessage("Please select a/c status !");
                        return;
                    } else {
                        /*SUB STANDARD
                         * DOUBT FUL
                         * LOSS
                         * STANDARD*/
                        if (acStatus.equalsIgnoreCase("SUB STANDARD")) {
                            rbiParameter = "Y";
                            resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        } else if (acStatus.equalsIgnoreCase("DOUBT FUL")) {
                            rbiParameter = "Y";
                            resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        } else if (acStatus.equalsIgnoreCase("LOSS")) {
                            rbiParameter = "Y";
                            resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        } else if (acStatus.equalsIgnoreCase("STANDARD")) {
                            // Software Bug #41986
                            rbiParameter = "N";
                            resultList = remote.getNpaStatusReportData1(status, acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), acStatus, branchCode, rbiParameter);
                            //resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        }
                    }
//                    resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                }

//                 for(int i = 0;i<resultList.size();i++){
//                     System.out.print(resultList.get(i).getAcno()+":"+resultList.get(i).getBalance());                     
//                 }

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByFlag());
                Collections.sort(resultList, chObj);

                if (!resultList.isEmpty()) {
                    String reportName = "Standard/Npa Status Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAdd", bankAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", reportName);
                    fillParams.put("pPrintedDate", this.frDt + " to " + this.toDt);

                    new ReportBean().jasperReport("NpaStatusReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                    getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    getHttpSession().setAttribute("ReportName", reportName);
                } else {
                    this.setMessage("There is no data to print !");
                }
            } else if (this.reportType.equals("1")) {
                if (this.tillDt == null || this.tillDt.equals("")) {
                    this.setMessage("Please select from date !");
                    return;
                }
                boolean result = new Validator().validateDate_dd_mm_yyyy(this.tillDt);
                if (result == false) {
                    this.setMessage("Please select proper till date !");
                    return;
                }
                //resultList = remote.getNpaReportData(this.npaReportAcType, ymd.format(dmy.parse(this.tillDt)), this.branchCode);

                if (!resultList.isEmpty()) {
                    String reportName = "Npa Status Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAdd", bankAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", reportName);
                    fillParams.put("pPrintedDate", this.tillDt);

                    new ReportBean().jasperReport("NpaReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                    getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    getHttpSession().setAttribute("ReportName", reportName);
                } else {
                    this.setMessage("There is no data to print !");
                }
            } else if (this.reportType.equals("2")) {
                if (this.forQuarter == null || this.forQuarter.equalsIgnoreCase("")) {
                    this.setMessage("Please for quarter month !");
                    return;
                }
                if (this.year == null || this.year.equalsIgnoreCase("")) {
                    this.setMessage("Please select year !");
                    return;
                }
                // resultList = remote.getNpaCategoryReportData(this.forQuarter, this.year, this.branchCode);

                if (!resultList.isEmpty()) {
                    String reportName = "Npa Category Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAdd", bankAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", reportName);
                    fillParams.put("pPrintedDate", dmy.format(ymd.parse(CbsUtil.getQuarterFirstAndLastDate(this.forQuarter, this.year, "L"))));

                    new ReportBean().jasperReport("NpaCategoryReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                    getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    getHttpSession().setAttribute("ReportName", reportName);
                } else {
                    this.setMessage("There is no data to print !");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        this.setMessage("");
        List resultList = new ArrayList();
        String bankName = "", bankAddress = "";
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMessage("Please select report type !");
                return;
            }

            if (!this.getBranchCode().equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(this.branchCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }

            if (this.reportType.equals("0")) {
                if (this.toDt == null || this.toDt.equals("")) {
                    this.setMessage("Please select from date !");
                    return;
                }
                boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
                if (result == false) {
                    this.setMessage("Please select proper as on date !");
                    return;
                }
                String rbiParameter = "Y";
                if (this.status.equals("0")) {
                    resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), "", this.branchCode, rbiParameter);
                } else if (this.status.equals("1")) {
                    if (this.acStatus == null || this.acStatus.equalsIgnoreCase("")) {
                        this.setMessage("Please select a/c status !");
                        return;
                    } else {
                        /*SUB STANDARD
                         * DOUBT FUL
                         * LOSS
                         * STANDARD*/
                        if (acStatus.equalsIgnoreCase("SUB STANDARD")) {
                            rbiParameter = "Y";
                            resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        } else if (acStatus.equalsIgnoreCase("DOUBT FUL")) {
                            rbiParameter = "Y";
                            resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        } else if (acStatus.equalsIgnoreCase("LOSS")) {
                            rbiParameter = "Y";
                            resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        } else if (acStatus.equalsIgnoreCase("STANDARD")) {
                            // Software Bug #41986
                            rbiParameter = "N";
                            resultList = remote.getNpaStatusReportData1(status, acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), acStatus, branchCode, rbiParameter);
                            //resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                        }
                    }
//                    resultList = remote.getNpaStatusReportDataOptimized(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode, rbiParameter);
                }
//                if (this.status.equals("0")) {
//                    resultList = remote.getNpaStatusReportDataNew(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), "", this.branchCode);
//                } else if (this.status.equals("1")) {
//                    if (this.acStatus == null || this.acStatus.equalsIgnoreCase("")) {
//                        this.setMessage("Please select a/c status !");
//                        return;
//                    }
//                    resultList = remote.getNpaStatusReportDataNew(this.status, this.acType, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)), this.acStatus, this.branchCode);
//                }

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByFlag());
                Collections.sort(resultList, chObj);

                if (!resultList.isEmpty()) {
                    String reportName = "Standard/Npa Status Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAdd", bankAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", reportName);
                    fillParams.put("pPrintedDate", this.frDt + " to " + this.toDt);

                    new ReportBean().openPdf("NpaStatusReport_" + ymd.format(dmy.parse(this.toDt)) + "_" + ymd.format(dmy.parse(this.getTodayDate())), "NpaStatusReport", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", reportName);

                } else {
                    this.setMessage("There is no data to print !");
                }
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.labelStatus = "";
        this.dropStatus = "";
        this.secondRowFirst = "";
        this.secondRowSecond = "none";
        this.secondRowThird = "none";
        this.thirdRow = "none";
        this.setReportType("0");
        this.setStatus("0");
        this.setAcType("ALL");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
