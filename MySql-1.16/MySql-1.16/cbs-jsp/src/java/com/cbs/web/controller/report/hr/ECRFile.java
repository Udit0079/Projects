package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.utils.LocalizationUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;



public class ECRFile extends BaseBean
{
  private String message;
  private List<SelectItem> monthList;
  private static final Logger logger = Logger.getLogger(FinancialSalaryProjection.class);
  private PayrollTransactionsFacadeRemote payrollRemote;
  private String yearFromDate;
  private String yearToDate;
  private String month;
  private List<SelectItem> yearList;
  private List<String> filenametxt;
  private String ecrTxtFileName;
  private String year;
  private PayrollOtherMgmFacadeRemote payrollOtrRemote;
  private String ecrFile;
  private CommonReportMethodsRemote commomRemote;
  private String todayDate;
  private boolean saveLink;
  private String fileName;
  
  public ECRFile() {
    try {
      commomRemote = ((CommonReportMethodsRemote)ServiceLocator.getInstance().lookup("CommonReportMethods"));
      payrollRemote = ((PayrollTransactionsFacadeRemote)HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade"));
      payrollOtrRemote = ((PayrollOtherMgmFacadeRemote)HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade"));
      ecrFile = new String();
      filenametxt = new ArrayList();
      getInitalData();
      todayDate = getTodayDate();
    } catch (Exception e) {
      setMessage(e.getMessage());
    }
  }
  
  public void getInitalData() {
    try {
      monthList = new ArrayList();
      monthList.add(new SelectItem("0", "--Select--"));
      monthList.add(new SelectItem("January", "January"));
      monthList.add(new SelectItem("February", "February"));
      monthList.add(new SelectItem("March", "March"));
      monthList.add(new SelectItem("April", "April"));
      monthList.add(new SelectItem("May", "May"));
      monthList.add(new SelectItem("June", "June"));
      monthList.add(new SelectItem("July", "July"));
      monthList.add(new SelectItem("August", "August"));
      monthList.add(new SelectItem("September", "September"));
      monthList.add(new SelectItem("October", "October"));
      monthList.add(new SelectItem("November", "November"));
      monthList.add(new SelectItem("December", "December"));
      
      yearList = new ArrayList();
      yearList.add(new SelectItem("0", "--Select--"));
      List yrlist = new ArrayList();
      List list = payrollRemote.getYears(getOrgnBrCode());
      yrlist = payrollRemote.getYearList();
      for (int j = 0; j < yrlist.size(); j++) {
        yearList.add(new SelectItem(yrlist.get(j).toString()));
      }
      
      for (int i = 0; i < list.size(); i++) {
        Object[] ele = (Object[])list.get(0);
        yearFromDate = ele[0].toString().trim();
        yearToDate = ele[1].toString().trim();
      }
    } catch (Exception e) {
      setMessage(e.getMessage());
    }
  }
  
  public void getFinancialYear() {
    try {
      List list = payrollRemote.getFinYears(getOrgnBrCode());
      for (int i = 0; i < list.size(); i++) {
        Object[] ele = (Object[])list.get(0);
        yearFromDate = ele[0].toString().trim();
        yearToDate = ele[1].toString().trim();
      }
    } catch (ServiceLocatorException e1) {
      logger.error("Exception occured while executing method getFinancialYear()", e1);
      setMessage(LocalizationUtil.getLocalizedText("system.exception.occured"));
    } catch (Exception e) {
      logger.error("Exception occured while executing method getFinancialYear()", e);
      setMessage(LocalizationUtil.getLocalizedText("system.exception.occured"));
    }
  }
  
  public HttpServletRequest getRequest() {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    if (request == null) {
      throw new RuntimeException("Sorry. Got a null request from faces context");
    }
    return request;
  }
  
  public void gpfRegisterRep()
  {
    setSaveLink(false);
    try {
      Pattern p = Pattern.compile("[0-9]*");
      if ((month == null) || (month.equalsIgnoreCase("0"))) {
        setMessage("Please select month !");
        return;
      }
      if ((year == null) || (year.equalsIgnoreCase(""))) {
        setMessage("Year can not be blank.");
        return;
      }
      Matcher m = p.matcher(year);
      if (m.matches() != true) {
        setMessage("Please fill proper Year.");
        return;
      }
      File dir = null;
      if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
        dir = new File("/install/ECRFileTxtFile/");
      } else {
        dir = new File("C:/ECRFileTxtFile/");
      }
      if (!dir.exists()) {
        dir.mkdirs();
      }
      ecrFile = payrollOtrRemote.getEcrFileData(dir.getCanonicalPath(), month, year);
      ecrTxtFileName = ecrFile;
      filenametxt.add(ecrTxtFileName);
      setSaveLink(true);
    }
    catch (Exception ex) {
      setMessage(ex.getMessage());
    }
  }
  
  public void downloadFile() {
    HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    String ecrPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    try {
      String dirName = "";
      String osName = System.getProperty("os.name");
      if (osName.equalsIgnoreCase("Linux")) {
        dirName = "/install/ECRFileTxtFile/";
      } else {
        dirName = "C:\\ECRFileTxtFile\\";
      }
      res.sendRedirect(ecrPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName);
    } catch (Exception e) {
      setMessage(e.getLocalizedMessage());
    }
  }
  

  public List<SelectItem> getMonthList()
  {
    return monthList;
  }
  
  public void setMonthList(List<SelectItem> monthList) {
    this.monthList = monthList;
  }
  
  public String getYearFromDate() {
    return yearFromDate;
  }
  
  public void setYearFromDate(String yearFromDate) {
    this.yearFromDate = yearFromDate;
  }
  
  public String getYearToDate() {
    return yearToDate;
  }
  
  public void setYearToDate(String yearToDate) {
    this.yearToDate = yearToDate;
  }
  
  public String getMonth() {
    return month;
  }
  
  public void setMonth(String month) {
    this.month = month;
  }
  
  public List<SelectItem> getYearList() {
    return yearList;
  }
  
  public void setYearList(List<SelectItem> yearList) {
    this.yearList = yearList;
  }
  
  public String getYear() {
    return year;
  }
  
  public void setYear(String year) {
    this.year = year;
  }
  
  public boolean isSaveLink() { return saveLink; }
  
  public void setSaveLink(boolean saveLink)
  {
    this.saveLink = saveLink;
  }
  
  public List<String> getFilenametxt() {
    return filenametxt;
  }
  
  public void setFilenametxt(List<String> filenametxt) {
    this.filenametxt = filenametxt;
  }
  
  public String getEcrTxtFileName() {
    return ecrTxtFileName;
  }
  
  public void setEcrTxtFileName(String ecrTxtFileName) {
    this.ecrTxtFileName = ecrTxtFileName;
  }
  
  public String getEcrFile() {
    return ecrFile;
  }
  
  public void setEcrFile(String ecrFile) {
    this.ecrFile = ecrFile;
  }
  
  public void refresh() {
    setMessage("");
    setMonth("0");
    setYear("0");
    setFileName("");
    setEcrTxtFileName("");
    setEcrFile("");
  }
  
  public String btnExitAction() {
    refresh();
    return "case1";
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public String getFileName() {
    return fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
