/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.MinorAccountPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
 * @author Athar Reza
 */
public class MinorAccount extends BaseBean {

    private String errorMsg;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String tillDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private OtherReportFacadeRemote beanRemote;
    //private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List tempList = null;
    Vector tempVector = null;
    List<MinorAccountPojo> resultlist = new ArrayList<MinorAccountPojo>();

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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public MinorAccount() {
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            //local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            tillDt = dmy.format(date);
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acctTypeList = new ArrayList<SelectItem>();
            acType();
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void acType() {
        try {
            acctTypeList.add(new SelectItem("ALL", "ALL"));
            tempList = common.getAllAcTypeList();
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
            if (acctType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please Select Account Type !");
                return;
            }

            if (tillDt == null || tillDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill Till Date");
                return;
            }
            if (!Validator.validateDate(tillDt)) {
                setErrorMsg("Please select Proper Till date ");
                return;
            }
            if (dmy.parse(tillDt).after(getDate())) {
                setErrorMsg("Till date should be less than current date !");
                return;
            }
            String tillDate[] = tillDt.split("/");
            String report = "Minor Account Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportDate", this.tillDt);
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);

            resultlist = beanRemote.getMinorAccount((tillDate[2] + tillDate[1] + tillDate[0]), acctType, branchCode);
            if (resultlist.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            new ReportBean().jasperReport("MinorAccountReport", "text/html",
                    new JRBeanCollectionDataSource(resultlist), fillParams, "Minor Account Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        String branchName = "";
        String address = "";
        try {
            if (acctType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please Select Account Type !");
                return;
            }

            if (tillDt == null || tillDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill Till Date");
                return;
            }
            if (!Validator.validateDate(tillDt)) {
                setErrorMsg("Please select Proper Till date ");
                return;
            }
            if (dmy.parse(tillDt).after(getDate())) {
                setErrorMsg("Till date should be less than current date !");
                return;
            }
            String tillDate[] = tillDt.split("/");
            String report = "Minor Account Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportDate", this.tillDt);
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);

            resultlist = beanRemote.getMinorAccount((tillDate[2] + tillDate[1] + tillDate[0]), acctType, branchCode);
            if (resultlist.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            new ReportBean().openPdf("Minor Account Report_" + (tillDate[2] + tillDate[1] + tillDate[0]), "MinorAccountReport", new JRBeanCollectionDataSource(resultlist), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setErrorMsg("");
            tillDt = dmy.format(new Date());
            this.setAcctType("Select");
            this.setBranchCode("ALL");
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
