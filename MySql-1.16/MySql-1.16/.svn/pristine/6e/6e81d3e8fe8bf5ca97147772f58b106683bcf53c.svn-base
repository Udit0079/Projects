/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanRepaymentPojo;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
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
public class LoanRepayMent extends BaseBean {
    
    private String errorMsg;
    private String acNo;
    private String oldacNo, acNoLen;
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    FtsPostingMgmtFacadeRemote fts;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     * Creates a new instance of LoanRepayMent
     */
    public LoanRepayMent() {
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setAcNoLen(getAcNoLength());
        } catch (Exception e) {
        }
        
    }
    
    public String getAcNo() {
        return acNo;
    }
    
    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }
    
    public String getOldacNo() {
        return oldacNo;
    }
    
    public void setOldacNo(String oldacNo) {
        this.oldacNo = oldacNo;
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
        
    public String viewReport() throws ApplicationException {
        if (validate()) {
            try {
                List<LoanRepaymentPojo> resultList = local.loanRepaymentSchedule(acNo, getOrgnBrCode());
                if (!resultList.isEmpty()) {
                    
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", "Loan Repayment Schedule");
                    fillParams.put("pReportDate", getTodayDate());
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    new ReportBean().jasperReport("EmiSchedule", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Loan Repayment Schedule");
                    return "report";
                    
                } else {
                    errorMsg = "There is no EMI Schedule for this a/c no ! ";
                    return null;
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
        return null;
    }
    
    public String pdfDownLoad() {
        if (validate()) {
            try {
                List<LoanRepaymentPojo> resultList = local.loanRepaymentSchedule(acNo, getOrgnBrCode());
                if (!resultList.isEmpty()) {
                    
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", "Loan Repayment Schedule");
                    fillParams.put("pReportDate", getTodayDate());
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    
                    new ReportBean().openPdf("Loan Repayment Schedule_" + ymd.format(dmy.parse(getTodayDate())), "EmiSchedule", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Loan Repayment Schedule");
                    
                } else {
                    errorMsg = "There is no EMI Schedule for this a/c no ! ";
                    return null;
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
        return null;
    }
    
    public boolean validate() {
        if (oldacNo.equalsIgnoreCase("")) {
            errorMsg = "Please enter the Account No";
            return false;
        //} else if (oldacNo.length() < 12) {
        } else if ((this.oldacNo.length() != 12) && (this.oldacNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
            errorMsg = "Please enter the valid Account No";
            return false;
        }
        return true;
    }
    
    public void getNewAcno() {
        Validator validate = new Validator();
        if (oldacNo.equalsIgnoreCase("")) {
            errorMsg = "Please enter the Account No";
            return;
        //} else if (oldacNo.length() < 12) {
        } else if ((this.oldacNo.length() != 12) && (this.oldacNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
            errorMsg = "Please enter the valid Account No";
            return;
        } else if (!validate.validateStringAllNoSpace(oldacNo)) {
            errorMsg = "Please enter the valid Account No";
            return;
        }
        try {
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            acNo = fts.getNewAccountNumber(oldacNo);
            String acctype = fts.getAccountNature(acNo);
            String[] accttype = {CbsConstant.TERM_LOAN, CbsConstant.DEMAND_LOAN, CbsConstant.CURRENT_AC};
            int flag = 0;
            for (int i = 0; i < accttype.length; i++) {
                if ((!accttype[i].equalsIgnoreCase(acctype))) {
                    flag = 1;
                } else {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                setErrorMsg("Please insert account no. of TL, DL or CA nature only");
                return;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        
    }
    
    public void refresh() {
        setErrorMsg("");
        setOldacNo("");
        setAcNo("");
    }
    
    public String exitAction() {
        refresh();
        return "case1";
        
    }
}
