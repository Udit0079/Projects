/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanAccStmPojo;
import com.cbs.dto.report.LoanNpaAccStmPojo;
import com.cbs.dto.report.NpaAccStmPojo;
import com.cbs.dto.report.OverdueRemainderPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.npa.NPAFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.AmtToWords;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class LoanAcStatement extends BaseBean {

    private String currentDate;
    private String loggedInUser;
    private String errorMsg;
    private String acNo, acNoLen;
    private String frmDt;
    private String toDt;
    private String showPeriod;
    private int isSeqMode = 0;
    private String seqFileDisplay;
    private boolean isbuttonDisb;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    private final String jndiHomeNameCommonReportMethods = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private InterBranchTxnFacadeRemote interBranchFacade;
    private NPAFacadeRemote npaRemote = null;
    private String newAcno;
    private String acnoName;
    private BankProcessManagementFacadeRemote bnkpRemote;
    private List<OverdueRemainderPojo> repDataList = new ArrayList<OverdueRemainderPojo>();
    private List<OverdueRemainderPojo> gridDetail;
    private OverdueRemainderPojo currentItem;
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private LoanInterestCalculationFacadeRemote loanIntCalFacade;

    /**
     * Creates a new instance of LoanAcStatement
     */
    public LoanAcStatement() {
        try {
            this.setCurrentDate(getTodayDate());
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setLoggedInUser(getUserName());
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommonReportMethods);
            interBranchFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
            bnkpRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup("BankProcessManagementFacade");
            npaRemote = (NPAFacadeRemote) ServiceLocator.getInstance().lookup("NPAFacade");
            loanIntCalFacade = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            this.setAcNoLen(getAcNoLength());

            isSeqMode = ftsPostRemote.getCodeForReportName("IS-OVERDUE-REMAINDER");
            if (isSeqMode != 1) {
                this.isbuttonDisb = true;
                this.seqFileDisplay = "none";
            }
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

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public List<OverdueRemainderPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<OverdueRemainderPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public OverdueRemainderPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(OverdueRemainderPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isIsbuttonDisb() {
        return isbuttonDisb;
    }

    public void setIsbuttonDisb(boolean isbuttonDisb) {
        this.isbuttonDisb = isbuttonDisb;
    }

    public String getSeqFileDisplay() {
        return seqFileDisplay;
    }

    public void setSeqFileDisplay(String seqFileDisplay) {
        this.seqFileDisplay = seqFileDisplay;
    }

    public int getIsSeqMode() {
        return isSeqMode;
    }

    public void setIsSeqMode(int isSeqMode) {
        this.isSeqMode = isSeqMode;
    }

    public String getAcnoName() {
        return acnoName;
    }

    public void setAcnoName(String acnoName) {
        this.acnoName = acnoName;
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
    public void overdueRemainder() {
        gridDetail = new ArrayList<OverdueRemainderPojo>();
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
            String ret = getNewAccNo();
            if (ret.equalsIgnoreCase("false")) {
                return;
            }
            String fromDt = frmDt.substring(6) + frmDt.substring(3, 5) + frmDt.substring(0, 2);
            String toDate = toDt.substring(6) + toDt.substring(3, 5) + toDt.substring(0, 2);
            if (ymd.parse(fromDt).after(ymd.parse(toDate))) {
                errorMsg = "Please from date should be less than to date";
                return;
            }

            List<OverdueRemainderPojo> resultList = npaRemote.getoverdueRemainderData(newAcno, toDate, getOrgnBrCode(), getTodayDate(), getUserName());
            if (resultList.isEmpty()) {
                this.setErrorMsg("Data does not exist");
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    OverdueRemainderPojo tablr = new OverdueRemainderPojo();
                    tablr.setAcNo(resultList.get(i).getAcNo());
                    tablr.setAddress(resultList.get(i).getAddress());
                    tablr.setAsOnDt(resultList.get(i).getAsOnDt());
                    tablr.setCustName(resultList.get(i).getCustName());
                    tablr.setLoanAmt(resultList.get(i).getLoanAmt());
                    tablr.setOutStandingBalance(resultList.get(i).getOutStandingBalance());
                    tablr.setOverdueEmi(resultList.get(i).getOverdueEmi());
                    tablr.setOverdueIntrest(resultList.get(i).getOverdueIntrest());
                    tablr.setPrincipleAmt(resultList.get(i).getPrincipleAmt());
                    tablr.setSanctionDT(dmy.format(ymd.parse(resultList.get(i).getSanctionDT())));
                    tablr.setTotalDue(resultList.get(i).getTotalDue());
                    tablr.setRefno(resultList.get(i).getRefno());

                    gridDetail.add(tablr);
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void printOverdueRemainder() {
        String report = "FINAL REMAINDER";
        try {
            OverdueRemainderPojo pojo = new OverdueRemainderPojo();
            pojo.setAcNo(currentItem.getAcNo());
            pojo.setAddress(currentItem.getAddress());
            pojo.setAsOnDt(dmy.format(ymd.parse(currentItem.getAsOnDt())));
            pojo.setCustName(currentItem.getCustName());
            pojo.setLoanAmt(currentItem.getLoanAmt());
            pojo.setOutStandingBalance(currentItem.getOutStandingBalance());
            pojo.setOverdueEmi(currentItem.getOverdueEmi());
            pojo.setOverdueIntrest(currentItem.getOverdueIntrest());
            pojo.setPrincipleAmt(currentItem.getPrincipleAmt());
            pojo.setRefno(currentItem.getRefno());
            pojo.setSanctionDT(currentItem.getSanctionDT());
            pojo.setTotalDue(currentItem.getTotalDue());

            repDataList.add(pojo);

            String fileNo = (currentItem.getRefno()).substring(18, 19);

            String insertionrefNo = npaRemote.insertionRefNo(fileNo, getTodayDate(), currentItem.getRefno(), getUserName(), getOrgnBrCode());
            if (!insertionrefNo.equalsIgnoreCase("true")) {
                this.setErrorMsg("Problem in RefNo insertion.");
            }

            String amtInWords = new AmtToWords().calculate(Double.valueOf(currentItem.getOutStandingBalance().toString()));
            String amtInWordsTotal = new AmtToWords().calculate(Double.valueOf(currentItem.getTotalDue().toString()));
            Map fillParams = new HashMap();
            String acNature = ftsPostRemote.getAccountNature(newAcno);
            fillParams.put("pPrintedDate", dmy.format(new Date()));
            fillParams.put("pReportName", report);
            fillParams.put("pAsOnDate", toDt);
            fillParams.put("pAmtInWords", amtInWords);
            fillParams.put("pTotalDueInWords", amtInWordsTotal);

            if (!repDataList.isEmpty()) {
                new ReportBean().openPdf("OVERDUE FINAL REMAINDER_" + ymd.format(dmy.parse(getTodayDate())), "overDueRemainderReport", new JRBeanCollectionDataSource(repDataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                this.setErrorMsg("There is no data for this account number.");
            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
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

            if (ymd.parse(toDate).after(new Date())) {
                errorMsg = "Please to date should be less than current date";
                return "";
            }

            //List npaStatusList = new ArrayList();
            String npaStatus = "";
            Map fillParams = new HashMap();
            String acNature = ftsPostRemote.getAccountNature(newAcno);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                report = "RD ACCOUNT STATEMENT";
                this.setShowPeriod("Y");
            } else {
                report = "LOAN ACCOUNT STATEMENT";
                this.setShowPeriod("Y");
                List npaStatusList = loanRemote.getNpaStatus(newAcno);
                if (!npaStatusList.isEmpty()) {
                    for (int i = 0; i < npaStatusList.size(); i++) {
                        Vector vec = (Vector) npaStatusList.get(i);
                        if (vec.get(0).toString().equalsIgnoreCase("11") || vec.get(0).toString().equalsIgnoreCase("12") || vec.get(0).toString().equalsIgnoreCase("13")) {
                            npaStatus = "SUB STANDARD";
                        } else {
                            npaStatus = "STANDARD";
                        }
                    }
                } else {
                    npaStatus = "STANDARD";
                }
            }
            double opBal = 0, npaOpBal = 0;
            List<LoanAccStmPojo> resultList = loanRemote.loanAccountStatement(newAcno, fromDt, toDate);
            if (!resultList.isEmpty()) {
                LoanAccStmPojo getEle = resultList.get(resultList.size() - 1);
                opBal = getEle.getOpenBal().doubleValue();
                List natureSecurityValueList = commonRemote.getNatureSecurityValueByAcno(newAcno, toDate);
                String security_desc = "", secAmt = "";
                if (!natureSecurityValueList.isEmpty()) {
                    Vector vtr = (Vector) natureSecurityValueList.get(0);
                    security_desc = vtr.get(1).toString();
                    secAmt = vtr.get(2).toString();
                    fillParams.put("pNature", security_desc);
                    fillParams.put("pSecurityValue", secAmt);
                }
                //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcno)).split("!");
                fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                fillParams.put("pStmtPeriod", frmDt + " to " + toDt);
                fillParams.put("pPrintedDate", ymd.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pShowPeriod", this.getShowPeriod());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcno.substring(0, 2))));
                fillParams.put("image", "/opt/images/logo.png");
                List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                Vector vtr = (Vector) brinfoList.get(0);
                fillParams.put("pBranchName", vtr.get(0).toString());
                fillParams.put("pBranchEmail", vtr.get(1).toString());
                fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcno.substring(0, 2))));
                String bankOrSoc = "";
                Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                if (bnkOrSoc == 1) {
                    bankOrSoc = "Society";
                } else {
                    bankOrSoc = "Bank";
                }
                fillParams.put("pBankOrSociety", bankOrSoc);
            }
            if (npaStatus.equalsIgnoreCase("STANDARD") || npaStatus.equalsIgnoreCase("OPERATIVE")) {
                if (!resultList.isEmpty()) {
                    new ReportBean().jasperReport("LoanAccountStatement", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    return "report";
                } else {
                    errorMsg = "Data does not exist";
                }
            } else {
                if (!resultList.isEmpty()) {
                    //Vector vector = (Vector) npaStatusList.get(0);
                    List<NpaAccStmPojo> npaList = loanRemote.npaAccountStatement(newAcno, fromDt, toDate);
                    if (!npaList.isEmpty()) {
                        NpaAccStmPojo getEle = npaList.get(npaList.size() - 1);
                        npaOpBal = getEle.getNpaOpenBal().doubleValue();
                    }
                    /*Get the current status of that account and 
                     * made that new function which provide the full account status*/
                    npaStatus = interBranchFacade.fnGetLoanStatusBetweenDate(newAcno, fromDt, toDate);
                    if (npaStatus.equalsIgnoreCase("DOU")) {
                        npaStatus = "DOUBTFUL";
                    } else if (npaStatus.equalsIgnoreCase("SUB")) {
                        npaStatus = "SUB-STANDARD";
                    } else if (npaStatus.equalsIgnoreCase("LOS")) {
                        npaStatus = "LOSS";
                    } else if (npaStatus.equalsIgnoreCase("INO")) {
                        npaStatus = "INOPERATIVE";
                    } else if (npaStatus.equalsIgnoreCase("SUI")) {
                        npaStatus = "SUIT FIELDS";
                    } else if (npaStatus.equalsIgnoreCase("DEC")) {
                        npaStatus = "DECREED";
                    } else if (npaStatus.equalsIgnoreCase("WIT")) {
                        npaStatus = "WITHDRAWAL STOPPED";
                    } else if (npaStatus.equalsIgnoreCase("PRO")) {
                        npaStatus = "PROTESTED";
                    } else if (npaStatus.equalsIgnoreCase("OPE") || npaStatus.equalsIgnoreCase("STANDARD")) {
                        npaStatus = "STANDARD/OPERATIVE";
                    } else {
                        npaStatus = "STANDARD/OPERATIVE";
                    }
                    fillParams.put("assClass", npaStatus);

                    List<LoanNpaAccStmPojo> finalList = getFinalList(resultList, npaList);
                    double tDr = 0, tCr = 0;
                    for (int i = 0; i < finalList.size(); i++) {
                        tDr = tDr + finalList.get(i).getWithDrawl().doubleValue();
                        tCr = tCr + finalList.get(i).getDeposit().doubleValue();
                    }
                    if (opBal < 0 && npaOpBal < 0) {
                        tDr = tDr + Math.abs(opBal) + Math.abs(npaOpBal);
                    } else {
                        tCr = tCr + opBal + npaOpBal;
                    }
                    double totaOsBal = tDr - tCr;
                    fillParams.put("pTotalOsBal", new BigDecimal(totaOsBal));

                    new ReportBean().jasperReport("LoanNpaAccountStatement", "text/html", new JRBeanCollectionDataSource(finalList), fillParams, report);
                    getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    errorMsg = "Data does not exist";
                }
            }
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
            pojo.setAcNature(loanPojo.getAcNature());
            pojo.setPeriod(loanPojo.getPeriod());
            pojo.setOpeningDt(loanPojo.getOpeningDt());
            pojo.setClosingDate(loanPojo.getClosingDate());

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
            pojo.setAcNature(loanPojo.getAcNature());
            pojo.setOpeningDt(loanPojo.getOpeningDt());
            pojo.setClosingDate(loanPojo.getClosingDate());

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
        pojo.setAcNature(loanPojo.getAcNature());

        return pojo;
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

            if (ymd.parse(toDate).after(new Date())) {
                errorMsg = "Please to date should be less than current date";
                return;
            }

            //List npaStatusList = new ArrayList();
            String npaStatus = "";
            Map fillParams = new HashMap();
            String acNature = ftsPostRemote.getAccountNature(newAcno);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                report = "RD ACCOUNT STATEMENT";
                this.setShowPeriod("Y");
            } else {
                report = "LOAN ACCOUNT STATEMENT";
                this.setShowPeriod("Y");
                List npaStatusList = loanRemote.getNpaStatus(newAcno);
                if (!npaStatusList.isEmpty()) {
                    for (int i = 0; i < npaStatusList.size(); i++) {
                        Vector vec = (Vector) npaStatusList.get(i);
                        if (vec.get(0).toString().equalsIgnoreCase("11") || vec.get(0).toString().equalsIgnoreCase("12") || vec.get(0).toString().equalsIgnoreCase("13")) {
                            npaStatus = "SUB STANDARD";
                        } else {
                            npaStatus = "STANDARD";
                        }
                    }
                } else {
                    npaStatus = "STANDARD";
                }
                //npaStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(newAcno, toDate);
            }
            int smtFlag = ftsPostRemote.getCodeForReportName("LOAN-STMT-PRIN-INT");
            List<LoanAccStmPojo> resultList = new ArrayList<>();

            if (smtFlag == 1) {
                resultList = loanRemote.loanAccountStatementBifurficationPrinInt(newAcno, fromDt, toDate, getOrgnBrCode());
            } else {
                resultList = loanRemote.loanAccountStatement(newAcno, fromDt, toDate);
            }
            double opBal = 0, npaOpBal = 0;
            if (!resultList.isEmpty()) {
                LoanAccStmPojo getEle = resultList.get(resultList.size() - 1);
                opBal = getEle.getOpenBal() == null ? 0 : getEle.getOpenBal().doubleValue();
                //opBal = getEle.getOpenBal().doubleValue();
                List natureSecurityValueList = commonRemote.getNatureSecurityValueByAcno(newAcno, toDate);
                String security_desc = "", secAmt = "";
                if (!natureSecurityValueList.isEmpty()) {
                    Vector vtr = (Vector) natureSecurityValueList.get(0);
                    security_desc = vtr.get(1).toString();
                    secAmt = vtr.get(2).toString();
                    fillParams.put("pNature", security_desc);
                    fillParams.put("pSecurityValue", secAmt);
                }
                String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcno)).split("!");
                fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                fillParams.put("pStmtPeriod", frmDt + " to " + toDt);
                fillParams.put("pPrintedDate", ymd.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pShowPeriod", this.getShowPeriod());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcno.substring(0, 2))));
                fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcno.substring(0, 2))));
                fillParams.put("image", "/opt/images/logo.png");
                List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                Vector vtr = (Vector) brinfoList.get(0);
                fillParams.put("pBranchName", vtr.get(0).toString());
                fillParams.put("pBranchEmail", vtr.get(1).toString());
                String bankOrSoc = "";
                Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("SOCIETY");
                if (bnkOrSoc == 1) {
                    bankOrSoc = "Society";
                } else {
                    bankOrSoc = "Bank";
                }
                fillParams.put("pBankOrSociety", bankOrSoc);
            }

            if (smtFlag == 1) {
                report = "";
                fillParams.put("pAcno", acNo);
                fillParams.put("pCustName", ftsPostRemote.getCustName(acNo));
                fillParams.put("pPrintedDate", getTodayDate());
                List list = loanRemote.getLoanStmaHeader(acNo);

                Vector vtr = (Vector) list.get(0);
                String acno = vtr.get(0).toString();
                String custname = vtr.get(1).toString();
                String acctdesc = vtr.get(2).toString();
                String jtname1 = vtr.get(3).toString();
                String nomination = vtr.get(4).toString();
                String craddress = vtr.get(5).toString();
                String praddress = vtr.get(6).toString();
                String jtname2 = vtr.get(7).toString();
                String jtname3 = vtr.get(8).toString();
                String jtname4 = vtr.get(9).toString();
                String OpeningDt = vtr.get(10).toString();
                String ODLIMIT = vtr.get(11).toString();
                String sanctionlimitdt = vtr.get(12).toString();
                String LOAN_PD_MONTH = vtr.get(13).toString();
                double rdRoi = Double.parseDouble(loanIntCalFacade.getRoiLoanAccount(0, toDate, acno));
                String presentStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, toDate);
                fillParams.put("pPostalAdd", craddress);
                fillParams.put("pPermAdd", praddress);
                fillParams.put("pRoi", rdRoi);
                fillParams.put("pSansDt", sanctionlimitdt);
                fillParams.put("pAssetClassifi", presentStatus);
                fillParams.put("pPeriod", Integer.parseInt(LOAN_PD_MONTH));
                fillParams.put("pAcType", acctdesc);
                fillParams.put("pjtName", jtname1);
                fillParams.put("pNominee", nomination);
                fillParams.put("pSansAmt", Double.parseDouble(ODLIMIT));
                fillParams.put("pInstall", 0.0);
                fillParams.put("pOpeningDt", dmy.format(ymd.parse(OpeningDt)));

                new ReportBean().downloadPdf(newAcno + fromDt + toDate, "Loan_Acc_Statement", new JRBeanCollectionDataSource(resultList), fillParams);
            } else {
                if (npaStatus.equalsIgnoreCase("STANDARD") || npaStatus.equalsIgnoreCase("OPERATIVE")) {
                    if (!resultList.isEmpty()) {
                        new ReportBean().downloadPdf(newAcno + fromDt + toDate, "LoanAccountStatement", new JRBeanCollectionDataSource(resultList), fillParams);
                    } else {
                        errorMsg = "Data does not exist";
                    }
                } else {
                    if (!resultList.isEmpty()) {
                        // Vector vector = (Vector) npaStatusList.get(0);
                        List<NpaAccStmPojo> npaList = loanRemote.npaAccountStatement(newAcno, fromDt, toDate);
                        //String npaStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(newAcno, toDate);
                        if (!npaList.isEmpty()) {
                            NpaAccStmPojo getEle = npaList.get(npaList.size() - 1);
                            npaOpBal = getEle.getNpaOpenBal() == null ? 0 : getEle.getNpaOpenBal().doubleValue();
                        }

                        // npaOpBal = getEle.getNpaOpenBal().doubleValue();
                        npaStatus = interBranchFacade.fnGetLoanStatusBetweenDate(newAcno, fromDt, toDate);
                        if (npaStatus.equalsIgnoreCase("DOU")) {
                            npaStatus = "DOUBTFUL";
                        } else if (npaStatus.equalsIgnoreCase("SUB")) {
                            npaStatus = "SUB-STANDARD";
                        } else if (npaStatus.equalsIgnoreCase("LOS")) {
                            npaStatus = "LOSS";
                        } else if (npaStatus.equalsIgnoreCase("INO")) {
                            npaStatus = "INOPERATIVE";
                        } else if (npaStatus.equalsIgnoreCase("SUI")) {
                            npaStatus = "SUIT FIELDS";
                        } else if (npaStatus.equalsIgnoreCase("DEC")) {
                            npaStatus = "DECREED";
                        } else if (npaStatus.equalsIgnoreCase("WIT")) {
                            npaStatus = "WITHDRAWAL STOPPED";
                        } else if (npaStatus.equalsIgnoreCase("PRO")) {
                            npaStatus = "PROTESTED";
                        } else if (npaStatus.equalsIgnoreCase("OPE") || npaStatus.equalsIgnoreCase("STANDARD")) {
                            npaStatus = "STANDARD/OPERATIVE";
                        } else {
                            npaStatus = "STANDARD/OPERATIVE";
                        }

                        fillParams.put("assClass", npaStatus);
                        List<LoanNpaAccStmPojo> finalList = getFinalList(resultList, npaList);
                        double tDr = 0, tCr = 0;
                        for (int i = 0; i < finalList.size(); i++) {
                            tDr = tDr + finalList.get(i).getWithDrawl().doubleValue();
                            tCr = tCr + finalList.get(i).getDeposit().doubleValue();
                        }
                        if (opBal < 0 && npaOpBal < 0) {
                            tDr = tDr + Math.abs(opBal) + Math.abs(npaOpBal);
                        } else {
                            tCr = tCr + opBal + npaOpBal;
                        }
                        double totaOsBal = tDr - tCr;
                        fillParams.put("pTotalOsBal", new BigDecimal(totaOsBal));
                        new ReportBean().downloadPdf(newAcno + fromDt + toDate, "LoanNpaAccountStatement", new JRBeanCollectionDataSource(finalList), fillParams);
                    } else {
                        errorMsg = "Data does not exist";
                    }
                }
            }
        } catch (ApplicationException ex) {
            this.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public String downLoadExcel() throws ApplicationException {

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

            if (ymd.parse(toDate).after(new Date())) {
                errorMsg = "Please to date should be less than current date";
                return "";
            }

            //List npaStatusList = new ArrayList();
            String npaStatus = "";
            Map fillParams = new HashMap();
            String acNature = ftsPostRemote.getAccountNature(newAcno);
            if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                report = "RD ACCOUNT STATEMENT";
                this.setShowPeriod("Y");
            } else {
                report = "LOAN ACCOUNT STATEMENT";
                this.setShowPeriod("Y");
                List npaStatusList = loanRemote.getNpaStatus(newAcno);
                if (!npaStatusList.isEmpty()) {
                    for (int i = 0; i < npaStatusList.size(); i++) {
                        Vector vec = (Vector) npaStatusList.get(i);
                        if (vec.get(0).toString().equalsIgnoreCase("11") || vec.get(0).toString().equalsIgnoreCase("12") || vec.get(0).toString().equalsIgnoreCase("13")) {
                            npaStatus = "SUB STANDARD";
                        } else {
                            npaStatus = "STANDARD";
                        }
                    }
                } else {
                    npaStatus = "STANDARD";
                }
            }
            double opBal = 0, npaOpBal = 0;
            List<LoanAccStmPojo> resultList = loanRemote.loanAccountStatement(newAcno, fromDt, toDate);
            if (!resultList.isEmpty()) {
                LoanAccStmPojo getEle = resultList.get(resultList.size() - 1);
                opBal = getEle.getOpenBal().doubleValue();
                List natureSecurityValueList = commonRemote.getNatureSecurityValueByAcno(newAcno, toDate);
                String security_desc = "", secAmt = "";
                if (!natureSecurityValueList.isEmpty()) {
                    Vector vtr = (Vector) natureSecurityValueList.get(0);
                    security_desc = vtr.get(1).toString();
                    secAmt = vtr.get(2).toString();
                    fillParams.put("pNature", security_desc);
                    fillParams.put("pSecurityValue", secAmt);
                }
                //String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                String s[] = commonRemote.getBranchNameandAddressString(commonRemote.getBrncodeByAcno(newAcno)).split("!");
                fillParams.put("pIsNominee", ftsPostRemote.getCodeForReportName("NOMINEE-SHOW-STATEMENT").toString());
                fillParams.put("pStmtPeriod", frmDt + " to " + toDt);
                fillParams.put("pPrintedDate", ymd.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pShowPeriod", this.getShowPeriod());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pGstin", bnkpRemote.getBankGstinByBrnCode(Integer.parseInt(this.newAcno.substring(0, 2))));
                fillParams.put("image", "/opt/images/logo.png");
                List brinfoList = ftsPostRemote.getBranchNameEmail(getOrgnBrCode());
                Vector vtr = (Vector) brinfoList.get(0);
                fillParams.put("pBranchName", vtr.get(0).toString());
                fillParams.put("pBranchEmail", vtr.get(1).toString());
                fillParams.put("pIfscCode", bnkpRemote.getIfscCodeByBrnCode(Integer.parseInt(this.newAcno.substring(0, 2))));
                String bankOrSoc = "";
                Integer bnkOrSoc = ftsPostRemote.getCodeForReportName("Society");
                if (bnkOrSoc == 1) {
                    bankOrSoc = "Society";
                } else {
                    bankOrSoc = "Bank";
                }
                fillParams.put("pBankOrSociety", bankOrSoc);
            }
            if (npaStatus.equalsIgnoreCase("STANDARD") || npaStatus.equalsIgnoreCase("OPERATIVE")) {
                if (!resultList.isEmpty()) {
                    new ReportBean().dynamicExcelJasper("LoanAccountStatement", "excel", new JRBeanCollectionDataSource(resultList), fillParams, report);
                } else {
                    errorMsg = "Data does not exist";
                }
            } else {
                if (!resultList.isEmpty()) {
                    //Vector vector = (Vector) npaStatusList.get(0);
                    List<NpaAccStmPojo> npaList = loanRemote.npaAccountStatement(newAcno, fromDt, toDate);
                    if (!npaList.isEmpty()) {
                        NpaAccStmPojo getEle = npaList.get(npaList.size() - 1);
                        npaOpBal = getEle.getNpaOpenBal().doubleValue();
                    }
                    /*Get the current status of that account and 
                     * made that new function which provide the full account status*/
                    npaStatus = interBranchFacade.fnGetLoanStatusBetweenDate(newAcno, fromDt, toDate);
                    if (npaStatus.equalsIgnoreCase("DOU")) {
                        npaStatus = "DOUBTFUL";
                    } else if (npaStatus.equalsIgnoreCase("SUB")) {
                        npaStatus = "SUB-STANDARD";
                    } else if (npaStatus.equalsIgnoreCase("LOS")) {
                        npaStatus = "LOSS";
                    } else if (npaStatus.equalsIgnoreCase("INO")) {
                        npaStatus = "INOPERATIVE";
                    } else if (npaStatus.equalsIgnoreCase("SUI")) {
                        npaStatus = "SUIT FIELDS";
                    } else if (npaStatus.equalsIgnoreCase("DEC")) {
                        npaStatus = "DECREED";
                    } else if (npaStatus.equalsIgnoreCase("WIT")) {
                        npaStatus = "WITHDRAWAL STOPPED";
                    } else if (npaStatus.equalsIgnoreCase("PRO")) {
                        npaStatus = "PROTESTED";
                    } else if (npaStatus.equalsIgnoreCase("OPE") || npaStatus.equalsIgnoreCase("STANDARD")) {
                        npaStatus = "STANDARD/OPERATIVE";
                    } else {
                        npaStatus = "STANDARD/OPERATIVE";
                    }
                    fillParams.put("assClass", npaStatus);

                    List<LoanNpaAccStmPojo> finalList = getFinalList(resultList, npaList);
                    double tDr = 0, tCr = 0;
                    for (int i = 0; i < finalList.size(); i++) {
                        tDr = tDr + finalList.get(i).getWithDrawl().doubleValue();
                        tCr = tCr + finalList.get(i).getDeposit().doubleValue();
                    }
                    if (opBal < 0 && npaOpBal < 0) {
                        tDr = tDr + Math.abs(opBal) + Math.abs(npaOpBal);
                    } else {
                        tCr = tCr + opBal + npaOpBal;
                    }
                    double totaOsBal = tDr - tCr;
                    fillParams.put("pTotalOsBal", new BigDecimal(totaOsBal));
                    new ReportBean().dynamicExcelJasper("LoanNpaAccountStatement", "excel", new JRBeanCollectionDataSource(finalList), fillParams, report);
                } else {
                    errorMsg = "Data does not exist";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrorMsg(ex.getMessage());
        }
        return null;
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
            acnoName = newAcno + " [" + ftsPostRemote.getCustName(newAcno) + "]";
            String acctype = ftsPostRemote.getAccountNature(newAcno);
            if (!(acctype.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctype.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctype.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                setErrorMsg("Please fill only Advance account !");
                return "false";
            } else if (acctype.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List accnoTypeList = commonRemote.getCaAdvanceAccount(acctype, newAcno.substring(2, 4));
                if (accnoTypeList.isEmpty()) {
                    setErrorMsg("Please fill only Advance account !");
                    return "false";
                }
            }
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
        this.setAcnoName("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
        this.currentItem = null;
        this.gridDetail = null;
    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }
}
