/*
 Document   : InstumentLostRegister
 Created on : Aug 16, 2010, 5:03:10 PM
 Author     : Zeeshan Waris
 */
package com.cbs.web.controller.bill;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.bill.InstrLostReg;
import com.cbs.facade.bill.InstrumentFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

/**
 *
 * @author root
 */
public class InstrumentLostRegister extends BaseBean {

    InstrumentFacadeRemote intrumentFacde;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String billType;
    private String issueCode;
    private String circularNo;
    private String instrumentno;
    private String sequeNo;
    private String infavourOf;
    private String reportingCode;
    private String lossDt;
    private String circularDt;
    private String amount;
    private String drCode;
    private String message;
    private String function;
    private String btnValue;
    private String showMsg;
    private Date orignDate = new Date();
    private Date lossDate = new Date();
    private Date circularDate = new Date();
    private List<SelectItem> brCodeList;
    private List<SelectItem> functionList;
    private List<SelectItem> billTypeList;
    private List<InstrLostReg> instrReg;
    private int currentRow;
    private InstrLostReg currentItem = new InstrLostReg();
    private boolean flag;
    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getSequeNo() {
        return sequeNo;
    }

    public void setSequeNo(String sequeNo) {
        this.sequeNo = sequeNo;
    }

    public Date getCircularDate() {
        return circularDate;
    }

    public void setCircularDate(Date circularDate) {
        this.circularDate = circularDate;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public Date getOrignDate() {
        return orignDate;
    }

    public void setOrignDate(Date orignDate) {
        this.orignDate = orignDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InstrLostReg> getInstrReg() {
        return instrReg;
    }

    public void setInstrReg(List<InstrLostReg> instrReg) {
        this.instrReg = instrReg;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getCircularDt() {
        return circularDt;
    }

    public void setCircularDt(String circularDt) {
        this.circularDt = circularDt;
    }

    public String getCircularNo() {
        return circularNo;
    }

    public void setCircularNo(String circularNo) {
        this.circularNo = circularNo;
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public String getInfavourOf() {
        return infavourOf;
    }

    public void setInfavourOf(String infavourOf) {
        this.infavourOf = infavourOf;
    }

    public String getInstrumentno() {
        return instrumentno;
    }

    public void setInstrumentno(String instrumentno) {
        this.instrumentno = instrumentno;
    }

    public String getIssueCode() {
        return issueCode;
    }

    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    public String getLossDt() {
        return lossDt;
    }

    public void setLossDt(String lossDt) {
        this.lossDt = lossDt;
    }

    public String getReportingCode() {
        return reportingCode;
    }

    public void setReportingCode(String reportingCode) {
        this.reportingCode = reportingCode;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> bilTypeList) {
        this.billTypeList = bilTypeList;
    }

    public List<SelectItem> getBrCodeList() {
        return brCodeList;
    }

    public void setBrCodeList(List<SelectItem> brCodeList) {
        this.brCodeList = brCodeList;
    }

    public InstrLostReg getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InstrLostReg currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * Creates a new instance of InstrumentLostRegister
     */
    public InstrumentLostRegister() {
        try {
            intrumentFacde = (InstrumentFacadeRemote) ServiceLocator.getInstance().lookup("InstrumentFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            instrReg = new ArrayList<InstrLostReg>();
            getDropDownList();
            getBillList();
            resetValue();
            this.setMessage("");
            this.setFlag(true);
            setAmount("0.0");
            setBtnValue("Save");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDropDownList() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("A", "Active"));
            functionList.add(new SelectItem("L", "Lost"));
            functionList.add(new SelectItem("E", "Edit"));
            functionList.add(new SelectItem("V", "Verify"));

            List resultList = intrumentFacde.branchCodeDropDown();
            if (!resultList.isEmpty()) {
                brCodeList = new ArrayList<SelectItem>();
                brCodeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        brCodeList.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                brCodeList = new ArrayList<SelectItem>();
                brCodeList.add(new SelectItem("--Select--"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getBillList() {
        try {
            List resultList = new ArrayList();
            resultList = intrumentFacde.billtypeDropDown();
            if (!resultList.isEmpty()) {
                billTypeList = new ArrayList<SelectItem>();
                billTypeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        billTypeList.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                billTypeList = new ArrayList<SelectItem>();
                billTypeList.add(new SelectItem("--Select--"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeFunction() {
        if (getFunction().equals("A")) {
            setBtnValue("Active");
            setFlag(true);
            setShowMsg("Are you sure to mark Active this instrument?");
        } else if (getFunction().equals("L")) {
            setBtnValue("Lost");
            setFlag(false);
            setShowMsg("Are you sure to mark Lost this instrument?");
        } else if (getFunction().equals("E")) {
            setBtnValue("Update");
            setFlag(true);
            setShowMsg("Are you sure to Update this instrument details?");
        } else {
            setBtnValue("Verify");
            setFlag(true);
            setShowMsg("Are you sure to verify this instrument details?");
        }
    }

    public void getTableDetails() throws NamingException {
        instrReg = new ArrayList<InstrLostReg>();
        try {
            List resultLt = intrumentFacde.tableDataInstrumentLost(billType, function, getOrgnBrCode());
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    InstrLostReg rd = new InstrLostReg();
                    rd.setSno(ele.get(0).toString());
                    rd.setSeqNo(ele.get(2).toString());
                    rd.setInstNo(ele.get(3).toString());

                    rd.setBillType(ele.get(4).toString());
                    rd.setAmount(ele.get(5).toString());
                    rd.setDt(ele.get(6).toString());
                    rd.setInfoof(ele.get(8).toString());

                    rd.setIssuecode(ele.get(9).toString());
                    rd.setRepcode(ele.get(10).toString());
                    rd.setDrawcode(ele.get(11).toString());
                    rd.setOrgndt(ele.get(12).toString());

                    rd.setLostdt(ele.get(13).toString());
                    rd.setCirNo(ele.get(14).toString());
                    rd.setCirdt(ele.get(15).toString());
                    rd.setEnterby(ele.get(16).toString());
                    if (ele.get(17).toString().equals("L")) {
                        rd.setStatus("Lost");
                    } else {
                        rd.setStatus("Active");
                    }
                    rd.setAuth(ele.get(18).toString());
                    instrReg.add(rd);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getInstDetails() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher insNo = p.matcher(instrumentno);
            if (billType.equals("")) {
                this.setMessage("Please select Bill Type.");
                return;
            }
            if (instrumentno.equals("")) {
                this.setMessage("Please Enter the Instrument No.");
                return;
            }
            if (!insNo.matches()) {
                this.setMessage("Instrument No. should be numeric");
                return;
            }
            if (Integer.parseInt(instrumentno) < 0) {
                this.setMessage("Instrument No. can't be negative");
                return;
            }
            if (orignDate == null || orignDate.equals("")) {
                this.setMessage("Please fill the Origin Date");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List instList = intrumentFacde.getInstDetails(billType, instrumentno, ymd.format(orignDate), issueCode);
            if (instList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
            Vector instDetails = (Vector) instList.get(0);
            setSequeNo(instDetails.get(0).toString());
            setAmount(instDetails.get(1).toString());
            setInfavourOf(instDetails.get(2).toString());
            setDrCode(instDetails.get(3).toString());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void select() {
         flag = false;
        this.setMessage("");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setAmount(currentItem.getAmount());
            setLossDate(sdf.parse(currentItem.getLostdt()));
            setOrignDate(sdf.parse(currentItem.getOrgndt()));
            setCircularDate(sdf.parse(currentItem.getCirdt()));

            setCircularNo(currentItem.getCirNo());
            setInstrumentno(currentItem.getInstNo());
            setSequeNo(currentItem.getSeqNo());
            setInfavourOf(currentItem.getInfoof());

            setIssueCode(currentItem.getIssuecode());
            setReportingCode(currentItem.getRepcode());
            setDrCode(currentItem.getDrawcode());
            setBillType(currentItem.getBillType());
        } catch (ParseException ex) {
            setMessage(ex.getMessage());
        }

    }

    public void delete() {
        this.setMessage("");
        try {
            String deleteEntry = intrumentFacde.instLostDeletion(currentItem.getInstNo(), Integer.parseInt(currentItem.getSno()), getUserName(), billType);
            this.setMessage(deleteEntry);
            getTableDetails();
            resetValue();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void saveBtnAction() {
        this.setMessage("");
        try {
            validate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String orgnDt = sdf.format(this.orignDate);
            String losDt = sdf.format(this.lossDate);
            String cirDt = sdf.format(this.circularDate);
            String result = "";
            if (function.equals("L")) {
                result = intrumentFacde.instLostSaveUpdate("save", billType, instrumentno, Float.parseFloat(sequeNo),
                        Float.parseFloat(amount), infavourOf, issueCode, reportingCode, drCode, orgnDt, losDt, circularNo, getUserName(), 0,
                        sdf.format(new Date()), cirDt, getOrgnBrCode());
            } else if (function.equals("E")) {
                result = intrumentFacde.instLostSaveUpdate("update", billType, instrumentno, Float.parseFloat(sequeNo),
                        Float.parseFloat(amount), infavourOf, issueCode, reportingCode, drCode, orgnDt, losDt, circularNo, getUserName(),
                        Integer.parseInt(currentItem.getSno()), sdf.format(new Date()), cirDt, getOrgnBrCode());
            } else if (function.equals("A")) {
                result = intrumentFacde.instrumentActivate(instrumentno, Integer.parseInt(currentItem.getSno()), getUserName(), billType);
            } else {
                if (ftsPostRemote.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    if (this.getUserName().equals(currentItem.getEnterby())) {
                        result = "You can not authorize your own entry.";
                    }else{
                        result = intrumentFacde.instVerified(currentItem.getInstNo(), Integer.parseInt(currentItem.getSno()), getUserName(), billType);
                    }
                }else{
                    result ="You are not authorized person for verifing this detail.";
                }
            }
            this.setMessage(result);
            resetValue();
            getTableDetails();
        } catch (NamingException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private void validate() throws ApplicationException {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher insNo = p.matcher(instrumentno);
        Matcher cNo = p.matcher(circularNo);
        if (instrumentno.equals("")) {
            throw new ApplicationException("Please Enter the Instrument No.");
        }
        if (!insNo.matches()) {
            throw new ApplicationException("Instrument No. Should be Numeric");
        }
        if (Integer.parseInt(instrumentno) < 0) {
            throw new ApplicationException("Instrument No. Can't be Negative");
        }
        if (sequeNo.equals("")) {
            throw new ApplicationException("Seq no is blank");
        }
        if (infavourOf.equals("")) {
            throw new ApplicationException("Infavour Of is blank");
        }
        if (issueCode.equals("--Select--")) {
            throw new ApplicationException("Please Select the value from DropDown Issue Code .");
        }
        if (reportingCode.equals("--Select--")) {
            throw new ApplicationException("Please Select the value from DropDown Reporting Code .");
        }
        if (drCode.equals("--Select--")) {
            throw new ApplicationException("Please Select the value from DropDown Drawee Code .");
        }
        if (orignDate == null || orignDate.equals("")) {
            throw new ApplicationException("Please fill the Origin Date");
        }
        if (lossDate == null || lossDate.equals("")) {
            throw new ApplicationException("Please fill the Loss Date");
        }
        if (circularNo.equals("")) {
            throw new ApplicationException("Please Enter the Circular No.");
        }
        if (!cNo.matches()) {
            throw new ApplicationException("Circular No. should be Numeric");
        }
        if (Integer.parseInt(circularNo) < 0) {
            throw new ApplicationException("Circular No. can't Be Negative");
        }
        if (circularDate == null || circularDate.equals("")) {
            throw new ApplicationException("Please fill the Circular Date");
        }
    }

    public void resetValue() {
        setCircularNo("");
        setInfavourOf("");
        setInstrumentno("");
        setSequeNo("");
        setAmount("0.0");
        setIssueCode("--Select--");
        setDrCode("--Select--");
        setReportingCode("");
        setFlag(true);
        setBtnValue("Save");
    }

    public void resetButton() {
        this.setMessage("");
        setCircularNo("");
        setInfavourOf("");
        setInstrumentno("");
        setSequeNo("");
        setAmount("0.0");
        setBillType("--Select--");
        setIssueCode("--Select--");
        setFunction("0");
        setReportingCode("");
        setDrCode("--Select--");
        if (instrReg.size() > 0) {
            instrReg.clear();
        }
        setFlag(true);
        setBtnValue("Save");
    }

    public String exitForm() {
        resetButton();
        return "case1";
    }
}
