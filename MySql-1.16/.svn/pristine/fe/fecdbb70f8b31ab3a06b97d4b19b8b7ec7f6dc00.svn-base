/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.dto.InterestSlabMasterTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Himanshu Bhatia
 */
public class InterestSlabMaster extends BaseBean{

    private String message;
    private String interestRate;
    private String fromPeriod;
    private String toPeriod;
    private String Enteredby;
    private String lastupdatedt;
    private Date applicableDate;
    
    private String fromAmt;
    private String toAmt;
    private String comm;
    private String surChg;
    private String agtTax;
    private String agSecDep;
    private String dBal;
    
    private List<InterestSlabMasterTable> datagrid;
    private InterestSlabMasterTable currentData;
    private boolean savebtn;
    private boolean updatebtn;
    private DDSManagementFacadeRemote interestSlabMasterRemote;
    
    public InterestSlabMaster() {
        try {
            interestSlabMasterRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            updatebtn = true;
            Date date = new Date();
            setApplicableDate(date);
            initData();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void initData() {
        try {
            datagrid = interestSlabMasterRemote.datalist();
            if (datagrid.isEmpty()) {
                setMessage("No Interest Rate Slab records exist !!");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnSave() {
        try {
            message = validation();
            if (message.equalsIgnoreCase("ok")) {
                String result = interestSlabMasterRemote.insertData(applicableDate, Double.parseDouble(interestRate), fromPeriod, toPeriod, 
                        getUserName(),this.getFromAmt(),this.getToAmt(),this.getComm(),this.getSurChg(),this.getAgtTax(),this.getAgSecDep(),this.getdBal());
                btnRefresh();
                setMessage(result);
                initData();
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefresh() {
        this.savebtn = false;
        this.updatebtn = true;
        this.setMessage("");
        this.setApplicableDate(new Date());
        this.setFromPeriod("");
        this.setToPeriod("");
        this.setInterestRate("");
    }

    public void selectData() {
        try {
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            savebtn = true;
            updatebtn = false;
            applicableDate = dmyFormat.parse(currentData.getApplicableDate());
            interestRate = currentData.getInterestrate();
            fromPeriod = currentData.getFromPeriod();
            toPeriod = currentData.getToPeriod();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExit() {
        try {
            btnRefresh();
            return "case1";
        } catch (Exception e) {
            return null;
        }
    }

    public String validation() {
        try {
            Validator validator = new Validator();
            if (applicableDate == null) {
                return "Enter the Applicable Date";
            }
            if (!validator.validateDoublePositive(interestRate)) {
                return "Field DDS Rate(%) contains illegal characters !!";
            }
            if (fromPeriod == null || fromPeriod.equals("") || checknumeric(fromPeriod)) {
                return checknumeric(fromPeriod) ? "Please enter numeric value in field 'From Period'" : "Enter From Period";
            }
            if (toPeriod == null || toPeriod.equals("") || checknumeric(toPeriod)) {
                return checknumeric(toPeriod) ? "Please enter numeric value in field 'To Period'" : "Enter To Period";
            }
            if (Float.parseFloat(fromPeriod) > Float.parseFloat(toPeriod)) {
                return "From Period should be less then To Period";
            }

            if (interestSlabMasterRemote.checkData(Float.parseFloat(fromPeriod), applicableDate)) {
                return "Slab Already Defined !!";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

        return "ok";
    }

    public boolean checknumeric(String s) {
        try {
            Double.parseDouble(s);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

 
    public boolean isSavebtn() {
        return savebtn;
    }

    public void setSavebtn(boolean savebtn) {
        this.savebtn = savebtn;
    }

    public boolean isUpdatebtn() {
        return updatebtn;
    }

    public void setUpdatebtn(boolean updatebtn) {
        this.updatebtn = updatebtn;
    }

    public InterestSlabMasterTable getCurrentData() {
        return currentData;
    }

    public void setCurrentData(InterestSlabMasterTable currentData) {
        this.currentData = currentData;
    }

    public List<InterestSlabMasterTable> getDatagrid() {
        return datagrid;
    }

    public String getEnteredby() {
        return Enteredby;
    }

    public void setEnteredby(String Enteredby) {
        this.Enteredby = Enteredby;
    }

    public String getLastupdatedt() {
        return lastupdatedt;
    }

    public void setLastupdatedt(String lastupdatedt) {
        this.lastupdatedt = lastupdatedt;
    }

    public void setDatagrid(List<InterestSlabMasterTable> datagrid) {
        this.datagrid = datagrid;
    }

    public String getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public Date getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(Date applicableDate) {
        this.applicableDate = applicableDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromAmt() {
        return fromAmt;
    }

    public void setFromAmt(String fromAmt) {
        this.fromAmt = fromAmt;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getSurChg() {
        return surChg;
    }

    public void setSurChg(String surChg) {
        this.surChg = surChg;
    }

    public String getAgtTax() {
        return agtTax;
    }

    public void setAgtTax(String agtTax) {
        this.agtTax = agtTax;
    }

    public String getAgSecDep() {
        return agSecDep;
    }

    public void setAgSecDep(String agSecDep) {
        this.agSecDep = agSecDep;
    }

    public String getdBal() {
        return dBal;
    }

    public void setdBal(String dBal) {
        this.dBal = dBal;
    }
    
}
