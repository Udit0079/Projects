/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.ho.InterOfycPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.StmtOneToTenFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author SAMY
 */
public class InterOfycMemo extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String errorMsg;
    private String acNo, acNoLen;
    private String newAcno;
    private String frmDt;
    private String toDt;
    private String showPeriod;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    private final String jndiHomeNameCommonReportMethods = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private InterBranchTxnFacadeRemote interBranchFacade;
    private final String jndiHomeNameSTMTReport = "StmtOneToTenFacade";
    private StmtOneToTenFacadeRemote stmtRemote = null;

    public InterOfycMemo() {
        try {
            this.setCurrentDate(getTodayDate());
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setLoggedInUser(getUserName());
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommonReportMethods);
            stmtRemote = (StmtOneToTenFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameSTMTReport);
            interBranchFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
            this.setAcNoLen(getAcNoLength());
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }

    }

    public String viewReport() throws ApplicationException {
        String report = "";
        try {
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                errorMsg = "Please enter valid account no";
                return "";
            }

            if ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                errorMsg = "Please enter valid account no";
                return "";
            }

            if (newAcno == null || newAcno.equalsIgnoreCase("") || newAcno.length() < 12) {
                errorMsg = "Please enter valid account no";
                return "";
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter from date";
                return "";
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter to date";
                return "";
            }

            String ret = getNewAccNo();
            if (ret.equalsIgnoreCase("false")) {
                return "";
            }

            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);

            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less than to date";
                return "";
            }

            Map fillParams = new HashMap();
            report = "BRANCH REPORT ON NEW / ENHANCEMENT / RENEWAL OF OD LIMIT /TERM LOAN PROPOSAL OVER RS. 3.00 LACS";
            List brNameAndAddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("username", getUserName());
            fillParams.put("report", report);
            this.setShowPeriod("Y");

            List<InterOfycPojo> resultList = stmtRemote.getInterOfycMemoDetail(acNo, fromDt, toDate);
            List<InterOfycPojo> jrxmlList = new ArrayList<InterOfycPojo>();
            if (!resultList.isEmpty()) {
                for (InterOfycPojo businessPojo : resultList) {
                    InterOfycPojo jrxmlPojo = new InterOfycPojo();
                    jrxmlPojo.setPart1(resultList.get(0).getPart1());
//                    jrxmlPojo.setPart2(resultList.get(0).getPart2());
//                    jrxmlPojo.setPart3(resultList.get(0).getPart3());
                    jrxmlList.add(jrxmlPojo);
                }
            }
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            new ReportBean().jasperReport("interOfycCover", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrorMsg(ex.getMessage());
        }
        return null;
    }

    public void viewPdfReport() throws ApplicationException {
        String report = "";
        try {
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                errorMsg = "Please enter valid account no";
                return;
            }

            if ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                errorMsg = "Please enter valid account no";
                return;
            }

            if (newAcno == null || newAcno.equalsIgnoreCase("") || newAcno.length() < 12) {
                errorMsg = "Please enter valid account no";
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter from date";
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                errorMsg = "Please enter to date";
                return;
            }

            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);

            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less than to date";
                return;
            }
            Map fillParams = new HashMap();
            report = "BRANCH REPORT ON NEW / ENHANCEMENT / RENEWAL OF OD LIMIT /TERM LOAN PROPOSAL OVER RS. 3.00 LACS";
            List brNameAndAddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("username", getUserName());
            fillParams.put("report", report);
            this.setShowPeriod("Y");

            List<InterOfycPojo> resultList = stmtRemote.getInterOfycMemoDetail(acNo, fromDt, toDate);
            List<InterOfycPojo> jrxmlList = new ArrayList<InterOfycPojo>();
            if (!resultList.isEmpty()) {
                for (InterOfycPojo businessPojo : resultList) {
                    InterOfycPojo jrxmlPojo = new InterOfycPojo();
                    jrxmlPojo.setPart1(resultList.get(0).getPart1());
                    jrxmlPojo.setPart2(resultList.get(0).getPart2());
                    jrxmlPojo.setPart3(resultList.get(0).getPart3());
                    jrxmlList.add(jrxmlPojo);
                }
            }
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            new ReportBean().openPdf(newAcno +"_"+ fromDt +"_"+ toDate, "interOfycCover", new JRBeanCollectionDataSource(jrxmlList), fillParams);
        } catch (ApplicationException ex) {
            this.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public String getNewAccNo() {
        setErrorMsg("");
        try {
            Validator validate = new Validator();
            if (acNo.equalsIgnoreCase("")) {
                errorMsg = "Please enter the Account No";
                return "false";
                //} else if (acNo.length() < 12) {
            } else if ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                errorMsg = "Please enter the valid Account No";
                return "false";
            } else if (!validate.validateStringAllNoSpace(acNo)) {
                errorMsg = "Please enter the valid Account No";
                return "false";
            }
            newAcno = ftsPostRemote.getNewAccountNumber(this.acNo);
            String acctype = ftsPostRemote.getAccountNature(newAcno);
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
                return "false";
            }
        } catch (ApplicationException ex) {
            this.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());

        }
        return "true";
    }

    public void refreshPage() {
        this.setErrorMsg("");
        this.setAcNo("");
        this.setNewAcno("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getShowPeriod() {
        return showPeriod;
    }

    public void setShowPeriod(String showPeriod) {
        this.showPeriod = showPeriod;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
}
