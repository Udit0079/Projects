/*
 Document   : IssuecheckBookRegister
 Created on : 27 Oct, 2010, 1:10:42 PM
 Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.controller.inventory;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.inventory.IssueCheqbook;
import com.cbs.web.pojo.inventory.TableChBook;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class IssuecheckBookRegister extends BaseBean {

    private final String jndiHomeName = "InventorySplitAndMergeFacade";
    InventorySplitAndMergeFacadeRemote beanRemote;
    private String acctNo;
    private String agcode;
    private String chqbookcharges;
    private String chqFrom;
    private String chqTo;
    private String noOfLeaves;
    private String cheqSeries;
    private String remarks;
    // private String stAccountNo;
    private String stPartyName;
    private String stJointName;
    private String stOperationMode;
    private String message;
    private String oldAcctNo;
    private List<IssueCheqbook> stocktable;
    private int currentRow;
    private IssueCheqbook currentItem = new IssueCheqbook();
    private List<SelectItem> ddchqbookCharges;
    private List<TableChBook> issueTable;
    private int currentRow1;
    private TableChBook currentItem1 = new TableChBook();
    private boolean flag, flagDisable;
    private boolean Valid;
    //private boolean invisible;
    private boolean seriesDisable;
    private boolean check;
    private boolean checkFrom;
    private boolean checkLeaves;
    private String accountNature;
    private String accountStatus, acNoLen;
    /**
     * Variables taken by Rohit Krishna Gupta On 03/08/2011*
     */
    private String invtClass;
    private List<SelectItem> invtClassList;
    private String invtType;
    private List<SelectItem> invtTypeList;
    private List<SelectItem> chequeSeriesList;
    private List<SelectItem> atParList;
    private String atPar;
    private List<SelectItem> chBookTypeList;
    private String chBookType;

    
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;

    // For Signature View
    String accNo;
    String custId;
    String opMode;
    String openDate;
    String custPanNo;
    String jtName;
    String annualTurnover;
    String accInstruction;
    String profession;
    String riskCategorization;
    String dpLimit;
    String imageData;
    private final String jndiHomeNameTxn = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeNameTxnRem = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    
    public List<SelectItem> getAtParList() {
        return atParList;
    }

    public void setAtParList(List<SelectItem> atParList) {
        this.atParList = atParList;
    }

    public String getAtPar() {
        return atPar;
    }

    public void setAtPar(String atPar) {
        this.atPar = atPar;
    }

    public List<SelectItem> getChBookTypeList() {
        return chBookTypeList;
    }

    public void setChBookTypeList(List<SelectItem> chBookTypeList) {
        this.chBookTypeList = chBookTypeList;
    }

    public String getChBookType() {
        return chBookType;
    }

    public void setChBookType(String chBookType) {
        this.chBookType = chBookType;
    }

    public List<SelectItem> getChequeSeriesList() {
        return chequeSeriesList;
    }

    public void setChequeSeriesList(List<SelectItem> chequeSeriesList) {
        this.chequeSeriesList = chequeSeriesList;
    }

    public boolean isFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
    }

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public String getInvtClass() {
        return invtClass;
    }

    public void setInvtClass(String invtClass) {
        this.invtClass = invtClass;
    }

    public List<SelectItem> getInvtClassList() {
        return invtClassList;
    }

    public void setInvtClassList(List<SelectItem> invtClassList) {
        this.invtClassList = invtClassList;
    }

    public String getInvtType() {
        return invtType;
    }

    public void setInvtType(String invtType) {
        this.invtType = invtType;
    }

    public List<SelectItem> getInvtTypeList() {
        return invtTypeList;
    }

    public void setInvtTypeList(List<SelectItem> invtTypeList) {
        this.invtTypeList = invtTypeList;
    }

    /**
     * *
     */
    public String getChqFrom() {
        return chqFrom;
    }

    public void setChqFrom(String chqFrom) {
        this.chqFrom = chqFrom;
    }

    public String getAccountNature() {
        return accountNature;
    }

    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isCheckFrom() {
        return checkFrom;
    }

    public void setCheckFrom(boolean checkFrom) {
        this.checkFrom = checkFrom;
    }

    public boolean isCheckLeaves() {
        return checkLeaves;
    }

    public void setCheckLeaves(boolean checkLeaves) {
        this.checkLeaves = checkLeaves;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isSeriesDisable() {
        return seriesDisable;
    }

    public void setSeriesDisable(boolean seriesDisable) {
        this.seriesDisable = seriesDisable;
    }

    public boolean isValid() {
        return Valid;
    }

    public void setValid(boolean Valid) {
        this.Valid = Valid;
    }

//    public boolean isInvisible() {
//        return invisible;
//    }
//
//    public void setInvisible(boolean invisible) {
//        this.invisible = invisible;
//    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCheqSeries() {
        return cheqSeries;
    }

    public void setCheqSeries(String cheqSeries) {
        this.cheqSeries = cheqSeries;
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

    public String getChqbookcharges() {
        return chqbookcharges;
    }

    public void setChqbookcharges(String chqbookcharges) {
        this.chqbookcharges = chqbookcharges;
    }

    public IssueCheqbook getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(IssueCheqbook currentItem) {
        this.currentItem = currentItem;
    }

    public TableChBook getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(TableChBook currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<SelectItem> getDdchqbookCharges() {
        return ddchqbookCharges;
    }

    public void setDdchqbookCharges(List<SelectItem> ddchqbookCharges) {
        this.ddchqbookCharges = ddchqbookCharges;
    }

    public List<TableChBook> getIssueTable() {
        return issueTable;
    }

    public void setIssueTable(List<TableChBook> issueTable) {
        this.issueTable = issueTable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStJointName() {
        return stJointName;
    }

    public void setStJointName(String stJointName) {
        this.stJointName = stJointName;
    }

    public String getStOperationMode() {
        return stOperationMode;
    }

    public void setStOperationMode(String stOperationMode) {
        this.stOperationMode = stOperationMode;
    }

    public String getStPartyName() {
        return stPartyName;
    }

    public void setStPartyName(String stPartyName) {
        this.stPartyName = stPartyName;
    }

    public List<IssueCheqbook> getStocktable() {
        return stocktable;
    }

    public void setStocktable(List<IssueCheqbook> stocktable) {
        this.stocktable = stocktable;
    }

    public String getChqTo() {
        return chqTo;
    }

    public void setChqTo(String chqTo) {
        this.chqTo = chqTo;
    }

    public String getNoOfLeaves() {
        return noOfLeaves;
    }

    public void setNoOfLeaves(String noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCustPanNo() {
        return custPanNo;
    }

    public void setCustPanNo(String custPanNo) {
        this.custPanNo = custPanNo;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public IssuecheckBookRegister() {
        try {
            beanRemote = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxnRem);
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            this.setMessage("");
            setAgcode("01");
            ddchqbookCharges = new ArrayList<SelectItem>();
            ddchqbookCharges.add(new SelectItem("Y", "Yes"));
            ddchqbookCharges.add(new SelectItem("N", "No"));
           // setInvisible(false);
            atParList = new ArrayList<SelectItem>();
            atParList.add(new SelectItem("Y", "Yes"));
            atParList.add(new SelectItem("N", "No"));
            setAtPar("N");
            chBookTypeList = new ArrayList<SelectItem>();
            chBookTypeList.add(new SelectItem("bearer", "Bearer"));
            chBookTypeList.add(new SelectItem("order", "Order"));
            setChBookType("order");
            setValid(true);
            setSeriesDisable(false);
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            invtClassListDetail();
            flagDisable = true;
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    /**
     * Code Added by Rohit Krishna Gupta On 03/08/2011*
     */
    public void invtClassListDetail() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.inventoryClassCombo();
            invtClassList = new ArrayList<SelectItem>();
            invtClassList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtClassList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setChequeSeriesList() {
        try {
            this.setMessage("");
            stocktable = new ArrayList<IssueCheqbook>();
            setChqFrom("");
            setChqTo("");
            setNoOfLeaves("");
            chequeSeriesList = new ArrayList<SelectItem>();
            chequeSeriesList.add(new SelectItem("--Select--"));
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Inventory Type.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.chequeSeriesCombo(getOrgnBrCode(), invtClass, invtType);
            if (!resultList.isEmpty()) {
                chequeSeriesList = new ArrayList<SelectItem>();
                chequeSeriesList.add(new SelectItem("--Select--"));
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        chequeSeriesList.add(new SelectItem(ele.get(0).toString()));
                    }
                }
            } else {
                this.setMessage("No Cheque Series available corresponding this Inventory Type");
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setInventoryType() {
        try {
            this.setMessage("");
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.inventoryTypeCombo(invtClass);
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getTableStock() {
        try {
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Inventory Type.");
                return;
            }
            if (this.cheqSeries.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Cheque Series.");
                return;
            }
            stocktable = new ArrayList<IssueCheqbook>();
            List resultLt = new ArrayList();
            resultLt = beanRemote.gridLoadForStockDetail(getOrgnBrCode(), this.invtClass, this.invtType, this.cheqSeries);
            int j = 1;
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    IssueCheqbook rd = new IssueCheqbook();
                    rd.setChNofrom(ele.get(0).toString());
                    rd.setChNoTo(ele.get(1).toString());
                    rd.setStockDt(ele.get(2).toString());
                    rd.setSno(j++);
                    stocktable.add(rd);
                }
            } else {
                this.setMessage("Inventory does not exists to issue.");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void select() {
        setRemarks("");
        setChqFrom(currentItem.getChNofrom());
        setChqTo(currentItem.getChNoTo());
        int result = (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) + 1;
        setNoOfLeaves(Integer.toString(result));
    }

    public void setLeaves() {
        if (onblurChecking().equalsIgnoreCase("false")) {
            return;
        }
        check = true;
        checkFrom = true;
        checkLeaves = true;
        this.setMessage("");
        if (chqFrom != null) {
            if (chqTo != null) {
                if (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom) > 0) {
                    int noLeaves = (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) + 1;
                    setNoOfLeaves(Integer.toString(noLeaves));

                } else {

                    this.setMessage("Chq. No. To Must Be Greater Than Chq. No. From !!!");
                    check = false;
                }
                if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) > 100) {
                    this.setMessage("Sorry, Leaves Can't Exceed 100");
                    check = false;
                }

                if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) < 0) {
                    this.setMessage("Please Enter the Cheque Series properly");
                    check = false;
                }

                if (Integer.parseInt(chqFrom) < 0) {
                    this.setMessage("Chq. No. From Can't Be Less than Zero ");
                    checkFrom = false;
                } else if (Integer.parseInt(chqTo) < 0) {
                    this.setMessage("Chq.No. To Can't Be Less than Zero ");
                    check = false;

                } else if (Integer.parseInt(noOfLeaves) < 0) {
                    this.setMessage("No. Of Leaves Can't Be Less than Zero ");
                    checkLeaves = false;
                }
            } else {
                this.setMessage("Please Enter the Chq.No. To");
                check = false;
            }
        } else {
            this.setMessage("Please Enter the Chq. No. From");

        }

    }

    /**
     * ************************ END Select Button
     * functionality***********************
     */
    public void saveBtnAction() {
        try {
            String rs = beanRemote.introducerAcDetail(acctNo);
            if (rs.equals("9")) {
                this.setMessage("This Account has been Closed. You Can't Issue Checkbook to this Account");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("000000")) {
                setAcctNo("");
            }

            if (this.acctNo.equalsIgnoreCase("") || this.acctNo == null) {
                this.setMessage("Please Fill The Account No.");
                return;

            }
            if (onblurChecking().equalsIgnoreCase("false")) {
                return;
            }

            if (chqSeriesValid().equalsIgnoreCase("false")) {
                return;
            }
            if (chqSeriesFrom().equalsIgnoreCase("false")) {
                return;
            }
            String fullAccountNo = acctNo;
            if (fullAccountNo == null || fullAccountNo.equalsIgnoreCase("")) {
                this.setMessage("ACCOUNT NUMBER IS BLANK !");
                return;
            }

            if (!getOrgnBrCode().equalsIgnoreCase(fts.getCurrentBrnCode(acctNo))) {
                this.setMessage("Account number should be of home branch !");
                return;
            }

            if (cheqSeries.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select the cheque series");
                return;
            }

            if (Integer.parseInt(chqTo) <= 0) {
                this.setMessage("Cheq No To Not less Than Zero Or Equal Zero! please fill proper series");
                return;
            }
            if (Integer.parseInt(chqFrom) <= 0) {
                this.setMessage("Cheq No from Not less Than Zero Or Equal Zero!please fill proper series");
                return;
            }
            if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) > 100) {
                this.setMessage("SORRY LEAFS CAN NOT EXCEED 100 !");
                return;
            }

            if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) < 0) {
                this.setMessage("PLEASE ENTER NO OF LEAFS PROPERLY !");
                return;
            }
            if ((Integer.parseInt(chqTo) < Integer.parseInt(chqFrom))) {
                this.setMessage("Chq.No. To Is Greater than Chq. No. From");
                return;
            }
            /**
             * New Addition*
             */
            String accountCode = fts.getAccountCode(acctNo);
            List paramList = fts.getBaseParameter(accountCode);
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String chqFlag = paramVector.get(4).toString();
                if (chqFlag.equalsIgnoreCase("N")) {
                    this.setMessage("You can not issue the cheque book for this account type.");
                    return;
                }
            }
            /**
             * New Addition End*
             */
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = beanRemote.ChqBookIssueSaveUpdation("save", Integer.parseInt(chqFrom), Integer.parseInt(chqTo), cheqSeries, Integer.parseInt(noOfLeaves), 
                    fullAccountNo, accountCode, remarks, getUserName(), ymd.format(new Date()), chqbookcharges, this.invtClass, this.invtType,atPar,chBookType);

            this.setMessage(result);
            getTableStock();
            getTableIssue();
            setChqFrom("0");
            setChqTo("0");
            setNoOfLeaves("0");
            setRemarks("");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void customerDetails() {
        this.imageData = null;
        try {
            this.setMessage("");
            setStPartyName("");

            setStJointName("");

            setStOperationMode("");
            setRemarks("");

            setNoOfLeaves("0");
            setChqTo("0");
            setChqFrom("0");

            this.setCustId("");
            this.setAnnualTurnover("");
            this.setRiskCategorization("");
            this.setCustPanNo("");
            this.setProfession("");
            this.setAccNo("");
            this.setOpMode("");
            this.setOpenDate("");
            this.setJtName("");
            this.setAccInstruction("");
            this.setDpLimit("");

            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                throw new ApplicationException("Please Enter the Account No.");
            }
            if (!oldAcctNo.matches("[0-9a-zA-z]*")) {
                throw new ApplicationException("Please Enter Valid  Account No.");
            }
            //if (oldAcctNo.length() != 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                throw new ApplicationException("Account number is not valid.");
            }
            acctNo = fts.getNewAccountNumber(oldAcctNo);
            if (!getOrgnBrCode().equalsIgnoreCase(fts.getCurrentBrnCode(acctNo))) {
                throw new ApplicationException("Account number should be of home branch !");
            }
            List resultList = beanRemote.getAccountDetails(acctNo);
            if (resultList.isEmpty()) {
                throw new ApplicationException("Account does not exist or not authorized");
            }
            Vector ele = (Vector) resultList.get(0);
            if (ele.get(0).toString().equals("9")) {
                throw new ApplicationException("This Account has been closed");
            }
            //setStAccountNo(ele.get(0).toString());
            setStPartyName(ele.get(1).toString());

            //  String fullJointName = ele.get(3).toString() + " / " + ele.get(4).toString() + " / " + ele.get(5).toString();
            setStJointName(ele.get(3).toString() + " / " + ele.get(4).toString() + " / " + ele.get(5).toString());

            setStOperationMode(ele.get(6).toString());

            getTableIssue();

            getSignatureDetail();

        } catch (Exception e) {
            setAcctNo("");
            setOldAcctNo("");
            setChqFrom("0");

            setChqTo("0");
            setNoOfLeaves("0");

            setRemarks("");
            issueTable = new ArrayList<TableChBook>();
            //setStAccountNo("");
            setStPartyName("");

            setStJointName("");
            setStOperationMode("");
            setMessage(e.getMessage());
        }
    }

   public void getTableIssue() {
        issueTable = new ArrayList<TableChBook>();
        try {
            List resultLt = beanRemote.tableDataIssue(acctNo);
            for (int i = 0; i < resultLt.size(); i++) {
                Vector ele = (Vector) resultLt.get(i);
                TableChBook rd = new TableChBook();
                rd.setAccountNo(ele.get(1).toString());
                rd.setChqSrNo(ele.get(2).toString());

                String date = (ele.get(7).toString());
                String h1 = date.substring(0, 4);
                String h2 = date.substring(4, 6);
                String h3 = date.substring(6, 8);
                String issueDt = h3 + "/" + h2 + "/" + h1;
                rd.setIssueDate(issueDt);
                rd.setChequeNoFrom(ele.get(3).toString());
                rd.setCheqNoTo(ele.get(4).toString());
                rd.setIssuedBy(ele.get(6).toString());
                if (ele.get(5) == null) {
                    rd.setRemarks("");
                } else {
                    rd.setRemarks(ele.get(5).toString());
                }
                rd.setLeaves(ele.get(8).toString());
                rd.setAuthorized(ele.get(9).toString());
                rd.setAtPar(ele.get(15).toString().equals("N") ? "No" : "Yes");
                rd.setChBookType(ele.get(16).toString());
                issueTable.add(rd);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

//    public void deleteHide() {
//        try {
//            setChqFromHide(currentItem1.getChequeNoFrom());
//            setChqToHide(currentItem1.getCheqNoTo());
//            setAcctNoHide(currentItem1.getAccountNo());
//            chqSrHide = currentItem1.getChqSrNo();
//            setInvisible(false);
//        } catch (Exception e) {
//            setMessage(e.getMessage());
//        }
//    }
    public void deleteIssue() {
        try {
            currentItem1.getChequeNoFrom();
            currentItem1.getCheqNoTo();
            currentItem1.getAccountNo();
            currentItem1.getChqSrNo();
            this.setMessage("");
            String result = beanRemote.ChqBookIssueDelete(acctNo, Integer.parseInt(currentItem1.getChequeNoFrom()), Integer.parseInt( currentItem1.getCheqNoTo()), fts.getAccountCode(acctNo), currentItem1.getChqSrNo(), this.invtClass, this.invtType);
            this.setMessage(result);
            getTableIssue();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String chqSeriesValid() {
        this.setMessage("");
        String chqfromCompare;
        String chqToCompare;
        int chqfromComp;
        int chqToComp;
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.cheqSeriesValidation(cheqSeries, getOrgnBrCode());
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    chqfromCompare = ele.get(0).toString();
                    chqToCompare = ele.get(1).toString();
                    chqfromComp = Integer.parseInt(chqfromCompare);
                    chqToComp = Integer.parseInt(chqToCompare);
                    if ((Integer.parseInt(chqFrom) >= chqfromComp && Integer.parseInt(chqFrom) <= chqToComp) || (Integer.parseInt(chqTo) >= chqfromComp && Integer.parseInt(chqTo) <= chqToComp)) {
                        this.setMessage("Cheque Series Already Issued");
                        return "false";
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "true";
    }

    public String chqSeriesFrom() {
        try {
            this.setMessage("");
            String chqfromStr;
            String chqToStr;
            int chqfromInt;
            int chqToInt;
            String fullAccountNo = acctNo;
            List resultList = new ArrayList();
            resultList = beanRemote.cheqFromValidation(fullAccountNo, this.getCheqSeries());
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    chqfromStr = ele.get(0).toString();
                    chqToStr = ele.get(1).toString();
                    chqfromInt = Integer.parseInt(chqfromStr);
                    chqToInt = Integer.parseInt(chqToStr);

                    if ((Integer.parseInt(chqFrom) >= chqfromInt && Integer.parseInt(chqFrom) <= chqToInt) || (Integer.parseInt(chqTo) >= chqfromInt && Integer.parseInt(chqTo) <= chqToInt)) {
                        this.setMessage("Cheque Series Already Issued");
                        return "false";
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "true";
    }

    public String btnExit() {
        try {
            btnRefresh();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }

    public void btnRefresh() {
        try {
            this.setMessage("");
            setOldAcctNo("");
            setAcctNo("");
            setChqFrom("0");
            setChqTo("0");
            setNoOfLeaves("0");
            setRemarks("");
            issueTable = new ArrayList<TableChBook>();
            stocktable = new ArrayList<IssueCheqbook>();
            //setStAccountNo("");
            setStPartyName("");
            setStJointName("");
            setStOperationMode("");
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setCheqSeries("--Select--");
            flagDisable = true;
            this.imageData = null;
            this.setCustId("");
            this.setAnnualTurnover("");
            this.setRiskCategorization("");
            this.setCustPanNo("");
            this.setProfession("");
            this.setAccNo("");
            this.setOpMode("");
            this.setOpenDate("");
            this.setJtName("");
            this.setAccInstruction("");
            this.setDpLimit("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String onblurChecking() {
        try {
            if (chqFrom == null || chqFrom.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Chq. No. From");
                return "false";
            } else {
                if (!chqFrom.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Chq. No. From");
                    return "false";
                }
            }
            if (chqTo == null || chqTo.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Chq.No. To");
                return "false";
            } else {
                if (!chqTo.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Chq.No. To");
                    return "false";
                }
            }
            if (noOfLeaves == null || noOfLeaves.equalsIgnoreCase("")) {
                this.setMessage("Please Fill No. Of Leaves");
                return "false";
            } else {
                if (!noOfLeaves.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in No. Of Leaves");
                    return "false";
                }
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher acno = p.matcher(acctNo);
            if (!acno.matches()) {
                this.setMessage("Please Enter Numeric Value in Account Number");
                return "false";
            }
            if (Integer.parseInt(chqFrom) < 0) {
                this.setMessage("Chq. No. From Can't Be Less than Zero ");
                return "false";
            }
            if (Integer.parseInt(chqTo) < 0) {
                this.setMessage("Chq.No. To Can't Be Less than Zero ");
                return "false";
            }
            if (Integer.parseInt(noOfLeaves) < 0) {
                this.setMessage("No. Of Leaves Can't Be Less than Zero ");
                return "false";
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "true";
    }

    public void getSignatureDetail() {
        try {
            this.imageData = null;
            int Toper;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String signature;
            accNo = this.getAcctNo();

            List list = txnAuthRemote.getDataForSignature(accNo);

            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                jtName = vec.get(0).toString() + " / " + vec.get(1).toString() + " / " + vec.get(2).toString() + " / " + vec.get(3).toString();
                opMode = vec.get(5).toString();
                Toper = Integer.parseInt(opMode);
                if (Toper == 1) {
                    if ((Toper == 1 && !accNo.equalsIgnoreCase("CA")) && (Toper == 1 && !accNo.equalsIgnoreCase("CC"))) {
                        opMode = "Self";
                    } else {
                        opMode = "Self";
                    }
                } else {
                    List list2 = txnAuthRemote.selectForOperationMode(opMode);
                    Vector v2 = (Vector) list2.get(0);
                    this.setOpMode(v2.get(0).toString());

                }

                accInstruction = vec.get(4).toString();
                String accOpenDate = vec.get(6).toString().substring(6) + "/" + vec.get(6).toString().substring(4, 6) + "/" + vec.get(6).toString().substring(0, 4);
                openDate = accOpenDate;
                this.setDpLimit(vec.get(8).toString());
                List<String> custIdList = txnRemote.getCustIdByAccount(accNo);
                if (!custIdList.isEmpty()) {
                    this.setCustId(custIdList.get(0));
                    this.setAnnualTurnover(custIdList.get(1));
                    this.setRiskCategorization(custIdList.get(2));
                    this.setCustPanNo(custIdList.get(3));
                    this.setProfession(custIdList.get(4));
                } else {
                    this.setCustId("");
                    this.setAnnualTurnover("");
                    this.setRiskCategorization("");
                    this.setCustPanNo("");
                    this.setProfession("");
                }
            }
            signature = txnAuthRemote.getSignatureDetails(accNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(accNo);
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (FileNotFoundException e) {
            this.setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
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
}
