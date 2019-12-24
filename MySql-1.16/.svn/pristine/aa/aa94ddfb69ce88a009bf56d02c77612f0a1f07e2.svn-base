/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanDemandRecoveryPojo;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
 * @author saurabhsipl
 */
public class LoanDmdRecovery extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    MiscReportFacadeRemote beanRemote;
    private String acType;
    private String acNature;
    private List<SelectItem> acNatureList;
    private List<SelectItem> acTypeList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote RemoteCode;
    private LoanInterestCalculationFacadeRemote loanremote;

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
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

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public LoanDmdRecovery() {
        try {
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanremote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            acTypeList = new ArrayList<SelectItem>();
            acNatureList = new ArrayList<SelectItem>();

            List listAcNat = RemoteCode.getAcctNatureOnlyDB();
            if (!listAcNat.isEmpty()) {
                acNatureList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < listAcNat.size(); i++) {
                    Vector tempVector = (Vector) listAcNat.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountCode() {
        try {
            List listAccTy = RemoteCode.getAcctTypeAsAcNatureOnlyDB(acNature);
            acTypeList = new ArrayList<SelectItem>();
              acTypeList.add(new SelectItem("0", "ALL"));
            if (!listAccTy.isEmpty()) {
              
                for (int i = 0; i < listAccTy.size(); i++) {
                    Vector tempVector = (Vector) listAccTy.get(i);
                    acTypeList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (acNature.equalsIgnoreCase("")) {
                message = "Please Select A/C Nature";
                return null;
            }
            if (acType.equalsIgnoreCase("")) {
                message = "Please select A/c Type";
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<LoanDemandRecoveryPojo> resultList = RemoteCode.loanDmdRecovery(acNature, acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branchCode);
//            List<AccountOpenRegisterPojo> accountOpenRegister = beanRemote.accountOpenRegister(acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branchCode);
            if (!resultList.isEmpty()) {
                String repName = "Loan Demand Recovery Report";
                Map fillParams = new HashMap();
                String bankName = "";
                String bankAddress = "";
                List dataList1 = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddress", bankAddress);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedDate", duration);
                new ReportBean().jasperReport("Loan_Demand_Recovery", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void viewPdfReport() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (acNature.equalsIgnoreCase("")) {
                message = "Please Select A/C Nature";
                return;
            }
            if (acType.equalsIgnoreCase("")) {
                message = "Please select A/c Type";
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<LoanDemandRecoveryPojo> resultList = RemoteCode.loanDmdRecovery(acNature, acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branchCode);
            if (!resultList.isEmpty()) {
                String report = "Loan Demand Recovery Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                String bankName = "";
                String bankAddress = "";
                List dataList1 = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddress", bankAddress);
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().openPdf("Loan_Demand_Recovery" + ymdFormat.format(sdf.parse(getTodayDate())), "Loan_Demand_Recovery", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            System.out.println("Error:--" + e);
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcType("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        try {
            refreshAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
