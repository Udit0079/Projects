/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.pojo.AtmCardIssueDetailPojo;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class AtmCardIssueDetail extends BaseBean {

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
    List<AtmCardIssueDetailPojo> reportList = new ArrayList<AtmCardIssueDetailPojo>();

    public AtmCardIssueDetail() {
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
            statusList.add(new SelectItem("A", "ADD"));
           // statusList.add(new SelectItem("V", "VERIFY"));
            statusList.add(new SelectItem("M", "MODIFY"));
            statusList.add(new SelectItem("R", "RE-PIN"));
            statusList.add(new SelectItem("E", "EXPIRY"));
            statusList.add(new SelectItem("L", "LOST"));
            statusList.add(new SelectItem("S", "STOLEN"));
            statusList.add(new SelectItem("C", "A/C CLOSE"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "", address = "";
        String report = "Atm Card Registration Detail Report";

        try {
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            if (status == null || status.equalsIgnoreCase("0")) {
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
            fillParams.put("pReprtName", report);
            fillParams.put("pReportDate", frmDt + " To " + toDt);
            fillParams.put("pPrintedBy", getUserName());
           
            
            String fDt = ymd.format(dmy.parse(frmDt));
            String tDt = ymd.format(dmy.parse(toDt));

            reportList = otherReportFacade.getAtmCardIssueDetail(branch, status, fDt, tDt);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }
            new ReportBean().jasperReport("Atm_Card_Issue_Detail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Atm Card Registration Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   
}
