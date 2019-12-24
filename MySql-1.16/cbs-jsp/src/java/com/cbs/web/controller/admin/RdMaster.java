/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class RdMaster extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String accountNo;
    private Boolean disableReceiptNo;
    private String receiptNO;
    private String message;
    private Date date = new Date();
    CommonReportMethodsRemote RemoteCode;
    private ClgReportFacadeRemote beanRemote;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String errorMsg;
    private String customerName;
    private String fatherName;
    private String dob;
    private String oldOrNewAccountNo;

    public RdMaster() {
        try {
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getAlphacodeAllAndBranch("90");
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    //branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                    branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(brnVector.get(1).toString())), brnVector.get(0).toString()));

                }
            }

            List acList = new ArrayList();
            acList = RemoteCode.getAccType(CbsConstant.RECURRING_AC);
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--Select--"));
            if (!acList.isEmpty()) {
                for (int i = 0; i < acList.size(); i++) {
                    Vector brV = (Vector) acList.get(i);
                    acTypeList.add(new SelectItem(brV.get(0), brV.get(0).toString()));
                }
            }

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Boolean getDisableReceiptNo() {
        return disableReceiptNo;
    }

    public void setDisableReceiptNo(Boolean disableReceiptNo) {
        this.disableReceiptNo = disableReceiptNo;
    }

    public String getReceiptNO() {
        return receiptNO;
    }

    public void setReceiptNO(String receiptNO) {
        this.receiptNO = receiptNO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOldOrNewAccountNo() {
        return oldOrNewAccountNo;
    }

    public void setOldOrNewAccountNo(String oldOrNewAccountNo) {
        this.oldOrNewAccountNo = oldOrNewAccountNo;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String btnExitAction() {
        return "case1";
    }

    public void btnRefreshAction() {

        this.message = "";
        this.customerName = "";
        this.fatherName = "";
        this.dob = "";
        this.accountNo = "";
        this.receiptNO = "";
        this.setAcType("All");

    }

    public void getAccountDetails() {
        setMessage("");
        try {
            if (acType == null || acType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Account Type.");
                return;
            }
            if (!(this.accountNo.length() <= 6 || this.accountNo.length() == 12)) {
                this.setMessage("Please enter 6 digit or 12 digit Account No");
                return;
            }
            if (this.accountNo.length() <= 6) {
                this.setDisableReceiptNo(false);
            } else {
                this.setDisableReceiptNo(true);
            }
            if (this.accountNo.length() == 12) {
                List list = ftsPostRemote.getAccountDetails(this.branchCode, this.acType, this.accountNo);
                if (list.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                Vector ele = (Vector) list.get(0);
                this.oldOrNewAccountNo = ele.get(0).toString();
                this.customerName = ele.get(2).toString();
                this.fatherName = ele.get(3).toString();
                this.dob = ele.get(4).toString();
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getReceiptDetails() {
        setMessage("");
        try {
            if (this.receiptNO == null || this.receiptNO.equalsIgnoreCase("")) {
                this.receiptNO = "0";
            }
            List list = ftsPostRemote.getReceiptNo(this.branchCode, this.acType, this.accountNo, this.receiptNO);
            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Vector ele = (Vector) list.get(0);
            this.oldOrNewAccountNo = ele.get(1).toString();
            this.customerName = ele.get(2).toString();
            this.fatherName = ele.get(3).toString();
            this.dob = ele.get(4).toString();

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
