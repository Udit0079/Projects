package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.MiscLongBookReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class MiscLongBook extends BaseBean{

    private String userName;
    private String brnCode;
    private String todayDate;
    private String msg;
    private String date;
    private LedgerReportFacadeRemote ledgerReportFacadeLocal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private HttpServletRequest req;
    List<SelectItem> orderByList;
    private String orderBy;
    private String focusId;
    private boolean disabled;

    /** Creates a new instance of MiscLongBook */
    public MiscLongBook() {
        try {
            req = getRequest();
            setUserName(req.getRemoteUser());
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            brnCode = Init.IP2BR.getBranch(localhost);
            todayDate = sdf.format(new Date());
            ledgerReportFacadeLocal = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("a.acno", "Account Number"));
            orderByList.add(new SelectItem("recno", "Txn ID"));
            setOrderBy("trantime");
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
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

    public boolean isDisabled() {
        return disabled;
    }
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    public String getFocusId() {
        return focusId;
    }
    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public List<SelectItem> getOrderByList() {
        return orderByList;
    }
    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
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
            try {
                String dt1[] = date.split("/");
                String frmDate = dt1[2] + dt1[1] + dt1[0];
                List<MiscLongBookReportPojo> reportList = ledgerReportFacadeLocal.getMiscLongBookReport(frmDate, brnCode,orderBy);
                Map fillParams = new HashMap();
                CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List vec = beanLocal.getBranchNameandAddress(brnCode);
                if (vec.size() > 0) {
                    fillParams.put("pReportName", "Misc Long Book Report");
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBranchAddress", vec.get(1).toString());
                    fillParams.put("pReportDate", date);
                }
                ReportBean rb = new ReportBean();
                if (reportList.size() > 0) {
                    rb.jasperReport("MiscLongBook", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Misc Long Book Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    setMsg("No detail exists");
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }

        }
    }
    
    public void pdfDownLoad() {
        String report = "Misc Long Book Report";
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String frmDate = dt1[2] + dt1[1] + dt1[0];
                List<MiscLongBookReportPojo> reportList = ledgerReportFacadeLocal.getMiscLongBookReport(frmDate, brnCode,orderBy);
                Map fillParams = new HashMap();
                CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List vec = beanLocal.getBranchNameandAddress(brnCode);
                if (vec.size() > 0) {
                    fillParams.put("pReportName",report );
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBranchAddress", vec.get(1).toString());
                    fillParams.put("pReportDate", date);
                }
                ReportBean rb = new ReportBean();
                if (reportList.size() > 0) {
                    rb.openPdf("MiscLongBook_", "MiscLongBook", new JRBeanCollectionDataSource(reportList), fillParams);
                      ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                } else {
                    setMsg("No detail exists");
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void refreshForm() {
        date = "";
        msg="";
    }

    public String exitAction() {
        return "case1";
    }

    public String validation() {
        if (date.equalsIgnoreCase("")) {
            return "Please Enter Date.";
        }
        return "ok";
    }
}
