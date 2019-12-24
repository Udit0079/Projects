/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.InterestReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.FdRdFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ThriftInterestReport extends BaseBean {

    private String message;
    private String area;
    private List<SelectItem> areaList;
    private String frmDt;
    private String toDt;
    private String branch;
    private List<SelectItem> branchList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private FdRdFacadeRemote fdrdRemoteFacade;
    private ShareReportFacadeRemote horfr;

    public ThriftInterestReport() {
        try {

            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            fdrdRemoteFacade = (FdRdFacadeRemote) ServiceLocator.getInstance().lookup("FdRdFacade");
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("0", "--Select--"));
            areaList.add(new SelectItem("AL", " All"));
            List result = horfr.getAreaTypeList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString()));
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String bankName = "", bankAddress = "";
        try {

            if (area.equalsIgnoreCase("0") || area == null) {
                setMessage("Please select Area !");
                return;
            }

            if (!Validator.validateDate(frmDt)) {
                this.setMessage("Please check from date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                this.setMessage("Please check to date");
                return;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                this.setMessage("From date should be less then to date");
                return;
            }
            if (dmy.parse(toDt).after(date)) {
                this.setMessage("To date should be less or Equal to Current date");
                return;
            }


            List<InterestReportPojo> interstReportList = fdrdRemoteFacade.getThriftInterestData(branch, area, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
            if (interstReportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            String repName = "THRIFT INTEREST REPORT";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", repName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", frmDt + " to " + toDt);
            fillParams.put("pPrintedDt", getTodayDate());
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);

            new ReportBean().jasperReport("ThriftInterestReport", "text/html", new JRBeanCollectionDataSource(interstReportList), fillParams, "THRIFT INTEREST REPORT");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", repName);



        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void printPdf() {

        setMessage("");
        String bankName = "", bankAddress = "";
        try {

            if (area.equalsIgnoreCase("0") || area == null) {
                setMessage("Please select Area !");
                return;
            }

            if (!Validator.validateDate(frmDt)) {
                this.setMessage("Please check from date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                this.setMessage("Please check to date");
                return;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                this.setMessage("From date should be less then to date");
                return;
            }
            if (dmy.parse(toDt).after(date)) {
                this.setMessage("To date should be less or Equal to Current date");
                return;
            }

            List<InterestReportPojo> interstReportList = fdrdRemoteFacade.getThriftInterestData(branch, area, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
            if (interstReportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            String repName = "THRIFT INTEREST REPORT";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", repName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", frmDt + " to " + toDt);
            fillParams.put("pPrintedDt", getTodayDate());
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", bankAddress);

            new ReportBean().openPdf("THRIFT INTEREST REPORT_" + ymd.format(dmy.parse(getTodayDate())), "ThriftInterestReport", new JRBeanCollectionDataSource(interstReportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", repName);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void btnRefreshAction() {
        setMessage("");
        setFrmDt(dmy.format(date));
        setToDt(dmy.format(date));
    }

    public String btnExitAction() {

        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
