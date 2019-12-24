/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.ho.DepositDrLoanCrBalPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class DepositDrLoanCrBal extends BaseBean {

    private String msg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String asOnDate;
    private Date date = new Date();
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote beanFacade;
    private DDSReportFacadeRemote ddsRemote;
    private List<DepositDrLoanCrBalPojo> reportList = new ArrayList<DepositDrLoanCrBalPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public DepositDrLoanCrBal() {
        setAsOnDate(dmy.format(new Date()));
        try {
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            getAcnoNature();
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void getAcnoNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = common.getAllNatureList();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("ALL", "ALL"));
            List aTypeList = common.getAccType(acNature);
            if (!aTypeList.isEmpty()) {
                for (int i = 0; i < aTypeList.size(); i++) {
                    Vector vec = (Vector) aTypeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void viewReport() {
        String report = "Deposit Dr Loan Cr Balance";
        try {
            if (acNature.equalsIgnoreCase("0")) {
                setMsg("Please select A/c Nature !");
                return;
            }

            if (asOnDate == null || asOnDate.equalsIgnoreCase("")) {
                setMsg("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(asOnDate)) {
                setMsg("Please select Proper As on date ");
                return;
            }
            if (dmy.parse(asOnDate).after(getDate())) {
                setMsg("As on date should be less than current date !");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", this.asOnDate);
            fillParams.put("pPrintedBy", getUserName());
            
            reportList = ddsRemote.getDepositDrLoanCrData(branchCode, acNature, acType, ymd.format(dmy.parse(asOnDate)));

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }

            new ReportBean().jasperReport("DepositDr_LoanCr", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Deposit Dr Loan Cr Balance");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void refreshForm() {
        setMsg("");
        setAcNature("0");
        setAsOnDate(dmy.format(new Date()));

    }

    public String exitAction() {
        return "case1";
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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
