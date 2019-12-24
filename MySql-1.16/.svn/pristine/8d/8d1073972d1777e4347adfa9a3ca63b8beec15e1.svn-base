/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.RdInstallmentPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class RdInstallment extends BaseBean {

    private String todayDate;
    private String errorMsg;
    private String option;
    private List<SelectItem> optionList;
    String accWise;
    String oldAccNo;
    private String toDt;
    Date dt = new Date();
    private String accFocus;
    private String accNo,acNoLen;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote facadeRemote;

    public String getAccWise() {
        return accWise;
    }

    public void setAccWise(String accWise) {
        this.accWise = accWise;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getAccFocus() {
        return accFocus;
    }

    public void setAccFocus(String accFocus) {
        this.accFocus = accFocus;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }    
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public RdInstallment() {
        try {
            setToDt(sdf.format(dt));

            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");

            optionList = new ArrayList<SelectItem>();
            optionList.add(new SelectItem("ALL", "ALL"));
            optionList.add(new SelectItem("AW", "A/C WISE"));

            this.setAcNoLen(getAcNoLength());
            this.setAccWise("true");
            this.setErrorMsg("");
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void setOptions() {
        try {
            if (this.option.equalsIgnoreCase("AW")) {
                this.setAccFocus("true");
                this.setAccWise("false");
            } else {
                this.setOldAccNo("");
                this.setAccFocus("false");
                this.setAccWise("true");
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void setAccount() {
        this.setAccNo("");
        try {
            if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo == null || this.oldAccNo.equalsIgnoreCase("null")) {
                this.setErrorMsg("Please Enter Account Number.");
                return;
            }
            //if (!this.oldAccNo.equalsIgnoreCase("") && this.oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMsg("Please Fill Proper Account Number.");
                return;
            }
            accNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
            String curBrnCode = ftsPostRemote.getCurrentBrnCode(accNo);
            if (!curBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                this.accNo = "";
                this.setErrorMsg("Account should be of self branch");
                return;
            }

            String acNature = ftsPostRemote.getAccountNature(accNo);
            if (!acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                this.accNo = "";
                this.setErrorMsg("Please Fill Only RD Account");
                return;
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void asOnDateAction() {
        this.setErrorMsg("");
        try {
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrorMsg("Please Fill Proper As On Date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
            if (result == false) {
                this.setErrorMsg("Please Fill Proper As On Date !");
                return;
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        this.setErrorMsg("");
        String bankName = "", bankAddress = "";

        Map fillParams = new HashMap();
        try {
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrorMsg("Please Fill Proper As On Date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
            if (result == false) {
                this.setErrorMsg("Please Fill Proper As On Date !");
                return;
            }

            if (this.option.equalsIgnoreCase("AW")) {
                if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo == null || this.oldAccNo.equalsIgnoreCase("null")) {
                    this.setErrorMsg("Please Enter Account Number.");
                    return;
                }
                
                if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMsg("Please Fill Proper Account Number.");
                    return;
                }
                
                accNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
                String curBrnCode = ftsPostRemote.getCurrentBrnCode(accNo);
                if (!curBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                    this.accNo = "";
                    this.setErrorMsg("Account should be of self branch");
                    return;
                }
                
                String acNature = ftsPostRemote.getAccountNature(accNo);
                if (!acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    this.accNo = "";
                    this.setErrorMsg("Please Fill Only RD Account");
                    return;
                }
            
                if (this.getAccNo().equalsIgnoreCase("")) {
                    this.setErrorMsg("Please Fill Account Number");
                    return;
                }
            }

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String report = "RD INSTALLMENT REPORT";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            fillParams.put("pPrintedDate", this.toDt);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);

            List<RdInstallmentPojo> rdInstallmentDetails = facadeRemote.getRdInstallmentDetails(this.option, this.accNo, ymd.format(sdf.parse(this.toDt)), this.getOrgnBrCode());
            new ReportBean().jasperReport("RdInstallmentReport", "text/html",
                    new JRBeanCollectionDataSource(rdInstallmentDetails), fillParams, "RD INSTALLMENT REPORT");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        this.setErrorMsg("");
        String bankName = "", bankAddress = "";

        Map fillParams = new HashMap();
        try {
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrorMsg("Please Fill Proper As On Date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.toDt);
            if (result == false) {
                this.setErrorMsg("Please Fill Proper As On Date !");
                return;
            }

            if (this.option.equalsIgnoreCase("AW")) {
                if (this.oldAccNo.equalsIgnoreCase("") || this.oldAccNo == null || this.oldAccNo.equalsIgnoreCase("null")) {
                    this.setErrorMsg("Please Enter Account Number.");
                    return;
                }

                if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMsg("Please Fill Proper Account Number.");
                    return;
                }
                
                accNo = ftsPostRemote.getNewAccountNumber(oldAccNo);
                String curBrnCode = ftsPostRemote.getCurrentBrnCode(accNo);
                if (!curBrnCode.equalsIgnoreCase(getOrgnBrCode())) {
                    this.accNo = "";
                    this.setErrorMsg("Account should be of self branch");
                    return;
                }
                
                String acNature = ftsPostRemote.getAccountNature(accNo);
                if (!acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    this.accNo = "";
                    this.setErrorMsg("Please Fill Only RD Account");
                    return;
                }
            
                if (this.getAccNo().equalsIgnoreCase("")) {
                    this.setErrorMsg("Please Fill Account Number");
                    return;
                }
            }

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String report = "RD INSTALLMENT REPORT";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            fillParams.put("pPrintedDate", this.toDt);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);

            List<RdInstallmentPojo> rdInstallmentDetails = facadeRemote.getRdInstallmentDetails(this.option, this.accNo, ymd.format(sdf.parse(this.toDt)), this.getOrgnBrCode());
            new ReportBean().openPdf("RD INSTALLMENT REPORT_" + ymd.format(sdf.parse(getTodayDate())), "RdInstallmentReport", new JRBeanCollectionDataSource(rdInstallmentDetails), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setOption("ALL");
        this.setOldAccNo("");
        this.setAccFocus("false");
        this.setAccWise("true");
        setToDt(sdf.format(dt));
    }
}