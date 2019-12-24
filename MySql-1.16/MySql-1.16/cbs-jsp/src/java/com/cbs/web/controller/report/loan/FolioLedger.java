/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.pojo.FolioLedgerPojo;
import com.cbs.pojo.FolioStatement;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
 * @author Admin
 */
public class FolioLedger extends BaseBean {

    private String errorMsg;
    private String folioNo;
    private String newFolioNo;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameCommonReportMethods = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    private PostalDetailFacadeRemote postalRemote;
    private DDSReportFacadeRemote ddsRemote;

    public FolioLedger() {
        try {
            this.setFrmDt(dmy.format(date));
            this.setToDt(dmy.format(date));
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommonReportMethods);
            postalRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getNewAccNo() {
        try {
            Validator validate = new Validator();
            if (folioNo.equalsIgnoreCase("")) {
                errorMsg = "Please enter the Folio No";
                return;
            } else if (folioNo.length() < 12) {
                errorMsg = "Please enter the valid Folio No";
                return;
            } else if (!validate.validateStringAllNoSpace(folioNo)) {
                errorMsg = "Please enter the valid Account No";
                return;
            }
            newFolioNo = ftsPostRemote.getNewAccountNumber(this.folioNo);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {

        try {
            if (folioNo == null || folioNo.equalsIgnoreCase("") || folioNo.length() < 12) {
                errorMsg = "Please enter valid folio no";
                return;
            }
            if (newFolioNo == null || newFolioNo.equalsIgnoreCase("") || newFolioNo.length() < 12) {
                errorMsg = "Please enter valid folio no";
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter from date";
                return;
            }

            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please select Proper from date ";
                return;
            }

            if (dmy.parse(frmDt).after(getDate())) {
                errorMsg = "From date should be less than current date !";
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter to date";
                return;
            }

            if (!Validator.validateDate(toDt)) {
                errorMsg = "Please select Proper to date ";
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                errorMsg = "To date should be less than current date !";
                return;
            }

            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);

            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less than to date";
                return;
            }

            Map fillParams = new HashMap();
            String report = "Ledger";
            List<FolioStatement> resultList = ddsRemote.getFolioStatementData(folioNo, fromDt, toDate);
            // List<FolioLedgerPojo> resultList = loanRemote.getFolioLedgerData1(folioNo, fromDt, toDate);
//            for (int i = 0; i < resultList.size(); i++) {
//                FolioLedgerPojo val = resultList.get(i);
//                System.out.println("ord op bal -> " + val.getOrdOpeningBal()+"Spe op Bal->"+val.getSpeOpeningBal()+"Eme Op Bal->" +val.getEmeOpeningBal()+"theft op Bal->"+val.getThrFuOpeningBal());
//            }
            if (!resultList.isEmpty()) {
                String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pStmtPeriod", frmDt + " to " + toDt);
                fillParams.put("pPrintedDate", ymd.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report + ":" + fromDt.substring(0, 4) + "-" + toDate.substring(0, 4));
                fillParams.put("pbankName", s[0] + " " + s[1]);
                //fillParams.put("pbankAddress", s[1]);
                List list = loanRemote.getFolioLedgerHeaderData(folioNo);
                Vector headerVector = (Vector) list.get(0);
                List mwfList = postalRemote.getPrimiumAmtDetails("WL", folioNo);
                Vector mwfv = (Vector) mwfList.get(0);
                fillParams.put("custName", headerVector.get(0).toString());
                fillParams.put("folioNo", this.folioNo);
                fillParams.put("pNo", headerVector.get(3).toString());
                fillParams.put("fatherName", headerVector.get(1).toString());
                fillParams.put("dob", headerVector.get(2).toString());
                fillParams.put("crPbNo", commonRemote.getArea(folioNo));
                fillParams.put("pMwfAmt", Double.parseDouble(mwfv.get(0).toString()));
                double shareMoney = loanRemote.getshareBal(folioNo, toDate);
                fillParams.put("pShareMoney", shareMoney);
            } else {
                setErrorMsg("Data does not exist");
                return;
            }
            new ReportBean().jasperReport("FolioStatement", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewPdfReport() {
        try {
            if (folioNo == null || folioNo.equalsIgnoreCase("") || folioNo.length() < 12) {
                errorMsg = "Please enter valid folio no";
                return;
            }
            if (newFolioNo == null || newFolioNo.equalsIgnoreCase("") || newFolioNo.length() < 12) {
                errorMsg = "Please enter valid folio no";
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter from date";
                return;
            }

            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please select Proper from date ";
                return;
            }

            if (dmy.parse(frmDt).after(getDate())) {
                errorMsg = "From date should be less than current date !";
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter to date";
                return;
            }

            if (!Validator.validateDate(toDt)) {
                errorMsg = "Please select Proper to date ";
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                errorMsg = "To date should be less than current date !";
                return;
            }

            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);

            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less than to date";
                return;
            }

            Map fillParams = new HashMap();
            String report = "Ledger";
            List<FolioStatement> resultList = ddsRemote.getFolioStatementData(folioNo, fromDt, toDate);
            // List<FolioLedgerPojo> resultList = loanRemote.getFolioLedgerData1(folioNo, fromDt, toDate);
//            for (int i = 0; i < resultList.size(); i++) {
//                FolioLedgerPojo val = resultList.get(i);
//                System.out.println("ord op bal -> " + val.getOrdOpeningBal()+"Spe op Bal->"+val.getSpeOpeningBal()+"Eme Op Bal->" +val.getEmeOpeningBal()+"theft op Bal->"+val.getThrFuOpeningBal());
//            }
            if (!resultList.isEmpty()) {
                String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pStmtPeriod", frmDt + " to " + toDt);
                fillParams.put("pPrintedDate", ymd.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report + ":" + fromDt.substring(0, 4) + "-" + toDate.substring(0, 4));
                fillParams.put("pbankName", s[0] + " " + s[1]);
                //fillParams.put("pbankAddress", s[1]);
                List list = loanRemote.getFolioLedgerHeaderData(folioNo);
                Vector headerVector = (Vector) list.get(0);
                List mwfList = postalRemote.getPrimiumAmtDetails("WL", folioNo);
                Vector mwfv = (Vector) mwfList.get(0);
                fillParams.put("custName", headerVector.get(0).toString());
                fillParams.put("folioNo", this.folioNo);
                fillParams.put("pNo", headerVector.get(3).toString());
                fillParams.put("fatherName", headerVector.get(1).toString());
                fillParams.put("dob", headerVector.get(2).toString());
                fillParams.put("crPbNo", commonRemote.getArea(folioNo));
                fillParams.put("pMwfAmt", Double.parseDouble(mwfv.get(0).toString()));
                double shareMoney = loanRemote.getshareBal(folioNo, toDate);
                fillParams.put("pShareMoney", shareMoney);
            } else {
                setErrorMsg("Data does not exist");
                return;
            }
            new ReportBean().openPdf("Folio_Ledger_" + fromDt + " to " + toDate, "FolioStatement", new JRBeanCollectionDataSource(resultList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refreshPage() {
        setErrorMsg("");
        setFolioNo("");
        this.setFrmDt(dmy.format(date));
        this.setToDt(dmy.format(date));
        setNewFolioNo("");
    }

    public String exitPage() {

        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getNewFolioNo() {
        return newFolioNo;
    }

    public void setNewFolioNo(String newFolioNo) {
        this.newFolioNo = newFolioNo;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
