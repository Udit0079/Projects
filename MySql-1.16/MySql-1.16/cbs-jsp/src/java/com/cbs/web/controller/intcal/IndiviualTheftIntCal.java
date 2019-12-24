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

/**
 *
 * @author Admin
 */
public class IndiviualTheftIntCal extends BaseBean {

    private String message;
    private String accountNo;
    private Date fromDate;
    private Date toDate;
    double totalNoOfDays;
    double totalInt;
    double totalProduct;
    double fdTotalProduct;
    double sbFdTotalInt;
    double totalFdInt;
    private List<IntCalTable> intCalc;
    private String acCloseFlag = null;
    LoanGenralFacadeRemote loanGenralFacade;
    private final String jndiHomeName = "LoanIntProductTestFacade";
    private LoanIntProductTestFacadeRemote beanRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    String oldAcNo;

    public double getSbFdTotalInt() {
        return sbFdTotalInt;
    }

    public void setSbFdTotalInt(double sbFdTotalInt) {
        this.sbFdTotalInt = sbFdTotalInt;
    }

    public double getFdTotalProduct() {
        return fdTotalProduct;
    }

    public void setFdTotalProduct(double fdTotalProduct) {
        this.fdTotalProduct = fdTotalProduct;
    }

    public double getTotalFdInt() {
        return totalFdInt;
    }

    public void setTotalFdInt(double totalFdInt) {
        this.totalFdInt = totalFdInt;
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
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public IndiviualTheftIntCal() {
        try {
            beanRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            intCalc = new ArrayList<IntCalTable>();
            this.setMessage("");
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
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0d;
            List<LoanMultiList> resultList = beanRemote.accWiseTheftIntProductCalc("", ymd.format(fromDate), ymd.format(toDate), accountNo, getOrgnBrCode());
            if (resultList.size() <= 0) {
                this.setMessage("Data does not exist");
                return;
            } else {
                //List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
                //LoanMultiList la = new LoanMultiList();
                 List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
                intProductDetail = resultList.get(0).getLoanProductIntDetails();
                //intDetails = resultList.get(0).getLoanIntDetails();
                totalNoOfDays = 0;
                totalProduct = 0;
                totalInt = 0;
                fdTotalProduct = 0;
                totalFdInt = 0;
                sbFdTotalInt = 0;
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
                    double acctFdProduct = intProductDetail.get(i).getFdProduct();
                    double acctFdInt = intProductDetail.get(i).getFdInt();
                    double fdRoi = intProductDetail.get(i).getFdRoi();
                    double sbFdTotalIntAmt = intProductDetail.get(i).getSbFdTotalIntAmt();

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
                    totalInt = acctInt + totalInt; // Total Sb Int
                    fdTotalProduct = acctFdProduct + fdTotalProduct;
                    totalFdInt = acctFdInt + totalFdInt; // Total Fd Int
                    //System.out.print(totalFdInt);
                    System.out.print(acctInt);
                    mt.setProduct(new BigDecimal(formatter.format(acctProduct))); //Sb Product
                    mt.setRoi(new BigDecimal(formatter.format(intProductDetail.get(i).getRoi())));//Sb Roi
                    mt.setTotalInt(new BigDecimal(formatter.format(acctInt))); //Sb int
                   // mt.setTotalInt(new BigDecimal(acctInt)); //Sb int

                    mt.setIntTableCode(intProductDetail.get(i).getIntTableCode());
                    mt.setFdInt(new BigDecimal(acctFdInt)); //Fd int
                    mt.setFdProduct(new BigDecimal(acctFdProduct));//Fd Product
                    mt.setFdRoi(new BigDecimal(fdRoi));//Fd Roi
                   // mt.setSbFdTotalInt(new BigDecimal(acctInt + acctFdInt));
                    mt.setSbFdTotalInt(new BigDecimal(sbFdTotalIntAmt));
                 
                    sbFdTotalInt = totalFdInt + totalInt;

                    intCalc.add(mt);
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
            intCalc = new ArrayList<IntCalTable>();
            totalNoOfDays = 0;
            totalProduct = 0;
            totalInt = 0;
            if (oldAcNo == null || oldAcNo.equalsIgnoreCase("")) {
                setMessage("Please enter the account no.");
                return;
            }
            if (oldAcNo.length() < 12) {
                setMessage("Please enter 12 digits account no.");
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

            intCalc = new ArrayList<IntCalTable>();

            String resultList;
            resultList = beanRemote.acWiseFromDt(accountNo, getOrgnBrCode());
            if (resultList.equalsIgnoreCase("Account Nature doesn't exists.")) {
                this.setMessage("Account Nature doesn't exists.");
                return;
            } else if (resultList.equalsIgnoreCase("Account No. Does Not Exist.")) {
                this.setMessage("Account No. Does Not Exist.");
                return;
            } else {
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(resultList))));
                  //this.setToDate(date);
                String yyyy = ymd.format(date).substring(0, 4);
                List dtList = beanRemote.acWiseFromDtToDt(yyyy, curBrnCode);
                Vector dtv = (Vector) dtList.get(0);
                //  setFromDate(ymd.parse(dtv.get(0).toString()));
                setToDate(ymd.parse(dtv.get(1).toString()));
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
