/*
 * Created By :ROHIT KRISHNA GUPTA
 * Creation Date : 06 Oct 2010
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.RealizationInwardChqGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.IbcObcFacadeRemote;
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
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class RealizationInwardCheque {

    Context ctx;
    IbcObcFacadeRemote realInwardChq;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String billType;
    private String billNo;
    private String year;
    private String instAmt;
    private String instNo;
    private String instDate;
    private String actNo;
    private String custName;
    private String comm;
    private String postage;
    private String ourChg;
    private String amtDebited;
    private String acNoCredit;
    private String acNoCreditName;
    private String reason;
    private String returnChg;
    private boolean dishonorFlag;
    private boolean realizationFlag;
    private boolean comFlag;
    private boolean instFlag;
    private boolean amFlag;
    private List<SelectItem> billTypeList;
    private List<SelectItem> yearList;
    private List<SelectItem> reasonList;
    private HttpServletRequest req;
    int currentRow;
    private List<RealizationInwardChqGrid> gridDetail;
    private RealizationInwardChqGrid currentItem = new RealizationInwardChqGrid();

    public String getAcNoCredit() {
        return acNoCredit;
    }

    public void setAcNoCredit(String acNoCredit) {
        this.acNoCredit = acNoCredit;
    }

    public String getAcNoCreditName() {
        return acNoCreditName;
    }

    public void setAcNoCreditName(String acNoCreditName) {
        this.acNoCreditName = acNoCreditName;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getAmtDebited() {
        return amtDebited;
    }

    public void setAmtDebited(String amtDebited) {
        this.amtDebited = amtDebited;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
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

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
        this.instAmt = instAmt;
    }

    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
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

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getOurChg() {
        return ourChg;
    }

    public void setOurChg(String ourChg) {
        this.ourChg = ourChg;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isComFlag() {
        return comFlag;
    }

    public void setComFlag(boolean comFlag) {
        this.comFlag = comFlag;
    }

    public boolean isDishonorFlag() {
        return dishonorFlag;
    }

    public void setDishonorFlag(boolean dishonorFlag) {
        this.dishonorFlag = dishonorFlag;
    }

    public boolean isRealizationFlag() {
        return realizationFlag;
    }

    public void setRealizationFlag(boolean realizationFlag) {
        this.realizationFlag = realizationFlag;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<SelectItem> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<SelectItem> reasonList) {
        this.reasonList = reasonList;
    }

    public String getReturnChg() {
        return returnChg;
    }

    public void setReturnChg(String returnChg) {
        this.returnChg = returnChg;
    }

    public RealizationInwardChqGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(RealizationInwardChqGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<RealizationInwardChqGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<RealizationInwardChqGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isInstFlag() {
        return instFlag;
    }

    public void setInstFlag(boolean instFlag) {
        this.instFlag = instFlag;
    }

    public boolean isAmFlag() {
        return amFlag;
    }

    public void setAmFlag(boolean amFlag) {
        this.amFlag = amFlag;
    }

    /** Creates a new instance of RealizationInwardCheque */
    public RealizationInwardCheque() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager1");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            realInwardChq = (IbcObcFacadeRemote) ServiceLocator.getInstance().lookup("IbcObcFacade");
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            this.setRealizationFlag(true);
            this.setComFlag(true);
            instFlag = true;
            dishonorFlag = false;
            amFlag = false;
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("BC"));
            yearCombo();
            gridLoad();
            reasonCombo();
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

    private void yearCombo() {
        try {
            List resultList = new ArrayList();
            resultList = realInwardChq.RealOutBillFormLoad(this.orgnBrCode);
            if (resultList.isEmpty()) {
                this.setErrorMessage("Year does not exists !!!.");
                yearList = new ArrayList<SelectItem>();
                yearList.add(new SelectItem("--Select--"));
                return;
            } else {
                yearList = new ArrayList<SelectItem>();
                yearList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        yearList.add(new SelectItem(ee.toString()));
                    }
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    private void reasonCombo() {
        try {
            List resultList = new ArrayList();
            ctx = new InitialContext();
            resultList = realInwardChq.RealOutBillFormLoad1();
            if (resultList.isEmpty()) {
                reasonList = new ArrayList<SelectItem>();
                reasonList.add(new SelectItem("--Select--"));
                return;
            } else {
                reasonList = new ArrayList<SelectItem>();
                reasonList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        reasonList.add(new SelectItem(ee.toString()));
                    }
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    private void gridLoad() {
        gridDetail = new ArrayList<RealizationInwardChqGrid>();
        try {
            List resultList = new ArrayList();
            resultList = realInwardChq.RealInwardChqOnLoad(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    RealizationInwardChqGrid detail = new RealizationInwardChqGrid();
                    detail.setsNo(i + 1);
                    detail.setBillNo(ele.get(0).toString());
                    detail.setfYear(ele.get(1).toString());
                    detail.setAcNo(ele.get(2).toString());
                    detail.setBillType(ele.get(3).toString());
                    detail.setInstNo(ele.get(4).toString());
                    detail.setInstAmt(Double.parseDouble(ele.get(5).toString()));
                    detail.setInstDate(ele.get(6).toString());
                    detail.setBankName(ele.get(7).toString());
                    detail.setBankAdd(ele.get(8).toString());
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

    public void yearComboLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.dishonorFlag = false;
        try {
            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                this.setPostage("");
                this.setReturnChg("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
                this.setOurChg("");
                this.setAmtDebited("");
                this.setReturnChg("");
                this.setAcNoCredit("");
                this.setAcNoCreditName("");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
                this.setErrorMessage("Please Enter Bill No.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                this.setPostage("");
                this.setReturnChg("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
                this.setOurChg("");
                this.setAmtDebited("");
                this.setReturnChg("");
                this.setAcNoCredit("");
                this.setAcNoCreditName("");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Bill No.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                this.setPostage("");
                this.setReturnChg("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
                this.setOurChg("");
                this.setAmtDebited("");
                this.setReturnChg("");
                this.setAcNoCredit("");
                this.setAcNoCreditName("");
                return;
            }
            if (this.billNo.contains(".")) {
                this.setErrorMessage("Please Enter Valid  Bill No.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                this.setPostage("");
                this.setReturnChg("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
                this.setOurChg("");
                this.setAmtDebited("");
                this.setReturnChg("");
                this.setAcNoCredit("");
                this.setAcNoCreditName("");
                return;
            }
            if (this.year.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Year.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                this.setPostage("");
                this.setReturnChg("");
                this.setReason("--Select--");
                this.setYear("--Select--");
                this.setOurChg("");
                this.setAmtDebited("");
                this.setReturnChg("");
                this.setAcNoCredit("");
                this.setAcNoCreditName("");
                return;
            }
            String result = realInwardChq.RealOutBillFyearProcess(this.orgnBrCode, this.billType, Float.parseFloat(billNo), Integer.parseInt(this.year));
            if (result == null) {
                return;
            } else {
                if (result.contains(":")) {
                    String[] values = null;
                    try {
                        String spliter = ":";
                        values = result.split(spliter);
                        this.setActNo(values[0]);
                        this.setCustName(values[1]);
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        //this.setInstAmt(values[2]);
                        this.setInstAmt(String.valueOf(formatter.format(new BigDecimal(values[2]).doubleValue())));
                        this.setInstNo(values[3]);
                        this.setInstDate(values[4]);
                        this.setComm(String.valueOf(formatter.format(new BigDecimal(values[5]).doubleValue())));
                        this.setPostage(String.valueOf(formatter.format(new BigDecimal(values[6]).doubleValue())));
                        this.setAcNoCredit(values[7]);
                    } catch (Exception ex) {
                        message = ex.getMessage();
                    }
                } else {
                    this.setErrorMessage(result);
                    this.setMessage("");
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    this.setPostage("");
                    this.setReturnChg("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    this.setOurChg("");
                    this.setAmtDebited("");
                    this.setReturnChg("");
                    this.setAcNoCredit("");
                    amFlag = false;
                    this.setAcNoCreditName("");
                    return;
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void dishonorPanel() {
        this.setErrorMessage("");
        this.setMessage("");
        this.dishonorFlag = true;
        if ((this.instAmt == null) || (this.instAmt.equalsIgnoreCase("")) || (this.instAmt.length() == 0) || (this.instNo == null) || (this.instNo.equalsIgnoreCase("")) || (this.instNo.length() == 0) || (this.instDate == null) || (this.instDate.length() == 0) || (this.instDate.equalsIgnoreCase("")) || (this.actNo == null) || (this.actNo.equalsIgnoreCase("")) || (this.actNo.length() == 0)) {
            this.setErrorMessage("Please Check Instruement details.");
            return;
        }
    }

    public void dishonoredRecord() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if ((this.instAmt == null) || (this.instAmt.equalsIgnoreCase("")) || (this.instAmt.length() == 0) || (this.instNo == null) || (this.instNo.equalsIgnoreCase("")) || (this.instNo.length() == 0) || (this.instDate == null) || (this.instDate.length() == 0) || (this.instDate.equalsIgnoreCase("")) || (this.actNo == null) || (this.actNo.equalsIgnoreCase("")) || (this.actNo.length() == 0)) {
                this.setErrorMessage("Please Check Instruement details.");
                return;
            }
            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
                this.setErrorMessage("Please Enter Bill No.");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Bill No.");
                return;
            }
            if (Float.parseFloat(billNo) < 0) {
                this.setErrorMessage("Please Enter Valid Bill No.");
                return;
            }

            if (this.instAmt.equalsIgnoreCase("") || this.instAmt.length() == 0) {
                this.setErrorMessage("Please Enter Instrument Amount.");
                return;
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Instrument Amount.");
                return;
            }
            if (Float.parseFloat(instAmt) < 0) {
                this.setErrorMessage("Please Enter Valid Instrument Amount.");
                return;
            }


            if (this.returnChg.equalsIgnoreCase("") || this.returnChg.length() == 0) {
                this.setErrorMessage("Please Enter Return Charges.");
                return;
            }
            Matcher returnChgCheck = p.matcher(returnChg);
            if (!returnChgCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Return Charges.");
                return;
            }
            if (Float.parseFloat(returnChg) < 0) {
                this.setErrorMessage("Please Enter Valid Return Charges.");
                return;
            }

            if (this.year.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Year.");
                return;
            }
            if (this.reason.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Reason.");
                return;
            }
            ctx = new InitialContext();
            String result = realInwardChq.RealOutBillDishonorOutBill(this.orgnBrCode, this.userName, this.billType, Float.parseFloat(billNo), Integer.parseInt(this.year), this.actNo, this.instNo, Double.parseDouble(instAmt), this.instDate, Double.parseDouble(this.returnChg), this.reason);
            if (result == null) {
                this.setErrorMessage("Dishonoured Not Done.");
                gridLoad();
                return;
            } else {
                if (result.substring(0, 11).equalsIgnoreCase("Dishonoured")) {
                    this.setMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    this.setPostage("");
                    this.setReturnChg("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    this.setOurChg("");
                    this.setAmtDebited("");
                    this.setReturnChg("");
                    this.setAcNoCredit("");
                    this.setAcNoCreditName("");
                    amFlag = false;
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    this.setPostage("");
                    this.setReturnChg("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    this.setOurChg("");
                    this.setAmtDebited("");
                    this.setReturnChg("");
                    this.setAcNoCredit("");
                    amFlag = false;
                    this.setAcNoCreditName("");
                    gridLoad();
                    return;
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            gridLoad();
            this.setActNo("");
            this.setCustName("");
            this.setInstAmt("");
            this.setInstNo("");
            this.setInstDate("");
            this.setComm("");
            this.setPostage("");
            this.setReturnChg("");
            this.setReason("--Select--");
            this.setBillNo("");
            this.setYear("--Select--");
            this.setBillType("--Select--");
            this.setOurChg("");
            this.setAmtDebited("");
            this.setReturnChg("");
            this.setAcNoCredit("");
            amFlag = false;
            this.setAcNoCreditName("");
            this.dishonorFlag = false;
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void setAmtDebited() {
        this.setErrorMessage("");
        this.setMessage("");
        amFlag = true;
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.instAmt.equalsIgnoreCase("") || this.instAmt.length() == 0) {
                this.setErrorMessage("Please Enter Instrument Amount.");
                return;
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Instrument Amount.");
                return;
            }
            if (Float.parseFloat(instAmt) < 0) {
                this.setErrorMessage("Please Enter Valid Instrument Amount.");
                return;
            }
            if (this.ourChg.equalsIgnoreCase("") || this.ourChg.length() == 0) {
                this.setErrorMessage("Please Enter Other Bank Charges.");
                return;
            }
            Matcher ourChgCheck = p.matcher(ourChg);
            if (!ourChgCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Other Bank Charges.");
                return;
            }
            if (Float.parseFloat(ourChg) < 0) {
                this.setErrorMessage("Please Enter Valid Other Bank Charges.");
                return;
            }
            double val = Double.parseDouble(instAmt) - Double.parseDouble(ourChg);
            NumberFormat formatter = new DecimalFormat("#0.00");
            //this.setAmtDebited(val.toString());
            this.setAmtDebited(String.valueOf(formatter.format(new BigDecimal(val))));
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }
    Integer VarPayBy;

    public void chkInstNo() {
        if ((this.actNo == null) || (this.actNo.equalsIgnoreCase("")) || (this.actNo.length() == 0) || (this.instNo == null) || (this.instNo.equalsIgnoreCase("")) || (this.instNo.length() == 0)) {
            this.setErrorMessage("Please Check Instrument Deatils.");
            return;
        }
        try {
            List resultList = new ArrayList();
            resultList = realInwardChq.chkInstrument(this.actNo, this.instNo);
            if (resultList.isEmpty()) {
                VarPayBy = 4;
                instFlag = false;
            } else {
                VarPayBy = 3;
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0).toString().equalsIgnoreCase("S")) {
                        this.setErrorMessage("Cheque is Marked as Stop Payment , Kindly Dishonor This Inst.");
                        return;
                    }
                }
                passRecord();
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void passRecord() {
        try {
            if ((this.instAmt == null) || (this.instAmt.equalsIgnoreCase("")) || (this.instAmt.length() == 0) || (this.instNo == null) || (this.instNo.equalsIgnoreCase("")) || (this.instNo.length() == 0) || (this.instDate == null) || (this.instDate.length() == 0) || (this.instDate.equalsIgnoreCase("")) || (this.actNo == null) || (this.actNo.equalsIgnoreCase("")) || (this.actNo.length() == 0) || (this.ourChg == null) || (this.ourChg.equalsIgnoreCase("")) || (this.ourChg.length() == 0) || (this.amtDebited == null) || (this.amtDebited.equalsIgnoreCase("")) || (this.amtDebited.length() == 0)) {
                this.setErrorMessage("Please Check Instruement details.");
                return;
            }
            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
                this.setErrorMessage("Please Enter Bill No.");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Bill No.");
                return;
            }
            if (Float.parseFloat(billNo) < 0) {
                this.setErrorMessage("Please Enter Valid Bill No.");
                return;
            }

            if (this.instAmt.equalsIgnoreCase("") || this.instAmt.length() == 0) {
                this.setErrorMessage("Please Enter Instrument Amount.");
                return;
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Instrument Amount.");
                return;
            }
            if (Float.parseFloat(instAmt) < 0) {
                this.setErrorMessage("Please Enter Valid Instrument Amount.");
                return;
            }


            if (this.ourChg.equalsIgnoreCase("") || this.ourChg.length() == 0) {
                this.setErrorMessage("Please Enter Our Charges.");
                return;
            }
            Matcher ourChgCheck = p.matcher(ourChg);
            if (!ourChgCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Our Charges.");
                return;
            }
            if (Float.parseFloat(ourChg) < 0) {
                this.setErrorMessage("Please Enter Valid Our Charges.");
                return;
            }

            if (this.amtDebited.equalsIgnoreCase("") || this.amtDebited.length() == 0) {
                this.setErrorMessage("Please Enter Amount To Be Debited.");
                return;
            }
            Matcher amtDebitedCheck = p.matcher(amtDebited);
            if (!amtDebitedCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Amount To Be Debited.");
                return;
            }
            if (Float.parseFloat(amtDebited) < 0) {
                this.setErrorMessage("Please Enter Valid Amount To Be Debited.");
                return;
            }

            if (this.year.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Year.");
                return;
            }
            String result = realInwardChq.RealInwardChqPass(this.orgnBrCode, this.userName, this.billType, Float.parseFloat(billNo), Integer.parseInt(this.year), this.actNo, this.instNo, Double.parseDouble(instAmt), this.instDate, Double.parseDouble(this.ourChg), Double.parseDouble(this.amtDebited), this.custName, VarPayBy);
            if (result == null) {
                this.setErrorMessage("Not Passed.");
                gridLoad();
                return;
            } else {
                if (result.substring(0, 5).equalsIgnoreCase("Value")) {
                    this.setMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    this.setPostage("");
                    this.setReturnChg("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    this.setOurChg("");
                    this.setAmtDebited("");
                    this.setReturnChg("");
                    this.setAcNoCredit("");
                    this.setAcNoCreditName("");
                    amFlag = false;
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    this.setPostage("");
                    this.setReturnChg("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    this.setOurChg("");
                    this.setAmtDebited("");
                    this.setReturnChg("");
                    this.setAcNoCredit("");
                    this.setAcNoCreditName("");
                    amFlag = false;
                    gridLoad();
                    return;
                }
            }
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
