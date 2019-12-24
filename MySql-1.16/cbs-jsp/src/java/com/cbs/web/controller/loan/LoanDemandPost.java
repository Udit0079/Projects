/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.loan.DemandTable;
import com.hrms.web.utils.WebUtil;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

public class LoanDemandPost extends BaseBean {

    String orgnBrCode;
    Context ctx;
    HttpServletRequest req;
    private String todayDate;
    private String userName;
    private String message;
    private String singleAcct;
    private String allAcct;
    private String month;
    private String year;
    private String accountNo, acNoLen;
    private boolean disablePost;
    private boolean disableAcno;
    String acctType = "";
    private List<SelectItem> singleAcctList;
    private List<SelectItem> allAcctList;
    private List<SelectItem> monthYearList;
    private List<DemandTable> dmdtbl;
    LoanGenralFacadeRemote loanGenralFacade;

    public List<DemandTable> getDmdtbl() {
        return dmdtbl;
    }

    public void setDmdtbl(List<DemandTable> dmdtbl) {
        this.dmdtbl = dmdtbl;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public boolean isDisableAcno() {
        return disableAcno;
    }

    public void setDisableAcno(boolean disableAcno) {
        this.disableAcno = disableAcno;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAllAcct() {
        return allAcct;
    }

    public void setAllAcct(String allAcct) {
        this.allAcct = allAcct;
    }

    public List<SelectItem> getAllAcctList() {
        return allAcctList;
    }

    public void setAllAcctList(List<SelectItem> allAcctList) {
        this.allAcctList = allAcctList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthYearList() {
        return monthYearList;
    }

    public void setMonthYearList(List<SelectItem> monthYearList) {
        this.monthYearList = monthYearList;
    }

    public String getSingleAcct() {
        return singleAcct;
    }

    public void setSingleAcct(String singleAcct) {
        this.singleAcct = singleAcct;
    }

    public List<SelectItem> getSingleAcctList() {
        return singleAcctList;
    }

    public void setSingleAcctList(List<SelectItem> singleAcctList) {
        this.singleAcctList = singleAcctList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public LoanDemandPost() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            this.setAcNoLen(getAcNoLength());
            dmdtbl = new ArrayList<DemandTable>();
            setUserName(req.getRemoteUser());
            // setUserName("Manager2");
            setTodayDate(sdf.format(date));
            setMessage("");
            getValues();
            setDisableAcno(true);
            setDisablePost(true);
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getValues() {
        try {
            singleAcctList = new ArrayList<SelectItem>();
            singleAcctList.add(new SelectItem("A", "All Account"));
            allAcctList = new ArrayList<SelectItem>();
            allAcctList.add(new SelectItem("S", "Single Account"));
            monthYearList = new ArrayList<SelectItem>();
            monthYearList.add(new SelectItem("S", "--SELECT--"));
            monthYearList.add(new SelectItem("0", "January"));
            monthYearList.add(new SelectItem("1", "February"));
            monthYearList.add(new SelectItem("2", "March"));
            monthYearList.add(new SelectItem("3", "April"));
            monthYearList.add(new SelectItem("4", "May"));
            monthYearList.add(new SelectItem("5", "June"));
            monthYearList.add(new SelectItem("6", "july"));
            monthYearList.add(new SelectItem("7", "August"));
            monthYearList.add(new SelectItem("8", "September"));
            monthYearList.add(new SelectItem("9", "October"));
            monthYearList.add(new SelectItem("10", "November"));
            monthYearList.add(new SelectItem("11", "December"));

        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void Post() {
        String acno = "";
        message = "";
        String frDt;
        String toDt;
        try {
            if (acctType.equalsIgnoreCase("S")) {
                if (accountNo.equalsIgnoreCase("") || accountNo == null) {
                    setMessage("Please Enter The Account no.");
                    return;
                } else if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please Enter The Account no.");
                    return;
                } else {
                    acno = accountNo;
                    List result;

                    result = loanGenralFacade.accountDetail(acno);
                    if (result.isEmpty()) {
                        this.setMessage("Account No. Does Not Exist.");
                        return;
                    } else {
                        for (int i = 0; i < result.size(); i++) {
                            Vector ele = (Vector) result.get(i);
                            if (ele.get(3).toString().equalsIgnoreCase("9")) {
                                this.setMessage("Account has been closed.");
                                return;
                            }
                        }
                    }
                }


            } else if (acctType.equalsIgnoreCase("A")) {
                acno = "";
            }

            if (month.equalsIgnoreCase("S") || month == null) {
                setMessage("Please Select  Month.");
                return;
            } else if (year.equalsIgnoreCase("") || year == null) {
                setMessage("Please Enter Year.");
                return;
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(year), Integer.parseInt(month), 1);
                calendar.add(Calendar.MONTH, 1);
                frDt = ymd.format(calendar.getTime());
                int lastDate = calendar.getActualMaximum(Calendar.DATE);
                calendar.set(Calendar.DATE, lastDate);
                toDt = ymd.format(calendar.getTime());
            }

            dmdtbl = new ArrayList<DemandTable>();
            List resultList;
            resultList = loanGenralFacade.post(acctType, acno, userName, ymd.format(sdf.parse(todayDate)), frDt, toDt, orgnBrCode);
            if (resultList.size() == 1) {
                setMessage(resultList.get(0).toString());
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector tableVector = (Vector) resultList.get(i);
                    DemandTable mt = new DemandTable();
                    mt.setAcno(tableVector.get(0).toString());
                    mt.setScheduleNo(tableVector.get(1).toString());
                    mt.setFlowId(tableVector.get(2).toString());
                    mt.setDemandDate(sdf.format(ymmd.parse(tableVector.get(3).toString())));
                    mt.setFreqtype(tableVector.get(4).toString());
                    mt.setDmdEffDt(sdf.format(ymmd.parse(tableVector.get(5).toString())));
                    mt.setOverDueDt(sdf.format(ymmd.parse(tableVector.get(6).toString())));
                    mt.setDmdAmt(tableVector.get(7).toString());
                    mt.setEquatedAmt(tableVector.get(8).toString());
                    mt.setLatePayFee(tableVector.get(9).toString());
                    dmdtbl.add(mt);
                }
                setMessage("Demand posted successfully");
                setDisablePost(true);

            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void disableAllAcct() {
        setMessage("");
        setSingleAcct("");
        setAccountNo("");
        setYear("");
        setMonth("S");
        setDisableAcno(false);
        acctType = "S";
    }

    public void changeOnYear() {
        setDisablePost(false);
    }

    public void disableSingleAcct() {
        setMessage("");
        setAllAcct("");
        setAccountNo("");
        setYear("");
        setMonth("S");
        setDisableAcno(true);
        acctType = "A";
    }

    public void refresh() {
        setSingleAcct("");
        setAllAcct("");
        setAccountNo("");
        setMonth("S");
        setYear("");
        setMessage("");
        setDisablePost(true);
        dmdtbl = new ArrayList<DemandTable>();
    }

    public void checkAccountNo() {
        setMessage("");
        try {
            List resultList;
            resultList = loanGenralFacade.accountDetail(accountNo);
            if (resultList.isEmpty()) {
                this.setMessage("Account No. Does Not Exist.");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(3).toString().equalsIgnoreCase("9")) {
                        this.setMessage("Account has been closed.");
                        return;
                    }
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public String exitForm() {

        refresh();
        this.setMessage("");
        return "case1";
    }
}
