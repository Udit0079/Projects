/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.bill;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.web.pojo.bill.OutwardChequesGrid;
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
public class OutwardCheques {

    Context ctx;
    OutwardFacadeRemote outwardCheque;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
   // private String acctType;
    private String acctNo;
    private String oldAcctNo;  
    private String custName;
    private String jtName1;
    private String jtName2;
    private String billType;
    private Date instDate;
    private String instAmt;
    private String instNo;
    private String inFavOf;
    private String instBankType;
    private String instAlphaCode;
    private String collectBankType;
    private String collectAlphaCode;
    private String instBankName;
    private String instBankAddress;
    private String collectBankName;
    private String collectBankAddress;
    private String actNo;
    private boolean bankFlag;
    private boolean bankPanelFlag;
    private boolean bankFlag1;
    private boolean bankPanelFlag1;
    private boolean btnFlag;
    private boolean btnUpdateFlag;
    private String comm;
    private String postage;
    private List<SelectItem> billTypeList;
    private List<SelectItem> acctNoOption;
    private List<SelectItem> instBankTypeList;
    private List<SelectItem> collectBankTypeList;
    private List<SelectItem> instAlphaCodeList;
    private List<SelectItem> collectAlphaCodeList;
    private List<OutwardChequesGrid> gridDetail;
    private HttpServletRequest req;
    int currentRow;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private OutwardChequesGrid currentItem = new OutwardChequesGrid();

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

    public String getInstAlphaCode() {
        return instAlphaCode;
    }

    public void setInstAlphaCode(String instAlphaCode) {
        this.instAlphaCode = instAlphaCode;
    }

    public List<SelectItem> getInstAlphaCodeList() {
        return instAlphaCodeList;
    }

    public void setInstAlphaCodeList(List<SelectItem> instAlphaCodeList) {
        this.instAlphaCodeList = instAlphaCodeList;
    }

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
        this.instAmt = instAmt;
    }

    public String getInstBankAddress() {
        return instBankAddress;
    }

    public void setInstBankAddress(String instBankAddress) {
        this.instBankAddress = instBankAddress;
    }

    public String getInstBankName() {
        return instBankName;
    }

    public void setInstBankName(String instBankName) {
        this.instBankName = instBankName;
    }

    public String getInstBankType() {
        return instBankType;
    }

    public void setInstBankType(String instBankType) {
        this.instBankType = instBankType;
    }

    public List<SelectItem> getInstBankTypeList() {
        return instBankTypeList;
    }

    public void setInstBankTypeList(List<SelectItem> instBankTypeList) {
        this.instBankTypeList = instBankTypeList;
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

    public OutwardChequesGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(OutwardChequesGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<OutwardChequesGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<OutwardChequesGrid> gridDetail) {
        this.gridDetail = gridDetail;
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

    public boolean isBankFlag1() {
        return bankFlag1;
    }

    public void setBankFlag1(boolean bankFlag1) {
        this.bankFlag1 = bankFlag1;
    }

    public boolean isBankPanelFlag1() {
        return bankPanelFlag1;
    }

    public void setBankPanelFlag1(boolean bankPanelFlag1) {
        this.bankPanelFlag1 = bankPanelFlag1;
    }

    public boolean isBtnUpdateFlag() {
        return btnUpdateFlag;
    }

    public void setBtnUpdateFlag(boolean btnUpdateFlag) {
        this.btnUpdateFlag = btnUpdateFlag;
    }

    /** Creates a new instance of OutwardCheques */
    public OutwardCheques() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            this.setUserName(req.getRemoteUser());
          //  setUserName("manager1");
            outwardCheque = (OutwardFacadeRemote) ServiceLocator.getInstance().lookup("OutwardFacade");
             fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setInstDate(date);
            this.setErrorMessage("");
            this.setMessage("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBankFlag1(false);
            this.setBankPanelFlag1(true);
            this.setBtnFlag(true);
            this.setBtnUpdateFlag(true);
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("BC"));
            instBankTypeList = new ArrayList<SelectItem>();
            instBankTypeList.add(new SelectItem("--Select--"));
            instBankTypeList.add(new SelectItem("Our Bank"));
            instBankTypeList.add(new SelectItem("Other Bank"));
            collectBankTypeList = new ArrayList<SelectItem>();
            collectBankTypeList.add(new SelectItem("--Select--"));
            collectBankTypeList.add(new SelectItem("Same Bank"));
            collectBankTypeList.add(new SelectItem("Our Bank"));
            collectBankTypeList.add(new SelectItem("Other Bank"));
            acTypeCombo();
            alphaCodeCombo();
            gridLoad();
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
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
            resultList = outwardCheque.acctTypeComboOutwardCheque(this.orgnBrCode);
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

    public void alphaCodeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = outwardCheque.alphaCodeCombo(this.orgnBrCode);
            instAlphaCodeList = new ArrayList<SelectItem>();
            instAlphaCodeList.add(new SelectItem("--Select--"));
            collectAlphaCodeList = new ArrayList<SelectItem>();
            collectAlphaCodeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    instAlphaCodeList.add(new SelectItem(ee.toString()));
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
        gridDetail = new ArrayList<OutwardChequesGrid>();
        try {
            List resultList = new ArrayList();
            resultList = outwardCheque.gridLoadOutwardCheque(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    OutwardChequesGrid detail = new OutwardChequesGrid();
                    detail.setsNo(i + 1);
                    detail.setAcNo(ele.get(0).toString());
                    detail.setInstNo(ele.get(1).toString());
                    detail.setInstAmt(Double.parseDouble(ele.get(2).toString()));
                    detail.setInstDt(ele.get(3).toString());
                    detail.setInFavOf(ele.get(4).toString());
                    detail.setBankName(ele.get(5).toString());
                    detail.setBankAddress(ele.get(6).toString());
                    detail.setBillType(ele.get(7).toString());
                    detail.setEnterBy(ele.get(8).toString());
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

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (OutwardChequesGrid item : gridDetail) {
            if (item.getAcNo().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String actype = fts.getAccountCode(currentItem.getAcNo());
            if (!actype.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                List resultList = outwardCheque.setCustName(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1(ele.get(1).toString());
                        this.setJtName2(ele.get(2).toString());
                    }
                    setValues();
                    this.setBtnUpdateFlag(false);
                } else {
                    return;
                }
            } else {
                List resultList = outwardCheque.setCustName(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1("");
                        this.setJtName2("");
                    }
                    setValues();
                    this.setBtnUpdateFlag(false);
                } else {
                    return;
                }
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
            List resultList = outwardCheque.setvaluesInFieldeOutCheque(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setActNo(ele.get(0).toString());
                 //   this.setAcctType(ele.get(0).toString().substring(2, 4));
                    this.setOldAcctNo(ele.get(0).toString());
                    this.setAcctNo(ele.get(0).toString());
                    this.setInstNo(ele.get(6).toString());
                    this.setInFavOf(ele.get(3).toString());
                    //this.setInstAmt(ele.get(7).toString());
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    this.setInstAmt(String.valueOf(formatter.format(new BigDecimal(ele.get(7).toString()).doubleValue())));
                    this.setInstDate(sdf.parse(ele.get(10).toString()));
                    this.setInstBankName(ele.get(4).toString());
                    this.setInstBankAddress(ele.get(5).toString());
                    this.setInstAlphaCode(ele.get(2).toString());
                    if (ele.get(2).toString().equalsIgnoreCase("0")) {
                        this.setInstBankType("Other Bank");
                        this.setInstAlphaCode("--Select--");
                        this.setBankFlag(true);
                        this.setBankPanelFlag(false);
                    } else {
                        this.setInstBankType("Our Bank");
                        this.setInstAlphaCode(ele.get(2).toString());
                        this.setBankFlag(false);
                        this.setBankPanelFlag(true);
                    }
                    this.setBillType(ele.get(1).toString());
                    if (ele.get(11).toString().equalsIgnoreCase("0")) {
                        this.setCollectBankType("Other Bank");
                        this.setInstAlphaCode("--Select--");
                        this.setBankFlag(true);
                        this.setBankPanelFlag(false);
                    } else {
                        this.setCollectBankType("Our Bank");
                        this.setCollectAlphaCode(ele.get(11).toString());
                        this.setBankFlag(false);
                        this.setBankPanelFlag(true);
                    }
                    this.setCollectBankName(ele.get(12).toString());
                    this.setCollectBankAddress(ele.get(13).toString());
                    this.setComm(String.valueOf(formatter.format(new BigDecimal(ele.get(8).toString()).doubleValue())));
                    this.setPostage(String.valueOf(formatter.format(new BigDecimal(ele.get(9).toString()).doubleValue())));
//                    this.setComm(ele.get(8).toString());
//                    this.setPostage(ele.get(9).toString());
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
            String result = outwardCheque.deleteRecord(this.orgnBrCode, currentItem.getBillType(), currentItem.getAcNo(), currentItem.getInstNo(), currentItem.getInstAmt());
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Record Not Deleted.");
                return;
            }
            gridLoad();
          //  this.setAcctType("--Select--");
            this.setInstAlphaCode("--Select--");
            this.setInstBankType("--Select--");
            this.setInstBankName("");
            this.setInstBankAddress("");
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
            this.setInstAmt("");
            Date date = new Date();
            this.setInstDate(date);
            this.setInstNo("");
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void instAlphaCodeLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setInstBankName("");
        this.setInstBankAddress("");
        try {
            if (this.instAlphaCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Inst. Alpha Code.");
                return;
            }
            List resultList = new ArrayList();
            resultList = outwardCheque.alphaCodeLostFocus(this.orgnBrCode, this.instAlphaCode);
            if (resultList.isEmpty()) {
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setInstBankName(ele.get(0).toString());
                    this.setInstBankAddress(ele.get(1).toString());
                }
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
        this.setInstBankName("");
        this.setInstBankAddress("");
        try {
            if (this.collectAlphaCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Collecting Alpha Code.");
                return;
            }
            List resultList = new ArrayList();
            resultList = outwardCheque.alphaCodeLostFocus(this.orgnBrCode, this.collectAlphaCode);
            if (resultList.isEmpty()) {
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

    public void instBankTypeComboLostFocus() {
        try {
            if (this.instBankType.equalsIgnoreCase("--Select--")) {
                this.setInstAlphaCode("--Select--");
                this.setInstBankAddress("");
                this.setInstBankName("");
                this.setBankFlag(false);
                this.setBankPanelFlag(false);
                this.setBtnFlag(true);
            } else if (this.instBankType.equalsIgnoreCase("Our Bank")) {
                this.setInstAlphaCode("--Select--");
                this.setInstBankAddress("");
                this.setInstBankName("");
                this.setBankFlag(false);
                this.setBankPanelFlag(true);
                this.setBtnFlag(false);
            } else if (this.instBankType.equalsIgnoreCase("Other Bank")) {
                this.setInstAlphaCode("--Select--");
                this.setBankFlag(true);
                this.setInstBankAddress("");
                this.setInstBankName("");
                this.setBankPanelFlag(false);
                this.setBtnFlag(false);
            }
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
                this.setBankFlag1(false);
                this.setBankPanelFlag1(false);
                this.setBtnFlag(true);
            } else if (this.collectBankType.equalsIgnoreCase("Same Bank")) {
                this.setCollectAlphaCode(instAlphaCode);
                this.setCollectBankName(instBankName);
                this.setCollectBankAddress(instBankAddress);
                this.setBankFlag1(true);
                this.setBankPanelFlag1(true);
                this.setBtnFlag(false);
            } else if (this.collectBankType.equalsIgnoreCase("Our Bank")) {
                this.setCollectAlphaCode("--Select--");
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setBankFlag1(false);
                this.setBankPanelFlag1(true);
                this.setBtnFlag(false);
            } else if (this.collectBankType.equalsIgnoreCase("Other Bank")) {
                this.setCollectAlphaCode("--Select--");
                this.setBankFlag1(true);
                this.setCollectBankName("");
                this.setCollectBankAddress("");
                this.setBankPanelFlag1(false);
                this.setBtnFlag(false);

            }
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }
    String alpha1;
    String alpha2;
    String com;
    String post;

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.instAmt != null) {
                if (this.instAmt.contains(".")) {
                    if (this.instAmt.indexOf(".") != this.instAmt.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Instrument Amount.");
                        return;
                    } else if (this.instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Instrument Amount Upto Two Decimal Places.");
                        return;
                    }
                }
            }
            if (this.instDate == null) {
                this.setErrorMessage("Please Enter Inst. Date.");
                return;
            }
            if (this.comm != null) {
                if (this.comm.contains(".")) {
                    if (this.comm.indexOf(".") != this.comm.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Commission.");
                        return;
                    } else if (this.comm.substring(comm.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Commission Upto Two Decimal Places.");
                        return;
                    }
                }
            }
            if (this.postage != null) {
                if (this.postage.contains(".")) {
                    if (this.postage.indexOf(".") != this.postage.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Postage.");
                        return;
                    } else if (this.postage.substring(postage.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Postage Upto Two Decimal Places.");
                        return;
                    }
                }
            }
            if (this.instBankType.equalsIgnoreCase("Other Bank") && this.collectBankType.equalsIgnoreCase("Same Bank")) {
                alpha1 = "0";
                alpha2 = "0";
            } else if (this.instBankType.equalsIgnoreCase("Other Bank") && this.collectBankType.equalsIgnoreCase("Our Bank")) {
                alpha1 = "0";
                alpha2 = this.collectAlphaCode;
            } else if (this.instBankType.equalsIgnoreCase("Other Bank") && this.collectBankType.equalsIgnoreCase("Other Bank")) {
                alpha1 = "0";
                alpha2 = "0";
            } else if (this.instBankType.equalsIgnoreCase("Our Bank") && this.collectBankType.equalsIgnoreCase("Same Bank")) {
                alpha1 = this.instAlphaCode;
                alpha2 = this.instAlphaCode;
            } else if (this.instBankType.equalsIgnoreCase("Our Bank") && this.collectBankType.equalsIgnoreCase("Our Bank")) {
                alpha1 = this.instAlphaCode;
                alpha2 = this.collectAlphaCode;
            } else if (this.instBankType.equalsIgnoreCase("Our Bank") && this.collectBankType.equalsIgnoreCase("Other Bank")) {
                alpha1 = this.instAlphaCode;
                alpha2 = "0";
            }
            if (this.comm == null || this.comm.equalsIgnoreCase("") || this.comm.length() == 0) {
                com = "0";
            } else {
                com = this.comm;
            }
            if (this.postage == null || this.postage.equalsIgnoreCase("") || this.postage.length() == 0) {
                post = "0";
            } else {
                post = this.postage;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = outwardCheque.saveBtnOutWardCheque(this.orgnBrCode, this.actNo, this.billType, this.alpha1, this.instBankName, this.instBankAddress, this.instNo, this.instAmt, ymd.format(this.instDate), this.com, this.post, this.inFavOf, this.userName, this.alpha2, this.collectBankName, this.collectBankAddress);
            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Record Saved Successfully.");
             //   this.setAcctType("--Select--");
                this.setInstAlphaCode("--Select--");
                this.setInstBankType("--Select--");
                this.setInstBankName("");
                this.setInstBankAddress("");
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
                this.setInstAmt("");
                Date date = new Date();
                this.setInstDate(date);
                this.setInstNo("");
                this.setBillType("--Select--");
                this.setInFavOf("");
                this.setBankFlag(false);
                this.setBankPanelFlag(true);
                this.setBankFlag1(false);
                this.setBankPanelFlag1(true);
                this.setBtnFlag(true);
                this.setBtnUpdateFlag(true);
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void updateDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.instAmt != null) {
                if (this.instAmt.contains(".")) {
                    if (this.instAmt.indexOf(".") != this.instAmt.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Instrument Amount.");
                        return;
                    } else if (this.instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Instrument Amount Upto Two Decimal Places.");
                        return;
                    }
                }
            }
            if (this.instDate == null) {
                this.setErrorMessage("Please Enter Inst. Date.");
                return;
            }
            if (this.comm != null) {
                if (this.comm.contains(".")) {
                    if (this.comm.indexOf(".") != this.comm.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Commission.");
                        return;
                    } else if (this.comm.substring(comm.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Commission Upto Two Decimal Places.");
                        return;
                    }
                }
            }
            if (this.postage != null) {
                if (this.postage.contains(".")) {
                    if (this.postage.indexOf(".") != this.postage.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Postage.");
                        return;
                    } else if (this.postage.substring(postage.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Postage Upto Two Decimal Places.");
                        return;
                    }
                }
            }
            if (this.instBankType.equalsIgnoreCase("Other Bank") && this.collectBankType.equalsIgnoreCase("Same Bank")) {
                alpha1 = "0";
                alpha2 = "0";
            } else if (this.instBankType.equalsIgnoreCase("Other Bank") && this.collectBankType.equalsIgnoreCase("Our Bank")) {
                alpha1 = "0";
                alpha2 = this.collectAlphaCode;
            } else if (this.instBankType.equalsIgnoreCase("Other Bank") && this.collectBankType.equalsIgnoreCase("Other Bank")) {
                alpha1 = "0";
                alpha2 = "0";
            } else if (this.instBankType.equalsIgnoreCase("Our Bank") && this.collectBankType.equalsIgnoreCase("Same Bank")) {
                alpha1 = this.instAlphaCode;
                alpha2 = this.instAlphaCode;
            } else if (this.instBankType.equalsIgnoreCase("Our Bank") && this.collectBankType.equalsIgnoreCase("Our Bank")) {
                alpha1 = this.instAlphaCode;
                alpha2 = this.collectAlphaCode;
            } else if (this.instBankType.equalsIgnoreCase("Our Bank") && this.collectBankType.equalsIgnoreCase("Other Bank")) {
                alpha1 = this.instAlphaCode;
                alpha2 = "0";
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
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = outwardCheque.updateBtnOutWardCheque(this.orgnBrCode, this.actNo, this.billType, this.alpha1, this.instBankName, this.instBankAddress, this.instNo, this.instAmt, ymd.format(this.instDate), this.com, this.post, this.inFavOf, this.userName, this.alpha2, this.collectBankName, this.collectBankAddress);
            if (result.equals("true")) {
                this.setMessage("Record Updated Successfully.");
            //    this.setAcctType("--Select--");
                this.setInstAlphaCode("--Select--");
                this.setInstBankType("--Select--");
                this.setInstBankName("");
                this.setInstBankAddress("");
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
                this.setInstAmt("");
                Date date = new Date();
                this.setInstDate(date);
                this.setInstNo("");
                this.setBillType("--Select--");
                this.setInFavOf("");
                this.setBankFlag(false);
                this.setBankPanelFlag(true);
                this.setBankFlag1(false);
                this.setBankPanelFlag1(true);
                this.setBtnFlag(true);
                this.setBtnUpdateFlag(true);
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void getAccountDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
//                this.setErrorMessage("Please Enter the Account No.");
//                return;
//            }
//            Matcher acctNoCheck = p.matcher(acctNo);
//            if (!acctNoCheck.matches()) {
//                this.setErrorMessage("Please Enter Valid  Account No.");
//                return;
//            }
//            int length = acctNo.length();
//            int addedZero = 6 - length;
//            for (int i = 1; i <= addedZero; i++) {
//                acctNo = "0" + acctNo;
//            }
        //    actNo = this.orgnBrCode + this.acctType + acctNo + "01";
             if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setMessage("Please Enter the Account No.");             
                return;
            }
            if (!oldAcctNo.matches("[0-9a-zA-z]*")) {                  
                    setMessage("Please Enter Valid  Account No.");
                    return ;
                }
            if (oldAcctNo.length() != 12) {
                setMessage("Account number is not valid.");
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
            resultList = outwardCheque.acNoLostFocus(this.orgnBrCode, this.actNo);
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
        try {
            this.setErrorMessage("");
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
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
            int addedZero = 10 - length;
            for (int i = 1; i <= addedZero; i++) {
                instNo = "0" + instNo;
            }
            this.setInstNo(instNo);
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
          //  this.setAcctType("--Select--");
            this.setInstAlphaCode("--Select--");
            this.setInstBankType("--Select--");
            this.setInstBankName("");
            this.setInstBankAddress("");
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
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBtnFlag(true);
            this.setBtnUpdateFlag(true);
        } catch (Exception ex) {
            message = ex.getMessage();
        }

    }

    public String exitForm() {
        try {
            resetForm();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return "case1";
    }
}
