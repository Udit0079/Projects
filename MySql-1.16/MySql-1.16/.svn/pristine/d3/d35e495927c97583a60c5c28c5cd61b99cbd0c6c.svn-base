/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanBorrowerModPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;                                                                       
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;                                                  
import java.util.ArrayList;                                                                               
import java.util.Date;                                                                                   
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class LoanBorrowerUserModifications extends BaseBean {
    private String userName;
    private String errorMsg; 
    private String acNature; 
    private String acctType;                                                                                   
    private String frmDt;                                                                    
    private String toDt;             
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;                                                
    private List<SelectItem> acctTypeList;
    private List<SelectItem> acNatureList;                                                                                                
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");                                                         
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");                                                                                    
    private Date date = new Date();
    private List tempList = null;
    Vector tempVector = null;
    private String branchCode;
    private String repOption;
    private String acno;        
    private String display;
    private String display1;
    private List<SelectItem> branchCodeList;                                                                
    private List<LoanBorrowerModPojo> baseList;                                                                   
    private List<SelectItem> repOptionList;

    public String getDisplay1() {
        return display1;                         
    }

    public void setDisplay1(String display1) {
        this.display1 = display1;
    }
  
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
   
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAcctType() {
        return acctType;
    }
    
    public void setAcctType(String acctType) {
        this.acctType = acctType;
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

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getRepOption() {
        return repOption;
    }

    public void setRepOption(String repOption) {
        this.repOption = repOption;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }
    
    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }
   
   public LoanBorrowerUserModifications(){
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
           common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            frmDt = dmy.format(date);
            toDt  = dmy.format(date);
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);                                 
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }      
            acNatureList = new ArrayList<SelectItem>();
            acctTypeList = new ArrayList<SelectItem>();
            baseList = new ArrayList<LoanBorrowerModPojo>();
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("--SELECT--","--SELECT--"));
            repOptionList.add(new SelectItem("Individual","Individual"));                                                                              
            repOptionList.add(new SelectItem("All","All"));
            initData();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        } 
   }
   
    private void initData() {
        try {
             tempList = common.getAcctNatureOnlyDB();
            if (!tempList.isEmpty()) {
                acNatureList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
   
    public void blurAcctNature() {
        if (repOption.equalsIgnoreCase("Individual")){
             display = "none";
             display1 = "";
        } else{
             display = "";
             display1 ="none";
             acno ="";
        }
        if (acctTypeList != null) {
            acctTypeList.clear();           
        }
        Vector vtr = null;
        try {
            List result = null;
            result = common.getAcctTypeAsAcNatureOnlyDB(acNature);
            acctTypeList.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                acctTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));               
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
     public boolean validate() {
        try {    
      
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check from date";
                return false;
            }
            if (!Validator.validateDate(toDt)) {
                errorMsg = "Please check to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }
     
     public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
     }
     
    public void printReport() throws ApplicationException { 
       try{
           if(!repOption.equalsIgnoreCase("All")){
           if (this.acno.equalsIgnoreCase("") || this.acno == null || this.acno.equalsIgnoreCase("null") ) {
                this.setErrorMsg("Please Enter Proper  Account No.");
                return;
            }

            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) )) {
                this.setErrorMsg("Please Fill Proper Debit Account Number.");
                return;
            }  
           }
           
       baseList = local.getLoanBorrowerDtl(branchCode ,acNature, acctType ,ymd.format(dmy.parse(frmDt)),ymd.format(dmy.parse(toDt)),acno);
        if(!baseList.isEmpty()){  
          Map fillParams = new HashMap();
           new ReportBean().openPdf("loanAcDetails", "loanAcDetails", new JRBeanCollectionDataSource(baseList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
               HttpSession httpSession = getHttpSession();
               httpSession.setAttribute("ReportName", "loanAcDetails");   
        }else{
            refresh();
            setErrorMsg("Data Not Exist");
        }
       }catch(Exception ex){
          throw new ApplicationException(ex);
     }         
    } 
   
     public void refresh() {
        this.branchCode = "ALL";
        this.acNature = "ALL";
        this.acctType ="ALL";
        this.setErrorMsg("");
      
        try {
            frmDt = dmy.format(dmy.parse(getTodayDate()));
            toDt  = dmy.format(dmy.parse(getTodayDate()));
        } catch (ParseException parseException) {
        
        }
    } 
    
   public String exitAction() {
        refresh();
        return "case1";
    }
}