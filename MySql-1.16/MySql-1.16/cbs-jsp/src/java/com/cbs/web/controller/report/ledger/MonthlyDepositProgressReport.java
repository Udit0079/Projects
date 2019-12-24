/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

/**
 *
 * @author Ankit Verma
 */
import com.cbs.dto.report.MonthlyDepositProgressReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class MonthlyDepositProgressReport extends BaseBean {

    private String userName;
    private String brnCode;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String repType;
    private List<SelectItem> repTypeList;
    private String todayDate;
    private String msg;
    private String date;
    private LedgerReportFacadeRemote ledgerReportFacadeLocal;
    TdReceiptManagementFacadeRemote RemoteCode;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private HttpServletRequest req;

    public MonthlyDepositProgressReport() {
        try {
            req = getRequest();
            setUserName(req.getRemoteUser());
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            brnCode = Init.IP2BR.getBranch(localhost);
            todayDate = sdf.format(new Date());
            ledgerReportFacadeLocal = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("D", "Deposit"));
            repTypeList.add(new SelectItem("L", "Loan"));

        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
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

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void viewReport() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            msg = "";
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + dt1[1] + dt1[0];
                List<MonthlyDepositProgressReportPojo> reportList = ledgerReportFacadeLocal.getMonthlyDepositProgressReport(dd3, branchCode, repType);
                Map fillParams = new HashMap();
                CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List vec = beanLocal.getBranchNameandAddress(brnCode);
                if (vec.size() > 0) {
                    fillParams.put("pReportName", "Monthly Deposit Progress Report");
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBankAdd", vec.get(1).toString());
                    fillParams.put("pTillDate", date);
                }
                if (reportList.size() > 0) {
                    new ReportBean().jasperReport("Monthly_Deposit_Progress_Report", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Monthly Deposit Progress Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    setMsg("No detail exists !!");
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void pdfDownLoad() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            msg = "";
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + dt1[1] + dt1[0];
                List<MonthlyDepositProgressReportPojo> reportList = ledgerReportFacadeLocal.getMonthlyDepositProgressReport(dd3, branchCode, repType);
                Map fillParams = new HashMap();
                CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List vec = beanLocal.getBranchNameandAddress(brnCode);
                if (vec.size() > 0) {
                    fillParams.put("pReportName", "Monthly Deposit Progress Report");
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBankAdd", vec.get(1).toString());
                    fillParams.put("pTillDate", date);
                }
                if (reportList.size() > 0) {
                    new ReportBean().openPdf("Monthly Deposit Progress Report_" + dd3, "Monthly_Deposit_Progress_Report", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Monthly Deposit Progress Report");

                } else {
                    setMsg("No detail exists !!");
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void refreshForm() {
        date = "";
        msg = "";
    }

    public String exitAction() {
        return "case1";
    }

    public String validation() {
        if (date.equalsIgnoreCase("")) {
            return "Please enter date.";
        } else if (!Validator.validateDate(date)) {
            return "Please check the date, you entered !";
        }
        return "ok";
    }
}
