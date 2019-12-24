/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.agentCommPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.facade.other.DailyProcessManagementRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author user
 */
public class AgentRdComm extends BaseBean {

    private String message;
    private String branchOpt;
    private List<SelectItem> branchOptionList;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private String accoutToCredited;
    private double creditAmount;
    private String viewID = "/pages/master/sm/test.jsp";
    private String calDisable;
    private boolean calcFlag;
    private boolean postDisable;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    MinBalanceChargesFacadeRemote remoteObject;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private final String jndiHomeName = "DailyProcessManagementFacade";
    private DailyProcessManagementRemote beanRemote = null;
    private String tempBd;
    private boolean calcFlag1;

    public boolean isCalcFlag1() {
        return calcFlag1;
    }

    public void setCalcFlag1(boolean calcFlag1) {
        this.calcFlag1 = calcFlag1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranchOpt() {
        return branchOpt;
    }

    public void setBranchOpt(String branchOpt) {
        this.branchOpt = branchOpt;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
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

    public String getAccoutToCredited() {
        return accoutToCredited;
    }

    public void setAccoutToCredited(String accoutToCredited) {
        this.accoutToCredited = accoutToCredited;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getCalDisable() {
        return calDisable;
    }

    public void setCalDisable(String calDisable) {
        this.calDisable = calDisable;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public boolean isPostDisable() {
        return postDisable;
    }

    public void setPostDisable(boolean postDisable) {
        this.postDisable = postDisable;
    }

    public String getTempBd() {
        return tempBd;
    }

    public void setTempBd(String tempBd) {
        this.tempBd = tempBd;
    }
    List<agentCommPojo> resultList = new ArrayList<agentCommPojo>();

    public AgentRdComm() {
        try {
            setUserName(this.getUserName());
            Date date = new Date();
            setTodayDate(sdf.format(date));
            remoteObject = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            beanRemote = (DailyProcessManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            calDisable = "true";
            setPostDisable(true);
            branchOptionList = new ArrayList<>();
            List allBranchCodeList = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (allBranchCodeList.isEmpty()) {
                this.setMessage("Please define branchmaster.");
                return;
            } else {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchOptionList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString()
                            : vec.get(0).toString(), vec.get(1).toString()));
                }
            }
            checkMonthEndDate();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void checkMonthEndDate() {
        calDisable = "false";
        try {
            List listBankDays = beanRemote.selectFromBankDays(getOrgnBrCode());
            if (!listBankDays.isEmpty()) {
                Vector vecBankDays = (Vector) listBankDays.get(0);
                tempBd = vecBankDays.get(0).toString();

                String tdDate = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);
                Long longTdDate = Long.parseLong(tdDate);
                String minMonthDate = beanRemote.getMinMonthDate(getOrgnBrCode());

                Long longMonthDate = Long.parseLong(minMonthDate);
                if (longTdDate.compareTo(longMonthDate) > 0 || longTdDate.compareTo(longMonthDate) == 0) {
                    calDisable = "false";
                }
                if (beanRemote.isHolidayDate(minMonthDate, getOrgnBrCode())) {
                    String maxDt = beanRemote.maxWorkingDate(minMonthDate, getOrgnBrCode());
                    if (maxDt == null || maxDt.equalsIgnoreCase("")) {
                        String holiday = CbsUtil.dateAdd(tdDate, 1);
                        if (beanRemote.isHolidayDate(holiday, getOrgnBrCode())) {
                            calDisable = "false";
                        }
                    } else {
                        if (ymd.parse(tdDate).compareTo(ymd.parse(maxDt)) == 0) {
                            calDisable = "false";
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setDates() {
        try {
            String frDt = remoteObject.getDtForCommPost(this.branchOpt, "f");
            this.setFromDate(sdf.parse(sdf.format(ymd.parse(frDt))));
            String tDt = remoteObject.getDtForCommPost(this.branchOpt, "t");
            this.setToDate(sdf.parse(sdf.format(ymd.parse(tDt))));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void calculateBtnAction() {
        try {
            if (fromDate == null) {
                this.setMessage("Please fill the From Date");
                return;
            }
            if (toDate == null) {
                this.setMessage("Please fill the To Date");
                return;
            }
            this.setMessage("");
            double total = 0f;
            resultList = remoteObject.agentCommCalculate(ymd.format(fromDate), ymd.format(toDate), this.getBranchOpt());
            if (resultList.isEmpty()) {
                setPostDisable(true);
                calDisable = "true";
                this.setMessage("There is no data");
                calcFlag = false;
                return;
            }
            String report = "Agent Commission Report";
            Map fillParams = new HashMap();
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    total = total + resultList.get(i).getNetAmount();
                    setCreditAmount(total);
                    setAccoutToCredited("As per List In The Report");
                    setPostDisable(false);
                    calDisable = "true";
                    calcFlag = true;
                    fillParams.put("pReportName", report);
                    fillParams.put("pFromDate", this.fromDate);
                    fillParams.put("pToDate", this.toDate);
                    fillParams.put("pPrintedBy", this.getUserName());
                }
                if (!resultList.isEmpty()) {
                    new ReportBean().jasperReport("AgentRdComm", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    this.setViewID("/report/ReportPagePopUp.jsp");
                } else {
                    setMessage("No details exists !!");
                    calcFlag = false;
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void postBtnAction() { 
        try {
            String currentDate1;
            String trsNo;
            Date date = new Date();
            currentDate1 = ymd.format(date);
            String result = null;
            result = remoteObject.agentRdCommPost(resultList, "", "", getUserName(), getOrgnBrCode(), creditAmount, currentDate1, ymd.format(fromDate), ymd.format(toDate));
            if (result.substring(0, 4).equalsIgnoreCase("true")) {
                String v[] = result.split(":");
                calcFlag1 = true;
                trsNo = result.substring(4);
                this.setMessage("Transaction Posted Successfully and  " + "Transfer Batch No :- " + v[1]);
                setPostDisable(true);
            } else {
                calcFlag1 = false;
                this.setMessage(result);
            }
            setPostDisable(true);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void resetValue() {
        setMessage("");
        setCreditAmount(0);
        List<agentCommPojo> resultList = new ArrayList<agentCommPojo>();
    }

    /**
     * **************** END *********************
     */
    public String btnExit() {
        resetValue();
        return "case1";
    }
}
