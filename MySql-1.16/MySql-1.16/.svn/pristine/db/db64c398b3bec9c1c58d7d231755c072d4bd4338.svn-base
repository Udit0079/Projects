/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.DenominationDetailsPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
//import com.google.common.collect.HashBiMap;
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
public class DenominationInfo extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;
    private String userId;
    private List<SelectItem> userIdList;
    private String branch;
    private List<SelectItem> branchList;
    private String toDt;
    private Date date = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DenominationDetailsPojo> reportList = new ArrayList<DenominationDetailsPojo>();

    public DenominationInfo() {
        try {
            setToDt(dmy.format(date));
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            repTypeList = new ArrayList<SelectItem>();

            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("1", "Summary"));
            repTypeList.add(new SelectItem("Y", "Account Wise"));
            repTypeList.add(new SelectItem("N", "Account Type Wise"));

            branchList = new ArrayList<SelectItem>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            // getUserIdUserName();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUserIdUserName() {
        setMessage("");
        try {
            userIdList = new ArrayList<>();
            userIdList.add(new SelectItem("A", "ALL"));
            List list = common.getUsernameandUserId(branch);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector brnVector = (Vector) list.get(i);
                    userIdList.add(new SelectItem(brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        try {
            if (repType == null || repType.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Type !");
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

            if (userId == null) {
                setMessage("Please Select User List !");
                return;
            }
            String report = "Currency Denomination Report", jasperName = "Denomination_Info";

            Map paramMap = new HashMap();
            if (repType.equalsIgnoreCase("Y") || repType.equalsIgnoreCase("N")) {
                reportList = remoteFacade.denominationDetailsAcWiseReport(branch, ymd.format(dmy.parse(toDt)), repType, userId);
                jasperName = "Denomination_Info_AcNoWise";
            } else {
                reportList = remoteFacade.denominationDetailsReport(branch, ymd.format(dmy.parse(toDt)), userId);
            }
            if (dmy.parse(toDt).after(dmy.parse("31/03/2017"))) {
                jasperName = jasperName.concat("_new");
            }
            String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
            paramMap.put("pbankName", s[0]);
            paramMap.put("pbankAdd", s[1]);
            paramMap.put("pReportName", report);
            paramMap.put("pReportDt", toDt);
            paramMap.put("pPrintedBy", getUserName());
            paramMap.put("pAcNoPrint", repType.equalsIgnoreCase("Y") ? repType : (repType.equalsIgnoreCase("N") ? "Y" : repType));
            paramMap.put("pUserName", common.getUserNameByUserId(userId));

            new ReportBean().openPdf("Currency_Denomination_Summary_" + ymd.format(dmy.parse(toDt)), jasperName, new JRBeanCollectionDataSource(reportList), paramMap);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void printAction() {
        setMessage("");
        try {
            if (repType == null || repType.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Type !");
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
            if (userId == null) {
                setMessage("Please Select User List !");
                return;
            }
            String report = "Currency Denomination Report", jasperName = "Denomination_Info_Text";

            Map paramMap = new HashMap();
            if (repType.equalsIgnoreCase("Y") || repType.equalsIgnoreCase("N")) {
                reportList = remoteFacade.denominationDetailsAcWiseReport(branch, ymd.format(dmy.parse(toDt)), repType, userId);
                jasperName = "Denomination_Info_AcNoWise_Text";
            } else {
                reportList = remoteFacade.denominationDetailsReport(branch, ymd.format(dmy.parse(toDt)), userId);
            }
            if (dmy.parse(toDt).after(dmy.parse("31/03/2017"))) {
                jasperName = jasperName.concat("_new");
            }
            String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
            paramMap.put("pbankName", s[0]);
            paramMap.put("pbankAdd", s[1]);
            paramMap.put("pReportName", report);
            paramMap.put("pReportDt", toDt);
            paramMap.put("pPrintedBy", getUserName());
            paramMap.put("pAcNoPrint", repType.equalsIgnoreCase("Y") ? repType : (repType.equalsIgnoreCase("N") ? "Y" : repType));
            paramMap.put("pUserName", common.getUserNameByUserId(userId));

            new ReportBean().jasperReport(jasperName, "text/html",
                    new JRBeanCollectionDataSource(reportList), paramMap, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setRepType("S");
        setUserId("A");
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

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
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

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SelectItem> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<SelectItem> userIdList) {
        this.userIdList = userIdList;
    }
}
