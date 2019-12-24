/*
Document   : TdRecieptIssueAuthorization
Created on : 29 Oct, 2010, 11:55:50 AM
Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.TdAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.TdIssueAuthorize;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author root
 */
public class TdRecieptIssueAuthorization extends BaseBean{

    private float stRNfrom;
    private float stRNTo;
    private String stBookNo;
    private float stSNo;
    private String scheme;
    private String series;
    private String authorise;
    private String message;
    private boolean flag;
    private boolean btnSaveDisable;
    private boolean snoInvisible;
    private List<TdIssueAuthorize> authtable;
    private int currentRow;
    private TdIssueAuthorize currentItem = new TdIssueAuthorize();
    private boolean btnDisable;
    String authFlag;
    private final String jndiHomeName = "TdAuthorizationManagementFacade";
    private TdAuthorizationManagementFacadeRemote tdAuthRemote = null;

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public boolean isSnoInvisible() {
        return snoInvisible;
    }

    public void setSnoInvisible(boolean snoInvisible) {
        this.snoInvisible = snoInvisible;
    }

    public boolean isBtnSaveDisable() {
        return btnSaveDisable;
    }

    public void setBtnSaveDisable(boolean btnSaveDisable) {
        this.btnSaveDisable = btnSaveDisable;
    }

    public String getAuthorise() {
        return authorise;
    }

    public void setAuthorise(String authorise) {
        this.authorise = authorise;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<TdIssueAuthorize> getAuthtable() {
        return authtable;
    }

    public void setAuthtable(List<TdIssueAuthorize> authtable) {
        this.authtable = authtable;
    }

    public TdIssueAuthorize getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdIssueAuthorize currentItem) {
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

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getStBookNo() {
        return stBookNo;
    }

    public void setStBookNo(String stBookNo) {
        this.stBookNo = stBookNo;
    }

    public float getStRNTo() {
        return stRNTo;
    }

    public void setStRNTo(float stRNTo) {
        this.stRNTo = stRNTo;
    }

    public float getStRNfrom() {
        return stRNfrom;
    }

    public void setStRNfrom(float stRNfrom) {
        this.stRNfrom = stRNfrom;
    }

    public float getStSNo() {
        return stSNo;
    }

    public void setStSNo(float stSNo) {
        this.stSNo = stSNo;
    }

    public TdRecieptIssueAuthorization() {
        try {
            tdAuthRemote = (TdAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getTableStock();
            this.setMessage("");
            setFlag(false);
            setSnoInvisible(false);
            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableStock() {
        authtable = new ArrayList<TdIssueAuthorize>();
        try {
            List resultLt = new ArrayList();
            resultLt = tdAuthRemote.tableAuthorize(getOrgnBrCode());

            if (!resultLt.isEmpty()) {
                btnDisable = true;
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    TdIssueAuthorize rd = new TdIssueAuthorize();
                    rd.setScheme(ele.get(0).toString());
                    rd.setBookNo(ele.get(1).toString());
                    rd.setSeries(ele.get(2).toString());
                    rd.setrNoFrom(Float.parseFloat(ele.get(3).toString()));
                    rd.setrNoTo(Float.parseFloat(ele.get(4).toString()));
                    rd.setLeaves(Integer.parseInt(ele.get(5).toString()));
                    String date = (ele.get(6).toString());
                    String issueDt = date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
                    rd.setIssueDt(issueDt);
                    rd.setEnterBy(ele.get(7).toString());
                    rd.setAuth(ele.get(8).toString());
                    rd.setSno(Float.parseFloat(ele.get(9).toString()));
                    authtable.add(rd);
                }
            } else {
                btnDisable = true;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    /************************** END Table Data functionality************************/
    /************************** Select Button functionality************************/
    public void select() {
        this.setMessage("");
        TdIssueAuthorize item = authtable.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.getUserName())) {
                this.setMessage("You cannot authorize your own entry");
                btnDisable = true;
                return;
            } else {
                boolean found1 = false;
                for (TdIssueAuthorize item1 : authtable) {
                    if (item1.getAuth().equalsIgnoreCase("Y")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setMessage("Only one transction can be authorize at one time.");
                    btnDisable = true;
                    return;
                } else {
                    item.setAuth("Y");
                    authtable.remove(currentRow);
                    authtable.add(currentRow, item);
                    btnDisable = false;
                    authFlag = item.getAuth();
                }
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            item.setAuth("N");
            authtable.remove(currentRow);
            authtable.add(currentRow, item);
            btnDisable = true;
            authFlag = item.getAuth();
            return;
        }


        this.setMessage("You Have Selected Book No. " + currentItem.getBookNo() + " Of Series " + currentItem.getSeries() + " And RN. From " + currentItem.getrNoFrom() + " RN. To " + currentItem.getrNoTo());
        setStRNfrom(currentItem.getrNoFrom());
        setStRNTo(currentItem.getrNoTo());
        setStBookNo(currentItem.getBookNo());
        setStSNo(currentItem.getSno());
        setScheme(currentItem.getScheme());
        setSeries(currentItem.getSeries());
        setAuthorise(currentItem.getAuth());

    }

    /************************** END Select Button functionality************************/
    /************************** Save Button functionality************************/
    public void saveBtnAction() {
        TdIssueAuthorize item = authtable.get(currentRow);
        if (currentItem.getBookNo() == null || currentItem.getBookNo().equalsIgnoreCase("") || currentItem.getSeries() == null || currentItem.getSeries().equalsIgnoreCase("")) {
            this.setMessage("Please Select Any Row from the Table for Authorization");
        } else {
            this.setMessage("");
            if (authorise.equals(getUserName())) {

                this.setMessage("You Can't Authorize Your Own Entry");
            } else if (authorise.equals("N")) {
                setBtnSaveDisable(true);
            }
            try {
                if (item.getAuth().equalsIgnoreCase("Y")) {
                    String result = tdAuthRemote.tdIssueAuthorize(getUserName(), stSNo, stRNfrom, stRNTo, stBookNo, series, scheme, getOrgnBrCode());
                    if (result.equalsIgnoreCase("true")) {
                        this.setMessage("Authorization Complete Successfully");
                        btnDisable = true;
                        getTableStock();
                    } else {
                        this.setMessage(result);
                    }
                }
            } catch (ApplicationException e) {
                this.setMessage(e.getMessage());
            } catch (Exception e) {
                this.setMessage(e.getLocalizedMessage());
            }
        }
    }

    public void Clear() {
        this.setMessage("");
        getTableStock();
    }

    public String btnExit() {
        this.setMessage("");
        return "case1";
    }
}
