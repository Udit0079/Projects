/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author user
 */
public class DdsEnquiry extends BaseBean {
    
    String message;
    private String amount;
    private String period;
    private String percentage;
    private String atotAmt;
    private String matAmount;
    private String totInt;
    AcctEnqueryFacadeRemote acctEnq;

    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getAtotAmt() {
        return atotAmt;
    }

    public void setAtotAmt(String atotAmt) {
        this.atotAmt = atotAmt;
    }

    public String getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(String matAmount) {
        this.matAmount = matAmount;
    }

    public String getTotInt() {
        return totInt;
    }

    public void setTotInt(String totInt) {
        this.totInt = totInt;
    }
    
    public DdsEnquiry() {
        try {
            Date date = new Date();
            acctEnq = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getInputData() {
        this.setMessage("");
        this.setAtotAmt("");
        this.setMatAmount("");
        this.setTotInt("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (this.amount == null || this.amount.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Amount");
                return;
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setMessage("INVALID AMOUNT.PLEASE FILL THE AMOUNT CORRECTLY.");
                    return;
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    this.setMessage("PLEASE FILL THE AMOUNT UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            Matcher amountcheck = p.matcher(amount);
            if (!amountcheck.matches()) {
                this.setMessage("Amount Should  be Numeric.");
                return;
            }
            if (period == null || this.period.equalsIgnoreCase("")) {
                this.setMessage("Please Enter period");
                return;
            }
            if (this.period.contains(".")) {
                if (this.period.indexOf(".") != this.period.lastIndexOf(".")) {
                    this.setMessage("INVALID PERIOD.PLEASE FILL THE PERIOD CORRECTLY.");
                    return;
                } else if (this.period.substring(period.indexOf(".") + 1).length() > 2) {
                    this.setMessage("PLEASE FILL THE PERIOD UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            Matcher gCodecheck = p.matcher(period);
            if (!gCodecheck.matches()) {
                this.setMessage("Period Should  be Numeric.");
                return;
            }
            if (Float.parseFloat(this.period) < 0) {
                this.setMessage("Negative Period Is Not Accepted.");
                return;
            }
            setPercentage("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void calbtnData() {
        try {
            this.setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.amount == null || this.amount.equalsIgnoreCase("")) {
                this.setMessage("Please Enter amount !!!");
                return;
            }
            if (this.amount.contains(".")) {
                if (this.amount.indexOf(".") != this.amount.lastIndexOf(".")) {
                    this.setMessage("INVALID AMOUNT.PLEASE FILL THE AMOUNT CORRECTLY !!!");
                    return;
                } else if (this.amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    this.setMessage("PLEASE FILL THE AMOUNT UPTO TWO DECIMAL PLACES !!!");
                    return;
                }
            }
            Matcher amountcheck = p.matcher(amount);
            if (!amountcheck.matches()) {
                this.setMessage("Amount Should be Numeric !!!");
                return;
            }
            if (Float.parseFloat(amount) < 0) {
                this.setMessage("Amount Must Be Greater Than Zero !!!");
                return;
            }
            if (this.period == null || this.period.equalsIgnoreCase("")) {
                this.setMessage("Please Enter period");
                return;
            }
            if (this.period.contains(".")) {
                if (this.period.indexOf(".") != this.period.lastIndexOf(".")) {
                    this.setMessage("INVALID PERIOD.PLEASE FILL THE PERIOD CORRECTLY !!!");
                    return;
                } else if (this.period.substring(period.indexOf(".") + 1).length() > 2) {
                    this.setMessage("PLEASE FILL THE PERIOD UPTO TWO DECIMAL PLACES !!!");
                    return;
                }
            }
            Matcher periodcheck = p.matcher(period);
            if (!periodcheck.matches()) {
                this.setMessage("Period Should be Numeric !!!");
                return;
            }
            if (Float.parseFloat(this.period) < 0) {
                this.setMessage("Period Must Be Greater Than Zero !!!");
                return;
            }
            if (this.percentage == null || this.percentage.equalsIgnoreCase("")) {
                this.setMessage("Please Enter ROI !!!");
                return;
            }
            if (this.percentage.contains(".")) {
                if (this.percentage.indexOf(".") != this.percentage.lastIndexOf(".")) {
                    this.setMessage("INVALID ROI.PLEASE FILL THE ROI CORRECTLY !!!");
                    return;
                } else if (this.percentage.substring(percentage.indexOf(".") + 1).length() > 2) {
                    this.setMessage("PLEASE FILL THE ROI UPTO TWO DECIMAL PLACES!!!");
                    return;
                }
            }
            Matcher percentagecheck = p.matcher(percentage);
            if (!percentagecheck.matches()) {
                this.setMessage("ROI Should be Numeric!!!");
                return;
            }
            if (Float.parseFloat(this.percentage) < 0) {
                this.setMessage("ROI Must Be Greater Than 0 !!!");
                return;
            }
            List cal = acctEnq.ddsInterestCal(Double.parseDouble(percentage), Double.parseDouble(amount), Float.parseFloat(period));
            if(!cal.isEmpty()){
                this.setAtotAmt(cal.get(0).toString());
                this.setMatAmount(cal.get(1).toString());
                this.setTotInt(cal.get(2).toString());
            }
            
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void reSet1() {
        this.setPeriod("");
        this.setAmount("");
        this.setPercentage("0");
    }

    public void reSet() {
        this.setMessage("");
        this.setPeriod("");
        this.setAmount("");
        this.setPercentage("0");
        this.setAtotAmt("");
        this.setTotInt("");
        this.setMatAmount("");
    }

    public String exitFrm() {
        reSet();
        return "case1";
    }
}
