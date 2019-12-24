/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.npci;

import com.cbs.dto.report.AccwiesECSACHReportPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AccwiesECSACHReport extends BaseBean {

    private String message;
    private String fileType;
    private String txnType;
    private String fromDate;
    private String toDate;
    private String accountNo;
    private String acNoLen;
    private String newAccountNo;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> txnTypeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private NpciMgmtFacadeRemote npciRemoteBene;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private List<AccwiesECSACHReportPojo> resultList;
    private final static String REPORT_NAME = "Account Wise ACH/ECS Status Report";

    public AccwiesECSACHReport() {
        try {
            npciRemoteBene = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            fileTypeList = new ArrayList<SelectItem>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            fileTypeList.add(new SelectItem("ACH", "ACH"));
            fileTypeList.add(new SelectItem("ECS", "ECS"));

            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("0", "--Select--"));
            txnTypeList.add(new SelectItem("CREDIT", "CREDIT"));
            txnTypeList.add(new SelectItem("DEBIT", "DEBIT"));

            this.setAcNoLen(getAcNoLength());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            String isValid = validateField();
            if (!isValid.equalsIgnoreCase("valid")) {
                this.setMessage(isValid);
                return;
            }

            String sponsorType = "";
            if (this.fileType.equalsIgnoreCase("ACH")) {
                sponsorType = "AINPSD";
            } else if (this.fileType.equalsIgnoreCase("ECS")) {
                sponsorType = "EINPSD";
            }

            resultList = npciRemoteBene.getAccwiesACHECSReportData(fileType, sponsorType, txnType, ymd.format(dmy.parse(fromDate)),
                    ymd.format(dmy.parse(toDate)), newAccountNo);
            if (resultList.isEmpty()) {
                this.setMessage("There is not data to print !");
                return;
            }

            Map fillParams = new HashMap();
            fillParams.put("pReportName", REPORT_NAME);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pReportType", fileType + "/" + txnType);
            fillParams.put("pbankName", common.getBankName());
            fillParams.put("pDateRange", fromDate + " - " + toDate);
            fillParams.put("pAccountNo", accountNo);

            new ReportBean().jasperReport("accwisenpcistatusreport", "text/html", new JRBeanCollectionDataSource(resultList), 
                    fillParams, REPORT_NAME);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", REPORT_NAME);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setMessage("");
        try {
            String isValid = validateField();
            if (!isValid.equalsIgnoreCase("valid")) {
                this.setMessage(isValid);
                return;
            }

            String sponsorType = "";
            if (this.fileType.equalsIgnoreCase("ACH")) {
                sponsorType = "AINPSD";
            } else if (this.fileType.equalsIgnoreCase("ECS")) {
                sponsorType = "EINPSD";
            }

            resultList = npciRemoteBene.getAccwiesACHECSReportData(fileType, sponsorType, txnType, ymd.format(dmy.parse(fromDate)),
                    ymd.format(dmy.parse(toDate)), newAccountNo);
            if (resultList.isEmpty()) {
                this.setMessage("There is not data to print !");
                return;
            }

            Map fillParams = new HashMap();
            fillParams.put("pReportName", REPORT_NAME);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", fileType + "/" + txnType);
            fillParams.put("pbankName", common.getBankName());
            fillParams.put("pDateRange", fromDate + " - " + toDate);
            fillParams.put("pAccountNo", accountNo);

            new ReportBean().openPdf("accwisenpcistatusreport", "accwisenpcistatusreport", new JRBeanCollectionDataSource(resultList), 
                    fillParams);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", REPORT_NAME);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateField() {
        try {
            if (this.fileType == null || this.fileType.equalsIgnoreCase("0")) {
                return "Please select File Type !";
            }
            if (this.txnType == null || this.txnType.equals("0")) {
                return "Please select Txn Type !";
            }
            if (this.accountNo == null || this.accountNo.equals("")) {
                return "Please fill Account No !";
            }
            newAccountNo = ftsRemote.getNewAccountNumber(accountNo);
            if (newAccountNo == null || newAccountNo.equalsIgnoreCase("")) {
                return "This account no not found !";
            }
            String alphaCode = common.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO") && !common.getBrncodeByAcno(newAccountNo).equalsIgnoreCase(getOrgnBrCode())) {
                return "Please fill your branch account no. !";
            }

            if (this.fromDate == null || this.fromDate.equals("")) {
                return "Please Fill From date !";
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fromDate)) {
                return "Please Fill Proper From Date !";
            }
            if (this.toDate == null || this.toDate.equals("")) {
                return "Please Fill To Date !";
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.toDate)) {
                return "Please Fill Proper To Date !";
            }
            if (dmy.parse(this.fromDate).compareTo(dmy.parse(this.toDate)) > 0) {
                return "From Date can not be greater than To Date !";
            }
            if (dmy.parse(this.toDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                return "To Date can not be greater than Current Date !";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "valid";
    }

    public void btnRefreshAction() {
        this.message = "";
        this.fileType = "0";
        this.txnType = "0";
        this.accountNo = "";
        this.fromDate = getTodayDate();
        this.toDate = getTodayDate();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
    //Getter And Setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNewAccountNo() {
        return newAccountNo;
    }

    public void setNewAccountNo(String newAccountNo) {
        this.newAccountNo = newAccountNo;
    }
}
