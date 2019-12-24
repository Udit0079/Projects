/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TdNewAndReNewPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
 * @author Athar Reza
 */
public class TdNewAndRenew extends BaseBean {

    private String message;
    private String status;
    private List<SelectItem> statusList;
    private String frmDt;
    private String toDt;
    private String branch;
    private List<SelectItem> branchList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherReportFacade;
    List<TdNewAndReNewPojo> reportList = new ArrayList<TdNewAndReNewPojo>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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
    
    public TdNewAndRenew() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherReportFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("New", "New"));
            statusList.add(new SelectItem("ReNew", "ReNew"));
            statusList.add(new SelectItem("Mature","Mature"));
            statusList.add(new SelectItem("Premature","Premature"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "";
        String address = "";
        String report = "New Term Deposit Report";
        String report1 = "Renewed Term Deposit Report";
        String report2 = "Mature Term Deposit Report";
        String report3 = "Premature Term Deposit Report";
        try {
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            
            if(status == null || status.equalsIgnoreCase("0")){
                setMessage("Please select Status!!!");
                return;
            }

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return;
            }

            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            if (status.equalsIgnoreCase("New")) {
                fillParams.put("pReprtName", report);
            } else if(status.equalsIgnoreCase("Renew")) {
                fillParams.put("pReprtName", report1);
            } else if(status.equalsIgnoreCase("Mature")){
                fillParams.put("pReprtName",report2);
            } else if (status.equalsIgnoreCase("Premature")){
                fillParams.put("pReprtName",report3);
            }
            fillParams.put("pReportDate", frmDt + " To " + toDt);
            fillParams.put("pPrintedBy", getUserName());

            String fDt = ymd.format(dmy.parse(frmDt));
            String tDt = ymd.format(dmy.parse(toDt));

            reportList = otherReportFacade.getTdNewAndRenewData(this.getBranch(), status, fDt, tDt);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }
            if (status.equalsIgnoreCase("New")) {
                new ReportBean().jasperReport("Td_New", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Td New and Renew Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else if(status.equalsIgnoreCase("Renew")) {
                new ReportBean().jasperReport("Td_Re_New", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Td New and Renew Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }else if(status.equalsIgnoreCase("Mature")){
                  new ReportBean().jasperReport("Td_New", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Td New and Renew Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }else if (status.equalsIgnoreCase("Premature")){
                  new ReportBean().jasperReport("Td_New", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Td New and Renew Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
        this.setStatus("0");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
