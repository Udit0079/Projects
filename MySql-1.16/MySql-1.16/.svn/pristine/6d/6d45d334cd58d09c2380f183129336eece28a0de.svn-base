/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.SignatureReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
 * @author Athar Reza
 */
public class SignatureReport extends BaseBean {

    private String errorMsg;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String sig;
    private List<SelectItem> sigList;
    private OtherReportFacadeRemote beanRemote;
    private CommonReportMethodsRemote common;
    List tempList = null;
    Vector tempVector = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<SignatureReportPojo> dataList = new ArrayList<SignatureReportPojo>();


    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public List<SelectItem> getSigList() {
        return sigList;
    }

    public void setSigList(List<SelectItem> sigList) {
        this.sigList = sigList;
    }

    public SignatureReport() {
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            sigList = new ArrayList<SelectItem>();
            sigList.add(new SelectItem("--Select--", "--Select--"));
            sigList.add(new SelectItem("Signature Scanned", "Signature Scanned"));
            sigList.add(new SelectItem("Duplicate Signature", "Duplicate Signature"));
            acctTypeList = new ArrayList<SelectItem>();
            acType();
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void acType() {
        try {
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("--Select--"));
            tempList = beanRemote.getAcctTypeList();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acctTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        String branchName = "";
        String address = "";
        try {
            String report = "Signature Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);
            
            dataList = beanRemote.getSignatureList(sig, acctType, getOrgnBrCode());

            new ReportBean().jasperReport("SignatureReport", "text/html",
                    new JRBeanCollectionDataSource(dataList), fillParams, "Signature Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        setSig("--Select--");
        setAcctType("--Select--");
    }

    public String exitAction() {
        return "case1";
    }
}
