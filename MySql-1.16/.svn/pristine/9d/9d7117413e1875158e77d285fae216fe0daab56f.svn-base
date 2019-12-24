/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author root
 */
public class SavingIntProductCal extends BaseBean {

    private String message;
    private Date fromDate;
    private Date toDate;
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    private final String sbIntCalcJndiName = "SbIntCalcFacade";
    private SbIntCalcFacadeRemote sbIntCalcRemote = null;

    /**
     * Creates a new instance of SavingIntProductCal
     */
    public SavingIntProductCal() {
        try {
            sbIntCalcRemote = (SbIntCalcFacadeRemote) ServiceLocator.getInstance().lookup(sbIntCalcJndiName);
            setProductCalcDt();
        } catch (ServiceLocatorException e) {
            setMessage(e.getMessage());
        }

    }
    public void setProductCalcDt(){
        try {
            String fromDt = sbIntCalcRemote.getProductFromDt();
            if(fromDt.equals("")){
                setFromDate(new Date());
                setDisabled(false);
            }else{
                this.setFromDate(sdf.parse(sdf.format(ymd.parse(CbsUtil.dateAdd(fromDt,1)))));
                setDisabled(true);
            }
            
            this.setToDate(sdf.parse(sdf.format(ymd.parse(CbsUtil.dateAdd(ymd.format(new Date()),-1)))));
        } catch (ApplicationException | ParseException ex) {
            setMessage(ex.getMessage());
        }
                
    }
    public void calculate() {
        try {
            setMessage("");
            if (fromDate == null) {
                throw new ApplicationException("Please Select From Date.");
            }
            if (toDate == null) {
                throw new ApplicationException("Please Select To Date.");
            }
            if (fromDate.after(toDate)) {
                throw new ApplicationException(" To date can not be greater current Date");
            }
            String rs = sbIntCalcRemote.savingProductCalculation(ymd.format(fromDate), ymd.format(toDate));
            if(rs.equalsIgnoreCase("True")){
                setMessage("Product successfully calculated and posted. Now your can calculate the Interest.");
            }
            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setMessage("");
            setProductCalcDt();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
