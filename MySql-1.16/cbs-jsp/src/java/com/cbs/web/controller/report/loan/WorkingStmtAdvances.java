package com.cbs.web.controller.report.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.AbstractWorkingStmtLoansPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.WorkingStmtLoansPojo;
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
 * @author Nishant Srivastava
 */
public class WorkingStmtAdvances extends BaseBean {

    private String message;
    private String stmtType;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private List<SelectItem> stmtTypeList;
    Date frdt;
    private String selectAcType;
    private List<SelectItem> selectAcTpList;
    HttpServletRequest req;
    String orgnBrCode;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanReportFacadeRemote loanFacade;
    private CommonReportMethodsRemote common;
    Date dt = new Date();
    private String status = "none";
    List<WorkingStmtLoansPojo> repLoansList = new ArrayList<WorkingStmtLoansPojo>();
    List<AbstractWorkingStmtLoansPojo> repAbstractLoansList = new ArrayList<AbstractWorkingStmtLoansPojo>();

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

    public List<SelectItem> getSelectAcTpList() {
        return selectAcTpList;
    }

    public void setSelectAcTpList(List<SelectItem> selectAcTpList) {
        this.selectAcTpList = selectAcTpList;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public String getStmtType() {
        return stmtType;
    }

    public void setStmtType(String stmtType) {
        this.stmtType = stmtType;
    }

    public List<SelectItem> getStmtTypeList() {
        return stmtTypeList;
    }

    public void setStmtTypeList(List<SelectItem> stmtTypeList) {
        this.stmtTypeList = stmtTypeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public WorkingStmtAdvances() {
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

            stmtTypeList = new ArrayList<SelectItem>();
            stmtTypeList.add(new SelectItem("0", "WORKING STATEMENT FOR LOANS"));
            stmtTypeList.add(new SelectItem("1", "ABSTRACT WORKING STATEMENT FOR LOANS"));

            List acTLst = new ArrayList();
            acTLst = loanFacade.getAdvancesAccList();
            selectAcTpList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector vec7 = (Vector) acTLst.get(i);
                    selectAcTpList.add(new SelectItem(vec7.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void statusAction() {
        if (this.stmtType.equalsIgnoreCase("0")) {
            this.setStatus("");
        } else {
            this.setStatus("none");
        }
    }

    public void btnHtmlAction() {
        try {
            String yearstart = "";
            yearstart = loanFacade.getFindate(ymd.format(this.getFrdt()), this.orgnBrCode);
            setMessage("");
            if (this.frdt == null) {
                setMessage("Please Fill Date.");
            }
            String branchName = "", branchAddress = "", finDt = "";
            Map fillParams = new HashMap();
            try {
                List dataList1 = common.getBranchNameandAddress(orgnBrCode);
                String report = "";
                if (this.getStmtType().equalsIgnoreCase("0")) {
                    report = "Working Statement for Advances";
                } else {
                    report = "Abstract Working Statement for Advances";
                }
                if (dataList1.size() > 0) {
                    branchName = (String) dataList1.get(0);
                    branchAddress = (String) dataList1.get(1);
                }
                fillParams.put("pReportFrDate", yearstart.substring(6) + "/" + yearstart.substring(4, 6) + "/" + yearstart.substring(0, 4));
                fillParams.put("pReportToDate", dmy.format(this.frdt));
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("branchName", branchName);
                fillParams.put("branchAddr", branchAddress);

                if (this.getStmtType().equalsIgnoreCase("0")) {
                    repLoansList = loanFacade.workingStmtAdvancesData(this.getSelectAcType(), ymd.format(this.getFrdt()), branchCode, yearstart);
                    if (repLoansList.isEmpty()) {
                        throw new ApplicationException("Data does not exist !");
                    }
                    new ReportBean().jasperReport("WorkingStatementforAdvances", "text/html",
                            new JRBeanCollectionDataSource(repLoansList), fillParams, "Working Statement for Advances");
                } else {
                    repAbstractLoansList = loanFacade.workingStmtAbstractAdvancesData(ymd.format(this.getFrdt()), branchCode, yearstart);
                    if (repAbstractLoansList.isEmpty()) {
                        throw new ApplicationException("Data does not exist !");
                    }
                    new ReportBean().jasperReport("workingStmtAbstractAdvancesDat", "text/html",
                            new JRBeanCollectionDataSource(repAbstractLoansList), fillParams, "Working Statement for Advances");
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();

                httpSession.setAttribute("ReportName", "Working Statement for Advances");
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            String yearstart = "";
            yearstart = loanFacade.getFindate(ymd.format(this.getFrdt()), this.orgnBrCode);
            setMessage("");
            if (this.frdt == null) {
                setMessage("Please Fill Date.");
            }
            String branchName = "", branchAddress = "", finDt = "";
            Map fillParams = new HashMap();
            try {
                List dataList1 = common.getBranchNameandAddress(orgnBrCode);
                String report = "";
                if (this.getStmtType().equalsIgnoreCase("0")) {
                    report = "Working Statement for Advances";
                } else {
                    report = "Abstract Working Statement for Advances";
                }
                if (dataList1.size() > 0) {
                    branchName = (String) dataList1.get(0);
                    branchAddress = (String) dataList1.get(1);
                }
                fillParams.put("pReportFrDate", yearstart.substring(6) + "/" + yearstart.substring(4, 6) + "/" + yearstart.substring(0, 4));
                fillParams.put("pReportToDate", dmy.format(this.frdt));
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("branchName", branchName);
                fillParams.put("branchAddr", branchAddress);

                if (this.getStmtType().equalsIgnoreCase("0")) {
                    repLoansList = loanFacade.workingStmtAdvancesData(this.getSelectAcType(), ymd.format(this.getFrdt()), branchCode, yearstart);
                    if (repLoansList.isEmpty()) {
                        throw new ApplicationException("Data does not exist !");
                    }
                    new ReportBean().openPdf("Working Statement for Advances_"+ ymd.format(dmy.parse(getTodayDate())), "WorkingStatementforAdvances", new JRBeanCollectionDataSource(repLoansList), fillParams);
                } else {
                    repAbstractLoansList = loanFacade.workingStmtAbstractAdvancesData(ymd.format(this.getFrdt()), branchCode, yearstart);
                    if (repAbstractLoansList.isEmpty()) {
                        throw new ApplicationException("Data does not exist !");
                    }
                    new ReportBean().openPdf("Working Statement for Advances_"+ ymd.format(dmy.parse(getTodayDate())), "workingStmtAbstractAdvancesDat", new JRBeanCollectionDataSource(repAbstractLoansList), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();

                httpSession.setAttribute("ReportName", "Working Statement for Advances");
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
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
