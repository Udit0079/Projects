/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareTransferPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
 * @author admin
 */
public class ShareTransfer extends BaseBean implements Serializable {

    private String errorMsg;
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private Date frDt, toDt;

    /** Creates a new instance of ShareTransfer */
    public ShareTransfer() {
        try {
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        } catch (Exception e) {
        }

    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getFrDt() {
        return frDt;
    }

    public void setFrDt(Date frDt) {
        this.frDt = frDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public String viewReport() {
        if (validate()) {
            try {
                List<ShareTransferPojo> resultList = horfr.getTransfererReport(ymd.format(frDt), ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate",dmy.format(frDt)+" to "+dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Share Transfer Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().jasperReport("ShareTransferReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Share Transfer Report");
                    return "report";
                }else{
                    setErrorMsg("Data does not exist !");
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    public String pdfDownLoad() {
        if (validate()) {
            try {
                List<ShareTransferPojo> resultList = horfr.getTransfererReport(ymd.format(frDt), ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate",dmy.format(frDt)+" to "+dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Share Transfer Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().openPdf("Share Transfer Report", "ShareTransferReport", new JRBeanCollectionDataSource(resultList), fillParams);
                   ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", "Share Transfer Report");
                }else{
                    setErrorMsg("Data does not exist !");
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public void refreshPage() {
    }

    private boolean validate() {
        if (frDt == null) {
            errorMsg = "Please enter from date";
            return false;
        } else if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        } else if (frDt.after(toDt)) {
            errorMsg = "Please from date is less then to date";
            return false;
        }
        return true;
    }
}
