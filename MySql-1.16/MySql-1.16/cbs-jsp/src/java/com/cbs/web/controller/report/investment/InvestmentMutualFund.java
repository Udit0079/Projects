/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.dto.report.InvestmentMutualFundPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class InvestmentMutualFund extends BaseBean {

    private Date date = new Date();
    private CommonReportMethodsRemote common;
    private String repType;
    private String frmDt;
    private String toDt;
    private String bankType;
    private List<SelectItem> repTypeList;  
    private List<SelectItem> bankTypeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String message;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;

    public void btnRefreshAction() {
        this.message = "";
        this.setRepType("All");
        this.setBankType("All");
        this.setFrmDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
    }

    public String btnExitAction() {
        return "case1";
    }

    public InvestmentMutualFund() {

        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup("InvestmentReportMgmtFacade");
            List<String> resultList = remoteObj.getBankingType();
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List bankList = remoteObj.getBankingType();
            bankTypeList = new ArrayList<SelectItem>();
            bankTypeList.add(new SelectItem("All"));
            for (int i = 0; i < bankList.size(); i++) {
                Vector ele = (Vector) bankList.get(i);
                bankTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                this.setFrmDt(dmy.format(new Date()));
                this.setToDt(dmy.format(new Date()));
            }
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("ALL", "ALL"));
            repTypeList.add(new SelectItem("A", "ACTIVE"));
            repTypeList.add(new SelectItem("R", "REDEEM"));
            } catch (Exception e) {
            setMesssage(e.getLocalizedMessage());
        }
    }

    public void onBlurAsOnDt() {
        this.setMessage("");
        if (this.frmDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (this.toDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.frmDt) == false) {
            this.setMessage("please fill correct from date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
            this.setMessage("please fill correct to date !");
            return;
        }
    }

    public void processAction() throws ParseException {
        String report = "";
        try {
            this.setMessage("");
            if (this.frmDt.length() < 10) {
                this.setMessage("please fill correct report as on date !");
                return;
            }
            if (this.toDt.length() < 10) {
                this.setMessage("please fill correct report as on date !");
                return;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.frmDt) == false) {
                this.setMessage("please fill correct from date !");
                return;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
                this.setMessage("please fill correct to date !");
                return;
            }
            report = "Investment Mutual Fund Info";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", this.frmDt + " - " + this.toDt);
            fillParams.put("pReportName", report);
            List<InvestmentMutualFundPojo> resultList = remoteObj.getInvestmentFundData(repType, bankType, ymd.format(dmy.parse(frmDt)),ymd.format(dmy.parse(toDt)));
            if (!resultList.isEmpty()) {
                new ReportBean().jasperReport("investment_mutual_fund_info", "text/html",
                        new JRBeanCollectionDataSource(resultList), fillParams, "Investment Mutual Fund Info");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                this.setMessage("Data does not exist !");
                return;
            }

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(InvestmentMutualFund.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         *
         */
    }

    public void pdfAction() {
        String report = "";
        try {

            report = "Investment Mutual Fund Info";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", this.frmDt + " - " + this.toDt);
            fillParams.put("pReportName", report);
            List<InvestmentMutualFundPojo> resultList = remoteObj.getInvestmentFundData(repType, bankType, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
            if (!resultList.isEmpty()) {
                new ReportBean().openPdf("Investment Mutual Fund Info" + ymd.format(dmy.parse(getTodayDate())), "investment_mutual_fund_info", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                this.setMessage("Data does not exist !");
                return;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public List<SelectItem> getBankTypeList() {
        return bankTypeList;
    }

    public void setBankTypeList(List<SelectItem> bankTypeList) {
        this.bankTypeList = bankTypeList;
    }

    public void setMesssage(String messsage) {
        this.message = message;
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
}