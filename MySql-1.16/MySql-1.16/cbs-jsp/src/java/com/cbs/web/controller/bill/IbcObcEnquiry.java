/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.bill;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.IbcObcFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
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
public class IbcObcEnquiry {

    Context ctx;
    IbcObcFacadeRemote ibcobcEnq;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String billType;
    private String bill;
    private String billNo;
    private String year;
    private String acNo;
    private String custName;
    private String instAmt;
    private String instNo;
    private String instDate;
    private String commision;
    private String postageAmt;
    private String status;
    private HttpServletRequest req;
    private List<SelectItem> billTypeList;
    private List<SelectItem> billList;
    private List<SelectItem> yearList;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
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

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
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

    public String getPostageAmt() {
        return postageAmt;
    }

    public void setPostageAmt(String postageAmt) {
        this.postageAmt = postageAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<SelectItem> getBillList() {
        return billList;
    }

    public void setBillList(List<SelectItem> billList) {
        this.billList = billList;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
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
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of IbcObcEnquiry */
    public IbcObcEnquiry() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("BC"));
            billTypeList.add(new SelectItem("BP"));
            billList = new ArrayList<SelectItem>();
            billList.add(new SelectItem("--Select--"));
            billList.add(new SelectItem("IBC"));
            billList.add(new SelectItem("OBC"));
            yearList = new ArrayList<SelectItem>();
            yearList.add(new SelectItem("--Select--"));
            ibcobcEnq = (IbcObcFacadeRemote) ServiceLocator.getInstance().lookup("IbcObcFacade");
            //yearCombo();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
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
        this.setErrorMessage("");
        this.setMessage("");
        try {
            List resultList = new ArrayList();
            resultList = ibcobcEnq.FYear(this.orgnBrCode, this.bill);
            yearList = new ArrayList<SelectItem>();
            //yearList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        yearList.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                yearList.add(new SelectItem("--Select--"));
                this.setErrorMessage("No Year Exists.");
                return;
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.bill.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill.");
                return;
            }
            if (this.billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
                this.setErrorMessage("Please Enter the Bill No.");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setErrorMessage("Please Enter Valid  Bill No.");
                return;
            }
            if (this.billNo.contains(".")) {
                this.setErrorMessage("Please Enter Valid  Bill No.");
                return;
            }
            if (this.year.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Year.");
                return;
            }
            ctx = new InitialContext();
            String result = ibcobcEnq.billEnquiry(bill, billType, Float.parseFloat(billNo), Integer.parseInt(year), this.orgnBrCode);
            if (result.contains(":")) {
                String[] values = null;
                try {
                    String spliter = ":";
                    values = result.split(spliter);
                    this.setAcNo(values[0]);
                    this.setCustName(values[1]);
                    this.setInstAmt(formatter.format(Double.parseDouble(values[2])));
                    this.setInstNo(values[3]);
                    this.setInstDate(values[4]);
                    this.setCommision(formatter.format(Double.parseDouble(values[5])));
                    this.setPostageAmt(formatter.format(Double.parseDouble(values[6])));
                    this.setStatus(values[7]);
                } catch (Exception e) {
                    message = e.getMessage();
                }
            } else {
                this.setErrorMessage(result);
                this.setMessage("");
                return;
            }

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void resetForm() {
        try {
            yearList = new ArrayList<SelectItem>();
            yearList.add(new SelectItem("--Select--"));
            this.setErrorMessage("");
            this.setMessage("");
            this.setBill("--Select--");
            this.setBillType("--Select--");
            this.setBillNo("");
            this.setYear("--Select--");
            this.setAcNo("");
            this.setCustName("");
            this.setInstAmt("");
            this.setInstDate("");
            this.setInstNo("");
            this.setCommision("");
            this.setPostageAmt("");
            this.setStatus("");
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public String exitForm() {
        resetForm();
        return "case1";
    }
}
