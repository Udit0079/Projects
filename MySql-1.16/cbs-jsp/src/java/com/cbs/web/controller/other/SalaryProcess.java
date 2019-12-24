/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.dto.SalaryProcessPojo;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author root
 */
public class SalaryProcess extends BaseBean {

    private String message;
    private String acno, acNoLen, accountNo;
    private String accountName;
    private String tranType;
    private String amount;
    private SalaryProcessPojo currentItem;
    private List<SelectItem> tranTypeList;
    private List<SalaryProcessPojo> tableDataList;
    private final String jndiHomeName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiName = "PrintManagementFacade";
    private CtsManagementFacadeRemote ctsRemote = null;
    private final String ctsJndiName = "CtsManagementFacade";
    private PrintFacadeRemote printRemote = null;
    private final String txnJndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private String salaryFileName = "salaryFile.xml";
    private String absoluteFilePath;
    private String tempAbsoluteFilePath;
    private String instNo;
    private String instDt;
    private String details;
    private String instNoFlag;
    private String instDtFlag;
    private String detailsFlag;
    private String singleDrFileName;
    private String mode;
    private BigDecimal totalCr;
    private BigDecimal totalDr;
    private List<SelectItem> modeList;
    private BigDecimal balance;
    private String TmpAcctNature;
    private String labelCrDr;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    Date curDt = new Date();
    private static final String batchFileName = "Batch-Transfer.xml";
    private boolean odBalFlag;
    private String confirmationMsg;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public SalaryProcessPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(SalaryProcessPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SalaryProcessPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<SalaryProcessPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

    public String getSalaryFileName() {
        return salaryFileName;
    }

    public void setSalaryFileName(String salaryFileName) {
        this.salaryFileName = salaryFileName;
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetailsFlag() {
        return detailsFlag;
    }

    public void setDetailsFlag(String detailsFlag) {
        this.detailsFlag = detailsFlag;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getInstDtFlag() {
        return instDtFlag;
    }

    public void setInstDtFlag(String instDtFlag) {
        this.instDtFlag = instDtFlag;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInstNoFlag() {
        return instNoFlag;
    }

    public void setInstNoFlag(String instNoFlag) {
        this.instNoFlag = instNoFlag;
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

    public String getTempAbsoluteFilePath() {
        return tempAbsoluteFilePath;
    }

    public void setTempAbsoluteFilePath(String tempAbsoluteFilePath) {
        this.tempAbsoluteFilePath = tempAbsoluteFilePath;
    }

    public String getSingleDrFileName() {
        return singleDrFileName;
    }

    public void setSingleDrFileName(String singleDrFileName) {
        this.singleDrFileName = singleDrFileName;
    }

    public BigDecimal getTotalCr() {
        return totalCr;
    }

    public void setTotalCr(BigDecimal totalCr) {
        this.totalCr = totalCr;
    }

    public BigDecimal getTotalDr() {
        return totalDr;
    }

    public void setTotalDr(BigDecimal totalDr) {
        this.totalDr = totalDr;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public boolean isOdBalFlag() {
        return odBalFlag;
    }

    public void setOdBalFlag(boolean odBalFlag) {
        this.odBalFlag = odBalFlag;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTmpAcctNature() {
        return TmpAcctNature;
    }

    public void setTmpAcctNature(String TmpAcctNature) {
        this.TmpAcctNature = TmpAcctNature;
    }

    public String getLabelCrDr() {
        return labelCrDr;
    }

    public void setLabelCrDr(String labelCrDr) {
        this.labelCrDr = labelCrDr;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * Creates a new instance of SalaryProcess
     */
    public SalaryProcess() {
        totalCr = new BigDecimal("0");
        totalDr = new BigDecimal("0");
        btnRefreshAction();
        modeList = new ArrayList<SelectItem>();
        tranTypeList = new ArrayList<SelectItem>();
        tableDataList = new ArrayList<SalaryProcessPojo>();
        try {
            modeList.add(new SelectItem("S", "Single Debit"));
            modeList.add(new SelectItem("M", "Multiple Debit"));

            tranTypeList.add(new SelectItem("0", "Cr"));
            tranTypeList.add(new SelectItem("1", "Dr"));

            this.setInstDt(getTodayDate());

            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            printRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            ctsRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(ctsJndiName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(txnJndiHomeName);
            this.setAcNoLen(getAcNoLength());
            ServletContext context = (ServletContext) getFacesContext().getExternalContext().getContext();
//            String absoluteDirectory = context.getInitParameter("cts");
            String absoluteDirectory = context.getInitParameter("cbsFiles");
            File dir = new File(absoluteDirectory + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String tempAbsoluteDirectory = context.getInitParameter("singleDr");
            File tempDir = new File(tempAbsoluteDirectory + "/");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            absoluteFilePath = absoluteDirectory + "/";
            tempAbsoluteFilePath = tempAbsoluteDirectory + "/";

            this.odBalFlag = false;
            this.confirmationMsg = "";
            this.setBalance(new BigDecimal("0.00"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void modeProcess() {
        this.setMessage("");
        if (this.mode.equals("S")) {
            this.detailsFlag = "false";
            this.details = "";
        } else {
            this.detailsFlag = "false";
        }
    }

    public void accountProcess() {
        this.setMessage("");
        try {
            setMessage("");
            balance = new BigDecimal("0");
            String result = validateAccount();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            String custname = ftsRemote.ftsGetCustName(this.acno);
            if (custname == null || custname.equals("")) {
                this.setMessage("Custname is blank.");
                return;
            } else {
                this.setAccountName(custname);
            }
            TmpAcctNature = ftsRemote.getAccountNature(this.acno);
            List selectReconTdReconCaRecon = txnRemote.selectFromReconTdReconCaRecon(this.acno, TmpAcctNature);
            if (!selectReconTdReconCaRecon.isEmpty()) {
                Vector vecReconTdReconCaRecon = (Vector) selectReconTdReconCaRecon.get(0);
                balance = new BigDecimal(vecReconTdReconCaRecon.get(0).toString());
            }
            if (Double.parseDouble(balance.toString()) < 0) {
                labelCrDr = " Dr";
                balance = balance.abs();
            } else {
                labelCrDr = " Cr";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void amountProcess() {
        this.setMessage("");
        try {
            String result = validateAmount();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void tranTypeProcess() {
        this.setMessage("");
        try {
            String result = validateAccount();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            result = validateAmount();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            String acnoMsg = ftsRemote.ftsAcnoValidate(this.acno, Integer.parseInt(this.tranType), "");
            if (!acnoMsg.equalsIgnoreCase("true")) {
                this.setMessage(acnoMsg);
                return;
            }

            if (this.tranType.equals("0")) {
                this.instNo = "";
                this.setInstNoFlag("true");
                this.setInstDtFlag("true");
            } else {
                this.instNo = "";
                this.setInstNoFlag("false");
                this.setInstDtFlag("false");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void instNoProcess() {
        this.setMessage("");
        try {
            if (this.tranType.equals("1")) {
//                if (this.instNo == null || this.instNo.equalsIgnoreCase("")) {
//                    this.setMessage("Please fill Inst. No.");
//                    return;
//                }
                if (this.instNo != null && !this.instNo.equalsIgnoreCase("")) {
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m = p.matcher(instNo);
                    if (m.matches() != true) {
                        this.setMessage("Please fill proper Inst.No.");
                        return;
                    }
                    String checkMessage = ctsRemote.chequeValidate(this.acno, this.instNo);
                    if (!checkMessage.equalsIgnoreCase("true")) {
                        this.setMessage(checkMessage);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void instDtProcess() {
        this.setMessage("");
        try {
            if (this.tranType.equals("1")) {
                if (this.instNo != null && !this.instNo.equalsIgnoreCase("")) {
                    if (this.instDt == null || this.instDt.equals("")) {
                        this.setMessage("Please fill Inst. Date.");
                        return;
                    }
                    boolean result = new Validator().validateDate_dd_mm_yyyy(this.instDt);
                    if (result == false) {
                        this.setMessage("Please fill proper Inst. Date.");
                        return;
                    }
                    String chqDtMsg = ftsRemote.ftsInstDateValidate(ymd.format(dmy.parse(this.instDt)));
                    if (!chqDtMsg.equalsIgnoreCase("true")) {
                        this.setMessage(chqDtMsg);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void detailProcess(String odBalCheck) {
        this.setMessage("");
        try {
            String result = validateAccount();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            result = validateAmount();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            String acnoMsg = ftsRemote.ftsAcnoValidate(this.acno, Integer.parseInt(this.tranType), "");
            if (!acnoMsg.equalsIgnoreCase("true")) {
                this.setMessage(acnoMsg);
                return;
            }
            /**
             * Small Account Checking.
             */
            if (this.tranType.equals("0")) {
                List paramList = ftsRemote.getBaseParameter(ftsRemote.getAccountCode(acno));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    BigDecimal maxDepInFinYear = new BigDecimal(paramVector.get(1).toString());
                    BigDecimal balAtAnyTime = new BigDecimal(paramVector.get(3).toString());

                    paramList = ftsRemote.getCurrentFinYear(getOrgnBrCode());
                    paramVector = (Vector) paramList.get(0);
                    String minFinDt = paramVector.get(0).toString();
                    BigDecimal totalFinYearDeposit = txnRemote.getFinYearDepositAsWellAsMonthWithdrawalTillDate(acno, minFinDt);
                    BigDecimal totalFinYearPlusCrAmt = totalFinYearDeposit.add(new BigDecimal(this.amount));
                    if (totalFinYearPlusCrAmt.compareTo(maxDepInFinYear) == 1) {
                        this.setMessage("You can not deposit more than financial year limit. Remaining deposit limit is " + maxDepInFinYear.subtract(totalFinYearDeposit) + " only");
                        return;
                    }
                    BigDecimal actualBalance = new BigDecimal(ftsRemote.ftsGetBal(acno));
                    BigDecimal actualBalPlusCrAmt = actualBalance.add(new BigDecimal(this.amount));
                    if (actualBalPlusCrAmt.compareTo(balAtAnyTime) == 1) {
                        this.setMessage("You can not deposit more than balance limit. Remaining deposit balance limit is " + balAtAnyTime.subtract(actualBalance) + " only");
                        return;
                    }
                }
            }
            if (this.tranType.equals("1")) {
                List paramList = ftsRemote.getBaseParameter(ftsRemote.getAccountCode(acno));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    BigDecimal cashTrfDrInMonth = new BigDecimal(paramVector.get(2).toString());

                    BigDecimal totalMonthWithdrawal = txnRemote.getFinYearDepositAsWellAsMonthWithdrawalTillDate(acno, "");
                    BigDecimal totalMonthWithPlusDrAmt = totalMonthWithdrawal.add(new BigDecimal(this.amount));
                    if (totalMonthWithPlusDrAmt.compareTo(cashTrfDrInMonth) == 1) {
                        this.setMessage("You can not withdrawal more than month limit. Remaining withdrawal limit is " + cashTrfDrInMonth.subtract(totalMonthWithdrawal) + " only");
                        return;
                    }
                }
            }
            /**
             * End Here
             */
            if (this.tranType.equals("1")) {
                String drAcNature = ftsRemote.getAccountNature(this.acno);
                if (!drAcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                    result = ftsRemote.checkBalance(this.acno, Double.parseDouble(this.amount), getUserName());
//                    if (!result.equalsIgnoreCase("true")) {
//                        this.setMessage(result);
//                        return;
//                    }

                    /*New Modification For OD Balance*/
                    this.odBalFlag = false;
                    odBalCheck = odBalCheck == null ? "" : odBalCheck;
                    if (!odBalCheck.equalsIgnoreCase("odcheck")) {
                        String actNature = ftsRemote.getAccountNature(acno);
                        String chkBalResult = ftsRemote.chkBal(this.acno, Integer.parseInt(this.tranType), 1, actNature);
                        if (!chkBalResult.equalsIgnoreCase("true")) {
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                String chkBalance = ftsRemote.checkBalForOdLimit(this.acno, Double.parseDouble(this.amount), getUserName());
                                if (chkBalance.equalsIgnoreCase("99")) {
                                    confirmationMsg = "Limit Exceed for : " + this.acno + ". \n To Proceed press Yes, \n To Cancel press No";
                                    this.odBalFlag = true;
                                    return;
                                } else if (!chkBalance.equalsIgnoreCase("1")) {
                                    this.odBalFlag = false;
                                    this.confirmationMsg = "";
                                    this.setMessage("Balance Exceeds.");
                                    return;
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                String balResult = ftsRemote.checkBalance(this.acno, Double.parseDouble(this.amount), getUserName());
                                if (!balResult.equalsIgnoreCase("true")) {
                                    this.odBalFlag = false;
                                    this.confirmationMsg = "";
                                    this.setMessage("Balance Exceeds.");
                                    return;
                                }
                            }
                        }
                    }
                    /*New Modification For OD Balance End Here*/

                    if (this.instNo != null && !this.instNo.equalsIgnoreCase("")) {
                        /**
                         * New Addition*
                         */
                        List paramList = ftsRemote.getBaseParameter(ftsRemote.getAccountCode(this.acno));
                        if (!paramList.isEmpty()) {
                            Vector paramVector = (Vector) paramList.get(0);
                            String chqFlag = paramVector.get(4).toString();
                            if (chqFlag.equalsIgnoreCase("N")) {
                                this.setMessage("Transaction with cheque is not allowed for this type of account.");
                                return;
                            }
                        }
                        /**
                         * New Addition End*
                         */
                        Pattern p = Pattern.compile("[0-9]*");
                        Matcher m = p.matcher(instNo);
                        if (m.matches() != true) {
                            this.setMessage("Please fill proper Inst.No.");
                            return;
                        }
                        String checkMessage = ctsRemote.chequeValidate(this.acno, this.instNo);
                        if (!checkMessage.equalsIgnoreCase("true")) {
                            this.setMessage(checkMessage);
                            return;
                        }
                    }
                }
            }

            /**
             * Code Added For ThreshHold Limit Checking *
             */
            String AcNature = ftsRemote.getAccountNature(this.acno);
            if ((AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || AcNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && this.tranType.equals("0")) {
                SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
                int thLmtParam = 0;
                List chParamThresh = ftsRemote.getThreshLmtParam();
                if (!(chParamThresh == null || chParamThresh.isEmpty())) {
                    Vector verLst = (Vector) chParamThresh.get(0);
                    thLmtParam = Integer.parseInt(verLst.get(0).toString());
                }
                if (thLmtParam == 1) {
                    String chkThresh = ftsRemote.isThreshLmtExceed(this.acno, Double.parseDouble(this.amount), ymmd.format(dmy.parse(this.instDt)));
                    if (!chkThresh.equalsIgnoreCase("true")) {
                        this.setMessage(chkThresh);
                        return;
                    }
                }
            }

            if (this.mode.equals("S")) {
//                String singleDetails = "Salary for the month of " + getTodayDate();
                String singleDetails = "By trf: " + this.details;
                this.detailsFlag = "false";

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                if (tableDataList.isEmpty()) {
                    if (this.tranType.equals("0")) {
                        this.setMessage("Please generate debit entry first.");
                        return;
                    } else {
                        this.instNoFlag = "false";
                        this.instDtFlag = "false";
                        String recno = "";

                        this.singleDrFileName = this.acno;

                        File tempXMLFile = new File(tempAbsoluteFilePath + this.singleDrFileName + ".xml");     //FileNotFoundException
                        File xmlFile = new File(absoluteFilePath + this.singleDrFileName + ".xml");             //FileNotFoundException

                        Document newDoc = dBuilder.newDocument();
                        Element rootElement = newDoc.createElement("SalaryDetail");
                        newDoc.appendChild(rootElement);

                        //If temp file(Batch-Trf) exists.
                        if (tempXMLFile.exists()) {
                            if (xmlFile.exists()) {
                                readXMLFileForSalary(xmlFile);
                            } else {
                                readXMLFileForSalary(tempXMLFile);
                            }
//                            xmlFile.delete();
//                            readXMLFileForSalary(tempXMLFile);
                            if (!tableDataList.isEmpty()) {
                                for (int i = 0; i < tableDataList.size(); i++) {
                                    recno = String.valueOf(CbsUtil.fourDigitRandomNumber());
                                    SalaryProcessPojo pojo = tableDataList.get(i);

                                    generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, pojo.getSalaryAccount(), pojo.getSalaryAccountName(), pojo.getSalaryAmount().toString(), pojo.getSalaryTranType(), pojo.getSalaryInstNo(), pojo.getSalaryInstDt(), singleDetails);
                                }
                                readXMLFileForSalary(xmlFile);
                            }
                        } else {
                            if (xmlFile.exists()) {
                                readXMLFileForSalary(xmlFile);

                                if (!tableDataList.isEmpty()) {
                                    for (int i = 0; i < tableDataList.size(); i++) {
                                        recno = String.valueOf(CbsUtil.fourDigitRandomNumber());
                                        SalaryProcessPojo pojo = tableDataList.get(i);

                                        generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, pojo.getSalaryAccount(), pojo.getSalaryAccountName(), pojo.getSalaryAmount().toString(), pojo.getSalaryTranType(), pojo.getSalaryInstNo(), pojo.getSalaryInstDt(), singleDetails);
                                    }
                                    readXMLFileForSalary(xmlFile);
                                } else {
                                    this.setMessage("Now you can generate your transfer batch.");

                                    recno = String.valueOf(CbsUtil.fourDigitRandomNumber());
                                    generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, this.acno, this.accountName, this.amount, this.tranType, this.instNo, this.instDt, singleDetails);
                                    readXMLFileForSalary(xmlFile);
                                    refreshAfterAddInTable();
                                }
                            } else {
                                /**
                                 * Process to check balance existence in Dr
                                 * Account.
                                 */
//                                if (this.tranType.equals("1")) {
//                                    String chkBalResult = ftsRemote.chkBal(this.acno, Integer.parseInt(this.tranType), 1, ftsRemote.getAccountNature(acno));
//                                    if (!chkBalResult.equalsIgnoreCase("TRUE")) {
//                                        String checkBalanceMessage = ftsRemote.checkBalance(this.acno, Double.parseDouble(this.amount), getUserName());
//                                        if (!checkBalanceMessage.equalsIgnoreCase("true")) {
//                                            this.setMessage(checkBalanceMessage);
//                                            return;
//                                        }
//                                    }
//                                }
                                //If temp file does not exist.
                                this.setMessage("Now you can generate your transfer batch.");
//                                xmlFile.delete();

                                recno = String.valueOf(CbsUtil.fourDigitRandomNumber());
                                generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, this.acno, this.accountName, this.amount, this.tranType, this.instNo, this.instDt, singleDetails);
                                readXMLFileForSalary(xmlFile);
                                refreshAfterAddInTable();
                            }
                        }
                    }
                } else {
                    //If TableDataList is not empty.
                    if (this.tranType.equals("1")) {
                        boolean drEntry = false;
                        for (int i = 0; i < tableDataList.size(); i++) {
                            SalaryProcessPojo pojo = tableDataList.get(i);
                            if (pojo.getSalaryTranType().equals("Debit")) {
                                drEntry = true; // If Dr entry found then it go from outside this current for loop.
                                break;
                            }
                        }
                        if (drEntry == true) {
                            this.setMessage("There is already a debit entry. Please verify.");
                            return;
                        } else {
                            if (!singleDrFileName.equalsIgnoreCase(this.acno)) {
                                this.setMessage("Please verify your dr account number.");
                                return;
                            }
//                            if (this.tranType.equals("1")) {
//                                String chkBalResult = ftsRemote.chkBal(this.acno, Integer.parseInt(this.tranType), 1, ftsRemote.getAccountNature(acno));
//                                if (!chkBalResult.equalsIgnoreCase("TRUE")) {
//                                    String checkBalanceMessage = ftsRemote.checkBalance(this.acno, Double.parseDouble(this.amount), getUserName());
//                                    if (!checkBalanceMessage.equalsIgnoreCase("true")) {
//                                        this.setMessage(checkBalanceMessage);
//                                        return;
//                                    }
//                                }
//                            }
                        }
                    }

                    File xmlFile = new File(absoluteFilePath + this.singleDrFileName + ".xml");

                    Document newDoc = dBuilder.parse(xmlFile);
                    newDoc.getDocumentElement().normalize();
                    NodeList nodeList = newDoc.getElementsByTagName("SalaryDetail");

                    String recno = String.valueOf(CbsUtil.fourDigitRandomNumber());
                    for (int temp = 0; temp < nodeList.getLength(); temp++) {
                        Node nNode = nodeList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element rootElement = (Element) nNode;
                            generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, this.acno, this.accountName, this.amount, this.tranType, this.instNo, this.instDt, singleDetails);
                        }
                    }
                    readXMLFileForSalary(xmlFile);
                    refreshAfterAddInTable();
                }
            } else {
                /**
                 * Multiple Debit Case
                 */
                File xmlFile = new File(absoluteFilePath + getUserName() + "-" + batchFileName);  //FileNotFoundException

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                String recno = String.valueOf(CbsUtil.fourDigitRandomNumber());

                if (!xmlFile.exists()) {
                    Document newDoc = dBuilder.newDocument();
                    Element rootElement = newDoc.createElement("SalaryDetail");
                    newDoc.appendChild(rootElement);

                    generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, this.acno, this.accountName, this.amount, this.tranType, this.instNo, this.instDt, this.details);
                    readXMLFileForSalary(xmlFile);
                    refreshAfterAddInTable();
                } else {
                    Document newDoc = dBuilder.parse(xmlFile);
                    newDoc.getDocumentElement().normalize();
                    NodeList nodeList = newDoc.getElementsByTagName("SalaryDetail");
                    for (int temp = 0; temp < nodeList.getLength(); temp++) {
                        Node nNode = nodeList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element rootElement = (Element) nNode;
                            generateXMLFileForSalary(newDoc, rootElement, xmlFile, recno, this.acno, this.accountName, this.amount, this.tranType, this.instNo, this.instDt, this.details);
                        }
                    }
                    readXMLFileForSalary(xmlFile);
                    refreshAfterAddInTable();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public void readXMLFileForSalary(File xmlFile) {
        totalCr = new BigDecimal("0");
        totalDr = new BigDecimal("0");
        tableDataList = new ArrayList<SalaryProcessPojo>();
        String type = "";
        try {
            if (xmlFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile); //if file exists but there is no data, it is blank file

                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("IndividualAccount");
                int number = 0;
                for (int temp = 0; temp < nodeList.getLength(); temp++) {
                    number = temp + 1;
                    SalaryProcessPojo pojo = new SalaryProcessPojo();
                    Node nNode = nodeList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        NodeList recnoNodeList = eElement.getElementsByTagName("Recno");
                        Node recnoNode = recnoNodeList.item(0);
                        Element recnoElement = (Element) recnoNode;
                        BigInteger recno = new BigInteger(recnoElement.getTextContent());

                        NodeList acnoNodeList = eElement.getElementsByTagName("Acno");
                        Node acnoNode = acnoNodeList.item(0);
                        Element acnoElement = (Element) acnoNode;
                        String readAcno = acnoElement.getTextContent();

                        NodeList nameNodeList = eElement.getElementsByTagName("Name");
                        Node nameNode = nameNodeList.item(0);
                        Element nameElement = (Element) nameNode;
                        String readAcnoName = nameElement.getTextContent();

                        NodeList amountNodeList = eElement.getElementsByTagName("Amount");
                        Node amountNode = amountNodeList.item(0);
                        Element amountElement = (Element) amountNode;
                        BigDecimal readAmount = new BigDecimal(amountElement.getTextContent().trim());

                        NodeList typeNodeList = eElement.getElementsByTagName("Type");
                        Node typeNode = typeNodeList.item(0);
                        Element typeElement = (Element) typeNode;
                        if (typeElement.getTextContent().equals("0") || typeElement.getTextContent().equals("Credit")) {
                            type = "Credit";
                            totalCr = totalCr.add(readAmount);
                        } else {
                            type = "Debit";
                            totalDr = totalDr.add(readAmount);
                        }

                        NodeList instNoNodeList = eElement.getElementsByTagName("InstNo");
                        Node instNoNode = instNoNodeList.item(0);
                        Element instNoElement = (Element) instNoNode;
                        String readInstNo = instNoElement.getTextContent();

                        NodeList instDtNodeList = eElement.getElementsByTagName("InstDt");
                        Node instDtNode = instDtNodeList.item(0);
                        Element instDtElement = (Element) instDtNode;
                        String readInstDt = instDtElement.getTextContent();

                        NodeList detailsNodeList = eElement.getElementsByTagName("Details");
                        Node detailsNode = detailsNodeList.item(0);
                        Element detailsElement = (Element) detailsNode;
                        String readDetails = detailsElement.getTextContent();

                        pojo.setSno(number);
                        pojo.setRecno(recno);
                        pojo.setSalaryAccount(readAcno);
                        pojo.setSalaryAccountName(readAcnoName);
                        pojo.setSalaryAmount(readAmount);
                        pojo.setSalaryTranType(type);
                        pojo.setSalaryInstNo(readInstNo);
                        pojo.setSalaryInstDt(readInstDt);
                        pojo.setSalaryDetails(readDetails);

                        tableDataList.add(pojo);
                        System.out.println("the i = " + temp);
                    }
                }
            } else {
                this.setMessage("Data does not exist for salary processing.");
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public boolean generateXMLFileForSalary(Document doc, Element rootElement, File xmlFile, String recno, String acno, String name, String amount, String type, String instNo, String instDt, String details) {
        try {
            if ((acno != null && !acno.equalsIgnoreCase("")) && (amount != null && !amount.equalsIgnoreCase("")) && (name != null && !name.equalsIgnoreCase(""))) {
                Element indEle = doc.createElement("IndividualAccount");
                rootElement.appendChild(indEle);

                Element recnoEle = doc.createElement("Recno");
                recnoEle.appendChild(doc.createTextNode(recno));
                indEle.appendChild(recnoEle);

                Element acnoEle = doc.createElement("Acno");
                acnoEle.appendChild(doc.createTextNode(acno));
                indEle.appendChild(acnoEle);

                Element nameEle = doc.createElement("Name");
                nameEle.appendChild(doc.createTextNode(name));
                indEle.appendChild(nameEle);

                Element amountEle = doc.createElement("Amount");
                amountEle.appendChild(doc.createTextNode(amount));
                indEle.appendChild(amountEle);

                Element typeEle = doc.createElement("Type");
                typeEle.appendChild(doc.createTextNode(type));
                indEle.appendChild(typeEle);

                Element instNoEle = doc.createElement("InstNo");
                instNoEle.appendChild(doc.createTextNode(instNo));
                indEle.appendChild(instNoEle);

                Element instDtEle = doc.createElement("InstDt");
                instDtEle.appendChild(doc.createTextNode(instDt));
                indEle.appendChild(instDtEle);

                Element detailsEle = doc.createElement("Details");
                detailsEle.appendChild(doc.createTextNode(details));
                indEle.appendChild(detailsEle);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);

                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
            }
        } catch (TransformerException tfe) {
            this.setMessage(tfe.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return true;
    }

    public boolean removeIndividualFromXMLFile(File xmlFile, String recNumber) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("IndividualAccount");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    NodeList recnoNodeList = eElement.getElementsByTagName("Recno");
                    Node recnoNode = recnoNodeList.item(0);
                    Element recnoElement = (Element) recnoNode;
                    String recno = recnoElement.getTextContent();
                    if (recno.equalsIgnoreCase(recNumber)) {
                        Node pNode = eElement.getParentNode();
                        pNode.removeChild(nodeList.item(temp));

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        DOMSource source = new DOMSource(doc);

                        StreamResult result = new StreamResult(xmlFile);
                        transformer.transform(source, result);
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return true;
    }

    public void btnSaveAction() {
        this.setMessage("");
        BigDecimal totalCrAmount = new BigDecimal("0");
        BigDecimal totalDrAmount = new BigDecimal("0");
        try {
            if (!tableDataList.isEmpty()) {
                for (int i = 0; i < tableDataList.size(); i++) {
                    SalaryProcessPojo pojo = tableDataList.get(i);
                    String drCr = pojo.getSalaryTranType();
                    if (drCr.equalsIgnoreCase("Credit")) {
                        totalCrAmount = totalCrAmount.add(pojo.getSalaryAmount());
                    } else {
                        totalDrAmount = totalDrAmount.add(pojo.getSalaryAmount());
                    }
                }
                if (totalCrAmount.compareTo(totalDrAmount) != 0) {
                    this.setMessage("In this batch total Cr amount is not equal to total Dr amount.");
                    return;
                }

                String trsno = printRemote.insertSalaryData(tableDataList, ymd.format(curDt), ymd.format(curDt), getUserName(), getOrgnBrCode());

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document newDoc = dBuilder.newDocument();
                Element rootElement = newDoc.createElement("SalaryDetail");
                newDoc.appendChild(rootElement);

                if (this.mode.equals("S")) {
                    File tempXMLFile = new File(tempAbsoluteFilePath + this.singleDrFileName + ".xml");     //FileNotFoundException
                    File xmlFile = new File(absoluteFilePath + this.singleDrFileName + ".xml");             //FileNotFoundException
                    tempXMLFile.delete();
                    readXMLFileForSalary(xmlFile);
                    if (!tableDataList.isEmpty()) {
                        for (int i = 0; i < tableDataList.size(); i++) {
                            SalaryProcessPojo pojo = tableDataList.get(i);

                            generateXMLFileForSalary(newDoc, rootElement, tempXMLFile, pojo.getRecno().toString(), pojo.getSalaryAccount(), pojo.getSalaryAccountName(), pojo.getSalaryAmount().toString(), pojo.getSalaryTranType(), pojo.getSalaryInstNo(), pojo.getSalaryInstDt(), pojo.getSalaryDetails());
                        }
                    }
                    xmlFile.delete();
                    tableDataList = new ArrayList<SalaryProcessPojo>();
                    this.setMessage("Generated batch no is " + trsno.substring(0, trsno.indexOf(".")));
                } else {
                    File tempXMLFile = new File(tempAbsoluteFilePath + getUserName() + "-" + batchFileName);     //FileNotFoundException
                    File xmlFile = new File(absoluteFilePath + getUserName() + "-" + batchFileName);            //FileNotFoundException
                    tempXMLFile.delete();
                    readXMLFileForSalary(xmlFile);
                    if (!tableDataList.isEmpty()) {
                        for (int i = 0; i < tableDataList.size(); i++) {
                            SalaryProcessPojo pojo = tableDataList.get(i);

                            generateXMLFileForSalary(newDoc, rootElement, tempXMLFile, pojo.getRecno().toString(), pojo.getSalaryAccount(), pojo.getSalaryAccountName(), pojo.getSalaryAmount().toString(), pojo.getSalaryTranType(), pojo.getSalaryInstNo(), pojo.getSalaryInstDt(), pojo.getSalaryDetails());
                        }
                    }
                    xmlFile.delete();
                    tableDataList = new ArrayList<SalaryProcessPojo>();
                    totalCr = new BigDecimal("0");
                    totalDr = new BigDecimal("0");
                    this.setMessage("Generated batch no is " + trsno.substring(0, trsno.indexOf(".")));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void deleteProcess() {
        this.setMessage("");
        try {
            File xmlFile = null;
            if (this.mode.equals("S")) {
                xmlFile = new File(absoluteFilePath + this.singleDrFileName + ".xml");
            } else {
                xmlFile = new File(absoluteFilePath + getUserName() + "-" + batchFileName);
            }

            boolean removeMessage = removeIndividualFromXMLFile(xmlFile, currentItem.getRecno().toString());
            if (removeMessage) {
                readXMLFileForSalary(xmlFile);
            } else {
                this.setMessage("Problem in removing the entry from batch.");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateAccount() {
        String result = "true";
        try {

            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")) {
                return "Please fill account no.";
            }

            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                return "Please fill proper account no.";
            }

            //String checkAcno = ftsRemote.getNewAccountNumber(this.accountNo);
//            if (checkAcno == null || checkAcno.equals("")) {
//                return result = "Account number does not exist.";
//            }
            acno = ftsRemote.getNewAccountNumber(this.accountNo);

            if (this.acno == null || this.acno.equals("")) {
                return "Please fill account no.";
            } else if (this.acno.length() < 12) {
                return "Please fill 12 digit account no.";
            }

            if (ftsRemote.getAccountNature(this.acno).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                String checkGlAcno = this.acno.substring(2, 10);
                if (!ftsRemote.getglheadDD(checkGlAcno).equalsIgnoreCase("true")) {
                    if (printRemote.getMsgFlagByAcno(this.acno).equals("true")) {
                        return "Please fill correct account no.";
                    }
                }
                /*Modified By Athar*/
                String brCode = ftsRemote.getCurrentBrnCode(this.acno);
                if (!getOrgnBrCode().equalsIgnoreCase(brCode)) {
                    return "Please fill own branch account No.";
                }
                // String checkGlAcno = this.acno.substring(2, 10);
                String msg = ftsRemote.getglheadDD(checkGlAcno);
                if (!msg.equalsIgnoreCase("true")) {
                    return "Please fill account No. only dd nature !";
                }
            }
            /**
             * * Check for GL Type Restriction **
             */
            String glType = ftsRemote.getGlTranInfo(acno, Integer.parseInt(this.getTranType()), Double.parseDouble(this.amount));
            if (!glType.equalsIgnoreCase("True")) {
                return glType;
            }
            /**
             * end here*
             */
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public String validateAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            Matcher m = p.matcher(amount);
            if (this.amount == null || this.amount.equals("")) {
                return "Amount can not be blank.";
            } else if (m.matches() != true) {
                return "Please fill proper amount.";
            } else if (this.amount.equalsIgnoreCase("0") || this.amount.equalsIgnoreCase("0.0")) {
                return "Amount can not be zero.";
            } else if (new BigDecimal(this.amount).compareTo(new BigDecimal("0")) == -1) {
                return "Amount can not be negative.";
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    return "Invalid amount. Please fill the amount correctly.";
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 3) {
                    return "Please fill the amount upto three decimal places.";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public void odBalExceedProcess() {
        try {
            detailProcess("odcheck");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshAfterAddInTable() {
        this.setAcno("");
        this.setAccountName("");
        this.setAmount("");
        this.setInstNo("");
        this.setAccountNo("");
        this.setInstDt(getTodayDate());
        this.odBalFlag = false;
        this.confirmationMsg = "";
        balance = new BigDecimal("0");
        this.setLabelCrDr("");
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAcno("");
        this.setAccountName("");
        this.setAmount("");
        this.setMode("S");
        this.setInstNo("");
        this.setAccountNo("");
        this.setInstDt(getTodayDate());
        this.setDetails("");
        this.instNoFlag = "true";
        this.instDtFlag = "true";
        this.detailsFlag = "false";

        this.odBalFlag = false;
        this.confirmationMsg = "";
        balance = new BigDecimal("0");
        this.setLabelCrDr("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
