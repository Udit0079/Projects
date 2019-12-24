/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.npci;

import com.cbs.dto.report.AchEcsResponseStatusReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.SortedByAchEcsStatus;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

public class achEcsResponseStatusReport extends BaseBean {

    private String message;
    private String txnFileType;
    private String txnType;
    private String fromDate;
    private String toDate;
    private String status;
    private List<SelectItem> txnFileTypeList;
    private List<SelectItem> txnTypeList;
    private List<SelectItem> statusList;
    private List<AchEcsResponseStatusReportPojo> resultdata;
    private NpciMgmtFacadeRemote npciRemoteBene;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final static String REPORT_NAME = "Sponsor ACH-ECS Report";

    public achEcsResponseStatusReport() {
        try {
            npciRemoteBene = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            txnFileTypeList = new ArrayList<SelectItem>();
            txnFileTypeList.add(new SelectItem("0", "--Select--"));
            txnFileTypeList.add(new SelectItem("ACH", "ACH"));
            txnFileTypeList.add(new SelectItem("ECS", "ECS"));

            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("0", "--Select--"));
            txnTypeList.add(new SelectItem("CREDIT", "CREDIT"));
            txnTypeList.add(new SelectItem("DEBIT", "DEBIT"));

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("s", "--Select--"));
            statusList.add(new SelectItem("In", "Initiated"));
            statusList.add(new SelectItem("'1'", "Success"));
            statusList.add(new SelectItem("'0','2'", "Return"));
            statusList.add(new SelectItem("All", "All"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void btnHtmlAction() throws ApplicationException {
        this.setMessage("");
        try {
            System.out.println(status);
            String isValid = validateField();
            if (!isValid.equalsIgnoreCase("valid")) {
                this.setMessage(isValid);
                return;
            }

            String sponsorType = "";
            if (this.txnFileType.equalsIgnoreCase("ACH")) {
                sponsorType = "AINPSD";
            } else if (this.txnFileType.equalsIgnoreCase("ECS")) {
                sponsorType = "EINPSD";
            }

            resultdata = npciRemoteBene.getAchEcsResponseStatusReportData(txnFileType, sponsorType, txnType,
                    ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), status);
            if (resultdata.isEmpty()) {
                this.setMessage("There is not data to print !");
                return;
            }

            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", REPORT_NAME);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pReportType", txnFileType + "/" + txnType);
            fillParams.put("pBankName", branchNameandAddress.get(0));
            fillParams.put("pBankAdd", branchNameandAddress.get(1));
            fillParams.put("pDateRange", fromDate + " - " + toDate);

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByAchEcsStatus());
            Collections.sort(resultdata, chObj);

            new ReportBean().jasperReport("achEcsResponseStatusReport", "text/html", new JRBeanCollectionDataSource(resultdata),
                    fillParams, "achEcsResponseStatusReport");

            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", REPORT_NAME);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setMessage("");
        try {
            String isValid = validateField();
            if (!isValid.equalsIgnoreCase("valid")) {
                this.setMessage(isValid);
                return;
            }

            String sponsorType = "";
            if (this.txnFileType.equalsIgnoreCase("ACH")) {
                sponsorType = "AINPSD";
            } else if (this.txnFileType.equalsIgnoreCase("ECS")) {
                sponsorType = "EINPSD";
            }
            resultdata = npciRemoteBene.getAchEcsResponseStatusReportData(txnFileType, sponsorType, txnType,
                    ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), status);
            if (resultdata.isEmpty()) {
                this.setMessage("There is not data to print !");
                return;
            }

            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", REPORT_NAME);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pReportType", txnFileType + "/" + txnType);
            fillParams.put("pBankName", branchNameandAddress.get(0));
            fillParams.put("pBankAdd", branchNameandAddress.get(1));
            fillParams.put("pDateRange", fromDate + " - " + toDate);

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByAchEcsStatus());
            Collections.sort(resultdata, chObj);

            new ReportBean().openPdf("achEcsResponseStatusReport", "achEcsResponseStatusReport", new JRBeanCollectionDataSource(resultdata), fillParams);

            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", REPORT_NAME);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateField() {
        try {
            if (this.txnFileType == null || this.txnFileType.equalsIgnoreCase("0")) {
                return "Please select File Type !";
            }
            if (this.txnType == null || this.txnType.equals("0")) {
                return "Please select Txn Type !";
            }
            if (this.status == null || this.status.equalsIgnoreCase("s")) {
                return "Please select Status !";
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                return "Please Fill From date !";
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fromDate)) {
                return "Please Fill Proper From Date !";
            }
            if (this.toDate == null || this.toDate.equals("")) {
                return "Please Fill To Date !";
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.toDate)) {
                return "Please Fill Proper To Date !";
            }
            if (dmy.parse(this.fromDate).compareTo(dmy.parse(this.toDate)) > 0) {
                return "From Date can not be greater than To Date !";
            }
            if (dmy.parse(this.toDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                return "To Date can not be greater than Current Date !";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "valid";
    }

    public void btnRefreshAction() {
        this.message = "";
        this.txnFileType = "0";
        this.txnType = "0";
        this.fromDate = getTodayDate();
        this.toDate = getTodayDate();
        this.status = "--Select--";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
    public String getTxnFileType() {
        return txnFileType;
    }

    public List<SelectItem> getTxnFileTypeList() {
        return txnFileTypeList;
    }

    public void setTxnFileTypeList(List<SelectItem> txnFileTypeList) {
        this.txnFileTypeList = txnFileTypeList;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public void setTxnFileType(String txnFileType) {
        this.txnFileType = txnFileType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }
}
