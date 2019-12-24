/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareStatusPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class ShareStatus extends BaseBean {

    private int frShareNo;
    private int toShareNo;
    private String status;
    List<SelectItem> statusList;
    private String message;
    private String display = "";
    Date date = new Date();
    private CommonReportMethodsRemote comman;
    private ShareReportFacadeRemote shareStatusFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/mm/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyymmdd");
    List<ShareStatusPojo> shareStatusList = new ArrayList<ShareStatusPojo>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getFrShareNo() {
        return frShareNo;
    }

    public void setFrShareNo(int frShareNo) {
        this.frShareNo = frShareNo;
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

    public int getToShareNo() {
        return toShareNo;
    }

    public void setToShareNo(int toShareNo) {
        this.toShareNo = toShareNo;
    }

    public ShareStatus() {
        try {
            this.setDate(date);
            comman = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            shareStatusFacade = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("Select", "--Select--"));
            statusList.add(new SelectItem("ACTIVE", "ACTIVE"));
            statusList.add(new SelectItem("CLOSE", "CLOSE"));
            statusList.add(new SelectItem("ALL", "ALL"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void printView() {

        String branchName = "";
        String address = "";
        try {
            List brNameAdd = new ArrayList();
            brNameAdd = comman.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            if (frShareNo > toShareNo) {
                setMessage("PLEASE ENTER VALID RANGE");
                return;
            }

            String report = "Share Status Report";
            Map fillmap = new HashMap();
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pReportDt", date);
            fillmap.put("pPrintDt", date);
            fillmap.put("pBrnName", branchName);
            fillmap.put("pBrnAddress", address);
            fillmap.put("pStatus", status);

            shareStatusList = shareStatusFacade.getShareStatus(frShareNo, toShareNo, status);

            new ReportBean().jasperReport("ShareStatusReport", "text/html",
                    new JRBeanCollectionDataSource(shareStatusList), fillmap, "Share Status Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void pdfDownLoad() {

        String branchName = "";
        String address = "";
        try {
            List brNameAdd = new ArrayList();
            brNameAdd = comman.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            if (frShareNo > toShareNo) {
                setMessage("PLEASE ENTER VALID RANGE");
                return;
            }

            String report = "Share Status Report";
            Map fillmap = new HashMap();
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pReportDt", date);
            fillmap.put("pPrintDt", date);
            fillmap.put("pBrnName", branchName);
            fillmap.put("pBrnAddress", address);
            fillmap.put("pStatus", status);

            shareStatusList = shareStatusFacade.getShareStatus(frShareNo, toShareNo, status);

            new ReportBean().openPdf("Share Status Report", "ShareStatusReport", new JRBeanCollectionDataSource(shareStatusList), fillmap);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void refreshForm() {
        this.setFrShareNo(0);
        this.setToShareNo(0);
        this.setMessage("");
        this.setStatus("Select");

    }

    public String closeAction() {
        return "case1";
    }
}
