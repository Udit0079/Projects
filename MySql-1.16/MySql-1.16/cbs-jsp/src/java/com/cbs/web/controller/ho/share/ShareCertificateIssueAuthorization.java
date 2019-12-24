package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareAuthorizationFacadeRemote;
import com.cbs.web.pojo.ho.ShareCertificateAuth;
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
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareCertificateIssueAuthorization {

    Context ctx;
    ShareAuthorizationFacadeRemote remoteObject;
    private HttpServletRequest req;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private List<ShareCertificateAuth> closeTable;
    private int currentRow;
    private boolean btnDisable;
    private ShareCertificateAuth currentItem = new ShareCertificateAuth();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public List<ShareCertificateAuth> getCloseTable() {
        return closeTable;
    }

    public void setCloseTable(List<ShareCertificateAuth> closeTable) {
        this.closeTable = closeTable;
    }

    public ShareCertificateAuth getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ShareCertificateAuth currentItem) {
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

    public ShareCertificateIssueAuthorization() {
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
        closeTable = new ArrayList<ShareCertificateAuth>();
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.shareIssuegridDetail();
            if (resultList == null || resultList.isEmpty()) {
                btnDisable = true;
                return;
            } else {
                btnDisable = false;
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ShareCertificateAuth detail = new ShareCertificateAuth();
                    if (ele.get(0) != null) {
                        detail.setRegfolioNo(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        detail.setName(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        detail.setCertNo(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        Date test = (Date) ele.get(3);
                        String dateLeave = dmy.format(test);
                        detail.setIssueDt(dateLeave);
                    }
                    if (ele.get(4) != null) {
                        detail.setEnterBy(ele.get(4).toString());
                    }
                    if (ele.get(5) != null) {
                        detail.setAuth(ele.get(5).toString());
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

    public void changeAuth() {
        this.setMessage("");
        ShareCertificateAuth item = closeTable.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.userName)) {
                this.setMessage("You can not authorize your self");
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
                this.setMessage("You can not authorize your self");
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

    public void authorizeBtnAction() {
        try {
            int certNo = 0;
            for(ShareCertificateAuth item : closeTable){
                if(item.getAuth().equalsIgnoreCase("Y")){
                    certNo = Integer.parseInt(item.getCertNo());
                    break;
                }
            }
            String result = remoteObject.shareCertificateAuthorization(certNo, this.userName, this.orgnBrCode);
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
