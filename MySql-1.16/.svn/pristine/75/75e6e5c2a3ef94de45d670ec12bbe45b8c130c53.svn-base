package com.cbs.web.controller.report.misc;

/**
 *
 * @author Zeeshan Waris
 */
import com.cbs.dto.report.AccountOpenRegisterPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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

public final class AccountOpenRegister extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    MiscReportFacadeRemote beanRemote;
    private String acType;
    private List<SelectItem> acTypeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote RemoteCode;

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

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
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

    public AccountOpenRegister() {
        try {
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acTypeList = new ArrayList<SelectItem>();
            List listAccTy = beanRemote.getAllAccounType();
            Vector vtr = new Vector();
            acNatureList = new ArrayList<SelectItem>();
            List accNture = RemoteCode.getAccountNature();
            if(!accNture.isEmpty()) {
                acNatureList.add(new SelectItem("0", "--Select--"));
                for(int j =0;j<accNture.size() ;j++) {
                    Vector vect = (Vector) accNture.get(j);
                    acNatureList.add(new SelectItem(vect.get(0).toString(), vect.get(0).toString()));
                }
            }
//            if (!listAccTy.isEmpty()) {
//                for (int i = 0; i < listAccTy.size(); i++) {
//                    vtr = (Vector) listAccTy.get(i);
//                    acTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
//                }
//                acTypeList.add(new SelectItem("ALL", "ALL"));
//            }
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
    public void blurAcctNature() {
        if (acTypeList != null) {
            acTypeList.clear();            
        }
        Vector vtr = null;
        try {
            List result = null;
            result = RemoteCode.getAccType(acNature);
            acTypeList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                acTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));                
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
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
//            if (acType.equalsIgnoreCase("0")) {
//                message = "Please select A/c Type";
//                return null;
//            }
            if(acNature.equalsIgnoreCase("0")) {
                message = "Please Select Account Nature!!!";
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<AccountOpenRegisterPojo> accountOpenRegister = beanRemote.accountOpenRegister(acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branchCode,acNature);
            if (!accountOpenRegister.isEmpty()) {
                String repName = "ACCOUNT OPEN REGISTER";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("dd-MMM-yyyy", FromDt);
                fillParams.put("dd-MMM-yyyy", ToDate);
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().jasperReport("acc_open_register", "text/html", new JRBeanCollectionDataSource(accountOpenRegister), fillParams, repName);
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
//            if (acType.equalsIgnoreCase("0")) {
//                message = "Please select A/c Type";
//                return;
//            }
            if(acNature.equalsIgnoreCase("0")) {
                message = "Please Select Account Nature!!!";
                return ;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<AccountOpenRegisterPojo> accountOpenRegister = beanRemote.accountOpenRegister(acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branchCode,acNature);
            if (!accountOpenRegister.isEmpty()) {
                String report = "ACCOUNT OPEN REGISTER";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("dd-MMM-yyyy", FromDt);
                fillParams.put("dd-MMM-yyyy", ToDate);
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().openPdf("ACCOUNT OPEN REGISTER_"+ ymdFormat.format(sdf.parse(getTodayDate())), "acc_open_register", new JRBeanCollectionDataSource(accountOpenRegister), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcNature("0");
            setAcType("ALL");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
