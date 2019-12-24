/*
 * CREATED BY      :    ROHIT KRISHNA GUPTA
 * CREATION DATE   :    13 OCT 2010
 */
package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.StockSatementGrid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public final class StockStatement extends BaseBean {

    OtherMasterFacadeRemote otherMasterRemote;
    private String orgnBrCode = getOrgnBrCode();
    private String userName = getUserName();
    // private String userName = "Shipra";
    private String errorMessage;
    private String message;
//    private String acctType;
    private String acctNo, acNoLen;
    private String oldAcctNo;
    private String acNo;
    private String custName;
    private List<SelectItem> acctNoOption;
    private List<SelectItem> secDescList;
    private String secDesc;
    private String desc;
    private String valueHypo;
    private String margin;
    private String sancLimit;
    private String prevDp;
    private String netDp;
    private Date stockStSubDt;
    private Date actualStSubDt;
    private Date stockStDueDt;
    private String gracePeriod;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private boolean flag5;
    private int currentRow;
    private List<StockSatementGrid> gridDetail;
    private StockSatementGrid currentItem = new StockSatementGrid();
    private Iterable<StockSatementGrid> stockSatementGrid;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public List<SelectItem> getAcctNoOption() {
        return acctNoOption;
    }

    public void setAcctNoOption(List<SelectItem> acctNoOption) {
        this.acctNoOption = acctNoOption;
    }

//    public String getAcctType() {
//        return acctType;
//    }
//
//    public void setAcctType(String acctType) {
//        this.acctType = acctType;
//    }
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getSecDesc() {
        return secDesc;
    }

    public void setSecDesc(String secDesc) {
        this.secDesc = secDesc;
    }

    public String getValueHypo() {
        return valueHypo;
    }

    public void setValueHypo(String valueHypo) {
        this.valueHypo = valueHypo;
    }

    public List<SelectItem> getSecDescList() {
        return secDescList;
    }

    public void setSecDescList(List<SelectItem> secDescList) {
        this.secDescList = secDescList;
    }

    public StockSatementGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(StockSatementGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<StockSatementGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<StockSatementGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getPrevDp() {
        return prevDp;
    }

    public void setPrevDp(String prevDp) {
        this.prevDp = prevDp;
    }

    public String getSancLimit() {
        return sancLimit;
    }

    public void setSancLimit(String sancLimit) {
        this.sancLimit = sancLimit;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public Date getActualStSubDt() {
        return actualStSubDt;
    }

    public void setActualStSubDt(Date actualStSubDt) {
        this.actualStSubDt = actualStSubDt;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Date getStockStDueDt() {
        return stockStDueDt;
    }

    public void setStockStDueDt(Date stockStDueDt) {
        this.stockStDueDt = stockStDueDt;
    }

    public Date getStockStSubDt() {
        return stockStSubDt;
    }

    public void setStockStSubDt(Date stockStSubDt) {
        this.stockStSubDt = stockStSubDt;
    }

    public String getNetDp() {
        return netDp;
    }

    public void setNetDp(String netDp) {
        this.netDp = netDp;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
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

    public boolean isFlag5() {
        return flag5;
    }

    public void setFlag5(boolean flag5) {
        this.flag5 = flag5;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public StockStatement() {
        try {
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            setStockStSubDt(date);
            setActualStSubDt(date);
            this.setErrorMessage("");
            this.setMessage("");
            setFlag1(true);
            setFlag2(true);
            setFlag3(true);
            setFlag4(true);
            setFlag5(false);
            secDescList = new ArrayList<SelectItem>();
            secDescList.add(new SelectItem("--Select--"));
            secDescList.add(new SelectItem("RAW MATERIAL"));
            secDescList.add(new SelectItem("WORK IN PROGRESS"));
            secDescList.add(new SelectItem("FINISHED GOODS"));
            secDescList.add(new SelectItem("SUNDRY DEBTORS"));
            secDescList.add(new SelectItem("OTHERS"));
            acTypeCombo();
            addMonth();
            gridDetail = new ArrayList<StockSatementGrid>();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void acTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = otherMasterRemote.actTypeCombo();
            if (!resultList.isEmpty()) {
                acctNoOption = new ArrayList<SelectItem>();
                acctNoOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        acctNoOption.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                acctNoOption = new ArrayList<SelectItem>();
                acctNoOption.add(new SelectItem("--Select--"));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void addMonth() {
        try {
            List resultList = new ArrayList();
            resultList = otherMasterRemote.addMonth();
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date orgnDt = sdf.parse(ele.get(0).toString());
                this.setStockStDueDt(orgnDt);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setMessage("Please Enter the Account No.");
                return;
            }
            if (!oldAcctNo.matches("[0-9a-zA-z]*")) {
                setMessage("Please Enter Valid  Account No.");
                return;
            }
            //if (oldAcctNo.length() != 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Account number is not valid.");
                return;
            }
            acNo = fts.getNewAccountNumber(oldAcctNo);
            setAcctNo(acNo);

            String acnature = fts.getAccountNature(acNo);
            if (!acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                this.setErrorMessage("Account nature does not exist");
                return;
            }

            List resultList = new ArrayList();
            resultList = otherMasterRemote.acDetail(acNo);
            if (resultList.isEmpty()) {
                this.setErrorMessage("Account No. Does Not Exist.");
                this.setCustName("");
                this.setAcctNo("");
                this.setOldAcctNo("");
                this.setSancLimit("0.0");
                this.setPrevDp("0.0");
                this.setDesc("");
                this.setValueHypo("");
                this.setSecDesc("--Select--");
                this.setDesc("");
                this.setMargin("");
                this.setGracePeriod("0");
                Date date = new Date();
                setStockStSubDt(date);
                setActualStSubDt(date);
                addMonth();
                return;
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(4).toString().equals("9")) {
                    this.setErrorMessage("This account is already Closed");
                    this.setAcctNo("");
                    this.setOldAcctNo("");
                    return;
                }
                gridLoadOnAcno();
                this.setCustName(ele.get(0).toString());
                this.setSancLimit(String.valueOf(formatter.format(new BigDecimal(ele.get(3).toString()).doubleValue())));
                this.setPrevDp(String.valueOf(formatter.format(new BigDecimal(ele.get(2).toString()).doubleValue())));
                this.setDesc("");
                this.setValueHypo("");
                this.setSecDesc("--Select--");
                this.setDesc("");
                this.setMargin("");
                this.setGracePeriod("0");
                Date date = new Date();
                setStockStSubDt(date);
                setActualStSubDt(date);
                addMonth();
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("stmNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (StockSatementGrid item : stockSatementGrid) {
                if (item.getStmNo().equals(accNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            if (currentItem.getSecDesc().equalsIgnoreCase("")) {
                return;
            }
            if (!currentItem.getStmNo().equalsIgnoreCase("")) {
                this.setErrorMessage("You Can't delete the saved record.");
                return;
            }
            this.setDesc("");
            this.setValueHypo("");
            this.setSecDesc("--Select--");
            this.setDesc("");
            this.setMargin("");
            this.setGracePeriod("");
            Date date = new Date();
            setStockStSubDt(date);
            setActualStSubDt(date);
            addMonth();
            gridDetail.remove(currentRow);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoadOnAcno() {
        gridDetail = new ArrayList<StockSatementGrid>();
        try {
            List resultList = new ArrayList();
            resultList = otherMasterRemote.gridLoad(acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (!ele.get(0).toString().equalsIgnoreCase("9")) {
                        StockSatementGrid detail = new StockSatementGrid();
                        detail.setsNo(i + 1);
                        detail.setStmNo(ele.get(0).toString());
                        detail.setSecNo(ele.get(1).toString());
                        detail.setSecurity(ele.get(2).toString());
                        detail.setSecDesc(ele.get(3).toString());
                        detail.setValue(Double.parseDouble(ele.get(4).toString()));
                        detail.setMargin(Double.parseDouble(ele.get(5).toString()));
                        double tmpMargin;
                        double tmpDp;
                        tmpMargin = (1 - (Double.parseDouble(ele.get(5).toString()) / 100));
                        if (tmpMargin > 0) {
                            tmpDp = Double.parseDouble(ele.get(4).toString()) * tmpMargin;
                        } else {
                            tmpDp = Double.parseDouble(ele.get(4).toString());
                        }
                        detail.setDpPartition(Double.parseDouble(String.valueOf(tmpDp)));
                        detail.setNetDp(Double.parseDouble(ele.get(8).toString()));
                        detail.setRenewDt(ele.get(6).toString());
                        List resultList1 = otherMasterRemote.chkGrid(ele.get(6).toString());
                        Vector ele1 = (Vector) resultList1.get(0);
                        if (Integer.parseInt(ele1.get(0).toString()) < 0) {
                            detail.setStatusToday("ACTIVE");
                        } else {
                            detail.setStatusToday("EXPIRED");
                        }
                        gridDetail.add(detail);
                    }
                }
                this.setDesc("");
                this.setValueHypo("");
                this.setSecDesc("--Select--");
                this.setDesc("");
                this.setMargin("");
                this.setGracePeriod("0");
                Date date = new Date();
                setStockStSubDt(date);
                setActualStSubDt(date);
                addMonth();
            } else {
                gridDetail = new ArrayList<StockSatementGrid>();
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.secDesc.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Security Description.");
                return;
            }
            if (this.desc.equalsIgnoreCase("") || this.desc.length() == 0) {
                this.setErrorMessage("Please Enter Description.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.valueHypo == null || this.valueHypo.equalsIgnoreCase("") || this.valueHypo.length() == 0) {
                this.setErrorMessage("Please Enter Value Hypothicated.");
                return;
            }
            /**
             * *
             */
            if (this.valueHypo.contains(".")) {
                if (this.valueHypo.indexOf(".") != this.valueHypo.lastIndexOf(".")) {
                    this.setErrorMessage("Invalid Value Hypothicated.Please Fill The Value Hypothicated Correctly.");
                    return;
                } else if (this.valueHypo.substring(valueHypo.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Value Hypothicated Upto Two Decimal Places.");
                    return;
                }
            }
            /**
             * *
             */
            Matcher valueHypoCheck = p.matcher(valueHypo);
            if (!valueHypoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Value Hypothicated(Numbers Only).");
                return;
            }
            if (Float.parseFloat(valueHypo) <= 0) {
                this.setErrorMessage("Value Hypothicated Cannot Be Less Than Or Equal To Zero.");
                return;
            }
            if (this.margin == null || this.margin.equalsIgnoreCase("") || this.margin.length() == 0) {
                this.setErrorMessage("Please Enter Margin");
                return;
            }
            if (this.margin.contains(".")) {
                if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                    this.setErrorMessage("Invalid Margin.Please Fill The Margin Correctly.");
                    return;
                } else if (this.margin.substring(margin.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Margin Upto Two Decimal Places.");
                    return;
                }
            }
            Matcher marginCheck = p.matcher(margin);
            if (!marginCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Margin.");
                return;
            }
            if (Float.parseFloat(margin) < 0) {
                this.setErrorMessage("Margin Cannot Be Less Than Zero.");
                return;
            }
            StockSatementGrid txnBean = createTxnBeanObj();
            gridDetail.add(txnBean);
            setFlag1(false);
            this.setDesc("");
            this.setValueHypo("");
            this.setSecDesc("--Select--");
            this.setDesc("");
            this.setMargin("");
            this.setGracePeriod("0");
            Date date = new Date();
            setStockStSubDt(date);
            setActualStSubDt(date);
            addMonth();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private StockSatementGrid createTxnBeanObj() {
        StockSatementGrid txnBean = new StockSatementGrid();
        try {
            Float tmpMargin;
            Float tmpDp;
            tmpMargin = (1 - (Float.parseFloat(this.margin) / 100));
            if (tmpMargin > 0) {
                tmpDp = Float.parseFloat(this.valueHypo) * tmpMargin;
            } else {
                tmpDp = Float.parseFloat(this.valueHypo);
            }
            for (int k = 0; k < gridDetail.size() + 1; k++) {
                txnBean.setsNo(k + 1);
            }
            txnBean.setStmNo("");
            txnBean.setSecNo("");
            txnBean.setSecurity(this.secDesc);
            txnBean.setSecDesc(this.desc);
            txnBean.setValue(Double.parseDouble(this.valueHypo));
            txnBean.setMargin(Double.parseDouble(this.margin));
            txnBean.setDpPartition(Double.parseDouble(tmpDp.toString()));
            txnBean.setNetDp(0.0d);
            txnBean.setRenewDt("");
            txnBean.setStatusToday("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return txnBean;
    }

    public void netProposedDpBtn() {
        this.setErrorMessage("");
        this.setMessage("");
        double tmpMargin;
        double tmpDp;
        try {
            List resultList = new ArrayList();
            resultList = otherMasterRemote.getNetProposedDp(acNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    tmpMargin = (1 - (Double.parseDouble(ele.get(1).toString()) / 100));
                    if (tmpMargin > 0) {
                        tmpDp = Double.parseDouble(ele.get(0).toString()) * tmpMargin;
                    } else {
                        tmpDp = Double.parseDouble(ele.get(0).toString());
                    }
                    this.setNetDp(formatter.format(tmpDp));
                    setFlag1(true);
                    setFlag2(false);
                    setFlag3(false);
                    setFlag4(true);
                    setFlag5(true);
                }
            } else {
                this.setErrorMessage("No Record Found for this A/C No.");
                this.setNetDp("");
                setFlag3(true);
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveStockDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter A/c No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("Please Enter A/c No.");
                return;
            }
            if (this.stockStSubDt == null) {
                this.setErrorMessage("Please Enter Stock Statement Submission Date.");
                return;
            }
            if (this.actualStSubDt == null) {
                this.setErrorMessage("Please Enter Actual Statement Submission Date.");
                return;
            }
            if (this.stockStDueDt == null) {
                this.setErrorMessage("Please Enter Stock Statement Due Date.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.gracePeriod.equalsIgnoreCase("") || this.gracePeriod.length() == 0) {
                this.setErrorMessage("Please Enter Grace Period");
                return;
            }
            Matcher gracePeriodCheck = p.matcher(gracePeriod);
            if (!gracePeriodCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Grace Period.");
                return;
            }
            if (this.gracePeriod.contains(".")) {
                this.setErrorMessage("Please Enter Valid Grace Period.");
                return;
            }
            if (Float.parseFloat(gracePeriod) < 0) {
                this.setErrorMessage("Please Enter Valid Grace Period.");
                return;
            }
            List arraylist = new ArrayList();
            String a[][] = new String[gridDetail.size()][12];
            for (int i = 0; i < gridDetail.size(); i++) {
                a[i][0] = gridDetail.get(i).getsNo().toString();
                a[i][1] = gridDetail.get(i).getStmNo().toString();
                a[i][2] = gridDetail.get(i).getSecNo().toString();
                a[i][3] = gridDetail.get(i).getSecurity().toString();
                a[i][4] = gridDetail.get(i).getSecDesc().toString();
                a[i][5] = String.valueOf(gridDetail.get(i).getValue());
                a[i][6] = String.valueOf(gridDetail.get(i).getMargin());
                a[i][7] = String.valueOf(gridDetail.get(i).getDpPartition());
                a[i][8] = String.valueOf(gridDetail.get(i).getNetDp());
                a[i][9] = gridDetail.get(i).getRenewDt().toString();
                a[i][10] = gridDetail.get(i).getStatusToday().toString();
            }
            for (int i = 0; i < gridDetail.size(); i++) {
                for (int j = 0; j < 12; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = otherMasterRemote.saveStockStatement(acNo, this.userName, arraylist, this.gracePeriod, ymd.format(this.stockStDueDt), ymd.format(this.stockStSubDt), ymd.format(this.actualStSubDt), this.orgnBrCode);
            if (result == null) {
                this.setErrorMessage("Not Saved");
                setFlag1(true);
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("RECORD SAVED SUCCESSFULLY.");
                    gridLoadOnAcno();
                    setFlag1(true);
                    setFlag2(false);
                    setFlag4(false);
                    this.setDesc("");
                    this.setValueHypo("");
                    this.setMargin("");
                    this.setGracePeriod("0");
                    Date date = new Date();
                    setStockStSubDt(date);
                    setActualStSubDt(date);
                    addMonth();
                } else {
                    this.setErrorMessage(result);
                    this.setDesc("");
                    this.setValueHypo("");
                    this.setMargin("");
                    this.setGracePeriod("0");
                    Date date = new Date();
                    setStockStSubDt(date);
                    setActualStSubDt(date);
                    addMonth();
                    setFlag1(true);
                    setFlag2(false);
                    setFlag4(false);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void newStatementDpBtn() {
        gridDetail = new ArrayList<StockSatementGrid>();
        setFlag1(true);
        addMonth();
    }

    public void resubmitBtn() {
        setFlag1(false);
        setFlag2(true);
        setFlag3(true);
        setFlag4(true);
    }

    public void setNewDp() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter A/c No.");
                return;
            }
            if (this.acNo.equalsIgnoreCase("") || this.acNo.length() == 0) {
                this.setErrorMessage("Please Enter A/c No.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.netDp.equalsIgnoreCase("") || this.netDp.length() == 0) {
                this.setErrorMessage("NO NEW DP EXISTS");
                return;
            }
            Matcher gracePeriodCheck = p.matcher(netDp);
            if (!gracePeriodCheck.matches()) {
                this.setErrorMessage("Please Enter Valid New DP.");
                return;
            }
            if (Double.parseDouble(netDp) < 0) {
                this.setErrorMessage("New DP Cannot Be Less Than Zero");
                return;
            }
            if (Double.parseDouble(netDp) > Double.parseDouble(this.sancLimit)) {
                this.setErrorMessage("DP Limit Can't be greater than Sanction Limit , DP Limit will be set to Sanction Limit.");
                netDp = this.sancLimit.toString();
                return;
            }
            String result = otherMasterRemote.setDpLimit(acNo, Double.parseDouble(netDp));
            if (result == null) {
                this.setErrorMessage("Not Updated");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                    setFlag3(true);
                } else {
                    this.setMessage(result);
                    setFlag3(true);
                    this.setAcctNo("");
                    this.setOldAcctNo("");
//                    this.setAcctType("--Select--");
                    this.setSecDesc("--Select--");
                    this.setDesc("");
                    this.setValueHypo("");
                    this.setMargin("");
                    this.setCustName("");
                    Date date = new Date();
                    setStockStSubDt(date);
                    setActualStSubDt(date);
                    addMonth();
                    this.setGracePeriod("0");
                    this.setSancLimit("0.0");
                    this.setPrevDp("0.0");
                    this.setNetDp("0.0");
                    gridDetail = new ArrayList<StockSatementGrid>();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcctNo("");
            this.setOldAcctNo("");
//            this.setAcctType("--Select--");
            this.setSecDesc("--Select--");
            this.setDesc("");
            this.setValueHypo("");
            this.setMargin("");
            this.setCustName("");
            Date date = new Date();
            setStockStSubDt(date);
            setActualStSubDt(date);
            addMonth();
            this.setGracePeriod("0");
            this.setSancLimit("0.0");
            this.setPrevDp("0.0");
            this.setNetDp("0.0");
            setFlag1(true);
            setFlag2(true);
            setFlag3(true);
            setFlag4(true);
            setFlag5(false);
            gridDetail = new ArrayList<StockSatementGrid>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onChangeStockStatementDueDate() {
        try {
            if (this.stockStSubDt == null) {
                this.setErrorMessage("Please Enter Stock Statement Submission Date.");
                return;
            }
            if (this.stockStDueDt == null) {
                this.setErrorMessage("Please Enter Stock Statement Due Date.");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            String subDate = sdf.format(this.stockStSubDt);
            String dueDate = sdf.format(this.stockStDueDt);
            cal1.set(Integer.parseInt(subDate.substring(0, 4)), Integer.parseInt(subDate.substring(4, 6)), Integer.parseInt(subDate.substring(6)));
            cal2.set(Integer.parseInt(dueDate.substring(0, 4)), Integer.parseInt(dueDate.substring(4, 6)), Integer.parseInt(dueDate.substring(6)));
            if (cal2.before(cal1)) {
                this.setErrorMessage("DUE DATE CAN NOT BE LESS THAN SUBMISSION DATE !!!");
                return;
            } else {
                this.setErrorMessage("");
                return;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        resetForm();
        return "case1";
    }
}
