package com.cbs.web.controller.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

public class RdEnquiry {

    Context ctx;
    AcctEnqueryFacadeRemote remoteObject;
    private String todayDate;
    private String userName;
    private String amount;
    private String period;
    private String percentage;
    private String atotAmt;
    String message;
    private String totInterest;
    private HttpServletRequest req;
    private String orgnBrCode;
    private String tillDate;
    private String aatotAmt;
    private String atotInterest;
    //boolean calculate;
    private String flag1;
    boolean flag2;

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public String getAatotAmt() {
        return aatotAmt;
    }

    public void setAatotAmt(String aatotAmt) {
        this.aatotAmt = aatotAmt;
    }

    public String getAtotInterest() {
        return atotInterest;
    }

    public void setAtotInterest(String atotInterest) {
        this.atotInterest = atotInterest;
    }

    public String getAtotAmt() {
        return atotAmt;
    }

    public void setAtotAmt(String atotAmt) {
        this.atotAmt = atotAmt;
    }

    public String getTotInterest() {
        return totInterest;
    }

    public void setTotInterest(String totInterest) {
        this.totInterest = totInterest;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public RdEnquiry() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            setTillDate(ymd.format(date));
            reSet();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getInputData() {
        this.setMessage("");
        this.setAtotAmt("");
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
            //String charge = remoteObject.roiSearch(Float.parseFloat(period), tillDate);

//            if (charge.equals("No Interest Rate Exists.")) {
//                //calculate = true;
//                this.setMessage("No Interest Rate Exists,Please Fill Right Period");
//                flag2 = false;
//                //this.setPeriod("");
//                this.setPercentage("0");
//                this.setAtotAmt("");
//                flag1 = "false";
//                return;
//            } else {
                //calculate = false;
                setPercentage("0");
//            }

        //} catch (ApplicationException e) {
   //         setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void calbtnData() {
        String cal = null;
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
            cal = remoteObject.rdRoiCal(Float.parseFloat(percentage), Float.parseFloat(amount), Float.parseFloat(period));
            //System.out.println("cal     "+cal);
            this.setAtotAmt(cal);
            //reSet1();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void reSet1() {
        //calculate = true;
        this.setPeriod("");
        this.setAmount("");
        this.setPercentage("0");
    }

    public void reSet() {
        flag2 = true;
        //calculate = true;
        this.setMessage("");
        this.setPeriod("");
        this.setAmount("");
        this.setPercentage("0");
        this.setAtotAmt("");
        this.setTotInterest("");
    }

    public String exitFrm() {
        reSet();
        return "case1";
    }
}
