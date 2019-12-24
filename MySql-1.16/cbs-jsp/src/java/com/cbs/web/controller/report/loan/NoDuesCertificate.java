
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanAccStmPojo;
import com.cbs.dto.report.LoanNpaAccStmPojo;
import com.cbs.dto.report.NpaAccStmPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class NoDuesCertificate extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String errorMsg;
    private String acNo;
    private String frmDt;
    private String showPeriod;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    private final String jndiHomeNameCommonReportMethods = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private InterBranchTxnFacadeRemote interBranchFacade;
    private String newAcno;

    /** Creates a new instance of LoanAcStatement */
    public NoDuesCertificate() {
        try {
            this.setCurrentDate(getTodayDate());
            this.setFrmDt(getTodayDate());
            this.setLoggedInUser(getUserName());
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommonReportMethods);
            interBranchFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }

    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
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

    public String getShowPeriod() {
        return showPeriod;
    }

    public void setShowPeriod(String showPeriod) {
        this.showPeriod = showPeriod;
    }
//    public String validate(String acNo, String frmDt, String toDt) {
//        String message = "";
//        try {
//            if (acNo == null || acNo.equalsIgnoreCase("") || acNo.length() < 12) {
//                return message = "Please enter valid account no";
//            }
//            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
//                return message = "Please enter from date";
//            }
//            if (toDt == null || toDt.equalsIgnoreCase("")) {
//                return message = "Please enter to date";
//            }
//            if (ymd.parse(frmDt).after(ymd.parse(toDt))) {
//                return message = "Please from date should be less than to date";
//            }
//        } catch (Exception ex) {
//            this.setErrorMsg(ex.getMessage());
//        }
//        return message = "true";
//    }
    public String viewReport() throws ApplicationException {
        String report = "";
        try {
            if (acNo == null || acNo.equalsIgnoreCase("") || acNo.length() < 12) {
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
            
            String ret = getNewAccNo();
            if(ret.equalsIgnoreCase("false")){
                return "";
            }
            
            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            
            List npaStatusList = new ArrayList();
            Map fillParams = new HashMap();
            String acNature = ftsPostRemote.getAccountNature(newAcno);
            
            report = "NO DUES CERTIFICATE";
            this.setShowPeriod("N");

            List<LoanAccStmPojo> resultList = loanRemote.noDuesCertificate(newAcno, fromDt);
            if (!resultList.isEmpty()) {
                //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                if(resultList.get(0).getParticulars().equalsIgnoreCase("True")){
                    String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcno)).split("!");                
                    fillParams.put("pStmtPeriod", frmDt );
                    fillParams.put("pPrintedDate", ymd.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pShowPeriod", this.getShowPeriod());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    new ReportBean().jasperReport("NoDuesCertificate", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    return "report";
                } else {
                    errorMsg = resultList.get(0).getParticulars();
                }
            }
//            if (npaStatusList.isEmpty()) {
//                if (!resultList.isEmpty()) {
//                    new ReportBean().jasperReport("LoanAccountStatement", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
//                    return "report";
//                } else {
//                    errorMsg = "Data does not exist";
//                }
//            } else {
//                if (!resultList.isEmpty()) {
//                    Vector vector = (Vector) npaStatusList.get(0);
//                    List<NpaAccStmPojo> npaList = null ;
////                    List<NpaAccStmPojo> npaList = loanRemote.noDuesCertificate(newAcno, fromDt, toDate);
//                    /*Get the current status of that account and 
//                     * made that new function which provide the full account status*/
//                    String npaStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(newAcno, fromDt);
//                    fillParams.put("assClass", npaStatus);
//
//                    List<LoanNpaAccStmPojo> finalList = getFinalList(resultList, npaList);
//
//                    new ReportBean().jasperReport("LoanNpaAccountStatement", "text/html", new JRBeanCollectionDataSource(finalList), fillParams, report);
//                    getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//                } else {
//                    errorMsg = "Data does not exist";
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrorMsg(ex.getMessage());
        }
        return null;
    }

    public List<LoanNpaAccStmPojo> getFinalList(List<LoanAccStmPojo> resultList, List<NpaAccStmPojo> npaList) {
        List<LoanNpaAccStmPojo> finalList = new ArrayList<LoanNpaAccStmPojo>();

        for (int i = 0; i < resultList.size(); i++) {
            LoanNpaAccStmPojo pojo = new LoanNpaAccStmPojo();
            LoanAccStmPojo loanPojo = resultList.get(i);

            pojo.setAcNo(loanPojo.getAcNo());
            pojo.setCustName(loanPojo.getCustName());
            pojo.setAcType(loanPojo.getAcType());
            pojo.setJtName(loanPojo.getJtName());
            pojo.setNomination(loanPojo.getNomination());
            pojo.setCrAdd(loanPojo.getCrAdd());
            pojo.setPrAdd(loanPojo.getPrAdd());
            pojo.setParticulars(loanPojo.getParticulars());
            pojo.setChequeno(loanPojo.getChequeno());
            pojo.setType("G");
            pojo.setDate(loanPojo.getDate());
            pojo.setRdMatDate(loanPojo.getRdMatDate());
            pojo.setValueDt(loanPojo.getValueDt());
            pojo.setRoi(loanPojo.getRoi());
            pojo.setWithDrawl(loanPojo.getWithDrawl());
            pojo.setDeposit(loanPojo.getDeposit());
            pojo.setBalance(loanPojo.getBalance());
            pojo.setPendBal(loanPojo.getPendBal());
            pojo.setOpenBal(loanPojo.getOpenBal());
            pojo.setAmtSanc(loanPojo.getAmtSanc());
            pojo.setAmtInst(loanPojo.getAmtInst());
            pojo.setOdLimit(loanPojo.getOdLimit());
            pojo.setRdMatAmt(loanPojo.getRdMatAmt());

            finalList.add(pojo);
        }

        for (int i = 0; i < npaList.size(); i++) {
            LoanNpaAccStmPojo pojo = new LoanNpaAccStmPojo();
            NpaAccStmPojo npaPojo = npaList.get(i);
            
            LoanAccStmPojo loanPojo = resultList.get(0);
            
            pojo.setAcNo(loanPojo.getAcNo());
            pojo.setCustName(loanPojo.getCustName());
            pojo.setAcType(loanPojo.getAcType());
            pojo.setJtName(loanPojo.getJtName());
            pojo.setNomination(loanPojo.getNomination());
            pojo.setCrAdd(loanPojo.getCrAdd());
            pojo.setPrAdd(loanPojo.getPrAdd());
            pojo.setParticulars(npaPojo.getNpaParticulars());
            pojo.setChequeno(npaPojo.getNpaChequeno());
            pojo.setType("N");
            pojo.setDate(npaPojo.getNpaDate());
            pojo.setRdMatDate(loanPojo.getRdMatDate());
            pojo.setValueDt(npaPojo.getNpaValueDt());
            pojo.setRoi(loanPojo.getRoi());
            pojo.setWithDrawl(npaPojo.getNpaWithDrawl());
            pojo.setDeposit(npaPojo.getNpaDeposit());
            pojo.setBalance(npaPojo.getNpaBalance());
            pojo.setPendBal(loanPojo.getPendBal());
            pojo.setOpenBal(npaPojo.getNpaOpenBal());
            pojo.setAmtSanc(loanPojo.getAmtSanc());
            pojo.setAmtInst(loanPojo.getAmtInst());
            pojo.setOdLimit(loanPojo.getOdLimit());
            pojo.setRdMatAmt(loanPojo.getRdMatAmt());
            
            finalList.add(pojo);
        }

        return finalList;
    }

    public LoanNpaAccStmPojo createExtraPojo(List<LoanAccStmPojo> resultList, String details, BigDecimal one, BigDecimal two) {
        LoanNpaAccStmPojo pojo = new LoanNpaAccStmPojo();
        LoanAccStmPojo loanPojo = resultList.get(0);

        pojo.setAcNo(loanPojo.getAcNo());
        pojo.setCustName(loanPojo.getCustName());
        pojo.setAcType(loanPojo.getAcType());
        pojo.setJtName(loanPojo.getJtName());

        pojo.setNomination(loanPojo.getNomination());
        pojo.setCrAdd(loanPojo.getCrAdd());
        pojo.setPrAdd(loanPojo.getPrAdd());
        pojo.setParticulars(loanPojo.getParticulars());

        pojo.setChequeno(details);
        pojo.setType("G");
        //pojo.setDate("");
        pojo.setRdMatDate(loanPojo.getRdMatDate());

        pojo.setValueDt(loanPojo.getValueDt());
        pojo.setWithDrawl(one);
        pojo.setDeposit(two);
        pojo.setBalance(loanPojo.getBalance());

        pojo.setPendBal(loanPojo.getPendBal());
        pojo.setOpenBal(loanPojo.getOpenBal());
        pojo.setAmtSanc(loanPojo.getAmtSanc());
        pojo.setAmtInst(loanPojo.getAmtInst());
        pojo.setOdLimit(loanPojo.getOdLimit());
        pojo.setRdMatAmt(loanPojo.getRdMatAmt());

        return pojo;
    }

    public void viewPdfReport() throws ApplicationException {
        String report = "";
        try {
            if (acNo == null || acNo.equalsIgnoreCase("") || acNo.length() < 12) {
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
            
            String ret = getNewAccNo();
            if(ret.equalsIgnoreCase("false")){
                return ;
            }
            
            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            
            List npaStatusList = new ArrayList();
            Map fillParams = new HashMap();
            String acNature = ftsPostRemote.getAccountNature(newAcno);
            
            report = "NO DUES CERTIFICATE";
            this.setShowPeriod("N");

            List<LoanAccStmPojo> resultList = loanRemote.noDuesCertificate(newAcno, fromDt);
            if (!resultList.isEmpty()) {
                //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                if(resultList.get(0).getParticulars().equalsIgnoreCase("True")){                
                    String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcno)).split("!");                
                    fillParams.put("pStmtPeriod", frmDt );
                    fillParams.put("pPrintedDate", ymd.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pShowPeriod", this.getShowPeriod());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    new ReportBean().downloadPdf(newAcno + fromDt , "NoDuesCertificate", new JRBeanCollectionDataSource(resultList), fillParams);                    
                } else {
                    errorMsg = resultList.get(0).getParticulars();
                }            
            }
//            if (npaStatusList.isEmpty()) {
//                if (!resultList.isEmpty()) {
//                    new ReportBean().downloadPdf(newAcno + fromDt , "LoanAccountStatement", new JRBeanCollectionDataSource(resultList), fillParams);                    
//                } else {
//                    errorMsg = "Data does not exist";
//                }
//            } else {
//                if (!resultList.isEmpty()) {
//                    Vector vector = (Vector) npaStatusList.get(0);
//                    List<NpaAccStmPojo> npaList = null;
//                            //loanRemote.npaAccountStatement(newAcno, fromDt);
//                    fillParams.put("assClass", vector.get(0).toString());
//                    List<LoanNpaAccStmPojo> finalList = getFinalList(resultList, npaList);
//                    new ReportBean().downloadPdf(newAcno + fromDt , "LoanNpaAccountStatement", new JRBeanCollectionDataSource(finalList), fillParams);
//                } else {
//                    errorMsg = "Data does not exist";
//                }
//            }
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
            } else if (acNo.length() < 12) {
                errorMsg = "Please enter the valid Account No";
                return "false";
            } else if (!validate.validateStringAllNoSpace(acNo)) {
                errorMsg = "Please enter the valid Account No";
                return "false";
            }
            newAcno = ftsPostRemote.getNewAccountNumber(this.acNo);
            String acctype = ftsPostRemote.getAccountNature(newAcno);
            String[] accttype = {CbsConstant.TERM_LOAN, CbsConstant.DEMAND_LOAN, CbsConstant.CURRENT_AC, CbsConstant.RECURRING_AC};
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
                setErrorMsg("Please insert account no. of TL, DL, CA or RD nature only");
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
    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }
}
