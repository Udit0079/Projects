/*
 * Created By : ROHIT KRISHNA GUPTA
 * Creation Date : 30 Sep 2010
 */
package com.cbs.web.controller.bill;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.web.pojo.bill.OutwardBillsGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.OutwardFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class OutwardBillBooking {

    Context ctx;
    OutwardFacadeRemote outwardBills;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
  //  private String acctType;
    private String oldAcctNo; 
    private String acctNo;
    private String custName;
    private String jtName1;
    private String jtName2;
    private String billType;
    private Date instDate;
    private String instAmt;
    private String instNo;
    private String inFavOf;
    private String collectBankType;
    private String collectAlphaCode;
    private String collectBankName;
    private String collectBankAddress;
    private String actNo;
    private String comm;
    private String postage;
    private String invNo;
    private String detainBillPeriod;
    private String billDetainUpto;
    private boolean bankFlag;
    private boolean bankPanelFlag;
    private boolean btnFlag;
    private boolean btnUpdateFlag;
    private boolean limitFlag;
    private String billdetainPeriod;
    private List<SelectItem> billTypeList;
    private List<SelectItem> acctNoOption;
    private List<SelectItem> collectBankTypeList;
    private List<SelectItem> collectAlphaCodeList;
    private List<OutwardBillsGrid> gridDetail;
    private OutwardBillsGrid currentItem = new OutwardBillsGrid();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private HttpServletRequest req;
    int currentRow;

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
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

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public String getCollectAlphaCode() {
        return collectAlphaCode;
    }

    public void setCollectAlphaCode(String collectAlphaCode) {
        this.collectAlphaCode = collectAlphaCode;
    }

    public List<SelectItem> getCollectAlphaCodeList() {
        return collectAlphaCodeList;
    }

    public void setCollectAlphaCodeList(List<SelectItem> collectAlphaCodeList) {
        this.collectAlphaCodeList = collectAlphaCodeList;
    }

    public String getCollectBankAddress() {
        return collectBankAddress;
    }

    public void setCollectBankAddress(String collectBankAddress) {
        this.collectBankAddress = collectBankAddress;
    }

    public String getCollectBankName() {
        return collectBankName;
    }

    public void setCollectBankName(String collectBankName) {
        this.collectBankName = collectBankName;
    }

    public String getCollectBankType() {
        return collectBankType;
    }

    public void setCollectBankType(String collectBankType) {
        this.collectBankType = collectBankType;
    }

    public List<SelectItem> getCollectBankTypeList() {
        return collectBankTypeList;
    }

    public void setCollectBankTypeList(List<SelectItem> collectBankTypeList) {
        this.collectBankTypeList = collectBankTypeList;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDetainBillPeriod() {
        return detainBillPeriod;
    }

    public void setDetainBillPeriod(String detainBillPeriod) {
        this.detainBillPeriod = detainBillPeriod;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInFavOf() {
        return inFavOf;
    }

    public void setInFavOf(String inFavOf) {
        this.inFavOf = inFavOf;
    }

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
        this.instAmt = instAmt;
    }

    public Date getInstDate() {
        return instDate;
    }

    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
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

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isBankFlag() {
        return bankFlag;
    }

    public void setBankFlag(boolean bankFlag) {
        this.bankFlag = bankFlag;
    }

    public boolean isBankPanelFlag() {
        return bankPanelFlag;
    }

    public void setBankPanelFlag(boolean bankPanelFlag) {
        this.bankPanelFlag = bankPanelFlag;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    public boolean isBtnUpdateFlag() {
        return btnUpdateFlag;
    }

    public void setBtnUpdateFlag(boolean btnUpdateFlag) {
        this.btnUpdateFlag = btnUpdateFlag;
    }

    public OutwardBillsGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(OutwardBillsGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<OutwardBillsGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<OutwardBillsGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isLimitFlag() {
        return limitFlag;
    }

    public void setLimitFlag(boolean limitFlag) {
        this.limitFlag = limitFlag;
    }

    public String getBillDetainUpto() {
        return billDetainUpto;
    }

    public void setBillDetainUpto(String billDetainUpto) {
        this.billDetainUpto = billDetainUpto;
    }

    public String getBilldetainPeriod() {
        return billdetainPeriod;
    }

    public void setBilldetainPeriod(String billdetainPeriod) {
        this.billdetainPeriod = billdetainPeriod;
    }

    /** Creates a new instance of OutwardBillBooking */
    public OutwardBillBooking() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            outwardBills = (OutwardFacadeRemote) ServiceLocator.getInstance().lookup("OutwardFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
           // setUserName(req.getRemoteUser());
            setUserName("manager1");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setInstDate(date);
            this.setErrorMessage("");
            this.setMessage("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBtnFlag(true);
            this.setBtnUpdateFlag(true);
            this.setLimitFlag(false);
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("BP"));
            collectBankTypeList = new ArrayList<SelectItem>();
            collectBankTypeList.add(new SelectItem("--Select--"));
            collectBankTypeList.add(new SelectItem("Our Bank"));
            collectBankTypeList.add(new SelectItem("Other Bank"));
            collectBankTypeList.add(new SelectItem("Direct Party"));
            acTypeCombo();
            alphaCodeCombo();
            gridLoad();
            limitFlagChk();
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void acTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = outwardBills.acctTypeCombo(this.orgnBrCode);
            acctNoOption = new ArrayList<SelectItem>();
            acctNoOption.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctNoOption.add(new SelectItem(ee.toString()));
                }
            }

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void limitFlagChk() {
        try {
            List resultList = new ArrayList();
            resultList = outwardBills.limitFlagChk(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(0).toString().equalsIgnoreCase("1")) {
                    this.setLimitFlag(true);
                }
            } else {
                this.setLimitFlag(false);
            }

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void setBilDetainDt() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setBillDetainUpto("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.billdetainPeriod.equalsIgnoreCase("") || this.billdetainPeriod.length() == 0) {
            this.setErrorMessage("Enter bill Detain Period.");
            return;
        }
        Matcher billdetainPeriodCheck = p.matcher(billdetainPeriod);
        if (!billdetainPeriodCheck.matches()) {
            this.setErrorMessage("Please Enter Valid  bill Detain Period.");
            return;
        }
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List resultList = new ArrayList();
            resultList = outwardBills.setbillDetainDate(Integer.parseInt(this.billdetainPeriod), ymd.format(this.instDate));
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                this.setBillDetainUpto(ele.get(0).toString());
            } else {
                this.setBillDetainUpto("");
                return;
            }

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void alphaCodeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = outwardBills.alphaCodeCombo(this.orgnBrCode);
            collectAlphaCodeList = new ArrayList<SelectItem>();
            collectAlphaCodeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    collectAlphaCodeList.add(new SelectItem(ee.toString()));
                }
            }

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<OutwardBillsGrid>();
        try {
            List resultList = new ArrayList();
            resultList = outwardBills.gridLoad(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    OutwardBillsGrid detail = new OutwardBillsGrid();
                    detail.setsNo(i + 1);
                    detail.setAcNo(ele.get(1).toString());
                    detail.setInstNo(ele.get(2).toString());
                    detail.setInstAmt(Double.parseDouble(ele.get(3).toString()));
                    detail.setInstDt(ele.get(6).toString());
                    detail.setComm(Double.parseDouble(ele.get(4).toString()));
                    detail.setPostage(Double.parseDouble(ele.get(5).toString()));
                    detail.setBankName(ele.get(7).toString());
                    detail.setBankAddress(ele.get(8).toString());
                    detail.setBillType(ele.get(0).toString());
                    detail.setEnterBy(ele.get(9).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void collectAlphaCodeLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCollectBankName("");
        this.setCollectBankAddress("");
        try {
            if (this.collectAlphaCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Inst. Alpha Code.");
                return;
            }
            List resultList = new ArrayList();
            resultList = outwardBills.alphaCodeLostFocus(this.orgnBrCode, this.collectAlphaCode);
            if (resultList.isEmpty()) {
                this.setErrorMessage("");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCollectBankName(ele.get(0).toString());
                    this.setCollectBankAddress(ele.get(1).toString());
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void collectBankTypeComboLostFocus() {
        try {
            if (this.collectBankType.equalsIgnoreCase("--Select--")) {
                this.setCollectAlphaCode("--Select--");
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setBankFlag(false);
                this.setBankPanelFlag(false);
                this.setBtnFlag(true);
            } else if (this.collectBankType.equalsIgnoreCase("Our Bank")) {
                this.setCollectAlphaCode("--Select--");
                this.setBankFlag(false);
                this.setBankPanelFlag(true);
                this.setBtnFlag(false);
            } else if (this.collectBankType.equalsIgnoreCase("Other Bank")) {
                this.setCollectAlphaCode("--Select--");
                this.setBankFlag(true);
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setBankPanelFlag(false);
                this.setBtnFlag(false);
            } else if (this.collectBankType.equalsIgnoreCase("Direct Party")) {
                this.setCollectAlphaCode("--Select--");
                this.setBankFlag(true);
                this.setBankPanelFlag(false);
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setBtnFlag(false);
            }
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (OutwardBillsGrid item : gridDetail) {
            if (item.getAcNo().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            List resultList = outwardBills.setCustName(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
            if (!resultList.isEmpty()) {
                String actype = fts.getAccountCode(currentItem.getAcNo());
                if (!actype.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1(ele.get(1).toString());
                        this.setJtName2(ele.get(2).toString());
                    }
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1("");
                        this.setJtName2("");
                    }
                }
                setValues();
                this.setBtnUpdateFlag(false);
            } else {
                return;
            }
            gridLoad();
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }

    }

    public void setValues() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            List resultList = outwardBills.setvaluesInFielde(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setActNo(ele.get(0).toString());
                    this.setOldAcctNo(ele.get(0).toString());
                   // this.setAcctType(ele.get(0).toString().substring(2, 4));
                    this.setAcctNo(ele.get(0).toString());
                    this.setInstNo(ele.get(2).toString());
                    if (ele.get(10).toString().contains(":")) {
                        int n = ele.get(10).toString().indexOf(":");
                        String fav = ele.get(10).toString().substring(0, n);
                        this.setInFavOf(fav);
                    } else {
                        this.setInFavOf(ele.get(10).toString());
                    }
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    //this.setInstAmt(ele.get(3).toString());
                    this.setInstAmt(String.valueOf(formatter.format(new BigDecimal(ele.get(3).toString()).doubleValue())));
                    this.setInstDate(sdf.parse(ele.get(6).toString()));

                    this.setBillType(ele.get(1).toString());
                    if (ele.get(9).toString().equalsIgnoreCase("0")) {
                        if (ele.get(10).toString().contains(":")) {
                            this.setCollectBankType("Direct Party");
                            this.setCollectAlphaCode("--Select--");
                            this.setBankFlag(true);
                            this.setBankPanelFlag(false);
                        } else {
                            this.setCollectBankType("Other Bank");
                            this.setCollectAlphaCode("--Select--");
                            this.setBankFlag(true);
                            this.setBankPanelFlag(false);
                        }

                    } else {
                        this.setCollectBankType("Our Bank");
                        this.setCollectAlphaCode(ele.get(9).toString());
                        this.setBankFlag(false);
                        this.setBankPanelFlag(true);
                    }

                    this.setCollectBankName(ele.get(7).toString());
                    this.setCollectBankAddress(ele.get(8).toString());
                    //this.setComm(ele.get(4).toString());
                    //this.setPostage(ele.get(5).toString());
                    this.setComm(String.valueOf(formatter.format(new BigDecimal(ele.get(4).toString()).doubleValue())));
                    this.setPostage(String.valueOf(formatter.format(new BigDecimal(ele.get(5).toString()).doubleValue())));
                    this.setBilldetainPeriod(ele.get(11).toString());
                    this.setInvNo(ele.get(12).toString());
                    this.setBillDetainUpto(ele.get(13).toString());
                }
            } else {
                return;
            }
            gridLoad();
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }

    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setBtnUpdateFlag(true);
        try {
            String result = outwardBills.deleteRecord(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Record Not Deleted.");
                return;
            }
            gridLoad();
       //     this.setAcctType("--Select--");
            this.setCollectAlphaCode("--Select--");
            this.setCollectBankType("--Select--");
            this.setCollectBankName("");
            this.setCollectBankAddress("");
            this.setComm("");
            this.setPostage("");
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setAcctNo("");
            this.setActNo("");
            this.setOldAcctNo("");
            this.setInstAmt("");
            Date date = new Date();
            this.setInstDate(date);
            this.setInstNo("");
            this.setInstAmt("");
            this.setInvNo("");
            this.setDetainBillPeriod("");
            this.setBillDetainUpto("");
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setBilldetainPeriod("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessage("");
     //   this.setAcctType("--Select--");
        this.setCollectAlphaCode("--Select--");
        this.setCollectBankType("--Select--");
        this.setCollectBankName("");
        this.setCollectBankAddress("");
        this.setComm("");
        this.setPostage("");
        this.setCustName("");
        this.setJtName1("");
        this.setJtName2("");
        this.setAcctNo("");
        this.setActNo("");
        this.setOldAcctNo("");
        this.setInstAmt("");
        Date date = new Date();
        this.setInstDate(date);
        this.setInstNo("");
        this.setInstAmt("");
        this.setInvNo("");
        this.setDetainBillPeriod("");
        this.setBillDetainUpto("");
        this.setBillType("--Select--");
        this.setInFavOf("");
        this.setBilldetainPeriod("");
        this.setBankFlag(false);
        this.setBankPanelFlag(true);
        this.setBtnUpdateFlag(true);
        this.setBtnFlag(true);
    }

    public void getAccountDetail() {
        this.setErrorMessage("");
        this.setMessage("");

//        if (this.acctType.equalsIgnoreCase("--Select--")) {
//            this.setErrorMessage("Please Select Account Type.");
//            return;
//        }
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
//            this.setErrorMessage("Please Enter the Account No.");
//            return;
//        }
//        Matcher acctNoCheck = p.matcher(acctNo);
//        if (!acctNoCheck.matches()) {
//            this.setErrorMessage("Please Enter Valid  Account No.");
//            return;
//        }
//        int length = acctNo.length();
//        int addedZero = 6 - length;
//        for (int i = 1; i <= addedZero; i++) {
//            acctNo = "0" + acctNo;
//        }
//        actNo = this.orgnBrCode + this.acctType + acctNo + "01";
             try {
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setErrorMessage("Please Enter the Account No.");             
                return;
            }
            if (!oldAcctNo.matches("[0-9a-zA-z]*")) {                  
                    this.setErrorMessage("Please Enter Valid  Account No.");
                    return ;
                }
            if (oldAcctNo.length() != 12) {
                this.setErrorMessage("Account number is not valid.");
                return;
            }
            actNo = fts.getNewAccountNumber(oldAcctNo);
            if(actNo.equalsIgnoreCase("A/c number does not exist")){
                this.setMessage("A/c number does not exist.");
                return;
            }
            else if(!orgnBrCode.equalsIgnoreCase(fts.getCurrentBrnCode(actNo))){
                this.setMessage("Unauthorized Account No to proceeds further.");
                return;
            }else{
                 setAcctNo(actNo);
             }
      
            List resultList = new ArrayList();
            resultList = outwardBills.acNoLostFocus(this.orgnBrCode, this.actNo);
            if (resultList.isEmpty()) {
                this.setErrorMessage("Please Fill The Relevent/Active Account No.");
                this.setAcctNo("");
                this.setActNo("");
                this.setCustName("");
                this.setJtName1("");
                this.setJtName2("");
                return;
            } else {
                String actype = fts.getAccountCode(actNo);
                if (actype.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1("");
                        this.setJtName2("");
                        this.setActNo(actNo);
                    }
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1(ele.get(1).toString());
                        this.setJtName2(ele.get(2).toString());
                        this.setActNo(actNo);
                    }
                }
            }


        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void instNoLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instNo == null || this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
                this.setErrorMessage("Please Enter the Inst. No.");
                return;
            }
            Matcher instNoCheck = p.matcher(instNo);
            if (!instNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Inst. No.");
                return;
            }
            if (this.instNo.contains(".")) {
                this.setErrorMessage("Please Enter Valid  Inst. No.");
                return;
            }
            int length = instNo.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                instNo = "0" + instNo;
            }
            this.setInstNo(instNo);
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }
    String alpha;
    String com;
    String post;
    String billDt;

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.collectBankType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Collecting Bank Type.");
                return;
            }
            if (this.instDate == null) {
                this.setErrorMessage("Please Enter LR/RR/PP/AW DL.");
                return;
            }
            if (this.collectBankType.equalsIgnoreCase("Other Bank") || this.collectBankType.equalsIgnoreCase("Direct Party")) {
                alpha = "0";
            } else if (this.collectBankType.equalsIgnoreCase("Our Bank")) {
                alpha = this.collectAlphaCode;
            }

            if (this.comm.equalsIgnoreCase("") || this.comm.length() == 0) {
                com = "0";
            } else {
                com = this.comm;
            }
            if (this.postage.equalsIgnoreCase("") || this.postage.length() == 0) {
                post = "0";
            } else {
                post = this.postage;
            }
            if (billDetainUpto.equalsIgnoreCase("")) {
                this.setErrorMessage("Bill Detain Date Cannot be Empty.");
                return;
            } else {
                billDt = billDetainUpto.substring(6) + billDetainUpto.substring(3, 5) + billDetainUpto.substring(0, 2);
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = outwardBills.saveBtnOutWardBill(this.orgnBrCode, this.actNo, this.billType, this.alpha, this.instNo, this.instAmt, ymd.format(this.instDate), this.com, this.post, this.inFavOf, this.userName, this.invNo, this.collectBankName, this.collectBankAddress, this.billdetainPeriod, billDt, this.limitFlag, this.collectBankType);
            if (result.equals("true")) {
                this.setMessage("Record Saved Successfully.");
           //     this.setAcctType("--Select--");
                this.setCollectAlphaCode("--Select--");
                this.setCollectBankType("--Select--");
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setComm("");
                this.setPostage("");
                this.setCustName("");
                this.setJtName1("");
                this.setJtName2("");
                
                this.setInstAmt("");
                Date date = new Date();
                this.setInstDate(date);
                this.setInstNo("");
                this.setInstAmt("");
                this.setInvNo("");
                this.setDetainBillPeriod("");
                this.setBillDetainUpto("");
                this.setBillType("--Select--");
                this.setInFavOf("");
                this.setBilldetainPeriod("");
                this.setBankFlag(false);
                this.setBankPanelFlag(true);
                this.setBtnFlag(true);
                this.setBtnUpdateFlag(true);
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
            this.setAcctNo("");
            this.setActNo("");
            this.setOldAcctNo("");
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void updateDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.collectBankType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Collecting Bank Type.");
                return;
            }
            if (this.instDate == null) {
                this.setErrorMessage("Please Enter LR/RR/PP/AW DL.");
                return;
            }
            if (this.collectBankType.equalsIgnoreCase("Other Bank") || this.collectBankType.equalsIgnoreCase("Direct Party")) {
                alpha = "0";
            } else if (this.collectBankType.equalsIgnoreCase("Our Bank")) {
                alpha = this.collectAlphaCode;
            }

            if (this.comm.equalsIgnoreCase("") || this.comm.length() == 0) {
                com = "0";
            } else {
                com = this.comm;
            }
            if (this.postage.equalsIgnoreCase("") || this.postage.length() == 0) {
                post = "0";
            } else {
                post = this.postage;
            }
            if (billDetainUpto.equalsIgnoreCase("") || billDetainUpto.length() == 0) {
                this.setErrorMessage("Bill Detain Date Cannot be Empty.");
                return;
            } else {
                billDt = billDetainUpto.substring(6) + billDetainUpto.substring(3, 5) + billDetainUpto.substring(0, 2);
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            String result = outwardBills.updateBtnOutWardBill(this.orgnBrCode, this.actNo, this.billType, this.alpha, this.instNo, this.instAmt, ymd.format(this.instDate), this.com, this.post, this.inFavOf, this.userName, this.invNo, this.collectBankName, this.collectBankAddress, this.billdetainPeriod, billDt, this.collectBankType);
            if (result.equals("true")) {
                this.setMessage("Record Updated Successfully.");
            //    this.setAcctType("--Select--");
                this.setCollectAlphaCode("--Select--");
                this.setCollectBankType("--Select--");
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setComm("");
                this.setPostage("");
                this.setCustName("");
                this.setJtName1("");
                this.setJtName2("");
                
                this.setInstAmt("");
                Date date = new Date();
                this.setInstDate(date);
                this.setInstNo("");
                this.setInstAmt("");
                this.setInvNo("");
                this.setDetainBillPeriod("");
                this.setBillDetainUpto("");
                this.setBillType("--Select--");
                this.setInFavOf("");
                this.setBilldetainPeriod("");
                this.setBankFlag(false);
                this.setBankPanelFlag(true);
                this.setBtnFlag(true);
                this.setBtnUpdateFlag(true);
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
            this.setAcctNo("");
            this.setActNo("");
            this.setOldAcctNo("");

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public String exitForm() {
        resetForm();
        return "case1";
    }
}
