package com.cbs.web.controller.report.other;

/**
 *
 * @author Zeeshan Waris
 */
import com.cbs.web.utils.AmtToWords;
import com.cbs.dto.report.BalanceCertificatePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class BalanceCertificate extends BaseBean {

    private String message;
    Date calDate = new Date();
    OtherReportFacadeRemote beanRemote;
    BankAndLoanMasterFacadeRemote loan;
    private String accountNo;
    private String checkDate;
    private String newAcno,acNoLen;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
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
    
    public BalanceCertificate() {
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
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            if (calDate == null) {
                message = "Please Fill Till Date";
                return null;
            }
            
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return null;
            }
            
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                message = "Account No Should Be Proper";
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
            AccountNo();
            String dd3 = ymdFormat.format(calDate);
            if (checkDate != null) {
                if (ymdFormat.parse(dd3).before(ymdFormat.parse(checkDate))) {
                    message = "Till Date is Greater than Your Account Opening Date.Please Select Another Date";
                    return null;
                }
            }
            String brnCode = fts.getCurrentBrnCode(newAcno);
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List<BalanceCertificatePojo> balanceCert = beanRemote.balanceCertificate(newAcno, ymdFormat.format(calDate), brnCode);
            if (!balanceCert.isEmpty()) {
                String amtInWords = new AmtToWords().calculate(Double.parseDouble(balanceCert.get(0).getAMOUNT().toString()));
                String repName = "BALANCE CERTIFICATE";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmtInWords", amtInWords);
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                new ReportBean().jasperReport("balanceCertificate", "text/html", new JRBeanCollectionDataSource(balanceCert), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
                return null;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
            return null;
        }
    }

    public void pdfDownLoad() {
        try {
            setMessage("");
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            if (calDate == null) {
                message = "Please Fill Till Date";
                return;
            }
            
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return;
            }
            
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                message = "Account No Should Be Proper";
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
            AccountNo();
            String dd3 = ymdFormat.format(calDate);
            if (checkDate != null) {
                if (ymdFormat.parse(dd3).before(ymdFormat.parse(checkDate))) {
                    message = "Till Date is Greater than Your Account Opening Date.Please Select Another Date";
                    return;
                }
            }
            String brnCode = fts.getCurrentBrnCode(newAcno);
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            List<BalanceCertificatePojo> balanceCert = beanRemote.balanceCertificate(newAcno, ymdFormat.format(calDate), brnCode);
            if (!balanceCert.isEmpty()) {
                String amtInWords = new AmtToWords().calculate(Double.parseDouble(balanceCert.get(0).getAMOUNT().toString()));
                String repName = "BALANCE CERTIFICATE";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAmtInWords", amtInWords);
                fillParams.put("pReportDate", new SimpleDateFormat("dd/MM/yyyy").format(calDate));
                new ReportBean().openPdf("BALANCE CERTIFICATE", "balanceCertificate", new JRBeanCollectionDataSource(balanceCert), fillParams);
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
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
            return;
        }
    }

    public void AccountNo() {
        try {
            loan = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            List resultList = loan.AcctNatureLoanInterestParameter(newAcno);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    checkDate = ele.get(0).toString();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAccountNo("");
            calDate = new Date();
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
                message = "Account No Should Be Proper";
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
