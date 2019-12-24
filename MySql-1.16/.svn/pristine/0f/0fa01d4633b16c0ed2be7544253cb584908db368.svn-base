/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanIntCertPoJoAll;
import com.cbs.dto.report.LoanIntCertPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.AmtToWords;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class LoanIntCert extends BaseBean {

    private String errorMsg;
    private String acNo;
    private String oldacNo, acNoLen;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private LoanReportFacadeRemote local;
    FtsPostingMgmtFacadeRemote fts;
    Validator validator;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private String type;
    private List<SelectItem> typeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String schemeType;
    private List<SelectItem> schemeTypeList;
    private boolean flag1;
    private boolean flag2;
    private String invisible;
    private String invisible1;
    

    /**
     * Creates a new instance of LoanIntCert
     */
    public LoanIntCert() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            this.setAcNoLen(getAcNoLength());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("ALL", "ALL"));
            typeList.add(new SelectItem("Ind", "Individual"));
            setInvisible("");
            flag1 = true;
            flag2 = false;
            acTypeList = new ArrayList<>();
            initData();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    private void initData() {
        try {
            List tempList = local.getLoanTypeList();
            for (int i = 0; i < tempList.size(); i++) {
                Vector tempVector = (Vector) tempList.get(i);
                acTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public List<SelectItem> getSchemeTypeList() {
        return schemeTypeList;
    }

    public void setSchemeTypeList(List<SelectItem> schemeTypeList) {
        this.schemeTypeList = schemeTypeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getInvisible() {
        return invisible;
    }

    public void setInvisible(String invisible) {
        this.invisible = invisible;
    }

    public String getInvisible1() {
        return invisible1;
    }

    public void setInvisible1(String invisible1) {
        this.invisible1 = invisible1;
    }

    public String getOldacNo() {
        return oldacNo;
    }

    public void setOldacNo(String oldacNo) {
        this.oldacNo = oldacNo;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public void onBlurType() {
        acNatureList = new ArrayList<>();
        acTypeList = new ArrayList<>();
        if (type.equalsIgnoreCase("All")) {
            flag1 = false;
            flag2 = true;
            setInvisible("");
            setInvisible1("none");
            initData();
        } else {
            flag1 = true;
            flag2 = false;
            setInvisible("none");
            setInvisible1("");
            flag1 = true;
        }
    }

    public void blurAcType() {
        setErrorMsg("");
        if (schemeTypeList != null) {
            schemeTypeList.clear();
        }
        Vector vtr = null;
        try {
            List result = null;
            schemeTypeList = new ArrayList<SelectItem>();
            schemeTypeList.add(new SelectItem("ALL", "ALL"));
            if (!acType.equalsIgnoreCase("ALL")) {
                result = common.getAcctTypeSchemeWise(acType);
                for (int k = 0; k < result.size(); k++) {
                    Vector vect = (Vector) result.get(k);
                    schemeTypeList.add(new SelectItem(vect.get(0).toString(), vect.get(1).toString()));
                }
            }
            setInvisible1("none");
            flag2 = false;
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String viewReport() {
        if (validate()) {
            try {
                String brnCode = "";
                String acnature="";
                String accured ="";
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                List<LoanIntCertPojo> resultList = null;
                List<LoanIntCertPoJoAll> resultList1 = null;
                if (type.equalsIgnoreCase("All")) {
                    resultList1 = local.loanIntDetailsAll(acType, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), getOrgnBrCode(), schemeType);
                } else {
                    if (getOrgnBrCode().equalsIgnoreCase("HO") || getOrgnBrCode().equalsIgnoreCase("90")) {
                        brnCode = acNo.substring(0, 2);
                        resultList = local.cbsrepLoanIntCert(acNo, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), brnCode);
                    } else {
                        resultList = local.cbsrepLoanIntCert(acNo, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), getOrgnBrCode());
                    }
                }
                if (!type.equalsIgnoreCase("All")) {
                    if (!resultList.isEmpty()) {
                        double intAmt = resultList.get(0).getIntAmt().doubleValue();
                        double prinAmt = resultList.get(0).getPrinAmt().doubleValue();
                        String report = "LOAN INTEREST CERTIFICATE";
                          acnature=  fts.getAccountNature(acNo);
                        if(acnature.equalsIgnoreCase("TL") || acnature.equalsIgnoreCase("DL")){
                           accured="";     
                        }else{
                            accured="accured/";
                        }
                        Map fillParams = new HashMap();
                        fillParams.put("pAmtInWordsIntAmt", new AmtToWords().calculate(intAmt));
                        fillParams.put("pAmtInWordsPrinAmt", new AmtToWords().calculate(prinAmt));
                         fillParams.put("pAccured",accured);
                        
                        new ReportBean().jasperReport("InterestCertificateOnLoan", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                        return "report";
                    } else {
                        errorMsg = "No data exists";
                    }
                } else if (type.equalsIgnoreCase("ALL")) {
                    List bnkList = common.getBranchNameandAddress(getOrgnBrCode());
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    if (!resultList1.isEmpty()) {
                        String report1 = "LOAN INTEREST DETAILS ALL SCHEME WISE";
                        String report = "LOAN INTEREST CERTIFICATE";
                        Map fillParams = new HashMap();
                        String reportType = "";
                        if (acType.equalsIgnoreCase("ALL")) {
                            reportType = " FOR ACCOUNT TYPE ALL AND SCHEME TYPE ALL";
                        } else {
                            if (schemeType.equalsIgnoreCase("ALL")) {
                                reportType = " FOR ACCOUNT TYPE " + acType + " (" + common.getAcctDecription(acType) + ") AND SCHEME TYPE ALL";
                            } else {
                                reportType = " FOR ACCOUNT TYPE " + acType + " (" + common.getAcctDecription(acType) + ") AND SCHEME TYPE " + common.getSchemeDescAcTypeAndSchemeWise(acType, schemeType) + " ";
                            }
                        }
                        fillParams.put("preportType", reportType);
                        fillParams.put("pPrintedBy", this.getUserName());
                        fillParams.put("pReportName", report);
                        fillParams.put("pbankName", bankName);
                        fillParams.put("pbankAddress", bankAdd);
                        fillParams.put("pReportDate", "For the period " + frmDt + " to " + toDt + " ");
                        new ReportBean().jasperReport("Loan_Acccount_Details_Scheme_Wise", "text/html", new JRBeanCollectionDataSource(resultList1), fillParams, report1);
                        return "report";
                    } else {
                        errorMsg = "No Data Exist";
                    }
                }
            } catch (ApplicationException ex) {
                errorMsg = ex.getMessage();
            } catch (Exception e) {
                errorMsg = e.getMessage();
            }
        }
        return null;
    }

    public String pdfDownLoad() {
        if (validate()) {
            try {
                String brnCode = "";
                String acnature="";
                String accured ="";
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                List<LoanIntCertPojo> resultList = null;
                List<LoanIntCertPoJoAll> resultList1 = null;
                if (type.equalsIgnoreCase("All")) {
                    resultList1 = local.loanIntDetailsAll(acType, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), getOrgnBrCode(), schemeType);
                } else {
                    if (getOrgnBrCode().equalsIgnoreCase("HO") || getOrgnBrCode().equalsIgnoreCase("90")) {
                        brnCode = acNo.substring(0, 2);
                        resultList = local.cbsrepLoanIntCert(acNo, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), brnCode);
                    } else {
                        resultList = local.cbsrepLoanIntCert(acNo, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), getOrgnBrCode());
                    }
                }
                if (!type.equalsIgnoreCase("All")) {
                    if (!resultList.isEmpty()) {
                        double intAmt = resultList.get(0).getIntAmt().doubleValue();
                        double prinAmt = resultList.get(0).getPrinAmt().doubleValue();
                        String report = "LOAN INTEREST CERTIFICATE";
                        acnature=  fts.getAccountNature(acNo);
                        if(acnature.equalsIgnoreCase("TL") || acnature.equalsIgnoreCase("DL")){
                           accured="";     
                        }else{
                            accured="accured/";
                        }
                        Map fillParams = new HashMap();
                        fillParams.put("pAmtInWordsIntAmt", new AmtToWords().calculate(intAmt));
                        fillParams.put("pAmtInWordsPrinAmt", new AmtToWords().calculate(prinAmt));
                        fillParams.put("pAccured",accured);
                        new ReportBean().openPdf("LOAN INTEREST CERTIFICATE_" + (fromDt[2] + fromDt[1] + fromDt[0]) + " to " + (toDate[2] + toDate[1] + toDate[0]), "InterestCertificateOnLoan", new JRBeanCollectionDataSource(resultList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    } else {
                        errorMsg = "No data exists";
                    }
                } else if (type.equalsIgnoreCase("All")) {
                    List bnkList = common.getBranchNameandAddress(getOrgnBrCode());
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    if (!resultList1.isEmpty()) {
                        String report = "LOAN INTEREST DETAILS ALL SCHEME WISE";
                        Map fillParams = new HashMap();
                        String reportType = "";
                        if (acType.equalsIgnoreCase("ALL")) {
                            reportType = " FOR ACCOUNT TYPE ALL AND SCHEME TYPE ALL";
                        } else {
                            if (schemeType.equalsIgnoreCase("ALL")) {
                                reportType = " FOR ACCOUNT TYPE " + acType + " (" + common.getAcctDecription(acType) + ") AND SCHEME TYPE ALL";
                            } else {
                                reportType = " FOR ACCOUNT TYPE " + acType + " (" + common.getAcctDecription(acType) + ") AND SCHEME TYPE " + common.getSchemeDescAcTypeAndSchemeWise(acType, schemeType) + " ";
                            }
                        }
                        fillParams.put("preportType", reportType);
                        fillParams.put("pPrintedBy", this.getUserName());
                        fillParams.put("pReportName", report);
                        fillParams.put("pbankName", bankName);
                        fillParams.put("pbankAddress", bankAdd);
                        fillParams.put("pReportDate", "For the period " + frmDt + " to " + toDt + " ");
                        new ReportBean().openPdf("LOAN INTEREST CERTIFICATE_" + (fromDt[2] + fromDt[1] + fromDt[0]) + " to " + (toDate[2] + toDate[1] + toDate[0]), "Loan_Acccount_Details_Scheme_Wise", new JRBeanCollectionDataSource(resultList1), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", "LOAN INTEREST CERTIFICATE");
                    } else {
                        errorMsg = "No Data Exist";
                    }
                }
            } catch (ApplicationException ex) {
                errorMsg = ex.getMessage();
            } catch (Exception e) {
                errorMsg = e.getMessage();
            }
        }
        return null;
    }

    public boolean validate() {
        if (frmDt.equalsIgnoreCase("")) {
            errorMsg = "Please enter from date";
            return false;
        }
        if (toDt.equalsIgnoreCase("")) {
            errorMsg = "Please enter to date";
            return false;
        }
        if (!type.equalsIgnoreCase("All")) {
            if (oldacNo.equalsIgnoreCase("")) {
                errorMsg = "Please enter Account No";
                return false;
                //} else if (oldacNo.length() < 12) {
            } else if ((this.oldacNo.length() != 12) && (this.oldacNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                errorMsg = "Please enter the valid Account No";
                return false;
            } else if (!validator.validateStringAllNoSpace(oldacNo)) {
                errorMsg = "Please enter the valid Account No";
                return false;
            }
        } else if (type.equalsIgnoreCase("All")) {
            if (acType.equalsIgnoreCase("")) {
                errorMsg = "Please Select Account Type !!";
                return false;
            }
            if (schemeType.equalsIgnoreCase("")) {
                errorMsg = "Please Select Scheme Type !!!";
                return false;
            }
        }
        try {
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                errorMsg = "From date should be less then to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }

    public void getNewAcno() {
        validator = new Validator();
        if (oldacNo.equalsIgnoreCase("")) {
            errorMsg = "Please enter Account No";
            return;
            //} else if (oldacNo.length() < 12) {
        } else if ((this.oldacNo.length() != 12) && (this.oldacNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
            errorMsg = "Please enter the valid Account No";
            return;
        } else if (!validator.validateStringAllNoSpace(oldacNo)) {
            errorMsg = "Please enter the valid Account No";
            return;
        }
        try {
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            acNo = fts.getNewAccountNumber(oldacNo);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        oldacNo = "";
        acNo = "";
        errorMsg = "";
        frmDt = dmy.format(new Date());
        toDt = dmy.format(new Date());
        this.flag1 = true;
        this.flag2 = false;
        setInvisible("none");
        setInvisible1("none");
    }

    public String exitAction() {
        refresh();
        return "case1";
    }
}
