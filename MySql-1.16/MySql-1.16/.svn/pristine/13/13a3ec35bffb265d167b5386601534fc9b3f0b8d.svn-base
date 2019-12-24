/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.pojo.SalarySheetPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.pojo.EmployeeDetailGrid;
import com.hrms.web.utils.LocalizationUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class SalarySheet extends BaseBean {
    private static final Logger logger = Logger.getLogger(SalaryProjection.class);
    private String message;
    private String repType;
    private List<SelectItem> repList;
    private String sheetCateory;
    private List<SelectItem> sheetCateoryList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String tillDate;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private MiscReportFacadeRemote remoteFacade;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<SalarySheetPojo> reportList = new ArrayList<SalarySheetPojo>();
    private String employeeWise = "false";
    private String empSearchCriteria;
    private String empSearchValue;
    private boolean disableCategorizeDetails;
    private List<EmployeeDetailGrid> empSearchTable;
    private int totalEmpRecords;
    private int compCode;
    private EmployeeDetailGrid currentItem;
    private String prvEmpId;
    
    public SalarySheet() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            repList = new ArrayList<SelectItem>();
            repList.add(new SelectItem("S", "--Select--"));
            repList.add(new SelectItem("SA", "SALARY ALLOWANCES SHEET"));
            repList.add(new SelectItem("SD", "SALARY DEDUCTION SHEET"));           
            sheetCateoryList = new ArrayList<SelectItem>();
            sheetCateoryList.add(new SelectItem("0", "--SELECT--"));
            sheetCateoryList.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            sheetCateoryList.add(new SelectItem("BRN", "BRANCH WISE"));
            monthList = new ArrayList<SelectItem>();
            monthList.add(new SelectItem("0", "--SELECT--"));
            monthList.add(new SelectItem("JANUARY", "JANUARY"));
            monthList.add(new SelectItem("FEBRUARY", "FEBRUARY"));
            monthList.add(new SelectItem("MARCH", "MARCH"));
            monthList.add(new SelectItem("APRIL", "APRIL"));
            monthList.add(new SelectItem("MAY", "MAY"));
            monthList.add(new SelectItem("JUNE", "JUNE"));
            monthList.add(new SelectItem("JULY", "JULY"));
            monthList.add(new SelectItem("AUGUST", "AUGUST"));
            monthList.add(new SelectItem("SEPTEMBER", "SEPTEMBER"));
            monthList.add(new SelectItem("OCTOBER", "OCTOBER"));
            monthList.add(new SelectItem("NOVEMBER", "NOVEMBER"));
            monthList.add(new SelectItem("DECEMBER", "DECEMBER"));           
            compCode = Integer.parseInt(getOrgnBrCode());

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        try {
            Pattern p = Pattern.compile("[0-9]*");
          
            if (month == null || month.equals("0")) {
                setMessage("Please select month !");
                return;
            }
            if (year == null || year.equals("")) {
                setMessage("Year can not be blank.");
                return;
            }
            Matcher m = p.matcher(year);
            if (m.matches() != true) {
                setMessage("Please fill proper Year.");
                return;
            }

            String val = "";
            Map fillParams = new HashMap();
            List brNameAndAddList;
            if (sheetCateory.equalsIgnoreCase("EWE")) {
                brNameAndAddList = common.getBranchNameandAddress(common.getBranchCode(this.getPrvEmpId())); 
                val = currentItem.getEmpId().toString();
                fillParams.put("pBankName", currentItem.getEmpName());
            }else{
                brNameAndAddList = common.getBranchNameandAddress(branchCode);
                val = branchCode;
                fillParams.put("pBankName", brNameAndAddList.get(1).toString() + " BRANCH");
            }
                
            fillParams.put("pMonthYear", month + " " + year);
          
            reportList = remoteFacade.getAccountSalarySheetData(this.getSheetCateory(), val, month, year);
          for(SalarySheetPojo  ssp :reportList){
              if(ssp.getComponentType().equalsIgnoreCase("EMPLOYER PF CONTRIBUTION ")){
               if(!ssp.getComponentAmt().toString().equalsIgnoreCase("0.0")){   
               fillParams.put("pEmpPFContriCompAmt",new BigDecimal(ssp.getComponentAmt().toString()) );
               
              }else{
               fillParams.put("pEmpPFContriCompAmt",new BigDecimal(ssp.getComponentAmt().toString()) );    
               }
          }
          }
            
            
            if(reportList.isEmpty()){
                 setMessage("Employee Is inactive");
             }
                new ReportBean().openPdf("Salary Sheet_"+month+" _ "+branchCode, "Salary_Sheet", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Salary Sheet");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void refreshPage() {
        setMessage("");
        setRepType("S");
        setMonth("0");
        setYear("");
    }
    
    public void onChangeCategory() {
        try {
            branchCodeList = new ArrayList<SelectItem>();
            branchCodeList.add(new SelectItem("0", "--SELECT--"));
            if (sheetCateory.equalsIgnoreCase("EWE")) {
                employeeWise = "true";
                disableCategorizeDetails = true;
                this.setMessage("");
            } else if (sheetCateory.equalsIgnoreCase("0")) {
                employeeWise = "false";
                disableCategorizeDetails = false;
                this.setMessage("Please Select Categorization !!");
            } else if (sheetCateory.equalsIgnoreCase("BRN")) {
                employeeWise = "false";
                disableCategorizeDetails = false;

                List brncode = new ArrayList();
                brncode = common.getAlphacodeAllAndBranch(this.getOrgnBrCode());
                if (!brncode.isEmpty()) {
                    for (int i = 0; i < brncode.size(); i++) {
                        Vector brnVector = (Vector) brncode.get(i);
                         branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(brnVector.get(1).toString())), brnVector.get(0).toString()));
                    }
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    /**
     * set the employee wise data on click of find button in page
     **/
    public void getEmployeeDetails() {
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            empSearchTable = new ArrayList<EmployeeDetailGrid>();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = payrollDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
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
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }
    
    public void setEmpRowValues() {
        branchCodeList = new ArrayList<SelectItem>();
        branchCodeList.add(new SelectItem(currentItem.getEmpId().toString(), currentItem.getEmpName().toString()));
        this.setPrvEmpId(currentItem.getEmpId());
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

    public List<SelectItem> getRepList() {
        return repList;
    }

    public void setRepList(List<SelectItem> repList) {
        this.repList = repList;
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

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSheetCateory() {
        return sheetCateory;
    }

    public void setSheetCateory(String sheetCateory) {
        this.sheetCateory = sheetCateory;
    }

    public List<SelectItem> getSheetCateoryList() {
        return sheetCateoryList;
    }

    public void setSheetCateoryList(List<SelectItem> sheetCateoryList) {
        this.sheetCateoryList = sheetCateoryList;
    }

    public String getEmployeeWise() {
        return employeeWise;
    }

    public void setEmployeeWise(String employeeWise) {
        this.employeeWise = employeeWise;
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }

    public String getEmpSearchValue() {
        return empSearchValue;
    }

    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }

    public boolean isDisableCategorizeDetails() {
        return disableCategorizeDetails;
    }

    public void setDisableCategorizeDetails(boolean disableCategorizeDetails) {
        this.disableCategorizeDetails = disableCategorizeDetails;
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

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public EmployeeDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmployeeDetailGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getPrvEmpId() {
        return prvEmpId;
    }

    public void setPrvEmpId(String prvEmpId) {
        this.prvEmpId = prvEmpId;
    }
    
}
