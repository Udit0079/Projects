/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.LoanMultiList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.IntCalTable;
import com.cbs.web.pojo.loan.InterestCalculationPojo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class TheftInterestCal extends BaseBean {

    private String message;
    private String allAccount;
    private String accountWise;
    private String accountType;
    private String accountNo;
    private String accountNoType;
    private String debitAmt;
    private String creditAmt;
    private String totalCredit;
    private String totalDebit;
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean disableAcctType;
    private boolean disableAcctNo;
    private boolean disablePost;
    private Date fromDate;
    private Date toDate;
    boolean fromDisable;
    boolean toDisable;
    boolean disableRoi;
    private List<SelectItem> allAccountList;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> accountWiseList;
    LoanGenralFacadeRemote loanGenralFacade;
    private List<IntCalTable> intCalc;
    private String acCloseFlag = null;
    private boolean reportFlag;
    private String tmpFromDt;
    private String tmpToDt;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    String oldAccNo;
    private LoanIntProductTestFacadeRemote loanIntRemote;
    private String gridStyle;
    private String gridStyle1;
    private String tranTypeList;
    private String roi;

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getTmpFromDt() {
        return tmpFromDt;
    }

    public void setTmpFromDt(String tmpFromDt) {
        this.tmpFromDt = tmpFromDt;
    }

    public String getTmpToDt() {
        return tmpToDt;
    }

    public void setTmpToDt(String tmpToDt) {
        this.tmpToDt = tmpToDt;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public boolean isToDisable() {
        return toDisable;
    }

    public void setToDisable(boolean toDisable) {
        this.toDisable = toDisable;
    }

    public List<IntCalTable> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<IntCalTable> intCalc) {
        this.intCalc = intCalc;
    }

    public boolean isDisableAcctNo() {
        return disableAcctNo;
    }

    public void setDisableAcctNo(boolean disableAcctNo) {
        this.disableAcctNo = disableAcctNo;
    }

    public boolean isDisableAcctType() {
        return disableAcctType;
    }

    public void setDisableAcctType(boolean disableAcctType) {
        this.disableAcctType = disableAcctType;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public String getAccountNoType() {
        return accountNoType;
    }

    public void setAccountNoType(String accountNoType) {
        this.accountNoType = accountNoType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(String debitAmt) {
        this.debitAmt = debitAmt;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountWise() {
        return accountWise;
    }

    public void setAccountWise(String accountWise) {
        this.accountWise = accountWise;
    }

    public String getAllAccount() {
        return allAccount;
    }

    public void setAllAccount(String allAccount) {
        this.allAccount = allAccount;
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

    public List<SelectItem> getAllAccountList() {
        return allAccountList;
    }

    public void setAllAccountList(List<SelectItem> allAccountList) {
        this.allAccountList = allAccountList;
    }

    public List<SelectItem> getAccountWiseList() {
        return accountWiseList;
    }

    public void setAccountWiseList(List<SelectItem> accountWiseList) {
        this.accountWiseList = accountWiseList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcCloseFlag() {
        return acCloseFlag;
    }

    public void setAcCloseFlag(String acCloseFlag) {
        this.acCloseFlag = acCloseFlag;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getGridStyle() {
        return gridStyle;
    }

    public void setGridStyle(String gridStyle) {
        this.gridStyle = gridStyle;
    }

    public String getGridStyle1() {
        return gridStyle1;
    }

    public void setGridStyle1(String gridStyle1) {
        this.gridStyle1 = gridStyle1;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public boolean isDisableRoi() {
        return disableRoi;
    }

    public void setDisableRoi(boolean disableRoi) {
        this.disableRoi = disableRoi;
    }
    
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public TheftInterestCal() {
        try {
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            loanIntRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup("LoanIntProductTestFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(getHttpRequest().getParameter("code").toString());
                this.setAccountNoType(getHttpRequest().getParameter("code").toString().substring(2, 4));
                this.setAccountNo(getHttpRequest().getParameter("code").toString());
                this.setOldAccNo(getHttpRequest().getParameter("code").toString());
                this.setAccountWise("I");
            }
            intCalc = new ArrayList<IntCalTable>();
            
            tranTypeList = ftsPostRemote.getCodeFromCbsParameterInfo("BNK_IDENTIFIER");
            if(tranTypeList.equalsIgnoreCase("A")){
                gridStyle = "";
                gridStyle1 = "none";
            }else{
                gridStyle = "none";
                gridStyle1 = "";
            }
            setMessage("");
            setDisableAcctNo(true);
            setDisableAcctType(true);
            setDisablePost(true);
            getValues();
            this.setReportFlag(false);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getValues() {
        try {
            this.setReportFlag(false);
            allAccountList = new ArrayList<SelectItem>();
            allAccountList.add(new SelectItem("A", "All Account"));
            accountWiseList = new ArrayList<SelectItem>();
            accountWiseList.add(new SelectItem("I", "Account Wise"));
            List resultList = new ArrayList();
            //List accountList = new ArrayList();
            resultList = beanRemote.getTheftAcType();
            if (!resultList.isEmpty()) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();

    public void calculate() {
        this.setMessage("");
        this.setReportFlag(false);
        intCalc = new ArrayList<IntCalTable>();
        String accType = null;
        String acno = null;
        String option = null;
        //String mode = "cal";
        setMessage("");
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (allAccount == null || allAccount.equalsIgnoreCase("")) {
        } else if (allAccount.equalsIgnoreCase("A")) {
            accType = accountType;
            acno = "";
            option = "A";
                
            if (accountType.equalsIgnoreCase("0")) {
                setMessage("Please Select the Account Type.");
                return;
            }
        }
        if(tranTypeList.equalsIgnoreCase("A")){            
            try {
                
                if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                } else if (accountWise.equalsIgnoreCase("I")) {
                    
                    if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                        setMessage("Please Enter the Account No.");
                        return;
                    } else {
                        acno = accountNo;
                        accType = ftsPostRemote.getAccountCode(acno);
                        List dataList = ftsPostRemote.getUnAuthorizedTranListForAcno(acno);
                        if (!dataList.isEmpty()) {
                                this.setMessage("There are some pending authorization !");
                            return;
                        }
                        List result;
                        
                        result = loanGenralFacade.accountDetail(acno);
                        if (result.isEmpty()) {
                            this.setMessage("Account No. Does Not Exist.");
                            return;
                        } else {
                            for (int i = 0; i < result.size(); i++) {
                                Vector ele = (Vector) result.get(i);
                                if (ele.get(3).toString().equalsIgnoreCase("9")) {
                                    this.setMessage("Account has been closed.");
                                    return;
                                }
                            }
                        }
                        if (toDate == null) {
                            setMessage("Please Select To Date.");
                            return;
                        }
                        option = "I";
                    }                    
                    String glHead = beanRemote.getGlHeads(accType);
                    setCreditAmt(glHead);
                    if (toDate == null) {
                        setMessage("Please Select To Date.");
                        return;
                    }
                    if (fromDate == null) {
                        setMessage("Please Select From Date.");
                        return;
                    }
                    if (fromDate.after(toDate)) {
                        setMessage("To date can not be greater than From Date");
                        return;
                    }
                }
                if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                } else if (allAccount.equalsIgnoreCase("A")) {
                    List dataList = ftsPostRemote.getUnAuthorizedTranList(accountType, getOrgnBrCode());
                    if (!dataList.isEmpty()) {
                        this.setMessage("There are some pending authorization !");
                        return;
                    }
                    if (ymmd.parse(ymmd.format(date)).before(ymmd.parse(ymmd.format(fromDate))) || ymmd.parse(ymmd.format(date)).equals(ymmd.parse(ymmd.format(fromDate)))) {
                        setMessage("Please check the Interest calculation date.");
                        return;
                    }
                    String glHead = beanRemote.getGlHeads(accountType);
                    setCreditAmt(glHead);
                }
                
                if (fromDate == null) {
                    setMessage("Please Select From Date.");
                    return;
                }
                if (toDate == null) {
                    setMessage("Please Select To Date.");
                    return;
                }
                if (option == null || option.equals("0") || option.equalsIgnoreCase("")) {
                    setMessage("Please Select the Radio Button.");
                    return;
                }
                if (creditAmt == null || creditAmt.equalsIgnoreCase("")) {
                    setMessage("Credit Account is not Set.");
                    return;
                }
                if (accType.equalsIgnoreCase("0")) {
                    setMessage("Please select/fill the account type.");
                    return;
                }
                if (accType == null || accType.equals("")) {
                    setMessage("Please select/fill the account type.");
                    return;
                }
                double amount = 0d;
                this.setTmpFromDt(sdf.format(fromDate));
                this.setTmpToDt(sdf.format(toDate));
                
                List<LoanIntCalcList> resultList = beanRemote.cbsTheftIntCalcNew(option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditAmt, getUserName(), getOrgnBrCode());
                intProductDetail =resultList;
                List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();                
                if (resultList.isEmpty()) {
                    setMessage("Data does not exist");
                    setDisablePost(true);
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        LoanIntCalcList lt = resultList.get(i);
                        IntCalTable mt = new IntCalTable();
                        InterestCalculationPojo pojo = new InterestCalculationPojo();
                        mt.setsNo(Integer.toString(i + 1));
                        
                        pojo.setAcNo(lt.getAcNo());
                        pojo.setCustName(lt.getCustName());
                        
                        mt.setAcNo(lt.getAcNo());
                        mt.setCustName(lt.getCustName());
                        mt.setFirstDt(sdf.format(ymd.parse(lt.getFirstDt())));
                        mt.setLastDt(sdf.format(ymd.parse(lt.getLastDt())));
                        
                        pojo.setFromDate(sdf.format(ymd.parse(lt.getFirstDt())));
                        pojo.setToDate(sdf.format(ymd.parse(lt.getLastDt())));
                        
                        double closingBal = lt.getClosingBal();
                        double acctProduct = lt.getProduct();
                        double acctInt = lt.getTotalInt();
                        //new code
                        double totalAcctInt = lt.getTotalInt();
                        double sbInt = lt.getSbInt();
                        double fdInt = lt.getFdInt();
                        double fdProduct = lt.getFdProduct();
                        //end
                        if (closingBal < 0.0) {
                            closingBal = -1 * closingBal;
                        }
                        if (acctProduct < 0.0) {
                            acctProduct = -1 * acctProduct;
                        }
                        if (acctInt < 0.0) {
                            acctInt = -1 * acctInt;
                        }
                        mt.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                        pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                        mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                        pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                        mt.setFdProduct(new BigDecimal(formatter.format(fdProduct)));    // Fd Product
                        mt.setRoi(new BigDecimal(formatter.format(lt.getRoi())));
                        mt.setFdRoi(new BigDecimal(formatter.format(lt.getFdRoi()))); //Fd Roi
                    
                        pojo.setRoi(new BigDecimal(formatter.format(lt.getRoi())));
                        mt.setTotalInt(new BigDecimal(formatter.format(acctInt)).setScale(0, BigDecimal.ROUND_HALF_UP));
                        mt.setFdInt(new BigDecimal(formatter.format(fdInt)));
                        mt.setSbInt(new BigDecimal(formatter.format(sbInt)));
                        pojo.setInterestAmt(new BigDecimal(formatter.format(totalAcctInt)).setScale(0, BigDecimal.ROUND_HALF_UP));
                        amount = amount + totalAcctInt;
                        
                        mt.setIntTableCode(lt.getIntTableCode());
                        pojo.setIntTableCode(lt.getIntTableCode());
                        intCalc.add(mt);
                        reportList.add(pojo);
                    }
                }
                                
                printReport(reportList);
                setDebitAmt("As per list");
                setTotalCredit(formatter.format(new BigDecimal(amount).setScale(0, BigDecimal.ROUND_HALF_UP)));
                setTotalDebit(formatter.format(new BigDecimal(amount).setScale(0, BigDecimal.ROUND_HALF_UP)));
                if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                    String glHead = beanRemote.getGlHeads(accountType);
                    setCreditAmt(glHead);
                } else if (allAccount.equalsIgnoreCase("I")) {
                    String glHead = beanRemote.getGlHeads(accountType);
                    setCreditAmt(glHead);
                }
                if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                    String glHead = beanRemote.getGlHeads(accType);
                    setCreditAmt(glHead);
                } else if (accountWise.equalsIgnoreCase("I")) {
                    String glHead = beanRemote.getGlHeads(accType);
                    setCreditAmt(glHead);
                }
                setDisablePost(false);
                setToDisable(true);
            } catch (ApplicationException e) {
                this.setMessage(e.getMessage());
                setDisablePost(true);
            } catch (Exception e) {
                e.printStackTrace();
                this.setMessage(e.getMessage());
                setDisablePost(true);
            }
        }else{
            if (accountType.equalsIgnoreCase("0")) {
                setMessage("Please Select the Account Type.");
                return;
            }
            try{
                if (toDate == null) {
                    setMessage("Please Select To Date.");
                    return;
                }
                if (fromDate == null) {
                    setMessage("Please Select From Date.");
                    return;
                }
                if (fromDate.after(toDate)) {
                    setMessage("To date can not be greater than From Date");
                    return;
                }
                if (ymmd.parse(ymmd.format(date)).before(ymmd.parse(ymmd.format(fromDate))) || ymmd.parse(ymmd.format(date)).equals(ymmd.parse(ymmd.format(fromDate)))) {
                    setMessage("Please check the Interest calculation date.");
                    return;
                }
                if (this.roi == null || this.roi.toString().equalsIgnoreCase("")) {
                    setMessage("Please Fill Roi Field.");
                    return;
                } else if (this.roi.toString().equalsIgnoreCase("0") || this.roi.toString().equalsIgnoreCase("0.0")) {
                    setMessage("Please Fill Roi Field.");
                    return;
                } else {
                    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                    Matcher roiCheck = p.matcher(this.roi.toString());
                    if (!roiCheck.matches()) {
                        setMessage("Invalid Roi.");
                        return;
                    }
                }
                String glHead = beanRemote.getGlHeads(accountType);
                setCreditAmt("As per list");
                
                List<LoanIntCalcList> resultList = beanRemote.threftIntCalcPostal(option, accType, acno, Float.parseFloat(roi), sdf.format(fromDate), sdf.format(toDate), getUserName(), getOrgnBrCode());
                intProductDetail =resultList;
                if (resultList.isEmpty()) {
                    setMessage("Data does not exist");
                    setDisablePost(true);
                }else{
                    double amount = 0d;
                    BigDecimal intAmt = new BigDecimal("0");
                    for (int i = 0; i < resultList.size(); i++) {
                        LoanIntCalcList lt = resultList.get(i);
                        IntCalTable mt = new IntCalTable();
                        mt.setsNo(Integer.toString(i + 1));
                        mt.setAcNo(lt.getAcNo());
                        mt.setCustName(lt.getCustName());
                        mt.setIntTableCode(lt.getIntTableCode());
                        mt.setFatherName(lt.getFatherName());
                        mt.setFirstDt(sdf.format(ymd.parse(lt.getFirstDt())));
                        mt.setLastDt(sdf.format(ymd.parse(lt.getLastDt())));
                        mt.setRoi(new BigDecimal(lt.getRoi()));
                        mt.setProduct(new BigDecimal(lt.getProduct()));
                        intAmt = new BigDecimal(lt.getTotalInt());
                       // System.out.println("Before Rounding "+intAmt+ "  After Rounding "+ intAmt.setScale(0, BigDecimal.ROUND_HALF_UP));
                        mt.setTotalInt(new BigDecimal(lt.getTotalInt()).setScale(0, BigDecimal.ROUND_HALF_UP));
                        mt.setRelatedAc(lt.getRelatedAc());
                        amount = amount + Math.round(lt.getTotalInt());
                        intCalc.add(mt);
                    }
                    setDebitAmt(this.getOrgnBrCode() + glHead + "01");
                    setTotalCredit(formatter.format(amount));
                    setTotalDebit(formatter.format(amount));
                    setDisablePost(false);
                    
                    setToDisable(true);
                    setDisableAcctType(true);
                    setFromDisable(true);
                    setToDisable(true);
                    setDisableRoi(true);
                    
                    List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
                    String bankName = bankDetailsList.get(0).toString();
                    String bankAddress = bankDetailsList.get(1).toString();
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", "Threft Interest Calculation");
                    fillParams.put("pReportDate", getTodayDate());
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pDt", "Interst From " + sdf.format(fromDate) + " To " + sdf.format(toDate));
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAddress);
                    
                    new ReportBean().jasperReport("Thef_Int_Cal_Report", "text/html", new JRBeanCollectionDataSource(intCalc), fillParams, "Interest Calculation");
                    this.setReportFlag(true);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                }                
            }catch (ApplicationException e) {
                this.setMessage(e.getMessage());
                setDisablePost(true);
            } catch (Exception e) {
                e.printStackTrace();
                this.setMessage(e.getMessage());
                setDisablePost(true);
            }
        }    
    }

    public void Post() {
        String accType = null;
        String acno = null;
        String option = null;
        this.setMessage("");
        this.setReportFlag(false);
        try {
            if(tranTypeList.equalsIgnoreCase("A")){
                if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                    allAccount = "";
                } else if (allAccount.equalsIgnoreCase("A")) {
                    accType = accountType;
                    acno = "";
                    option = "A";
                    if (accountType.equalsIgnoreCase("0")) {
                        setMessage("Please Select the Account Type.");
                        return;
                    }
                }
                if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                    accountWise = "";
                } else if (accountWise.equalsIgnoreCase("I")) {
                    
                    if (oldAccNo.equalsIgnoreCase("") || oldAccNo == null) {
                        setMessage("Please Enter the Account No.");
                        return;
                    } else {
                        acno = accountNo;
                        accType = ftsPostRemote.getAccountCode(acno);
                        List result;
                        result = loanGenralFacade.accountDetail(acno);
                        if (result.isEmpty()) {
                            this.setMessage("Account No. Does Not Exist.");
                            return;
                        } else {
                            for (int i = 0; i < result.size(); i++) {
                                Vector ele = (Vector) result.get(i);
                                if (ele.get(3).toString().equalsIgnoreCase("9")) {
                                    this.setMessage("Account has been closed.");
                                    return;
                                }
                            }
                        }
                        option = "I";
                        if (toDate == null) {
                            setMessage("Please Select To Date.");
                            return;
                        } else if (toDate.after(date)) {
                            setMessage("To date can not be greater current Date");
                            this.setToDate(date);
                            return;
                        }
                    }
                }
                if (allAccount == null || allAccount.equalsIgnoreCase("")) {
                    allAccount = "";
                } else if (allAccount.equalsIgnoreCase("A")) {
                    String glHead = beanRemote.getGlHeads(accountType);
                    setCreditAmt(glHead);
                }
                if (accountWise == null || accountWise.equalsIgnoreCase("")) {
                    accountWise = "";
                } else if (accountWise.equalsIgnoreCase("I")) {
                    String glHead = beanRemote.getGlHeads(accType);
                    setCreditAmt(glHead);
                    if (toDate == null) {
                        setMessage("Please Select To Date.");
                        return;
                    }
                    if (fromDate == null) {
                        setMessage("Please Select From Date.");
                        return;
                    }
                    if (fromDate.after(toDate)) {
                        setMessage("To date can not be greater than From Date");
                        return;
                    }
                }
                if (fromDate == null) {
                    setMessage("Please Select From Date.");
                    return;
                }
                if (toDate == null) {
                    setMessage("Please Select To Date.");
                    return;
                }
                if (option == null || option.equals("0") || option.equalsIgnoreCase("")) {
                    setMessage("Please Select the Radio Button.");
                    return;
                }
                if (creditAmt == null || creditAmt.equalsIgnoreCase("")) {
                    setMessage("Credit Account is not Set.");
                    return;
                }
                if (accountWise.equalsIgnoreCase("I")) {
                    accType = acno.substring(2, 4);
                }
                String result = beanRemote.theftInterestPosting(intProductDetail, option, accType, acno, sdf.format(fromDate), sdf.format(toDate), creditAmt, getUserName(), getOrgnBrCode());
                setMessage(result);
            }else{
                String result = beanRemote.theftInterestPostalPosting(intProductDetail, accountType, sdf.format(fromDate), sdf.format(toDate), debitAmt, getUserName(), getOrgnBrCode());
                setMessage(result);
            }            
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");

            setAccountNoType("");
            setAccountType("0");
            setDebitAmt("");

            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            setDisablePost(true);
            setDisableRoi(false);
            setRoi("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void disableAcctType() {
        try {
            this.setReportFlag(false);
            setDisableAcctType(false);
            setDisablePost(true);
            setAccountWise("");
            setDisableAcctNo(true);
            //setAccountNoType("0");
            setAccountNo("");
            this.setOldAccNo("");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            this.setMessage("");
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void disableAcctTypeNo() {
        try {
            this.setReportFlag(false);
            setDisableAcctNo(false);
            setAllAccount("");
            setDisablePost(true);
            setDisableAcctType(true);
            setAccountType("0");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            this.setMessage("");
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            setDisableAcctNo(false);
            this.setReportFlag(false);
            this.setMessage("");
            setAllAccount("");
            setAccountWise("");
            setAccountNo("");
            this.setOldAccNo("");
            setAccountNoType("");
            setAccountType("0");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
            setToDisable(false);
            setFromDisable(false);
            setDisableRoi(false);
            setRoi("");                  
            tranTypeList = ftsPostRemote.getCodeFromCbsParameterInfo("BNK_IDENTIFIER");
            if(tranTypeList.equalsIgnoreCase("A")){
                gridStyle = "";
                gridStyle1 = "none";
            }else{
                gridStyle = "none";
                gridStyle1 = "";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    public void ddAccountNo(String acctNo) {
        try {
            String glHead = beanRemote.getGlHeads(acctNo);
            setCreditAmt(glHead);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            this.setMessage("");
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();

            if (this.acCloseFlag == null || this.acCloseFlag.equalsIgnoreCase("")) {
                refresh();
                return "case1";
            } else {
                this.acCloseFlag = null;
                refresh();
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/AccountClosingRegister.jsp?code=" + 1);
                return "case2";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            setDebitAmt("");
            setCreditAmt("");
            setTotalCredit("");
            setTotalDebit("");

            intCalc = new ArrayList<IntCalTable>();
            String fromDt = "";
            String toDt = "";
            //Float amount = (float) 0;
            if (accountType == null) {
                accountType = "";
            }
            if (accountType.equals("0")) {
                accountType = "";
            }

            if (!accountType.equals("0") && !accountType.equals("")) {
                System.out.println(accountType);
                fromDt = beanRemote.allFromDt(accountType, getOrgnBrCode(), "f");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));
                    setFromDisable(true);
                }
                toDt = beanRemote.allFromDt(accountType, getOrgnBrCode(), "t");
                if (toDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setToDate(sdf.parse(sdf.format(ymd.parse(toDt))));
                    setToDisable(true);
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setDateAccountWise() {
        this.setMessage("");
        setDebitAmt("");
        setCreditAmt("");
        setTotalCredit("");
        setTotalDebit("");
        intCalc = new ArrayList<IntCalTable>();
        try {
            if (oldAccNo == null || oldAccNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            }
            if (oldAccNo.length() < 12) {
                setMessage("Please enter 12 digits account no.");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(oldAccNo);

            String curBrCode = ftsPostRemote.getCurrentBrnCode(accountNo);
            if (!curBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                setMessage("Account should be of self branch.");
                return;
            }

            String yyyy = ymd.format(date).substring(0, 4);
            List dtList = beanRemote.acWiseFromDtToDt(yyyy, getOrgnBrCode());
            Vector dtv = (Vector) dtList.get(0);
            setToDate(ymd.parse(dtv.get(1).toString()));

            String resultList = loanIntRemote.acWiseFromDt(accountNo, getOrgnBrCode());
            if ((resultList != null) || (!resultList.equalsIgnoreCase(""))) {
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
                setFromDisable(true);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void printReport(List<InterestCalculationPojo> resultList) {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankDetailsList.size() > 0) {
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Interest Calculation");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", tmpFromDt);
                fillParams.put("pToDt", tmpToDt);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);

                new ReportBean().jasperReport("LOAN_INT_CAL_REPORT", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Interest Calculation");
                this.setReportFlag(true);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
