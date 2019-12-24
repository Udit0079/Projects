/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.CustIdDetaiPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author mayank
 */
public class IdMergeAuth extends BaseBean {

    private String message;
    private List<CustIdDetaiPojo> idMergeList, gridDetail1, gridDetail2;
    int currentRow;
    CustIdDetaiPojo currentItem;
    boolean authorizeValue;
    TxnAuthorizationManagementFacadeRemote txnAuthRemote;
    private AcctEnqueryFacadeRemote remoteObject;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustIdDetaiPojo> getIdMergeList() {
        return idMergeList;
    }

    public void setIdMergeList(List<CustIdDetaiPojo> idMergeList) {
        this.idMergeList = idMergeList;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public CustIdDetaiPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CustIdDetaiPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isAuthorizeValue() {
        return authorizeValue;
    }

    public void setAuthorizeValue(boolean authorizeValue) {
        this.authorizeValue = authorizeValue;
    }

    public List<CustIdDetaiPojo> getGridDetail1() {
        return gridDetail1;
    }

    public void setGridDetail1(List<CustIdDetaiPojo> gridDetail1) {
        this.gridDetail1 = gridDetail1;
    }

    public List<CustIdDetaiPojo> getGridDetail2() {
        return gridDetail2;
    }

    public void setGridDetail2(List<CustIdDetaiPojo> gridDetail2) {
        this.gridDetail2 = gridDetail2;
    }

    public IdMergeAuth() {
        try {
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("TxnAuthorizationManagementFacade");
            getIdUnAuth();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    private void getIdUnAuth() {
        try {
            idMergeList = new ArrayList<CustIdDetaiPojo>();
            List resultList = new ArrayList();
            resultList = txnAuthRemote.getUnAuthMergId(this.getOrgnBrCode());
            if (resultList.isEmpty()) {
                throw new ApplicationException("There is no data for authorization");
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector element = (Vector) resultList.get(i);
                CustIdDetaiPojo custIdP = new CustIdDetaiPojo();

                custIdP.setsNo(i + 1);
                custIdP.setOrgId(element.get(0).toString());
                custIdP.setMergId(element.get(1).toString());

                custIdP.setAuth(element.get(2).toString());
                custIdP.setEnterBy(element.get(3).toString());

                idMergeList.add(custIdP);
            }

        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeAuth() {
        this.setMessage("");
        try {
            CustIdDetaiPojo item = idMergeList.get(currentRow);

            gridDetail1 = remoteObject.getIdDetails(item.getOrgId());
            gridDetail2 = remoteObject.getIdDetails(item.getMergId());

            if (item.getAuth().equalsIgnoreCase("N")) {
                if (item.getEnterBy().equalsIgnoreCase(this.getUserName())) {
                    this.setMessage("You cannot authorize your own entry");
                    authorizeValue = true;
                } else {
                    boolean found1 = false;
                    for (CustIdDetaiPojo item1 : idMergeList) {
                        if (item1.getAuth().equalsIgnoreCase("Y")) {
                            found1 = true;
                        }
                    }
                    if (found1) {
                        this.setMessage("Only one entry can be authorize at one time.");
                    } else {
                        item.setAuth("Y");
                        idMergeList.remove(currentRow);
                        idMergeList.add(currentRow, item);
                        authorizeValue = false;
                    }
                }
            } else if (item.getAuth().equalsIgnoreCase("Y")) {
                item.setAuth("N");
                idMergeList.remove(currentRow);
                idMergeList.add(currentRow, item);
                authorizeValue = true;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnAuthAction() {
        this.setMessage("");
        try {
            for (int j = 0; j < idMergeList.size(); j++) {
                CustIdDetaiPojo items = idMergeList.get(j);
                if (items.getAuth().equals("Y")) {
                    String authFinalMsg = txnAuthRemote.mergeIdAuth(items.getOrgId(), items.getMergId(), items.getEnterBy(), this.getUserName(), getOrgnBrCode());

                    if (authFinalMsg.equalsIgnoreCase("TRUE")) {
                        btnRefreshAction();
                        this.setMessage("Authorization successfully completed");
                    } else {
                        this.setMessage(authFinalMsg);
                    }
                    authorizeValue = true;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnDeleteAction() {
        try {
            this.setMessage("");
            String s1 = txnAuthRemote.mergeIdDeletion(currentItem.getOrgId(), currentItem.getMergId(), this.getUserName());
            this.setMessage(s1);
            getIdUnAuth();
            authorizeValue = true;
            gridDetail1 = new ArrayList<CustIdDetaiPojo>();
            gridDetail2 = new ArrayList<CustIdDetaiPojo>();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String btnRefreshAction() {
        getIdUnAuth();
        gridDetail1 = new ArrayList<CustIdDetaiPojo>();
        gridDetail2 = new ArrayList<CustIdDetaiPojo>();
        return null;
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
