package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.AccountStatusReportPojo;
import com.cbs.dto.report.AccountStatusShortedByDepLoan;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

public final class AccountStatusReport extends BaseBean {

    private String message;
    private String brCode;
    private List<SelectItem> branchCodeList;
    Date calDate = new Date();
    MiscReportFacadeRemote beanRemote;
    CommonReportMethodsRemote RemoteCode;

    public AccountStatusReport() {
        try {
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchCode = new ArrayList();
            branchCode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            // branchCodeList.add(new SelectItem("ALL"));
            if (!branchCode.isEmpty()) {
                for (int i = 0; i < branchCode.size(); i++) {
                    Vector brnVector = (Vector) branchCode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public String printAction() {
        String bankName = "";
        String branchAddress = "";

        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            if (this.brCode.length() == 1) {
                brCode = "0" + brCode;
            }
            List<AccountStatusReportPojo> accountStatusReport = beanRemote.getAccountStatusReport(ymdFormat.format(calDate), this.brCode);
            if (accountStatusReport == null) {
                setMessage("System error occurred");
                return null;
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new AccountStatusShortedByDepLoan());
            Collections.sort(accountStatusReport, chObj);

            for (int i = 0; i < accountStatusReport.size(); i++) {
                AccountStatusReportPojo val = accountStatusReport.get(i);
                System.out.println("depostt loan->" + val.getDepositLoan() + "AcName ->" + val.getAcName() + "active acno ->" + val.getActiveAcc() + "close acno ->" + val.getClosedAcc());
            }
            if (!accountStatusReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "Account Status Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                new ReportBean().jasperReport("AccountStatus_Report", "text/html", new JRBeanCollectionDataSource(accountStatusReport), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print !!");
            }

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void viewPdfReport() {
        String bankName = "";
        String branchAddress = "";

        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            if (this.brCode.length() == 1) {
                brCode = "0" + brCode;
            }
            List<AccountStatusReportPojo> accountStatusReport = beanRemote.getAccountStatusReport(ymdFormat.format(calDate), this.brCode);
            if (accountStatusReport == null) {
                setMessage("System error occurred");
                return;
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new AccountStatusShortedByDepLoan());
            Collections.sort(accountStatusReport, chObj);

            for (int i = 0; i < accountStatusReport.size(); i++) {
                AccountStatusReportPojo val = accountStatusReport.get(i);
                System.out.println("depostt loan->" + val.getDepositLoan() + "AcName ->" + val.getAcName() + "active acno ->" + val.getActiveAcc() + "close acno ->" + val.getClosedAcc());
            }
            if (!accountStatusReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String report = "Account Status Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                new ReportBean().openPdf("Account Status Report_"+ ymdFormat.format(dmyFormat.parse(getTodayDate())), "AccountStatus_Report", new JRBeanCollectionDataSource(accountStatusReport), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print !!");
            }

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }
}
