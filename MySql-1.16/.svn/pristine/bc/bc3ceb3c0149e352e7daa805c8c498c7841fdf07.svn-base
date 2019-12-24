/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.ChqBookStopTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author Sudhir
 */
public class ChequeBookStop extends BaseBean {

    private String message;
    private String reportType;
    private String accountType;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> accountTypeList;
    private Date fromDate, toDate;
    private String branch;
    private List<SelectItem> branchList;
    private final String othersReportjndiName = "OtherReportFacade";
    private OtherReportFacadeRemote otherFacadeRemote = null;
    private final String commonReportjndiName = "CommonReportMethods";
    private CommonReportMethodsRemote commonFacadeRemote = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;
    private boolean disableAccType;

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
    
    public boolean isDisableAccType() {
        return disableAccType;
    }

    public void setDisableAccType(boolean disableAccType) {
        this.disableAccType = disableAccType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a new instance of AccountCloseRegister
     */
    public ChequeBookStop() {
        try {
            otherFacadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup(othersReportjndiName);
            commonFacadeRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonReportjndiName);
            this.setMessage("");
            onLoadData();
            disableAccType = true;
            List brnLst = new ArrayList();
            brnLst = commonFacadeRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void onLoadData() {
        reportTypeList = new ArrayList<SelectItem>();
        accountTypeList = new ArrayList<SelectItem>();
        reportTypeList.add(new SelectItem("0", "ALL Cheque Stop Payment"));
        reportTypeList.add(new SelectItem("1", "A/c Type Wise Stop Payment"));
        reportTypeList.add(new SelectItem("2", "ALL Cheque Operative"));
        reportTypeList.add(new SelectItem("3", "A/c Type Wise Operative Cheque"));
        reportTypeList.add(new SelectItem("4", "ALL Cheque Wise Stop Payment Reversal"));
        reportTypeList.add(new SelectItem("5", "A/c Type Wise Stop Payment Reversal"));
        List acTypeList = new ArrayList();
        try {
            acTypeList = commonFacadeRemote.getCASBAcTypeList();
        } catch (Exception e) {
        }
        if (!acTypeList.isEmpty()) {
            for (int i = 0; i < acTypeList.size(); i++) {
                Vector vec = (Vector) acTypeList.get(i);
                accountTypeList.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
            }
        }
    }

    public void onChaneReportType() {
        try {
            if (reportType.equalsIgnoreCase("1") || reportType.equalsIgnoreCase("3") || reportType.equalsIgnoreCase("5")) {
                disableAccType = false;
            } else {
                disableAccType = true;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void printReport() {
        try {
            setMessage("");
            flag = "true";
            if (fromDate == null) {
                setMessage("please set from date");
                flag = "false";
                return;
            }
            if (toDate == null) {
                setMessage("Please set to date");
                flag = "false";
                return;
            }
            if (disableAccType == false) {
                if (accountTypeList.isEmpty()) {
                    setMessage("Error!!! Account type is empty");
                    flag = "false";
                    return;
                }
            }
            flag = "true";
            String report = "";
            if (reportType.equals("0")) {
                report = "ALL Cheque Stop Payment";
            } else if (reportType.equals("1")) {
                report = "A/c Type Wise Stop Payment";
            } else if (reportType.equals("2")) {
                report = "ALL Cheque Operative";
            } else if (reportType.equals("3")) {
                report = "A/c Type Wise Operative Cheque";
            } else if (reportType.equals("4")) {
                report = "ALL Cheque Wise Stop Payment Reversal";
            } else if (reportType.equals("5")) {
                report = "A/c Type Wise Stop Payment Reversal";
            }
            if (reportType.equalsIgnoreCase("0") || reportType.equalsIgnoreCase("2") || reportType.equalsIgnoreCase("4")) {
                accountType = "";
            }
            List<ChqBookStopTable> stopChequeDetails = otherFacadeRemote.chequeStopDetails(reportType, accountType, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branch);
            if (stopChequeDetails.isEmpty()) {
                setMessage("No proper data available!!!");
                return;
            }
            Map fillParams = new HashMap();

            fillParams.put("repName", report);
            fillParams.put("printBy", getUserName());
            fillParams.put("pFromDate", dmyFormatter.format(fromDate));
            fillParams.put("oToDate", dmyFormatter.format(toDate));
            new ReportBean().jasperReport("ChequeStopPayRegister", "text/html", new JRBeanCollectionDataSource(stopChequeDetails), fillParams, "Cheque Stop Pay Register");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void pdfDownLoad() {
        try {
            setMessage("");
            flag = "true";
            if (fromDate == null) {
                setMessage("please set from date");
                flag = "false";
                return;
            }
            if (toDate == null) {
                setMessage("Please set to date");
                flag = "false";
                return;
            }
            if (disableAccType == false) {
                if (accountTypeList.isEmpty()) {
                    setMessage("Error!!! Account type is empty");
                    flag = "false";
                    return;
                }
            }
            flag = "true";
            String report = "";
            if (reportType.equals("0")) {
                report = "ALL Cheque Stop Payment";
            } else if (reportType.equals("1")) {
                report = "A/c Type Wise Stop Payment";
            } else if (reportType.equals("2")) {
                report = "ALL Cheque Operative";
            } else if (reportType.equals("3")) {
                report = "A/c Type Wise Operative Cheque";
            } else if (reportType.equals("4")) {
                report = "ALL Cheque Wise Stop Payment Reversal";
            } else if (reportType.equals("5")) {
                report = "A/c Type Wise Stop Payment Reversal";
            }
            if (reportType.equalsIgnoreCase("0") || reportType.equalsIgnoreCase("2") || reportType.equalsIgnoreCase("4")) {
                accountType = "";
            }
            List<ChqBookStopTable> stopChequeDetails = otherFacadeRemote.chequeStopDetails(reportType, accountType, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branch);
            if (stopChequeDetails.isEmpty()) {
                setMessage("No proper data available!!!");
                return;
            }
            Map fillParams = new HashMap();

            fillParams.put("repName", report);
            fillParams.put("printBy", getUserName());
            fillParams.put("pFromDate", dmyFormatter.format(fromDate));
            fillParams.put("oToDate", dmyFormatter.format(toDate));
            new ReportBean().openPdf("Cheque Book_" + ymdFormatter.format(dmyFormatter.parse(getTodayDate())), "ChequeStopPayRegister", new JRBeanCollectionDataSource(stopChequeDetails), fillParams);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Cheque Stop Pay Register");

        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public String exitFrm() {
        disableAccType = true;
        return "case1";
    }
}
