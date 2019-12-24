/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.ConsolidateProfitLossPojo;
import com.cbs.dto.report.SortByGroupCode;
import com.cbs.dto.report.SortBySubGroupCode;
import com.cbs.dto.report.SortByType;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.PlList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author sipl
 */
public class ConsolidateProfitLoss extends BaseBean {

    String message;
    private String branch;
    private String showPanel;
    private String exCounter;
    private String displayExCtr;
    private List<SelectItem> exCounterList;
    private List<SelectItem> branchList;
    Date calDate;
    private String selectOption;
    private List<SelectItem> selectOptionList;
    Date dt = new Date();
    private GLReportFacadeRemote glBeanRemote;
    private CommonReportMethodsRemote common;
    List<ConsolidateProfitLossPojo> repDataList = new ArrayList<ConsolidateProfitLossPojo>();

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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(String selectOption) {
        this.selectOption = selectOption;
    }

    public List<SelectItem> getSelectOptionList() {
        return selectOptionList;
    }

    public void setSelectOptionList(List<SelectItem> selectOptionList) {
        this.selectOptionList = selectOptionList;
    }

    public String getShowPanel() {
        return showPanel;
    }

    public void setShowPanel(String showPanel) {
        this.showPanel = showPanel;
    }

    public String getExCounter() {
        return exCounter;
    }

    public void setExCounter(String exCounter) {
        this.exCounter = exCounter;
    }

    public String getDisplayExCtr() {
        return displayExCtr;
    }

    public void setDisplayExCtr(String displayExCtr) {
        this.displayExCtr = displayExCtr;
    }

    public List<SelectItem> getExCounterList() {
        return exCounterList;
    }

    public void setExCounterList(List<SelectItem> exCounterList) {
        this.exCounterList = exCounterList;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    String halfYearFlag = "0";

    public ConsolidateProfitLoss() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (brnLst.size() > 1) {
                branchList.add(new SelectItem("CA", "CONSOLIDATED"));
            }
            for (int i = 0; i < brnLst.size(); i++) {
                Vector ele7 = (Vector) brnLst.get(i);
                branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
            }
            halfYearFlag = glBeanRemote.getHalfYearEndFlag();
            exCounterList = new ArrayList<>();
            exCounterList.add(new SelectItem("N", "No"));
            exCounterList.add(new SelectItem("Y", "Yes"));
            setExCounter("N");
            setDisplayExCtr("none");
            this.setCalDate(new Date());
            setShowPanel("none");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurOfDate() {
        selectOptionList = new ArrayList<SelectItem>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(calDate);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        if(halfYearFlag.equals("0")){
            if (day == 31 && month == 3) {
                selectOptionList.add(new SelectItem("0", "Before Year End"));
                selectOptionList.add(new SelectItem("1", "After Year End"));
                selectOption = "0";
                setShowPanel("block");
            }else {
                setShowPanel("none");
                selectOptionList.add(new SelectItem("0", "Before Year End"));
                selectOption = "0";
            }
        } else {
            if (day == 31 && month == 3) {
                selectOptionList.add(new SelectItem("0", "Before Year End"));
                selectOptionList.add(new SelectItem("1", "After Year End"));
                selectOption = "0";
                setShowPanel("block");
            }else if (day == 30 && month == 9) {
                selectOptionList.add(new SelectItem("2", "Before Half Year End"));
                selectOptionList.add(new SelectItem("3", "After Half Year End"));
                selectOption = "2";
                setShowPanel("block");
            } else {
                setShowPanel("none");
                if(month > 3 && month < 10){
                    selectOptionList.add(new SelectItem("2", "Before Half Year End"));
                    selectOption = "2";
                }else{
                    selectOptionList.add(new SelectItem("0", "Before Year End"));
                    selectOption = "0";
                }
                
            }
        }
    }
    
    
    public void checkExtCounter(){
        try {
            setMessage("");
            if(!branch.equals("0A") && common.isExCounterExit(branch)){
                setDisplayExCtr("");
            }
            else{
                setDisplayExCtr("none");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }

    public void btnHtmlAction() {
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        if (this.getCalDate() == null) {
            this.setMessage("Please Fill Date");
        }
        try {

            if (!((this.branch.equalsIgnoreCase("0A")) || (this.branch.equalsIgnoreCase("CA")))) {
                List dataList1 = common.getBranchNameandAddress(this.branch);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }

            Map fillParams = new HashMap();
            String report = "Consolidated Profit And Loss";
            fillParams.put("pPrintedDate", sdf.format(this.calDate));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchName", bankAddress);
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");

            repDataList = glBeanRemote.getConsolidateProfitLoss(ymd.format(this.calDate), this.getBranch(), selectOption, halfYearFlag,this.exCounter);

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortByType());
            chObj.addComparator(new SortByGroupCode());
            chObj.addComparator(new SortBySubGroupCode());

            Collections.sort(repDataList, chObj);

            if (this.branch.equalsIgnoreCase("0A")) {
                // PlList myList = new PlList();
                //  myList.setAssList(new JRBeanCollectionDataSource(repDataList));
                //   myList.setLibList(new JRBeanCollectionDataSource(repDataList));
                //   List<PlList> s = new ArrayList<PlList>();
                //  s.add(myList);
                new ReportBean().jasperReport("PL_Report", "text/html",
                        new JRBeanCollectionDataSource(repDataList), fillParams, "Consolidated Profit And Loss");
            } else if (this.branch.equalsIgnoreCase("CA")) {
                PlList myList = new PlList();
                myList.setAssList(new JRBeanCollectionDataSource(repDataList));
                myList.setLibList(new JRBeanCollectionDataSource(repDataList));
                List<PlList> s = new ArrayList<PlList>();
                s.add(myList);
                new ReportBean().jasperReport("PL_Report_PageWise", "text/html",
                        new JRBeanCollectionDataSource(s), fillParams, "Consolidated Profit And Loss");
            } else {
                if(this.exCounter.equalsIgnoreCase("Y")){
                   new ReportBean().jasperReport("PL_Report", "text/html",
                        new JRBeanCollectionDataSource(repDataList), fillParams, "Consolidated Profit And Loss");   
                }else{
                PlList myList = new PlList();
                myList.setAssList(new JRBeanCollectionDataSource(repDataList));
                myList.setLibList(new JRBeanCollectionDataSource(repDataList));
                List<PlList> s = new ArrayList<PlList>();
                s.add(myList);
                new ReportBean().jasperReport("PL_Report_PageWise", "text/html",
                        new JRBeanCollectionDataSource(s), fillParams, "Consolidated Profit And Loss");
                }
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void viewPdfReport(){
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        if (this.getCalDate() == null) {
            this.setMessage("Please Fill Date");
        }
        try {

            if (!((this.branch.equalsIgnoreCase("0A")) || (this.branch.equalsIgnoreCase("CA")))) {
                List dataList1 = common.getBranchNameandAddress(this.branch);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }

            Map fillParams = new HashMap();
            String report = "Consolidated Profit And Loss";
            fillParams.put("pPrintedDate", sdf.format(this.calDate));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchName", bankAddress);
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");

            repDataList = glBeanRemote.getConsolidateProfitLoss(ymd.format(this.calDate), this.getBranch(), selectOption, halfYearFlag,this.exCounter);

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortByType());
            chObj.addComparator(new SortByGroupCode());
            chObj.addComparator(new SortBySubGroupCode());

            Collections.sort(repDataList, chObj);

            if (this.branch.equalsIgnoreCase("0A")) {
                // PlList myList = new PlList();
                //  myList.setAssList(new JRBeanCollectionDataSource(repDataList));
                //   myList.setLibList(new JRBeanCollectionDataSource(repDataList));
                //   List<PlList> s = new ArrayList<PlList>();
                //  s.add(myList);
                new ReportBean().openPdf("PL_Report_"+ymd.format(sdf.parse(getTodayDate())), "PL_Report",new JRBeanCollectionDataSource(repDataList), fillParams);
            } else if (this.branch.equalsIgnoreCase("CA")) {
                PlList myList = new PlList();
                myList.setAssList(new JRBeanCollectionDataSource(repDataList));
                myList.setLibList(new JRBeanCollectionDataSource(repDataList));
                List<PlList> s = new ArrayList<PlList>();
                s.add(myList);
                new ReportBean().openPdf("PL_Report_PageWise_"+ymd.format(sdf.parse(getTodayDate())), "PL_Report_PageWise",new JRBeanCollectionDataSource(s), fillParams);
            } else {
                 if(this.exCounter.equalsIgnoreCase("Y")){
                   new ReportBean().jasperReport("PL_Report", "text/html",
                        new JRBeanCollectionDataSource(repDataList), fillParams, "Consolidated Profit And Loss");   
                }else{
                PlList myList = new PlList();
                myList.setAssList(new JRBeanCollectionDataSource(repDataList));
                myList.setLibList(new JRBeanCollectionDataSource(repDataList));
                List<PlList> s = new ArrayList<PlList>();
                s.add(myList);
                new ReportBean().openPdf("PL_Report_PageWise_"+ymd.format(sdf.parse(getTodayDate())), "PL_Report_PageWise",new JRBeanCollectionDataSource(s), fillParams);
            }
           }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}