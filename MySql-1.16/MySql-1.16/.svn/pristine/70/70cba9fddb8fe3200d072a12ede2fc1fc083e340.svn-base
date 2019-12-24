package com.cbs.web.controller.report.loan;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.pojo.CustAccountInfoPojo;
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

public class AccWiseMinMaxAvgBalance extends BaseBean {

    private String msg;
    private String frDate;
    private String toDate;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String selectAcType;
    private List<SelectItem> selectAcTypeList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private TdReceiptManagementFacadeRemote beanFacade;
    private CommonReportMethodsRemote common;
    private LoanReportFacadeRemote loanLocal = null;
    List<LoanIntCalcList> reportList = new ArrayList<LoanIntCalcList>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrDate() {
        return frDate;
    }

    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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

    public AccWiseMinMaxAvgBalance() {
        try {
            frDate = sdf.format(new Date());
            toDate = sdf.format(new Date());
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanLocal = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getacNature();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getacNature() {
        try {
            acnoNatureList = new ArrayList<SelectItem>();
            List acNtureList = common.getAcctNatureOnlyDB();
            if (!acNtureList.isEmpty()) {
//                 acnoNatureList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acnoNatureList.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
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
            List acTypeList = common.getAcctTypeAsAcNatureOnlyDB(acnoNature);
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

    public void viewReport() {
        setMsg("");
        String report = "Acc Wise Max Min Avg Balance";
        try {

            if (acnoNature.equalsIgnoreCase("0")) {
                setMsg("Please Select A/C. Nature.");
                return;
            }
            if (frDate == null || frDate.equalsIgnoreCase("")) {
                setMsg("Please enter from date");
                return;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                setMsg("Please enter to date");
                return;
            }

            if (sdf.parse(frDate).after(sdf.parse(toDate))) {
                setMsg("Please from date should be less than to date");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", sdf.format(sdf.parse(frDate)) + " to " + sdf.format(sdf.parse(toDate)));

            reportList = loanLocal.getMinMaxAvgBalance(branchCode, acnoNature, selectAcType, ymd.format(sdf.parse(frDate)), ymd.format(sdf.parse(toDate)));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            new ReportBean().jasperReport("Acc_Wise_Min_Max_Avg_Balance", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }

    }

    public void viewPdfReport() {
        setMsg("");
        String report = "Acc Wise Max Min Avg Balance";
        try {
            if (acnoNature.equalsIgnoreCase("0")) {
                setMsg("Please Select A/C. Nature.");
                return;
            }
            if (frDate == null || frDate.equalsIgnoreCase("")) {
                setMsg("Please enter from date");
                return;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                setMsg("Please enter to date");
                return;
            }

            if (sdf.parse(frDate).after(sdf.parse(toDate))) {
                setMsg("Please from date should be less than to date");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", sdf.format(sdf.parse(frDate)) + " to " + sdf.format(sdf.parse(toDate)));

            reportList = loanLocal.getMinMaxAvgBalance(branchCode, acnoNature, selectAcType, ymd.format(sdf.parse(frDate)), ymd.format(sdf.parse(toDate)));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            new ReportBean().openPdf("Acc_Wise_Min_Max_Avg_Balance" + ymd.format(sdf.parse(getToDate())), "Acc_Wise_Min_Max_Avg_Balance", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void refreshForm() {
        msg = "";
        acnoNature = "0";
        selectAcType = "ALL";
        frDate = sdf.format(new Date());
        toDate = sdf.format(new Date());
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }
}
