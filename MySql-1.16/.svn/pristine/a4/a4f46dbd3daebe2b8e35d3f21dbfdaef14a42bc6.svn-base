package com.cbs.web.controller.report.other;

import com.cbs.dto.report.IssueChequeBookRegisterPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
 * @author Zeeshan Waris
 */
public final class ChequeBookIssued extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    OtherReportFacadeRemote beanRemote;
    private String acType;
    private String repType;
    private boolean acTypeDisable;
    private List<SelectItem> acTypeList;
    private List<SelectItem> reportTypeList;
    private String branch;
    private List<SelectItem> branchList;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

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

    public boolean isAcTypeDisable() {
        return acTypeDisable;
    }

    public void setAcTypeDisable(boolean acTypeDisable) {
        this.acTypeDisable = acTypeDisable;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public ChequeBookIssued() {
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acTypeDisable = true;
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "ALL Cheque Book Issue"));
            reportTypeList.add(new SelectItem("1", "A/c Type Wise Cheque Book Issue"));
            getAccountypeList();
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountypeList() {
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List acctCodeList = common.getCASBAcTypeList();
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--Select--"));
            if (!acctCodeList.isEmpty()) {
                for (int i = 0; i < acctCodeList.size(); i++) {
                    Vector ele = (Vector) acctCodeList.get(i);
                    for (Object ee : ele) {
                        acTypeList.add(new SelectItem(ee.toString()));
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (calFromDate.after(caltoDate)) {
                setMessage("From date should be less than to date");
                return null;
            }
            if (repType.equalsIgnoreCase("1")) {
                if (acType.equalsIgnoreCase("--Select--")) {
                    message = "Please select A/c Type";
                    return null;
                }
            }
            if (repType.equalsIgnoreCase("0")) {
                acType = "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<IssueChequeBookRegisterPojo> isueChequeKookRegister = beanRemote.issueChequeBookRegister(Integer.parseInt(repType), acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branch);
            if (!isueChequeKookRegister.isEmpty()) {
                String repName = "Cheque Book Issue Register";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("fromDate", sdf.format(calFromDate));
                fillParams.put("toDate", sdf.format(caltoDate));
                new ReportBean().jasperReport("chequeBookIssuedRegister", "text/html", new JRBeanCollectionDataSource(isueChequeKookRegister), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (calFromDate.after(caltoDate)) {
                setMessage("From date should be less than to date");
                return null;
            }
            if (repType.equalsIgnoreCase("1")) {
                if (acType.equalsIgnoreCase("--Select--")) {
                    message = "Please select A/c Type";
                    return null;
                }
            }
            if (repType.equalsIgnoreCase("0")) {
                acType = "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<IssueChequeBookRegisterPojo> isueChequeKookRegister = beanRemote.issueChequeBookRegister(Integer.parseInt(repType), acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branch);
            if (!isueChequeKookRegister.isEmpty()) {
                String repName = "Cheque Book Issue Register";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("fromDate", sdf.format(calFromDate));
                fillParams.put("toDate", sdf.format(caltoDate));
                new ReportBean().openPdf("Cheque Book Issue Register_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "chequeBookIssuedRegister", new JRBeanCollectionDataSource(isueChequeKookRegister), fillParams);

                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "Cheque Stop Pay Register");
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;

    }

    public void ddReportTypeprocessValueChange() {
        if (repType.equalsIgnoreCase("1")) {
            acTypeDisable = false;
        } else {
            acTypeDisable = true;

        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcType("--Select--");
            calFromDate = new Date();
            caltoDate = new Date();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
