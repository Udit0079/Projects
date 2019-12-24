/*
 * CREATED BY :      SHIPRA GUPTA
 * MODIFIED BY:      ROHIT KRISHNA GUPTA ON 10/03/2011
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.InstActiveReg;
import com.cbs.facade.bill.InstrumentFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class InstrumentActiveRegister {

    private String todayDate;
    private String userName;
    private Date orgDt = new Date();
    private Date LossDt = new Date();
    private Date CircularDt = new Date();
    private String billType;
    String orgnBrCode;
    Context ctx;
    String message;
    String instNo;
    String tempBd;
    private int currentRow;
    private int sno;
    private int bookNo;
    private String issueCode;
    private float seqNo;
    private float amount;
    private String branchCode;
    private String infavourOf;
    private String reportingCode;
    private String draweeCode;
    private String status;
    private String circularNo;
    private String SysMsg;
    InstrumentFacadeRemote intrumentFacde;
    HttpServletRequest req;
    private List<SelectItem> billTypedd;
    private List<InstActiveReg> instActiveObj;
    private InstActiveReg currentItem = new InstActiveReg();
    private List<InstActiveReg> instActive;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getSysMsg() {
        return SysMsg;
    }

    public void setSysMsg(String SysMsg) {
        this.SysMsg = SysMsg;
    }

    public InstActiveReg getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InstActiveReg currentItem) {
        this.currentItem = currentItem;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public String getCircularNo() {
        return circularNo;
    }

    public void setCircularNo(String circularNo) {
        this.circularNo = circularNo;
    }

    public float getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(float seqNo) {
        this.seqNo = seqNo;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDraweeCode() {
        return draweeCode;
    }

    public void setDraweeCode(String draweeCode) {
        this.draweeCode = draweeCode;
    }

    public String getInfavourOf() {
        return infavourOf;
    }

    public void setInfavourOf(String infavourOf) {
        this.infavourOf = infavourOf;
    }

    public String getIssueCode() {
        return issueCode;
    }

    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    public String getReportingCode() {
        return reportingCode;
    }

    public void setReportingCode(String reportingCode) {
        this.reportingCode = reportingCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InstActiveReg> getInstActiveObj() {
        return instActiveObj;
    }

    public void setInstActiveObj(List<InstActiveReg> instActiveObj) {
        this.instActiveObj = instActiveObj;
    }

    public List<SelectItem> getBillTypedd() {
        return billTypedd;
    }

    public void setBillTypedd(List<SelectItem> billTypedd) {
        this.billTypedd = billTypedd;
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

    public Date getCircularDt() {
        return CircularDt;
    }

    public void setCircularDt(Date CircularDt) {
        this.CircularDt = CircularDt;
    }

    public Date getLossDt() {
        return LossDt;
    }

    public void setLossDt(Date LossDt) {
        this.LossDt = LossDt;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Date getOrgDt() {
        return orgDt;
    }

    public void setOrgDt(Date orgDt) {
        this.orgDt = orgDt;
    }

    public List<InstActiveReg> getInstActive() {
        return instActive;
    }

    public void setInstActive(List<InstActiveReg> instActive) {
        this.instActive = instActive;
    }

    public InstrumentActiveRegister() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            intrumentFacde = (InstrumentFacadeRemote) ServiceLocator.getInstance().lookup("InstrumentFacade");
            setUserName(req.getRemoteUser());
            Date date = new Date();
            setTodayDate(sdf.format(date));
            setBlank();
            instActiveObj = new ArrayList<InstActiveReg>();
            setMessage("");
            getDropDownList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getDropDownList() {
        setMessage("");
        try {
            List resultList = new ArrayList();
            resultList = intrumentFacde.getDropDownValuesInstrumentActive();
            billTypedd = new ArrayList<SelectItem>();
            billTypedd.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    billTypedd.add(new SelectItem(ee.toString()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getTableDetails() throws NamingException {
        setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher m = p.matcher(instNo);
            if (!m.matches()) {
                setInstNo("");
                this.setMessage("Please Enter numeric Value for Instrument No.");
                return;
            }

            instActiveObj = new ArrayList<InstActiveReg>();
            int length = instNo.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                instNo = "0" + instNo;

            }
            List resultLt = new ArrayList();
            resultLt = intrumentFacde.tableDataInstrumentActive(billType, instNo);
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    InstActiveReg rd = new InstActiveReg();
                    rd.setSno(ele.get(0).toString());
                    rd.setBookNo((ele.get(1).toString()));
                    rd.setSeqNo(ele.get(2).toString());
                    rd.setInstNo(ele.get(3).toString());
                    rd.setBillType(ele.get(4).toString());
                    rd.setAmount(Float.parseFloat(ele.get(5).toString()));
                    rd.setDt(ele.get(6).toString());
                    rd.setBranchcode(ele.get(7).toString());
                    rd.setInfoof(ele.get(8).toString());
                    rd.setIssuecode(ele.get(9).toString());
                    rd.setRepcode(ele.get(10).toString());
                    rd.setDrawcode(ele.get(11).toString());
                    rd.setOrgndt(ele.get(12).toString());
                    rd.setLostdt(ele.get(13).toString());
                    rd.setCirNo(ele.get(14).toString());
                    rd.setCirdt(ele.get(15).toString());
                    rd.setCautionMark(ele.get(16).toString());
                    rd.setEnterby(ele.get(17).toString());
                    instActiveObj.add(rd);
                }
            } else {
                setInstNo("");
                setMessage("Instrument No Does Not Exist");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String sNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (InstActiveReg item : instActiveObj) {
                if (item.getSno().equals(sNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete() {
        setMessage("");
        try {
            Date date = new Date();
            String tdate = ymd.format(date);
            String deleteEntry = new String();
            
            
            deleteEntry = intrumentFacde.instrumentDeletionInstrumentActive(Integer.parseInt(instNo), Integer.parseInt(currentItem.getSno()), tdate, userName, billType);
            instActiveObj.remove(currentRow);
            this.setMessage(deleteEntry);
            setBranchCode("");
            setDraweeCode("");
            setInfavourOf("");
            setInstNo("");
            setReportingCode("");
            setCircularNo("");
            setBookNo(0);
            setAmount(0);
            setStatus("");
            setSeqNo(0);
            setSno(0);
            setInstNo("");
            setIssueCode("");
            setBillType("--SELECT--");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void select() {
        try {
            setMessage("");
            setReportingCode(currentItem.getRepcode());
            setBillType(currentItem.getBillType());
            setSeqNo(Float.parseFloat(currentItem.getSeqNo()));
            setAmount(currentItem.getAmount());
            setBookNo(Integer.parseInt(currentItem.getBookNo()));
            setBranchCode(currentItem.getBranchcode());
            setIssueCode(currentItem.getIssuecode());
            setCircularNo(currentItem.getCirNo());
            setDraweeCode(currentItem.getDrawcode());
            setInfavourOf(currentItem.getInfoof());
            setInstNo(currentItem.getInstNo());
            setCircularNo(currentItem.getCirNo());
            setDraweeCode(currentItem.getDrawcode());
            setSno(Integer.parseInt(currentItem.getSno()));
            setOrgDt(sdf.parse(currentItem.getOrgndt()));
            setLossDt(sdf.parse(currentItem.getLostdt()));
            setCircularDt(sdf.parse(currentItem.getCirdt()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update() {
        setMessage("");
        String msg;
        try {
            if (this.billType.equalsIgnoreCase("--SELECT--") || this.billType == null) {
                this.setMessage("Please Select Bill Type.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher m = p.matcher(instNo);
            if (this.instNo == null || this.instNo.equalsIgnoreCase("")) {
                this.setMessage("Please Enter the Instrument No.");
                return;
            }
            if (this.instNo.contains(".")) {
                this.setMessage("Please Enter Valid Instrument No.");
                return;
            }
            if (!m.matches()) {
                this.setInstNo("");
                this.setMessage("Please Enter Numeric Value for Instrument No.");
                return;
            }
            if (this.sno == 0) {
                this.setMessage("Select the Instrument For Updation.");
                return;
            }
            Date date = new Date();
            String tdate = ymd.format(date);
            //msg = intrumentFacde.instrumentActivate(Integer.parseInt(instNo), sno, tdate, userName, billType);
            setBlank();
            instActiveObj = new ArrayList<InstActiveReg>();
            //instActiveObj.remove(currentRow);
            this.setMessage("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setRefresh() {
        try {
            setBlank();
            instActiveObj = new ArrayList<InstActiveReg>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setBlank() {
        try {
            setBranchCode("");
            setDraweeCode("");
            setInfavourOf("");
            setInstNo("");
            setReportingCode("");
            setCircularNo("");
            setBookNo(0);
            setAmount(0);
            setStatus("");
            setSeqNo(0);
            setSno(0);
            setInstNo("");
            setIssueCode("");
            setBillType("--SELECT--");
            setMessage("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String exitForm() {
        try {
            setBlank();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "case1";
    }
}
