/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.pojo.HoReconsilePojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class HoReconsileEntry extends BaseBean {
    
    private String message;
    private String frdt;
    private String todt;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "NpaReportFacade";
    private NpaReportFacadeRemote remote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    
    public String getFrdt() {
        return frdt;
    }
    
    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTodt() {
        return todt;
    }
    
    public void setTodt(String todt) {
        this.todt = todt;
    }
    
    public HoReconsileEntry() {
        try {
            remote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            this.setFrdt(getTodayDate());
            this.setTodt(getTodayDate());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (this.frdt == null || this.frdt.equals("")) {
                this.setMessage("Please select from date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.frdt);
            if (result == false) {
                this.setMessage("Please select proper from date !");
                return;
            }
            if (this.todt == null || this.todt.equals("")) {
                this.setMessage("Please select to date !");
                return;
            }
            result = new Validator().validateDate_dd_mm_yyyy(this.todt);
            if (result == false) {
                this.setMessage("Please select proper to date !");
                return;
            }
            
            int compareValue = dmy.parse(frdt).compareTo(dmy.parse(todt));
            if (compareValue > 0) {
                this.setMessage("Please check from date !");
                return;
            }
            List resultList = remote.getReconsiledEntry(ymd.format(dmy.parse(this.frdt)), ymd.format(dmy.parse(this.todt)), this.getOrgnBrCode());
            System.out.println("ResultList Size is " + resultList.size());
            for (int k = 0; k < resultList.size(); k++) {
                HoReconsilePojo pojo = (HoReconsilePojo) resultList.get(k);
                System.out.println(pojo.getBaseBranch() + "     " + pojo.getRespondingBranch() + "      " + pojo.getTranType() + "     " + pojo.getAmount() + "       " + pojo.getTranDt() + "\n");
            }
            if (!resultList.isEmpty()) {
                String reportName = "Ho Reconsilation Report";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pPrintedDate", getFrdt() + " to " + getTodt());
                
                new ReportBean().jasperReport("HoReconsileEntry", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnRefreshAction() {
        this.setMessage("");
        this.setFrdt(getTodayDate());
        this.setTodt(getTodayDate());
    }
    
    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
