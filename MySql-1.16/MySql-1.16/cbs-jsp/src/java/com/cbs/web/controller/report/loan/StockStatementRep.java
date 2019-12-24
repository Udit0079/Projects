/**
 *
 * @author root
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.ho.StockStatementRepPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
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

public class StockStatementRep extends BaseBean {

    private String branchcode;
    private List<SelectItem> branchcodeList;
    private String acnochoice;
    private String acno;
    private List<SelectItem> acnochoiceList;
    private String fromDate;
    private String toDate;
    private String errorMsg;
    private String status = "none";
    SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private LoanReportFacadeRemote local;
    List<StockStatementRepPojo> list;

    public StockStatementRep() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchcodeList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchcodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            acnochoiceList = new ArrayList();
            acnochoiceList.add(new SelectItem("ALL", "ALL"));
            acnochoiceList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));

        } catch (Exception e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
    }

    public void statusAction() {
        if (this.acnochoice.equalsIgnoreCase("ALL")) {
            this.status = "none";
            this.acno = "";
        } else {
            this.status = "";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public List<SelectItem> getBranchcodeList() {
        return branchcodeList;
    }

    public void setBranchcodeList(List<SelectItem> branchcodeList) {
        this.branchcodeList = branchcodeList;
    }

    public String getAcnochoice() {
        return acnochoice;
    }

    public void setAcnochoice(String acnochoice) {
        this.acnochoice = acnochoice;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public List<SelectItem> getAcnochoiceList() {
        return acnochoiceList;
    }

    public void setAcnochoiceList(List<SelectItem> acnochoiceList) {
        this.acnochoiceList = acnochoiceList;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void viewPdfReport() {

        if (fromDate.equalsIgnoreCase("") || fromDate == null) {
            this.setErrorMsg("Fill Valid From Date");
            return;
        }

        if (toDate.equalsIgnoreCase("") || toDate == null) {
            this.setErrorMsg("Fill Valid To Date");
            return;
        }

        if (acnochoice.equalsIgnoreCase("INDIVIDUAL") && acno.equalsIgnoreCase("")) {
            this.setErrorMsg("Acno can't be blacnk");
            return;
        }
        try {
            list = local.getStockStatementDetails(branchcode, acnochoice, acno, ymd.format(ymdFormat.parse(fromDate)), ymd.format(ymdFormat.parse(toDate)));
            if (!list.isEmpty()) {
                List bnkList;
                if (branchcode.equalsIgnoreCase("0A")) {
                    bnkList = common.getBranchNameandAddress("90");
                } else {
                    bnkList = common.getBranchNameandAddress(getBranchcode());
                }
                String bankName = "", bankAdd = "";
                if (!bnkList.isEmpty()) {
                    bankName = bnkList.get(0).toString();
                    bankAdd = bnkList.get(1).toString();
                }
                Map fillParams = new HashMap();
                String report = "Stock Statement Report";
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAdd);
                fillParams.put("pPrintedDate", this.getTodayDate());
                fillParams.put("pReportDate", "For the period " + fromDate + " to " + toDate + " ");
                new ReportBean().openPdf("Stock Statement Report", "Stock Statement Report", new JRBeanCollectionDataSource(list), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "Stock Statement Report");
            } else {
                this.setErrorMsg("Data doesn't exist!!");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        this.branchcode = "0";
        this.acnochoice = "0";
        this.acno = "0";
        this.fromDate = "";
        this.toDate = "";
        this.setErrorMsg("");
        this.status = "none";
    }

    public String exitAction() {
        return "case1";
    }
}
