/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.CbsCashTrfClgScrollTimewisePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
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
 * @author root
 */
public class MismatchXferScroll extends BaseBean {

    String message;
    private String branchOption;
    private List<SelectItem> branchOptionList;
    String date;
    Date dt = new Date();
    private String auth;
    private List<SelectItem> authList;
    private final String jndiHomeName = "LedgerReportFacade";
    private LedgerReportFacadeRemote ledgerReportRemote = null;
    private CommonReportMethodsRemote common;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<CbsCashTrfClgScrollTimewisePojo> cashScrollTimewiseDataList = new ArrayList<CbsCashTrfClgScrollTimewisePojo>();

    public MismatchXferScroll() {

        try {
            ledgerReportRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            branchOptionList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchOptionList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
                }
            }
            authList = new ArrayList<>();
            authList.add(new SelectItem("N", "N"));
            authList.add(new SelectItem("Y", "Y"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        try {
            String report = "Mismatch Transfer Scroll";
            String bankName = "", bankAddress = "";
            List dataList1 = common.getBranchNameandAddress(branchOption);
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportDate", date);
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", bankName);
            fillParams.put("pBnkAdd", bankAddress);

            cashScrollTimewiseDataList = ledgerReportRemote.getMismatchTransferScrollData(branchOption, ymd.format(sdf.parse(date)), auth);
            if (!cashScrollTimewiseDataList.isEmpty()) {
                new ReportBean().jasperReport("MismatchTransferBatch", "text/html",
                        new JRBeanCollectionDataSource(cashScrollTimewiseDataList), fillParams, "Mismatch Transfer Scroll");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No detail exists !!");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");

        try {
            String report = "Mismatch Transfer Scroll";
            Map fillParams = new HashMap();
            fillParams.put("pReportDate", sdf.format(new Date()));
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            cashScrollTimewiseDataList = ledgerReportRemote.getMismatchTransferScrollData(branchOption, ymd.format(sdf.parse(date)), auth);
            if (!cashScrollTimewiseDataList.isEmpty()) {
                new ReportBean().openPdf("Mismatch Transfer Scroll", "MismatchTransferBatch", new JRBeanCollectionDataSource(cashScrollTimewiseDataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } else {
                setMessage("No detail exists !!");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(String branchOption) {
        this.branchOption = branchOption;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public List<SelectItem> getAuthList() {
        return authList;
    }

    public void setAuthList(List<SelectItem> authList) {
        this.authList = authList;
    }
}
