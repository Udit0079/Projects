/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.dto.report.WorkingStmtBorrowingPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.Util;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class WorkingStmtBorrowing extends BaseBean {

    private String message;
    private String acType;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    Date frdt;
    private List<SelectItem> acTypeList;
    Date dt = new Date();
    HttpServletRequest req;
    String orgnBrCode;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanReportFacadeRemote loanFacade;
    private CommonReportMethodsRemote common;
    List<WorkingStmtBorrowingPojo> repDataList = new ArrayList<WorkingStmtBorrowingPojo>();

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

    public Date getFrdt() {
        return frdt;
    }

    public void setFrdt(Date frdt) {
        this.frdt = frdt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public WorkingStmtBorrowing() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setFrdt(dt);

            loanFacade = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            List acTLst = new ArrayList();
            acTLst = loanFacade.getBorrowAccList();
            acTypeList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele7 = (Vector) acTLst.get(i);
                    acTypeList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        if (this.frdt == null) {
            setMessage("Please Fill Date.");
        }
        String bankName = "", bankAddress = "", finDt = "";
        Map fillParams = new HashMap();
        try {
            List dataList1 = common.getBranchNameandAddress(orgnBrCode);
            String report = "Working Statement Borrowing";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            finDt = loanFacade.getFindate(ymd.format(this.getFrdt()), this.orgnBrCode);
            //     finDt = loanFacade.getFindate(ymd.format(this.getFrdt()), branchCode);
            String PrevDt = ymd.format(Util.calculateMonthEndDate(Integer.parseInt(finDt.substring(4, 6)) - 1, Integer.parseInt(finDt.substring(0, 4))));

            fillParams.put("pReportFrDate", finDt.substring(6) + "/" + finDt.substring(4, 6) + "/" + finDt.substring(0, 4));
            fillParams.put("pReportToDate", dmy.format(this.frdt));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchName", bankAddress);
            fillParams.put("repType", this.getAcType());

            repDataList = loanFacade.workingStmtBorrowData(this.getAcType(), ymd.format(this.getFrdt()), branchCode, finDt, PrevDt);
            if (repDataList.isEmpty()) {
                throw new ApplicationException("Data does not exit!");
            }
            new ReportBean().jasperReport("WorkingStmtBorrow", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "Working Statement Borrowing");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        if (this.frdt == null) {
            setMessage("Please Fill Date.");
        }
        String bankName = "", bankAddress = "", finDt = "";
        Map fillParams = new HashMap();
        try {
            List dataList1 = common.getBranchNameandAddress(orgnBrCode);
            String report = "Working Statement Borrowing";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            finDt = loanFacade.getFindate(ymd.format(this.getFrdt()), this.orgnBrCode);
            //     finDt = loanFacade.getFindate(ymd.format(this.getFrdt()), branchCode);
            String PrevDt = ymd.format(Util.calculateMonthEndDate(Integer.parseInt(finDt.substring(4, 6)) - 1, Integer.parseInt(finDt.substring(0, 4))));

            fillParams.put("pReportFrDate", finDt.substring(6) + "/" + finDt.substring(4, 6) + "/" + finDt.substring(0, 4));
            fillParams.put("pReportToDate", dmy.format(this.frdt));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchName", bankAddress);
            fillParams.put("repType", this.getAcType());

            repDataList = loanFacade.workingStmtBorrowData(this.getAcType(), ymd.format(this.getFrdt()), branchCode, finDt, PrevDt);
            if (repDataList.isEmpty()) {
                throw new ApplicationException("Data does not exit!");
            }
            new ReportBean().openPdf("Working Statement Borrowing_"+ ymd.format(dmy.parse(getTodayDate())), "WorkingStmtBorrow", new JRBeanCollectionDataSource(repDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setMessage("");
            setFrdt(dt);

        } catch (Exception e) {
            setMessage(e.getMessage());

        }
    }

    public String exitAction() {
        return "case1";
    }
}