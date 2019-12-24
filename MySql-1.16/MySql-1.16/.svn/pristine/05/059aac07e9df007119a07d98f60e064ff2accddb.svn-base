/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
public class IndividualCharge extends BaseBean {

    private String message;
    private String acNo;
    private String accNo;
    private String chgValue;
    private List<SelectItem> chgList;
    private String gstType;
    private List<SelectItem> gstTypeList;
    private String chgAmt;
    private String crHead;
    private String acNoLen;
    private String custName;
    private String bal;
    private String show;
    private String acNature;
    private String accountStatus;
    private MiscReportFacadeRemote remoteObject;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private DDSManagementFacadeRemote ddsRemote;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getChgValue() {
        return chgValue;
    }

    public void setChgValue(String chgValue) {
        this.chgValue = chgValue;
    }

    public List<SelectItem> getChgList() {
        return chgList;
    }

    public void setChgList(List<SelectItem> chgList) {
        this.chgList = chgList;
    }

    public String getChgAmt() {
        return chgAmt;
    }

    public void setChgAmt(String chgAmt) {
        this.chgAmt = chgAmt;
    }

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public List<SelectItem> getGstTypeList() {
        return gstTypeList;
    }

    public void setGstTypeList(List<SelectItem> gstTypeList) {
        this.gstTypeList = gstTypeList;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public IndividualCharge() {
        try {
            remoteObject = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            this.setAcNoLen(getAcNoLength());
            List chargeList = new ArrayList();
            chargeList = remoteObject.getDistinctChargeName("OTH-CHARGE");
            chgList = new ArrayList<SelectItem>();
            chgList.add(new SelectItem("0", "--SELECT--"));
            if (!chargeList.isEmpty()) {
                for (int i = 0; i < chargeList.size(); i++) {
                    Vector brV = (Vector) chargeList.get(i);
                    chgList.add(new SelectItem(brV.get(0), brV.get(0).toString()));
                }
            }

            gstTypeList = new ArrayList<>();
            gstTypeList.add(new SelectItem("SGST and CGST", "SGST and CGST"));
            gstTypeList.add(new SelectItem("IGST", "IGST"));

            this.show = "none";

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getAccountDetail() {
        this.setMessage("");
        this.setChgAmt("");
        this.setCrHead("");
        this.setCustName("");
        this.setBal("");
        this.setAccNo("");
        try {
            if (this.acNo.equalsIgnoreCase("") || this.acNo == null || this.acNo.equalsIgnoreCase("null")) {
                this.setMessage("Please enter proper account number.");
                return;
            }
            if (!this.acNo.equalsIgnoreCase("") && ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper account number.");
                return;
            }
            accNo = ftsRemote.getNewAccountNumber(acNo);
            bal = ftsRemote.ftsGetBal(accNo);
            custName = ftsRemote.getCustName(accNo);
            acNature = ftsRemote.getAccountNature(accNo);
            if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                accountStatus = ddsRemote.getAccStatus(acNo);
                if (!((accountStatus.equalsIgnoreCase("1")) || (accountStatus.equalsIgnoreCase("11")) 
                        || (accountStatus.equalsIgnoreCase("12")) || (accountStatus.equalsIgnoreCase("13")))) {
                    this.setMessage("This Account Number is not Active !");
                    return;
                }
            }
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                this.show = "";
            } else {
                this.show = "none";
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        }
    }

//    public void setGlAndAmount(){
//        try{
//            if (this.accNo.equalsIgnoreCase("") || this.accNo == null || this.accNo.equalsIgnoreCase("null")) {
//                this.setMessage("Please Enter Proper Account Number.");
//                return;
//            }
//            if(this.getChgValue().equalsIgnoreCase("0")){
//                this.setMessage("Please Select Charge Type.");
//                return;
//            }
//            
//            List glAmtList = remoteObject.getChargeGlAndAmt(ftsRemote.getAccountCode(accNo),this.getChgValue());
//            if (!glAmtList.isEmpty()) {
//                for (int i = 0; i < glAmtList.size(); i++) {
//                    Vector chgV = (Vector) glAmtList.get(i);
//                    chgAmt = chgV.get(0).toString();
//                    crHead = this.getOrgnBrCode() + chgV.get(1).toString();
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            this.setMessage(e.getMessage());
//        }
//    }
    public void postAction() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (this.accNo.equalsIgnoreCase("") || this.accNo == null || this.accNo.equalsIgnoreCase("null")) {
                this.setMessage("Please enter proper account number.");
                return;
            }

            if (!this.accNo.equalsIgnoreCase("") && ((this.accNo.length() != 12))) {
                this.setMessage("Please fill proper account number.");
                return;
            }
            
            if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (!((accountStatus.equalsIgnoreCase("1")) || (accountStatus.equalsIgnoreCase("11")) 
                        || (accountStatus.equalsIgnoreCase("12")) || (accountStatus.equalsIgnoreCase("13")))) {
                    this.setMessage("This Account Number is not Active !");
                    return;
                }
            }

//            if(!this.accNo.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
//                this.setMessage("Account no should be of your branch.");
//                return;
//            }

            String chkAccNo = txnRemote.checkForAccountNo(this.accNo);
            if (!chkAccNo.equals("TRUE")) {
                this.setMessage(this.accNo + chkAccNo);
                return;
            }

            if(this.getChgValue().equalsIgnoreCase("0")){
                this.setMessage("Please select charge type.");
                return;
            }
            if (this.chgAmt.equalsIgnoreCase("") || this.chgAmt == null || this.chgAmt.equalsIgnoreCase("null") || this.chgAmt.equalsIgnoreCase("0")) {
                this.setMessage("Please define charge for this charge type.");
                return;
            }
            if (this.crHead.equalsIgnoreCase("") || this.crHead == null || this.crHead.equalsIgnoreCase("null")) {
                this.setMessage("Please define credit head for this charge type.");
                return;
            }

            if (this.chgAmt == null || this.chgAmt.toString().equalsIgnoreCase("")) {
                this.setMessage("Please fill charge amount field.");
                return;
            } else if (this.chgAmt.toString().equalsIgnoreCase("0") || this.chgAmt.toString().equalsIgnoreCase("0.0")) {
                this.setMessage("Please fill charge amount field.");
                return;
            } else {
                Matcher amountTxnCheck = p.matcher(this.chgAmt.toString());
                if (!amountTxnCheck.matches()) {
                    this.setMessage("Invalid charge amount entry.");
                    return;
                }
            }
            if (validateAmount() != true) {
                this.setMessage("Invalid charge amount.");
                return;
            }

            if (this.getCrHead().equalsIgnoreCase("") || this.getCrHead() == null || this.getCrHead().equalsIgnoreCase("null")) {
                this.setMessage("Please enter proper credit head.");
                return;
            }

            if (!this.getCrHead().equalsIgnoreCase("") && ((this.getCrHead().length() != 12))) {
                this.setMessage("Please fill proper credit head.");
                return;
            }

            String chkGlAccNo = txnRemote.checkForAccountNo(this.getCrHead());
            if (!chkGlAccNo.equals("TRUE")) {
                this.setMessage(this.getCrHead() + chkGlAccNo);
                return;
            }

            if (!this.getCrHead().substring(0, 2).equalsIgnoreCase(this.getOrgnBrCode())) {
                this.setMessage("Credit head should be of your branch.");
                return;
            }

            if (!(Integer.parseInt(this.crHead.substring(4, 10)) >= 302501 && Integer.parseInt(this.crHead.substring(4, 10)) < 399999)) {
                this.setMessage("Credit head should be of income series.");
                return;
            }

            String msg = remoteObject.individualAccountCharges(accNo, chgAmt, getOrgnBrCode(), getUserName(), crHead, chgValue, gstType);
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                this.setMessage("Batch saved successfully and generated batch is " + msg.substring(4));
                this.setAcNo("");
                this.setAccNo("");
                this.setCrHead("");
                this.setChgAmt("");
                this.setCustName("");
                this.setBal("");
                this.setChgValue("0");
            } else {
                this.setMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setAcNo("");
        this.setAccNo("");
        this.setCrHead("");
        this.setChgAmt("");
        this.setCustName("");
        this.setBal("");
        this.setChgValue("0");
        this.show = "none";
        this.setGstType("SGST and CGST");
    }

    public String exitForm() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public boolean validateAmount() {
        if (this.chgAmt.toString().contains(".")) {
            if (this.chgAmt.toString().indexOf(".") != this.chgAmt.toString().lastIndexOf(".")) {
                this.setMessage("Invalid amount.please fill the amount correctly.");
                return false;
            } else if (this.chgAmt.toString().substring(chgAmt.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please fill the amount upto two decimal places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }
}
