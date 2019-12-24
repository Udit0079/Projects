/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.CashierCashRecievedFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.misc.ActiveCashierGridDetail;
import com.cbs.web.pojo.misc.CashierCashDetailGridDetail;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class CashierOpeningBalanceRegister {

    Context ctx;
    CashierCashRecievedFacadeRemote remoteObject;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private List<ActiveCashierGridDetail> activeUserGridDetail;
    int currentRow;
    private ActiveCashierGridDetail currentItem = new ActiveCashierGridDetail();
    private String userId;
    private String cashierName;
    private String cashCounterNo;
    private List<CashierCashDetailGridDetail> cashInSafeDetail;
    private boolean saveFlag;
    private boolean updateFlag;

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public List<CashierCashDetailGridDetail> getCashInSafeDetail() {
        return cashInSafeDetail;
    }

    public void setCashInSafeDetail(List<CashierCashDetailGridDetail> cashInSafeDetail) {
        this.cashInSafeDetail = cashInSafeDetail;
    }

    public String getCashCounterNo() {
        return cashCounterNo;
    }

    public void setCashCounterNo(String cashCounterNo) {
        this.cashCounterNo = cashCounterNo;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ActiveCashierGridDetail> getActiveUserGridDetail() {
        return activeUserGridDetail;
    }

    public void setActiveUserGridDetail(List<ActiveCashierGridDetail> activeUserGridDetail) {
        this.activeUserGridDetail = activeUserGridDetail;
    }

    public ActiveCashierGridDetail getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ActiveCashierGridDetail currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
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
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

    /** Creates a new instance of CashierOpeningBalanceRegister */
    public CashierOpeningBalanceRegister() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            remoteObject = (CashierCashRecievedFacadeRemote) ServiceLocator.getInstance().lookup("CashierCashRecievedFacade");
            setUserName(req.getRemoteUser());
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            gridOnload();
            cashInSafeGridLoad();
            this.setSaveFlag(true);
            this.setUpdateFlag(false);
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

    public void gridOnload() {
        try {
            activeUserGridDetail = new ArrayList<ActiveCashierGridDetail>();
            List resultList = new ArrayList();
            resultList = remoteObject.gridLoadCashier(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ActiveCashierGridDetail detail = new ActiveCashierGridDetail();
                    detail.setSno(i + 1);
                    detail.setName(ele.get(0).toString());
                    detail.setUserId(ele.get(1).toString());
                    detail.setCounterNo(ele.get(2).toString());
                    detail.setStatus(ele.get(3).toString());
                    activeUserGridDetail.add(detail);
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
            resultList = remoteObject.safeDenominitionGridDetail(this.orgnBrCode, enterDt);
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

    public void getUserInformation() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCashierName("");
            this.setSaveFlag(true);
            this.setUpdateFlag(false);
            if (this.userId == null || this.userId.equalsIgnoreCase("") || this.userId.length() == 0) {
                this.setErrorMessage("Please enter user ID !!!");
                return;
            }
            String result = remoteObject.getUserNameMethod(this.userId, this.orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                return;
            } else {
                this.setCashierName(result);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void assignCounterToCashier() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.userId == null || this.userId.equalsIgnoreCase("") || this.userId.length() == 0) {
                this.setErrorMessage("Please enter user ID !!!");
                return;
            }
            if (this.cashierName == null || this.cashierName.equalsIgnoreCase("") || this.cashierName.length() == 0) {
                this.setErrorMessage("Cashier name could not be blank !!!");
                return;
            }
            if (this.cashCounterNo == null || this.cashCounterNo.equalsIgnoreCase("") || this.cashCounterNo.length() == 0) {
                this.setErrorMessage("Please enter counter number !!!");
                return;
            }
            Matcher cashCounterNoCheck = p.matcher(this.cashCounterNo);
            if (!cashCounterNoCheck.matches()) {
                this.setErrorMessage("Please enter only numeric values in cash counter number field !!!");
                return;
            }
            if (this.cashCounterNo.contains(".")) {
                this.setErrorMessage("Please enter only numeric values in cash counter number field !!!");
                return;
            }
            String result = remoteObject.activateCashierId(this.userId, this.cashierName, this.cashCounterNo, this.userName, this.orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
            } else {
                this.setMessage(result);
            }
            gridOnload();
            this.setUserId("");
            this.setCashierName("");
            this.setCashCounterNo("");
            this.setSaveFlag(true);
            this.setUpdateFlag(false);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setSaveFlag(false);
            this.setUpdateFlag(true);
            this.setErrorMessage("");
            this.setMessage("");
            this.setUserId(currentItem.getUserId());
            this.setCashierName(currentItem.getName());
            this.setCashCounterNo(currentItem.getCounterNo());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateCounterOfCashier() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.userId == null || this.userId.equalsIgnoreCase("") || this.userId.length() == 0) {
                this.setErrorMessage("Please enter user ID !!!");
                return;
            }
            if (this.cashierName == null || this.cashierName.equalsIgnoreCase("") || this.cashierName.length() == 0) {
                this.setErrorMessage("Cashier name could not be blank !!!");
                return;
            }
            if (this.cashCounterNo == null || this.cashCounterNo.equalsIgnoreCase("") || this.cashCounterNo.length() == 0) {
                this.setErrorMessage("Please enter counter number !!!");
                return;
            }
            Matcher cashCounterNoCheck = p.matcher(this.cashCounterNo);
            if (!cashCounterNoCheck.matches()) {
                this.setErrorMessage("Please enter only numeric values in cash counter number field !!!");
                return;
            }
            if (this.cashCounterNo.contains(".")) {
                this.setErrorMessage("Please enter only numeric values in cash counter number field !!!");
                return;
            }
            String result = remoteObject.updateCashierCounter(this.userId, this.cashierName, this.cashCounterNo, this.userName, this.orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
            } else {
                this.setMessage(result);
            }
            gridOnload();
            this.setUserId("");
            this.setCashierName("");
            this.setCashCounterNo("");
            this.setSaveFlag(true);
            this.setUpdateFlag(false);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetActiveUserPanel() {
        try {
            this.setSaveFlag(true);
            this.setUpdateFlag(false);
            this.setErrorMessage("");
            this.setMessage("");
            this.setUserId("");
            this.setCashierName("");
            this.setCashCounterNo("");
            gridOnload();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetActiveUserPanel();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
