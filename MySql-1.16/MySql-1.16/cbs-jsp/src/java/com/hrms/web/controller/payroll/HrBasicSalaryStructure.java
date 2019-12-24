/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.cbs.web.controller.BaseBean;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.pojo.HrBasicSalaryStructurePojo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HrBasicSalaryStructure extends BaseBean {

    private String message;
    private String frAmt;
    private String toAmt;
    private String effectivedt;
    private String enterBy;
    private String authBy;
    private String modDate;
    private String salaryTabId;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private PayrollTransactionsFacadeRemote payrollRemote;
    private List<HrBasicSalaryStructurePojo> result;

    public HrBasicSalaryStructure() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            this.setEnterBy(getUserName());
            this.setAuthBy(getUserName());
            this.setEffectivedt(getTodayDate());

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }

    public void btnRefreshAction() {

        this.setEffectivedt(getTodayDate());
        this.setFrAmt("");
        this.setToAmt("");
        this.setMessage("");
    }     
     
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrAmt() {
        return frAmt;
    }

    public void setFrAmt(String frAmt) {
        this.frAmt = frAmt;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public String getEffectivedt() {
        return effectivedt;
    }

    public void setEffectivedt(String effectivedt) {
        this.effectivedt = effectivedt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public void btnSaveAction() {
        try {
            result = new ArrayList<HrBasicSalaryStructurePojo>();

            HrBasicSalaryStructurePojo data = new HrBasicSalaryStructurePojo();
            data.setFromAmount(this.frAmt);
            data.setToAmount(this.toAmt);
            data.setEffectivedt(ymd.format(dmy.parse(this.effectivedt)));
            result.add(data);
            String result1 = payrollRemote.getSaveData(authBy, enterBy, frAmt, toAmt,
                    (ymd.format(dmy.parse(this.effectivedt))), (dt.format(new Date())));

            if (result1.equalsIgnoreCase("true")) {   
                btnRefreshAction();
                this.setMessage("Data saved succefully");
            } else {
                this.setMessage("Data does'nt save seccessfully ");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

   
}
