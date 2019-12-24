/*
Document   : AccountClosingAuthorization
Created on : 30 Oct, 2010, 3:25:00 PM
Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.AccountCloseauth;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class AccountClosingAuthorization {

    private HttpServletRequest req;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private List<AccountCloseauth> closeTable;
    private int currentRow;
    private boolean btnDisable;
    private AccountCloseauth currentItem = new AccountCloseauth();
    private final String jndiHomeName = "AccountAuthorizationManagementFacade";
    private AccountAuthorizationManagementFacadeRemote accountAuthRemote = null;

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public List<AccountCloseauth> getCloseTable() {
        return closeTable;
    }

    public void setCloseTable(List<AccountCloseauth> closeTable) {
        this.closeTable = closeTable;
    }

    public AccountCloseauth getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AccountCloseauth currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
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

    public List getArraylist() {
        return arraylist;
    }

    public void setArraylist(List arraylist) {
        this.arraylist = arraylist;
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

    public AccountClosingAuthorization() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager2");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            accountAuthRemote = (AccountAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getTableStock();
            this.setMessage("");
            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
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

    public void getTableStock() {
        closeTable = new ArrayList<AccountCloseauth>();
        try {
            List resultList = new ArrayList();

            resultList = accountAuthRemote.closedActGridDetail(this.orgnBrCode);
            if (resultList == null || resultList.isEmpty()) {
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    AccountCloseauth detail = new AccountCloseauth();
                    detail.setAccountNo(ele.get(0).toString());
                    String tmpCustName = accountAuthRemote.closeActCustName(ele.get(0).toString());
                    detail.setName(tmpCustName);
                    detail.setClossedby(ele.get(1).toString());
                    detail.setAuthorize(ele.get(2).toString());
                    closeTable.add(detail);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Name"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (AccountCloseauth item : closeTable) {
            if (item.getName().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void changeAuth() {

        this.setMessage("");
        AccountCloseauth item = closeTable.get(currentRow);
        if (item.getAuthorize().equalsIgnoreCase("N")) {
            if (item.getClossedby().equalsIgnoreCase(this.userName)) {
                this.setMessage("You Cant Authorize YourSelf");
                return;
            } else {
                item.setAuthorize("Y");
                closeTable.remove(currentRow);
                closeTable.add(currentRow, item);
            }
        } else if (item.getAuthorize().equalsIgnoreCase("Y")) {
            if (item.getClossedby().equalsIgnoreCase(this.userName)) {
                this.setMessage("You Cant Authorize YourSelf");
                return;
            } else {
                item.setAuthorize("N");
                closeTable.remove(currentRow);
                closeTable.add(currentRow, item);
            }
        }
    }
    List arraylist = new ArrayList();

    public void authorizeBtnAction() {
        //this.setMessage("");
        arraylist = new ArrayList();
        String a[][] = new String[closeTable.size()][9];
        for (int i = 0; i < closeTable.size(); i++) {
            a[i][0] = closeTable.get(i).getAccountNo().toString();
            a[i][1] = closeTable.get(i).getName().toString();
            a[i][2] = closeTable.get(i).getClossedby().toString();
            a[i][3] = closeTable.get(i).getAuthorize().toString();
        }
        for (int i = 0; i < closeTable.size(); i++) {
            if (a[i][3].equalsIgnoreCase("Y")) {
                for (int j = 0; j < 4; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
        }

        try {
            String result = accountAuthRemote.authorizeAction(this.arraylist, this.userName, this.orgnBrCode);
            if (result.contains("AUTHORIZATION SUCCESSFUL")) {
                this.setMessage(result);
            } else {
                this.setMessage("Authorization Not Completed");
                return;
            }
            getTableStock();
            arraylist = new ArrayList();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        return "case1";
    }

    public void btnRefrsh() {
        this.setMessage("");
        getTableStock();
    }
}
