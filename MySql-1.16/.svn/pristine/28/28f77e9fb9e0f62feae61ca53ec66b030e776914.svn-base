/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.ho.DividendAuthTable;
import com.cbs.facade.ho.share.ShareAuthorizationFacadeRemote;
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
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class DividendAuthorization {

    Context ctx;
    ShareAuthorizationFacadeRemote remoteObject;
    private HttpServletRequest req;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private List<DividendAuthTable> closeTable;
    private int currentRow;
    private boolean btnDisable;
    private DividendAuthTable currentItem = new DividendAuthTable();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public List<DividendAuthTable> getCloseTable() {
        return closeTable;
    }

    public void setCloseTable(List<DividendAuthTable> closeTable) {
        this.closeTable = closeTable;
    }

    public DividendAuthTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(DividendAuthTable currentItem) {
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

    public DividendAuthorization() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (ShareAuthorizationFacadeRemote) ServiceLocator.getInstance().lookup("ShareAuthorizationFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            getTableAuth();
            this.setMessage("");
            btnDisable = true;
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

    public void getTableAuth() {
        closeTable = new ArrayList<DividendAuthTable>();
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.gridDetail();
            if (resultList == null || resultList.isEmpty()) {
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    DividendAuthTable detail = new DividendAuthTable();
                    if (ele.get(0) != null) {
                        detail.setName(ele.get(0).toString());

                    }
                    if (ele.get(1) != null) {
                        detail.setAcno(ele.get(1).toString().substring(2, 10));
                    }
                    if (ele.get(2) != null) {
                        detail.setTxnId(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        detail.setCrAmt(ele.get(3).toString());
                    }
                    if (ele.get(4) != null) {
                        detail.setDrAmt(ele.get(4).toString());
                    }
                    if (ele.get(5) != null) {
                        Date test = (Date) ele.get(5);
                        String dateLeave = dmy.format(test);
                        detail.setTrnTime(dateLeave);
                    }
                    if (ele.get(6) != null) {
                        detail.setEnterBy(ele.get(6).toString());
                    }
                    if (ele.get(7) != null) {
                        detail.setAuth(ele.get(7).toString());
                    }
                    if (ele.get(8) != null) {
                        detail.setDetails(ele.get(8).toString());
                    }
                    closeTable.add(detail);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Name"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DividendAuthTable item : closeTable) {
            if (item.getName().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void changeAuth() {
        this.setMessage("");
        DividendAuthTable item = closeTable.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.userName)) {
                this.setMessage("You Cant Authorize YourSelf");
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                item.setAuth("Y");
                closeTable.remove(currentRow);
                closeTable.add(currentRow, item);
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            if (item.getEnterBy().equalsIgnoreCase(this.userName)) {
                this.setMessage("You Cant Authorize YourSelf");
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                item.setAuth("N");
                closeTable.remove(currentRow);
                closeTable.add(currentRow, item);
            }
        }
    }
    List arraylist = new ArrayList();

    public void authorizeBtnAction() {
        arraylist = new ArrayList();
        String a[][] = new String[closeTable.size()][9];
        for (int i = 0; i < closeTable.size(); i++) {
            a[i][0] = closeTable.get(i).getName().toString();
            a[i][1] = closeTable.get(i).getAcno().toString();
            a[i][2] = closeTable.get(i).getTxnId().toString();
            a[i][3] = closeTable.get(i).getCrAmt().toString();
            a[i][4] = closeTable.get(i).getDrAmt().toString();
            a[i][5] = closeTable.get(i).getTrnTime().toString();
            a[i][6] = closeTable.get(i).getEnterBy().toString();
            a[i][7] = closeTable.get(i).getAuth().toString();
            a[i][8] = closeTable.get(i).getDetails().toString();
        }
        for (int i = 0; i < closeTable.size(); i++) {
            if (a[i][8].equalsIgnoreCase("Y")) {
                for (int j = 0; j < 9; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
        }
        try {
            String result = remoteObject.authorizeAction(this.arraylist, this.userName, this.orgnBrCode);
            if (result.equalsIgnoreCase("Authorization Successful")) {
                this.setMessage(result);
            } else {
                this.setMessage("Authorization Successful");
                return;
            }
            getTableAuth();
            arraylist = new ArrayList();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void deleteBtnAction() {
        if (currentItem.getTxnId() == null || currentItem.getTxnId().equalsIgnoreCase("")) {
            this.setMessage("Please Select the Row To which You Want To Delete");
            return;
        }
        try {
            String result = remoteObject.deleteDividendAction(currentItem.getTxnId());
            this.setMessage(result);
            getTableAuth();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        return "case1";
    }

    public void btnRefrsh() {
        this.setMessage("");
        getTableAuth();
    }
}
