/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.pojo.SortedByAcTypeDeamanRecovery;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
 * @author Admin
 */
public class AreaWiseLoanRecovery extends BaseBean {

    private String message;
    private String area;
    private List<SelectItem> areaList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String repType;
    private List<SelectItem> repList;
    private String frmDt;
    private String toDt;
    private String dmdSrno;
    private List<SelectItem> dmdSrnoList;
    private String isoverDue;
    private List<SelectItem> isoverDueList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanReportFacadeRemote RemoteLocal;
    private CommonReportMethodsRemote common;
    FtsPostingMgmtFacadeRemote fts;
    private List<StatemenrtOfRecoveriesPojo> reportList = new ArrayList<StatemenrtOfRecoveriesPojo>();

    public AreaWiseLoanRecovery() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteLocal = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("ALL", "ALL"));
            List result = common.getRefRecList("019");
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            repList = new ArrayList<SelectItem>();
            repList.add(new SelectItem("Se", "--Select--"));
            repList.add(new SelectItem("De", "Detail"));
            repList.add(new SelectItem("Su", "Summary"));

            isoverDueList = new ArrayList<SelectItem>();
            isoverDueList.add(new SelectItem("S", "--Select--"));
            isoverDueList.add(new SelectItem("O", "OverDueList"));
            isoverDueList.add(new SelectItem("R", "Recovery"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chenageOperation() {
        try {
            dmdSrnoList = new ArrayList<SelectItem>();
            dmdSrnoList.add(new SelectItem("S", "--Select--"));
            List result = common.getAreaWiseDmdsrno(area, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    dmdSrnoList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }


    }

    public void viewReport() {

        setMessage("");
        String branchName = "", address = "";
        String report = "Area wise Demand recovery";
        try {

            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }

            if (!Validator.validateDate(frmDt)) {
                setMessage("Please check from date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please check to date");
                return;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less then to date");
                return;
            }

            if (dmdSrno == null || dmdSrno.equalsIgnoreCase("S")) {
                setMessage("Please select Demand Srl No. !");
                return;
            }

            if (repType.equalsIgnoreCase("Se")) {
                setMessage("Please select Report Type !");
                return;
            }

            if (isoverDue == null || isoverDue.equalsIgnoreCase("S")) {
                setMessage("Please select Over Due !");
                return;
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            if (isoverDue.equalsIgnoreCase("O")) {
                fillParams.put("pReportName", report + " (Over Due)");
            } else {
                fillParams.put("pReportName", report);
            }

            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", this.toDt);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pRepType", repType);
            fillParams.put("pSrlNo", dmdSrno);
            if (fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                fillParams.put("pArea", this.area);
            } else {
                if (!area.equalsIgnoreCase("ALL")) {
                    if (this.area.contains("P")) {
                        fillParams.put("pArea", "PAY BILL NO : " + this.area.substring(8));
                    } else {
                        fillParams.put("pArea", "CHECK ROLL NO : " + this.area.substring(10));
                    }
                }
            }
            reportList = RemoteLocal.getAreaWiseRecoveryData(area, getOrgnBrCode(), repType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), Integer.parseInt(dmdSrno), isoverDue);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }

            ComparatorChain cnObj = new ComparatorChain();
            cnObj.addComparator(new SortedByAcTypeDeamanRecovery());
            Collections.sort(reportList, cnObj);

            new ReportBean().jasperReport("AreaWiseRecovery", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Area wise Demand recovery");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public String getIsoverDue() {
        return isoverDue;
    }

    public void setIsoverDue(String isoverDue) {
        this.isoverDue = isoverDue;
    }

    public List<SelectItem> getIsoverDueList() {
        return isoverDueList;
    }

    public void setIsoverDueList(List<SelectItem> isoverDueList) {
        this.isoverDueList = isoverDueList;
    }

    public void pdfDownLoad() {
        setMessage("");
        String branchName = "", address = "";
        String report = "Area wise Demand recovery";
        try {

            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }

            if (!Validator.validateDate(frmDt)) {
                setMessage("Please check from date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please check to date");
                return;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less then to date");
                return;
            }

            if (dmdSrno == null || dmdSrno.equalsIgnoreCase("S")) {
                setMessage("Please select Demand Srl No. !");
                return;
            }

            if (repType.equalsIgnoreCase("Se")) {
                setMessage("Please select Report Type !");
                return;
            }

            if (isoverDue == null || isoverDue.equalsIgnoreCase("S")) {
                setMessage("Please select Over Due !");
                return;
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            if (isoverDue.equalsIgnoreCase("O")) {
                fillParams.put("pReportName", report + " (Over Due)");
            } else {
                fillParams.put("pReportName", report);
            }
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", this.toDt);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pRepType", repType);
            fillParams.put("pSrlNo", dmdSrno);
            if (fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                fillParams.put("pArea", this.area);
            } else {
                if (!area.equalsIgnoreCase("ALL")) {
                    if (this.area.contains("P")) {
                        fillParams.put("pArea", "PAY BILL NO : " + this.area.substring(8));
                    } else {
                        fillParams.put("pArea", "CHECK ROLL NO : " + this.area.substring(10));
                    }
                }
            }
            reportList = RemoteLocal.getAreaWiseRecoveryData(area, getOrgnBrCode(), repType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), Integer.parseInt(dmdSrno), isoverDue);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }

            ComparatorChain cnObj = new ComparatorChain();
            cnObj.addComparator(new SortedByAcTypeDeamanRecovery());
            Collections.sort(reportList, cnObj);

            new ReportBean().openPdf("Area wise Demand recovery", "AreaWiseRecovery", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Area wise Demand recovery");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshPage() {
        setMessage("");
        setArea("S");
        setRepType("Se");
        frmDt = dmy.format(date);
        toDt = dmy.format(date);
        setDmdSrno("S");
        setIsoverDue("S");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<SelectItem> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SelectItem> areaList) {
        this.areaList = areaList;
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

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepList() {
        return repList;
    }

    public void setRepList(List<SelectItem> repList) {
        this.repList = repList;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
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

    public String getDmdSrno() {
        return dmdSrno;
    }

    public void setDmdSrno(String dmdSrno) {
        this.dmdSrno = dmdSrno;
    }

    public List<SelectItem> getDmdSrnoList() {
        return dmdSrnoList;
    }

    public void setDmdSrnoList(List<SelectItem> dmdSrnoList) {
        this.dmdSrnoList = dmdSrnoList;
    }
}
