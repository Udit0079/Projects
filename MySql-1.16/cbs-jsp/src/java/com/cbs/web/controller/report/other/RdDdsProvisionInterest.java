/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.FdRdFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author root
 */
public class RdDdsProvisionInterest extends BaseBean {

    private String msg;
    private String date;
    private String selectAcType;
    private String acNature;
    private List<SelectItem> selectAcTypeList;
    private List<SelectItem> acNatureList;
    private FdRdFacadeRemote beanLocal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote remoteCode;

    public RdDdsProvisionInterest() {
        try {

            beanLocal = (FdRdFacadeRemote) ServiceLocator.getInstance().lookup("FdRdFacade");
            remoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = remoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            List natureList = beanLocal.getAcNaturetldlcaList();
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            if (natureList.size() > 0) {
                for (int i = 0; i < natureList.size(); i++) {
                    Vector vec = (Vector) natureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0).toString()));
                }
            }
            // List codeList = beanLocal.getDistinctAcCodeByAcNature();
            selectAcTypeList = new ArrayList<SelectItem>();
            selectAcTypeList.add(new SelectItem("0", "--Select--"));
//            if (codeList.size() > 0) {
//                for (int i = 0; i < codeList.size(); i++) {
//                    Vector vec = (Vector) codeList.get(i);
//                    selectAcTypeList.add(new SelectItem(vec.get(0).toString()));
//                }
//            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public String validation() {
        if (selectAcType.equalsIgnoreCase("0")) {
            return "Please Select A/C. Type.";
        } else if (date.equalsIgnoreCase("")) {
            return "Please Enter Date.";
        }
        return "ok";
    }

    public void viewReport() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    msg = "ok";
                    String dt1[] = date.split("/");
                    String tranDt = dt1[2] + dt1[1] + dt1[0];
                    List list1;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        list1 = remoteCode.getBranchNameandAddress("90");
                    } else {
                        list1 = remoteCode.getBranchNameandAddress(branchCode);
                    }
                    String bankName = list1.get(0).toString();
                    String bankAdd = list1.get(1).toString();
                    String pin = list1.get(2).toString();
                    List<OverdueNonEmiResultList> reportList = beanLocal.getFdRdNonEmi(getAcNature(), selectAcType, tranDt, branchCode);
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", "FD Over Due Report");
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", date);
                    fillParams.put("pAcType", selectAcType);
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAddress", bankAdd);

                    if (reportList.size() > 0) {
                        new ReportBean().jasperReport("FdRdNonEmiReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "FD Over Due Report");
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    } else {
                        setMsg("No data exists !!");
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    msg = "ok";
                    String dt1[] = date.split("/");
                    String tranDt = dt1[2] + dt1[1] + dt1[0];
                    List list1;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        list1 = remoteCode.getBranchNameandAddress("90");
                    } else {
                        list1 = remoteCode.getBranchNameandAddress(branchCode);
                    }
                    String bankName = list1.get(0).toString();
                    String bankAdd = list1.get(1).toString();
                    String pin = list1.get(2).toString();

                    List<OverdueNonEmiResultList> reportList = beanLocal.getFdRdNonEmi(getAcNature(), selectAcType, tranDt, branchCode);
                    Map fillParams = new HashMap();
                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        fillParams.put("pReportName", "Rd Over Due Report");
                        fillParams.put("pHeader1", "Rd Date");
                    } else {
                        fillParams.put("pReportName", "DDs Over Due Report");
                        fillParams.put("pHeader1", "DDS Date");
                    }

                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", date);
                    fillParams.put("pAcType", selectAcType);
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAddress", bankAdd);

                    if (reportList.size() > 0) {
                        new ReportBean().jasperReport("RdGetInterestReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "RD DDS Provision");
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    } else {
                        setMsg("No data exists !!");
                    }
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void pdfDownLoad() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    msg = "ok";
                    String dt1[] = date.split("/");
                    String tranDt = dt1[2] + dt1[1] + dt1[0];
                    List list1;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        list1 = remoteCode.getBranchNameandAddress("90");
                    } else {
                        list1 = remoteCode.getBranchNameandAddress(branchCode);
                    }
                    String bankName = list1.get(0).toString();
                    String bankAdd = list1.get(1).toString();
                    String pin = list1.get(2).toString();

                    List<OverdueNonEmiResultList> reportList = beanLocal.getFdRdNonEmi(getAcNature(), selectAcType, tranDt, branchCode);
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", "FD Over Due Report");
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", date);
                    fillParams.put("pAcType", selectAcType);
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAddress", bankAdd);

                    if (reportList.size() > 0) {
                        new ReportBean().openPdf("FD Over Due Report_" + tranDt, "FdRdNonEmiReport", new JRBeanCollectionDataSource(reportList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", "FD Over Due Report");
                    } else {
                        setMsg("No data exists !!");
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    msg = "ok";
                    String dt1[] = date.split("/");
                    String tranDt = dt1[2] + dt1[1] + dt1[0];
                    List list1;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        list1 = remoteCode.getBranchNameandAddress("90");
                    } else {
                        list1 = remoteCode.getBranchNameandAddress(branchCode);
                    }
                    String bankName = list1.get(0).toString();
                    String bankAdd = list1.get(1).toString();
                    String pin = list1.get(2).toString();


                    List<OverdueNonEmiResultList> reportList = beanLocal.getFdRdNonEmi(getAcNature(), selectAcType, tranDt, branchCode);
                    Map fillParams = new HashMap();
                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        fillParams.put("pReportName", "Rd Over Due Report");
                        fillParams.put("pHeader1", "Rd Date");
                    } else {
                        fillParams.put("pReportName", "DDs Over Due Report");
                        fillParams.put("pHeader1", "DDS Date");
                    }
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", date);
                    fillParams.put("pAcType", selectAcType);
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBankAddress", bankAdd);

                    if (reportList.size() > 0) {
                        new ReportBean().openPdf("Rd Get Interest Report_" + tranDt, "RdGetInterestReport", new JRBeanCollectionDataSource(reportList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", "RD DDS Provision");
                    } else {
                        setMsg("No data exists !!");
                    }
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void getAcTypeForNature() {
        selectAcTypeList = new ArrayList<SelectItem>();
        try {
            if (!getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                selectAcTypeList.add(new SelectItem("All"));
            }
            List list = remoteCode.getAcctTypeAsAcNatureOnlyDB1(acNature);
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                selectAcTypeList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void refreshForm() {
        selectAcType = "0";
        date = "";
        msg = "";
    }

    public String exitAction() {
        return "case1";
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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }
}
