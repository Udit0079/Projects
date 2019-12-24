/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareMemberDetailPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ShareMemberDetail extends BaseBean {

    private String message;
    private String member;
    private List<SelectItem> memberList;
    private String accountCat;
    private List<SelectItem> accountCatList;
    private String asOnDate;
    private String memberNo;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private ShareReportFacadeRemote shareStatusFacade;
    private CommonReportMethodsRemote common;
    private String custEntityType;
    private Boolean disableMemberNo;
    List<ShareMemberDetailPojo> resultList = new ArrayList<ShareMemberDetailPojo>();

    public ShareMemberDetail() {
        try {
            this.setAsOnDate(getTodayDate());
            shareStatusFacade = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            memberList = new ArrayList<SelectItem>();
            memberList.add(new SelectItem("--Select--"));
            memberList.add(new SelectItem("All"));
            memberList.add(new SelectItem("Member Wise"));
            accountCatList = new ArrayList<SelectItem>();
            accountCatList.add(new SelectItem("--Select--"));
            accountCatList.add(new SelectItem("01", "Individual"));
            accountCatList.add(new SelectItem("02", "Associate"));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
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

    public List<SelectItem> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<SelectItem> memberList) {
        this.memberList = memberList;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public Boolean getDisableMemberNo() {
        return disableMemberNo;
    }

    public void setDisableMemberNo(Boolean disableMemberNo) {
        this.disableMemberNo = disableMemberNo;
    }

    public String getAccountCat() {
        return accountCat;
    }

    public void setAccountCat(String accountCat) {
        this.accountCat = accountCat;
    }

    public List<SelectItem> getAccountCatList() {
        return accountCatList;
    }

    public void setAccountCatList(List<SelectItem> accountCatList) {
        this.accountCatList = accountCatList;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String btnExitAction() {
        return "case1";
    }

    public void btnRefreshAction() {

        this.message = "";
        this.setAsOnDate(getTodayDate());

        this.setMember("0");
        this.setAccountCat("");
        this.setMemberNo("");
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.member == null || this.member.equals("--Select--")) {
                this.setMessage("Please Select Member Type !");
                return;
            }
            if (!this.member.equalsIgnoreCase("Member Wise")) {
                if (this.accountCat == null || this.accountCat.equals("--Select--")) {
                    this.setMessage("Please Select Accounct Cateageory !");
                    return;
                }
            }
            if (this.member.equalsIgnoreCase("Member Wise")) {
                if (memberNo == null || memberNo.equalsIgnoreCase("")) {
                    this.setMessage("Please Fill Member No");
                    return;
                }

            }
            resultList = shareStatusFacade.getMemberDetailsReport(member, memberNo, accountCat, ymd.format(dmy.parse(this.asOnDate)));
            String pReportName = "";
            if (!resultList.isEmpty()) {
                pReportName = "Share Member Detail";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pReportDate", this.asOnDate);
                fillParams.put("pPrintdate", dmy.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pRepName", "Share Member Detail");
                new ReportBean().jasperReport("shareMemberDetail", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, pReportName);
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

            if (this.member == null || this.member.equals("--Select--")) {
                this.setMessage("Please Select Member Type !");
                return;
            }
            if (!this.member.equalsIgnoreCase("Member Wise")) {
                if (this.accountCat == null || this.accountCat.equals("--Select--")) {
                    this.setMessage("Please Select Accounct Cateageory !");
                    return;
                }
            }
            if (this.member.equalsIgnoreCase("Member Wise")) {
                if (memberNo == null || memberNo.equalsIgnoreCase("")) {
                    this.setMessage("Please Fill Member No");
                }

            }

            resultList = shareStatusFacade.getMemberDetailsReport(member, memberNo, accountCat, ymd.format(dmy.parse(this.asOnDate)));
            String pReportName = "";
            if (!resultList.isEmpty()) {
                pReportName = "Share Member Detail";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pReportDate", this.asOnDate);
                fillParams.put("pPrintdate", dmy.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pRepName", "Share Member Detail");
                new ReportBean().openPdf("Share Member Detail", "shareMemberDetail", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", pReportName);
            } else {
                this.setMessage("There is no data to print !");
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getMemberWiseData() {
        if (this.getMember().equalsIgnoreCase("Member Wise")) {
            this.setDisableMemberNo(false);
        } else {
            this.setDisableMemberNo(true);
        }
    }
}
