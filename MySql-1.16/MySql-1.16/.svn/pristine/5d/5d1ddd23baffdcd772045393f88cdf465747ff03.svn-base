/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.npci;

import com.cbs.dto.report.AccWiesMMSRepPojo;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AccWiesMMSReport extends BaseBean {

    private String message;
    private String reportType;
    private String fromDate;
    private String toDate;
    private String accountNo;
    private String umrn;
    private String displayOnAccType;
    private String displayOnUmrnType;
    private List<SelectItem> reportTypeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private NpciMandateFacadeRemote npciManRemot;
    private CommonReportMethodsRemote common;
    private List<AccWiesMMSRepPojo> resultList;

    public AccWiesMMSReport() {
        try {
            npciManRemot = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            resultList = new ArrayList<AccWiesMMSRepPojo>();

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("ACCWISE", "ACCOUNT WISE"));
            reportTypeList.add(new SelectItem("UMRNWIES", "UMRN WISE"));

            displayOnAccType = "";
            displayOnUmrnType = "none";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onblurReportType() {
        if (reportType.equalsIgnoreCase("ACCWISE")) {
            displayOnAccType = "";
            displayOnUmrnType = "none";
        } else if (reportType.equalsIgnoreCase("UMRNWIES")) {
            displayOnAccType = "none";
            displayOnUmrnType = "";
        }
    }

    public void btnRefreshAction() {
        this.message = "";
        this.reportType = "";
        this.fromDate = "";
        this.toDate = "";
        this.accountNo = "";
        this.umrn = "";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
                this.setMessage("Please select Report Type !");
                return;
            }
            if (reportType.equalsIgnoreCase("ACCWISE")) {
                if (this.accountNo == null || this.accountNo.equals("")) {
                    this.setMessage("Please Fill Account No.!");
                    return;
                }
            } else if (reportType.equalsIgnoreCase("UMRNWIES")) {
                if (this.umrn == null || this.umrn.equals("")) {
                    this.setMessage("Please Fill UMRN !");
                    return;
                }
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }

            resultList = npciManRemot.mmsReportData(reportType, accountNo, umrn, fromDate, toDate);
            String pReportName = "", pReportDate = "", pPrintedBy = "", pbankName = "",
                    pReportType = "", pDateRange = "", pAccountNo = "", pAccountName = "";
            if (!resultList.isEmpty()) {
                pReportName = "MMS Status Report";

                pbankName = common.getBankName();
                pReportDate = getTodayDate();
                pPrintedBy = getUserName();
                if (reportType.equalsIgnoreCase("ACCWISE")) {
                    pReportType = "Account Wies MMS Report";
                    pAccountName = "Account No.:";
                    pAccountNo = accountNo;
                } else if (reportType.equalsIgnoreCase("UMRNWIES")) {
                    pReportType = "UMRN Wies MMS Report";
                    pAccountName = "UMRN No.:";
                    pAccountNo = umrn;
                }
                pDateRange = fromDate + " - " + toDate;

                Map fillParams = new HashMap();

                fillParams.put("pReportName", pReportName);
                fillParams.put("pReportDate", pReportDate);
                fillParams.put("pPrintedBy", pPrintedBy);

                fillParams.put("pReportType", pReportType);
                fillParams.put("pbankName", pbankName);
                fillParams.put("pDateRange", pDateRange);
                fillParams.put("pAccountNo", pAccountNo);
                fillParams.put("pAccountName", pAccountName);

                new ReportBean().jasperReport("accwiseMMSreport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, pReportName);

                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
                this.setMessage("Please select Report Type !");
                return;
            }
            if (reportType.equalsIgnoreCase("ACCWISE")) {
                if (this.accountNo == null || this.accountNo.equals("")) {
                    this.setMessage("Please Fill Account No.!");
                    return;
                }
            } else if (reportType.equalsIgnoreCase("UMRNWIES")) {
                if (this.umrn == null || this.umrn.equals("")) {
                    this.setMessage("Please Fill UMRN !");
                    return;
                }
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }

            resultList = npciManRemot.mmsReportData(reportType, accountNo, umrn, fromDate, toDate);
            String pReportName = "", pReportDate = "", pPrintedBy = "", pbankName = "",
                    pReportType = "", pDateRange = "", pAccountNo = "", pAccountName = "";
            if (!resultList.isEmpty()) {
                pReportName = "MMS Status Report";
                pbankName = common.getBankName();
                pReportDate = getTodayDate();
                pPrintedBy = getUserName();
                if (reportType.equalsIgnoreCase("ACCWISE")) {
                    pReportType = "Account Wies MMS Report";
                    pAccountName = "Account No.:";
                    pAccountNo = accountNo;
                } else if (reportType.equalsIgnoreCase("UMRNWIES")) {
                    pReportType = "UMRN Wies MMS Report";
                    pAccountName = "UMRN No.:";
                    pAccountNo = umrn;
                }
                pDateRange = fromDate + " - " + toDate;

                Map fillParams = new HashMap();

                fillParams.put("pReportName", pReportName);
                fillParams.put("pReportDate", pReportDate);
                fillParams.put("pPrintedBy", pPrintedBy);

                fillParams.put("pReportType", pReportType);
                fillParams.put("pbankName", pbankName);
                fillParams.put("pDateRange", pDateRange);
                fillParams.put("pAccountNo", pAccountNo);
                fillParams.put("pAccountName", pAccountName);

                new ReportBean().openPdf("accwiseMMSreport", "accwiseMMSreport", new JRBeanCollectionDataSource(resultList), fillParams);

                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }


        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getDisplayOnAccType() {
        return displayOnAccType;
    }

    public void setDisplayOnAccType(String displayOnAccType) {
        this.displayOnAccType = displayOnAccType;
    }

    public String getDisplayOnUmrnType() {
        return displayOnUmrnType;
    }

    public void setDisplayOnUmrnType(String displayOnUmrnType) {
        this.displayOnUmrnType = displayOnUmrnType;
    }
}
