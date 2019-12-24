/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.pojo.MemberShareLetterPojo;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class MemberShareLetter extends BaseBean {

    private String message;
    private String member;
    private List<SelectItem> memberList;
    private String fromDate;
    private String toDate;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private ShareReportFacadeRemote shareStatusFacade;
    List<MemberShareLetterPojo> resultList = new ArrayList<MemberShareLetterPojo>();
    private String orgnbrcode;
    Date date = new Date();
    //getter Setter method

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

    public List<SelectItem> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<SelectItem> memberList) {
        this.memberList = memberList;
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

    public void btnRefreshAction() {

        this.message = "";
        this.setFromDate(getTodayDate());
        this.setToDate(getTodayDate());
        this.setMember("");
    }

    public String btnExitAction() {
        return "case1";
    }

    public MemberShareLetter() {
        try {

            shareStatusFacade = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            memberList = new ArrayList<SelectItem>();
            memberList.add(new SelectItem("New"));
            memberList.add(new SelectItem("Addition"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {

            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }
            resultList = shareStatusFacade.getAdditionNewShareReport(ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), member, getOrgnBrCode());
            String pReportName = "";
            if (!resultList.isEmpty()) {
                pReportName = "Member Share Letter";
                Map fillParams = new HashMap();
                new ReportBean().jasperReport("MemberLetter", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, pReportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        this.setMessage("");
        try {

            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
                return;
            }
            resultList = shareStatusFacade.getAdditionNewShareReport(ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)), member, getOrgnBrCode());
            String pReportName = "";
            if (!resultList.isEmpty()) {
                pReportName = "Member Share Letter";
                Map fillParams = new HashMap();
                new ReportBean().openPdf("MemberLetter", "MemberLetter", new JRBeanCollectionDataSource(resultList), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }
}
