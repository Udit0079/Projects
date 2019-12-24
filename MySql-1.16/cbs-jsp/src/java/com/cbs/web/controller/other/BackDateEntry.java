/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.other.BackDateEntryPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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
public class BackDateEntry extends BaseBean {

    private String message;
    private String function;
    private String batch;
    private String batchdisplay = "none";
    private String modedisplay = "";
    private String panel4display = "";
    private String panel1display = "";
    private String panel2display = "";
    private String panel3display = "";
    private String panel8display = "";
    private String remarks;
    private String mode;
    private String acno;
    private String acNoLen;
    private String newAccountNo;
    private String glHeadname;
    private String txnType;
    private String valueDate;
    private BigDecimal totalcredit = BigDecimal.ZERO;
    private BigDecimal totaldebit = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private String btnLbl = "Save";
    private BigDecimal amount = BigDecimal.ZERO;
    private String focusId;
    private boolean disableBtn;
    private boolean selectRender;
    private String gridTableDisplay = "";
    private String verifyTableDisplay = "none";
    private List<SelectItem> FuncList;
    private List<SelectItem> txnTypeList;
    private List<SelectItem> modeList;
    private List<BackDateEntryPojo> gridTable;
    private List<BackDateEntryPojo> verifyTable;
    private BackDateEntryPojo CurrentItem;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiFtsName = "FtsPostingMgmtFacade";
    private CommonReportMethodsRemote commonReportRemote;
    private BankProcessManagementFacadeRemote bankProcess;
    private TransactionManagementFacadeRemote txnRemote;
    private boolean disabledate = false;

    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public BackDateEntry() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsName);
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            bankProcess = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup("BankProcessManagementFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            this.setAcNoLen(getAcNoLength());
            FuncList = new ArrayList<SelectItem>();
            FuncList.add(new SelectItem("0", "--Select--"));
            FuncList.add(new SelectItem("A", "ADD"));
            FuncList.add(new SelectItem("V", "VERIFY"));
            FuncList.add(new SelectItem("D", "DELETE"));

            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("CR", "CREDIT"));
            txnTypeList.add(new SelectItem("DR", "DEBIT"));

            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "--Select--"));
            modeList.add(new SelectItem("N", "Normal Transaction"));
            modeList.add(new SelectItem("Y", "Year End Transaction"));
            setFocusId("ddMode");

            this.disabledate = false;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void funcTypeAction() {
        if (function.equalsIgnoreCase("A")) {
            this.batchdisplay = "none";
            this.modedisplay = "";
            this.panel1display = "";
            this.panel2display = "";
            this.panel3display = "";
            this.panel4display = "";
            this.panel8display = "";
            this.btnLbl = "Save";
            this.verifyTableDisplay = "none";
            this.gridTableDisplay = "";
            this.setFocusId("ddMode");
        } else if (function.equalsIgnoreCase("V")) {
            this.batchdisplay = "";
            this.modedisplay = "none";
            this.panel1display = "none";
            this.panel2display = "none";
            this.panel3display = "none";
            this.panel4display = "none";
            this.panel8display = "none";
            this.btnLbl = "VERIFY";
            this.gridTableDisplay = "none";
            this.verifyTableDisplay = "";
            this.setMessage("Now enter the batch No. which you want to Authorize.");
            try {
                verifyTable = new ArrayList<BackDateEntryPojo>();
                verifyTable = txnRemote.getUnauthorizedData();
                if (verifyTable.isEmpty()) {
                    this.setMessage("There is no data .");
                }
            } catch (Exception ex) {
                this.setMessage(ex.getMessage());
            }
            this.setFocusId("txtbatchNo");
        } else if (function.equalsIgnoreCase("D")) {
            this.batchdisplay = "";
            this.modedisplay = "none";
            this.panel1display = "none";
            this.panel2display = "none";
            this.panel3display = "none";
            this.panel4display = "none";
            this.panel8display = "none";
            this.btnLbl = "DELETE";
            this.gridTableDisplay = "none";
            this.verifyTableDisplay = "";
            this.setMessage("Now enter the batch No. which you want to Delete.");
            this.setFocusId("txtbatchNo");
        }
    }

    public void batchAction() {
        this.verifyTable = null;
        try {
            if (this.batch == null || this.batch.equalsIgnoreCase("")) {
                this.setMessage("Please fill the batch no.");
                return;
            }

            if (function.equalsIgnoreCase("V") || (function.equalsIgnoreCase("D"))) {
                verifyTable = new ArrayList<>();
                verifyTable = txnRemote.getBatchList(this.function, Integer.parseInt(batch));
                if (verifyTable.isEmpty()) {
                    this.setMessage("There is no batch list");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return;
        }
    }

    public boolean accountvalidation() {
        try {
            if (acno.equalsIgnoreCase("") || acno.equalsIgnoreCase(null)) {
                this.setMessage("Account should not be blank.");
                return false;
            }
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper account no.");
                return false;
            }
            newAccountNo = ftsRemote.getNewAccountNumber(acno);
            if (!(newAccountNo.substring(0, 2).equals(getOrgnBrCode()))) {
                this.setMessage("Please fill your branch Gl account no.");
                return false;
            }
            String chkAccNo = txnRemote.checkForAccountNo(this.newAccountNo);
            if (!chkAccNo.equalsIgnoreCase("true")) {
                this.setMessage(chkAccNo);
                return false;
            }

            if (this.mode.equalsIgnoreCase("BT")) {
                String AcNature = ftsRemote.getAccountNature(newAccountNo);
                if (AcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    List selectGLTable = txnRemote.selectFromGLTable(newAccountNo);
                    if (selectGLTable.isEmpty()) {
                        this.setMessage("Account No does not exist.");
                        return false;
                    }
                    Vector vecGLTable = (Vector) selectGLTable.get(0);
                    int TmpPostFlag = Integer.parseInt(vecGLTable.get(2).toString());
                    int msgFlag = Integer.parseInt(vecGLTable.get(3).toString());
                    if (TmpPostFlag == 0 || TmpPostFlag == 99) {
                        this.setMessage("Entry for this Account No is not allowed.");
                        return false;
                    }
                    if (msgFlag == 0) {
                        this.setMessage("Entry for this Account No is not allowed.");
                        return false;
                    }
                    if (msgFlag == 50) {
                        this.setMessage("Entry in Share Capital Head is not allowed from this Screen");
                        return false;
                    }

                    if (msgFlag == 04) {
                        this.setMessage("This Gl head account can not allow for this entry.");
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void accountAction() {
        try {
            this.setMessage("");
            if (accountvalidation() == true) {
                List GlHeadName = commonReportRemote.getGlHeadName(newAccountNo);
                Vector vec1 = (Vector) GlHeadName.get(0);
                this.setGlHeadname(vec1.get(0).toString());

                List glHeadBalance = ftsRemote.getGlHeadbalance(newAccountNo);
                Vector vec2 = (Vector) glHeadBalance.get(0);
                this.setBalance(new BigDecimal(vec2.get(0).toString()));
                this.setFocusId("ddtxnType");
            }
            if (!(this.gridTable == null)) {
                String valuDt = this.gridTable.get(0).getDate().toString();
                this.valueDate = valuDt;
                this.disabledate = true;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean txntypeValidation() {
        try {
            this.setMessage("");
            if (accountvalidation() == true) {
                List crDRFlaglist = txnRemote.selectFromGLTablecrDRFlag(newAccountNo);
                Vector crdb = (Vector) crDRFlaglist.get(0);
                String crdrFlag = crdb.get(0).toString();
                if (this.txnType.equalsIgnoreCase("CR")) {
                    if (crdrFlag.equalsIgnoreCase("D")) {
                        this.setMessage("This account only allow for debit entry.");
                        return false;
                    }
                } else if (this.txnType.equalsIgnoreCase("DR")) {
                    if (crdrFlag.equalsIgnoreCase("C")) {
                        this.setMessage("This account only allow for credit entry.");
                        return false;
                    }
                }
                if (!(this.gridTable == null)) {
                    String valuDt = this.gridTable.get(0).getDate().toString();
                    this.valueDate = valuDt;
                    this.disabledate = true;
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void txnTypeAction() {
        try {
            txntypeValidation();
            this.setFocusId("txtAmount");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void gridAction() {
        try {
            this.setMessage("");
            if (this.remarks.equalsIgnoreCase("") || this.remarks.equalsIgnoreCase(null)) {
                this.setMessage("Please fill the remarks for entry.");
                return;
            }
            if (accountvalidation() == true && txntypeValidation() == true && validationDateField() == true) {
                if (function.equalsIgnoreCase("A")) {
                    Pattern p = Pattern.compile("[0-9]+([,.][0-9]{1,2})?");
                    Matcher amountCheck = p.matcher(String.valueOf(this.amount));
                    if (!amountCheck.matches()) {
                        this.setMessage("Please fill the valid amount. Amount should be with two decimal point.");
                        return;
                    }
                    BackDateEntryPojo newEntry = new BackDateEntryPojo();
                    newEntry.setAcno(this.newAccountNo);
                    newEntry.setAmount(this.amount);
                    newEntry.setDate(this.valueDate);
                    newEntry.setGlname(this.glHeadname);
                    newEntry.setTxnType(txnType);
                    newEntry.setMode(this.mode);
                    if (gridTable == null || gridTable.isEmpty()) {
                        gridTable = new ArrayList<>();
                    }
                    gridTable.add(newEntry);
                }
                if (txnType.equalsIgnoreCase("CR")) {
                    this.totalcredit = new BigDecimal(formatter.format(totalcredit.add(this.amount)));
                } else if (txnType.equalsIgnoreCase("DR")) {
                    this.totaldebit = new BigDecimal(formatter.format(totaldebit.add(this.amount)));
                }
                setAcno("");
                setNewAccountNo("");
                setGlHeadname("");
                setBalance(BigDecimal.ZERO);
                setAmount(BigDecimal.ZERO);
                setRemarks("");
                setFocusId("txtAcno");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        // int count = 0;
        try {
            if (function.equalsIgnoreCase("A")) {
                if (this.gridTable == null || this.gridTable.isEmpty()) {
                    this.setMessage("Batch Empty");
                    return;
                }
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                BigDecimal bal = totalcredit.subtract(totaldebit);
                if (bal.compareTo(BigDecimal.ZERO) == 0) {
                    String result = txnRemote.saveBackDateEntry(gridTable, getOrgnBrCode(), getUserName(), this.mode, this.remarks, this.valueDate);
                    if (!result.equalsIgnoreCase("true")) {
                        this.setMessage(result);
                        return;
                    }
                    // }
                    this.setNewAccountNo("");
                    this.acno = "";
                    this.setBatch("");
                    this.setAmount(BigDecimal.ZERO);
                    this.setTxnType("CR");
                    this.setFunction("0");
                    this.setGlHeadname("");
                    this.gridTable = null;
                    this.CurrentItem = null;
                    this.totalcredit = BigDecimal.ZERO;
                    this.totaldebit = BigDecimal.ZERO;
                    this.verifyTable = null;
                    this.balance = BigDecimal.ZERO;

                    this.setMessage("Tranfer Batch save successfully and Batch No is " + result.substring(4));
                } else {
                    this.setMessage("Batch Mismatch");
                    return;
                }
            } else if (function.equalsIgnoreCase("V")) {
                if (verifyTable.isEmpty() || verifyTable == null) {
                    this.setMessage("There is no detail to verify.");
                }
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                String authorization = txnRemote.authorizeBackDateEntry(verifyTable, getUserName(), getTodayDate(), getOrgnBrCode(), Float.parseFloat(batch));
                if (!authorization.equalsIgnoreCase("true")) {
                    this.setMessage(authorization);
                    return;
                }

                this.setMessage("Batch entry authorize successfully for batch " + this.batch);
                this.setVerifyTable(null);
                this.setBatch("");
            } else if (function.equalsIgnoreCase("D")) {

                String deleteOperation = txnRemote.deleteOperationBackDateEnry(verifyTable, getUserName(), getTodayDate(), getOrgnBrCode(), Float.parseFloat(batch));
                if (!deleteOperation.equalsIgnoreCase("true")) {
                    this.setMessage(deleteOperation);
                    return;
                }
                this.setMessage("Batch entry delete successfully. for batch " + this.batch);
                this.setVerifyTable(null);
                this.setBatch("");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private boolean validationDateField() {
        try {
            if (this.function.equalsIgnoreCase("") || this.function.equalsIgnoreCase(null) || this.function.equalsIgnoreCase("0")) {
                this.setMessage("Please select the function.");
                return false;
            }
            if (acno.equalsIgnoreCase("") || acno.equalsIgnoreCase(null)) {
                this.setMessage("Account should not be blank.");
                return false;
            }
            if (this.valueDate.equalsIgnoreCase("") || this.valueDate.equalsIgnoreCase(null) || this.valueDate.equalsIgnoreCase("__/__/____")) {
                this.setMessage("Please fill the transaction date .");
                return false;
            }
            if (dmy.parse(valueDate).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                this.setMessage("Transaction date can not be greater than current date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(valueDate)) {
                this.setMessage("Please fill proper transaction date.");
                return false;
            }

            if (mode.equalsIgnoreCase("N")) {
                if (!(this.valueDate.substring(3, 10).equalsIgnoreCase(getTodayDate().substring(3, 10)))) {
                    this.setMessage("Transaction date should be only of current month.");
                    return false;
                }
            }
            if (mode.equalsIgnoreCase("Y")) {
                List yearEndDate = ftsRemote.getMaxyearEndDate(valueDate.substring(6, 10));
                Vector v = (Vector) yearEndDate.get(0);
                String Date = dmy.format(ymd.parse(v.get(0).toString()));
                if (!this.valueDate.substring(0, 5).equalsIgnoreCase(Date.substring(0, 5))) {
                    this.setMessage("Date should be last date of financial year.");
                    return false;
                }
            }
        } catch (ApplicationException | ParseException ex) {
            this.setMessage(ex.getMessage());
        }
        return true;
    }

    public void deleteDetailsAction() {
        try {
            if (CurrentItem == null) {
                this.setMessage("Please select the row from the table.");
                return;
            }
            if (CurrentItem.getTxnType().equalsIgnoreCase("CR")) {
                this.totalcredit = new BigDecimal(formatter.format(totalcredit.subtract(CurrentItem.getAmount())));
            } else {
                this.totaldebit = new BigDecimal(formatter.format(totaldebit.subtract(CurrentItem.getAmount())));
            }
            gridTable.remove(CurrentItem);

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void modeAction() {
        if (disabledate) {
            this.setFocusId("txtAcno");
        } else {
            this.setFocusId("valueDt");
        }
    }

    public void refresh() {
        this.setMessage("");
        this.setNewAccountNo("");
        this.acno = "";
        this.setBatch("");
        this.setAmount(BigDecimal.ZERO);
        this.setTxnType("CR");
        this.setFunction("0");
        this.setGlHeadname("");
        this.gridTable = null;
        this.CurrentItem = null;
        this.totalcredit = BigDecimal.ZERO;
        this.totaldebit = BigDecimal.ZERO;
        this.verifyTable = null;
        this.balance = BigDecimal.ZERO;
        this.setMode("0");
        this.setRemarks("");
        this.setValueDate("");

    }

    public String exitForm() {
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBatchdisplay() {
        return batchdisplay;
    }

    public void setBatchdisplay(String batchdisplay) {
        this.batchdisplay = batchdisplay;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNewAccountNo() {
        return newAccountNo;
    }

    public void setNewAccountNo(String newAccountNo) {
        this.newAccountNo = newAccountNo;
    }

    public String getGlHeadname() {
        return glHeadname;
    }

    public void setGlHeadname(String glHeadname) {
        this.glHeadname = glHeadname;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getBtnLbl() {
        return btnLbl;
    }

    public void setBtnLbl(String btnLbl) {
        this.btnLbl = btnLbl;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public List<SelectItem> getFuncList() {
        return FuncList;
    }

    public void setFuncList(List<SelectItem> FuncList) {
        this.FuncList = FuncList;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public List<BackDateEntryPojo> getGridTable() {
        return gridTable;
    }

    public void setGridTable(List<BackDateEntryPojo> gridTable) {
        this.gridTable = gridTable;
    }

    public BackDateEntryPojo getCurrentItem() {
        return CurrentItem;
    }

    public void setCurrentItem(BackDateEntryPojo CurrentItem) {
        this.CurrentItem = CurrentItem;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isSelectRender() {
        return selectRender;
    }

    public void setSelectRender(boolean selectRender) {
        this.selectRender = selectRender;
    }

    public BigDecimal getTotalcredit() {
        return totalcredit;
    }

    public void setTotalcredit(BigDecimal totalcredit) {
        this.totalcredit = totalcredit;
    }

    public BigDecimal getTotaldebit() {
        return totaldebit;
    }

    public void setTotaldebit(BigDecimal totaldebit) {
        this.totaldebit = totaldebit;
    }

    public List<BackDateEntryPojo> getVerifyTable() {
        return verifyTable;
    }

    public void setVerifyTable(List<BackDateEntryPojo> verifyTable) {
        this.verifyTable = verifyTable;
    }

    public String getVerifyTableDisplay() {
        return verifyTableDisplay;
    }

    public void setVerifyTableDisplay(String verifyTableDisplay) {
        this.verifyTableDisplay = verifyTableDisplay;
    }

    public String getGridTableDisplay() {
        return gridTableDisplay;
    }

    public void setGridTableDisplay(String gridTableDisplay) {
        this.gridTableDisplay = gridTableDisplay;
    }

    public String getPanel4display() {
        return panel4display;
    }

    public void setPanel4display(String panel4display) {
        this.panel4display = panel4display;
    }

    public String getPanel1display() {
        return panel1display;
    }

    public void setPanel1display(String panel1display) {
        this.panel1display = panel1display;
    }

    public String getPanel2display() {
        return panel2display;
    }

    public void setPanel2display(String panel2display) {
        this.panel2display = panel2display;
    }

    public String getPanel3display() {
        return panel3display;
    }

    public void setPanel3display(String panel3display) {
        this.panel3display = panel3display;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getModedisplay() {
        return modedisplay;
    }

    public void setModedisplay(String modedisplay) {
        this.modedisplay = modedisplay;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPanel8display() {
        return panel8display;
    }

    public void setPanel8display(String panel8display) {
        this.panel8display = panel8display;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public boolean isDisabledate() {
        return disabledate;
    }

    public void setDisabledate(boolean disabledate) {
        this.disabledate = disabledate;
    }

}
