/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.StandingErrorPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
import java.net.InetAddress;
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
 * @author admin
 */
public class StandingInstructionError extends BaseBean implements Serializable {

    private String todayDate;
    private String userName;
    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private Date frmDt;
    private Date toDt;
    private HttpServletRequest req;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private Date date = new Date();
    private String brnCode;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;

    /**
     * Creates a new instance of StandingInstructionError
     */
    public StandingInstructionError() {
        try {
//            userName = getHttpRequest().getRemoteUser();
//            InetAddress localhost = InetAddress.getByName(getHttpRequest().getRemoteAddr());
//            brnCode = Init.IP2BR.getBranch(localhost);

            todayDate = dmy.format(date);
            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            frmDt = date;
            toDt = date;

            List brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(Date frmDt) {
        this.frmDt = frmDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String viewReport() {
        if (validate()) {
            try {
                List<StandingErrorPojo> resultList = local.stanIntReport(ymd.format(frmDt), ymd.format(toDt), branchCode);
                if (!resultList.isEmpty()) {
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParams = new HashMap();
                    fillParams.put("Pby", userName);
                    fillParams.put("Rname", "Stand. Instruction Error Report");
                    fillParams.put("fromdate", dmy.format(frmDt));
                    fillParams.put("todate", dmy.format(toDt));
                    fillParams.put("pbankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());
                    new ReportBean().jasperReport("Error Report", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Stand. Instruction Error Report");
                    return "report";

                } else {
                    errorMsg = "Data does not lie";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public void viewPdfReport() {
        if (validate()) {
            try {
                List<StandingErrorPojo> resultList = local.stanIntReport(ymd.format(frmDt), ymd.format(toDt), branchCode);
                if (!resultList.isEmpty()) {
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParams = new HashMap();
                    fillParams.put("Pby", userName);
                    fillParams.put("Rname", "Stand. Instruction Error Report");
                    fillParams.put("fromdate", dmy.format(frmDt));
                    fillParams.put("todate", dmy.format(toDt));
                    fillParams.put("pbankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());
                    new ReportBean().openPdf("Stand. Instruction Error Report_" + ymd.format(frmDt) + "" + ymd.format(toDt), "Error Report", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Stand. Instruction Error Report");
                } else {
                    errorMsg = "Data does not lie";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate() {
        if (frmDt == null) {
            errorMsg = "Please enter from date";
            return false;
        }
        if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        }
        if (frmDt.after(toDt)) {
            errorMsg = "Please from date should be less then to date";
            return false;
        }
        return true;
    }

    public String exitAction() {
        return "case1";
    }
}
