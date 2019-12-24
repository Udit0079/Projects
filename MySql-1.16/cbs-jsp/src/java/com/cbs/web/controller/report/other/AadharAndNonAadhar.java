/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.pojo.AadharNonAadharPoJo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
 * @author SAMY
 */
public class AadharAndNonAadhar extends BaseBean {
    private String message;
    private String branch;    
    private List<SelectItem> branchList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    Date dt = new Date();
    private GLReportFacadeRemote glBeanRemote;
    private CommonReportMethodsRemote common = null;
    AcctEnqueryFacadeRemote remoteObject;
    private final String jndiName = "CommonReportMethods";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    
    public AadharAndNonAadhar() {
        reportTypeList = new ArrayList<SelectItem>();
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            List brnLst = new ArrayList();
            brnLst = glBeanRemote.getBranchCodeListExt(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("A", "AADHAR"));
            reportTypeList.add(new SelectItem("N", "NON-AADHAR"));
        } catch (Exception ex) {
           this.setMessage(ex.getMessage());
        }
    }
    public void downloadPdf() {
        this.setMessage("");
        List<AadharNonAadharPoJo> resultList = new ArrayList<AadharNonAadharPoJo>();
        try {
            String reportName = "", reptName = "",reportDesc = "";
            Map fillParams = new HashMap();
            reportName = "AADHAR AND NON-AADHAR REPORT";
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("username", getUserName());
            fillParams.put("pReport", reportName);
            if(this.reportType.equalsIgnoreCase("A")) {
                resultList = remoteObject.getAadharNonAadharDetail(branch, reportType);
            } else if(this.reportType.equalsIgnoreCase("N")) {
                resultList = remoteObject.getAadharNonAadharDetail(branch, reportType);
            }            
            if (!resultList.isEmpty()) {
                new ReportBean().openPdf("XBRL_FORM9_", "Aadhar_NonAadhar",
                        new JRBeanCollectionDataSource(resultList), fillParams);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", reportName);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }        

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }
}
