/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.pojo.SalarySheetPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.delegate.PayrollTransactionsManagementDelegate;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.hrms.web.utils.LocalizationUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
public class EarningRecords extends BaseBean {
    private static final Logger logger = Logger.getLogger(EarningRecords.class);
    private String message;
    private String empTxtId;
    private String empTxtName;
    private String employeeId;
    private String frDt;
    private String toDt;
    private List<EmployeeDetailGrid> empSearchTable;
    private int totalEmpRecords;
    private EmployeeDetailGrid currentItem;
    private int compCode;
    private PayrollTransactionsManagementDelegate payRollDelegate;
    private String empSearchCriteria;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private MiscReportFacadeRemote remoteFacade;  
    private CommonReportMethodsRemote common;
    List<SalarySheetPojo> repList = new ArrayList<SalarySheetPojo>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmpTxtId() {
        return empTxtId;
    }

    public void setEmpTxtId(String empTxtId) {
        this.empTxtId = empTxtId;
    }

    public String getEmpTxtName() {
        return empTxtName;
    }

    public void setEmpTxtName(String empTxtName) {
        this.empTxtName = empTxtName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<EmployeeDetailGrid> getEmpSearchTable() {
        return empSearchTable;
    }

    public void setEmpSearchTable(List<EmployeeDetailGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }

    public int getTotalEmpRecords() {
        return totalEmpRecords;
    }

    public void setTotalEmpRecords(int totalEmpRecords) {
        this.totalEmpRecords = totalEmpRecords;
    }

    public EmployeeDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmployeeDetailGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }
    
    public EarningRecords() {
        try {
            compCode = Integer.parseInt(getOrgnBrCode());
            payRollDelegate = new PayrollTransactionsManagementDelegate(); 
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        } catch (Exception e) {
            logger.error("Exception occured while executing method SalaryProcessing()", e);
            logger.error("SalaryProcessing()", e);
        }
    }
    
    public void getEmployeeDetails() {
        this.setEmpTxtName("");
        this.setEmployeeId("");
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollDelegate.getHrPersonnelData(compCode, empSearchCriteria, empTxtId);
            int i = 0;
            EmployeeDetailGrid currentEmpRow;
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                currentEmpRow = new EmployeeDetailGrid();
                currentEmpRow.setSno(++i);
                currentEmpRow.setEmpId(hrPersonnelDetailsTO.getEmpId());
                currentEmpRow.setEmpName(hrPersonnelDetailsTO.getEmpName());
                currentEmpRow.setEmpAddress(hrPersonnelDetailsTO.getEmpContAdd());
                currentEmpRow.setEmpPhone(hrPersonnelDetailsTO.getEmpContTel());
                empSearchTable.add(currentEmpRow);
            }
            totalEmpRecords = i;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessage()));
        }
    }
    
    public void setEmpRowValues() {
        this.setEmpTxtName("");
        this.setEmployeeId("");
        this.setEmpTxtName(currentItem.getEmpName());
        this.setEmployeeId(currentItem.getEmpId());
    }
    
    public void earningRecordRep(){
        String branchName = "", address = "";
        try {
            if(this.getEmployeeId()==null || this.getEmployeeId().equalsIgnoreCase("")){
                this.setMessage("Please select employee id");
                return;
            }
            
            if(this.getFrDt()==null || this.getFrDt().equalsIgnoreCase("")){
                this.setMessage("Please fill From Date");
                return;
            }
            
            if(this.getToDt()==null || this.getToDt().equalsIgnoreCase("")){
                this.setMessage("Please fill To Date");
                return;
            }
            
            if (!Validator.validateDate(this.getFrDt())) {
                setMessage("Please fill Proper from date ");
                return;
            }
            
            if (!Validator.validateDate(this.getToDt())) {
                setMessage("Please fill Proper to date ");
                return;
            }
            
            if(!this.getFrDt().substring(0, 2).equalsIgnoreCase("01")){
                setMessage("From Date must be start date of month ");
                return;
            }
            
            String tDt = ymd.format(CbsUtil.getLastDateOfMonth(dmy.parse(this.getToDt())));
            if(!tDt.equalsIgnoreCase(ymd.format(dmy.parse(this.getToDt())))){
                setMessage("To Date must be Last date of month ");
                return;
            }
            
            if (dmy.parse(this.getFrDt()).after(dmy.parse(this.getToDt()))) {
                message = "From date should be less than to date";
                return;
            }
            
            String report = "Earning Records Report";
            Map fillParams = new HashMap();
            List brNameAdd = common.getBranchNameandAddress(getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pFrDate", this.getFrDt());
            fillParams.put("pToDate", this.getToDt());

            repList = remoteFacade.getEarningRecordsData(getEmployeeId(), ymd.format(dmy.parse(this.getFrDt())), ymd.format(dmy.parse(this.getToDt())));
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            new ReportBean().openPdf("Earning Records Report", "EARNINGRECORD", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessage()));
        }
    }
    
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    
    public void earningRecordExl(){
        String branchName = "", address = "";
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            if(this.getEmployeeId()==null || this.getEmployeeId().equalsIgnoreCase("")){
                this.setMessage("Please select employee id");
                return;
            }
            
            if(this.getFrDt()==null || this.getFrDt().equalsIgnoreCase("")){
                this.setMessage("Please fill From Date");
                return;
            }
            
            if(this.getToDt()==null || this.getToDt().equalsIgnoreCase("")){
                this.setMessage("Please fill To Date");
                return;
            }
            
            if (!Validator.validateDate(this.getFrDt())) {
                setMessage("Please fill Proper from date ");
                return;
            }
            
            if (!Validator.validateDate(this.getToDt())) {
                setMessage("Please fill Proper to date ");
                return;
            }
            
            if(!this.getFrDt().substring(0, 2).equalsIgnoreCase("01")){
                setMessage("From Date must be start date of month ");
                return;
            }
            
            String tDt = ymd.format(CbsUtil.getLastDateOfMonth(dmy.parse(this.getToDt())));
            if(!tDt.equalsIgnoreCase(ymd.format(dmy.parse(this.getToDt())))){
                setMessage("To Date must be Last date of month ");
                return;
            }
            
            if (dmy.parse(this.getFrDt()).after(dmy.parse(this.getToDt()))) {
                message = "From date should be less than to date";
                return;
            }
            
            repList = remoteFacade.getEarningRecordsData(getEmployeeId(), ymd.format(dmy.parse(this.getFrDt())), ymd.format(dmy.parse(this.getToDt())));
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            
            String empName = "", empDesg="";
            for(int i = 0;i<repList.size();i++){
                empName = repList.get(i).getEmpName();
                empDesg = repList.get(i).getDesignation();                
            }
            
            
            List brNameAdd = common.getBranchNameandAddress(getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.CENTER);
            style.setFont(Font.TIMES_NEW_ROMAN_BIG_BOLD);
            style.setBorder(Border.THIN);
            
            Style subTitle = new Style();
            subTitle.setHorizontalAlign(HorizontalAlign.LEFT);
            subTitle.setFont(Font.TIMES_NEW_ROMAN_MEDIUM_BOLD);
            subTitle.setBorder(Border.THIN);
            
            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. NO.", 50);
            AbstractColumn purposeType = ReportBean.getColumn("purposeType", String.class, "MONTH & YEAR", 200);
            AbstractColumn basicPay = ReportBean.getColumn("basicPay", Double.class, "GROSS SALARY", 150);
            AbstractColumn cca = ReportBean.getColumn("cca", Double.class, "OTHERS", 100);
            AbstractColumn netPayble = ReportBean.getColumn("netPayble", BigDecimal.class, "TOTAL SALARY", 100);
            AbstractColumn lop = ReportBean.getColumn("lop", Double.class, "BANK PF CONTRIBUTION", 150);
            AbstractColumn pfEmp = ReportBean.getColumn("pfEmp", Double.class, "EMPLOYEE PF CONTRIBUTION", 150);
            AbstractColumn totaldeduction = ReportBean.getColumn("totaldeduction", BigDecimal.class, "PF TOTAL", 100);
            
            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(purposeType);
            width = width + purposeType.getWidth();

            fastReport.addColumn(basicPay);
            width = width + basicPay.getWidth();

            fastReport.addColumn(cca);
            width = width + cca.getWidth();
            
            fastReport.addColumn(netPayble);
            width = width + netPayble.getWidth();

            fastReport.addColumn(lop);
            width = width + lop.getWidth();

            fastReport.addColumn(pfEmp);
            width = width + pfEmp.getWidth();

            fastReport.addColumn(totaldeduction);
            width = width + totaldeduction.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle(branchName + "\\n" + address + "\\n" + "EMPLOYEE EARNING RECORD" + "\\n" + "FROM" + " " + this.frDt + "TO" + " " + this.toDt);
            fastReport.setTitleStyle(style);
            fastReport.setSubtitle("EMPLOYEE NAME " + "      " + empName + "                         " + " EMPLOYEE DESIG. " + "      " + empDesg + "                         " + " EMPLOYEE NO. " + "          " + this.getEmployeeId());
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "Earning Records Report");
            
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableDataEmloyeeWise()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessage()));
        }
    }
    
    public void refresh() {
        this.setMessage("");
        this.setEmpTxtName("");
        this.setEmployeeId("");
        empSearchTable = new ArrayList<EmployeeDetailGrid>();
        this.setEmpTxtId("");        
    }
    
    public String btnExitAction() {
        refresh();
        return "case1";
    }
}
