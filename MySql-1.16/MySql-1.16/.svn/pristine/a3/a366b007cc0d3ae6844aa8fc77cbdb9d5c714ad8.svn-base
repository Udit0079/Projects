/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.OutwardClearingBankwiseReportPojo;
import com.cbs.dto.report.OutwardClearingBatchReportPojo;
import com.cbs.dto.report.OutwardClearingEnteredReportPojo;
import com.cbs.dto.report.OutwardClearingSummaryReportPojo;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.CompareOwByAcType;
import com.cbs.web.pojo.OutwardClearingBatchReportPojoList;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class OutwardClearing extends BaseBean {

    String orgnBrCode;
    HttpServletRequest req;
    String message;
    String stxtDate;
    String stxtUser;
    String ddAcType;
    List<SelectItem> ddAcTypeList;
    String dropDown1;
    List<SelectItem> dropDown1List;
    String dropDown2;
    List<SelectItem> dropDown2List;
    String ddOrderBy;
    List<SelectItem> ddOrderByList;
    private String branch;
    private List branchList;
    private final String jndiHomeName = "ClgReportFacade";
    private ClgReportFacadeRemote outwardClgRemote = null;
    Date calDate;
    String flag;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getStxtDate() {
        return stxtDate;
    }

    public void setStxtDate(String stxtDate) {
        this.stxtDate = stxtDate;
    }

    public String getStxtUser() {
        return stxtUser;
    }

    public void setStxtUser(String stxtUser) {
        this.stxtUser = stxtUser;
    }

    public String getDdAcType() {
        return ddAcType;
    }

    public void setDdAcType(String ddAcType) {
        this.ddAcType = ddAcType;
    }

    public List<SelectItem> getDdAcTypeList() {
        return ddAcTypeList;
    }

    public void setDdAcTypeList(List<SelectItem> ddAcTypeList) {
        this.ddAcTypeList = ddAcTypeList;
    }

    public String getDropDown1() {
        return dropDown1;
    }

    public void setDropDown1(String dropDown1) {
        this.dropDown1 = dropDown1;
    }

    public List<SelectItem> getDropDown1List() {
        return dropDown1List;
    }

    public void setDropDown1List(List<SelectItem> dropDown1List) {
        this.dropDown1List = dropDown1List;
    }

    public String getDropDown2() {
        return dropDown2;
    }

    public void setDropDown2(String dropDown2) {
        this.dropDown2 = dropDown2;
    }

    public List<SelectItem> getDropDown2List() {
        return dropDown2List;
    }

    public void setDropDown2List(List<SelectItem> dropDown2List) {
        this.dropDown2List = dropDown2List;
    }

    public String getDdOrderBy() {
        return ddOrderBy;
    }

    public void setDdOrderBy(String ddOrderBy) {
        this.ddOrderBy = ddOrderBy;
    }

    public List<SelectItem> getDdOrderByList() {
        return ddOrderByList;
    }

    public void setDdOrderByList(List<SelectItem> ddOrderByList) {
        this.ddOrderByList = ddOrderByList;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List getBranchList() {
        return branchList;
    }

    public void setBranchList(List branchList) {
        this.branchList = branchList;
    }

    public OutwardClearing() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setStxtUser(req.getRemoteUser());
            //  setStxtUser("Nishant");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setStxtDate(sdf.format(date));

            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("O/W Clg Entered", "O/W Clg Entered"));
            ddAcTypeList.add(new SelectItem("O/W Clg Entered Based On Batch", "O/W Clg Entered Based On Batch"));
            ddAcTypeList.add(new SelectItem("O/W Clg Passed", "O/W Clg Passed"));
            ddAcTypeList.add(new SelectItem("O/W CLG RETURNED CHEQUES", "O/W CLG RETURNED CHEQUES"));
            ddAcTypeList.add(new SelectItem("O/W CLG HELD CHEQUES", "O/W CLG HELD CHEQUES"));
            ddAcTypeList.add(new SelectItem("O/W CLG BANKWISE", "O/W CLG BANKWISE"));
            ddAcTypeList.add(new SelectItem("O/W CLG SUMMARY", "O/W CLG SUMMARY"));

            ddOrderByList = new ArrayList<SelectItem>();
            ddOrderByList.add(new SelectItem("AcType", "Account Type"));
            ddOrderByList.add(new SelectItem("AcNo", "Account No."));
            ddOrderByList.add(new SelectItem("VchNo", "Voucher No."));
            ddOrderByList.add(new SelectItem("Amt", "Amount Wise"));

            outwardClgRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            dropDown1List = new ArrayList<SelectItem>();
            dropDown1List.add(new SelectItem("0", "--Select--"));
            List circleList = outwardClgRemote.circleType();
            for (Object element : circleList) {
                Vector vector = (Vector) element;
                dropDown1List.add(new SelectItem(vector.get(0).toString(), vector.get(1).toString()));
            }

            int rVal = outwardClgRemote.MergedRepType();
            if (rVal == 0) {
                dropDown2List = new ArrayList<SelectItem>();
                dropDown2List.add(new SelectItem("N", "NO"));
                dropDown2List.add(new SelectItem("Y", "YES"));
            } else {
                dropDown2List = new ArrayList<SelectItem>();
                dropDown2List.add(new SelectItem("N", "MERGED"));
            }

            this.setFlag("");
            this.setCalDate(new Date());

            // added by Manish kumar
            //String brnCode = orgnBrCode;//this.getOrgnBrCode();
            branchList = new ArrayList<SelectItem>();
            // branchList.add(new SelectItem("0", "--Select--"));
            // List list = outwardClgRemote.getBranchList();
            //common.getAlphacodeAllAndBranch(orgnBrCode);
            List list = common.getAlphacodeAllAndBranch(this.getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vec.get(1).toString())), vec.get(0).toString()));
                //branchList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
            }

            //----------
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void btnHtmlAction() {
        this.setMessage("");
        if (this.branch.equals("0")) {
            this.setMessage("Please Select Branch !");
            return;
        }
        if (this.dropDown1.equals("0")) {
            this.setMessage("Please Select Report");
            //return "Please Select Report";
        }

        if (this.calDate == null) {
            this.setMessage("Please Enter Date");
            //return "Please Enter Date";
        }
        String report = "OUTWARD CLEARING REPORT";
        Map fillParams = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        boolean hdfcFlag = false;
        if (this.dropDown2.equalsIgnoreCase("N")) {
            hdfcFlag = false;
        } else if (this.dropDown2.equalsIgnoreCase("Y")) {
            hdfcFlag = true;
        }
        try {
            if (((this.ddAcType.equalsIgnoreCase("O/W Clg Entered"))
                    || (this.ddAcType.equalsIgnoreCase("O/W Clg Passed"))
                    || (this.ddAcType.equalsIgnoreCase("O/W CLG RETURNED CHEQUES"))
                    || (this.ddAcType.equalsIgnoreCase("O/W CLG HELD CHEQUES")))) {

                if (this.dropDown2.equalsIgnoreCase("Y") && this.ddAcType.equalsIgnoreCase("O/W Clg Entered")) {
                    report = "OUTWARD CLEARING FOR HDFC";
                }
                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                List<OutwardClearingBatchReportPojoList> resultList = new ArrayList<OutwardClearingBatchReportPojoList>();
                List<OutwardClearingEnteredReportPojo> dataList = outwardClgRemote.getOutwardClearingEnteredReport(ymd.format(calDate), ddAcType,
                        this.dropDown1, this.branch, hdfcFlag, ddOrderBy);

                List<OutwardClearingEnteredReportPojo> assList = new ArrayList<OutwardClearingEnteredReportPojo>();
                for (OutwardClearingEnteredReportPojo cbsPojo : dataList) {
                    OutwardClearingEnteredReportPojo pojo1 = new OutwardClearingEnteredReportPojo();
                    pojo1.setBnkName(cbsPojo.getBnkName());
                    pojo1.setBnkAdd(cbsPojo.getBnkAdd());
                    pojo1.setBrncode(cbsPojo.getBrncode());
                    pojo1.setActype(cbsPojo.getActype());
                    pojo1.setsNo(cbsPojo.getsNo());
                    pojo1.setAcNo(cbsPojo.getAcNo());
                    pojo1.setCustName(cbsPojo.getCustName());
                    pojo1.setVoucherNo(cbsPojo.getVoucherNo());
                    pojo1.setBankName(cbsPojo.getBankName());
                    pojo1.setBankAddress(cbsPojo.getBankAddress());
                    pojo1.setInstNo(cbsPojo.getInstNo());
                    pojo1.setInstDate(cbsPojo.getInstDate());
                    pojo1.setInstAmount(cbsPojo.getInstAmount());
                    pojo1.setVchAmount(cbsPojo.getVchAmount());
                    pojo1.setStatus(cbsPojo.getStatus());
                    pojo1.setReasons(cbsPojo.getReasons());
                    pojo1.setMicrCode(cbsPojo.getMicrCode());
                    assList.add(pojo1);
                }
                Collections.sort(assList, new CompareOwByAcType());
                if (!dataList.isEmpty()) {
                    for (int j = 0; j < dataList.size(); j++) {
                        OutwardClearingEnteredReportPojo cbsPojo = dataList.get(j);
                        OutwardClearingBatchReportPojoList pojo1 = new OutwardClearingBatchReportPojoList();

                        pojo1.setBnkName(cbsPojo.getBnkName());
                        pojo1.setBnkAdd(cbsPojo.getBnkAdd());
                        pojo1.setBrncode(cbsPojo.getBrncode());
                        pojo1.setActype(cbsPojo.getActype());
                        pojo1.setsNo(cbsPojo.getsNo());
                        pojo1.setAcNo(cbsPojo.getAcNo());
                        pojo1.setCustName(cbsPojo.getCustName());
                        pojo1.setVoucherNo(cbsPojo.getVoucherNo());
                        pojo1.setBankName(cbsPojo.getBankName());
                        pojo1.setBankAddress(cbsPojo.getBankAddress());
                        pojo1.setInstNo(cbsPojo.getInstNo());
                        pojo1.setInstDate(cbsPojo.getInstDate());
                        pojo1.setInstAmount(cbsPojo.getInstAmount());
                        pojo1.setVchAmount(cbsPojo.getVchAmount());
                        pojo1.setStatus(cbsPojo.getStatus());
                        pojo1.setReasons(cbsPojo.getReasons());
                        pojo1.setMicrCode(cbsPojo.getMicrCode());
                        pojo1.setAssList(new JRBeanCollectionDataSource(assList));

                        resultList.add(pojo1);
                    }
                }
                new ReportBean().jasperReport("OW_CLG_ENT_PASS_RET_CON", "text/html",
                        new JRBeanCollectionDataSource(resultList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else if (this.ddAcType.equalsIgnoreCase("O/W Clg Entered Based On Batch")) {

                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));

                List<OutwardClearingBatchReportPojo> dataList = outwardClgRemote.getOutwardClearingBasedOnBatchReport(ymd.format(calDate), dropDown1,
                        this.branch, ddOrderBy);
                new ReportBean().jasperReport("OW_CLG_BASED_ON_BATCH", "text/html",
                        new JRBeanCollectionDataSource(dataList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else if (this.ddAcType.equalsIgnoreCase("O/W CLG SUMMARY")) {

                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));

                List<OutwardClearingSummaryReportPojo> dataList = outwardClgRemote.getOutwardClearingSummaryReport(ymd.format(calDate), ddAcType, dropDown1,
                        this.branch);
                new ReportBean().jasperReport("OW_CLG_SUMMRY", "text/html",
                        new JRBeanCollectionDataSource(dataList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else if (this.ddAcType.equalsIgnoreCase("O/W CLG BANKWISE")) {

                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));

                List<OutwardClearingBankwiseReportPojo> dataList = outwardClgRemote.getOutwardClearingBankwiseReport(ymd.format(calDate), ddAcType, dropDown1,
                        this.branch);
                new ReportBean().jasperReport("OW_CLG_BANKWISE", "text/html",
                        new JRBeanCollectionDataSource(dataList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        //return "report";
    }

    public void btnPdfAction() {
        this.setMessage("");
        if (this.branch.equals("0")) {
            this.setMessage("Please Select Branch !");
            return;
        }
        if (this.dropDown1.equals("0")) {
            this.setMessage("Please Select Report");
            //return "Please Select Report";
        }

        if (this.calDate == null) {
            this.setMessage("Please Enter Date");
            //return "Please Enter Date";
        }
        String report = "OUTWARD CLEARING REPORT";
        Map fillParams = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        boolean hdfcFlag = false;
        if (this.dropDown2.equalsIgnoreCase("N")) {
            hdfcFlag = false;
        } else if (this.dropDown2.equalsIgnoreCase("Y")) {
            hdfcFlag = true;
        }
        try {
            if (((this.ddAcType.equalsIgnoreCase("O/W Clg Entered"))
                    || (this.ddAcType.equalsIgnoreCase("O/W Clg Passed"))
                    || (this.ddAcType.equalsIgnoreCase("O/W CLG RETURNED CHEQUES"))
                    || (this.ddAcType.equalsIgnoreCase("O/W CLG HELD CHEQUES")))) {

                if (this.dropDown2.equalsIgnoreCase("Y") && this.ddAcType.equalsIgnoreCase("O/W Clg Entered")) {
                    report = "OUTWARD CLEARING FOR HDFC";
                }
                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                List<OutwardClearingBatchReportPojoList> resultList = new ArrayList<OutwardClearingBatchReportPojoList>();
                List<OutwardClearingEnteredReportPojo> dataList = outwardClgRemote.getOutwardClearingEnteredReport(ymd.format(calDate), ddAcType,
                        this.dropDown1, this.branch, hdfcFlag, ddOrderBy);

                List<OutwardClearingEnteredReportPojo> assList = new ArrayList<OutwardClearingEnteredReportPojo>();
                for (OutwardClearingEnteredReportPojo cbsPojo : dataList) {
                    OutwardClearingEnteredReportPojo pojo1 = new OutwardClearingEnteredReportPojo();
                    pojo1.setBnkName(cbsPojo.getBnkName());
                    pojo1.setBnkAdd(cbsPojo.getBnkAdd());
                    pojo1.setBrncode(cbsPojo.getBrncode());
                    pojo1.setActype(cbsPojo.getActype());
                    pojo1.setsNo(cbsPojo.getsNo());
                    pojo1.setAcNo(cbsPojo.getAcNo());
                    pojo1.setCustName(cbsPojo.getCustName());
                    pojo1.setVoucherNo(cbsPojo.getVoucherNo());
                    pojo1.setBankName(cbsPojo.getBankName());
                    pojo1.setBankAddress(cbsPojo.getBankAddress());
                    pojo1.setInstNo(cbsPojo.getInstNo());
                    pojo1.setInstDate(cbsPojo.getInstDate());
                    pojo1.setInstAmount(cbsPojo.getInstAmount());
                    pojo1.setVchAmount(cbsPojo.getVchAmount());
                    pojo1.setStatus(cbsPojo.getStatus());
                    pojo1.setMicrCode(cbsPojo.getMicrCode());
                    assList.add(pojo1);
                }
                Collections.sort(assList, new CompareOwByAcType());
                if (!dataList.isEmpty()) {
                    for (int j = 0; j < dataList.size(); j++) {
                        OutwardClearingEnteredReportPojo cbsPojo = dataList.get(j);
                        OutwardClearingBatchReportPojoList pojo1 = new OutwardClearingBatchReportPojoList();

                        pojo1.setBnkName(cbsPojo.getBnkName());
                        pojo1.setBnkAdd(cbsPojo.getBnkAdd());
                        pojo1.setBrncode(cbsPojo.getBrncode());
                        pojo1.setActype(cbsPojo.getActype());
                        pojo1.setsNo(cbsPojo.getsNo());
                        pojo1.setAcNo(cbsPojo.getAcNo());
                        pojo1.setCustName(cbsPojo.getCustName());
                        pojo1.setVoucherNo(cbsPojo.getVoucherNo());
                        pojo1.setBankName(cbsPojo.getBankName());
                        pojo1.setBankAddress(cbsPojo.getBankAddress());
                        pojo1.setInstNo(cbsPojo.getInstNo());
                        pojo1.setInstDate(cbsPojo.getInstDate());
                        pojo1.setInstAmount(cbsPojo.getInstAmount());
                        pojo1.setVchAmount(cbsPojo.getVchAmount());
                        pojo1.setStatus(cbsPojo.getStatus());
                        pojo1.setMicrCode(cbsPojo.getMicrCode());
                        pojo1.setAssList(new JRBeanCollectionDataSource(assList));

                        resultList.add(pojo1);
                    }
                }
                new ReportBean().openPdf("OUTWARD CLEARING REPORT_" + ymd.format(sdf.parse(getTodayDate())), "OW_CLG_ENT_PASS_RET_CON",
                        new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else if (this.ddAcType.equalsIgnoreCase("O/W Clg Entered Based On Batch")) {

                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));

                List<OutwardClearingBatchReportPojo> dataList = outwardClgRemote.getOutwardClearingBasedOnBatchReport(ymd.format(calDate), dropDown1,
                        this.branch, ddOrderBy);
                new ReportBean().openPdf("OUTWARD CLEARING REPORT_" + ymd.format(sdf.parse(getTodayDate())), "OW_CLG_BASED_ON_BATCH",
                        new JRBeanCollectionDataSource(dataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else if (this.ddAcType.equalsIgnoreCase("O/W CLG SUMMARY")) {

                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));

                List<OutwardClearingSummaryReportPojo> dataList = outwardClgRemote.getOutwardClearingSummaryReport(ymd.format(calDate), ddAcType, dropDown1,
                        this.branch);
                new ReportBean().openPdf("OUTWARD CLEARING REPORT_" + ymd.format(sdf.parse(getTodayDate())), "OW_CLG_SUMMRY",
                        new JRBeanCollectionDataSource(dataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);

            } else if (this.ddAcType.equalsIgnoreCase("O/W CLG BANKWISE")) {

                fillParams.put("pPrintedBy", this.stxtUser);
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", sdf.format(calDate));

                List<OutwardClearingBankwiseReportPojo> dataList = outwardClgRemote.getOutwardClearingBankwiseReport(ymd.format(calDate), ddAcType, dropDown1,
                        this.branch);
                new ReportBean().openPdf("OUTWARD CLEARING REPORT_" + ymd.format(sdf.parse(getTodayDate())), "OW_CLG_BANKWISE",
                        new JRBeanCollectionDataSource(dataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void ddAcTypeProcessValueChange() {
        if (this.ddAcType.equalsIgnoreCase("O/W Clg Entered")) {
            this.setFlag("");
        } else {
            this.setFlag("none");
        }
    }

    public void refreshForm() {
        this.setCalDate(new Date());
        this.setBranch("0");
        this.setDropDown1("0");
        this.setDdAcType("O/W Clg Entered");
        this.setDropDown2("N");
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }
}
