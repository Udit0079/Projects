/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.MemberLabelPojo;
import com.cbs.exception.ApplicationException;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class memberLabel extends BaseBean {

    private String username;
    private String message;
    private String status;
    private String member;
    private String fromDate;
    private String toDate;
    private List<SelectItem> statusTypeList;
    private List<SelectItem> memberTypeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private ShareReportFacadeRemote horfr = null;
    private List<MemberLabelPojo> resultdata;
    Date date = new Date();
    private String nomStatus;
    private List<SelectItem> nomStatusTypeList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusTypeList() {
        return statusTypeList;
    }

    public void setStatusTypeList(List<SelectItem> statusTypeList) {
        this.statusTypeList = statusTypeList;
    }

    public List<SelectItem> getMemberTypeList() {
        return memberTypeList;
    }

    public void setMemberTypeList(List<SelectItem> memberTypeList) {
        this.memberTypeList = memberTypeList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getNomStatus() {
        return nomStatus;
    }

    public void setNomStatus(String nomStatus) {
        this.nomStatus = nomStatus;
    }

    public List<SelectItem> getNomStatusTypeList() {
        return nomStatusTypeList;
    }

    public void setNomStatusTypeList(List<SelectItem> nomStatusTypeList) {
        this.nomStatusTypeList = nomStatusTypeList;
    }

    public memberLabel() {
        try {

            setFromDate(dmy.format(date));
            setToDate(dmy.format(date));

            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");

            statusTypeList = new ArrayList<SelectItem>();
            statusTypeList.add(new SelectItem("ALL", "ALL"));
            statusTypeList.add(new SelectItem("A", "Active"));
            statusTypeList.add(new SelectItem("C", "Close"));

            memberTypeList = new ArrayList<SelectItem>();
            memberTypeList.add(new SelectItem("ALL", "ALL"));
            memberTypeList.add(new SelectItem("01", "Individual"));
            memberTypeList.add(new SelectItem("02", "Associtaive"));

            nomStatusTypeList = new ArrayList<SelectItem>();
            nomStatusTypeList.add(new SelectItem("ALL", "ALL"));
            nomStatusTypeList.add(new SelectItem("W", "With Nominee"));
            nomStatusTypeList.add(new SelectItem("WN", "Without Nominee"));


        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() throws ApplicationException {
        this.setMessage("");
        try {
            if (this.status == null || this.status.equalsIgnoreCase("0")) {
                this.setMessage("Please select Status Type !");
                return;
            }
            if (this.member == null || this.member.equals("0")) {
                this.setMessage("Please select Member Type !");
                return;
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }
            resultdata = horfr.getMemberLabelReportData(ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), status, member, nomStatus);
            if (!resultdata.isEmpty()) {
                Map fillParams = new HashMap();
                new ReportBean().jasperReport("MemberLabel", "text/html", new JRBeanCollectionDataSource(resultdata), fillParams, "Member Label");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", "Member Label");
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setMessage("");
        try {
            if (this.status == null || this.status.equalsIgnoreCase("0")) {
                this.setMessage("Please select Status Type !");
                return;
            }
            if (this.member == null || this.member.equals("0")) {
                this.setMessage("Please select Member Type !");
                return;
            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }
            resultdata = horfr.getMemberLabelReportData(ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), status, member, nomStatus);
            if (!resultdata.isEmpty()) {
                Map fillParams = new HashMap();
                new ReportBean().openPdf("Member Label", "MemberLabel", new JRBeanCollectionDataSource(resultdata), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", "Member Label");
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.message = "";
        this.setStatus("ALL");
        this.setMember("ALL");
        this.setFromDate(getTodayDate());
        this.setToDate(getTodayDate());
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
