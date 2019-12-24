/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.CashDepositAggTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.SortedByCustId;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Sudhir
 */
public class CashDepositAggregate extends BaseBean {

    // private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private Date tillDate;
    private String accountType,
            transactionType;
    private String branchOption;
    private List<SelectItem> branchOptionList;
    private String repTypeOption;
    private List<SelectItem> repTypeOptionList;
    private String acNatureOption;
    private List<SelectItem> acNatureOptionList;
    private List<SelectItem> amountList;
    private DDSReportFacadeRemote ddsRemote;
    private String fromAmt,toAmt;
    String displayfromDate;
    String invisible;
    private boolean displayBool;
    private List<SelectItem> accountTypeList = new ArrayList<SelectItem>(),
            transactionTypeList = new ArrayList<SelectItem>();
    private final String glreportFacadejndiName = "GLReportFacade";
    private final String commonfacadeJndiName = "CommonReportMethods";
    private GLReportFacadeRemote gLReportFacadeRemote = null;
    private CommonReportMethodsRemote commonFacadeRemote = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag, amountType;
    private Date fromDate,
            toDate;
    private String trOptType;
    private List<SelectItem> trOptList;

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }    

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<SelectItem> getTransactionTypeList() {
        return transactionTypeList;
    }

    public void setTransactionTypeList(List<SelectItem> transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
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

//    public String getOrgnBrCode() {
//        return orgnBrCode;
//    }
//
//    public void setOrgnBrCode(String orgnBrCode) {
//        this.orgnBrCode = orgnBrCode;
//    }
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

    public List<SelectItem> getTrOptList() {
        return trOptList;
    }

    public void setTrOptList(List<SelectItem> trOptList) {
        this.trOptList = trOptList;
    }

    public String getTrOptType() {
        return trOptType;
    }

    public void setTrOptType(String trOptType) {
        this.trOptType = trOptType;
    }

    public String getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(String branchOption) {
        this.branchOption = branchOption;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getAcNatureOption() {
        return acNatureOption;
    }
    public void setAcNatureOption(String acNatureOption) {
        this.acNatureOption = acNatureOption;
    }
    public List<SelectItem> getAcNatureOptionList() {
        return acNatureOptionList;
    }
    public void setAcNatureOptionList(List<SelectItem> acNatureOptionList) {
        this.acNatureOptionList = acNatureOptionList;
    }
    public List<SelectItem> getAmountList() {
        return amountList;
    }
    public void setAmountList(List<SelectItem> amountList) {
        this.amountList = amountList;
    }
    public String getRepTypeOption() {
        return repTypeOption;
    }
    public void setRepTypeOption(String repTypeOption) {
        this.repTypeOption = repTypeOption;
    }
    public List<SelectItem> getRepTypeOptionList() {
        return repTypeOptionList;
    }
    public void setRepTypeOptionList(List<SelectItem> repTypeOptionList) {
        this.repTypeOptionList = repTypeOptionList;
    }
    public String getFromAmt() {
        return fromAmt;
    }
    public void setFromAmt(String fromAmt) {
        this.fromAmt = fromAmt;
    }
    public String getToAmt() {
        return toAmt;
    }
    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }
    public String getDisplayfromDate() {
        return displayfromDate;
    }
    public void setDisplayfromDate(String displayfromDate) {
        this.displayfromDate = displayfromDate;
    }
    public boolean isDisplayBool() {
        return displayBool;
    }
    public void setDisplayBool(boolean displayBool) {
        this.displayBool = displayBool;
    }
    public String getInvisible() {
        return invisible;
    }
    public void setInvisible(String invisible) {
        this.invisible = invisible;
    }
    /**
     * Creates a new instance of AllLedgerAndSubsidiary
     */
    public CashDepositAggregate() {
        try {
            gLReportFacadeRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(glreportFacadejndiName);
            commonFacadeRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonfacadeJndiName);
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            // req = getRequest();
//            String orgnBrIp = WebUtil.getClientIP(req);
//            InetAddress localhost = InetAddress.getByName(orgnBrIp);
//            orgnBrCode = Init.IP2BR.getBranch(localhost);

            trOptList = new ArrayList<SelectItem>();
            accountTypeList= new ArrayList<SelectItem>();
            amountList= new ArrayList<SelectItem>();
            amountList.add(new SelectItem("0", "Amount Upto"));
            amountList.add(new SelectItem("1", "Amount More Than"));
            amountList.add(new SelectItem("2", "Amount Between"));
            repTypeOptionList = new ArrayList<SelectItem>();
            repTypeOptionList.add(new SelectItem("0", "Customer ID"));
            repTypeOptionList.add(new SelectItem("1", "Account Number"));
            acNatureOptionList = new ArrayList<SelectItem>();
            acNatureOptionList.add(new SelectItem("s", "--Select--"));
            acNatureOptionList.add(new SelectItem("All", "All"));
            acNatureOptionList.add(new SelectItem("CA", "CA"));
            acNatureOptionList.add(new SelectItem("SB", "SB"));
            displayfromDate ="Amount Upto";
            //orgnBrCode = "06";
            //userName="Sudhir";
            setUserName(getUserName());
            Date date = new Date();
            setTodayDate(dmyformatter.format(date));
            this.setMessage("");
            branchOptionList = new ArrayList<SelectItem>();
            List list = RemoteCode.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchOptionList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }
            onloadData();
            trOptList.add(new SelectItem("0", "DEPOSIT"));
            trOptList.add(new SelectItem("1", "WITHDRAWAL"));
            displayBool=false;
            this.invisible = "none";
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
    
    public void getAcTypeForNature() {
        accountTypeList = new ArrayList<SelectItem>();
        try {
            List list;
            accountTypeList.add(new SelectItem("All"));
            if(!acNatureOption.equalsIgnoreCase("ALL")){
                if (acNatureOption.equalsIgnoreCase("CA")) {
                    list = ddsRemote.getAccountCodeByClassificationAndNature("'C','B','D'", this.acNatureOption);
                } else {
                    list = ddsRemote.getAccountCodeByClassificationAndNature("'C'", this.acNatureOption);
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    accountTypeList.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
     public void amountListValueChng() {
         setMessage("");
        try {            
            if (amountType.equals("2")) {
               this.invisible = "";
            } else {
               this.invisible = "none";
            }
            if(amountType.equalsIgnoreCase("0")){
                flag ="0";
                displayfromDate = "Amount Upto";
            } else if(amountType.equalsIgnoreCase("1")){
                flag ="1";
                displayfromDate ="Amount More Than";
            } else if(amountType.equalsIgnoreCase("2")){
                flag ="2";
                displayfromDate ="From Amount";
            }
                    
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
     }
     
    public void onloadData() {
        try {
            transactionTypeList.add(new SelectItem("All", "All"));
            transactionTypeList.add(new SelectItem("0", "CASH"));
            transactionTypeList.add(new SelectItem("1", "CLEARING"));
            transactionTypeList.add(new SelectItem("2", "TRANSFER"));
            accountTypeList = new ArrayList<SelectItem>();
            List acctCodelist = null;
            acctCodelist = commonFacadeRemote.getCASBAcTypeList();            
            if (acctCodelist.isEmpty()) {
                setMessage("Account type cannot be found");
                flag = "false";
                return;
            } else {
                accountTypeList.add(new SelectItem("All", "ALL"));
                for (int i = 0; i < acctCodelist.size(); i++) {
                    Vector vec = (Vector) acctCodelist.get(i);
                    accountTypeList.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
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
            if(amountType.equalsIgnoreCase("0")|| amountType.equalsIgnoreCase("1")){
                toAmt = "0.0";
            }
            String report = "Deposit And Withdrawal Aggregate Report";
            List<CashDepositAggTable> cashDepositaggTable = gLReportFacadeRemote.cashDepositAggregateReport(repTypeOption,acNatureOption,accountType, transactionType, amountType, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchOption, this.getTrOptType(),Double.parseDouble(fromAmt),Double.parseDouble(toAmt));
            if (cashDepositaggTable.isEmpty()) {
                setMessage("Data Does Not Exist!!");
                flag = "false";
                return;
            }
            if (transactionType.equalsIgnoreCase("0")) {
                transactionType = "Cash";
            } else if (transactionType.equalsIgnoreCase("1")) {
                transactionType = "Clearing";
            } else if (transactionType.equalsIgnoreCase("2")) {
                transactionType = "Transfer";
            } else if (transactionType.equalsIgnoreCase("ALL")){
                transactionType= "Cash, Clearing,Transfer";
            }
            String acountDesc="";
            if(acNatureOption.equalsIgnoreCase("ALL")) {
                acountDesc= "All CA and SB";
            } else {
                if(accountType.equalsIgnoreCase("ALL")){
                    acountDesc ="All "+acNatureOption+"";
                } else {
                    acountDesc =""+accountType+"";
                }
            }
            String amtType ="";
            if(amountType.equalsIgnoreCase("0")){
                amtType ="Upto "+fromAmt+"";
            } else if(amountType.equalsIgnoreCase("1")){
                amtType ="More Than "+fromAmt+"";
            } else if(amountType.equalsIgnoreCase("2")){
                amtType ="Between "+fromAmt+" and  "+toAmt+"";
            }
            String visible= "";
            String groupBy="";
            if(repTypeOption.equalsIgnoreCase("0")){
                visible="1";
                groupBy =" (Customer Id Wise) ";
            } else {
                visible="2";
                groupBy =" (Account number Wise) ";
            }
            if(repTypeOption.equalsIgnoreCase("0")){
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByCustId());
                Collections.sort(cashDepositaggTable, chObj);
            }

            String opt = "";
            if (this.getTrOptType().equalsIgnoreCase("0")) {
                opt = "Deposit";
            } else {
                opt = "Withdrawal";
            }

            Map fillParams = new HashMap();
            fillParams.put("pReortName", report);
            fillParams.put("pPrintedBy", userName);
            fillParams.put("pAcType", acountDesc);
            fillParams.put("fromdate", dmyformatter.format(fromDate));
            fillParams.put("todate", dmyformatter.format(toDate));
            fillParams.put("amount", amtType);
            fillParams.put("pTrnType", transactionType);
            fillParams.put("opt", opt);
            fillParams.put("visible", visible);
            fillParams.put("groupBy", groupBy);
            fillParams.put("pPrintedDate", dmyformatter.format(fromDate) + " to " + dmyformatter.format(toDate));

            new ReportBean().jasperReport("CashDepositAggregateReport", "text/html", new JRBeanCollectionDataSource(cashDepositaggTable), fillParams, "Cash Deposit Aggregate Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void pdfDownLoad() {
        try {
            String valdationResult = Validtions();
            if (!valdationResult.equalsIgnoreCase("true")) {
                setMessage(valdationResult);
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            if(amountType.equalsIgnoreCase("0")|| amountType.equalsIgnoreCase("1")){
                toAmt = "0.0";
            }
            String report = "Deposit And Withdrawal Aggregate Report";
            List<CashDepositAggTable> cashDepositaggTable = gLReportFacadeRemote.cashDepositAggregateReport(repTypeOption,acNatureOption,accountType, transactionType, amountType, ymdFormatter.format(fromDate), ymdFormatter.format(toDate), branchOption, this.getTrOptType(),Double.parseDouble(fromAmt),Double.parseDouble(toAmt));
            if (cashDepositaggTable.isEmpty()) {
                setMessage("Data Does Not Exist!!");
                flag = "false";
                return;
            }
            if(repTypeOption.equalsIgnoreCase("0")){
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByCustId());
                Collections.sort(cashDepositaggTable, chObj);
            }
            if (transactionType.equalsIgnoreCase("0")) {
                transactionType = "Cash";
            } else if (transactionType.equalsIgnoreCase("1")) {
                transactionType = "Clearing";
            } else if (transactionType.equalsIgnoreCase("2")) {
                transactionType = "Transfer";
            } else if (transactionType.equalsIgnoreCase("ALL")){
                transactionType= "Cash, Clearing,Transfer";
            }
            String acountDesc="";
            if(acNatureOption.equalsIgnoreCase("ALL")) {
                acountDesc= "All CA and SB";
            } else {
                if(accountType.equalsIgnoreCase("ALL")){
                    acountDesc ="All "+acNatureOption+"";
                } else {
                    acountDesc =""+accountType+"";
                }
            }
            String amtType ="";
            if(amountType.equalsIgnoreCase("0")){
                amtType ="Upto "+fromAmt+"";
            } else if(amountType.equalsIgnoreCase("1")){
                amtType ="More Than "+fromAmt+"";
            } else if(amountType.equalsIgnoreCase("2")){
                amtType ="Between "+fromAmt+" and  "+toAmt+"";
            }
            String visible= "";
            String groupBy="";
            if(repTypeOption.equalsIgnoreCase("0")){
                visible="1";
                groupBy =" (Customer Id Wise) ";
            } else {
                visible="2";
                groupBy =" (Account number Wise) ";
            }

            String opt = "";
            if (this.getTrOptType().equalsIgnoreCase("0")) {
                opt = "Deposit";
            } else {
                opt = "Withdrawal";
            }

            Map fillParams = new HashMap();
            fillParams.put("pReortName", report);
            fillParams.put("pPrintedBy", userName);
            fillParams.put("pAcType", acountDesc);
            fillParams.put("fromdate", dmyformatter.format(fromDate));
            fillParams.put("todate", dmyformatter.format(toDate));
            fillParams.put("amount", amtType);
            fillParams.put("pTrnType", transactionType);
            fillParams.put("opt", opt);
            fillParams.put("visible", visible);
            fillParams.put("groupBy", groupBy);
            fillParams.put("pPrintedDate", dmyformatter.format(fromDate) + " to " + dmyformatter.format(toDate));

            new ReportBean().openPdf("Cash Deposit Aggregate Report_" + ymdFormatter.format(fromDate) + " to " + ymdFormatter.format(toDate), "CashDepositAggregateReport", new JRBeanCollectionDataSource(cashDepositaggTable), fillParams);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Cash Deposit Aggregate Report");

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
            if (getAmountType().equalsIgnoreCase("")) {
                return "Error!!! Amount field is empty";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (!getAmountType().equalsIgnoreCase("")) {
                Matcher amountCheck = p.matcher(getAmountType());
                if (!amountCheck.matches()) {
                    return "Error!!! Enter numeric value for amount";
                }
                if (Double.parseDouble(getAmountType()) < 0) {
                    return "Error!!! Elease positive value for amount field!!!";
                }
            }
            if (fromDate == null) {
                return "Please Enter From Date!!!";
            }
            if (toDate == null) {
                return "Please Enter To Date!!!";
            }            
            if(amountType.equalsIgnoreCase("2") && fromAmt.equalsIgnoreCase("")){
                setMessage("Please fill from Amount!!");
                return "Please Fill From Amount!!";                
            }
            if(amountType.equalsIgnoreCase("2") && (new BigDecimal(fromAmt).compareTo(new BigDecimal(toAmt)) > 0)){                
                setMessage("From Amount can Not be grater than to Amount!!");
                return "From Amount can Not be grater than to Amount!!";                
            }            
        } catch (Exception e) {
            return "Error occurred during validations!!!";
        }
        return "true";
    }

    public String exitFrm() {
        this.setAmountType("0");
        this.setTrOptType("0");
        this.setDisplayBool(false);
        this.setAccountType("All");
        return "case1";
    }

    public void refresh() {
        setMessage("");
        this.setAcNatureOption("ALL");
        this.setAccountType("All");
        this.setFromAmt("0");
        this.setToAmt("0");
        this.setTrOptType("0");
        this.setRepTypeOption("ALL");
        this.setDisplayBool(false);
        this.setAmountType("0");
        this.setAmountType("0");
        this.setTrOptType("0");
        this.fromDate = null;
        this.toDate = null;
    }
}
