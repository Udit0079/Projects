/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentShare;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class ShareClose extends BaseBean {

    private String message;
    private String ctrlNo;
    private String securityDetail;
    private String shareCertificate;
    private String bank;
    private String branch;
    private String purDate;
    private String faceValue;
    private String drHead;
    private String drHeadName;
    private String branchName;
    private boolean enableCertificate;
    private boolean enableBranch;
    private boolean enableSaveButton;
    private List<SelectItem> ctrlNoList;
    private List<SelectItem> bankList;
    private List<SelectItem> branchList;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<SelectItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<SelectItem> bankList) {
        this.bankList = bankList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(String ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public List<SelectItem> getCtrlNoList() {
        return ctrlNoList;
    }

    public void setCtrlNoList(List<SelectItem> ctrlNoList) {
        this.ctrlNoList = ctrlNoList;
    }

    public String getDrHead() {
        return drHead;
    }

    public void setDrHead(String drHead) {
        this.drHead = drHead;
    }

    public String getDrHeadName() {
        return drHeadName;
    }

    public void setDrHeadName(String drHeadName) {
        this.drHeadName = drHeadName;
    }

    public boolean isEnableBranch() {
        return enableBranch;
    }

    public void setEnableBranch(boolean enableBranch) {
        this.enableBranch = enableBranch;
    }

    public boolean isEnableCertificate() {
        return enableCertificate;
    }

    public void setEnableCertificate(boolean enableCertificate) {
        this.enableCertificate = enableCertificate;
    }

    public boolean isEnableSaveButton() {
        return enableSaveButton;
    }

    public void setEnableSaveButton(boolean enableSaveButton) {
        this.enableSaveButton = enableSaveButton;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPurDate() {
        return purDate;
    }

    public void setPurDate(String purDate) {
        this.purDate = purDate;
    }

    public String getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(String securityDetail) {
        this.securityDetail = securityDetail;
    }

    public String getShareCertificate() {
        return shareCertificate;
    }

    public void setShareCertificate(String shareCertificate) {
        this.shareCertificate = shareCertificate;
    }

    public ShareClose() {
        ctrlNoList = new ArrayList<SelectItem>();
        branchList = new ArrayList<SelectItem>();
        bankList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            setBranchList();
            setBankNameList();
            setBranchInCombo();
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setBranchInCombo() {
        try {
            BranchMaster entity = remoteObj.getAlphaCodeByBrnCode(getOrgnBrCode());
            if (entity != null) {
                this.setBranch(entity.getAlphaCode());
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setBranchList() {
        try {
            List<BranchMaster> entityList = remoteObj.getAllAlphaCode();
            if (!entityList.isEmpty()) {
                for (BranchMaster entity : entityList) {
                    branchList.add(new SelectItem(entity.getAlphaCode()));
                }
                this.setBranch("HO");
                this.setBranchName("HO");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setBankNameList() {
        try {
            List<GlDescRange> entityList = remoteObj.getGlRange("S");
            if (!entityList.isEmpty()) {
                for (GlDescRange entity : entityList) {
                    String fromNo = String.format("%06d", Integer.parseInt(entity.getFromno()));
                    String toNo = String.format("%06d", Integer.parseInt(entity.getTono()));
                    List<String> securityList = remoteObj.getSecurityType(fromNo, toNo);
                    if (!securityList.isEmpty()) {
                        for (String security : securityList) {
                            bankList.add(new SelectItem(security));
                        }
                    } else {
                        this.setMessage("Data is not found for government security in GL DESC RANGE table !");
                        return;
                    }
                }
            } else {
                this.setMessage("Range is not found for government security !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurCtrlNo() {
        this.setMessage("");
        try {
            InvestmentShare entity = remoteObj.getControlNoDetails(Integer.parseInt(this.ctrlNo));
            if (entity != null) {
                this.setSecurityDetail(entity.getSecDesc());
                this.setShareCertificate(entity.getFdrNo());
                this.setBank(entity.getSellerName());
                this.setBranch(entity.getBranch());
                this.setPurDate(dmy.format(entity.getPurchaseDt()));
                this.setFaceValue(formatter.format(entity.getFaceValue()));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurPurDate() {
        this.setMessage("");
        if (this.purDate == null || this.purDate.equals("") || this.purDate.length() < 10) {
            this.setMessage("Please fill correct purchase date !");
            return;
        }

        boolean var = new Validator().validateDate_dd_mm_yyyy(this.purDate);
        if (var == false) {
            this.setMessage("Please fill proper date !");
            return;
        }
    }

    public void onBlurDrHead() {
        this.setMessage("");
        if (this.drHead == null || this.drHead.equals("")) {
            this.setMessage("Please fill debited GL head !");
            return;
        }
        if (this.drHead.length() != 12) {
            this.setMessage("Please fill proper debited GL head !");
            return;
        }
        if (!this.drHead.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
            this.setMessage("Please fill debited GL head of your branch!");
            return;
        }
        try {
            Gltable entity = remoteObj.getGltableByAcno(this.drHead);
            if (entity != null) {
                this.setDrHeadName(entity.getAcName());
            }
        } catch (ApplicationException ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("Please fill proper Debit GL Head.");
            } else {
                this.setMessage(ex.getMessage());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurBrName() {
        this.setMessage("");
        this.enableSaveButton = false;
    }

    public void unAuthorizeCtrlNo() {
        ctrlNoList = new ArrayList<SelectItem>();
        try {
            List<InvestmentShare> dataList = remoteObj.getUnAuthorizeControlNo();
            if (!dataList.isEmpty()) {
                ctrlNoList.add(new SelectItem("--Select--"));
                for (InvestmentShare entity : dataList) {
                    ctrlNoList.add(new SelectItem(entity.getCtrlNo()));
                }
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (validate()) {
                String msg = remoteObj.closeShare(Integer.parseInt(this.ctrlNo), Double.parseDouble(this.faceValue), getUserName(), this.bank, this.drHead, getOrgnBrCode());
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Record has been close successfully, Batch No: " + msg.substring(4));
                    return;
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validate() {
        if (this.shareCertificate == null || this.shareCertificate.equals("")) {
            this.setMessage("Please fill correct Share Certificate !");
            return false;
        }

        if (this.purDate == null || this.purDate.equals("") || this.purDate.length() < 10) {
            this.setMessage("Please fill correct purchase date !");
            return false;
        }

        boolean var = new Validator().validateDate_dd_mm_yyyy(this.purDate);
        if (var == false) {
            this.setMessage("Please fill proper date !");
            return false;
        }

        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("There should be face value !");
            return false;
        }

        if (this.drHead == null || this.drHead.equals("")) {
            this.setMessage("Please fill Debited GL Head !");
            return false;
        }

        if (this.drHeadName == null || this.drHeadName.equals("")) {
            this.setMessage("Please fill correct Debited GL Head !");
            return false;
        }
        if (!this.drHead.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
            this.setMessage("Please fill debited GL head of your branch!");
            return false;
        }
        return true;
    }

    public void resetForm() {
        this.setMessage("");
        this.setSecurityDetail("");
        this.setShareCertificate("");
        this.setPurDate("");
        this.setFaceValue("");
        this.setDrHead("");
        this.setDrHeadName("");
        this.enableSaveButton = true;
        this.enableBranch = true;
        this.enableCertificate = true;
        unAuthorizeCtrlNo();
        this.setCtrlNo("--Select--");
        this.setMessage("please select a control no to close !");
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
