package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.BalanceBookReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class BalanceBookReport extends BaseBean {

    private String message;
    private String branch;
    private String exCounter;
    private String displayExCtr;
    private List<SelectItem> exCounterList;
    private List<SelectItem> branchList;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private GLReportFacadeRemote beanRemote;
    private CommonReportMethodsRemote common;

    public BalanceBookReport() {
        try {
            beanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            exCounterList = new ArrayList<>();
            exCounterList.add(new SelectItem("N", "No"));
            exCounterList.add(new SelectItem("Y", "Yes"));
            setExCounter("N");
            setDisplayExCtr("none");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
   
     public void checkExtCounter(){
        try {
            setMessage("");
            if(!branch.equals("0A") && common.isExCounterExit(branch)){
                setDisplayExCtr("");
            }
            else{
                setDisplayExCtr("none");
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
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<BalanceBookReportPojo> balanceBookReport = beanRemote.getBalanceBookReport(ymdFormat.format(fromDate), ymdFormat.format(toDate),this.branch,this.exCounter);
            if (balanceBookReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!balanceBookReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "Balance Book Report";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pReportName", repName);
                fillParams.put("pFromDate", dmyFormat.format(fromDate));
                fillParams.put("pToDate", dmyFormat.format(toDate));
                fillParams.put("pPrintedDate", dmyFormat.format(fromDate) + " to " + dmyFormat.format(toDate));
                new ReportBean().jasperReport("balanceBookReport", "text/html", new JRBeanCollectionDataSource(balanceBookReport), fillParams, repName);
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

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String viewPdfReport() {
        String bankName = "";
        String branchAddress = "";
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            GLReportFacadeRemote beanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            List<BalanceBookReportPojo> balanceBookReport = beanRemote.getBalanceBookReport(ymdFormat.format(fromDate), ymdFormat.format(toDate),this.branch,this.exCounter);
            if (balanceBookReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!balanceBookReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String report = "Balance Book Report";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                fillParams.put("pReportName", report);
                fillParams.put("pFromDate", dmyFormat.format(fromDate));
                fillParams.put("pToDate", dmyFormat.format(toDate));
                fillParams.put("pPrintedDate", dmyFormat.format(fromDate) + " to " + dmyFormat.format(toDate));

                new ReportBean().openPdf("balanceBookReport", "balanceBookReport", new JRBeanCollectionDataSource(balanceBookReport), fillParams);
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
        return null;
    }

    public String exitAction() {
        return "case1";
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getExCounter() {
        return exCounter;
    }

    public void setExCounter(String exCounter) {
        this.exCounter = exCounter;
    }

    public String getDisplayExCtr() {
        return displayExCtr;
    }

    public void setDisplayExCtr(String displayExCtr) {
        this.displayExCtr = displayExCtr;
    }

    public List<SelectItem> getExCounterList() {
        return exCounterList;
    }

    public void setExCounterList(List<SelectItem> exCounterList) {
        this.exCounterList = exCounterList;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
    
}
