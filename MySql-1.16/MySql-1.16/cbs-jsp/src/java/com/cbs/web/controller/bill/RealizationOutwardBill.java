/*
 * Created By :ROHIT KRISHNA GUPTA
 * Creation Date : 05 Oct 2010
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.RealOutwardBillGrid;
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
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class RealizationOutwardBill {

    IbcObcFacadeRemote realOutBill;
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
    private Date dtOfRel;
    private String otherChgAct;
    private String otherBankChg;
    private String relInstAmt;
    private String reason;
    private String returnChg;
    private List<SelectItem> billTypeList;
    private List<SelectItem> yearList;
    private List<SelectItem> reasonList;
    private HttpServletRequest req;
    int currentRow;
    private List<RealOutwardBillGrid> gridDetail;
    private RealOutwardBillGrid currentItem = new RealOutwardBillGrid();
    private boolean dishonorFlag;
    private boolean realizationFlag;
    private boolean comFlag;

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

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
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

    public Date getDtOfRel() {
        return dtOfRel;
    }

    public void setDtOfRel(Date dtOfRel) {
        this.dtOfRel = dtOfRel;
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

    public String getOtherBankChg() {
        return otherBankChg;
    }

    public void setOtherBankChg(String otherBankChg) {
        this.otherBankChg = otherBankChg;
    }

    public String getOtherChgAct() {
        return otherChgAct;
    }

    public void setOtherChgAct(String otherChgAct) {
        this.otherChgAct = otherChgAct;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getRelInstAmt() {
        return relInstAmt;
    }

    public void setRelInstAmt(String relInstAmt) {
        this.relInstAmt = relInstAmt;
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

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
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

    public RealOutwardBillGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(RealOutwardBillGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<RealOutwardBillGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<RealOutwardBillGrid> gridDetail) {
        this.gridDetail = gridDetail;
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

    public boolean isRealizationFlag() {
        return realizationFlag;
    }

    public void setRealizationFlag(boolean realizationFlag) {
        this.realizationFlag = realizationFlag;
    }

    /** Creates a new instance of RealizationOutwardBill */
    public RealizationOutwardBill() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            realOutBill = (IbcObcFacadeRemote) ServiceLocator.getInstance().lookup("IbcObcFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            setDtOfRel(date);
            this.setErrorMessage("");
            this.setMessage("");
            this.setRealizationFlag(true);
            this.setComFlag(true);
            //this.setDishonorFlag(false);
            dishonorFlag = false;
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("BP"));
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

    public void yearCombo() {
        try {
            List resultList = new ArrayList();
            resultList = realOutBill.RealOutBillFormLoad(this.orgnBrCode);
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

    public void reasonCombo() {
        try {
            List resultList = new ArrayList();

            resultList = realOutBill.RealOutBillFormLoad1();
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

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (RealOutwardBillGrid item : gridDetail) {
            if (item.getAcNo().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<RealOutwardBillGrid>();
        try {
            List resultList = new ArrayList();
            resultList = realOutBill.RealOutBillOnLoad(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    RealOutwardBillGrid detail = new RealOutwardBillGrid();
                    detail.setsNo(i + 1);
                    detail.setBillNo(ele.get(0).toString());
                    detail.setBillType(ele.get(1).toString());
                    detail.setfYear(ele.get(2).toString());
                    detail.setAcNo(ele.get(3).toString());
                    detail.setInstNo(ele.get(4).toString());
                    detail.setInstAmt(Double.parseDouble(ele.get(5).toString()));
                    detail.setInstDate(ele.get(6).toString());
                    detail.setColBankName(ele.get(7).toString());
                    detail.setColBankAdd(ele.get(8).toString());
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
        try {
            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                Date date = new Date();
                this.setDtOfRel(date);
                this.setPostage("");
                this.setOtherBankChg("");
                this.setOtherChgAct("");
                this.setReturnChg("");
                this.setRelInstAmt("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.billNo == null || this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
                this.setErrorMessage("Please Enter Bill No.");
                this.setActNo("");
                this.setCustName("");
                this.setInstAmt("");
                this.setInstNo("");
                this.setInstDate("");
                this.setComm("");
                Date date = new Date();
                this.setDtOfRel(date);
                this.setPostage("");
                this.setOtherBankChg("");
                this.setOtherChgAct("");
                this.setReturnChg("");
                this.setRelInstAmt("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
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
                Date date = new Date();
                this.setDtOfRel(date);
                this.setPostage("");
                this.setOtherBankChg("");
                this.setOtherChgAct("");
                this.setReturnChg("");
                this.setRelInstAmt("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
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
                Date date = new Date();
                this.setDtOfRel(date);
                this.setPostage("");
                this.setOtherBankChg("");
                this.setOtherChgAct("");
                this.setReturnChg("");
                this.setRelInstAmt("");
                this.setReason("--Select--");
                this.setBillNo("");
                this.setYear("--Select--");
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
                Date date = new Date();
                this.setDtOfRel(date);
                this.setPostage("");
                this.setOtherBankChg("");
                this.setOtherChgAct("");
                this.setReturnChg("");
                this.setRelInstAmt("");
                this.setReason("--Select--");
                this.setYear("--Select--");
                return;
            }
            String result = realOutBill.RealOutBillFyearProcess(this.billType, Float.parseFloat(billNo), Integer.parseInt(this.year), this.orgnBrCode);
            if (result == null) {
                return;
            } else {
                if (result.contains(":")) {
                    String[] values = null;
                    try {
                        String spliter = ":";
                        values = result.split(spliter);
                        this.setErrorMessage(values[0]);
                        if (values[0] == null || values[0].equalsIgnoreCase("") || values[0].length() == 0) {
                            this.setRealizationFlag(true);
                        } else {
                            this.setRealizationFlag(false);
                        }
                        this.setActNo(values[1]);
                        this.setCustName(values[2]);
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        //this.setInstAmt(values[3]);
                        this.setInstAmt(String.valueOf(formatter.format(new BigDecimal(values[3]).doubleValue())));
                        this.setInstNo(values[4]);
                        this.setInstDate(values[5]);
                        //this.setComm(values[6]);
                        this.setComm(String.valueOf(formatter.format(new BigDecimal(values[6]).doubleValue())));
//                    Date orgnDt = sdf.parse(values[7]);
//                    this.setOriginalDt(orgnDt);
                        //this.setPostage(values[7]);
                        this.setPostage(String.valueOf(formatter.format(new BigDecimal(values[7]).doubleValue())));
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
                    Date date = new Date();
                    this.setDtOfRel(date);
                    this.setPostage("");
                    this.setOtherBankChg("");
                    this.setOtherChgAct("");
                    this.setReturnChg("");
                    this.setRelInstAmt("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    return;
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void setRealInstAmount() {
        this.setErrorMessage("");
        this.setMessage("");
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
            if (Double.parseDouble(instAmt) < 0) {
                this.setErrorMessage("Please Enter Valid Instrument Amount.");
                return;
            }
            if (this.otherBankChg.equalsIgnoreCase("") || this.otherBankChg.length() == 0) {
                this.setErrorMessage("Please Enter Other Bank Charges.");
                return;
            }
            Matcher otherBankChgCheck = p.matcher(otherBankChg);
            if (!otherBankChgCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Other Bank Charges.");
                return;
            }
            if (Double.parseDouble(otherBankChg) < 0) {
                this.setErrorMessage("Please Enter Valid Other Bank Charges.");
                return;
            }
            double val = Double.parseDouble(instAmt) - Double.parseDouble(otherBankChg);
            //this.setRelInstAmt(val.toString());
            NumberFormat formatter = new DecimalFormat("#0.00");
            this.setRelInstAmt(String.valueOf(formatter.format(val)));
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
            if (this.dishonorFlag == true) {
            }
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
            String result = realOutBill.RealOutBillDishonorOutBill(this.orgnBrCode, this.userName, this.billType, Float.parseFloat(billNo), Integer.parseInt(this.year), this.actNo, this.instNo, Double.parseDouble(instAmt), this.instDate, Double.parseDouble(this.returnChg), this.reason);
            if (result == null) {
                this.setErrorMessage("Dishonoured Not Done.");
                gridLoad();
                return;
            } else {
                if (result.equalsIgnoreCase("Dishonoured Successfully")) {
                    this.dishonorFlag = false;
                    this.setMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    Date date = new Date();
                    this.setDtOfRel(date);
                    this.setPostage("");
                    this.setOtherBankChg("");
                    this.setOtherChgAct("");
                    this.setReturnChg("");
                    this.setRelInstAmt("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    Date date = new Date();
                    this.setDtOfRel(date);
                    this.setPostage("");
                    this.setOtherBankChg("");
                    this.setOtherChgAct("");
                    this.setReturnChg("");
                    this.setRelInstAmt("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
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
            Date date = new Date();
            this.setDtOfRel(date);
            this.setPostage("");
            this.setOtherBankChg("");
            this.setOtherChgAct("");
            this.setReturnChg("");
            this.setRelInstAmt("");
            this.setReason("--Select--");
            this.setBillNo("");
            this.setYear("--Select--");
            this.setBillType("--Select--");
            this.dishonorFlag = false;
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void realizeRecord() {
        this.setErrorMessage("");
        this.setMessage("");
        this.dishonorFlag = false;
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

            if (this.year.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Year.");
                return;
            }
            if (this.otherChgAct.equalsIgnoreCase("") || this.otherChgAct.length() == 0) {
                this.setErrorMessage("Please Enter Other Charges.");
                return;
            }
            Matcher otherChgActCheck = p.matcher(otherChgAct);
            if (!otherChgActCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Other Charges.");
                return;
            }
            if (Float.parseFloat(otherChgAct) < 0) {
                this.setErrorMessage("Please Enter Valid Other Charges.");
                return;
            }

            if (this.otherBankChg.equalsIgnoreCase("") || this.otherBankChg.length() == 0) {
                this.setErrorMessage("Please Enter Other Bank Charges.");
                return;
            }
            Matcher otherBankChgCheck = p.matcher(otherBankChg);
            if (!otherBankChgCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Other Bank Charges.");
                return;
            }
            if (Float.parseFloat(otherBankChg) < 0) {
                this.setErrorMessage("Please Enter Valid Other Bank Charges.");
                return;
            }

            if (this.relInstAmt.equalsIgnoreCase("") || this.relInstAmt.length() == 0) {
                this.setErrorMessage("Please Enter Return Charges.");
                return;
            }
            Matcher relInstAmtCheck = p.matcher(relInstAmt);
            if (!relInstAmtCheck.matches()) {
                this.setErrorMessage("Please Enter Valid Realized Instrument Amount.");
                return;
            }
            if (Float.parseFloat(relInstAmt) < 0) {
                this.setErrorMessage("Please Enter Valid Realized Instrument Amount.");
                return;
            }
            if (this.dtOfRel == null) {
                this.setErrorMessage("Please Enter Date Of Realization .");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = realOutBill.RealOutBillRealized(this.orgnBrCode, this.userName, this.billType, Float.parseFloat(billNo), Integer.parseInt(this.year), this.actNo, this.instNo, Double.parseDouble(instAmt), this.instDate, Double.parseDouble(this.otherChgAct), Double.parseDouble(this.otherBankChg), Double.parseDouble(this.relInstAmt), this.custName, ymd.format(this.dtOfRel));
            if (result == null) {
                this.setErrorMessage("Realization Not Done.");
                gridLoad();
                return;
            } else {
                if (result.substring(0, 9).equalsIgnoreCase("Generated")) {
                    this.setMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    Date date = new Date();
                    this.setDtOfRel(date);
                    this.setPostage("");
                    this.setOtherBankChg("");
                    this.setOtherChgAct("");
                    this.setReturnChg("");
                    this.setRelInstAmt("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
                    gridLoad();
                } else {
                    this.setErrorMessage(result);
                    this.setActNo("");
                    this.setCustName("");
                    this.setInstAmt("");
                    this.setInstNo("");
                    this.setInstDate("");
                    this.setComm("");
                    Date date = new Date();
                    this.setDtOfRel(date);
                    this.setPostage("");
                    this.setOtherBankChg("");
                    this.setOtherChgAct("");
                    this.setReturnChg("");
                    this.setRelInstAmt("");
                    this.setReason("--Select--");
                    this.setBillNo("");
                    this.setYear("--Select--");
                    this.setBillType("--Select--");
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
        try {
            resetForm();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return "case1";
    }
}
