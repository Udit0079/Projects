/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.CustIdDetaiPojo;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mayank
 */
public class IdMerge extends BaseBean {

    private String message;
    private String orgCode;
    private String mergCode;
    private List<CustIdDetaiPojo> gridDetail;
    private List<CustIdDetaiPojo> gridDetail1;
    private AcctEnqueryFacadeRemote remoteObject;
    TxnAuthorizationManagementFacadeRemote txnAuthRemote;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMergCode() {
        return mergCode;
    }

    public void setMergCode(String mergCode) {
        this.mergCode = mergCode;
    }

    public List<CustIdDetaiPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CustIdDetaiPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<CustIdDetaiPojo> getGridDetail1() {
        return gridDetail1;
    }

    public void setGridDetail1(List<CustIdDetaiPojo> gridDetail1) {
        this.gridDetail1 = gridDetail1;
    }

    public IdMerge() {
        try {
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("TxnAuthorizationManagementFacade");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getOrgDetails() {
        setMessage("");
        try {
            List list = remoteObject.getOriginalIdMergeId(this.getOrgCode());
            if (!list.isEmpty()) {
                this.setMessage("This customer Id is Already merge : " + this.getOrgCode());
                return;
            }
            if (!this.getOrgCode().equalsIgnoreCase("")) {
                gridDetail = new ArrayList<CustIdDetaiPojo>();
                gridDetail = remoteObject.getIdDetails(this.getOrgCode());
            } else {
                gridDetail = new ArrayList<CustIdDetaiPojo>();
                this.setMessage("Please Fill Original Id");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getMergDetails() {
        setMessage("");
        try {
            List list = remoteObject.getOriginalIdMergeId(this.getMergCode());
            if (!list.isEmpty()) {
                this.setMessage("This customer Id is Already merge : " + this.getMergCode());
                return;
            }
            if (!mergCode.equalsIgnoreCase("")) {
                gridDetail1 = new ArrayList<CustIdDetaiPojo>();
                gridDetail1 = remoteObject.getIdDetails(this.getMergCode());
            } else {
                gridDetail1 = new ArrayList<CustIdDetaiPojo>();
                this.setMessage("Please Fill To Be Merged Id");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setOrgCode("");
        this.setMergCode("");
        gridDetail = new ArrayList<CustIdDetaiPojo>();
        gridDetail1 = new ArrayList<CustIdDetaiPojo>();
    }

    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void saveDetails() {
        try {
            if (gridDetail.isEmpty()) {
                this.setMessage("Details Not Exist For Original Id");
                return;
            }
            if (gridDetail1.isEmpty()) {
                this.setMessage("Details Not Exist For To Be Merged Id");
                return;
            }

            if (this.getOrgCode().equalsIgnoreCase(this.getMergCode())) {
                this.setMessage("No Need To Merge Same ID");
                return;
            }

            String result = txnAuthRemote.idMergeSave(this.getOrgCode(), this.getMergCode(), this.getUserName(), this.getOrgnBrCode());

            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Id Merged Successfully");
                this.setOrgCode("");
                this.setMergCode("");
                gridDetail = new ArrayList<CustIdDetaiPojo>();
                gridDetail1 = new ArrayList<CustIdDetaiPojo>();
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}