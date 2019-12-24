package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.CashReserveReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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

public final class CashReserveReport extends BaseBean {

    private String message;
    Date calDate = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote RemoteCode;

    public CashReserveReport() {
        try {
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String printAction() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            GLReportFacadeRemote beanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            List<CashReserveReportPojo> cashReserveReport = beanRemote.getCashReserveReport(ymdFormat.format(calDate), branchCode);
            if (cashReserveReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!cashReserveReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                
                double crrPercent = RemoteCode.getcrrPercent(ymdFormat.format(calDate));
                
                String repName = "Cash Reserve Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("crrPercent", crrPercent);
                new ReportBean().jasperReport("Cash_Reserve_Report", "text/html", new JRBeanCollectionDataSource(cashReserveReport), fillParams, repName);
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

    public String btnPdfAction() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            GLReportFacadeRemote beanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            List<CashReserveReportPojo> cashReserveReport = beanRemote.getCashReserveReport(ymdFormat.format(calDate), branchCode);
            if (cashReserveReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!cashReserveReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                double crrPercent = RemoteCode.getcrrPercent(ymdFormat.format(calDate));
                String report = "Cash Reserve Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("crrPercent", crrPercent);
                new ReportBean().openPdf("Cash_Reserve_Report_", "Cash_Reserve_Report", new JRBeanCollectionDataSource(cashReserveReport), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
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

    public String exitAction() {
        return "case1";
    }

    public void refresh() {
        try {
            this.setMessage("");
//            this.setFromDate(new Date());
//            this.setToDate(new Date());

        } catch (Exception e) {

            setMessage(e.getMessage());
        }
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
}
