/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.AcntCloseDetailsTable;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
  public class AccountCloseRegister extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private String acType;
    private List<SelectItem> acTypeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private final String jndiHomeName = "OtherReportFacade";
    private OtherReportFacadeRemote beanRemoteFacade = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
    dmyFormatter = new SimpleDateFormat("dd/MM/yyyy");
    CommonReportMethodsRemote RemoteCode;
    MiscReportFacadeRemote beanRemote;
    

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
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

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
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

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccountCloseRegister() {
        try {
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemoteFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            acTypeList = new ArrayList<SelectItem>();
            List listAccTy = beanRemote.getAllAccounType();
            Vector vtr = new Vector();
            acNatureList = new ArrayList<SelectItem>();
            List accNture = RemoteCode.getAccountNature();
            if(!accNture.isEmpty()) {
                acNatureList.add(new SelectItem("0", "--Select--"));
                for(int j =0;j<accNture.size() ;j++) {
                    Vector vect = (Vector) accNture.get(j);
                    acNatureList.add(new SelectItem(vect.get(0).toString(), vect.get(0).toString()));
                }
            }
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    public void blurAcctNature() {
        if (acTypeList != null) {
            acTypeList.clear();            
        }
        Vector vtr = null;
        try {
            List result = null;
            result = RemoteCode.getAccType(acNature);
            acTypeList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                acTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));                
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void printReport() {
        String report = "Account Close Register";
        try {

            if (calFromDate == null) {
                message = "Please Fill From Date!";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill To Date!";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                message = "Please From Date Should be Less than To Date! ";
                return ;
            }
            
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedDate", dmyFormatter.format(calFromDate) + " to " + dmyFormatter.format(caltoDate));
            fillParams.put("pPrintedBy", getUserName());
             
            List<AcntCloseDetailsTable> accountDetails = beanRemoteFacade.getAcctCloseDetails(acType,acNature, ymdFormatter.format(calFromDate), ymdFormatter.format(caltoDate), branchCode);
            if (accountDetails.isEmpty()) {
                setMessage("Data is not exit!");
                return;
            }

            new ReportBean().jasperReport("AccCloseRegister", "text/html",
                    new JRBeanCollectionDataSource(accountDetails), fillParams, "Account Close Register");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void viewPdfReport(){
     String report = "Account Close Register";
        try {

            if (calFromDate == null) {
                message = "Please Fill From Date!";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill To Date!";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                message = "Please From Date Should be Less than To Date! ";
                return ;
            }
            
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedDate", dmyFormatter.format(calFromDate) + " to " + dmyFormatter.format(caltoDate));
            fillParams.put("pPrintedBy", getUserName());
             
            List<AcntCloseDetailsTable> accountDetails = beanRemoteFacade.getAcctCloseDetails(acType,acNature, ymdFormatter.format(calFromDate), ymdFormatter.format(caltoDate), branchCode);
            if (accountDetails.isEmpty()) {
                setMessage("Data is not exit!");
                return;
            }

            new ReportBean().openPdf("Account Close Register_"+ ymdFormatter.format(dmyFormatter.parse(getTodayDate())), "AccCloseRegister", new JRBeanCollectionDataSource(accountDetails), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }   
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcNature("0");
            setAcType("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
