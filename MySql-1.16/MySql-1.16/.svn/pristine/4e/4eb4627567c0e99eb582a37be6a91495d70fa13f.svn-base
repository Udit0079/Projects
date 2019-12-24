/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.AccountEditHistoryPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.util.ArrayList;
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
 * @author mayank
 */
public class SigScanReport extends BaseBean {

    private String msg;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String selectAcType;
    private List<SelectItem> selectAcTypeList;
    private String optType;
    private List<SelectItem> optTypeLst;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote miscFacde;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public List<SelectItem> getOptTypeLst() {
        return optTypeLst;
    }

    public void setOptTypeLst(List<SelectItem> optTypeLst) {
        this.optTypeLst = optTypeLst;
    }

    public SigScanReport() {
        try {
            miscFacde = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            getacNature();
            optTypeLst = new ArrayList<SelectItem>();
            optTypeLst.add(new SelectItem("S", "SCANNED"));
            optTypeLst.add(new SelectItem("P", "PENDING"));
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

    public void refreshForm() {
        acnoNature = "0";
        msg = "";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public void viewReport() {
        String branchName = "";
        String address = "";
        try {
            
            if(this.getAcnoNature().equalsIgnoreCase("0")){
                setMsg("Please Select Account Nature");
                return;
            }
            
            List<AccountEditHistoryPojo> reportList = miscFacde.getSignatureReport(this.getOrgnBrCode(),this.getAcnoNature(),this.getSelectAcType(),this.getOptType());
            
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            
            Map fillParams = new HashMap();
            if(optType.equalsIgnoreCase("S")){
                fillParams.put("pReportName", "Signature Scanned");
            }else{
                fillParams.put("pReportName", "Signature Scan Pending");
            }            
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pAcType", selectAcType);
            fillParams.put("pBnk", branchName);
            fillParams.put("pBnkAdd", address);
            ReportBean rb = new ReportBean();
            if (reportList.size() > 0) {
                rb.jasperReport("sigScanReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Signature Scanned");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                setMsg("No details exists");
            }             
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
}