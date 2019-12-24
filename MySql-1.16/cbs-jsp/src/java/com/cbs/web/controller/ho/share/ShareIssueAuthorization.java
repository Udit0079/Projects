package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareAuthorizationFacadeRemote;
import com.cbs.web.pojo.ho.ShareHolderAuth;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareIssueAuthorization extends BaseBean {

    ShareAuthorizationFacadeRemote remoteObject;
    private List<ShareHolderAuth> shareHolder;
    private List<SelectItem> txnNoList;
    private String txnNo;

    private String message;
    private int currentRow;
    private boolean btnDisable;
    private ShareHolderAuth currentItem = new ShareHolderAuth();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public List<ShareHolderAuth> getShareHolder() {
        return shareHolder;
    }

    public void setShareHolder(List<ShareHolderAuth> shareHolder) {
        this.shareHolder = shareHolder;
    }

    public ShareHolderAuth getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ShareHolderAuth currentItem) {
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

    public String getTxnNo() {
        return txnNo;
    }

    public void setTxnNo(String txnNo) {
        this.txnNo = txnNo;
    }

    public List<SelectItem> getTxnNoList() {
        return txnNoList;
    }

    public void setTxnNoList(List<SelectItem> txnNoList) {
        this.txnNoList = txnNoList;
    }

    public ShareIssueAuthorization() {
        try {
            shareHolder = new ArrayList<ShareHolderAuth>();
            remoteObject = (ShareAuthorizationFacadeRemote) ServiceLocator.getInstance().lookup("ShareAuthorizationFacade");
            getPendingShares();
            this.setMessage("");
            btnDisable = true;
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getPendingShares() {
        try {
            txnNoList = new ArrayList<SelectItem>();
            List list = remoteObject.getPendingShares(getOrgnBrCode());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    txnNoList.add(new SelectItem(ele.get(0).toString()));
                }
            } else {
                setMessage("There is not pending Share Issue transaction");
            }
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getShareDetails() {
        shareHolder = new ArrayList<>();
        try {
            
            List resultList = remoteObject.shareIssuedDetails(Long.parseLong(txnNo.equals("") ? "0" : txnNo), ymd.format(new Date()));
            if (resultList == null || resultList.isEmpty()) {
                btnDisable = true;
                return;
            } else {
                //btnDisable = false;
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ShareHolderAuth detail = new ShareHolderAuth();
                    detail.setsNo(++i);
                    if (ele.get(0) != null) {
                        detail.setShareNoFrom(Long.parseLong(ele.get(0).toString()));
                    }
                    if (ele.get(1) != null) {
                        detail.setShareNoTo(Long.parseLong(ele.get(1).toString()));
                    }
                    detail.setNoOfShare(Long.parseLong(ele.get(1).toString()) - Long.parseLong(ele.get(0).toString()) + 1);
                    if (ele.get(2) != null) {
                        detail.setIssueBy(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        detail.setIssueDt(ele.get(3).toString());
                    }
                    if (ele.get(4) != null) {
                        detail.setAuth(ele.get(4).toString());
                    }
                    shareHolder.add(detail);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void changeAuth() {
        this.setMessage("");
        ShareHolderAuth item = shareHolder.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getIssueBy().equalsIgnoreCase(getUserName())) {
                this.setMessage("You can not authorize your self");
                btnDisable = true;
                return;
            } else {
                boolean found = false;
                for (ShareHolderAuth item1 : shareHolder) {
                    if (item1.getAuth().equalsIgnoreCase("Y")) {
                        found = true;
                    }
                }
                if (found) {
                    this.setMessage("Only one transction can be authorize at one time.");
                } else {
                    btnDisable = false;
                    item.setAuth("Y");
                    shareHolder.remove(currentRow);
                    shareHolder.add(currentRow, item);
                }
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            if (item.getIssueBy().equalsIgnoreCase(getUserName())) {
                this.setMessage("You can not authorize your self");
                btnDisable = true;
                return;
            } else {
                btnDisable = true;
                item.setAuth("N");
                shareHolder.remove(currentRow);
                shareHolder.add(currentRow, item);
            }
        }
    }

    public void authorizeBtnAction() {
        try {
            for (ShareHolderAuth shareAuth : shareHolder) {
                if (shareAuth.getAuth().equalsIgnoreCase("Y")) {
                    String result = remoteObject.shareIssueAuthorizeAction(shareAuth.getShareNoFrom(), shareAuth.getShareNoTo(), getUserName(), this.getOrgnBrCode());
                    if (result.equalsIgnoreCase("True")) {
                        this.setMessage("Authorization successfull");
                    } else {
                        this.setMessage("Authorization not completed");
                    }
                    getPendingShares();
                    shareHolder = new ArrayList<ShareHolderAuth>();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void btnDeleteAction() {
        try {
            this.setMessage("");
            ShareHolderAuth item = shareHolder.get(currentRow);
            String result = remoteObject.deleteUnAuthShares(item.getShareNoFrom(), item.getShareNoTo());
            if (result.equalsIgnoreCase("True")) {
                this.setMessage("Share Stock successfully deleted");
            } else {
                this.setMessage("Authorization not completed");
            }
            getPendingShares();
            shareHolder = new ArrayList<ShareHolderAuth>();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExit() {
        return "case1";
    }

    public void btnRefrsh() {
        this.setMessage("");
        getPendingShares();
        shareHolder = new ArrayList<ShareHolderAuth>();
    }
}
