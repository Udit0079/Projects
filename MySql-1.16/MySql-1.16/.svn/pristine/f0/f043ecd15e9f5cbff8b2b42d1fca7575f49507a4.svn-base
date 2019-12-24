/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.deaf;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.constant.CbsConstant;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.DeafMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.deaf.DeafMarkPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.common.exception.ApplicationException;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class DeafMarkInfo extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private String acType;
    private List<SelectItem> acTypeList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    TdReceiptManagementFacadeRemote RemoteCode;
    private DDSReportFacadeRemote ddsRemote;
    private DeafMgmtFacadeRemote deafRemote;
    private DDSManagementFacadeRemote ddsMgmtRemote;
    private CommonReportMethodsRemote common = null;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    List<DeafMarkPojo> repoprtList = new ArrayList<DeafMarkPojo>();

    public DeafMarkInfo() {
        try {
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            deafRemote = (DeafMgmtFacadeRemote) ServiceLocator.getInstance().lookup("DeafMgmtFacade");
            ddsMgmtRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getacNature();
            refreshAction();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void getacNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = ddsRemote.getAccountNatureClassificationWithGl("'C','B'");
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        List actCodeList;
        try {
            acTypeList = new ArrayList<SelectItem>();
            //acTypeList.add(new SelectItem("ALL", "ALL"));
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.TD_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                acTypeList.add(new SelectItem("ALL", "ALL"));
                actCodeList = ddsRemote.getAcctCodeByNature(this.acNature);
            } else {
                acTypeList.add(new SelectItem("Select", "--Select--"));
                actCodeList = ddsMgmtRemote.getGlCodeByGlNature(this.acNature);
            }

            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void printAction() {
        setMessage("");
        String colName = "Receipt No.";
        try {
            Map fillParams = new HashMap();
            if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.TD_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC))) {

                if (acType == null || acType.equalsIgnoreCase("Select")) {
                    message = "Please select A/c Type !";
                    return;
                }
                colName = "Sequence No.";
            }
            if (calFromDate == null) {
                message = "Please Fill From Date !";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill To Date !";
                return;
            }
            if (calFromDate.compareTo(caltoDate) > 0) {
                this.setMessage("From date can not be greater than to date !");
                return;
            }
            repoprtList = deafRemote.getDeafInfoData(branchCode, acNature, acType,
                    ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
            if (repoprtList.isEmpty()) {
                this.setMessage("Data does not exist !");
                return;
            }
            String reportName = "Deaf Mark Detail Report";

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", dmyFormat.format(calFromDate) + " to " + dmyFormat.format(caltoDate));
            fillParams.put("pReportName", reportName);
            fillParams.put("pColName", colName);
            new ReportBean().jasperReport("DeafMarkAccountInfo", "text/html",
                    new JRBeanCollectionDataSource(repoprtList), fillParams, "Deaf Mark Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).
                    sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            refreshAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void downloadExcel() {
        setMessage("");
        String colName = "Receipt No.";
        try {
            Map fillParams = new HashMap();
            if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.TD_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC))) {

                if (acType == null || acType.equalsIgnoreCase("Select")) {
                    message = "Please select A/c Type !";
                    return;
                }
                colName = "Sequence No.";
            }
            if (calFromDate == null) {
                message = "Please Fill From Date !";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill To Date !";
                return;
            }
            if (calFromDate.compareTo(caltoDate) > 0) {
                this.setMessage("From date can not be greater than to date !");
                return;
            }
            repoprtList = deafRemote.getDeafInfoData(branchCode, acNature, acType,
                    ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
            if (repoprtList.isEmpty()) {
                this.setMessage("Data does not exist !");
                return;
            }

            FastReportBuilder fastReport = new FastReportBuilder();
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No.", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Customer Address", 200);
            AbstractColumn deafAmt = ReportBean.getColumn("deafAmt", Double.class, "Amount", 80);
            AbstractColumn deafDate = ReportBean.getColumn("deafDate", String.class, "Deaf date", 90);
            AbstractColumn effectDate = ReportBean.getColumn("effectDate", String.class, "Int.Till.Date", 90);
            AbstractColumn receiptNo = ReportBean.getColumn("receiptNo", String.class, "Receipt No.", 150);

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(deafAmt);
            deafAmt.setStyle(style);
            width = width + deafAmt.getWidth();

            fastReport.addColumn(deafDate);
            width = width + deafDate.getWidth();

            fastReport.addColumn(effectDate);
            width = width + effectDate.getWidth();

            fastReport.addColumn(receiptNo);
            receiptNo.setStyle(style);
            width = width + receiptNo.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Deaf Mark Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repoprtList), fastReport, "Deaf Mark Detail Report");



            refreshAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshAction() {
        setMessage("");
        setAcNature("0");
        setAcType("ALL");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }
}
