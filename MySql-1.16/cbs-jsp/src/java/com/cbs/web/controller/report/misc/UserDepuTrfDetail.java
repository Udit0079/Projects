/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.ho.UserDepuTrfDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class UserDepuTrfDetail extends BaseBean {

    private String message;
    private String fromBranch;
    private List<SelectItem> fromBranchList;
    private String toBranch;
    private List<SelectItem> toBranchList;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private List<UserDepuTrfDetailPojo> reportList = new ArrayList<UserDepuTrfDetailPojo>();

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(String fromBranch) {
        this.fromBranch = fromBranch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToBranch() {
        return toBranch;
    }

    public void setToBranch(String toBranch) {
        this.toBranch = toBranch;
    }

    public List<SelectItem> getFromBranchList() {
        return fromBranchList;
    }

    public void setFromBranchList(List<SelectItem> fromBranchList) {
        this.fromBranchList = fromBranchList;
    }

    public List<SelectItem> getToBranchList() {
        return toBranchList;
    }

    public void setToBranchList(List<SelectItem> toBranchList) {
        this.toBranchList = toBranchList;
    }

    public UserDepuTrfDetail() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            fromBranchList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeIncludingHo();
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                fromBranchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

            toBranchList = new ArrayList<SelectItem>();
            List list1 = common.getAlphacodeIncludingHo();
            for (int i = 0; i < list1.size(); i++) {
                Vector vtr = (Vector) list1.get(i);
                toBranchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void printAction() {
        try {
            String report = "User Detail Report";
            
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }

            if (caltoDate == null) {
                message = "Please Fill To Date";
                return;
            }

            if (calFromDate.after(caltoDate)) {
                message = "From date should be less than to date !";
                return;
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", dmy.format(calFromDate) + " to " + dmy.format(caltoDate));
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", getUserName());

            reportList = remoteFacade.getUserDetailData(fromBranch, toBranch, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("UserDetailReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "User Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            refreshAction();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void pdfDownLoad(){
        
        try {
            String report = "User Detail Report";
            
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }

            if (caltoDate == null) {
                message = "Please Fill To Date";
                return;
            }

            if (calFromDate.after(caltoDate)) {
                message = "From date should be less than to date !";
                return;
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", dmy.format(calFromDate) + " to " + dmy.format(caltoDate));
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", getUserName());

            reportList = remoteFacade.getUserDetailData(fromBranch, toBranch, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }           
             new ReportBean().openPdf("User Detail Report" , "UserDetailReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "User Detail Report");
            
            refreshAction();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
        
    }

    public void refreshAction() {
        setMessage(""); 
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
