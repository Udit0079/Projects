/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.GlbList;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
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
 * @author Admin
 */
public class Glb extends BaseBean {

    String message;
    private String calDate;
    private String branch;
    private List<SelectItem> branchList;
    private String reportIn;
    private List<SelectItem> reportInList;
    private String optgl;
    private List<SelectItem> optglList;
    Date dt = new Date();
    private CommonReportMethodsRemote common;
    private GLReportFacadeRemote glBeanRemote;
    private RbiReportFacadeRemote ossBeanRemote;
    private RbiSoss1And2ReportFacadeRemote oss1And2BeanRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public Glb() {
        setCalDate(sdf.format(dt));
        reportInList = new ArrayList<SelectItem>();
        try {
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            oss1And2BeanRemote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
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

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "THOUSANDS"));
            reportInList.add(new SelectItem("L", "LACS"));
            reportInList.add(new SelectItem("C", "CARORS"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void downloadPdf() {
        this.setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        String report = "";
        if (this.getCalDate() == null) {
            this.setMessage("Please Fill Date");
        }

        try {
            if (!this.branch.equalsIgnoreCase("0A")) {
                if (!this.branch.equalsIgnoreCase("0B")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "General Ledger";
                } else {
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    report = "Consolidated General Ledger";
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
                report = "Consolidated General Ledger";
            }
            String[] arr = Util.getReportOptionAndDescription(this.reportIn);

            String date = ymd.format(sdf.parse(this.calDate));

            List<RbiSossPojo> resultList = oss1And2BeanRemote.getSOSS1("FORM9", date, getOrgnBrCode(), new BigDecimal(arr[0]),"N");

            BigDecimal grandTotalAssets = new BigDecimal("0");
            BigDecimal grandTotalLib = new BigDecimal("0");
            List<RbiSossPojo> assList = new ArrayList<RbiSossPojo>();
            List<RbiSossPojo> libList = new ArrayList<RbiSossPojo>();
            if (!resultList.isEmpty()) {
                for (RbiSossPojo cbsPojo : resultList) {
                    RbiSossPojo pojo1 = new RbiSossPojo();
                    RbiSossPojo pojo2 = new RbiSossPojo();
                    if (cbsPojo.getgNo().toString().equalsIgnoreCase("1")) {

                        pojo1.setAmt(cbsPojo.getAmt());
                        pojo1.setClassification(cbsPojo.getClassification());
                        pojo1.setDescription(cbsPojo.getDescription());
                        pojo1.setFormula(cbsPojo.getFormula());
                        pojo1.setGlHeadTo(cbsPojo.getGlHeadTo());
                        pojo1.setGlheadFrom(cbsPojo.getGlheadFrom());
                        pojo1.setNpaClassification(cbsPojo.getNpaClassification());
                        pojo1.setRangeBaseOn(cbsPojo.getRangeBaseOn());
                        pojo1.setRangeFrom(cbsPojo.getRangeFrom());
                        pojo1.setRangeTo(cbsPojo.getRangeTo());
                        pojo1.setReportName(cbsPojo.getReportName());
                        pojo1.setgNo(cbsPojo.getgNo());
                        pojo1.setsGNo(cbsPojo.getsGNo());
                        pojo1.setSsGNo(cbsPojo.getSsGNo());
                        pojo1.setSssGNo(cbsPojo.getSssGNo());
                        pojo1.setSsssGNo(cbsPojo.getSsssGNo());
                        pojo1.setfGNo(cbsPojo.getfGNo());
                        pojo1.setfSGNo(cbsPojo.getfSGNo());
                        pojo1.setfSsGNo(cbsPojo.getfSssGNo());
                        pojo1.setfSssGNo(cbsPojo.getfSssGNo());
                        pojo1.setfSsssGNo(cbsPojo.getfSssGNo());
                        pojo1.setsNo(cbsPojo.getsNo());
                        pojo1.setAcNature(cbsPojo.getAcNature());
                        pojo1.setAcType(cbsPojo.getAcType());
                        pojo1.setCountApplicable(cbsPojo.getCountApplicable());               
                        grandTotalAssets = grandTotalAssets.add(cbsPojo.getAmt());
                        assList.add(pojo1);

                    } else {

                        pojo2.setAmt(cbsPojo.getAmt());
                        pojo2.setClassification(cbsPojo.getClassification());
                        pojo2.setDescription(cbsPojo.getDescription());
                        pojo2.setFormula(cbsPojo.getFormula());
                        pojo2.setGlHeadTo(cbsPojo.getGlHeadTo());
                        pojo2.setGlheadFrom(cbsPojo.getGlheadFrom());
                        pojo2.setNpaClassification(cbsPojo.getNpaClassification());
                        pojo2.setRangeBaseOn(cbsPojo.getRangeBaseOn());
                        pojo2.setRangeFrom(cbsPojo.getRangeFrom());
                        pojo2.setRangeTo(cbsPojo.getRangeTo());
                        pojo2.setReportName(cbsPojo.getReportName());
                        pojo2.setgNo(cbsPojo.getgNo());
                        pojo2.setsGNo(cbsPojo.getsGNo());
                        pojo2.setSsGNo(cbsPojo.getSsGNo());
                        pojo2.setSssGNo(cbsPojo.getSssGNo());
                        pojo2.setSsssGNo(cbsPojo.getSsssGNo());
                        pojo2.setfGNo(cbsPojo.getfGNo());
                        pojo2.setfSGNo(cbsPojo.getfSGNo());
                        pojo2.setfSsGNo(cbsPojo.getfSssGNo());
                        pojo2.setfSssGNo(cbsPojo.getfSssGNo());
                        pojo2.setfSsssGNo(cbsPojo.getfSssGNo());
                        pojo2.setsNo(cbsPojo.getsNo());
                        pojo2.setAcNature(cbsPojo.getAcNature());
                        pojo2.setAcType(cbsPojo.getAcType());
                        pojo2.setCountApplicable(cbsPojo.getCountApplicable());    
                        grandTotalLib = grandTotalLib.add(cbsPojo.getAmt());
                        libList.add(pojo2);
                    }
                }
            }

           
            if (!libList.isEmpty() && (!assList.isEmpty())) {

                GlbList myList = new GlbList();
                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));

                Map fillParams = new HashMap();
                fillParams.put("pReportDate", this.calDate);
                fillParams.put("pPrintedDate", sdf.format(new Date()));
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                //fillParams.put("pLibGrandTotal", grandTotalLib);
                //fillParams.put("pAssGrandTotal", grandTotalAssets);
                //fillParams.put("option", this.getOptgl());

                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);

                new ReportBean().openPdf("GlbNew_", "GlbNew", new JRBeanCollectionDataSource(s), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");

    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
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

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
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
