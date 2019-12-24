/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.bill;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class ChequePurchase extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String sequnceNo;
    private List<SelectItem> sequnceNoList;
    private String bcBillNo;
    private String billNo;
    private String transType;
    private List<SelectItem> transTypeList;
    private String acNo;
    private String acType;
    private double totalLimit;
    private String collBank;
    private String schemeCode;
    private String schemeCodeDesc;
    private String docType;
    private List<SelectItem> docTypeList;
    private String docNo;
    private String docDate;
    private int usanceDays;
    private double collectionChr;
    private double margin;
    private String status;
    private double commPaidToBankers;
    private String activityType;
    private String acholderName;
    private double avaLimit;
    private String chgRecover;
    private String docSeriies;
    private String billDate;
    private double amount;
    private int gracePeriod;
    private String group;
    private String maturityDate;
    private double postageAmt;
    private double pocketExp;
    private double interestRate;
    private double interestAmt;
    private double realisationAmt;
    private String drVoucher;
    private String freeDelivered;
    private String acNoLen;
    private String btnValue;
    private boolean btnFlag;
    private String confirmationMsg;
    private String bankCode1;
    private String bankCode2;
    private String bankCode3;
    private String micorCode;
    private String flag2 = "false";
    boolean flag3;
    boolean flag4;
    private String branch;
    private String bankName;
    private String branchName;
    private String displaySeqNo;
    private String enterBy;
    private String focusId;
    private boolean disableField;
    private boolean disableBcBillNo;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private BillCommissionFacadeRemote revalidateEjb;
    private OutwardClearingManagementFacadeRemote outwardClgRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;

    //getCodeFromCbsParameterInfo
    public ChequePurchase() {
        try {

            this.setDocDate(dmy.format(new Date()));
            this.setBillDate(dmy.format(new Date()));

            revalidateEjb = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup("OutwardClearingManagementFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setBankCode1(outwardClgRemote.getCityCodeAsMiccr(getOrgnBrCode()));

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "--Select--"));
            functionList.add(new SelectItem("1", "Add"));
            functionList.add(new SelectItem("2", "Delete"));
            functionList.add(new SelectItem("3", "Verify"));

            transTypeList = new ArrayList<SelectItem>();
            transTypeList.add(new SelectItem("2", "Transfer"));

            docTypeList = new ArrayList<SelectItem>();
            docTypeList.add(new SelectItem("C", "CHQ"));
            this.btnValue = "Add";
            this.displaySeqNo = "none";
            this.setActivityType(ftsRemote.getCodeFromCbsParameterInfo("CHEQUE-ACTIVITY-TYPE"));

//            List list = revalidateEjb.getChargeDays();
//            if (list.isEmpty()) {
//                throw new ApplicationException("Please fill Charge and days Parameter !");
//            }
//            Vector vtr = (Vector) list.get(0);
//            this.setCollectionChr(Double.parseDouble(vtr.get(0).toString()));
//            this.setUsanceDays(Integer.parseInt(vtr.get(1).toString()));
            refreshForm();

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void processFunction() {
        try {
            setMessage("");
            if (function == null || function.equalsIgnoreCase("")) {
                setMessage("Please Select Function !");
                return;
            } else if (function.equals("1")) {
                this.setMessage("");
                this.setActivityType(ftsRemote.getCodeFromCbsParameterInfo("CHEQUE-ACTIVITY-TYPE"));
                this.btnValue = "Add";
                this.displaySeqNo = "none";
                this.setFocusId("stxtacNo");
                this.disableField = false;
                this.disableBcBillNo = true;
            } else if (function.equals("2")) {
                this.setMessage("");
                this.btnValue = "Delete";
                this.displaySeqNo = "";
                getSerialNo();
                this.setBtnFlag(false);
                this.disableField = false;
            } else if (function.equals("3")) {
                this.setMessage("");
                this.btnValue = "Verify";
                this.setBtnFlag(false);
                this.displaySeqNo = "";
                getSerialNo();
                this.disableField = false;
            } else if (function.equals("4")) {
                this.setMessage("");
                this.btnValue = "Help";
                this.setBtnFlag(false);
                this.displaySeqNo = "none";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("1")) {
            this.setConfirmationMsg("Are you sure to Add this detail ?");
        } else if (this.function.equals("2")) {
            this.setConfirmationMsg("Are you sure to Delete this detail ?");
        } else if (this.function.equals("3")) {
            this.setConfirmationMsg("Are you sure to Verify this detail ?");
        } else if (this.function.equals("4")) {
            this.setConfirmationMsg("Are you sure to Help this detail ?");
        }
    }

    public void getSerialNo() {
        setMessage("");
        try {
            sequnceNoList = new ArrayList<SelectItem>();
            List snoList = revalidateEjb.getSerialNo();
            if (!snoList.isEmpty()) {
                for (int i = 0; i < snoList.size(); i++) {
                    Vector vtr = (Vector) snoList.get(i);
                    sequnceNoList.add(new SelectItem(vtr.get(0).toString()));
                }
            } else {
                if (function.equalsIgnoreCase("2")) {
                    setMessage("No Data to Delete !");
                } else {
                    setMessage("No Data to Verify !");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getChequePurchaseData() {
        setMessage("");
        try {

            if (function.equalsIgnoreCase("2")) {
                if (sequnceNo == null || sequnceNo.equalsIgnoreCase("")) {
                    setMessage("No Data to Delete !");
                    return;
                }
            } else {
                if (sequnceNo == null || sequnceNo.equalsIgnoreCase("")) {
                    setMessage("No Data to Verify !");
                    return;
                }
            }

            List chequePurchaseList = revalidateEjb.getChequePurchaseDataBySeqNo(sequnceNo);
            for (int i = 0; i < chequePurchaseList.size(); i++) {
                Vector element = (Vector) chequePurchaseList.get(i);
                this.setBcBillNo(element.get(1).toString());
                this.setActivityType(element.get(2).toString());
                this.setBillNo(element.get(3).toString());
                this.setAcNo(element.get(4).toString());
                this.setAcholderName(element.get(5).toString());
                this.setTransType(element.get(6).toString());
                this.setAcType(element.get(7).toString());
                this.setGroup(element.get(8).toString());
                this.setTotalLimit(Double.parseDouble(element.get(9).toString()));
                this.setAvaLimit(Double.parseDouble(element.get(10).toString()));
                //this.setMicorCode(element.get(11).toString());
                String fullMicorCode = element.get(11).toString();
                this.setBankCode2(fullMicorCode.substring(3, 6));
                this.setBankCode3(fullMicorCode.substring(6, 9));
                this.setBankName(element.get(12).toString());
                this.setBranchName(element.get(13).toString());
                this.setSchemeCode(element.get(14).toString());
                this.setDocType(element.get(15).toString());
                this.setDocNo(element.get(16).toString());
                this.setDocSeriies(element.get(17).toString());
                this.setDocDate(element.get(18).toString());
                this.setBillDate(element.get(19).toString());
                this.setAmount(Double.parseDouble(element.get(20).toString()));
                this.setUsanceDays(Integer.parseInt(element.get(21).toString()));
                this.setGracePeriod(Integer.parseInt(element.get(22).toString()));
                this.setMaturityDate(element.get(23).toString());
                this.setCollectionChr(Double.parseDouble(element.get(24).toString()));
                this.setPostageAmt(Double.parseDouble(element.get(25).toString()));
                this.setPocketExp(Double.parseDouble(element.get(26).toString()));
                this.setMargin(Double.parseDouble(element.get(27).toString()));
                this.setInterestRate(Double.parseDouble(element.get(28).toString()));
                this.setInterestAmt(Double.parseDouble(element.get(29).toString()));
                this.setStatus(element.get(30).toString());
                this.setRealisationAmt(Double.parseDouble(element.get(31).toString()));
                this.setCommPaidToBankers(Double.parseDouble(element.get(32).toString()));
                this.setEnterBy(element.get(33).toString());
                this.setSchemeCodeDesc(element.get(34).toString());
            }
            this.disableField = true;

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void processOnAccount() {
        setMessage("");
        try {
            Pattern p = Pattern.compile("[0-9]*");
            if (function == null || function.equalsIgnoreCase("")) {
                throw new ApplicationException("Please select Function.");
            } else if (function.equals("1")) {
                if (this.acNo.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please fill Account Number");
                }
                if (!this.acNo.equalsIgnoreCase("") && ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    throw new ApplicationException("Account Number Should be Proper");
                }
                Matcher matcher = p.matcher(this.acNo);
                if (!matcher.matches()) {
                    throw new ApplicationException("Account Number should be in numeric digits.");
                }
                if (!acNo.substring(0, 2).equals(getOrgnBrCode())) {
                    throw new ApplicationException("Registration allow only from base branch.");
                }
                String msg = ftsRemote.ftsAcnoValidate(this.acNo, 1, "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            List list = revalidateEjb.getcustInfoByAcno(acNo);
            Vector vtr = (Vector) list.get(0);
            this.setAcholderName(vtr.get(0).toString());
            this.setAcType(vtr.get(1).toString());
            this.setGroup(vtr.get(2).toString());
            this.setSchemeCode(vtr.get(3).toString());
            this.setSchemeCodeDesc(vtr.get(4).toString());
            String acNoForStaff = vtr.get(5).toString();

            List list2 = revalidateEjb.getChargeDays();
            Vector chVector = (Vector) list2.get(0);
            if (acNoForStaff.equalsIgnoreCase("ST")) {
                this.collectionChr = 0;
            } else {
                this.collectionChr = Double.parseDouble(chVector.get(0).toString());
            }
            this.usanceDays = Integer.parseInt(chVector.get(1).toString());

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void bankCode1LostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode1Check = p.matcher(this.bankCode1);
        if (!bankCode1Check.matches()) {
            this.setMessage("Please enter some Integer Value in Bank Code.");
            return;
        } else {
            this.setMessage("");
        }
    }

    public void bankCode2LostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode2Check = p.matcher(this.bankCode2);
        if (flag2.equalsIgnoreCase("false")) {
            if (!bankCode2Check.matches()) {
                this.setMessage("Please enter some Integer Value in Bank Code.");
                return;
            } else {
                this.setMessage("");
            }
        } else if (flag2.equalsIgnoreCase("true")) {
            this.setBankCode2("000");
        }

    }

    public boolean bankCode3LostFocus() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher bankCode3Check = p.matcher(this.bankCode3);
            if (flag2.equalsIgnoreCase("false")) {
                if (!bankCode3Check.matches()) {
                    this.setMessage("Please enter some Integer Value in Bank Code.");
                    return false;
                } else {
                    this.setMessage("");
                }
                List listForBankBranch = outwardClgRemote.instrUpdtDelRegBankDetail(bankCode1, bankCode2, bankCode3);
                if (listForBankBranch.isEmpty()) {
                    this.setMessage("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
                    return false;
                } else {
                    Vector vecBankBranch = (Vector) listForBankBranch.get(0);
                    this.setBankName(vecBankBranch.get(0).toString());
                    this.setBranchName(vecBankBranch.get(1).toString());

                }
            } else if (flag2.equalsIgnoreCase("true")) {
                this.setBankCode3("000");
            }

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return true;
    }

    public void setMaturityDateByUsanceDays() {
        setMessage("");
        try {
            if (billDate == null || billDate.equalsIgnoreCase("")) {
                setMessage("Please fill bill date !");
                return;
            }
            maturityDate = dmy.format(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(billDate)), usanceDays)));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void saveMasterDetail() {
        setMessage("");
        try {
            this.micorCode = bankCode1 + bankCode2 + bankCode3;
            if (validateField()) {
                if (function.equalsIgnoreCase("3")) {
                    if (this.enterBy.equalsIgnoreCase(getUserName())) {
                        throw new ApplicationException("You can not Verify your own transaction");
                    }
                }
                String result = revalidateEjb.chequePurchaseSave(function, getOrgnBrCode(), sequnceNo, bcBillNo, activityType, billNo, acNo, acholderName,
                        transType, acType, group, totalLimit, avaLimit, micorCode, bankName, branchName, schemeCode, schemeCodeDesc,
                        docType, docNo, docSeriies, docDate, billDate, amount, usanceDays, gracePeriod, maturityDate,
                        collectionChr, postageAmt, pocketExp, margin, interestRate, interestAmt, status, realisationAmt,
                        commPaidToBankers, "N", getUserName(), getUserName(), getUserName(), ymd.format(dmy.parse(getTodayDate())));

                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    if (function.equalsIgnoreCase("1")) {
                        setMessage("Data has been Save Successfuly !");
                        clearFormData();
                    } else if (function.equalsIgnoreCase("2")) {
                        setMessage("Data has been Deleted Successfuly !");
                        clearFormData();
                    } else if (function.equalsIgnoreCase("3")) {
                        //setMessage("Data has been Verify Successfuly and Batch No. " + result.substring(4));
                        setMessage("Data has been Verify Successfuly and BcBill No. " + result.substring(4, 16) + " and Batch No. " + result.substring(16));
                        clearFormData();
                    }
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public boolean validateField() throws ApplicationException {

        Pattern p = Pattern.compile("[0-9]*");
        Pattern pt = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");

        if (function == null || function.equalsIgnoreCase("")) {
            throw new ApplicationException("Please select Function !");
        }
        if (acNo == null || acNo.equalsIgnoreCase("")) {
            throw new ApplicationException("Please fill A/c No.");
        }
        if (!this.acNo.equalsIgnoreCase("") && ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            throw new ApplicationException("Account Number Should be Proper");
        }
        if (docNo == null || docNo.equalsIgnoreCase("")) {
            throw new ApplicationException("Please fill Instrument No.");
        }
        Matcher matcher = p.matcher(this.docNo);
        if (!matcher.matches()) {
            throw new ApplicationException("Instrument No should be in numeric digits.");
        }
        if (docNo.length() != 6) {
            throw new ApplicationException("Instrument No. should be 6 digits.");
        }
        if (docDate == null || docDate.equalsIgnoreCase("")) {
            throw new ApplicationException("Please fill Instrument Date");
        }
        if (billDate == null || billDate.equalsIgnoreCase("")) {
            throw new ApplicationException("Please fill Bill Date");
        }
        if (amount == 0) {
            throw new ApplicationException("Please fill Amount");
        }
        return true;
    }

    public void refreshForm() {
        setMessage("");
        setFunction("");
        setActivityType("");
        setBcBillNo("");
        setBillNo("");
        setAcNo("");
        setAcholderName("");
        setAcType("");
        setGroup("");
        setSchemeCode("");
        setSchemeCodeDesc("");
        setBankCode2("");
        setBankCode3("");
        setBankName("");
        setBranchName("");
        setTotalLimit(0);
        setAvaLimit(0);
        setDocNo("");
        setDocDate(dmy.format(new Date()));
        setDocSeriies("");
        setGracePeriod(0);
        setBillDate(dmy.format(new Date()));
        setUsanceDays(0);
        setMaturityDate(dmy.format(new Date()));
        setAmount(0);
        setCollectionChr(0);
        setPostageAmt(0);
        setPocketExp(0);
        setMargin(0);
        setInterestRate(0);
        setInterestAmt(0);
        setStatus("");
        setRealisationAmt(0);
        setCommPaidToBankers(0);
        this.disableField = false;
        setFocusId("");
        setDisplaySeqNo("none");
        this.btnValue = "Add";
    }

    public void clearFormData() {

        setFunction("");
        setActivityType("");
        setBcBillNo("");
        setBillNo("");
        setAcNo("");
        setAcholderName("");
        setAcType("");
        setGroup("");
        setSchemeCode("");
        setSchemeCodeDesc("");
        setBankCode2("");
        setBankCode3("");
        setBankName("");
        setBranchName("");
        setTotalLimit(0);
        setAvaLimit(0);
        setDocNo("");
        setDocDate(dmy.format(new Date()));
        setDocSeriies("");
        setGracePeriod(0);
        setBillDate(dmy.format(new Date()));
        setUsanceDays(0);
        setMaturityDate(dmy.format(new Date()));
        setAmount(0);
        setCollectionChr(0);
        setPostageAmt(0);
        setPocketExp(0);
        setMargin(0);
        setInterestRate(0);
        setInterestAmt(0);
        setStatus("");
        setRealisationAmt(0);
        setCommPaidToBankers(0);
        setFocusId("");
        setDisplaySeqNo("none");
        this.btnValue = "Add";
    }

    public String exitBtnAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getBcBillNo() {
        return bcBillNo;
    }

    public void setBcBillNo(String bcBillNo) {
        this.bcBillNo = bcBillNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public String getCollBank() {
        return collBank;
    }

    public void setCollBank(String collBank) {
        this.collBank = collBank;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCodeDesc() {
        return schemeCodeDesc;
    }

    public void setSchemeCodeDesc(String schemeCodeDesc) {
        this.schemeCodeDesc = schemeCodeDesc;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public List<SelectItem> getDocTypeList() {
        return docTypeList;
    }

    public void setDocTypeList(List<SelectItem> docTypeList) {
        this.docTypeList = docTypeList;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public int getUsanceDays() {
        return usanceDays;
    }

    public void setUsanceDays(int usanceDays) {
        this.usanceDays = usanceDays;
    }

    public double getCollectionChr() {
        return collectionChr;
    }

    public void setCollectionChr(double collectionChr) {
        this.collectionChr = collectionChr;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCommPaidToBankers() {
        return commPaidToBankers;
    }

    public void setCommPaidToBankers(double commPaidToBankers) {
        this.commPaidToBankers = commPaidToBankers;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public List<SelectItem> getTransTypeList() {
        return transTypeList;
    }

    public void setTransTypeList(List<SelectItem> transTypeList) {
        this.transTypeList = transTypeList;
    }

    public String getAcholderName() {
        return acholderName;
    }

    public void setAcholderName(String acholderName) {
        this.acholderName = acholderName;
    }

    public double getAvaLimit() {
        return avaLimit;
    }

    public void setAvaLimit(double avaLimit) {
        this.avaLimit = avaLimit;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getChgRecover() {
        return chgRecover;
    }

    public void setChgRecover(String chgRecover) {
        this.chgRecover = chgRecover;
    }

    public String getDocSeriies() {
        return docSeriies;
    }

    public void setDocSeriies(String docSeriies) {
        this.docSeriies = docSeriies;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getPostageAmt() {
        return postageAmt;
    }

    public void setPostageAmt(double postageAmt) {
        this.postageAmt = postageAmt;
    }

    public double getPocketExp() {
        return pocketExp;
    }

    public void setPocketExp(double pocketExp) {
        this.pocketExp = pocketExp;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(double interestAmt) {
        this.interestAmt = interestAmt;
    }

    public double getRealisationAmt() {
        return realisationAmt;
    }

    public void setRealisationAmt(double realisationAmt) {
        this.realisationAmt = realisationAmt;
    }

    public String getDrVoucher() {
        return drVoucher;
    }

    public void setDrVoucher(String drVoucher) {
        this.drVoucher = drVoucher;
    }

    public String getFreeDelivered() {
        return freeDelivered;
    }

    public void setFreeDelivered(String freeDelivered) {
        this.freeDelivered = freeDelivered;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public String getBankCode1() {
        return bankCode1;
    }

    public void setBankCode1(String bankCode1) {
        this.bankCode1 = bankCode1;
    }

    public String getBankCode2() {
        return bankCode2;
    }

    public void setBankCode2(String bankCode2) {
        this.bankCode2 = bankCode2;
    }

    public String getBankCode3() {
        return bankCode3;
    }

    public void setBankCode3(String bankCode3) {
        this.bankCode3 = bankCode3;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
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

    public boolean isFlag4() {
        return flag4;
    }

    public void setFlag4(boolean flag4) {
        this.flag4 = flag4;
    }

    public String getSequnceNo() {
        return sequnceNo;
    }

    public void setSequnceNo(String sequnceNo) {
        this.sequnceNo = sequnceNo;
    }

    public List<SelectItem> getSequnceNoList() {
        return sequnceNoList;
    }

    public void setSequnceNoList(List<SelectItem> sequnceNoList) {
        this.sequnceNoList = sequnceNoList;
    }

    public String getDisplaySeqNo() {
        return displaySeqNo;
    }

    public void setDisplaySeqNo(String displaySeqNo) {
        this.displaySeqNo = displaySeqNo;
    }

    public String getMicorCode() {
        return micorCode;
    }

    public void setMicorCode(String micorCode) {
        this.micorCode = micorCode;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public boolean isDisableField() {
        return disableField;
    }

    public void setDisableField(boolean disableField) {
        this.disableField = disableField;
    }

    public boolean isDisableBcBillNo() {
        return disableBcBillNo;
    }

    public void setDisableBcBillNo(boolean disableBcBillNo) {
        this.disableBcBillNo = disableBcBillNo;
    }
}
