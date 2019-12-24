/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.CbsCompGeneralLedgerBookPojo;
import com.cbs.dto.report.GlbComperativePojo;
import com.cbs.dto.report.SortByCompGno;
import com.cbs.dto.report.SortByCompSGno;
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
public class ComperativeGLB extends BaseBean {

    String message;
    Date calDate1;
    Date calDate2;
    private String branch;
    private List<SelectItem> branchList;
    private String optgl;
    private List<SelectItem> optglList;
    Date dt = new Date();
    private GLReportFacadeRemote glBeanRemote;
    private CommonReportMethodsRemote common;
    List<GlbComperativePojo> repDataList = new ArrayList<GlbComperativePojo>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

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

    public Date getCalDate1() {
        return calDate1;
    }

    public void setCalDate1(Date calDate1) {
        this.calDate1 = calDate1;
    }

    public Date getCalDate2() {
        return calDate2;
    }

    public void setCalDate2(Date calDate2) {
        this.calDate2 = calDate2;
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

    public List<GlbComperativePojo> getRepDataList() {
        return repDataList;
    }

    public void setRepDataList(List<GlbComperativePojo> repDataList) {
        this.repDataList = repDataList;
    }    

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }
    
    public ComperativeGLB() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brnLst = new ArrayList();
            brnLst = glBeanRemote.getBranchCodeListExt(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            optglList = new ArrayList<SelectItem>();
            optglList.add(new SelectItem("A", "ALL HEADS"));
            optglList.add(new SelectItem("H", "HEAD WITH BALANCE"));

            this.setCalDate1(dt);
            this.setCalDate2(dt);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnHtmlAction() {
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        String report = "";
        if (this.getCalDate1() == null) {
            this.setMessage("Please Fill Date1");
        }
        
        if (this.getCalDate2() == null) {
            this.setMessage("Please Fill Date2");
        }

        try {
            if (!this.branch.equalsIgnoreCase("0A")) {
                if(!this.branch.equalsIgnoreCase("0B")){
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "Comperative General Ledger Book";
                }else{
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "Consolidated Comperative General Ledger Book";
                }                
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                report = "Consolidated Comperative General Ledger Book";
            }

            repDataList = glBeanRemote.getComperativeGlbData(ymd.format(this.getCalDate1()),ymd.format(this.getCalDate2()), this.getBranch(),getOrgnBrCode());
            List<CbsCompGeneralLedgerBookPojo> assList = new ArrayList<CbsCompGeneralLedgerBookPojo>();
            List<CbsCompGeneralLedgerBookPojo> libList = new ArrayList<CbsCompGeneralLedgerBookPojo>();

            double grandTotalAssets = 0.0,grandTotalAssets1 = 0.0;
            double grandTotalLib = 0.0,grandTotalLib1 = 0.0;
            if (!repDataList.isEmpty()) {
                for (GlbComperativePojo cbsPojo : repDataList) {
                    CbsCompGeneralLedgerBookPojo pojo1 = new CbsCompGeneralLedgerBookPojo();
                    CbsCompGeneralLedgerBookPojo pojo2 = new CbsCompGeneralLedgerBookPojo();
                    if (cbsPojo.getType().equalsIgnoreCase("A")) {
                        if ((!((cbsPojo.getDramt().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getDramt().abs().compareTo(new BigDecimal("0.0")) == 0)) && !((cbsPojo.getDramt1().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getDramt1().abs().compareTo(new BigDecimal("0.0")) == 0)))) {
                            pojo1.setACNAME(cbsPojo.getDescription());
                            pojo1.setACTYPE(cbsPojo.getType());
                            pojo1.setAMOUNT(cbsPojo.getDramt().abs());
                            pojo1.setAMOUNT1(cbsPojo.getDramt1().abs());
                            pojo1.setBankaddress(bankAddress);
                            pojo1.setBankname(bankName);
                            pojo1.setGNO(cbsPojo.getGroupCode());
                            pojo1.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalAssets += Math.abs(Double.parseDouble(pojo1.getAMOUNT().toString()));
                            grandTotalAssets1 += Math.abs(Double.parseDouble(pojo1.getAMOUNT1().toString()));
                            assList.add(pojo1);
                        }
                    } else {
                        if ((!((cbsPojo.getCramt().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getCramt().abs().compareTo(new BigDecimal("0.0")) == 0)) || !((cbsPojo.getCramt1().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getCramt1().abs().compareTo(new BigDecimal("0.0")) == 0)))) {
                            pojo2.setACNAME(cbsPojo.getDescription());
                            pojo2.setACTYPE(cbsPojo.getType());
                            pojo2.setAMOUNT(cbsPojo.getCramt().abs());
                            pojo2.setAMOUNT1(cbsPojo.getCramt1().abs());
                            pojo2.setBankaddress(bankAddress);
                            pojo2.setBankname(bankName);
                            pojo2.setGNO(cbsPojo.getGroupCode());
                            pojo2.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalLib += Math.abs(Double.parseDouble(pojo2.getAMOUNT().toString()));
                            grandTotalLib1 += Math.abs(Double.parseDouble(pojo2.getAMOUNT1().toString()));
                            libList.add(pojo2);
                        }
                    }
                }
            }
            if (!libList.isEmpty() && (!assList.isEmpty())) {
                
                Map fillParams = new HashMap();

                fillParams.put("pReportDate", sdf.format(this.calDate1));
                fillParams.put("pReportDate1", sdf.format(this.calDate2));
                fillParams.put("pPrintedDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", this.getUserName());

                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);
                fillParams.put("pLibGrandTotal1", grandTotalLib1);
                fillParams.put("pAssGrandTotal1", grandTotalAssets1);
                fillParams.put("option", this.getOptgl());

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                GlbList myList = new GlbList();

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortByCompGno());
                chObj.addComparator(new SortByCompSGno());
                Collections.sort(assList, chObj);
                Collections.sort(libList, chObj);
                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                new ReportBean().jasperReport("generalLedgerBookConComp", "text/html",
                        new JRBeanCollectionDataSource(s), fillParams,report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnPdfAction() {
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        String report = "";
        if (this.getCalDate1() == null) {
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
                    report = "Comperative General Ledger Book";
                }else{
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "Consolidated Comperative General Ledger Book";
                }                
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                report = "Consolidated Comperative General Ledger Book";
            }

            repDataList = glBeanRemote.getComperativeGlbData(ymd.format(this.getCalDate1()),ymd.format(this.getCalDate2()), this.getBranch(),getOrgnBrCode());
            List<CbsCompGeneralLedgerBookPojo> assList = new ArrayList<CbsCompGeneralLedgerBookPojo>();
            List<CbsCompGeneralLedgerBookPojo> libList = new ArrayList<CbsCompGeneralLedgerBookPojo>();

            double grandTotalAssets = 0.0,grandTotalAssets1 = 0.0;
            double grandTotalLib = 0.0,grandTotalLib1 = 0.0;
            if (!repDataList.isEmpty()) {
                for (GlbComperativePojo cbsPojo : repDataList) {
                    CbsCompGeneralLedgerBookPojo pojo1 = new CbsCompGeneralLedgerBookPojo();
                    CbsCompGeneralLedgerBookPojo pojo2 = new CbsCompGeneralLedgerBookPojo();
                    if (cbsPojo.getType().equalsIgnoreCase("A")) {
                        if ((!((cbsPojo.getDramt().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getDramt().abs().compareTo(new BigDecimal("0.0")) == 0)) && !((cbsPojo.getDramt1().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getDramt1().abs().compareTo(new BigDecimal("0.0")) == 0)))) {
                            pojo1.setACNAME(cbsPojo.getDescription());
                            pojo1.setACTYPE(cbsPojo.getType());
                            pojo1.setAMOUNT(cbsPojo.getDramt().abs());
                            pojo1.setAMOUNT1(cbsPojo.getDramt1().abs());
                            pojo1.setBankaddress(bankAddress);
                            pojo1.setBankname(bankName);
                            pojo1.setGNO(cbsPojo.getGroupCode());
                            pojo1.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalAssets += Math.abs(Double.parseDouble(pojo1.getAMOUNT().toString()));
                            grandTotalAssets1 += Math.abs(Double.parseDouble(pojo1.getAMOUNT1().toString()));
                            assList.add(pojo1);
                        }
                    } else {
                        if ((!((cbsPojo.getCramt().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getCramt().abs().compareTo(new BigDecimal("0.0")) == 0)) || !((cbsPojo.getCramt1().abs().compareTo(new BigDecimal("0")) == 0) || (cbsPojo.getCramt1().abs().compareTo(new BigDecimal("0.0")) == 0)))) {
                            pojo2.setACNAME(cbsPojo.getDescription());
                            pojo2.setACTYPE(cbsPojo.getType());
                            pojo2.setAMOUNT(cbsPojo.getCramt().abs());
                            pojo2.setAMOUNT1(cbsPojo.getCramt1().abs());
                            pojo2.setBankaddress(bankAddress);
                            pojo2.setBankname(bankName);
                            pojo2.setGNO(cbsPojo.getGroupCode());
                            pojo2.setSGNO(cbsPojo.getSubGroupCode());
                            grandTotalLib += Math.abs(Double.parseDouble(pojo2.getAMOUNT().toString()));
                            grandTotalLib1 += Math.abs(Double.parseDouble(pojo2.getAMOUNT1().toString()));
                            libList.add(pojo2);
                        }
                    }
                }
            }
            if (!libList.isEmpty() && (!assList.isEmpty())) {

                Map fillParams = new HashMap();

                fillParams.put("pReportDate", sdf.format(this.calDate1));
                fillParams.put("pReportDate1", sdf.format(this.calDate2));
                fillParams.put("pPrintedDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", this.getUserName());

                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);
                fillParams.put("pLibGrandTotal1", grandTotalLib1);
                fillParams.put("pAssGrandTotal1", grandTotalAssets1);
                fillParams.put("option", this.getOptgl());

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                GlbList myList = new GlbList();

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortByCompGno());
                chObj.addComparator(new SortByCompSGno());
                Collections.sort(assList, chObj);
                Collections.sort(libList, chObj);
                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                
                new ReportBean().openPdf("generalLedgerBookConComp_"+ ymd.format(this.calDate1) , "generalLedgerBookConComp", new JRBeanCollectionDataSource(s), fillParams);  
                 ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }
}