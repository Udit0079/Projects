/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.report.AgentCommissionPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class AgentCommisonPosting extends BaseBean {
    private String errorMsg;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private double totalNetAmt=0.0;
    private double totalAgBal=0.0;
    private double totalCommision=0.0;
    private double totalTaxAmt=0.0;
    private boolean reportFlag;
    private String viewID = "/report/ReportPagePopUp.jsp";
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<AgentCommissionPojo> tableList = new ArrayList<AgentCommissionPojo>();
    private DDSReportFacadeRemote DDSReportRemote = null;
    private final String jndiHomeName = "DDSReportFacade";

    public AgentCommisonPosting() {
        try{
            DDSReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            
            
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            this.setReportFlag(false);
        }catch(Exception ex){
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void onblurOption() {
        tableList = new ArrayList<>();
        this.setErrorMsg("");
        try {
            if (frmDt == null || frmDt.trim().equals("")) {
                this.setErrorMsg("Please fill the from date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmDt)) {
                this.setErrorMsg("Please fill proper from date.");
                return;
            }
            if (dmy.parse(frmDt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                this.setErrorMsg("From date can not be greater than current date.");
                return;
            }
            if (toDt == null || toDt.trim().equals("")) {
                this.setErrorMsg("Please fill the to date .");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                this.setErrorMsg("Please fill proper to date.");
                return;
            }
            if (dmy.parse(toDt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                this.setErrorMsg("To date can not be greater than current date.");
                return;
            }
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                this.setErrorMsg("From date can not be greater than to date.");
                return;
            }

            Date date1 = dmy.parse(frmDt);
            Date date2 = dmy.parse(toDt);
            long diff = date2.getTime() - date1.getTime();
            diff = diff / ((1000 * 60 * 60 * 24));
            if (!(frmDt.substring(3, 5).equalsIgnoreCase("02"))) {
                if (!((diff == 29) || diff == 30)) {
                    this.setErrorMsg("Date Range should be one month.");
                    return;
                }
            } else {
                if (!(diff == 27 || diff == 28)) {
                    this.setErrorMsg("Date Range should be one month.");
                    return;
                }
            }
            Date lastDateOfMonth = CbsUtil.getLastDateOfMonth(dmy.parse(frmDt));
            if (!this.toDt.equals(dmy.format(lastDateOfMonth))) {
                this.setErrorMsg("This date is not last date of month . please enter the last date of month.");
                return;
            }
            tableList = DDSReportRemote.getAgentCommission(ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), branchCode);
            if (tableList.isEmpty()) {
                this.setErrorMsg("date does not exits");
            }
            for(AgentCommissionPojo obj : tableList){
                totalNetAmt = totalNetAmt + obj.getNetAmt();
                totalTaxAmt = totalTaxAmt + obj.getTaxAmt();
                totalCommision = totalCommision + obj.getComAmt();
                totalAgBal = totalAgBal + obj.getAgBal();
            }
           printReport(tableList, "AgentCommissionReport");
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }
    
    
    public void printReport(List<AgentCommissionPojo> resultList ,String jrxmlName ) {
            String branchName = "";
            String address = "";
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                String report = "Agent Commission Report";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                
                String duration = CbsUtil.getMonthName(Integer.parseInt(frmDt.substring(3, 5)));
                String Yr = frmDt.substring(6);
                
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportDate", "From " + frmDt + " To " + toDt);
                fillParams.put("pDuration", duration.toUpperCase() + " OF " + Yr);
                fillParams.put("pPrintDate", dmy.format(date));
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);
                
               
                new ReportBean().jasperReport("AgentCommissionReport", "text/html",
                        new JRBeanCollectionDataSource(resultList), fillParams, "Agent Commission Report");
                
               this.setReportFlag(true);
               this.setViewID("/report/ReportPagePopUp.jsp");
               
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMsg(e.getLocalizedMessage());
            }
        }
    
    
    public void postBtnAction() {
        this.setErrorMsg("");
        try {
            if (frmDt == null || frmDt.trim().equals("")) {
                this.setErrorMsg("Please fill the from date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmDt)) {
                this.setErrorMsg("Please fill proper from date.");
                return;
            }
            if (dmy.parse(frmDt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                this.setErrorMsg("From date can not be greater than current date.");
                return;
            }
            if (toDt == null || toDt.trim().equals("")) {
                this.setErrorMsg("Please fill the to date .");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                this.setErrorMsg("Please fill proper to date.");
                return;
            }
            if (dmy.parse(toDt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                this.setErrorMsg("To date can not be greater than current date.");
                return;
            }
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                this.setErrorMsg("From date can not be greater than to date.");
                return;
            }

            Date date1 = dmy.parse(frmDt);
            Date date2 = dmy.parse(toDt);
            long diff = date2.getTime() - date1.getTime();
            diff = diff / ((1000 * 60 * 60 * 24));
            if (!(frmDt.substring(3, 5).equalsIgnoreCase("02"))) {
                if (!((diff == 29) || diff == 30)) {
                    this.setErrorMsg("Date Range should be one month.");
                    return;
                }
            } else {
                if (!(diff == 27 || diff == 28)) {
                    this.setErrorMsg("Date Range should be one month.");
                    return;
                }
            }
            Date lastDateOfMonth = CbsUtil.getLastDateOfMonth(dmy.parse(frmDt));
            if (!this.toDt.equals(dmy.format(lastDateOfMonth))) {
                this.setErrorMsg("This date is not last date of month . please enter the last date of month.");
                return;
            }
            if (this.branchCode.equalsIgnoreCase("0A")) {
                String brncode = "";
                String checkPostCommisionIsExits = DDSReportRemote.getAllBranchCodefromddsAgentListforCheckPostHistory(this.frmDt,this.toDt);
                if(!checkPostCommisionIsExits.equalsIgnoreCase("true")){
                   this.setErrorMsg(checkPostCommisionIsExits);
                   return;
                }
                
                Map<String, String> mapBranch = new HashMap<String, String>();
                for (AgentCommissionPojo obj : tableList) {
                    mapBranch.put(obj.getBranch(), obj.getBranch());
                }

                Set<Map.Entry<String, String>> setBranch = mapBranch.entrySet();
                Iterator<Map.Entry<String, String>> itBranch = setBranch.iterator();
                List<AgentCommissionPojo> BrnWiseAgentDataList = new ArrayList<AgentCommissionPojo>();
                while (itBranch.hasNext()) {
                    BrnWiseAgentDataList = new ArrayList<AgentCommissionPojo>();
                    Map.Entry<String, String> entryBranch = itBranch.next();
                    brncode = entryBranch.getValue();

                    for (int m = 0; m < tableList.size(); m++) {
                        if (tableList.get(m).getBranch().equalsIgnoreCase(brncode)) {
                            BrnWiseAgentDataList.add(tableList.get(m));
                        }
                    }
                    if (!BrnWiseAgentDataList.isEmpty()) {
                        String result = DDSReportRemote.postAgentCommisionTransaction(BrnWiseAgentDataList, getTodayDate(), getUserName(), getOrgnBrCode(), frmDt, toDt, this.branchCode);
                        if (!result.equalsIgnoreCase("true")) {
                            this.setErrorMsg(result);
                            return;
                        }
                    }
                }
            } else {
                String result = DDSReportRemote.postAgentCommisionTransaction(tableList, getTodayDate(), getUserName(), getOrgnBrCode(), frmDt, toDt, this.branchCode);
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMsg(result);
                    return;
                }
            }
            this.setErrorMsg("Commision has been posted Successfully.");
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }

    }
    
    
    public void refresh(){
        this.setReportFlag(false);
        this.setErrorMsg("");
        this.setBranchCode("");
        this.setFrmDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
        this.setTableList(null);
        tableList = new ArrayList<>();
        setTotalAgBal(0);
        setTotalCommision(0);
        setTotalNetAmt(0);
        setTotalTaxAmt(0);
    }
    
    
    public String exitAction(){
        refresh();
        return "case1";
    }
    
    
    
    
    
    
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public List<AgentCommissionPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<AgentCommissionPojo> tableList) {
        this.tableList = tableList;
    }

    public double getTotalNetAmt() {
        return totalNetAmt;
    }

    public void setTotalNetAmt(double totalNetAmt) {
        this.totalNetAmt = totalNetAmt;
    }

    public double getTotalAgBal() {
        return totalAgBal;
    }

    public void setTotalAgBal(double totalAgBal) {
        this.totalAgBal = totalAgBal;
    }

    public double getTotalCommision() {
        return totalCommision;
    }

    public void setTotalCommision(double totalCommision) {
        this.totalCommision = totalCommision;
    }

    public double getTotalTaxAmt() {
        return totalTaxAmt;
    }

    public void setTotalTaxAmt(double totalTaxAmt) {
        this.totalTaxAmt = totalTaxAmt;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }
    
    
    
    
}
