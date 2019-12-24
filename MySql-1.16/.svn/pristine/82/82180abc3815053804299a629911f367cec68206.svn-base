/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TdPeriodMaturityPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class CustNoWiseTdReceiptDetail extends BaseBean {

    private String message;
    private String custId, newCustId, custName;
    private String toDate;
    private DDSReportFacadeRemote ddsBeanRemote;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public CustNoWiseTdReceiptDetail() {
        try {
            setToDate(dmyFormat.format(new Date()));
            ddsBeanRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void onBlurAccountNumber() {
        setMessage("");
        setNewCustId("");
        try {
            if (custId == null || custId.equalsIgnoreCase("")) {
                setMessage("Please fill Customer Id !");
                return;
            }
            custName = ddsBeanRemote.getCustNameByCustId(this.custId);
            if (custName.equalsIgnoreCase("")) {
                setMessage("Customer Id is invalid !");
                return;
            }
            newCustId = this.custId + " [" + custName + "]";

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void printAction() {
        setMessage("");
        try {
            List<TdPeriodMaturityPojo> resultList = ddsBeanRemote.getCustWiseRecieptDetail(custId, ymdFormat.format(dmyFormat.parse(toDate)));
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            String bankName = "";
            String branchAddress = "";
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List ele = common.getBranchNameandAddress(getOrgnBrCode());
            if (ele.get(0) != null) {
                bankName = ele.get(0).toString();
            }
            if (ele.get(1) != null) {
                branchAddress = ele.get(1).toString();
            }
            String repName = "Details of Deposit Receipt of Sh.OR Smt";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", branchAddress);
            fillParams.put("pStmtPeriod", toDate);
            fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", repName);
            fillParams.put("pCustId", this.custId);
            fillParams.put("pCustName", ddsBeanRemote.getCustNameByCustId(this.custId));
            new ReportBean().jasperReport("CustIdWiseTdReceipt", "text/html",
                    new JRBeanCollectionDataSource(resultList), fillParams, "Details of Deposit Receipt");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void downloadPdf() {
        setMessage("");
        try {
            List<TdPeriodMaturityPojo> resultList = ddsBeanRemote.getCustWiseRecieptDetail(custId, ymdFormat.format(dmyFormat.parse(toDate)));
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            String bankName = "";
            String branchAddress = "";
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List ele = common.getBranchNameandAddress(getOrgnBrCode());
            if (ele.get(0) != null) {
                bankName = ele.get(0).toString();
            }
            if (ele.get(1) != null) {
                branchAddress = ele.get(1).toString();
            }
            String repName = "Details of Deposit Receipt of Sh.OR Smt";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", branchAddress);
            fillParams.put("pStmtPeriod", toDate);
            fillParams.put("pPrintedDate", dmyFormat.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", repName);
            fillParams.put("pCustId", this.custId);
            fillParams.put("pCustName", ddsBeanRemote.getCustNameByCustId(this.custId));
            new ReportBean().openPdf("Details of Deposit Receipt of Sh.OR Smt", "CustIdWiseTdReceipt", new JRBeanCollectionDataSource(resultList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Details of Deposit Receipt");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getNewCustId() {
        return newCustId;
    }

    public void setNewCustId(String newCustId) {
        this.newCustId = newCustId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
