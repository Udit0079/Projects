/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.ho.DuplicateCustIdPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
public class DuplicateCustIdInfo extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String orderBy;
    private List<SelectItem> orderByList;
    private String repType;
    private List<SelectItem> repTypeList;
    private String duplicateIdBy;
    private List<SelectItem> duplicateIdByList;
    private boolean disableOrderBy;
    Date dt = new Date();
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    List<DuplicateCustIdPojo> reportList = new ArrayList<DuplicateCustIdPojo>();

    public DuplicateCustIdInfo() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("M", "Merged Id"));
            repTypeList.add(new SelectItem("R", "Remaining Merge Id"));

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("2", "CustName"));
            orderByList.add(new SelectItem("3", "FatherName"));
            orderByList.add(new SelectItem("4", "Pan No."));

            duplicateIdByList = new ArrayList<SelectItem>();
            duplicateIdByList.add(new SelectItem("1", "CustName,FatherName,PanNo. and Dob"));
            duplicateIdByList.add(new SelectItem("2", "CustName and FatherName"));
            duplicateIdByList.add(new SelectItem("3", "Pan No."));
            duplicateIdByList.add(new SelectItem("4", "Aadhar No."));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onReportAction() {
        setMessage("");
        if (repType.equalsIgnoreCase("M")) {
            setDuplicateIdBy("1");
            this.disableOrderBy = true;
        } else {
            this.disableOrderBy = false;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
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

    public String getDuplicateIdBy() {
        return duplicateIdBy;
    }

    public void setDuplicateIdBy(String duplicateIdBy) {
        this.duplicateIdBy = duplicateIdBy;
    }

    public List<SelectItem> getDuplicateIdByList() {
        return duplicateIdByList;
    }

    public void setDuplicateIdByList(List<SelectItem> duplicateIdByList) {
        this.duplicateIdByList = duplicateIdByList;
    }

    public boolean isDisableOrderBy() {
        return disableOrderBy;
    }

    public void setDisableOrderBy(boolean disableOrderBy) {
        this.disableOrderBy = disableOrderBy;
    }

    public void btnHtmlAction() {

        setMessage("");
        String bankName = "";
        String bankAddress = "";
        String report = "Duplicate Customer Id Detail";
        try {

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            Map map = new HashMap();
            map.put("pPrintedBy", this.getUserName());
            map.put("pReportDate", this.dt);
            map.put("pReportName", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            if (repType.equalsIgnoreCase("M")) {
                map.put("pRepType", "Merged Id");
                map.put("pH1", "Org Id as Merged Id Is");
            } else {
                map.put("pRepType", "Remaining Merge Id");
                map.put("pH1", "Duplicate Id Is");
            }
            if (duplicateIdBy.equalsIgnoreCase("4")) {
                map.put("pH2", "Aadhar No.");
            }
            reportList = hoRemote.getDuplicateCustIdData(repType, orderBy, branchCode, duplicateIdBy);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            String jrxmlName = "";
            if (duplicateIdBy.equalsIgnoreCase("1")) {
                jrxmlName = "DuplicateIdInfo";
            } else {
                jrxmlName = "DuplicateIdDetail";
            }
            new ReportBean().jasperReport(jrxmlName, "text/html",
                    new JRBeanCollectionDataSource(reportList), map, "Duplicate Customer Id Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        String bankName = "";
        String bankAddress = "";
        String report = "Duplicate Customer Id Detail";
        try {

            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            Map map = new HashMap();
            map.put("pPrintedBy", this.getUserName());
            map.put("pReportName", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            if (repType.equalsIgnoreCase("M")) {
                map.put("pRepType", "Merged Id");
                map.put("pH1", "Org Id as Merged Id Is");
            } else {
                map.put("pRepType", "Remaining Merge Id");
                map.put("pH1", "Duplicate Id Is");
            }
            if (duplicateIdBy.equalsIgnoreCase("4")) {
                map.put("pH2", "Aadhar No.");
            }
            reportList = hoRemote.getDuplicateCustIdData(repType, orderBy, branchCode, duplicateIdBy);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            String jrxmlName = "";
            if (duplicateIdBy.equalsIgnoreCase("1")) {
                jrxmlName = "DuplicateIdInfo";
            } else {
                jrxmlName = "DuplicateIdDetail";
            }
            new ReportBean().openPdf("Duplicate Customer Id Detail_", jrxmlName, new JRBeanCollectionDataSource(reportList), map);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }

    public void refreshForm() {
        this.setMessage("");

    }
}
