/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.NeftRtgsOutwardPojo;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.report.other.JointHolderBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutwardNeftRtgs extends BaseBean {

    private boolean fieldDisableFlag;
    private boolean disbBtn;
    private boolean ifscFlag;
    private boolean jtDetailPopUp;
    private String message;
    private String payTp;
    private String functionFlag;
    private String drAccNo;
    private String newAcNo;
    private String paymentDate;
    private String custName;
    private String labelCrDr;
    private String jointName;
    private String accStatus;
    private String tranAmount;
    private String neftAmount;
    private String ifsCode;
    private String crAccNo;
    private String beneficiaryCustName;
    private String beneficiaryCustCode;
    private String email;
    private String mobNo;
    private String details;
    private String enterUser;
    private String instNo;
    private String instDt;
    private String custRef;
    private String confirmationMsg;
    private String imageData;
    private String chargeType;
    private String totalCredit = "0";
    private String popUpTotalDebit;
    private String popUpTotalCredit;
    private String deleteMessage;
    private String batchNo;
    private String delBtnValue;
    private String chargeAmount;
    private String nextFocusValue;
    private String modeType;
    private String mobileOrEmail;
    private String bankCode = "";
    private String alertDelVerifyMessage = "";
    private String popUpAccount;
    private String transferMode;
    private String popUpCustName;
    private String popUpJointName, acNoLen;
    private String ifscBranchName;
    private String neftBankName;
    private String displaybnkNM = "none";
    private List<SelectItem> neftBankNameList;
    private String viewID = "/pages/master/sm/test.jsp";
    private BigDecimal balance;
    NeftRtgsOutwardPojo currentItem;
    NeftRtgsOutwardPojo tempCurrentItem;
    private List<SelectItem> functionOption;
    private List<SelectItem> payTpList;
    private List<SelectItem> batchNoList;
    private List<SelectItem> chargeTypeList;
    private List<SelectItem> modeTypeList;
    private List<SelectItem> transferModeList;
    private List<NeftRtgsOutwardPojo> unAuthList;
    private List<NeftRtgsOutwardPojo> tempUnAuthList;
    CustomerMasterDelegate masterDelegate;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameTr = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private CtsManagementFacadeRemote ctsRemote = null;
    private final String ctsJndiName = "CtsManagementFacade";
    private final String jndiHomeNameTxnAuth = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private SmsManagementFacadeRemote smsRemote;
    private CommonReportMethodsRemote commonReport;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat dhmsss = new SimpleDateFormat("ddHHmmssSSS");
    SimpleDateFormat ymdd = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.##");
    Date dt = new Date();
    private static final Object LOCK = new Object();
    private JointHolderBean jointBean;

    public OutwardNeftRtgs() {
        this.setMessage("");
        try {
            masterDelegate = new CustomerMasterDelegate();
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTr);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            ctsRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(ctsJndiName);
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxnAuth);
            smsRemote = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup("SmsManagementFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setAcNoLen(getAcNoLength());

            functionOption = new ArrayList<SelectItem>();
            functionOption.add(new SelectItem("0", "--Select--"));
            functionOption.add(new SelectItem("A", "ADD"));
            functionOption.add(new SelectItem("D", "DELETE"));
            functionOption.add(new SelectItem("V", "VERIFY"));

            modeTypeList = new ArrayList<SelectItem>();
            modeTypeList.add(new SelectItem("EML", "E-MAIL ID"));
            modeTypeList.add(new SelectItem("SMS", "Mobile No"));

            payTpList = new ArrayList<SelectItem>();
            payTpList.add(new SelectItem("0", "--Select--"));
            payTpList.add(new SelectItem("S", "Single Credit"));
            payTpList.add(new SelectItem("M", "Multiple Credit"));

            transferModeList = new ArrayList<SelectItem>();
            transferModeList.add(new SelectItem("0", "--Select--"));

            List<CbsRefRecTypeTO> trfModeList = masterDelegate.getFunctionValues("009");
            for (CbsRefRecTypeTO recTypeTO : trfModeList) {
                transferModeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }


            batchNoList = new ArrayList<SelectItem>();
            batchNoList.add(new SelectItem("0", "--Select--"));

            chargeTypeList = new ArrayList<SelectItem>();
            List<CbsRefRecTypeTO> chargeToList = masterDelegate.getFunctionValues("301");
            if (!chargeToList.isEmpty()) {
                for (CbsRefRecTypeTO recTypeTO : chargeToList) {
                    chargeTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }
            }
            this.fieldDisableFlag = false;
            refreshForm();
            //Setting Outward Bank Name
            List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
            bankCode = bankTo.get(0).getBankcode().trim();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void funtionAction() {
        if (this.getFunctionFlag().equalsIgnoreCase("0")) {
            this.setMessage("Please select desired function.");
            return;
        }
        refreshOnFunctionProcess();
        if (this.functionFlag.equalsIgnoreCase("A")) {
            this.disbBtn = false;
            this.nextFocusValue = "addition";
            this.setMessage("Please fill all details to generate a transaction entry.");
        }
        if (this.functionFlag.equalsIgnoreCase("D") || this.functionFlag.equalsIgnoreCase("V")) {
            this.disbBtn = true;
            this.nextFocusValue = "popUpTrsNo";
            this.setAlertDelVerifyMessage("");
            fetchUnAuthorizedBatchNos();
        }
        if (this.functionFlag.equalsIgnoreCase("D")) {
            this.setDelBtnValue("Delete");
            this.setDeleteMessage("Please select batch no to delete.");
        }
        if (this.functionFlag.equalsIgnoreCase("V")) {
            this.setDelBtnValue("Verify");
            this.setDeleteMessage("Please select batch no to verify.");
        }
    }

    public void validatePaymentDt() {
        this.setMessage("");
        try {
            if (this.paymentDate == null || this.paymentDate.equals("") || this.paymentDate.length() < 10) {
                this.setMessage("Please fill proper Payment. Date.");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.paymentDate);
            if (result == false) {
                this.setMessage("Please fill proper Payment. Date.");
                return;
            }
            if (dmy.parse(this.paymentDate).compareTo(dmy.parse(dmy.format(dt))) < 0
                    || dmy.parse(this.paymentDate).compareTo(dmy.parse(dmy.format(dt))) > 0) {
                this.setMessage("Payment date should be current date.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateDrAccount() {
        this.setMessage("");
        try {
            if (this.drAccNo == null || this.drAccNo.equalsIgnoreCase("") || ((this.drAccNo.length() != 12) && (this.drAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper account number.");
                return;
            }
            this.newAcNo = ftsPostRemote.getNewAccountNumber(this.drAccNo);
            List paramList = ftsPostRemote.isModuleActiveBasedOnAcCode(ftsPostRemote.getAccountCode(newAcNo));
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String modFlag = paramVector.get(1).toString();
                if (modFlag.equalsIgnoreCase("N")) {
                    this.setMessage("Transaction is not allowed for this type of account.");
                    return;
                }
            }

            String acNature = ftsPostRemote.getAccountNature(newAcNo);

            if (acNature.equalsIgnoreCase("PO") && !getOrgnBrCode().equalsIgnoreCase(newAcNo.substring(0, 2))) {
                this.setMessage("You can not generate transaction in other branch gl head.");
                return;
            }

            if (!(acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                this.setMessage("Transaction is not allowed other than saving,current and gl nature.");
                return;
            } else {
                List list = txnRemote.selectForStopBalance(newAcNo, acNature);
                if (list == null || list.isEmpty()) {
                    this.setMessage("");
                } else {
                    this.setMessage("Account has stop payment mark.");
                }
                if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    list = txnRemote.selectFromGLTable(this.drAccNo);
                    if (list == null || list.isEmpty()) {
                        this.setMessage("A/c no does not exist :" + drAccNo);
                        return;
                    }
                    Vector vec = (Vector) list.get(0);
                    int postflag = Integer.parseInt(vec.get(2).toString());
                    if (postflag == 1) {
                        setNewAcNo("");
                        setDrAccNo("");
                        this.setMessage("Transaction is not allowed for this A/c");
                        return;
                    }
                    int msgflag = Integer.parseInt(vec.get(3).toString());
                    if (msgflag == 4) {
                        setNewAcNo("");
                        setDrAccNo("");
                        this.setMessage("Transaction is not allowed for PO/DD GL Heads");
                        return;
                    }
                }
            }
            String resultMessage = ftsPostRemote.ftsAcnoValidate(newAcNo, 1, "");
            if (!resultMessage.equalsIgnoreCase("true")) {
                setNewAcNo("");
                this.setMessage(resultMessage);
                return;
            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List accountChqList = txnRemote.selectFromAcctMaster(newAcNo);
                Vector vector = (Vector) accountChqList.get(0);
                String accountStatus = vector.get(10).toString();
                if (accountStatus.equalsIgnoreCase("11") || accountStatus.equalsIgnoreCase("12") || accountStatus.equalsIgnoreCase("13")) {
                    setNewAcNo("");
                    this.setMessage("This account is NPA.");
                    return;
                }
            }

           // stock statement exipered account not debited
//            String parameterCode = ftsPostRemote.getValueFromCbsparameterInfo("STOCK-STMT-ACCTCODE");
//            if (!(parameterCode.equalsIgnoreCase(""))) {
//                if (parameterCode.contains(newAcNo.substring(2, 4).toString())) {
//                    List doccumentExList = txnRemote.getDocumentExpiryDate(newAcNo);
//                    if (!doccumentExList.isEmpty()) {
//                        Vector docD = (Vector) doccumentExList.get(0);
//                        String docExpDate = docD.get(0).toString();
//                        if (!docExpDate.equalsIgnoreCase("1900-01-01")) {
//                            if (dmy.parse(getTodayDate()).compareTo(ymdd.parse(docExpDate)) > 0) {
//                                this.setMessage("Stock statement has been expired of this account. you can not debited to this account.");
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
            getAccountDetails(acNature);
            getSignatureDetail();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void transferModeOperation() {
        try {
            if (!this.transferMode.equalsIgnoreCase("IMPS")) {
                this.displaybnkNM = "";
                neftBankNameList = new ArrayList<>();
                neftBankNameList.add(new SelectItem("0", "--Select--"));
                List list = remoteInterface.autoNeftBankNameList();
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    neftBankNameList.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
                }
            } else {
                this.displaybnkNM = "none";
                neftBankNameList = new ArrayList<>();
                this.setNeftBankName("");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAccountDetails(String acNature) {
        try {
            if (acNature.equalsIgnoreCase("PO")) {
                this.setCustName(ctsRemote.getGlHeadName(newAcNo));
            } else {
                List AccountDetailList = txnRemote.selectFromAcctMaster(newAcNo);
                if (!AccountDetailList.isEmpty()) {
                    Vector element = (Vector) AccountDetailList.get(0);
                    String accountStatus = element.get(10).toString();
                    this.setAccStatus(ftsPostRemote.getAccountStatusMessage(Integer.parseInt(accountStatus)));
                    this.setCustName(element.get(1).toString());
                    this.setJointName(element.get(2).toString() + " " + element.get(3).toString() + " " + element.get(4).toString() + " " + element.get(5).toString());
                }
            }

            List selectReconTdReconCaRecon = txnRemote.selectFromReconTdReconCaRecon(newAcNo, acNature);
            if (!selectReconTdReconCaRecon.isEmpty()) {
                Vector vecReconTdReconCaRecon = (Vector) selectReconTdReconCaRecon.get(0);
                this.balance = new BigDecimal(vecReconTdReconCaRecon.get(0).toString());
            }

            if (Double.parseDouble(this.balance.toString()) < 0) {
                labelCrDr = " Dr";
                balance = balance.abs();
            } else {
                labelCrDr = " Cr";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateInstNo() {
        this.setMessage("");
        try {
            if (!(this.instNo == null || this.instNo.equals("") || this.instNo.length() == 0)) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                Matcher instNoCheck = p.matcher(this.instNo);
                if (!instNoCheck.matches()) {
                    this.setMessage("Invalid Inst.No. Entry.");
                    return;
                }

                String checkMessage = ctsRemote.chequeValidate(this.newAcNo, this.instNo.trim());
                if (!checkMessage.equalsIgnoreCase("true")) {
                    this.setMessage(checkMessage);
                }
            } else {
                if (!ftsPostRemote.existInParameterInfoReport("NEFT-VCH-ALLOW")) {
                    this.setMessage("Vocher entry is not allowed.");
                    return;
                }
                this.instNo = "";
                this.instDt = dmy.format(new Date());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateInstDt() {
        this.setMessage("");
        try {
            if (!(this.instNo == null || this.instNo.equals("") || this.instNo.length() == 0)) {
                if (this.instDt == null || this.instDt.equals("") || this.instDt.length() < 10) {
                    this.setMessage("Please fill Inst. Date.");
                    return;
                }
                boolean result = new Validator().validateDate_dd_mm_yyyy(this.instDt);
                if (result == false) {
                    this.setMessage("Please fill proper Inst. Date.");
                    return;
                }
                String chqDtMsg = ftsPostRemote.ftsInstDateValidate(ymd.format(dmy.parse(this.instDt)));
                if (!chqDtMsg.equalsIgnoreCase("true")) {
                    this.setMessage(chqDtMsg);
                }
            } else {
                if (!ftsPostRemote.existInParameterInfoReport("NEFT-VCH-ALLOW")) {
                    this.setMessage("Vocher entry is not allowed.");
                    return;
                }
                this.instNo = "";
                this.instDt = dmy.format(new Date());
            }
        } catch (ApplicationException aex) {
            this.setMessage(aex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateAmount() {
        this.setMessage("");
        this.setChargeAmount("");
        this.fieldDisableFlag = false;
        try {
            if (this.tranAmount.equalsIgnoreCase("0") || this.tranAmount.equalsIgnoreCase("0.00")) {
                this.setMessage("Please fill the debit amount upto two decimal places.");
                return;
            }
            if (!ParseFileUtil.isValidAmount(this.tranAmount, 2)) {
                this.setMessage("Please fill the debit amount upto two decimal places.");
            }

            double totalDrAmount = Double.parseDouble(this.getTranAmount());
            if (!this.chargeType.equalsIgnoreCase("0NA")) {
                String charge = getChargeApplyDueToTransaction(this.chargeType, totalDrAmount,
                        ftsPostRemote.getAccountCode(this.newAcNo));
                this.setChargeAmount(charge);
                totalDrAmount = totalDrAmount + Double.parseDouble(charge);
            } else {
                this.setChargeAmount("0");
            }

            String chBal = ftsPostRemote.checkBalance(this.newAcNo, totalDrAmount, getUserName());
            if (!chBal.equalsIgnoreCase("true")) {
                this.setMessage(chBal);
            }
            this.fieldDisableFlag = true;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateNeftAmount() {
        this.setMessage("");
        try {
            if (this.neftAmount.equalsIgnoreCase("0") || this.neftAmount.equalsIgnoreCase("0.00")) {
                this.setMessage("Please fill the credit amount upto two decimal places.");
                return;
            }
            if (!ParseFileUtil.isValidAmount(this.neftAmount, 2)) {
                this.setMessage("Please fill the credit amount upto two decimal places.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getChargeApplyDueToTransaction(String chargeType, double transactionAmount, String accountType) {
        String charge = "0";
        try {
            List chargeApplyList = remoteInterface.getChargeApplyOnCustomer(this.chargeType, transactionAmount,
                    ftsPostRemote.getAccountCode(this.newAcNo), getOrgnBrCode());
            if (!chargeApplyList.isEmpty()) {
                Vector element = (Vector) chargeApplyList.get(0);
                String chargeFlag = element.get(0).toString();
                Double amtOrPerc = Double.parseDouble(element.get(1).toString());
                if (chargeFlag.equalsIgnoreCase("P")) {
                    amtOrPerc = (transactionAmount * amtOrPerc) / 100;
                }
                charge = formatter.format(amtOrPerc);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return charge;
    }

    public void validateIfscCode() {
        try {
            if (!ParseFileUtil.isValidIfsc(this.ifsCode)) {
                this.setMessage("Please fill proper Beneficiary IFSC");
            } else {
                this.ifscBranchName = remoteInterface.branchNameByIfsc(this.ifsCode.trim());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateCrAcno() {
        this.setMessage("");
        if ((this.crAccNo == null) || (this.crAccNo.equalsIgnoreCase("")) || this.crAccNo.length() == 0) {
            this.setMessage("Please fill proper Beneficiary A/c");
        }
    }

    public void validateBeneficiaryCustname() {
        this.setMessage("");
        if ((this.getBeneficiaryCustName() == null) || (this.getBeneficiaryCustName().equalsIgnoreCase(""))
                || this.getBeneficiaryCustName().length() == 0) {
            this.setMessage("Please Enter Proper Beneficiary Name");
        }
    }

    public void validateEmailId() {
        this.setMessage("");
        if (!this.email.equalsIgnoreCase("")) {
            boolean check = new Validator().validateEmail(this.email);
            if (!check == true) {
                this.setMessage("Please Enter Proper Email ID.");
            }
        }
    }

    public void validateMobile() {
        this.setMessage("");
        try {
            if (!(this.getMobNo() == null || this.getMobNo().equalsIgnoreCase(""))) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                Matcher mobileCheck = p.matcher(this.mobNo);
                if (!mobileCheck.matches()) {
                    this.setMessage("Invalid Mobile Number Entry.");
                    return;
                }

                if (!((this.getMobNo() == null) || (this.getMobNo().equalsIgnoreCase("")))) {
                    if (this.getMobNo().length() < 10) {
                        this.setMessage("Please fill 10 digit mobile number");
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateMobileOrEmail() {
        this.setMessage("");
        try {
            if (this.mobileOrEmail == null || this.mobileOrEmail.equals("")) {
                this.setMessage("Please fill Sender Comm. Mode.");
                return;
            }
            if (this.modeType.equals("EML")) {
                boolean check = new Validator().validateEmail(this.mobileOrEmail);
                if (!check == true) {
                    this.setMessage("Please enter proper email-id in sender communication mode.");
                }
            } else if (this.modeType.equals("SMS")) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                Matcher mobileCheck = p.matcher(this.mobileOrEmail);
                if (!mobileCheck.matches()) {
                    this.setMessage("Invalid mobile number entry in sender communication mode.");
                    return;
                }
                if (this.getMobileOrEmail().length() != 10) {
                    this.setMessage("Please fill 10 digit mobile number in sender communication mode.");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateFieldOnSave(String confirmation) {
        String validateMsg = "true";
        try {
            if (this.payTp == null || this.payTp.equals("0")) {
                return validateMsg = "Please select credit type.";
            }
            if (this.paymentDate == null || this.paymentDate.equals("") || this.paymentDate.length() < 10) {
                return validateMsg = "Please fill proper Payment. Date.";
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.paymentDate);
            if (result == false) {
                return validateMsg = "Please fill proper Payment. Date.";
            }
            if (dmy.parse(this.paymentDate).compareTo(dmy.parse(dmy.format(dt))) < 0 || dmy.parse(this.paymentDate).compareTo(dmy.parse(dmy.format(dt))) > 0) {
                return validateMsg = "Payment date should be current date.";
            }
            if (this.drAccNo == null || this.drAccNo.equalsIgnoreCase("") || ((this.drAccNo.length() != 12) && (this.drAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                return validateMsg = "Please fill proper account number.";
            }
            this.newAcNo = ftsPostRemote.getNewAccountNumber(this.drAccNo);

            List paramList = ftsPostRemote.isModuleActiveBasedOnAcCode(ftsPostRemote.getAccountCode(newAcNo));
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String modFlag = paramVector.get(1).toString();
                if (modFlag.equalsIgnoreCase("N")) {
                    return validateMsg = "Transaction is not allowed for this type of account.";
                }
            }

            String acNature = ftsPostRemote.getAccountNature(newAcNo);

            if (acNature.equalsIgnoreCase("PO") && !getOrgnBrCode().equalsIgnoreCase(newAcNo.substring(0, 2))) {
                return validateMsg = "You can not generate transaction in other branch gl head.";
            }

            if (!(acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                return validateMsg = "Transaction is not allowed other than saving,current and gl nature.";
            } else {
                List list = txnRemote.selectForStopBalance(newAcNo, acNature);
                if (list == null || list.isEmpty()) {
                    this.setMessage("");
                } else {
                    this.setMessage("Account has stop payment mark.");
                }
                if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    list = txnRemote.selectFromGLTable(this.drAccNo);
                    if (list == null || list.isEmpty()) {
                        return validateMsg = "A/c no does not exist :" + drAccNo;
                    }
                    Vector vec = (Vector) list.get(0);
                    int postflag = Integer.parseInt(vec.get(2).toString());
                    if (postflag == 1) {
                        return validateMsg = "Transaction is not allowed for this A/c";
                    }
                }
            }

            String resultMessage = ftsPostRemote.ftsAcnoValidate(newAcNo, 1, "");
            if (!resultMessage.equalsIgnoreCase("true")) {
                return validateMsg = resultMessage;
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (!(this.instNo == null || this.instNo.equals("") || this.instNo.length() == 0)) {
                Matcher instNoCheck = p.matcher(this.instNo);
                if (!instNoCheck.matches()) {
                    return validateMsg = "Invalid Inst.No. Entry.";
                }

                String checkMessage = ctsRemote.chequeValidate(this.newAcNo, this.instNo.trim());
                if (!checkMessage.equalsIgnoreCase("true")) {
                    return validateMsg = checkMessage;
                }

                if (this.instDt == null || this.instDt.equals("") || this.instDt.length() < 10) {
                    return validateMsg = "Please fill Inst. Date.";
                }

                boolean resultInstDt = new Validator().validateDate_dd_mm_yyyy(this.instDt);
                if (resultInstDt == false) {
                    return validateMsg = "Please fill proper Inst. Date.";
                }

                String chqDtMsg = ftsPostRemote.ftsInstDateValidate(ymd.format(dmy.parse(this.instDt)));
                if (!chqDtMsg.equalsIgnoreCase("true")) {
                    return validateMsg = chqDtMsg;
                }
            } else {
                if (!ftsPostRemote.existInParameterInfoReport("NEFT-VCH-ALLOW")) {
                    return validateMsg = "Vocher entry is not allowed.";
                }
                this.instNo = "";
                this.instDt = dmy.format(new Date());
            }
            if (this.tranAmount.equalsIgnoreCase("0") || this.tranAmount.equalsIgnoreCase("0.00")) {
                return validateMsg = "Please fill debit amount field.";
            }
            if (!ParseFileUtil.isValidAmount(tranAmount, 2)) {
                return validateMsg = "Please fill debit amount field.";
            }

            double totalDrAmount = Double.parseDouble(this.getTranAmount());
            if (!this.chargeType.equalsIgnoreCase("0NA")) {
                String charge = getChargeApplyDueToTransaction(this.chargeType, totalDrAmount, ftsPostRemote.getAccountCode(this.newAcNo));
                this.setChargeAmount(charge);
                totalDrAmount = totalDrAmount + Double.parseDouble(charge);
            } else {
                this.setChargeAmount("0");
            }

            String chBal = ftsPostRemote.checkBalance(this.newAcNo, totalDrAmount, getUserName());
            if (!chBal.equalsIgnoreCase("true")) {
                return validateMsg = chBal;
            }
            if ((this.getBeneficiaryCustName() == null) || (this.getBeneficiaryCustName().equalsIgnoreCase("")) || this.getBeneficiaryCustName().length() == 0) {
                return validateMsg = "Please fill Proper Beneficiary Name";
            }
            if ((this.crAccNo == null) || (this.crAccNo.equalsIgnoreCase("")) || this.crAccNo.length() == 0) {
                return validateMsg = "Please fill proper Beneficiary A/c";
            }
            if (!ParseFileUtil.isValidIfsc(this.getIfsCode())) {
                return validateMsg = "Please fill proper Beneficiary IFSC";
            }
            //Ifsc code validation from central repository
            this.ifscFlag = false;
            if (confirmation.equalsIgnoreCase("")) {
                try {
//                    String webServiceUrl = Init.webServices.getProperty("webservice.ifsc");
//                    URL url = new URL(webServiceUrl + this.getIfsCode().trim());
//                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//                    String stream = in.readLine();
//                    if (stream.equalsIgnoreCase("false")) {
//                        in.close();
//                        this.ifscFlag = true;
//                        return "IFSC code was not validated";
//                    }
//                    in.close();

                    if (this.ifscBranchName == null || this.ifscBranchName.equals("")) {
                        this.ifscFlag = true;
                        return "IFSC code was not validated";
                    }
                } catch (Exception ex) {
                    this.ifscFlag = true;
                    return "IFSC code was not validated";
                }
            }
            if (this.neftAmount.equalsIgnoreCase("0") || this.neftAmount.equalsIgnoreCase("0.00")) {
                return "Please fill the credit amount upto two decimal places.";
            }
            if (!ParseFileUtil.isValidAmount(this.neftAmount, 2)) {
                return "Please fill the credit amount upto two decimal places.";
            }
            if (this.transferMode == null || this.transferMode.equals("0") || this.transferMode.equals("")) {
                return "Please select transfer mode.";
            }
            String rtgsMinimumAmount = ftsPostRemote.getCodeFromCbsParameterInfo("RTGS-AMOUNT-LIMIT");
            if (this.transferMode.equalsIgnoreCase("R") && new BigDecimal(this.neftAmount).compareTo(new BigDecimal(rtgsMinimumAmount)) == -1) {
                return "Please check credit amount and transfer mode. Transfer mode is not proper.";
            }

            String impsLimitAmount = ftsPostRemote.getCodeFromCbsParameterInfo("IMPS-AMOUNT-LIMIT");
            if (this.transferMode.equalsIgnoreCase("IMPS") && new BigDecimal(this.neftAmount).compareTo(new BigDecimal(impsLimitAmount)) == 1) {
                return "Please check amount and transfer mode. Amount limit is not proper.";
            }
            //For working on axis bank outward case.
            if (!this.transferMode.equalsIgnoreCase("IMPS")) { //Only for NEFT/RTGS
                if (this.neftBankName.trim().equalsIgnoreCase("axis")) {
                    if (this.beneficiaryCustCode == null || this.beneficiaryCustCode.equalsIgnoreCase("")
                            || this.beneficiaryCustCode.trim().length() != 2) {
                        return validateMsg = "Please fill two digit numeic Beneficiary Code like- 10,11...";
                    }
                    Matcher benCustCode = p.matcher(this.beneficiaryCustCode);
                    if (!benCustCode.matches()) {
                        return validateMsg = "Please fill two digit numeic Beneficiary Code like- 10,11...";
                    }
                }
            }
            //End here
            if (!this.email.equalsIgnoreCase("")) {
                boolean check = new Validator().validateEmail(this.email);
                if (!check == true) {
                    return validateMsg = "Please Enter Proper Email ID.";
                }
            }
            if (!(this.getMobNo() == null || this.getMobNo().equalsIgnoreCase(""))) {
                Matcher mobileCheck = p.matcher(this.mobNo);
                if (!mobileCheck.matches()) {
                    return validateMsg = "Invalid Mobile Number Entry.";
                }

                if (!((this.getMobNo() == null) || (this.getMobNo().equalsIgnoreCase("")))) {
                    if (this.getMobNo().length() < 10) {
                        return validateMsg = "Please fill 10 digit mobile number";
                    }
                }
            }
            if (this.mobileOrEmail == null || this.mobileOrEmail.equals("")) {
                return validateMsg = "Please fill Sender Comm. Mode.";
            }
            if (this.modeType.equals("EML")) {
                boolean check = new Validator().validateEmail(this.mobileOrEmail);
                if (!check == true) {
                    return validateMsg = "Please enter proper email-id in sender communication mode.";
                }
            } else if (this.modeType.equals("SMS")) {
                Matcher mobileCheck = p.matcher(this.mobileOrEmail);
                if (!mobileCheck.matches()) {
                    return validateMsg = "Invalid mobile number entry in sender communication mode.";
                }
                if (this.getMobileOrEmail().length() != 10) {
                    return validateMsg = "Please fill 10 digit mobile number in sender communication mode.";
                }
                if (!(this.getMobileOrEmail().substring(0, 1).equals("7")
                        || this.getMobileOrEmail().substring(0, 1).equals("8")
                        || this.getMobileOrEmail().substring(0, 1).equals("9")
                        || this.getMobileOrEmail().substring(0, 1).equals("6"))) {
                    return validateMsg = "Mobile no should start with 6,7,8 or 9.";
                }
            }
            //Checking In case of outward IMPS
            if (this.transferMode.equalsIgnoreCase("IMPS")) {
                if (!this.payTp.equalsIgnoreCase("S")) {
                    return validateMsg = "Only single credit is allow in case of IMPS.";
                }
                if (this.mobNo == null || this.mobNo.equals("") || this.mobNo.trim().length() != 10) {
                    return validateMsg = "In IMPS, Mobile No is mandatory.";
                }
                if (!this.modeType.equalsIgnoreCase("SMS")) {
                    return validateMsg = "Only mobile no in Sender Comm. Mode is allow in case of IMPS.";
                }
            } else {
                if (this.neftBankName == null || this.neftBankName.equals("0")) {
                    return validateMsg = "Please select bank name.";
                }
            }
            if (!tempUnAuthList.isEmpty()) {
                if (!this.transferMode.equalsIgnoreCase("IMPS")) {
                    if (!this.transferMode.equals(tempUnAuthList.get(0).getPaymentType().trim())) {
                        return validateMsg = "Transfer mode should be same as previous entry in this batch.";
                    }
                    if (!this.neftBankName.equals(tempUnAuthList.get(0).getNeftBankName())) {
                        return validateMsg = "Bank Name should be same in this batch.";
                    }
                }
            }
//            String parameterCode = ftsPostRemote.getValueFromCbsparameterInfo("STOCK-STMT-ACCTCODE");
//            if (!(parameterCode.equalsIgnoreCase(""))) {
//                if (parameterCode.contains(newAcNo.substring(2, 4).toString())) {
//                    List doccumentExList = txnRemote.getDocumentExpiryDate(newAcNo);
//                    if (!doccumentExList.isEmpty()) {
//                        Vector docD = (Vector) doccumentExList.get(0);
//                        String docExpDate = docD.get(0).toString();
//                        if (!docExpDate.equalsIgnoreCase("1900-01-01")) {
//                            if (dmy.parse(getTodayDate()).compareTo(ymdd.parse(docExpDate)) > 0) {
//                              return validateMsg = "Stock statement has been expired of this account. you can not debited to this account.";
//                            }
//                        }
//                    }
//                }
//            }
            //End Here
            if (this.details == null || this.details.equals("")) {
                return validateMsg = "Please fill details.";
            }
            return validateMsg;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public void fetchUnAuthorizedBatchNos() {
        this.setMessage("");
        this.setDeleteMessage("");
        try {
            batchNoList = new ArrayList<>();
            batchNoList.add(new SelectItem("0", "--Select--"));

            List resultList = remoteInterface.getNeftUnAuthBatchNos(this.getOrgnBrCode(), "P", "N", ymd.format(dmy.parse(getTodayDate())));
            if (resultList.isEmpty()) {
                this.setMessage("There is no batch no to delete or authorize.");
                this.setDeleteMessage("There is no batch no to delete or authorize.");
                return;
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                batchNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            this.setDeleteMessage(ex.getMessage());
        }
    }

    public void fetchAllUnAuthData() {
        unAuthList = new ArrayList<NeftRtgsOutwardPojo>();
        this.setDeleteMessage("");
        this.setPopUpAccount("");
        this.setPopUpTotalDebit("0");
        this.setPopUpTotalCredit("0");
        this.setPopUpCustName("");
        this.setPopUpJointName("");
        try {
            if (this.batchNo == null || this.batchNo.equals("0") || this.batchNo.equals("")) {
                this.setDeleteMessage("Please select batch no.");
                return;
            }
            List<NeftOwDetails> resultList = remoteInterface.getOwUnAuthEntryBranch(Double.parseDouble(this.batchNo),
                    this.getOrgnBrCode(), "P", "N", ymd.format(dmy.parse(getTodayDate())));
            if (resultList.isEmpty()) {
                this.setDeleteMessage("There is no data.");
                return;
            }

            BigDecimal tempPopUpDebit = new BigDecimal(this.popUpTotalDebit);
            BigDecimal tempPopUpCredit = new BigDecimal(this.popUpTotalCredit);

            for (NeftOwDetails entity : resultList) {
                NeftRtgsOutwardPojo pojo = new NeftRtgsOutwardPojo();

//                if (entity.getPaymentType().equalsIgnoreCase("N")) {
//                    pojo.setPaymentType("NEFT");
//                } else if (entity.getPaymentType().equalsIgnoreCase("R")) {
//                    pojo.setPaymentType("RTGS");
//                }
                pojo.setPaymentType(commonReport.getRefRecDesc("009", entity.getPaymentType().trim()));
                pojo.setUniqueCustomerRefNo(entity.getUniqueCustomerRefNo());
                pojo.setBeneficiaryName(entity.getBeneficiaryName());
                pojo.setBeneficiaryCode(entity.getBeneficiaryCode() != null ? entity.getBeneficiaryCode() : "");
                pojo.setNeftAmount(entity.getTxnAmt());
                pojo.setPaymentDate(entity.getPaymentDate());
                pojo.setCreditAccountNo(entity.getCreditAccountNo());
                pojo.setBeneficiaryIfsc(entity.getOutsideBankIfscCode());
                pojo.setDebitAccountNo(entity.getDebitAccountNo());
                pojo.setBeneficiaryEmailId(entity.getBeneficiaryEmailId() != null ? entity.getBeneficiaryEmailId() : "");
                pojo.setBeneficiaryMobileNo(entity.getBeneficiaryMobileNo() != null ? entity.getBeneficiaryMobileNo() : "");
                pojo.setCmsBankRefNo(entity.getCmsBankRefNo() == null ? "" : entity.getCmsBankRefNo());
                pojo.setUtrNo(entity.getUtrNo() == null ? "" : entity.getUtrNo());
                pojo.setInstNo(entity.getInstNo());
                pojo.setInstDate(dmy.format(entity.getInstDate()));

                pojo.setDt(ymd.format(entity.getDt()));
                pojo.setTranTime(entity.getTrantime());
                pojo.setStatus(entity.getStatus());
                pojo.setReason(entity.getReason() == null ? "" : entity.getReason());
                pojo.setDetails(entity.getDetails() == null ? "" : entity.getDetails());
                pojo.setOrgnId(entity.getOrgBrnid());
                pojo.setDestbrnId(entity.getDestBrnid());
                pojo.setAuth(entity.getAuth() == null ? "" : entity.getAuth());
                pojo.setEnterBy(entity.getEnterBy());
                pojo.setAuthBy(entity.getAuthby() == null ? "" : entity.getAuthby());
                String chgType = entity.getChargeType() == null ? "" : entity.getChargeType();
                pojo.setChargeType((chgType.equals("") || chgType.equals("0NA")) ? "Not Applicable" : entity.getChargeType());
                pojo.setChargeAmount(entity.getChargeAmount() == null ? BigDecimal.ZERO : entity.getChargeAmount());
                pojo.setFileName(entity.getFileName() != null ? entity.getFileName() : "");
                pojo.setSenderCommModeType(entity.getSenderCommModeType() != null ? entity.getSenderCommModeType() : "");
                pojo.setSenderCommMode(entity.getSenderCommMode() != null ? entity.getSenderCommMode() : "");
                pojo.setRemmitInfo(entity.getRemmitInfo() != null ? entity.getRemmitInfo() : "");
                pojo.setOutwardFileName(entity.getOutwardFileName() == null ? "" : entity.getOutwardFileName());
                pojo.setCpsmsMessageId(entity.getCPSMSMessageId() == null ? "" : entity.getCPSMSMessageId());
                pojo.setCpsmsBatchNo(entity.getCPSMSBatchNo() == null ? "" : entity.getCPSMSBatchNo());
                pojo.setCpsmsTranIdCrTranId(entity.getCPSMSTranIdCrTranId() == null ? "" : entity.getCPSMSTranIdCrTranId());
                pojo.setDebitSuccessTrsno(entity.getDebitSuccessTrsno() == null ? 0f : entity.getDebitSuccessTrsno());
                pojo.setResponseUpdateTime(entity.getResponseUpdateTime() == null ? new Date() : entity.getResponseUpdateTime());
                pojo.setSuccessToFailureFlag(entity.getSuccessToFailureFlag() == null ? "" : entity.getSuccessToFailureFlag());
                pojo.setDebitAmount(entity.getDebitAmount());
                pojo.setTrsNo(entity.getTrsNo());
                pojo.setNeftBankName(entity.getInitiatedBankName() == null ? "" : entity.getInitiatedBankName());

                unAuthList.add(pojo);

                tempPopUpDebit = tempPopUpDebit.add(entity.getTxnAmt());
                tempPopUpCredit = tempPopUpCredit.add(entity.getTxnAmt());
            }
            this.setPopUpAccount(resultList.get(0).getDebitAccountNo());
            this.setPopUpTotalDebit(tempPopUpDebit.toString());
            this.setPopUpTotalCredit(tempPopUpCredit.toString());
            getPopUpAccountDetail(resultList.get(0).getDebitAccountNo());
            getPopUpSignatureDetail();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            this.setDeleteMessage(ex.getMessage());
        }
    }

    public void deleteRow() {
        try {
            if (tempCurrentItem == null) {
                this.setMessage("Please select a row from table to delete.");
                return;
            }
            tempUnAuthList.remove(tempCurrentItem);

            BigDecimal prevTotalCredit = new BigDecimal(this.totalCredit);
            prevTotalCredit = prevTotalCredit.subtract(tempCurrentItem.getNeftAmount());
            this.totalCredit = prevTotalCredit.toString();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.functionFlag.equals("A")) {
            this.setConfirmationMsg("Are you sure to save this entry!");
        }
    }

    public void addUnAuthTxnEntry(String confirmation) {
        try {
            createUnAuthTxnEntry(confirmation);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void createUnAuthTxnEntry(String confirmation) {
        this.setMessage("");
        try {
            if (this.functionFlag.equalsIgnoreCase("A")) {
                String validateMsg = validateFieldOnSave(confirmation);
                if (!validateMsg.equalsIgnoreCase("true")) {
                    this.setMessage(validateMsg);
                    return;
                }
                if (payTp.equals("S") && tempUnAuthList != null && tempUnAuthList.size() == 1) { //Single Credit
                    this.setMessage("You can not add more then one entry.");
                    return;
                }
                String uCRefNo = "";
//                CbsAutoNeftDetails autoObj = null;
                if (this.transferMode.equalsIgnoreCase("IMPS")) {
                    String hoAccountNo = ftsPostRemote.getCodeFromCbsParameterInfo("IMPS-OW-HEAD");
                    if (hoAccountNo == null || hoAccountNo.trim().equals("")) {
                        this.setMessage("Please define IMPS Head.");
                        return;
                    }
                    uCRefNo = ymdms.format(new Date());
                } else {
//                    autoObj = remoteInterface.findAutoNeftDetailForOutward();
                    if (this.neftBankName.trim().equalsIgnoreCase("icici")) {
                        uCRefNo = this.getDrAccNo().substring(0, 10) + ymdms.format(new Date());
                    } else if (this.neftBankName.trim().equalsIgnoreCase("axis")) {
                        uCRefNo = dhmsss.format(new Date());
                    } else {
                        uCRefNo = ymdms.format(new Date());
                    }
                }

                NeftRtgsOutwardPojo obj = new NeftRtgsOutwardPojo();

                obj.setPaymentType(this.transferMode);
                obj.setUniqueCustomerRefNo(uCRefNo);
                obj.setBeneficiaryName(this.beneficiaryCustName);
                obj.setBeneficiaryCode(this.beneficiaryCustCode == null ? "" : this.beneficiaryCustCode);
                obj.setNeftAmount(new BigDecimal(this.neftAmount));
                obj.setPaymentDate(this.paymentDate);
                obj.setCreditAccountNo(this.crAccNo);
                obj.setBeneficiaryIfsc(this.ifsCode);
                obj.setDebitAccountNo(this.newAcNo);
                obj.setBeneficiaryEmailId(this.email == null ? "" : this.email);
                obj.setBeneficiaryMobileNo(this.mobNo == null ? "" : this.mobNo);
                obj.setCmsBankRefNo("");
                obj.setUtrNo("");
                obj.setInstNo(this.instNo.trim());
                obj.setInstDate(this.instDt);
                obj.setDt(ymd.format(dt));
                obj.setTranTime(new Date());
                obj.setStatus("P");
                obj.setReason("");
                obj.setDetails("Pending");
                obj.setOrgnId(getOrgnBrCode());
                obj.setDestbrnId(this.newAcNo.substring(0, 2));
                obj.setAuth("N");
                obj.setEnterBy(getUserName());
                obj.setAuthBy("");
                obj.setChargeType(this.chargeType);
                obj.setChargeAmount(new BigDecimal(this.chargeAmount));
                obj.setFileName("");
                obj.setSenderCommModeType(this.modeType);
                obj.setSenderCommMode(this.mobileOrEmail);
                obj.setRemmitInfo(this.details);
                obj.setOutwardFileName("");
                obj.setCpsmsMessageId("");
                obj.setCpsmsBatchNo("");
                obj.setCpsmsTranIdCrTranId("");
                obj.setDebitSuccessTrsno(0f);
                obj.setResponseUpdateTime(new Date());
                obj.setSuccessToFailureFlag("");
                obj.setDebitAmount(new BigDecimal(this.tranAmount));
                obj.setNeftBankName(this.neftBankName == null ? "" : this.neftBankName);

                BigDecimal prevTotalCredit = new BigDecimal(this.totalCredit);
                prevTotalCredit = prevTotalCredit.add(new BigDecimal(this.neftAmount));
                this.totalCredit = prevTotalCredit.toString();

                tempUnAuthList.add(obj);
                nextFocusValue = this.payTp.equals("S") ? "saveButton" : "beneficiaryName";
                refreshOnlyBeneficiaryField();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void generateOwTxn() {
        try {
            if (this.functionFlag.equals("A")) {
                if (tempUnAuthList.isEmpty()) {
                    this.setMessage("There is no data to generate the outward transactions.");
                    return;
                }
                //Batch amount validation
                BigDecimal tempDrAmount = tempUnAuthList.get(0).getDebitAmount();
                BigDecimal tempCrAmount = BigDecimal.ZERO;

                float trsNo = ftsPostRemote.getTrsNo();
                List<NeftOwDetails> dataList = new ArrayList<>();
                for (NeftRtgsOutwardPojo pojo : tempUnAuthList) {
                    tempCrAmount = tempCrAmount.add(pojo.getNeftAmount());

                    NeftOwDetails neftOwEntity = new NeftOwDetails();

                    neftOwEntity.setPaymentType(pojo.getPaymentType());
                    neftOwEntity.setUniqueCustomerRefNo(pojo.getUniqueCustomerRefNo());
                    neftOwEntity.setBeneficiaryName(pojo.getBeneficiaryName());
                    neftOwEntity.setBeneficiaryCode(pojo.getBeneficiaryCode());
                    neftOwEntity.setTxnAmt(pojo.getNeftAmount());
                    neftOwEntity.setPaymentDate(pojo.getPaymentDate());
                    neftOwEntity.setCreditAccountNo(pojo.getCreditAccountNo());
                    neftOwEntity.setOutsideBankIfscCode(pojo.getBeneficiaryIfsc());
                    neftOwEntity.setDebitAccountNo(pojo.getDebitAccountNo());
                    neftOwEntity.setBeneficiaryEmailId(pojo.getBeneficiaryEmailId());
                    neftOwEntity.setBeneficiaryMobileNo(pojo.getBeneficiaryMobileNo());
                    neftOwEntity.setCmsBankRefNo(pojo.getCmsBankRefNo());
                    neftOwEntity.setUtrNo(pojo.getUtrNo());
                    neftOwEntity.setInstNo(pojo.getInstNo());
                    neftOwEntity.setInstDate(dmy.parse(pojo.getInstDate()));
                    neftOwEntity.setDt(ymd.parse(pojo.getDt()));
                    neftOwEntity.setTrantime(pojo.getTranTime());
                    neftOwEntity.setStatus(pojo.getStatus());
                    neftOwEntity.setReason(pojo.getReason());
                    neftOwEntity.setDetails(pojo.getDetails());
                    neftOwEntity.setOrgBrnid(pojo.getOrgnId());
                    neftOwEntity.setDestBrnid(pojo.getDestbrnId());
                    neftOwEntity.setAuth(pojo.getAuth());
                    neftOwEntity.setEnterBy(pojo.getEnterBy());
                    neftOwEntity.setAuthby(pojo.getAuthBy());
                    neftOwEntity.setChargeType(pojo.getChargeType());
                    neftOwEntity.setChargeAmount(pojo.getChargeAmount());
                    neftOwEntity.setFileName(pojo.getFileName());
                    neftOwEntity.setSenderCommModeType(pojo.getSenderCommModeType());
                    neftOwEntity.setSenderCommMode(pojo.getSenderCommMode());
                    neftOwEntity.setRemmitInfo(pojo.getRemmitInfo());
                    neftOwEntity.setOutwardFileName(pojo.getOutwardFileName());
                    neftOwEntity.setCPSMSMessageId(pojo.getCpsmsMessageId());
                    neftOwEntity.setCPSMSBatchNo(pojo.getCpsmsBatchNo());
                    neftOwEntity.setCPSMSTranIdCrTranId(pojo.getCpsmsTranIdCrTranId());
                    neftOwEntity.setDebitSuccessTrsno(pojo.getDebitSuccessTrsno());
                    neftOwEntity.setResponseUpdateTime(pojo.getResponseUpdateTime());
                    neftOwEntity.setSuccessToFailureFlag(pojo.getSuccessToFailureFlag());
                    neftOwEntity.setDebitAmount(pojo.getDebitAmount());
                    neftOwEntity.setTrsNo(trsNo);
                    neftOwEntity.setInitiatedBankName(pojo.getNeftBankName());

                    dataList.add(neftOwEntity);
                }
                if (tempDrAmount.compareTo(tempCrAmount) != 0) {
                    this.setMessage("Total credit and debit amount is mismatch.");
                    return;
                }
                String resultMessage = "";
                synchronized (LOCK) {
                    resultMessage = remoteInterface.saveNeftOwDetails(dataList, getOrgnBrCode(), getUserName(), getTodayDate());
                }
                if (!resultMessage.equalsIgnoreCase("true")) {
                    this.setMessage(resultMessage);
                    return;
                }
                refreshField();
                this.setMessage("Data has been saved successfully. Batch No is:-" + trsNo);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processDeleteAndVerifyPanel() {
        this.setDeleteMessage("");
        try {
            if (this.batchNo == null || this.batchNo.equals("0") || this.batchNo.equals("")) {
                this.setDeleteMessage("Please select batch no.");
                return;
            }
            if (this.popUpTotalDebit == null || this.popUpTotalDebit.equals("")) {
                this.setDeleteMessage("There should be total debit amount for this batch.");
                return;
            }
            if (this.popUpTotalCredit == null || this.popUpTotalCredit.equals("")) {
                this.setDeleteMessage("There should be total credit amount for this batch.");
                return;
            }
            if (unAuthList.isEmpty()) {
                this.setDeleteMessage("There is no data to delete or verify");
            }
            String resultMessage = "";
            if (this.functionFlag.equals("D")) {
                synchronized (LOCK) {
                    resultMessage = remoteInterface.deleteNeftOwDetails(unAuthList, getOrgnBrCode(), getUserName(), getTodayDate());
                }
            } else if (this.functionFlag.equals("V")) {
                synchronized (LOCK) {
                    //Change method here
                    resultMessage = remoteInterface.verifyNeftOwDetails(unAuthList, getOrgnBrCode(), getUserName(), getTodayDate());
                }
            }
            if (!resultMessage.equalsIgnoreCase("true")) {
                this.setDeleteMessage(resultMessage);
                return;
            }
            fetchUnAuthorizedBatchNos();

            this.setDeleteMessage("");
            this.setBatchNo("0");
            this.setPopUpAccount("");
            this.setPopUpTotalDebit("0");
            this.setPopUpTotalCredit("0");
            this.setPopUpCustName("");
            this.setPopUpJointName("");
            unAuthList = new ArrayList<NeftRtgsOutwardPojo>();
            if (this.functionFlag.equals("D")) {
                this.setDeleteMessage("Data has been deleted successfully.");
            } else if (this.functionFlag.equals("V")) {
                this.setDeleteMessage("Data has been verified successfully.");
            }
        } catch (Exception ex) {
            this.setDeleteMessage(ex.getMessage());
            this.setMessage(ex.getMessage());
        }
    }

    public void delPopulateMessage() {
        this.setAlertDelVerifyMessage("");
        try {
            if (this.delBtnValue.equalsIgnoreCase("delete")) {
                this.setAlertDelVerifyMessage("Are you sure to delete this batch ?");
            } else if (this.delBtnValue.equalsIgnoreCase("verify")) {
                this.setAlertDelVerifyMessage("Are you sure to verify this batch ?");
            }
        } catch (Exception ex) {
            this.setDeleteMessage(ex.getMessage());
        }
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail() {
        try {
            imageData = null;
            String sign = txnAuthRemote.getSignatureDetails(this.newAcNo);
            if (!sign.equalsIgnoreCase("Signature not found")) {
                String imageCode = sign.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(this.newAcNo);
                System.out.println("EncryptAcno Is-->" + encryptAcno);
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
//            System.out.println("Image Data Is-->" + imageData);
        } catch (FileNotFoundException fne) {
            if (fne.getMessage().contains("No such file or directory")) {
                this.setMessage("Signature does not exist.");
            } else {
                this.setMessage("");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getPopUpSignatureDetail() {
        try {
            imageData = null;
            String sign = txnAuthRemote.getSignatureDetails(this.popUpAccount);
            if (!sign.equalsIgnoreCase("Signature not found")) {
                String imageCode = sign.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(this.popUpAccount);
                System.out.println("EncryptAcno Is-->" + encryptAcno);
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
//            System.out.println("Image Auth Data Is-->" + imageData);
        } catch (FileNotFoundException fne) {
            if (fne.getMessage().contains("No such file or directory")) {
                this.setMessage("Signature does not exist.");
            } else {
                this.setMessage("");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getPopUpAccountDetail(String account) {
        try {
            String accountNature = ftsPostRemote.getAccountNature(account);
            if (accountNature.equalsIgnoreCase("PO")) {
                this.setPopUpCustName(ctsRemote.getGlHeadName(account));
            } else {
                List AccountDetailList = txnRemote.selectFromAcctMaster(account);
                if (!AccountDetailList.isEmpty()) {
                    Vector element = (Vector) AccountDetailList.get(0);
                    this.setPopUpCustName(element.get(1).toString());
                    this.setPopUpJointName(element.get(2).toString() + " " + element.get(3).toString() + " " + element.get(4).toString() + " " + element.get(5).toString());
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateJointAccount() {
        this.setMessage("");
        this.jtDetailPopUp = false;
        try {
            if (this.popUpAccount == null || this.popUpAccount.equals("")) {
                this.setDeleteMessage("Debit account can not be blank.");
                return;
            }
            System.out.println("Form Account No Is->" + popUpAccount);
            ftsPostRemote.getNewAccountNumber(this.popUpAccount);
            this.jtDetailPopUp = true;
            this.setViewID("/pages/other/jointholderdetail.jsp");
        } catch (Exception ex) {
            this.setDeleteMessage(ex.getMessage());
        }
    }

    public void jtDetails() {
        this.setMessage("");
        try {
            jointBean.jtDetails(ftsPostRemote.getNewAccountNumber(this.popUpAccount));
        } catch (Exception ex) {
            this.setDeleteMessage(ex.getMessage());
        }
    }

    //Complete
    public void refreshOnFunctionProcess() {
        this.setPayTp("0");
        this.setTransferMode("0");
        this.setPaymentDate(dmy.format(dt));
        this.setDrAccNo("");
        this.setNewAcNo("");
        this.setCustName("");
        this.setBalance(new BigDecimal("0"));
        this.setLabelCrDr("");
        this.setJointName("");
        this.setAccStatus("");
        this.setInstNo("");
        this.setInstDt(dmy.format(dt));
        this.setTranAmount("0");
        this.setIfsCode("");
        this.setIfscBranchName("");
        this.setCrAccNo("");
        this.setBeneficiaryCustName("");
        this.setBeneficiaryCustCode("");
        this.setEmail("");
        this.setMobNo("");
        this.setDetails("");
        this.setEnterUser("");
        this.setCustRef("");
        this.setChargeAmount("");
        this.setMobileOrEmail("");
        this.ifscFlag = false;
        this.setNeftAmount("");
        this.setTotalCredit("0");
        this.setDeleteMessage("");
        this.setBatchNo("0");
        this.setPopUpAccount("");
        this.setPopUpTotalDebit("0");
        this.setPopUpTotalCredit("0");
        this.setPopUpCustName("");
        this.setPopUpJointName("");
        this.setDelBtnValue("");
        this.setAlertDelVerifyMessage("");
        this.setNeftBankName("0");
        this.fieldDisableFlag = false;
        tempCurrentItem = null;
        currentItem = null;
        tempUnAuthList = new ArrayList<>();
        unAuthList = new ArrayList<>();
    }

    //Complete
    public void refreshField() {
        disbBtn = true;
        this.setFunctionFlag("0");
        this.setPayTp("0");
        this.setTransferMode("0");
        this.setPaymentDate(dmy.format(dt));
        this.setDrAccNo("");
        this.setNewAcNo("");
        this.setCustName("");
        this.setBalance(new BigDecimal("0"));
        this.setLabelCrDr("");
        this.setJointName("");
        this.setAccStatus("");
        this.setInstNo("");
        this.setInstDt(dmy.format(dt));
        this.setTranAmount("0");
        this.setIfsCode("");
        this.setIfscBranchName("");
        this.setCrAccNo("");
        this.setBeneficiaryCustName("");
        this.setBeneficiaryCustCode("");
        this.setEmail("");
        this.setMobNo("");
        this.setDetails("");
        this.setEnterUser("");
        this.setCustRef("");
        this.setChargeAmount("");
        this.setMobileOrEmail("");
        this.ifscFlag = false;
        this.setNeftAmount("");
        this.setTotalCredit("0");
        this.setDeleteMessage("");
        this.setBatchNo("0");
        this.setPopUpAccount("");
        this.setPopUpTotalDebit("0");
        this.setPopUpTotalCredit("0");
        this.setPopUpCustName("");
        this.setPopUpJointName("");
        this.setDelBtnValue("");
        this.setAlertDelVerifyMessage("");
        this.fieldDisableFlag = false;
        tempCurrentItem = null;
        currentItem = null;
        tempUnAuthList = new ArrayList<>();
        unAuthList = new ArrayList<>();
    }

    //Complete
    public void refreshForm() {
        disbBtn = true;
        this.setMessage("");
        this.setFunctionFlag("0");
        this.setPayTp("0");
        this.setTransferMode("0");
        this.setPaymentDate(dmy.format(dt));
        this.setDrAccNo("");
        this.setNewAcNo("");
        this.setCustName("");
        this.setBalance(new BigDecimal("0"));
        this.setLabelCrDr("");
        this.setJointName("");
        this.setAccStatus("");
        this.setInstNo("");
        this.setInstDt(dmy.format(dt));
        this.setTranAmount("0");
        this.setIfsCode("");
        this.setIfscBranchName("");
        this.setCrAccNo("");
        this.setBeneficiaryCustName("");
        this.setBeneficiaryCustCode("");
        this.setEmail("");
        this.setMobNo("");
        this.setDetails("");
        this.setEnterUser("");
        this.setCustRef("");
        this.setChargeAmount("");
        this.setMobileOrEmail("");
        this.ifscFlag = false;
        this.setNeftAmount("");
        this.setTotalCredit("0");
        this.setDeleteMessage("");
        this.setBatchNo("0");
        this.setPopUpAccount("");
        this.setPopUpTotalDebit("0");
        this.setPopUpTotalCredit("0");
        this.setPopUpCustName("");
        this.setPopUpJointName("");
        this.setDelBtnValue("");
        this.setAlertDelVerifyMessage("");
        this.fieldDisableFlag = false;
        tempCurrentItem = null;
        currentItem = null;
        tempUnAuthList = new ArrayList<>();
        unAuthList = new ArrayList<>();
        neftBankNameList = new ArrayList<>();
        this.setNeftBankName("");
    }

    //Complete
    public void refreshDeletePanel() {
        this.setDeleteMessage("");
        this.setBatchNo("0");
        this.setPopUpAccount("");
        this.setPopUpTotalDebit("0");
        this.setPopUpTotalCredit("0");
        this.setPopUpCustName("");
        this.setPopUpJointName("");
        unAuthList = new ArrayList<>();
    }

    public void refreshOnlyBeneficiaryField() {
        this.setBeneficiaryCustName("");
        this.setCrAccNo("");
        this.setIfsCode("");
        this.setIfscBranchName("");
        this.setNeftAmount("");
        this.setTransferMode("0");
        this.setBeneficiaryCustCode("");
        this.setEmail("");
        this.setMobNo("");
        this.setModeType("SMS");
        this.setMobileOrEmail("");
        this.setDetails("");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public boolean isIfscFlag() {
        return ifscFlag;
    }

    public void setIfscFlag(boolean ifscFlag) {
        this.ifscFlag = ifscFlag;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getMobileOrEmail() {
        return mobileOrEmail;
    }

    public void setMobileOrEmail(String mobileOrEmail) {
        this.mobileOrEmail = mobileOrEmail;
    }

    public List<SelectItem> getModeTypeList() {
        return modeTypeList;
    }

    public void setModeTypeList(List<SelectItem> modeTypeList) {
        this.modeTypeList = modeTypeList;
    }

    public String getNextFocusValue() {
        return nextFocusValue;
    }

    public void setNextFocusValue(String nextFocusValue) {
        this.nextFocusValue = nextFocusValue;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterUser(String enterUser) {
        this.enterUser = enterUser;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public List<SelectItem> getFunctionOption() {
        return functionOption;
    }

    public void setFunctionOption(List<SelectItem> functionOption) {
        this.functionOption = functionOption;
    }

    public String getPayTp() {
        return payTp;
    }

    public void setPayTp(String payTp) {
        this.payTp = payTp;
    }

    public List<SelectItem> getPayTpList() {
        return payTpList;
    }

    public void setPayTpList(List<SelectItem> payTpList) {
        this.payTpList = payTpList;
    }

    public String getDrAccNo() {
        return drAccNo;
    }

    public void setDrAccNo(String drAccNo) {
        this.drAccNo = drAccNo;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getLabelCrDr() {
        return labelCrDr;
    }

    public void setLabelCrDr(String labelCrDr) {
        this.labelCrDr = labelCrDr;
    }

    public String getJointName() {
        return jointName;
    }

    public void setJointName(String jointName) {
        this.jointName = jointName;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getIfsCode() {
        return ifsCode;
    }

    public void setIfsCode(String ifsCode) {
        this.ifsCode = ifsCode;
    }

    public String getCrAccNo() {
        return crAccNo;
    }

    public void setCrAccNo(String crAccNo) {
        this.crAccNo = crAccNo;
    }

    public String getBeneficiaryCustName() {
        return beneficiaryCustName;
    }

    public void setBeneficiaryCustName(String beneficiaryCustName) {
        this.beneficiaryCustName = beneficiaryCustName;
    }

    public String getBeneficiaryCustCode() {
        return beneficiaryCustCode;
    }

    public void setBeneficiaryCustCode(String beneficiaryCustCode) {
        this.beneficiaryCustCode = beneficiaryCustCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<NeftRtgsOutwardPojo> getUnAuthList() {
        return unAuthList;
    }

    public void setUnAuthList(List<NeftRtgsOutwardPojo> unAuthList) {
        this.unAuthList = unAuthList;
    }

    public NeftRtgsOutwardPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NeftRtgsOutwardPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isDisbBtn() {
        return disbBtn;
    }

    public void setDisbBtn(boolean disbBtn) {
        this.disbBtn = disbBtn;
    }

    public String getCustRef() {
        return custRef;
    }

    public void setCustRef(String custRef) {
        this.custRef = custRef;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public List<SelectItem> getChargeTypeList() {
        return chargeTypeList;
    }

    public void setChargeTypeList(List<SelectItem> chargeTypeList) {
        this.chargeTypeList = chargeTypeList;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getNeftAmount() {
        return neftAmount;
    }

    public void setNeftAmount(String neftAmount) {
        this.neftAmount = neftAmount;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getDeleteMessage() {
        return deleteMessage;
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDelBtnValue() {
        return delBtnValue;
    }

    public void setDelBtnValue(String delBtnValue) {
        this.delBtnValue = delBtnValue;
    }

    public List<SelectItem> getBatchNoList() {
        return batchNoList;
    }

    public void setBatchNoList(List<SelectItem> batchNoList) {
        this.batchNoList = batchNoList;
    }

    public NeftRtgsOutwardPojo getTempCurrentItem() {
        return tempCurrentItem;
    }

    public void setTempCurrentItem(NeftRtgsOutwardPojo tempCurrentItem) {
        this.tempCurrentItem = tempCurrentItem;
    }

    public List<NeftRtgsOutwardPojo> getTempUnAuthList() {
        return tempUnAuthList;
    }

    public void setTempUnAuthList(List<NeftRtgsOutwardPojo> tempUnAuthList) {
        this.tempUnAuthList = tempUnAuthList;
    }

    public String getPopUpTotalDebit() {
        return popUpTotalDebit;
    }

    public void setPopUpTotalDebit(String popUpTotalDebit) {
        this.popUpTotalDebit = popUpTotalDebit;
    }

    public String getPopUpTotalCredit() {
        return popUpTotalCredit;
    }

    public void setPopUpTotalCredit(String popUpTotalCredit) {
        this.popUpTotalCredit = popUpTotalCredit;
    }

    public boolean isFieldDisableFlag() {
        return fieldDisableFlag;
    }

    public void setFieldDisableFlag(boolean fieldDisableFlag) {
        this.fieldDisableFlag = fieldDisableFlag;
    }

    public String getAlertDelVerifyMessage() {
        return alertDelVerifyMessage;
    }

    public void setAlertDelVerifyMessage(String alertDelVerifyMessage) {
        this.alertDelVerifyMessage = alertDelVerifyMessage;
    }

    public String getPopUpAccount() {
        return popUpAccount;
    }

    public void setPopUpAccount(String popUpAccount) {
        this.popUpAccount = popUpAccount;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public List<SelectItem> getTransferModeList() {
        return transferModeList;
    }

    public void setTransferModeList(List<SelectItem> transferModeList) {
        this.transferModeList = transferModeList;
    }

    public String getPopUpCustName() {
        return popUpCustName;
    }

    public void setPopUpCustName(String popUpCustName) {
        this.popUpCustName = popUpCustName;
    }

    public String getPopUpJointName() {
        return popUpJointName;
    }

    public void setPopUpJointName(String popUpJointName) {
        this.popUpJointName = popUpJointName;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public boolean isJtDetailPopUp() {
        return jtDetailPopUp;
    }

    public void setJtDetailPopUp(boolean jtDetailPopUp) {
        this.jtDetailPopUp = jtDetailPopUp;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public JointHolderBean getJointBean() {
        return jointBean;
    }

    public void setJointBean(JointHolderBean jointBean) {
        this.jointBean = jointBean;
    }

    public String getIfscBranchName() {
        return ifscBranchName;
    }

    public void setIfscBranchName(String ifscBranchName) {
        this.ifscBranchName = ifscBranchName;
    }

    public String getDisplaybnkNM() {
        return displaybnkNM;
    }

    public void setDisplaybnkNM(String displaybnkNM) {
        this.displaybnkNM = displaybnkNM;
    }

    public String getNeftBankName() {
        return neftBankName;
    }

    public void setNeftBankName(String neftBankName) {
        this.neftBankName = neftBankName;
    }

    public List<SelectItem> getNeftBankNameList() {
        return neftBankNameList;
    }

    public void setNeftBankNameList(List<SelectItem> neftBankNameList) {
        this.neftBankNameList = neftBankNameList;
    }
}
