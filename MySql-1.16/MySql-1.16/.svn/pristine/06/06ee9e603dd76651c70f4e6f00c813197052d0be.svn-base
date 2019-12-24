/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.report.MailingDetailsPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.email.EmailMgmtFacadeRemote;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class MailingDetailsReport  extends BaseBean{
    private String firstdate;
    private String secondDate;
    private String displayoncheque;
    private String displayPanel3;
    //main field
    private String errorMsg;
    private String serviceType;
    private String branchCode;
    private String mode;
    private String frdt;
    private String todt;
    private String acNo;
    private String newAcno;
    private String custid;
    private String fromdt;
    private String toInddt;
    Date dt = new Date();
    private List<SelectItem> serviceList;
    private List<SelectItem> modeList;
    private List<SelectItem> branchCodeList;
    List<MailingDetailsPojo> repDataList = new ArrayList<MailingDetailsPojo>(); 
    private CommonReportMethodsRemote reportRemote = null;
    private EmailMgmtFacadeRemote emailMgmRemote = null;
    private MiscMgmtFacadeS1Remote miscRemoteS1;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public MailingDetailsReport() {
        try{
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            emailMgmRemote = (EmailMgmtFacadeRemote) ServiceLocator.getInstance().lookup("EmailMgmtFacade");
            miscRemoteS1 = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            serviceList = new ArrayList<SelectItem>();
            serviceList.add(new SelectItem("0", "--Select--"));
           List list = reportRemote.getRefRecList("371");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                serviceList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            
            List acTLst = new ArrayList();
            acTLst = reportRemote.getBranchCodeList(getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele1 = (Vector) acTLst.get(i);
                    branchCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0","--Select--"));
            modeList.add(new SelectItem("ALL","ALL"));
            modeList.add(new SelectItem("IND","INDIVIDUAL"));
            
            this.displayoncheque="none";
            this.firstdate="";
            this.secondDate="";
            this.displayPanel3="none";
            this.setFrdt(getTodayDate());
            this.setTodt(getTodayDate());
            this.setFromdt(getTodayDate());
            this.setToInddt(getTodayDate());
            if(acNo.equalsIgnoreCase(null)){
                acNo = "";
            }
            if(custid.equalsIgnoreCase(null)){
                custid = "";
            }
        }catch(Exception ex){
           this.setErrorMsg(ex.getMessage());
        }
    }
    
    public void modeAction(){
        try{
            if(this.mode.equalsIgnoreCase("0")||this.mode.equalsIgnoreCase("")||this.mode.equalsIgnoreCase(null)){
                this.setErrorMsg("Please select the mode");
            }
            if(this.mode.equalsIgnoreCase("ALL")){
                this.displayoncheque="none";
                this.firstdate="";
                this.secondDate="";
                this.displayPanel3="none";
            }else if(this.mode.equalsIgnoreCase("IND")){
                this.displayoncheque= "";
                this.firstdate="none";
                this.secondDate="none";
                this.displayPanel3="";
            }
        }catch(Exception ex){
            this.setErrorMsg(ex.getMessage());
        }
    }
    
    
    public void viewReport(){
        setErrorMsg("");
        String bankName = "", bankAddress = "";
        List dataList1;
        Map fillParams = new HashMap();
        try{
          if (this.branchCode.equalsIgnoreCase("A")) {
                dataList1 = reportRemote.getBranchNameandAddress("90");
            } else {
                dataList1 = reportRemote.getBranchNameandAddress(this.branchCode);
            }
          if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
           String report = "MAILING DETAILS REPORT";
                fillParams.put("pReporDate", this.getTodayDate());
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddr", bankAddress);
            if(mode.equalsIgnoreCase("ALL")){
                if(validateDateField(frdt, todt)== true){
                repDataList = emailMgmRemote.getIndividualMailingDetail(this.branchCode,this.custid,this.serviceType,
                      this.ymd.format(dmy.parse(frdt)),ymd.format(dmy.parse(todt)),this.mode); 
             if (repDataList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            new ReportBean().jasperReport("mailingDetailsReport", "text/html",
               new JRBeanCollectionDataSource(repDataList), fillParams, "MAILING DETAILS REPORT");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report); 
          }
            }else if(this.mode.equalsIgnoreCase("IND")){
                 if(!(this.acNo==null||this.acNo.equalsIgnoreCase(""))){
                  if (validateAccountField(this.acNo) ==true){
                List custIdList = reportRemote.getCustIdfromacNo(this.newAcno);
               String newcustId = custIdList.get(0).toString();
              repDataList = emailMgmRemote.getIndividualMailingDetail(this.branchCode,newcustId,this.serviceType,
                      this.ymd.format(dmy.parse(fromdt)),ymd.format(dmy.parse(toInddt)),this.mode);
             if (repDataList.isEmpty()) {
              throw new ApplicationException("Data does not exist");
            }
            new ReportBean().jasperReport("mailingDetailsReport", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "MAILING DETAILS REPORT");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report); 
         }
      }
         if(!(this.custid.equalsIgnoreCase(null) || this.custid.equalsIgnoreCase(""))){
             if(validatecustId()==true){
       repDataList = emailMgmRemote.getIndividualMailingDetail(this.branchCode,this.custid,this.serviceType,
                      this.ymd.format(dmy.parse(fromdt)),ymd.format(dmy.parse(toInddt)),this.mode);
             if (repDataList.isEmpty()) {
              throw new ApplicationException("Data does not exist");
            }
            new ReportBean().jasperReport("mailingDetailsReport", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "MAILING DETAILS REPORT");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report); 
          }
         }
       } 
     }catch(Exception ex){
            this.setErrorMsg(ex.getMessage());
        }
    }
    
    public void viewPdfReport(){
        setErrorMsg("");
        String bankName = "", bankAddress = "";
        List dataList1;
        Map fillParams = new HashMap();
        try{
          if (this.branchCode.equalsIgnoreCase("A")) {
                dataList1 = reportRemote.getBranchNameandAddress("90");
            } else {
                dataList1 = reportRemote.getBranchNameandAddress(this.branchCode);
            }
          if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
           String report = "MAILING DETAILS REPORT";
                fillParams.put("pReporDate", this.getTodayDate());
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddr", bankAddress);
            if(mode.equalsIgnoreCase("ALL")){
                if(validateDateField(frdt, todt)== true){
                repDataList = emailMgmRemote.getIndividualMailingDetail(this.branchCode,this.custid,this.serviceType,
                      this.ymd.format(dmy.parse(frdt)),ymd.format(dmy.parse(todt)),this.mode); 
             if (repDataList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
             new ReportBean().openPdf("MAILING DETAILS REPORT_"+ ymd.format(dmy.parse(getTodayDate())) , "mailingDetailsReport", new JRBeanCollectionDataSource(repDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);;  
          }
            }else if(this.mode.equalsIgnoreCase("IND")){
                 if(!(this.acNo==null||this.acNo.equalsIgnoreCase(""))){
                  if (validateAccountField(this.acNo) ==true){
                List custIdList = reportRemote.getCustIdfromacNo(this.newAcno);
               String newcustId = custIdList.get(0).toString();
              repDataList = emailMgmRemote.getIndividualMailingDetail(this.branchCode,newcustId,this.serviceType,
                      this.ymd.format(dmy.parse(fromdt)),ymd.format(dmy.parse(toInddt)),this.mode);
             if (repDataList.isEmpty()) {
              throw new ApplicationException("Data does not exist");
            }
            new ReportBean().openPdf("MAILING DETAILS REPORT_"+ ymd.format(dmy.parse(getTodayDate())) , "mailingDetailsReport", new JRBeanCollectionDataSource(repDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);;  
         }
      }
         if(!(this.custid.equalsIgnoreCase(null) || this.custid.equalsIgnoreCase(""))){
             if(validatecustId()==true){
       repDataList = emailMgmRemote.getIndividualMailingDetail(this.branchCode,this.custid,this.serviceType,
                      this.ymd.format(dmy.parse(fromdt)),ymd.format(dmy.parse(toInddt)),this.mode);
             if (repDataList.isEmpty()) {
              throw new ApplicationException("Data does not exist");
            }
           new ReportBean().openPdf("MAILING DETAILS REPORT_"+ ymd.format(dmy.parse(getTodayDate())) , "mailingDetailsReport", new JRBeanCollectionDataSource(repDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);; 
          }
         }
       } 
     }catch(Exception ex){
            this.setErrorMsg(ex.getMessage());
        }
    }
    
    
    public boolean validateDateField(String frdt,String todt){
       try{
           if (frdt == null || frdt.trim().equals("")) {
                    this.setErrorMsg("Please fill the from date.");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frdt)) {
                    this.setErrorMsg("Please fill proper from date.");
                    return false;
                }
                if (dmy.parse(frdt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setErrorMsg("From date can not be greater than current date.");
                    return false;
                }
                if (todt == null || todt.trim().equals("")) {
                    this.setErrorMsg("Please fill the to date .");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(todt)) {
                    this.setErrorMsg("Please fill proper to date.");
                    return false;
                }
                if (dmy.parse(todt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setErrorMsg("To date can not be greater than current date.");
                    return false;
                }
                if (dmy.parse(frdt).compareTo(dmy.parse(todt)) > 0) {
                    this.setErrorMsg("From date can not be greater than to date.");
                    return false;
                }
        }catch(Exception ex){
           this.setErrorMsg(ex.getMessage());
       }  
       return true;  
     }
    
     
     public boolean validateAccountField(String accNo) {
         try{
             if (acNo == null || acNo.equalsIgnoreCase("")) {
                this.setErrorMsg("Account number should not be blank.");
                return false;
            }
            if (!(acNo.length() == 12 )) {
                this.setErrorMsg("Please fill proper Account Number.");
                return false;
            }
            this.newAcno = ftsRemote.getNewAccountNumber(acNo);
            if(this.newAcno==null){
                this.setErrorMsg("This account no. does not exist.");
                return false;
            }
             String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO") && !getOrgnBrCode().equalsIgnoreCase(reportRemote.getBrncodeByAcno(newAcno))) {
                this.setErrorMsg("Please fill your branch account no.");
                return false;
            }
//            if (!miscRemoteS1.isPrimaryTypeAccount(newAcno)) {
//                this.setErrorMsg("Please fill your branch primary account number !");
//                return false ;
//            }
        }catch(Exception ex){
             this.setErrorMsg(ex.getMessage());
             return false;
         }
         return true;
     }
     
     public boolean validatecustId(){
         try{
              if (custid == null || custid.equalsIgnoreCase("")) {
                this.setErrorMsg("Customer Id should not be blank.");
                return false;
            }
              List custId = reportRemote.checkCustId(custid);
              if(custId.isEmpty()){
                  this.setErrorMsg("This customer id does not exist.");
                  return false;
              }
         }catch(Exception ex){
             this.setErrorMsg(ex.getMessage());
         }
         return true;
     }
    
    public void btnRefreshAction(){
        this.setAcNo("");
        this.setBranchCode("");
        this.setCustid("");
        this.setErrorMsg("");
        this.setFirstdate("");
        this.setSecondDate("");
        this.setDisplayoncheque("none");
        this.setDisplayPanel3("none");
        this.setServiceType("0");
        this.setMode("0");
        this.setTodt(getTodayDate());
        this.setFrdt(getTodayDate());
    }
    
    public String exitAction(){
        return "case1";
    }
    
    
    public String getFirstdate() {
        return firstdate;
    }

    public void setFirstdate(String firstdate) {
        this.firstdate = firstdate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }

    public String getDisplayoncheque() {
        return displayoncheque;
    }

    public void setDisplayoncheque(String displayoncheque) {
        this.displayoncheque = displayoncheque;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public List<SelectItem> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<SelectItem> serviceList) {
        this.serviceList = serviceList;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public CommonReportMethodsRemote getReportRemote() {
        return reportRemote;
    }

    public void setReportRemote(CommonReportMethodsRemote reportRemote) {
        this.reportRemote = reportRemote;
    }

    public EmailMgmtFacadeRemote getEmailMgmRemote() {
        return emailMgmRemote;
    }

    public void setEmailMgmRemote(EmailMgmtFacadeRemote emailMgmRemote) {
        this.emailMgmRemote = emailMgmRemote;
    }

    public MiscMgmtFacadeS1Remote getMiscRemoteS1() {
        return miscRemoteS1;
    }

    public void setMiscRemoteS1(MiscMgmtFacadeS1Remote miscRemoteS1) {
        this.miscRemoteS1 = miscRemoteS1;
    }

    public FtsPostingMgmtFacadeRemote getFtsRemote() {
        return ftsRemote;
    }

    public void setFtsRemote(FtsPostingMgmtFacadeRemote ftsRemote) {
        this.ftsRemote = ftsRemote;
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

    public String getDisplayPanel3() {
        return displayPanel3;
    }

    public void setDisplayPanel3(String displayPanel3) {
        this.displayPanel3 = displayPanel3;
    }

    public String getFromdt() {
        return fromdt;
    }

    public void setFromdt(String fromdt) {
        this.fromdt = fromdt;
    }

    public String getToInddt() {
        return toInddt;
    }

    public void setToInddt(String toInddt) {
        this.toInddt = toInddt;
    }
 }
