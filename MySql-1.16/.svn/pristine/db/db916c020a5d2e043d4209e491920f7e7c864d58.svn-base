/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.AtmTransactionStatusPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class AtmTransactionStatus extends BaseBean {

    private String errorMsg;
    private String type;
    private List<SelectItem> typeList;
    private String mode;
    private List<SelectItem> modeList;
    private String transaction;
    private List<SelectItem> transactionList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String atmId;
    private List<SelectItem> atmIdList;
    private String status;
    private List<SelectItem> statusList;
    private boolean disbleAtmId;
    private boolean disbleAccountnNo;
    private String AccountNo;
    private String frmDt;
    private String toDt;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private Date date = new Date();
    private boolean flag;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    List<AtmTransactionStatusPojo> reportList = new ArrayList<AtmTransactionStatusPojo>();
    List<AtmTransactionStatusPojo> reportListM = new ArrayList<AtmTransactionStatusPojo>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDisbleAccountnNo() {
        return disbleAccountnNo;
    }

    public void setDisbleAccountnNo(boolean disbleAccountnNo) {
        this.disbleAccountnNo = disbleAccountnNo;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public boolean isDisbleAtmId() {
        return disbleAtmId;
    }

    public void setDisbleAtmId(boolean disbleAtmId) {
        this.disbleAtmId = disbleAtmId;

    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public List<SelectItem> getAtmIdList() {
        return atmIdList;
    }

    public void setAtmIdList(List<SelectItem> atmIdList) {
        this.atmIdList = atmIdList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public List<SelectItem> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<SelectItem> transactionList) {
        this.transactionList = transactionList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String AccountNo) {
        this.AccountNo = AccountNo;
    }

    public AtmTransactionStatus() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            frmDt = dmy.format(date);
            toDt = dmy.format(date);

            List brncode = new ArrayList();
            brncode = common.getAtmBranchList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "--Select--"));
            modeList.add(new SelectItem("All", "All"));
            modeList.add(new SelectItem("AC", "Account Wise"));

            getNormal();
            getNFSIssuer();
            getNfsAcquirer();

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("S", "Success"));
            statusList.add(new SelectItem("F", "Un-Success"));
            statusList.add(new SelectItem("P", "Un-Process"));

            if (AccountNo.equalsIgnoreCase("") || AccountNo.equalsIgnoreCase(null)) {
                this.setAccountNo("");
            }

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getNormal() {
        transactionList = new ArrayList<SelectItem>();
        transactionList.add(new SelectItem("0", "--Select--"));
        transactionList.add(new SelectItem("00", "Cash Withdrawal"));
        transactionList.add(new SelectItem("30", "Balance Inquiry"));
        transactionList.add(new SelectItem("36", "Mini Statement"));
        transactionList.add(new SelectItem("00N", "Reversal"));

    }

    public void getNFSIssuer() {
        transactionList = new ArrayList<SelectItem>();
        transactionList.add(new SelectItem("0", "--Select--"));
        transactionList.add(new SelectItem("01", "Cash Withdrawal"));
        transactionList.add(new SelectItem("32", "Balance Inquiry"));
        transactionList.add(new SelectItem("37", "Mini Statement"));
        transactionList.add(new SelectItem("01NF", "Reversal"));
    }

    public void getNfsAcquirer() {
        transactionList = new ArrayList<SelectItem>();
        transactionList.add(new SelectItem("", "--Select--"));
        transactionList.add(new SelectItem("0", "Cash Withdrawal"));
        transactionList.add(new SelectItem("1", "Reversal"));
    }
    
      

    public void getAtmIdVal() {
        atmIdList = new ArrayList<SelectItem>();
        try {
            atmIdList.add(new SelectItem("S", "--Select--"));
            List list = common.getAtmId(branchCode);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    atmIdList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void optionsMode() {
        try {
               typeList = new ArrayList<SelectItem>();
            
                if (this.mode.equals("All")) {
                this.setType("0");
                this.setAccountNo("");
                typeList = new ArrayList<SelectItem>();
                typeList.add(new SelectItem("0", "--Select--"));
                typeList.add(new SelectItem("Normal", "Normal"));
                typeList.add(new SelectItem("NFS Issuer", "NFS Issuer"));
                typeList.add(new SelectItem("POS", "POS"));
                typeList.add(new SelectItem("NFS Acquirer", "NFS Acquirer"));
                typeList.add(new SelectItem("ECOM", "ECOM"));
                
                this.setDisbleAccountnNo(true);
                
              } else if (this.mode.equalsIgnoreCase("AC")) {
                 this.setDisbleAccountnNo(false); 
                typeList = new ArrayList<SelectItem>();
                typeList.add(new SelectItem("0", "--Select--"));
                typeList.add(new SelectItem("Normal", "Normal"));
                typeList.add(new SelectItem("NFS Issuer", "NFS Issuer"));
                typeList.add(new SelectItem("POS", "POS"));
                typeList.add(new SelectItem("ECOM", "ECOM"));
           
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void options() {
        if (this.type.equals("Normal")) {
            this.getNormal();
            setDisbleAtmId(false);
//            if(transaction.equalsIgnoreCase("30")||transaction.equalsIgnoreCase("31")||transaction.equalsIgnoreCase("35")||transaction.equalsIgnoreCase("36")){    
//            }
            getAtmIdVal();
            this.flag = true;
        } else if (this.type.equals("NFS Issuer")) {
            this.getNFSIssuer();
            this.setAtmId("--Select--");
            setDisbleAtmId(true);
            this.flag = true;
        } else if (this.type.equals("NFS Acquirer")) {
            
                this.getNfsAcquirer();
                setDisbleAtmId(false);
                getAtmIdVal();
                this.flag = true;
            
        } else if (this.type.equals("POS")) {
            transactionList = new ArrayList<SelectItem>();
            transactionList.add(new SelectItem("", "--Select--"));
            transactionList.add(new SelectItem("Withdrawal", "Cash Withdrawal"));
             transactionList.add(new SelectItem("31", "Balance Inquiry"));
              transactionList.add(new SelectItem("35", "Mini Statement"));
               transactionList.add(new SelectItem("Reversal", "Reversal"));
             
            this.setAtmId("--Select--");
            setDisbleAtmId(true);
            this.flag = true;
        }
        else if(this.type.equals("ECOM")){
         transactionList = new ArrayList<SelectItem>();
          transactionList.add(new SelectItem("", "--Select--"));
        transactionList.add(new SelectItem("49", "Cash Withdrawal"));
        transactionList.add(new SelectItem("Reversal", "Reversal"));
        
        this.setAtmId("--Select--");
            setDisbleAtmId(true);
            this.flag = true;
        }
        else {
            this.flag = false;
            setErrorMsg("");
        }
    }

    public boolean validateAccountField(String AccountNo) {
        try {
            if (AccountNo == null || AccountNo.equalsIgnoreCase("")) {
                this.setErrorMsg("Account number should not be blank.");
                return false;
            }

            if (!(AccountNo.length() == 12)) {
                this.setErrorMsg("Please fill proper Account Number.");
                return false;
            }
            if (!AccountNo.matches("[0-9]+")) {
                this.setErrorMsg("Invalid AcccountNo");
                return false;
            }
            String newAcno = "";
            newAcno = ftsRemote.getNewAccountNumber(this.AccountNo);
            if (newAcno == null) {
                this.setErrorMsg(newAcno);
                return false;
            }

            String alphaCode = common.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO") && !getOrgnBrCode().equalsIgnoreCase(common.getBrncodeByAcno(newAcno))) {
                this.setErrorMsg("Please fill your branch account no.");
                return false;
            }
//            if (!miscRemoteS1.isPrimaryTypeAccount(newAcno)) {
//                this.setErrorMsg("Please fill your branch primary account number !");
//                return false ;
//            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
            return false;
        }
        return true;

    }

    public void viewReport() {
        String brnName = "";
        String brnAddress = "";
        try {
            if (atmId.equalsIgnoreCase("S")) {
                setErrorMsg("Please select Atm Id!");
                return;
            }
            if (mode.equalsIgnoreCase("AC")) {
                if (!validateAccountField(AccountNo)) {
                    return;
                }
            }

            if (type.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Type!");
                return;
            }
            if (transaction.equalsIgnoreCase("")) {
                setErrorMsg("Please select Transaction!");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (status.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Status");
                return;
            }
            String fromDatet = ymd.format(dmy.parse(this.frmDt));
            String toDate = ymd.format(dmy.parse(this.toDt));
            String report = "ATM Transaction Status Report";
            List brnnamead = new ArrayList();
            brnnamead = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brnnamead.size() > 0) {
                brnName = (String) brnnamead.get(0);
                brnAddress = (String) brnnamead.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            String statusDesc = this.status.equalsIgnoreCase("s") ? "Success" : this.status.equalsIgnoreCase("F") ? "Un-Success"
                    : this.status.equalsIgnoreCase("P") ? "Un-Process" : "";

            reportList = otherFacade.getAtmTransactionStatus(type, transaction, branchCode, fromDatet, toDate, atmId, status, mode, AccountNo);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits.");
            }

            if (type.equals("Normal") && transaction.equals("00") || type.equals("NFS Issuer") && transaction.equals("01")) {
                fillParams.put("pReportType", type + ", " + "Cash Withdrawal" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionIssuerWithdrawal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("30") || transaction.equalsIgnoreCase("36")) {
                fillParams.put("pReportType", type + ", " + "Balance Inquery" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionBalanceInquiry", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("32") || transaction.equalsIgnoreCase("37")) {
                fillParams.put("pReportType", type + ", " + "Mini Statement" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionBalanceInquiry", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
                fillParams.put("pReportType", type + ", " + "Reversal" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionIssuerReversal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("0")) {
                fillParams.put("pReportType", type + ", " + "Cash Withdrawal" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionAcquirerWithdrawal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("1")) {
                fillParams.put("pReportType", type + ", " + "Reversal" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionAcquirerReversal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equals("POS") && transaction.equalsIgnoreCase("Withdrawal")) {
                fillParams.put("pReportType", type + ", " + transaction + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionIssuerWithdrawal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            } else if (type.equals("POS") && transaction.equalsIgnoreCase("Reversal")) {
                fillParams.put("pReportType", type + ", " + transaction + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionIssuerReversal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            }
           else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("31") || transaction.equalsIgnoreCase("35")) {
                fillParams.put("pReportType", type + ", " + "Mini Statement" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionBalanceInquiry", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            }
           else  if (type.equals("ECOM") && transaction.equals("49")){
                fillParams.put("pReportType", type + ", " + "Cash Withdrawal" + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionIssuerWithdrawal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");}
            
            else if (type.equals("ECOM") && transaction.equalsIgnoreCase("Reversal")) {
                fillParams.put("pReportType", type + ", " + transaction + ", " + statusDesc);
                new ReportBean().jasperReport("ATMTransactionIssuerReversal", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "ATM Transaction Status Report");
            }
            
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            //e.printStackTrace();
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

    public void btnPdfAction() {
        String brnName = "";
        String brnAddress = "";

        try {
            if (atmId.equalsIgnoreCase("S")) {
                setErrorMsg("Please select Atm Id!");
                return;
            }
            if (mode.equalsIgnoreCase("AC")) {
                if (!validateAccountField(AccountNo)) {
                    return;
                }
            }
            if (type.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Type!");
                return;
            }
            if (transaction.equalsIgnoreCase("")) {
                setErrorMsg("Please select Transaction!");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (status.equalsIgnoreCase("0")) {
                setErrorMsg("Please Select Status");
                return;
            }
            String fromDatet = ymd.format(dmy.parse(this.frmDt));
            String toDate = ymd.format(dmy.parse(this.toDt));
            String report = "ATM Transaction Status Report";
            List brnnamead = new ArrayList();
            brnnamead = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brnnamead.size() > 0) {
                brnName = (String) brnnamead.get(0);
                brnAddress = (String) brnnamead.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            String statusDesc = this.status.equalsIgnoreCase("s") ? "Success" : this.status.equalsIgnoreCase("F") ? "Un-Success"
                    : this.status.equalsIgnoreCase("P") ? "Un-Process" : "";

         reportList = otherFacade.getAtmTransactionStatus(type, transaction, branchCode, fromDatet, toDate, atmId, status, mode, AccountNo);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exit.");
            }

            if (type.equals("Normal") && transaction.equals("00") || type.equals("NFS Issuer") && transaction.equals("01")) {
                fillParams.put("pReportType", type + ", " + "Cash Withdrawal" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionIssuerWithdrawal_" + fromDatet + " to " + toDate, "ATMTransactionIssuerWithdrawal", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("30") || transaction.equalsIgnoreCase("36")) {
                fillParams.put("pReportType", type + ", " + "Balance Inquery" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionBalanceInquiry_" + fromDatet + " to " + toDate, "ATMTransactionBalanceInquiry", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("32") || transaction.equalsIgnoreCase("37")) {
                fillParams.put("pReportType", type + ", " + "Mini Statement" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionBalanceInquiry_" + fromDatet + " to " + toDate, "ATMTransactionBalanceInquiry", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
                fillParams.put("pReportType", type + ", " + "Reversal" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionIssuerReversal_" + fromDatet + " to " + toDate, "ATMTransactionIssuerReversal", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("0")) {
                fillParams.put("pReportType", type + ", " + "Cash Withdrawal" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionAcquirerWithdrawal_" + fromDatet + " to " + toDate, "ATMTransactionAcquirerWithdrawal", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("1")) {
                fillParams.put("pReportType", type + ", " + "Reversal" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionAcquirerReversal_" + fromDatet + " to " + toDate, "ATMTransactionAcquirerReversal", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equals("POS") && transaction.equalsIgnoreCase("Withdrawal")) {
                fillParams.put("pReportType", type + ", " + transaction + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionIssuerWithdrawal_" + fromDatet + " to " + toDate, "ATMTransactionIssuerWithdrawal", new JRBeanCollectionDataSource(reportList), fillParams);
            } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("31") || transaction.equalsIgnoreCase("35")) {
                fillParams.put("pReportType", type + ", " + "Balance Inquery" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionBalanceInquiry_" + fromDatet + " to " + toDate, "ATMTransactionBalanceInquiry", new JRBeanCollectionDataSource(reportList), fillParams);
            } 
            else if (type.equals("POS") && transaction.equalsIgnoreCase("Reversal")) {
                fillParams.put("pReportType", type + ", " + transaction + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionIssuerReversal_" + fromDatet + " to " + toDate, "ATMTransactionIssuerReversal", new JRBeanCollectionDataSource(reportList), fillParams);
            }
            else  if (type.equals("ECOM") && transaction.equals("49")) {
                fillParams.put("pReportType", type + ", " + "Cash Withdrawal" + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionIssuerWithdrawal_" + fromDatet + " to " + toDate, "ATMTransactionIssuerWithdrawal", new JRBeanCollectionDataSource(reportList), fillParams);
             }
             else if (type.equals("ECOM") && transaction.equalsIgnoreCase("Reversal")) {
                fillParams.put("pReportType", type + ", " + transaction + ", " + statusDesc);
                new ReportBean().openPdf("ATMTransactionIssuerReversal_" + fromDatet + " to " + toDate, "ATMTransactionIssuerReversal", new JRBeanCollectionDataSource(reportList), fillParams);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            e.printStackTrace();
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setFrmDt(dmy.format(date));
        this.setToDt(dmy.format(date));
        this.typeList = null;
        this.setType("0");
        this.setTransaction("--Select--");
        this.setStatus("0");
        this.setAtmId("S");
        this.setAccountNo("");
        this.setMode("0");
    }

    public String exitAction() {
        return "case1";
    }
}
