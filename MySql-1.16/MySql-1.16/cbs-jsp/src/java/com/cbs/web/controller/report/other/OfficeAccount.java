/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.pojo.OfficeAccountPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class OfficeAccount extends BaseBean {

    private String message;
    private String brAccountNo;
    private String reportDt;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "NpaReportFacade";
    private NpaReportFacadeRemote remote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameTxnAuth = "TransactionManagementFacade";
    private FtsPostingMgmtFacadeRemote ftsFacade = null;
    private final String ftsHome = "FtsPostingMgmtFacade";

    public String getBrAccountNo() {
        return brAccountNo;
    }

    public void setBrAccountNo(String brAccountNo) {
        this.brAccountNo = brAccountNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    /** Creates a new instance of OfficeAccount */
    public OfficeAccount() {
        this.setMessage("");
        this.brAccountNo = "";
        try {
            remote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxnAuth);
            ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(ftsHome);
            this.setReportDt(getTodayDate());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.brAccountNo == null || this.brAccountNo.equalsIgnoreCase("")) {
                this.setMessage("Please fill Account No.");
                return;
            }
            if (this.brAccountNo.length() < 12) {
                this.setMessage("Please fill account no of 12 digit");
                return;
            }
            if (!ftsFacade.getCurrentBrnCode(this.brAccountNo).equalsIgnoreCase(getOrgnBrCode())) {
                this.setMessage("Please fill self branch account number.");
                return;
            }

            String acnoMsg = txnRemote.checkForAccountNo(this.brAccountNo);
            if (!acnoMsg.equalsIgnoreCase("true")) {
                this.setMessage(acnoMsg);
                return;
            }
            if (this.reportDt == null || this.reportDt.equals("")) {
                this.setMessage("Please select report date.");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.reportDt);
            if (result == false) {
                this.setMessage("Please select proper report date.");
                return;
            }
            List<OfficeAccountPojo> resultList = remote.getOfficeAccountTransaction(this.brAccountNo, ymd.format(dmy.parse(reportDt)), getOrgnBrCode());
            if (!resultList.isEmpty()) {
                String reportName = "Office Account Report";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pPrintedDate", this.reportDt);

                new ReportBean().jasperReport("officeAccount", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String btnExitAction() {
        this.brAccountNo = "";
        return "case1";
    }
}
