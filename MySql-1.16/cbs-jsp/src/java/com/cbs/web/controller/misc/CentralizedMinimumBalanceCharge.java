/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.ChargesObject;
import com.cbs.dto.report.MinBalanceChargesPostPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.misc.MinBalnceChargeTable;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class CentralizedMinimumBalanceCharge {

    MinBalanceChargesFacadeRemote remoteObject;
    CommonReportMethodsRemote RemoteCode;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String acctType;
    private String tabledate;
    private String serverdate;
    private String myDate;
    private boolean bFlag;
    private String message;
    private float crDrAmt;
    private double debitAmount;
    private double creditAmount;
    private String glAccountNo;
    private boolean postDisable;
    private boolean calDisable;
    private String accoutToDebited;
    private String crAccount;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private HttpServletRequest req;
    private List<SelectItem> accountype;
    private List<MinBalnceChargeTable> minBalance;
    private boolean amountDisable;
    private boolean glDisable;
    private boolean calcFlag;
    private String currentDate;
    private String monthTest;
    private boolean calcFlag1;
    private String trsNo;
    private String viewID = "/pages/master/sm/test.jsp";
    NumberFormat formatter = new DecimalFormat("#0.00");
    private String branchCode;
    private List<SelectItem> branchCodeList;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(String trsNo) {
        this.trsNo = trsNo;
    }

    public boolean isCalcFlag1() {
        return calcFlag1;
    }

    public void setCalcFlag1(boolean calcFlag1) {
        this.calcFlag1 = calcFlag1;
    }

    public String getMonthTest() {
        return monthTest;
    }

    public void setMonthTest(String monthTest) {
        this.monthTest = monthTest;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public boolean isGlDisable() {
        return glDisable;
    }

    public void setGlDisable(boolean glDisable) {
        this.glDisable = glDisable;
    }

    public boolean isAmountDisable() {
        return amountDisable;
    }

    public void setAmountDisable(boolean amountDisable) {
        this.amountDisable = amountDisable;
    }

    public String getAccoutToDebited() {
        return accoutToDebited;
    }

    public void setAccoutToDebited(String accoutToDebited) {
        this.accoutToDebited = accoutToDebited;
    }

    public boolean isCalDisable() {
        return calDisable;
    }

    public void setCalDisable(boolean calDisable) {
        this.calDisable = calDisable;
    }

    public boolean isPostDisable() {
        return postDisable;
    }

    public void setPostDisable(boolean postDisable) {
        this.postDisable = postDisable;
    }

    public String getGlAccountNo() {
        return glAccountNo;
    }

    public void setGlAccountNo(String glAccountNo) {
        this.glAccountNo = glAccountNo;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public List<MinBalnceChargeTable> getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(List<MinBalnceChargeTable> minBalance) {
        this.minBalance = minBalance;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

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

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public String getMyDate() {
        return myDate;
    }

    public void setMyDate(String myDate) {
        this.myDate = myDate;
    }

    public String getServerdate() {
        return serverdate;
    }

    public void setServerdate(String serverdate) {
        this.serverdate = serverdate;
    }

    public String getTabledate() {
        return tabledate;
    }

    public void setTabledate(String tabledate) {
        this.tabledate = tabledate;
    }

    public List<SelectItem> getAccountype() {
        return accountype;
    }

    public void setAccountype(List<SelectItem> accountype) {
        this.accountype = accountype;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
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

    /**
     * Creates a new instance of MinBalanceCharges
     */
    public CentralizedMinimumBalanceCharge() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            remoteObject = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setUserName(req.getRemoteUser());
            Date date = new Date();
            setTodayDate(sdf.format(date));
            setDate();
            setCalDisable(false);
            setPostDisable(true);
            tableDateChk();
            serverDateChk();
            accountTypeDropDown();
            setAmountDisable(true);
            setGlDisable(true);
            //resetValue();
            minBalance = new ArrayList<MinBalnceChargeTable>();
            branchCodeList = new ArrayList<>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            branchCodeList.add(new SelectItem("0A", "ALL"));
//            if (!brncode.isEmpty()) {
//                for (int i = 0; i < brncode.size(); i++) {
//                    Vector brnVector = (Vector) brncode.get(i);
//                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
//                }
//            } 
            
             List list = remoteObject.setAcToCreditAll(acctType);
             Vector vtr = (Vector)list.get(0);
             crAccount = vtr.get(0).toString();     
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
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
     * ********************* Functionality Of Dropdown Account Type
     * ***********************
     */
    public void accountTypeDropDown() {

//            List resultList = new ArrayList();
//            resultList = remoteObject.accountTypeDropDown();
//            if (!resultList.isEmpty()) {
//                accountype = new ArrayList<SelectItem>();
//                accountype.add(new SelectItem("ALL"));
//                for (int i = 0; i < resultList.size(); i++) {
//                    Vector ele = (Vector) resultList.get(i);
//                    for (Object ee : ele) {
//
//                        accountype.add(new SelectItem(ee.toString()));
//                    }
//                }
//            } else {
//                accountype = new ArrayList<SelectItem>();
//                accountype.add(new SelectItem("--Select--"));
//            }
        accountype = new ArrayList<SelectItem>();
        accountype.add(new SelectItem("ALL"));


    }

    /**
     * **************** END *******************************************
     */
    /**
     * **************** Functionality Of DateCheck Bankdays ***************
     */
    public void tableDateChk() {
        try {
            tabledate = new String();
            tabledate = remoteObject.BDateCheck(orgnBrCode);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * **************** END *********************
     */
    /**
     * ********************** Functionality Of serverDateChk ************
     */
    public void serverDateChk() {
        try {
            serverdate = new String();
            serverdate = remoteObject.serverDateCheck();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * **************** END *********************
     */
    /**
     * ********************* Functionality Of BDateCheck To Cheking The System
     * And Server Date *******
     */
    private void BDateCheck() {
        try {
            myDate = todayDate.substring(6) + todayDate.substring(3, 5) + todayDate.substring(0, 2);
            if (tabledate.equals(myDate)) {
                bFlag = true;
            } else {
                bFlag = false;
                this.setMessage("Login from this System is allowed only in" + tabledate.substring(6, 8) + "/" + tabledate.substring(4, 6) + "/" + tabledate.substring(0, 4));
            }
            if (tabledate.equals(serverdate)) {
                bFlag = true;
            } else {
                bFlag = false;
                this.setMessage("Please Check Server Date!" + tabledate.substring(6, 8) + "/" + tabledate.substring(4, 6) + "/" + tabledate.substring(0, 4));

            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * **************** END *********************
     */
    /**
     * ******************* Functionality Of exitSoft ***************
     */
    public void exitSoft() {

        try {
            String result = remoteObject.exitSoft(userName);
            this.setMessage(result);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * **************** END *********************
     */
    /**
     * ************* Functionaity Of setDate To Setting The Date In Calender
     * **************
     */
    public void setDate() throws ParseException, java.text.ParseException {
        Date date = new Date();
        String tdate = sdf.format(date);
        Integer month = date.getMonth();
        Integer year = date.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (month == 1) {
            calendar.set(year, month - 1, 1);
            maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (month == 0) {
            month = 12;
        }
        String fromDt = maxDay.toString() + "/" + month.toString() + tdate.substring(5);
        String toDt = "01" + "/" + month.toString() + tdate.substring(5);
        setToDate(sdf.parse(fromDt));
        setFromDate(sdf.parse(toDt));
    }

    /**
     * **************** END *********************
     */
    /**
     * *************** Functionaity To Set The Value In TxtA/c To Be Credited
     * *******************
     */
    public void accountToCredit() {
        setMessage("");
        try {
            List resultList = new ArrayList();
            minBalance.clear();
            resultList = remoteObject.setAcToCreditAll(acctType);
            if (resultList.size() > 0) {
                Vector ele = (Vector) resultList.get(0);
                crAccount = ele.get(1).toString();
                crAccount = orgnBrCode + crAccount + "01";
                setDebitAmount(0);
                setCreditAmount(0);
                setAccoutToDebited("");
                setGlAccountNo("");
                minBalance.clear();
                setCalDisable(false);
                setPostDisable(true);
            } else {

                setDebitAmount(0);
                setCreditAmount(0);
                setAccoutToDebited("");
                setGlAccountNo("");
                setMessage("");
                minBalance.clear();
                setPostDisable(true);
                setCalDisable(true);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * **************** END *********************
     */
    /**
     * ************* Functionaity Of Post Button **************
     */
    public void postBtnAction() {
        try {
            BDateCheck();
            if (bFlag == false) {
                exitSoft();
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher insNo = p.matcher(glAccountNo);
//            if (glAccountNo.equals("") || glAccountNo == null) {
//                this.setMessage("Please Enter the A/c To Be Credited");
//                return;
//            }
            String currentDate1;
            Date date = new Date();
            currentDate1 = ymd.format(date);
            String result = remoteObject.minBalanceChargesPostAllBranch(resultList, acctType, glAccountNo, userName, branchCode, creditAmount, currentDate1, ymd.format(fromDate), ymd.format(toDate));
            if (result.substring(0, 4).equalsIgnoreCase("true")) {
                calcFlag1 = true;
                trsNo = result.substring(4);
                this.setMessage("TRANSACTION POSTED SUCCESSFULLY. " + "Transfer Batch No :- " + trsNo);
                String dateString = sdf.format(date);
                List<MinBalanceChargesPostPojo> minBalanceChargesPostReport = new ArrayList<>();
                String v[] = trsNo.split(",");
                String trsNo1 = "";
                for (int i = 0; i < v.length; i++) {
                    trsNo1 = v[i];

                    if (!trsNo1.equalsIgnoreCase("")) {
                        minBalanceChargesPostReport = remoteObject.minBalanceChargesPostReport(Float.parseFloat(trsNo1), acctType, userName, currentDate1, orgnBrCode);
                    }
                }
                String report = "Minimum Balance";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", dateString);
                fillParams.put("pPrintedBy", userName);
                fillParams.put("pAcType", acctType);
                if (!minBalanceChargesPostReport.isEmpty()) {
                    new ReportBean().jasperReport("Minimum_Balance_Charge_Report", "text/html", new JRBeanCollectionDataSource(minBalanceChargesPostReport), fillParams, report);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                }
            } else {
                calcFlag1 = false;
                this.setMessage(result);
            }
            setPostDisable(true);

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }
    List<ChargesObject> resultList = new ArrayList<ChargesObject>();

    public void calculateBtnAction() {
        try {
            if (acctType.equals("--Select--")) {
                this.setMessage("Please Select the value from  DropDown Account Type.");
                return;
            }
            if (fromDate == null) {
                this.setMessage("Please fill the From Date");
                return;
            }
            if (toDate == null) {
                this.setMessage("Please fill the To Date");
                return;
            }
            this.setMessage("");
            Date date = new Date();
            currentDate = ymd.format(date);
            String month = ymd.format(fromDate);
            monthTest = month.substring(4, 6);
            double total = 0f;
            resultList = remoteObject.minBalanceChargesCaculateAllBranch(acctType, ymd.format(fromDate), ymd.format(toDate), branchCode, userName, currentDate);
            if (resultList.isEmpty()) {
                setPostDisable(true);
                setCalDisable(true);
                this.setMessage("There is no data");
                calcFlag = false;
                return;
            }
            if (resultList.size() > 0 && resultList.get(0).getMsg().equals("TRUE")) {
                int i = 0;
                for (ChargesObject minChargeItem : resultList) {
                    MinBalnceChargeTable rd = new MinBalnceChargeTable();
                    rd.setsNo(i + 1);
                    rd.setAccountNo(minChargeItem.getAcNo());
                    rd.setJuly(formatter.format(minChargeItem.getMonth1()));
                    rd.setTransaction(String.valueOf(minChargeItem.getMonth2()));
                    rd.setLimit(String.valueOf(minChargeItem.getMonth2()));
                    rd.setCharges(minChargeItem.getPenalty());
                    if (minChargeItem.getMinbal() != 2) {
                        total = total + minChargeItem.getPenalty();
                    }
                    if (minChargeItem.getMinbal() == 2) {
                        rd.setMinBalnceCharge("Not Applicable");
                    } else {
                        rd.setMinBalnceCharge("Applicable");
                    }
                    minBalance.add(rd);
                    i = i + 1;
                }

                // HttpSession sess = getRequest().getSession();
                // sess.setAttribute("resultList", resultList);

                setDebitAmount(total);
                setCreditAmount(total);
                setAccoutToDebited("As per List In The Report");

                setGlAccountNo(crAccount);
                setPostDisable(false);
                setCalDisable(true);
                glDisable = false;
                calcFlag = true;
                String dateString = sdf.format(date);
                String report = "Minimum Balance Charges";
                String monthName = getMonthName(Integer.parseInt(monthTest) - 1);
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", dateString);
                fillParams.put("pPrintedBy", userName);
                fillParams.put("pMonth", monthName);
                if (!resultList.isEmpty()) {
                    new ReportBean().jasperReport("TMP_NewMinCharges", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                } else {
                    setMessage("No details exists !!");
                    calcFlag = false;
                }
            }

//            else {
//                setPostDisable(true);
//                setCalDisable(true);
//                this.setMessage(resultList.get(0).getMsg());
//                calcFlag = false;
//            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    private String getMonthName(int month) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        return monthName[month];
    }

    /**
     * **************** END *********************
     */
    /**
     * ************* Functionaity Of Blank the Value **************
     */
    public void resetValue() {

        setDebitAmount(0);
        setCreditAmount(0);
        setAccoutToDebited("");
        setGlAccountNo("");
        setMessage("");
        minBalance = new ArrayList<MinBalnceChargeTable>();
        setAcctType("--Select--");
        glDisable = true;


    }

    /**
     * **************** END *********************
     */
    public String btnExit() {
        resetValue();
        return "case1";
    }
}
