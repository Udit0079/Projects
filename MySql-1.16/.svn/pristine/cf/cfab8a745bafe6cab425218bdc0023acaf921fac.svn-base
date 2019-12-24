/*
 * CREATED BY       :  ROHIT KRISHNA GUPTA
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.FdRenewalActOldDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.txn.FdRenewalAuthPendingAcctList;
import com.cbs.facade.txn.TdAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class FdRenewalAuthorization extends BaseBean{

    private String actNo = "";
    private String batchNo = "";
    private String errorMessage;
    private String message;
    public String auth;
    public String enterby;
    private List<FdRenewalAuthPendingAcctList> pendingAcctList;
    private List<FdRenewalActOldDetail> oldDetailList;
    private List<FdRenewalActOldDetail> newDetailList;
    int currentRow;
    FdRenewalAuthPendingAcctList currentItem;
    private final String jndiHomeName = "TdAuthorizationManagementFacade";
    private TdAuthorizationManagementFacadeRemote tdAuthRemote = null;

    
    public FdRenewalAuthPendingAcctList getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(FdRenewalAuthPendingAcctList currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<FdRenewalActOldDetail> getOldDetailList() {
        return oldDetailList;
    }

    public void setOldDetailList(List<FdRenewalActOldDetail> oldDetailList) {
        this.oldDetailList = oldDetailList;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public List<FdRenewalActOldDetail> getNewDetailList() {
        return newDetailList;
    }

    public void setNewDetailList(List<FdRenewalActOldDetail> newDetailList) {
        this.newDetailList = newDetailList;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<FdRenewalAuthPendingAcctList> getPendingAcctList() {
        return pendingAcctList;
    }

    public void setPendingAcctList(List<FdRenewalAuthPendingAcctList> pendingAcctList) {
        this.pendingAcctList = pendingAcctList;
    }

    public String getVouchNo() {
        return vouchNo;
    }

    public void setVouchNo(String vouchNo) {
        this.vouchNo = vouchNo;
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

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    /** Creates a new instance of FdRenewalAuthorization */
    public FdRenewalAuthorization() {
        try {
            tdAuthRemote = (TdAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            pendingAcctGridLoad();
            this.setErrorMessage("");
            this.setMessage("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    String vouchNo;

    public void pendingAcctGridLoad() {
        pendingAcctList = new ArrayList<FdRenewalAuthPendingAcctList>();
        try {
            List resultList = new ArrayList();
            resultList = tdAuthRemote.accountNoList(this.getOrgnBrCode());
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                FdRenewalAuthPendingAcctList fd = new FdRenewalAuthPendingAcctList();
                fd.setAcctNo(ele.get(0).toString());
                fd.setVoucherNo(ele.get(1).toString());
                fd.setBatchNo(ele.get(2).toString());
                pendingAcctList.add(fd);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void loadOldAndNewGrid() {
        this.setErrorMessage("");
        this.setMessage("");
        oldDetailList = new ArrayList<FdRenewalActOldDetail>();
        newDetailList = new ArrayList<FdRenewalActOldDetail>();

        try {
            this.setActNo(currentItem.getAcctNo());
            this.setBatchNo(currentItem.getBatchNo());
            String batNo = currentItem.getBatchNo();
            List<FdRenewalActOldDetail> dataList = tdAuthRemote.getOldAndNewData(batNo, this.getOrgnBrCode());
            if (dataList.size() > 0) {
                oldDetailList = (List<FdRenewalActOldDetail>) dataList.get(0);
                newDetailList = (List<FdRenewalActOldDetail>) dataList.get(1);
            }
            if (oldDetailList.isEmpty()) {
                resetForm();
                return;
            }
            if (newDetailList.isEmpty()) {
                resetForm();
                return;
            }
            for (FdRenewalActOldDetail object : newDetailList) {
                auth = object.getAuth();
                enterby = object.getEnterBy();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void accountAuthorization() {

        if (pendingAcctList.isEmpty()) {
            this.setErrorMessage("There is no data to authorize.");
            return;
        }
        if ((this.actNo.equalsIgnoreCase("")) || (this.actNo.length() == 0) || (this.batchNo.equalsIgnoreCase("")) || (this.batchNo.length() == 0)) {
            this.setErrorMessage("Please select a row from Pending List To Be Authorized table");
            return;
        }
        if (enterby.equalsIgnoreCase(getUserName())) {
            this.setErrorMessage("Sorry You Can't Authorize Yourself.");
            return;
        }
        try {
            String saveResult = tdAuthRemote.authorizeAction(this.getUserName(), this.batchNo, this.getOrgnBrCode());
            resetForm();
            this.setMessage(saveResult);
            pendingAcctGridLoad();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setAuth("");
        this.setActNo("");
        this.setBatchNo("");
        this.setEnterby("");
        oldDetailList = new ArrayList<FdRenewalActOldDetail>();
        newDetailList = new ArrayList<FdRenewalActOldDetail>();
        pendingAcctGridLoad();
    }

    public String exitFrm() {
        resetForm();
        return "case1";
    }
}
