package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.CADebitBalancePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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

/**
 *
 * @author Zeeshan Waris
 */
public final class CADebitBalanceReport extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    MiscReportFacadeRemote beanRemote;
    private String acType;
    private List<SelectItem> acTypeList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote RemoteCode;

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

    public CADebitBalanceReport() {
        try {
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
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
            getAccountypeList();
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountypeList() {
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List acctCodeList = common.getCAAcTypeList();
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

            if (acType.equalsIgnoreCase("--Select--")) {
                message = "Please select A/c Type";
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<CADebitBalancePojo> CADebitBalance = beanRemote.CADebitBalance(ymdFormat.format(calFromDate), acType, branchCode);

            if (CADebitBalance == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!CADebitBalance.isEmpty()) {
                String desc = beanRemote.getAccDescriptionByAcctCode(acType);
                String repName = "DEBIT BALANCE";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", sdf.format(calFromDate));
                fillParams.put("pType", "\"" + desc + "\"");
                new ReportBean().jasperReport("CADebitBalance", "text/html", new JRBeanCollectionDataSource(CADebitBalance), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }
    
    public void viewPdfReport(){
      setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return ;
            }

            if (acType.equalsIgnoreCase("--Select--")) {
                message = "Please select A/c Type";
                return ;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<CADebitBalancePojo> CADebitBalance = beanRemote.CADebitBalance(ymdFormat.format(calFromDate), acType, branchCode);

            if (CADebitBalance == null) {
                setMessage("System error occurred");
                return ;
            }
            if (!CADebitBalance.isEmpty()) {
                String desc = beanRemote.getAccDescriptionByAcctCode(acType);
                String report = "DEBIT BALANCE";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", sdf.format(calFromDate));
                fillParams.put("pType", "\"" + desc + "\"");
                new ReportBean().openPdf("DEBIT BALANCE_"+ ymdFormat.format(sdf.parse(getTodayDate())), "CADebitBalance", new JRBeanCollectionDataSource(CADebitBalance), fillParams);
                  ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
              
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
       
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcType("--Select--");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
