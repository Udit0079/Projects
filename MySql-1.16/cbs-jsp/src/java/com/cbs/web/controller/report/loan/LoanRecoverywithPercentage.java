/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanRecoveryTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class LoanRecoverywithPercentage extends BaseBean {
    private String msg;
    private String branchCode;
    private String acNature;
    private String selectAcType;
    private String percentage;
    private String frmDt;
    private String toDt;
    private String recoveryMode;
    private Date date = new Date();
    private List<SelectItem> branchCodeList;
    private List<SelectItem> acNatureList;
    private List<SelectItem> selectAcTypeList;
    private List<SelectItem> recoveryModeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;

    
    public LoanRecoverywithPercentage() {
       try{
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            
            List acTLst = new ArrayList();
            acTLst = common.getBranchCodeList(getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele1 = (Vector) acTLst.get(i);
                    branchCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            recoveryModeList = new ArrayList();
            recoveryModeList.add(new SelectItem("0","--Select--"));
            recoveryModeList.add(new SelectItem("R","RECOVERD"));
            recoveryModeList.add(new SelectItem("U","UNRECOVERD"));
            
            List tempList = common.getAcctNatureOnlyDB();
            acNatureList = new ArrayList();
            if (!tempList.isEmpty()) {
                acNatureList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                   Vector tempVector = (Vector) tempList.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
       }catch(Exception ex){
           this.setMsg(ex.getMessage());
       }
    }

    
    public void  getAcTypeForNature(){
         if (selectAcTypeList != null) {
            selectAcTypeList.clear();            
        }
        Vector vtr = null;
        try{
           List result = null;
            result = common.getAcctTypeAsAcNatureOnlyDB(acNature);
            selectAcTypeList = new ArrayList();
            selectAcTypeList.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                selectAcTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));                
            }  
        }catch(Exception ex){
            this.setMsg(ex.getMessage());
        }
    }
     
    public void perfunctionvalidate(){
        Pattern p = Pattern.compile("\\b(?<!\\.)(?!0+(?:\\.0+)?%)(?:\\d|[1-9]\\d|100)(?:(?<!100)\\.\\d+)?");
        try{
           if(this.percentage.equalsIgnoreCase("")||this.percentage.equalsIgnoreCase(null)){
               this.setMsg("Please fill the value of percentage of recovery.");
               return;
           }
            Matcher perCheck = p.matcher(this.percentage);
           if(!perCheck.matches()){
             this.setMsg("Percentage value out of range or too much decimal");
             return;
           }
        }catch(Exception ex){
            this.setMsg(ex.getMessage());
        }
    }
    
    public boolean validation(){
        try{
            if(this.recoveryMode.equalsIgnoreCase("")|| this.recoveryMode.equalsIgnoreCase(null)||this.recoveryMode.equalsIgnoreCase("0")){
                this.setMsg("Please select the reovery mode.");
                return false;
            }
             if (frmDt == null || frmDt.trim().equals("")) {
                    this.setMsg("Please fill the from date.");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frmDt)) {
                    this.setMsg("Please fill proper from date.");
                    return false;
                }
                if (dmy.parse(frmDt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMsg("From date can not be greater than current date.");
                    return false;
                }
                if (toDt == null || toDt.trim().equals("")) {
                    this.setMsg("Please fill the to date .");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                    this.setMsg("Please fill proper to date.");
                    return false;
                }
                if (dmy.parse(toDt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setMsg("To date can not be greater than current date.");
                    return false;
                }
                if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                    this.setMsg("From date can not be greater than to date.");
                    return false;
                }
            
        }catch(Exception ex){
            this.setMsg(ex.getMessage());
        }
        return true;
    }
    
     public String viewReport() throws ApplicationException {
         String report = "Loan Description Report";
         try{
             if(validation()){
              Map fillParams = new HashMap();
              String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
              fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy",getUserName());
                    fillParams.put("pReportDate", frmDt + " to " + toDt);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pFROM", frmDt);
                    fillParams.put("pTo", toDt);
                    
             List<LoanRecoveryTable> resultList = local.getLoanRecoveryWithPercentage(this.selectAcType,this.acNature,ymd.format(dmy.parse(frmDt)), 
                     ymd.format(dmy.parse(toDt)),this.percentage,this.branchCode,this.recoveryMode);
               if (!resultList.isEmpty()){
                    new ReportBean().jasperReport("LoanRecoveryReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    return "report";
               } else{
                   setMsg("No data exists !");
                   return null;
               }
             }  
         }catch(Exception ex){
             this.setMsg(ex.getMessage());
         }
         return null;
     }
     
     
      public String pdfDownLoad(){
          String report = "Loan Description Report";
         try{
             if(validation()){
              Map fillParams = new HashMap();
              String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
              fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy",getUserName());
                    fillParams.put("pReportDate", frmDt + " to " + toDt);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pFROM", frmDt);
                    fillParams.put("pTo", toDt);
                    
             List<LoanRecoveryTable> resultList = local.getLoanRecoveryWithPercentage(this.selectAcType,this.acNature,ymd.format(dmy.parse(frmDt)), 
                     ymd.format(dmy.parse(toDt)),this.percentage,this.branchCode,this.recoveryMode);
               if (!resultList.isEmpty()){
                   new ReportBean().openPdf("Loan Description Report_", "LoanRecoveryReport", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
               } else{
                   setMsg("No data exists !");
                   return null;
               }
             }
             }catch(Exception ex){
             this.setMsg(ex.getMessage());
         }
         return null; 
      }
     
     
    
    public void refreshForm(){
        this.setMsg("");
        this.setAcNature("0");
        this.setPercentage("");
        this.setRecoveryMode("0");
        this.setSelectAcType("0");
        this.frmDt= getTodayDate();
        this.toDt = getTodayDate();
    }
    
    
    
     public String exitAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
    
    
    
    
    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
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

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public List<SelectItem> getRecoveryModeList() {
        return recoveryModeList;
    }

    public void setRecoveryModeList(List<SelectItem> recoveryModeList) {
        this.recoveryModeList = recoveryModeList;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public LoanReportFacadeRemote getLocal() {
        return local;
    }

    public void setLocal(LoanReportFacadeRemote local) {
        this.local = local;
    }

    public CommonReportMethodsRemote getCommon() {
        return common;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public String getRecoveryMode() {
        return recoveryMode;
    }

    public void setRecoveryMode(String recoveryMode) {
        this.recoveryMode = recoveryMode;
    }
 }
