/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.web.pojo.intcal.IntCalTable;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.LoanMultiList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.loan.InterestCalculationPojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class LoanIntProductCal extends BaseBean {

    private String message;
    private String accountNo;
    private Date fromDate;
    private Date toDate;
    double totalNoOfDays;
    double totalInt;
    double totalProduct;
    private List<IntCalTable> intCalc;
    private String viewID = "/pages/master/sm/test.jsp";
    private String acCloseFlag = null;
    LoanGenralFacadeRemote loanGenralFacade;
    private final String jndiHomeName = "LoanIntProductTestFacade";
    private LoanIntProductTestFacadeRemote beanRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    String oldAcNo, acNoLen, pAddress, custAdd, schemeCode;
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private boolean reportFlag;
    BigDecimal priAmt, intAmt;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;

    public double getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(double totalInt) {
        this.totalInt = totalInt;
    }

    public double getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(double totalProduct) {
        this.totalProduct = totalProduct;
    }

    public double getTotalNoOfDays() {
        return totalNoOfDays;
    }

    public void setTotalNoOfDays(double totalNoOfDays) {
        this.totalNoOfDays = totalNoOfDays;
    }

    public List<IntCalTable> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<IntCalTable> intCalc) {
        this.intCalc = intCalc;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getOldAcNo() {
        return oldAcNo;
    }

    public void setOldAcNo(String oldAcNo) {
        this.oldAcNo = oldAcNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
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
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public LoanIntProductCal() {
        try {
            beanRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            this.setAcNoLen(getAcNoLength());
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            intCalc = new ArrayList<IntCalTable>();
            this.setMessage("");
            this.setReportFlag(false);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void calculate() {
        try {
            setMessage("");
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            //} else if (oldAcNo.length() < 12) {
            } else if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please enter proper account no.");
                return;
            } else if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                setMessage("There should be a display account no for your filled account no.");
                return;
            } else if (accountNo.length() < 12) {
                setMessage("Display account no length should be of 12 digits.");
                return;
            } else {
                //String resultList = beanRemote.acWiseFromDt(accountNo, getOrgnBrCode());
                loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
                List result = loanGenralFacade.accountDetail(accountNo);
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
            }
            if (fromDate == null) {
                setMessage("Please Select From Date.");
                return;
            }
            if (toDate == null) {
                setMessage("Please Select To Date.");
                return;
            }
            if (fromDate != null && toDate != null) {
                if (fromDate.after(toDate)) {
                    setMessage(" To date can not be greater current Date");
                    this.setToDate(date);
                    return;
                }
            }
            NumberFormat formatter = new DecimalFormat("#0.00");
            //List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();

            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0d;
            List<LoanMultiList> resultList = beanRemote.accWiseLoanIntProductCalc(ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode().equalsIgnoreCase("90")?accountNo.substring(0, 2):getOrgnBrCode());
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                //List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
                //LoanMultiList la = new LoanMultiList();
                //lml = resultList;
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                //intDetails = resultList.get(0).getLoanIntDetails();
                totalNoOfDays = 0;
                totalProduct = 0;
                totalInt = 0;
                for (int i = 0; i < intProductDetail.size(); i++) {
                    IntCalTable mt = new IntCalTable();
                    mt.setsNo(Integer.toString(i + 1));
                    mt.setAcNo(intProductDetail.get(i).getAcNo());
                    mt.setCustName(intProductDetail.get(i).getCustName());
                    mt.setFirstDt(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setLastDt(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                    double closingBal = intProductDetail.get(i).getClosingBal();
                    double acctProduct = intProductDetail.get(i).getProduct();
                    double acctInt = intProductDetail.get(i).getTotalInt();
                    if (closingBal < 0.0) {
                        closingBal = -1 * closingBal;
                    }
                    if (acctProduct < 0.0) {
                        acctProduct = -1 * acctProduct;
                    }
                    if (acctInt < 0.0) {
                        acctInt = -1 * acctInt;
                    }
                    double noOfDays = intProductDetail.get(i).getClosingBal();
                    totalNoOfDays = noOfDays + totalNoOfDays;
                    totalProduct = acctProduct + totalProduct;
                    totalInt = acctInt + totalInt;
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    mt.setFdProduct(new BigDecimal(formatter.format(intProductDetail.get(i).getFdProduct())));
                    mt.setFdInt(new BigDecimal(formatter.format(intProductDetail.get(i).getFdInt())));
                    intCalc.add(mt);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void calculateProjectedIntCertificate() {
        try {
            setMessage("");
            this.setReportFlag(false);
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            } else if (oldAcNo.length() < 12) {
                setMessage("Please enter 12 digits account no.");
                return;
            } else if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                setMessage("There should be a display account no for your filled account no.");
                return;
            } else if (accountNo.length() < 12) {
                setMessage("Display account no length should be of 12 digits.");
                return;
            } else {
                //String resultList = beanRemote.acWiseFromDt(accountNo, getOrgnBrCode());
                loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
                List result = loanGenralFacade.accountDetail(accountNo);
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
            }
            if (fromDate == null) {
                setMessage("Please Select From Date.");
                return;
            }
            if (toDate == null) {
                setMessage("Please Select To Date.");
                return;
            }
            if (fromDate != null && toDate != null) {
                if (fromDate.after(toDate)) {
                    setMessage(" To date can not be greater current Date");
                    this.setToDate(date);
                    return;
                }
            }
            String custId = acOpenFacadeRemote.customerId(this.accountNo);
            
            String custinfo = acOpenFacadeRemote.custIdInformation(custId);
            String[] values = null;
            String spliter = ": ";
            values = custinfo.split(spliter);            
            custAdd = values[3]+values[12];
            NumberFormat formatter = new DecimalFormat("#0.00");
            //List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
            List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0d;
            BigDecimal totalEmi = new BigDecimal("0");
            List<LoanMultiList> resultList = beanRemote.accWiseLoanIntProductCalcAsPerEmiRecovery(ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode().equalsIgnoreCase("90")?accountNo.substring(0, 2):getOrgnBrCode(),"N",0);
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                //List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
                //LoanMultiList la = new LoanMultiList();
                //lml = resultList;
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                //intDetails = resultList.get(0).getLoanIntDetails();
                totalNoOfDays = 0;
                totalProduct = 0;
                totalInt = 0;
                for (int i = 0; i < intProductDetail.size(); i++) {
                    IntCalTable mt = new IntCalTable();
                    InterestCalculationPojo pojo = new InterestCalculationPojo();
                    mt.setsNo(Integer.toString(i + 1));
                    mt.setAcNo(intProductDetail.get(i).getAcNo());
                    mt.setCustName(intProductDetail.get(i).getCustName());
                    mt.setFirstDt(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setLastDt(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                    double closingBal = intProductDetail.get(i).getClosingBal();
                    double acctProduct = intProductDetail.get(i).getProduct();
                    double acctInt = intProductDetail.get(i).getTotalInt();
                    if (closingBal < 0.0) {
                        closingBal = -1 * closingBal;
                    }
                    if (acctProduct < 0.0) {
                        acctProduct = -1 * acctProduct;
                    }
                    if (acctInt < 0.0) {
                        acctInt = -1 * acctInt;
                    }
                    double noOfDays = intProductDetail.get(i).getClosingBal();
                    totalNoOfDays = noOfDays + totalNoOfDays;
                    totalProduct = acctProduct + totalProduct;
                    totalInt = acctInt + totalInt;
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    mt.setFdProduct(new BigDecimal(formatter.format(intProductDetail.get(i).getFdProduct())));
                    mt.setFdInt(new BigDecimal(formatter.format(intProductDetail.get(i).getFdInt())));
                    intCalc.add(mt);
                    
                    pojo.setGroupBy("EMI");
                    pojo.setAcNo(intProductDetail.get(i).getAcNo());
                    pojo.setCustName(intProductDetail.get(i).getCustName());
                    pojo.setFromDate(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    pojo.setToDate(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    pojo.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    pojo.setInterestAmt(new BigDecimal(formatter.format(acctInt)));
                    pojo.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    pojo.setEmiAmt(new BigDecimal(formatter.format(intProductDetail.get(i).getPriAmt())));
                    
                    schemeCode = intProductDetail.get(i).getIntTableCode();
                    totalEmi = totalEmi.add(new BigDecimal(formatter.format(intProductDetail.get(i).getPriAmt())));
                    reportList.add(pojo);
                }
                intAmt = new BigDecimal(formatter.format(totalInt));
                priAmt = totalEmi.subtract(intAmt);
                if(priAmt.doubleValue()<0){
                    priAmt = new BigDecimal("0");
                }
                printReport(reportList, "LOAN_INT_CAL_REPORT_PROJECTED_CERTIFICATE");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void calculateProjectedEmiIntVsAcIntCalc() {
        try {
            setMessage("");
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            } else if (oldAcNo.length() < 12) {
                setMessage("Please enter 12 digits account no.");
                return;
            } else if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                setMessage("There should be a display account no for your filled account no.");
                return;
            } else if (accountNo.length() < 12) {
                setMessage("Display account no length should be of 12 digits.");
                return;
            } else {
                //String resultList = beanRemote.acWiseFromDt(accountNo, getOrgnBrCode());
                loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
                List result = loanGenralFacade.accountDetail(accountNo);
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
            }
            if (fromDate == null) {
                setMessage("Please Select From Date.");
                return;
            }
            if (toDate == null) {
                setMessage("Please Select To Date.");
                return;
            }
            if (fromDate != null && toDate != null) {
                if (fromDate.after(toDate)) {
                    setMessage(" To date can not be greater current Date");
                    this.setToDate(date);
                    return;
                }
            }
            NumberFormat formatter = new DecimalFormat("#0.00");
            //List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
            List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0d;
            List<LoanMultiList> resultList = beanRemote.accWiseLoanIntProductCalcAsPerEmiRecovery(ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode().equalsIgnoreCase("90")?accountNo.substring(0, 2):getOrgnBrCode(),"N",0);
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                //List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
                //LoanMultiList la = new LoanMultiList();
                //lml = resultList;
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                //intDetails = resultList.get(0).getLoanIntDetails();
                totalNoOfDays = 0;
                totalProduct = 0;
                totalInt = 0;
                for (int i = 0; i < intProductDetail.size(); i++) {
                    IntCalTable mt = new IntCalTable();
                    InterestCalculationPojo pojo = new InterestCalculationPojo();
                    mt.setsNo(Integer.toString(i + 1));
                    mt.setAcNo(intProductDetail.get(i).getAcNo());
                    mt.setCustName(intProductDetail.get(i).getCustName());
                    mt.setFirstDt(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setLastDt(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                    double closingBal = intProductDetail.get(i).getClosingBal();
                    double acctProduct = intProductDetail.get(i).getProduct();
                    double acctInt = intProductDetail.get(i).getTotalInt();
                    if (closingBal < 0.0) {
                        closingBal = -1 * closingBal;
                    }
                    if (acctProduct < 0.0) {
                        acctProduct = -1 * acctProduct;
                    }
                    if (acctInt < 0.0) {
                        acctInt = -1 * acctInt;
                    }
                    double noOfDays = intProductDetail.get(i).getClosingBal();
                    totalNoOfDays = noOfDays + totalNoOfDays;
                    totalProduct = acctProduct + totalProduct;
                    totalInt = acctInt + totalInt;
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    mt.setFdProduct(new BigDecimal(formatter.format(intProductDetail.get(i).getFdProduct())));
                    mt.setFdInt(new BigDecimal(formatter.format(intProductDetail.get(i).getFdInt())));
                    intCalc.add(mt);

                    pojo.setGroupBy("EMI");
                    pojo.setAcNo(intProductDetail.get(i).getAcNo());
                    pojo.setCustName(intProductDetail.get(i).getCustName());
                    pojo.setFromDate(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    pojo.setToDate(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    pojo.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    pojo.setInterestAmt(new BigDecimal(formatter.format(acctInt)));
                    pojo.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    pojo.setEmiAmt(new BigDecimal(formatter.format(intProductDetail.get(i).getPriAmt())));
                    reportList.add(pojo);
                }
            }
            
            resultList = beanRemote.accWiseLoanProjectedIntCalc(ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode().equalsIgnoreCase("90")?accountNo.substring(0, 2):getOrgnBrCode(),"N",0);
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                //List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
                //LoanMultiList la = new LoanMultiList();
                //lml = resultList;
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                //intDetails = resultList.get(0).getLoanIntDetails();
                totalNoOfDays = 0;
                totalProduct = 0;
                totalInt = 0;
                for (int i = 0; i < intProductDetail.size(); i++) {
                    IntCalTable mt = new IntCalTable();
                    InterestCalculationPojo pojo = new InterestCalculationPojo();
                    mt.setsNo(Integer.toString(i + 1));
                    mt.setAcNo(intProductDetail.get(i).getAcNo());
                    mt.setCustName(intProductDetail.get(i).getCustName());
                    mt.setFirstDt(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setLastDt(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                    double closingBal = intProductDetail.get(i).getClosingBal();
                    double acctProduct = intProductDetail.get(i).getProduct();
                    double acctInt = intProductDetail.get(i).getTotalInt();
                    if (closingBal < 0.0) {
                        closingBal = -1 * closingBal;
                    }
                    if (acctProduct < 0.0) {
                        acctProduct = -1 * acctProduct;
                    }
                    if (acctInt < 0.0) {
                        acctInt = -1 * acctInt;
                    }
                    double noOfDays = intProductDetail.get(i).getClosingBal();
                    totalNoOfDays = noOfDays + totalNoOfDays;
                    totalProduct = acctProduct + totalProduct;
                    totalInt = acctInt + totalInt;
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    intCalc.add(mt);
                    
                    pojo.setGroupBy("AcNo");
                    pojo.setAcNo(intProductDetail.get(i).getAcNo());
                    pojo.setCustName(intProductDetail.get(i).getCustName());
                    pojo.setFromDate(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    pojo.setToDate(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                    pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    pojo.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    pojo.setInterestAmt(new BigDecimal(formatter.format(acctInt)));
                    pojo.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    pojo.setEmiAmt(new BigDecimal(formatter.format(intProductDetail.get(i).getPriAmt())));
                    schemeCode = intProductDetail.get(i).getIntTableCode();
                    reportList.add(pojo);
                }
            }
            printReport(reportList, "LOAN_INT_CAL_REPORT_PROJECTED");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void printReport(List<InterestCalculationPojo> resultList, String jrxmlName) {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankDetailsList.size() > 0) {
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Loan Interest Product Calculation");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", sdf.format(fromDate));
                fillParams.put("pToDt", sdf.format(toDate));
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);
                fillParams.put("pPriAmt", priAmt);
                fillParams.put("pIntAmt", intAmt);
                fillParams.put("pAddress", custAdd);
                fillParams.put("pSchemeCode", schemeCode);
                
                new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Loan Interest Product Calculation");
                this.setReportFlag(true);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void refresh() {
        try {
            this.setReportFlag(false);
            setMessage("");
            setAccountNo("");
            this.setOldAcNo("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
            setTotalNoOfDays(0);
            setTotalProduct(0);
            setTotalInt(0);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void setDateAccountWise() {
        try {
            setMessage("");
            this.setReportFlag(false);
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0;
            totalProduct = 0;
            totalInt = 0;
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            }
            //if (oldAcNo.length() < 12) {
            if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please enter proper account no.");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(oldAcNo);

            String curBrnCode = ftsPostRemote.getCurrentBrnCode(accountNo);
            if (!(getOrgnBrCode().equalsIgnoreCase("90") || getOrgnBrCode().equalsIgnoreCase("0A"))) {
                if (!curBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                    this.setMessage("Account should be of self branch");
                    return;
                }
            }

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
            String resultList;
            resultList = beanRemote.acWiseFromDt(accountNo, getOrgnBrCode().equalsIgnoreCase("90")?accountNo.substring(0, 2):getOrgnBrCode());
            if (resultList.equalsIgnoreCase("Account Nature doesn't exists.")) {
                this.setMessage("Account Nature doesn't exists.");
                return;
            } else if (resultList.equalsIgnoreCase("Account No. Does Not Exist.")) {
                this.setMessage("Account No. Does Not Exist.");
                return;
            } else {
                this.setToDate(date);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
