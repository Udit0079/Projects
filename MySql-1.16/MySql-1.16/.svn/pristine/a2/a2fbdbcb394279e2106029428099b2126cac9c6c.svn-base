/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.ho.Form2StmtUnSecAdvToDirFirmPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeHalfYearlyRemote;
import com.cbs.utils.ServiceLocator;
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
import com.cbs.web.pojo.ho.Form2StmtUnSecAdvToDirFirm;

/**
 *
 * @author Alok Yadav
 */
public class UnSecLoanToDirectorsFirms  extends BaseBean {
    private String asOnDt;
    private String message;
    private String repOpt;
    private double repOptIn;
    private List<SelectItem> repOptionList;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private final String jndiRbiReportFacadeHalfYearlyName = "RbiReportFacadeHalfYearly";
    private RbiReportFacadeHalfYearlyRemote rbiReportFacadeHalfYearlyRemote = null;
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public String getRepOpt() {
        return repOpt;
    }

    public void setRepOpt(String repOpt) {
        this.repOpt = repOpt;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public UnSecLoanToDirectorsFirms() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            rbiReportFacadeHalfYearlyRemote = (RbiReportFacadeHalfYearlyRemote) ServiceLocator.getInstance().lookup(jndiRbiReportFacadeHalfYearlyName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setMessage("");
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs."));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

      
    public void viewPdf() {
        Map map = new HashMap();
        try {
             if (this.asOnDt == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (sdf.parse(asOnDt).compareTo(new Date()) > 0) {
                setMessage("Date can not be greater than Current Date.");
                return;
            }
            if (this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            setMessage("");
            String bankName = "";
            String bankAddress = "";
            String report = "";
            List dataList1;
            dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            String amtIn = null;
            if (repOpt.equalsIgnoreCase("T")) {
                amtIn = "Amount (Rs. in Thousand)";
                repOptIn = 1000;
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
                repOptIn = 100000;
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
                repOptIn = 10000000;
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amount (Rs.)";
                repOptIn = 1;
            }
            report = "Form II-Statement of Unsecured Loans and Advances to Directors/Firms in which they have interest";
            map.put("pPrintedBy", this.getUserName());
            map.put("pPrintedDate", this.asOnDt);
            map.put("report", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            List<Form2StmtUnSecAdvToDirFirmPojo> mainList = rbiReportFacadeHalfYearlyRemote.getForm2StmtUnSecAdvToDirFirm(ymd.format(sdf.parse(asOnDt)), getOrgnBrCode(), repOptIn);
            List<Form2StmtUnSecAdvToDirFirm> dirAdvList = new ArrayList<Form2StmtUnSecAdvToDirFirm>();
            Form2StmtUnSecAdvToDirFirm pojo1 = new Form2StmtUnSecAdvToDirFirm();
            pojo1.setDirAdvList(new JRBeanCollectionDataSource(mainList));
            pojo1.setDirAdvList1(new JRBeanCollectionDataSource(mainList));
            dirAdvList.add(pojo1);            
            new ReportBean().openPdf("Form2AdvOfUnSecAdv"+ymd.format(sdf.parse(asOnDt)), "Form2AdvUnSecToDirFirmCover", new JRBeanCollectionDataSource(dirAdvList), map);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh(){
        setMessage("");
        setRepOpt("");
    }
    
    public String exit() {
        return "case1";
    }
}
