/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.ManualInwardPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class ManualInward extends BaseBean {

    private String message;
    private String acctCode;
    private String accNo, acNoLen;
    private String name;
    private String opMode;
    private String jtName;
    private String clBal;
    private String unclBal;
    private String instNo;
    private String instDate;
    private String instAmount;
    private String inFavourOf;
    private String prBankCode;
    private String bankName;
    private String branchName;
    private String seqNo;
    private String displaySeqNo = "none";
    private String acctNature;
    private String txnId;
    private boolean disableFavourof;
    private ManualInwardPojo currentItem;
    private List<ManualInwardPojo> tableDataList;
    private final String jndiNameCts = "CtsManagementFacade";
    private CtsManagementFacadeRemote ctsRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getClBal() {
        return clBal;
    }

    public void setClBal(String clBal) {
        this.clBal = clBal;
    }

    public String getInFavourOf() {
        return inFavourOf;
    }

    public void setInFavourOf(String inFavourOf) {
        this.inFavourOf = inFavourOf;
    }

    public String getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(String instAmount) {
        this.instAmount = instAmount;
    }

    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getUnclBal() {
        return unclBal;
    }

    public void setUnclBal(String unclBal) {
        this.unclBal = unclBal;
    }

    public String getDisplaySeqNo() {
        return displaySeqNo;
    }

    public void setDisplaySeqNo(String displaySeqNo) {
        this.displaySeqNo = displaySeqNo;
    }

    public ManualInwardPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ManualInwardPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<ManualInwardPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<ManualInwardPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public String getPrBankCode() {
        return prBankCode;
    }

    public void setPrBankCode(String prBankCode) {
        this.prBankCode = prBankCode;
    }

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public boolean isDisableFavourof() {
        return disableFavourof;
    }

    public void setDisableFavourof(boolean disableFavourof) {
        this.disableFavourof = disableFavourof;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    /**
     * Creates a new instance of ManualInward
     */
    public ManualInward() {
        try {
            ctsRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiNameCts);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            btnRefreshAction();
            this.setAcNoLen(getAcNoLength());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateAccount() {
        this.setMessage("");
        refreshField();
        String array[] = null;
        int j, k, l, m, n;
        try {
            if (this.acctCode == null || this.acctCode.equals("")) {
                this.setMessage("Please fill Account Number.");
                return;
            }

            if (!this.acctCode.equalsIgnoreCase("") && ((this.acctCode.length() != 12) && (this.acctCode.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill Account Number.");
                return;
            }

            accNo = ftsPostRemote.getNewAccountNumber(acctCode);

            String loggedInAlphaCode = ctsRemote.getBranchAplhaCode(Integer.parseInt(getOrgnBrCode()));
            if (!(loggedInAlphaCode.equalsIgnoreCase("CELL") || loggedInAlphaCode.equalsIgnoreCase("HO"))) {
                if (!getOrgnBrCode().equalsIgnoreCase(accNo.substring(0, 2))) {
                    this.setMessage("Please fill only your branch A/c Number");
                    return;
                }
            }

            List paramList = ftsPostRemote.getBaseParameter(ftsPostRemote.getAccountCode(accNo));
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String chqFlag = paramVector.get(4).toString();
                if (chqFlag.equalsIgnoreCase("N")) {
                    this.setMessage("Inward Clearing is not allowed for this type of account.");
                    return;
                }
            }

            acctNature = ftsPostRemote.getAccountNature(accNo);
            if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                this.setName(ctsRemote.getGlHeadName(accNo));

                this.displaySeqNo = "";
                this.disableFavourof = true;
//                this.setName("");
                this.setOpMode("");
                this.setJtName("");
                this.setClBal("");
                this.setUnclBal("");
            } else {
                this.displaySeqNo = "none";
                this.disableFavourof = false;
                String result = ctsRemote.getClngDetails(accNo);
                if (result.contains("[")) {
                    result = result.replace("[", "");
                    result = result.replace("]", "");
                    String spliter = ", ";
                    array = result.split(spliter);

                    for (j = 0, k = 1, l = 2, m = 3, n = 4; n < (array.length); j = j + 5, k = k + 5, l = l + 5, m = m + 5, n = n + 5) {
                        this.setName(array[j]);
                        this.setOpMode(array[n]);
                        this.setJtName(array[k]);
                        this.setClBal(formatter.format(Double.parseDouble(array[l])));
                        this.setUnclBal(formatter.format(Double.parseDouble(array[m])));
                    }
                } else {
                    this.setMessage(result);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateInstNo() {
        this.setMessage("");
        try {
            if (this.instNo == null || this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
                this.setMessage("Please fill proper Inst. No.");
                return;
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher instNoCheck = p.matcher(this.instNo);
            if (!instNoCheck.matches()) {
                this.setMessage("Please fill proper Inst. No.");
                return;
            }

            String checkMessage = ctsRemote.chequeValidate(this.accNo, this.instNo.trim());
            if (!checkMessage.equalsIgnoreCase("true")) {
                this.setMessage(checkMessage);
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateInstDt() {
        this.setMessage("");
        try {
            if (this.instDate == null || this.instDate.equals("") || this.instDate.length() < 10) {
                this.setMessage("Please fill Inst. Date.");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.instDate);
            if (result == false) {
                this.setMessage("Please fill proper Inst. Date.");
                return;
            }
            String chqDtMsg = ftsPostRemote.ftsInstDateValidate(ymd.format(dmy.parse(this.instDate)));
            if (!chqDtMsg.equalsIgnoreCase("true")) {
                this.setMessage(chqDtMsg);
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getCtsSeqNo() {
        this.setMessage("");
        //String array[] = null;
        //int j, k, l;
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instAmount == null || this.instAmount.equalsIgnoreCase("")) {
                this.setMessage("Please fill instrument amount field.");
                return;
            }
            if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(Double.parseDouble(instAmount))) == 0) {
                this.setMessage("instrument amount field should not be zero.");
                return;
            }

            Matcher amountTxnCheck = p.matcher(this.instAmount);
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid instrument amount entry.");
                return;
            }

            if (this.instAmount.contains(".")) {
                if (this.instAmount.indexOf(".") != this.instAmount.lastIndexOf(".")) {
                    this.setMessage("Invalid instrument amount. Please fill the amount correctly.");
                    return;
                } else if (this.instAmount.substring(this.instAmount.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please fill the instrument amount upto two decimal places.");
                    return;
                }
            }

            if (!acctNature.equals(CbsConstant.PAY_ORDER)) {
                String chBal = ftsPostRemote.checkBalance(this.accNo, Float.parseFloat(this.getInstAmount()), getUserName());
                if (!chBal.equalsIgnoreCase("true")) {
                    this.setMessage(chBal);
                }
            }

            String alphaCode = ctsRemote.getBranchAplhaCode(Integer.parseInt(this.accNo.substring(0, 2)));
            if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                this.disableFavourof = true;
                this.setInFavourOf("");
                this.displaySeqNo = "";
                this.setSeqNo("");
                String valMsg = ctsRemote.valBillHead(accNo, this.instNo, this.instAmount, ymd.format(dmy.parse(this.instDate)), alphaCode);
                if (!valMsg.equalsIgnoreCase("true")) {
                    this.setMessage("Inward Clearing is not allowed for this account. May be already paid or inst. no is not for this account");
                    return;
                }
                String result = ctsRemote.getSeqNo(this.instNo, this.instAmount, ymd.format(dmy.parse(this.instDate)), alphaCode);
                String[] array = result.split("~`");

//              this.setName(array[l]);
                this.setSeqNo(array[0]);
                this.setInFavourOf(array[1]);

            } else {
                this.disableFavourof = false;
                this.setInFavourOf("");
                this.displaySeqNo = "none";
                this.setSeqNo("");
            }
        } catch (Exception e) {
            this.setSeqNo("");
            this.setInFavourOf("");
            this.setMessage(e.getMessage());
        }
    }

    public void validatePrBankCode() {
        this.setMessage("");
        try {
            if (this.prBankCode == null || this.prBankCode.equals("")) {
                this.setMessage("Please fill Pr.Bank Code.");
                return;
            }

            if (this.prBankCode.length() != 9) {
                this.setMessage("Length of Pr.Bank Code. should be 9 digit.");
                return;
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher prBankCodeChq = p.matcher(this.instNo);
            if (!prBankCodeChq.matches()) {
                this.setMessage("Please fill proper Pr.Bank Code.");
                return;
            }
            List micrList = ctsRemote.getMicrDetails(this.prBankCode.substring(0, 3), this.prBankCode.substring(3, 6), this.prBankCode.substring(6));
            if (!micrList.isEmpty()) {
                Vector element = (Vector) micrList.get(0);
                this.setBankName(element.get(0).toString());
                this.setBranchName(element.get(1).toString());
            } else {
                this.setMessage("Bank and branch name does not exists.");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getUnAuthorizedInstrument() {
        this.setMessage("");
        tableDataList = new ArrayList<ManualInwardPojo>();
        try {
            List dataList = ctsRemote.getUnAuthorizedInstrument(ymd.format(dmy.parse(getTodayDate())), getOrgnBrCode());
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    ManualInwardPojo pojo = new ManualInwardPojo();
                    Vector element = (Vector) dataList.get(i);
                    pojo.setSno(Integer.parseInt(element.get(0).toString()));
                    pojo.setAccountNo(element.get(1).toString());
                    pojo.setInstNo(element.get(2).toString());
                    pojo.setInstDt(dmy.format(sdf.parse(element.get(3).toString())));
                    pojo.setAmount(new BigDecimal(formatter.format(element.get(4))));
                    pojo.setInFavourof(element.get(5).toString());
                    pojo.setBankName(element.get(6).toString());
                    pojo.setBranchName(element.get(7).toString());
                    pojo.setPrBankCode(element.get(8).toString());
                    pojo.setCustName(element.get(9).toString());
                    tableDataList.add(pojo);
                }
            } else {
                this.setMessage("There is no unauthorized instrument.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveTransaction() {
        this.setMessage("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssms");
        try {
            if (this.accNo == null || this.accNo.equals("")) {
                this.setMessage("Account number can not be blank.");
                return;
            }
            if (this.instNo == null || this.instNo.equals("")) {
                this.setMessage("Instrument number can not be blank.");
                return;
            }

            if (this.instDate == null || this.instDate.equals("")) {
                this.setMessage("Instrument date can not be blank.");
                return;
            }
            if (this.instAmount == null || this.instAmount.equals("")) {
                this.setMessage("Instrument amount can not be blank.");
                return;
            }
            if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(Double.parseDouble(instAmount))) == 0) {
                this.setMessage("instrument amount field should not be zero.");
                return;
            }
            if (this.inFavourOf == null || this.inFavourOf.equals("")) {
                this.setMessage("In favour of can not be blank.");
                return;
            }
            List paramList = ftsPostRemote.getBaseParameter(ftsPostRemote.getAccountCode(accNo));
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String chqFlag = paramVector.get(4).toString();
                if (chqFlag.equalsIgnoreCase("N")) {
                    this.setMessage("Inward Clearing is not allowed for this type of account.");
                    return;
                }
            }
            txnId = WebUtil.getClientIP(this.getHttpRequest()) + sdf.format(new Date());
            String sequenceNo;
            if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                sequenceNo = this.seqNo;
            } else {
                sequenceNo = "0";
            }
            if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                String alphaCode = ctsRemote.getBranchAplhaCode(Integer.parseInt(this.accNo.substring(0, 2)));
                String valMsg = ctsRemote.valBillHead(accNo, this.instNo, this.instAmount, ymd.format(dmy.parse(this.instDate)), alphaCode);
                if (!valMsg.equalsIgnoreCase("true")) {
                    this.setMessage("Inward Clearing is not allowed for this account. May be already paid or inst. no is not for this account");
                    return;
                }
            }

            /**
             * * Check for GL Type Restriction **
             */
            String glType = ftsPostRemote.getGlTranInfo(accNo, 1, Double.parseDouble(this.instAmount));
            if (!glType.equalsIgnoreCase("True")) {
                this.setMessage(glType);
                return;
            }
            /**
             * end here*
             */
            String accountBrCode = ftsPostRemote.getCurrentBrnCode(this.accNo);
            String saveResult = ctsRemote.saveManualInward(txnId, "0", this.accNo, this.instNo, this.instAmount, ymd.format(dmy.parse(this.instDate)), this.inFavourOf,
                    this.bankName, this.branchName, getUserName(), sequenceNo, getOrgnBrCode(), accountBrCode,
                    ymd.format(new Date()), "", this.name, this.opMode, this.prBankCode, "", "");

            if (!saveResult.substring(0, 4).equalsIgnoreCase("true")) {
                this.setMessage(saveResult);
                return;
            } else {
                btnRefreshAction();
                this.setMessage("Generated Voucher No. is:-   " + saveResult.substring(4));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshField() {
        this.setAccNo("");
        this.setName("");
        this.setOpMode("");
        this.setJtName("");
        this.setClBal("");
        this.setUnclBal("");
        this.setInstNo("");
        this.setInstDate(getTodayDate());
        this.setInstAmount("");
        this.setInFavourOf("");
        this.setPrBankCode("");
        this.setBankName("");
        this.setBranchName("");
        this.setSeqNo("");
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAcctCode("");
        this.setAccNo("");
        this.setName("");
        this.setOpMode("");
        this.setJtName("");
        this.setClBal("");
        this.setUnclBal("");
        this.setInstNo("");
        this.setInstDate(getTodayDate());
        this.setInstAmount("");
        this.setInFavourOf("");
        this.setPrBankCode("");
        this.setBankName("");
        this.setBranchName("");
        this.displaySeqNo = "none";
        this.disableFavourof = false;
        this.setSeqNo("");
        getUnAuthorizedInstrument();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
