/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ThriftInterestCalculationReport extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String area;
    private List<SelectItem> areaList;
    private String accType;
    private List<SelectItem> accTypeList;
    private float roi;
    private Date fromDate;
    private Date toDate;
    private LoanInterestCalculationFacadeRemote beanRemote = null;
    private OtherLoanReportFacadeRemote olrepfac;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    TdReceiptManagementFacadeRemote RemoteCode;
    private ShareReportFacadeRemote horfr;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    private List<LoanIntCalcList> dataList = new ArrayList();
    private CommonReportMethodsRemote common;

    public ThriftInterestCalculationReport() {
        try {
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            olrepfac = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List resultList = new ArrayList();
            resultList = beanRemote.getTheftAcType();

            if (!resultList.isEmpty()) {
                accTypeList = new ArrayList();
                accTypeList.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    accTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    if (!brnVector.get(0).toString().equalsIgnoreCase("A")) {
                        branchList.add(new SelectItem(brnVector.get(0).toString(), brnVector.get(1).toString()));
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAreaListmethod() {
        try {
            areaList = new ArrayList();
            areaList.add(new SelectItem("A", "ALL"));
            List result = horfr.getAreaWiseList(this.getBranch());
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    if (!ele.get(0).toString().equalsIgnoreCase("")) {
                        areaList.add(new SelectItem(ele.get(0).toString()));
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            String fromDt = "";
            String toDt = "";
            if (accType == null) {
                accType = "";
            }
            if (accType.equals("0")) {
                accType = "";
            }

            if (!accType.equals("0") && !accType.equals("")) {
                fromDt = beanRemote.allFromDt(accType, getBranch(), "f");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setFromDate(sdf.parse(sdf.format(ymd.parse(fromDt))));

                }
                toDt = beanRemote.allFromDt(accType, getBranch(), "t");
                if (toDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setToDate(sdf.parse(sdf.format(ymd.parse(toDt))));

                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        try {
            if (this.area.equalsIgnoreCase("") || this.area == null) {
                this.setMessage("Please Fill Up Area");
                return;
            }

            if (this.roi == 0.0) {
                this.setMessage("Please Fill Roi");
                return;
            }

            if (this.fromDate == null) {
                this.setMessage("Please Fill From Date");
                return;
            }

            if (this.toDate == null) {
                this.setMessage("Please Fill To Date");
                return;
            }

            Map<String, String> accareamap = new HashMap();
            accareamap = olrepfac.getThriftAreawiseAcc(area, branch);
            List<LoanIntCalcList> resultList = new ArrayList();

            resultList = beanRemote.threftIntCalcPostal("ALL", accType, "", roi, sdf.format(fromDate), sdf.format(toDate), getUserName(), getBranch());
            if (!accareamap.isEmpty() && !resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    LoanIntCalcList pojo = new LoanIntCalcList();
                    if (accareamap.containsKey(resultList.get(i).getAcNo())) {
                        pojo.setSno(String.valueOf(i + 1));
                        pojo.setAcNo(resultList.get(i).getAcNo());
                        pojo.setCustName(resultList.get(i).getCustName());
                        pojo.setFatherName(resultList.get(i).getFatherName());
                        pojo.setRoi(resultList.get(i).getRoi());
                        pojo.setProduct(resultList.get(i).getProduct());
                        pojo.setTotalInt(resultList.get(i).getTotalInt());
                        pojo.setArea(accareamap.get(pojo.getAcNo()));
                        dataList.add(pojo);
                    }

                }
                if (!dataList.isEmpty()) {
                    List bnkList;
                    if (branch.equalsIgnoreCase("0A")) {
                        bnkList = common.getBranchNameandAddress("90");
                    } else {
                        bnkList = common.getBranchNameandAddress(getBranch());
                    }
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    Map fillParams = new HashMap();
                    String report = "Thrift Interest Calculation Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", "For the period " + sdf.format(toDate) + " to " + sdf.format(fromDate) + " ");
                    new ReportBean().jasperReport("ThriftInterestCalculationReport", "text/html",
                            new JRBeanCollectionDataSource(dataList), fillParams, "ThriftInterestCalculationReport");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");


                } else {
                    this.setMessage("Data does not exist !!");
                }
            } else {
                this.setMessage("Data does not exist !!");

            }
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

    public void printPdf() {
        try {
            if (this.area.equalsIgnoreCase("") || this.area == null) {
                this.setMessage("Please Fill Up Area");
                return;
            }

            if (this.roi == 0.0) {
                this.setMessage("Please Fill Roi");
                return;
            }

            if (this.fromDate == null) {
                this.setMessage("Please Fill From Date");
                return;
            }

            if (this.toDate == null) {
                this.setMessage("Please Fill To Date");
                return;
            }

            Map<String, String> accareamap = new HashMap();
            accareamap = olrepfac.getThriftAreawiseAcc(area, branch);
            List<LoanIntCalcList> resultList = new ArrayList();
            resultList = beanRemote.cbsTheftIntCalcNew("ALL", accType, "", sdf.format(fromDate), sdf.format(toDate), "", getUserName(), getBranch());
            if (!accareamap.isEmpty() && !resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    LoanIntCalcList pojo = new LoanIntCalcList();
                    if (accareamap.containsKey(resultList.get(i).getAcNo())) {
                        pojo.setSno(String.valueOf(i + 1));
                        pojo.setAcNo(resultList.get(i).getAcNo());
                        pojo.setCustName(resultList.get(i).getCustName());
                        pojo.setFatherName(resultList.get(i).getFatherName());
                        pojo.setRoi(resultList.get(i).getRoi());
                        pojo.setProduct(resultList.get(i).getProduct());
                        pojo.setTotalInt(resultList.get(i).getTotalInt());
                        pojo.setArea(accareamap.get(pojo.getAcNo()));
                        dataList.add(pojo);
                    }
                }
                if (!dataList.isEmpty()) {
                    List bnkList;
                    if (branch.equalsIgnoreCase("0A")) {
                        bnkList = common.getBranchNameandAddress("90");
                    } else {
                        bnkList = common.getBranchNameandAddress(getBranch());
                    }
                    String bankName = "", bankAdd = "";
                    if (!bnkList.isEmpty()) {
                        bankName = bnkList.get(0).toString();
                        bankAdd = bnkList.get(1).toString();
                    }
                    Map fillParams = new HashMap();
                    String report = "Thrift Interest Calculation Report";
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pbankName", bankName);
                    fillParams.put("pbankAddress", bankAdd);
                    fillParams.put("pPrintedDate", this.getTodayDate());
                    fillParams.put("pReportDate", "For the period " + fromDate + " to " + toDate + " ");
                    new ReportBean().openPdf("ThriftInterestCalculationReport", "ThriftInterestCalculationReport", new JRBeanCollectionDataSource(dataList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "ThriftInterestCalculationReport");

                } else {
                    this.setMessage("Data does not exist !!");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshPage() {
        this.setMessage("");
        this.setBranch("0");
        this.setArea("S");
        this.setRoi(0.0F);
        this.setFromDate(null);
        this.setToDate(null);
        this.setAccType("0");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<SelectItem> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SelectItem> areaList) {
        this.areaList = areaList;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public List<SelectItem> getAccTypeList() {
        return accTypeList;
    }

    public void setAccTypeList(List<SelectItem> accTypeList) {
        this.accTypeList = accTypeList;
    }

    public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
