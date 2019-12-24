package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TransferBatchPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
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
 * @author Zeeshan Waris
 */
public final class TransferBatch extends BaseBean {

    private String message;
    Date calDate = new Date();
    OtherReportFacadeRemote beanRemote;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private String voucherNo;
    private String branch;
    private List<SelectItem> branchList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

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

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
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

    public TransferBatch() {
        branchList = new ArrayList<SelectItem>();
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List list = RemoteCode.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calDate == null) {
                message = "Please Fill Till Date";
                return null;
            }
            if (voucherNo == null || voucherNo.equalsIgnoreCase("")) {
                message = "Please Fill The Voucher Number";
                return null;
            }
            if (!voucherNo.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Voucher No.");
                return null;
            }
            List<TransferBatchPojo> transferBatch = beanRemote.transferBatch(voucherNo, ymdFormat.format(calDate), branch);
            if (!transferBatch.isEmpty()) {
                String repName = "Transfer Batch Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", dmyFormat.format(calDate));
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().jasperReport("TransferBatch", "text/html", new JRBeanCollectionDataSource(transferBatch), fillParams, repName);
                return "report";
            } else {
                setMessage("This Transfer Batch is not your branch./No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calDate == null) {
                message = "Please Fill Till Date";
                return null;
            }
            if (voucherNo == null || voucherNo.equalsIgnoreCase("")) {
                message = "Please Fill The Voucher Number";
                return null;
            }
            if (!voucherNo.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Voucher No.");
                return null;
            }
            List<TransferBatchPojo> transferBatch = beanRemote.transferBatch(voucherNo, ymdFormat.format(calDate), branch);
            if (!transferBatch.isEmpty()) {
                String repName = "Transfer Batch Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", dmyFormat.format(calDate));
                fillParams.put("pPrintedBy", getUserName());
                new ReportBean().openPdf("Transfer Batch Report_" + ymdFormat.format(calDate), "TransferBatch", new JRBeanCollectionDataSource(transferBatch), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                setMessage("This Transfer Batch is not your branch./No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void refreshAction() {
        try {
            setMessage("");
            setVoucherNo("");
            calDate = new Date();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
