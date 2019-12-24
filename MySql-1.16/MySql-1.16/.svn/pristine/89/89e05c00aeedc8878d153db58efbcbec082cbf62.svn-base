/*
 * CREATED BY : ROHIT KRISHNA GUPTA
 */
package com.cbs.web.controller.bill;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.web.pojo.bill.InwardBillBookingGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.InwardFacadeRemote;
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
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class InwardBillBooking {

   // Context ctx;
    InwardFacadeRemote inwardBillBooking;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String acctType;
    private String acctNo;
    private String oldAcctNo;
    private String custName;
    private String jtName1;
    private String jtName2;
    private String bankType;
    private String alphaCode;
    private String remType;
    private String billType;
    private Date instDate;
    private String instAmt;
    private String instNo;
    private String inFavOf;
    private String payableAt;
    private String refNo;
    private String bankName;
    private String bankAddress;
    private String actNo;
    private boolean bankFlag;
    private boolean bankPanelFlag;
    private boolean remFlag;
    private boolean btnFlag;
    private boolean btnUpdateFlag;
    private String labelParty;
    private String otherBankCom;
    private String otherBankPostage;
    private List<SelectItem> remTypeList;
    private List<SelectItem> billTypeList;
    private List<SelectItem> acctNoOption;
    private List<SelectItem> bankTypeList;
    private List<SelectItem> alphaCodeList;
    private List<InwardBillBookingGrid> gridDetail;
    private HttpServletRequest req;
    int currentRow;
    private InwardBillBookingGrid currentItem = new InwardBillBookingGrid();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;

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

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public List<SelectItem> getAlphaCodeList() {
        return alphaCodeList;
    }

    public void setAlphaCodeList(List<SelectItem> alphaCodeList) {
        this.alphaCodeList = alphaCodeList;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public boolean isBankFlag() {
        return bankFlag;
    }

    public void setBankFlag(boolean bankFlag) {
        this.bankFlag = bankFlag;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isBankPanelFlag() {
        return bankPanelFlag;
    }

    public void setBankPanelFlag(boolean bankPanelFlag) {
        this.bankPanelFlag = bankPanelFlag;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public List<SelectItem> getBankTypeList() {
        return bankTypeList;
    }

    public void setBankTypeList(List<SelectItem> bankTypeList) {
        this.bankTypeList = bankTypeList;
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

    public List<InwardBillBookingGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<InwardBillBookingGrid> gridDetail) {
        this.gridDetail = gridDetail;
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

    public String getLabelParty() {
        return labelParty;
    }

    public void setLabelParty(String labelParty) {
        this.labelParty = labelParty;
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

    public String getOtherBankCom() {
        return otherBankCom;
    }

    public void setOtherBankCom(String otherBankCom) {
        this.otherBankCom = otherBankCom;
    }

    public String getOtherBankPostage() {
        return otherBankPostage;
    }

    public void setOtherBankPostage(String otherBankPostage) {
        this.otherBankPostage = otherBankPostage;
    }

    public String getPayableAt() {
        return payableAt;
    }

    public void setPayableAt(String payableAt) {
        this.payableAt = payableAt;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public boolean isRemFlag() {
        return remFlag;
    }

    public void setRemFlag(boolean remFlag) {
        this.remFlag = remFlag;
    }

    public String getRemType() {
        return remType;
    }

    public void setRemType(String remType) {
        this.remType = remType;
    }

    public List<SelectItem> getRemTypeList() {
        return remTypeList;
    }

    public void setRemTypeList(List<SelectItem> remTypeList) {
        this.remTypeList = remTypeList;
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

    public InwardBillBookingGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InwardBillBookingGrid currentItem) {
        this.currentItem = currentItem;
    }

    /** Creates a new instance of InwardBillBooking */
    public InwardBillBooking() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
         //   setUserName("Shipra");
            inwardBillBooking = (InwardFacadeRemote) ServiceLocator.getInstance().lookup("InwardFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
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
            this.setLabelParty("In Favour Of :");
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("BP"));
            bankTypeList = new ArrayList<SelectItem>();
            bankTypeList.add(new SelectItem("--Select--"));
            bankTypeList.add(new SelectItem("Our Bank"));
            bankTypeList.add(new SelectItem("Other Bank"));
            bankTypeList.add(new SelectItem("Direct Party"));
            acTypeCombo();
            alphaCodeCombo();
            remTypeCombo();
            gridLoad();
            
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
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
            resultList = inwardBillBooking.acctTypeCombo(this.orgnBrCode);
            acctNoOption = new ArrayList<SelectItem>();
            acctNoOption.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctNoOption.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void alphaCodeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = inwardBillBooking.alphaCodeCombo(this.orgnBrCode);
            alphaCodeList = new ArrayList<SelectItem>();
            alphaCodeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    alphaCodeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void remTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = inwardBillBooking.remTypeCombo(this.orgnBrCode);
            remTypeList = new ArrayList<SelectItem>();
            remTypeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    remTypeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<InwardBillBookingGrid>();
        try {
            List resultList = new ArrayList();
            resultList = inwardBillBooking.gridLoad(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    InwardBillBookingGrid detail = new InwardBillBookingGrid();
                    detail.setsNo(i + 1);
                    detail.setAcNo(ele.elementAt(0).toString());
                    detail.setInstNo(ele.elementAt(1).toString());
                    detail.setInstAmt(Double.parseDouble(ele.elementAt(2).toString()));
                    detail.setInstDt(ele.elementAt(3).toString());
                    detail.setPayableAt(ele.elementAt(4).toString());
                    detail.setInFavOf(ele.elementAt(5).toString());
                    detail.setBankName(ele.elementAt(6).toString());
                    detail.setBankAddress(ele.elementAt(7).toString());
                    detail.setBillType(ele.elementAt(8).toString());
                    detail.setRemType(ele.elementAt(9).toString());
                    detail.setEnterBy(ele.elementAt(10).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void alphaCodeLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setBankName("");
        this.setBankAddress("");
        if (this.alphaCode.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please Select Alpha Code.");
            return;
        }
        try {
            List resultList = new ArrayList();
            resultList = inwardBillBooking.alphaCodeLostFocus(this.orgnBrCode, this.alphaCode);
            if (resultList.isEmpty()) {
                this.setErrorMessage("");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setBankName(ele.get(0).toString());
                    this.setBankAddress(ele.get(1).toString());
                }
            }
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void bankTypeComboLostFocus() {
        if (this.bankType.equalsIgnoreCase("--Select--")) {
            this.setAlphaCode("--Select--");
            this.setRemType("--Select--");
            this.setBankFlag(false);
            this.setBankPanelFlag(false);
            this.setBtnFlag(true);
            this.setLabelParty("In Favour Of :");
        } else if (this.bankType.equalsIgnoreCase("Our Bank")) {
            this.setAlphaCode("--Select--");
            this.setRemType("--Select--");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBtnFlag(false);
            this.setLabelParty("In Favour Of :");
        } else if (this.bankType.equalsIgnoreCase("Other Bank")) {
            this.setAlphaCode("--Select--");
            this.setRemType("--Select--");
            this.setBankFlag(true);
            this.setBankAddress("");
            this.setBankName("");
            this.setBankPanelFlag(false);
            this.setBtnFlag(false);
            this.setLabelParty("In Favour Of :");
        } else if (this.bankType.equalsIgnoreCase("Direct Party")) {
            this.setAlphaCode("--Select--");
            this.setRemType("--Select--");
            this.setBankFlag(true);
            this.setBankAddress("");
            this.setBankName("");
            this.setBtnFlag(false);
            this.setBankPanelFlag(false);
            this.setLabelParty("Party Name :");
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
            
          //  actNo = this.orgnBrCode + this.acctType + acctNo + "01";
            List resultList = new ArrayList();
            resultList = inwardBillBooking.acNoLostFocus(this.orgnBrCode, this.actNo);
            if (resultList.isEmpty()) {
                this.setErrorMessage("Please Fill The Relevent/Active Account No.");
                this.setAcctNo("");
                this.setActNo("");
                this.setCustName("");
                this.setJtName1("");
                this.setJtName2("");
                return;
            } else {
               //  String actype = this.actNo.substring(2, 4);
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
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void instNoLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
                this.setErrorMessage("Please Enter the LR/RR/PP/AW No.");
                return;
            }
            Matcher instNoCheck = p.matcher(instNo);
            if (!instNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  LR/RR/PP/AW No.");
                return;
            }
            if (this.instNo.contains(".")) {
                this.setErrorMessage("Please Enter Valid  LR/RR/PP/AW No.");
                return;
            }
            int length = instNo.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                instNo = "0" + instNo;
            }
            this.setInstNo(instNo);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (InwardBillBookingGrid item : gridDetail) {
            if (item.getAcNo().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            List resultList = inwardBillBooking.setCustName(this.orgnBrCode, currentItem.getAcNo());
            if (!resultList.isEmpty()) {
               // String actype = currentItem.getAcNo().substring(2, 4);
                String actype = fts.getAccountCode(currentItem.getAcNo());
                if (actype.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1("");
                        this.setJtName2("");
                    }
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCustName(ele.get(0).toString());
                        this.setJtName1(ele.get(1).toString());
                        this.setJtName2(ele.get(2).toString());
                    }
                }
                setValues();
                this.setBtnUpdateFlag(false);
            } else {
                return;
            }
            gridLoad();
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

    }

    public void setValues() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            List resultList = inwardBillBooking.setvaluesInFielde(this.orgnBrCode, currentItem.getAcNo(), currentItem.getBillType(), currentItem.getInstNo());
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setActNo(ele.get(0).toString());
                    this.setAcctType(ele.get(0).toString().substring(2, 4));
                    this.setAcctNo(ele.get(0).toString());
                     this.setOldAcctNo(ele.get(0).toString());
                    this.setInstNo(ele.get(8).toString());
                    this.setInFavOf(ele.get(5).toString());
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    this.setInstAmt(String.valueOf(formatter.format(new BigDecimal(ele.get(9).toString()).doubleValue())));
                    this.setInstDate(sdf.parse(ele.get(13).toString()));
                    this.setBankAddress(ele.get(7).toString());
                    this.setBankName(ele.get(6).toString());
                    this.setAlphaCode(ele.get(3).toString());
                    if (ele.get(3).toString().equalsIgnoreCase("0")) {
                        this.setBankType("Other Bank");
                        this.setBankFlag(true);
                        this.setBankPanelFlag(false);
                    } else {
                        this.setBankType("Our Bank");
                        this.setBankFlag(false);
                        this.setBankPanelFlag(true);
                        this.setLabelParty("In Favour Of :");
                    }
                    this.setBillType(ele.get(2).toString());
                    this.setPayableAt(ele.get(4).toString());
                    this.setRefNo(ele.get(1).toString());
                    this.setRemType(ele.get(12).toString());
                    this.setOtherBankCom(ele.get(10).toString());
                    this.setOtherBankPostage(ele.get(11).toString());
                }
            } else {
                return;
            }
            gridLoad();
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setBtnUpdateFlag(true);
        try {
            String result = inwardBillBooking.deleteRecord(this.orgnBrCode, currentItem.getAcNo(), currentItem.getBillType(), currentItem.getInstNo(), String.valueOf(currentItem.getInstAmt()));
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Record Not Deleted.");
                return;
            }
            gridLoad();
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setAcctNo("");
            this.setActNo("");
            this.setInstAmt("0");
            Date date = new Date();
            this.setInstDate(date);
            this.setInstNo("");
            this.setBankType("--Select--");
            this.setBankAddress("");
            this.setBankName("");
            this.setAlphaCode("--Select--");
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setPayableAt("--Select--");
            this.setRefNo("");
            this.setRemType("--Select--");
            this.setOtherBankCom("");
            this.setOtherBankPostage("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setLabelParty("In Favour Of :");
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setAcctNo("");
            this.setActNo("");
            this.setInstAmt("0");
            Date date = new Date();
            this.setInstDate(date);
            this.setInstNo("");
            this.setBankType("--Select--");
            this.setBankAddress("");
            this.setBankName("");
            this.setAlphaCode("--Select--");
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setPayableAt("--Select--");
            this.setRefNo("");
            this.setRemType("--Select--");
            this.setOtherBankCom("");
            this.setOtherBankPostage("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBtnFlag(true);
            this.setBtnUpdateFlag(true);
            this.setLabelParty("In Favour Of :");
            this.setOldAcctNo("");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }
    String alpha;
    Pattern pm = Pattern.compile("[a-zA-z0-9,-/]+([ '-][a-zA-Z0-9,-/]+)*");

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
              if (this.acctNo == null  || this.acctNo.length() == 0 || this.acctNo.equalsIgnoreCase("")) {
                this.setMessage("Please Enter the Account No.");
                return;
            } 
//            if (this.acctType.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("Please Select Account Type.");
//                return;
//            }
//            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
//                this.setErrorMessage("Please Enter Account No.");
//                return;
//            }
//            if (this.actNo.equalsIgnoreCase("") || this.actNo.length() == 0) {
//                this.setErrorMessage("Please Enter Account No.");
//                return;
//            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instAmt == null || this.instAmt.equalsIgnoreCase("") || this.instAmt.length() == 0) {
                this.setErrorMessage("Please Enter the Bill Amount.");
                return;
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Bill Amount.");
                return;
            }
            if (this.instAmt.contains(".")) {
                if (this.instAmt.indexOf(".") != this.instAmt.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Bill Amount.");
                    return;
                } else if (this.instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Bill Amount Upto Two Decimal Places.");
                    return;
                }
            }
            if (Double.parseDouble(this.instAmt) < 0) {
                this.setErrorMessage("Bill Amount Cannot Be Less Than Zero.");
                return;
            }
            if (this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
                this.setErrorMessage("Please Enter the LR/RR/PP/AW No.");
                return;
            }
            Matcher instNoCheck = p.matcher(instNo);
            if (!instNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  LR/RR/PP/AW No.");
                return;
            }
            if (this.instNo.contains(".")) {
                this.setErrorMessage("Please Enter Valid  LR/RR/PP/AW No.");
                return;
            }
            if (this.instDate == null) {
                this.setErrorMessage("Please Enter LR/RR/PP/AW DL.");
                return;
            }
            if (this.bankType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bank Type.");
                return;
            }

            if (this.bankType.equalsIgnoreCase("Other Bank") || this.bankType.equalsIgnoreCase("Direct Party")) {
                alpha = "0";
            } else {
                if (this.alphaCode.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("Please Select Alpha Code.");
                    return;
                }
                alpha = this.alphaCode;
            }

            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                return;
            }
            if (this.inFavOf.equalsIgnoreCase("") || this.inFavOf.length() == 0) {
                this.setErrorMessage("Please Enter The In Favour Of.");
                return;
            }
            Matcher inFavOfCheck = pm.matcher(inFavOf);
            if (!inFavOfCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid In Favour Of.");
                return;
            }
            if (this.payableAt.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Payable At.");
                return;
            }
            if (this.refNo.equalsIgnoreCase("") || this.refNo.length() == 0) {
                this.setErrorMessage("Please Enter The Ref No.");
                return;
            }
            Matcher refNoCheck = p.matcher(refNo);
            if (!refNoCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid Ref No.(Numbers Only)");
                return;
            }
            if (this.bankName.equalsIgnoreCase("") || this.bankName.length() == 0) {
                this.setErrorMessage("Please Enter The Bank Name.");
                return;
            }
            Matcher bankNameCheck = pm.matcher(bankName);
            if (!bankNameCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid Bank Name.");
                return;
            }
            if (this.bankAddress.equalsIgnoreCase("") || this.bankAddress.length() == 0) {
                this.setErrorMessage("Please Enter The Bank Address.");
                return;
            }
            Matcher bankAddressCheck = pm.matcher(bankAddress);
            if (!bankAddressCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid Bank Address.");
                return;
            }
            if (this.remType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Remittance Type.");
                return;
            }
            if (this.otherBankCom.equalsIgnoreCase("") || this.otherBankCom.length() == 0) {
                this.setErrorMessage("Please Enter the Other Bank Commission.");
                return;
            }
            Matcher commCheck = p.matcher(otherBankCom);
            if (!commCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Other Bank Commission.");
                return;
            }
            if (this.otherBankCom.contains(".")) {
                if (this.otherBankCom.indexOf(".") != this.otherBankCom.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Other Bank Commission.");
                    return;
                } else if (this.otherBankCom.substring(otherBankCom.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Other Bank Commission Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(otherBankCom) < 0) {
                this.setErrorMessage("Other Bank Commission Cannot Be Less Than Zero.");
                return;
            }
            if (this.otherBankPostage.equalsIgnoreCase("") || this.otherBankPostage.length() == 0) {
                this.setErrorMessage("Please Enter the Other Bank Postage.");
                return;
            }
            Matcher postageCheck = p.matcher(otherBankPostage);
            if (!postageCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Other Bank Postage.");
                return;
            }
            if (this.otherBankPostage.contains(".")) {
                if (this.otherBankPostage.indexOf(".") != this.otherBankPostage.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Other Bank Postage.");
                    return;
                } else if (this.otherBankPostage.substring(otherBankPostage.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Other Bank Postage Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(otherBankPostage) < 0) {
                this.setErrorMessage("Other Bank Postage Cannot Be Less Than Zero.");
                return;
            }
            double a = Double.parseDouble(otherBankCom) + Double.parseDouble(otherBankPostage);
            if (a >= Double.parseDouble(instAmt)) {
                this.setErrorMessage("Please Check Commission and Postage Amount.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = inwardBillBooking.saveBtnInwardBillBooking(this.orgnBrCode, this.actNo, this.refNo, this.billType, this.alpha, this.payableAt, this.inFavOf, this.bankName, this.bankAddress, this.instNo, Double.parseDouble(this.instAmt.toString()), Float.parseFloat(this.otherBankCom), this.remType, ymd.format(this.instDate), this.userName, Float.parseFloat(this.otherBankPostage));
            if (result.equals("true")) {
                this.setMessage("Record Saved Successfully.");
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setAcctNo("");
            this.setActNo("");
            this.setInstAmt("0");
            Date date = new Date();
            this.setInstDate(date);
            this.setInstNo("");
            this.setBankType("--Select--");
            this.setBankAddress("");
            this.setBankName("");
            this.setAlphaCode("--Select--");
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setPayableAt("--Select--");
            this.setRefNo("");
            this.setRemType("--Select--");
            this.setOtherBankCom("");
            this.setOtherBankPostage("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBtnFlag(true);
            this.setBtnUpdateFlag(true);
            this.setLabelParty("In Favour Of :");
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }
    String alpha1;

    public void updateDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type.");
                return;
            }
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            if (this.actNo.equalsIgnoreCase("") || this.actNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instAmt == null || this.instAmt.equalsIgnoreCase("") || this.instAmt.length() == 0) {
                this.setErrorMessage("Please Enter the Bill Amount.");
                return;
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Bill Amount.");
                return;
            }
            if (this.instAmt.contains(".")) {
                if (this.instAmt.indexOf(".") != this.instAmt.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Bill Amount.");
                    return;
                } else if (this.instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Bill Amount Upto Two Decimal Places.");
                    return;
                }
            }
            if (Double.parseDouble(this.instAmt) < 0) {
                this.setErrorMessage("Bill Amount Cannot Be Less Than Zero.");
                return;
            }
            if (this.instNo.equalsIgnoreCase("") || this.instNo.length() == 0) {
                this.setErrorMessage("Please Enter the LR/RR/PP/AW No.");
                return;
            }
            Matcher instNoCheck = p.matcher(instNo);
            if (!instNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  LR/RR/PP/AW No.");
                return;
            }
            if (this.instNo.contains(".")) {
                this.setErrorMessage("Please Enter Valid  LR/RR/PP/AW No.");
                return;
            }
            if (this.instDate == null) {
                this.setErrorMessage("Please Enter LR/RR/PP/AW DL.");
                return;
            }
            if (this.bankType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bank Type.");
                return;
            }

            if (this.bankType.equalsIgnoreCase("Other Bank") || this.bankType.equalsIgnoreCase("Direct Party")) {
                alpha1 = "0";
            } else {
                if (this.alphaCode.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("Please Select Alpha Code.");
                    return;
                }
                alpha1 = this.alphaCode;
            }

            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                return;
            }
            if (this.inFavOf.equalsIgnoreCase("") || this.inFavOf.length() == 0) {
                this.setErrorMessage("Please Enter The In Favour Of.");
                return;
            }
            Matcher inFavOfCheck = pm.matcher(inFavOf);
            if (!inFavOfCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid In Favour Of.");
                return;
            }
            if (this.payableAt.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Payable At.");
                return;
            }
            if (this.refNo.equalsIgnoreCase("") || this.refNo.length() == 0) {
                this.setErrorMessage("Please Enter The Ref No.");
                return;
            }
            Matcher refNoCheck = p.matcher(refNo);
            if (!refNoCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid Ref No.(Numbers Only)");
                return;
            }
            if (this.bankName.equalsIgnoreCase("") || this.bankName.length() == 0) {
                this.setErrorMessage("Please Enter The Bank Name.");
                return;
            }
            Matcher bankNameCheck = pm.matcher(bankName);
            if (!bankNameCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid Bank Name.");
                return;
            }
            if (this.bankAddress.equalsIgnoreCase("") || this.bankAddress.length() == 0) {
                this.setErrorMessage("Please Enter The Bank Address.");
                return;
            }
            Matcher bankAddressCheck = pm.matcher(bankAddress);
            if (!bankAddressCheck.matches()) {
                this.setErrorMessage("Please Enter The Valid Bank Address.");
                return;
            }
            if (this.remType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Remittance Type.");
                return;
            }
            if (this.otherBankCom.equalsIgnoreCase("") || this.otherBankCom.length() == 0) {
                this.setErrorMessage("Please Enter the Other Bank Commission.");
                return;
            }
            Matcher commCheck = p.matcher(otherBankCom);
            if (!commCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Other Bank Commission.");
                return;
            }
            if (this.otherBankCom.contains(".")) {
                if (this.otherBankCom.indexOf(".") != this.otherBankCom.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Other Bank Commission.");
                    return;
                } else if (this.otherBankCom.substring(otherBankCom.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Other Bank Commission Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(otherBankCom) < 0) {
                this.setErrorMessage("Other Bank Commission Cannot Be Less Than Zero.");
                return;
            }
            if (this.otherBankPostage.equalsIgnoreCase("") || this.otherBankPostage.length() == 0) {
                this.setErrorMessage("Please Enter the Other Bank Postage.");
                return;
            }
            Matcher postageCheck = p.matcher(otherBankPostage);
            if (!postageCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Other Bank Postage.");
                return;
            }
            if (this.otherBankPostage.contains(".")) {
                if (this.otherBankPostage.indexOf(".") != this.otherBankPostage.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Other Bank Postage.");
                    return;
                } else if (this.otherBankPostage.substring(otherBankPostage.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Other Bank Postage Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(otherBankPostage) < 0) {
                this.setErrorMessage("Other Bank Postage Cannot Be Less Than Zero.");
                return;
            }
            double a = Double.parseDouble(otherBankCom) + Double.parseDouble(otherBankPostage);
            if (a >= Double.parseDouble(instAmt)) {
                this.setErrorMessage("Please Check Commission and Postage Amount.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = inwardBillBooking.updateBtn(this.orgnBrCode, this.actNo, this.refNo, this.billType, this.alpha1, this.payableAt, this.inFavOf, this.bankName, this.bankAddress, this.instNo, Double.parseDouble(this.instAmt.toString()), Float.parseFloat(this.otherBankCom), this.remType, ymd.format(this.instDate), this.userName, Float.parseFloat(this.otherBankPostage));
            if (result.equals("true")) {
                this.setMessage("Record Updated Successfully.");
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
            this.setCustName("");
            this.setJtName1("");
            this.setJtName2("");
            this.setAcctNo("");
            this.setActNo("");
            this.setInstAmt("0");
            Date date = new Date();
            this.setInstDate(date);
            this.setInstNo("");
            this.setBankType("--Select--");
            this.setBankAddress("");
            this.setBankName("");
            this.setAlphaCode("--Select--");
            this.setBillType("--Select--");
            this.setInFavOf("");
            this.setPayableAt("--Select--");
            this.setRefNo("");
            this.setRemType("--Select--");
            this.setOtherBankCom("");
            this.setOtherBankPostage("");
            this.setBankFlag(false);
            this.setBankPanelFlag(true);
            this.setBtnFlag(true);
            this.setLabelParty("In Favour Of :");
            this.setBtnUpdateFlag(true);
        } catch (ApplicationException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    public String exitForm() {
        try {
            resetForm();
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
        }
        return "case1";
    }
}
