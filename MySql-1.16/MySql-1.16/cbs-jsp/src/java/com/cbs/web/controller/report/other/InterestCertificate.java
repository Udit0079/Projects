package com.cbs.web.controller.report.other;

import com.cbs.dto.report.InterestCertificatePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class InterestCertificate extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    OtherReportFacadeRemote beanRemote;
    BankAndLoanMasterFacadeRemote loan;
    private String accountNo;
    private String yearend;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private String newAcno,acNoLen;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private CommonReportMethodsRemote common;

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
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
    
    public InterestCertificate() {
        try {
            loan = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setAcNoLen(getAcNoLength());
            List finYearDropDown = loan.finYearDropDownLoanInterestParameter();
            if (finYearDropDown.size() > 0) {
                for (int i = 0; i < finYearDropDown.size(); i++) {
                    Vector ele = (Vector) finYearDropDown.get(i);
                    yearend = ele.get(0).toString();
                }
            }
            String dt = 01 + "/" + 04 + "/" + yearend;
            int nYear = (Integer.parseInt(yearend.toString()) + 1);
            String nYear1 = Integer.toString(nYear);
            String dt1 = 31 + "/" + 03 + "/" + nYear1;
            setCalFromDate(dmyFormat.parse(dt));
            setCaltoDate(dmyFormat.parse(dt1));
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        try {
            setMessage("");
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (calFromDate.after(caltoDate)) {
                this.setMessage("From Date can not be greater than To Date.");
                return null;
            }
            
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return null;
            }
            
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                message = "Account no should Be Proper";
                return null;
            }            
            
            if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return null;

            }
            if (newAcno.length() < 12) {
                message = "Account no should Be 12 digit Long";
                return null;
            }
            String actype = newAcno.substring(2, 4);
            String acctNo = newAcno.substring(4, 10);
            String brncode = fts.getCurrentBrnCode(newAcno);
            String agCode = newAcno.substring(10, 12);
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List<InterestCertificatePojo> interestCertificate = beanRemote.interestCertificate(actype, acctNo, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), brncode, agCode);
            if (!interestCertificate.isEmpty()) {
                String amtInWords = new AmtToWords().calculate(interestCertificate.get(0).getSUMAMOUNT().doubleValue());
                String repName = "INTEREST CERTIFICATE";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmtInWords", amtInWords);
                fillParams.put("pReportDate", dmyFormat.format(calFromDate) + " to " + dmyFormat.format(caltoDate));
                fillParams.put("pAcctDesc", common.getAcctDecription(actype));
                new ReportBean().jasperReport("interestCertificate", "text/html", new JRBeanCollectionDataSource(interestCertificate), fillParams, repName);
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

    public void pdfDownLoad() {
        try {
            setMessage("");
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                this.setMessage("From Date can not be greater than To Date.");
                return;
            }
            
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return;
            }
            
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                message = "Account no should Be Proper";
                return;
            }
            
            if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return;

            }
            if (newAcno.length() < 12) {
                message = "Account no should Be 12 digit Long";
                return;
            }
            String actype = newAcno.substring(2, 4);
            String acctNo = newAcno.substring(4, 10);
            String brncode = fts.getCurrentBrnCode(newAcno);
            String agCode = newAcno.substring(10, 12);
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List<InterestCertificatePojo> interestCertificate = beanRemote.interestCertificate(actype, acctNo, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), brncode, agCode);
            if (!interestCertificate.isEmpty()) {
                String amtInWords = new AmtToWords().calculate(interestCertificate.get(0).getSUMAMOUNT().doubleValue());
                String repName = "INTEREST CERTIFICATE";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmtInWords", amtInWords);
                fillParams.put("pReportDate", dmyFormat.format(calFromDate) + " to " + dmyFormat.format(caltoDate));
                fillParams.put("pAcctDesc", common.getAcctDecription(actype));

                new ReportBean().openPdf("INTEREST CERTIFICATE_" + ymdFormat.format(calFromDate) + " to " + ymdFormat.format(caltoDate), "interestCertificate", new JRBeanCollectionDataSource(interestCertificate), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                setMessage("No data to print");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return;
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAccountNo("0");
            calFromDate = new Date();
            caltoDate = new Date();
            setNewAcno("");
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
                message = "Account no should Be Proper";
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
