/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.pojo.GuarantorDetailPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
 * @author Admin
 */
public class GuarantorDetail extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;
    private String guarantor;
    private List<SelectItem> guarantorList;
    private String lableButton;
    private String acNo;
    private String newAcNo;
    private boolean disableAcno;
    private String displayPanal;
    private String branch;
    private List<SelectItem> branchList;
    private String asOnDt;
    private String bankCode;
    private LoanReportFacadeRemote RemoteLocal;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private List<GuarantorDetailPojo> reportDataList = new ArrayList<GuarantorDetailPojo>();
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public GuarantorDetail() {
        try {
            setAsOnDt(dmyFormat.format(new Date()));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteLocal = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            List acTLst = new ArrayList();
            acTLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele1 = (Vector) acTLst.get(i);
                    branchList.add(new SelectItem(ele1.get(0).toString().length() < 2 ? "0" + ele1.get(0).toString() : ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            bankCode = ftsPostRemote.getBankCode();
//            if (bankCode.equalsIgnoreCase("army")) {
//                this.displayPanal = "";
//            } else {
//                this.displayPanal = "none";
//            }
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("Guarantor", "Guarantor"));
            repTypeList.add(new SelectItem("Account No.", "Account No."));

            guarantorList = new ArrayList<SelectItem>();
            this.lableButton = "Account No.";
            this.disableAcno = true;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void reportTypeAction() {
        setMessage("");
        if (repType == null || repType.equalsIgnoreCase("S")) {
            setMessage("Please select report type !");
            return;
        }
        guarantorList = new ArrayList<SelectItem>();
        if (repType.equalsIgnoreCase("Guarantor")) {
            guarantorList.add(new SelectItem("ALL", "ALL"));
            //guarantorList.add(new SelectItem("A/c No.", "A/c No."));
            guarantorList.add(new SelectItem("Folio No.", "Folio No."));
            guarantorList.add(new SelectItem("Cust Id.", "Cust Id."));
        } else {
            guarantorList.add(new SelectItem("ALL", "ALL"));
            guarantorList.add(new SelectItem("A/c No.", "A/c No."));
        }
    }

    public void guarOptAction() {
        setMessage("");

        if (guarantor.equalsIgnoreCase("A/c No.")) {
            this.lableButton = "Account No.";
            this.disableAcno = false;
        } else if (guarantor.equalsIgnoreCase("Folio No.")) {
            this.lableButton = "Folio No.";
            this.disableAcno = false;
        } else if (guarantor.equalsIgnoreCase("Cust Id.")) {
            this.lableButton = "Customer Id";
            this.disableAcno = false;
        } else if (guarantor.equalsIgnoreCase("ALL")) {
            this.lableButton = "Account No.";
            this.disableAcno = true;
        }
    }

    public void guarantorAction() {
        setMessage("");
        try {
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                setMessage("Please fill Account No. !");
                return;
            }
            if (acNo.length() == 12) {
                this.newAcNo = ftsPostRemote.getNewAccountNumber(acNo);
            } else {
                this.newAcNo = acNo;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String report = "Guarantor Detail Report";
        try {

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", asOnDt);
            fillParams.put("pPrintedBy", getUserName());

            reportDataList = RemoteLocal.getguarantorData(branch, repType, guarantor, acNo, ymdFormat.format(dmyFormat.parse(asOnDt)));
            if (reportDataList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (bankCode.equalsIgnoreCase("army")) {
                if (repType.equalsIgnoreCase("Guarantor")) {
                    new ReportBean().jasperReport("suretyCard", "text/html",
                            new JRBeanCollectionDataSource(reportDataList), fillParams, "Guarantor Detail Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    new ReportBean().jasperReport("GuarantorDetail", "text/html",
                            new JRBeanCollectionDataSource(reportDataList), fillParams, "Guarantor Detail Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                }
            } else {
                if (repType.equalsIgnoreCase("Guarantor")) {
                    new ReportBean().jasperReport("suretyCard", "text/html",
                            new JRBeanCollectionDataSource(reportDataList), fillParams, "Guarantor Detail Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    new ReportBean().jasperReport("GuarantorList", "text/html",
                            new JRBeanCollectionDataSource(reportDataList), fillParams, "Guarantor Detail Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        String report = "Guarantor Detail Report";
        try {

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", asOnDt);
            fillParams.put("pPrintedBy", getUserName());
            reportDataList = RemoteLocal.getguarantorData(branch, repType, guarantor, acNo, ymdFormat.format(dmyFormat.parse(asOnDt)));
            if (reportDataList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (bankCode.equalsIgnoreCase("army")) {
                if (repType.equalsIgnoreCase("Guarantor")) {
                    new ReportBean().openPdf("Guarantor Detail Report_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "suretyCard", new JRBeanCollectionDataSource(reportDataList), fillParams);
                } else {
                    new ReportBean().openPdf("Guarantor Detail Report_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "GuarantorDetail", new JRBeanCollectionDataSource(reportDataList), fillParams);
                }
            } else {
                if (repType.equalsIgnoreCase("Guarantor")) {
                    new ReportBean().openPdf("Guarantor Detail Report_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "suretyCard", new JRBeanCollectionDataSource(reportDataList), fillParams);
                } else {
                    new ReportBean().openPdf("Guarantor Detail Report_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "GuarantorList", new JRBeanCollectionDataSource(reportDataList), fillParams);
                }
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshPage() {
        setMessage("");
        setRepType("S");
        setGuarantor("ALL");
        setAcNo("");
        setNewAcNo("");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public List<SelectItem> getGuarantorList() {
        return guarantorList;
    }

    public void setGuarantorList(List<SelectItem> guarantorList) {
        this.guarantorList = guarantorList;
    }

    public String getLableButton() {
        return lableButton;
    }

    public void setLableButton(String lableButton) {
        this.lableButton = lableButton;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public boolean isDisableAcno() {
        return disableAcno;
    }

    public void setDisableAcno(boolean disableAcno) {
        this.disableAcno = disableAcno;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public String getDisplayPanal() {
        return displayPanal;
    }

    public void setDisplayPanal(String displayPanal) {
        this.displayPanal = displayPanal;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
