/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.cpsms;

import com.cbs.dto.report.CPSMSBatchDetailPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.cpsms.CPSMSViewMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CPSMSBatchDtl extends BaseBean {

    private String date;
    private List<SelectItem> cpsmsMsgIdList;
    private String cpsmsMsgId;
    private List<SelectItem> cpsmsBatchNoList;
    private String cpsmsBatchNo;
    private String msg;
    private CommonReportMethodsRemote reportRemote = null;
    private CPSMSViewMgmtFacadeRemote cpsmsRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CPSMSBatchDtl() {
        try {
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            cpsmsRemote = (CPSMSViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CPSMSViewMgmtFacade");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onblurDate() {
        try {
            List list = cpsmsRemote.getcpsmsMassageId(date);
            cpsmsMsgIdList = new ArrayList<SelectItem>();
            cpsmsMsgIdList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                cpsmsMsgIdList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onblurCPSMSMsgId() {
        try {
            List list = cpsmsRemote.getcpsmsBatchNo(date, cpsmsMsgId);
            cpsmsBatchNoList = new ArrayList<SelectItem>();
            cpsmsBatchNoList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                cpsmsBatchNoList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String viewReport() {
        String bankName = "";
        String branchAddress = "";
        if (date == null || date.equalsIgnoreCase("")) {
            setMsg("Please fill Date");
            return null;
        }
        if (!Validator.validateDate(date)) {
            setMsg("Please fill Proper date ");
            return null;
        }
        if (cpsmsMsgId == null || cpsmsMsgId.equalsIgnoreCase("0")) {
            setMsg("Please Select CPSMS Message Id");
            return null;
        }
        if (cpsmsBatchNo == null || cpsmsBatchNo.equalsIgnoreCase("0")) {
            setMsg("Please Select CPSMS Batch No.");
            return null;
        }
        try {

            List<CPSMSBatchDetailPojo> reportDataList = cpsmsRemote.getCPSMSBatchDetailReport(date, cpsmsMsgId, cpsmsBatchNo);
            String[] data = cpsmsRemote.getcpsmsBatchNoStatusAndTxnNo(date, cpsmsMsgId, cpsmsBatchNo);
            String status = "";
            if (!data[0].equalsIgnoreCase("")) {
                if (data[0].equalsIgnoreCase("01")) {
                    status = "To be Processed";
                } else if (data[0].equalsIgnoreCase("03")) {
                    status = "Success Intra Batch";
                } else if (data[0].equalsIgnoreCase("05")) {
                    status = "Mismatch Print Advice";
                } else if (data[0].equalsIgnoreCase("06")) {
                    status = "Only Debit Transaction Success";
                } else {
                    status = "";
                }

            }
            if (!reportDataList.isEmpty()) {
                List ele = reportRemote.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "CPSMS Batch Detail Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", date);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", bankName);
                fillParams.put("pBankAdd", branchAddress);
                fillParams.put("pCPSMSMsgId", this.cpsmsMsgId);
                fillParams.put("pCPSMSBatchNo", this.cpsmsBatchNo);
                fillParams.put("pStatus", status);
                fillParams.put("pTxnNo", data[1]);
                new ReportBean().jasperReport("cpsms_batch_detail", "text/html", new JRBeanCollectionDataSource(reportDataList), fillParams, repName);
                return "report";
            } else {
                setMsg("No data to print !!");
                return null;
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return null;
        }
    }

    public void downloadPdfreport() {
        String bankName = "";
        String branchAddress = "";
        if (date == null || date.equalsIgnoreCase("")) {
            setMsg("Please fill Date");
            return;
        }
        if (!Validator.validateDate(date)) {
            setMsg("Please fill Proper date ");
            return;
        }
        if (cpsmsMsgId == null || cpsmsMsgId.equalsIgnoreCase("0")) {
            setMsg("Please Select CPSMS Message Id");
            return;
        }
        if (cpsmsBatchNo == null || cpsmsBatchNo.equalsIgnoreCase("0")) {
            setMsg("Please Select CPSMS Batch No.");
            return;
        }
        try {
            List<CPSMSBatchDetailPojo> reportDataList = cpsmsRemote.getCPSMSBatchDetailReport(date, cpsmsMsgId, cpsmsBatchNo);
            String[] data = cpsmsRemote.getcpsmsBatchNoStatusAndTxnNo(date, cpsmsMsgId, cpsmsBatchNo);
            String status = "";
            if (!data[0].equalsIgnoreCase("")) {
                if (data[0].equalsIgnoreCase("01")) {
                    status = "To be Processed";
                } else if (data[0].equalsIgnoreCase("03")) {
                    status = "Success Intra Batch";
                } else if (data[0].equalsIgnoreCase("05")) {
                    status = "Mismatch Print Advice";
                } else if (data[0].equalsIgnoreCase("06")) {
                    status = "Only Debit Transaction Success";
                } else {
                    status = "";
                }

            }
            if (!reportDataList.isEmpty()) {
                List ele = reportRemote.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "CPSMS Batch Detail Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", date);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", bankName);
                fillParams.put("pBankAdd", branchAddress);
                fillParams.put("pCPSMSMsgId", this.cpsmsMsgId);
                fillParams.put("pCPSMSBatchNo", this.cpsmsBatchNo);
                fillParams.put("pStatus", status);
                fillParams.put("pTxnNo", data[1]);
                new ReportBean().openPdf("cpsms_batch_detail_report_" + ymd.format(dmy.parse(date)), "cpsms_batch_detail", new JRBeanCollectionDataSource(reportDataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                setMsg("No data to print !!");
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void btnRefreshAction() {
        this.date = "";
        this.cpsmsMsgId = "";
        this.cpsmsBatchNo = "";
        this.msg = "";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /*---------------------------------------------------Getter And Setter----------------------------------------------------------*/
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCpsmsMsgId() {
        return cpsmsMsgId;
    }

    public void setCpsmsMsgId(String cpsmsMsgId) {
        this.cpsmsMsgId = cpsmsMsgId;
    }

    public String getCpsmsBatchNo() {
        return cpsmsBatchNo;
    }

    public void setCpsmsBatchNo(String cpsmsBatchNo) {
        this.cpsmsBatchNo = cpsmsBatchNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SelectItem> getCpsmsMsgIdList() {
        return cpsmsMsgIdList;
    }

    public void setCpsmsMsgIdList(List<SelectItem> cpsmsMsgIdList) {
        this.cpsmsMsgIdList = cpsmsMsgIdList;
    }

    public List<SelectItem> getCpsmsBatchNoList() {
        return cpsmsBatchNoList;
    }

    public void setCpsmsBatchNoList(List<SelectItem> cpsmsBatchNoList) {
        this.cpsmsBatchNoList = cpsmsBatchNoList;
    }
}
