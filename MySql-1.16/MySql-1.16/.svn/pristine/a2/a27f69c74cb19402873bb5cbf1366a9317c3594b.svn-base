/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.LoanMultiList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.dto.ProvisionalCertificatePojo;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author saurabhsipl
 */
public class ProvisionalCertificate extends BaseBean {

    private String message;
    private String accountNo;
    private Date fromDate;
    private Date toDate;
    double totalNoOfDays;
    double totalInt;
    double totalProduct;
    private List<ProvisionalCertificatePojo> intCalc;
    private String acCloseFlag = null;
    LoanGenralFacadeRemote loanGenralFacade;
    private final String jndiHomeName = "LoanIntProductTestFacade";
    private LoanIntProductTestFacadeRemote beanRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    String oldAcNo,acNoLen;

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

    public List<ProvisionalCertificatePojo> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<ProvisionalCertificatePojo> intCalc) {
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
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public ProvisionalCertificate() {
        try {
            beanRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            intCalc = new ArrayList<ProvisionalCertificatePojo>();
            this.setAcNoLen(getAcNoLength());
            this.setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
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
            ProvisionalCertificatePojo it = new ProvisionalCertificatePojo();
            List<ProvisionalCertificatePojo> intProductDetail = new ArrayList<ProvisionalCertificatePojo>();

            intCalc = new ArrayList<ProvisionalCertificatePojo>();
            List<LoanMultiList> resultList = beanRemote.accWiseLoanIntProductCalcProvisional(ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode());
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                for (int i = 0; i < intProductDetail.size(); i++) {
                    ProvisionalCertificatePojo mt = new ProvisionalCertificatePojo();
                    mt.setsNo(Integer.toString(i + 1));
                    mt.setAcNo(intProductDetail.get(i).getAcNo());
                    mt.setCustName(intProductDetail.get(i).getCustName());
                    mt.setFirstDt(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setLastDt(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                    double noOfDays = intProductDetail.get(i).getClosingBal().doubleValue();
                    double acctProduct = intProductDetail.get(i).getProduct().doubleValue();
                    double acctInt = intProductDetail.get(i).getTotalInt().doubleValue();
                    
                    if (acctProduct < 0) {
                        acctProduct = acctProduct * -1;
                    }
                    if (acctInt < 0) {
                        acctInt = acctInt * -1;
                    }
                    totalNoOfDays = noOfDays + totalNoOfDays;
                    totalProduct = acctProduct + totalProduct;
                    totalInt = acctInt + totalInt;
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct * noOfDays)));
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    intCalc.add(mt);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            System.out.println("Error Is "+ e);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void printPDF() {
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
            ProvisionalCertificatePojo it = new ProvisionalCertificatePojo();
            List<ProvisionalCertificatePojo> intProductDetail = new ArrayList<ProvisionalCertificatePojo>();
            intCalc = new ArrayList<ProvisionalCertificatePojo>();
            List<LoanMultiList> resultList = beanRemote.accWiseLoanIntProductCalcProvisional(ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode());
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                BigDecimal totalNoOfDays = new BigDecimal("0");
                BigDecimal totalProduct = new BigDecimal("0");
                BigDecimal totalInt = new BigDecimal("0");
                for (int i = 0; i < intProductDetail.size(); i++) {
                    ProvisionalCertificatePojo mt = new ProvisionalCertificatePojo();
                    mt.setsNo(Integer.toString(i + 1));
                    mt.setAcNo(intProductDetail.get(i).getAcNo());
                    mt.setCustName(intProductDetail.get(i).getCustName());
                    mt.setAcNat(intProductDetail.get(i).getAcNat());
                    mt.setFirstDt(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setLastDt(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setFromDate(sdf.format(ymmd.parse(intProductDetail.get(i).getFirstDt())));
                    mt.setToDate(sdf.format(ymmd.parse(intProductDetail.get(i).getLastDt())));
                    mt.setSanctionAmt(intProductDetail.get(i).getSanctionAmt());
                    mt.setSanctionDt(intProductDetail.get(i).getSanctionDt());
                    mt.setJointName1(intProductDetail.get(i).getJointName1());
                    mt.setJointName2(intProductDetail.get(i).getJointName2());
                    mt.setJointName3(intProductDetail.get(i).getJointName3());
                    mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                    mt.setCurAdd(intProductDetail.get(i).getCurAdd());
                    BigDecimal closingBal = intProductDetail.get(i).getClosingBal();
                    BigDecimal acctProduct = intProductDetail.get(i).getProduct();
                    BigDecimal acctInt = intProductDetail.get(i).getTotalInt();
                    if (closingBal.compareTo(new BigDecimal("0")) < 0) {
                        closingBal = closingBal.multiply(new BigDecimal("-1"));
                    }
                    if (acctProduct.compareTo(new BigDecimal("0")) < 0) {
                        acctProduct = acctProduct.multiply(new BigDecimal("-1"));
                    }
                    if (acctInt.compareTo(new BigDecimal("0")) < 0) {
                        acctInt = acctInt.multiply(new BigDecimal("-1"));
                    }
                    BigDecimal noOfDays = intProductDetail.get(i).getClosingBal();
                    totalNoOfDays = noOfDays.add(totalNoOfDays);
                    totalProduct = acctProduct.add(totalProduct);
                    totalInt = acctInt.add(totalInt);
                    mt.setInterestAmt(acctInt);
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    intCalc.add(mt);
                }

                String report = "Provisional Certificate";
                List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Loan Interest Provisional Certificate");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", sdf.format(fromDate));
                fillParams.put("pToDt", sdf.format(toDate));
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);
                new ReportBean().openPdf("ProvisionalCertificate" + accountNo, "ProvisionalCertificate", new JRBeanCollectionDataSource(intCalc), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            }
        } catch (ApplicationException e) {
            System.out.println("Error is " + e);
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error is " + e);
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setMessage("");
            setAccountNo("");
            this.setOldAcNo("");
            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<ProvisionalCertificatePojo>();
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
            intCalc = new ArrayList<ProvisionalCertificatePojo>();
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
            if (!curBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                this.setMessage("Account should be of self branch");
                return;
            }

            setToDate(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<ProvisionalCertificatePojo>();
            String resultList;
            resultList = beanRemote.acWiseFromDt(accountNo, getOrgnBrCode());
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
