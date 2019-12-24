/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LienReportPojo;
import com.cbs.dto.report.LineSortedByAcno;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class LienReport extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String errorMsg;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String asOnDate;
    private Date date = new Date();
    private String naturetype;
    private List<SelectItem> naturetypeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    List<LienReportPojo> resultlist = new ArrayList<LienReportPojo>();
    List tempList = null;
    Vector tempVector = null;

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

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
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

    public String getNaturetype() {
        return naturetype;
    }

    public void setNaturetype(String naturetype) {
        this.naturetype = naturetype;
    }

    public List<SelectItem> getNaturetypeList() {
        return naturetypeList;
    }

    public void setNaturetypeList(List<SelectItem> naturetypeList) {
        this.naturetypeList = naturetypeList;
    }

    public LienReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            asOnDate = dmy.format(date);

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acType();
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void acType() {
        try {
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("ALL"));
            tempList = loanRemote.getAcctTypecadlList();
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

            String asOnDt = ymd.format(dmy.parse(this.asOnDate));
            String report = "Lien Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportDate", this.asOnDate);
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);
            fillParams.put("pAcctCode", acctType);

            resultlist = loanRemote.getLienReport(acctType, asOnDt, branchCode);
            if (resultlist.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new LineSortedByAcno());
            Collections.sort(resultlist, chObj);

            new ReportBean().jasperReport("LienReport", "text/html",
                    new JRBeanCollectionDataSource(resultlist), fillParams, "Lien Report");
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

            String asOnDt = ymd.format(dmy.parse(this.asOnDate));
            String report = "Lien Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportDate", this.asOnDate);
            fillParams.put("pbankName", branchName);
            fillParams.put("pbankAddress", address);
            fillParams.put("pAcctCode", acctType);

            resultlist = loanRemote.getLienReport(acctType, asOnDt, branchCode);
            if (resultlist.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new LineSortedByAcno());
            Collections.sort(resultlist, chObj);

            new ReportBean().openPdf("Lien Report_" + ymd.format(dmy.parse(getTodayDate())), "LienReport", new JRBeanCollectionDataSource(resultlist), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        asOnDate = dmy.format(new Date());
    }

    public String exitAction() {
        return "case1";
    }
}
