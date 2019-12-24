/*
 * CREATED BY     : ROHIT KRISHNA GUPTA
 * CREATION DATE  : 10 NOV 2010
 */
package com.cbs.web.controller.inventory;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.inventory.ChequeMaintinanceRegisterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.inventory.ChequeMaintinanceRegisterGrid;
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
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class ChequeMaintinanceRegister extends BaseBean{

    ChequeMaintinanceRegisterFacadeRemote chqMainReg;
    private String errorMessage;
    private String message;
    private String acctNo;
    private String custName;
    private String jtName1;
    private String jtName2;
    private String oprMode;
    private String balance;
    //private List<SelectItem> debitChgList;
   //private String debitChg;
    private List<SelectItem> statusList;
    private String status;
    private String selectRadioValue;
    private boolean rdoButtonFlag;
    private String option;
    private String chqNoFrom;
    private String chqNoTo;
    private Date chqDate;
    private String amount;
    private String favouring;
    private String suFlag, acNoLen;
    private List<ChequeMaintinanceRegisterGrid> unusedInstGridDetail;
    private List<ChequeMaintinanceRegisterGrid> stopInstGridDetail;
    int currentRow;
    private ChequeMaintinanceRegisterGrid currentItem = new ChequeMaintinanceRegisterGrid();
    String oldAccNo;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOprMode() {
        return oprMode;
    }

    public void setOprMode(String oprMode) {
        this.oprMode = oprMode;
    }

//    public String getDebitChg() {
//        return debitChg;
//    }
//
//    public void setDebitChg(String debitChg) {
//        this.debitChg = debitChg;
//    }
//
//    public List<SelectItem> getDebitChgList() {
//        return debitChgList;
//    }
//
//    public void setDebitChgList(List<SelectItem> debitChgList) {
//        this.debitChgList = debitChgList;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getSelectRadioValue() {
        return selectRadioValue;
    }

    public void setSelectRadioValue(String selectRadioValue) {
        this.selectRadioValue = selectRadioValue;
    }

    public boolean isRdoButtonFlag() {
        return rdoButtonFlag;
    }

    public void setRdoButtonFlag(boolean rdoButtonFlag) {
        this.rdoButtonFlag = rdoButtonFlag;
    }

    public String getChqNoFrom() {
        return chqNoFrom;
    }

    public void setChqNoFrom(String chqNoFrom) {
        this.chqNoFrom = chqNoFrom;
    }

    public String getChqNoTo() {
        return chqNoTo;
    }

    public void setChqNoTo(String chqNoTo) {
        this.chqNoTo = chqNoTo;
    }

    public Date getChqDate() {
        return chqDate;
    }

    public void setChqDate(Date chqDate) {
        this.chqDate = chqDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFavouring() {
        return favouring;
    }

    public void setFavouring(String favouring) {
        this.favouring = favouring;
    }

    public ChequeMaintinanceRegisterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ChequeMaintinanceRegisterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ChequeMaintinanceRegisterGrid> getStopInstGridDetail() {
        return stopInstGridDetail;
    }

    public void setStopInstGridDetail(List<ChequeMaintinanceRegisterGrid> stopInstGridDetail) {
        this.stopInstGridDetail = stopInstGridDetail;
    }

    public List<ChequeMaintinanceRegisterGrid> getUnusedInstGridDetail() {
        return unusedInstGridDetail;
    }

    public void setUnusedInstGridDetail(List<ChequeMaintinanceRegisterGrid> unusedInstGridDetail) {
        this.unusedInstGridDetail = unusedInstGridDetail;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getSuFlag() {
        return suFlag;
    }

    public void setSuFlag(String suFlag) {
        this.suFlag = suFlag;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    String actNo;
    String charges;
    String glheadMisc;

    public ChequeMaintinanceRegister() {
        try {
            chqMainReg = (ChequeMaintinanceRegisterFacadeRemote) ServiceLocator.getInstance().lookup("ChequeMaintinanceRegisterFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.setChqDate(date);
            this.setErrorMessage("");
            this.setMessage("");
            statusList = new ArrayList<SelectItem>();
           // statusList.add(new SelectItem("--Select--"));
            statusList.add(new SelectItem("SP", "STOP PAYMENT"));
           // statusList.add(new SelectItem("O", "OPERATIVE"));
//            debitChgList = new ArrayList<SelectItem>();
//            debitChgList.add(new SelectItem("--Select--"));
//            debitChgList.add(new SelectItem("Yes"));
//            debitChgList.add(new SelectItem("No"));
            this.setRdoButtonFlag(true);
            this.setOption("SINGLE");
            this.setSuFlag("S");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void selectRadioValue(ValueChangeEvent event) {
        HtmlSelectOneRadio radio = (HtmlSelectOneRadio) event.getComponent();
        if (radio.getValue().toString().equalsIgnoreCase("true")) {
            this.setRdoButtonFlag(true);
            this.setOption("SINGLE");
            this.setChqNoTo("");
        } else {
            this.setRdoButtonFlag(false);
            this.setOption("SERIES");
            this.setChqNoTo("");
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcctNo("");
            this.setOldAccNo("");
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setOprMode("");
            this.setBalance("");
           // this.setDebitChg("--Select--");
            this.setStatus("SP");
            this.setAmount("");
            this.setFavouring("");
            Date date = new Date();
            this.setChqDate(date);
            this.setChqNoTo("");
            this.setChqNoFrom("");
            this.setRdoButtonFlag(true);
            this.setSuFlag("S");
            unusedInstGridDetail = new ArrayList<ChequeMaintinanceRegisterGrid>();
            stopInstGridDetail = new ArrayList<ChequeMaintinanceRegisterGrid>();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAccountDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setOprMode("");
            this.setBalance("");
           // this.setDebitChg("--Select--");
            this.setStatus("SP");
            this.setAmount("");
            this.setFavouring("");
            Date date = new Date();
            this.setChqDate(date);
            this.setChqNoTo("");
            this.setChqNoFrom("");
            unusedInstGridDetail = new ArrayList<ChequeMaintinanceRegisterGrid>();
            stopInstGridDetail = new ArrayList<ChequeMaintinanceRegisterGrid>();
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.oldAccNo == null || this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo.length() == 0) {
                this.setErrorMessage("Please enter the account no.");
                return;
            }

            acctNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            if (acctNo.equalsIgnoreCase("A/c number does not exist")) {
                this.setMessage("A/c number does not exist.");
                return;
            } else if (!getOrgnBrCode().equalsIgnoreCase(ftsPostRemote.getCurrentBrnCode(acctNo))) {
                this.setMessage("Unauthorized Account No to proceeds further.");
                return;
            } else {
                setAcctNo(acctNo);
            }
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            List glHeadChkList = new ArrayList();
            List custDetailList = new ArrayList();
            List balanceList = new ArrayList();
            String result = chqMainReg.custStatus(this.acctNo);
            if (result == null) {
                return;
            } else {
                if (result.equalsIgnoreCase("This Account No Does Not Exist") || result.equalsIgnoreCase("This Account Has Been Closed")) {
                    this.setErrorMessage(result);
                    return;
                } else {
                    glHeadChkList = chqMainReg.chkGLHead(ftsPostRemote.getAccountCode(acctNo));
                    if (glHeadChkList.isEmpty()) {
                        this.setErrorMessage("There Is No GLHead For Stop Payment Charges , Please Enter GLHead !!!");
                    } else {
                        for (int i = 0; i < glHeadChkList.size(); i++) {
                            Vector ele = (Vector) glHeadChkList.get(i);
                            charges = ele.get(0).toString();
                            glheadMisc = ele.get(1).toString();
                        }
                    }
                    custDetailList = chqMainReg.custDetail(this.acctNo);
                    if (!custDetailList.isEmpty()) {
                        for (int i = 0; i < custDetailList.size(); i++) {
                            Vector ele = (Vector) custDetailList.get(i);
                            this.setCustName(ele.get(0).toString());
                            this.setJtName1(ele.get(3).toString());
                            this.setJtName2(ele.get(4).toString());
                            this.setOprMode(ele.get(5).toString());
                        }
                    }
                    balanceList = chqMainReg.getAcBalance(this.acctNo);
                    if (!balanceList.isEmpty()) {
                        Vector ele = (Vector) balanceList.get(0);
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        this.setBalance(String.valueOf(formatter.format(new BigDecimal(ele.get(0).toString()).doubleValue())));
                    }
                    resultList = chqMainReg.custChqDetailForFreshChq(this.acctNo);
                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            Vector ele = (Vector) resultList.get(i);
                            ChequeMaintinanceRegisterGrid detail = new ChequeMaintinanceRegisterGrid();
                            detail.setChqNoUnused(ele.get(0).toString());
                            detail.setIssueDtUnused(ele.get(1).toString());
                            unusedInstGridDetail.add(detail);
                        }
                    }
                    resultList1 = chqMainReg.custChqDetailForStopChq(this.acctNo);
                    if (!resultList1.isEmpty()) {
                        for (int i = 0; i < resultList1.size(); i++) {
                            Vector ele = (Vector) resultList1.get(i);
                            ChequeMaintinanceRegisterGrid detail1 = new ChequeMaintinanceRegisterGrid();
                            detail1.setChqNoStop(ele.get(0).toString());
                            detail1.setIssueDtStop(ele.get(1).toString());
                            //detail1.setEnterBy(ele.get(2).toString());
                            stopInstGridDetail.add(detail1);
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.setChqNoFrom(currentItem.getChqNoStop());
            this.setChqDate(sdf.parse(currentItem.getIssueDtStop()));
            this.setSuFlag("U");
            this.setRdoButtonFlag(true);
            this.setOption("SINGLE");
            this.setChqNoTo("");
            this.setStatus("SP");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo.length() == 0) {
                this.setErrorMessage("Please enter the account no.");
                return;
            }
            if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo.length() == 0) {
                this.setErrorMessage("A/C. No Cannot Be Null , Please Enter A/C. No.");
                return;
            }
            if (this.balance == null || this.balance.equalsIgnoreCase("") || this.balance.length() == 0) {
                this.setErrorMessage("Balance Is Blank");
                return;
            }
            Matcher balanceCheck = p.matcher(balance);
            if (!balanceCheck.matches()) {
                this.setErrorMessage("Balance Is Not Valid");
                return;
            }
//            if (this.suFlag.equalsIgnoreCase("S")) {
//                if (this.debitChg.equalsIgnoreCase("--Select--")) {
//                    this.setErrorMessage("Please Select Debit Charges Option.");
//                    return;
//                }
//            } else {
//                this.debitChg = "No";
//            }
            if (this.chqNoFrom.equalsIgnoreCase("") || this.chqNoFrom.length() == 0) {
                this.setErrorMessage("Please Enter Cheque No.");
                return;
            }
            Matcher chqNoFromCheck = p.matcher(chqNoFrom);
            if (!chqNoFromCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Cheque No.");
                return;
            }
            if (this.chqNoFrom.contains(".")) {
                this.setErrorMessage("INVALID CHEQUE NO. PLEASE FILL THE CHEQUE NO. CORRECTLY.");
                return;
            }
            if (option.equalsIgnoreCase("SERIES")) {
                if (this.chqNoTo.equalsIgnoreCase("") || this.chqNoTo.length() == 0) {
                    this.setErrorMessage("Please Enter Cheque No. To");
                    return;
                }
                Matcher chqNoToCheck = p.matcher(chqNoTo);
                if (!chqNoToCheck.matches()) {
                    this.setErrorMessage("Please Enter Valid Cheque No. To");
                    return;
                }
                if (this.chqNoTo.contains(".")) {
                    this.setErrorMessage("INVALID CHEQUE NO TO.PLEASE FILL THE CHEQUE NO CORRECTLY.");
                    return;
                }
            } else {
                chqNoTo = "0";
            }
            String amountTmp = "";
            if (this.amount.equalsIgnoreCase("") || this.amount.length() == 0) {
                amountTmp = "0";
            } else {
                amountTmp = this.amount;
            }
            Matcher amountCheck = p.matcher(amountTmp);
            if (!amountCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Amount.");
                return;
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID AMOUNT.PLEASE FILL THE AMOUNT CORRECTLY.");
                    return;
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("PLEASE FILL THE AMOUNT UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            if (this.chqDate == null) {
                this.setErrorMessage("Please Enter Cheque Date.");
                return;
            }
            if (this.status.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Status.");
                return;
            }
            String debitChg = "Yes";
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = chqMainReg.saveChqMaintinanceDetail(this.acctNo, Long.parseLong(this.chqNoFrom), this.option, Long.parseLong(this.chqNoTo),
                    this.status, this.getUserName(), this.favouring, ymd.format(chqDate), Float.parseFloat(amountTmp), debitChg, Float.parseFloat(this.balance),
                    Float.parseFloat(this.charges), this.glheadMisc, this.getOrgnBrCode(), this.getOrgnBrCode(), this.suFlag);
            if (result == null) {
                this.setErrorMessage("Sorry , Process Not Done !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                } else {
                    this.setMessage(result);
                }
            }
            this.setChqNoFrom("");
            this.setChqNoTo("");
            this.setAmount("");
            this.setFavouring("");
           //this.setDebitChg("--Select--");
            this.setStatus("SP");
            Date dt = new Date();
            this.setChqDate(dt);
            this.setRdoButtonFlag(true);
            this.setSuFlag("S");
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            unusedInstGridDetail = new ArrayList<ChequeMaintinanceRegisterGrid>();
            stopInstGridDetail = new ArrayList<ChequeMaintinanceRegisterGrid>();
            resultList = chqMainReg.custChqDetailForFreshChq(this.acctNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ChequeMaintinanceRegisterGrid detail = new ChequeMaintinanceRegisterGrid();
                    detail.setChqNoUnused(ele.get(0).toString());
                    detail.setIssueDtUnused(ele.get(1).toString());
                    unusedInstGridDetail.add(detail);
                }
            }
            resultList1 = chqMainReg.custChqDetailForStopChq(this.acctNo);
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    ChequeMaintinanceRegisterGrid detail1 = new ChequeMaintinanceRegisterGrid();
                    detail1.setChqNoStop(ele.get(0).toString());
                    detail1.setIssueDtStop(ele.get(1).toString());
                    //detail1.setEnterBy(ele.get(2).toString());
                    stopInstGridDetail.add(detail1);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }
}
