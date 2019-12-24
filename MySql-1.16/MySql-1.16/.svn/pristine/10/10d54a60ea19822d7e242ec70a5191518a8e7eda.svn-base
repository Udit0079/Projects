/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.GLDetailsTable;
import com.cbs.dto.report.GenReportLedgerTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.lang.reflect.Array;
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
 * @author SAMY
 */
public class ExpenditureRptPeriodWise extends BaseBean {
    
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
    
    public ExpenditureRptPeriodWise() {
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
            txnModeList.add(new SelectItem("0", "Equal To"));
            txnModeList.add(new SelectItem("1", "Less Than"));
            txnModeList.add(new SelectItem("2", "Greater Than"));
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
        reportTypeList.add(new SelectItem("1", "Expenditure"));
        glTypeList.add(new SelectItem("0", "--SELECT--"));
        glTypeList.add(new SelectItem("5", "EXPENDITURE"));
        accountList.add(new SelectItem("0", "--SELECT--"));
    }
    
    public void glTypeOnchange() {
        try {
            setMessage("");
            flag = "true";
            accountList = new ArrayList<SelectItem>();
            accountList.add(new SelectItem("0", "--SELECT--"));
            accountList.add(new SelectItem("A", "All"));
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
    
    public String Validtions() {
        try {            
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
            if(reportType.equalsIgnoreCase("") || reportType.equalsIgnoreCase("null") ) {
                return "Report Amount can not be Blank!!!";
            }
            if(fromDate.after(toDate)) {
                return "To Date Should be Greater Than or Equal to To Date!!!";
            }
        } catch (Exception e) {
            return "Error occurred during field validations!!!";
        }
        return "true";
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
            report = "Expenditure Report Amount Wise";
            List tempList= new ArrayList();
            acno = acno1;
            if(!acno.equalsIgnoreCase("A")) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    tempList = commonFacadeBeanRemote.getGlNames(acno1);
                } else {
                    if(acno.length()> 8) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                        account=acno1.substring(2,10);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                        account=acno1;
                    }
                }
            }
            if (!tempList.isEmpty()) {
                Vector recLst = (Vector) tempList.get(0);
                acname = recLst.get(0).toString();
            }
            reportLedgerList = glfacadeBeanRemote.expenditureReportAmtWise(branchCode,gltype, account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate),this.reportType, txnMode);
                
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
            if(account.equalsIgnoreCase("A")) {
                fillParams.put("pAcNo", "All");
            } else {
                fillParams.put("pAcNo", acno);
            }
            if(account.equalsIgnoreCase("A")) {
                fillParams.put("pAcName", "All");
            } else {
                fillParams.put("pAcName", acname);
            }
            if (txnMode.equalsIgnoreCase("0")) {
                fillParams.put("pTxnMode", "Equals To "+reportType+"");
            } else if (txnMode.equalsIgnoreCase("1")) {
                fillParams.put("pTxnMode", "Less Than  "+reportType+"");
            } else if (txnMode.equalsIgnoreCase("2")) {
                fillParams.put("pTxnMode", "Greater Than  "+reportType+"");
            }
            new ReportBean().jasperReport("ExpenditureAmtWise", "text/html", new JRBeanCollectionDataSource(reportLedgerList), fillParams, "Expenditure Report Amount Wise");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
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
            report = "Expenditure Report Amount Wise";
//                    List tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
            acno = acno1;
            List tempList = new ArrayList();
            if(!acno.equalsIgnoreCase("A")) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    tempList = commonFacadeBeanRemote.getGlNames(acno1);
                } else {
                    if(acno.length()>8) {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1.substring(2, 10));
                        account=acno1.substring(2,10);
                    } else {
                        tempList = commonFacadeBeanRemote.getGlNames(acno1);
                        account=acno1;
                    }
                }
            }
            if (!tempList.isEmpty()) {
                Vector recLst = (Vector) tempList.get(0);
                acname = recLst.get(0).toString();
            }
            reportLedgerList = glfacadeBeanRemote.expenditureReportAmtWise(branchCode,gltype, account, ymdFormatter.format(fromDate), ymdFormatter.format(toDate),this.reportType, txnMode);

                
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
            if(account.equalsIgnoreCase("A")) {
                fillParams.put("pAcNo", "All");
            } else {
                fillParams.put("pAcNo", acno);
            }
            if(account.equalsIgnoreCase("A")) {
                fillParams.put("pAcName", "All");
            } else {
                fillParams.put("pAcName", acname);
            }
            if (txnMode.equalsIgnoreCase("0")) {
                fillParams.put("pTxnMode", "Equals To "+reportType+"");
            } else if (txnMode.equalsIgnoreCase("1")) {
                fillParams.put("pTxnMode", "Less Than  "+reportType+"");
            } else if (txnMode.equalsIgnoreCase("2")) {
                fillParams.put("pTxnMode", "Greater Than  "+reportType+"");
            }
            new ReportBean().openPdf("Expenditure_Report_Amount_Wise_", "ExpenditureAmtWise", new JRBeanCollectionDataSource(reportLedgerList), fillParams);
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
        this.setAccount("0");
        this.setGltype("0");
        this.setMessage("");
        this.setFromDate(new Date());
        this.setToDate(new Date());
        return "case1";
            
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
    
    
}
