/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.dto.report.DdsTransactionGrid;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
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
import javax.servlet.http.HttpSession;

public class Transaction extends BaseBean {

    private String message;
    private String accountType;
    private String agentCode;
    private String accountNo;
    private String agentName;
    private String name;
    private String receiptNo;
    private String flag;
    private String actypeFocus;
//    private String focusId = "acountNoList";
    private double amount;
    private double totalAmount;
    private boolean disableSaveButton;
    private boolean disableDeleteButton;
    private DdsTransactionGrid ddsGrid;
    private DdsTransactionGrid currentDdsGrid;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> agentCodeList;
    private List<DdsTransactionGrid> ddstransactionGrid;
    private DDSManagementFacadeRemote transactionsBean;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    NumberFormat formatDecimal = new DecimalFormat("0.00");
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String originalAcno = "";
    private String disableAccount;
    private String processBtnValue;
    private String updateDt;

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getProcessBtnValue() {
        return processBtnValue;
    }

    public void setProcessBtnValue(String processBtnValue) {
        this.processBtnValue = processBtnValue;
    }

    public String getDisableAccount() {
        return disableAccount;
    }

    public void setDisableAccount(String disableAccount) {
        this.disableAccount = disableAccount;
    }

    public String getActypeFocus() {
        return actypeFocus;
    }

    public void setActypeFocus(String actypeFocus) {
        this.actypeFocus = actypeFocus;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public DdsTransactionGrid getCurrentDdsGrid() {
        return currentDdsGrid;
    }

    public void setCurrentDdsGrid(DdsTransactionGrid currentDdsGrid) {
        this.currentDdsGrid = currentDdsGrid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DdsTransactionGrid getDdsGrid() {
        return ddsGrid;
    }

    public void setDdsGrid(DdsTransactionGrid ddsGrid) {
        this.ddsGrid = ddsGrid;
    }

    public List<DdsTransactionGrid> getDdstransactionGrid() {
        return ddstransactionGrid;
    }

    public void setDdstransactionGrid(List<DdsTransactionGrid> ddstransactionGrid) {
        this.ddstransactionGrid = ddstransactionGrid;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Transaction() {
        try {
            ddstransactionGrid = new ArrayList<DdsTransactionGrid>();
            accountTypeList = new ArrayList<SelectItem>();
            agentCodeList = new ArrayList<SelectItem>();

            agentCodeList.add(new SelectItem("--SELECT--"));
            accountTypeList.add(new SelectItem("--SELECT--"));

            transactionsBean = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            initialDataLoad();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initialDataLoad() {
        try {
            List acctCodeList = transactionsBean.getAcctType();
            if (!acctCodeList.isEmpty()) {
                for (int i = 0; i < acctCodeList.size(); i++) {
                    Vector acctCodeVector = (Vector) acctCodeList.get(i);
                    accountTypeList.add(new SelectItem(acctCodeVector.get(0).toString(),"("+acctCodeVector.get(0).toString()+")"+acctCodeVector.get(1).toString() ));
                }
            }

            List agCodeList = transactionsBean.getAgents(getOrgnBrCode());
            if (!agCodeList.isEmpty()) {
                for (int i = 0; i < agCodeList.size(); i++) {
                    Vector agCodeVector = (Vector) agCodeList.get(i);
                    agentCodeList.add(new SelectItem(agCodeVector.get(0).toString(),"("+agCodeVector.get(0).toString()+")"+agCodeVector.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void cmbAgCodeLostFocus() {
        this.setMessage("");
        this.disableAccount = "false";
        this.processBtnValue = "Save";
        ddstransactionGrid = new ArrayList<DdsTransactionGrid>();
        try {
            if (this.accountType.equalsIgnoreCase("--SELECT--")) {
                setMessage("Please select the A/c type !");
                return;
            }
            if (this.agentCode.equalsIgnoreCase("--SELECT--")) {
                setMessage("Please select the Agent Code !");
                return;
            }

//            this.setReceiptNo(String.valueOf(Double.parseDouble(transactionsBean.getReceiptNo(this.agentCode, getOrgnBrCode())) + 1));

            this.setReceiptNo(String.valueOf(transactionsBean.getReceiptNo(this.agentCode, getOrgnBrCode())));

//            if (ddstransactionGrid.size() <= 0) {
//                this.setReceiptNo(String.valueOf(Double.parseDouble(transactionsBean.getReceiptNo(agentCode)) + 1));
//            } 

//            else {
//                int flag1 = 0;
//                double maxReceiptNo = 0;
//                for (int i = 0; i < ddstransactionGrid.size(); i++) {
//                    if (ddstransactionGrid.get(i).getAgentCode().equals(agentCode)) {
//                        flag1 = 1;
//                        if (Double.parseDouble(ddstransactionGrid.get(i).getReceiptNo()) > maxReceiptNo) {
//                            maxReceiptNo = Double.parseDouble(ddstransactionGrid.get(i).getReceiptNo());
//                        }
//                    }
//                }
//                if (flag1 == 0) {
//                    this.setReceiptNo(transactionsBean.getReceiptNo(agentCode));
//                } else {
//                    this.setReceiptNo(String.valueOf(maxReceiptNo + 1));
//                }
//            }

            List agentNameList = transactionsBean.getActiveAgentName(this.agentCode, getOrgnBrCode());
            if (agentNameList.isEmpty()) {
                this.setMessage("Agent does not exist.");
                return;
            }

            Vector element = (Vector) agentNameList.get(0);
            this.setAgentName(element.get(0).toString());

            getUnAuthorizedEntry();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getUnAuthorizedEntry() {
        this.setMessage("");
        totalAmount = 0.0;
        try {
            ddstransactionGrid = new ArrayList<DdsTransactionGrid>();
            List<DdsTransactionGrid> agentUnAuthList = transactionsBean.getAgentUnAuthorizedTransaction(getOrgnBrCode(), this.agentCode, this.accountType, this.agentName);
            if (!agentUnAuthList.isEmpty()) {
                this.flag = "true";
                ddstransactionGrid = agentUnAuthList;
                for (int i = 0; i < ddstransactionGrid.size(); i++) {
                    DdsTransactionGrid pojo = ddstransactionGrid.get(i);
                    totalAmount = totalAmount + Double.parseDouble(pojo.getAmount());
                }
            } else {
                this.flag = "false";
                this.totalAmount = 0.0;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void cboAccountLostFocus() {
        this.setMessage("");
        this.originalAcno = "";
        this.processBtnValue = "Save";
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")) {
                this.setMessage("Account no can not be blank !");
                return;
            }
            Matcher accountCheck = p.matcher(this.accountNo);
            if (!accountCheck.matches()) {
                this.setMessage("Please fill numerical value for account no !");
                return;
            }
//            if (!this.accountNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
//                this.setMessage("Account no should be of self branch !");
//                return;
//            }
//            if (!this.accountNo.substring(10).equalsIgnoreCase(this.agentCode)) {
//                this.setMessage("Account no should be of selected agent code !");
//                return;
//            }

            originalAcno = getOrgnBrCode() + this.accountType + ftsRemote.lPading(this.accountNo, 6, "0") + this.agentCode;

            List custNameList = transactionsBean.getCustNamebasedOnAcNo(originalAcno);
            if (!custNameList.isEmpty()) {
                Vector custNameVector = (Vector) custNameList.get(0);
                int accStatus = Integer.parseInt(custNameVector.get(1).toString());
                if (accStatus == 9) {
                    this.setMessage("This account has been closed");
                    this.setAccountNo("");
//                    setFocusId("amountText");
                    return;
                } else {
                    this.setName(custNameVector.get(0).toString());
//                    setFocusId("amountText");
                }
            } else {
                this.originalAcno = "";
                this.setMessage("This account number does not exists !");
//                this.setFocusId("amountText");
                return;
            }
        } catch (Exception e) {
//            this.setFocusId("amountText");
            this.setMessage(e.getMessage());
        }
    }

//    public void amountlostFocus() {
//        this.setMessage("");
//        try {
//            String valResult = valiations();
//            if (!valResult.equalsIgnoreCase("true")) {
//                this.setMessage(valResult);
//                return;
//            }
//            if (!ddstransactionGrid.isEmpty()) {
//                for (int i = 0; i < ddstransactionGrid.size(); i++) {
//                    DdsTransactionGrid dds = ddstransactionGrid.get(i);
//                    if (getReceiptNo().equalsIgnoreCase(dds.getReceiptNo())) {
//                        this.setMessage("Receipt number already exist!!!");
//                        this.setReceiptNo("");
//                        return;
//                    }
//                }
//            }
//            double totAmount = amount + totalAmount;
//            this.setTotalAmount(totAmount);
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            ddsGrid = new DdsTransactionGrid();
//            ddsGrid.setAccountNo(accountNo);
//            ddsGrid.setAgentCode(agentCode);
//            ddsGrid.setAmount(formatDecimal.format(amount));
//            ddsGrid.setDate(formatter.format(new Date()));
//            ddsGrid.setReceiptNo(String.valueOf(new Float(receiptNo).intValue()));
//            ddsGrid.setAgentName(agentName);
//            ddsGrid.setName(name);
//            ddstransactionGrid.add(ddsGrid);
//            Collections.sort(ddstransactionGrid);
//            this.flag = "true";
//            this.setAmount(0.0);
//            this.setReceiptNo(String.valueOf(Double.parseDouble(Collections.max(ddstransactionGrid).getReceiptNo()) + 1));
//            this.setAccountNo("");
//            this.setName("");
//        } catch (Exception e) {
//            this.setMessage(e.getMessage());
//
//        }
//    }
    public void postEntries() {
        this.setMessage("");
        try {
            String valResult = valiations();
            if (!valResult.equalsIgnoreCase("true")) {
                this.setMessage(valResult);
                return;
            }

//            if (ddstransactionGrid.size() <= 0) {
//                this.setMessage("No entries exist for posting!!!");
//                return;
//            }

            String tokenPaid = "";
//            String cashMode = transactionsBean.getCashMode(getOrgnBrCode());
//            if (!cashMode.equalsIgnoreCase("")) {
//                if (cashMode.equalsIgnoreCase("Y")) {
//                    tokenPaid = "N";
//                } else {
//                    tokenPaid = "Y";
//                }
//            } else {
//                this.setMessage("Cash mode status has not been set for your branch.");
//                return;
//            }

            Boolean[] arr = ftsRemote.ddsCashReceivedFlag(getOrgnBrCode());
            tokenPaid = (arr[0] == true) ? "N" : "Y";




//            while (ddstransactionGrid.size() > 0) {
//                DdsTransactionGrid dds = ddstransactionGrid.get(0);
//                String dt = dds.getDate().substring(6, 10) + dds.getDate().substring(3, 5) + dds.getDate().substring(0, 2);
//                String result = transactionsBean.saveDdsTransactions(dds.getAccountNo(), dt, Double.parseDouble(dds.getAmount()), dds.getReceiptNo(), getUserName(), ftsRemote.getRecNo(), tokenPaid);
//                if (result.equalsIgnoreCase("true")) {
//                    ddstransactionGrid.remove(0);
//                } else {
//                    this.setMessage("Entries remaining in the grid do not posted successfully!!!");
//                    return;
//                }
//            }
//            if (ddstransactionGrid.size() <= 0) {
//                refreshAll();
//                this.setMessage("All Records have been Posted successfully");
//            }

            if (this.originalAcno == null || this.originalAcno.equals("") || this.originalAcno.length() != 12) {
                this.setMessage("Account number can not blank.");
                return;
            }

            String result = "";
            if (this.processBtnValue.equalsIgnoreCase("Save")) {
                result = transactionsBean.saveDdsTransactions(this.originalAcno, ymd.format(new Date()), this.amount, this.receiptNo, getUserName(), ftsRemote.getRecNo(), tokenPaid);
                if (result.equalsIgnoreCase("true")) {
                    this.setAgentName("");
                    this.setReceiptNo("");
                    this.setAccountNo("");
                    this.setAmount(0.0);
                    this.setName("");

                    getUnAuthorizedEntry();
                    this.setMessage("Data has been saved successfully.");
                } else {
                    this.setMessage(result);
                    return;
                }
            } else if (this.processBtnValue.equalsIgnoreCase("Update")) {
                result = transactionsBean.updateDDSAccountTransaction(this.originalAcno, updateDt, this.receiptNo, this.amount);
                if (result.equalsIgnoreCase("true")) {
                    this.setAgentName("");
                    this.setReceiptNo("");
                    this.setAccountNo("");
                    this.setAmount(0.0);
                    this.setName("");

                    getUnAuthorizedEntry();
                    this.setMessage("Data has been updated successfully.");
                } else {
                    this.setMessage(result);
                    return;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String valiations() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.accountType.equalsIgnoreCase("--SELECT--")) {
                return "Account type cannot be blank.";
            }
            if (this.agentCode.equalsIgnoreCase("--SELECT--")) {
                return "Agent code cannot be blank.";
            }
            if (this.receiptNo.equalsIgnoreCase("")) {
                return "Receipt no cannot be blank.";
            }
            Matcher receiptCheck = p.matcher(receiptNo);
            if (!receiptCheck.matches()) {
                return "Enter the integral values for receipt no (0-9).";
            }
            if (Double.parseDouble(receiptNo) <= 0) {
                return "Receipt no cannot be negative or zero and should be integral value.";
            }

            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")) {
                return "Please fill account no.";
            }

            Matcher accountCheck = p.matcher(this.accountNo);
            if (!accountCheck.matches()) {
                return "Enter the numeric values for account no (0-9).";
            }

//            if (!this.accountNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
//                return "Account no should be of self branch !";
//            }
//            if (!this.accountNo.substring(10).equalsIgnoreCase(this.agentCode)) {
//                return "Account no should be of selected agent code !";
//            }

//            List custNameList = transactionsBean.getCustNamebasedOnAcNo(getAccountNo());
//            if (custNameList.isEmpty()) {
//                return "This account number does not exists !";
//            }
            if (String.valueOf(amount).equalsIgnoreCase("")) {
                return "Amount field cannot be blank.";
            }

            Matcher amountCheck = p.matcher(String.valueOf(amount));
            if (!amountCheck.matches()) {
                return "Enter valid numeric value for amount.";
            }
            if (amount <= 0) {
                return "Amount field cannot be negative or zero(0).";
            }
            return "true";
        } catch (Exception ex) {
            return "Error in validating numeric value." + ex.getMessage();
        }
    }

    public void setTransactionRowValues() {
        this.setMessage("");
        this.disableAccount = "true";
        this.processBtnValue = "Update";
        try {
//            String ddsAccount = currentDdsGrid.getAccountNo();
//            String ddsDate = ymd.format(formatter.parse(currentDdsGrid.getDate()));
//            String ddsReceiptNo = currentDdsGrid.getReceiptNo();
//
//            String msg = transactionsBean.deleteDDSAccountTransaction(ddsAccount, ddsDate, ddsReceiptNo);
//            if (!msg.equalsIgnoreCase("true")) {
//                this.setMessage(msg);
//                return;
//            }

            this.originalAcno = currentDdsGrid.getAccountNo();
            this.updateDt = ymd.format(formatter.parse(currentDdsGrid.getDate()));

            this.setAccountNo(String.valueOf(Integer.parseInt(currentDdsGrid.getAccountNo().substring(4, 10))));
            this.setAgentCode(currentDdsGrid.getAgentCode());
            this.setAmount(Double.parseDouble(currentDdsGrid.getAmount()));
            this.setReceiptNo(currentDdsGrid.getReceiptNo());
            this.setAgentName(currentDdsGrid.getAgentName());
            this.setName(currentDdsGrid.getName());
            this.setMessage("");
            ddstransactionGrid.remove(currentDdsGrid);
            this.setTotalAmount(totalAmount - (Double.parseDouble(currentDdsGrid.getAmount())));
            if (ddstransactionGrid.size() <= 0) {
                flag = "false";
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteTransactionRows() {
        try {
//            this.setReceiptNo(currentDdsGrid.getReceiptNo());
//            ddstransactionGrid.remove(currentDdsGrid);
//            this.setTotalAmount(totalAmount - (Double.parseDouble(currentDdsGrid.getAmount())));

            String ddsAccount = currentDdsGrid.getAccountNo();
            String ddsDate = ymd.format(formatter.parse(currentDdsGrid.getDate()));
            String ddsReceiptNo = currentDdsGrid.getReceiptNo();

            String msg = transactionsBean.deleteDDSAccountTransaction(ddsAccount, ddsDate, ddsReceiptNo);
            if (msg.equalsIgnoreCase("true")) {
                getUnAuthorizedEntry();
                this.setMessage("Data has been deleted successfully.");
            } else {
                this.setMessage(msg);
                return;
            }
            if (ddstransactionGrid.size() <= 0) {
                refreshAll();
                flag = "false";
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        this.setMessage("");
        this.setAccountType("0");
        this.setAgentCode("0");
        this.setAgentName("");
        this.setReceiptNo("");
        this.setAccountNo("");
        this.setAmount(0.0);
        this.setName("");
        this.actypeFocus = "true";
        this.originalAcno = "";
        this.disableAccount = "false";
        this.processBtnValue = "Save";
    }

    public void refreshAll() {
        this.setMessage("");
        this.setAccountType("0");
        this.setAgentCode("0");
        this.setAgentName("");
        this.setReceiptNo("");
        this.setAccountNo("");
        this.setAmount(0.0);
        this.setName("");
        this.setTotalAmount(0.0);
        this.ddstransactionGrid.clear();
        this.actypeFocus = "true";
        this.originalAcno = "";
        this.disableAccount = "false";
        this.processBtnValue = "Save";
    }

    public void showTransactionForm() {
        try {
            getHttpResponse().sendRedirect("/cbs-jsp/faces/pages/txn/Transactions.jsp");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void showAuthForm() {
        try {
            getHttpResponse().sendRedirect("/cbs-jsp/faces/pages/txn/CashDeposit.jsp");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitBtnAction() {
        refresh();
        ddstransactionGrid.clear();
        this.setTotalAmount(0.0);
        return "case1";
    }

    public void printReport() {
        try {
            if (ddstransactionGrid == null || ddstransactionGrid.isEmpty()) {
                this.setMessage("No Data To Print !!");
            } else {
                this.setMessage("");
                try {
                    Vector ele = transactionsBean.getBranchNameandAddress(getOrgnBrCode());
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ddstransactionGrid", ddstransactionGrid);
                    if (ele.get(0) != null) {
                        httpSession.setAttribute("bankName", ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        httpSession.setAttribute("branchAddress", ele.get(1).toString());
                    }
                } catch (Exception e) {
                    this.setMessage(e.getMessage());
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
