/*
Document   : AcctEnquery
Created on : 1 Nov, 2010, 2:58:09 PM
Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.misc.CustenqJointName;
import com.cbs.web.pojo.misc.CustTransactionFile;
import com.cbs.web.pojo.misc.AccountEnq;
import com.cbs.web.pojo.misc.CustomerDetailAcct;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class AcctEnquery extends BaseBean{

    AcctEnqueryFacadeRemote remoteObject;
    private List<SelectItem> branchList;
    private String oldAcctNo, acNoLen;
    private String message;
    private String modeOfInquery;
    private String accountNo;
    private String jointName1;
    private String jointName2;
    private String jointName3;
    private String jointName4;
    private String customerName;
    private String address;
    private String contactNo;
    private BigDecimal balance;
    private String daughterOF;
    private String showMode;
    private List<SelectItem> ddModeOfInq;
    private List<AccountEnq> accountWiseTable;
    private List<SelectItem> ddAccountType;
    private String acctNo;
    private String agcode;
    private String stAcctNo;
    private List<CustomerDetailAcct> custDetailtable;
    private int currentRow5;
    private CustomerDetailAcct currentItem5 = new CustomerDetailAcct();
    private List<SelectItem> statusDd;
    private List<SelectItem> nameWiseDd;
    private String statusVariable;
    private String branch;
    private String acctTypeNamewise;
    private String nameMode;
    private String name;
    private String tmpAcountNo;
    private List<CustenqJointName> jointNametable;
    private String acctNoBalancing;
    private String custNameBalancing;
    private BigDecimal balanceBalancing;
    private boolean flag;
    private boolean visible;
    private List<CustTransactionFile> clearingTable;
    private boolean disableValue;
    private TransactionManagementFacadeRemote transRemote;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    BigDecimal openBalance;
    private BigDecimal presentBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public boolean isDisableValue() {
        return disableValue;
    }

    public void setDisableValue(boolean disableValue) {
        this.disableValue = disableValue;
    }

    public List<CustTransactionFile> getClearingTable() {
        return clearingTable;
    }

    public void setClearingTable(List<CustTransactionFile> clearingTable) {
        this.clearingTable = clearingTable;
    }

    public String getTmpAcountNo() {
        return tmpAcountNo;
    }

    public void setTmpAcountNo(String tmpAcountNo) {
        this.tmpAcountNo = tmpAcountNo;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getAcctNoBalancing() {
        return acctNoBalancing;
    }

    public void setAcctNoBalancing(String acctNoBalancing) {
        this.acctNoBalancing = acctNoBalancing;
    }

    public BigDecimal getBalanceBalancing() {
        return balanceBalancing;
    }

    public void setBalanceBalancing(BigDecimal balanceBalancing) {
        this.balanceBalancing = balanceBalancing;
    }

    public String getCustNameBalancing() {
        return custNameBalancing;
    }

    public void setCustNameBalancing(String custNameBalancing) {
        this.custNameBalancing = custNameBalancing;
    }

    public List<CustenqJointName> getJointNametable() {
        return jointNametable;
    }

    public void setJointNametable(List<CustenqJointName> jointNametable) {
        this.jointNametable = jointNametable;
    }

    public String getAcctTypeNamewise() {
        return acctTypeNamewise;
    }

    public void setAcctTypeNamewise(String acctTypeNamewise) {
        this.acctTypeNamewise = acctTypeNamewise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMode() {
        return nameMode;
    }

    public void setNameMode(String nameMode) {
        this.nameMode = nameMode;
    }

    public String getStatusVariable() {
        return statusVariable;
    }

    public void setStatusVariable(String statusVariable) {
        this.statusVariable = statusVariable;
    }

    public List<SelectItem> getNameWiseDd() {
        return nameWiseDd;
    }

    public void setNameWiseDd(List<SelectItem> nameWiseDd) {
        this.nameWiseDd = nameWiseDd;
    }

    public List<SelectItem> getStatusDd() {
        return statusDd;
    }

    public void setStatusDd(List<SelectItem> statusDd) {
        this.statusDd = statusDd;
    }

    public String getShowMode() {
        return showMode;
    }

    public void setShowMode(String showMode) {
        this.showMode = showMode;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAgcode() {
        return agcode;
    }

    public void setAgcode(String agcode) {
        this.agcode = agcode;
    }

    public List<SelectItem> getDdAccountType() {
        return ddAccountType;
    }

    public void setDdAccountType(List<SelectItem> ddAccountType) {
        this.ddAccountType = ddAccountType;
    }

    public String getStAcctNo() {
        return stAcctNo;
    }

    public void setStAcctNo(String stAcctNo) {
        this.stAcctNo = stAcctNo;
    }

    public List<AccountEnq> getAccountWiseTable() {
        return accountWiseTable;
    }

    public void setAccountWiseTable(List<AccountEnq> accountWiseTable) {
        this.accountWiseTable = accountWiseTable;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CustomerDetailAcct getCurrentItem5() {
        return currentItem5;
    }

    public void setCurrentItem5(CustomerDetailAcct currentItem5) {
        this.currentItem5 = currentItem5;
    }

    public int getCurrentRow5() {
        return currentRow5;
    }

    public void setCurrentRow5(int currentRow5) {
        this.currentRow5 = currentRow5;
    }

    public List<CustomerDetailAcct> getCustDetailtable() {
        return custDetailtable;
    }

    public void setCustDetailtable(List<CustomerDetailAcct> custDetailtable) {
        this.custDetailtable = custDetailtable;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDaughterOF() {
        return daughterOF;
    }

    public void setDaughterOF(String daughterOF) {
        this.daughterOF = daughterOF;
    }

    public String getJointName1() {
        return jointName1;
    }

    public void setJointName1(String jointName1) {
        this.jointName1 = jointName1;
    }

    public String getJointName2() {
        return jointName2;
    }

    public void setJointName2(String jointName2) {
        this.jointName2 = jointName2;
    }

    public String getJointName3() {
        return jointName3;
    }

    public void setJointName3(String jointName3) {
        this.jointName3 = jointName3;
    }

    public String getJointName4() {
        return jointName4;
    }

    public void setJointName4(String jointName4) {
        this.jointName4 = jointName4;
    }

    public String getModeOfInquery() {
        return modeOfInquery;
    }

    public void setModeOfInquery(String modeOfInquery) {
        this.modeOfInquery = modeOfInquery;
    }

    public List<SelectItem> getDdModeOfInq() {
        return ddModeOfInq;
    }

    public void setDdModeOfInq(List<SelectItem> ddModeOfInq) {
        this.ddModeOfInq = ddModeOfInq;
    }

    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TxnDetailBean> getTxnDetailList() {
        return txnDetailList;
    }

    public void setTxnDetailList(List<TxnDetailBean> txnDetailList) {
        this.txnDetailList = txnDetailList;
    }

    public List<TxnDetailBean> getTxnDetailUnAuthList() {
        return txnDetailUnAuthList;
    }

    public void setTxnDetailUnAuthList(List<TxnDetailBean> txnDetailUnAuthList) {
        this.txnDetailUnAuthList = txnDetailUnAuthList;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getPresentBalance() {
        return presentBalance;
    }

    public void setPresentBalance(BigDecimal presentBalance) {
        this.presentBalance = presentBalance;
    }

    public TxnDetailBean getTxnDetailGrid() {
        return txnDetailGrid;
    }

    public void setTxnDetailGrid(TxnDetailBean txnDetailGrid) {
        this.txnDetailGrid = txnDetailGrid;
    }
        
    public TxnDetailBean getTxnDetailUnAuthGrid() {
        return txnDetailUnAuthGrid;
    }

    public void setTxnDetailUnAuthGrid(TxnDetailBean txnDetailUnAuthGrid) {
        this.txnDetailUnAuthGrid = txnDetailUnAuthGrid;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public AcctEnquery() {
        try {
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            transRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            accountWiseTable = new ArrayList<AccountEnq>();
            clearingTable = new ArrayList<CustTransactionFile>();
            jointNametable = new ArrayList<CustenqJointName>();
            custDetailtable = new ArrayList<CustomerDetailAcct>();
            ddInquery();
            getDropDownList();
            ddByName();
            ddStatus();
            setAgcode("01");
            setFlag(true);
            setDisableValue(true);
            //setVisible(true);
            this.setMessage("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void ddInquery() {
        ddModeOfInq = new ArrayList<SelectItem>();
        ddModeOfInq.add(new SelectItem("0", "--Select--"));
        ddModeOfInq.add(new SelectItem("1", "Customer Account Wise Query"));
        ddModeOfInq.add(new SelectItem("2", "Customer Name Wise Query"));


    }

    public void mode() {
        if (modeOfInquery.equalsIgnoreCase("1")) {
            setShowMode("Customer Account Wise Query");
            setAcctNoBalancing("");
            setCustNameBalancing("");
            setBalanceBalancing(new BigDecimal("0.00"));
            setMessage("");
            setAccountNo("");
            setName("");
            if (clearingTable.size() > 0 || jointNametable.size() > 0 || custDetailtable.size() > 0) {
                clearingTable.clear();
                jointNametable.clear();
                custDetailtable.clear();
            }

        } else if (modeOfInquery.equalsIgnoreCase("2")) {
            setShowMode("Customer Name Wise Query");
            setJointName1("");
            setJointName2("");
            setJointName3("");
            setJointName4("");
            setCustomerName("");
            setAddress("");
            setContactNo("");
            setDaughterOF("");
            setBalance(new BigDecimal("0.00"));
            setStAcctNo("");
            setAccountNo("");
            setAcctNo("");
            setMessage("");
            setOldAcctNo("");
            if (accountWiseTable.size() > 0) {
                accountWiseTable.clear();
            }
        } else if (modeOfInquery.equalsIgnoreCase("0")) {
            setShowMode("");
            setAccountNo("");
        }
    }

    public void ddStatus() {
        statusDd = new ArrayList<SelectItem>();
        statusDd.add(new SelectItem("Active"));
        statusDd.add(new SelectItem("Close"));
    }

    public void ddByName() {
        nameWiseDd = new ArrayList<SelectItem>();
        nameWiseDd.add(new SelectItem("First"));
        nameWiseDd.add(new SelectItem("Last"));
    }

    public void resetValue() {
        setJointName1("");
        setJointName2("");
        setJointName3("");
        setJointName4("");
        setCustomerName("");
        setAddress("");
        setContactNo("");
        setDaughterOF("");
        setBalance(new BigDecimal("0.00"));
        setAccountNo("");
        accountWiseTable.clear();

    }

    public void getDropDownList() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.ddStatus();
            ddAccountType = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    ddAccountType.add(new SelectItem(ee.toString()));
                }
            }
            resultList = remoteObject.branchList();
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("ALL","ALL"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                branchList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void detailAcctWise() {
         try {
        setJointName1("");
        setJointName2("");
        setJointName3("");
        setJointName4("");
        setCustomerName("");
        setAddress("");
        setContactNo("");
        setDaughterOF("");
        setBalance(new BigDecimal("0.00"));
        setAccountNo("");
        setStAcctNo("");
        setMessage("");
        accountWiseTable = new ArrayList<AccountEnq>();
        
        if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setMessage("Please Enter the Account No.");             
                return;
            }
            if (!oldAcctNo.matches("[0-9a-zA-z]*")) {                  
                    setMessage("Please Enter Valid  Account No.");
                    return ;
                }
            //if (oldAcctNo.length() != 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Account number is not valid.");
                return;
            }
            acctNo = fts.getNewAccountNumber(oldAcctNo);
            setAcctNo(acctNo); 
            setStAcctNo(acctNo);
        acNature();
        if (message.equalsIgnoreCase("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT")) {
            setMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT");
            return;
        }

        if (acctNo.equalsIgnoreCase("") || acctNo == null) {
            setMessage("Please Fill Account Number");
            return;
        }
        if (acctNo.length() < 12) {
            setMessage("Please Fill 12 Digit Account Number");
            return;
        } else {
            accountDetails();
            getTableAccountWise();
            accountStatus();
            checkBalanceAccountWise();
        }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void checkBalanceAccountWise() {
        try {
            Double result = remoteObject.AccountWiseBalanceCheck(acctNo);
            setBalance(new BigDecimal(result));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void accountStatus() {
        try {
            String result = remoteObject.AccountStatus(acctNo);
            if (result != null) {
                if (result.equalsIgnoreCase("Account No. Does Not Exist")) {
                    setAccountNo("");
                }
            }
            this.setMessage(result);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void acNature() {
        try {
            if (acctNo.equalsIgnoreCase("") || acctNo == null) {
                setMessage("Please Fill Account Number");
                return;
            }
            if (acctNo.length() < 12) {
                setMessage("Please Fill 12 Digit Account Number");
                return;
            } else {
                String resultList;
                resultList = remoteObject.acNature(acctNo);
                this.setMessage(resultList);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void getTableAccountWise() {
        accountWiseTable = new ArrayList<AccountEnq>();
        try {
            List resultLt = new ArrayList();
            resultLt = remoteObject.tableAccountWise(acctNo);
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    AccountEnq rd = new AccountEnq();
                    rd.setTxninstNo(ele.get(0).toString());
                    rd.setTxninStmt(Float.parseFloat(ele.get(1).toString()));
                    rd.setDate(ele.get(2).toString());
                    accountWiseTable.add(rd);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void accountDetails() {
        this.setMessage("");
        try {
            acNature();
            if ((acctNo.length() == 12) && (message.equalsIgnoreCase("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT"))) {
                setMessage("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT");
                return;
            }
            if (acctNo.equalsIgnoreCase("") || acctNo == null) {
                setMessage("Please Fill Account Number");
                return;
            }
            if (acctNo.length() < 12) {
                setMessage("Please Fill 12 Digit Account Number");
                return;
            } else {
                String acctType = fts.getAccountCode(acctNo);
                agcode = acctNo.substring(10, 12);
                setAccountNo(acctNo);
                List resultList = new ArrayList();
                resultList = remoteObject.CustomerDetail(acctNo, acctType, agcode);
                if (resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);

                        if (ele.get(0) == null || ele.get(0).toString().equalsIgnoreCase("")) {
                            setJointName1("");

                        } else {
                            setJointName1(ele.get(0).toString());
                        }

                        if (ele.get(1) == null || ele.get(1).toString().equalsIgnoreCase("")) {
                            setJointName2("");
                        } else {
                            setJointName2(ele.get(1).toString());

                        }
                        if (ele.get(2) == null || ele.get(2).toString().equalsIgnoreCase("")) {
                            setJointName3("");
                        } else {
                            setJointName3(ele.get(2).toString());
                        }
                        if (ele.get(3) == null || ele.get(3).toString().equalsIgnoreCase("")) {
                            setJointName4("");
                        } else {
                            setJointName4(ele.get(2).toString());
                        }
                        if (ele.get(4) == null || ele.get(4).toString().equalsIgnoreCase("")) {
                            setCustomerName("");
                        } else {
                            setCustomerName(ele.get(4).toString());
                            setStAcctNo(ele.get(4).toString());
                        }
                        if (ele.get(5) == null || ele.get(5).toString().equalsIgnoreCase("")) {
                            setAddress("");
                        } else {
                            setAddress(ele.get(5).toString());
                        }
                        if (ele.get(6) == null || ele.get(6).toString().equalsIgnoreCase("")) {
                            setContactNo("");
                        } else {
                            setContactNo(ele.get(6).toString());
                        }
                        if (ele.get(7) == null || ele.get(7).toString().equalsIgnoreCase("")) {
                            setDaughterOF("");
                        } else {
                            setDaughterOF(ele.get(7).toString());
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Cust Name"));
        currentRow5 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (CustomerDetailAcct item5 : custDetailtable) {
            if (item5.getCustname().equals(accNo)) {
                currentItem5 = item5;
                break;
            }
        }
    }

    public void getTableNameWiseCustomerDetails() {
        setMessage("");

        custDetailtable = new ArrayList<CustomerDetailAcct>();
        try {
            if (name.equalsIgnoreCase("") || name == null) {
                setMessage("Please Fill the Correct Name");
                setAcctNoBalancing("");
                setCustNameBalancing("");
                setBalanceBalancing(new BigDecimal("0.00"));
                if (clearingTable.size() > 0 || jointNametable.size() > 0 || custDetailtable.size() > 0) {
                    clearingTable.clear();
                    jointNametable.clear();
                    custDetailtable.clear();
                }
            } else {
                if (clearingTable.size() > 0 || jointNametable.size() > 0 || custDetailtable.size() > 0) {
                    clearingTable.clear();
                    jointNametable.clear();
                    custDetailtable.clear();
                }
                setAcctNoBalancing("");
                setCustNameBalancing("");
                setBalanceBalancing(new BigDecimal("0.00"));

                List resultLt = remoteObject.nameWiseEnquery(getUserName(), statusVariable, acctTypeNamewise, nameMode, name, getOrgnBrCode(),branch);
                if (!resultLt.isEmpty()) {
                    for (int i = 0; i < resultLt.size(); i++) {
                        Vector ele = (Vector) resultLt.get(i);
                        CustomerDetailAcct rd = new CustomerDetailAcct();
                        rd.setAcno(ele.get(0).toString());
                        rd.setCustname(ele.get(1).toString());
                        rd.setCraddress(ele.get(2).toString());
                        if (ele.get(7) != null) {
                            rd.setPhoneno(ele.get(7).toString());
                        }
                        if (ele.get(8) != null) {
                            rd.setFname(ele.get(8).toString());
                        }
                        custDetailtable.add(rd);
                    }

                } else {
                    setMessage("No Detail Exist Corresponding This Name");
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void select() {
        this.setMessage("");
        setAcctNoBalancing("");
        setCustNameBalancing("");
        setBalanceBalancing(new BigDecimal("0.00"));
        setAcctNoBalancing(currentItem5.getAcno());
        setCustNameBalancing(currentItem5.getCustname());
        getTableNameWiseJointName();
        checkBalanceNameWise();
        getTableNameWiseOutwordClearing();
        accountNo = currentItem5.getAcno();


    }

    public void acctViewAuthUnAuth() {
//        String array[] = null;
//        String array1[] = null;
//        int i, j, k, l, m, n, o, p, q;

        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();
            if (this.accountNo == null) {
                setMessage("Please Fill/Select Account No.");
                return;
            }
            if (this.accountNo.equals("")) {
                setMessage("Please Fill/Select Account No.");
                return;
            }


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar now = Calendar.getInstance();
            now.roll(Calendar.MONTH, false);
            String oneMonthBeforeDate = sdf.format(now.getTime());


            String openBal = transRemote.getOpeningBalF4(accountNo, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {

                this.setOpenBalance(new BigDecimal(openBal));
            }

            String prsntBal = transRemote.getOpeningBalF4(accountNo, getTodayDate());
            if (!prsntBal.equals("FALSE")) {

                this.setPresentBalance(new BigDecimal(prsntBal));
            }

           // BigDecimal netAmount = this.getPresentBalance();

            txnDetailList = transRemote.accViewAuth(accountNo, getTodayDate(), getOrgnBrCode());
            txnDetailUnAuthList = transRemote.accViewUnauth(accountNo, getTodayDate(), getOrgnBrCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTableNameWiseJointName() {
        jointNametable = new ArrayList<CustenqJointName>();
        try {
            List resultLt = new ArrayList();
            resultLt = remoteObject.nameWiseEnqueryJointName(acctNoBalancing);
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    CustenqJointName rd = new CustenqJointName();
                    if (!ele.get(2).toString().equalsIgnoreCase("") || ele.get(2).toString().length() != 0) {
                        rd.setJtname1(ele.get(2).toString());
                        rd.setJtname2(ele.get(3).toString());
                        rd.setJtname3(ele.get(4).toString());
                        rd.setJtname4(ele.get(5).toString());
                        jointNametable.add(rd);
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void checkBalanceNameWise() {
        try {
            Double result = remoteObject.AccountWiseBalanceCheck(acctNoBalancing);
            setBalanceBalancing(new BigDecimal(result));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableNameWiseOutwordClearing() {
        clearingTable = new ArrayList<CustTransactionFile>();
        try {
            List resultLt = new ArrayList();
            resultLt = remoteObject.nameWiseOutwordClearing(acctNoBalancing);
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    CustTransactionFile rd = new CustTransactionFile();
                    rd.setTxnNum(ele.get(0).toString());
                    rd.setTxnAmt(Float.parseFloat(ele.get(1).toString()));
                    rd.setTxnDt(ele.get(2).toString());
                    clearingTable.add(rd);
                }

            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void btnRefAct() {
        if (modeOfInquery.equalsIgnoreCase("1")) {
            setShowMode("Customer Account Wise Query");
            this.setMessage("");
            setJointName1("");
            setJointName2("");
            setJointName3("");
            setJointName4("");
            setCustomerName("");
            setAddress("");
            setContactNo("");
            setDaughterOF("");
            setBalance(new BigDecimal("0.00"));
            setStAcctNo("");
            setAccountNo("");
            setAcctNo("");
            setName("");
            setAccountNo("");
            setOldAcctNo("");
            if (accountWiseTable.size() > 0) {
                accountWiseTable.clear();
            }
        } else if (modeOfInquery.equalsIgnoreCase("2")) {
            setShowMode("Customer Name Wise Query");
            this.setMessage("");
            setCustomerName("");
            setName("");
            setAcctNoBalancing("");
            setCustNameBalancing("");
            setAccountNo("");
            setBalanceBalancing(new BigDecimal("0.00"));
            if (clearingTable.size() > 0 || jointNametable.size() > 0 || custDetailtable.size() > 0) {
                clearingTable.clear();
                jointNametable.clear();
                custDetailtable.clear();
            }

        } else if (modeOfInquery.equalsIgnoreCase("0")) {
            setShowMode("");
            setAccountNo("");
        }
    }

    public String btnExit() {
        btnRefAct();
        return "case1";
    }
}
