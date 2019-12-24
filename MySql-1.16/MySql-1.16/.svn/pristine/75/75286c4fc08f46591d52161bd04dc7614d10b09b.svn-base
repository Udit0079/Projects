package com.cbs.web.controller.report.other;

import com.cbs.dto.report.CheqHonouredCertificate;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.AmtToWords;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class ChequeHonuredReport extends BaseBean {

    private String message;
    OtherReportFacadeRemote beanRemote;
    private String accountNo, chequeNo;
    private String newAcno,acNoLen;;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public ChequeHonuredReport() {
        try {
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setAcNoLen(getAcNoLength());
        } catch (ServiceLocatorException ex) {
            Logger.getLogger(BalanceCertificate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String printAction() {
        try {
            setMessage("");
            if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return null;
            }
            if (newAcno.length() < 12) {
                message = "Account no should Be 12 digit Long";
                return null;
            }
            if (chequeNo == null || chequeNo.equalsIgnoreCase("")) {
                message = "Please Fill The Cheque No";
                return null;
            }
            if (!chequeNo.matches("[0-9]*")) {
                message = "Please fill numeric value in  Cheque No";
                return null;
            }
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List<CheqHonouredCertificate> chequeHonouredCertificate = beanRemote.chequeHonouredCertificate(newAcno, Integer.parseInt(chequeNo));
            if (!chequeHonouredCertificate.isEmpty()) {
                double amount = chequeHonouredCertificate.get(0).getAmount();
                String amtInWords = new AmtToWords().calculate(amount < 0 ? (amount * (-1)) : amount);
                String repName = "Cheque Honoured Certificate";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pAmtToWords", amtInWords);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                new ReportBean().jasperReport("ChequeCert", "text/html", new JRBeanCollectionDataSource(chequeHonouredCertificate), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
                return null;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return null;
        }
    }

    public String pdfDownLoad() {
        try {
            setMessage("");
            if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return null;
            }
            if (newAcno.length() < 12) {
                message = "Account no should Be 12 digit Long";
                return null;
            }
            if (chequeNo == null || chequeNo.equalsIgnoreCase("")) {
                message = "Please Fill The Cheque No";
                return null;
            }
            if (!chequeNo.matches("[0-9]*")) {
                message = "Please fill numeric value in  Cheque No";
                return null;
            }
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List<CheqHonouredCertificate> chequeHonouredCertificate = beanRemote.chequeHonouredCertificate(newAcno, Integer.parseInt(chequeNo));

            if (!chequeHonouredCertificate.isEmpty()) {
                double amount = chequeHonouredCertificate.get(0).getAmount();
                String amtInWords = new AmtToWords().calculate(amount < 0 ? (amount * (-1)) : amount);
                String repName = "Cheque Honoured Certificate";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pAmtToWords", amtInWords);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                new ReportBean().openPdf("ChequeCert" + ymd.format(sdf.parse(getTodayDate())), "ChequeCert", new JRBeanCollectionDataSource(chequeHonouredCertificate), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
                return "report";
            } else {
                setMessage("No data to print");
                return null;
            }

        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return null;
        } catch (Exception e) {
            setMessage(e.getMessage());
            return null;
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAccountNo("");
            setNewAcno("");
            setChequeNo("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public void getNewAccountNo() {
        try {
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return;
            }
            //if (accountNo.length() < 12) {
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                message = "Please Fill Proper Account Number.";
                return;
            }
            newAcno = fts.getNewAccountNumber(accountNo);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
