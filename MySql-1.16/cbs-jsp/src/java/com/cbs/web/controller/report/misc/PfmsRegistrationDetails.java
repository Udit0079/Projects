/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.PfmsRegistrationDetailPojo;
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
 * @author Admin
 */
public class PfmsRegistrationDetails extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String frDt;
    private String toDt;
    private Date date = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<PfmsRegistrationDetailPojo> reportList = new ArrayList<PfmsRegistrationDetailPojo>();

    public PfmsRegistrationDetails() {
        try {
            setFrDt(dmy.format(date));
            setToDt(dmy.format(date));
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            branchList = new ArrayList<SelectItem>();

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void printAction() {
        setMessage("");
        try {
            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("From date should be less than current date !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("To date should be less than current date !");
                return;
            }

            if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less than current To date !");
                return;
            }
            String report = "Pfms Registration detail";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", frDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = remoteFacade.getPfmsRegstrationData(branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("PfmsRegistrationDetail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Pfms Registration Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        try {
            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("from date should be less than current date !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("to date should be less than current date !");
                return;
            }

            if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less than current To date !");
                return;
            }
            String report = "Pfms Registration detail";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", frDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = remoteFacade.getPfmsRegstrationData(branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Pfms Registration detail", "PfmsRegistrationDetail", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Pfms Registration Detail");

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setFrDt(dmy.format(date));
        setToDt(dmy.format(date));
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
}
