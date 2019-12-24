/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.ChargesObject;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.InoperativeChargesFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.misc.InoperativeChargesTable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class InoperativeCharges extends BaseBean {

    InoperativeChargesFacadeRemote remoteObject;
    private Date fromDate;
    private Date toDate;
    private String acType;
    private String amtToCrd;
    private double crDrAmt;
    private String crAmt;
    private String drAmt;
    private String message;
    private boolean postEnable;
    private boolean calEnable;
    private List<SelectItem> acctNo;
    private List<SelectItem> acToCredit;
    private List<InoperativeChargesTable> instCharges;

    public boolean isPostEnable() {
        return postEnable;
    }

    public void setPostEnable(boolean postEnable) {
        this.postEnable = postEnable;
    }

    public List<InoperativeChargesTable> getInstCharges() {
        return instCharges;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInstCharges(List<InoperativeChargesTable> instCharges) {
        this.instCharges = instCharges;
    }

    public String getAmtToCrd() {
        return amtToCrd;
    }

    public void setAmtToCrd(String amtToCrd) {
        this.amtToCrd = amtToCrd;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcToCredit() {
        return acToCredit;
    }

    public void setAcToCredit(List<SelectItem> acToCredit) {
        this.acToCredit = acToCredit;
    }

    public List<SelectItem> getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(List<SelectItem> acctNo) {
        this.acctNo = acctNo;
    }

    public boolean isCalEnable() {
        return calEnable;
    }

    public void setCalEnable(boolean calEnable) {
        this.calEnable = calEnable;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public InoperativeCharges() {
        try {
            setMessage("");
            remoteObject = (InoperativeChargesFacadeRemote) ServiceLocator.getInstance().lookup("InoperativeChargesFacade");
            setDate();
            getDropDownList();
            setPostEnable(true);
            setCalEnable(false);
            instCharges = new ArrayList<InoperativeChargesTable>();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * ************************** FUNCTION TO SET THE VALUE IN ACCOUNT TYPE
     * ***************************************************
     */
    public void getDropDownList() {
        try {

            List resultList = new ArrayList();
            resultList = remoteObject.getDropdownList();
            acctNo = new ArrayList<SelectItem>();
            acctNo.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    acctNo.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setAccountToCredit() {
        this.setMessage("");
        setBlank();
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.setDate(acType, ymd.format(sdf.parse(getTodayDate())), getOrgnBrCode());

            this.setFromDate(sdf.parse(resultList.get(0).toString()));
            this.setToDate(sdf.parse(resultList.get(1).toString()));

            resultList = remoteObject.setAcToCredit(acType);
            if (resultList.size() > 0) {
                Vector ele = (Vector) resultList.get(0);
                this.setAmtToCrd(ele.get(0).toString());
                instCharges.clear();
                this.setCalEnable(false);
                this.setPostEnable(true);
            } else {
                setMessage("Gl Head does not define for Inoperative Charges.");
                this.setCalEnable(true);
                this.setPostEnable(true);
                this.setAmtToCrd("");
                this.setCrAmt("0.00");
                this.setDrAmt("0.00");
                instCharges.clear();
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    /**
     * ********************************* FUNCTION TO CALCULATE THE CREDIT AND
     * DEBIT AMT **********************************************
     */
    public void calculateBtn() {
        this.instCharges = new ArrayList<InoperativeChargesTable>();
        this.setMessage("");
        setBlank();
        this.crDrAmt = 0.0d;
        try {
            List<ChargesObject> resultList = remoteObject.inoperativeChargesCaculate(acType, ymd.format(fromDate), ymd.format(toDate), getOrgnBrCode(), getUserName(), ymd.format(sdf.parse(getTodayDate())));

            if (resultList.size() > 0 && resultList.get(0).getMsg().equals("TRUE")) {
                int m = 1;
                for (ChargesObject iOpChargesObj : resultList) {
                    InoperativeChargesTable rd = new InoperativeChargesTable();
                    rd.setSno(m++);
                    rd.setAccountNo(iOpChargesObj.getAcNo());
                    rd.setCustomerName(iOpChargesObj.getCustName());
                    rd.setPenalty(Double.parseDouble(String.valueOf(iOpChargesObj.getPenalty())));
                    this.crDrAmt = this.crDrAmt + Double.parseDouble(String.valueOf(iOpChargesObj.getPenalty()));
                    rd.setStatus("Calculated");
                    instCharges.add(rd);
                }
                if (this.crDrAmt == 0) {
                    this.instCharges = new ArrayList<InoperativeChargesTable>();
                    this.setMessage("Transaction Does Not Exist.");
                } else {
                    this.setCrAmt(formatter.format(crDrAmt));
                    this.setDrAmt(formatter.format(crDrAmt));
                    this.setPostEnable(false);
                }

            } else {
                this.setMessage(resultList.get(0).getMsg());
            }

        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    /**
     * ****************************** FUNCTION TO SET THE FROM DATE AND THE TO
     * DATE ON THE PAGE LOAD **********************************************
     */
    public void setDate() throws ParseException {
        try {
            Date date = new Date();
            String tdate = sdf.format(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            String toDt = maxDay.toString() + tdate.substring(2);
            String frDt = "01" + tdate.substring(2);
            this.setToDate(sdf.parse(toDt));
            this.setFromDate(sdf.parse(frDt));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void postBtn() {
        this.setMessage("");
        setBlank();
        try {
            String resultList;
            resultList = remoteObject.inoperativeChargesPost(acType, amtToCrd, ymd.format(fromDate), ymd.format(toDate),
                    getUserName(), getOrgnBrCode(), ymd.format(sdf.parse(getTodayDate())));
            //System.out.println("resultList -->" + resultList);
            this.setPostEnable(true);
            if (resultList.equalsIgnoreCase("false")) {
            } else {
                setMessage(resultList);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setBlank() {
        this.setCrAmt("0.00");
        this.setDrAmt("0.00");
    }

    public String exitForm() throws ParseException {
        try {
            refreshPage();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }


    /*ADDED BY ROHIT*/
    public void refreshPage() throws ParseException {
        try {
            this.setMessage("");
            this.setCrAmt("0.00");
            this.setDrAmt("0.00");
            this.setDate();
            getDropDownList();
            this.setPostEnable(true);
            this.setCalEnable(false);
            this.setAcType("--Select--");
            Date d = new Date();
            this.setFromDate(d);
            this.setToDate(d);
            this.instCharges = new ArrayList<InoperativeChargesTable>();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
