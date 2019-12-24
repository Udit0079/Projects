/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.ActiveRdReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author ANKIT VERMA
 */
public class RdActive extends BaseBean {

    private String acType;
    private List<SelectItem> acTypeList;
    private Date calDate;
    private String errorMsg;
    private OtherReportFacadeRemote facadeRemote;
    private String branch;
    private List<SelectItem> branchList;
    private CommonReportMethodsRemote common;

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public RdActive() {
        try {
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            setCalDate(new Date());
            List rdAccountCode = facadeRemote.getRdAccountCode();
            Vector vec = new Vector();
            acTypeList = new ArrayList<SelectItem>();
            if (!rdAccountCode.isEmpty()) {
                for (int i = 0; i < rdAccountCode.size(); i++) {
                    vec = (Vector) rdAccountCode.get(i);
                    acTypeList.add(new SelectItem(vec.get(0).toString()));
                }
            }

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

    }

    public void btnHtmlAction() {
        String bankName = "ALL", bankAddress = "ALL";
        if (calDate == null) {
            setErrorMsg("Please enter date !");
            return;
        }
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List<ActiveRdReportPojo> rdActiveReportDetails = facadeRemote.getRdActiveReportDetails(acType, ymd.format(calDate), this.getBranch());
            if (rdActiveReportDetails.size() > 0) {

                if (!this.branch.equalsIgnoreCase("0A")) {
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

                String repName = "RD Active Report";
                Map fillParamer = new HashMap();
                fillParamer.put("pReportName", repName);
                fillParamer.put("pPrintedDate", calDate);
                fillParamer.put("pPrintedBy", getUserName());
                fillParamer.put("pbankName", bankName);
                fillParamer.put("pbankAddress", bankAddress);
                new ReportBean().jasperReport("RdActiveReport", "text/html", new JRBeanCollectionDataSource(rdActiveReportDetails), fillParamer, repName);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            } else {
                setErrorMsg("No detail exists !");
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

    }

    public void pdfDownLoad() {
        String bankName = "ALL", bankAddress = "ALL";
        if (calDate == null) {
            setErrorMsg("Please enter date !");
            return;
        }
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List<ActiveRdReportPojo> rdActiveReportDetails = facadeRemote.getRdActiveReportDetails(acType, ymd.format(calDate), this.getBranch());
            if (rdActiveReportDetails.size() > 0) {

                if (!this.branch.equalsIgnoreCase("0A")) {
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

                String repName = "RD Active Report";
                Map fillParamer = new HashMap();
                fillParamer.put("pReportName", repName);
                fillParamer.put("pPrintedDate", calDate);
                fillParamer.put("pPrintedBy", getUserName());
                fillParamer.put("pbankName", bankName);
                fillParamer.put("pbankAddress", bankAddress);

                new ReportBean().openPdf("RD Active Report_" + ymd.format(calDate), "RdActiveReport", new JRBeanCollectionDataSource(rdActiveReportDetails), fillParamer);

                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);

            } else {
                setErrorMsg("No detail exists !");
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

    }

    public void refreshForm() {
        setCalDate(new Date());
        setErrorMsg("");
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }
}
