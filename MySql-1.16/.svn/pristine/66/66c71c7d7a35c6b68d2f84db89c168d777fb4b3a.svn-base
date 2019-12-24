/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.CashierCashRecievedFacadeRemote;
import com.cbs.web.pojo.misc.CashierCashDetailGridDetail;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class CashierCurrencyClose {

    Context ctx;
    CashierCashRecievedFacadeRemote remoteObject;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private Validator validator;
    private String activeCashier;
    private List<SelectItem> activeCashierList;
    private String openingBal;
    private String cashRecieved;
    private String subTotal;
    private String cashPayment;
    private String drAmtThroughTran;
    private String drAmtForDemand;
    private String crAmtToDemand;
    private String closingBal;
    private String cashCounterAssigned;
    private String statusOfCounter;
    private String cashierType;
    private List<CashierCashDetailGridDetail> cashInSafeDetail;
    private double totalAmt;

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public List<CashierCashDetailGridDetail> getCashInSafeDetail() {
        return cashInSafeDetail;
    }

    public void setCashInSafeDetail(List<CashierCashDetailGridDetail> cashInSafeDetail) {
        this.cashInSafeDetail = cashInSafeDetail;
    }

    public String getCashierType() {
        return cashierType;
    }

    public void setCashierType(String cashierType) {
        this.cashierType = cashierType;
    }

    public String getCashCounterAssigned() {
        return cashCounterAssigned;
    }

    public void setCashCounterAssigned(String cashCounterAssigned) {
        this.cashCounterAssigned = cashCounterAssigned;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    }

    public String getCashRecieved() {
        return cashRecieved;
    }

    public void setCashRecieved(String cashRecieved) {
        this.cashRecieved = cashRecieved;
    }

    public String getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(String closingBal) {
        this.closingBal = closingBal;
    }

    public String getCrAmtToDemand() {
        return crAmtToDemand;
    }

    public void setCrAmtToDemand(String crAmtToDemand) {
        this.crAmtToDemand = crAmtToDemand;
    }

    public String getDrAmtForDemand() {
        return drAmtForDemand;
    }

    public void setDrAmtForDemand(String drAmtForDemand) {
        this.drAmtForDemand = drAmtForDemand;
    }

    public String getDrAmtThroughTran() {
        return drAmtThroughTran;
    }

    public void setDrAmtThroughTran(String drAmtThroughTran) {
        this.drAmtThroughTran = drAmtThroughTran;
    }

    public String getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(String openingBal) {
        this.openingBal = openingBal;
    }

    public String getStatusOfCounter() {
        return statusOfCounter;
    }

    public void setStatusOfCounter(String statusOfCounter) {
        this.statusOfCounter = statusOfCounter;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getActiveCashier() {
        return activeCashier;
    }

    public void setActiveCashier(String activeCashier) {
        this.activeCashier = activeCashier;
    }

    public List<SelectItem> getActiveCashierList() {
        return activeCashierList;
    }

    public void setActiveCashierList(List<SelectItem> activeCashierList) {
        this.activeCashierList = activeCashierList;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
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

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of CashierCurrencyClose */
    public CashierCurrencyClose() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (CashierCashRecievedFacadeRemote) ServiceLocator.getInstance().lookup("CashierCashRecievedFacade");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            validator = new Validator();
            this.setErrorMessage("");
            this.setMessage("");
            onloadActiveCashier();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void onloadActiveCashier() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.activeCashierCombo(this.orgnBrCode);
            activeCashierList = new ArrayList<SelectItem>();
            activeCashierList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele1 = (Vector) resultList.get(i);
                    activeCashierList.add(new SelectItem(ele1.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void activeCashierOnBlur() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setOpeningBal("0.00");
            this.setCashRecieved("0.00");
            this.setSubTotal("0.00");
            this.setCashPayment("0.00");
            this.setDrAmtThroughTran("0.00");
            this.setDrAmtForDemand("0.00");
            this.setCrAmtToDemand("0.00");
            this.setClosingBal("0.00");
            this.setCashCounterAssigned("");
            this.setStatusOfCounter("");
            this.setCashierType("");
            this.setTotalAmt(0.0d);
            cashInSafeDetail = new ArrayList<CashierCashDetailGridDetail>();
            if (this.activeCashier.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select active cashier.");
                return;
            }
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            String result = remoteObject.loadCashierData(this.activeCashier, this.orgnBrCode, enterDt);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains(":")) {
                String[] values = null;
                try {
                    String spliter = ":";
                    values = result.split(spliter);
                    this.setOpeningBal(formatter.format(Double.parseDouble(values[0])));
                    this.setCashRecieved(formatter.format(Double.parseDouble(values[1])));
                    this.setSubTotal(formatter.format(Double.parseDouble(values[2])));
                    this.setCashPayment(formatter.format(Double.parseDouble(values[3])));
                    this.setDrAmtThroughTran(formatter.format(Double.parseDouble(values[4])));
                    this.setDrAmtForDemand(formatter.format(Double.parseDouble(values[5])));
                    this.setCrAmtToDemand(formatter.format(Double.parseDouble(values[6])));
                    this.setClosingBal(formatter.format(Double.parseDouble(values[7])));
                    this.setTotalAmt(Double.parseDouble(values[7]));
                    this.setCashCounterAssigned(values[8]);
                    this.setStatusOfCounter(values[9]);
                    this.setCashierType(values[10]);
                    cashInSafeGridLoad();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void cashInSafeGridLoad() {
        try {
            cashInSafeDetail = new ArrayList<CashierCashDetailGridDetail>();
            List resultList = new ArrayList();
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            resultList = remoteObject.cashierDenominitionGridDetail(this.activeCashier, this.orgnBrCode, enterDt);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    CashierCashDetailGridDetail detail = new CashierCashDetailGridDetail();
                    detail.setCashierName(ele.get(0).toString());
                    detail.setAmount(Double.parseDouble(ele.get(1).toString()));
                    detail.setDate(ele.get(2).toString());
                    detail.setRs1000(Integer.parseInt(ele.get(3).toString()));
                    detail.setRs500(Integer.parseInt(ele.get(4).toString()));
                    detail.setRs100(Integer.parseInt(ele.get(5).toString()));
                    detail.setRs50(Integer.parseInt(ele.get(6).toString()));
                    detail.setRs20(Integer.parseInt(ele.get(7).toString()));
                    detail.setRs10(Integer.parseInt(ele.get(8).toString()));
                    detail.setRs5(Integer.parseInt(ele.get(9).toString()));
                    detail.setRs2(Integer.parseInt(ele.get(10).toString()));
                    detail.setRs1(Integer.parseInt(ele.get(11).toString()));
                    detail.setPaise50(Integer.parseInt(ele.get(12).toString()));
                    cashInSafeDetail.add(detail);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void cashCloseBtnAction() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.activeCashier.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select active cashier.");
                return;
            }
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            String result = remoteObject.cashCloseAction(this.activeCashier, this.orgnBrCode, enterDt, this.totalAmt);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
            } else {
                this.setMessage(result);
            }
            this.setOpeningBal("");
            this.setCashRecieved("");
            this.setSubTotal("");
            this.setCashPayment("");
            this.setDrAmtThroughTran("");
            this.setDrAmtForDemand("");
            this.setCrAmtToDemand("");
            this.setClosingBal("");
            this.setCashCounterAssigned("");
            this.setStatusOfCounter("");
            this.setCashierType("");
            this.setTotalAmt(0.0d);
            this.setActiveCashier("--Select--");
            onloadActiveCashier();
            cashInSafeDetail = new ArrayList<CashierCashDetailGridDetail>();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setOpeningBal("");
            this.setCashRecieved("");
            this.setSubTotal("");
            this.setCashPayment("");
            this.setDrAmtThroughTran("");
            this.setDrAmtForDemand("");
            this.setCrAmtToDemand("");
            this.setClosingBal("");
            this.setCashCounterAssigned("");
            this.setStatusOfCounter("");
            this.setCashierType("");
            this.setTotalAmt(0.0d);
            this.setActiveCashier("--Select--");
            onloadActiveCashier();
            cashInSafeDetail = new ArrayList<CashierCashDetailGridDetail>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
