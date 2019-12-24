/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.ZeroBalReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ankit Verma
 */
public class ZeroBalReport extends BaseBean {

    private String msg;
    private String date;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String selectAcType;
    private List<SelectItem> selectAcTypeList;
    private MiscReportFacadeRemote beanLocal = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private TdReceiptManagementFacadeRemote beanFacade;
    private CommonReportMethodsRemote common;
    private String selectTxnType;
    private List<SelectItem> selectTxnTypeList;

    public ZeroBalReport() {
        try {

            setDate(sdf.format(new Date()));
            beanLocal = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getacNature();
            selectTxnTypeList = new ArrayList<SelectItem>();
            selectTxnTypeList.add(new SelectItem("0", "With Txn"));
            selectTxnTypeList.add(new SelectItem("1", "Without Txn"));
            selectTxnTypeList.add(new SelectItem("2", "Deaf Txn"));

        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getacNature() {
        try {
            acnoNatureList = new ArrayList<SelectItem>();
            acnoNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = common.getAllNatureList();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acnoNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            selectAcTypeList = new ArrayList<SelectItem>();
            selectAcTypeList.add(new SelectItem("ALL", "ALL"));
            List acTypeList = common.getAccType(acnoNature);
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    selectAcTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public String getAcnoNature() {
        return acnoNature;
    }

    public void setAcnoNature(String acnoNature) {
        this.acnoNature = acnoNature;
    }

    public List<SelectItem> getAcnoNatureList() {
        return acnoNatureList;
    }

    public void setAcnoNatureList(List<SelectItem> acnoNatureList) {
        this.acnoNatureList = acnoNatureList;
    }

    public String getSelectTxnType() {
        return selectTxnType;
    }

    public void setSelectTxnType(String selectTxnType) {
        this.selectTxnType = selectTxnType;
    }

    public List<SelectItem> getSelectTxnTypeList() {
        return selectTxnTypeList;
    }

    public void setSelectTxnTypeList(List<SelectItem> selectTxnTypeList) {
        this.selectTxnTypeList = selectTxnTypeList;
    }

    public void viewReport() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + dt1[1] + dt1[0];
                List<ZeroBalReportPojo> reportList = beanLocal.getZeroBalReportDetails(dd3, selectAcType, branchCode, acnoNature, selectTxnType);
                Map fillParams = new HashMap();
                if (selectTxnType.equalsIgnoreCase("0")) {
                    fillParams.put("pReportName", "Zero Balance Report With Transaction");
                } else if (selectTxnType.equalsIgnoreCase("1")) {
                    fillParams.put("pReportName", "Zero Balance Report Without Transaction");
                }
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pPrintedDate", date);
                fillParams.put("pAcType", selectAcType);
                ReportBean rb = new ReportBean();
                if (reportList.size() > 0) {
                    rb.jasperReport("ZeroBalanceReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Zero Balance Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    setMsg("No details exists");
                }

            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }

        }
    }

    public void viewPdfReport() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + dt1[1] + dt1[0];
                List<ZeroBalReportPojo> reportList = beanLocal.getZeroBalReportDetails(dd3, selectAcType, branchCode, acnoNature, selectTxnType);
                Map fillParams = new HashMap();
                String report = "Zero Balance Report";
                if (selectTxnType.equalsIgnoreCase("0")) {
                    fillParams.put("pReportName", "Zero Balance Report With Transaction");
                } else if (selectTxnType.equalsIgnoreCase("1")) {
                    fillParams.put("pReportName", "Zero Balance Report Without Transaction");
                }
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pPrintedDate", date);
                fillParams.put("pAcType", selectAcType);
                ReportBean rb = new ReportBean();
                if (reportList.size() > 0) {
                    rb.openPdf("ZeroBalanceReport_" + ymd.format(sdf.parse(getTodayDate())), "ZeroBalanceReport", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    setMsg("No details exists");
                }

            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }

        }
    }

    public void refreshForm() {
        acnoNature = "0";
        selectAcType = "ALL";
        date = "";
        msg = "";
        setDate(sdf.format(new Date()));
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public String validation() {
        if (selectAcType.equalsIgnoreCase("0")) {
            return "Please Select A/C. Type.";
        } else if (date.equalsIgnoreCase("")) {
            return "Please Enter Date.";
        }
        return "ok";
    }
}
