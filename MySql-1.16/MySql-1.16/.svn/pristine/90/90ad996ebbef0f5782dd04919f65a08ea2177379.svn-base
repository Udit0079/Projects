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
import com.cbs.facade.intcal.LoanPenalProductCalFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.intcal.IntCalTable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class LoanPenalProductCal extends BaseBean {

    private String message;
    private String accountNo;
    private Date fromDate;
    private Date toDt;
    double totalNoOfDays;
    double totalInt;
    double totalProduct;
    LoanIntProductTestFacadeRemote testEJB;
    private List<IntCalTable> intCalc;
    private String acCloseFlag = null;
    LoanGenralFacadeRemote loanGenralFacade;
    private final String loanPenalProductCalcJndiName = "LoanPenalProductCalFacade";
    private LoanPenalProductCalFacadeRemote loanPenalProductCalcRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private LoanInterestCalculationFacadeRemote loanIntCalc ;
    String oldAccNo, acNoLen;

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

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

    public LoanPenalProductCal() {
        try {
            loanPenalProductCalcRemote = (LoanPenalProductCalFacadeRemote) ServiceLocator.getInstance().lookup(loanPenalProductCalcJndiName);
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            loanIntCalc = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            this.setAcNoLen(getAcNoLength());
            intCalc = new ArrayList<IntCalTable>();
            setMessage("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void calculate() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        intCalc = new ArrayList<IntCalTable>();
        String option = "I";
        int sno = 0;
        totalNoOfDays = 0;
        totalProduct = 0;
        totalInt = 0;
        try {
            setMessage("");
            if (oldAccNo == null || oldAccNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account number");
                return;
            }
            //if (oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please enter proper account no.");
                return;
            }

            List result;
            result = loanGenralFacade.accountDetail(accountNo);
            if (result.isEmpty()) {
                this.setMessage("Account number does not exist");
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
            if (fromDate == null) {
                setMessage("Please select From Date.");
                return;
            }
            if (toDt == null) {
                setMessage("Please select To Date.");
                return;
            }
            if (fromDate != null && toDt != null) {
                if (fromDate.after(toDt)) {
                    setMessage(" To date can not be greater current Date");
                    this.setToDt(date);
                    return;
                }
            }
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0d;
            String acctcode = ftsPostRemote.getAccountCode(accountNo);
            List<LoanMultiList> lml = loanPenalProductCalcRemote.cbsLoanPenalCalculation(option, acctcode, accountNo, sdf.format(fromDate), sdf.format(toDt), getUserName(), getOrgnBrCode());
            if (lml.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                for (int j = 0; j < lml.size(); j++) {
                    intProductDetail = lml.get(j).getLoanProductIntDetails();
                    if (intProductDetail.size() <= 0) {
                        this.setMessage("Data does not exist");
                        return;
                    } else {
                        for (int i = 0; i < intProductDetail.size(); i++) {
                            IntCalTable mt = new IntCalTable();
                            sno = sno + 1;
                            mt.setsNo(Integer.toString(sno));
                            mt.setAcNo(intProductDetail.get(i).getAcNo());
                            mt.setCustName(intProductDetail.get(i).getCustName());
                            mt.setFirstDt(intProductDetail.get(i).getFirstDt());
                            mt.setLastDt(intProductDetail.get(i).getLastDt());
                            mt.setClosingBal(new BigDecimal(formatter.format(intProductDetail.get(i).getClosingBal())));
                            double acctProduct = -1 * intProductDetail.get(i).getProduct();
                            double acctInt = -1 * intProductDetail.get(i).getTotalInt();
                            double noOfDays = intProductDetail.get(i).getClosingBal();
                            if (acctProduct < 0.0) {
                                acctProduct = -1 * acctProduct;
                            }
                            if (acctInt < 0.0) {
                                acctInt = -1 * acctInt;
                            }
                            totalNoOfDays = noOfDays + totalNoOfDays;
                            totalProduct = acctProduct + totalProduct;
                            totalInt = acctInt + totalInt;
                            mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                            mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));
                            mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                            mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                            mt.setDetails(intProductDetail.get(i).getDetails());
                            if (acctInt <= 0.0) {
                                sno = sno - 1;
                            } else {
                                intCalc.add(mt);
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setMessage("");
            setAccountNo("");
            this.setOldAccNo("");
            setTotalNoOfDays(0d);
            setTotalInt(0d);
            setTotalProduct(0d);
            setToDt(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }

    public void setDateAccountWise() {
        try {
            setMessage("");
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0;
            totalProduct = 0;
            totalInt = 0;
            if (oldAccNo == null || oldAccNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account number");
                return;
            }
            //if (oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please enter proper account no.");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            String curBrCode = ftsPostRemote.getCurrentBrnCode(accountNo);
            if (!curBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                setMessage("Account should be of self branch.");
                return;
            }
            setToDt(sdf.parse(getTodayDate()));
            setFromDate(sdf.parse(getTodayDate()));
            intCalc = new ArrayList<IntCalTable>();
            String resultList = loanIntCalc.acWiseFromDt(accountNo, getOrgnBrCode());
            if ((resultList != null) || (!resultList.equalsIgnoreCase(""))) {
                this.setToDt(date);
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
