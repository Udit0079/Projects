/*
 CREATED BY       :    ROHIT KRISHNA GUPTA
 CREATION DATE    :    03 DECEMBER 2010
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountEditCloseFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.admin.AccountCloseRegisterGrid;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class AccountClosingRegister extends BaseBean {

    AccountEditCloseFacadeRemote acCloseReg;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private String acctType;
    private String acctNo;
    private String acctNo1;
    private String custName;
    private String accStatus;
    private String todayTxnAmt;
    private String availBal;
    private String pendingBal;
    private String accInstruction;
    private String acNature;
    private boolean flag1;
    private boolean flag2;
    private List<AccountCloseRegisterGrid> gridDetail;
    private List<AccountCloseRegisterGrid> unusedChqDetail;
    int currentRow;
    private AccountCloseRegisterGrid currentItem = new AccountCloseRegisterGrid();
    private String sbIntCalFlag;
    private boolean intCalDisFlag;
    private boolean secDisFlag;
    private boolean lockerDisFlag;
    private boolean standInstDisFlag;
    private boolean emiDisFlag;
    private boolean acNoDisFlag;
    private String oldAcctNo;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String flag3 = "none";
    private String newStatus, acNoLen;
    private double closerCharge;
    private double serviceTax;
    private int accountClosureFlag;
    private String closureFlag = "none";

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAvailBal() {
        return availBal;
    }

    public void setAvailBal(String availBal) {
        this.availBal = availBal;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPendingBal() {
        return pendingBal;
    }

    public void setPendingBal(String pendingBal) {
        this.pendingBal = pendingBal;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayTxnAmt() {
        return todayTxnAmt;
    }

    public void setTodayTxnAmt(String todayTxnAmt) {
        this.todayTxnAmt = todayTxnAmt;
    }

    public AccountCloseRegisterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AccountCloseRegisterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<AccountCloseRegisterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AccountCloseRegisterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<AccountCloseRegisterGrid> getUnusedChqDetail() {
        return unusedChqDetail;
    }

    public void setUnusedChqDetail(List<AccountCloseRegisterGrid> unusedChqDetail) {
        this.unusedChqDetail = unusedChqDetail;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public String getSbIntCalFlag() {
        return sbIntCalFlag;
    }

    public void setSbIntCalFlag(String sbIntCalFlag) {
        this.sbIntCalFlag = sbIntCalFlag;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public boolean isEmiDisFlag() {
        return emiDisFlag;
    }

    public void setEmiDisFlag(boolean emiDisFlag) {
        this.emiDisFlag = emiDisFlag;
    }

    public boolean isIntCalDisFlag() {
        return intCalDisFlag;
    }

    public void setIntCalDisFlag(boolean intCalDisFlag) {
        this.intCalDisFlag = intCalDisFlag;
    }

    public boolean isLockerDisFlag() {
        return lockerDisFlag;
    }

    public void setLockerDisFlag(boolean lockerDisFlag) {
        this.lockerDisFlag = lockerDisFlag;
    }

    public boolean isSecDisFlag() {
        return secDisFlag;
    }

    public void setSecDisFlag(boolean secDisFlag) {
        this.secDisFlag = secDisFlag;
    }

    public boolean isStandInstDisFlag() {
        return standInstDisFlag;
    }

    public void setStandInstDisFlag(boolean standInstDisFlag) {
        this.standInstDisFlag = standInstDisFlag;
    }

    public boolean isAcNoDisFlag() {
        return acNoDisFlag;
    }

    public void setAcNoDisFlag(boolean acNoDisFlag) {
        this.acNoDisFlag = acNoDisFlag;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public double getCloserCharge() {
        return closerCharge;
    }

    public void setCloserCharge(double closerCharge) {
        this.closerCharge = closerCharge;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    public int getAccountClosureFlag() {
        return accountClosureFlag;
    }

    public void setAccountClosureFlag(int accountClosureFlag) {
        this.accountClosureFlag = accountClosureFlag;
    }

    public String getClosureFlag() {
        return closureFlag;
    }

    public void setClosureFlag(String closureFlag) {
        this.closureFlag = closureFlag;
    }

    public String getAcctNo1() {
        return acctNo1;
    }

    public void setAcctNo1(String acctNo1) {
        this.acctNo1 = acctNo1;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Creates a new instance of AccountClosingRegister
     */
    public AccountClosingRegister() {
        try {
            acCloseReg = (AccountEditCloseFacadeRemote) ServiceLocator.getInstance().lookup("AccountEditCloseFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag1(false);
            this.setFlag2(true);
            this.setIntCalDisFlag(true);
            this.setSecDisFlag(true);
            this.setLockerDisFlag(true);
            this.setEmiDisFlag(true);
            this.setStandInstDisFlag(true);
            this.setAcNoDisFlag(false);
            this.setFlag3("none");
            this.setNewStatus("");
            onLoadClosedAcGridLoad();
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equals("")) {
                setOldAcctNo("");
            } else {
                setOldAcctNo(getHttpRequest().getParameter("code").toString());
                customerDetail();
            }
            this.accountClosureFlag = ftsPostRemote.getCodeForReportName("ACCOUNT-CLOSING-CHARGES");
            if (this.accountClosureFlag == 1) {
                closureFlag = "";
            } else {
                closureFlag = "none";
            }

        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void onLoadClosedAcGridLoad() {
        gridDetail = new ArrayList<AccountCloseRegisterGrid>();
        try {
            List resultList = new ArrayList();
            resultList = acCloseReg.closedActGridDetail(this.getOrgnBrCode());
            //System.out.println("resultList:=======" + resultList);
            if (resultList == null || resultList.isEmpty()) {
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    AccountCloseRegisterGrid detail = new AccountCloseRegisterGrid();
                    detail.setAcctNo(ele.get(0).toString());
                    String tmpCustName = acCloseReg.closeActCustName(ele.get(0).toString());
                    detail.setCustName(tmpCustName);
                    detail.setCloseBy(ele.get(1).toString());
                    detail.setAuthorize(ele.get(2).toString());
                    gridDetail.add(detail);
                }
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void deleteClosedAcFromGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag2(true);
        try {
            String result = acCloseReg.deleteRecord(this.currentItem.getAcctNo());
            if (result == null) {
                this.setErrorMessage("PROBLEM OCCURED IN DELETION !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("RECORD DELETED SUCCESSFULLY .");
                    this.setAcNoDisFlag(false);
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
            this.setCustName("");
            this.setAcctType("");
            this.setAccStatus("");
            this.setTodayTxnAmt("");
            this.setAvailBal("");
            this.setPendingBal("");
            this.setAccInstruction("");
            this.setAcctNo("");
            this.setFlag1(false);
            unusedChqDetail = new ArrayList<AccountCloseRegisterGrid>();
            onLoadClosedAcGridLoad();
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void customerDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCustName("");
            this.setAcctType("");
            this.setAccStatus("");
            this.setTodayTxnAmt("");
            this.setAvailBal("");
            this.setPendingBal("");
            this.setAccInstruction("");
            this.setFlag1(false);
            this.setFlag2(true);
            this.setIntCalDisFlag(true);
            this.setSecDisFlag(true);
            this.setLockerDisFlag(true);
            this.setEmiDisFlag(true);
            this.setStandInstDisFlag(true);
            unusedChqDetail = new ArrayList<AccountCloseRegisterGrid>();
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
                return;
            }
            //if (this.oldAcctNo.length() < 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO. CAREFULLY.");
                this.setAcctNo("");
                return;
            }
            String INT_TRF_ACNO = "";
            INT_TRF_ACNO = ftsPostRemote.getIntTrfAcNo(oldAcctNo);
             acctNo = ftsPostRemote.getNewAccountNumber(oldAcctNo);
            if (INT_TRF_ACNO.equalsIgnoreCase("")) {
                acctNo1 = acctNo;
            } else {
                acctNo1 = acctNo + "," + INT_TRF_ACNO;
            }
            //Addition on 03/12/2015
            String result = acCloseReg.chkAutoPaidAcnoInTd(acctNo);
            if (!result.equals("")) {
                this.setErrorMessage("This a/c is registered as auto payment a/c in "
                        + "td for a/c-->" + result + " which is not close.");
                return;
            }
            String result1 = "";
            //End here
            result = acCloseReg.customerAcNoDetail(this.acctNo);
            if (!INT_TRF_ACNO.equalsIgnoreCase("")) {
                result1 = acCloseReg.customerAcNoDetail(INT_TRF_ACNO);
            }
            if (result == null) {
                this.setErrorMessage("PROBLEM IN GETTING ACCOUNT DETAIL !!!");
                this.setFlag2(true);
                this.setIntCalDisFlag(true);
                this.setSecDisFlag(true);
                this.setLockerDisFlag(true);
                this.setEmiDisFlag(true);
                this.setStandInstDisFlag(true);
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                    this.setAcctNo("");
                    this.setFlag2(true);
                    this.setIntCalDisFlag(true);
                    this.setSecDisFlag(true);
                    this.setLockerDisFlag(true);
                    this.setEmiDisFlag(true);
                    this.setStandInstDisFlag(true);
                    return;
                } else {
                    if (result.contains(":")) {
                        this.setIntCalDisFlag(false);
                        this.setSecDisFlag(false);
                        this.setLockerDisFlag(false);
                        this.setEmiDisFlag(false);
                        this.setStandInstDisFlag(false);
                        this.setAcNoDisFlag(true);
                        String[] values = null;
                        String[] values1 = null;
                        this.setFlag3("none");
                        this.setNewStatus("");
                        String acNat = ftsPostRemote.getAccountNature(acctNo);
                        if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            String intDiff = acCloseReg.dayDiffIntPost(this.acctNo);
                            int iDiff = Integer.parseInt(intDiff);
                            if (iDiff > 1) {
                                this.setFlag3("");
                                this.setNewStatus("Interest Posting is Pending in this Account");
                            } else {
                                this.setFlag3("none");
                                this.setNewStatus("");
                            }
                        }
                        try {
                            String spliter = ":";
                            values = result.split(spliter);
                            values1 = result1.split(spliter);
                            this.setAcctNo(values[0]);
                            this.setCustName(values[1]);
                            this.setAcctType(values[2]);
                            this.setAccStatus(values[3]);
                            this.setTodayTxnAmt(formatter.format(Double.parseDouble(values[4])));
                            if (!INT_TRF_ACNO.equalsIgnoreCase("")) {
                                this.setAvailBal(formatter.format(Double.parseDouble(values1[5])) + formatter.format(Double.parseDouble(values[5])));
                            } else {
                                this.setAvailBal(formatter.format(Double.parseDouble(values[5])));
                            }
                            this.setPendingBal(formatter.format(Double.parseDouble(values[6])));

                            if (accountClosureFlag == 1) {
                                double totalClosingCharge = 0d;
                                //double totalClosingCharge = acCloseReg.chargeAmt();
                                String[] val = null;
                                String s = acCloseReg.chargeAmt();
                                val = s.split(spliter);
                                String closerChgAmt = val[0];
                                String servTaxAmt = val[1];
                                this.closerCharge = Double.parseDouble(closerChgAmt);
                                this.serviceTax = Double.parseDouble(servTaxAmt);
                                totalClosingCharge = Double.parseDouble(closerChgAmt) + Double.parseDouble(servTaxAmt);

                                if (Double.parseDouble(this.availBal) == totalClosingCharge && Double.parseDouble(this.availBal) != 0) {
                                    this.setFlag2(false);
                                } else if (Double.parseDouble(this.availBal) < totalClosingCharge) {
                                    this.setFlag2(true);
                                    this.setErrorMessage("Account closure charges should be available in your account");
                                } else if (Double.parseDouble(this.availBal) > totalClosingCharge) {
                                    this.setFlag2(true);
                                    this.setErrorMessage("Available balance can't greater than Account closure charges");
                                }
                            } else {
                                if (this.availBal.equalsIgnoreCase("0.00") && this.pendingBal.equalsIgnoreCase("0.00")) {
                                    this.setFlag2(false);
                                } else {
                                    if (this.availBal.equalsIgnoreCase("-0.00") && this.pendingBal.equalsIgnoreCase("0.00")) {
                                        this.setFlag2(false);
                                    } else {
                                        this.setFlag2(true);
                                    }
                                }
                            }
                            if (values[7] == null || values[7].length() == 0 || values[7].equalsIgnoreCase("")) {
                                this.setAccInstruction("");
                            } else {
                                this.setAccInstruction(values[7]);
                            }
                            this.setAcNature(values[8]);
                        } catch (Exception e) {
                            setErrorMessage(e.getLocalizedMessage());
                        }
                        unusedChqDetail = new ArrayList<AccountCloseRegisterGrid>();
                        try {
                            List resultList = new ArrayList();
                            resultList = acCloseReg.custChqDetailForFreshChq(this.acctNo);
                            if (!resultList.isEmpty()) {
                                this.setFlag1(true);
                                for (int i = 0; i < resultList.size(); i++) {
                                    Vector ele = (Vector) resultList.get(i);
                                    AccountCloseRegisterGrid detail = new AccountCloseRegisterGrid();
                                    detail.setChqNo(ele.get(0).toString());
                                    detail.setIssueDt(ele.get(1).toString());
                                    unusedChqDetail.add(detail);
                                }
                            } else {
                                this.setFlag1(false);
                                return;
                            }
                        } catch (Exception ex) {
                            setErrorMessage(ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void closeAccountAction() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            //if (this.oldAcctNo.length() < 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("Please Enter Account No Carefully.");
                return;
            } else if (!ftsPostRemote.getCurrentBrnCode(acctNo).equalsIgnoreCase(getOrgnBrCode())) {
                setErrorMessage("This is not your branch's account no.");
                return;
            }

            if (accountClosureFlag == 1) {
                double totalClosingCharge = 0d;
                //double totalClosingCharge = acCloseReg.chargeAmt();
                String[] val = null;
                String s = acCloseReg.chargeAmt();
                val = s.split(":");
                String closerChgAmt = val[0];
                String servTaxAmt = val[1];
                totalClosingCharge = Double.parseDouble(closerChgAmt) + Double.parseDouble(servTaxAmt);
                if (Double.parseDouble(this.availBal) > totalClosingCharge) {
                    this.setErrorMessage("Available balance can't greater than Account closure charges");
                    return;
                }
            }

            String result = acCloseReg.closeAccountProcedure(this.acctNo, this.getUserName(), this.getTodayDate().substring(6) + this.getTodayDate().substring(3, 5) + this.getTodayDate().substring(0, 2), this.getOrgnBrCode());
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("SOME PROBLEM OCCURED IN CLOSING ACCOUNT !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("VALIDATION COMPLETED , PENDING FOR AUTHORIZATION !!!");
                    this.setAcNoDisFlag(false);
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
            this.setCustName("");
            this.setAcctType("");
            this.setAccStatus("");
            this.setTodayTxnAmt("");
            this.setAvailBal("");
            this.setPendingBal("");
            this.setAccInstruction("");
            this.setAcctNo("");
            this.setFlag1(false);
            this.setFlag3("none");
            this.setNewStatus("");
            this.setCloserCharge(0);
            this.setServiceTax(0);
            unusedChqDetail = new ArrayList<AccountCloseRegisterGrid>();
            onLoadClosedAcGridLoad();
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCustName("");
            this.setAcctType("");
            this.setAccStatus("");
            this.setTodayTxnAmt("");
            this.setAvailBal("");
            this.setPendingBal("");
            this.setAccInstruction("");
            this.setAcctNo("");
            this.setFlag1(false);
            this.setIntCalDisFlag(true);
            this.setSecDisFlag(true);
            this.setLockerDisFlag(true);
            this.setEmiDisFlag(true);
            this.setStandInstDisFlag(true);
            this.setAcNoDisFlag(false);
            this.setFlag3("none");
            this.setNewStatus("");
            this.setCloserCharge(0);
            this.setServiceTax(0);
            this.closureFlag = "none";
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void redirectOnSBInterestCalculation() {
        this.setErrorMessage("");
        this.setMessage("");
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
                return;
            }
            //if (this.oldAcctNo.length() != 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("PLEASE ENTER PROPER ACCOUNT NO.");
                return;
            }
            if (this.acNature == null || this.acNature.equalsIgnoreCase("") || this.acNature.length() == 0) {
                this.setErrorMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!");
                return;
            }
            //System.out.println("acNature:=======" + acNature);
            if (this.acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || this.acNature.equalsIgnoreCase(CbsConstant.MS_AC) || this.acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || this.acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || this.acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || this.acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                this.setErrorMessage("INTEREST CALCULATION FOR FD,RD,DS,PO ACCOUNT NATURE CANNOT BE DONE FROM HERE. !!!");
                this.setIntCalDisFlag(true);
                return;
            }
            if (this.acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || this.acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || this.acNature.equalsIgnoreCase(CbsConstant.CC_AC) || this.acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                // this.setIntCalDisFlag(true);
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/intcal/LoanInterestCalculation.jsp?code=" + acctNo);  //Changed by Ankit because of url change
            } else {
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/intcal/sbIntCalIndividual.jsp?code=" + acctNo);     //Changed by Ankit because of url change
            }
        } catch (IOException ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void redirectOnLockerSurr() {
        this.setErrorMessage("");
        this.setMessage("");
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
                return;
            }
            //if (this.acctNo.length() != 12) {
            if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("PLEASE ENTER PROPER ACCOUNT NO.");
                return;
            }
            if (this.acNature == null || this.acNature.equalsIgnoreCase("") || this.acNature.length() == 0) {
                this.setErrorMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!");
                return;
            }
            List resultList = new ArrayList();
            resultList = acCloseReg.lockerCkeck(this.acctNo);
            if (resultList.isEmpty()) {
                this.setLockerDisFlag(true);
                this.setErrorMessage("NO LOCKER EXISTS TO SURRENDER FOR THIS ACCOUNT !!!");
                return;
            }
            //this.setLockerDisFlag(true);
            ext.redirect(ext.getRequestContextPath() + "/faces/pages/other/LockerSurrender.jsp?code=" + acctNo);
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void redirectOnInstrCancel() {
        this.setErrorMessage("");
        this.setMessage("");
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
                return;
            }
            //if (this.acctNo.length() != 12) {
            if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("PLEASE ENTER PROPER ACCOUNT NO.");
                return;
            }
            if (this.acNature == null || this.acNature.equalsIgnoreCase("") || this.acNature.length() == 0) {
                this.setErrorMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!");
                return;
            }
            resultList = acCloseReg.instructionCkeck(this.acctNo);
            resultList1 = acCloseReg.instructionCkeck1(this.acctNo);
            if (resultList.isEmpty()) {
                if (resultList1.isEmpty()) {
                    this.setStandInstDisFlag(true);
                    this.setErrorMessage("NO STANDING INSTRUCTION EXISTS FOR THIS ACCOUNT !!!");
                    return;
                }
            }
            if (resultList1.isEmpty()) {
                if (resultList.isEmpty()) {
                    this.setStandInstDisFlag(true);
                    this.setErrorMessage("NO STANDING INSTRUCTION EXISTS FOR THIS ACCOUNT !!!");
                    return;
                }
            }
            //this.setStandInstDisFlag(true);
            ext.redirect(ext.getRequestContextPath() + "/faces/pages/other/SICancellation.jsp?code=" + acctNo);
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void redirectOnEMIMaster() {
        this.setErrorMessage("");
        this.setMessage("");
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
                return;
            }
            //if (this.acctNo.length() != 12) {
            if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("PLEASE ENTER PROPER ACCOUNT NO.");
                return;
            }
            if (this.acNature == null || this.acNature.equalsIgnoreCase("") || this.acNature.length() == 0) {
                this.setErrorMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!");
                return;
            }
            List resultList = new ArrayList();
            resultList = acCloseReg.emiCkeck(this.acctNo);
            if (resultList.isEmpty()) {
                this.setEmiDisFlag(true);
                this.setErrorMessage("NO EMI ARE PENDING FOR THIS ACCOUNT !!!");
                return;
            }
            //this.setEmiDisFlag(true);
            ext.redirect(ext.getRequestContextPath() + "/faces/pages/master/LoanEmiMaster.jsp?code=" + acctNo);
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void redirectOnSecurityDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
                return;
            }
            //if (this.acctNo.length() != 12) {
            if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMessage("PLEASE ENTER PROPER ACCOUNT NO.");
                return;
            }
            if (this.acNature == null || this.acNature.equalsIgnoreCase("") || this.acNature.length() == 0) {
                this.setErrorMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!");
                return;
            }
            List resultList = new ArrayList();
            resultList = acCloseReg.securityCkeck(this.acctNo);
            if (resultList.isEmpty()) {
                this.setSecDisFlag(true);
                this.setErrorMessage("NO SECURITIES ARE ACTIVE FOR THIS ACCOUNT !!!");
                return;
            }
            //this.setSecDisFlag(true);
            ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/SecurityDetails.jsp?code=" + acctNo + ":" + this.custName);
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
        return "case1";
    }
}
