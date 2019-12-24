/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.CbsGeneralLedgerBookPojo;
import com.cbs.dto.report.GlbTempActypeEntryPojo;
import com.cbs.dto.report.SortByGno;
import com.cbs.dto.report.SortBySGno;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.GlbList;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author sipl
 */
public class ConsolidateGLB extends BaseBean {

    String message;
    Date calDate;
    private String branch;
    private String exCounter;
    private String displayExCtr;
    private List<SelectItem> exCounterList;
    private List<SelectItem> branchList;
    private String optgl;
    private List<SelectItem> optglList;
    private String focusId;
    
    Date dt = new Date();
    private GLReportFacadeRemote glBeanRemote;
    //private TdReceiptManagementFacadeRemote tdFacade;
    private CommonReportMethodsRemote common;
    List<GlbTempActypeEntryPojo> repDataList = new ArrayList<GlbTempActypeEntryPojo>();

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOptgl() {
        return optgl;
    }

    public void setOptgl(String optgl) {
        this.optgl = optgl;
    }

    public List<SelectItem> getOptglList() {
        return optglList;
    }

    public void setOptglList(List<SelectItem> optglList) {
        this.optglList = optglList;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getExCounter() {
        return exCounter;
    }

    public void setExCounter(String exCounter) {
        this.exCounter = exCounter;
    }

    public List<SelectItem> getExCounterList() {
        return exCounterList;
    }

    public void setExCounterList(List<SelectItem> exCounterList) {
        this.exCounterList = exCounterList;
    }

    public String getDisplayExCtr() {
        return displayExCtr;
    }

    public void setDisplayExCtr(String displayExCtr) {
        this.displayExCtr = displayExCtr;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

   
    public ConsolidateGLB() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brnLst = new ArrayList();
            brnLst = glBeanRemote.getBranchCodeListExt(getOrgnBrCode());
            branchList = new ArrayList<>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            optglList = new ArrayList<>();
            optglList.add(new SelectItem("A", "ALL HEADS"));
            optglList.add(new SelectItem("H", "HEAD WITH BALANCE"));
            
            exCounterList = new ArrayList<>();
            exCounterList.add(new SelectItem("N", "No"));
            exCounterList.add(new SelectItem("Y", "Yes"));
            setExCounter("N");
            setDisplayExCtr("none");
            setFocusId("branchddl");
            this.setCalDate(dt);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void checkExtCounter(){
        try {
            setMessage("");
            if(!branch.equals("0A") && common.isExCounterExit(branch)){
                setDisplayExCtr("");
                setFocusId("exddl");
            }
            else{
                setDisplayExCtr("none");
                setFocusId("calDate");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void btnHtmlAction(){
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        String report = "";
        if (this.getCalDate() == null) {
            this.setMessage("Please Fill Date");
        }

        try {
            if (!this.branch.equalsIgnoreCase("0A")) {
                if(!this.branch.equalsIgnoreCase("0B")){
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "General Ledger Book";
                }else{
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "Consolidated General Ledger Book";
                }                
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                report = "Consolidated General Ledger Book";
            }
            
//            repDataList = glBeanRemote.getconsolidateGeneralLedgerBookData(ymd.format(this.getCalDate()), this.getBranch(),getOrgnBrCode());
            repDataList = glBeanRemote.getNewConsolidateGLBData(ymd.format(this.getCalDate()), this.getBranch(),getOrgnBrCode(),exCounter);
            List<CbsGeneralLedgerBookPojo> assList = new ArrayList<CbsGeneralLedgerBookPojo>();
            List<CbsGeneralLedgerBookPojo> libList = new ArrayList<CbsGeneralLedgerBookPojo>();

            double grandTotalAssets = 0.0;
            double grandTotalLib = 0.0;
            if (!repDataList.isEmpty()) {
                for (GlbTempActypeEntryPojo cbsPojo : repDataList) {
                    CbsGeneralLedgerBookPojo pojo1 = new CbsGeneralLedgerBookPojo();
                    CbsGeneralLedgerBookPojo pojo2 = new CbsGeneralLedgerBookPojo();
                    if (cbsPojo.getType().equalsIgnoreCase("A")) {
                        if (cbsPojo.getDramt().abs().compareTo(new BigDecimal("0")) == 1) {
                            pojo1.setACNAME(cbsPojo.getDescription());
                            pojo1.setACTYPE(cbsPojo.getType());
                            pojo1.setAMOUNT(cbsPojo.getDramt().abs());
                            pojo1.setBankaddress(bankAddress);
                            pojo1.setBankname(bankName);
                            pojo1.setGNO(cbsPojo.getGroupCode());
                            pojo1.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalAssets += Math.abs(Double.parseDouble(pojo1.getAMOUNT().toString()));
                            assList.add(pojo1);
                        }
                    } else {
                        if (cbsPojo.getCramt().abs().compareTo(new BigDecimal("0")) == 1) {
                            pojo2.setACNAME(cbsPojo.getDescription());
                            pojo2.setACTYPE(cbsPojo.getType());
                            pojo2.setAMOUNT(cbsPojo.getCramt().abs());
                            pojo2.setBankaddress(bankAddress);
                            pojo2.setBankname(bankName);
                            pojo2.setGNO(cbsPojo.getGroupCode());
                            pojo2.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalLib += Math.abs(Double.parseDouble(pojo2.getAMOUNT().toString()));
                            libList.add(pojo2);
                        }
                    }
                }
            }
            if (!libList.isEmpty() && (!assList.isEmpty())) {

                Map fillParams = new HashMap();

                fillParams.put("pReportDate", sdf.format(this.calDate));
                fillParams.put("pPrintedDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", this.getUserName());

                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);
                fillParams.put("option", this.getOptgl());

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                GlbList myList = new GlbList();

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortByGno());
                chObj.addComparator(new SortBySGno());
                Collections.sort(assList, chObj);
                Collections.sort(libList, chObj);
                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                new ReportBean().jasperReport("generalLedgerBookCon", "text/html",
                        new JRBeanCollectionDataSource(s), fillParams, "Consolidated General Ledger Book");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnPdfAction() {
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        String report = "";
        if (this.getCalDate() == null) {
            this.setMessage("Please Fill Date");
        }

        try {
            if (!this.branch.equalsIgnoreCase("0A")) {
                if(!this.branch.equalsIgnoreCase("0B")){
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "General Ledger Book Report";
                }else{
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "Consolidated General Ledger Book";
                }                
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                report = "Consolidated General Ledger Book";
            }

//            repDataList = glBeanRemote.getconsolidateGeneralLedgerBookData(ymd.format(this.getCalDate()), this.getBranch(),getOrgnBrCode());
            repDataList = glBeanRemote.getNewConsolidateGLBData(ymd.format(this.getCalDate()), this.getBranch(),getOrgnBrCode(),exCounter);
            List<CbsGeneralLedgerBookPojo> assList = new ArrayList<CbsGeneralLedgerBookPojo>();
            List<CbsGeneralLedgerBookPojo> libList = new ArrayList<CbsGeneralLedgerBookPojo>();

            double grandTotalAssets = 0.0;
            double grandTotalLib = 0.0;
            if (!repDataList.isEmpty()) {
                for (GlbTempActypeEntryPojo cbsPojo : repDataList) {
                    CbsGeneralLedgerBookPojo pojo1 = new CbsGeneralLedgerBookPojo();
                    CbsGeneralLedgerBookPojo pojo2 = new CbsGeneralLedgerBookPojo();
                    if (cbsPojo.getType().equalsIgnoreCase("A")) {
                        if (cbsPojo.getDramt().abs().compareTo(new BigDecimal("0")) == 1) {
                            pojo1.setACNAME(cbsPojo.getDescription());
                            pojo1.setACTYPE(cbsPojo.getType());
                            pojo1.setAMOUNT(cbsPojo.getDramt().abs());
                            pojo1.setBankaddress(bankAddress);
                            pojo1.setBankname(bankName);
                            pojo1.setGNO(cbsPojo.getGroupCode());
                            pojo1.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalAssets += Math.abs(Double.parseDouble(pojo1.getAMOUNT().toString()));
                            assList.add(pojo1);
                        }
                    } else {
                        if (cbsPojo.getCramt().abs().compareTo(new BigDecimal("0")) == 1) {
                            pojo2.setACNAME(cbsPojo.getDescription());
                            pojo2.setACTYPE(cbsPojo.getType());
                            pojo2.setAMOUNT(cbsPojo.getCramt().abs());
                            pojo2.setBankaddress(bankAddress);
                            pojo2.setBankname(bankName);
                            pojo2.setGNO(cbsPojo.getGroupCode());
                            pojo2.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalLib += Math.abs(Double.parseDouble(pojo2.getAMOUNT().toString()));
                            libList.add(pojo2);
                        }
                    }
                }
            }
            if (!libList.isEmpty() && (!assList.isEmpty())) {

                Map fillParams = new HashMap();

                fillParams.put("pReportDate", sdf.format(this.calDate));
                fillParams.put("pPrintedDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", this.getUserName());

                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);
                fillParams.put("option", this.getOptgl());

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                GlbList myList = new GlbList();

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortByGno());
                chObj.addComparator(new SortBySGno());
                Collections.sort(assList, chObj);
                Collections.sort(libList, chObj);
                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                
//                new ReportBean().jasperReport("generalLedgerBookCon", "text/html",
//                        new JRBeanCollectionDataSource(s), fillParams, "Consolidated General Ledger Book");
//                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                
                new ReportBean().openPdf("generalLedgerBookCon_" , "generalLedgerBookCon", new JRBeanCollectionDataSource(s), fillParams);  
                 ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshAction(){
        setExCounter("N");
        setDisplayExCtr("none");
        setCalDate(new Date());
        setMessage("");
        setFocusId("branchddl");
    }
    
    public String btnExitAction() {
        return "case1";
    }
}