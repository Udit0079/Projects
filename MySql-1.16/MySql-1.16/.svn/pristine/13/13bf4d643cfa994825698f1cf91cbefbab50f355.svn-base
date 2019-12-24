/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

/**
 *
 * @author root
 */
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeHalfYearlyRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;                             
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
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
 * @author saurabhsipl
 */
public class AdvToDirRelative extends BaseBean {
    private String asOnDt;
    private String message;
    private String repOpt;
    private List<SelectItem> repOptionList;
    private List<SelectItem> reportTypeList;    
    private List<SelectItem> reportInList;
    private String reportIn;
    private String reportType;
    Date dt = new Date();
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    private RbiReportFacadeRemote rbiRemote = null;
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote = null;
    private RbiReportFacadeHalfYearlyRemote rbiHalfYearRemote = null;
    private LoanReportFacadeRemote loanRemote = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    
      public AdvToDirRelative() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            rbiSoss1And2Remote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
            rbiRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            rbiHalfYearRemote = (RbiReportFacadeHalfYearlyRemote) ServiceLocator.getInstance().lookup("RbiReportFacadeHalfYearly");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiMonthlyReportFacade");
            setMessage("");
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs."));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
            
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("","--Select--"));
            reportTypeList.add(new SelectItem("1", "Proforma -I"));
            reportTypeList.add(new SelectItem("2", "Proforma-II"));
            
            reportInList = new ArrayList<SelectItem>();
            reportInList.add(new SelectItem("","--Select--"));
            reportInList.add(new SelectItem("N", "In Detail"));
            reportInList.add(new SelectItem("Y", "In Summary"));
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
            if(reportType.equalsIgnoreCase("")){
                setMessage("Please Select The Report Type!!!!");
                return;
            }
            if(this.reportIn.equalsIgnoreCase("")){
                setMessage("Please Select The Report In!!");
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
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amount (Rs.)";
            }
            report = "ADVANCES TO DIRECTORS RELATIVES";
//            map.put("repDate", this.asOnDt);
//            map.put("pPrintedDate", this.dt);
            String[] arr = Util.getReportOptionAndDescription(this.repOpt);
            map.put("pPrintedBy", this.getUserName());
            map.put("pReportDate", this.asOnDt);
            map.put("pReportName", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
//            System.out.println("Start"+new Date());
//            List <LoanMisCellaniousPojo>  resultList = loanRemote.cbsLoanMisReport("0A", "ALL", "", asOnDt, "A", 0.0, 99999999999.99, "A", "", "", "", "", "", "",
//                    "", "", "", "", "", "", "", "", "", "", "DIRREL", "", "", "", "", "", "", "", "A", "", "N", "Y");
            if(reportType.equalsIgnoreCase("1")) {
                List<LoanMisCellaniousPojo> resultList = rbiHalfYearRemote.getAdvToDirRelative("0A", ymd.format(sdf.parse(asOnDt)),new BigDecimal(arr[0]));
                new ReportBean().openPdf("Advance_TO_Directors_Relatives_Proforma_I"+ymd.format(sdf.parse(asOnDt)), "AdvncToDirRelative", new JRBeanCollectionDataSource(resultList), map);
            } else if(reportType.equalsIgnoreCase("2")) {
                List<String> dates = new ArrayList<>();
                dates.add(ymd.format(sdf.parse(asOnDt)));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List<RbiSossPojo> partBList= rbiSoss1And2Remote.getSOSS2("DIR_REL_PART_B", ymd.format(sdf.parse(asOnDt)), getOrgnBrCode(), new BigDecimal(arr[0]), this.reportIn,osBlancelist,"0") ;
                new ReportBean().openPdf("Advance_TO_Directors_Relatives_Proforma_II"+ymd.format(sdf.parse(asOnDt)), "AdvncToDirRelative_PartB",new JRBeanCollectionDataSource(partBList), map);
            }
            
//            System.out.println("END"+new Date());
            

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
        refresh();
        return "case1";
    }
    
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }
    
    public String getReportIn() {
        return reportIn;
    }    
    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }
    public List<SelectItem> getReportInList() {
        return reportInList;
    }
    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
    }
}
