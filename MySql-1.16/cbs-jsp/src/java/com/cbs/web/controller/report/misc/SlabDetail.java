/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.SlabDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
 * @author Athar Reza
 */
public class SlabDetail extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private String acType;
    private List<SelectItem> acTypeList;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<SlabDetailPojo> reportList = new ArrayList<SlabDetailPojo>();

    public SlabDetail() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            acTypeList = new ArrayList<SelectItem>();
            List listAccTy = remoteFacade.getFdrdtltdAcctType();
            Vector vtr = new Vector();
            if (!listAccTy.isEmpty()) {
                acTypeList.add(new SelectItem("S", "--Select--"));
                for (int i = 0; i < listAccTy.size(); i++) {
                    vtr = (Vector) listAccTy.get(i);
                    acTypeList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public void printAction() {
        String report = "Slab Detail Report";
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }

            if (caltoDate == null) {
                message = "Please Fill To Date";
                return;
            }
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            String FromDt = dmy.format(calFromDate);
            String ToDate = dmy.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            
            int cParam = common.getCodeByReportName("TD_MINOR_GIRL");
           
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pParamCode", cParam);
            reportList = remoteFacade.getSlabDetal(acType, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            if (acType.equalsIgnoreCase("FD") || acType.equalsIgnoreCase("MS") || acType.equalsIgnoreCase("RD")) {
                new ReportBean().jasperReport("SlabDetail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Slab Detail Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else if (acType.equalsIgnoreCase("DS")) {
                new ReportBean().jasperReport("SlabDetail_DS", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Slab Detail Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().jasperReport("SlabDetailLoan", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Slab Detail Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcType("S");
            setCalFromDate(new Date());
            setCaltoDate(new Date());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
