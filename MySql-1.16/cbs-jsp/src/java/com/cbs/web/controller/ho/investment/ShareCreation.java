/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class ShareCreation extends BaseBean {

    private String message;
    private String secType;
    private String shareDetails;
    private String shareCertificate;
    private String bank;
    private String branch;
    private String purDate;
    private String faceValue;
    private String crHead;
    private String crHeadName;
    private String branchName;
    private List<SelectItem> secTypeList;
    private List<SelectItem> bankList;
    private List<SelectItem> branchList;
    private boolean processBtnVisible;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

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

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public String getCrHeadName() {
        return crHeadName;
    }

    public void setCrHeadName(String crHeadName) {
        this.crHeadName = crHeadName;
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

    public boolean isProcessBtnVisible() {
        return processBtnVisible;
    }

    public void setProcessBtnVisible(boolean processBtnVisible) {
        this.processBtnVisible = processBtnVisible;
    }

    public String getPurDate() {
        return purDate;
    }

    public void setPurDate(String purDate) {
        this.purDate = purDate;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<SelectItem> getSecTypeList() {
        return secTypeList;
    }

    public void setSecTypeList(List<SelectItem> secTypeList) {
        this.secTypeList = secTypeList;
    }

    public String getShareCertificate() {
        return shareCertificate;
    }

    public void setShareCertificate(String shareCertificate) {
        this.shareCertificate = shareCertificate;
    }

    public String getShareDetails() {
        return shareDetails;
    }

    public void setShareDetails(String shareDetails) {
        this.shareDetails = shareDetails;
    }

    public ShareCreation() {
        secTypeList = new ArrayList<SelectItem>();
        branchList = new ArrayList<SelectItem>();
        bankList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            setBranchList();
            setSecurityList();
            setBankNameList();
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
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

    public void setSecurityList() {
        secTypeList.add(new SelectItem("Share With Banks"));
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

    public void onBlurShareCertificate() {
        this.setMessage("");
        if (this.shareCertificate == null || this.shareCertificate.equals("") || this.shareCertificate.equals("0") || this.shareCertificate.equals("0.0")) {
            this.setMessage("Please fill correct Share Certificate !");
            return;
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

        try {
            if (dmy.parse(this.purDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                this.setMessage("Purchase date should be less than current date !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurfaceValue() {
        this.setMessage("");
        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("Please fill face value !");
            return;
        }
    }

    public void onBlurCrHead() {
        this.setMessage("");
        if (this.crHead == null || this.crHead.equals("")) {
            this.setMessage("Please fill credited GL head !");
            return;
        }
        if (this.crHead.length() != 12) {
            this.setMessage("Please fill proper credited GL head !");
            return;
        }
        if (!this.crHead.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
            this.setMessage("Please fill credited GL head of your branch!");
            return;
        }
        try {
            Gltable entity = remoteObj.getGltableByAcno(this.crHead);
            if (entity != null) {
                this.setCrHeadName(entity.getAcName());
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurBrName() {
        this.setMessage("");
        this.processBtnVisible = false;
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (validate()) {
                Integer maxCtrlNo = remoteObj.getmaxCtrlNoInvestmentShare();
                String msg = remoteObj.saveShareCreation(maxCtrlNo, this.secType, dmy.parse(this.purDate), this.shareDetails, this.bank,
                        Double.parseDouble(this.faceValue), this.shareCertificate, this.branch,
                        getUserName(), this.crHead, getOrgnBrCode());
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Transaction has been made successfully, Controll No: " + maxCtrlNo.toString() + " And " + "Batch No: " + msg.substring(4));
                    return;
                }
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validate() {
        if (this.shareCertificate == null || this.shareCertificate.equals("") || this.shareCertificate.equals("0") || this.shareCertificate.equals("0.0")) {
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
            this.setMessage("Please fill face value !");
            return false;
        }

        if (this.crHead == null || this.crHead.equals("")) {
            this.setMessage("Please fill credited gL head !");
            return false;
        }
        if (!this.crHead.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
            this.setMessage("Please fill credited GL head of your branch!");
            return false;
        }
        return true;
    }

    public void resetForm() {
        this.setMessage("");
        this.setShareDetails("");
        this.setShareCertificate("");
        this.setPurDate("");
        this.setFaceValue("");
        this.setCrHead("");
        this.setCrHeadName("");
        this.processBtnVisible = true;
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
