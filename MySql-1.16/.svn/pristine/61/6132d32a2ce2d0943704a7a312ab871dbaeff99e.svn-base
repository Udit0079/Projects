package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareAuthorizationFacadeRemote;
import com.cbs.web.pojo.ho.ShareTransferAuth;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareTransferAuthorization {

    Context ctx;
    ShareAuthorizationFacadeRemote remoteObject;
    private HttpServletRequest req;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private List<ShareTransferAuth> shareData;
    private int currentRow;
    private boolean btnDisable;
    private ShareTransferAuth currentItem = new ShareTransferAuth();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public List<ShareTransferAuth> getShareData() {
        return shareData;
    }

    public void setShareData(List<ShareTransferAuth> shareData) {
        this.shareData = shareData;
    }

    public ShareTransferAuth getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ShareTransferAuth currentItem) {
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

    public ShareTransferAuthorization() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (ShareAuthorizationFacadeRemote) ServiceLocator.getInstance().lookup("ShareAuthorizationFacade");
            //setUserName("manager2");
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
        shareData = new ArrayList<ShareTransferAuth>();
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.shareTransferGridDetail();
            if (resultList == null || resultList.isEmpty()) {
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ShareTransferAuth detail = new ShareTransferAuth();
                    if (ele.get(0) != null) {
                        Date test = (Date) ele.get(0);
                        String dateLeave = dmy.format(test);
                        detail.setTrfDt(dateLeave);
                    }
                    if (ele.get(1) != null) {
                        detail.setCertNo(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        detail.setTrfrNo(ele.get(2).toString());
                    }
                    if (ele.get(4) != null) {
                        detail.setTrfrName(ele.get(4).toString());
                    }
                    if (ele.get(3) != null) {
                        detail.setTrfeNo(ele.get(3).toString());
                    }

                    if (ele.get(5) != null) {
                        detail.setTrfeName(ele.get(5).toString());
                    }
                    if (ele.get(8) != null) {
                        detail.setNoOfShare(ele.get(8).toString());
                    }
                    if (ele.get(6) != null) {
                        detail.setMinshare(ele.get(6).toString());
                    }
                    if (ele.get(7) != null) {
                        detail.setMaxShare(ele.get(7).toString());
                    }
                    if (ele.get(9) != null) {
                        detail.setAuth(ele.get(9).toString());
                    }
                    if (ele.get(10) != null) {
                        detail.setEnterby(ele.get(11).toString());
                    }
                    shareData.add(detail);
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
        for (ShareTransferAuth item : shareData) {
            if (item.getTrfDt().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void changeAuth() {
        this.setMessage("");
        ShareTransferAuth item = shareData.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterby().equalsIgnoreCase(this.userName)) {
                this.setMessage("You Cant Authorize YourSelf");
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                item.setAuth("Y");
                shareData.remove(currentRow);
                shareData.add(currentRow, item);
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            if (item.getEnterby().equalsIgnoreCase(this.userName)) {
                this.setMessage("You Cant Authorize YourSelf");
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                item.setAuth("N");
                shareData.remove(currentRow);
                shareData.add(currentRow, item);
            }
        }
    }
    List arraylist = new ArrayList();

    public void authorizeBtnAction() {
        arraylist = new ArrayList();
        String a[][] = new String[shareData.size()][11];
        for (int i = 0; i < shareData.size(); i++) {
            a[i][0] = shareData.get(i).getTrfDt().toString();
            a[i][1] = shareData.get(i).getCertNo().toString();
            a[i][2] = shareData.get(i).getTrfrNo().toString();
            a[i][3] = shareData.get(i).getTrfeNo().toString();
            a[i][4] = shareData.get(i).getTrfrName().toString();
            a[i][5] = shareData.get(i).getTrfeName().toString();
            a[i][6] = shareData.get(i).getMinshare().toString();
            a[i][7] = shareData.get(i).getMaxShare().toString();
            a[i][8] = shareData.get(i).getNoOfShare().toString();
            a[i][9] = shareData.get(i).getEnterby().toString();
            a[i][10] = shareData.get(i).getAuth().toString();
        }
        for (int i = 0; i < shareData.size(); i++) {
            if (a[i][10].equalsIgnoreCase("Y")) {
                for (int j = 0; j < 11; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
        }
        try {
            String result = remoteObject.shareTransferAuthorizeAction(this.arraylist, this.userName, this.orgnBrCode);
            if (result.equalsIgnoreCase("Authorization Successful")) {
                this.setMessage(result);
            } else {
                this.setMessage("Authorization not completed");
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

    public String btnExit() {
        return "case1";
    }

    public void btnRefrsh() {
        this.setMessage("");
        getTableAuth();
    }
}
