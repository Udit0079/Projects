/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.ho.CashInAtmPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.utils.CbsUtil;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class CashInAtm extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String atmHead;
    private List<SelectItem> atmHeadList;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<CashInAtmPojo> reportList = new ArrayList<CashInAtmPojo>();

    public String getAtmHead() {
        return atmHead;
    }

    public void setAtmHead(String atmHead) {
        this.atmHead = atmHead;
    }

    public List<SelectItem> getAtmHeadList() {
        return atmHeadList;
    }

    public void setAtmHeadList(List<SelectItem> atmHeadList) {
        this.atmHeadList = atmHeadList;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CashInAtm() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            branchCodeList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void getAtmGlhead() {
        atmHeadList = new ArrayList<SelectItem>();
        try {
            atmHeadList.add(new SelectItem("S", "--Select--"));
            List list = common.getAtmGlhead(branchCode);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    atmHeadList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        String report = "Cash in Atm Report";
        try {
            if (calFromDate == null) {
                errorMsg = "Please Fill From Date";
                return;
            }

            if (caltoDate == null) {
                errorMsg = "Please Fill To Date";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                errorMsg = "From Date should be less than To Date !";
                return;
            }
            String FromDt = dmy.format(calFromDate);
            String ToDate = dmy.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pGlheadNo", atmHead);

//            reportList = ddsRemote.getGlHeadData(branchCode,atmHead, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("Cash_in_atm", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Cash in Atm Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void refresh() {
    }

    public String exitAction() {
        return "case1";
    }
}
