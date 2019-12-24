/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.CallMoneydetailPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class CallMoneyDetail extends BaseBean {

    public String errorMsg;
    public String frDate;
    public String toDate;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    List<CallMoneydetailPojo> reportList = new ArrayList<CallMoneydetailPojo>();

    public CallMoneyDetail() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void viewReport() {
        String bnkName = "", bnkAddress = "";

        try {
            if (frDate.equalsIgnoreCase("") || frDate == null) {
                setErrorMsg("Plese fill From date!");
                return;
            }
            if (toDate.equalsIgnoreCase("") || toDate == null) {
                setErrorMsg("Plese fill To date!");
                return;
            }

            String report = "Call Money Detail Report";
            List bnkList = new ArrayList();
            bnkList = common.getBranchNameandAddress(getOrgnBrCode());
            if (bnkList.size() > 0) {
                bnkName = (String) bnkList.get(0);
                bnkAddress = (String) bnkList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pbnkName", bnkName);
            fillParams.put("pbnkAddress", bnkAddress);
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", this.frDate);
            fillParams.put("pPrintBy", getUserName());
            reportList = remoteObj.getCallMoneydata(ymd.format(dmy.parse(frDate)), ymd.format(dmy.parse(toDate)));
            new ReportBean().jasperReport("CallMoneyDetail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Call Money Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//            HttpSession httpSession = getHttpSession();
//            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        frDate = dmy.format(new Date());
        toDate = dmy.format(new Date());

    }

    public String exitAction() {
        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
