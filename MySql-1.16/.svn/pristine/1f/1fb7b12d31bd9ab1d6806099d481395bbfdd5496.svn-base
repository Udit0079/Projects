/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareAccountStatementPojo;
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
public class ShareAcctStmt extends BaseBean {

    private String errorMsg;
    private String foliono, folionoShow, acNoLen;
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private Date frDt, toDt;
    FtsPostingMgmtFacadeRemote ftsPostRemote;

    /**
     * Creates a new instance of ShareAcctStmt
     */
    public ShareAcctStmt() {
        try {
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
        } catch (Exception e) {
        }
    }

    public String getFolionoShow() {
        return folionoShow;
    }

    public void setFolionoShow(String folionoShow) {
        this.folionoShow = folionoShow;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFoliono() {
        return foliono;
    }

    public void setFoliono(String foliono) {
        this.foliono = foliono;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public String viewReport() {
        try {
            if (validate()) {
                List<ShareAccountStatementPojo> resultList = horfr.getAccountStatement(foliono, ymd.format(frDt), ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pStatementPeriod", dmy.format(frDt) + " to " + dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Share Account Statement Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().jasperReport("ShareAcctStmtReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Share Account Statement Report");
                    return "report";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String pdfDownLoad() {
        try {
            if (validate()) {
                List<ShareAccountStatementPojo> resultList = horfr.getAccountStatement(foliono, ymd.format(frDt), ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pStatementPeriod", dmy.format(frDt) + " to " + dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Share Account Statement Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().openPdf("Share Account Statement Report", "ShareAcctStmtReport", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Share Account Statement Report");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validate() {
        if (foliono == null || foliono.equalsIgnoreCase("")) {
            errorMsg = "Please enter valid folio no";
            return false;
        } if (!this.foliono.equalsIgnoreCase("") && ((this.foliono.length() != 12) && (this.foliono.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            errorMsg = "Please enter valid folio no";
            return false;
        } else if (frDt == null) {
            errorMsg = "Please enter valid from date";
            return false;
        } else if (toDt == null) {
            errorMsg = "Please enter valid to date";
            return false;
        } else if (frDt.after(toDt)) {
            errorMsg = "Please enter from date should be less than to to date";
            return false;
        }
        return true;
    }

    public void folioToMethod() {
        setErrorMsg("");
        if (folionoShow == null || folionoShow.equalsIgnoreCase("")) {
            setErrorMsg("Please fill Folio No");
            return;
        }
        //if (folionoShow.length() < 12) {
        if (!this.folionoShow.equalsIgnoreCase("") && ((this.folionoShow.length() != 12) && (this.folionoShow.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setErrorMsg("Please enter Proper Folio No");
            return;
        }
        try {
            foliono = ftsPostRemote.getNewAccountNumber(folionoShow);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setErrorMsg("");
            setFoliono("");
            setFolionoShow("");
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
}
