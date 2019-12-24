package com.cbs.web.controller.ho.deadstock;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.web.pojo.txn.GLHeadGrid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.dto.DeadstockTranTable;
import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.dto.ItemMasterTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author ANKIT VERMA
 */
public final class DsPurchaseTransfer extends BaseBean {

    private String acno;
    private String oldAcno, acNoLen;
    private String msg;
    private boolean flag;
    private String accountName;
    private String originBranch;
    private List<SelectItem> branchList;
    private List<SelectItem> distBranchList;
    private String destBranch;
    private String details;
    private List<SelectItem> tranTypeList;
    private String tranMode;
    private List<SelectItem> transactionModeList;
    private String type;
    private String tranBy;
    private List<SelectItem> tranByList;
    private String seqNo;
    private String advise;
    private List<SelectItem> adviseList;
    private double drAmt;
    private boolean boolSaveBatch;
    public String itemCode;
    public List<SelectItem> itemCodeList;
    private String purMessage;
    private String itemCd;
    private String itemName;
    private String instNo;
    private Date instDate;
    private String invoiceNo;
    private Date invoiceDate;
    private double rate;
    private int itemQty;
    private String tranTotal;
    private double crAmt;
    private int transferedQty;
    private double transferedQtyRate;
    private String tranRef;
    private String seqenceNo;
    private String year;
    private String inventorySno;
    private String manufNo;
    private String favorOf;
    private String custName;
    private String labelCrDr;
    private boolean disableFavorOf;
    private boolean disableCustName;
    private Date refDate;
    private String writemsg;
    private String issuemsg;
    private BigDecimal poComm;
    private String purflag;
    private String Tempbd;
    private String writeFlag;
    private String mydate1;
    private long totalQty;
    private int currentRow;
    private int currentRow1;
    private boolean disableQty;
    private double totAmt = 0.0;
    double commAmt = 0.0;
    double taxAmt = 0.0;
    String code;
    private boolean disableIssueSave;
    int quantity;
    private String amount;
    private String issueFlag;
    double itemBookValue = 0.0;
    double itemSaleValue = 0.0;
    private boolean disableOriginBranch;
    private boolean disableDestBranch;
    private boolean disableMainSave;
    private boolean disableSeqNo;
    private boolean disableYear;
    private boolean disableInstDate;
    private boolean disableInstNo;
    private String modeOfTrns;
    private String resBrancCode;
    private List taxList = new ArrayList();
    private List taxListGLHead = new ArrayList();
    private GLHeadGrid glHeadGrid;
    private String totalAmountInTransfer;
    private List<GLHeadGrid> listForF6;
    private List<ItemMasterTable> itemTable;
    List<DeadstockTranTable> batchTable1;
    List<DeadstockTranTable> batchTable2;
    private List<DeadstockTranTable> batchTable;
    private DeadstockTranTable currentItem1 = new DeadstockTranTable();
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    private int qty = 0;
    Validator validator;
    private boolean disableAmount;
    private BigDecimal balan;
    private FtsPostingMgmtFacadeRemote ftsPost43CBSRemote;
    private DeadstockFacadeRemote deadstockFacadeRemote;
    private TransactionManagementFacadeRemote transactionsRemote;
    private InterBranchTxnFacadeRemote interFtsRemote;

    public DsPurchaseTransfer() {
        try {
            deadstockFacadeRemote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            ftsPost43CBSRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            transactionsRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            interFtsRemote = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
            this.setAcNoLen(getAcNoLength());
            purflag = "false";
            issueFlag = "false";
            tranTotal = "0.00";
            amount = "0.00";
            totalAmountInTransfer = "0.00";
            setTodayDate(dmyFormat.format(date));
            branchList = new ArrayList<SelectItem>();
            tranByList = new ArrayList<SelectItem>();
            tranByList.add(new SelectItem("0", "Voucher"));
            transactionModeList = new ArrayList<SelectItem>();
            transactionModeList.add(new SelectItem("1", "Purchase"));
            transactionModeList.add(new SelectItem("2", "Transfer"));
            transactionModeList.add(new SelectItem("3", "Write Off"));
            tranTypeList = new ArrayList<SelectItem>();
            tranTypeList.add(new SelectItem("0", "Cr."));
            tranTypeList.add(new SelectItem("1", "Dr."));
            adviseList = new ArrayList<SelectItem>();
            adviseList.add(new SelectItem("AD"));
            adviseList.add(new SelectItem("DD"));
            adviseList.add(new SelectItem("PO"));
            advise = "";
            seqenceNo = "0";
            year = "0";
            instNo = "";
            resBrancCode = "";
            itemCodeList = new ArrayList<SelectItem>();
            distBranchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("--Select--"));
            distBranchList.add(new SelectItem("--Select--"));
            boolSaveBatch = true;
            disableMainSave = true;
            disableSeqNo = true;
            disableYear = true;
            custName = "";
            favorOf = "";
            modeOfTrns = "";
            disableCustName = true;
            disableFavorOf = true;
            disableInstNo = true;
            disableInstDate = true;
            seqNo = "";
            disableQty = true;
            validator = new Validator();
            itemTable = new ArrayList<ItemMasterTable>();
            batchTable = new ArrayList<DeadstockTranTable>();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public boolean isDisableInstDate() {
        return disableInstDate;
    }

    public void setDisableInstDate(boolean disableInstDate) {
        this.disableInstDate = disableInstDate;
    }

    public boolean isDisableInstNo() {
        return disableInstNo;
    }

    public void setDisableInstNo(boolean disableInstNo) {
        this.disableInstNo = disableInstNo;
    }

    public DeadstockTranTable getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(DeadstockTranTable currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public BigDecimal getBalan() {
        return balan;
    }

    public void setBalan(BigDecimal balan) {
        this.balan = balan;
    }

    public String getWritemsg() {
        return writemsg;
    }

    public void setWritemsg(String writemsg) {
        this.writemsg = writemsg;
    }

    public String getWriteFlag() {
        return writeFlag;
    }

    public void setWriteFlag(String writeFlag) {
        this.writeFlag = writeFlag;
    }

    public boolean isDisableCustName() {
        return disableCustName;
    }

    public void setDisableCustName(boolean disableCustName) {
        this.disableCustName = disableCustName;
    }

    public boolean isDisableFavorOf() {
        return disableFavorOf;
    }

    public void setDisableFavorOf(boolean disableFavorOf) {
        this.disableFavorOf = disableFavorOf;
    }

    public String getTempbd() {
        return Tempbd;
    }

    public void setTempbd(String Tempbd) {
        this.Tempbd = Tempbd;
    }

    public boolean isDisableIssueSave() {
        return disableIssueSave;
    }

    public void setDisableIssueSave(boolean disableIssueSave) {
        this.disableIssueSave = disableIssueSave;
    }

    public List<ItemMasterTable> getItemTable() {
        return itemTable;
    }

    public void setItemTable(List<ItemMasterTable> itemTable) {
        this.itemTable = itemTable;
    }

    public boolean isDisableDestBranch() {
        return disableDestBranch;
    }

    public void setDisableDestBranch(boolean disableDestBranch) {
        this.disableDestBranch = disableDestBranch;
    }

    public boolean isDisableOriginBranch() {
        return disableOriginBranch;
    }

    public void setDisableOriginBranch(boolean disableOriginBranch) {
        this.disableOriginBranch = disableOriginBranch;
    }

    public boolean isDisableMainSave() {
        return disableMainSave;
    }

    public void setDisableMainSave(boolean disableMainSave) {
        this.disableMainSave = disableMainSave;
    }

    public String getTranMode() {
        return tranMode;
    }

    public void setTranMode(String tranMode) {
        this.tranMode = tranMode;
    }

    public String getPurflag() {
        return purflag;
    }

    public void setPurflag(String purflag) {
        this.purflag = purflag;
    }

    public int getTransferedQty() {
        return transferedQty;
    }

    public void setTransferedQty(int transferedQty) {
        this.transferedQty = transferedQty;
    }

    public String getInventorySno() {
        return inventorySno;
    }

    public void setInventorySno(String inventorySno) {
        this.inventorySno = inventorySno;
    }

    public String getManufNo() {
        return manufNo;
    }

    public void setManufNo(String manufNo) {
        this.manufNo = manufNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public boolean isBoolSaveBatch() {
        return boolSaveBatch;
    }

    public void setBoolSaveBatch(boolean boolSaveBatch) {
        this.boolSaveBatch = boolSaveBatch;
    }

    public List<DeadstockTranTable> getBatchTable() {
        return batchTable;
    }

    public void setBatchTable(List<DeadstockTranTable> batchTable) {
        this.batchTable = batchTable;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GLHeadGrid> getListForF6() {
        return listForF6;
    }

    public void setListForF6(List<GLHeadGrid> listForF6) {
        this.listForF6 = listForF6;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public List<SelectItem> getAdviseList() {
        return adviseList;
    }

    public void setAdviseList(List<SelectItem> adviseList) {
        this.adviseList = adviseList;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public String getDestBranch() {
        return destBranch;
    }

    public void setDestBranch(String destBranch) {
        this.destBranch = destBranch;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public GLHeadGrid getGlHeadGrid() {
        return glHeadGrid;
    }

    public void setGlHeadGrid(GLHeadGrid glHeadGrid) {
        this.glHeadGrid = glHeadGrid;
    }

    public String getOriginBranch() {
        return originBranch;
    }

    public void setOriginBranch(String originBranch) {
        this.originBranch = originBranch;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getTranBy() {
        return tranBy;
    }

    public void setTranBy(String tranBy) {
        this.tranBy = tranBy;
    }

    public List<SelectItem> getTranByList() {
        return tranByList;
    }

    public void setTranByList(List<SelectItem> tranByList) {
        this.tranByList = tranByList;
    }

    public List<SelectItem> getTransactionModeList() {
        return transactionModeList;
    }

    public void setTransactionModeList(List<SelectItem> transactionModeList) {
        this.transactionModeList = transactionModeList;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public List<SelectItem> getItemCodeList() {
        return itemCodeList;
    }

    public void setItemCodeList(List<SelectItem> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

    public String getPurMessage() {
        return purMessage;
    }

    public void setPurMessage(String purMessage) {
        this.purMessage = purMessage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(String tranRef) {
        this.tranRef = tranRef;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTranTotal() {
        return tranTotal;
    }

    public void setTranTotal(String tranTotal) {
        this.tranTotal = tranTotal;
    }

    public Date getInstDate() {
        return instDate;
    }

    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    public List<SelectItem> getDistBranchList() {
        return distBranchList;
    }

    public void setDistBranchList(List<SelectItem> distBranchList) {
        this.distBranchList = distBranchList;
    }

    public String getIssueFlag() {
        return issueFlag;
    }

    public void setIssueFlag(String issueFlag) {
        this.issueFlag = issueFlag;
    }

    public long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(long totalQty) {
        this.totalQty = totalQty;
    }

    public String getTotalAmountInTransfer() {
        return totalAmountInTransfer;
    }

    public void setTotalAmountInTransfer(String totalAmountInTransfer) {
        this.totalAmountInTransfer = totalAmountInTransfer;
    }

    public String getIssuemsg() {
        return issuemsg;
    }

    public void setIssuemsg(String issuemsg) {
        this.issuemsg = issuemsg;
    }

    public double getTransferedQtyRate() {
        return transferedQtyRate;
    }

    public void setTransferedQtyRate(double transferedQtyRate) {
        this.transferedQtyRate = transferedQtyRate;
    }

    public String getSeqenceNo() {
        return seqenceNo;
    }

    public void setSeqenceNo(String seqenceNo) {
        this.seqenceNo = seqenceNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isDisableSeqNo() {
        return disableSeqNo;
    }

    public void setDisableSeqNo(boolean disableSeqNo) {
        this.disableSeqNo = disableSeqNo;
    }

    public boolean isDisableYear() {
        return disableYear;
    }

    public void setDisableYear(boolean disableYear) {
        this.disableYear = disableYear;
    }

    public String getMydate1() {
        return mydate1;
    }

    public void setMydate1(String mydate1) {
        this.mydate1 = mydate1;
    }

    public BigDecimal getPoComm() {
        return poComm;
    }

    public void setPoComm(BigDecimal poComm) {
        this.poComm = poComm;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFavorOf() {
        return favorOf;
    }

    public void setFavorOf(String favorOf) {
        this.favorOf = favorOf;
    }

    public String getLabelCrDr() {
        return labelCrDr;
    }

    public void setLabelCrDr(String labelCrDr) {
        this.labelCrDr = labelCrDr;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public boolean isDisableAmount() {
        return disableAmount;
    }

    public void setDisableAmount(boolean disableAmount) {
        this.disableAmount = disableAmount;
    }

    public boolean isDisableQty() {
        return disableQty;
    }

    public void setDisableQty(boolean disableQty) {
        this.disableQty = disableQty;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public void onBlurOfAcno() {
        try {
            msg = "";
            seqenceNo = "0";
            year = "0";
            List acctdetails = null;
            if (oldAcno == null || oldAcno.equalsIgnoreCase("")) {
                msg = "Please Enter Account Number.";
                return;
            }
            
            if (!this.oldAcno.equalsIgnoreCase("") && ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                msg = "Please Enter Proper Account Number.";
                return;
            }
            acno = ftsPost43CBSRemote.getNewAccountNumber(oldAcno);
            String acNature = ftsPost43CBSRemote.getAccountNature(acno);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                acctdetails = deadstockFacadeRemote.getAccountDetailsGlhead(acno);
            } else {
                msg = "Please select only GL Account";
                return;
            }
            if (acctdetails == null || acctdetails.isEmpty()) {
                msg = "Account No Does Not exist";
                return;
            }
            Vector vtr = (Vector) acctdetails.get(0);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (vtr.get(3).toString().equalsIgnoreCase("80")) {
                    flag = true;
                } else {
                    flag = false;
                }
                accountName = vtr.get(0).toString();
                List selectReconTdReconCaRecon = transactionsRemote.selectFromReconTdReconCaRecon(acno, CbsAcCodeConstant.GL_ACCNO.getAcctCode());
                if (!selectReconTdReconCaRecon.isEmpty()) {
                    Vector vecReconTdReconCaRecon = (Vector) selectReconTdReconCaRecon.get(0);
                    balan = new BigDecimal(vecReconTdReconCaRecon.get(0).toString());
                    if (Double.parseDouble(balan.toString()) < 0) {
                        labelCrDr = " Dr";
                        balan = balan.abs();
                    } else {
                        labelCrDr = " Cr";
                    }
                }
                // if (accountName.equalsIgnoreCase("SUSPENSE PAYMENT")|| accountName.equalsIgnoreCase("SUNDRY")) {
                if (acno.equalsIgnoreCase(acno.substring(0, 2) + ftsPost43CBSRemote.getAcnoByPurpose("SUSPENSE PAYMENT"))
                        || acno.equalsIgnoreCase(acno.substring(0, 2) + ftsPost43CBSRemote.getAcnoByPurpose("SUNDRY"))) {
                    disableSeqNo = false;
                    disableYear = false;
                } else {
                    disableSeqNo = true;
                    disableYear = true;
                }
                // if (accountName.equalsIgnoreCase("PAY ORDER")) {
                if (acno.equalsIgnoreCase(acno.substring(0, 2) + ftsPost43CBSRemote.getAcnoByPurpose("PO ACCOUNT"))) {
                    disableCustName = false;
                    disableFavorOf = false;
                } else {
                    disableCustName = true;
                    disableFavorOf = true;
                }
                disableMainSave = false;
            } else {
                flag = false;
                accountName = vtr.get(0).toString();
                String acctStatus = vtr.get(2).toString();
                if (Integer.parseInt(acctStatus) == 2) {
                    this.setMsg("Account Has been marked Inoperative");
                } else if (Integer.parseInt(acctStatus) == 3) {
                    this.setMsg("Account Has been marked Suit Filed");
                } else if (Integer.parseInt(acctStatus) == 4) {
                    this.setMsg("Account Has been marked Frozen");
                } else if (Integer.parseInt(acctStatus) == 5) {
                    this.setMsg("Account Has been marked Recalled");
                } else if (Integer.parseInt(acctStatus) == 6) {
                    this.setMsg("Account Has been marked Decreed");
                } else if (Integer.parseInt(acctStatus) == 7) {
                    this.setMsg("Withdrawal is not Allowed in this Account");
                } else if (Integer.parseInt(acctStatus) == 8) {
                    this.setMsg("Account Has been marked Operation Stopped");
                } else if (Integer.parseInt(acctStatus) == 9) {
                    this.setMsg("Account Has been Closed");
                } else if (Integer.parseInt(acctStatus) == 11) {
                    this.setMsg("This Account is SUB STANDARD Account");
                } else if (Integer.parseInt(acctStatus) == 12) {
                    this.setMsg("This Account is DOUBT FUL Account");
                } else if (Integer.parseInt(acctStatus) == 13) {
                    this.setMsg("This Account is LOSS Account");
                } else if (Integer.parseInt(acctStatus) == 14) {
                    this.setMsg("This Account is PROTESTED BILL Account");
                } else {
                    this.setMsg("");
                }
            }
            resBrancCode = ftsPost43CBSRemote.getCurrentBrnCode(acno);
            if (resBrancCode.equalsIgnoreCase("A/C No. does not exist")) {
                setMsg("A/C No. does not exist");
                return;
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getItemList() {
        try {
            itemCodeList = new ArrayList<SelectItem>();
            List resultList = deadstockFacadeRemote.getItemList(getOrgnBrCode(), "P");
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    itemCodeList.add(new SelectItem(ele.get(0)));
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getItemList1() {
        try {
            itemCodeList = new ArrayList<SelectItem>();
            List resultList = deadstockFacadeRemote.getItemList(getOrgnBrCode(), "T");
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    itemCodeList.add(new SelectItem(ele.get(0)));
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void selectGlHeadOnPressF2() {
        try {
            String query = "select AcName,acno from gltable where substring(acno,1,2)='" + getOrgnBrCode() + "' order by AcName";
            List listF6 = transactionsRemote.getDataForF6(query);
            if (!listF6.isEmpty()) {
                this.setMsg("");
                listForF6 = new ArrayList<GLHeadGrid>();
                for (int i = 0; i < listF6.size(); i++) {
                    glHeadGrid = new GLHeadGrid();
                    Vector vecF6 = (Vector) listF6.get(i);
                    glHeadGrid.setAccName(vecF6.get(0).toString());
                    glHeadGrid.setGlhead(vecF6.get(1).toString());
                    listForF6.add(glHeadGrid);
                }
            } else {
                this.setMsg("No Data For GL Head.");
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void onblurType() {
        try {
            String glhead = "";
            glhead = acno.substring(0, 2) + ftsPost43CBSRemote.getAcnoByPurpose("DEAD STOCK");
            //if (getAccountName().equalsIgnoreCase("DEAD STOCK") && (acno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {//&&(acno.substring(0,2).equalsIgnoreCase(getOrgnBrCode()))
            if (acno.equalsIgnoreCase(glhead) && (acno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
                setPurMessage("");
                if (type.equalsIgnoreCase("1") && tranMode.equalsIgnoreCase("1")) {
                    purflag = "true";
                    writeFlag = "false";
                    issueFlag = "false";
                    getItemList();
                } else if (tranMode.equalsIgnoreCase("2")) {
                    if (itemTable != null) {
                        itemTable.clear();
                    }
                    issuemsg = "";
                    transferedQty = 0;
                    totalAmountInTransfer = "0.00";
                    purflag = "false";
                    writeFlag = "false";
                    issueFlag = "true";
                    getItemList1();
                } else if (tranMode.equalsIgnoreCase("3") && type.equalsIgnoreCase("0")) {
                    if (itemTable != null) {
                        itemTable.clear();
                    }
                    transferedQty = 0;
                    totalAmountInTransfer = "0.00";
                    issuemsg = "";
                    purflag = "false";
                    issueFlag = "false";
                    writeFlag = "true";
                    modeOfTrns = tranMode;
                    getItemList1();
                } else {
                    purflag = "false";
                    issueFlag = "false";
                    writeFlag = "false";
                }
            } else {
                purflag = "false";
                issueFlag = "false";
                writeFlag = "false";
            }

        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void btnSave() {
        try {
            String glAcno = "";
            if (originBranch.equalsIgnoreCase("--Select--")) {
                msg = "Please select origin branch";
                return;
            } else if (destBranch.equalsIgnoreCase("--Select--")) {
                msg = "Please select responding branch";
                return;
            } else if (!validator.validateDoublePositive(amount)) {
                msg = "amount is not valid";
                return;
            }
            double profitAmt = 0.0;
            double lossAmt = 0.0;
            int flagForProfit = 0;
            msg = "";
            glAcno = acno.substring(0, 2) + ftsPost43CBSRemote.getAcnoByPurpose("SUNDRY");
            if (itemTable.size() > 0 && tranMode.equalsIgnoreCase("3") && acno.equalsIgnoreCase(glAcno)) {
                for (ItemMasterTable imtObj : itemTable) {
                    if (imtObj.getTranFlag().equalsIgnoreCase("Y")) {
                        itemBookValue += imtObj.getAmtPerUnit();
                        itemSaleValue += imtObj.getItemSaleValue();
                    }
                }
            }
            batchTable2 = new ArrayList<DeadstockTranTable>();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (itemBookValue > itemSaleValue) {
                DeadstockTranTable grid2 = new DeadstockTranTable();
                glAcno = ftsPost43CBSRemote.getAcnoByPurpose("LOSS ON SALE OF BANK ASSET");
                String acno2 = "";
//                List acnoList = deadstockFacadeRemote.getAccountNoAccordingToGlhead("LOSS ON SALE OF BANK ASSET");
//                if (acnoList.size() > 0) {
//                    Vector vec = (Vector) acnoList.get(0);
//                    acno2 = resBrancCode + vec.get(0).toString().substring(2, 10) + "01";
//                }
                acno2 = resBrancCode + glAcno;
                grid2.setAcno(acno2);
                grid2.setTranMode(Integer.parseInt(tranMode));
                grid2.setTranTotal(0.0);
                grid2.setTranType("2");
                grid2.setTranDate(dmyFormat.format(new java.util.Date()));
                grid2.setEnterBy(getUserName());
                grid2.setTranDesc(80);//for deadstock
                grid2.setDetails("Loss on Sale");
                grid2.setDestBrnCode(destBranch.length() < 2 ? "0" + destBranch : destBranch);
                grid2.setOrgBrnCode(originBranch.length() < 2 ? "0" + originBranch : originBranch);
                grid2.setTy(1);
                float rec = ftsPost43CBSRemote.getRecNo();
                grid2.setRecno(rec);
                grid2.setAuth("N");
                grid2.setPayBy(3);
               // grid2.setInstDate("");
                grid2.setAdviseNo("");
                grid2.setBalance(0);
                grid2.setFavourOf("");
               // grid2.setInvoiceDt("");
                grid2.setInvoiceNo("");
                grid2.setDt(ymd.format(new Date()));
                grid2.setItemCode(0);
                grid2.setItemRate(0.0);
                grid2.setItmQty(0);
                grid2.setTranRef("");
                grid2.setSequencNo("0");
                grid2.setYear("0");
                //grid2.setTranRefDt("");
                SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                grid2.setTranTime(ymdhms.format(new Date()));
                grid2.setAuthBy("");
                grid2.setInstNo("");
                grid2.setValueDt(dmyFormat.format(new java.util.Date()));
                if (type.equalsIgnoreCase("0")) {
                    grid2.setCrAmt(0.0);
                } else {
                    lossAmt = itemBookValue - itemSaleValue;
                    grid2.setDrAmt(lossAmt);
                }
                batchTable2.add(grid2);
            } else if (itemBookValue < itemSaleValue) {
                DeadstockTranTable grid2 = new DeadstockTranTable();
                glAcno = ftsPost43CBSRemote.getAcnoByPurpose("PROFIT ON SALE OF BANKS ASSETS");
                String acno2 = "";
//                List acnoList = deadstockFacadeRemote.getAccountNoAccordingToGlhead("PROFIT ON SALE OF BANKS ASSETS");
//                if (acnoList.size() > 0) {
//                    Vector vec = (Vector) acnoList.get(0);
//                    acno2 = resBrancCode + vec.get(0).toString().substring(2, 10) + "01";
//                }
                acno2 = resBrancCode + glAcno;
                grid2.setAcno(acno2);
                grid2.setTranMode(Integer.parseInt(tranMode));
                grid2.setTranTotal(0.0);
                grid2.setTranType("2");
                grid2.setTranDate(dmyFormat.format(new java.util.Date()));
                grid2.setEnterBy(getUserName());
                grid2.setTranDesc(80);//for deadstock
                grid2.setDetails("Profit on Sale");
                grid2.setDestBrnCode(destBranch.length() < 2 ? "0" + destBranch : destBranch);
                grid2.setOrgBrnCode(originBranch.length() < 2 ? "0" + originBranch : originBranch);
                grid2.setTy(0);
                float rec = ftsPost43CBSRemote.getRecNo();
                grid2.setRecno(rec);
                grid2.setAuth("N");
                grid2.setPayBy(3);
                //grid2.setInstDate("");
                grid2.setAdviseNo("");
                grid2.setBalance(0);
                grid2.setFavourOf("");
                //grid2.setInvoiceDt("");
                grid2.setInvoiceNo("");
                grid2.setDt(ymd.format(new Date()));
                grid2.setItemCode(0);
                grid2.setItemRate(0.0);
                grid2.setItmQty(0);
                grid2.setTranRef("");
                grid2.setSequencNo("0");
                grid2.setYear("0");
                //grid2.setTranRefDt("");
                SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                grid2.setTranTime(ymdhms.format(new Date()));
                grid2.setAuthBy("");
                grid2.setInstNo("");
                grid2.setValueDt(dmyFormat.format(new java.util.Date()));
                if (type.equalsIgnoreCase("1")) {
                    profitAmt = itemSaleValue - itemBookValue;
                    grid2.setCrAmt(profitAmt);
                    flagForProfit = 1;
                } else {
                    grid2.setDrAmt(0.0);
                }
                batchTable2.add(grid2);
            }
            DeadstockTranTable grid = new DeadstockTranTable();
            grid.setAcno(acno);
            grid.setTranMode(Integer.parseInt(tranMode));
            grid.setTranTotal(Double.parseDouble(amount));
            grid.setTranType("2");
            grid.setTranDate(dmyFormat.format(new java.util.Date()));
            grid.setEnterBy(getUserName());
            grid.setTranDesc(80);//for deadstock
            grid.setDetails(details);
            grid.setDestBrnCode(destBranch.length() < 2 ? "0" + destBranch : destBranch);
            grid.setOrgBrnCode(originBranch.length() < 2 ? "0" + originBranch : originBranch);
            grid.setTy(Integer.parseInt(type));
            float rec = ftsPost43CBSRemote.getRecNo();
            grid.setRecno(rec);
            grid.setAuth("N");
            grid.setPayBy(3);
            if (instDate == null) {
                grid.setInstDate(null);
            } else {
                grid.setInstDate(ymd.format(instDate));
            }
            if (seqNo.equalsIgnoreCase("")) {
                grid.setAdviseNo("");
            } else {
                grid.setAdviseNo(advise + seqNo);
            }
            grid.setBalance(0);
            grid.setFavourOf("");
            if (invoiceDate == null) {
                grid.setInvoiceDt(null);
            } else {
                grid.setInvoiceDt(ymd.format(invoiceDate));
            }
            grid.setInvoiceNo(invoiceNo);
            grid.setDt(ymd.format(new Date()));
            if (itemCd == null) {
                grid.setItemCode(0);
            } else {
                grid.setItemCode(Integer.parseInt(itemCd));
            }
            grid.setItemRate(rate);
            grid.setItmQty(quantity);
            grid.setTranRef(tranRef);
            grid.setSequencNo(seqenceNo);
            grid.setYear(year);
            if (refDate == null) {
                grid.setTranRefDt(null);
            } else {
                grid.setTranRefDt(ymd.format(refDate));
            }
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            grid.setTranTime(ymdhms.format(new Date()));
            grid.setAuthBy("");
            grid.setInstNo(instNo);
            grid.setValueDt(dmyFormat.format(new java.util.Date()));
            if (type.equalsIgnoreCase("0")) {
                grid.setCrAmt(Double.parseDouble(amount));
            } else {
                grid.setDrAmt(Double.parseDouble(amount));
            }
            batchTable1 = new ArrayList<DeadstockTranTable>();
//            if (accountName.equalsIgnoreCase("PAY ORDER")) {
//                for (int i = 0; i < 2; i++) {
//                    DeadstockTranTable grid1 = new DeadstockTranTable();
//                    if (i == 0) {
//                        List list = transactionsRemote.checkCommFlag("PO");
//                        if (list.isEmpty()) {
//                            setMsg("");
//                        }
//                        Vector v1 = (Vector) list.get(0);
//                        String acNumber = getOrgnBrCode() + v1.get(2).toString() + "01";
//                        grid1.setAcno(acNumber);
//                        grid1.setDetails("COMM. FOR PO");
//                        grid1.setTranTotal(0.0);
//                        float rec1 = ftsPost43CBSRemote.getRecNo();
//                        grid1.setRecno(rec1);
//                    } else if (i == 1) {
//                        String acNumber = getOrgnBrCode() + taxListGLHead.get(0).toString() + "01";
//                        grid1.setAcno(acNumber);
//                        grid1.setTranTotal(0.0);
//                        float rec1 = ftsPost43CBSRemote.getRecNo();
//                        grid1.setRecno(rec1);
//                        grid1.setDetails("SERVICE TAX");
//                    }
//                    grid1.setTranMode(Integer.parseInt(tranMode));
//                    grid1.setTranType("2");
//                    grid1.setTranDate(dmyFormat.format(new java.util.Date()));
//                    grid1.setEnterBy(getUserName());
//                    grid1.setTranDesc(80);//for deadstock
//                    grid1.setDestBrnCode(destBranch.length() < 2 ? "0" + destBranch : destBranch);
//                    grid1.setOrgBrnCode(originBranch.length() < 2 ? "0" + originBranch : originBranch);
//                    grid1.setTy(Integer.parseInt(type));
//                    grid1.setAuth("N");
//                    grid1.setPayBy(3);
//                    grid1.setComm(0.0);
//                    grid1.setFavourOf("");
//                    grid1.setCustName("");
//                    grid1.setFavourOf("");
//                    if (instDate == null) {
//                        grid1.setInstDate(null);
//                    } else {
//                        grid1.setInstDate(ymd.format(instDate));
//                    }
//                    if (seqNo.equalsIgnoreCase("")) {
//                        grid1.setAdviseNo("");
//                    } else {
//                        grid1.setAdviseNo(advise + seqNo);
//                    }
//                    grid1.setBalance(0);
//                    if (invoiceDate == null) {
//                        grid1.setInvoiceDt(null);
//                    } else {
//                        grid1.setInvoiceDt(ymd.format(invoiceDate));
//                    }
//                    grid1.setInvoiceNo(invoiceNo);
//                    grid1.setDt(ymd.format(new Date()));
//                    if (itemCd == null) {
//                        grid1.setItemCode(0);
//                    } else {
//                        grid1.setItemCode(Integer.parseInt(itemCd));
//                    }
//                    grid1.setItemRate(rate);
//                    grid1.setItmQty(quantity);
//                    grid1.setTranRef(tranRef);
//                    grid1.setSequencNo(seqenceNo);
//                    grid1.setYear(year);
//                    if (refDate == null) {
//                        grid1.setTranRefDt(null);
//                    } else {
//                        grid1.setTranRefDt(ymd.format(refDate));
//                    }
//                    grid1.setTranTime(ymdhms.format(new Date()));
//                    grid1.setAuthBy("");
//                    grid1.setInstNo(instNo);
//                    grid1.setValueDt(dmyFormat.format(new java.util.Date()));
//                    if (type.equalsIgnoreCase("0")) {
//                        if (i == 0) {
//                            commAmt = Double.parseDouble(poComm.toString());
//                            grid1.setCrAmt(commAmt);
//                        } else {
//                            taxAmt = Double.parseDouble(formatter.format(Double.parseDouble(taxList.get(0).toString())));
//                            grid1.setCrAmt(taxAmt);
//                        }
//                    } else {
//                        if (i == 0) {
//                            commAmt = Double.parseDouble(poComm.toString());
//                            grid1.setDrAmt(commAmt);
//                        } else {
//                            taxAmt = Double.parseDouble(formatter.format(Double.parseDouble(taxList.get(0).toString())));
//                            grid1.setDrAmt(taxAmt);
//                        }
//                    }
//                    batchTable1.add(grid1);
//                }
//            }
            grid.setComm(commAmt);
            grid.setFavourOf(favorOf);
            grid.setCustName(custName);
            batchTable.add(grid);
            if (!batchTable2.isEmpty()) {
                batchTable.addAll(batchTable2);
            }
            if (!batchTable1.isEmpty()) {
                batchTable.addAll(batchTable1);
            }
            if (type.equalsIgnoreCase("0")) {
                crAmt = crAmt + Double.parseDouble(amount) + commAmt + taxAmt;
                commAmt = 0.0;
                taxAmt = 0.0;
                profitAmt = 0.0;
            } else {
                if (flagForProfit == 1) {
                    crAmt += profitAmt;
                }
                drAmt = drAmt + Double.parseDouble(amount) + commAmt + taxAmt + lossAmt;
                commAmt = 0.0;
                taxAmt = 0.0;
                lossAmt = 0.0;
                flagForProfit = 0;
            }
            if (drAmt == crAmt && drAmt != 0) {
                boolSaveBatch = false;
            } else {
                boolSaveBatch = true;
            }
            disableMainSave = true;
            refresh();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        refresh();
        return "case1";
    }

    public void saveBatch() {
        try {
            float trsno = ftsPost43CBSRemote.getTrsNo();
            msg = deadstockFacadeRemote.saveTransactionRecord(batchTable, trsno, itemTable, modeOfTrns, getUserName());
            if (batchTable != null) {
                batchTable.clear();
            }
            if (batchTable1 != null) {
                batchTable1.clear();
            }
            if (itemTable != null) {
                itemTable.clear();
            }
            if (batchTable2 != null) {
                batchTable2.clear();
            }
            refresh();
            boolSaveBatch = true;
            setCrAmt(0.00);
            setDrAmt(0.00);
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void onChangeItemCode() {
        try {
            itemName = deadstockFacadeRemote.getItemName(itemCode);
            itemCd = itemCode;
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void onChangeItemCode1() {
        try {
            itemName = deadstockFacadeRemote.getItemName(itemCode);
            itemCd = itemCode;
            if (tranMode.equalsIgnoreCase("2")) {
                getDataInGrid(itemCd);
            } else if (tranMode.equalsIgnoreCase("3")) {
                getDataInGridForWriteOff(itemCd);
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getDataInGrid(String itemCode) {
        double amtPrUnit = 0.0;
        qty = 0;
        totAmt = 0.0;
        long distinctiveSno = 0;
        try {
            List resultList = deadstockFacadeRemote.getDataInGrid(itemCode, getOrgnBrCode());
            itemTable = new ArrayList<ItemMasterTable>();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    ItemMasterTable grid = new ItemMasterTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        distinctiveSno = Long.parseLong(ele.get(0).toString());
                        grid.setItemDistinctiveSno(distinctiveSno);
                    }
                    if (ele.get(1) != null) {
                        amtPrUnit = Double.parseDouble(ele.get(1).toString());
                        grid.setAmtPerUnit(amtPrUnit);
                    }
                    grid.setSno(i + 1);
                    grid.setItemCode(Integer.parseInt(itemCode));
                    grid.setFlag("T");
                    grid.setTranFlag("N");
                    itemTable.add(grid);
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getDataInGridForWriteOff(String itemCode) {
        double amtPrUnit = 0.0;
        qty = 0;
        totAmt = 0.0;
        long distinctiveSno = 0;
        try {
            List resultList = deadstockFacadeRemote.getDataInGridForWriteOff(itemCode, getOrgnBrCode());
            itemTable = new ArrayList<ItemMasterTable>();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    ItemMasterTable grid = new ItemMasterTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        distinctiveSno = Long.parseLong(ele.get(0).toString());
                        grid.setItemDistinctiveSno(distinctiveSno);
                    }
                    if (ele.get(1) != null) {
                        amtPrUnit = Double.parseDouble(ele.get(1).toString());
                        grid.setAmtPerUnit(amtPrUnit);
                    }
                    if (ele.get(2) != null) {
                        grid.setOriginBranchId(ele.get(2).toString());
                        grid.setDestBrnid(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        grid.setDepApplyUpto(ele.get(3).toString());
                    }
                    if (ele.get(4) != null) {
                        grid.setOriginalCost(Double.parseDouble(ele.get(4).toString()));
                    }
                    if (ele.get(5) != null) {
                        grid.setItemDepAmount(Double.parseDouble(ele.get(5).toString()));
                    }
                    grid.setSno(i + 1);
                    grid.setItemCode(Integer.parseInt(itemCode));
                    grid.setTranFlag("N");
                    grid.setFlag("W");
                    itemTable.add(grid);
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void calculateTotal() {
        quantity = itemQty;
        double total = itemQty * rate;
        setTranTotal(total + "");
    }

    public void saveTransferAmount() {
        setAmount(totalAmountInTransfer);
        disableAmount = true;
    }

    public void savePurchaseAmount() {
        setAmount(tranTotal);
        disableAmount = true;
    }

    public void refresh() {
        itemCodeList.clear();
        setAcno("");
        setOldAcno("");
        setAdvise("AD");
        setSeqNo("0");
        setAccountName("");
        setDestBranch("--Select--");
        setAmount("0.00");
        setDetails("");
        setDisableMainSave(true);
        setInstNo("");
        setInvoiceNo("");
        setIssuemsg("");
        setItemName("");
        setItemQty(0);
        setOriginBranch("--Select--");
        setPurMessage("");
        setRate(0.00);
        setTotalQty(0);
        transferedQty = 0;
        setType("0");
        setTranRef("");
        setTotalAmountInTransfer("0.00");
        tranTotal = "0.00";
        disableDestBranch = false;
        disableOriginBranch = false;
        disableIssueSave = false;
        seqenceNo = "0";
        year = "0";
        seqNo = "";
        disableSeqNo = true;
        disableYear = true;
        favorOf = "";
        custName = "";
        disableCustName = true;
        disableFavorOf = true;
        resBrancCode = "";
        purflag = "false";
        issueFlag = "false";
        writeFlag = "false";
        itemBookValue = 0.0;
        itemSaleValue = 0.0;
        balan = null;
        disableQty = true;
        disableAmount = false;
        labelCrDr = "";
        if (batchTable2 != null) {
            batchTable2.clear();
        }
    }

    public void btnRefresh() {
        refresh();
        setTranMode("1");
        setMsg("");
        if (batchTable != null) {
            batchTable.clear();
        }
        if (batchTable1 != null) {
            batchTable1.clear();
        }
        if (batchTable2 != null) {
            batchTable2.clear();
        }
        if (itemTable != null) {
            itemTable.clear();
        }
        crAmt = 0.00;
        drAmt = 0.00;
    }

    public void onBlurOfMode() {
        branchList.clear();
        distBranchList.clear();
        disableOriginBranch = false;
        disableDestBranch = false;
        try {
            List resultList = deadstockFacadeRemote.selectAlphaCodeByBranchCode(getOrgnBrCode());
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                branchList.add(new SelectItem(ele.get(0)));
                originBranch = ele.get(0).toString();
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        distBranchList.add(new SelectItem("--Select--"));
        try {
            List resultList = deadstockFacadeRemote.selectAlphaCodeByBranchCode(resBrancCode);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                distBranchList.add(new SelectItem(ele.get(0)));
                destBranch = ele.get(0).toString();
            }
            disableOriginBranch = true;
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void calculateTransferTotal() {
        issuemsg = "";
        disableIssueSave = false;
        quantity = transferedQty;
        rate = transferedQtyRate;
        for (ItemMasterTable imt : itemTable) {
            if (rate == imt.getAmtPerUnit()) {
                if (quantity <= imt.getTotalItems()) {
                    disableIssueSave = false;
                } else {
                    issuemsg = "transfered quantity is not valid";
                    disableIssueSave = true;
                    break;
                }
            }
        }
        double total = transferedQtyRate * transferedQty;
        setTotalAmountInTransfer(total + "");
    }

    public void changeTranFlag() {
        ItemMasterTable data = itemTable.get(currentRow);
        if (data.getTranFlag().equalsIgnoreCase("N")) {
            itemTable.remove(currentRow);
            data.setTranFlag("Y");
            qty = qty + 1;
            totAmt = totAmt + data.getAmtPerUnit();
            itemTable.add(currentRow, data);
        } else if (itemTable.get(currentRow).getTranFlag().equalsIgnoreCase("Y")) {
            itemTable.remove(currentRow);
            data.setTranFlag("N");
            qty = qty - 1;
            totAmt = totAmt - data.getAmtPerUnit();
            itemTable.add(currentRow, data);
        }
        setTransferedQty(qty);
        setTotalAmountInTransfer(totAmt + "");
    }

    public void checkAmountForSeqno() {
        if (!validator.validateDoublePositive(amount)) {
            msg = "amount is not valid";
            return;
        }
        disableMainSave = false;
        // List list = new ArrayList();
        poComm = new BigDecimal("0");
        // double scharge = 0;
        //BigDecimal amt = null;
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
//        if (!amount.equalsIgnoreCase("")) {
//            amt = new BigDecimal(amount);
//        }
        msg = "";
        try {
            String glAcno = "";
            glAcno = acno.substring(0, 2) + ftsPost43CBSRemote.getAcnoByPurpose("PO ACCOUNT");
            // if (accountName.equalsIgnoreCase("PAY ORDER")) {
            if (acno.equalsIgnoreCase(glAcno)) {
                if (favorOf.equalsIgnoreCase("")) {
                    msg = "Please insert favour of details";
                    return;
                } else if (custName.equalsIgnoreCase("")) {
                    msg = "please insert customer name";
                    return;
                }
                List listBnkDays = transactionsRemote.selectFromBankDays(getOrgnBrCode());
                if (!listBnkDays.isEmpty()) {
                    Vector vecBnkDays = (Vector) listBnkDays.get(0);
                    Tempbd = vecBnkDays.get(0).toString();
                    mydate1 = ymdFormat.format(date);
                    Calendar tempbd = Calendar.getInstance();
                    Calendar mydat = Calendar.getInstance();
                    tempbd.set(Integer.parseInt(Tempbd.substring(0, 4)), Integer.parseInt(Tempbd.substring(5, 7)), Integer.parseInt(Tempbd.substring(8, 10)));
                    mydat.set(Integer.parseInt(mydate1.substring(0, 4)), Integer.parseInt(mydate1.substring(4, 6)), Integer.parseInt(mydate1.substring(6)));
                    String tempDate = Tempbd.substring(0, 4) + Tempbd.substring(5, 7) + Tempbd.substring(8, 10);
                    Long longTempDate = Long.parseLong(tempDate);
                    Long longMydate1 = Long.parseLong(mydate1);
                    if (longTempDate.compareTo(longMydate1) != 0) {
                        this.setMsg("This Date is Denied.");
                        return;
                    }
                }
                String tempDate = Tempbd.substring(0, 4) + Tempbd.substring(5, 7) + Tempbd.substring(8, 10);
                setPoComm(new BigDecimal(transactionsRemote.getCommission(tempDate, CbsConstant.PAY_ORDER, 1, Double.parseDouble(amount))));
                String commiFlag = "";
                double passAmt = 0.0;
                double commFinAmt = 0;
                int rUpto = 0, j;
                //BigDecimal taxAmtresult = new BigDecimal(0);
                Map<String, Double> map = new HashMap<String, Double>();
                //TODO take care later
                try {

                    map = interFtsRemote.getTaxComponent(Double.parseDouble(amount), tempDate);
                    Set<Map.Entry<String, Double>> set = map.entrySet();
                    Iterator<Map.Entry<String, Double>> itS = set.iterator();
                    while (itS.hasNext()) {
                        Entry entry = itS.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        String taxHead = keyArray[1];
                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                        passAmt = passAmt + taxAmount;
                        taxList.add(passAmt);
                        taxListGLHead.add(taxHead);
                    }
//                    List list1 = transactionsRemote.findServiceTax1(Tempbd);
//                    if (list1.isEmpty()) {
//                        return;
//                    } else {
//                        for (int m = 0; m < list1.size(); m++) {
//                            Vector v1 = (Vector) list1.get(m);
//                            commiFlag = v1.get(0).toString();
//                            rUpto = Integer.parseInt(v1.get(1).toString());
//                        }
//                        if (commiFlag.equals("V")) {
//                            passAmt = Double.parseDouble(this.getPoComm().toString());
//                        } else {
//                            passAmt = Double.parseDouble(this.getPoComm().toString());
//                        }
//                        List result = transactionsRemote.fnTaxApplicableROT(Tempbd);
//                        if (result.isEmpty()) {
//                            return;
//                        } else {
//                            for (j = 0; j < result.size(); j++) {
//                                Vector vResult = (Vector) result.get(j);
//                                if (vResult.get(2).toString().equals("C")) {
//                                    taxAmtresult = new BigDecimal(Float.parseFloat(transactionsRemote.taxAmountProcedure(passAmt, vResult.get(0).toString(), rUpto)));
//                                } else if (vResult.get(2).toString().equals("T")) {
//                                    taxAmtresult = new BigDecimal(Float.parseFloat(transactionsRemote.taxAmountProcedure(commFinAmt, vResult.get(0).toString(), rUpto)));
//                                }
//                                taxAmtresult.add(taxAmtresult);
//                                taxList.add(taxAmtresult);
//                                taxListGLHead.add(vResult.get(3).toString());
//                            }
//                        }
                    // }
                } catch (Exception e) {
                    setMsg(e.getLocalizedMessage());
                }
            }
            if ((!seqenceNo.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("")) && tranMode.equalsIgnoreCase("1")) {//for Suspence Account
                String message = deadstockFacadeRemote.checkAmountForSuspenseAc(seqenceNo, year, acno, amount);
                if (message.equalsIgnoreCase("true")) {
                    disableMainSave = true;
                    msg = "In valid amount for this sequence no. and year for this suspense payment";
                } else {
                    msg = "";
                }
            } else if ((!seqenceNo.equalsIgnoreCase("")) && (!year.equalsIgnoreCase("")) && tranMode.equalsIgnoreCase("3")) {//for Sundry Account
                String message = deadstockFacadeRemote.checkAmountForSundryAc(seqenceNo, year, acno, amount);
                if (message.equalsIgnoreCase("true")) {
                    disableMainSave = true;
                    msg = "In valid amount for this sequence no. and year for this sundry account";
                } else {
                    msg = "";
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            DeadstockTranTable data = batchTable.get(currentRow1);
            crAmt = crAmt - data.getCrAmt();
            drAmt = drAmt - data.getDrAmt();
            batchTable.remove(currentRow1);
            if (drAmt == crAmt && drAmt != 0) {
                boolSaveBatch = false;
            } else {
                boolSaveBatch = true;
            }

        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
}
