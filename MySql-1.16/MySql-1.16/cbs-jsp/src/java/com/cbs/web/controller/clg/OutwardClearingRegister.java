/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.dto.npci.cts.InstrumentsForTheSameVoucherGrid;
import com.cbs.web.pojo.clg.UnverifiedInstrumentsGrid;
import com.cbs.web.pojo.txn.AccountTypeDescPojo;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
public class OutwardClearingRegister extends BaseBean {

    String entryType;
    List<SelectItem> entryTypeList;
    String bankNames;
    List<SelectItem> bankNamesList;
    String nextChq;
    List<SelectItem> nextChqList;
    String branchNames;
    List<SelectItem> branchNamesList = new ArrayList<SelectItem>();
    String alphaCode;
    List<SelectItem> alphaCodeList;
    String circleType;
    List<SelectItem> circleTypeList;
    String flag1;
    String flag2 = "false";
    boolean flag3;
    boolean flag4;
    String flag5;
    String accountNo;
    String errorMsg;
    String jtName;
    String custName;
    Date instrDate;
    String bankCode1;
    String bankCode2;
    String bankCode3;
    String regisDate;
    List<SelectItem> regisDateList;
    UnverifiedInstrumentsGrid unverifyInstr;
    List<UnverifiedInstrumentsGrid> unverifyInstrList;
    String bankName;
    String branchName;
    String instrAmt;
    String instrNo;
    String flag6;
    //String flag7;
    String flag8 = "none";
    String flag9 = "none";
    String drawAccNo;
    String drawerName;
    private InstrumentsForTheSameVoucherGrid instrSaveVouch;
    String bcbpNo;
    String remarks;
    String relatedTo;
    boolean flag10 = false;
    List<SelectItem> relatedToList = new ArrayList<SelectItem>();
    String flag11;
    String flag12;
    String flag13;
    boolean flag14;
    String flag15;
    boolean flag16;
    String flag17;
    String voucherNo;
    int currentRow;
    String resultOfSave;
    String accNoPopup;
    String instrNoPopup;
    String instrTypePopup;
    String instrAmtPopup;
    Date instrDatePopup;
    String micrCode1Popup;
    String micrCode2Popup;
    String micrCode3Popup;
    String bankAddressPopup;
    String bankNamePopup;
    boolean flag18;
    boolean flag19;
    String flag20;
    String flag21 = "none";
    private List<InstrumentsForTheSameVoucherGrid> instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
    private List<InstrumentsForTheSameVoucherGrid> instrTempList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
    //List accnoList = new ArrayList();
    String errorMsgPopup;
    String flag25;
    String acnoFlag;
    String accInfoFlag;
    String flag30 = "none";
    String accStatus = "";
    String accStatusFlag;
    String accountNoSanNo;
    String transactionCode;
    String accountName;
    String fileFlag = "none";
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    private final String jndiHomeNameOther = "OtherTransactionManagementFacade";
    private OtherTransactionManagementFacadeRemote otherRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeName = "TransactionManagementFacade";
    String oldAccNo;
    String acctNature;
    String acctopeningdate, acNoLen;
    List<AccountTypeDescPojo> accTypeDescList;
    private int ctsSponsor = 0;

    public String getAcctopeningdate() {
        return acctopeningdate;
    }

    public void setAcctopeningdate(String acctopeningdate) {
        this.acctopeningdate = acctopeningdate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public List<SelectItem> getAlphaCodeList() {
        return alphaCodeList;
    }

    public void setAlphaCodeList(List<SelectItem> alphaCodeList) {
        this.alphaCodeList = alphaCodeList;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNames() {
        return bankNames;
    }

    public void setBankNames(String bankNames) {
        this.bankNames = bankNames;
    }

    public List<SelectItem> getBankNamesList() {
        return bankNamesList;
    }

    public void setBankNamesList(List<SelectItem> bankNamesList) {
        this.bankNamesList = bankNamesList;
    }

    public String getBcbpNo() {
        return bcbpNo;
    }

    public void setBcbpNo(String bcbpNo) {
        this.bcbpNo = bcbpNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchNames() {
        return branchNames;
    }

    public void setBranchNames(String branchNames) {
        this.branchNames = branchNames;
    }

    public List<SelectItem> getBranchNamesList() {
        return branchNamesList;
    }

    public void setBranchNamesList(List<SelectItem> branchNamesList) {
        this.branchNamesList = branchNamesList;
    }

    public String getCircleType() {
        return circleType;
    }

    public void setCircleType(String circleType) {
        this.circleType = circleType;
    }

    public List<SelectItem> getCircleTypeList() {
        return circleTypeList;
    }

    public void setCircleTypeList(List<SelectItem> circleTypeList) {
        this.circleTypeList = circleTypeList;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDrawAccNo() {
        return drawAccNo;
    }

    public void setDrawAccNo(String drawAccNo) {
        this.drawAccNo = drawAccNo;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public List<SelectItem> getEntryTypeList() {
        return entryTypeList;
    }

    public void setEntryTypeList(List<SelectItem> entryTypeList) {
        this.entryTypeList = entryTypeList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag10() {
        return flag10;
    }

    public void setFlag10(boolean flag10) {
        this.flag10 = flag10;
    }

    public String getFlag11() {
        return flag11;
    }

    public void setFlag11(String flag11) {
        this.flag11 = flag11;
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

    public boolean isFlag4() {
        return flag4;
    }

    public void setFlag4(boolean flag4) {
        this.flag4 = flag4;
    }

    public String getFlag5() {
        return flag5;
    }

    public void setFlag5(String flag5) {
        this.flag5 = flag5;
    }

    public String getFlag6() {
        return flag6;
    }

    public void setFlag6(String flag6) {
        this.flag6 = flag6;
    }

    public String getInstrAmt() {
        return instrAmt;
    }

    public void setInstrAmt(String instrAmt) {
        this.instrAmt = instrAmt;
    }

    public Date getInstrDate() {
        return instrDate;
    }

    public void setInstrDate(Date instrDate) {
        this.instrDate = instrDate;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public List<InstrumentsForTheSameVoucherGrid> getInstrSaveList() {
        return instrSaveList;
    }

    public void setInstrSaveList(List<InstrumentsForTheSameVoucherGrid> instrSaveList) {
        this.instrSaveList = instrSaveList;
    }

    public InstrumentsForTheSameVoucherGrid getInstrSaveVouch() {
        return instrSaveVouch;
    }

    public void setInstrSaveVouch(InstrumentsForTheSameVoucherGrid instrSaveVouch) {
        this.instrSaveVouch = instrSaveVouch;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getNextChq() {
        return nextChq;
    }

    public void setNextChq(String nextChq) {
        this.nextChq = nextChq;
    }

    public List<SelectItem> getNextChqList() {
        return nextChqList;
    }

    public void setNextChqList(List<SelectItem> nextChqList) {
        this.nextChqList = nextChqList;
    }

    public String getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(String regisDate) {
        this.regisDate = regisDate;
    }

    public List<SelectItem> getRegisDateList() {
        return regisDateList;
    }

    public void setRegisDateList(List<SelectItem> regisDateList) {
        this.regisDateList = regisDateList;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    public List<SelectItem> getRelatedToList() {
        return relatedToList;
    }

    public void setRelatedToList(List<SelectItem> relatedToList) {
        this.relatedToList = relatedToList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public UnverifiedInstrumentsGrid getUnverifyInstr() {
        return unverifyInstr;
    }

    public void setUnverifyInstr(UnverifiedInstrumentsGrid unverifyInstr) {
        this.unverifyInstr = unverifyInstr;
    }

    public List<UnverifiedInstrumentsGrid> getUnverifyInstrList() {
        return unverifyInstrList;
    }

    public void setUnverifyInstrList(List<UnverifiedInstrumentsGrid> unverifyInstrList) {
        this.unverifyInstrList = unverifyInstrList;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getFlag8() {
        return flag8;
    }

    public void setFlag8(String flag8) {
        this.flag8 = flag8;
    }

    public String getFlag9() {
        return flag9;
    }

    public void setFlag9(String flag9) {
        this.flag9 = flag9;
    }

//    public List getAccnoList() {
//        return accnoList;
//    }
//
//    public void setAccnoList(List accnoList) {
//        this.accnoList = accnoList;
//    }
    public String getFlag12() {
        return flag12;
    }

    public void setFlag12(String flag12) {
        this.flag12 = flag12;
    }

    public String getFlag13() {
        return flag13;
    }

    public void setFlag13(String flag13) {
        this.flag13 = flag13;
    }

    public boolean isFlag14() {
        return flag14;
    }

    public void setFlag14(boolean flag14) {
        this.flag14 = flag14;
    }

    public String getFlag15() {
        return flag15;
    }

    public void setFlag15(String flag15) {
        this.flag15 = flag15;
    }

    public boolean isFlag16() {
        return flag16;
    }

    public void setFlag16(boolean flag16) {
        this.flag16 = flag16;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getResultOfSave() {
        return resultOfSave;
    }

    public void setResultOfSave(String resultOfSave) {
        this.resultOfSave = resultOfSave;
    }

    public String getFlag17() {
        return flag17;
    }

    public void setFlag17(String flag17) {
        this.flag17 = flag17;
    }

    public String getAccNoPopup() {
        return accNoPopup;
    }

    public void setAccNoPopup(String accNoPopup) {
        this.accNoPopup = accNoPopup;
    }

    public String getBankAddressPopup() {
        return bankAddressPopup;
    }

    public void setBankAddressPopup(String bankAddressPopup) {
        this.bankAddressPopup = bankAddressPopup;
    }

    public String getBankNamePopup() {
        return bankNamePopup;
    }

    public void setBankNamePopup(String bankNamePopup) {
        this.bankNamePopup = bankNamePopup;
    }

    public String getInstrAmtPopup() {
        return instrAmtPopup;
    }

    public void setInstrAmtPopup(String instrAmtPopup) {
        this.instrAmtPopup = instrAmtPopup;
    }

    public Date getInstrDatePopup() {
        return instrDatePopup;
    }

    public void setInstrDatePopup(Date instrDatePopup) {
        this.instrDatePopup = instrDatePopup;
    }

    public String getInstrNoPopup() {
        return instrNoPopup;
    }

    public void setInstrNoPopup(String instrNoPopup) {
        this.instrNoPopup = instrNoPopup;
    }

    public String getInstrTypePopup() {
        return instrTypePopup;
    }

    public void setInstrTypePopup(String instrTypePopup) {
        this.instrTypePopup = instrTypePopup;
    }

    public String getMicrCode1Popup() {
        return micrCode1Popup;
    }

    public void setMicrCode1Popup(String micrCode1Popup) {
        this.micrCode1Popup = micrCode1Popup;
    }

    public String getMicrCode2Popup() {
        return micrCode2Popup;
    }

    public void setMicrCode2Popup(String micrCode2Popup) {
        this.micrCode2Popup = micrCode2Popup;
    }

    public String getMicrCode3Popup() {
        return micrCode3Popup;
    }

    public void setMicrCode3Popup(String micrCode3Popup) {
        this.micrCode3Popup = micrCode3Popup;
    }

    public boolean isFlag18() {
        return flag18;
    }

    public void setFlag18(boolean flag18) {
        this.flag18 = flag18;
    }

    public boolean isFlag19() {
        return flag19;
    }

    public void setFlag19(boolean flag19) {
        this.flag19 = flag19;
    }

    public String getErrorMsgPopup() {
        return errorMsgPopup;
    }

    public void setErrorMsgPopup(String errorMsgPopup) {
        this.errorMsgPopup = errorMsgPopup;
    }

    public String getFlag20() {
        return flag20;
    }

    public void setFlag20(String flag20) {
        this.flag20 = flag20;
    }

    public String getFlag21() {
        return flag21;
    }

    public void setFlag21(String flag21) {
        this.flag21 = flag21;
    }

    public String getFlag25() {
        return flag25;
    }

    public void setFlag25(String flag25) {
        this.flag25 = flag25;
    }

    public String getAcnoFlag() {
        return acnoFlag;
    }

    public void setAcnoFlag(String acnoFlag) {
        this.acnoFlag = acnoFlag;
    }

    public String getAccInfoFlag() {
        return accInfoFlag;
    }

    public void setAccInfoFlag(String accInfoFlag) {
        this.accInfoFlag = accInfoFlag;
    }

    public List<InstrumentsForTheSameVoucherGrid> getInstrTempList() {
        return instrTempList;
    }

    public void setInstrTempList(List<InstrumentsForTheSameVoucherGrid> instrTempList) {
        this.instrTempList = instrTempList;
    }

    public String getFlag30() {
        return flag30;
    }

    public void setFlag30(String flag30) {
        this.flag30 = flag30;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccStatusFlag() {
        return accStatusFlag;
    }

    public void setAccStatusFlag(String accStatusFlag) {
        this.accStatusFlag = accStatusFlag;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public List<AccountTypeDescPojo> getAccTypeDescList() {
        return accTypeDescList;
    }

    public void setAccTypeDescList(List<AccountTypeDescPojo> accTypeDescList) {
        this.accTypeDescList = accTypeDescList;
    }

    public String getAccountNoSanNo() {
        return accountNoSanNo;
    }

    public void setAccountNoSanNo(String accountNoSanNo) {
        this.accountNoSanNo = accountNoSanNo;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }

    public OutwardClearingRegister() {
        try {
            this.setAcNoLen(getAcNoLength());
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            otherRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOther);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);

            getDropDownOnLoad();
            this.setNextChq("1");

            unverifyInstrList = new ArrayList<UnverifiedInstrumentsGrid>();
            instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
            instrTempList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
            this.setBankCode1(outwardClgRemote.getCityCodeAsMiccr(getOrgnBrCode()));
            int fileData = ftsPostRemote.getCodeForReportName("CTS_FILE_DATA");
            if (fileData == 1) {
                this.fileFlag = "";
            } else {
                this.fileFlag = "none";
            }
            ctsSponsor = ftsPostRemote.getCodeForReportName("CTS-SPONSOR");
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getDropDownOnLoad() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            entryTypeList = new ArrayList<SelectItem>();
            entryTypeList.add(new SelectItem("", "--Select--"));
            entryTypeList.add(new SelectItem("0", "GENERAL"));
            entryTypeList.add(new SelectItem("1", "BC"));
            entryTypeList.add(new SelectItem("2", "BP"));
            ///////////////////////////////////////////////////////////////////////////////////
            List listForBankNames = outwardClgRemote.loadBankNames();
            bankNamesList = new ArrayList<SelectItem>();
            bankNamesList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < listForBankNames.size(); i++) {
                Vector vecForBankNames = (Vector) listForBankNames.get(i);
                bankNamesList.add(new SelectItem(vecForBankNames.get(0).toString(), vecForBankNames.get(0).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////
            nextChqList = new ArrayList<SelectItem>();
            nextChqList.add(new SelectItem("0", "Yes"));
            nextChqList.add(new SelectItem("1", "No"));
            //////////////////////////////////////////////////////////////////////////////////
            branchNamesList.add(new SelectItem("0", "--Select--"));
            ///////////////////////////////////////////////////////////////////////////////////
            List alphaCod = outwardClgRemote.loadAlphaCode(getOrgnBrCode());
            alphaCodeList = new ArrayList<SelectItem>();
            alphaCodeList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < alphaCod.size(); i++) {
                Vector vecForalphaCod = (Vector) alphaCod.get(i);
                alphaCodeList.add(new SelectItem(vecForalphaCod.get(0).toString(), vecForalphaCod.get(0).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////////
            List circleTyp = otherRemote.getClearingOption();
            circleTypeList = new ArrayList<SelectItem>();
            circleTypeList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < circleTyp.size(); i++) {
                Vector vecForcircleTyp = (Vector) circleTyp.get(i);
                circleTypeList.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////////
            String date = outwardClgRemote.instrDate(getOrgnBrCode());
            date = date.substring(6) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
            this.setInstrDate(sdf.parse(date));
            ///////////////////////////////////////////////////////////////////////////////////////
            regisDateList = new ArrayList<SelectItem>();
            regisDateList.add(new SelectItem(" ", ""));
            ///////////////////////////////////////////////////////////////////////////////////////
            List l3 = txnRemote.getDropdownDataOnLoad("42");
            for (int i = 0; i < l3.size(); i++) {
                Vector v3 = (Vector) l3.get(i);
                relatedToList.add(new SelectItem(v3.get(0).toString(), v3.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////////
            // this.setBankCode1("110");

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getDetailsOnBlurAccountNo() {
        flag16 = false;
        flag3 = false;
        flag4 = false;
        String[] array = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
            //accnoList = new ArrayList();
            if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo == null) {
                this.setErrorMsg("Account number can not be blank !");
                this.setCustName("");
                this.setAccountName("");
                this.setAcctopeningdate("");
                this.setJtName("");
                acnoFlag = "true";
                return;
            }
            this.setErrorMsg("");
            acnoFlag = "false";
            if (this.nextChq.equalsIgnoreCase("y")) {
                this.setErrorMsg("Next Cheque can not be yes!");
                acnoFlag = "true";
                return;
            }
            this.setErrorMsg("");
            acnoFlag = "false";
            //if (this.oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMsg("Please Enter Proper Account No!");
                this.setCustName("");
                this.setAccountName("");
                this.setAcctopeningdate("");
                this.setJtName("");
                return;
            }
            accountNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            acctNature = ftsPostRemote.getAccountNature(accountNo);
            String result = outwardClgRemote.cbsOutClearRegAccInfo(this.accountNo, getOrgnBrCode(), acctNature);
            if (result.contains(":")) {
                accInfoFlag = "";
                String spliter = ":";
                array = result.split(spliter);
                flag5 = "true";
                accStatusFlag = array[8];
                if (array.length > 9) {
                    acctopeningdate = sdf.format(ymd.parse(array[9]));
                }

                this.setCustName(array[1]);
                this.setAccountName(array[1].length() > 25 ? array[1].substring(0, 25) : array[1]);
                if ((array[2] == null) || (array[2].equals("null"))) {
                    array[2] = "";
                }
                if ((array[3] == null) || (array[3].equals("null"))) {
                    array[3] = "";
                }
                if ((array[4] == null) || (array[4].equals("null"))) {
                    array[4] = "";
                }
                this.setJtName(array[2] + "" + array[3] + "" + array[4]);

                if (array[7].equalsIgnoreCase(CbsConstant.TERM_LOAN) || array[7].equalsIgnoreCase(CbsConstant.RECURRING_AC)
                        || array[7].equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || array[7].equalsIgnoreCase(CbsConstant.SS_AC)) {
                    this.setRelatedTo("1");
                } else {
                    this.setRelatedTo("0");
                }
                if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    if (array[0].equalsIgnoreCase("11") || array[0].equalsIgnoreCase("12") || array[0].equalsIgnoreCase("13") || array[0].equalsIgnoreCase("14")) {
                        flag8 = "";
                    } else {
                        flag8 = "none";
                    }
                } else {
                    flag8 = "none";
                }

            } else {
                flag5 = "false";
                setOldAccNo("");
                accountNo = "";
                this.setErrorMsg(result);
                this.setCustName("");
                this.setAccountName("");
                this.setAcctopeningdate("");
                this.setJtName("");
                accInfoFlag = result;
                acnoFlag = "true";
            }

//            String msg = "";
//            if (!acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                msg = outwardClgRemote.accStatus(accountNo, getOrgnBrCode());
//            }
//
//            if (msg.equalsIgnoreCase("Account Has been marked Inoperative")) {
//                throw new ApplicationException(msg);
//            }
//
//            if (msg.equalsIgnoreCase("")) {
//                flag30 = "none";
//            } else {
//                this.setAccStatus(msg);
//                flag30 = "";
//            }
        } catch (ApplicationException e) {
            setOldAccNo("");
            accountNo = "";
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            setOldAccNo("");
            accountNo = "";
            this.setErrorMsg(e.getMessage());
        }
    }

    public void visibilityInvisibilityDropdownBankNameAndAddrEntryTypeGrid() {
        try {
            instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
            if (this.circleType.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please Select Some circle Type.");
                return;
            }
            int fileData = ftsPostRemote.getCodeForReportName("CTS_FILE_DATA");
            if (fileData == 1) {
                this.fileFlag = "";
            } else {
                this.fileFlag = "none";
            }
            this.setErrorMsg("");
            if (this.circleType.equalsIgnoreCase("A") || this.circleType.equalsIgnoreCase("C") || this.circleType.equalsIgnoreCase("E") || this.circleType.equalsIgnoreCase("G")) {
                flag3 = false;
                flag4 = true;
                flag2 = "false";
            } else if (this.circleType.equalsIgnoreCase("B") || this.circleType.equalsIgnoreCase("D") || this.circleType.equalsIgnoreCase("F")) {
                flag2 = "true";
                flag3 = true;
                flag4 = false;
                List listForBankNames = outwardClgRemote.loadBankNames();
                bankNamesList = new ArrayList<SelectItem>();
                bankNamesList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < listForBankNames.size(); i++) {
                    Vector vecForBankNames = (Vector) listForBankNames.get(i);
                    bankNamesList.add(new SelectItem(vecForBankNames.get(0).toString(), vecForBankNames.get(0).toString()));
                }
            }

            List listForRegisDate = outwardClgRemote.getRegisterdate(circleType, getOrgnBrCode());
            regisDateList = new ArrayList<SelectItem>();
            regisDateList.add(new SelectItem(" ", ""));
            for (int i = 0; i < listForRegisDate.size(); i++) {
                Vector vecForRegisdate = (Vector) listForRegisDate.get(i);
                String regdate = vecForRegisdate.get(0).toString();
                regdate = regdate.substring(8, 10) + "/" + regdate.substring(5, 7) + "/" + regdate.substring(0, 4);
                regisDateList.add(new SelectItem(regdate));
            }

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getUnverifiedInstrumentsOnRegisDatelostFocus() {
        flag10 = false;
        try {
            instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
            unverifyInstrList = new ArrayList<UnverifiedInstrumentsGrid>();
            if (this.regisDate.equalsIgnoreCase(" ")) {
                this.setErrorMsg("Please Select Some Register Date.");
                return;
            } else {
                this.setErrorMsg("");
                List listForUnverifyInstr = outwardClgRemote.getUnverifyInstrument(getOrgnBrCode(), this.circleType, this.regisDate);
                if (!listForUnverifyInstr.isEmpty()) {
                    for (int i = 0; i < listForUnverifyInstr.size(); i++) {
                        Vector vecForUnverifyInstr = (Vector) listForUnverifyInstr.get(i);
                        unverifyInstr = new UnverifiedInstrumentsGrid();
                        unverifyInstr.setAcNo(vecForUnverifyInstr.get(0).toString());
                        unverifyInstr.setTxnInstrNo(vecForUnverifyInstr.get(1).toString());
                        //unverifyInstr.setTxnInstrAmt(formatter.format(vecForUnverifyInstr.get(2).toString()).toString());
                        unverifyInstr.setTxnInstrAmt(formatter.format(Double.parseDouble((vecForUnverifyInstr.get(2).toString()).toString())));
                        unverifyInstr.setEmflag(vecForUnverifyInstr.get(3).toString());
                        unverifyInstr.setVtot(vecForUnverifyInstr.get(4).toString());
                        unverifyInstrList.add(unverifyInstr);
                    }
                } else {
                    unverifyInstrList.clear();
                }
            }

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    /**
     * **************************************************************************************************************************
     */
    public String instrdatelostFocus() {
        try {
            flag13 = "false";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (accountNo.equals("")) {
                throw new ApplicationException("Please fill account Number");
            }
            // String chequeMsg = ftsPostRemote.ftsInstDateValidate(sdf.format(instrDate));

            String chequeMsg = outwardClgRemote.owInstDateValidate(sdf.format(instrDate), this.regisDate);

            if (!chequeMsg.equalsIgnoreCase("True")) {
                flag12 = chequeMsg;
                flag13 = "true";
                return chequeMsg;
            }
            //Code commented by Dhirendra Singh due to the bug no 11634
//            else {
//                flag13 = "false";
//                if (!acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                    // chkinstrdateOpDate();
//                    String instChk = outwardClgRemote.instrDateCheck(sdf.format(instrDate), this.accountNo);
//                    if (!instChk.equalsIgnoreCase("true")) {
//                        flag12 = instChk;
//                        flag13 = "true";
//                        return chequeMsg;
//                    }
//                }
//            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return "true";
    }

    /**
     * **************************************************************************************************************************
     */
    public boolean bankCode3LostFocus() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher bankCode3Check = p.matcher(this.bankCode3);
            if (flag2.equalsIgnoreCase("false")) {
                if (!bankCode3Check.matches()) {
                    this.setErrorMsg("Please enter some Integer Value in Bank Code.");
                    return false;
                } else {
                    this.setErrorMsg("");
                }
                List listForBankBranch = outwardClgRemote.instrUpdtDelRegBankDetail(bankCode1, bankCode2, bankCode3);
                if (listForBankBranch.isEmpty()) {
                    this.setErrorMsg("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
                    return false;
                } else {
                    Vector vecBankBranch = (Vector) listForBankBranch.get(0);
                    this.setBankName(vecBankBranch.get(0).toString());
                    this.setBranchName(vecBankBranch.get(1).toString());
                    flag4 = true;
                    flag4 = true;
                }
            } else if (flag2.equalsIgnoreCase("true")) {
                this.setBankCode3("000");
            }

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return true;
    }

    public void bankCode1LostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode1Check = p.matcher(this.bankCode1);
        if (!bankCode1Check.matches()) {
            this.setErrorMsg("Please enter some Integer Value in Bank Code.");
            return;
        } else {
            this.setErrorMsg("");

        }
    }

    public void bankCode2LostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode2Check = p.matcher(this.bankCode2);
        if (flag2.equalsIgnoreCase("false")) {
            if (!bankCode2Check.matches()) {
                this.setErrorMsg("Please enter some Integer Value in Bank Code.");
                return;
            } else {
                this.setErrorMsg("");
            }
        } else if (flag2.equalsIgnoreCase("true")) {
            this.setBankCode2("000");
        }

    }

    public boolean instrAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher instrAmtCheck = p.matcher(this.instrAmt);
        if (!instrAmtCheck.matches()) {
            flag6 = "true";
            this.setErrorMsg("Please enter a numeric value in Instrument Amount");
            return false;
        } else {
            flag6 = "false";
            this.setErrorMsg("");
            return true;
        }
    }

    public boolean instrNumber() {

        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher instrNoCheck = p.matcher(this.instrNo);
        if (!instrNoCheck.matches()) {
            flag25 = "true";
            this.setErrorMsg("Please enter a numeric value in Instrument No.");
            return false;
        } else {
            flag25 = "false";
            this.setErrorMsg("");
            return true;
        }
    }

    public void isAccountName() {
        this.setErrorMsg("");
        try {
            if (this.accountName == null || this.accountName.equalsIgnoreCase("")) {
                this.setErrorMsg("Please fill account name.");
                return;
            }
            List list = ftsPostRemote.getNameForcts(this.accountName, this.accountNo);
            if (list.isEmpty()) {
                this.setErrorMsg("Please fill correct Account Name.");
                return;
            } else {
                Vector vtr = (Vector) list.get(0);
                String acCustName = vtr.get(0).toString();
                String jtName1 = vtr.get(1).toString();
                String jtName2 = vtr.get(2).toString();
                String jtName3 = vtr.get(3).toString();
                String jtName4 = vtr.get(4).toString();
                if (!(acCustName.trim().contains(this.accountName.trim())
                        || jtName1.trim().contains(this.accountName.trim())
                        || jtName2.trim().contains(this.accountName.trim())
                        || jtName3.trim().contains(this.accountName.trim())
                        || jtName4.trim().contains(this.accountName.trim()))) {

                    this.setErrorMsg("Please fill correct account name.");
                    return;
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void drawerAccNoLostFocus() {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (this.oldAccNo.equalsIgnoreCase("")) {
                this.setErrorMsg("A/C No. Can Not Be Blank");
                return;
            }
            //if (this.oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMsg("Please Enter Proper A/C No.");
                return;
            }
            if (!accInfoFlag.equals("")) {
                this.setErrorMsg(accInfoFlag);
                return;
            }
            if (this.instrAmt.equalsIgnoreCase("")) {
                this.setErrorMsg("Instrument Amount Can Not Be Blank");
                return;
            }
            if (this.instrNo.equalsIgnoreCase("")) {
                this.setErrorMsg("Instrument No Can Not Be Blank");
                return;
            }
            if (this.bankCode1.equalsIgnoreCase("")) {
                this.setErrorMsg("Bank Code Can Not Be Blank");
                return;
            }
            if (this.bankCode2.equalsIgnoreCase("")) {
                this.setErrorMsg("Bank Code Can Not Be Blank");
                return;
            }
            if (this.bankCode3.equalsIgnoreCase("")) {
                this.setErrorMsg("Bank Code Can Not Be Blank");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher bankCode1Check = p.matcher(this.bankCode1);
            if (!bankCode1Check.matches()) {
                this.setErrorMsg("Please enter some Integer Value in Bank Code.");
                return;
            }
            if (!instrAmount()) {
                this.setErrorMsg("Please enter a numeric value in Instrument Amount");
                return;
            }
            if (!instrNumber()) {
                this.setErrorMsg("Please enter a numeric value in Instrument No.");
                return;
            }
            if (!bankCode3LostFocus()) {
                this.setErrorMsg("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
                return;
            }
            if (Double.parseDouble(instrAmt) < 0) {
                this.setErrorMsg("Please fill positive value in Instrument Amount");
                return;
            }
            if (Double.parseDouble(instrNo) < 0) {
                this.setErrorMsg("Please fill positive value in Instrument No.");
                return;
            }
            /**
             * New Addition*
             */
            List paramList = ftsPostRemote.isModuleActiveBasedOnAcCode(ftsPostRemote.getAccountCode(accountNo));
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String modFlag = paramVector.get(2).toString();
                if (modFlag.equalsIgnoreCase("N")) {
                    this.setErrorMsg("Outward Clearing is not allowed for this type of account.");
                    return;
                }
            }
            /**
             * New Addition End*
             */
            if (flag2.equalsIgnoreCase("true")) {
                if (this.bankNames.equals("0")) {
                    this.setErrorMsg("Please select bank name.");
                    return;
                }
                if (this.branchNames.equals("0")) {
                    this.setErrorMsg("Please select branch name.");
                    return;
                }
            }
            int fileData = ftsPostRemote.getCodeForReportName("CTS_FILE_DATA");
            if (fileData == 1) {
                if (ctsSponsor != 2) {
                    if (this.accountNoSanNo == null || this.accountNoSanNo.equalsIgnoreCase("")) {
                        this.setErrorMsg("Please fill account no / san no.");
                        return;
                    }
                }

                if (this.transactionCode == null || this.transactionCode.equalsIgnoreCase("")) {
                    this.setErrorMsg("Please fill transaction code.");
                    return;
                }
                if (this.accountName == null || this.accountName.equalsIgnoreCase("")) {
                    this.setErrorMsg("Please fill account name.");
                    return;
                }
                //Account Name Validation
                List list = ftsPostRemote.getNameForcts(this.accountName, this.accountNo);
                if (list.isEmpty()) {
                    this.setErrorMsg("Please fill correct Account Name.");
                    return;
                } else {
                    Vector vtr = (Vector) list.get(0);
                    String acCustName = vtr.get(0).toString();
                    String jtName1 = vtr.get(1).toString();
                    String jtName2 = vtr.get(2).toString();
                    String jtName3 = vtr.get(3).toString();
                    String jtName4 = vtr.get(4).toString();
                    if (!(acCustName.trim().contains(this.accountName.trim())
                            || jtName1.trim().contains(this.accountName.trim())
                            || jtName2.trim().contains(this.accountName.trim())
                            || jtName3.trim().contains(this.accountName.trim())
                            || jtName4.trim().contains(this.accountName.trim()))) {

                        this.setErrorMsg("Please fill correct account name.");
                        return;
                    }
                }
            }

            String regDate = regisDate.substring(6) + regisDate.substring(3, 5) + regisDate.substring(0, 2);
            List list = outwardClgRemote.getDuplicateChqInfo(accountNo, instrNo, regDate, bankCode1, bankCode2, bankCode3, this.circleType, getOrgnBrCode());
            if (!list.isEmpty()) {
                this.setErrorMsg("This Instrument No. Already Exists In Records");
                return;
            }

            /**
             * Code Added For ThreshHold Limit Checking *
             */
            String AcNature = ftsPostRemote.getAccountNature(accountNo);
            if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || AcNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
                int thLmtParam = 0;
                List chParamThresh = ftsPostRemote.getThreshLmtParam();
                if (!(chParamThresh == null || chParamThresh.isEmpty())) {
                    Vector verLst = (Vector) chParamThresh.get(0);
                    thLmtParam = Integer.parseInt(verLst.get(0).toString());
                }
                if (thLmtParam == 1) {
                    String chkThresh = ftsPostRemote.isThreshLmtExceed(accountNo, Double.parseDouble(this.instrAmt), ymmd.format(ymd.parse(regDate)));
                    if (!chkThresh.equalsIgnoreCase("true")) {
                        this.setErrorMsg(chkThresh);
                        return;
                    }
                }
            }

            if (this.nextChq.equals("0")) {
                if (!instrTempList.isEmpty()) {
                    for (int i = 0; i < instrTempList.size(); i++) {
                        String instruNo = instrTempList.get(i).getInstructionNo();
                        String bank1 = instrTempList.get(i).getCityCode();
                        String bank2 = instrTempList.get(i).getBnkCode();
                        String bank3 = instrTempList.get(i).getBrnchCode();
                        if (instruNo.equalsIgnoreCase(this.instrNo) && bank1.equalsIgnoreCase(bankCode1) && bank2.equalsIgnoreCase(bankCode2) && bank3.equalsIgnoreCase(bankCode3)) {
                            this.setErrorMsg("This Instrument No. Has Been Already Used For This Bank");
                            return;
                        }
                    }
                }
            }
            this.setErrorMsg("");
            flag21 = "";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        this.setErrorMsg("");
            instrSaveVouch = new InstrumentsForTheSameVoucherGrid();

            //accnoList.add(this.accountNo);
            instrSaveVouch.setInstructionNo(ctsSponsor == 2 ? ParseFileUtil.addTrailingZeros(instrNo, 6) : instrNo);
            instrSaveVouch.setInstructionDate(sdf.format(instrDate));
            instrSaveVouch.setAmount(formatter.format(Double.parseDouble(this.instrAmt)));
            if (drawerName.equalsIgnoreCase("") || drawerName == null) {
                instrSaveVouch.setDrawerName("N.A.");
            } else {
                instrSaveVouch.setDrawerName(drawerName);
            }
            if (drawAccNo.equalsIgnoreCase("") || drawAccNo == null) {
                instrSaveVouch.setDrawerAccNo("N.A.");
            } else {
                instrSaveVouch.setDrawerAccNo(drawAccNo);
            }
            if (branchNames == null || branchNames.equals("0")) {
                instrSaveVouch.setBnkName(bankName);
                instrSaveVouch.setBnkAddress(this.branchName);
            } else {
                instrSaveVouch.setBnkName(bankNames);
                instrSaveVouch.setBnkAddress(this.branchNames);
            }
            instrSaveVouch.setCityCode(this.bankCode1);
            instrSaveVouch.setBnkCode(bankCode2);
            instrSaveVouch.setBrnchCode(bankCode3);
            instrSaveVouch.setAlphaCode("");
            instrSaveVouch.setBcbpCode("");
            instrSaveVouch.setBillType("");
            instrSaveVouch.setCircleMode(circleType);
            instrSaveVouch.setFyear(sdf.format(instrDate).substring(6));
            instrSaveVouch.setRemarks(this.remarks);
            instrSaveVouch.setTransdes(this.relatedTo);
            instrSaveVouch.setAcNosanNo(this.accountNoSanNo);
            instrSaveVouch.setTransactionCode(this.transactionCode);
            instrSaveVouch.setAccName(this.accountName);

            //instrSaveVouch.setTransdes("0");
            instrTempList.add(instrSaveVouch);

            instrSaveList.add(instrSaveVouch);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());

        }

    }

    public void getBranchNameOnBlurBankName() {
        try {
            List brnNames = outwardClgRemote.loadBranchNames(bankNames);
            if (!brnNames.isEmpty()) {
                branchNamesList = new ArrayList<SelectItem>();
                branchNamesList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < brnNames.size(); i++) {
                    Vector vecBrnname = (Vector) brnNames.get(i);
                    branchNamesList.add(new SelectItem(vecBrnname.get(0).toString(), vecBrnname.get(0).toString()));
                    this.setBankCode2(vecBrnname.get(1).toString());
                }

            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void visibilityOfBcbpAndAlphaCode() {

        if (this.entryType.equalsIgnoreCase("0")) {
            flag9 = "";
        } else {
            flag9 = "none";
        }
    }

    public void nextChqLostFocus() {
        if (this.oldAccNo.equalsIgnoreCase("")) {
            this.setErrorMsg("A/C No. Can Not Be Blank");
            return;
        }
        if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setErrorMsg("Please Enter Proper A/C No.");
            return;
        }
        if (!accInfoFlag.equals("")) {
            this.setErrorMsg(accInfoFlag);
            return;
        }
        if (this.instrAmt.equalsIgnoreCase("")) {
            this.setErrorMsg("Instrument Amount Can Not Be Blank");
            return;
        }
        if (this.instrNo.equalsIgnoreCase("")) {
            this.setErrorMsg("Instrument No Can Not Be Blank");
            return;
        }
        if (this.bankCode1.equalsIgnoreCase("")) {
            this.setErrorMsg("Bank Code Can Not Be Blank");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode1Check = p.matcher(this.bankCode1);
        if (!bankCode1Check.matches()) {
            this.setErrorMsg("Please enter some Integer Value in Bank Code.");
            return;
        }
        if (this.bankCode2.equalsIgnoreCase("")) {
            this.setErrorMsg("Bank Code Can Not Be Blank");
            return;
        }
        if (this.bankCode3.equalsIgnoreCase("")) {
            this.setErrorMsg("Bank Code Can Not Be Blank");
            return;
        }
        if (!instrAmount()) {
            this.setErrorMsg("Please enter a numeric value in Instrument Amount");
            return;
        }
        if (!instrNumber()) {
            this.setErrorMsg("Please enter a numeric value in Instrument No.");
            return;
        }
        if (!bankCode3LostFocus()) {
            this.setErrorMsg("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
            return;
        }
        if (Double.parseDouble(instrAmt) < 0) {
            this.setErrorMsg("Please fill positive value in Instrument Amount");
            return;
        }
        if (Double.parseDouble(instrNo) < 0) {
            this.setErrorMsg("Please fill positive value in Instrument No.");
            return;
        }
        if (flag2.equalsIgnoreCase("true")) {
            if (this.bankNames.equals("0")) {
                this.setErrorMsg("Please select bank name.");
                return;
            }
            if (this.branchNames.equals("0")) {
                this.setErrorMsg("Please select branch name.");
                return;
            }
        }
        if (this.nextChq.equalsIgnoreCase("0")) {
            flag10 = true;
            flag15 = "false";
            flag16 = false;
            flag3 = false;
            flag4 = false;
            refreshForm1();
        } else if (this.nextChq.equalsIgnoreCase("1")) {
            flag10 = false;
            flag15 = "true";
            refreshForm1();
        }
    }

    public String getVoucherNumber() {
        try {
            regisDate = regisDate.toString().trim();
            if ((circleType == null) || (circleType.equalsIgnoreCase("0"))) {
                this.setErrorMsg("Please Select Circle Type.");
                return "Please Select Circle Type.";
            }
            if ((regisDate == null) || (regisDate.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please Select Register Date.");
                return "Please Select Register Date.";
            }
            String date = this.regisDate.substring(6) + this.regisDate.substring(3, 5) + this.regisDate.substring(0, 2);
            List vouchrNo = outwardClgRemote.generateVoucherNo(this.circleType, date, getOrgnBrCode());

            if (instrSaveList.isEmpty()) {
                flag11 = "false";
            } else {
                if (!vouchrNo.isEmpty()) {
                    flag11 = "true";
                    Vector vecVouchNo = (Vector) vouchrNo.get(0);
                    this.setVoucherNo(vecVouchNo.get(0).toString());
                    return vecVouchNo.get(0).toString();
                } else {
                    flag11 = "false";
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
        return "";
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    public void saveGridData() {

        try {
            if (acnoFlag.equalsIgnoreCase("true")) {
                // accnoList = new ArrayList();
                resultOfSave = "Account no. is not valid";
                this.setAccountNo("");
                this.setOldAccNo("");
                instrSaveList.clear();
                return;
            }
            if (!instrdatelostFocus().equalsIgnoreCase("true")) {
                resultOfSave = instrdatelostFocus();
                return;
            }

            String voucher = getVoucherNumber();

            String saveResult = outwardClgRemote.cbsOwChqDeposit(instrSaveList, accountNo, getUserName(), this.circleType, this.regisDate, "", "", voucher, getOrgnBrCode());
            if (saveResult.equalsIgnoreCase("Success")) {
                resultOfSave = "Data has been saved for voucher no." + voucher;
                unverifyInstrList = new ArrayList<UnverifiedInstrumentsGrid>();
                List listForUnverifyInstr = outwardClgRemote.getUnverifyInstrument(getOrgnBrCode(), this.circleType, this.regisDate);
                if (!listForUnverifyInstr.isEmpty()) {
                    for (int i = 0; i < listForUnverifyInstr.size(); i++) {
                        Vector vecForUnverifyInstr = (Vector) listForUnverifyInstr.get(i);
                        unverifyInstr = new UnverifiedInstrumentsGrid();
                        unverifyInstr.setAcNo(vecForUnverifyInstr.get(0).toString());
                        unverifyInstr.setTxnInstrNo(vecForUnverifyInstr.get(1).toString());
                        unverifyInstr.setTxnInstrAmt(formatter.format(Double.parseDouble((vecForUnverifyInstr.get(2).toString()).toString())));
                        unverifyInstr.setEmflag(vecForUnverifyInstr.get(3).toString());
                        unverifyInstr.setVtot(vecForUnverifyInstr.get(4).toString());
                        unverifyInstrList.add(unverifyInstr);
                    }
                }

                //accnoList.clear();
                this.setAccountNo("");
                this.setOldAccNo("");
                    //this.setJtName("");
                //this.setCustName("");
                this.setAccountNoSanNo("");
                this.setTransactionCode("");
                this.setAccountName("");
                //   accnoList = new ArrayList();
                instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
                flag30 = "none";
            } else {
                resultOfSave = "Data has not been saved for voucher no." + voucher;
                //accnoList.clear();
                this.setAccountNo("");
                this.setOldAccNo("");
                //   accnoList = new ArrayList();
                instrSaveList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
                flag30 = "";
            }
            //}
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void setRowValues() {
        flag16 = false;
        flag3 = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.setInstrNo(instrSaveVouch.getInstructionNo().toString());
            this.setInstrAmt(instrSaveVouch.getAmount().toString());
            this.setInstrDate(sdf.parse(instrSaveVouch.getInstructionDate()));
            this.setRemarks(instrSaveVouch.getRemarks().toString());
            this.setBankCode1(instrSaveVouch.getCityCode().toString());
            this.setBankCode2(instrSaveVouch.getBnkCode().toString());
            this.setBankCode3(instrSaveVouch.getBrnchCode().toString());
            this.setBankName(instrSaveVouch.getBnkName().toString());
            this.setBranchName(instrSaveVouch.getBnkAddress().toString());
            this.setDrawerName(instrSaveVouch.getDrawerName().toString());
            this.setDrawAccNo(instrSaveVouch.getDrawerAccNo().toString());
            instrSaveList.remove(instrSaveVouch);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getDetailsOnPopUp() {

        String tableName = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.setErrorMsgPopup("");
            if ((circleType == null) || (this.circleType.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please Select Circle Type.");
                return;
            } else if ((regisDate == null) || (regisDate.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please Select Some Register Date.");
                return;
            } else {
                List listForLockInfo = outwardClgRemote.getLockInfo(this.circleType, this.regisDate, getOrgnBrCode());
                Vector vecForLockInfo = (Vector) listForLockInfo.get(0);
                if (vecForLockInfo.get(0).toString().equalsIgnoreCase("LOCKED")) {
                    resultOfSave = "Sorry! Instrument can't be updated/deleted.The selected register is locked right now.";
                    flag17 = "true";
                } else {
                    flag17 = "false";
                    List descList = outwardClgRemote.getDescription(this.circleType);
                    Vector circleDescVect = (Vector) descList.get(0);
                    String tmpEmFlag = circleDescVect.elementAt(0).toString();
                    if (this.circleType.equalsIgnoreCase("A")) {
                        tableName = "clg_ow_entry";
                        flag18 = true;
                        flag19 = false;
                    }
                    if (this.circleType.equalsIgnoreCase("B")) {
                        tableName = "clg_ow_entry";
                        flag18 = false;
                        flag19 = true;
                    }
                    if (this.circleType.equalsIgnoreCase("G")) {
                        tableName = "clg_ow_entry";
                        flag18 = true;
                        flag19 = false;
                    }
                    if (this.circleType.equalsIgnoreCase("C")) {
                        tableName = "clg_localchq";
                        flag18 = true;
                        flag19 = false;
                    }
                    if (this.circleType.equalsIgnoreCase("D")) {
                        tableName = "clg_localchq";
                        flag18 = true;
                        flag19 = false;
                    }
                    if (this.circleType.equalsIgnoreCase("E")) {
                        tableName = "clg_localchq";
                        flag18 = false;
                        flag19 = true;
                    }
                    if (this.circleType.equalsIgnoreCase("F")) {
                        tableName = "clg_localchq";
                        flag18 = true;
                        flag19 = false;
                    }
                    List listForPopupValues = outwardClgRemote.getDetailsOnPopup(unverifyInstr.getTxnInstrNo(), unverifyInstr.getAcNo(),
                            unverifyInstr.getEmflag(), unverifyInstr.getVtot(), tableName);

                    if (!listForPopupValues.isEmpty()) {
                        Vector vecForPopupValues = (Vector) listForPopupValues.get(0);
                        this.setAccNoPopup(vecForPopupValues.get(0).toString());
                        this.setInstrNoPopup(vecForPopupValues.get(4).toString());
                        this.setInstrTypePopup(tmpEmFlag);
                        this.setInstrAmtPopup(vecForPopupValues.get(5).toString());
                        String date = vecForPopupValues.get(7).toString();
                        date = date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);

                        this.setInstrDatePopup(sdf.parse(date));
                        this.setMicrCode1Popup(vecForPopupValues.get(10).toString());
                        this.setMicrCode2Popup(vecForPopupValues.get(11).toString());
                        this.setMicrCode3Popup(vecForPopupValues.get(12).toString());
                        this.setBankAddressPopup(vecForPopupValues.get(9).toString());
                        this.setBankNamePopup(vecForPopupValues.get(8).toString());
                    }
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void clickOnUpdateButton() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if ((instrAmtPopup == null) || (instrAmtPopup.equalsIgnoreCase(""))) {
                this.setErrorMsgPopup("Please enter inst amount !");
                return;
            } else {
                //Commented By Dhirendra Singh because it remove decimal part of amount
                /*if (instrAmtPopup.contains(".")) {
                 instrAmtPopup = instrAmtPopup.substring(0, instrAmtPopup.indexOf("."));
                 }*/
                if (Double.parseDouble(instrAmtPopup) <= 0) {
                    this.setErrorMsgPopup("Instrument Amount cannot be Negative or Zero.");
                    return;
                }
            }
            if (this.bankNamePopup.equalsIgnoreCase("")) {
                this.setErrorMsgPopup("BANK NAME IS BLANK !");
                return;
            }
            if (this.bankAddressPopup.equalsIgnoreCase("")) {
                this.setErrorMsgPopup("BANK ADDRESS IS BLANK !");
                return;
            }
            if (this.getErrorMsgPopup().equalsIgnoreCase("Code No. Does Not Exist,Please Add This Information Into Bank Directory.")) {
                this.setErrorMsgPopup("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
                return;
            }
            this.setErrorMsgPopup("");
            if (circleType.equalsIgnoreCase("--Select--")) {
                this.setErrorMsgPopup("Please Select Circle Type !");
                return;
            }
            String cbsOwEntryUpdateResult = outwardClgRemote.cbsOwEntryUpdate(sdf.format(this.instrDatePopup), this.circleType, this.accNoPopup,
                    this.instrAmtPopup, this.instrNoPopup, unverifyInstr.getTxnInstrNo(), this.bankAddressPopup, this.bankNamePopup,
                    unverifyInstr.getVtot(), regisDate, unverifyInstr.getAcNo(), getOrgnBrCode(), micrCode1Popup, micrCode2Popup, micrCode3Popup);
                this.setErrorMsgPopup(cbsOwEntryUpdateResult);
                refreshFormPopup();
            
        } catch (Exception e) {
            this.setErrorMsgPopup(e.getMessage());
        }
    }

    public void instrdatelostFocusPopUp() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String chequeMsg = outwardClgRemote.owInstDateValidate(sdf.format(instrDate), this.regisDate);
            if (!chequeMsg.equalsIgnoreCase("true")) {
                this.setErrorMsgPopup(chequeMsg);
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void clickOnDeleteButton() {
        try {
            String deleteResult = outwardClgRemote.cbsOwEntryDelete(this.circleType, this.accNoPopup, this.instrNoPopup, unverifyInstr.getVtot().toString(), "", getOrgnBrCode());
            this.setErrorMsgPopup(deleteResult);
            refreshFormPopup();
            
        } catch (Exception e) {
            this.setErrorMsgPopup(e.getMessage());
        }
    }

    public void refreshForm1() {
        try {
            this.setInstrAmt("");
            this.setInstrNo("");
            this.setRemarks("");
            this.setBankCode1(outwardClgRemote.getCityCodeAsMiccr(getOrgnBrCode()));
            this.setBankCode2("");
            this.setBankCode3("");
            this.setRelatedTo("");
            this.setBankName("");
            this.setBranchName("");
            this.setDrawAccNo("");
            this.setDrawerName("");
            this.setJtName("");

            if (flag2.equalsIgnoreCase("true")) {
                this.setBankNames("0");
                this.setBranchNames("0");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }

    }

    public void refreshFormPopup() {
        this.setAccNoPopup("");
        this.setInstrAmtPopup("");
        this.setInstrNoPopup("");
        this.setInstrTypePopup("");
        Date date = new Date();
        this.setInstrDatePopup(date);
        this.setMicrCode1Popup("");
        this.setMicrCode2Popup("");
        this.setMicrCode3Popup("");
        this.setBankAddressPopup("");
        this.setBankNamePopup("");
    }

    public void refreshForm() {
        try {
            this.setErrorMsg("");
            this.setAccountNo("");
            this.setOldAccNo("");
            this.setCircleType("--Select--");
            this.setRegisDate("");
            Date date = new Date();
            this.setInstrAmt("");
            this.setInstrNo("");
            this.setInstrDate(date);
            this.setRemarks("");
            this.setBankCode1(outwardClgRemote.getCityCodeAsMiccr(getOrgnBrCode()));
            this.setBankCode2("");
            this.setBankCode3("");
            this.setRelatedTo("");
            this.setBankName("");
            this.setBranchName("");
            this.setDrawAccNo("");
            this.setDrawerName("");
            this.setJtName("");
            this.setNextChq("1");
            this.setErrorMsgPopup("");
            this.setCustName("");
            this.setAcctopeningdate("");
            instrSaveList.clear();
            unverifyInstrList.clear();
            instrTempList = new ArrayList<InstrumentsForTheSameVoucherGrid>();
            flag21 = "none";
            flag16 = false;
            flag3 = false;
            flag4 = false;
            flag10 = false;
            flag30 = "none";
            flag2 = "false";

            bankNamesList = new ArrayList<SelectItem>();
            branchNamesList = new ArrayList<SelectItem>();
            bankNamesList.add(new SelectItem("0", "--Select--"));
            branchNamesList.add(new SelectItem("0", "--Select--"));
            this.setAccountNoSanNo("");
            this.setTransactionCode("");
            this.setAccountName("");
            this.setFileFlag("none");
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

    }

    public void accTypeNatureDesc() {
        try {
            accTypeDescList = new ArrayList<AccountTypeDescPojo>();
            AccountTypeDescPojo pojo;
            List resultList = ftsPostRemote.getAcctTypeDesc();
            if (!resultList.isEmpty()) {
                for (Object object : resultList) {
                    Vector element = (Vector) object;
                    pojo = new AccountTypeDescPojo();
                    pojo.setAcctCode((String) element.get(0));
                    pojo.setAccType((String) element.get(1));
                    pojo.setAccNature((String) element.get(2));
                    pojo.setAccDesc((String) element.get(3));
                    accTypeDescList.add(pojo);
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getBankNameAddressOnPopup() {
        try {
            List listForBankBranch = outwardClgRemote.instrUpdtDelRegBankDetail(micrCode1Popup, micrCode2Popup, micrCode3Popup);
            if (listForBankBranch.isEmpty()) {
                this.setErrorMsgPopup("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
            } else {
                Vector vecBankBranch = (Vector) listForBankBranch.get(0);
                this.setBankNamePopup(vecBankBranch.get(0).toString());
                this.setBankAddressPopup(vecBankBranch.get(1).toString());
                this.setErrorMsgPopup("");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void chkinstrdateOpDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String instChk = outwardClgRemote.instrDateCheck(sdf.format(instrDate), this.accountNo);
            if (!instChk.equalsIgnoreCase("true")) {
                this.setErrorMsg(instChk);
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }
}
