/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.AggregateDepositTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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

/**
 *
 * @author Sudhir
 */
public class AggregateDeposit extends BaseBean {

    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private Date tillDate;
    private String branch;
    private List<SelectItem> branchList;
    private final String jndiHomeName = "GLReportFacade";
    private GLReportFacadeRemote beanRemote = null;
    private CommonReportMethodsRemote common;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
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

    /**
     * Creates a new instance of AggregateDeposit
     */
    public AggregateDeposit() {
        try {
            beanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            req = getRequest();
//            String orgnBrIp = WebUtil.getClientIP(req);
//            InetAddress localhost = InetAddress.getByName(orgnBrIp);
//            orgnBrCode = Init.IP2BR.getBranch(localhost);
            //orgnBrCode = "06";
            //userName="Sudhir";

            List brnLst = new ArrayList();
            brnLst = beanRemote.getBranchCodeListExt(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            setUserName(req.getRemoteUser());
            Date date = new Date();
            setTodayDate(dmyformatter.format(date));
            this.setMessage("");
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void printReport() {
        String bankName = "ALL", bankAddress = "ALL";
        try {
            if (tillDate == null) {
                setMessage("Date is not set!!!");
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            List<AggregateDepositTable> aggregateDeposit = beanRemote.getAggregateDepositReport(ymdFormatter.format(tillDate), branch);
            if (aggregateDeposit.isEmpty()) {
                setMessage("No proper data available!!!");
                return;
            }
            String report = "Aggregate Deposit Report";
            String fdt, tdt;

            if (!this.branch.equalsIgnoreCase("0A")) {
                if (!this.branch.equalsIgnoreCase("0B")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }

            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAdd", bankAddress);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", userName);
            fillParams.put("pTillDate", dmyformatter.format(tillDate));
            fillParams.put("pPrintedDate", dmyformatter.format(tillDate));
            new ReportBean().jasperReport("aggregateDepositReport", "text/html", new JRBeanCollectionDataSource(aggregateDeposit), fillParams, "Aggregate Deposit Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void viewPdfReport() {
         String bankName = "ALL", bankAddress = "ALL";
        try {
            if (tillDate == null) {
                setMessage("Date is not set!!!");
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            List<AggregateDepositTable> aggregateDeposit = beanRemote.getAggregateDepositReport(ymdFormatter.format(tillDate), branch);
            if (aggregateDeposit.isEmpty()) {
                setMessage("No proper data available!!!");
                return;
            }
            String report = "Aggregate Deposit Report";
            String fdt, tdt;

            if (!this.branch.equalsIgnoreCase("0A")) {
                if (!this.branch.equalsIgnoreCase("0B")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }

            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAdd", bankAddress);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", userName);
            fillParams.put("pTillDate", dmyformatter.format(tillDate));
            fillParams.put("pPrintedDate", dmyformatter.format(tillDate));
            new ReportBean().openPdf("AggregateDepositReport_"+ymdFormatter.format(tillDate), "aggregateDepositReport", new JRBeanCollectionDataSource(aggregateDeposit), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public String exitFrm() {
        return "case1";
    }
}
