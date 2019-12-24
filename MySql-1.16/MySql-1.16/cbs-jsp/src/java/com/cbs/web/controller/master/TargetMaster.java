/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.dto.TargentMasterGrid;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Nishant Srivastava
 */
public class TargetMaster extends BaseBean {

    private Date fromDate = new Date();
    private Date toDate = new Date();
    private String userName;
    private String todayDate;
    private String flag1;
    private String flag2;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private String sno;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String accountType;
    private List<SelectItem> accountTypeList;
    private String actype;
    private List<SelectItem> acTypeList;
    private String targetAc;
    private String tragetAmount;
    private String monthlyYear;
    private List<SelectItem> monthlyYearList;
    private String year;
    private List<SelectItem> yearList;
    private TargentMasterGrid currentItem = new TargentMasterGrid();
    private List<TargentMasterGrid> gridDetail;
    int currentRow;
    private final String quarterlyInterestCalJndiName = "RDIntCalFacade";
    private RDIntCalFacadeRemote quarterlyInterestCalculationRemote = null;
    private String month;
    private List<SelectItem> monthList;
    private boolean addFlag;
    private String orgntype = "0";
    private String orgnCode = "0";
    private String matAmtLblDisFlag = "none";
    private String matAmtTxtDisFlag = "none";
    private String acctNature;
    private List<SelectItem> acctNaturelist;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Pattern getPm() {
        return pm;
    }

    public void setPm(Pattern pm) {
        this.pm = pm;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public List<SelectItem> getAcctNaturelist() {
        return acctNaturelist;
    }

    public void setAcctNaturelist(List<SelectItem> acctNaturelist) {
        this.acctNaturelist = acctNaturelist;
    }

    public String getMatAmtLblDisFlag() {
        return matAmtLblDisFlag;
    }

    public void setMatAmtLblDisFlag(String matAmtLblDisFlag) {
        this.matAmtLblDisFlag = matAmtLblDisFlag;
    }

    public String getMatAmtTxtDisFlag() {
        return matAmtTxtDisFlag;
    }

    public void setMatAmtTxtDisFlag(String matAmtTxtDisFlag) {
        this.matAmtTxtDisFlag = matAmtTxtDisFlag;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getOrgnCode() {
        return orgnCode;
    }

    public void setOrgnCode(String orgnCode) {
        this.orgnCode = orgnCode;
    }

    public String getOrgntype() {
        return orgntype;
    }

    public void setOrgntype(String orgntype) {
        this.orgntype = orgntype;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TargentMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TargentMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public TargentMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TargentMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String Year) {
        this.year = Year;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> YearList) {
        this.yearList = YearList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMonthlyYear() {
        return monthlyYear;
    }

    public void setMonthlyYear(String monthlyYear) {
        this.monthlyYear = monthlyYear;
    }

    public List<SelectItem> getMonthlyYearList() {
        return monthlyYearList;
    }

    public void setMonthlyYearList(List<SelectItem> monthlyYearList) {
        this.monthlyYearList = monthlyYearList;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getTargetAc() {
        return targetAc;
    }

    public void setTargetAc(String targetAc) {
        this.targetAc = targetAc;
    }

    public String getTragetAmount() {
        return tragetAmount;
    }

    public void setTragetAmount(String tragetAmount) {
        this.tragetAmount = tragetAmount;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public TargetMaster() {
        req = getRequest();
        try {

            Date date = new Date();
            setTodayDate(sdf.format(date));
            quarterlyInterestCalculationRemote = (RDIntCalFacadeRemote) ServiceLocator.getInstance().lookup(quarterlyInterestCalJndiName);

            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--SELECT--"));
            acTypeList.add(new SelectItem("D", "D"));
            acTypeList.add(new SelectItem("L", "L"));

            monthlyYearList = new ArrayList<SelectItem>();
            monthlyYearList.add(new SelectItem("--SELECT--"));
            monthlyYearList.add(new SelectItem("M", "MONTHLY"));
            monthlyYearList.add(new SelectItem("Y", "YEARLY"));

            List resultList = new ArrayList();
            resultList = quarterlyInterestCalculationRemote.getAcctNature();
            accountTypeList = new ArrayList<SelectItem>();
            if (!resultList.isEmpty()) {
                accountTypeList.add(new SelectItem("--SELECT--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }

        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getMonthYEAR() {
        try {
            if (this.monthlyYear.equals("M")) {
                this.getMonthly();
                this.getYearly();
                flag1 = "true";
            } else {
                if (this.monthlyYear.equals("Y")) {
                    this.getYearly();
                    flag2 = "true";
                } else {
                    this.month.equalsIgnoreCase("S");
                    flag1 = "false";
                    flag2 = "false";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getMonthly() {
        monthList = new ArrayList<SelectItem>();
        monthList.add(new SelectItem("S", "--SELECT--"));
        monthList.add(new SelectItem("0", "January"));
        monthList.add(new SelectItem("1", "February"));
        monthList.add(new SelectItem("2", "March"));
        monthList.add(new SelectItem("3", "April"));
        monthList.add(new SelectItem("4", "May"));
        monthList.add(new SelectItem("5", "June"));
        monthList.add(new SelectItem("6", "july"));
        monthList.add(new SelectItem("7", "August"));
        monthList.add(new SelectItem("8", "September"));
        monthList.add(new SelectItem("9", "October"));
        monthList.add(new SelectItem("10", "November"));
        monthList.add(new SelectItem("11", "December"));
    }

    public void getYearly() {
        try {
            yearList = new ArrayList<SelectItem>();
            Calendar now = Calendar.getInstance();
            for (int i = -15; i < 15; i++) {
                int year = now.get(Calendar.YEAR) + i;
                yearList.add(new SelectItem(String.valueOf(year)));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<TargentMasterGrid>();
            List resultList = new ArrayList();
            resultList = quarterlyInterestCalculationRemote.gridDetailTargetMaster(this.accountType);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    TargentMasterGrid tm = new TargentMasterGrid();
                    tm.setAccountType(ele.get(0).toString());
                    tm.setAcctNature(ele.get(1).toString());
                    tm.setOrgntype(ele.get(2).toString());
                    tm.setOrgnCode(ele.get(3).toString());
                    tm.setTargetAc(ele.get(4).toString());
                    tm.setTragetAmount(ele.get(5).toString());
                    tm.setMonth(ele.get(6).toString());
                    tm.setYear(ele.get(7).toString());
                    tm.setActype(ele.get(8).toString());
                    tm.setMonthlyYear(ele.get(9).toString());
                    tm.setEnterBy(ele.get(10).toString());
                    tm.setEnterBy(ele.get(11).toString());
                    tm.setEnterDate(ele.get(12).toString());
                    gridDetail.add(tm);
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAccountType(currentItem.getAccountType());
            this.setAcctNature(currentItem.getAcctNature());
            this.setOrgntype(currentItem.getOrgntype());
            this.setOrgnCode(currentItem.getOrgnCode());
            this.setTargetAc(currentItem.getTargetAc());
            this.setTragetAmount(currentItem.getTragetAmount());
            this.setMonth(currentItem.getMonth());
            this.setYear(currentItem.getYear());
            this.setActype(currentItem.getActype());
            this.setMonthlyYear(currentItem.getMonthlyYear());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getacctNature() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            this.setErrorMessage("");
            this.setMessage("");
            List resultList1 = new ArrayList();
            resultList1 = quarterlyInterestCalculationRemote.Acctnature(accountType);
            if (resultList1.size() > 0) {
                Vector ele = (Vector) resultList1.get(0);
                acctNature = ele.get(0).toString();
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveMasterDetail() {
        String frDt = "";
        String toDt = "";
        try {
            this.setErrorMessage("");
            this.setMessage("");          
            if (this.accountType.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select Account Decrepitation. ");
                return ;
            }
            if (this.actype.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select Account Type. ");
                return ;
            }

            if (this.targetAc == null || this.targetAc.equalsIgnoreCase("") || this.targetAc.length() !=12) {
                this.setErrorMessage("Target A/c's should be of 12 Digit !!!");
                return ;
            }
            Matcher targetAc1 = pm.matcher(this.targetAc);
            if (!targetAc1.matches()) {
                this.setErrorMessage("Target A/c's does not contain special character's ");
                return;
            }
            if (this.tragetAmount == null || this.tragetAmount.equalsIgnoreCase("") || this.tragetAmount.length() == 0) {
                this.setErrorMessage("Please enter Target A/c's.");
                return;
            }
            Matcher tragetAmount1 = pm.matcher(this.tragetAmount);
            if (!tragetAmount1.matches()) {
                this.setErrorMessage("Traget Amount should not contain special characters !!!");
                return;
            }
            if (this.monthlyYear.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select Monthly/Yearly Type. ");
                return;
            }
            if (this.monthlyYear.equalsIgnoreCase("M")) {
                if (this.month.equalsIgnoreCase("S")) {
                    this.setErrorMessage("Plese select Month.");
                    return;
                }
            } else {
                if (this.year.equalsIgnoreCase("")) {
                    this.setErrorMessage("Please select Year.");
                    return;
                }
            }
            if (this.monthlyYear.equalsIgnoreCase("M")) {
                if (month.equalsIgnoreCase("S") || month == null) {
                    setMessage("Please Select  Month.");
                    return;
                } else if (year.equalsIgnoreCase("") || year == null) {
                    setMessage("Please Enter Year.");
                    return;
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(year), (Integer.parseInt(month) - 1), 1);
                    calendar.add(Calendar.MONTH, 1);
                    frDt = ymd.format(calendar.getTime());
                    int lastDate = calendar.getActualMaximum(Calendar.DATE);
                    calendar.set(Calendar.DATE, lastDate);
                    toDt = ymd.format(calendar.getTime());
                }
            }

            if (this.monthlyYear.equalsIgnoreCase("Y")) {
                if (year.equalsIgnoreCase("") || year == null) {
                    setMessage("Please Select  Year.");
                    return;
                } else {
                    frDt = Integer.parseInt(year) + "/04/01";
                    toDt = ((Integer.parseInt(year) + 1) + "/03/31");

                }
            }

            String result = quarterlyInterestCalculationRemote.saveRecordTargent(accountType, acctNature, orgntype, orgnCode, targetAc, tragetAmount, frDt, toDt, monthlyYear, actype, getUserName(), ymd.format(new Date()));
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                return;
            } else {
                this.setMessage(result);
            }
            this.setAddFlag(false);
            gridLoad();

        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        }

    }

    public void refreshForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAccountType("--SELECT--");
            this.setActype("--SELECT--");
            this.setMonthlyYear("--SELECT--");
            this.setTargetAc("");
            this.setTragetAmount("");
            this.setMonth("S");
            this.setYear("");
            gridLoad();

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
