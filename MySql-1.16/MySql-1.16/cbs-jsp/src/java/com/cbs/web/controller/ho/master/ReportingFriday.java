/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.facade.ho.master.ReportingFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.ho.GridReportingFriday;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportingFriday extends BaseBean {
    
    private String message;
    private String fromDt;
    private String toDt;
    private boolean wait;
    private String balance;
    private String hiddenInput;
    private String label1;
    private String label2;
    private String hiddenToDate;
    private int k = 0;
    private boolean disableCal1;
    private boolean disableCal2;
    private String hiddenLabelToDate;
    private ArrayList<GridReportingFriday> gridData;
    private ReportingFacadeRemote reportingFacadeService = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    
    public String getFromDt() {
        return fromDt;
    }
    
    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }
    
    public String getToDt() {
        return toDt;
    }
    
    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
    
    public String getHiddenLabelToDate() {
        return hiddenLabelToDate;
    }
    
    public void setHiddenLabelToDate(String hiddenLabelToDate) {
        this.hiddenLabelToDate = hiddenLabelToDate;
    }
    
    public boolean isDisableCal1() {
        return disableCal1;
    }
    
    public void setDisableCal1(boolean disableCal1) {
        this.disableCal1 = disableCal1;
    }
    
    public boolean isDisableCal2() {
        return disableCal2;
    }
    
    public void setDisableCal2(boolean disableCal2) {
        this.disableCal2 = disableCal2;
    }
    
    public String getHiddenToDate() {
        return hiddenToDate;
    }
    
    public void setHiddenToDate(String hiddenToDate) {
        this.hiddenToDate = hiddenToDate;
    }
    
    public String getLabel1() {
        return label1;
    }
    
    public void setLabel1(String label1) {
        this.label1 = label1;
    }
    
    public String getLabel2() {
        return label2;
    }
    
    public void setLabel2(String label2) {
        this.label2 = label2;
    }
    
    public String getHiddenInput() {
        return hiddenInput;
    }
    
    public void setHiddenInput(String hiddenInput) {
        this.hiddenInput = hiddenInput;
    }
    
    public String getBalance() {
        return balance;
    }
    
    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    public boolean isWait() {
        return wait;
    }
    
    public void setWait(boolean wait) {
        this.wait = wait;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public ArrayList<GridReportingFriday> getGridData() {
        return gridData;
    }
    
    public void setGridData(ArrayList<GridReportingFriday> gridData) {
        this.gridData = gridData;
    }
    
    public ReportingFriday() {
        try {
            reportingFacadeService = (ReportingFacadeRemote) ServiceLocator.getInstance().lookup("ReportingFacade");
            formLoad();
            loadGrid();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void formLoad() {
        try {
            List l1 = reportingFacadeService.formLoad();
            if (l1.isEmpty() || l1 == null) {
                String date = dmy.format(dt);
                this.setLabel1("From Date");
                this.setLabel2("To Date");
                this.setFromDt(date);
                this.setToDt(date);
                this.hiddenInput = "none";
                this.hiddenToDate = "";
                k = 0;
            } else {
                k = 1;
                this.setLabel1("Date");
                this.hiddenInput = "";
                this.hiddenToDate = "none";
                this.disableCal1 = true;
                this.disableCal2 = true;
                Vector repdate = (Vector) l1.get(0);
                this.setFromDt(repdate.get(0).toString());
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void loadGrid() {
        try {
            gridData = new ArrayList<GridReportingFriday>();
            List result = reportingFacadeService.gridLoad();
            for (int i = 0; i < result.size(); i++) {
                GridReportingFriday rd = new GridReportingFriday();
                Vector element = (Vector) result.get(i);
                rd.setSerial(i);
                rd.setReportingFriday(element.get(1).toString().substring(0, 10));
                rd.setNetLiabilities(Double.parseDouble(element.get(2).toString()));
                rd.setEnterBy(element.get(4).toString());
                gridData.add(rd);
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void update() {
        String resultMsg = "";
        try {
            wait = true;
            if (k == 1) {
                if (getBalance().equals("")) {
                    this.setMessage("Please enter balance !");
                    return;
                } else {
                    Pattern pm = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                    Matcher lockerNoCheck = pm.matcher(this.getBalance());
                    if (!lockerNoCheck.matches()) {
                        this.setMessage("Please enter the balance in numbers only !");
                        this.setBalance("");
                        return;
                    }
                    List list = reportingFacadeService.minRepDate();
                    if (!list.isEmpty()) {
                        Vector vector = (Vector) list.get(0);
                        String d1 = vector.get(0).toString();
                        String enterBy = vector.get(2).toString();
                        String msg = reportingFacadeService.updateSingleRecord(d1, this.getBalance(), enterBy);
                        
                        this.setMessage(msg);
                        loadGrid();
                        this.setFromDt("");
                        this.setToDt("");
                        this.setBalance("");
                    }
                }
            } else {
                List resultSet = new ArrayList();
                if (this.fromDt.isEmpty()) {
                    this.setMessage("From date not selected !");
                    return;
                } else if (this.toDt.isEmpty()) {
                    this.setMessage("To date not selected !");
                    return;
                } else {
                    String fromdate = ymd.format(dmy.parse(fromDt));
                    String todate = ymd.format(dmy.parse(toDt));
                    
                    resultSet = reportingFacadeService.getReportDate(fromdate, todate);
                    if (resultSet.isEmpty() || resultSet == null) {
                        this.setMessage("No Reporting Friday between from date and to date !");
                        return;
                    }
                    String msg = insertHistory(fromdate, todate);
                    if (msg.equals("false")) {
                        this.setMessage("Transaction not processed, could not able to maintained history !");
                        return;
                    }
                    for (int i = 0; i < resultSet.size(); i++) {
                        Vector element = (Vector) resultSet.get(i);
                        String repfridate = element.get(0).toString();
                        resultMsg = reportingFacadeService.aftergetReportDate(repfridate, getUserName());
                    }
                    this.setMessage(resultMsg);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public String insertHistory(String fdate, String Tdate) {
        try {
            return reportingFacadeService.insertHistory(fdate, Tdate, getUserName());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public void refresh() {
        try {
            this.setMessage("");
            this.setFromDt(dmy.format(dt));
            if (k == 0) {
                this.setToDt(dmy.format(dt));
            }
            loadGrid();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
