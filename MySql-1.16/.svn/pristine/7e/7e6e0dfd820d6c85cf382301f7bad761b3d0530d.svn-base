/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.GLDetailsTable;
import com.cbs.dto.report.GenReportLedgerTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
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
 * @author Sudhir
 */
public class AllLedgerAndSubsidiary extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String message;
    private Date tillDate;
    private String reportType,
            gltype,
            account;
    private List<SelectItem> reportTypeList = new ArrayList<SelectItem>(),
            glTypeList = new ArrayList<SelectItem>(),
            accountList = new ArrayList<SelectItem>();
    private final String glReporfacadejndiName = "GLReportFacade";
    private final String commonReportFacadejndiName = "CommonReportMethods";
    private GLReportFacadeRemote glfacadeBeanRemote = null;
    private CommonReportMethodsRemote commonFacadeBeanRemote = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;
    private Date fromDate,
            toDate;
    private String txnMode;
    private List<SelectItem> txnModeList;
    public String opBal;

    public String getOpBal() {
        return opBal;
    }
    public void setOpBal(String opBal) {
        this.opBal = opBal;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<SelectItem> accountList) {
        this.accountList = accountList;
    }

    public List<SelectItem> getGlTypeList() {
        return glTypeList;
    }

    public void setGlTypeList(List<SelectItem> glTypeList) {
        this.glTypeList = glTypeList;
    }

    public String getGltype() {
        return gltype;
    }

    public void setGltype(String gltype) {
        this.gltype = gltype;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
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

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public String getTxnMode() {
        return txnMode;
    }

    public void setTxnMode(String txnMode) {
        this.txnMode = txnMode;
    }

    public List<SelectItem> getTxnModeList() {
        return txnModeList;
    }

    public void setTxnModeList(List<SelectItem> txnModeList) {
        this.txnModeList = txnModeList;
    }

    /**
     * Creates a new instance of AllLedgerAndSubsidiary
     */
    public AllLedgerAndSubsidiary() {
        try {
            glfacadeBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(glReporfacadejndiName);
            commonFacadeBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonReportFacadejndiName);
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            this.setMessage("");
            onloadData();
            txnModeList = new ArrayList<>();
            txnModeList.add(new SelectItem("ALL", "ALL"));
            txnModeList.add(new SelectItem("0", "Cash"));
            txnModeList.add(new SelectItem("1", "Clearing"));
            txnModeList.add(new SelectItem("2", "Transfer"));
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void onloadData() {
        reportTypeList.add(new SelectItem("0", "--SELECT--"));
        reportTypeList.add(new SelectItem("1", "GENERAL LEDGER REPORT"));
        reportTypeList.add(new SelectItem("2", "SUBSIDIARY BOOK STATEMENT"));

        glTypeList.add(new SelectItem("0", "--SELECT--"));
        glTypeList.add(new SelectItem("1", "INCOME"));
        glTypeList.add(new SelectItem("2", "BANKER-CA WITH OTHER BANK"));
        glTypeList.add(new SelectItem("3", "BANKER-FD WITH OTHER BANK"));
        glTypeList.add(new SelectItem("4", "OTHERS GL HEAD"));
        glTypeList.add(new SelectItem("ACTYPE", "A/C TYPE"));
        glTypeList.add(new SelectItem("5", "EXPENDITURE"));

        accountList.add(new SelectItem("0", "--SELECT--"));
    }

    public void glTypeOnchange() {
        try {
            setMessage("");
            flag = "true";
            accountList = new ArrayList<SelectItem>();
            accountList.add(new SelectItem("0", "--SELECT--"));
            List<GLDetailsTable> glheadList = glfacadeBeanRemote.getGLtypeHeads(gltype, branchCode);
            if (!glheadList.isEmpty()) {
                for (int i = 0; i < glheadList.size(); i++) {
                    GLDetailsTable gLDetailsTable = glheadList.get(i);
                    accountList.add(new SelectItem(gLDetailsTable.getAcno(), gLDetailsTable.getAcname()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void excelReport() {
        try {
            String valdationResult = Validtions();
            if (!valdationResult.equalsIgnoreCase("true")) {
                setMessage(valdationResult);
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            String report = "";
            String acno1 = account;
            String acno = null;
            String acname = null;
            Map fillParams = new HashMap();
            List<GenReportLedgerTable> reportLedgerList = new ArrayList<GenReportLedgerTable>();
            if (reportType.equalsIgnoreCase("1")) {
                report = "General Ledger Report";
                if (gltype.equalsIgnoreCase("ACTYPE")) {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        acno = acno1.substring(2, 10);
                        acname = acno1.substring(0, 2);
                    } else {
                        acno = acno1.substring(2, 14);
                        acname = acno1.substring(0, 2);
                    }
                    reportLedgerList = glfacadeBeanRemote.genRepAllLedger(gltype, account, "", ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                } else {
                    List tempList;
                    acno = acno1;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    }
                    if (!tempList.isEmpty()) {
                        Vector recLst = (Vector) tempList.get(0);
                        acname = recLst.get(0).toString();
                    }
                    reportLedgerList = glfacadeBeanRemote.genRepAllLedger("", "", account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                }
            }
            if (reportType.equalsIgnoreCase("2")) {
                report = "SUBSIDIARY BOOKS STATEMENT";
                if (gltype.equalsIgnoreCase("ACTYPE")) {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        acno = acno1.substring(2, 10);
                        acname = acno1.substring(0, 2);
                    } else {
                        acno = acno1.substring(2, 14);
                        acname = acno1.substring(0, 2);
                    }
                    reportLedgerList = glfacadeBeanRemote.subsidiaryBookStatement(gltype, account, "", ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                } else {
                    acno = acno1;
                    List tempList = new ArrayList();
                    if (branchCode.equalsIgnoreCase("0A")) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    }
                    if (!tempList.isEmpty()) {
                        Vector recLst = (Vector) tempList.get(0);
                        acname = recLst.get(0).toString();
                    }
                    reportLedgerList = glfacadeBeanRemote.subsidiaryBookStatement("", "", account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                }
            }
            List bankdetails = commonFacadeBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankdetails.isEmpty()) {
                setMessage("Bank details is not present!!!");
                flag = "false";
                return;
            }
            String bankName = bankdetails.get(0).toString();
            String bankAddress = bankdetails.get(1).toString();
            if (reportLedgerList.isEmpty()) {
                flag = "false";
                setMessage("No data available for the input parameters entered!!!");
                return;
            } else {
                for (int i = 0; i < reportLedgerList.size(); i++) {
                    GenReportLedgerTable genReportLedgerTable = reportLedgerList.get(i);
                    genReportLedgerTable.setBankName(bankName);
                    genReportLedgerTable.setBankAddress(bankAddress);
                    setOpBal(String.valueOf(genReportLedgerTable.getOpBal()));
                }
            }
            FastReportBuilder fastReportBuilder = genrateDaynamicReport();
            String reportName = "";
            if (reportType.equalsIgnoreCase("1")) {
                reportName = "General Ledger Report";
            } else {
                reportName = "SUBSIDIARY BOOKS STATEMENT";
            }
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportLedgerList), fastReportBuilder, reportName);
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }
    
    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);            
            AbstractColumn date = ReportBean.getColumn("date", String.class, "Txn Date [ValueDate] ", 100);
//            AbstractColumn tranTime = ReportBean.getColumn("tranTime", String.class, "Txn Time", 60);
            AbstractColumn particular = ReportBean.getColumn("particular", String.class, "Particulars", 500);
            AbstractColumn debit = ReportBean.getColumn("debit", BigDecimal.class, "Debit", 150);
            AbstractColumn credit = ReportBean.getColumn("credit", BigDecimal.class, "Credit", 150);
            AbstractColumn balance = ReportBean.getColumn("balance", BigDecimal.class, "Balance", 200);
            AbstractColumn originBranch = ReportBean.getColumn("originBranch", String.class, "Origin Branch", 0);
            AbstractColumn destBranch = ReportBean.getColumn("destBranch", String.class, "Destination Branch", 0);
            if(reportType.equalsIgnoreCase("2")) {
                originBranch = ReportBean.getColumn("originBranch", String.class, "Origin Branch", 90);
                destBranch = ReportBean.getColumn("destBranch", String.class, "Destination Branch", 90);
            }
            
            fastReport.addColumn(date);
            width = width + date.getWidth();
            
            fastReport.addColumn(particular);
            width = width + particular.getWidth();
            
            fastReport.addColumn(debit);
            width = width + debit.getWidth();
            
            fastReport.addColumn(credit);
            width = width + credit.getWidth();
            
            fastReport.addColumn(balance);
            width = width + balance.getWidth();
            
            if(reportType.equalsIgnoreCase("2")) {
                fastReport.addColumn(originBranch);
                width = width + originBranch.getWidth();
                
                fastReport.addColumn(destBranch);
                width = width + destBranch.getWidth();
            }
            
            Page page = new Page(600, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            if (reportType.equalsIgnoreCase("1")) {
                fastReport.setTitle("General Ledger Report ");
            } else {
                fastReport.setTitle("SUBSIDIARY BOOKS STATEMENT " );
            }
            String acno1 = account;
            String acno = null;
            String acname = null;
            if (gltype.equalsIgnoreCase("ACTYPE")) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    acno = acno1.substring(2, 10);
                    acname = acno1.substring(0, 2);
                } else {
                    acno = acno1.substring(2, 14);
                    acname = acno1.substring(0, 2);
                }
            } else {
                acno = acno1;
                List tempList = new ArrayList();
                if (branchCode.equalsIgnoreCase("0A")) {
                    tempList = commonFacadeBeanRemote.getGlNames(acno1);
                } else {
                    tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                }
                if (!tempList.isEmpty()) {
                    Vector recLst = (Vector) tempList.get(0);
                    acname = recLst.get(0).toString();
                }
            }
            if (txnMode.equalsIgnoreCase("0")) {
                fastReport.setSubtitle("Account Number: "+acno +" and Account Type: "+ acname +" and Transaction Mode: Cash and Opening Balance : "+getOpBal());
            } else if (txnMode.equalsIgnoreCase("1")) {
                fastReport.setSubtitle("Account Number: "+acno +" and Account Type: "+ acname +" and Transaction Mode: Clearing and Opening Balance : "+getOpBal());
            } else if (txnMode.equalsIgnoreCase("2")) {
                fastReport.setSubtitle("Account Number: "+acno +" and Account Type: "+ acname +" and Transaction Mode: Transfer and Opening Balance : "+getOpBal());
            } else {
                fastReport.setSubtitle("Account Number: "+acno +" and Account Type: "+ acname +" and Transaction Mode: ALL and Opening Balance : "+getOpBal());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void printReport() {
        try {
            String valdationResult = Validtions();
            if (!valdationResult.equalsIgnoreCase("true")) {
                setMessage(valdationResult);
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            String report = "";
            String acno1 = account;
            String acno = null;
            String acname = null;
            Map fillParams = new HashMap();
            List<GenReportLedgerTable> reportLedgerList = new ArrayList<GenReportLedgerTable>();
            if (reportType.equalsIgnoreCase("1")) {
                report = "General Ledger Report";
                if (gltype.equalsIgnoreCase("ACTYPE")) {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        acno = acno1.substring(2, 10);;
                        acname = acno1.substring(0, 2);
                    } else {
                        acno = acno1.substring(2, 14);
                        acname = acno1.substring(0, 2);
                    }

                    reportLedgerList = glfacadeBeanRemote.genRepAllLedger(gltype, account, "", ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                } else {
                    List tempList;
                    acno = acno1;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    }

                    if (!tempList.isEmpty()) {
                        Vector recLst = (Vector) tempList.get(0);
                        acname = recLst.get(0).toString();
                    }
                    reportLedgerList = glfacadeBeanRemote.genRepAllLedger("", "", account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                }
            }
            if (reportType.equalsIgnoreCase("2")) {
                report = "SUBSIDIARY BOOKS STATEMENT";
                if (gltype.equalsIgnoreCase("ACTYPE")) {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        acno = acno1.substring(2, 10);;
                        acname = acno1.substring(0, 2);
                    } else {
                        acno = acno1.substring(2, 14);
                        acname = acno1.substring(0, 2);
                    }
                    reportLedgerList = glfacadeBeanRemote.subsidiaryBookStatement(gltype, account, "", ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                } else {
                    acno = acno1;
                    List tempList = new ArrayList();
                    if (branchCode.equalsIgnoreCase("0A")) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    }
                    if (!tempList.isEmpty()) {
                        Vector recLst = (Vector) tempList.get(0);
                        acname = recLst.get(0).toString();
                    }
                    reportLedgerList = glfacadeBeanRemote.subsidiaryBookStatement("", "", account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                }
            }
            List bankdetails = commonFacadeBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankdetails.isEmpty()) {
                setMessage("Bank details is not present!!!");
                flag = "false";
                return;
            }
            String bankName = bankdetails.get(0).toString();
            String bankAddress = bankdetails.get(1).toString();
            if (reportLedgerList.isEmpty()) {
                flag = "false";
                setMessage("No data available for the input parameters entered!!!");
                return;
            } else {
                for (int i = 0; i < reportLedgerList.size(); i++) {
                    GenReportLedgerTable genReportLedgerTable = reportLedgerList.get(i);
                    genReportLedgerTable.setBankName(bankName);
                    genReportLedgerTable.setBankAddress(bankAddress);
                }
            }
            fillParams.put("dd-MMM-yyyy", dmyformatter.format(fromDate));
            fillParams.put("pPrintedDate", dmyformatter.format(fromDate) + " to " + dmyformatter.format(toDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pAcNo", acno);
            fillParams.put("pAcName", acname);
            if (txnMode.equalsIgnoreCase("0")) {
                fillParams.put("pTxnMode", "Cash");
            } else if (txnMode.equalsIgnoreCase("1")) {
                fillParams.put("pTxnMode", "Clearing");
            } else if (txnMode.equalsIgnoreCase("2")) {
                fillParams.put("pTxnMode", "Transfer");
            } else {
                fillParams.put("pTxnMode", "ALL");
            }

            if (reportType.equalsIgnoreCase("1")) {
                new ReportBean().jasperReport("AllLedgerAccountType", "text/html", new JRBeanCollectionDataSource(reportLedgerList), fillParams, "General Ledger Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
            if (reportType.equalsIgnoreCase("2")) {
                new ReportBean().jasperReport("SubsidiaryBookStatement", "text/html", new JRBeanCollectionDataSource(reportLedgerList), fillParams, "General Ledger Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public String Validtions() {
        try {
            if (reportType.equalsIgnoreCase("0")) {
                return "Report type is not selected!!!";
            }
            if (gltype.equalsIgnoreCase("0")) {
                return "GL type is not selected!!!";
            }
            if (account.equalsIgnoreCase("0")) {
                return "Accounts type is not selected!!!";
            }
            if (fromDate == null) {
                return "From date is not set!!!";
            }
            if (toDate == null) {
                return "To date is not set!!!";
            }
        } catch (Exception e) {
            return "Error occurred during field validations!!!";
        }
        return "true";
    }

    public void printPDF() {
        try {
            String valdationResult = Validtions();
            if (!valdationResult.equalsIgnoreCase("true")) {
                setMessage(valdationResult);
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            String report = "";
            String acno1 = account;
            String acno = null;
            String acname = null;
            Map fillParams = new HashMap();
            List<GenReportLedgerTable> reportLedgerList = new ArrayList<GenReportLedgerTable>();
            if (reportType.equalsIgnoreCase("1")) {
                report = "General Ledger Report";
                if (gltype.equalsIgnoreCase("ACTYPE")) {
//                    acno = acno1.substring(2, 14);
//                    acname = acno1.substring(0, 2);
                    if (branchCode.equalsIgnoreCase("0A")) {
                        acno = acno1.substring(2, 10);;
                        acname = acno1.substring(0, 2);
                    } else {
                        acno = acno1.substring(2, 14);
                        acname = acno1.substring(0, 2);
                    }
                    reportLedgerList = glfacadeBeanRemote.genRepAllLedger(gltype, account, "", ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                } else {
//                    List tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    acno = acno1;
                    List tempList = new ArrayList();
                    if (branchCode.equalsIgnoreCase("0A")) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    }
                    if (!tempList.isEmpty()) {
                        Vector recLst = (Vector) tempList.get(0);
                        acname = recLst.get(0).toString();
                    }
                    reportLedgerList = glfacadeBeanRemote.genRepAllLedger("", "", account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                }
            }
            if (reportType.equalsIgnoreCase("2")) {
                report = "SUBSIDIARY BOOKS STATEMENT";
                if (gltype.equalsIgnoreCase("ACTYPE")) {
//                    acno = acno1.substring(2, 14);
//                    acname = acno1.substring(0, 2);
                    if (branchCode.equalsIgnoreCase("0A")) {
                        acno = acno1.substring(2, 10);;
                        acname = acno1.substring(0, 2);
                    } else {
                        acno = acno1.substring(2, 14);
                        acname = acno1.substring(0, 2);
                    }
                    reportLedgerList = glfacadeBeanRemote.subsidiaryBookStatement(gltype, account, "", ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                } else {
//                    acno = acno1;
//                    List tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    acno = acno1;
                    List tempList = new ArrayList();
                    if (branchCode.equalsIgnoreCase("0A")) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                    }
                    if (!tempList.isEmpty()) {
                        Vector recLst = (Vector) tempList.get(0);
                        acname = recLst.get(0).toString();
                    }
                    reportLedgerList = glfacadeBeanRemote.subsidiaryBookStatement("", "", account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchCode, txnMode);
                }
            }
            List bankdetails = commonFacadeBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankdetails.isEmpty()) {
                setMessage("Bank details is not present!!!");
                flag = "false";
                return;
            }
            String bankName = bankdetails.get(0).toString();
            String bankAddress = bankdetails.get(1).toString();
            if (reportLedgerList.isEmpty()) {
                flag = "false";
                setMessage("No data available for the input parameters entered!!!");
                return;
            } else {
                for (int i = 0; i < reportLedgerList.size(); i++) {
                    GenReportLedgerTable genReportLedgerTable = reportLedgerList.get(i);
                    genReportLedgerTable.setBankName(bankName);
                    genReportLedgerTable.setBankAddress(bankAddress);
                }
            }
            fillParams.put("dd-MMM-yyyy", dmyformatter.format(fromDate));
            fillParams.put("pPrintedDate", dmyformatter.format(fromDate) + " to " + dmyformatter.format(toDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pAcNo", acno);
            fillParams.put("pAcName", acname);
            if (txnMode.equalsIgnoreCase("0")) {
                fillParams.put("pTxnMode", "Cash");
            } else if (txnMode.equalsIgnoreCase("1")) {
                fillParams.put("pTxnMode", "Clearing");
            } else if (txnMode.equalsIgnoreCase("2")) {
                fillParams.put("pTxnMode", "Transfer");
            } else {
                fillParams.put("pTxnMode", "ALL");
            }

            if (reportType.equalsIgnoreCase("1")) {
                new ReportBean().openPdf("AllLedgerAccountType_", "AllLedgerAccountType", new JRBeanCollectionDataSource(reportLedgerList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
            if (reportType.equalsIgnoreCase("2")) {
                new ReportBean().openPdf("SubsidiaryBookStatement_", "SubsidiaryBookStatement", new JRBeanCollectionDataSource(reportLedgerList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void refresh() {
        try {
            this.setMessage("");
            this.setFromDate(new Date());
            this.setToDate(new Date());
            this.setAccount("0");
            this.setGltype("0");
            this.setReportType("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        return "case1";
    }
}
