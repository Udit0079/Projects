/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.FolioAccountStatementPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
public class FolioStatement extends BaseBean {
    
    private String folioNo, acNoLen;
    private String errorMsg, folioNoShow;
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private Date date;

    /** Creates a new instance of FolioStatement */
    public FolioStatement() {
        try {
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
        } catch (Exception e) {
        }
    }
    
    public String getFolioNoShow() {
        return folioNoShow;
    }
    
    public void setFolioNoShow(String folioNoShow) {
        this.folioNoShow = folioNoShow;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getFolioNo() {
        return folioNo;
    }
    
    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public String viewReport() {
        if (validate()) {
            try {
                List<FolioAccountStatementPojo> resutList = horfr.getFolioStatementList(folioNo, ymd.format(date));
                if (!resutList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(date));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Folio Statement");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().jasperReport("FolioStatementReport", "text/html", new JRBeanCollectionDataSource(resutList), fillParams, "Folio Statement");
                    return "report";
                } else {
                    errorMsg = "Data does not exist";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public String pdfDownLoad() {
        if (validate()) {
            try {
                List<FolioAccountStatementPojo> resutList = horfr.getFolioStatementList(folioNo, ymd.format(date));
                if (!resutList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(date));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Folio Statement");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().openPdf("Folio Statement", "FolioStatementReport", new JRBeanCollectionDataSource(resutList), fillParams);
                   ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                  HttpSession httpSession = getHttpSession();
                  httpSession.setAttribute("ReportName", "Folio Statement");
                } else {
                    errorMsg = "Data does not exist";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public void refreshPage() {
        setErrorMsg("");
        setFolioNo("");
        setFolioNoShow("");
    }
    
    private boolean validate() {
        if (folioNo == null || folioNo.equalsIgnoreCase("")) {
            errorMsg = "Please enter valid folio no";
            return false;
        } if (!this.folioNo.equalsIgnoreCase("") && ((this.folioNo.length() != 12) && (this.folioNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            errorMsg = "Please enter valid folio no";
            return false;
        } else if (date == null) {
            errorMsg = "Please Enter Date";
            return false;
        }
        return true;
    }
    
    public void folioToMethod() {
        setErrorMsg("");
        if (folioNoShow == null || folioNoShow.equalsIgnoreCase("")) {
            setErrorMsg("Please fill Folio Number");
            return;
        }
        //if (folioNoShow.length() < 12) {
        if (!this.folioNoShow.equalsIgnoreCase("") && ((this.folioNoShow.length() != 12) && (this.folioNoShow.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setErrorMsg("Please enter 12 digit Folio Number");
            return;
        }
        try {
            folioNo = ftsPostRemote.getNewAccountNumber(folioNoShow);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
}
